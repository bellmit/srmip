<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目名称变更</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
	//编写自定义JS代码
	function uploadFile(data) {
		$("#bid").val(data.obj.id);
		if ($(".uploadify-queue-item").length > 0) {
			upload();
		} else {
			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}

	function close() {
		frameElement.api.close();
	}
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBPmChangeProjectnameController.do?doAdd" tiptype="1" callback="@Override uploadFile">
		<input id="createBy" name="createBy" type="hidden" value="${tBPmChangeProjectnamePage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tBPmChangeProjectnamePage.createName }">
		<input id="createDate" name="createDate" type="hidden" value="${tBPmChangeProjectnamePage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tBPmChangeProjectnamePage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tBPmChangeProjectnamePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tBPmChangeProjectnamePage.updateDate }">
		<input id="bpmStatus" name="bpmStatus" type="hidden" value="1">
		<input id="id" name="id" type="hidden" value="${tBPmChangeProjectnamePage.id }">
		<input id="projectId" name="projectId" type="hidden" value="${projectId}">
		<input id="changeTime" name="changeTime" type="hidden" value="${tBPmChangeProjectnamePage.changeTime }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 变更前项目名称:&nbsp;&nbsp; </label></td>
				<td class="value"><input id="beforeProjectName" name="beforeProjectName" type="text" style="width: 450px" class="inputxt" value="${beforeProjectName}" readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更前项目名称</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 变更后项目名称:<font color="red">*</font></label></td>
				<td class="value"><input id="afterProjectName" name="afterProjectName" type="text" style="width: 450px" class="inputxt" datatype="byterange" max="100" min="1"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更后项目名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 变&nbsp;&nbsp;&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;&nbsp;因:<font color="red">*</font></label></td>
				<td class="value"><textarea id="changeReason" name="changeReason" type="text" style="width: 450px" class="inputxt" datatype="byterange" max="400" min="1" rows="5"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更原因</label></td>
			<tr>
				<td align="right"><label class="Validform_label"> 变&nbsp;&nbsp;&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;依&nbsp;&nbsp;&nbsp;&nbsp;据:<font color="red">*</font></label></td>
				<td class="value"><input id="changeAccording" name="changeAccording" type="text" style="width: 450px" class="inputxt" datatype="byterange" max="100" min="1"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更依据</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
				<td class="value">
					<input type="hidden" value="${tBPmChangeProjectnamePage.id}" id="bid" name="bid" />
					<div>
						<div class="form" id="filediv"></div>
						<t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tBPmChangeProjectName">
						</t:upload>
					</div>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/kingtake/project/change/tBPmChangeProjectname.js"></script>