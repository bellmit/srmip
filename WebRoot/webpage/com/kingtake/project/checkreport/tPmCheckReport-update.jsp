<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中期检查报告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function saveCallback(data) {
            var win;
            var dialog = W.$.dialog.list['processDialog'];
            if (dialog == undefined) {
                win = frameElement.api.opener;
            } else {
                win = dialog.content;
            }
            win.tip(data.msg);
            if (data.success) {
                win.reloadTable();
                frameElement.api.close();
            }
    }

</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmCheckReportController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tPmCheckReportPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmCheckReportPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmCheckReportPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmCheckReportPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmCheckReportPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmCheckReportPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmCheckReportPage.updateDate }">
    <input id="projectId" name="tpId" type="hidden" value='${tPmCheckReportPage.tpId}'>
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            标题: <font color="red">*</font>
          </label></td>
        <td class="value" colspan="3"><input id="reportTitle" name="reportTitle" type="text" style="width: 450px" datatype="byterange" max="200" min="1" class="inputxt" value='${tPmCheckReportPage.reportTitle}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            起始时间: <font color="red">*</font>
          </label></td>
        <td class="value" style="width:200px;"><input id="yearStart" name="yearStart" type="text" datatype="date" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'yearEnd\')}'})"
            value='<fmt:formatDate value='${tPmCheckReportPage.yearStart}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">报告起始时间</label></td>
        <td align="right"><label class="Validform_label">
            结束时间: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="yearEnd" name="yearEnd" type="text" datatype="date" style="width: 150px" class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'yearStart\')}'})"
            value='<fmt:formatDate value='${tPmCheckReportPage.yearEnd}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">报告结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="remark" name="remark" datatype="byterange" max="500" min="0" cols="5" rows="4" style="width: 450px" class="inputxt">${tPmCheckReportPage.remark}</textarea> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tPmCheckReportPage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tPmCheckReportPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=checkReport" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/checkreport/tPmCheckReport.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>