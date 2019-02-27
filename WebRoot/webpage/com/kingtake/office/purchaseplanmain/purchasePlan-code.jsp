<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>科研采购计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  $(document).ready(function() {
	  var planIds = frameElement.api.data.planIds;
	  $("#planIds").val(planIds);
  });
  
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tBPurchasePlanMainController.do?doCreateCode" >
    <input id="planIds" name="planIds" type="hidden" value="${planIds }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 年度: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="year" name="year" datatype="*" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy'})" value='${year}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">计划时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">批次:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <select id="batch" name="batch" datatype="*" style="width: 155px;">
             <option value="1">1</option>
             <option value="2">2</option>
             <option value="3">3</option>
             <option value="4">4</option>
             <option value="5">5</option>
             <option value="6">6</option>
             <option value="7">7</option>
             <option value="8">8</option>
             <option value="9">9</option>
             <option value="10">10</option>
             <option value="11">11</option>
             <option value="12">12</option>
          </select>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">批次</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
