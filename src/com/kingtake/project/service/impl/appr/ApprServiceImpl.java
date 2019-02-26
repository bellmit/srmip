package com.kingtake.project.service.impl.appr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;
import com.kingtake.project.service.appr.ApprServiceI;

@Service("apprService")
@Transactional
public class ApprServiceImpl extends CommonServiceImpl implements ApprServiceI {

	@Override
	public ValidForm validformCode(String tableName, String codeField,
			String id, String codeValue) {
		ValidForm v = new ValidForm();

        if (StringUtil.isNotEmpty(codeValue)) {
            String sql = "select id from " + tableName + " where " + codeField + " = ?";
            if (StringUtil.isNotEmpty(id)) {
                sql += " and id != '" + id + "'";
            }
            List<Map<String, Object>> results = this.findForJdbc(sql, codeValue);
            if (results != null && results.size() > 0) {
                v.setStatus("n");
                v.setInfo("合同编号已被使用，请重新输入！");
            }
        }

        return v;
	}

	@Override
	public List<Map<String, Object>> getSendButtons(String apprType, String roleType) {
		//获得审批小类以及是否有按钮权限
		String apprTypeSql = "SELECT ID, LABEL, HANDLER_TYPE, "
				+ "CASE WHEN instr( (SELECT ROLE_JURISDICTION FROM T_PM_FORM_ROLE WHERE FORM_GATEGORY = ? AND ROLE = ?), "
				+ "ID ) > 0  THEN '1' ELSE '0' END AS BUTTONFLAG FROM T_PM_FORM_CATEGORY ORDER BY HANDLER_TYPE,ID";
		List<Map<String, Object>> apprChildTypes = this.findForJdbc(apprTypeSql, apprType, roleType);
		
		return apprChildTypes;
	}
	
	@Override
	public List<Map<String, Object>> getApprLogsAndSendButtons(String apprId, String apprType, String roleType) {
		List<Map<String, Object>> apprTypesSuggest = new ArrayList<>();
		
		//获得审批小类以及是否有按钮权限
		String apprTypeSql = "SELECT ID, LABEL, HANDLER_TYPE, "
				+ "CASE WHEN instr( (SELECT ROLE_JURISDICTION FROM T_PM_FORM_ROLE WHERE FORM_GATEGORY = ? AND ROLE = ?), "
				+ "ID ) > 0  THEN '1' ELSE '0' END AS BUTTONFLAG "
				+ "FROM T_PM_FORM_CATEGORY WHERE SUBSTR(ID, 0, 2) = ? ORDER BY HANDLER_TYPE,ID";
		List<Map<String, Object>> apprChildTypes = this.findForJdbc(apprTypeSql, apprType, roleType, apprType);
		
		for(Map<String, Object> apprChildType : apprChildTypes){
			/*if(ApprovalConstant.HANDLER_TYPE_HANDLER.equals(apprChildType.get("HANDLER_TYPE"))){*/
				CriteriaQuery cq = new CriteriaQuery(TPmApprSendLogsEntity.class);
				cq.eq("apprId", apprId);
				cq.eq("suggestionType", apprChildType.get("ID"));
				cq.add();
				List<TPmApprSendLogsEntity> tPmApprSendLogs = this.getListByCriteriaQuery(cq, false);
				
				//找到有效的记录
				List<Map<String, Object>> apprSendLogs = new ArrayList<Map<String, Object>>();
				int count = 0;
				if(tPmApprSendLogs!=null && tPmApprSendLogs.size()>0){
					for(TPmApprSendLogsEntity tPmApprSendLog : tPmApprSendLogs){
						Map<String, Object> apprSendLog = new HashMap<String, Object>();
						
						CriteriaQuery cq2 = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
						cq2.eq("apprSendLog.id", tPmApprSendLog.getId());
						/*//无效已处理
						cq2.eq("validFlag", SrmipConstants.NO);
						cq2.eq("operateStatus", SrmipConstants.YES);*/
						//有效
						cq2.eq("validFlag", SrmipConstants.YES);
						/*cq2.add(cq2.and(cq2.or(cq2.and(cq2, 1, 2), cq2, 3), cq2, 0));*/
						cq2.add();
						List<TPmApprReceiveLogsEntity> apprReceiveLogs = this.getListByCriteriaQuery(cq2, false);
						
						if(apprReceiveLogs!=null && apprReceiveLogs.size()>0){
							apprSendLog.put("operateUsername", tPmApprSendLog.getOperateUsername());
							apprSendLog.put("operateDate", tPmApprSendLog.getOperateDate());
							apprSendLog.put("apprReceiveLogs", apprReceiveLogs);
							apprSendLog.put("receiveCount", apprReceiveLogs.size());
							
							apprSendLogs.add(apprSendLog);
							count += apprReceiveLogs.size();
						}
					}
				}
				apprChildType.put("apprSendLogs", apprSendLogs);
				apprChildType.put("count", (count + 1));
			/*}else{
				CriteriaQuery cq3 = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
				cq3.eq("apprId", apprId);
				cq3.eq("suggestionType", apprChildType.get("ID"));
				cq3.eq("validFlag", SrmipConstants.YES);
				cq3.add();
				List<TPmApprReceiveLogsEntity> apprReceiveLogs = this.getListByCriteriaQuery(cq3, false);
				if(apprReceiveLogs!=null && apprReceiveLogs.size()>0){
					apprChildType.put("apprReceiveLog", apprReceiveLogs.get(0));
				}
			}*/
			apprTypesSuggest.add(apprChildType);
		}
		
		return apprTypesSuggest;
	}
	
