<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>工时费率标准</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzGsflController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzGsflPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzGsflPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzGsflPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzGsflPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzGsflPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzGsflPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzGsflPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位: </label></td>
        <td class="value"><input id="dw" name="dw" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgbzGsflPage.dw}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 小时费率: </label></td>
        <td class="value"><input id="xsfl" name="xsfl" type="text" style="width: 400px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzGsflPage.xsfl}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">小时费率</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><textarea id="beiz" style="width: 400px;" class="inputxt" rows="8" name="beiz" datatype="byterange" min="0" max="500">${tBJgbzGsflPage.beiz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">备注</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzgsfl/tBJgbzGsfl.js"></script>