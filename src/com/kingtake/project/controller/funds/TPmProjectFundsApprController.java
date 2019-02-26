package com.kingtake.project.controller.funds;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.util.POIPublicUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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

import com.alibaba.fastjson.JSON;
import com.kingtake.base.entity.projecttype.TBProjectTypeEntity;
import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.common.service.ExportXmlServiceI;
import com.kingtake.project.entity.abatepay.TPmAbateEntity;
import com.kingtake.project.entity.abatepay.TPmPayfirstEntity;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.funds.TPmContractFundsEntity;
import com.kingtake.project.entity.funds.TPmProjectBalanceEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsApprEntity;
import com.kingtake.project.entity.funds.TPmProjectFundsBagEntity;
import com.kingtake.project.entity.incomeapply.TPmIncomeApplyEntity;
import com.kingtake.project.entity.incomeplan.TPmIncomePlanEntity;
import com.kingtake.project.entity.incomeplan.TPmProjectPlanEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.member.TPmProjectMemberEntity;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;
import com.kingtake.project.service.funds.TPmContractFundsServiceI;
import com.kingtake.project.service.funds.TPmProjectFundsApprServiceI;
import com.kingtake.project.service.manage.TPmProjectServiceI;

/**
 * @Title: Controller
 * @Description: 项目预算管理
 * @author onlineGenerator
 * @date 2015-07-26 15:40:37
 * @version V1.0
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPmProjectFundsApprController")
public class TPmProjectFundsApprController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TPmProjectFundsApprController.class);

    @Autowired
    private TPmProjectFundsApprServiceI tPmProjectFundsApprService;
    
    @Autowired
    private TPmContractFundsServiceI tPmContractFundsServiceI;
    
    @Autowired
    private TPmProjectServiceI tPmProjectService;
    
    @Autowired
    private ExportXmlServiceI exportXmlServiceI;
    
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
     * 预算审批处理tab页面跳转（处理和待处理）
     * 
     * @return
     */
    @RequestMapping(params = "goReceiveTab")
    public ModelAndView goReceiveTab(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsApprList-receiveTab");
    }
    
    @RequestMapping(params = "openReceive")
    public ModelAndView openReceive(HttpServletRequest req) {
        return new ModelAndView("com/kingtake/project/funds/tPm-receive");
    }

    /**
     * 项目预算管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectFundsAppr")
    public ModelAndView tPmProjectFundsAppr(HttpServletRequest request) {
        //发起审批流程
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
            List<TPmProjectEntity> projectList = systemService.findByProperty(TPmProjectEntity.class,"glxm.id", projectId);
            // 获得项目信息
            TPmProjectEntity project = projectList.get(0);
            
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            request.setAttribute("projectId", project.getId());
            request.setAttribute("projectName", project.getProjectName());
            request.setAttribute("planContractFlag", project.getLxyj());
            request.setAttribute("accountingCode", project.getAccountingCode());
            request.setAttribute("PLANFLAG", ProjectConstant.PROJECT_PLAN);
            return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsApprList");
        }

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
            return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsApprList-receive");
        }
        return new ModelAndView("common/404.jsp");
    }
    
    
    /**
     * 项目预算管理列表 页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectYearFundsApprList")
    public ModelAndView tPmProjectYearFundsApprList(HttpServletRequest request) {
    	//发起审批流程
        String projectId = request.getParameter("projectId");
        request.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/funds/tPmProjectYearFundsApprList");
    }

    /**
     * 项目预算管理列表 过程管理页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "tPmProjectFundsApprProcess")
    public ModelAndView tPmProjectFundsApprProcess(HttpServletRequest request) {
        //发起审批流程
        String projectId = request.getParameter("projectId");
        if (StringUtil.isNotEmpty(projectId)) {
        	 
            List<TPmProjectEntity> projectList = systemService.findByProperty(TPmProjectEntity.class,"glxm.id", projectId);
                   // 获得项目信息
            TPmProjectEntity project_0 = projectList.get(0);
        	TPmProjectEntity project = systemService.getEntity(TPmProjectEntity.class, projectId);
            request.setAttribute("datagridType", ApprovalConstant.APPR_DATAGRID_SEND);
            
            request.setAttribute("projectId", project_0.getId());
            request.setAttribute("id", projectId);
            request.setAttribute("planContractFlag", project.getPlanContractFlag());
            request.setAttribute("accountingCode", project.getAccountingCode());
            request.setAttribute("PLANFLAG", ProjectConstant.PROJECT_PLAN);
            request.setAttribute("projectName", project.getProjectName());
        }
        return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsApprListProccess");
    }

    @RequestMapping(params = "getFundsLimit")
    @ResponseBody
    public AjaxJson getFundsLimit(TPmProjectFundsApprEntity appr, HttpServletRequest request,
            HttpServletResponse response) {
        appr = systemService.get(TPmProjectFundsApprEntity.class, appr.getId());
        TPmProjectEntity project = systemService.get(TPmProjectEntity.class, appr.getTpId());
        // 获得项目的经费类型：ABC...
        // 获得项目类型：计划类、合同类
        // 获得该项目的经费限制
        
        SQLQuery sqlQuery = systemService.getSession().createSQLQuery(ProjectConstant.FUNDS_SQL.get("fundsLimit"));
        Query query = sqlQuery.setParameter("projectType", project.getPlanContractFlag()).setParameter("feeType", project.getFeeType().getId()).setParameter("isScale", SrmipConstants.YES);
        Query query2 =query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> list = query2.list();
        /*List<Map<String, Object>> list = systemService.getSession()
                .createSQLQuery(ProjectConstant.FUNDS_SQL.get("fundsLimit"))
                .setParameter("projectType", project.getPlanContractFlag())
                .setParameter("feeType", project.getFeeType().getId()).setParameter("isScale", SrmipConstants.YES)
                .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();*/

        AjaxJson json = new AjaxJson();
        json.setSuccess(true);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("funds", project.getAllFee());
        result.put("limits", JSONHelper.collection2json(list));
        json.setObj(result);
        return json;
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
    public void datagrid(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        try {
            String datagridType = request.getParameter("datagridType");
            if (ApprovalConstant.APPR_DATAGRID_SEND.equals(datagridType)) {//发起审批
                String projectId = request.getParameter("projectId");
                String fundsCategory = request.getParameter("fundsCategory");

                CriteriaQuery cq = new CriteriaQuery(TPmProjectFundsApprEntity.class, dataGrid);
                org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectFundsAppr,
                        request.getParameterMap());
                cq.addOrder("createDate", SortDirection.desc);
                if (StringUtil.isNotEmpty(projectId)) {
                    cq.eq("tpId", projectId);
                }
                if(StringUtil.isNotEmpty(fundsCategory)) {
                	cq.eq("fundsCategory", fundsCategory);
                }
                cq.add();
                this.tPmProjectFundsApprService.getDataGridReturn(cq, true);
            } else if (ApprovalConstant.APPR_DATAGRID_RECEIVE.equals(datagridType)) {//处理审批
                String operateStatus = request.getParameter("operateStatus");

                TSUser user = ResourceUtil.getSessionUserName();
                StringBuffer countSql = new StringBuffer("SELECT COUNT(0)");
                StringBuffer resultSql = new StringBuffer();
                resultSql.append("SELECT APPR.ID AS APPR_ID, PROJECT.PLAN_CONTRACT_FLAG, APPR.PROJECT_NAME, PROJECT.ACCOUNTING_CODE,");
                resultSql.append("APPR.VOUCHER_NUM, APPR.INVOICE_NUM, APPR.START_YEAR, APPR.END_YEAR, APPR.ACCOUNT_FUNDS, ");
                resultSql.append("APPR.TOTAL_FUNDS, APPR.INCOMEPLAN_AMOUNT, ");
                resultSql.append("APPR.YEAR_FUNDS_PLAN, APPR.REIN_FUNDS_PLAN, APPR.FINISH_FLAG, APPR.T_P_ID AS PROJECTID,");
                resultSql.append("SEND.OPERATE_USERNAME, SEND.OPERATE_DATE, ");
                resultSql.append("EXT.LABEL, EXT.HANDLER_TYPE, ");
                resultSql.append("RECEIVE.ID, RECEIVE.REBUT_FLAG, RECEIVE.SUGGESTION_CODE, RECEIVE.SUGGESTION_CONTENT ");

                String temp = "FROM T_PM_PROJECT PROJECT, T_PM_PROJECT_FUNDS_APPR APPR, T_PM_APPR_SEND_LOGS SEND, "
                        + "T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT "
                        + "WHERE PROJECT.ID = APPR.T_P_ID AND APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID "
                        + "AND RECEIVE.SUGGESTION_TYPE = EXT.ID "
                        + "AND RECEIVE.RECEIVE_USERID = ? ";

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

                String accountingCode = request.getParameter("accounting.code");
                if (StringUtil.isNotEmpty(accountingCode)) {
                    temp += " AND APPR.ACCOUNTING_CODE LIKE '%" + accountingCode + "%'";
                }

                String invoiceNum = request.getParameter("invoice.num");
                if (StringUtil.isNotEmpty(invoiceNum)) {
                    temp += " AND APPR.INVOICE_NUM LIKE '%" + invoiceNum + "%'";
                }

                String voucherNum = request.getParameter("voucher.num");
                if (StringUtil.isNotEmpty(voucherNum)) {
                    temp += " AND APPR.VOUCHER_NUM LIKE '%" + voucherNum + "%'";
                }

                temp += "ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
                String[] params = new String[] { user.getId() };

                List<Map<String, Object>> result = systemService.findForJdbcParam(resultSql.append(temp).toString(),
                        dataGrid.getPage(), dataGrid.getRows(), params);
                Long count = systemService.getCountForJdbcParam(countSql.append(temp).toString(), params);

                dataGrid.setResults(result);
                dataGrid.setTotal(count.intValue());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        TagUtil.datagrid(response, dataGrid);
    }
    
    
    /**项目预算审核待办列表*/
   	@RequestMapping(params = "getFundsAppr")
   	public void getFundsAppr(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
   		 String operateStatus = request.getParameter("operateStatus");
   		 String project_name = request.getParameter("project_name");
   		 String accounting_code = request.getParameter("accounting_code");
   		 
   		 operateStatus = "0".equals(operateStatus) ? "'1'" : "'2','3'";
   		 String userId = ResourceUtil.getSessionUserName().getId();
   		 String sql = "select b.PROJECT_NO, b.ID as \"projectid\", b.project_name ,b.accounting_code, a.id, a.t_p_id, a.funds_appr_Id,"
   		 		+ " a.total_funds, a.year_funds, a.recive_userIds, a.recive_userNames,"
   		 		+ " a.submit_userId, a.submit_userName, a.submit_msg, a.submit_time, a.audit_status,"
   		 		+ " a.audit_userName, a.audit_time, a.audit_msg "
   		 		+ " from t_pm_project_funds_appr_audit a, T_PM_PROJECT b "
   		 		+ " where a.T_P_ID=b.ID and a.RECIVE_USERIDS like '%"+userId+"%' and  a.audit_status in("+operateStatus+")";
   		 
   		 if(StringUtils.isNotEmpty(project_name)) {
   			 sql += " and b.project_name like '%"+project_name+"%'";
   		 }
   		 if(StringUtils.isNotEmpty(accounting_code)) {
   			sql += " and b.accounting_code like '%"+accounting_code+"%'";
   		 }
   		 
   		List list  = this.systemService.findForJdbcParam(sql, dataGrid.getPage(), dataGrid.getRows());
   		dataGrid.setTotal(list.size());
   		dataGrid.setResults(list);
   		TagUtil.datagrid(response, dataGrid);    
   	}
   	
   	

    /**
     * 删除项目预算管理
     * 
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tPmProjectFundsAppr = systemService.getEntity(TPmProjectFundsApprEntity.class, tPmProjectFundsAppr.getId());
        message = "项目预算管理删除成功";
        try {
            tPmProjectFundsApprService.delMain(tPmProjectFundsAppr);
            this.systemService.getSession().createSQLQuery("delete from t_pm_contract_funds WHERE T_APPR_ID = :apprId").setParameter("apprId", tPmProjectFundsAppr.getId()).executeUpdate();
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目预算管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除项目预算管理
     * 
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目预算管理删除成功";
        try {
            for (String id : ids.split(",")) {
                TPmProjectFundsApprEntity tPmProjectFundsAppr = systemService.getEntity(
                        TPmProjectFundsApprEntity.class, id);
                tPmProjectFundsApprService.delete(tPmProjectFundsAppr);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目预算管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新之前进行判断：是否可以进行更新
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateFlag")
    @ResponseBody
    public AjaxJson updateFlag(String id, HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(id)) {
            TPmProjectFundsApprEntity tPmProjectFundsAppr = systemService.get(TPmProjectFundsApprEntity.class, id);
            if (tPmProjectFundsAppr != null) {
            	String fundsType = tPmProjectFundsAppr.getFundsType();
            	if("1".equals(fundsType)){
            		j.setSuccess(true);
            	}
            	else{
            		String apprStatus = tPmProjectFundsAppr.getFinishFlag();
                    if (ApprovalConstant.APPRSTATUS_FINISH.equals(apprStatus)) {
                        j.setSuccess(false);
                        j.setMsg("该预算审批流程已完成，预算信息不能再进行编辑！");
                    } else if (ApprovalConstant.APPRSTATUS_SEND.equals(apprStatus)) {
                        j.setSuccess(false);
                        j.setMsg("该预算已发送审批，预算信息不能再进行编辑！");
                    }
            	}                
            }
        }
        return j;
    }

    /**
     * 更新项目预算管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "doAddOrUpdate")
    @ResponseBody
    public AjaxJson doAddOrUpdate(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "项目预算表保存成功";

        try {
        	if(!StringUtil.isEmpty(tPmProjectFundsAppr.getPayfirstId())){
        		TPmPayfirstEntity payfirstEntity = this.systemService.getEntity(TPmPayfirstEntity.class, tPmProjectFundsAppr.getPayfirstId());
        		tPmProjectFundsAppr.setReinFundsPlan(payfirstEntity.getPayFunds());
        	}

        	//保存总预算明细
            tPmProjectFundsApprService.saveFundsAppr(tPmProjectFundsAppr);
            // 删除总预算明细表   
            this.systemService.getSession().createSQLQuery("delete from t_pm_contract_funds WHERE T_APPR_ID = :apprId").setParameter("apprId", tPmProjectFundsAppr.getId()).executeUpdate();
            /*List<TPmContractFundsEntity> list = buildTPmProjectFundsDetailEntity(tPmProjectFundsAppr.getFeeType(), request,tPmProjectFundsAppr.getTpId(),tPmProjectFundsAppr.getId());
            this.systemService.batchSave(list);*/
            
            tPmProjectFundsApprService.saveFundsDetail(tPmProjectFundsAppr,request);
            
            //把计划下达的预算状态改为是
            if(!StringUtil.isEmpty(tPmProjectFundsAppr.getIncomePlanId())){
        		TPmIncomePlanEntity incomePlanEntity = this.systemService.getEntity(TPmIncomePlanEntity.class, tPmProjectFundsAppr.getIncomePlanId());
        		incomePlanEntity.setYsStatus("1");
        		this.systemService.saveOrUpdate(incomePlanEntity);
        	}
            //把到账认领的预算状态改为是
            if(!StringUtil.isEmpty(tPmProjectFundsAppr.getIncomeApplyId())){
        		TPmIncomeApplyEntity incomeApplyEntity = this.systemService.getEntity(TPmIncomeApplyEntity.class, tPmProjectFundsAppr.getIncomeApplyId());
        		incomeApplyEntity.setYsStatus("1");
        		this.systemService.saveOrUpdate(incomeApplyEntity);
        	}
            //把项目余额的预算状态改为是
            if(!StringUtil.isEmpty(tPmProjectFundsAppr.getBalanceId())){
        		TPmProjectBalanceEntity projectBalanceEntity = this.systemService.getEntity(TPmProjectBalanceEntity.class, tPmProjectFundsAppr.getBalanceId());
        		projectBalanceEntity.setYsStatus("1");
        		this.systemService.saveOrUpdate(projectBalanceEntity);
        	}

        } catch (Exception e) {
            e.printStackTrace();
            message = "项目预算表保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    

    
    private List<Map<String,Object>> getApprovalBbudgetRela(String projApproval){
    	StringBuilder sb = new StringBuilder();
    	sb.append(" SELECT * FROM t_b_approval_budget_rela ");
    	sb.append(" 	WHERE PROJ_APPROVAL=? ");
    	sb.append(" 	AND PARENT_ID IS NULL");
    	sb.append(" ");
    	sb.append(" UNION ");
    	sb.append(" SELECT *  FROM t_b_approval_budget_rela  ");
    	sb.append(" 	WHERE PARENT_ID IN  ");
    	sb.append(" 	( ");
    	sb.append(" 		SELECT ID FROM t_b_approval_budget_rela  ");
    	sb.append(" 		WHERE PROJ_APPROVAL=? ");
    	sb.append(" 		AND PARENT_ID IS NULL ");
    	sb.append(" 	) ");
    	
  	    return systemService.findForJdbc(sb.toString(),projApproval,projApproval);
    }
    
    private List<TPmContractFundsEntity> buildTPmProjectFundsDetailEntity(String projApproval,HttpServletRequest request,String projectId,String fundsId){
    	   List<Map<String,Object>> list = getApprovalBbudgetRela(projApproval);
           List<TPmContractFundsEntity> entityList = new ArrayList<TPmContractFundsEntity>();
           for(Map<String,Object> detail : list) {
	        	TPmContractFundsEntity  entity = new TPmContractFundsEntity();
	        	entity.setTpId(fundsId);
	          	entity.setNum(MapUtils.getString(detail, "SORT_CODE"));
	        	entity.setContent(MapUtils.getString(detail, "BUDGET_NAE"));
	        	entity.setContentId(MapUtils.getString(detail, "ID"));
	        	entity.setAddChildFlag(MapUtils.getString(detail, "ADD_CHILD_FLAG"));
	        	entity.setParent(MapUtils.getString(detail, "PARENT_ID"));
	        	
	        	String feeStr = request.getParameter(MapUtils.getString(detail, "ID"))+"";
	        	entity.setMoney(getFeeValue(feeStr));
	        	entityList.add(entity);
           }
    	return entityList;
    }
    
    private Double getFeeValue(String feeStr) {
    	if(StringUtils.isBlank(feeStr)) {
    		return Double.valueOf(0);
    	}
    	feeStr = feeStr.replaceAll(",","");
    	if(!NumberUtils.isNumber(feeStr)) {
    		return Double.valueOf(0);
    	}
    	return Double.parseDouble(feeStr);
    }
    

    /**
     * 添加项目预算管理
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "getBudgetType")
    @ResponseBody
    public AjaxJson getBudgetType(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String type = request.getParameter("projectTypeId");//项目类型id
        String approvalCode = ProjectConstant.PROJECT_PLAN;
        if (StringUtils.isNotEmpty(type)) {
            TBProjectTypeEntity projectType = this.systemService.get(TBProjectTypeEntity.class, type);
            approvalCode = projectType.getApprovalCode();
        }
        String fundsId = request.getParameter("fundsId");//预算信息表ID

        String sql = "";
        if (StringUtil.isEmpty(fundsId)) {
            sql = ProjectConstant.FUNDS_SQL.get("statistics");
        } else {
            if (ProjectConstant.PROJECT_PLAN.equals(approvalCode)) {
                sql = ProjectConstant.FUNDS_SQL.get("planStatistics");
            } else {
                sql = ProjectConstant.FUNDS_SQL.get("contractStatistics");
            }
        }

        SQLQuery query = systemService.getSession().createSQLQuery(sql);
        query.setParameter("type", type);
        query.setParameter("flag", SrmipConstants.YES);
        if (StringUtil.isNotEmpty(fundsId)) {
            query.setParameter("fundsId", fundsId);
        }

        List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", list);
        int size = (list.size()) / 2;
        if (list.size() % 2 > 0) {
            size += 1;
        }
        map.put("size", size);
        j.setObj(map);
        return j;
    }

    /**
     * 新增、编辑页面跳转
     * 
     * @return
     */
    @RequestMapping(params = "goAddOrUpdate")
    public ModelAndView goAddOrUpdate(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest req) {
    	//判断计划下达ID是否为空，如不为空则把文件号，计划下达id，计划下达金额传到页面中
    	String incomeplanId = req.getParameter("incomeplanId");
    	if(!StringUtil.isEmpty(incomeplanId)){
    		TPmIncomePlanEntity incomePlanEntity = this.systemService.getEntity(TPmIncomePlanEntity.class, incomeplanId);
    		tPmProjectFundsAppr.setIncomePlanId(incomePlanEntity.getId());
    		tPmProjectFundsAppr.setDocumentNo(incomePlanEntity.getDocumentNo());
    		tPmProjectFundsAppr.setIncomeplanAmount(incomePlanEntity.getPlanAmount());
    	}
    	//判断到账信息认领ID是否为空，如不为空则把到账凭证号，到账信息认领id，到账金额，发票号传到页面中
    	String incomeApplyId = req.getParameter("incomeApplyId");
    	if(!StringUtil.isEmpty(incomeApplyId)){
    		TPmIncomeApplyEntity incomeApplyEntity = this.systemService.getEntity(TPmIncomeApplyEntity.class, incomeApplyId);
    		tPmProjectFundsAppr.setIncomeApplyId(incomeApplyEntity.getId());
    		tPmProjectFundsAppr.setVoucherNum(incomeApplyEntity.getVoucherNo());
    		tPmProjectFundsAppr.setAccountFunds(incomeApplyEntity.getApplyAmount());
    		if(incomeApplyEntity.getInvoice() != null){
    			tPmProjectFundsAppr.setInvoiceNum(incomeApplyEntity.getInvoice().getInvoiceNum1());
    		}    		
    	}
    	//判断项目余额ID是否为空，如不为空则把项目余额id,预算余额传到页面中
    	String balanceId = req.getParameter("balanceId");
    	if(!StringUtil.isEmpty(balanceId)){
    		TPmProjectBalanceEntity projectBalanceEntity = this.systemService.getEntity(TPmProjectBalanceEntity.class, balanceId);
    		tPmProjectFundsAppr.setBalanceId(projectBalanceEntity.getId());    		
    		tPmProjectFundsAppr.setTotalFunds(projectBalanceEntity.getBalanceAmount());    		
    	}
        if (StringUtil.isEmpty(tPmProjectFundsAppr.getId())) {
    	     String type = req.getParameter("type");
            if(type!=null){
            	req.setAttribute("type", type);
            }else{
            	req.setAttribute("type", 0);
            }
               
            // 根据申报项目id，查询立项项目信息
            String projectId = req.getParameter("projectId");
            TPmProjectEntity proj = this.systemService.getEntity(TPmProjectEntity.class, projectId);
            req.setAttribute("project", proj);
            if (proj != null && proj.getProjectType() != null) {
                req.setAttribute("projectTypeName", proj.getProjectType().getProjectTypeName());
                req.setAttribute("projectTypeId", proj.getProjectType().getId());
            }
            req.setAttribute("planContractFlag", proj.getLxyj());
            tPmProjectFundsAppr.setTpId(proj.getId());
            tPmProjectFundsAppr.setProjectName(proj.getProjectName());
            tPmProjectFundsAppr.setAccountingCode(proj.getAccountingCode());
            tPmProjectFundsAppr.setDepartsId(proj.getDevDepart() == null ? null : proj.getDevDepart().getId());
            tPmProjectFundsAppr.setDepartsName(proj.getDevDepart() == null ? null : proj.getDevDepart().getDepartname());
            tPmProjectFundsAppr.setProjectManager(proj.getProjectManagerId());
            tPmProjectFundsAppr.setProjectManagerName(proj.getProjectManager());
            tPmProjectFundsAppr.setStartYear(DateUtil.formatDate(proj.getBeginDate(),"yyyy"));
            tPmProjectFundsAppr.setEndYear(DateUtil.formatDate(proj.getEndDate(), "yyyy"));
            tPmProjectFundsAppr.setFeeType(proj.getFeeType() == null ? null : proj.getFeeType().getId());
            req.setAttribute("feeTypeName", proj.getFeeType() == null ? null : proj.getFeeType().getFundsName());
            tPmProjectFundsAppr.setProjectSource(proj.getProjectSource());
            BigDecimal allFee = tPmProjectService.getAllFee(proj.getId());//获取项目总经费
            tPmProjectFundsAppr.setAllFee(allFee);
            
            
          	//获取项目预算信息[总预算次数、历次总预算合计]
        	String sql = "select * from t_pm_project_funds_appr where t_p_id='"+projectId+"' and funds_category=1";
        	List<Map<String,Object>> budgetList = systemService.findForJdbc(sql);
        	Double allFunds = Double.valueOf("0");//历次总预算合计总和
        	if(CollectionUtils.isNotEmpty(budgetList)) {
        		for(Map<String,Object> fundsAppr : budgetList) {
        			Double totalFunds = MapUtils.getDouble(fundsAppr, "TOTAL_FUNDS", Double.valueOf("0"));
        			allFunds = allFunds + totalFunds;
        		}
        	}
        	if(CollectionUtils.isNotEmpty(budgetList)) {
        		tPmProjectFundsAppr.setTotalFunds(allFee.subtract(BigDecimal.valueOf(allFunds)));
        	}else {
        		tPmProjectFundsAppr.setTotalFunds(allFee);
        	}
        	
        	
            
            //如果是合同类项目，则取合同编号,计划类则从项目基本信息去计划合同文号
            String billNum = getBillNum(proj);
            tPmProjectFundsAppr.setBillNum(billNum);

            // 获得项目组成员
            List<TPmProjectMemberEntity> memberList = systemService.findByProperty(TPmProjectMemberEntity.class,
                    "project.id", projectId);
            List<String> idList = new ArrayList<String>();
            List<String> nameList = new ArrayList<String>();
            if (memberList.size() > 0) {
                for (TPmProjectMemberEntity member : memberList) {
                    if (StringUtil.isNotEmpty(member.getId())) {
                        idList.add(member.getId());
                        nameList.add(member.getName());
                    }
                }
            }
            tPmProjectFundsAppr.setMembersId(StringUtil.listTtoString(idList));
            tPmProjectFundsAppr.setMembersName(StringUtil.listTtoString(nameList));
        	tPmProjectFundsAppr.setFundsType( req.getParameter("operType")+"");
        } else {
            tPmProjectFundsAppr = tPmProjectFundsApprService.getEntity(TPmProjectFundsApprEntity.class,
                    tPmProjectFundsAppr.getId());
            req.setAttribute("type", tPmProjectFundsAppr.getFundsType());
            req.setAttribute("tpId", tPmProjectFundsAppr.getTpId());
            TPmProjectEntity proj = this.systemService.get(TPmProjectEntity.class, tPmProjectFundsAppr.getTpId());
            if (proj != null && proj.getProjectType() != null) {
                req.setAttribute("projectTypeName", proj.getProjectType().getProjectTypeName());
                req.setAttribute("projectTypeId", proj.getProjectType().getId());
            }
            req.setAttribute("planContractFlag", proj.getLxyj());
            req.setAttribute("feeTypeName", proj.getFeeType() == null ? null : proj.getFeeType().getFundsName());
        }
    
        req.setAttribute("appr", tPmProjectFundsAppr);
        System.out.println(JSON.toJSONString(tPmProjectFundsAppr));
        //req.setAttribute("PROJECT_CONTRACT", ProjectConstant.PROJECT_CONTRACT);
        //buildFoundTable2(tPmProjectFundsAppr.getFeeType(),req,tPmProjectFundsAppr.getId());
        buildFoundTable(req,tPmProjectFundsAppr);
        
        return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsAppr");
    }
    //预算树加载修改   modified by xiaozhongliang  2018-08-20
    private void buildFoundTable(HttpServletRequest req,TPmProjectFundsApprEntity tPmProjectFundsAppr){
    	String projectId = tPmProjectFundsAppr.getTpId();
    	String apprId = tPmProjectFundsAppr.getId();
    	String sql = "select B.BUDGET_CATEGORY as ID, " +
    			"B.BUDGET_CATEGORY as SORT_CODE, " +
    			"B.BUDGET_CATEGORY_NAME as BUDGET_NAE, " +
    			"A.ALL_FEE as MONEY, " +
    			"(B.UNIVERSITY_PROP+B.UNIT_PROP+B.DEV_UNIT_PROP+B.PROJECTGROUP_PROP )/100 * A.ALL_FEE as BALANCE, " +
    			"B.UNIVERSITY_PROP as UNIVERSITY_PROP, " +
    			"B.UNIT_PROP as UNIT_PROP, " +
    			"B.DEV_UNIT_PROP as DEV_UNIT_PROP, " +
    			"B.PROJECTGROUP_PROP as PROJECTGROUP_PROP " +
    			"from t_pm_project A left join T_PM_BUDGET_FUNDS_REL B on A.FEE_TYPE=B.FUNDS_TYPE " +
    			"where A.ID=? ";
    	Map<String,Object> dataMap = systemService.findOneForJdbc(sql, projectId);
    	Map<String,Object> data = StringUtil.isNotEmpty(dataMap) ? dataMap : new HashMap<String,Object>();
    	String sql2 = "select C.ID as ID, " +
    			"C.CATEGORY_CODE_DTL as SORT_CODE, " +
    			"C.DETAIL_NAME as BUDGET_NAE, " +
    			"nvl(D.MONEY,0) as MONEY, " +
    			"nvl(C.DETAIL_SYMBOL,D.ADD_CHILD_FLAG) as SCALE_FLAG " +
    			"from t_pm_project A inner join T_PM_BUDGET_FUNDS_REL B on A.FEE_TYPE=b.FUNDS_TYPE " +
    			"left join T_PM_BUDGET_CATEGORY_ATTR C on B.BUDGET_CATEGORY=C.CATEGORY_CODE " +
    			"left join t_pm_contract_funds D on C.CATEGORY_CODE_DTL=D.NUM and A.ID=D.T_P_ID " +
    			"where A.ID=? " ;
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	if(StringUtil.isNotEmpty(apprId)){
    		sql2 += " and D.T_APPR_ID=? order by C.ID " ;
    		list = systemService.findForJdbc(sql2,projectId, apprId);
    	}else{
    		sql2 += "order by C.ID " ;
    		list = systemService.findForJdbc(sql2,projectId);
    	}
    	double sumMoney = MapUtils.getDoubleValue(data, "MONEY");
    	double sunFunds = 0.0;
    	for(String key : data.keySet()){
    		try {
    			IndirectEnum.valueOf(key);
    			Map<String,Object> jjData = null;
    			int index = -1;
    			for(int i = 0 ; i < list.size() ;i++){
    	    		Map<String,Object> fundsData = list.get(i);
    	    		double prop = MapUtils.getDoubleValue(data, key)/100;//百分比
    	    		if(MapUtils.getString(fundsData, "SORT_CODE").equals(IndirectEnum.JJ_PROP.value)){
    	    			jjData = fundsData;
    	    			index = i;
    	    		}else if(MapUtils.getString(fundsData, "SORT_CODE").equals(IndirectEnum.valueOf(IndirectEnum.class, key).value)){
    	    			
    	    			double jjFunds = sumMoney * prop;
    	    			fundsData.put("MONEY", jjFunds);
    	    			sunFunds += jjFunds;
    	    		}
    	    	}
    			if(index > 0){
    				jjData.put("MONEY", sunFunds);
    				list.set(index, jjData);
    			}
			} catch (Exception e) {
				continue;
			}
    	}
    	
    	int row = list.size() % 2 ==0 ? list.size() / 2 : list.size() / 2 +1;
    	data.put("SUB_TYPE", list);
    	data.put("COS_SPAN", row);
    	req.setAttribute("TYPE_HAS_SUB", data);
        req.setAttribute("FOUDS_ROW_NUM", row + 2 );
    	
    }
    private void buildFoundTable2(String projApproval,HttpServletRequest req,String fundid) {
        //设置总预算明细
        String sql = "select CONTENT_ID,MONEY from t_pm_contract_funds where T_P_ID = ?";
        List<Map<String,Object>> datalist = systemService.findForJdbc(sql,fundid);
        HashMap<String,Object> moneyMap = new HashMap<String,Object>();
        if(CollectionUtils.isNotEmpty(datalist)) {
        	for(Map<String,Object> data : datalist) {
        		String name = MapUtils.getString(data, "CONTENT_ID");
        		Double meney = MapUtils.getDouble(data, "MONEY", Double.valueOf(0));
        		moneyMap.put(name, meney);
        	}
        }
        
    	//获取一级分类(有子节点)
    	ArrayList<Map<String,Object>>  topLevelHasSubList = new ArrayList<Map<String,Object>>();
    	//获取一级分类(子无节点)
    	ArrayList<Map<String,Object>>  topLevelNoSubList = new ArrayList<Map<String,Object>>();
    	int FOUDS_ROW_NUM = 1;
    	
    	
    	StringBuilder sb = new StringBuilder(); 
    	sb.append(" SELECT ID,BUDGET_NAE,SORT_CODE,SCALE_FLAG FROM t_b_approval_budget_rela WHERE PROJ_APPROVAL=?");
    	sb.append(" AND PARENT_ID IS NULL ORDER BY SORT_CODE");
        List<Map<String,Object>> topList = systemService.findForJdbc(sb.toString(),projApproval);
        if(CollectionUtils.isNotEmpty(topList)) {
        	for(Map<String,Object> data : topList) {
        		String id = MapUtils.getString(data, "ID");
        		List<Map<String,Object>>  secondLevelList = getSubList(id);
        		data.put("SUB_TYPE", secondLevelList);
        		int colspan = (secondLevelList.size())/2+1;
        		data.put("COS_SPAN", colspan);
        	
        		if(CollectionUtils.isNotEmpty(secondLevelList)) {
        			for(Map<String,Object> subData : secondLevelList) {
        				String subId = MapUtils.getString(subData, "ID");
        				subData.put("MONEY", moneyMap.get(subId));
        			}
        			FOUDS_ROW_NUM+= colspan;
        			topLevelHasSubList.add(data);
        		}else {
        			topLevelNoSubList.add(data);
        			data.put("MONEY", moneyMap.get(id));
        		}
        	}
        }
        req.setAttribute("TYPE_HAS_SUB", topLevelHasSubList);
        req.setAttribute("TYPE_NO_SUB", topLevelNoSubList);
        req.setAttribute("FOUDS_ROW_NUM", FOUDS_ROW_NUM);
        
    }
    
    private  List<Map<String,Object>>  getSubList(String pid){
    	StringBuilder sb1 = new StringBuilder(); 
    	sb1.append(" SELECT ID,BUDGET_NAE,SORT_CODE,SCALE_FLAG FROM t_b_approval_budget_rela WHERE PARENT_ID=? ORDER BY SORT_CODE");
    	return systemService.findForJdbc(sb1.toString(),pid);
    }
    
    /*
    //预算树加载修改：采用接口过来的模板数据    modified by luokun  2018-08-10
    private void buildFoundTable(HttpServletRequest req,String fundid) {
    	int FOUDS_ROW_NUM = 3;//两个小计，占两行; 总额和差额占一行
    	List hasSubList = new ArrayList();
    	List noSubList = new ArrayList();
    	if(!StringUtil.isEmpty(fundid)){
    		//查询预算类型
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select k.budget_category from t_pm_project t ");
    		sql.append(" left join T_PM_BUDGET_FUNDS_REL k on t.fee_type = k.funds_type ");
    		sql.append(" where t.id=? ");
    		List<Map<String, Object>> typeList = systemService.findForJdbc(sql.toString(), fundid);
    		if(typeList != null && typeList.size() > 0){
    			Object budgetType = typeList.get(0).get("budget_category");
    			String sql2 = " select t.id,t.category_code as SORT_CODE,t.category_name as BUDGET_NAE,0 as SCALE_FLAG "
    					+ "from T_PM_BUDGET_CATEGORY t where t.CATEGORY_CODE= ? ";
    			List<Map<String, Object>> oneList = systemService.findForJdbc(sql2, budgetType);
    			if(oneList != null && oneList.size() > 0){
    				for(int i=0;i<oneList.size();i++){
    					Map oneMap = oneList.get(i);
    					Object code = oneMap.get("SORT_CODE");
    					
    					List<Map<String, Object>> twoList = new ArrayList<Map<String, Object>>();
    					//判断是否有预算
    					String existSql = "select * from t_pm_contract_funds t where t.t_p_id=? ";
    					List<Map<String, Object>> isExist = systemService.findForJdbc(existSql, fundid);
    					if(isExist != null && isExist.size() > 0){
    						String sql3 = " select t.id,t.category_code_dtl as SORT_CODE,t.detail_name as BUDGET_NAE,"
        							+ " 0 as SCALE_FLAG,k.money as MONEY from T_PM_BUDGET_CATEGORY_attr t "
        							+ " left join t_pm_contract_funds k on t.id = k.content_id where t.category_code='"+code+"' and k.t_p_id='"+fundid+"' ";
        					twoList = systemService.findForJdbc(sql3);
    					}else{
    						String sql3 = " select t.id,t.category_code_dtl as SORT_CODE,t.detail_name as BUDGET_NAE,"
        							+ " 0 as SCALE_FLAG,0 as MONEY from T_PM_BUDGET_CATEGORY_attr t where t.category_code='"+code+"' ";
        					twoList = systemService.findForJdbc(sql3);
    					}
    					oneMap.put("SUB_TYPE", twoList);
    					
    					int row = twoList.size() % 2 ==0 ? twoList.size() / 2 : twoList.size() / 2 +1;
    					FOUDS_ROW_NUM+= row;
    					oneMap.put("COS_SPAN", row);
    					
    					if(twoList != null && twoList.size() > 0){
    						hasSubList.add(oneMap);
    					}else{
    						noSubList.add(oneMap);
    					}
    				}
    				
    			}
    			
    		}
    	}

    	 req.setAttribute("TYPE_HAS_SUB", hasSubList);
         req.setAttribute("TYPE_NO_SUB", noSubList);
         req.setAttribute("FOUDS_ROW_NUM", FOUDS_ROW_NUM);
    	
    	
    }
    
    */
    
    
    /**
     * 点击录入预算按钮
     * 
     * @return
     */
    @RequestMapping(params = "checkYsType")
    public ModelAndView checkYsType(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest req) {
    	String projectId = req.getParameter("projectId");
    	req.setAttribute("projectId", projectId);
        return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsType");
    }
    
    /**
     * 点击录入预算按钮
     * 
     * @return
     */
    @RequestMapping(params = "checkYearYsType")
    public ModelAndView checkYearYsType(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest req) {
    	String projectId = req.getParameter("projectId");
    	TPmProjectEntity projectEntity = systemService.get(TPmProjectEntity.class, projectId);
    	String planContractFlag = projectEntity.getLxyj();
    	req.setAttribute("projectId", projectId);
    	CriteriaQuery cq = new CriteriaQuery(TPmProjectFundsApprEntity.class);
        cq.eq("tpId", projectId);
        cq.eq("fundsCategory", "1");//是否有总预算
        cq.add();
        List<TPmProjectFundsApprEntity> totalFundsApp = this.systemService.getListByCriteriaQuery(cq, false);
        req.setAttribute("fundsType", "-1");
        String sql = "";
        Double totalFunds = 0d;
        if(totalFundsApp == null || totalFundsApp.size() == 0) {//当前项目无总预算，查询是否有余额
        	req.setAttribute("isHaveTotalFund", "0");
        	sql = "select ID, BALANCE_AMOUNT from t_pm_project_balance where (T_P_ID = ? or PROJECT_NO = ?) and (YS_STATUS = 0 or YS_STATUS is null)";
    		List<Map<String, Object>> balances = this.systemService.findForJdbc(sql, projectId, projectEntity.getProjectNo());
    		if(balances != null && balances.size() > 0) {//有余额，一定要先做零基预算
    			int len = balances.size();
    			String iBalanceIds = "";
    			for (int i = 0; i < len; i++) {
    				Map<String, Object> balance = balances.get(i);
    				String BALANCE_AMOUNT = balance.get("BALANCE_AMOUNT") == null ? "0" : balance.get("BALANCE_AMOUNT").toString();
    				totalFunds += Double.parseDouble(BALANCE_AMOUNT);
    				if(i > 0) {
    					iBalanceIds += ",";
    				}
    				iBalanceIds += balance.get("ID").toString();
				}
    			req.setAttribute("fundsType", "5");//零基预算
    			req.setAttribute("iBalanceIds", iBalanceIds);
    		}else if(ProjectConstant.PROJECT_PLAN.equals(planContractFlag)) {//无总预算，无余额，计划类项目，判断是否有计划分配金额
    			sql = "select a.ID as \"id\", a.CW_STATUS as \"cwStatus\", b.JE as \"je\" from";
    			sql += " t_pm_project_plan a, T_PM_FPJE b where a.ID = b.JHWJID and b.XMID = ?";
    			sql += " and a.ADUIT_STATUS = 2 and (a.YS_STATUS = 0 or a.YS_STATUS is null)";
    			List<Map<String, Object>> planMountList = this.systemService.findForJdbc(sql, projectId);
    	        if (planMountList != null && planMountList.size() > 0) {
    	        	Map<String, Object> planMount = planMountList.get(0);
    	        	String id = planMount.get("id").toString();
    	        	String cwStatus = planMount.get("cwStatus") == null ? "0" : planMount.get("cwStatus").toString();
    	        	String je = "";
    	        	if(planMount.get("je") == null || "".equals(planMount.get("je").toString())) {
    	        		je = "0";
    	        	}else {
    	        		je = planMount.get("je").toString();
    	        	}
    	        	if("2".equals(cwStatus)) {//财务审核通过不通过
    	                req.setAttribute("msg", "当前项目无余额，并且当前计划分配金额财务审核未通过，无法进行年度预算！");
    	        	}else if("0".equals(cwStatus) || "".equals(cwStatus)){
    	        		 req.setAttribute("msg", "当前项目无余额，并且当前计划分配金额财务还未审核，无法进行年度预算！");
    	        	}else {
    	        		totalFunds = new BigDecimal(je).doubleValue();
    	        		if(totalFunds > 0) {
    	        			req.setAttribute("fundsType", "6");//开支计划
    	        			req.setAttribute("iPlanIncomeIds", id);
    	        		}else {
    	        			req.setAttribute("msg", "当前项目无余额，并且没有计划分配金额，无法进行年度预算！");
    	        		}
    	        	}
				}else {
	    			req.setAttribute("msg", "当前项目无余额，并且没有计划分配金额，无法进行年度预算！");
	    		}
    		}else {//无总预算，无余额，合同类项目，不能做预算
    			req.setAttribute("msg", "当前项目无余额，并且没有做总预算，无法进行年度预算！");
    		}
    		
    	}else {//已经有总预算了
    		req.setAttribute("isHaveTotalFund", "1");
    		String iIncomeIds = "";
    		String spProject = projectEntity.getGlxm().getId();
    		List<Map<String, Object>> funList = new ArrayList<Map<String, Object>>();
    		String sql2 = "select ID,PAY_FUNDS as APPLY_AMOUNT from T_B_PM_PAYFIRST where PROJECT_ID=? and CW_STATUS='1' and BPM_STATUS >=3 and nvl(FUNDS_STATUS,0)=0 ";
    		List<Map<String, Object>> payfirstFuns = this.systemService.findForJdbc(sql2, spProject);
    		
    		if(ProjectConstant.PROJECT_PLAN.equals(planContractFlag)) {//计划类项目判断是否有计划分配金额
    			sql = "select a.ID as ID, b.JE as APPLY_AMOUNT from";
    			sql += " t_pm_project_plan a, T_PM_FPJE b where a.ID = b.JHWJID and b.XMID = ?";
    			sql += " and a.CW_STATUS = 1 and a.ADUIT_STATUS = 2 and (a.YS_STATUS = 0 or a.YS_STATUS is null)";
    			funList = this.systemService.findForJdbc(sql, projectId);
    		}else {//合同类判断是否有到款分配信息
    			sql = "select ID, PAYFIRST_FUNDS,APPLY_AMOUNT from t_pm_income_apply where T_P_ID = ? and  CW_STATUS = 1 and AUDIT_STATUS = 2 and (YS_STATUS = 0 or YS_STATUS is null)";
    			funList = this.systemService.findForJdbc(sql, spProject);
	       		
    		}
    		if(payfirstFuns != null && payfirstFuns.size() > 0){
    			funList.addAll(payfirstFuns);
    		}
    		for (int i = 0; i < funList.size(); i++) {
  				 Map<String, Object> oneApply = funList.get(i);
  				 double APPLY_AMOUNT = MapUtils.getDoubleValue(oneApply, "APPLY_AMOUNT",0.0);
  				 double PAYFIRST_FUNDS = MapUtils.getDoubleValue(oneApply, "PAYFIRST_FUNDS",0.0);
  				 totalFunds += (APPLY_AMOUNT - PAYFIRST_FUNDS);
  				 if(i > 0) {
  					 iIncomeIds += ",";
  				 }
  				 iIncomeIds += oneApply.get("ID").toString();
			}
    		
  			req.setAttribute("fundsType", "6");//开支计划
  			req.setAttribute("incomeIds", iIncomeIds);
    		
    		
    		sql = "select ID from t_pm_project_funds_appr where CW_STATUS = 1 and FINISH_FLAG = 2 and FUNDS_CATEGORY = 2 and FUNDS_TYPE in(5, 6) and T_P_ID = ? order by CREATE_DATE desc ";
    		List<Map<String, Object>> list = this.systemService.findForJdbc(sql, projectId);
    		if(list != null && list.size() > 0 && totalFunds == 0) {//只能做调整预算
    			req.setAttribute("fundsType", "7");//调整预算
    			req.setAttribute("tzApprId", MapUtils.getString(list.get(0), "ID"));
    		}else if(list != null && list.size() > 0 && totalFunds > 0){//可以做调整预算，也可以做开支预算
    			req.setAttribute("iIsCanTz", "1");
    			req.setAttribute("tzApprId", MapUtils.getString(list.get(0), "ID"));
    		}else {
    			req.setAttribute("msg", "当前项目没有进行过年度预算，并且没有余额，也没有计划分配金额或到账分配金额，无法做年度预算！");
    		}
    	}
        req.setAttribute("projectId", projectId);
        req.setAttribute("totalFunds", totalFunds+"");
        return new ModelAndView("com/kingtake/project/funds/tPmProjectYearFundsType");
    }
    
    /**
     * 添加
     * 
     * @param ids
     * @return
     */
    @RequestMapping(params = "addYearFundsDefault")
    @ResponseBody
    public AjaxJson addYearFundsDefault(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest req) {
    	AjaxJson j = new AjaxJson();
        message = "年度预算表保存成功";
        try {
        	String projectId = req.getParameter("tpId"); 
        	TPmProjectEntity project =  this.systemService.getEntity(TPmProjectEntity.class, projectId);
        	tPmProjectFundsAppr.setProjectName(project.getProjectName());
        	tPmProjectFundsAppr.setAllFee(project.getAllFee());
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        	Date startYear = project.getBeginDate();
        	if(startYear != null) {
        		tPmProjectFundsAppr.setStartYear(sdf.format(startYear));
        	}
        	Date endYear = project.getEndDate();
        	if(endYear != null) {
        		tPmProjectFundsAppr.setEndYear(sdf.format(endYear));
        	}
        	// 获得项目组成员
            List<TPmProjectMemberEntity> memberList = systemService.findByProperty(TPmProjectMemberEntity.class,
                    "project.id", projectId);
            List<String> idList = new ArrayList<String>();
            List<String> nameList = new ArrayList<String>();
            if (memberList.size() > 0) {
                for (TPmProjectMemberEntity member : memberList) {
                    if (StringUtil.isNotEmpty(member.getId())) {
                        idList.add(member.getId());
                        nameList.add(member.getName());
                    }
                }
            }
            tPmProjectFundsAppr.setMembersId(StringUtil.listTtoString(idList));
            tPmProjectFundsAppr.setMembersName(StringUtil.listTtoString(nameList));
        	tPmProjectFundsAppr.setTpId(projectId);
        	tPmProjectFundsAppr.setFundsCategory("2");//年度预算
        	tPmProjectFundsAppr.setFinishFlag("9");//9:待编辑提交 0：待提交（该状态下可编辑，可删除） 1：待提交  2：    2：完成审核（这里待确认，因为预算信息和财务交互的时候财务会返回一个审核状态给我们，我们收到这个状态不知道是不是要同步回这个字段）
        	tPmProjectFundsAppr.setTotalFunds(new BigDecimal(req.getParameter("totalFunds")));
        	tPmProjectFundsAppr.setFundsType(req.getParameter("fundsType"));
        	tPmProjectFundsAppr.setFeeType(project.getFeeType().getId());
            tPmProjectFundsApprService.saveFundsAppr(tPmProjectFundsAppr);
            addYearFundsDetail(tPmProjectFundsAppr,req);
            if("5".equals(req.getParameter("fundsType"))) {//零基预算，把余额状态更新为预算中
            	String iBalanceIds = req.getParameter("iBalanceIds");
            	//把项目余额的预算状态改为是
                if(!StringUtil.isEmpty(iBalanceIds)){
                	String[] iBalanceIdArray = iBalanceIds.split(",");
                	int len = iBalanceIdArray.length;
                	for (int i = 0; i < len; i++) {
                		TPmProjectBalanceEntity projectBalanceEntity = this.systemService.getEntity(TPmProjectBalanceEntity.class, iBalanceIdArray[i]);
                		projectBalanceEntity.setYsStatus("1");
                		projectBalanceEntity.setYsApprId(tPmProjectFundsAppr.getId());
                		this.systemService.saveOrUpdate(projectBalanceEntity);
					}
            	}
            }
            if("6".equals(req.getParameter("fundsType"))) {
            	String iPlanIncomeIds = req.getParameter("iPlanIncomeIds");
            	//把计划分配预算状态改为是
            	if(!StringUtil.isEmpty(iPlanIncomeIds)){
            		String[] iPlanIncomeIdArray = iPlanIncomeIds.split(",");
            		int len = iPlanIncomeIdArray.length;
            		for (int i = 0; i < len; i++) {
            			TPmProjectPlanEntity projectPlan = this.systemService.getEntity(TPmProjectPlanEntity.class, iPlanIncomeIdArray[i]);
            			projectPlan.setYsStatus("1");
            			projectPlan.setYsApprId(tPmProjectFundsAppr.getId());
						this.systemService.saveOrUpdate(projectPlan);
					}
            	}
            	String incomeIds = req.getParameter("incomeIds");
            	//把到账认领的预算状态改为是
            	if(!StringUtil.isEmpty(incomeIds)){
            		String[] incomeIdArray = incomeIds.split(",");
            		int len = incomeIdArray.length;
            		for (int i = 0; i < len; i++) {
            			String incomeId = incomeIdArray[i];
            			TPmIncomeApplyEntity incomeApplyEntity = this.systemService.getEntity(TPmIncomeApplyEntity.class, incomeId);
            			if(StringUtil.isNotEmpty(incomeApplyEntity)){
            				incomeApplyEntity.setYsStatus("1");
                			incomeApplyEntity.setYsApprId(tPmProjectFundsAppr.getId());
                			this.systemService.saveOrUpdate(incomeApplyEntity);
            			}else{
            				String sql = "update T_B_PM_PAYFIRST set FUNDS_STATUS=1 where id=?";
            				this.systemService.executeSql(sql, incomeId);
            			}
            			
            		}
            	}
            }
           
            //tPmContractFundsServiceI.init(tPmProjectFundsAppr.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "项目预算表保存失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    private void addYearFundsDetail(TPmProjectFundsApprEntity tPmProjectFundsAppr,HttpServletRequest req){
    	String tzApprId = req.getParameter("tzApprId");
    	String tpId = tPmProjectFundsAppr.getTpId();
    	String sql = "select B.* from t_pm_project_funds_appr A left join t_pm_contract_funds B on A.ID=B.T_APPR_ID " + 
				"where A.T_P_ID=? and FUNDS_CATEGORY='1'";
    	if(StringUtil.isNotEmpty(tzApprId)){
    		sql = " select * from t_pm_contract_funds where T_APPR_ID=? ";
    		tpId = tzApprId;
    	}
    	List<Map<String, Object>> list = this.systemService.findForJdbc(sql, tpId);
    	List<TPmContractFundsEntity> tpFundsList = new ArrayList<TPmContractFundsEntity>();
    	for(int i = 0 ; i <  list.size() ;i++){
    		Map<String, Object> data = list.get(i);
    		TPmContractFundsEntity entity = new TPmContractFundsEntity();
    		entity.setTpId(MapUtils.getString(data, "T_P_ID"));
    		entity.setNum(MapUtils.getString(data, "NUM"));
    		entity.setContentId(MapUtils.getString(data, "CONTENT_ID"));
    		entity.setContent(MapUtils.getString(data, "CONTENT"));
    		entity.setMoney(MapUtils.getDouble(data, "MONEY"));
    		entity.setRemark(MapUtils.getString(data, "REMARK"));
    		entity.setParent(MapUtils.getString(data, "PARENT"));
    		entity.setAddChildFlag(MapUtils.getString(data, "ADD_CHILD_FLAG"));
    		entity.setVariableMoney(MapUtils.getDouble(data, "VARIABLE_MONEY"));
    		entity.setHistoryMoney(MapUtils.getDouble(data, "HISTORY_MONEY"));
    		entity.setUnit(MapUtils.getString(data, "UNIT"));
    		entity.setPrice(MapUtils.getDouble(data, "PRICE"));
    		entity.setAmount(MapUtils.getInteger(data, "AMOUNT"));
    		entity.settApprId(tPmProjectFundsAppr.getId());
    		tpFundsList.add(entity);
    	}
    	this.systemService.batchSave(tpFundsList);
    }
    /**
     * 年度预算新增
     * @param tPmProjectFundsAppr
     * @param req
     * @return
     */
    @RequestMapping(params = "goYearFundsUpdate")
    public ModelAndView goYearFundsUpdate(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest req) {
    	tPmProjectFundsAppr = tPmProjectFundsApprService.getEntity(TPmProjectFundsApprEntity.class,
                tPmProjectFundsAppr.getId());
	   	String apprId = tPmProjectFundsAppr.getId();
	   	String tpId = tPmProjectFundsAppr.getTpId();
	   	TPmProjectEntity tPmProject = tPmProjectFundsApprService.getEntity(TPmProjectEntity.class, tpId);
	   	
    	String isEdit = req.getParameter("isEdit");
    	if("2".equals(isEdit)) {
    		req.setAttribute("edit", false);
    	}else {
    		req.setAttribute("edit", true);
    	}
    	req.setAttribute("tPmProject", tPmProject);
        req.setAttribute("apprId", apprId);
        req.setAttribute("tpId", tpId);
        String totalFunds = req.getParameter("totalFunds");
        if(StringUtil.isEmpty(totalFunds)) {
        	totalFunds = tPmProjectFundsAppr.getAllFee() + "";
        }
        req.setAttribute("totalFunds", totalFunds);
        return new ModelAndView("com/kingtake/project/funds/tPmProjectYearFundsDetail");
    }
    
    /**
     * 提交年度预算
     * @param ids
     * @return
     */
    @RequestMapping(params = "submitYearFunds")
    @ResponseBody
    public AjaxJson submitYearFunds(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest req) {
    	AjaxJson j = new AjaxJson();
    	j.setSuccess(true);
        message = "年度预算提交成功";
        try {
	        String apprId = req.getParameter("apprId");
	        TPmProjectFundsApprEntity thisYearProjectFundsAppr = this.systemService.getEntity(TPmProjectFundsApprEntity.class, apprId);
	        thisYearProjectFundsAppr.setFinishFlag("0");//待提交审核
	        //首先更改当前年度预算记录的审核状态
	        this.systemService.updateEntitie(thisYearProjectFundsAppr);
	        String projectId = thisYearProjectFundsAppr.getTpId();
	        TPmProjectEntity project =  this.systemService.getEntity(TPmProjectEntity.class, projectId);
	        //如果是零基预算或者是计划类项目计划开支，则判断当前数据库有没有总预算记录，没有，则新增一条总预算（包括总预算明细）
	        if("5".equals(thisYearProjectFundsAppr.getFundsType()) || 
	        		("6".equals(thisYearProjectFundsAppr.getFundsType()) && ProjectConstant.PROJECT_PLAN.equals(project.getLxyj()))) {
	        	CriteriaQuery cq = new CriteriaQuery(TPmProjectFundsApprEntity.class);
	        	cq.eq("tpId", projectId);
	        	cq.eq("fundsCategory", "1");
	        	cq.add();
	        	List<TPmProjectFundsApprEntity> totalFundsList = this.systemService.getListByCriteriaQuery(cq, false);
	        	if(totalFundsList == null || totalFundsList.size() == 0) {//如果但却没有总预算，则自动生成总预算信息
	        		TPmProjectFundsApprEntity newTotalFundsAppr = new TPmProjectFundsApprEntity();
	        		newTotalFundsAppr.setProjectName(project.getProjectName());
	        		newTotalFundsAppr.setAllFee(project.getAllFee());
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	            	Date startYear = project.getBeginDate();
	            	if(startYear != null) {
	            		newTotalFundsAppr.setStartYear(sdf.format(startYear));
	            	}
	            	Date endYear = project.getEndDate();
	            	if(endYear != null) {
	            		newTotalFundsAppr.setEndYear(sdf.format(endYear));
	            	}
	            	// 获得项目组成员
	                List<TPmProjectMemberEntity> memberList = systemService.findByProperty(TPmProjectMemberEntity.class,
	                        "project.id", projectId);
	                List<String> idList = new ArrayList<String>();
	                List<String> nameList = new ArrayList<String>();
	                if (memberList.size() > 0) {
	                    for (TPmProjectMemberEntity member : memberList) {
	                        if (StringUtil.isNotEmpty(member.getId())) {
	                            idList.add(member.getId());
	                            nameList.add(member.getName());
	                        }
	                    }
	                }
	                newTotalFundsAppr.setMembersId(StringUtil.listTtoString(idList));
	                newTotalFundsAppr.setMembersName(StringUtil.listTtoString(nameList));
	        		newTotalFundsAppr.setTotalFunds(thisYearProjectFundsAppr.getTotalFunds());
	        		newTotalFundsAppr.setFundsType("1");
	        		newTotalFundsAppr.setFundsCategory("1");
	        		newTotalFundsAppr.setFinishFlag("0");
	        		newTotalFundsAppr.setTpId(projectId);
	        		newTotalFundsAppr.setFeeType(project.getFeeType().getId());
	        		newTotalFundsAppr.setProjectManagerName(project.getProjectManager());
	        		newTotalFundsAppr.setProjectManager(project.getProjectManagerId());
	        		newTotalFundsAppr.setDepartsId(project.getDevDepart() == null ? null : project.getDevDepart().getId());
	        		newTotalFundsAppr.setDepartsName(project.getDevDepart() == null ? null : project.getDevDepart().getDepartname());

	        		this.systemService.save(newTotalFundsAppr);
	        		cq = new CriteriaQuery(TPmContractFundsEntity.class);
	            	cq.eq("apprId", apprId);
	            	cq.add();
	        		List<TPmContractFundsEntity> yearContractFundsList = this.systemService.getListByCriteriaQuery(cq, false);
	        		int len = yearContractFundsList.size();
	        		Map<String, TPmContractFundsEntity> alreadySaveFunds = new HashMap<String, TPmContractFundsEntity>();
	        		for (int i = 0; i < len; i++) {
	        			TPmContractFundsEntity oneYearContractFunds = yearContractFundsList.get(i);
	        			if(oneYearContractFunds.getNum() != null && oneYearContractFunds.getNum().length() == 2) {//一级科目
	        				TPmContractFundsEntity newContractFundsEntity = new TPmContractFundsEntity();
	        				newContractFundsEntity.setTpId(newTotalFundsAppr.getId());
	        				newContractFundsEntity.setNum(oneYearContractFunds.getNum());
	        				newContractFundsEntity.setContentId(oneYearContractFunds.getContentId());
	        				newContractFundsEntity.setContent(oneYearContractFunds.getContent());
	        				newContractFundsEntity.setMoney(oneYearContractFunds.getMoney());
	        				newContractFundsEntity.setHistoryMoney(oneYearContractFunds.getHistoryMoney());
	        				newContractFundsEntity.setParent(null);
	        				newContractFundsEntity.setRemark(oneYearContractFunds.getRemark());
	        				this.systemService.save(newContractFundsEntity);
	        				alreadySaveFunds.put(oneYearContractFunds.getNum(), newContractFundsEntity);
	        			}
					}
	        		for (int i = 0; i < len; i++) {
	        			TPmContractFundsEntity oneYearContractFunds = yearContractFundsList.get(i);
	        			if(oneYearContractFunds.getNum() != null && oneYearContractFunds.getNum().length() == 4) {//二级科目
	        				String thisNo = oneYearContractFunds.getNum();
	        				thisNo = thisNo.substring(0, 2);
	        				if(alreadySaveFunds.get(thisNo) != null) {
	        					TPmContractFundsEntity newContractFundsEntity = new TPmContractFundsEntity();
	        					newContractFundsEntity.setTpId(newTotalFundsAppr.getId());
	        					newContractFundsEntity.setParent(alreadySaveFunds.get(thisNo).getId());
	        					newContractFundsEntity.setNum(oneYearContractFunds.getNum());
		        				newContractFundsEntity.setContentId(oneYearContractFunds.getContentId());
		        				newContractFundsEntity.setContent(oneYearContractFunds.getContent());
		        				newContractFundsEntity.setMoney(oneYearContractFunds.getMoney());
		        				newContractFundsEntity.setHistoryMoney(oneYearContractFunds.getHistoryMoney());
		        				newContractFundsEntity.setRemark(oneYearContractFunds.getRemark());
	        					this.systemService.save(newContractFundsEntity);
	        				}
	        			}
					}
	        	}
	        } 
        } catch (Exception e) {
            e.printStackTrace();
            message = "年度预算表提交失败";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    } 
    
    /**
     * 选择预算编制包
     * 
     * @return
     */
    @RequestMapping(params = "ysbzbList")
    public ModelAndView ysbzbList(TPmIncomeApplyEntity tPmIncomeApply, HttpServletRequest req) {
    	String projectId = req.getParameter("projectId");
    	String type = req.getParameter("type");
    	req.setAttribute("projectId", projectId);
    	req.setAttribute("type", type);
    	TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, projectId);
    	String planContractFlag = projectEntity.getPlanContractFlag();
    	if (ProjectConstant.PROJECT_PLAN.equals(planContractFlag)) {
    		req.setAttribute("stage", "ys");
    		return new ModelAndView("com/kingtake/project/incomeplan/incomePlanListForDepartment");
        } else {
        	req.setAttribute("stage", "ys");
        	return new ModelAndView("com/kingtake/project/incomeapply/tPmIncomeApplyList");
        }
//        return new ModelAndView("com/kingtake/project/funds/tPmProjectFundsYsbzb");
    }
    
    /**
     * 选择项目余额
     * 
     * @return
     */
    @RequestMapping(params = "balanceList")
    public ModelAndView balanceList(TPmProjectBalanceEntity tPmProjectBalance, HttpServletRequest req) {
    	String projectId = req.getParameter("projectId");
    	String type = req.getParameter("type");
    	req.setAttribute("projectId", projectId);
    	req.setAttribute("type", type);
    	TPmProjectEntity ProjectEntity = this.systemService.getEntity(TPmProjectEntity.class, projectId);
    	String planContractFlag = "0";
    	if(ProjectEntity != null){
    		planContractFlag = ProjectEntity.getPlanContractFlag();
    	}
    	if(ProjectConstant.PROJECT_PLAN.equals(planContractFlag)){
    		req.setAttribute("stage", "ys");
    		return new ModelAndView("com/kingtake/project/incomeplan/incomePlanListForDepartment");
    	}else{
    		return new ModelAndView("com/kingtake/project/funds/tPmProjectBalance");
    	}        
    }
    
    @RequestMapping(params = "ysbzbDatagrid")
    public void ysbzbDatagrid(TPmIncomeEntity tPmIncome, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
    	String projectId = request.getParameter("projectId");
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class);
        cq.eq("projectId", projectId);
        cq.isNull("fundsFlag");
        cq.eq("auditStatus", "2");
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmIncomeApplyEntity> incomeApplyList = this.systemService.getListByCriteriaQuery(cq, false);
//        List<TPmIncomeEntity> incomeList = new ArrayList<TPmIncomeEntity>();
        List<Map<String,Object>> incomeMapList = new ArrayList<Map<String,Object>>(); 
        for(TPmIncomeApplyEntity tmp:incomeApplyList){
            TPmIncomeEntity incomeQuery = this.systemService.get(TPmIncomeEntity.class, tmp.getIncomeId());
//            incomeList.add(incomeQuery);
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("incomeTime", incomeQuery.getIncomeTime());
            dataMap.put("certificate", incomeQuery.getCertificate());
            dataMap.put("digest", incomeQuery.getDigest());
            dataMap.put("incomeApplyId", tmp.getId());
            incomeMapList.add(dataMap);
        }
        dataGrid.setResults(incomeMapList);
        TagUtil.datagrid(response, dataGrid);
    }
    
    
    /**
     * 获取计划合同文号 
     * @param project
     * @return
     */
    private String getBillNum(TPmProjectEntity project){
        String billNum = "";
        if(ProjectConstant.PROJECT_CONTRACT.equals(project.getPlanContractFlag())){//如果是合同类项目，则取进账合同编号
            List<TPmIncomeContractApprEntity> incomeApprList = this.systemService.findByProperty(TPmIncomeContractApprEntity.class, "project.id", project.getId());
            if(incomeApprList.size()>0){
            TPmIncomeContractApprEntity incomeAppr = incomeApprList.get(0);
            billNum = incomeAppr.getContractCode();
            }
        }else{//如果是计划类项目，则取项目基本信息计划合同文号
            billNum = project.getPlanContractRefNo();
        }
        return billNum;
    }

    /**
     * 进入预算页面
     * 
     * @param req
     * @return
     */
    @RequestMapping(params = "goFundsTab")
    public ModelAndView goFundsTab(HttpServletRequest req) {
        String apprId = req.getParameter("tpId");
        req.setAttribute("tpId", apprId);
        
        TPmProjectFundsApprEntity entity = this.systemService.get(TPmProjectFundsApprEntity.class, apprId);
        req.setAttribute("finishFlag", entity.getFinishFlag());

        String planContractFlag = req.getParameter("planContractFlag");
        req.setAttribute("planContractFlag", planContractFlag);

        String changeFlag = req.getParameter("changeFlag");
        req.setAttribute("changeFlag", changeFlag);

        req.setAttribute("edit", req.getParameter("edit"));

        req.setAttribute("PROJECT_CONTRACT", ProjectConstant.PROJECT_CONTRACT);
        req.setAttribute("PROJECT_PLAN", ProjectConstant.PROJECT_PLAN);

        TPmProjectFundsApprEntity projectFunds =  this.systemService.get(TPmProjectFundsApprEntity.class, apprId);
        if(projectFunds!=null){//判断是否存在减免
            CriteriaQuery cq = new CriteriaQuery(TPmAbateEntity.class);
            cq.eq("projectId", projectFunds.getTpId());
            cq.add();
            List<TPmAbateEntity> abateList = this.systemService.getListByCriteriaQuery(cq, false);
            if(abateList.size()>0){
                req.setAttribute("abateId", abateList.get(0).getId());
            }
            req.setAttribute("projectId", projectFunds.getTpId());
        }
        return new ModelAndView("com/kingtake/project/funds/tPmFundsTab");
    }

    /**
     * 获得预算的总条数、未完成审核的条数
     * 
     * @return
     */
    @RequestMapping(params = "getCount")
    @ResponseBody
    public void getCount(HttpServletRequest request, HttpServletResponse response) {
        List<TPmProjectFundsApprEntity> list = systemService.getSession()
                .createCriteria(TPmProjectFundsApprEntity.class)
                .add(Restrictions.eq("tpId", request.getParameter("projectId"))).list();

        Map<String, Integer> result = new HashMap<String, Integer>();
        int noFinish = 0;
        int finish = 0;
        for (int i = 0; i < list.size(); i++) {
            String finishFlag = list.get(i).getFinishFlag();
            if (ApprovalConstant.APPRSTATUS_FINISH.equals(finishFlag)) {
                ++finish;
            }

            if (!ApprovalConstant.APPRSTATUS_FINISH.equals(finishFlag)
                    && !ApprovalConstant.APPRSTATUS_REBUT.equals(finishFlag)) {
                ++noFinish;
            }
        }
        result.put("noFinish", noFinish);
        result.put("finish", finish);

        TagUtil.response(response, JSONHelper.map2json(result));
    }

    /**
     * 获得一个项目的预算表的总数，合同状态为已完成
     * 
     * @param tPmProjectFundsAppr
     * @param request
     * @param response
     */
    @RequestMapping(params = "getCounts")
    @ResponseBody
    public AjaxJson getCounts(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        j.setSuccess(true);

        // 获得之前的预算变更
        List<TPmProjectFundsApprEntity> changes = systemService.getSession()
                .createCriteria(TPmProjectFundsApprEntity.class)
                .add(Restrictions.eq("project.id", request.getParameter("projectId")))
                .add(Restrictions.eq("changeFlag", SrmipConstants.YES)).addOrder(Order.desc("finishTime")).list();

        if (changes != null && changes.size() > 0) {
            TPmProjectFundsApprEntity lastChange = changes.get(0);
            /** 判断最近一次预算变更的完成状态 */

            // 未完成--暂时无法做预算变更
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(lastChange.getFinishFlag())
                    || ApprovalConstant.APPRSTATUS_SEND.equals(lastChange.getFinishFlag())) {
                j.setSuccess(false);
                j.setMsg("上次预算变更审核未完成，暂时无法做预算变更");

                // 已完成
            } else if (ApprovalConstant.APPRSTATUS_FINISH.equals(lastChange.getFinishFlag())) {
                // 判断该预算变更创建开始到当前，是否有新的预算审核完成
                Object count = systemService.getSession().createCriteria(TPmProjectFundsApprEntity.class)
                        .add(Restrictions.eq("project.id", request.getParameter("projectId")))
                        .add(Restrictions.eq("finishFlag", ApprovalConstant.APPRSTATUS_FINISH))
                        .add(Restrictions.gt("createDate", lastChange.getCreateDate()))
                        .setProjection(Projections.count("id")).uniqueResult();
                if (Integer.parseInt(count.toString()) <= 0) {
                    j.setSuccess(false);
                    j.setMsg("从上次预算变更到现在，没有新的预算审核完成，无法做预算变更");
                }
            }

            // 之前没有做过预算变更
        } else {
            // 判断是否有预算审核完成
            Object count = systemService.getSession().createCriteria(TPmProjectFundsApprEntity.class)
                    .add(Restrictions.eq("project.id", request.getParameter("projectId")))
                    .add(Restrictions.eq("finishFlag", ApprovalConstant.APPRSTATUS_FINISH))
                    .setProjection(Projections.count("id")).uniqueResult();
            if (Integer.parseInt(count.toString()) <= 0) {
                j.setSuccess(false);
                j.setMsg("没有预算审核完成，无法做预算变更");
            }
        }

        return j;
    }

    /**
     * 导入功能跳转
     * 
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("tpId", req.getParameter("tpId"));
        req.setAttribute("flag", req.getParameter("flag"));
        return new ModelAndView("com/kingtake/project/funds/tPmFunds-upload");
    }

    /**
     * 导出excel
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TPmProjectFundsApprEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectFundsAppr,
                request.getParameterMap());
        List<TPmProjectFundsApprEntity> tPmProjectFundsApprs = this.tPmProjectFundsApprService.getListByCriteriaQuery(
                cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "项目预算管理");
        modelMap.put(NormalExcelConstants.CLASS, TPmProjectFundsApprEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("项目预算管理列表", "导出人:"
                + ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tPmProjectFundsApprs);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public void exportXlsByT(TPmProjectFundsApprEntity tPmProjectFundsAppr, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        String tpId = request.getParameter("tpId");
        String planContractFlag = request.getParameter("planContractFlag");
        try {
            Workbook wb = this.tPmProjectFundsApprService.getFundsTemplate(tpId, planContractFlag);
            String fileName = "计划类预算导入模板.xls";
            if (ProjectConstant.PROJECT_CONTRACT.equals(planContractFlag)) {
                fileName = "合同类预算导入模板.xls";
            }
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
                List<TPmProjectFundsApprEntity> listTPmProjectFundsApprEntitys = ExcelImportUtil.importExcelByIs(
                        file.getInputStream(), TPmProjectFundsApprEntity.class, params);
                for (TPmProjectFundsApprEntity tPmProjectFundsAppr : listTPmProjectFundsApprEntitys) {
                    tPmProjectFundsApprService.save(tPmProjectFundsAppr);
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

    @RequestMapping(params = "selectIncomeInfo")
    public ModelAndView selectIncomeInfo(HttpServletRequest request) {
        String projectId = request.getParameter("projectId");
        if(StringUtils.isNotEmpty(projectId)){
            request.setAttribute("projectId", projectId);
        }
        return new ModelAndView("com/kingtake/project/funds/selectIncomeList");
    }

    @RequestMapping(params = "datagridIncomeInfo")
    public void datagridIncomeInfo(TPmIncomeEntity tPmIncome, HttpServletRequest request,
            HttpServletResponse response, DataGrid dataGrid) {
        String projectId = request.getParameter("projectId");
        CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class);
        cq.eq("projectId", projectId);
        cq.isNull("fundsFlag");
        cq.eq("auditStatus", "2");
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        List<TPmIncomeApplyEntity> incomeApplyList = this.systemService.getListByCriteriaQuery(cq, false);
//        List<TPmIncomeEntity> incomeList = new ArrayList<TPmIncomeEntity>();
        List<Map<String,Object>> incomeMapList = new ArrayList<Map<String,Object>>(); 
        for(TPmIncomeApplyEntity tmp:incomeApplyList){
            TPmIncomeEntity incomeQuery = this.systemService.get(TPmIncomeEntity.class, tmp.getIncomeId());
//            incomeList.add(incomeQuery);
            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("incomeTime", incomeQuery.getIncomeTime());
            dataMap.put("certificate", incomeQuery.getCertificate());
            dataMap.put("digest", incomeQuery.getDigest());
            dataMap.put("incomeApplyId", tmp.getId());
            incomeMapList.add(dataMap);
        }
        dataGrid.setResults(incomeMapList);
        TagUtil.datagrid(response, dataGrid);
    }
    
    /**
     * 判断是否能录入
     * @param tPmProjectFundsAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "checkFunds")
    @ResponseBody
    public AjaxJson checkFunds(HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("id");
        TPmProjectEntity project = this.systemService.get(TPmProjectEntity.class, projectId);
        if(StringUtils.isEmpty(project.getAccountingCode())){
            j.setMsg("请在项目基本信息中完善会计编码后，再进行录入预算操作！");
            j.setSuccess(false);
            return j;
        }
        if(project.getIncomeApplyFlag()!=null&&"1".equals(project.getIncomeApplyFlag())){
            CriteriaQuery cq = new CriteriaQuery(TPmIncomeApplyEntity.class);
            cq.eq("projectId", project.getId());
            cq.eq("auditStatus", "2");//已通过
            cq.add();
            List<TPmIncomeApplyEntity> applyList = this.systemService.getListByCriteriaQuery(cq, false);
            if(applyList.size()==0){
                j.setMsg("必须先认款，才能做预算！");
                j.setSuccess(false);
                return j;
            }
        }
        return j;
    }
    
    /**
     * 判断是否能录入
     * @param tPmProjectFundsAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "checkBudget")
    @ResponseBody
    public Map checkBudget(HttpServletRequest request,
            HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        return this.tPmProjectFundsApprService.checkBudget(projectId);
    }
    
    
    /**
     * 获取项目的零基预算状态
     * @param tPmProjectFundsAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "getIsCanApplyYearFunds")
    @ResponseBody
    public AjaxJson getIsCanApplyYearFunds(HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        CriteriaQuery cq = new CriteriaQuery(TPmProjectFundsApprEntity.class);
        cq.eq("tpId", projectId);
        cq.eq("fundsCategory", "2");//预算类别为年度预算
        Object[] flags = new Object[3];
        flags[0] = "0";
        flags[1] = "1";
        cq.in("finishFlag", flags);//状态待编辑，待提交审核或审核中
        cq.add();
        List<TPmProjectFundsApprEntity> totalFundsApp = this.systemService.getListByCriteriaQuery(cq, false);
        if(totalFundsApp != null && totalFundsApp.size() > 0){
        	j.setSuccess(false);
        	j.setMsg("当前项目下还有待发送或者审核中的年度预算，请在上次年度预算审批完成后再次发起！");
        }else{
        	j.setSuccess(true);  
        }        
        return j;
    }
    
    
    /**
     * 获取项目的零基预算状态
     * @param tPmProjectFundsAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "getProjectId")
    @ResponseBody
    public AjaxJson getProjectId(HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        TPmProjectEntity projectEntity = this.systemService.getEntity(TPmProjectEntity.class, projectId);
        String planContractFlag = projectEntity.getPlanContractFlag();
        if(ProjectConstant.PROJECT_PLAN.equals(planContractFlag)){
        	j.setMsg("5");
        }else{
        	List<Map> projectList = this.tPmProjectFundsApprService.getProjectId(projectId);
            if(projectList.size()>0){
            	String ljys = "0";
            	if(projectList.get(0).get("TZYS_STATUS") != null){
            		ljys = projectList.get(0).get("TZYS_STATUS").toString();
            	}            	 
            	j.setMsg(ljys);
                j.setSuccess(true);
                return j;
            }
        }        
        return j;
    }
    
    /**
     * 修改零基预算状态
     * @param tPmProjectFundsAppr
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "editProjectLjysStatus")
    @ResponseBody
    public AjaxJson editProjectLjysStatus(HttpServletRequest request,
            HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String projectId = request.getParameter("projectId");
        int count = this.tPmProjectFundsApprService.editProjectLjysStatus(projectId,1);
        if(count>0){
        	j.setMsg("已提交申请！");
            j.setSuccess(true);
            return j;
        }
        return j;
    }
    
    /**
	 * easyui 预算编织包列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "ysbzGrid")
	public void ysbzGrid(TPmProjectFundsBagEntity tPmProjectFundsBagEntity,HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
	    CriteriaQuery cq = new CriteriaQuery(TPmProjectFundsBagEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectFundsBagEntity);
		this.tPmProjectFundsApprService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui 项目余额明细表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "balanceGrid")
	public void balanceGrid(TPmProjectBalanceEntity tPmProjectBalanceEntity,HttpServletRequest request, 
			HttpServletResponse response, DataGrid dataGrid) {
	    CriteriaQuery cq = new CriteriaQuery(TPmProjectBalanceEntity.class, dataGrid);
	    String projectId = request.getParameter("projectId");
	    TPmProjectEntity tPmProjectEntity = this.systemService.get(TPmProjectEntity.class, projectId);
	    if(tPmProjectEntity != null){
	    	cq.eq("projectNo", tPmProjectEntity.getProjectNo());
	    }
	    cq.eq("ysStatus", "0");//未做预算的
        cq.add();
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPmProjectBalanceEntity);
		this.tPmProjectFundsApprService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**项目预算发送审核*/
	@RequestMapping(params = "submitFundsAppr")
	@ResponseBody
	public AjaxJson submitFundsAppr(HttpServletRequest request) {
		 AjaxJson j = new AjaxJson();
		 String msg = "操作成功";
		  
		 String total_funds_id = request.getParameter("total_funds_id");
		 total_funds_id = StringUtils.isEmpty(total_funds_id)?"":total_funds_id;
		 String total_funds = request.getParameter("total_funds");
		 String year_funds_id = request.getParameter("year_funds_id");
		 year_funds_id = StringUtils.isEmpty(year_funds_id)?"":year_funds_id;
		 String year_funds = request.getParameter("year_funds");
		 String funds_appr_Id = total_funds_id+","+year_funds_id;
		 String recive_userIds = request.getParameter("recive_userIds");
		 String recive_userNames = request.getParameter("recive_userNames");
		 String submit_msg = request.getParameter("submit_msg");
		 String t_p_id = request.getParameter("t_p_id");
		 String id = java.util.UUID.randomUUID().toString().replaceAll("-", "");
		 String userId = ResourceUtil.getSessionUserName().getId();
		 String userName = ResourceUtil.getSessionUserName().getRealName();
		 
		 try {
			 //--发送审核插入
			 String sql ="insert into t_pm_project_funds_appr_audit"
			 		+ "	(id, t_p_id, funds_appr_Id, total_funds, year_funds, recive_userIds, recive_userNames, "
			 		+ "	submit_userId, submit_userName, submit_msg, submit_time, audit_status)"
			 		+ " values (?,?,?,?,?,?,?,?,?,?,sysdate,'1')";
			 
			 this.systemService.executeSql(sql, id,t_p_id,funds_appr_Id,total_funds,year_funds,recive_userIds,recive_userNames,userId,userName,submit_msg);
			 
			 //--发送审核时改预算表审核状态
			 funds_appr_Id = funds_appr_Id.startsWith(",") ? funds_appr_Id.substring(1) : funds_appr_Id;
			 funds_appr_Id = funds_appr_Id.endsWith(",") ? funds_appr_Id.replace(",","") : funds_appr_Id; 
			 funds_appr_Id = "'"+funds_appr_Id.replace(",", "','")+"'";
			 sql = "update t_pm_project_funds_appr set finish_flag='1'  where ID in("+funds_appr_Id+")";
			 this.systemService.executeSql(sql);
		 }catch(Exception ex) {
			 ex.printStackTrace();
			 msg = "操作失败";
		 }
		 
		 j.setMsg(msg);
		 return j;
	}
	
	/**项目预算审核*/
	@RequestMapping(params = "auditFundsAppr")
	@ResponseBody
	public AjaxJson auditFundsAppr(HttpServletRequest request) {
		 AjaxJson j = new AjaxJson();
		 String msg = "操作成功";
		 
		 String userId = ResourceUtil.getSessionUserName().getId();
		 String userName = ResourceUtil.getSessionUserName().getRealName();
		 String audit_msg = request.getParameter("audit_msg");
		 String audit_status = request.getParameter("audit_status");
		 String id =  request.getParameter("id");
		 String funds_appr_Id = request.getParameter("funds_appr_Id");
		 
		 try { 
			 //--发送审核插入
			 String sql = " update t_pm_project_funds_appr_audit set audit_userId = ?, audit_userName=?, audit_time=sysdate, audit_msg=?, audit_status=?  where ID = ?";
			 this.systemService.executeSql(sql,userId,userName,audit_msg,audit_status,id);
			 
			 //--发送审核时改预算表审核状态
			 funds_appr_Id = funds_appr_Id.startsWith(",") ? funds_appr_Id.substring(1) : funds_appr_Id;
			 funds_appr_Id = funds_appr_Id.endsWith(",") ? funds_appr_Id.replace(",","") : funds_appr_Id; 
			 funds_appr_Id = "'"+funds_appr_Id.replace(",", "','")+"'";
			 sql = " update t_pm_project_funds_appr set finish_flag=?  where ID in ("+funds_appr_Id+")";
			 this.systemService.executeSql(sql, audit_status);
			 
		 }catch(Exception ex) {
			 ex.printStackTrace();
			 msg = "操作失败";
		 }
				 
		 j.setMsg(msg);
		 return j;
	}
}



