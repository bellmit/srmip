<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专利奖励</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
  <div style="margin: 0 auto; width: 600px;">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tZZljlController.do?doUpdate" tiptype="1">
      <input id="id" name="id" type="hidden" value="${tZZljlPage.id }">
      <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right"><label class="Validform_label">
              专利类型: <font color="red">*</font>
            </label></td>
          <td class="value">
          <t:codeTypeSelect name="zllx" type="select" codeType="1" code="ZLLX" id="zllx" defaultVal="${tZZljlPage.zllx }" labelText="选择" extendParam='{"datatype":"*","style":"width: 156px;"}'></t:codeTypeSelect>
           <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">专利类型</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              奖励金额: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="jlje" name="jlje" type="text" value="${tZZljlPage.jlje }" datatype="*1-10" class="easyui-numberbox" style="width: 150px;" data-options="min:0,max:999999999,groupSeparator:','" > <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">奖励金额</label></td>
        </tr>
      </table>
    </t:formvalid>
  </div>
</body>
<script type="text/javascript">
    
</script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>