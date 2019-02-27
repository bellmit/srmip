<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script>
</script>

<t:datagrid name="userList" title="common.operation" actionUrl="userController.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.department" field="userOrgList.tsDepart.departname" sortable="false" query="false"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true" isLike="true"></t:dgCol>
</t:datagrid>
