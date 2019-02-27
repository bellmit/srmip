<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>科研采购计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  $(document).ready(function() {
  });
  
  function formCallback(data){
      var win = frameElement.api.opener;
      win.tip(data.msg);
      if(data.success){
         win.reloadDetails();
         frameElement.api.close();
      }
  }
  
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tBPurchasePlanMainController.do?doUpdateDetail" callback="@Override formCallback">
    <input id="id" name="id" type="hidden" value="${tBPurchasePlanDetailPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBPurchasePlanDetailPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBPurchasePlanDetailPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBPurchasePlanDetailPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBPurchasePlanDetailPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBPurchasePlanDetailPage.updateName }">
    <input id="purchasePlanId" name="purchasePlanId" type="hidden" value="${tBPurchasePlanDetailPage.purchasePlanId }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 采购计划名称: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="planName" name="planName" datatype="*1-100" type="text" style="width: 350px" value='${tBPurchasePlanDetailPage.planName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">采购计划名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">采购概算:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="purchaseEstimates" name="purchaseEstimates" type="text" datatype="/^([1-9]\d{0,11}|0)(\.\d{1,2})?$/" errorMsg="请输入一个整数部分最多10位，小数部分最多2位的金额" value='${tBPurchasePlanDetailPage.purchaseEstimates}' style="width: 150px;">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">采购概算</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">采购方式:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <t:codeTypeSelect name="purchaseMode" labelText="请选择" type="select" codeType="1" code="CGFS" id="purchaseMode" extendParam="{'datatype':'*','style':'width:156px;'}" defaultVal="${tBPurchasePlanDetailPage.purchaseMode }"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">采购方式</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">采购来源:&nbsp;&nbsp;&nbsp;</label>
        </td>
        <td class="value">
          <input id="purchaseSource" name="purchaseSource" type="text" style="width: 350px" datatype="*0-100" class="inputxt" value='${tBPurchasePlanDetailPage.purchaseSource}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">采购来源</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">采购理由:&nbsp;&nbsp;&nbsp;</label>
        </td>
        <td class="value">
          <textarea id="purchaseReason" name="purchaseReason" cols="3" rows="3" style="width: 350px" datatype="*0-100" class="inputxt" >${tBPurchasePlanDetailPage.purchaseReason}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">采购理由</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMain.js"></script>