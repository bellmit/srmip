<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>申报需求信息表</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function() {
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });
    var bpmStatus = $("#bpmStatus").val();
    if (bpmStatus!=""&& bpmStatus!='1') {
        $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
        $("textarea").attr("disabled", "disabled");
        $("select").attr("disabled", "disabled");
        $(".jeecgDetail").hide();
    }
  });
  
  function uploadFile(data) {
    tip(data.msg);
    if(data.success){
    	window.location.reload();
    }
  }

  //上传文件成功后刷新
  function refresh() {
    window.location.reload();
  }
</script>
</head>
<body style="height: 550px;">
  <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
    <h1 align="center">${tBPmReportReqPage.projectName }-申报需求信息</h1>
    <span>
      <input id="saveBtn" type="button" style="position: fixed; right: 5px; top: 3px;" value="保存申报需求信息">
    </span>
  </div>
  <br>
  <br>
  <t:formvalid formid="formobj" dialog="true" layout="table" postonce="false" action="tBPmReportReqController.do?doUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBPmReportReqPage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${tBPmReportReqPage.projectId }">
    <input id="projectName" name="projectName" type="hidden" value="${tBPmReportReqPage.projectName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBPmReportReqPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBPmReportReqPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBPmReportReqPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBPmReportReqPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBPmReportReqPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBPmReportReqPage.updateDate }">
    <input id="reqNum" name="reqNum" type="hidden" value="${tBPmReportReqPage.reqNum }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 项目名称: </label>
        </td>
        <td class="value">
          <input id="projectName" name="projectName" type="text" disabled="disabled" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.projectName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 起止时间: </label>
        </td>
        <td class="value">
          <input id="beginDate" name="beginDate" type="text" readonly="true" style="width: 165px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBPmReportReqPage.beginDate}' type="date" pattern="yyyy-MM-dd"/>'>
          -
          <input id="endDate" name="endDate" type="text" readonly="true" style="width: 165px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBPmReportReqPage.endDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">起始时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 申报单位: </label>
        </td>
        <td class="value">
          <input id="reportUnitName" name="reportUnitName" type="text" readonly="true" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.reportUnit.departname}'>
          <input id="reportUnit" name="reportUnit.id" type="hidden" value='${tBPmReportReqPage.reportUnit.id}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申报单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 责任单位: </label>
        </td>
        <td class="value">
          <input id="manageUnitName" name="manageUnitName" type="text" readonly="true" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.manageUnit.departname}'>
          <input id="manageUnit" name="manageUnit.id" type="hidden" value='${tBPmReportReqPage.manageUnit.id}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">责任单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 经费需求: </label>
        </td>
        <td class="value">
          <input id="feeReq" name="feeReq" type="text" style="width: 350px" readonly="true" class="inputxt" value='${tBPmReportReqPage.feeReq}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">经费需求</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 申请人: </label>
        </td>
        <td class="value">
          <input id="applicantor" name="applicantor" type="text" readonly="true" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.applicantor}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申请人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 流程处理状态: </label>
        </td>
        <td class="value">
          <input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly">
         <input id="bpmStatus" name="bpmStatus" value="${tBPmReportReqPage.bpmStatus }" type="hidden" class="inputxt" />
         <input id="planStatus" name="planStatus" value="${tBPmReportReqPage.planStatus }" type="hidden" class="inputxt" />
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">流程处理状态</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">
            立项需求及
            <br>
            研究总体要求:
          </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <textarea id="researchReq" name="researchReq" type="text" datatype="*2-250" style="width: 350px; height: 80px;" class="inputxt">${tBPmReportReqPage.researchReq}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">立项需求及研究总体要求</label>
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBPmReportReqPage.attachments }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBPmReportReqPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=reportReq" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/reportreq/tBPmReportReq.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>