<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>翻译费标准</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzFyController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzFyPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzFyPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzFyPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzFyPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzFyPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzFyPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzFyPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;源语种: </label></td>
        <td class="value"><input id="yz" name="yz" type="text" style="width: 350px" class="inputxt" value='${tBJgbzFyPage.yz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">语种</label></td>

<%--         <td class="value"><t:codeTypeSelect name="yyz" type="select" codeType="1" code="YZ" id="yyz" extendParam="{style:\"width:355px;\"}" defaultVal="${tBJgbzFyPage.yyz}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">源语种</label></td> --%>
      </tr>
<%--       <tr>
        <td align="right"><label class="Validform_label"> 目标语种: </label></td>
        <td class="value"><input id="mbyz" name="mbyz" type="text" style="width: 350px" class="inputxt" value='${tBJgbzFyPage.mbyz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">目标语种</label></td>
        <td class="value"><t:codeTypeSelect name="mbyz" type="select" codeType="1" code="YZ" id="mbyz" extendParam="{style:\"width:355px;\"}" defaultVal="${tBJgbzFyPage.mbyz}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">目标语种</label></td>
      </tr> --%>
      <tr>
        <td align="right"><label class="Validform_label"> 普通类: </label></td>
        <td class="value"><input id="ptl" name="ptl" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox"
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzFyPage.ptl}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">普通类</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 技术类: </label></td>
        <td class="value"><input id="jsl" name="jsl" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzFyPage.jsl}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">技术类</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 合同条款类: </label></td>
        <td class="value"><input id="httkl" name="httkl" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzFyPage.httkl}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">合同条款类</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzfy/tBJgbzFy.js"></script>