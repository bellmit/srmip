<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专家咨询费标准</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzZjzxController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzZjzxPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzZjzxPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzZjzxPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzZjzxPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzZjzxPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzZjzxPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzZjzxPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;咨询专家: </label></td>
<%--         <td class="value"><input id="zxzj" name="zxzj" type="text" style="width: 350px" class="inputxt" value='${tBJgbzZjzxPage.zxzj}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">咨询专家</label></td> --%>
          
          <td class="value"><t:codeTypeSelect name="zxzj" type="select" codeType="1" code="ZXZJ" id="zxzj" extendParam="{style:\"width:355px;\"}" defaultVal="${tBJgbzZjzxPage.zxzj}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">咨询专家</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 咨询方式: </label></td>
<%--         <td class="value"><input id="zxfs" name="zxfs" type="text" style="width: 350px" class="inputxt" value='${tBJgbzZjzxPage.zxfs}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">咨询方式</label></td> --%>
          
                    <td class="value"><t:codeTypeSelect name="zxfs" type="select" codeType="1" code="ZXFS" id="zxfs" extendParam="{style:\"width:355px;\"}" defaultVal="${tBJgbzZjzxPage.zxfs}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">咨询方式</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 标准: </label></td>
        <td class="value"><textarea id="bz" style="width: 350px;" class="inputxt" rows="6" name="bz" datatype="byterange" min="0" max="400">${tBJgbzZjzxPage.bz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">标准</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><textarea id="beiz" style="width: 350px;" class="inputxt" rows="6" name="beiz" datatype="byterange" min="0" max="500">${tBJgbzZjzxPage.beiz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">备注</label></td>
      </tr>
<%--       <tr>
        <td align="right"><label class="Validform_label"> 执行时间: </label></td>
        <td class="value"><input id="zxsj" name="zxsj" type="text" style="width: 350px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgbzZjzxPage.zxsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">执行时间</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr> --%>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzzjzx/tBJgbzZjzx.js"></script>