<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学术团体信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function uploadFile(data) {
            frameElement.api.opener.reloadTable();
            frameElement.api.opener.tip(data.msg);
            frameElement.api.close();
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tBLearningTeamController.do?doAddUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBLearningTeamPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBLearningTeamPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBLearningTeamPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBLearningTeamPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBLearningTeamPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBLearningTeamPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBLearningTeamPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 团体名称:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="teamName" name="teamName" type="text" style="width: 150px" class="inputxt" value='${tBLearningTeamPage.teamName}' datatype="*1-60">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">团体名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 团体业务主管单位:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="manageDepart" name="manageDepart" type="text" style="width: 150px" class="inputxt" value='${tBLearningTeamPage.manageDepart}' datatype="*1-60">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">团体业务主管单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 兼任领导职务名称:</label>&nbsp;
        </td>
        <td class="value">
          <input id="leaderJob" name="leaderJob" type="text" style="width: 150px" class="inputxt" value='${tBLearningTeamPage.leaderJob}' datatype="*1-15" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">兼任领导职务名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 批准单位:</label>&nbsp;
        </td>
        <td class="value">
          <input id="approveUnit" name="approveUnit" type="text" style="width: 150px" class="inputxt" value='${tBLearningTeamPage.approveUnit}' datatype="*1-30" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">批准单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">社团类别:</label><font color="red">*</font>
        </td>
        <td class="value">
          <t:codeTypeSelect name="collegeType" type="" codeType="1" code="STLB" id="collegeType" defaultVal="${tBLearningTeamPage.collegeType}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">社团类别</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">是否在民政部门注册:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="isRegisterYes" name="isRegister" type="radio" <c:if test="${tBLearningTeamPage.isRegister eq '1'}">checked</c:if> value="1">
          <label for="isRegisterYes">是</label>
          <input id="isRegisterNo" name="isRegister" type="radio" <c:if test="${tBLearningTeamPage.isRegister ne '1'}">checked</c:if> value="0">
          <label for="isRegisterNo">否</label>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">是否在民政部门注册</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">参加团体时间:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="collegeTime" name="collegeTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy'})" title="如：2014" placeholder="如：2014"
            value='${tBLearningTeamPage.collegeTime}' datatype="n">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">参加团体时间</label>
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBLearningTeamPage.uploadFiles }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBLearningTeamPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=learningTeam" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/learning/tBLearningTeam.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>