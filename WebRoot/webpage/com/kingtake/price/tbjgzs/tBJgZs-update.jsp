<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>价格知识库</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgZsController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgZsPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgZsPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgZsPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgZsPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgZsPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgZsPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgZsPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 类别: </label></td>
        <td class="value"><input id="lb" name="lb" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="10" value='${tBJgZsPage.lb}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">类别</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 标题: </label></td>
        <td class="value"><input id="bt" name="bt" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgZsPage.bt}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 内容: </label></td>
        <td class="value"><textarea id="nr" style="width: 400px;" class="inputxt" rows="8" name="nr" datatype="byterange" min="0" max="4000">${tBJgZsPage.nr}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">内容</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgzs/tBJgZs.js"></script>