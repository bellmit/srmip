<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>来文单位</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBSendDocUnitController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tBSendDocUnitPage.id }">
		<input id="createBy" name="createBy" type="hidden" value="${tBSendDocUnitPage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tBSendDocUnitPage.createName }">
		<input id="createDate" name="createDate" type="hidden" value="${tBSendDocUnitPage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tBSendDocUnitPage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tBSendDocUnitPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tBSendDocUnitPage.updateDate }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 单位全称:<font color="red">*</font> </label></td>
				<td class="value"><input id="unitName" name="unitName" datatype="byterange" max="200" min="1"  type="text" style="width: 80%;" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位全称</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 单位简称: </label></td>
				<td class="value"><input id="unitShortName" name="unitShortName" datatype="byterange" max="100" min="0"  type="text" style="width: 80%;" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位简称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: </label></td>
				<td class="value"><input id="contact" name="contact" type="text" datatype="byterange" max="30" min="0" style="width: 80%;" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 联系电话: </label></td>
				<td class="value"><input id="contactPhone" name="contactPhone" datatype="n0-30" type="text" style="width: 80%;" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系电话</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/kingtake/base/senddocunit/tBSendDocUnit.js"></script>