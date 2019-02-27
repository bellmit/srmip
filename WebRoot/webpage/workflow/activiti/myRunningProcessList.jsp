<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="myRunningProcessList" title="我发起的流程" autoLoadData="true" actionUrl="processInstanceController.do?myRunningProcessListDataGrid" sortName="userName" fitColumns="true"
	idField="id" fit="true" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="流程名称" sortable="false" field="prcocessDefinitionName"></t:dgCol>
	<t:dgCol title="业务名称" sortable="false" field="businessName"></t:dgCol>
	<t:dgCol title="流程定义ID" sortable="false" field="processDefinitionId" hidden="true"></t:dgCol>
	<t:dgCol title="流程实例ID" field="processInstanceId" hidden="true"></t:dgCol>
	<t:dgCol title="发起人" field="startUserId" ></t:dgCol>
	<t:dgCol title="开始时间" field="starttime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
	<t:dgCol title="结束时间" field="endtime" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
	<t:dgCol title="耗时" field="spendTimes" ></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgFunOpt exp="endtime#eq#" funname="invalidProcess(processInstanceId,isSuspended)" title="作废流程"></t:dgFunOpt>
	<t:dgFunOpt exp="endtime#eq#" funname="callBackProcess(processInstanceId,isSuspended)" title="流程追回"></t:dgFunOpt>
	<%-- <t:dgFunOpt funname="viewHistory(processInstanceId)" title="历史"></t:dgFunOpt>--%>
	<t:dgFunOpt funname="goProcessHisTab(processInstanceId,prcocessDefinitionName)" title="历史"></t:dgFunOpt>
</t:datagrid>
<script type="text/javascript">
	//作废流程
	function invalidProcess(processInstanceId,isSuspended){
	    dialogConfirm('processInstanceController.do?invalidProcess&processInstanceId='+processInstanceId,'确定作废吗？','myRunningProcessList');
	}
	
	//流程追回
	function callBackProcess(processInstanceId,isSuspended){
	    dialogConfirm('processInstanceController.do?callBackProcess&processInstanceId='+processInstanceId,'确定追回吗？','myRunningProcessList');
	}
	
	//查看流程历史
	function viewHistory(processInstanceId){
		var url = "";
		var title = "流程历史";
		url = "activitiController.do?viewProcessInstanceHistory&processInstanceId="+processInstanceId+"&isIframe"
		addOneTab(title, url);
	}
</script>