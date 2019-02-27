<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>价格库系统_差旅费</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzClController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzClPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzClPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzClPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzClPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzClPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzClPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzClPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 地区: </label></td>
        <td class="value"><input id="dq" name="dq" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="50" value='${tBJgbzClPage.dq}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">地区</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 大军区职以上以及相当职务人员: </label></td>
        <td class="value"><input id="djqz" name="djqz" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgbzClPage.djqz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">大军区职以上以及相当职务人员</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 军职以及相当职务人员: </label></td>
        <td class="value"><input id="jz" name="jz" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgbzClPage.jz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">军职以及相当职务人员</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 师职以及相当职务人员: </label></td>
        <td class="value"><input id="sz" name="sz" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgbzClPage.sz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">师职以及相当职务人员</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 团职以下以及相当职务人员: </label></td>
        <td class="value"><input id="tz" name="tz" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgbzClPage.tz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">团职以下以及相当职务人员</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 伙食补助费标准: </label></td>
        <td class="value"><input id="hsbz" name="hsbz" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgbzClPage.hsbz}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">伙食补助费标准</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 市内交通费标准: </label></td>
        <td class="value"><input id="snjt" name="snjt" type="text" style="width: 350px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgbzClPage.snjt}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">市内交通费标准</label></td>
      </tr>
      <%--       <tr>
        <td align="right"><label class="Validform_label"> 执行时间: </label></td>
        <td class="value"><input id="zxsj" name="zxsj" type="text" style="width: 350px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgbzClPage.zxsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">执行时间</label></td>
      </tr> --%>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzcl/tBJgbzCl.js"></script>