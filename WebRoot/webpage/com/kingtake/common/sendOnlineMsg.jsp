<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>发送消息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
    var userId = frameElement.api.data.userId;
    var userName = frameElement.api.data.userName;
    var msgType = frameElement.api.data.msgType;
    if(userId!=""){
        $("#userId").val(userId);
    }
    if(userName!=""){
        $("#realName").val(userName);
        $("#msgTitle").val("["+userName+"]向您发了一条消息");
    }
    if(msgType!=""){
        $("#msgType").val(msgType);
    }
});
</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" layout="table" action="commonMessageController.do?sendMsg" tiptype="1">
    <input id="msgType" name="msgType" type="hidden">
    <input id="msgTitle" name="msgTitle" type="hidden" >
    <input id="userId" name="userId" type="hidden" >
    <input id="realName" name="realName" type="hidden" >
    <table style="width: 425px;" cellpadding="0" cellspacing="1" class="formtable">
      <!-- <tr>
        <td align="left" ><label class="Validform_label">请填写消息内容：</label></td>
      </tr> -->
      <tr>
        <td class="value" >
          <textarea rows="5" style="width: 400px;height: 200px;margin-left: 15px;" placeholder="请填写消息内容！" title="请填写消息内容！" id="msgContent" name="msgContent"></textarea>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
</html>