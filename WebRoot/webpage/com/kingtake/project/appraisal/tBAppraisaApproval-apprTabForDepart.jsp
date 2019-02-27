<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审批已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
		var parent = frameElement.api.opener;
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}
});
</script>
<t:tabs id="tt" iframe="false" tabPosition="top" >
	<!-- 申请审批信息 -->
	<t:tab href="tBAppraisaApprovalController.do?goApproval&role=depart&appraisalProject.id=${appraisalProjectId}&id=${apprId}&send=${send}&idFlag=${idFlag}"
		icon="icon-search" title="鉴定申请审批表" id="appr"></t:tab>
	
	<!-- 审批信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=${apprType}&send=false" 
			icon="icon-search" title="审批记录表" id="apprLogs2"></t:tab>
</t:tabs>