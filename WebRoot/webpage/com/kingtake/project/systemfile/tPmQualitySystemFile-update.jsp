<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>体系文件信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	function uploadFile(data){
			frameElement.api.opener.reloadTable();
			frameElement.api.close();
	}
	
	function close(){
		frameElement.api.close();
	}
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmQualitySystemFileController.do?doUpdate" tiptype="1"  callback="@Override uploadFile" >
		<input id="id" name="id" type="hidden" value="${tPmQualitySystemFilePage.id }">
		<input id="writingUserid" name="writingUserid" type="hidden" value="${tPmQualitySystemFilePage.writingUserid }">
		<input id="createBy" name="createBy" type="hidden" value="${tPmQualitySystemFilePage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tPmQualitySystemFilePage.createName }">
		<input id="createDate" name="createDate" type="hidden" value="${tPmQualitySystemFilePage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tPmQualitySystemFilePage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tPmQualitySystemFilePage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tPmQualitySystemFilePage.updateDate }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right" ><label class="Validform_label"> 名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称: <font color="red">*</font></label></td>
				<td class="value" ><input id="fileName" name="fileName" datatype="byterange" max="80" min="1" type="text" style="width: 150px" class="inputxt" value='${tPmQualitySystemFilePage.fileName}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">名称</label></td>
				<td align="right"><label class="Validform_label"> 发&nbsp;布&nbsp;时&nbsp;间: <font color="red">*</font></label></td>
				<td class="value"><input id="releaseTime" name="releaseTime" datatype="*"  type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmQualitySystemFilePage.releaseTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发布时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 实&nbsp;施&nbsp;时&nbsp;间: <font color="red">*</font></label></td>
				<td class="value"><input id="executeTime" name="executeTime" datatype="*"  type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmQualitySystemFilePage.executeTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">实施时间</label></td>
				<td align="right"><label class="Validform_label"> 册&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数: <font color="red">*</font></label></td>
				<td class="value"><input id="volumeNum" name="volumeNum"  datatype="byterange" max="10" min="1" type="text" style="width: 150px" class="inputxt" value='${tPmQualitySystemFilePage.volumeNum}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">册数</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 出&nbsp;版&nbsp;年&nbsp;份: <font color="red">*</font></label></td>
				<td class="value"><input id="publishYear" name="publishYear" datatype="*" type="text" style="width: 150px" class="Wdate" value="${tPmQualitySystemFilePage.publishYear}" onClick="WdatePicker({dateFmt:'yyyy'})"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">出版年份</label></td>
				<td align="right"><label class="Validform_label"> 编制人姓名: <font color="red">*</font></label></td>
				<td class="value"><input id="writingUsername" name="writingUsername" datatype="byterange" max="50" min="1"  type="text" style="width: 150px" class="inputxt" value='${tPmQualitySystemFilePage.writingUsername}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">编制人姓名</label></td>
			</tr>
			<tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tPmQualitySystemFilePage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tPmQualitySystemFilePage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmQualitySystemFile" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
		</table>
	</t:formvalid>
</body>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/project/systemfile/tPmQualitySystemFile.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>