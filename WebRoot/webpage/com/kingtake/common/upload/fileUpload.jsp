<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>申请文件</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div style="margin: 0 auto;width: 600px;">
    <input id="businessType" value="${businessType}">
    <input id="projectId" value="${projectId}">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td colspan="3" class="value"><input type="hidden" value="${attachmentCode }" id="bid" name="bid" />
          <table style="max-width: 360px;" id="fileShow">
            <c:forEach items="${attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <c:if test="${opt ne 'view' }">
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </c:if>
              </tr>
            </c:forEach>
          </table>
          <c:if test="${opt ne 'view' }">
          <div>
            <div class="form" id="filediv"></div>
            <span id="file_uploadspan"><input type="file" id="file_upload" /></span>
            <!-- <input type="button" value="上传" onclick='$("#file_upload").uploadify("upload", "*")'> -->
          </div>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
          </c:if>
          </td>
      </tr>
    </table>
  </div>
</body>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>