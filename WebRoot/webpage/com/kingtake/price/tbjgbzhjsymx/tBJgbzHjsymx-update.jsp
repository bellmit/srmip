<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>环境试验费标准明细表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzHjsymxController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzHjsymxPage.id }">
    <input id="syxmid" name="syxmid" type="hidden" value="${tBJgbzHjsymxPage.syxmid }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzHjsymxPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzHjsymxPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzHjsymxPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzHjsymxPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzHjsymxPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzHjsymxPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 收费标准: </label></td>
        <td class="value"><input id="sfbz" name="sfbz" type="text" style="width: 400px" class="inputxt" value='${tBJgbzHjsymxPage.sfbz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">收费标准</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 收费单位: </label></td>
        <td class="value"><input id="sfdw" name="sfdw" type="text" style="width: 400px" class="inputxt" value='${tBJgbzHjsymxPage.sfdw}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">收费单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 试验设备: </label></td>
        <td class="value"><input id="sysb" name="sysb" type="text" style="width: 400px" class="inputxt" value='${tBJgbzHjsymxPage.sysb}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">试验设备</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><textarea id="beiz" style="width: 400px;" class="inputxt" rows="8" name="beiz" datatype="byterange" min="0" max="500">${tBJgbzHjsymxPage.beiz}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzhjsymx/tBJgbzHjsymx.js"></script>