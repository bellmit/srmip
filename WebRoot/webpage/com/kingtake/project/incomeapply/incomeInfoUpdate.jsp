<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>到账分配信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
    var checkFlag;
    function checkAmount() {
        var balance = Number($('#incomeBalance').val());
        var amount = Number($('#amount').val());
        if (balance < amount) {
            $.Showmsg("指定金额不得大于可分配金额，请重新填写分配金额！");
            checkFlag = false;
            return false;
        }
        checkFlag = true;
        return true;
    }

    function getCheckFlag() {
        var form = $("#formobj").Validform();
        if (checkFlag) {
            checkFlag = form.check(false);
        }
        return checkFlag;
    }
    
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmIncomeAllotController.do?doUpdate" beforeSubmit="checkAmount" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tPmIncomeAllotPage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${tPmIncomeAllotPage.projectId }">
    <input id="projectManagerId" name="projectManagerId" type="hidden" value="${tPmIncomeAllotPage.projectManagerId }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmIncomeAllotPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmIncomeAllotPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmIncomeAllotPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmIncomeAllotPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmIncomeAllotPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmIncomeAllotPage.updateDate }">
    <input id="incomeId" name="income.id" type="hidden" value="${tPmIncomeAllotPage.income.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
     <%--  <tr>
        <td align="right"><label class="Validform_label"> 可分配金额: </label></td>
        <td class="value"><input id="incomeBalance" name="incomeBalance" type="text" style="width: 150px; text-align: right;" class="inputxt" value="${income.balance}" disabled="disabled">(元)
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">可分配金额</label></td>
      </tr> --%>
      <tr>
        <td align="right"><label class="Validform_label">
            项目名称:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt" value='${tPmIncomeAllotPage.projectName}' datatype="*"> <t:chooseProject
            inputName="projectName" inputId="projectId" mode="single" fun="findInfoById"></t:chooseProject> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">项目名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 负责人: </label></td>
        <td class="value"><input id="projectManager" name="projectManager" type="text" style="width: 150px" class="inputxt" value='${tPmIncomeAllotPage.projectManager}' readonly="readonly">
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">负责人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 负责人单位: </label></td>
        <td class="value"><input id="projectMgrDept" name="projectMgrDept" type="text" style="width: 150px" class="inputxt" value='${tPmIncomeAllotPage.projectMgrDept}' readonly="readonly">
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">负责人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 分配单位: </label></td>
        <td class="value"><input id="incomeDept" name="incomeDept" type="text" style="width: 150px" class="inputxt" value='${tPmIncomeAllotPage.incomeDept}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">负责人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            分配金额:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="amount" name="amount" type="text" style="width: 150px; text-align: right;" class="inputxt" value='${tPmIncomeAllotPage.amount}' datatype="n1-10">(元) <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">分配金额</label></td>
      </tr>
    </table>
  </t:formvalid>
  
</body>
<script src="webpage/com/kingtake/project/tpmincomeallot/tPmIncomeAllot.js"></script>