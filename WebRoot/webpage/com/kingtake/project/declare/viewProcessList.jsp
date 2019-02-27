<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <input id="processInstId" type="hidden" value="${processInstId}">
    <t:datagrid name="tpmLogList" fitColumns="true"
      actionUrl="tPmDeclareController.do?processOprateDatagrid&processInstId=${processInstId }" idField="id" fit="true" queryMode="group" pagination="false">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程实例ID" field="bpm_id" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程名称" field="task_name" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程节点名称" field="task_node" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作人" field="op_name" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作时间" field="op_time"  extendParams="formatter:dateFormatter," queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作动作" field="op_type" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="备注" field="memo"  queryMode="group" width="250" align="center"></t:dgCol>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript" src="webpage/com/kingtake/common/dateFormat.js">
</script>
<script type="text/javascript">
function dateFormatter(value, rowData,
		rowIndex) {
	if (value != null) {
		return new Date(value).format('yyyy-MM-dd hh:mm:ss');
	}
}
</script>