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
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tOWarnController.do?doAddReceiveUser">
    <input id="warnId" name="warnId" type="hidden" value="${warnId}">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width: 25%;" align="right"><label class="Validform_label">接收人:</label></td>
        <td style="width: 75%;" class="value"><input type="hidden" id="receiveUserids" name="receiveUserids"
          value="${receiveUserIds }"> <input type="text" id="receiveUsernames" name="receiveUsernames"
          value="${receiveUserNames }" class="inputxt"> <t:chooseUser idInput="receiveUserids"
            inputTextname="receiveUserids,receiveUsernames" icon="icon-search" title="用户列表" textname="id,realName"
            isclear="true"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">接收人</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/warn/tOWarn.js"></script>