package com.kingtake.project.controller.apprlog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.kingtake.base.entity.xmlb.TBXmlbEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.office.service.message.TOMessageServiceI;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.incomeplan.TPmFpje;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceContext;
import com.kingtake.project.service.appr.ApprServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;

/**
 * @Title: Controller
 * @Description: 审批记录
 * @author onlineGenerator
 * @date 2015-08-31 16:58:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmApprLogsController")
public class TPmApprLogsController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmApprLogsController.class);

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TOMessageServiceI tOMessageService;
    @Autowired
    private ApprServiceI apprService;
    private String message;
    
    @Autowired
    private ApprFlowServiceContext apprFlowServiceContext;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 审批记录和审批主表信息tab页面
     * 
     * @return
     */
    @RequestMapping(params = "goApprTab")
    public ModelAndView goApprTab(HttpServletRequest req) {
        String apprId = req.getParameter("apprId");
        req.setAttribute("apprId", apprId);
        String idFlag = req.getParameter("idFlag");
        boolean send = "false".equals(req.getParameter("send")) ? false : true;
        req.setAttribute("send", send);

        String apprType = req.getParameter("apprType");
        req.setAttribute("apprType", apprType);

        if (ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
        	String ids[] = apprId.split(",");
        	String id1 = StringUtils.isNotEmpty(ids[0]) ? ids[0] : "";
        	String id2 = ids.length>1 && StringUtils.isNotEmpty(ids[1]) ? ids[1] : "";
        	String id = StringUtils.isNotEmpty(id1) ? id1 : StringUtils.isNotEmpty(id2) ? id2 : "";
        	if(StringUtils.isNotEmpty(id)) {
        		TPmProjectFundsApprEntity appr = systemService.get(TPmProjectFundsApprEntity.class, id);
        		if(appr != null){
                    req.setAttribute("tpId", appr.getTpId());
                    req.setAttribute("changeFlag", appr.getChangeFlag());
                }
        	}
            req.setAttribute("planContractFlag", req.getParameter("planContractFlag"));
            req.setAttribute("PROJECT_CONTRACT", ProjectConstant.PROJECT_CONTRACT);
            req.setAttribute("PROJECT_PLAN", ProjectConstant.PROJECT_PLAN);
            req.setAttribute("sptype", "ys");
            req.setAttribute("apprId", apprId);
            req.setAttribute("id1", id1);
            req.setAttribute("id2", id2);
           
            return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsAppr-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_CHECK.equals(apprType)) {
        	req.setAttribute("sptype", "jzys");
            return new ModelAndView("com/kingtake/project/apprcontractcheck/tPmContractCheck-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_INCOME.equals(apprType)) {
        	req.setAttribute("sptype", "jz");
            return new ModelAndView("com/kingtake/project/apprincomecontract/tPmIncomeContractAppr-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_OUTCOME.equals(apprType)) {
            String contractType = req.getParameter("contractType");
            req.setAttribute("contractType", contractType);
            req.setAttribute("sptype", "cz");
            return new ModelAndView("com/kingtake/project/approutcomecontract/tPmOutcomeContractAppr-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_NODECHECK.equals(apprType)) {
        	req.setAttribute("sptype", "czjd");
            return new ModelAndView("com/kingtake/project/contractnodecheck/tPmContractNodeCheck-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_APPRAISAL.equals(apprType)) {
            req.setAttribute("idFlag", "false".equals(req.getParameter("idFlag")) ? false : true);
            req.setAttribute("appraisalProjectId", req.getParameter("appraisalProjectId"));
            String role = req.getParameter("role");
            if ("depart".equals(role)) {
                return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApproval-apprTabForDepart");
            }
            return new ModelAndView("com/kingtake/project/appraisal/tBAppraisaApproval-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY.equals(apprType)) {
            req.setAttribute("idFlag", "false".equals(req.getParameter("idFlag")) ? false : true);
            req.setAttribute("appraisalProjectId", req.getParameter("appraisalProjectId"));
            req.setAttribute("sptype", "jd");
            return new ModelAndView("com/kingtake/project/appraisal/tBAppraisalApply-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_RESULT_REWARD.equals(apprType)) {
        	req.setAttribute("sptype", "jl");
            return new ModelAndView("com/kingtake/project/resultreward/tBResultReward-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_TASK.equals(apprType)) {
        	req.setAttribute("sptype", "rws");
            return new ModelAndView("com/kingtake/project/task/task-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_PAY_APPLY.equals(apprType)) {
            return new ModelAndView("com/kingtake/project/contractnodecheck/payApply-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_INCOME_CONTRACT_NODECHECK.equals(apprType)) {
            return new ModelAndView("com/kingtake/project/contractnodecheck/incomeNodeCheck-apprTab");
        }else if(ApprovalConstant.APPR_TYPE_TASK_NODECHECK.equals(apprType)){
            return new ModelAndView("com/kingtake/project/task/taskNode-apprTab");
        }else if (ApprovalConstant.APPR_TYPE_FINISH_APPLY.equals(apprType)) {
        	req.setAttribute("sptype", "jt");
        	return new ModelAndView("com/kingtake/project/finish/tPmFinishApply-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_PLAN_DRAFT.equals(apprType)) {
            return new ModelAndView("com/kingtake/project/plandraft/planDraftApply-apprTab");
        }else if (ApprovalConstant.APPR_TYPE_SEAL_USE.equals(apprType)) {
          return new ModelAndView("com/kingtake/office/seal/tOSealUse-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_TRAVEL.equals(apprType)) {
            return new ModelAndView("com/kingtake/office/travel/travel-apprTab");
        } else if (ApprovalConstant.APPR_TYPE_ZLSQ.equals(apprType)) {
            return new ModelAndView("com/kingtake/zscq/zlsq/zlsq-apprTab");
        }else if (ApprovalConstant.APPR_TYPE_CGJH.equals(apprType)) {
        	req.setAttribute("sptype", "cg");
            return new ModelAndView("com/kingtake/office/purchaseplanmain/purchase-apprTab");
        }else if (ApprovalConstant.APPR_TYPE_SBS.equals(apprType)) {
        	req.setAttribute("sptype", "sbs");
            return new ModelAndView("com/kingtake/project/declare/tPmDeclare-sbsTab");
        }else if (ApprovalConstant.APPR_TYPE_XM.equals(apprType)) {
        	req.setAttribute("sptype", "xm");
        	String bmlx = req.getParameter("bmlx");
        	if(bmlx!=null){
        		req.setAttribute("bmlx", bmlx);
        	}
            return new ModelAndView("com/kingtake/project/declare/tPmDeclare-xmTab");
        }

        return new ModelAndView("common/404.jsp");
    }

    /**
     * 审批主表信息：href转到iframe
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goApprInfo")
    public ModelAndView goApprInfo(HttpServletRequest req) {
        String apprId = req.getParameter("apprId");
        String apprType = req.getParameter("apprType");
        String type = req.getParameter("type");

        String url = null;
        if (ApprovalConstant.APPR_TYPE_FUNDS.equals(apprType)) {
            if ("1".equals(type)) {
                url = "tPmProjectFundsApprController.do?goAddOrUpdate&id=" + apprId + "&edit=false";
            } else if ("2".equals(type)) {
                url = "tPmContractFundsController.do?tPmContractFunds&tpId=" + apprId + "&edit=false";
            } else if ("3".equals(type)) {
                url = "tPmFundsBudgetAddendumController.do?tPmFundsBudgetAddendum&tpId=" + apprId + "&edit=false";
            }
        } else if (ApprovalConstant.APPR_TYPE_OUTCOME.equals(apprType)) {
            url = "tPmContractPriceCoverController.do?tPmContractPriceCover&contractType="
                    + req.getParameter("contractType") + "&contractId=" + apprId ;
        }

        req.setAttribute("url", url);
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprInfo");
    }

    /**
     * 审批接受记录列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmApprLogsTable")
    public ModelAndView tPmApprLogsTable(HttpServletRequest request) {
        String apprId = request.getParameter("apprId");
        String apprType = request.getParameter("apprType");
        String sptype = request.getParameter("sptype");
        request.setAttribute("sptype", sptype);
        String bmlx = request.getParameter("bmlx");
        if(bmlx!=null){
        	if(apprType.equals("20") && bmlx.equals("0")){
        		apprType="21";
        	}else if(apprType.equals("20") && (bmlx.equals("1") || bmlx.equals("2"))){
        		apprType="22";
        	}else if(apprType.equals("20") && (bmlx.equals("3") || bmlx.equals("4") || bmlx.equals("5") || bmlx.equals("6"))){
        		apprType="23";
        	}
        }
        List<Map<String, Object>> apprLogs = apprService.getApprLogs(apprId, apprType);
        request.setAttribute("apprTypes", apprLogs);
        request.setAttribute("apprId", apprId);

        String send = request.getParameter("send");
        if ("".equals(send)) {
            request.setAttribute("send", true);
        } else {
            request.setAttribute("send", send);
        }

        String idFlag = request.getParameter("idFlag");
        if ("false".equals(idFlag)) {
            request.setAttribute("idFlag", false);
        } else {
            request.setAttribute("idFlag", true);
        }
        int count = tPmApprLogsService.getRebutCount(apprId);
        if(count>0){
            request.setAttribute("rebutCount", count);
        }
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogsTable");
        /*
         * String roleType = ApprovalConstant.APPR_ROLE_OTHER; String datagridType = req.getParameter("datagridType");
         * if(ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)){ String creatBy = req.getParameter("creatBy");
         * TSUser user = ResourceUtil.getSessionUserName(); if(user.getUserName().equals(creatBy)){ roleType =
         * ApprovalConstant.APPR_ROLE_FAQI; } }else if(ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)){
         * String handlerType = req.getParameter("handlerType");
         * if(ApprovalConstant.HANDLER_TYPE_JIGUAN.equals(handlerType)){ roleType = ApprovalConstant.APPR_ROLE_JIGUAN;
         * }else if(ApprovalConstant.HANDLER_TYPE_YANXI.equals(handlerType)){ roleType =
         * ApprovalConstant.APPR_ROLE_YUANXI; }
         * 
         * } List<Map<String, Object>> apprLogs = apprService.getApprLogsAndSendButtons( apprId, apprType, roleType);
         */
        /*
         * request.setAttribute("HANDLER_TYPE_HANDLER", ApprovalConstant.HANDLER_TYPE_HANDLER);
         * request.setAttribute("HANDLER_TYPE_JIGUAN", ApprovalConstant.HANDLER_TYPE_JIGUAN);
         * request.setAttribute("HANDLER_TYPE_YANXI", ApprovalConstant.HANDLER_TYPE_YANXI);
         */
    }

    /**
     * 审批接受记录列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmApprLogs")
    public ModelAndView tPmApprLogs(HttpServletRequest request) {
        String apprId = request.getParameter("apprId");
        if (StringUtil.isNotEmpty(apprId)) {
            request.setAttribute("apprId", apprId);
        } else {
            request.setAttribute("apprId", "index");
        }

        String send = request.getParameter("send");
        if ("".equals(send)) {
            request.setAttribute("send", true);
        } else {
            request.setAttribute("send", send);
        }

        String apprType = request.getParameter("apprType");
        request.setAttribute("apprType", apprType);
        TSUser user = ResourceUtil.getSessionUserName();
        request.setAttribute("userId", user.getId());
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogsList");
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
    public void datagrid(TPmApprReceiveLogsEntity tPmApprReceiveLogs, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TPmApprReceiveLogsEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmApprReceiveLogs,
                request.getParameterMap());
        try {
            //自定义追加查询条件
            //无效已处理
            cq.eq("validFlag", SrmipConstants.NO);
            cq.eq("operateStatus", SrmipConstants.YES);
            //有效
            cq.eq("validFlag", SrmipConstants.YES);
            cq.add(cq.or(cq.and(cq, 0, 1), cq, 2));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        this.tPmApprLogsService.getDataGridReturn(cq, true);
        String apprType = request.getParameter("apprType");
        String apprTypeSql = "SELECT ID, LABEL, HANDLER_TYPE, FIELD_LABEL FROM T_PM_FORM_CATEGORY "
                + "WHERE SUBSTR(ID, 0, 2) = ? ORDER BY ID";
        List<Map<String,Object>> apprNodeMap = this.systemService.findForJdbc(apprTypeSql,apprType);
        Map<String,String> suggestionTypeMap = new HashMap<String,String>();
        for(Map<String,Object> map:apprNodeMap){
            suggestionTypeMap.put((String)map.get("ID"), (String)map.get("LABEL"));
        }
        suggestionTypeMap.put("0000","发起人");
        List<Object> results = dataGrid.getResults();
        for(Object obj:results){
            TPmApprReceiveLogsEntity recLog = (TPmApprReceiveLogsEntity) obj;
            String suggestionTypeName = suggestionTypeMap.get(recLog.getSuggestionType());
            recLog.setSuggestionTypeName(suggestionTypeName);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 添加审批记录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TPmApprSendLogsEntity tPmApprSendLogs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成功发送审批";
        try {
            String operateIp = IpUtil.getIpAddr(request);
            tPmApprSendLogs.setOperateIp(operateIp);

            String receiveUseIds = request.getParameter("receiveUseIds");
            String receiveUseNames = request.getParameter("receiveUseNames");
            String receiveUseDepartIds = request.getParameter("receiveUseDepartIds");
            String receiveUseDepartNames = request.getParameter("receiveUseDepartNames");
            String rebutFlag = request.getParameter("rebutFlag");
            String opt = request.getParameter("opt");

            Map<String, Object> map = tPmApprLogsService.doAddLogs(tPmApprSendLogs, receiveUseIds, receiveUseNames,
                    receiveUseDepartIds, receiveUseDepartNames, rebutFlag);
            if(StringUtils.isNotEmpty(opt)){
               map.put("opt",opt);
            }
            j.setAttributes(map);

        } catch (Exception e) {
            e.printStackTrace();
            message = "发送审批失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新审批接受记录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TPmApprReceiveLogsEntity tPmApprReceiveLogs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成功处理审批";
        TPmApprReceiveLogsEntity t = tPmApprLogsService.get(TPmApprReceiveLogsEntity.class, tPmApprReceiveLogs.getId());
        try {
            //保存审批信息
            t.setOperateStatus(SrmipConstants.YES);
            t.setOperateTime(new Date());
            MyBeanUtils.copyBeanNotNull2Bean(tPmApprReceiveLogs, t);

            String rebutValidFlag = request.getParameter("rebutValidFlag");
            Map<String, Object> map = tPmApprLogsService.doUpdateLog(t, rebutValidFlag);
            j.setAttributes(map);

        } catch (Exception e) {
            e.printStackTrace();
            message = "处理审批失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新审批接受记录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doUpdateValid")
    @ResponseBody
    public AjaxJson doUpdateValid(TPmApprReceiveLogsEntity tPmApprReceiveLogs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        TPmApprReceiveLogsEntity t = tPmApprLogsService.get(TPmApprReceiveLogsEntity.class, tPmApprReceiveLogs.getId());
        try {
            CriteriaQuery cq = new CriteriaQuery(TPmApprSendLogsEntity.class);
            cq.eq("apprId", t.getApprId());
            cq.eq("operateUserid", t.getReceiveUserid());
            cq.notEq("id", t.getApprSendLog().getId());
            cq.add();
            List<TPmApprSendLogsEntity> tPmApprSendLogs = systemService.getListByCriteriaQuery(cq, false);

            if (tPmApprSendLogs != null && tPmApprSendLogs.size() > 0) {
                j.setSuccess(false);
                message = "接收人已处理审批，无法撤回！";
            } else {
                t.setValidFlag(SrmipConstants.NO);
            }

            tPmApprLogsService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 审批接受记录新增页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TPmApprSendLogsEntity tPmApprSendLogs, HttpServletRequest req) {
        String apprType = req.getParameter("apprType");
        String apprTypeId = req.getParameter("apprTypeId");
        if (StringUtil.isNotEmpty(apprTypeId)) {
            apprType = apprTypeId.substring(0, 2);
            Map<String, Object> apprTypeInfo = apprService.selectApprTypeInfo(apprTypeId);
            req.setAttribute("apprTypeInfo", apprTypeInfo);
        } else {
            //找到审批类型信息
            List<Map<String, Object>> apprTypeInfos = apprService.selectApprTypeInfos(apprType);
            req.setAttribute("apprTypeInfos", apprTypeInfos);
        }

        String apprId = tPmApprSendLogs.getApprId();
        if (StringUtil.isNotEmpty(apprId)) {

            //记录被驳回的话，发送审批时提示
            TPmApprReceiveLogsEntity backLog = tPmApprLogsService.goAddGetBackLog(apprType, apprId);
            String tip = apprService.addBackLog(backLog, ApprovalConstant.APPR_TYPE_NAME.get(apprType));
            tPmApprSendLogs.setSenderTip(tip);
            tPmApprSendLogs.setTableName(ApprovalConstant.APPR_TYPE_TABLENAME.get(apprType));//将表名带入
            req.setAttribute("tPmApprSendLogsPage", tPmApprSendLogs);
        }

        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogs-add");
    }

    /**
     * 审批接受记录编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TPmApprReceiveLogsEntity tPmApprReceiveLogs, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tPmApprReceiveLogs.getId())) {
            tPmApprReceiveLogs = tPmApprLogsService.getEntity(TPmApprReceiveLogsEntity.class,
                    tPmApprReceiveLogs.getId());
            req.setAttribute("tPmApprReceiveLogsPage", tPmApprReceiveLogs);

            if (tPmApprReceiveLogs != null) {
                Map<String, Object> apprTypeInfo = apprService.selectApprTypeInfo(tPmApprReceiveLogs
                        .getSuggestionType());
                req.setAttribute("apprTypeInfo", apprTypeInfo);
            }

            req.setAttribute("YES", SrmipConstants.YES);
            req.setAttribute("NO", SrmipConstants.NO);

            String rebutValidFlag = req.getParameter("rebutValidFlag");
            if ("false".equals(rebutValidFlag)) {
                req.setAttribute("rebutValidFlag", false);
            } else {
                req.setAttribute("rebutValidFlag", true);
            }
        }
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogs-update");
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogsUpload");
    }

    /**
     * 跳转到发送页面
     * @param req
     * @return
     */
    @RequestMapping(params = "goApprSend")
    public ModelAndView goApprSend(HttpServletRequest req){
        String apprType = req.getParameter("apprType");
        String apprTypeSql = "SELECT ID, LABEL, HANDLER_TYPE, FIELD_LABEL FROM T_PM_FORM_CATEGORY "
                + "WHERE SUBSTR(ID, 0, 2) = ? ORDER BY ID";
        List<Map<String,Object>> apprNodeMap = this.systemService.findForJdbc(apprTypeSql,apprType);
        req.setAttribute("apprNodeMap", apprNodeMap);
        String recId = req.getParameter("recId");
        if(StringUtils.isNotEmpty(recId)){
            TPmApprReceiveLogsEntity recLog = this.systemService.get(TPmApprReceiveLogsEntity.class, recId);
            if(recLog!=null){
                String cur = recLog.getSuggestionType();
                if(apprNodeMap.size()>0){
                    for(int i=0;i<apprNodeMap.size();i++){
                        Map<String,Object> map = apprNodeMap.get(i);
                        String type = (String) map.get("ID");
                        if(cur.equals(type)){
                            if(i!=(apprNodeMap.size()-1)){
                            apprNodeMap.get(i+1).put("select","1");
                            break;
                            }
                        }
                    }
                }
            }
            req.setAttribute("opt", "audit");
        }else{
            if(apprNodeMap.size()>0){
               apprNodeMap.get(0).put("select", "1");
            }
        }
        String apprId = req.getParameter("apprId");
        req.setAttribute("apprNodes", apprNodeMap);
        req.setAttribute("apprId", apprId);
        req.setAttribute("apprType", apprType);
        req.setAttribute("recId", recId);
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogs-send");
    }
    
    /**
     * 跳转到驳回页面
     * @param req
     * @return
     */
    @RequestMapping(params = "goApprBack")
    public ModelAndView goApprBack(HttpServletRequest req){
        String recId = req.getParameter("recId");
        String apprType = req.getParameter("apprType");
        String apprId = req.getParameter("apprId");
        List<Map<String,Object>> nodeList = this.tPmApprLogsService.getNodeLink(recId,apprType);
        req.setAttribute("nodeList", JSON.toJSONString(nodeList));
        req.setAttribute("recId", recId);
        req.setAttribute("apprId", apprId);
        return new ModelAndView("com/kingtake/project/apprlog/tPmApprLogs-back");
    }
    
    
    
    @RequestMapping(params = "updateFinishFlag")
    @ResponseBody
    public AjaxJson updateFinishFlag(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson json = new AjaxJson();
        try {
           this.tPmApprLogsService.finishAppr(request);
           String apprType = request.getParameter("apprType");
           String apprId = request.getParameter("apprId");
           
           if(apprType.equals("20")){
        	   TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, apprId);
        	   project.getSchoolCooperationFlag();
               project.setAuditStatus("2");
               this.systemService.updateEntitie(project);
           }
          /* if(apprType.equals("03")){ 
        	   TPmIncomeContractApprEntity contractAppr = this.systemService.get(TPmIncomeContractApprEntity.class, apprId);
        	   String projectId = contractAppr.getProject().getId();
        	   //TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        	   TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        	   if(project.getSchoolCooperationFlag().equals("1") && StringUtil.isEmpty(project.getLxStatus())){
					this.lxStatus(project);
					project.setId(null);
					this.systemService.save(project);
        	   }
           }*/
           
           json.setMsg("结束审核流程成功！");
        } catch (Exception e) {
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("结束审核流程失败！");
        }
        return json;
    }
    /**
     * 立项
     * @param tpmProject
     */
    private void lxStatus(TPmProjectEntity tpmProject){
    	tpmProject.setLxStatus("1");
	    
	    String xmbhSql = "select t.PROJECT_NO,lsh from T_PM_PROJECT t where lsh is not null order by to_number(lsh) desc";
		List<Map<String, Object>> numMapList = this.systemService.findForJdbc(xmbhSql);
		String mr = "0000";
		int lshTemp;
		if (numMapList!=null && numMapList.size() > 0 && numMapList.get(0).get("lsh")!="" && numMapList.get(0).get("lsh")!=null) {
			String lsh = (String) numMapList.get(0).get("lsh");
			lshTemp = Integer.parseInt(lsh);
			lshTemp++;
			if(lshTemp<10){
				mr = "000"+lshTemp;
			}else if(lshTemp<100){
				mr = "00"+lshTemp;
			}else if(lshTemp<1000){
				mr = "0"+lshTemp;
			}else{
				mr = String.valueOf(lshTemp);
			}
		}
		tpmProject.setLsh(mr);
		
		List<TBXmlbEntity> xmlb = this.systemService.findByProperty(TBXmlbEntity.class, "id", tpmProject.getXmlb().getId());
		tpmProject.setXmml(xmlb.get(0).getParentType().getXmlb());
		tpmProject.setZgdw(xmlb.get(0).getZgdw());
		tpmProject.setXmlx(xmlb.get(0).getXmlx());
		tpmProject.setXmxz(xmlb.get(0).getXmxz());
		tpmProject.setXmly(xmlb.get(0).getXmly());
		String xmlx = xmlb.get(0).getXmlx();
		String xmxz = xmlb.get(0).getXmxz();
		String xmly = xmlb.get(0).getXmly();
		String rwzd = tpmProject.getRwzd();
		String lxyj = tpmProject.getLxyj();
		String wybh = "";
		if(xmlb.get(0).getWybh()!=null){
			wybh = xmlb.get(0).getWybh().toString();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String bd = sdf.format(tpmProject.getBeginDate()).substring(2, 4);
		String ed = sdf.format(tpmProject.getEndDate()).substring(2, 4);
		String xmbh="";
		xmbh = xmlx+xmxz+xmly+rwzd+wybh+lxyj+bd+ed+mr;
		tpmProject.setProjectNo(xmbh);
		
		TPmFpje fpje = new TPmFpje();
		fpje.setJhwjid(tpmProject.getJhid());
		fpje.setXmid(tpmProject.getId());
		fpje.setJe("0");
		systemService.save(fpje);
		
		String sql= "select ID from t_pm_project where LX_STATUS is null and project_no is null and CXM = ?";
		Map<String, Object> map = this.systemService.findOneForJdbc(sql, tpmProject.getCxm());
		if(map!=null && map.get("ID")!=null) {
			TPmProjectEntity pProject = systemService.get(TPmProjectEntity.class, (String)map.get("ID"));
			pProject.setProjectStatus("97");//状态为立项
			systemService.updateEntitie(pProject);
		}
    }
    
    /**
     * 驳回
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doBack")
    @ResponseBody
    public AjaxJson doBack(TPmApprReceiveLogsEntity tPmApprReceiveLogs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "驳回成功！";
        try {
            this.tPmApprLogsService.doBack(request);
        } catch (Exception e) {
            e.printStackTrace();
            message = "驳回失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 添加审批记录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doPass")
    @ResponseBody
    public AjaxJson doPass(TPmApprSendLogsEntity tPmApprSendLogs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "成功发送审批";
        try {
            tPmApprLogsService.doPass(request);

        } catch (Exception e) {
            e.printStackTrace();
            message = "发送审批失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    /**
     * 撤销发送记录
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "cancel")
    @ResponseBody
    public AjaxJson cancel(TPmApprSendLogsEntity tPmApprSendLogs, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "撤销成功";
        try {
            String recId = request.getParameter("recId");
            if(recId != null && recId != ""){
            	tPmApprLogsService.doCacel(recId);
            }else{
            	 message = "撤销失败,id为空";
                 j.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤销失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    
    @RequestMapping(params = "getIsHaveContractNode")
    @ResponseBody
    public AjaxJson getIsHaveContractNode(HttpServletRequest request) {
    	 AjaxJson j = new AjaxJson();
    	String incomeApprId = request.getParameter("incomeApprId");
    	String sql = "select a.ID from t_pm_contract_node a, t_pm_income_contract_appr b where a.IN_OUT_CONTRACTID = b.ID and b.ID = ?";
    	List<Map<String, Object>> list = this.systemService.findForJdbc(sql, incomeApprId);
    	if(list != null && list.size() > 0) {
    		j.setSuccess(true);
    	}else {
    		j.setSuccess(false);
    		j.setMsg("请先新增合同节点！");
    	}
    	return j;
    }
}
