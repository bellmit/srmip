<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>表单填写说明</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFormTipController.do?doAddUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBFormTipPage.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" border="0" class="formtable">
      <tr>
        <td align="right" ><label class="Validform_label"> 业务代码: </label></td>
        <td class="value"><input id="businessCode" name="businessCode" type="text" style="width: 500px" class="inputxt" value='${tBFormTipPage.businessCode}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">业务代码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 业务名称: </label></td>
        <td class="value" colspan="5"><input id="businessName" name="businessName" type="text" style="width: 500px" class="inputxt" value='${tBFormTipPage.businessName}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">业务名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 填写说明: </label></td>
        <td class="value" colspan="5"><t:ckeditor name="tipContent" isfinder="true" value="${tBFormTipPage.tipContent}" type="width:750"></t:ckeditor> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">填写说明</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
