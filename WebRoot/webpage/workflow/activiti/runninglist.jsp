<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="runningList" title="流程实例管理" autoLoadData="true" actionUrl="processInstanceController.do?runningProcessDataGrid" sortName="userName" fitColumns="false"
	idField="id" fit="true" queryMode="group">
	<t:dgCol title="编号" field="id"  hidden="true"></t:dgCol>
	<t:dgCol title="流程名称" sortable="false" field="prcocessDefinitionName" treefield="text" width="100"></t:dgCol>
	<t:dgCol title="流程定义ID" sortable="false" field="processDefinitionId" treefield="processDefinitionId" hidden="true" width="120"></t:dgCol>
	<t:dgCol title="流程实例ID" field="processInstanceId" query="true" treefield="processInstanceId" width="120"></t:dgCol>
	<t:dgCol title="发起人" field="startUserId" treefield="startUserId" query="true" width="120"></t:dgCol>
	<t:dgCol title="当前任务名称" field="activityName" treefield="activityName" width="120"></t:dgCol>
	<t:dgCol title="当前任务处理人" field="activityUser" treefield="activityUser" width="120"></t:dgCol>
	<t:dgCol title="开始时间" field="starttime" treefield="starttime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150"></t:dgCol>
	<t:dgCol title="结束时间" field="endtime" treefield="endtime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="耗时" field="spendTimes" treefield="spendTimes" width="120"></t:dgCol>
	<t:dgCol title="任务ID" field="taskId" treefield="taskId" hidden="true" ></t:dgCol>
	<t:dgCol title="状态" sortable="false" field="isSuspended" treefield="isSuspended" replace="已结束_finished,启动_false,暂停_true" style="background:red;_true" width="60"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="200"></t:dgCol>
	<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#true&&isSuspended#ne#finished" funname="suspendProcessInstance(processInstanceId,isSuspended)" title="暂停"></t:dgFunOpt>
	<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#false&&isSuspended#ne#finished" funname="startProcessInstance(processInstanceId,isSuspended)" title="启动"></t:dgFunOpt>
	<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#finished" funname="closeProcessInstance(processInstanceId,isSuspended)" title="关闭"></t:dgFunOpt>
	<t:dgFunOpt exp="isSuspended#ne#&&isSuspended#ne#true&&isSuspended#ne#finished" funname="reassign(taskId,isSuspended)" title="委派"></t:dgFunOpt>
	<%-- <t:dgFunOpt funname="viewHistory(processInstanceId)" title="历史"></t:dgFunOpt>--%>
	<t:dgFunOpt funname="goProcessHisTab(processInstanceId)" title="历史"></t:dgFunOpt>
	<t:dgFunOpt funname="skipNode(taskId,isSuspended)" title="跳转"></t:dgFunOpt>
</t:datagrid>
</div>
</div>
<script type="text/javascript">
		//暂停
		function suspendProcessInstance(processInstanceId,isSuspended){
		    dialogConfirm('processInstanceController.do?suspend&processInstanceId='+processInstanceId,'确定暂停吗？','runningList');
		}
		
		//启动
		function startProcessInstance(processInstanceId,isSuspended){
		    dialogConfirm('processInstanceController.do?restart&processInstanceId='+processInstanceId,'确定启动吗？','runningList');
		}
		
		//关闭
		function closeProcessInstance(processInstanceId,isSuspended){
		    dialogConfirm('processInstanceController.do?close&processInstanceId='+processInstanceId,'确定关闭吗？','runningList');
		}
		
		//委派(重新分配处理人)
		function reassign(taskId,isSuspended){
			createwindow('委派','processInstanceController.do?reassignInit&taskId='+taskId,700,100);
		}
		
		//查看流程历史
		function viewHistory(processInstanceId){
			var url = "";
			var title = "流程历史";
			url = "activitiController.do?viewProcessInstanceHistory&processInstanceId="+processInstanceId+"&isIframe"
			addOneTab(title, url);
		}
		
		//流程跳转（选择节点，跳转哪个节点）
		function skipNode(taskId,isSuspended){
			createwindow('跳转','processInstanceController.do?skipNodeInit&taskId='+taskId,700,100);
		}
		
		$(document).ready(function(){
			$("input[name='starttime_begin']").attr("class","easyui-datebox");
			$("input[name='starttime_end']").attr("class","easyui-datebox");
			$("input[name='endtime_begin']").attr("class","easyui-datebox");
			$("input[name='endtime_end']").attr("class","easyui-datebox");
		});
</script>