<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专利申请</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
  <div style="margin: 0 auto; width: 600px;">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tZZhyyController.do?doUpdate" tiptype="1">
      <input id="id" name="id" type="hidden" value="${zhyyPage.id }">
      <input id="zlsqId" name="zlsqId" type="hidden" value="${zhyyPage.zlsqId }">
      <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right"><label class="Validform_label">
              转化类别: <font color="red">*</font>
            </label></td>
          <td class="value"><t:codeTypeSelect name="zhlb" type="select" codeType="1" code="ZLZHLB" id="zhlb" defaultVal="${zhyyPage.zhlb}" labelText="请选择"
              extendParam='{"datatype":"*","style":"width:150px;"}'></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">转化类别</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp;&nbsp;&nbsp; </label></td>
          <td colspan="3" class="value"><input type="hidden" value="${zhyyPage.fjbm }" id="bid" name="fjbm" /> 
            <table style="max-width: 360px;" id="fileShow">
              <c:forEach items="${zhyyPage.attachments}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td><a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${zhyyPage.fjbm}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                  <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=zhyy" dialog="false" auto="true"
                onUploadSuccess="updateUploadSuccess"></t:upload>
            </div></td>
        </tr>
      </table>
    </t:formvalid>
  </div>
</body>
<script type="text/javascript">
    
</script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>