<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学术报告厅信息登记表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  function uploadFile(data) {
      frameElement.api.opener.reloadTable();
      frameElement.api.close();
  }
  function close() {
    frameElement.api.close();
  }
  function getSerialNum() {
    alert('待生成规则确定后再补充完整');
  }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tBReportHallUseController.do?doSave" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBReportHallUsePage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBReportHallUsePage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBReportHallUsePage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBReportHallUsePage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBReportHallUsePage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBReportHallUsePage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBReportHallUsePage.updateDate }">
    <table style="width: 595px;" cellpadding="0" cellspacing="1" border="0" class="formtable">
      <tr>
        <td align="right" width="115px">
          <label class="Validform_label"> 编号: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <c:if test="${checkFlag == '1'}">
            <input id="reviewNumber" name="reviewNumber" value="${tBReportHallUsePage.reviewNumber }" placeholder="由机关填写" style="width: 435px" datatype="byterange" max="50" min="1" type="text"  class="inputxt" />
            <span class="Validform_checktip"> </span>
            <label class="Validform_label" style="display: none;"> 编号 </label>
            <input id="checkFlag" name="checkFlag" type="hidden" value="1" />
          </c:if>
          <c:if test="${checkFlag != '1'}">
            <input id="checkFlag" name="checkFlag" type="hidden" value="${tBReportHallUsePage.checkFlag }">
            <input id="reviewNumber" name="reviewNumber" placeholder="由机关填写" type="text" style="width: 441px" class="inputxt" value='${tBReportHallUsePage.reviewNumber}' readonly="true">
          </c:if>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">编号</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 学术报告厅: </label>
        </td>
        <td class="value" colspan="3">
          <t:codeTypeSelect name="reportHallId" type="select" codeType="1" code="XSBGT" id="reportHallId">
          </t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">学术报告厅</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 使用单位: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="useDepartId" name="useDepartId" type="hidden" value="${tBReportHallUsePage.useDepartId }" datatype="*">
          <input id="useDepartName" name="useDepartName" value="${tBReportHallUsePage.useDepartName }" type="hidden">
          <t:departComboTree id="departCombo" nameInput="useDepartName" width="441" idInput="useDepartId" value="${tBReportHallUsePage.useDepartName}"></t:departComboTree>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">使用单位名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 使用目的: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="usePurpose" name="usePurpose" placeholder="请填写使用目的" datatype="byterange" max="100" min="1" style="width: 435px" class="inputxt" rows="3">${tBReportHallUsePage.usePurpose}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">使用目的</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 使用时间: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="beginUseTime" name="beginUseTime" type="text" style="width: 435px" datatype="*" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBReportHallUsePage.beginUseTime}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">使用时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 联系人: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="contactId" name="contactId" type="hidden" style="width: 155px" class="inputxt" value='${tBReportHallUsePage.contactId}'>
          <input id="contactName" name="contactName" type="text" style="width: 145px" class="inputxt" value='${tBReportHallUsePage.contactName}' datatype="byterange" max="36" min="1">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">联系人</label>
        </td>
        <td align="right" width="90px">
          <label class="Validform_label"> 联系电话: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="contactPhone" name="contactPhone" type="text" datatype="byterange" max="20" min="1" style="width: 150px" class="inputxt" value='${tBReportHallUsePage.contactPhone}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">联系电话</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 拟参加人员: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="attendeeName" name="attendeeName" type="text" style="width: 435px" class="inputxt" value='${tBReportHallUsePage.attendeeName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">拟参加人员</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">
            需学术报告
            <br>
            厅准备事宜:
          </label>
        </td>
        <td class="value" colspan="3">
          <textarea id="prepareThings" name="prepareThings" placeholder="请填写需学术报告厅准备事宜" datatype="byterange" max="500" min="0" style="width: 435px" class="inputxt" rows="3">${tBReportHallUsePage.prepareThings}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">需学术报告厅准备事宜</label>
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBReportHallUsePage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBReportHallUsePage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tBReportHallUse" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/reporthall/tBReportHallUse.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $('#reportHallId').attr('style', 'width:441px;');
  });
</script>
</html>