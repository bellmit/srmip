<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
   
});
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" 
    action="tONoticeController.do?doWarn">
    <span style="display: inline-block;text-align: center;"><font color="red" style="font-size: 18px;">注：当前提醒会定时提醒给该通知公告的创建人及接收人。</font></span>
    <input id="id" name="id" type="hidden" value="${tOWarnPage.id }">
    <input id="sourceId" name="sourceId" type="hidden" value="${tOWarnPage.sourceId }">
    <input id="createBy" name="createBy" type="hidden" value="${tOWarnPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOWarnPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOWarnPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOWarnPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOWarnPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOWarnPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">是否生效:&nbsp;&nbsp;&nbsp;</label></td>
        <td class="value">
        <label for="valid">是</label><input id="valid" name="warnStatus" type="radio" value="0" <c:if test="${tOWarnPage.warnStatus eq 0 }">checked="checked"</c:if> >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <label for="invalid">否</label><input id="invalid" type="radio" name="warnStatus" value="2" <c:if test="${tOWarnPage.warnStatus eq 2 }">checked="checked"</c:if>>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提醒时限范围:</label></td>
        <td class="value" colspan="3"><input id="warnBeginTime" name="warnBeginTime" type="text"
          style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
          value='<fmt:formatDate value='${tOWarnPage.warnBeginTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒范围开始时间</label>~
          <input id="warnEndTime" name="warnEndTime" type="text" style="width: 150px" class="Wdate"
          onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
          value='<fmt:formatDate value='${tOWarnPage.warnEndTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒范围结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;&nbsp;醒&nbsp;&nbsp;频&nbsp;&nbsp;率&nbsp;:</label></td>
        <td class="value"><t:codeTypeSelect id="warnFrequency" name="warnFrequency" codeType="0"
            code="WARNFREQUENCY" type="select" defaultVal="${tOWarnPage.warnFrequency}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒频率</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;醒&nbsp;时&nbsp;间&nbsp;点:</label></td>
        <td class="value"><input id="warnTimePoint" name="warnTimePoint" type="text" style="width: 150px"
          class="Wdate" onClick="WdatePicker({dateFmt: 'HH:mm'})"
          value='${tOWarnPage.warnTimePoint}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒时间点</label></td>
      </tr>
      <tr>
        <input id="warnWay" type="hidden" name="warnWay" value="1">
      </tr>
    </table>
  </t:formvalid>
</body>
</html>