<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>填写分配金额</title>
</head>
<body>
<table style="width: 100%;font-size:14px;" cellpadding="0" cellspacing="1" class="formtable">
	<tr>
		<td align="right" width="30%">
			<label class="Validform_label">分配金额：</label>
		</td>
		<td class="value" width="80%">
			<input id="je" name="je" type="text" maxlength="13"   class="easyui-numberbox"  data-options="min:-99999999.99,max:99999999.99,precision:2,groupSeparator:','"
				style="width: 180px; text-align: right;" value="${je}">&nbsp;元 
			<input value="${id}" id="id" type="hidden" />
			<input value="${type}" id="type" type="hidden" />
		</td>
	</tr>
</table>
<script type="text/javascript">
function getMsgText(){
	return document.getElementById("je").value;
}

function getId(){
	return document.getElementById("id").value;
}

function getType(){
	return document.getElementById("type").value;
}

function closeParent(){
	frameElement.api.opener.close();
}
</script>
</body>
</html>