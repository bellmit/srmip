<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>环境试验收费标准</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzHjsysfController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzHjsysfPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzHjsysfPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzHjsysfPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzHjsysfPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzHjsysfPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzHjsysfPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzHjsysfPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="5" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;试验项目: </label></td>
        <td class="value"><textarea id="syxm" style="width: 400px;" class="inputxt" rows="4" name="syxm" datatype="byterange" min="0" max="1000">${tBJgbzHjsysfPage.syxm}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">试验项目</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 收费标准: </label></td>
        <td class="value"><textarea id="sfbz" style="width: 400px;" class="inputxt" rows="4" name="sfbz" datatype="byterange" min="0" max="1000">${tBJgbzHjsysfPage.sfbz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">收费标准</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 收费单位: </label></td>
        <td class="value"><input id="sfdw" name="sfdw" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="100" value='${tBJgbzHjsysfPage.sfdw}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">收费单位</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 试验设备: </label></td>
        <td class="value"><textarea id="sysb" style="width: 400px;" class="inputxt" rows="4" name="sysb" datatype="byterange" min="0" max="500">${tBJgbzHjsysfPage.sysb}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">试验设备</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><textarea id="beiz" style="width: 400px;" class="inputxt" rows="4" name="beiz" datatype="byterange" min="0" max="500">${tBJgbzHjsysfPage.beiz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">备注</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzhjsysf/tBJgbzHjsysf.js"></script>