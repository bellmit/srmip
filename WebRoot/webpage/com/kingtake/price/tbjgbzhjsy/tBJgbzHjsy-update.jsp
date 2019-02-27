<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>价格库系统_环境试验费标准</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzHjsyController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzHjsyPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzHjsyPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzHjsyPage.createDate }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzHjsyPage.createBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzHjsyPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzHjsyPage.updateDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzHjsyPage.updateBy }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 试验项目: </label></td>
        <td class="value"><textarea id="syxm" style="width: 500px;" class="inputxt" rows="6" name="syxm">${tBJgbzHjsyPage.syxm}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">试验项目</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzhjsy/tBJgbzHjsy.js"></script>