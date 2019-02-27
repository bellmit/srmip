<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>技术基础项目申报书-预算</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" callback="@Override reloadTechnologyFundsList"
		action="tPmDeclareFundsTechnologyController.do?doAdd" tiptype="1">
		<input id="id" name="id" add="true" type="hidden" value="${tPmDeclareFundsTechnologyPage.id }">
		<input id="projDeclareId" name="projDeclareId" type="hidden" value='${tPmDeclareFundsTechnologyPage.projDeclareId}'>
		<table style="width: 300px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 年度:<font color="red">*</font></label></td>
				<td class="value">
					<input id="budgetYear" name="budgetYear" type="text" style="width: 150px" class="Wdate" datatype="*"
			    		onClick="WdatePicker({dateFmt:'yyyy'})" value='${tPmDeclareFundsTechnologyPage.budgetYear}' > 
			    	<span class="Validform_checktip"></span> 
			    	<label class="Validform_label" style="display: none;">年度</label>
			    </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 开支范围:</label></td>
				<td class="value">
					<input id="budgetName" name="budgetName" type="text" style="width: 150px" class="inputxt"
						value='${tPmDeclareFundsTechnologyPage.budgetName}' maxlength="25"> 
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">开支范围</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 金额:</label></td>
				<td class="value">
					<input id="budgetFunds" name="budgetFunds" type="text" style="width: 150px" 
						class="inputxt easyui-numberbox" value='${tPmDeclareFundsTechnologyPage.budgetFunds}'
						data-options="min:0,precision:2"> 
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">预算金额</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label></td>
				<td class="value">
					<input id="memo" name="memo" type="text" style="width: 150px" class="inputxt"
						value='${tPmDeclareFundsTechnologyPage.memo}' maxlength="150"> 
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
</body>

<script type="text/javascript">
$(document).ready(function(){
	var id = $("#id").val();
	if(id){
		$("#formobj").attr("action", "tPmDeclareFundsTechnologyController.do?doUpdate");
		$("#budgetYear").attr("disabled", "disabled");
		$("#id").attr("add", "false");
	}
});

function reloadTechnologyFundsList(data){
	frameElement.api.opener.reloadBudget();
	frameElement.api.opener.showMsg(data.msg);
    frameElement.api.close();
}
</script>
