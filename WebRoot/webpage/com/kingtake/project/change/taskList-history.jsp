<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid fitColumns="true" name="historyTaskList" queryMode="group" title="历史任务列表" actionUrl="tBPmChangeProjectnameController.do?taskHistoryList&businessCode=${businessCode}" idField="id">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="流程编号" field="procDefId" width="80" hidden="true"></t:dgCol>
 <t:dgCol title="流程" field="prodef_name" width="150" hidden="true"></t:dgCol>
 <t:dgCol title="流程实例" field="proInsl_procInstId" hidden="true" width="100"></t:dgCol>
 <t:dgCol title="项目名称" field="business_name" width="150" ></t:dgCol>
 <t:dgCol title="任务名称" field="name" width="100"></t:dgCol>
 <t:dgCol title="任务发起人" field="proInsl_startUserId" width="100"></t:dgCol>
 <t:dgCol title="任务处理人" field="assignee" width="100"></t:dgCol>
 <t:dgCol title="开始时间" field="startTime" width="150"  formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
 <t:dgCol title="结束时间" field="endTime"  width="150"  formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
 <t:dgCol title="持续时间" field="durationStr"  width="100"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
 <%-- <t:dgFunOpt funname="viewHistory(proInsl_procInstId)" title="<font style=color:red>查看</font>"></t:dgFunOpt>--%>
 <t:dgFunOpt funname="goProcessHisTab(proInsl_procInstId,prodef_name)" title="历史"></t:dgFunOpt>
 </t:datagrid>
<SCRIPT type="text/javascript">
//查看流程历史
function viewHistory(processInstanceId){
	var url = "";
	var title = "流程历史";
	url = "activitiController.do?viewProcessInstanceHistory&processInstanceId="+processInstanceId;
	addOneTab(title, url);
}
</SCRIPT>