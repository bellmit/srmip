<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>设计用品费</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzSjypController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzSjypPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzSjypPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzSjypPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzSjypPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzSjypPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzSjypPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzSjypPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">      
      <tr>
        <td align="right"><label class="Validform_label"> 标准: </label></td>
        <td class="value"><textarea id="bz" style="width: 450px;" class="inputxt" rows="8" name="bz" datatype="byterange" min="0" max="200">${tBJgbzSjypPage.bz}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标准</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzsjyp/tBJgbzSjyp.js"></script>