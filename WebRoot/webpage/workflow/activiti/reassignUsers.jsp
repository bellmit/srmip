<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>角色集合</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:datagrid name="roleList" title="请选择委派人员" actionUrl="processInstanceController.do?datagridUsers" idField="id" checkbox="true" queryMode="group" showRefresh="false">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户账号" field="userName" width="50" query="true"></t:dgCol>
	<t:dgCol title="用户中文" field="realName" width="50" query="true"></t:dgCol>
</t:datagrid>
</body>
</html>
