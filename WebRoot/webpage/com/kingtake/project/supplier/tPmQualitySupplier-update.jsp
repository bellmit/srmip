<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>供方名录信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmQualitySupplierController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tPmQualitySupplierPage.id }">
    <input id="validFlag" name="validFlag" type="hidden" value="${tPmQualitySupplierPage.validFlag }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmQualitySupplierPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmQualitySupplierPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmQualitySupplierPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmQualitySupplierPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmQualitySupplierPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmQualitySupplierPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            供方名称: <font color="red">*</font>
          </label></td>
        <td class="value" colspan="3"><input id="supplierName" name="supplierName" type="text" datatype="byterange" max="60" min="1" style="width: 508px" class="inputxt"
            value='${tPmQualitySupplierPage.supplierName}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">供方名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:&nbsp;&nbsp;&nbsp; </label></td>
        <td class="value" colspan="3"><t:codeTypeSelect id="supplierType" name="supplierType" defaultVal="${tPmQualitySupplierPage.supplierType}" type="select" code="GFLB" codeType="1" extendParam="{'style':'width:514px;'}" labelText="请选择"></t:codeTypeSelect> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">类别</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 通信地址:&nbsp;&nbsp;&nbsp; </label></td>
        <td class="value" colspan="3"><input id="supplierAddress" name="supplierAddress" datatype="byterange" max="500" min="0" type="text" style="width: 508px" class="inputxt"
            value='${tPmQualitySupplierPage.supplierAddress}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">通信地址</label></td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label">
            联&nbsp;系&nbsp;人&nbsp;: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="supplierContact" name="supplierContact" type="text" datatype="byterange" max="50" min="1" style="width: 150px" class="inputxt"
            value='${tPmQualitySupplierPage.supplierContact}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系人</label></td>
        <td align="right"><label class="Validform_label">
            联系电话: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="supplierPhone" name="supplierPhone" type="text" datatype="*1-50" style="width: 150px" class="inputxt" value='${tPmQualitySupplierPage.supplierPhone}'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">联系电话</label></td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label"> 邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编:&nbsp;&nbsp;&nbsp; </label></td>
        <td class="value"><input id="supplierPostcode" name="supplierPostcode" datatype="n0-6" type="text" style="width: 150px" class="inputxt" value='${tPmQualitySupplierPage.supplierPostcode}'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">邮编</label></td>
        <td align="right"><label class="Validform_label"> 传&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;真:&nbsp;&nbsp;&nbsp; </label></td>
        <td class="value"><input id="supplierFax" name="supplierFax" datatype="byterange" max="50" min="0" type="text" style="width: 150px" class="inputxt"
            value='${tPmQualitySupplierPage.supplierFax}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">传真</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 承制资格:&nbsp;&nbsp;&nbsp; </label></td>
        <td class="value">
            <input id="isQualifyY" name="isQualify" type="radio" value='1' <c:if test="${tPmQualitySupplierPage.isQualify eq 1}">checked="true"</c:if>><label for="isQualifyY">是</label>
            <input id="isQualifyN" name="isQualify" type="radio"  value='0' <c:if test="${tPmQualitySupplierPage.isQualify ne 1}">checked="true"</c:if>><label for="isQualifyN">否 </label>
            <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承制资格</label>
         </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 临时供方:&nbsp;&nbsp;&nbsp; </label></td>
        <td class="value">
          <input id="isTempY" name="isTemp" type="radio"  value='1' <c:if test="${tPmQualitySupplierPage.isTemp eq 1}">checked="true"</c:if>><label for="isTempY">是</label>
          <input id="isTempN" name="isTemp" type="radio" value='0' <c:if test="${tPmQualitySupplierPage.isTemp ne 1}">checked="true"</c:if>><label for="isTempN">否</label>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">传真</label>
         </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            提供产品: <font color="red">*</font>
          </label></td>
        <td class="value" colspan="3"><textarea id="supplierProduct" name="supplierProduct" cols="3" rows="4" datatype="byterange" max="500" min="1" style="width: 508px;height: 100px;" class="inputxt">${tPmQualitySupplierPage.supplierProduct}</textarea>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提供产品</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/supplier/tPmQualitySupplier.js"></script>