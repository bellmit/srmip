<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>合同正本上传</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
function callback(data) {
	var win;
	var dialog = W.$.dialog.list['processDialog'];
    if (dialog == undefined) {
    	win = frameElement.api.opener;
    } else {
    	win = dialog.content;
    }
    win.reloadTable();
    frameElement.api.close();
}
</script>
</head>
<body>
<t:formvalid formid="formobj" layout="table" dialog="true" action="tPmIncomeContractApprController.do?updateContractBook" callback="@Override callback">
	<table id="fileShow" style="max-width: 515px;margin-top: 10px;">
		<c:forEach items="${contractBook}"
			var="file" varStatus="idx">
			<tr style="height: 30px;">
				<td><a href="javascript:void(0);">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
				<td style="width: 40px;"><a
					href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0"
					title="下载">下载</a></td>
					<c:if test="${contractBookFlag ne 2 }">
				<td style="width: 40px;"><a href="javascript:void(0)"
					class="jeecgDetail"
					onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
					</c:if>
			</tr>
		</c:forEach>
	</table>
	<div>
		<div class="form" id="filediv"></div>
		<input type="hidden" value="${bid}" id="bid" name="attachmentCode" />
		<input type="hidden" value="${projectId}" id="projectId"
			name="projectId" />
		<input type="hidden" value="${contractId}" id="contractId"
		    name="contractId" >
		<input type="hidden" value="${inoutFlag}" id="inoutFlag"
		    name="inoutFlag" >
		<c:if test="${contractBookFlag ne 2 }">
		<t:upload queueID="filediv" name="fiels" id="file_upload"
			buttonText="添加文件" formData="bid,projectId" auto="true" multi="false"
			dialog="false" onUploadSuccess="uploadSuccess" fileSizeLimit="2GB"
			uploader="commonController.do?saveUploadFilesToFTP&businessType=${businessCode }"></t:upload>
			</c:if>
	</div>
	</t:formvalid>
</body>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
</html>
