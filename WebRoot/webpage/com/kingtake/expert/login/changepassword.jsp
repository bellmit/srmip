<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" refresh="false" dialog="true" action="expertLoginController.do?savenewpwd" usePlugin="password" layout="table">
	<input id="id" type="hidden" value="${user.id }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tbody>
			<tr>
				<td align="right"><span class="filedzt">新密码:</span></td>
				<td class="value"><input type="password" value="" name="newpassword" class="inputxt" plugin="passwordStrength" datatype="*6-18" errormsg="密码至少6个字符,最多18个字符！" style="width:150px;height:20px;"/> <span
					class="Validform_checktip"> 密码至少6个字符,最多18个字符！ </span> <span class="passwordStrength" style="display: none;"> <b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span> </span></td>
			</tr>
			<tr>
				<td align="right"><span class="filedzt">重复密码:</span></td>
				<td class="value"><input id="newpassword" type="password" class="inputxt" recheck="newpassword" datatype="*6-18" errormsg="两次输入的密码不一致！" style="width:150px;height:20px;"> <span class="Validform_checktip"></span></td>
			</tr>
		</tbody>
	</table>
</t:formvalid>
</body>