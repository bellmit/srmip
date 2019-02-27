<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid fitColumns="true" name="myTaskList" queryMode="group" title="我的任务列表" pagination="false" actionUrl="tPmDeclareController.do?taskAllList&businessCode=${businessCode}" idField="id">
<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="流程编号" field="Process_processDefinition_id" width="80" hidden="true" ></t:dgCol>
 <t:dgCol title="流程" field="Process_processDefinition_name" width="150" ></t:dgCol>
 <t:dgCol title="业务名称" field="businessName" width="150" ></t:dgCol>
 <t:dgCol title="流程实例" field="Process_task_processInstanceId" hidden="true" width="100"></t:dgCol>
 <t:dgCol title="任务发起人" field="userRealName" width="100"></t:dgCol>
 <t:dgCol title="任务办理人" field="assigneeName" width="100"></t:dgCol>
 <t:dgCol title="开始时间" field="Process_task_createTime" width="150" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
 <t:dgCol title="结束时间" field="Process_task_dueTime" width="150" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description" width="80"></t:dgCol>
 <t:dgCol title="当前环节" field="Process_task_name" width="100"></t:dgCol>
 <t:dgCol hidden="true" title="TASK ID(该字段隐藏)" field="Process_task_id"></t:dgCol>
 <t:dgCol hidden="true" title="key" field="Process_task_taskDefinitionKey"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="220" frozenColumn="true"></t:dgCol>
 <t:dgConfOpt exp="assigneeName#empty#true" url="activitiController.do?claim&taskId={Process_task_id}" message="确定签收?" title="签收"></t:dgConfOpt>
  <%--<t:dgFunOpt exp="Process_task_assignee#empty#false" funname="openhandleMixTab(Process_task_id,Process_task_name)" title="办理"></t:dgFunOpt> --%>
 <t:dgFunOpt exp="assigneeName#empty#false" funname="openhandleMix(Process_task_id,Process_task_name)" title="办理"></t:dgFunOpt>
 <t:dgFunOpt exp="assigneeName#empty#false" funname="selectEntruster(Process_task_id,Process_task_name)" title="委托"></t:dgFunOpt>
 <c:if test="${businessCode == 'declare'}">
 	<t:dgFunOpt funname="ExportProjectXls(id)" title="导出项目基本信息"></t:dgFunOpt>
 </c:if>
</t:datagrid>

<script type="text/javascript">
//导出
function ExportProjectXls(id) {
	JeecgExcelExport("tPmDeclareController.do?exportProjectXls&id=" + id,"myTaskList");
}
</script>