	@Override
	public List<Map<String, Object>> getApprLogs(String apprId, String apprType) {
		List<Map<String, Object>> apprTypesSuggest = new ArrayList<>();
		
		//获得审批小类以及是否有按钮权限
		String apprTypeSql = "SELECT ID, LABEL, HANDLER_TYPE, FIELD_LABEL FROM T_PM_FORM_CATEGORY "
				+ "WHERE SUBSTR(ID, 0, 2) = ? ORDER BY ID";
		List<Map<String, Object>> apprChildTypes = this.findForJdbc(apprTypeSql, apprType);
		if(StringUtil.isNotEmpty(apprId)){
			for(Map<String, Object> apprChildType : apprChildTypes){
				CriteriaQuery cq = new CriteriaQuery(TPmApprSendLogsEntity.class);
				cq.eq("apprId", apprId);
				cq.eq("suggestionType", apprChildType.get("ID"));
				cq.add();
				cq.addOrder("operateDate", SortDirection.asc);
				List<TPmApprSendLogsEntity> tPmApprSendLogs = this.getListByCriteriaQuery(cq, false);
				
				//找到有效的记录
				List<Map<String, Object>> apprSendLogs = new ArrayList<Map<String, Object>>();
				int count = 0;
				if(tPmApprSendLogs!=null && tPmApprSendLogs.size()>0){
					for(TPmApprSendLogsEntity tPmApprSendLog : tPmApprSendLogs){
						Map<String, Object> apprSendLog = new HashMap<String, Object>();
						
						CriteriaQuery cq2 = new CriteriaQuery(TPmApprReceiveLogsEntity.class);
						cq2.eq("apprSendLog.id", tPmApprSendLog.getId());
						cq2.eq("validFlag", SrmipConstants.YES);//有效
						cq.addOrder("operateTime", SortDirection.asc);
						cq2.add();
						List<TPmApprReceiveLogsEntity> apprReceiveLogs = this.getListByCriteriaQuery(cq2, false);
						
						if(apprReceiveLogs!=null && apprReceiveLogs.size()>0){
							apprSendLog.put("operateUsername", tPmApprSendLog.getOperateUsername());
							apprSendLog.put("operateDate", tPmApprSendLog.getOperateDate());
							apprSendLog.put("apprReceiveLogs", apprReceiveLogs);
							apprSendLog.put("receiveCount", apprReceiveLogs.size());
							
							apprSendLogs.add(apprSendLog);
							count += apprReceiveLogs.size();
						}
					}
				}
				apprChildType.put("apprSendLogs", apprSendLogs);
				apprChildType.put("count", (count + 1));
				apprTypesSuggest.add(apprChildType);
			}
			
			return apprTypesSuggest;
		}else{
			return apprChildTypes;
		}
	}

	@Override
	public String addBackLog(TPmApprReceiveLogsEntity backLog, String type) {
		String tipMsg = "";
		if(backLog != null && StringUtil.isNotEmpty(backLog.getId())){
			tipMsg = "因"+backLog.getReceiveUsername()
					+ DateUtils.date2Str(backLog.getOperateTime(), DateUtils.datetimeFormat)
					+ "驳回"+type+"审批"
					+ "（驳回理由："+backLog.getSuggestionContent()+"），" + type +"需重新审批";
		}
		return tipMsg;
	}
	
	@Override
	public Map<String, Object> selectApprTypeInfo(String apprType) {
		Map<String, Object> apprTypeInfo = new HashMap<String, Object>();
		if(StringUtil.isNotEmpty(apprType)){
			String sql = "SELECT ID, LABEL, HANDLER_TYPE, REBUT_FLAG FROM T_PM_FORM_CATEGORY WHERE ID = ?";
			apprTypeInfo = this.findOneForJdbc(sql, apprType);
		}
		return apprTypeInfo;
	}
	
	@Override
	public List<Map<String, Object>> selectApprTypeInfos(String apprType) {
		List<Map<String, Object>> apprTypeInfos = new ArrayList<Map<String, Object>>();
		if(StringUtil.isNotEmpty(apprType)){
			String sql = "SELECT ID, LABEL, HANDLER_TYPE, REBUT_FLAG FROM T_PM_FORM_CATEGORY WHERE SUBSTR(ID, 0, 2) = ?";
			apprTypeInfos = this.findForJdbc(sql, apprType);
		}
		return apprTypeInfos;
	}
	
}