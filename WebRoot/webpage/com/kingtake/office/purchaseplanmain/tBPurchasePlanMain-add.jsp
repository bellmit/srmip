<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>科研采购计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  $(document).ready(function() {
    $('#tt').tabs({
      onSelect : function(title) {
        $('#tt .panel-body').css('width', 'auto');
      }
    });
    $(".tabs-wrap").css('width', '100%');
  });

  function setPlanDate() {
    $('#planDate').val($('#planDateText').val() + "-01");
  }
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tBPurchasePlanMainController.do?doAdd" beforeSubmit="setPlanDate();">
    <input id="id" name="id" type="hidden" value="${tBPurchasePlanMainPage.id }">
    <input id="dutyDepartId" name="dutyDepartId" type="hidden" value="${tBPurchasePlanMainPage.dutyDepartId }">
    <input id="managerId" name="managerId" type="hidden" value="${tBPurchasePlanMainPage.managerId }">
    <input id="createBy" name="createBy" type="hidden" value="${tBPurchasePlanMainPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBPurchasePlanMainPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBPurchasePlanMainPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBPurchasePlanMainPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBPurchasePlanMainPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBPurchasePlanMainPage.updateDate }">
    <input id="planDate" name="planDate" type="hidden" value="${tBPurchasePlanMainPage.planDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 计划时间: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="planDateText" name="planDateText" datatype="*" type="text" style="width: 350px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">计划时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">责任单位:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="dutyDepartName" name="dutyDepartName" type="hidden" datatype="*">
          <t:departComboTree id="ddn" idInput="dutyDepartId" nameInput="dutyDepartName" lazy="false" width="355"></t:departComboTree>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">责任单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">负责人:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="managerName" name="managerName" type="text" datatype="*1-36" style="width: 350px" class="inputxt" readonly="readonly">
          <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" inputTextname="managerId,managerName" idInput="managerId" mode="single"></t:chooseUser>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">负责人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">项目编码:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="projectCode" name="projectCode" type="text" datatype="s2-20" style="width: 350px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目编码</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">项目名称:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="projectName" name="projectName" type="text" datatype="*1-200" style="width: 350px" class="inputxt">
          <t:choose url="tPmProjectController.do?projectSelect&multiply=1" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="projectId,projectName"
              isclear="true"></t:choose>
          <input id="projectId"  type="hidden" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">总经费:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="totalFunds" name="totalFunds" type="text" datatype="*" class="easyui-numberbox" data-options="min:1,max:100000000000,precision:2,groupSeparator:','" style="width: 350px; text-align: right;">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">总经费</label>
        </td>
      </tr>
    </table>
    
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMain.js"></script>