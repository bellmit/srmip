package com.kingtake.project.controller.m2income;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.service.m2income.TPmIncomeNodeServiceI;

/**
 * @Title: Controller
 * @Description: ??????????????????
 * @author onlineGenerator
 * @date 2015-07-21 16:35:20
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmIncomeNodeController")
public class TPmIncomeNodeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmIncomeNodeController.class);

    @Autowired
    private TPmIncomeNodeServiceI tPmIncomeNodeService;
    @Autowired
    private SystemService systemService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeNode")
    public ModelAndView tPmIncomeNode(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2income/tPmIncomeNodeList");
    }

    /**
     * ???????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeNodeAndContractNode")
    public ModelAndView tPmIncomeNodeAndContractNode(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String contractNodeId = request.getParameter("contractNodeId");
        String checkFlag = request.getParameter("checkFlag");
        request.setAttribute("checkFlag", checkFlag);
        request.setAttribute("contractNodeId", contractNodeId);
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2income/tPmIncomeNodeAndContractNodeList");
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "incomeContracToIncomeNode")
    public ModelAndView incomeContracToIncomeNode(HttpServletRequest request) {
        String contractId = request.getParameter("contractId");
        request.setAttribute("contractId", contractId);
        return new ModelAndView("com/kingtake/project/m2income/incomeContracToIncomeNodeList");
    }

    /**
     * easyui AJAX????????????
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeNodeEntity.class, dataGrid);
        //?????????????????????
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeNode,
                request.getParameterMap());
        try {
            //???????????????????????????
            String contractId = request.getParameter("contractId");
            if (StringUtil.isNotEmpty(contractId)) {
                TPmIncomeContractApprEntity contract = systemService.get(TPmIncomeContractApprEntity.class, contractId);
                StringBuffer incomeNodeIds = new StringBuffer();
                if (contract != null) {
                    List<TPmContractNodeEntity> contractNodes = contract.getTPmContractNodes();
                    if (contractNodes != null && contractNodes.size() > 0) {
                        for (TPmContractNodeEntity contractNode : contractNodes) {
                            incomeNodeIds.append(contractNode.getProjPayNodeId());
                            incomeNodeIds.append(",");
                        }
                    }
                }
                //????????????????????????
                if ("".equals(incomeNodeIds.toString())) {
                    cq.eq("id", "*");
                } else {
                    String incomeNodeIdsStr = incomeNodeIds.toString();
                    cq.in("id", incomeNodeIdsStr.substring(0, incomeNodeIdsStr.length() - 1).split(","));
                    cq.addOrder("incomeTime", SortDirection.asc);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();

        this.tPmIncomeNodeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * ????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmIncomeNode = systemService.getEntity(TPmIncomeNodeEntity.class, tPmIncomeNode.getId());
        message = "??????????????????????????????";
        try {
            tPmIncomeNodeService.deleteAddAttach(tPmIncomeNode);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ??????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            for (String id : ids.split(",")) {
                TPmIncomeNodeEntity tPmIncomeNode = systemService.getEntity(TPmIncomeNodeEntity.class, id);
                tPmIncomeNodeService.deleteAddAttach(tPmIncomeNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        try {
            tPmIncomeNode.setAuditFlag(SrmipConstants.NO);
            tPmIncomeNode.setBalance(tPmIncomeNode.getIncomeAmount());
            tPmIncomeNodeService.save(tPmIncomeNode);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(tPmIncomeNode);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        TPmIncomeNodeEntity t = tPmIncomeNodeService.get(TPmIncomeNodeEntity.class, tPmIncomeNode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeNode, t);
            t.setBalance(t.getIncomeAmount());
            tPmIncomeNodeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(t);
        return j;
    }

    /**
     * ????????????????????????
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateAuditFlag")
    @ResponseBody
    public AjaxJson doUpdateAuditFlag(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "??????????????????????????????";
        TPmIncomeNodeEntity t = tPmIncomeNodeService.get(TPmIncomeNodeEntity.class, tPmIncomeNode.getId());
        if (SrmipConstants.YES.equals(t.getAuditFlag())) {
            //?????????????????????
            t.setAuditFlag(SrmipConstants.NO);
            t.setAuditUserid(null);
            t.setAuditUsername(null);
            t.setAuditTime(null);
        } else {
            //???????????????
            t.setAuditFlag(SrmipConstants.YES);
            t.setAuditUserid(ResourceUtil.getSessionUserName().getId());
            t.setAuditUsername(ResourceUtil.getSessionUserName().getRealName());
            t.setAuditTime(new Date());
        }
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmIncomeNode, t);
            tPmIncomeNodeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "??????????????????????????????";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeNode.getId())) {
            tPmIncomeNode = tPmIncomeNodeService.getEntity(TPmIncomeNodeEntity.class, tPmIncomeNode.getId());
        }
        req.setAttribute("tPmIncomeNodePage", tPmIncomeNode);
        return new ModelAndView("com/kingtake/project/m2income/tPmIncomeNode-add");
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmIncomeNode.getId())) {
            tPmIncomeNode = tPmIncomeNodeService.getEntity(TPmIncomeNodeEntity.class, tPmIncomeNode.getId());
            req.setAttribute("tPmIncomeNodePage", tPmIncomeNode);
        }
        return new ModelAndView("com/kingtake/project/m2income/tPmIncomeNode-update");
    }

    /**
     * ??????????????????
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/m2income/tPmIncomeNodeUpload");
    }

    /**
     * ??????excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeNodeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmIncomeNode,
                request.getParameterMap());
        List<TPmIncomeNodeEntity> tPmIncomeNodes = this.tPmIncomeNodeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(NormalExcelConstants.CLASS, TPmIncomeNodeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("????????????????????????", "?????????:"
                + ResourceUtil.getSessionUserName().getRealName(), "????????????"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmIncomeNodes);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * ??????excel ?????????
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmIncomeNodeEntity tPmIncomeNode, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "??????????????????");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel????????????"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmIncomeNodeEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// ????????????????????????
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TPmIncomeNodeEntity> listTPmIncomeNodeEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmIncomeNodeEntity.class, params);
                for (TPmIncomeNodeEntity tPmIncomeNode : listTPmIncomeNodeEntitys) {
                    tPmIncomeNodeService.save(tPmIncomeNode);
                }
                j.setMsg("?????????????????????");
            } catch (Exception e) {
                j.setMsg("?????????????????????");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    /**
     * ????????????????????????????????????
     * 
     * @return
     */
    @RequestMapping(params = "goProjectListForIncomeNode")
    public ModelAndView goProjectListForIncomeNode(TPmIncomeNodeEntity incomeNode, HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForIncomeNode");
    }

    /**
     * ?????????????????????????????? ????????????
     * 
     * @return
     */
    @RequestMapping(params = "tPmIncomeNodeListForDepartment")
    public ModelAndView tPmIncomeNodeListForDepartment(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2income/tPmIncomeNodeListForDepartment");
    }
}
