<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>选择委派人员</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="processInstanceController.do?reassign">
	<input id="taskId" name="taskId" type="hidden" value="${taskId }" />
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label"> 委派人: </label></td>
			<td class="value" nowrap>
				<input name="userName" name="userName" type="hidden" value="" id="userName"> 
				<input name="realName" class="inputxt" value="" id="realName" readonly="readonly" datatype="*" /> 
				<t:choose hiddenName="userName" hiddenid="userName" url="processInstanceController.do?reassignUsers" name="roleList" icon="icon-search" title="委派人选择列表" textname="realName" isclear="true"></t:choose>
				<span class="Validform_checktip">用户不可多选</span>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>