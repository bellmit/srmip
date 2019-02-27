<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>假前工作计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOHolidayPlanController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tOHolidayPlanPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tOHolidayPlanPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOHolidayPlanPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOHolidayPlanPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOHolidayPlanPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOHolidayPlanPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOHolidayPlanPage.updateDate }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 标题: <font color="red">*</font></label></td>
        <td class="value"><input id="title" name="title" type="text" style="width: 400px" class="inputxt" value='${tOHolidayPlanPage.title}' datatype="*1-100"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 假期: <font color="red">*</font></label></td>
        <td class="value"><t:codeTypeSelect name="holidayCode" type="select" codeType="1" code="JQ" id="holidayCode" defaultVal="${tOHolidayPlanPage.holidayCode}" labelText="请选择"
            extendParam="{'style':'width:156px;','datatype':'*'}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">假期编码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 单位名称: </label></td>
        <td class="value"><input id="deptName" name="deptName" type="hidden"  value='${tOHolidayPlanPage.deptName}'> <input id="deptId" name="deptId"
            type="hidden" value='${tOHolidayPlanPage.deptId}'> <t:departComboTree name="" id="tt" idInput="deptId" nameInput="deptName" lazy="false" value="${tOHolidayPlanPage.deptId}" width="156"></t:departComboTree>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 计划内容: <font color="red">*</font></label></td>
        <td class="value"><textarea id="planContent" style="width: 400px; height: 200px;" class="inputxt" rows="6" name="planContent" datatype="byterange" max="4000" min="1">${tOHolidayPlanPage.planContent}</textarea> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">计划内容</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/holiday/tOHolidayPlan.js"></script>