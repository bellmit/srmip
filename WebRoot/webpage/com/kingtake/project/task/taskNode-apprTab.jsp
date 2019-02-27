<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审批已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
		var parent = W.$.dialog.list['processDialog'].content;
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}
});
</script>
<t:tabs id="addContractInfo" iframe="false" tabPosition="top">
	<!-- 审批信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${apprId}&apprType=${apprType}&send=${send}" 
			icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
	
	<!-- 任务节点完成情况录入 -->
	<t:tab href="tPmTaskController.do?goTaskNodeApprEdit&id=${apprId}" icon="icon-search"
		title="任务节点考核" id="payApplyInfo" ></t:tab>
		
	<!-- 审批信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=${apprType}&send=${send}" 
			icon="icon-search" title="审批记录表" id="apprLogs2"></t:tab>
</t:tabs>
