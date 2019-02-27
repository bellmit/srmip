<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>开题报告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(function() {
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });
    
    if ($("#bpmStatus").val()=='2'||$("#bpmStatus").val()=='3'||$("#bpmStatus").val()=='4') {
        $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
        $("textarea").attr("disabled", "disabled");
        $("select").attr("disabled", "disabled");
        $(".jeecgDetail").hide();
    }
    
  });
  function uploadFile(data) {
        if (data.success) {
            window.location.reload();
        }
        tip(data.msg);
  }

  //上传文件成功后刷新
  function refresh() {
    window.location.reload();
  }
</script>
</head>
<body>
  <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
    <h1 align="center">${tPmOpenSubjectPage.project.projectName }-开题报告</h1>
    <span>
      <input id="saveBtn" type="button" style="position: fixed; right: 5px; top: 3px;" value="保存开题报告">
    </span>
  </div>
  <br>
  <br>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" postonce="false" action="tPmOpenSubjectController.do?doUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmOpenSubjectPage.id }">
    <input id="projectId" name="tpId" type="hidden" value="${tPmOpenSubjectPage.project.id}">
    <input id="bpmStatus" type="hidden" value="${tPmOpenSubjectPage.bpmStatus}">
    <input id="createBy" name="createBy" type="hidden" value="${tPmOpenSubjectPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmOpenSubjectPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmOpenSubjectPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmOpenSubjectPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmOpenSubjectPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmOpenSubjectPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width: 25%" align="right">
          <label class="Validform_label"> 关键技术指标: </label>
        </td>
        <td style="width: 75%" class="value">
          <textarea id="technicalIndicator" style="width: 80%;" datatype="*0-800" maxlength="800" class="inputxt" rows="6" name="technicalIndicator">${tPmOpenSubjectPage.technicalIndicator}</textarea>
          <spanclass="Validform_checktip"> </span> <label class="Validform_label" style="display: none;">关键技术指标</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 主要研究内容: </label>
        </td>
        <td class="value">
          <textarea id="researchContents" style="width: 80%;" maxlength="800" class="inputxt" rows="6" name="researchContents">${tPmOpenSubjectPage.researchContents}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主要研究内容</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
        <td class="value"><input type="hidden" value="${tPmOpenSubjectPage.attachmentCode}" id="bid" name="attachmentCode">
          <table style="max-width: 92%;" id="fileShow">
            <c:forEach items="${tPmOpenSubjectPage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" buttonText="添加文件"  auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
		    	formData="bid,projId" uploader="commonController.do?saveUploadFilesToFTP&businessType=openSubject" >
            </t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/subject/tPmOpenSubject.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
</html>