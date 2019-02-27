<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>中央和国家机关会议费</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzZyhgjhyController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzZyhgjhyPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzZyhgjhyPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzZyhgjhyPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzZyhgjhyPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzZyhgjhyPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzZyhgjhyPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzZyhgjhyPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;会议类型: </label></td>
        <td class="value"><input id="hylx" name="hylx" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="50" value='${tBJgbzZyhgjhyPage.hylx}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会议类型</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 住宿费: </label></td>
        <td class="value"><input id="zsf" name="zsf" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzZyhgjhyPage.zsf}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">住宿费</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 伙食费: </label></td>
        <td class="value"><input id="hsf" name="hsf" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzZyhgjhyPage.hsf}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">伙食费</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 其他费用: </label></td>
        <td class="value"><input id="qt" name="qt" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzZyhgjhyPage.qt}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">其他费用</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzzyhgjhy/tBJgbzZyhgjhy.js"></script>