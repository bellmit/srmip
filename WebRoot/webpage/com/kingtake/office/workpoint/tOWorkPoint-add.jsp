<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>工作要点</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOWorkPointController.do?doAdd" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tOWorkPointPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tOWorkPointPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOWorkPointPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOWorkPointPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOWorkPointPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOWorkPointPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOWorkPointPage.updateDate }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 时间: </label></td>
        <td class="value"><input id="time" name="time" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 工作内容: </label></td>
        <td class="value"><textarea style="width: 600px;" class="inputxt" rows="6" id="workContent" name="workContent"></textarea> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">工作内容</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 单位: </label></td>
        <td class="value"><input id="unit" name="unit" type="text" style="width: 150px" class="inputxt"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/workpoint/tOWorkPoint.js"></script>