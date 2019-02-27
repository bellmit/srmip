<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>公用提醒接收人表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    
</script>
</head>
<c:set var="now" value="<%=new java.util.Date()%>" />
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tOWarnController.do?finishWarn">
    <input id="id" name="id" type="hidden" value="${tOWarnReceivePage.id }">
    <input id="warnId" name="warnId" type="hidden" value="${warnId}">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width: 25%;" align="right"><label class="Validform_label">是否完成:</label></td>
        <td style="width: 75%;" class="value"><t:codeTypeSelect type="select" code="SFBZ" codeType="0"
            id="finishFlag" name="finishFlag" extendParam="{style:'width:156px;'}" defaultVal="1"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否完成</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">完成时间:</label></td>
        <td class="value"><input id="finishDate" name="finishDate" type="text" style="width: 150px" class="Wdate"
          onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
          value='<fmt:formatDate value='${now }' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">完成时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">备注:</label></td>
        <td class="value"><textarea rows="4" style="width: 75%;" id="memo" name="memo">${tOWarnReceivePage.memo }</textarea>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
      </tr>
    </table>

  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/warn/tOWarn.js"></script>