<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目发票信息</title>
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
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tBPmInvoiceController.do?doSave" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBPmInvoicePage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${tBPmInvoicePage.projectId }">
    <input id="createBy" name="createBy" type="hidden" value="${tBPmInvoicePage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBPmInvoicePage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBPmInvoicePage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBPmInvoicePage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBPmInvoicePage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBPmInvoicePage.updateDate }">
    <table style="width: 400px;" cellpadding="0" cellspacing="1" class="formtable">
    	<tr>
          <td align="right">
          	<label class="Validform_label"> 票据年度: </label>
          	<font color="red">*</font>
          </td>
          <td class="value">
          	<input id="invoiceYear" name="invoiceYear" type="text" style="width: 250px" value='${tBPmInvoicePage.invoiceYear}' datatype="*1-4"> 
          	<span class="Validform_checktip"></span> 
          	<label class="Validform_label" style="display: none;">票据年度</label></td>
        </tr>
      <tr>
        <td align="right" width="100px">
          <label class="Validform_label"> 申请时间: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="applyDate" name="applyDate" type="text" datatype="date" style="width: 250px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBPmInvoicePage.applyDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申请时间</label>
        </td>
      <tr>
        <td align="right">
          <label class="Validform_label"> 发票号1: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="invoiceNum1" name="invoiceNum1" type="text" datatype="byterange" max="12" min="1" style="width: 250px" class="inputxt" datatype="byterange" max="12" min="1"
            value='${tBPmInvoicePage.invoiceNum1}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">发票号1</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 发票号2: </label>
        </td>
        <td class="value">
          <input id="invoiceNum2" name="invoiceNum2" type="text" datatype="byterange" max="12" min="0" style="width: 250px" class="inputxt" value='${tBPmInvoicePage.invoiceNum2}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">发票号2</label>
        </td>
      <tr>
        <td align="right">
          <label class="Validform_label"> 发票金额(元): </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="invoiceAmount" name="invoiceAmount" type="text" style="width: 250px; text-align: right;" class="easyui-numberbox"
            data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tBPmInvoicePage.invoiceAmount}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">发票金额</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 备注: </label>
        </td>
        <td class="value">
          <textarea id="memo" name="memo" placeholder="请填写备注" datatype="byterange" max="200" min="0" style="width: 250px" class="inputxt" rows="4">${tBPmInvoicePage.memo}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
        <td align="right">
          <label class="Validform_label"> </label>
        </td>
        <td class="value"></td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 附件: </label>
        </td>
        <td class="value">
          <table style="max-width: 92%;" id="fileShow">
            <c:forEach items="${tBPmInvoicePage.certificates }" var="file" varStatus="idx">
              <tr>
                <td>
                  <a href="javascript:void(0)" >${file.attachmenttitle}</a>
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                </td>
                <td style="width: 40px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <input type="hidden" value="${tBPmInvoicePage.attachmentCode}" id="bid" name="attachmentCode">
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" extend="*.png;*.jpg;*.gif;" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tBPmInvoice"></t:upload>
          </div>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 附件 </label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/invoice/tBPmInvoice.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>