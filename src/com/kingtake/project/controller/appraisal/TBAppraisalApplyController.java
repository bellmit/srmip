package com.kingtake.project.controller.appraisal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSFilesEntity;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.kingtake.base.service.code.TBCodeTypeServiceI;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ReceiveBillConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.util.ConvertDicUtil;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyAttachedEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalApplyMemRelaEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalMemberEntity;
import com.kingtake.project.entity.appraisal.TBAppraisalReceiveLogsEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.reportmaterial.TBAppraisalReportMaterialEntity;
import com.kingtake.project.service.appraisal.TBAppraisalApplyServiceI;

/**
 * @Title: Controller
 * @Description: 鉴定申请表
 * @author onlineGenerator
 * @date 2015-09-09 09:43:44
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAppraisalApplyController")
public class TBAppraisalApplyController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBAppraisalApplyController.class);

    @Autowired
    private TBAppraisalApplyServiceI tBAppraisalApplyService;
    @Autowired
    private SystemService systemService;
    private String message;

    @Autowired
    private TBCodeTypeServiceI tBCodeTypeService;

    @Autowired
    private ActivitiService activitiService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @RequestMapping(params = "tBAppraisalApplyListForDepartment")
    public ModelAndView tBAppraisalApplyListForDepartment(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyList-receiveTab");

    }

    /**
     * 接收列表
     * 
     * @param request
     * @return
     */
    @RequestMapping(params = "applyReceiveListForDepartment")
    public ModelAndView applyReceiveListForDepartment(HttpServletRequest request) {
        String type = request.getParameter("type");
        if (StringUtils.isNotEmpty(type)) {
            request.setAttribute("type", type);
        }
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyListForDepartment");
    }

    /**
     * 列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisalApply")
    public ModelAndView tBAppraisalApply(HttpServletRequest request) {
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
            return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyList-receive");
        }
        return new ModelAndView("common/404.jsp");
    }

    @RequestMapping(params = "sendApply")
    public ModelAndView sendApply(HttpServletRequest req, TBAppraisalReceiveLogsEntity receive) {
        String applyId = req.getParameter("applyId");
        receive.setSendReceiveId(applyId);
        if (StringUtil.isNotEmpty(receive.getId())) {
            receive = systemService.get(TBAppraisalReceiveLogsEntity.class, receive.getId());
        }
        req.setAttribute("tBAppraisalReceiveLogsPage", receive);
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalReceiveLogs-update");
    }

    @RequestMapping(params = "goPropose")
    public ModelAndView goPropose(HttpServletRequest req) {
        String id = req.getParameter("id");
        String type = req.getParameter("type");
        if (StringUtils.isNotEmpty(id)) {
            if ("1".equals(type)) {
                TBAppraisalApplyEntity apply = this.systemService.get(TBAppraisalApplyEntity.class, id);
                req.setAttribute("apply", apply);
            } else if ("2".equals(type)) {
                TBAppraisalMeetingDepartEntity apply = this.systemService.get(TBAppraisalMeetingDepartEntity.class, id);
                req.setAttribute("apply", apply);
            } else if ("3".equals(type)) {
                TBAppraisalReportMaterialEntity apply = this.systemService.get(TBAppraisalReportMaterialEntity.class,
                        id);
                req.setAttribute("apply", apply);
            }
        }
        return new ModelAndView("com/kingtake/project/appraisal/proposePage");
    }

    @RequestMapping(params = "doPropose")
    @ResponseBody
    public AjaxJson doPropose(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            String id = req.getParameter("id");
            String msgText = req.getParameter("msgText");
            if (StringUtil.isNotEmpty(id)) {
                TBAppraisalApplyEntity apply = systemService.get(TBAppraisalApplyEntity.class, id);
                apply.setMsgText(msgText);
                apply.setAuditStatus(ApprovalConstant.APPLYSTATUS_UNSEND);
                systemService.updateEntitie(apply);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAudit")
    @ResponseBody
    public AjaxJson doAudit(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        String id = req.getParameter("id");
        try {
            if (StringUtil.isNotEmpty(id)) {
                TBAppraisalApplyEntity apply = systemService.get(TBAppraisalApplyEntity.class, id);
                apply.setAuditStatus(ApprovalConstant.APPLYSTATUS_AUDITED);
                systemService.updateEntitie(apply);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doSend")
    @ResponseBody
    public AjaxJson doSend(HttpServletRequest req, TBAppraisalReceiveLogsEntity receive) {
        AjaxJson j = new AjaxJson();
        message = "操作成功！";
        try {
            if (StringUtil.isEmpty(receive.getId())) {//发送
                receive.setOperateStatus(SrmipConstants.NO);
                systemService.save(receive);
                TBAppraisalApplyEntity apply = systemService.get(TBAppraisalApplyEntity.class,
                        receive.getSendReceiveId());
                apply.setBpmStatus(ApprovalConstant.APPRSTATUS_SEND);
                systemService.updateEntitie(apply);
            } else {
                TBAppraisalReceiveLogsEntity t = systemService.get(TBAppraisalReceiveLogsEntity.class, receive.getId());
                MyBeanUtils.copyBeanNotNull2Bean(receive, t);
                t.setOperateStatus(SrmipConstants.YES);
                t.setOperateTime(new Date());
                systemService.save(t);
                TBAppraisalApplyEntity apply = systemService.get(TBAppraisalApplyEntity.class, t.getSendReceiveId());
                if (ReceiveBillConstant.AUDIT_PASS.equals(t.getSuggestionCode())) {
                    apply.setBpmStatus(ApprovalConstant.APPRSTATUS_FINISH);//通过即为流程完成
                } else {
                    apply.setBpmStatus(ApprovalConstant.APPRSTATUS_REBUT);//驳回
                }
                systemService.updateEntitie(apply);
            }
        } catch (Exception e) {
            message = "操作失败！";
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
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
    public void datagrid(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String operateStatus = request.getParameter("operateStatus");
        TSUser user = ResourceUtil.getSessionUserName();//获取参数

        StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.ACHIEVEMENT_NAME, APPR.FINISH_UNIT, APPR.PROJECT_TYPE, APPR.BEGIN_TIME, APPR.END_TIME, \n");
        resultSql
                .append(" APPR.ARCHIVE_NUM, APPR.FINISH_CONTACT_ID, APPR.FINISH_CONTACT_NAME, APPR.FINISH_CONTACT_PHONE,\n");
        resultSql
                .append("APPR.APPRAISAL_CONTACT_NAME, APPR.APPRAISAL_CONTACT_PHONE, APPR.REGISTER_CODE, APPR.RESULT_TYPE, \n");
        resultSql.append("APPR.APPRAISAL_FORM, APPR.APPRAISAL_TIME, APPR.APPRAISAL_ADDRESS, APPR.AUDIT_STATUS,APPR.CONTACT_NAME,APPR.CONTACT_PHONE, \n");
        resultSql.append("APPR.PROJECT_NAME,APPR.TOTAL_AMOUNT, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_B_APPRAISAL_APPLY APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID \n" 
                + "AND RECEIVE.RECEIVE_USERID = ?  ";

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
            temp += " AND APPR.PROJECT_NAME LIKE '%" + projectname + "%'";
        }

        temp += "ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
        String[] params = new String[] { user.getId() };

        List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                dataGrid.getPage(), dataGrid.getRows(), params);
        Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

        dataGrid.setResults(result);
        dataGrid.setTotal(count.intValue());
        TagUtil.datagrid(response, dataGrid);

    }

    @RequestMapping(params = "datagridForDepart")
    public void datagridForDepart(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String type = request.getParameter("type");
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalApply);
        try {
            //自定义追加查询条件
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("checkUserid", user.getId());
            cq.eq("checkDepartid", user.getCurrentDepart().getId());
            if ("0".equals(type)) {
                cq.or(Restrictions.eq("auditStatus", "1"), Restrictions.eq("auditStatus", "2"));//查询出已发送和已审批通过的数据
            } else {
                cq.notEq("auditStatus", "1");
                cq.notEq("auditStatus", "2");//查询出其他状态的数据
            }
            cq.addOrder("createDate", SortDirection.desc);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBAppraisalApplyService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /*
     * @RequestMapping(params = "datagridForDepart") public void datagridForDepart(TBAppraisalApplyEntity
     * tBAppraisalApply, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) { TSUser user =
     * ResourceUtil.getSessionUserName(); StringBuffer sql = new StringBuffer();
     * sql.append("SELECT T.*,R.ID AS RID,A.ACHIEVEMENT_NAME "); StringBuffer cql = new StringBuffer();
     * cql.append("SELECT COUNT(0) "); StringBuffer temp = new StringBuffer();
     * temp.append("FROM T_B_APPRAISAL_APPLY T JOIN T_B_APPRAISAL_RECEIVE_LOGS R ");
     * temp.append("ON T.ID = R.SEND_RECEIVE_ID AND (R.OPERATE_STATUS ='" + SrmipConstants.NO + "'");
     * temp.append(" OR R.SUGGESTION_CODE='" + ReceiveBillConstant.AUDIT_PASS + "')");
     * temp.append(" AND R.RECEIVE_USERID='" + user.getId() + "' AND R.RECEIVE_DEPARTID = '" +
     * user.getCurrentDepart().getId() + "'"); temp.append(" LEFT JOIN T_B_APPRAISAL_PROJECT A ON A.ID = T.T_B_ID ");
     * String[] params = new String[] {}; List<Map<String, Object>> result =
     * systemService.findForJdbcParam(sql.append(temp.toString()).toString(), dataGrid.getPage(), dataGrid.getRows(),
     * params); Long count = systemService.getCountForJdbcParam(cql.append(temp).toString(), params);
     * dataGrid.setResults(result); dataGrid.setTotal(count.intValue()); TagUtil.datagrid(response, dataGrid); }
     */

    @RequestMapping(params = "getWorkFlowInfoById")
    @ResponseBody
    public AjaxJson getWorkFlowInfoById(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String applyId = req.getParameter("applyId");
        Map<String, Object> map = activitiService.getProcessInstance(applyId);
        j.setAttributes(map);
        return j;
    }

    /**
     * 删除鉴定申请表
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tBAppraisalApply = systemService.getEntity(TBAppraisalApplyEntity.class, tBAppraisalApply.getId());
        message = "鉴定申请表删除成功";
        try {
            tBAppraisalApplyService.deleteAddAttach(tBAppraisalApply);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定申请表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新鉴定申请表
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String memberStr = request.getParameter("memberStr");
        String sendIds = request.getParameter("sendIds");
        try {
            this.tBAppraisalApplyService.saveApplyAndMember(tBAppraisalApply, memberStr, sendIds);
            message = "鉴定申请表更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            message = "鉴定申请表更新失败";
            j.setSuccess(false);
        }
        j.setObj(JSONHelper.bean2json(tBAppraisalApply));
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "updateAttached")
    @ResponseBody
    public AjaxJson updateAttached(HttpServletRequest req, TBAppraisalApplyAttachedEntity attached) {
        AjaxJson j = this.tBAppraisalApplyService.saveAttached(attached);
        return j;
    }

    @RequestMapping(params = "updateFinishFlag")
    @ResponseBody
    public AjaxJson updateFinishFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TBAppraisalApplyEntity t = systemService.get(TBAppraisalApplyEntity.class, id);
            if (t != null) {
                if (ApprovalConstant.APPRSTATUS_FINISH.equals(t.getAuditStatus())) {
                    //操作：取消完成流程(流程状态为已完成才能进行该操作)
                    t.setFinishTime(null);
                    t.setFinishUserid(null);
                    t.setFinishUsername(null);
                    t.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);//只有状态是已发送的时候才有完成按钮
                    systemService.saveOrUpdate(t);
                    j.setSuccess(true);
                    j.setMsg("成功将审批流程状态设置为未完成");
                } else if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getAuditStatus())) {
                    //操作：完成流程操作(流程状态为已完成才能进行该操作)
                    CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
                    cq.eq("apprId", id);
                    cq.eq("operateStatus", SrmipConstants.NO);
                    cq.eq("validFlag", SrmipConstants.YES);
                    cq.add();
                    List<TPmApprReceiveLogsEntity> tPmContractReceiveLogs = systemService.getListByCriteriaQuery(cq,
                            false);
                    if (tPmContractReceiveLogs != null && tPmContractReceiveLogs.size() > 0) {
                        j.setSuccess(false);
                        j.setMsg("还有审批意见未处理，确定完成审批流程吗？");
                        j.setObj("1");//失败情况一
                    } else {
                        TSUser user = ResourceUtil.getSessionUserName();
                        //操作：完成流程
                        t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                        t.setFinishTime(new Date());
                        t.setFinishUserid(user.getId());
                        t.setFinishUsername(user.getRealName());
                        systemService.saveOrUpdate(t);
                        j.setSuccess(true);
                        j.setMsg("成功将审批流程状态设置为完成");
                    }
                } else {
                    //其他人操作改变了finishFlag属性，需要刷新重新操作
                    j.setSuccess(false);
                    j.setMsg("审批流程状态设置失败，请刷新列表再进行操作");
                    j.setObj("2");//失败情况二
                }
            } else {
                j.setSuccess(false);
                j.setMsg("审批流程状态设置失败");
            }
        } else {
            j.setSuccess(false);
            j.setMsg("审批流程状态设置失败");
        }
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
        AjaxJson j = new AjaxJson();

        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TBAppraisalApplyEntity t = systemService.get(TBAppraisalApplyEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getAuditStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                systemService.saveOrUpdate(t);
                j.setSuccess(true);
            } else {
                j.setSuccess(false);
                message = "审批流程状态设置失败，清刷新列表再进行操作";
            }
        } else {
            j.setSuccess(false);
            message = "审批流程状态设置失败";
        }

        return j;
    }

    /**
     * 鉴定申请表编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBAppraisalApply.getId())) {
            tBAppraisalApply = systemService.get(TBAppraisalApplyEntity.class, tBAppraisalApply.getId());
            req.setAttribute("projectId", tBAppraisalApply.getProjectId());
            CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyAttachedEntity.class);
            cq.eq("applyId", tBAppraisalApply.getId());
            cq.add();
            List<TBAppraisalApplyAttachedEntity> attachedList = systemService.getListByCriteriaQuery(cq, false);
            if (attachedList.size() > 0) {
                TBAppraisalApplyAttachedEntity tBAppraisalApplyAttached = attachedList.get(0);
                req.setAttribute("tBAppraisalApplyAttachedPage", tBAppraisalApplyAttached);
            }
        } else{
            if(StringUtils.isNotEmpty(tBAppraisalApply.getProjectId())){
                TSUser user = ResourceUtil.getSessionUserName();
                TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, tBAppraisalApply.getProjectId());
                tBAppraisalApply.setProjectName(project.getProjectName());
                tBAppraisalApply.setApprovalUnitName(user.getCurrentDepart().getDepartname());
                tBAppraisalApply.setProjectType(project.getProjectTypeStr());
                tBAppraisalApply.setBeginTime(project.getBeginDate());
                tBAppraisalApply.setEndTime(project.getEndDate());
                tBAppraisalApply.setTotalAmount(project.getAllFee()==null?BigDecimal.ZERO:project.getAllFee());
                tBAppraisalApply.setContactName(project.getContact());
                tBAppraisalApply.setContactPhone(project.getContactPhone());
            }
            ResourceBundle bundle = java.util.ResourceBundle.getBundle("appraisalConfig");
            String finishUnit = bundle.getString("finishUnit");
            String finishContactName = bundle.getString("finishContactName");
            String finishContactPhone = bundle.getString("finishContactPhone");
            tBAppraisalApply.setFinishUnit(finishUnit);
            List<TSUser> users = this.systemService.findByProperty(TSUser.class, "realName", finishContactName);
            if(users.size()>0){
                tBAppraisalApply.setFinishContactId( users.get(0).getId());
            }
            tBAppraisalApply.setFinishContactName(finishContactName);
            tBAppraisalApply.setFinishContactPhone(finishContactPhone);
        }
        //附件
        if(StringUtils.isEmpty(tBAppraisalApply.getAttachmentCode())){
            tBAppraisalApply.setAttachmentCode(UUIDGenerator.generate());
        }else{
            List<TSFilesEntity> certificates = systemService.getAttachmentByCode(tBAppraisalApply.getAttachmentCode(), "");
            tBAppraisalApply.setCertificates(certificates);
        }
        req.setAttribute("tBAppraisalApply", tBAppraisalApply);
        String opt = req.getParameter("opt");
        if(StringUtils.isNotEmpty(opt)){
            req.setAttribute("opt", opt);
        }
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApply");
    }

    @RequestMapping(params = "goApplyForDepart")
    public ModelAndView goApplyForDepart(HttpServletRequest req, TBAppraisalApplyEntity apply) {
        if (StringUtil.isNotEmpty(apply.getId())) {
            apply = systemService.get(TBAppraisalApplyEntity.class, apply.getId());
            List<TBAppraisalApplyAttachedEntity> attachedList = systemService.findByProperty(
                    TBAppraisalApplyAttachedEntity.class, "applyId", apply.getId());
            if (attachedList.size() > 0) {
                req.setAttribute("tBAppraisalApplyAttachedPage", attachedList.get(0));
            }
        }
        req.setAttribute("projectId", apply.getProjectId());
        req.setAttribute("tBAppraisalApply", apply);
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyForDepart");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalApply,
                request.getParameterMap());
        List<TBAppraisalApplyEntity> tBAppraisalApplys = this.tBAppraisalApplyService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "T_B_APPRAISAL_APPLY");
        modelMap.put(NormalExcelConstants.CLASS, TBAppraisalApplyEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("T_B_APPRAISAL_APPLY列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tBAppraisalApplys);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 根据申请获取成员
     * 
     * @param tBAppraisalMember
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "getMemberListByApply")
    @ResponseBody
    public JSONObject getMemberListByApply(TBAppraisalApplyMemRelaEntity memberRela, HttpServletRequest request,
            HttpServletResponse response) {
        JSONObject json = new JSONObject();
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyMemRelaEntity.class);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, memberRela, request.getParameterMap());
        cq.add();
        List<TBAppraisalApplyMemRelaEntity> memRelaList = this.systemService.getListByCriteriaQuery(cq, false);
        if (memRelaList.size() > 0) {
            List<String> memberList = new ArrayList<String>();
            for (TBAppraisalApplyMemRelaEntity entity : memRelaList) {
                memberList.add(entity.getMemberId());
            }
            CriteriaQuery memCq = new CriteriaQuery(TBAppraisalMemberEntity.class);
            if (memberList != null && memberList.size() > 0) {
                memCq.in("id", memberList.toArray());
            }
            memCq.add();
            List<TBAppraisalMemberEntity> resultList = this.systemService.getListByCriteriaQuery(memCq, false);
            json.put("rows", resultList);
            json.put("total", resultList.size());
        } else {
            json.put("rows", new ArrayList<TBAppraisalMemberEntity>());
            json.put("total", 0);
        }
        return json;
    }


    /**
     * 提交审批
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "submitAppraisalApply", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson submitAppraisalApply(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String operator = request.getParameter("operator");
        String userId = request.getParameter("userId");
        String departId = request.getParameter("departId");
        TBAppraisalApplyEntity entity = this.systemService.get(TBAppraisalApplyEntity.class, id);
        entity.setCheckUserid(userId);
        entity.setCheckUsername(operator);
        entity.setCheckDepartid(departId);
        entity.setCheckDate(new Date());
        AjaxJson j = this.tBAppraisalApplyService.submitApply(entity, userId);
        return j;
    }
    
    /**
     * 鉴定申请基本信息页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAppraisalApplyListInfo")
    public ModelAndView goAppraisalApplyListInfo(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if(StringUtils.isNotEmpty(projectId)){
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyListInfo");
    }
    
    /**
     * 鉴定申请过程管理页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAppraisalApplyListProcess")
    public ModelAndView goAppraisalApplyListProcess(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest req) {
        String projectId = req.getParameter("projectId");
        if(StringUtils.isNotEmpty(projectId)){
            req.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyListProcess");
    }
    
    /**
     * 鉴定申请列表列表请求数据
     * 
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */
    @RequestMapping(params = "datagridForApply")
    public void datagridForApply(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalApplyEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAppraisalApply,
                request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBAppraisalApplyService.getDataGridReturn(cq, true);
        List<TBAppraisalApplyEntity> applyList = dataGrid.getResults();
        for(TBAppraisalApplyEntity apply:applyList){
            TBAppraisalMeetingEntity meeting = this.systemService.findUniqueByProperty(TBAppraisalMeetingEntity.class, "applyId", apply.getId());
            if(meeting!=null){
                apply.setMeetingStatus(meeting.getMeetingStatus());
            }else{
                apply.setMeetingStatus("");
            }
            TBAppraisalReportMaterialEntity material = this.systemService.findUniqueByProperty(TBAppraisalReportMaterialEntity.class, "applyId", apply.getId());
            if(material!=null){
                apply.setMaterialStatus(material.getCheckFlag());
            }else{
                apply.setMaterialStatus("");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * 成果鉴定申请审批tab页
     * 
     * @return
     */
    @RequestMapping(params = "tBAppraisaApplyReceiveTab")
    public ModelAndView tBAppraisaApprovalReceiveTab(HttpServletRequest request) {
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApplyList-receiveTab");
    }
    
    /**
     * 机关审核鉴定申请
     * 
     * @return
     */
    @RequestMapping(params = "goAppraisaApplyReceive")
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
            return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApplyList-receive");
        }
        return new ModelAndView("common/404.jsp");
    }
    
    /**
     * 鉴定申请鉴定委员会成员页面
     * 
     * @return
     */
    @RequestMapping(params = "goAppraisalApplyMember")
    public ModelAndView goAppraisalApplyMember(TBAppraisalApplyEntity tBAppraisalApply, HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApplyMember");
    }
    
    /**
     * 导出word
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportWord")
    public String exportWord(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String id = request.getParameter("id");
        Map<String, Object> map = new HashMap<String, Object>();
        TBAppraisalApplyEntity appraisalApply = this.systemService.get(TBAppraisalApplyEntity.class, id);
        map.put("achievementName", appraisalApply.getAchievementName() == null ? "" : appraisalApply.getAchievementName());
        map.put("finishUnit", appraisalApply.getFinishUnit() == null ? "" : appraisalApply.getFinishUnit());
        Date beginTime = appraisalApply.getBeginTime();
        Date endTime = appraisalApply.getEndTime();
        String qzsj = "";
        if(beginTime!=null&&endTime!=null){
            String beginTimeStr = DateUtils.formatDate(beginTime, "yyyy.MM");
            String endTimeStr = DateUtils.formatDate(endTime, "yyyy.MM");
            qzsj = beginTimeStr+"-"+endTimeStr;
        }
        map.put("qzsj", qzsj);
        map.put("archiveNum", appraisalApply.getArchiveNum() == null ? "" : appraisalApply.getArchiveNum());
        map.put("finishContactName", appraisalApply.getFinishContactName() == null ? "" : appraisalApply.getFinishContactName());
        map.put("finishContactPhone", appraisalApply.getFinishContactPhone() == null ? "" : appraisalApply.getFinishContactPhone());
        map.put("appraisalContactName", appraisalApply.getAppraisalContactName() == null ? "" : appraisalApply.getAppraisalContactName());
        map.put("appraisalContactPhone", appraisalApply.getAppraisalContactPhone() == null ? "" : appraisalApply.getAppraisalContactPhone());
        map.put("basicNum", appraisalApply.getBasicNum() == null ? "" : appraisalApply.getBasicNum());
        String resultTypeStr = "";
        if(StringUtils.isNotEmpty(appraisalApply.getResultType())){
            resultTypeStr = ConvertDicUtil.getConvertDic("1", "CGLB", appraisalApply.getResultType());
        }
        map.put("resultTypeStr", resultTypeStr);
        String appraisalFormStr = "";
        if(StringUtils.isNotEmpty(appraisalApply.getResultType())){
            appraisalFormStr = ConvertDicUtil.getConvertDic("1", "JDXS", appraisalApply.getAppraisalForm());
        }
        map.put("appraisalFormStr", appraisalFormStr);
        String appraisalTimeStr = "";
        if(appraisalApply.getAppraisalTime()!=null){
            appraisalTimeStr = DateUtils.formatDate(appraisalApply.getAppraisalTime(), "yyyy年MM月");
        }
        map.put("appraisalTimeStr", appraisalTimeStr);
        map.put("appraisalAddress", appraisalApply.getAppraisalAddress()==null?"":appraisalApply.getAppraisalAddress());
        String sql = "SELECT M.MEMBER_NAME ,M.MEMBER_POSITION,M.MEMBER_PROFESSION,M.WORK_UNIT,M.MEMO FROM T_B_APPRAISAL_MEMBER M JOIN T_B_APPRAISA_APPLY_MEM_RELA R ON M.ID=R.MEMBER_ID WHERE R.T_B_ID=? ORDER BY M.CREATE_DATE ASC";
        List<Map<String,Object>> memberList = this.systemService.findForJdbc(sql, new Object[]{appraisalApply.getId()});
        List<TBAppraisalMemberEntity> wyhcys = new ArrayList<TBAppraisalMemberEntity>();
        List<Map<String,String>> wyhcysMap = new ArrayList<Map<String,String>>();
        for(Map<String,Object> tmpMap:memberList){
            TBAppraisalMemberEntity member = new TBAppraisalMemberEntity();
            Map<String,String> cyMap = new HashMap<String,String>();
            String memberName = "";
            if(tmpMap.get("MEMBER_NAME")!=null){
                memberName = (String) tmpMap.get("MEMBER_NAME");
            }
            cyMap.put("xm", memberName);
            member.setMemberName(memberName);
            String memberPositionStr = "";
            if(tmpMap.get("MEMBER_POSITION")!=null){
                memberPositionStr = ConvertDicUtil.getConvertDic("1", "ZHCH", (String)tmpMap.get("MEMBER_POSITION"));
            }
            member.setMemberPosition(memberPositionStr);
            cyMap.put("jszc", memberPositionStr);

            String memberProfessionStr = "";
            if(tmpMap.get("MEMBER_PROFESSION")!=null){
                memberProfessionStr = ConvertDicUtil.getConvertDic("1", "MAJOR", (String)tmpMap.get("MEMBER_PROFESSION"));
            }
            member.setMemberProfession(memberProfessionStr);
            cyMap.put("zy", memberProfessionStr);
            String workUnit = "";
            if(tmpMap.get("WORK_UNIT")!=null){
                workUnit = (String) tmpMap.get("WORK_UNIT");
            }
            member.setWorkUnit(workUnit);
            cyMap.put("gzdw", workUnit);
            String memo = "";
            if(tmpMap.get("MEMO")==null){
                memo = (String) tmpMap.get("MEMO");
            }
            member.setMemo(memo);
            cyMap.put("bz", memo);
            wyhcys.add(member);
            wyhcysMap.add(cyMap);
        }
        map.put("wyhcys", wyhcysMap);
                
        modelMap.put(TemplateWordConstants.FILE_NAME, "科技成果鉴定申请表_" + DateUtils.formatDate());
        modelMap.put(TemplateWordConstants.MAP_DATA, map);
        modelMap.put(TemplateWordConstants.URL, "export/template/jdsqTemplate.docx");
        
        return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;                   
    }
    
    @RequestMapping(params = "checkIsAppr")
    @ResponseBody
    public JSONObject checkIsAppr(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("isAppr", "0");
        String apprId = request.getParameter("apprId");
        List<Map<String, Object>> apprList = this.getAppr(apprId);//查询课题组长
        if (apprList.size() > 0) {
            Map<String, Object> map = apprList.get(0);
            if (map.get("APPR_ID") != null) {
                json.put("isAppr", "1");
                json.put("receiveId", map.get("ID"));
            }
        }
        return json;
    }

    /**
     * 查询课题组长审批
     * 
     * @param projectId
     * @return
     */
    private List<Map<String, Object>> getAppr(String apprId) {
        TSUser user = ResourceUtil.getSessionUserName();//获取参数
        StringBuffer resultSql = new StringBuffer();
        resultSql.append("SELECT APPR.ID AS APPR_ID, APPR.ACHIEVEMENT_NAME, APPR.FINISH_UNIT, APPR.PROJECT_TYPE, APPR.BEGIN_TIME, APPR.END_TIME, \n");
        resultSql
                .append(" APPR.ARCHIVE_NUM, APPR.FINISH_CONTACT_ID, APPR.FINISH_CONTACT_NAME, APPR.FINISH_CONTACT_PHONE,\n");
        resultSql
                .append("APPR.APPRAISAL_CONTACT_NAME, APPR.APPRAISAL_CONTACT_PHONE, APPR.REGISTER_CODE, APPR.RESULT_TYPE, \n");
        resultSql.append("APPR.APPRAISAL_FORM, APPR.APPRAISAL_TIME, APPR.APPRAISAL_ADDRESS, APPR.AUDIT_STATUS,APPR.CONTACT_NAME,APPR.CONTACT_PHONE, \n");
        resultSql.append("APPR.PROJECT_NAME,APPR.TOTAL_AMOUNT, \n");
        resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, \n");
        resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, \n");
        resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT \n");

        String temp = "FROM T_B_APPRAISAL_APPLY APPR, T_PM_APPR_SEND_LOGS SEND, "
                + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT \n"
                + "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID AND EXT.ID='0701' \n" 
                + "AND RECEIVE.RECEIVE_USERID = ?  ";
      //未处理：需要是有效的
        temp += "AND RECEIVE.OPERATE_STATUS =" + SrmipConstants.NO + "  AND RECEIVE.VALID_FLAG =" + SrmipConstants.YES;
        temp += " AND APPR.ID=?";
        String[] params = new String[] { user.getId(), apprId };
        List<Map<String, Object>> result = systemService.findForJdbc(resultSql.append(temp).toString(), params);
        return result;
    }
}
