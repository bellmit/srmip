<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.Wdate {
	height: 14px;
}
</style>
<script type="text/javascript">
  $(document).ready(function() {
    if (location.href.indexOf("load=detail") != -1) {
      //无效化所有表单元素，只能进行查看
      $(":input").attr("disabled", "true");
    }
  });
</script>
</head>
<body>
  <div style="height: 500px; width: 100%;">
    <div style="position: fixed; top: 30; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
      <h1 align="center">${tPmFinishApplyPage.project.projectName }-项目结题申请</h1>
      <span> </span>
    </div>
    <br>
    <br>
    <input id="auditStatus" type="hidden" value="${tPmFinishApplyPage.auditStatus}">
    <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tPmFinishApplyController.do?doAddUpdate" beforeSubmit="checkDate">
      <input id="id" name="id" type="hidden" value="${tPmFinishApplyPage.id }">
      <input id="projectId" name="project.id" type="hidden" value="${tPmFinishApplyPage.project.id }">
      <input id="createBy" name="createBy" type="hidden" value="${tPmFinishApplyPage.createBy }">
      <input id="createName" name="createName" type="hidden" value="${tPmFinishApplyPage.createName }">
      <input id="createDate" name="createDate" type="hidden" value="${tPmFinishApplyPage.createDate }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tPmFinishApplyPage.updateBy }">
      <input id="updateName" name="updateName" type="hidden" value="${tPmFinishApplyPage.updateName }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tPmFinishApplyPage.updateDate }">
      <table style="width: 800px;" cellpadding="0" cellspacing="1" border="0" class="formtable">
        <caption>
          <strong style="font-size: xx-large;">科研项目结题申请表</strong>
        </caption>
        <tr>
          <td align="right" colspan="3">
            <label class="Validform_label"> 结题时间: </label>
          </td>
          <td width="150" class="value">
            <input id="finishDate" name="finishDate" type="text" style="width: 130px" class="Wdate" onClick="WdatePicker()"
              value='<fmt:formatDate value='${tPmFinishApplyPage.finishDate}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">结题时间</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 项目名称: </label>
          </td>
          <td class="value" colspan="3">
            <input id="projectName" name="projectName" type="text" readonly="true" style="width: 630px" class="inputxt" value='${tPmFinishApplyPage.projectName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">项目名称</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 项目来源: </label>
          </td>
          <td class="value" colspan="3">
            <input id="sourceUnit" name="sourceUnit" type="text" style="width: 630px" readonly="true" class="inputxt" value='${tPmFinishApplyPage.sourceUnit}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">项目来源</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 起止年度: </label>
          </td>
          <td class="value">
            <input id="beginYear" name="beginYear" type="text" style="width: 50px" readonly="true" class="inputxt" value='${tPmFinishApplyPage.beginYear}'>
            <span style="padding-left: 5px;">~</span>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">起始年度</label>
            <input id="endYear" name="endYear" type="text" style="width: 50px" readonly="true" class="inputxt" value='${tPmFinishApplyPage.endYear}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">截止年度</label>
          </td>
          <td width="90" align="right">
            <label class="Validform_label"> 经费性质: </label>
          </td>
          <td width="150" class="value">
            <input id="feeType" name="feeType" type="hidden" style="width: 130px" class="inputxt" value='${tPmFinishApplyPage.feeType}'>
            <input id="feeType" name="feeTypeName" type="text" readonly="true" style="width: 130px" class="inputxt" value='${tPmFinishApplyPage.feeTypeName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">经费性质</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 研究单位: </label>
          </td>
          <td class="value">
            <input id="developerDepartId" name="developerDepartId" type="hidden" style="width: 150px" class="inputxt" value='${tPmFinishApplyPage.developerDepartId}'>
            <input id="developerDepartName" name="developerDepartName" type="text" readonly="true" style="width: 380px" class="inputxt" value='${tPmFinishApplyPage.developerDepartName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">研究单位</label>
          </td>
          <td width="90" align="right">
            <label class="Validform_label"> 项目负责人: </label>
          </td>
          <td width="150" class="value">
            <input id="projectManagerId" name="projectManagerId" type="hidden" style="width: 150px" class="inputxt" value='${tPmFinishApplyPage.projectManagerId}'>
            <input id="projectManager" name="projectManager" type="text" readonly="true" style="width: 130px" class="inputxt" value='${tPmFinishApplyPage.projectManager}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">项目负责人</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 总经费: </label>
          </td>
          <td class="value">
            <input id="allFee" name="allFee" type="text" readonly="true" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" style="width: 380px; text-align: right;"
              value='${tPmFinishApplyPage.allFee}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">总经费</label>
          </td>
          <td width="90" align="right">
            <label class="Validform_label"> 剩余经费: </label>
          </td>
          <td width="150" class="value">
            <input id="extraFee" name="extraFee" type="text" data-options="min:0,precision:2,groupSeparator:','" style="width: 130px; text-align: right;" value='${tPmFinishApplyPage.extraFee}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">剩余经费</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 项目完成情况: </label>
            <font color="red">*</font>
          </td>
          <td class="value" colspan="3">
            <textarea id="projectFinishInfo" style="width: 630px;" datatype="byterange" max="800" min="1" maxlength="800" class="inputxt" rows="4" name="projectFinishInfo">${tPmFinishApplyPage.projectFinishInfo}</textarea>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">项目完成情况</label>
          </td>
        </tr>
        <tr>
          <td width="136" align="right">
            <label class="Validform_label"> 剩余经费处理意见: </label>
            <font color="red">*</font>
          </td>
          <td class="value" colspan="3">
            <textarea id="extraFeeSuggestion" style="width: 630px;" datatype="byterange" max="800" min="1" maxlength="800" class="inputxt" rows="4" name="extraFeeSuggestion">${tPmFinishApplyPage.extraFeeSuggestion}</textarea>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">剩余经费处理意见</label>
          </td>
        </tr>
        <tr>
              <td align="right">
                <label class="Validform_label"> 附件:&nbsp;&nbsp; </label>
              </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${tPmFinishApplyPage.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width: 360px;" id=fileShow>
                  <c:forEach items="${tPmFinishApplyPage.attachments}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                      </td>
                      <td style="width: 60px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload" buttonText="添加文件"  auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
		    	    formData="bid,projectId" uploader="commonController.do?saveUploadFilesToFTP&businessType=finishApply" ></t:upload>
                </div>
              </td>
            </tr>
      </table>
    </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/project/finish/tPmFinishApply.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>