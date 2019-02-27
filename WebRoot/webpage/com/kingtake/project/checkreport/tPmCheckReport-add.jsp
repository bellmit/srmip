<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中期检查报告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmCheckReportController.do?doAdd" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tPmCheckReportPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmCheckReportPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmCheckReportPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmCheckReportPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmCheckReportPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmCheckReportPage.updateName }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 项目基_主键: </label></td>
        <td class="value"><input id="tpId" name="tpId" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">项目基_主键</label></td>
        <td align="right"><label class="Validform_label"> 标题: </label></td>
        <td class="value"><input id="reportTitle" name="reportTitle" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 报告起始时间: </label></td>
        <td class="value"><input id="yearStart" name="yearStart" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">报告起始时间</label></td>
        <td align="right"><label class="Validform_label"> 报告结束时间: </label></td>
        <td class="value"><input id="yearEnd" name="yearEnd" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">报告结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><input id="remark" name="remark" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">备注</label></td>
        <td align="right"><label class="Validform_label"> 修改时间: </label></td>
        <td class="value"><input id="updateDate" name="updateDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">修改时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 提交状态: </label></td>
        <td class="value"><input id="submitStatus" name="submitStatus" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">提交状态</label></td>
        <td align="right"><label class="Validform_label"> 审查状态: </label></td>
        <td class="value"><input id="checkStatus" name="checkStatus" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">审查状态</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 审查意见: </label></td>
        <td class="value"><input id="checkSuggest" name="checkSuggest" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">审查意见</label></td>
        <td align="right"><label class="Validform_label"> 审查人id: </label></td>
        <td class="value"><input id="checkUserid" name="checkUserid" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">审查人id</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 审核人姓名: </label></td>
        <td class="value"><input id="checkUsername" name="checkUsername" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">审核人姓名</label></td>
        <td align="right"><label class="Validform_label"> 审核时间: </label></td>
        <td class="value"><input id="checkDate" name="checkDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">审核时间</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/checkreport/tPmCheckReport.js"></script>