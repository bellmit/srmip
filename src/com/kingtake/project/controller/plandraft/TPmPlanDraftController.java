package com.kingtake.project.controller.plandraft;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.ant.util.DateUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.common.WorkFlowGlobals;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.TPmDeclareEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareArmyResearchEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareRepairEntity;
import com.kingtake.project.entity.declare.army.TBPmDeclareTechnologyEntity;
import com.kingtake.project.entity.declare.army.TPmDeclareBackEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDetailEntity;
import com.kingtake.project.entity.plandraft.TPmPlanDraftEntity;
import com.kingtake.project.entity.reportreq.TBPmReportReqEntity;
import com.kingtake.project.service.plandraft.TPmPlanDraftServiceI;

/**
 * @Title: Controller
 * @Description: 计划草案
 * @author onlineGenerator
 * @date 2016-03-30 10:46:58
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmPlanDraftController")
public class TPmPlanDraftController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmPlanDraftController.class);

    @Autowired
    private TPmPlanDraftServiceI tPmPlanDraftService;
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
     * 计划草案列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmPlanDraft")
    public ModelAndView tPmPlanDraft(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDraftList");
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
    public void datagrid(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmPlanDraftEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
                .installHql(cq, tPmPlanDraft, request.getParameterMap());
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tPmPlanDraftService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除计划草案
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmPlanDraft = systemService.getEntity(TPmPlanDraftEntity.class, tPmPlanDraft.getId());
        message = "计划草案删除成功";
        try {
            tPmPlanDraftService.deletePlan(tPmPlanDraft);
        } catch (Exception e) {
            e.printStackTrace();
            message = "计划草案删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除计划草案
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "计划草案删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmPlanDraftEntity tPmPlanDraft = systemService.getEntity(TPmPlanDraftEntity.class, id);
                tPmPlanDraftService.delete(tPmPlanDraft);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "计划草案删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加计划草案
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "计划草案添加成功";
        try {
            tPmPlanDraftService.save(tPmPlanDraft);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "计划草案添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新计划草案
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        try {
            this.tPmPlanDraftService.savePlanDraft(tPmPlanDraft, request);
            j.setMsg("保存计划草案成功！");
        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
            j.setMsg("保存计划草案失败！");
        }
        return j;
    }

    /**
     * 计划草案新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPlanDraft.getId())) {
            tPmPlanDraft = tPmPlanDraftService.getEntity(TPmPlanDraftEntity.class, tPmPlanDraft.getId());
            req.setAttribute("tPmPlanDraftPage", tPmPlanDraft);
        }
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDraft-add");
    }

    /**
     * 计划草案编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPlanDraft.getId())) {
            tPmPlanDraft = tPmPlanDraftService.getEntity(TPmPlanDraftEntity.class, tPmPlanDraft.getId());
            req.setAttribute("tPmPlanDraftPage", tPmPlanDraft);
        }
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDraft-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDraftUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmPlanDraftEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil
                .installHql(cq, tPmPlanDraft, request.getParameterMap());
        List<TPmPlanDraftEntity> tPmPlanDrafts = this.tPmPlanDraftService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "计划草案");
        modelMap.put(NormalExcelConstants.CLASS, TPmPlanDraftEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("计划草案列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmPlanDrafts);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
    
    /**
     * 导出项目基本信息excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportProjectXls")
    public void exportProjectXls(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request,
            HttpServletResponse response) {
        try {
        	String id = request.getParameter("id");
            Workbook wb = this.tPmPlanDraftService.exportProject(id);
            String fileName = "计划草案项目基本信息表.xls";
            fileName = POIPublicUtil
                    .processFileName(request, fileName);
            response.setHeader("Content-Disposition", "attachment; filename="
                    + fileName);
            ServletOutputStream out = response.getOutputStream();
            wb.write(out);
            out.flush();
        } catch (Exception e) {
            logger.error("导出模板失败", e);
            throw new BusinessException("导出模板失败", e);
        }
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "计划草案");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TPmPlanDraftEntity.class);
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
                List<TPmPlanDraftEntity> listTPmPlanDraftEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmPlanDraftEntity.class, params);
                for (TPmPlanDraftEntity tPmPlanDraft : listTPmPlanDraftEntitys) {
                    tPmPlanDraftService.save(tPmPlanDraft);
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
     * 跳转到项目列表
     * 
     * @return
     */
    @RequestMapping(params = "goProjectListForPlan")
    public ModelAndView goProjectListForPlan() {
        return new ModelAndView("com/kingtake/project/plandraft/projectListForPlan");
    }

    /**
     * 查询项目列表
     * 
     * @return
     */
    @RequestMapping(params = "datagridForProject")
    @ResponseBody
    public JSONObject datagridForProject(TPmProjectEntity tpmProject, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String planId = request.getParameter("planId");
        try {
            if (StringUtils.isNotEmpty(planId)) {
                List<TPmPlanDetailEntity> detailList = this.systemService.findByProperty(TPmPlanDetailEntity.class,
                        "planId", planId);
                for (TPmPlanDetailEntity detail : detailList) {
                    TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, detail.getProjectId());
                    generateJSON(array, project, detail);
                }
            } else {
                CriteriaQuery cq = new CriteriaQuery(TPmProjectEntity.class, dataGrid);
                //查询条件组装器
                org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tpmProject,
                        request.getParameterMap());
                cq.or(Restrictions.isNull("planFlag"), Restrictions.eq("planFlag", "0"));
                TSUser user = ResourceUtil.getSessionUserName();
                cq.eq("approvalUserid", user.getId());
                cq.add();
                List<TPmProjectEntity> approvalProjectList = this.systemService.getListByCriteriaQuery(cq, false);
                for (TPmProjectEntity project : approvalProjectList) {
                    TBProjectTypeEntity projectType = project.getProjectType();
                    if (projectType != null) {
                        boolean declareFlag = false;
                        String declareType = projectType.getDeclareType();
                        if ("1".equals(declareType)) {
                            List<TPmDeclareEntity> declareList = this.systemService.findByProperty(
                                    TPmDeclareEntity.class, "project.id", project.getId());
                            if (declareList.size() > 0) {
                                declareFlag = true;
                                TPmDeclareEntity declare = declareList.get(0);
                                if (WorkFlowGlobals.BPM_BUS_STATUS_3.equals(declare.getBpmStatus())) {
                                    TPmPlanDetailEntity detail = new TPmPlanDetailEntity();
                                    detail.setDeclareId(declare.getId());
                                    detail.setDeclareType("declare");
                                    generateJSON(array, project, detail);
                                }
                            }
                        } else if ("2".equals(declareType)) {
                            List<TBPmDeclareArmyResearchEntity> declareList = this.systemService.findByProperty(
                                    TBPmDeclareArmyResearchEntity.class, "tpId", project.getId());
                            if (declareList.size() > 0) {
                                declareFlag = true;
                                TBPmDeclareArmyResearchEntity declare = declareList.get(0);
                                if (WorkFlowGlobals.BPM_BUS_STATUS_3.equals(declare.getBpmStatus())) {
                                    TPmPlanDetailEntity detail = new TPmPlanDetailEntity();
                                    detail.setDeclareId(declare.getId());
                                    detail.setDeclareType("army");
                                    generateJSON(array, project, detail);
                                }
                            }
                        } else if ("3".equals(declareType)) {
                            List<TBPmDeclareRepairEntity> declareList = this.systemService.findByProperty(
                                    TBPmDeclareRepairEntity.class, "tpId", project.getId());
                            if (declareList.size() > 0) {
                                declareFlag = true;
                                TBPmDeclareRepairEntity declare = declareList.get(0);
                                if (WorkFlowGlobals.BPM_BUS_STATUS_3.equals(declare.getBpmStatus())) {
                                    TPmPlanDetailEntity detail = new TPmPlanDetailEntity();
                                    detail.setDeclareId(declare.getId());
                                    detail.setDeclareType("repair");
                                    generateJSON(array, project, detail);
                                }
                            }
                        } else if ("4".equals(declareType)) {
                            List<TBPmDeclareTechnologyEntity> declareList = this.systemService.findByProperty(
                                    TBPmDeclareTechnologyEntity.class, "tpId", project.getId());
                            if (declareList.size() > 0) {
                                declareFlag = true;
                                TBPmDeclareTechnologyEntity declare = declareList.get(0);
                                if (WorkFlowGlobals.BPM_BUS_STATUS_3.equals(declare.getBpmStatus())) {
                                    TPmPlanDetailEntity detail = new TPmPlanDetailEntity();
                                    detail.setDeclareId(declare.getId());
                                    detail.setDeclareType("tech");
                                    generateJSON(array, project, detail);
                                }
                            }
                        } else if ("5".equals(declareType)) {
                            List<TPmDeclareBackEntity> declareList = this.systemService.findByProperty(
                                    TPmDeclareBackEntity.class, "tpId", project.getId());
                            if (declareList.size() > 0) {
                                declareFlag = true;
                                TPmDeclareBackEntity declare = declareList.get(0);
                                if (WorkFlowGlobals.BPM_BUS_STATUS_3.equals(declare.getBpmStatus())) {
                                    TPmPlanDetailEntity detail = new TPmPlanDetailEntity();
                                    detail.setDeclareId(declare.getId());
                                    detail.setDeclareType("back");
                                    generateJSON(array, project, detail);
                                }
                            }
                        }
                        if (!declareFlag) {
                            List<TBPmReportReqEntity> reportList = this.systemService.findByProperty(
                                    TBPmReportReqEntity.class, "projectId", project.getId());
                            if (reportList.size() > 0) {
                                TBPmReportReqEntity declare = reportList.get(0);
                                if (WorkFlowGlobals.BPM_BUS_STATUS_3.equals(declare.getBpmStatus())) {
                                    TPmPlanDetailEntity detail = new TPmPlanDetailEntity();
                                    detail.setDeclareId(declare.getId());
                                    detail.setDeclareType("reportReq");
                                    generateJSON(array, project, detail);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        result.put("rows", array);
        result.put("total", array.size());
        return result;
    }

    /**
     * 组装json
     * 
     * @param array
     * @param project
     * @param type
     */
    private void generateJSON(JSONArray array, TPmProjectEntity project, TPmPlanDetailEntity detail) {
        JSONObject json = new JSONObject();
        json.put("id", project.getId());
        json.put("projectNo", project.getProjectNo());
        json.put("projectName", project.getProjectName());
        json.put("beginDate", DateUtils.format(project.getBeginDate(), "yyyy-MM-dd HH:mm:ss"));
        json.put("endDate", DateUtils.format(project.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
        json.put("projectManager", project.getProjectManager());
        json.put("projectType_projectTypeName", project.getProjectType().getProjectTypeName());
        json.put("accountingCode", project.getAccountingCode());
        json.put("devDepart_departname", project.getDevDepart().getDepartname());
        json.put("dutyDepart_departname", project.getDutyDepart().getDepartname());
        json.put("allFee", project.getAllFee());
        json.put("projectStatus", ProjectConstant.projectStatusMap.get(project.getProjectStatus()));
        json.put("planFlag", project.getPlanFlag() == null ? "0" : project.getPlanFlag());
        json.put("declareId", detail.getDeclareId());
        json.put("declareType", detail.getDeclareType());
        json.put("planDetailId", detail.getId());
        json.put("auditStatus", detail.getAuditStatus());
        
        json.put("sourceUnit", project.getSourceUnit());
        json.put("projectType_id", project.getProjectType().getId());
        json.put("projectType_projectTypeName", project.getProjectType().getProjectTypeName());
        json.put("feeType_fundsName", project.getFeeType().getFundsName());
        json.put("outsideNo", project.getOutsideNo());
        json.put("secretDegree", project.getSecretDegree());
        if(project.getParentPmProject() != null){
        	json.put("parentPmProject_projectNo", project.getParentPmProject().getProjectNo());
        }        
        json.put("projectSource", project.getProjectSource());
        json.put("approvalStatus", project.getApprovalStatus());
        array.add(json);
    }

    /**
     * 查询审批列表
     * 
     * @param planDraftEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "auditList")
    public void auditList(TPmPlanDraftEntity planDraftEntity, HttpServletRequest request, HttpServletResponse response,
            DataGrid dataGrid) {
        String datagridType = request.getParameter("datagridType");
        if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {// 处理审批

            String operateStatus = request.getParameter("operateStatus");
            TSUser user = ResourceUtil.getSessionUserName();// 获取当前登录人信息

            StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
            StringBuffer resultSql = new StringBuffer();
            resultSql
                    .append("SELECT APPR.ID AS APPR_ID, APPR.PLAN_NAME, APPR.PLAN_TIME, APPR.REMARK, APPR.PLAN_STATUS  auditStatus, SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, EXT.LABEL, EXT.HANDLER_TYPE, RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT ");
            String temp = " FROM  t_pm_plan_draft  APPR, T_PM_APPR_SEND_LOGS    SEND,"
                    + " T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY     EXT"
                    + " WHERE APPR.ID = SEND.APPR_ID and SEND.ID = RECEIVE.SEND_ID AND RECEIVE.SUGGESTION_TYPE = EXT.ID "
                    + "AND RECEIVE.RECEIVE_USERID = ? ";

            if (SrmipConstants.YES.equals(operateStatus)) {
                // 已处理
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
            } else if (SrmipConstants.NO.equals(operateStatus)) {
                // 未处理：需要是有效的
                temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                        + SrmipConstants.YES;
            }

            String planName = request.getParameter("planName");
            if (StringUtil.isNotEmpty(planName)) {
                temp += " AND APPR.PLAN_NAME LIKE '%" + planName + "%'";
            }

            temp += " ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
            String[] params = new String[] { user.getId() };

            List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                    dataGrid.getPage(), dataGrid.getRows(), params);
            Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

            dataGrid.setResults(result);
            dataGrid.setTotal(count.intValue());
        }

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 结题申请审核列表页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "applyAuditDepartment")
    public ModelAndView applyAuditDepartment(HttpServletRequest request) {
        // 处理审批
        // 0：未处理；1：已处理
        String type = request.getParameter("type");
        if (StringUtil.isNotEmpty(type)) {
            if ("0".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.NO);
            } else if ("1".equals(type)) {
                request.setAttribute("operateStatus", SrmipConstants.YES);
            }
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_RECEIVE);
            request.setAttribute("YES", SrmipConstants.YES);
            request.setAttribute("NO", SrmipConstants.NO);
        }
        return new ModelAndView("com/kingtake/project/plandraft/applyApprList-receive");
    }

    /**
     * 结题申请信息机关审批处理tab页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/plandraft/applyAppr-receiveTab");
    }

    /**
     * 针对每个项目审核通过与不通过
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(TPmPlanDetailEntity planDetail, HttpServletRequest req) {
        AjaxJson json = new AjaxJson();
        try {
            json = this.tPmPlanDraftService.doAudit(planDetail, req);
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("审批操作失败！");
        }
        return json;
    }

    @RequestMapping(params = "goPlanAudit")
    public ModelAndView goPlanAudit(TPmPlanDraftEntity tPmPlanDraft, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmPlanDraft.getId())) {
            tPmPlanDraft = tPmPlanDraftService.getEntity(TPmPlanDraftEntity.class, tPmPlanDraft.getId());
            req.setAttribute("tPmPlanDraftPage", tPmPlanDraft);
            TSUser user = ResourceUtil.getSessionUserName();
            if (user.getUserName().equals(tPmPlanDraft.getCreateBy())) {
                req.setAttribute("show", "1");
            }
        }
        return new ModelAndView("com/kingtake/project/plandraft/tPmPlanDraft-audit");
    }

    /**
     * 弹出填写修改意见的弹出框
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (StringUtils.isNotEmpty(id)) {
            TPmPlanDetailEntity apply = this.systemService.get(TPmPlanDetailEntity.class, id);
            req.setAttribute("msgText", apply.getMsgText());
        }
        return new ModelAndView("com/kingtake/project/plandraft/proposePage");
    }


    /**
     * 申报信息跳转
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "goDeclareInfo")
    public ModelAndView goDeclareInfo(HttpServletRequest req, HttpServletResponse resp) {
        String projectId = req.getParameter("projectId");
        String id = req.getParameter("id");
        String declareType = req.getParameter("declareType");
        if ("army".equals(declareType)) {
            return new ModelAndView("redirect:tBPmDeclareArmyResearchController.do?tBPmDeclareArmyResearch&projectId="
                    + projectId + "&read=1");
        } else if ("tech".equals(declareType)) {
            return new ModelAndView("redirect:tBPmDeclareTechnologyController.do?tBPmDeclareTechnology&projectId="
                    + projectId + "&read=1");
        } else if ("repair".equals(declareType)) {
            return new ModelAndView("redirect:tBPmDeclareRepairController.do?tBPmDeclareRepair&projectId=" + projectId
                    + "&read=1");
        } else if ("back".equals(declareType)) {
            return new ModelAndView("redirect:tPmDeclareBackController.do?tPmDeclareBack&projectId=" + projectId
                    + "&read=1");
        } else if ("declare".equals(declareType)) {
            TPmDeclareEntity declare = this.systemService.get(TPmDeclareEntity.class, id);
            req.setAttribute("projectId", projectId);
            req.setAttribute("declare", declare);
            req.setAttribute("read", '1');
            return new ModelAndView("com/kingtake/project/declare/tPmDeclare4Execute");
        } else if ("reportReq".equals(declareType)) {
            return new ModelAndView("redirect:tBPmReportReqController.do?goEdit&load=detail&projectId=" + id);
        }
        return new ModelAndView("common/404");
    }
}
