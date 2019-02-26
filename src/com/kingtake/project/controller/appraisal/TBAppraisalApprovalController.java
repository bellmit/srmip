package com.kingtake.project.controller.appraisal;

import java.io.IOException;
import java.util.HashMap;
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
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.controller.sendReceive.PrimaryGenerater;
import com.kingtake.project.entity.appraisal.TBAppraisalApprovalEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appraisal.TBAppraisalApprovalServiceI;

/**
 * @Title: Controller
 * @Description: 鉴定申请审批
 * @author onlineGenerator
 * @date 2015-09-09 09:43:07
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisaApprovalController")
public class TBAppraisalApprovalController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBAppraisalApprovalController.class);

    @Autowired
    private TBAppraisalApprovalServiceI tBAppraisaApprovalService;
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
     * 成果鉴定审批tab页
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisaApprovalReceiveTab")
    public ModelAndView tBAppraisaApprovalReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApprovalList-receiveTab");
    }

    /**
     * T_B_APPRAISA_APPROVAL列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisaApproval")
    public ModelAndView tBAppraisaApproval(HttpServletRequest request) {
        //处理审批
        //0：未处理；1：已处理
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
            return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApprovalList-receive");
        }
        return new ModelAndView("common/404.jsp");
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
    public void datagrid(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String operateStatus = request.getParameter("operateStatus");
        TSUser user = ResourceUtil.getSessionUserName();//获取参数

        StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.T_B_ID, APPR.PROJECT_CHARACTER, APPR.APPRAISA_CONDITION, \n");
        resultSql
                .append("APPR.BEGIN_TIME, APPR.END_TIME, APPR.TOTAL_AMOUNT, APPR.APPRAISA_TIME, APPR.APPRAISA_ADDRESS,\n");
        resultSql.append("APPR.CONTACT_USERID, APPR.CONTACT_USERNAME, APPR.CONTACT_PHONE, APPR.AUDIT_STATUS, \n");
        resultSql.append("APPRAISAL.PROJECT_NAME, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_B_APPRAISA_APPROVAL APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT, \n" + "T_B_APPRAISAL_PROJECT APPRAISAL \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n" + "AND APPR.T_B_ID = APPRAISAL.ID \n"
                + "AND RECEIVE.RECEIVE_USERID = ? AND RECEIVE.RECEIVE_DEPARTID = ? ";

        if (SrmipConstants.YES.equals(operateStatus)) {
            //已处理
            temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.YES;
        } else if (SrmipConstants.NO.equals(operateStatus)) {
            //未处理：需要是有效的
            temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                    + SrmipConstants.YES;
        }

        String projectname = request.getParameter("project.name");
        if (StringUtil.isNotEmpty(projectname)) {
            temp += " AND APPRAISAL.PROJECT_NAME LIKE '%" + projectname + "%'";
        }

        temp += "ORDER BY SEND.OPERATE_DATE, RECEIVE.OPERATE_TIME";
        String[] params = new String[] { user.getId(), user.getCurrentDepart().getId() };

        List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                dataGrid.getPage(), dataGrid.getRows(), params);
        Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

        dataGrid.setResults(result);
        dataGrid.setTotal(count.intValue());
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridByApprId")
    public void datagridByApprId(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String apprId = request.getParameter("apprId");

        CriteriaQuery cq = new CriteriaQuery(TBAppraisalApprovalEntity.class, dataGrid);
        //查询条件组装器
        //        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisaApproval,
        //                request.getParameterMap());
        try {
            //自定义追加查询条件
            cq.eq("appraisalProject.id", apprId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBAppraisaApprovalService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * 删除
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBAppraisaApproval = systemService.getEntity(TBAppraisalApprovalEntity.class, tBAppraisaApproval.getId());
        message = "T_B_APPRAISA_APPROVAL删除成功";
        try {
            tBAppraisaApprovalService.delete(tBAppraisaApproval);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISA_APPROVAL删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "T_B_APPRAISA_APPROVAL删除成功";
        try {
            for (String id : ids.split(",")) {
                TBAppraisalApprovalEntity tBAppraisaApproval = systemService.getEntity(TBAppraisalApprovalEntity.class,
                        id);
                tBAppraisaApprovalService.delete(tBAppraisaApproval);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISA_APPROVAL删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "T_B_APPRAISA_APPROVAL添加成功";
        try {
            tBAppraisaApprovalService.save(tBAppraisaApproval);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "T_B_APPRAISA_APPROVAL添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddUpdate")
    @ResponseBody
    public AjaxJson doAddUpdate(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "鉴定申请审批新增/更新成功";
        try {
            String sendIds = request.getParameter("sendIds");
            tBAppraisaApprovalService.doAddOrUpdate(tBAppraisaApproval, sendIds);
            j.setObj(tBAppraisaApproval.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定申请审批新增/更新失败";
            throw new BusinessException(message, e);
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 表单状态进行判断
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getOperateStatus")
    @ResponseBody
    public AjaxJson getOperateStatus(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (tBAppraisaApproval.getAppraisalProject() != null
                && StringUtil.isNotEmpty(tBAppraisaApproval.getAppraisalProject().getId())) {
            List<TBAppraisalApprovalEntity> approvalList = this.tBAppraisaApprovalService.findByProperty(
                    TBAppraisalApprovalEntity.class, "appraisalProject.id", tBAppraisaApproval.getAppraisalProject()
                            .getId());
            Map<String, Object> attributes = new HashMap<String, Object>();
            String apprStatus = null;
            String apprId = null;
            if (approvalList != null && approvalList.size() > 0) {
                tBAppraisaApproval = approvalList.get(0);
                apprStatus = tBAppraisaApproval.getAuditStatus();
                apprId = tBAppraisaApproval.getId();
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) {
                    j.setMsg("该成果鉴定审批流程已完成，审批信息不能编辑且无法发送审批！");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                    j.setMsg("该成果鉴定审批已发送，审批信息不能编辑！");
                } else if (ApprovalConstant.APPRSTATUS_REBUT.equals(apprStatus)) {
                    j.setMsg("流程被驳回，请修改审批信息！");
                }
            } else {
                apprStatus = ApprovalConstant.APPRSTATUS_UNAPPR;
            }
            attributes.put("apprStatus", apprStatus);
            attributes.put("apprId", apprId);
            j.setAttributes(attributes);
        }

        return j;
    }

    @RequestMapping(params = "updateFinishFlag")
    @ResponseBody
    public AjaxJson updateFinishFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = this.tBAppraisaApprovalService.updateFinishFlag(id);
        return j;
    }

    /**
     * 编辑之前共同的判断方法：是否有已编辑的 有：基本信息只能查看
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateFinishFlag2")
    @ResponseBody
    public AjaxJson updateFinishFlag2(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = this.tBAppraisaApprovalService.updateFinishFlag2(id);
        return j;
    }

    /**
     * 新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisaApproval.getId())) {
            tBAppraisaApproval = tBAppraisaApprovalService.getEntity(TBAppraisalApprovalEntity.class,
                    tBAppraisaApproval.getId());
            req.setAttribute("tBAppraisaApprovalPage", tBAppraisaApproval);
        }
        return new ModelAndView("com/kingtake/test/tBAppraisaApproval-add");
    }

    @RequestMapping(params = "getApprApprovalNum")
    @ResponseBody
    public AjaxJson getApprApprovalNum(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        PrimaryGenerater gen = PrimaryGenerater.getInstance();
        String nextNum = gen.generaterNextApprApprovalNum();
        j.setObj(nextNum);
        return j;
    }

    /**
     * T_B_APPRAISA_APPROVAL编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goApproval")
    public ModelAndView goApproval(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest req) {
        TBAppraisalProjectEntity appraisalProject = null;
        if (StringUtil.isNotEmpty(tBAppraisaApproval.getId())) {
            tBAppraisaApproval = tBAppraisaApprovalService.get(TBAppraisalApprovalEntity.class,
                    tBAppraisaApproval.getId());
            appraisalProject = tBAppraisaApprovalService.get(TBAppraisalProjectEntity.class, tBAppraisaApproval
                    .getAppraisalProject().getId());
            req.setAttribute("projectId", appraisalProject.getProjectId());
        } else if (tBAppraisaApproval.getAppraisalProject() != null
                && StringUtil.isNotEmpty(tBAppraisaApproval.getAppraisalProject().getId())) {
            appraisalProject = tBAppraisaApprovalService.get(TBAppraisalProjectEntity.class,
                    tBAppraisaApproval.getAppraisalProject().getId());
            if (appraisalProject != null) {
                tBAppraisaApproval.setAppraisalProject(appraisalProject);
                String projectId = appraisalProject.getProjectId();
                req.setAttribute("projectId", appraisalProject.getProjectId());
                if (StringUtil.isNotEmpty(projectId)) {
                    TPmProjectEntity project = systemService.get(TPmProjectEntity.class, projectId);
                    tBAppraisaApproval.setTotalAmount(project.getAllFee());
                }
            }
        }
        List<Map<String, Object>> apprList = this.getAppr(appraisalProject.getProjectId());//查询课题组长
        if (apprList.size() > 0) {
            Map<String, Object> map = apprList.get(0);
            if (map.get("APPR_ID") != null) {
                req.setAttribute("isAppr", "1");
                req.setAttribute("receiveId", map.get("ID"));
            }
        }

        if (StringUtil.isEmpty(tBAppraisaApproval.getAppraisaCondition())) {
            tBAppraisaApproval.setAppraisaCondition("01");
        }

        req.setAttribute("tBAppraisaApprovalPage", tBAppraisaApproval);
        req.setAttribute("apprType", ApprovalConstant.APPR_TYPE_APPRAISAL);
        req.setAttribute("idFlag", req.getParameter("idFlag"));
        String send = req.getParameter("send");
        req.setAttribute("send", send);
        String role = req.getParameter("role");
        if ("depart".equals(role)) {
            return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApprovalForDepart");
        }
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApproval");
    }

    /**
     * 查询课题组长审批
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String projectId) {
        TSUser user = ResourceUtil.getSessionUserName();//获取参数

        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.T_B_ID, APPR.PROJECT_CHARACTER, APPR.APPRAISA_CONDITION, \n");
        resultSql
                .append("APPR.BEGIN_TIME, APPR.END_TIME, APPR.TOTAL_AMOUNT, APPR.APPRAISA_TIME, APPR.APPRAISA_ADDRESS,\n");
        resultSql.append("APPR.CONTACT_USERID, APPR.CONTACT_USERNAME, APPR.CONTACT_PHONE, APPR.AUDIT_STATUS, \n");
        resultSql.append("APPRAISAL.PROJECT_NAME, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_B_APPRAISA_APPROVAL APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT, \n" + "T_B_APPRAISAL_PROJECT APPRAISAL \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n" + "AND APPR.T_B_ID = APPRAISAL.ID \n"
                + "AND RECEIVE.RECEIVE_USERID = ? AND RECEIVE.RECEIVE_DEPARTID = ? ";
            //未处理：需要是有效的
            temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG ="
                    + SrmipConstants.YES;
        temp += " and APPRAISAL.PROJECT_ID=?";
        String[] params = new String[] { user.getId(), user.getCurrentDepart().getId(), projectId };
        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/test/tBAppraisaApprovalUpload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalApprovalEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisaApproval,
                request.getParameterMap());
        List<TBAppraisalApprovalEntity> tBAppraisaApprovals = this.tBAppraisaApprovalService.getListByCriteriaQuery(cq,
                false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "T_B_APPRAISA_APPROVAL");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalApprovalEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("T_B_APPRAISA_APPROVAL列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisaApprovals);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBAppraisalApprovalEntity tBAppraisaApproval, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "T_B_APPRAISA_APPROVAL");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TBAppraisalApprovalEntity.class);
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
                List<TBAppraisalApprovalEntity> listTBAppraisaApprovalEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TBAppraisalApprovalEntity.class, params);
                for (TBAppraisalApprovalEntity tBAppraisaApproval : listTBAppraisaApprovalEntitys) {
                    tBAppraisaApprovalService.save(tBAppraisaApproval);
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
