package com.kingtake.project.service.appr;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.apprlog.TPmApprReceiveLogsEntity;
import com.kingtake.project.entity.apprlog.TPmApprSendLogsEntity;

public interface ApprServiceI extends CommonService{
	
	/**
	 * 验证编号是否重复
	 * @param id
	 * @param contractCode
	 * @param tableName
	 * @return
	 */
 	public ValidForm validformCode(String tableName, String codeField, String id, String codeValue);
 	
 	/**
 	 * 新增页面：获得审批类型和审批类型的按钮权限（新增为发起人）
 	 * @param apprType
 	 * @param roleType
 	 * @return
 	 */
 	public List<Map<String, Object>> getSendButtons(String apprType, String roleType);
 	
 	/**
 	 * 更新页面：获得审批类型和审批类型的按钮权限（新增为发起人）
 	 * @param apprId
 	 * @param apprType
 	 * @param roleType
 	 * @return
 	 */
 	public List<Map<String, Object>> getApprLogsAndSendButtons(String apprId, String apprType, String roleType);
 	
 	public List<Map<String, Object>> getApprLogs(String apprId, String apprType);
 	
 	/**
 	 * 发送审批时如有驳回记录，返回驳回消息
 	 * @param backLog
 	 * @param type
 	 * @return
 	 */
 	public String addBackLog(TPmApprReceiveLogsEntity backLog,  String type);
 	
 	/**
 	 * 根据审批类型获取审批信息
 	 * @param apprType
 	 * @return
 	 */
 	public Map<String, Object> selectApprTypeInfo(String apprType);
 	
 	/**
 	 * 获取所有审批类型
 	 * @param apprType
 	 * @param apprTypeId
 	 * @return
 	 */
 	public List<Map<String, Object>> selectApprTypeInfos(String apprType);
 	
}
