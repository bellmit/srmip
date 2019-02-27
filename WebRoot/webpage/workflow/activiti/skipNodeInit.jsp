<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>跳转流程节点</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="processInstanceController.do?skipNode">
	<input id="taskId" name="taskId" type="hidden" value="${taskId }" />
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right"><label class="Validform_label">选择跳转节点: </label></td>
			<td class="value" nowrap>
				<select name="skipTaskNode">
	  				<c:forEach items="${taskList}" var="taskList">
	  					<option value="${taskList.taskKey}">${taskList.name } ${taskList.taskKey}</option>
	  				</c:forEach>
	  			</select>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>