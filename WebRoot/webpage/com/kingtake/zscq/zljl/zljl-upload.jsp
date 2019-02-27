<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body>
  <div style="margin: 0 auto;width: 610px;">
  <t:formvalid formid="formobj" layout="table" dialog="true" action="tZZljlController.do?doUploadLbd">
  <input name="id" type="hidden" value="${zljl.id}">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td colspan="3" class="value"><input type="hidden" value="${zljl.fjbm }" id="bid" name="fjbm" />
          <table style="max-width: 360px;" id="fileShow">
            <c:forEach items="${zljl.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);"
                    onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${zljl.fjbm}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=zljl" dialog="false" auto="true"
              onUploadSuccess="updateUploadSuccess"></t:upload>
          </div></td>
      </tr>
    </table>
    </fieldset>
  </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
</html>
