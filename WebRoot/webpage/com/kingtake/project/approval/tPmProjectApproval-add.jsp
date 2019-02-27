<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>论证报告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
function uploadFile(data){
	$("#bid").val(data.obj.id);
	if($(".uploadify-queue-item").length>0){
		upload();
	}else{
		frameElement.api.opener.reloadTable();
		frameElement.api.close();
	}
}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
    action="tPmProjectApprovalController.do?doAdd" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmProjectApprovalPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmProjectApprovalPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmProjectApprovalPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmProjectApprovalPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmProjectApprovalPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmProjectApprovalPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmProjectApprovalPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width:25%" align="right"><label class="Validform_label"> 建议等级: <font color="red">*</font></label></td>
        <td style="width:75%" class="value"><input id="suggestGrade" name="suggestGrade" type="text" style="width: 150px"
          class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">建议等级</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 建议单位: <font color="red">*</font></label></td>
        <td class="value"><input id="suggestUnit" name="suggestUnit" type="text" style="width: 150px"
          class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">建议单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 研究时限: <font color="red">*</font></label></td>
        <td class="value"><input id="studyTime" name="studyTime" type="text" style="width: 150px" class="inputxt">
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">研究时限</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 项目来源: <font color="red">*</font></label></td>
        <td class="value"><input id="projectSrc" name="projectSrc" type="text" style="width: 150px" class="inputxt">
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目来源</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 所属项目: <font color="red">*</font></label></td>
        <td class="value"><input type="hidden" id="tpId" name="tpId"> <input id="projectName"
          name="projectName" type="text" style="width: 150px" class="inputxt" readonly="readonly"> <t:choose
            url="tPmProjectController.do?projectSelect" name="projectList" icon="icon-search" title="项目列表" width="1000px" height="460px" left="10%" top="10%"
            textname="id,projectName" inputTextname="tpId,projectName" isclear="true"></t:choose> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属项目</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 研究的必要性: </label></td>
        <td class="value"><textarea style="width:50%;" class="inputxt" rows="6" id="studyNecessity"
            name="studyNecessity"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">研究的必要性</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 国内外军内外有关情况分析: </label></td>
        <td class="value"><textarea style="width:50%;" class="inputxt" rows="6" id="situationAnalysis"
            name="situationAnalysis"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">国内外军内外有关情况分析</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 主要研究内容: </label></td>
        <td class="value"><textarea style="width:50%;" class="inputxt" rows="6" id="studyContent"
            name="studyContent"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">主要研究内容</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 研究进度与成果形式: </label></td>
        <td class="value"><textarea style="width:50%;" class="inputxt" rows="6" id="scheduleAndAchieve"
            name="scheduleAndAchieve"></textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">研究进度与成果形式</label></td>
      </tr>
      <tr>
         <td align="right"><label class="Validform_label"> 附件: </label></td>
         <td colspan="3" class="value"><input type="hidden" id="bid" name="bid" ></td>
        </tr>
        <tr>
        <td></td>
        <td colspan="3" class="value">
      <div class="form" id="filediv"></div>
      <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.zip;" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=projectApproval">
      </t:upload>
      </td>
    </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/approval/tPmProjectApproval.js"></script>