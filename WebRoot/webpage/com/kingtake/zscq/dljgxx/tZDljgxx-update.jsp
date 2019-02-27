<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>代理机构信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tZDljgxxController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tZDljgxxPage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 机构名称: <font color="red">*</font></label></td>
        <td class="value"><input id="jgmc" name="jgmc" type="text" style="width: 150px" class="inputxt" value='${tZDljgxxPage.jgmc}' datatype="*1-25"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">机构名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 代号: <font color="red">*</font></label></td>
        <td class="value"><input id="dh" name="dh" type="text" style="width: 150px" class="inputxt" value='${tZDljgxxPage.dh}' datatype="*1-25"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">代号</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 联系人: <font color="red">*</font></label></td>
        <td class="value"><input id="lxr" name="lxr" type="text" style="width: 150px" class="inputxt" value='${tZDljgxxPage.lxr}' datatype="*1-10"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 联系电话: <font color="red">*</font></label></td>
        <td class="value"><input id="lxdh" name="lxdh" type="text" style="width: 150px" class="inputxt" value='${tZDljgxxPage.lxdh}' datatype="*1-30"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系电话</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/zscq/dljgxx/tZDljgxx.js"></script>