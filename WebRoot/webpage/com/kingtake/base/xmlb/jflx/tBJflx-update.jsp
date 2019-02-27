<%@ page language="java"
	import="java.util.*,com.kingtake.common.constant.ProjectConstant"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>T_B_PROJECT_TYPE</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(function(){
		var id = $("#id").val();
		var url = 'tBJflxController.do?getJflxList';
		if(id!=''){
			url = url+'&id='+id;
		}
		$("#parentType").combobox({
			url:url,
			valueField: 'id',    
			textField: 'text',
			onLoadSuccess : function() {
				$("#parentType").combobox('setValue',$("#parentTypeId").val());
			},
			onChange : function() {
				var parentType = $("#parentType").combobox('getValue');
				$("#parentTypeId").val(parentType);
			}
		}); 
	});
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="tBJflxController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tBJflx.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">经费类型名称：<font color="red">*</font></label>
				</td>
				<td class="value">
					<input id="jflxmc" name="jflxmc" type="text" class="inputxt" datatype="*1-25" value="${tBJflx.jflxmc}">
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">项目类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">父类型：</label>
				</td>
				<td class="value">
					<select id="parentType" style="width: 156px"></select>
					<input id="parentTypeId" name="parentType.id" type="hidden" class="inputxt" datatype="*1-32" value="${tBJflx.parentType.id}">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">项目类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">排&nbsp;序&nbsp;&nbsp;码：<font color="red">*</font></label>
				</td>
				<td class="value">
					<input id="code" name="code" type="text" style="width: 150px" class="inputxt" datatype="n1-6" value="${tBJflx.code}">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">排序码</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
