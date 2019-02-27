<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目名称变更</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	$(function() {
		$("#saveBtn").click(function() {
			$("#btn_sub").click();
		});
	});

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
	<div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF;border-bottom: solid 1px #95B8E7;">
		<h1 align="center">项目名称变更申请</h1>
		<span><c:if test="${read ne 1}"><input id="saveBtn" type="button" style="position: fixed; right: 5px; top: 3px;" value="保存"></c:if></span>
	</div>
	<br />
	<br />
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBPmChangeProjectnameController.do?doUpdate" tiptype="1" callback="@Override uploadFile">
		<input id="createBy" name="createBy" type="hidden" value="${tBPmChangeProjectnamePage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tBPmChangeProjectnamePage.createName }">
		<input id="createDate" name="createDate" type="hidden" value="${tBPmChangeProjectnamePage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tBPmChangeProjectnamePage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tBPmChangeProjectnamePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tBPmChangeProjectnamePage.updateDate }">
		<input id="bpmStatus" name="bpmStatus" type="hidden" value="${tBPmChangeProjectnamePage.bpmStatus }">
		<input id="id" name="id" type="hidden" value="${tBPmChangeProjectnamePage.id }">
		<input id="projectId" name="projectId" type="hidden" value="${tBPmChangeProjectnamePage.projectId }">
		<input id="changeTime" name="changeTime" type="hidden" value="${tBPmChangeProjectnamePage.changeTime }">
		<table style="width: 100%;" class="formtable" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 变更前项目名称:&nbsp;&nbsp; </label></td>
				<td class="value"><input id="beforeProjectName" name="beforeProjectName" type="text" style="width: 450px" class="inputxt" value='${tBPmChangeProjectnamePage.beforeProjectName}' readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更前项目名称</label></td>
            </tr>
			<tr>
				<td align="right"><label class="Validform_label"> 变更后项目名称:<font color="red">*</font></label></td>
				<td class="value"><input id="afterProjectName" name="afterProjectName" type="text" style="width: 450px" datatype="byterange" max="100" min="1" class="inputxt" value='${tBPmChangeProjectnamePage.afterProjectName}' datatype="*1-100"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更后项目名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 变&nbsp;&nbsp;&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;&nbsp;因:<font color="red">*</font></label></td>
				<td class="value"><textarea id="changeReason" name="changeReason" type="text" style="width: 450px" class="inputxt" datatype="byterange" max="400" min="1">${tBPmChangeProjectnamePage.changeReason}</textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更原因</label></td>
            </tr>
			<tr>
				<td align="right"><label class="Validform_label"> 变&nbsp;&nbsp;&nbsp;&nbsp;更&nbsp;&nbsp;&nbsp;依&nbsp;&nbsp;&nbsp;&nbsp;据:<font color="red">*</font></label></td>
				<td class="value"><input id="changeAccording" name="changeAccording" type="text" style="width: 450px" class="inputxt" value='${tBPmChangeProjectnamePage.changeAccording}' datatype="byterange" max="100" min="1"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">变更依据</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
				<td class="value"><input type="hidden" value="${tBPmChangeProjectnamePage.id}" id="bid" name="bid" />
					<table style="max-width:450px;">
						<c:forEach items="${tBPmChangeProjectnamePage.certificates }" var="file"  varStatus="idx">
				          <tr>
				            <td>
				            	<a href="javascript:void(0)" onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBPmChangeProjectnamePage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">
				           			${file.attachmenttitle}
				            	</a>&nbsp;&nbsp;&nbsp;
				            </td>
				            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
				            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
				          </tr>
				        </c:forEach>
			      	</table>
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
<t:authFilter></t:authFilter>
<script src="webpage/com/kingtake/project/change/tBPmChangeProjectname.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>