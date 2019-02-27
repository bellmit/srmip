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
		$("#jflx").combotree({
			url : 'tBXmlbController.do?getJflx',
			valueField : 'id',
			textField : 'jflxmc',
			onChange : function() {
				var jflx = $("#jflx").combobox('getValue');
				$("#jflxId").val(jflx);
			}
		});

		var id = $("#id").val();
		var url = 'tBXmlbController.do?getXmlbList';
		if (id != '') {
			url = url + '&id=' + id;
		}
		$("#parentType").combobox({
			url : url,
			valueField : 'id',
			textField : 'text',
			onChange : function() {
				$("#zgdwtr").show();
				$("#jflxtr").show();
				$("#xmlxtr").show();
				$("#xmxztr").show();
				$("#xmlytr").show();
				$("#kjbmgztr").show();
				$("#bztr").show();
			}
		});
	});
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBXmlbController.do?doAdd" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tBXmlx.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">项目类别：</label>
					<font color="red">*</font>
				</td>
				<td class="value">
					<input id="xmlb" name="xmlb" type="text" style="width: 350px" class="inputxt" datatype="*1-25">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">项目类别</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">父类型：</label>
				</td>
				<td class="value">
					<select id="parentType" name="parentType.id" style="width: 350px"></select>
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">项目类型</label>
				</td>
			</tr>
			<tr id="zgdwtr" style="display:none">
				<td align="right">
					<label class="Validform_label">主管单位：</label>
				</td>
				<td class="value">
					<input id="zgdw" name="zgdw" type="text" style="width: 350px" class="inputxt">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">主管单位</label>
				</td>
			</tr>
			<tr id="jflxtr" style="display:none">
				<td align="right">
					<label class="Validform_label">经费类型：</label>
				</td>
				<td class="value">
					<input id="jflx" name="jf.id" style="width: 350px; height: 27px;">
            		<input id="jflxId" name="jflx" type="hidden">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">经费类型</label>
				</td>
			</tr>
			<tr id="xmlxtr" style="display:none">
				<td align="right">
					<label class="Validform_label">项目类型：</label>
				</td>
				<td class="value">
					<t:codeTypeSelect id="xmlx" name="xmlx" type="select" code="XMLX" codeType="0" extendParam="{style:'width:350px'}"></t:codeTypeSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">项目类型</label>
				</td>
			</tr>
			<tr id="xmxztr" style="display:none">
				<td align="right">
					<label class="Validform_label">项目性质：</label>
				</td>
				<td class="value">
					<t:codeTypeSelect id="xmxz" name="xmxz" type="select" code="XMXZ" codeType="0" extendParam="{style:'width:350px'}"></t:codeTypeSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">项目性质</label>
				</td>
			</tr>
			<tr id="xmlytr" style="display:none">
				<td align="right">
					<label class="Validform_label">项目来源：</label>
				</td>
				<td class="value">
					<t:codeTypeSelect id="xmly" name="xmly" type="select" code="XMLY" codeType="0" extendParam="{style:'width:350px'}"></t:codeTypeSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">项目来源</label>
				</td>
			</tr>
			<tr id="kjbmgztr" style="display:none">
				<td align="right">
					<label class="Validform_label">会计编码规则：</label>
				</td>
				<td class="value">
					<input id="kjbmgz" name="kjbmgz" type="text" style="width: 350px" class="inputxt">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">会计编码规则</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">唯一编号：<font color="red">*</font></label>
				</td>
				<td class="value">
					<input id="wybh" name="wybh" type="text" style="width: 350px" class="inputxt" datatype="n1-6">
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">唯一编号</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">排&nbsp;序&nbsp;&nbsp;码：<font color="red">*</font></label>
				</td>
				<td class="value">
					<input id="sortCode" name="sortCode" type="text" style="width: 350px" class="inputxt" datatype="n1-6">
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">排序码</label>
				</td>
			</tr>
			<tr id="bztr" style="display:none">
				<td align="right">
					<label class="Validform_label">备注：</label>
				</td>
				<td class="value">
					<textarea id="bz" name="bz" style="width: 350px; height: 80px;"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>
<script type="text/javascript">
	function tipWybh(){
		tip("唯一编号重复");
	}
</script>