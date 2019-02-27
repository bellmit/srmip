<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>基础标准代码类别表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
$(document).ready(function() {
		$('#tt').tabs({
			onSelect : function(title) {
				$('#tt .panel-body').css('width', 'auto');
			}
		});
		$(".tabs-wrap").css('width', '100%');
	});
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1"
    action="tBCodeTypeController.do?saveCodeType">
    <input id="id" name="id" type="hidden" value="${tBCodeTypePage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBCodeTypePage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBCodeTypePage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBCodeTypePage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBCodeTypePage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBCodeTypePage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBCodeTypePage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">类别代码:<font color="red">*</font></label></td>
        <td class="value"><input id="code" name="code" type="text" style="width: 150px" class="inputxt"
          datatype="*1-24" value="${tBCodeTypePage.code}"> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">类别代码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">类别名称:<font color="red">*</font></label></td>
        <td class="value"><input id="name" name="name" type="text" style="width: 150px" class="inputxt"
          datatype="*1-13" value="${tBCodeTypePage.name}"> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">类别名称</label></td>
      </tr>
      <input type="hidden" id="codeType" name="codeType" value="${codeType}">
      <input type="hidden" id="validFlag" name="validFlag" value="1">
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/base/code/tBCodeType.js"></script>