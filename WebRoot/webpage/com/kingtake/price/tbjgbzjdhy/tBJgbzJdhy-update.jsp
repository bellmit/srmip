<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>军队会议费</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzJdhyController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzJdhyPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzJdhyPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzJdhyPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzJdhyPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzJdhyPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzJdhyPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzJdhyPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 招待所类别: </label></td>
        <td class="value"><input id="zdslb" name="zdslb" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="100" value='${tBJgbzJdhyPage.zdslb}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">招待所类别</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 一类会议: </label></td>
        <td class="value"><input id="ylhy" name="ylhy" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzJdhyPage.ylhy}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">一类会议</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 二类会议: </label></td>
        <td class="value"><input id="elhy" name="elhy" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzJdhyPage.elhy}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">二类会议</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 四类会议: </label></td>
        <td class="value"><input id="silhy" name="silhy" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox"
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzJdhyPage.silhy}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">四类会议</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 三类会议: </label></td>
        <td class="value"><input id="sanlhy" name="sanlhy" type="text" style="width: 350px; text-align: right;" class="easyui-numberbox"
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgbzJdhyPage.sanlhy}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">三类会议</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><textarea id="beiz" style="width: 350px;" class="inputxt" rows="6" name="beiz" datatype="byterange" min="0" max="500">${tBJgbzJdhyPage.beiz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">备注</label></td>
      </tr>
<%--       <tr>
        <td align="right"><label class="Validform_label"> 执行时间: </label></td>
        <td class="value"><input id="zxsj" name="zxsj" type="text" style="width: 350px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgbzJdhyPage.zxsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">执行时间</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr> --%>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzjdhy/tBJgbzJdhy.js"></script>