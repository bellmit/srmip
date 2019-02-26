package com.kingtake.project.controller.payapply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
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

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.approutcomecontract.TPmOutcomeContractApprEntity;
import com.kingtake.project.entity.contractnode.TPmContractNodeEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.payapply.TPmPayApplyEntity;
import com.kingtake.project.service.payapply.TPmPayApplyServiceI;

/**
 * @Title: Controller
 * @Description: 支付申请
 * @author onlineGenerator
 * @date 2016-03-03 15:44:30
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPayApplyController")
public class TPmPayApplyController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmPayApplyController.class);

    @Autowired
    private TPmPayApplyServiceI tPmPayApplyService;
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
     * 支付申请列表界面跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "payApplyList")
    public ModelAndView payApplyList(HttpServletRequest request) {
        //发起审批
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            request.setAttribute("projectId", projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
        }
        return new ModelAndView("com/kingtake/project/payapply/tPmPayApplyList");
    }

    /**
     * 支付申请数据查询
     * 
     * @param tPmContractNodeCheck
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "payApplyDatagrid")
    public void outcomeContractNodeDatagrid(TPmPayApplyEntity payApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            String projectId = request.getParameter("projectId");
            List<TPmOutcomeContractApprEntity> contractApprList = this.systemService.findByProperty(
                    TPmOutcomeContractApprEntity.class, "project.id", projectId);
            if (contractApprList.size() > 0) {
                List<String> nodeIdList = new ArrayList<String>();
                for (TPmOutcomeContractApprEntity contractAppr : contractApprList) {
                    List<TPmContractNodeEntity> contractNodeList = this.systemService.findByProperty(
                            TPmContractNodeEntity.class, "inOutContractid", contractAppr.getId());
                    for (TPmContractNodeEntity node : contractNodeList) {
                        nodeIdList.add(node.getId());
                    }
                }
                if (nodeIdList.size() > 0) {
                    CriteriaQuery cq = new CriteriaQuery(TPmPayApplyEntity.class, dataGrid);
                    org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, payApply,
                            request.getParameterMap());
                    cq.in("contractNodeId", nodeIdList.toArray());
                    cq.add();
                    cq.addOrder("createDate", SortDirection.desc);
                    this.tPmPayApplyService.getDataGridReturn(cq, true);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除支付申请
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmPayApplyEntity tPmPayApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmPayApply = systemService.getEntity(TPmPayApplyEntity.class, tPmPayApply.getId());
        message = "支付申请删除成功";
        try {
            tPmPayApplyService.delete(tPmPayApply);
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付申请删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除支付申请
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "支付申请删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmPayApplyEntity tPmPayApply = systemService.getEntity(TPmPayApplyEntity.class, id);
                tPmPayApplyService.delete(tPmPayApply);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付申请删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新支付申请
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TPmPayApplyEntity tPmPayApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "支付申请新增/更新成功";
        try {
            if (StringUtils.isEmpty(tPmPayApply.getId())) {
                TPmContractNodeEntity node = this.systemService.get(TPmContractNodeEntity.class,
                        tPmPayApply.getContractNodeId());
                if (node != null) {
                    tPmPayApply.setContractNodeName(node.getContractNodeName());
                }
                tPmPayApply.setOperateStatus(ApprovalConstant.APPRSTATUS_UNSEND);
                tPmPayApplyService.save(tPmPayApply);
            } else {
                TPmPayApplyEntity t = tPmPayApplyService.get(TPmPayApplyEntity.class, tPmPayApply.getId());
                MyBeanUtils.copyBeanNotNull2Bean(tPmPayApply, t);
                tPmPayApplyService.saveOrUpdate(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "支付申请新增/更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 支付申请编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddUpdate")
    public ModelAndView goAddUpdate(TPmPayApplyEntity tPmPayApply, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPayApply.getId())) {
            tPmPayApply = tPmPayApplyService.getEntity(TPmPayApplyEntity.class, tPmPayApply.getId());
            req.setAttribute("tPmPayApplyPage", tPmPayApply);
        }else{
            TPmContractNodeEntity contractNode = this.systemService.get(TPmContractNodeEntity.class, tPmPayApply.getContractNodeId());
            TPmOutcomeContractApprEntity contractAppr = this.systemService.get(TPmOutcomeContractApprEntity.class, contractNode.getInOutContractid());
            TPmProjectEntity project = contractAppr.getProject();
            tPmPayApply.setContractName(contractAppr.getContractName());
            tPmPayApply.setContractCode(contractAppr.getContractCode());
            tPmPayApply.setAccountingCode(project.getAccountingCode());
            tPmPayApply.setContractStartTime(contractAppr.getStartTime());
            tPmPayApply.setContractEndTime(contractAppr.getEndTime());
            tPmPayApply.setProjectManagerName(project.getProjectManager());
            if (project.getDevDepart() != null) {
                tPmPayApply.setDevdepartName(project.getDevDepart().getDepartname());
            }
            if (contractAppr.getTpmContractBasics() != null && contractAppr.getTpmContractBasics().size() > 0) {
                tPmPayApply.setContractUnitnameb(contractAppr.getTpmContractBasics().get(0).getUnitNameB());
            }
            tPmPayApply.setProjectCode(project.getProjectNo());
            tPmPayApply.setContractTotalAmount(contractAppr.getTotalFunds());
            tPmPayApply.setProjectName(project.getProjectName());
            req.setAttribute("tPmPayApplyPage", tPmPayApply);
        }
        return new ModelAndView("com/kingtake/project/payapply/tPmPayApply-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/payapply/tPmPayApplyUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmPayApplyEntity tPmPayApply, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmPayApplyEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmPayApply, request.getParameterMap());
        List<TPmPayApplyEntity> tPmPayApplys = this.tPmPayApplyService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "支付申请");
        modelMap.put(NormalExcelConstants.CLASS, TPmPayApplyEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("支付申请列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmPayApplys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmPayApplyEntity tPmPayApply, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "支付申请");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmPayApplyEntity.class);
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
                List<TPmPayApplyEntity> listTPmPayApplyEntitys = ExcelImportUtil.importExcelByIs(file.getInputStream(),
                        TPmPayApplyEntity.class, params);
                for (TPmPayApplyEntity tPmPayApply : listTPmPayApplyEntitys) {
                    tPmPayApplyService.save(tPmPayApply);
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
}
