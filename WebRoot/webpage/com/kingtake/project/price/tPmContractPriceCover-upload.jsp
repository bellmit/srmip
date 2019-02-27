<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
    function callback(data) {
        var win = frameElement.api.opener;
        win.callback(serverMsg);
        frameElement.api.close();
    }
</script>
</head>

<body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload" callback="@Override callback">
    <fieldset class="step">
      <input id="tpId" type="hidden" value="${tpId}">
      <div class="form">
        <t:upload name="fiels" buttonText="选择要导入的文件" uploader="tPmContractPriceCoverController.do?importExcel"
          extend="*.xls;*.xlsx" id="file_upload" formData="tpId" dialog="false" callback="callback"></t:upload>
      </div>
      <div class="form" id="filediv" style="height: 50px"></div>
    </fieldset>
  </t:formvalid>
</body>
</html>
