<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>模板选择页面</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" layout="table" action="loginController.do?login">
	<input id="id" name="id" type="hidden" value="${user.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="15%" nowrap>公文种类：</td>
			<td class="value" width="85%">
				<t:convert codeType="1" code="GWZL" value="${regType}"></t:convert>
            </td>
		</tr>
		<tr>
			<td align="right">模板名称：</td>
			<td class="value">
                <select id="templateRealpath" name="templateRealpath" datatype="*">
                    <c:forEach items="${list}" var="l">
                        <option value="${l.FILEPATH }">${l.MODEL_NAME}</option>
                    </c:forEach>
                </select>
            <span><font color="#999999">请选择模板</font></span></td>
		</tr>
	</table>
</t:formvalid>
</body>