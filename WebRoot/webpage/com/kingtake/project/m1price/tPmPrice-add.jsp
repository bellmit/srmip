<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>报价</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function() {
    $("#contractId").combobox({
      url : 'tPmPriceController.do?getContractList&projectId=${tPmPricePage.project.id}',
      valueField : 'id',
      textField : 'contractName',
      /* onLoadSuccess : function() {
        var contractId = $("#contractId").val();
        if (contractId != "") {
          $("#contractId").combobox('setValue', contractId);
        } else {
          var data = $("#contractId").combobox('getData');
          if (data.length > 0) {
            $("#contractId").combobox('setValue', data[0].id);
            $("#contractName").val(data[0].contractName);
            $("#contractType").val(data[0].contractType);
          }
        }
      }, */
      onChange : function(newValue, oldValue) {
        var data = $('#contractId').combobox('getData');
        //遍历json找到对应的属性值并赋值到对应的字段中
        for (var key in data) {
          if (newValue == data[key].id) {
            $("#contractName").val(data[key].contractName);
            $("#contractType").val(data[key].contractType);
            $("#contractTypeName").val(data[key].contractType == "<%=ProjectConstant.INCOME_CONTRACT%>" ? "进账" : "出账");
          }
        }
      }
    });
  });
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPriceController.do?doAdd" tiptype="1" tipSweep="true" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmPricePage.id }">
    <input id="projectId" name="project.id" type="hidden" value="${tPmPricePage.project.id}">
    <input id="createBy" name="createBy" type="hidden">
    <input id="createName" name="createName" type="hidden">
    <input id="createDate" name="createDate" type="hidden">
    <input id="updateBy" name="updateBy" type="hidden">
    <input id="updateName" name="updateName" type="hidden">
    <input id="updateDate" name="updateDate" type="hidden">
    <input id="contractName" name="contractName" type="hidden">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 标题: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="title" name="title" type="text" style="width: 300px" class="inputxt" datatype="*1-100" title="请填写报价标题">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">标题</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 关联合同: </label>
        </td>
        <td class="value">
          <input id="contractId" name="contractId" style="width: 305px;">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关联合同名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 合同类型: </label>
        </td>
        <td class="value">
          <input id="contractType" name="contractType" type="hidden">
          <input id="contractTypeName" name="contractTypeName" type="text" style="width: 300px" class="inputxt" disabled="disabled" readonly="readonly">
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tPmPricePage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tPmPricePage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmPrice" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/m1price/tPmPrice.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>