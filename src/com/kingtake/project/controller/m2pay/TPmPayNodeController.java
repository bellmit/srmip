package com.kingtake.project.controller.m2pay;

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
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.m2income.TPmIncomeNodeEntity;
import com.kingtake.project.entity.m2pay.TPmPayNodeEntity;
import com.kingtake.project.service.m2pay.TPmPayNodeServiceI;

/**
 * @Title: Controller
 * @Description: 支付节点管理
 * @author onlineGenerator
 * @date 2015-07-22 19:02:48
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPayNodeController")
public class TPmPayNodeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmPayNodeController.class);

    @Autowired
    private TPmPayNodeServiceI tPmPayNodeService;
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
     * 支付节点管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayNode")
    public ModelAndView tPmPayNode(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNodeList");
    }

    /**
     * 来款节点与合同节点关联页面 页面跳转
     * 
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayNodeAndContractNode")
    public ModelAndView tPmPayNodeAndContract(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        String contractNodeId = request.getParameter("contractNodeId");
        String checkFlag = request.getParameter("checkFlag");
        request.setAttribute("checkFlag", checkFlag);
        request.setAttribute("contractNodeId", contractNodeId);
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNodeAndContractNodeList");
    }

    /**
     * 查询：合同的支付节点
     * 
     * @return
     */
    @RequestMapping(params = "outcomeContracToPayNode")
    public ModelAndView outcomeContracToPayNode(HttpServletRequest request) {
        String contractId = request.getParameter("contractId");
        request.setAttribute("contractId", contractId);
        return new ModelAndView("com/kingtake/project/m2pay/outcomeContracToPayNodeList");
    }

    /**
     * easyui AJAX请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TPmPayNodeEntity tPmPayNode, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmPayNodeEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPayNode, request.getParameterMap());
        try {
            //自定义追加查询条件
            String contractId = request.getParameter("contractId");
            if (StringUtil.isNotEmpty(contractId)) {
                TPmOutcomeContractApprEntity contract = systemService.get(TPmOutcomeContractApprEntity.class,
                        contractId);
                StringBuffer outcomeNodeIds = new StringBuffer();
                if (contract != null) {
                    List<TPmContractNodeEntity> contractNodes = contract.getTPmContractNodes();
                    if (contractNodes != null && contractNodes.size() > 0) {
                        for (TPmContractNodeEntity contractNode : contractNodes) {
                            outcomeNodeIds.append(contractNode.getProjPayNodeId());
                            outcomeNodeIds.append(",");
                        }
                    }
                }
                //没有找到来款阶段
                if ("".equals(outcomeNodeIds.toString())) {
                    cq.eq("id", "*");
                } else {
                    String outcomeNodeIdsStr = outcomeNodeIds.toString();
                    cq.in("id", outcomeNodeIdsStr.substring(0, outcomeNodeIdsStr.length() - 1).split(","));
                    cq.addOrder("payTime", SortDirection.asc);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tPmPayNodeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除支付节点管理
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmPayNodeEntity tPmPayNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmPayNode = systemService.getEntity(TPmPayNodeEntity.class, tPmPayNode.getId());
        message = "支付节点管理删除成功";
        try {
            tPmPayNodeService.deleteAddAttach(tPmPayNode);
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付节点管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除支付节点管理
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "支付节点管理删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmPayNodeEntity tPmPayNode = systemService.getEntity(TPmPayNodeEntity.class, id);
                tPmPayNodeService.deleteAddAttach(tPmPayNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付节点管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加支付节点管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmPayNodeEntity tPmPayNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "支付节点管理添加成功";
        try {
            tPmPayNode.setAuditFlag(SrmipConstants.NO);
            tPmPayNode.setBalance(tPmPayNode.getPayAmount());
            tPmPayNodeService.save(tPmPayNode);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付节点管理添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(tPmPayNode);
        return j;
    }

    /**
     * 更新支付节点管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmPayNodeEntity tPmPayNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "支付节点管理更新成功";
        TPmPayNodeEntity t = tPmPayNodeService.get(TPmPayNodeEntity.class, tPmPayNode.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmPayNode, t);
            tPmPayNodeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付节点管理更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        j.setObj(t);
        return j;
    }

    /**
     * 更新来款节点管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateAuditFlag")
    @ResponseBody
    public AjaxJson doUpdateAuditFlag(TPmPayNodeEntity tPmPayNode, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "来款节点管理更新成功";
        TPmPayNodeEntity t = tPmPayNodeService.get(TPmPayNodeEntity.class, tPmPayNode.getId());
        if (SrmipConstants.YES.equals(t.getAuditFlag())) {
            //操作：取消审核
            t.setAuditFlag(SrmipConstants.NO);
            t.setAuditUserid(null);
            t.setAuditUsername(null);
            t.setAuditTime(null);
        } else {
            t.setAuditFlag(SrmipConstants.YES);
            t.setAuditUserid(ResourceUtil.getSessionUserName().getId());
            t.setAuditUsername(ResourceUtil.getSessionUserName().getRealName());
            t.setAuditTime(new Date());
        }
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tPmPayNode, t);
            tPmPayNodeService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "来款节点管理更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 支付节点管理新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmPayNodeEntity tPmPayNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPayNode.getId())) {
            tPmPayNode = tPmPayNodeService.getEntity(TPmPayNodeEntity.class, tPmPayNode.getId());
        }
        req.setAttribute("tPmPayNodePage", tPmPayNode);
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNode-add");
    }

    /**
     * 支付节点管理编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmPayNodeEntity tPmPayNode, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPayNode.getId())) {
            tPmPayNode = tPmPayNodeService.getEntity(TPmPayNodeEntity.class, tPmPayNode.getId());
            req.setAttribute("tPmPayNodePage", tPmPayNode);
        }
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNode-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNodeUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmPayNodeEntity tPmPayNode, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmPayNodeEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPayNode, request.getParameterMap());
        List<TPmPayNodeEntity> tPmPayNodes = this.tPmPayNodeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "支付节点管理");
        modelMap.put(NormalExcelConstants.CLASS, TPmPayNodeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("支付节点管理列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmPayNodes);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmPayNodeEntity tPmPayNode, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "支付节点管理");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmPayNodeEntity.class);
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
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<TPmPayNodeEntity> listTPmPayNodeEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TPmPayNodeEntity.class, params);
                for (TPmPayNodeEntity tPmPayNode : listTPmPayNodeEntitys) {
                    tPmPayNodeService.save(tPmPayNode);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
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
     * 跳转到支付节点的项目列表
     * 
     * @return
     */
    @RequestMapping(params = "goProjectListForPayNode")
    public ModelAndView goProjectListForPayNode(TPmIncomeNodeEntity incomeNode, HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/manage/tPmProjectListForPayNode");
    }

    /**
     * 支付节点管理列表机关 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmPayNodeListForDepartment")
    public ModelAndView tPmPayNodeListForDepartment(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/m2pay/tPmPayNodeListForDepartment");
    }
}
