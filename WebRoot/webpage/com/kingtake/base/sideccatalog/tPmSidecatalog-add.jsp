<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目模块配置表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmSidecatalogController.do?doAdd" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tPmSidecatalogPage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 标题: </label>
        </td>
        <td class="value" colspan="5">
          <input id="title" name="title" type="text" style="width: 500px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">标题</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 等级: </label>
        </td>
        <td class="value">
          <input id="nodelevel" name="level" type="text" style="width: 50px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">等级</label>
        </td>
        <td align="right"  width="40">
          <label class="Validform_label"> 序号: </label>
        </td>
        <td class="value">
          <input id="seriaNumber" name="index" type="text" style="width: 50px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">序号</label>
        </td>
        <td align="right" width="90">
          <label class="Validform_label"> 业务代码: </label>
        </td>
        <td class="value">
          <input id="businessCode" name="businessCode" type="text" style="width: 190px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">业务代码</label>
        </td>
      </tr>
      <tr>
        <td align="right" width="60">
          <label class="Validform_label"> 页面url: </label>
        </td>
        <td class="value" colspan="5">
          <input id="url" name="url" type="text" style="width: 500px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">页面url</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/base/sideccatalog/tPmSidecatalog.js"></script>