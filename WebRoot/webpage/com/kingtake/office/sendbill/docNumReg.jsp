<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>发文呈批单归档人选择</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function getDocNum() {
        var docNum = {};
        docNum['fileNumPrefix'] = $("#fileNumPrefix").val();
        docNum['sendYear'] = $("#sendYear").val();
        docNum['sendNum'] = $("#sendNum").val();
        return docNum;
    }

    function checkForm() {
        var fileNumPrefix = $("#fileNumPrefix").val();
        var sendYear = $("#sendYear").val();
        var sendNum = $("#sendNum").val();
        if(sendYear==""){
            tip("年份不能为空！");
            return false;
        }
        if(!/\d{2}/.test(sendYear)){
            tip("年份不合法！");
            return false;
        }
        if(sendNum==""){
            tip("后缀不能为空！");
            return false;
        }
        if(sendNum.length>20){
            tip("后缀不能超过20个字符！");
            return false;
        }
        return true;
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="">
    <table cellpadding="0" cellspacing="1" class="formtable" style="line-height: 30px; font-size: 50px;">
      <tr>
        <td align="right"><label class="Validform_label">
            公文编号:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="fileNumPrefix" name="fileNumPrefix" type="text" value="${fileNumMap.prefix}" style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;"
            class="inputxt"> ﹝20<input id="sendYear" name="sendYear" type="text" value="${fileNumMap.year}"
            style="width: 20px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt">﹞ <input id="sendNum" name="sendNum" type="text"
            style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value="${fileNumMap.fileNum}">号 <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">公文编号</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
</html>