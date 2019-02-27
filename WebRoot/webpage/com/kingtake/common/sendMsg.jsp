<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>发送消息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
    var msgType = frameElement.api.data.msgType;
    if(msgType!=""){
        $("#msgType").val(msgType);
    }
});
</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" layout="table" action="commonMessageController.do?sendMsg" tiptype="1">
    <input id="msgType" name="msgType" type="hidden">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
        <td align="right" ><label class="Validform_label">消息标题：<font color="red">*</font></label></td>
        <td class="value" ><input id="msgTitle" name="msgTitle" type="text" style="width: 400px;"></td>
      </tr>
      <tr>
        <td align="right" ><label class="Validform_label">消息内容：</label></td>
        <td class="value" ><textarea rows="5" style="width: 400px;height: 200px;" id="msgContent" name="msgContent"></textarea></td>
      </tr>
      <tr>
        <td align="right" width="160px"><label class="Validform_label">
            接收人:<font color="red">*</font>
          </label></td>
        <td class="value" ><input type="hidden" name="nextUser" id="nextUser"> <input id="realName" style="width: 350px;" class="inputxt" name="realName" readonly="readonly"
            datatype="*"></input> <input id="userId" name="userId" type="hidden"></input> <input id="departId" type="hidden"></input> <t:chooseUser icon="icon-search" title="人员列表"
            textname="id,userName,realName,departId" isclear="true" inputTextname="userId,nextUser,realName,departId" idInput="userId" mode="multiply"></t:chooseUser> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">接收人</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
</html>