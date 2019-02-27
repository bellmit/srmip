<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>项目经费年度预算</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" callback="@Override reloadBudget"
			action="tPmFundsBudgetController.do?doAdd" tiptype="1" beforeSubmit="checkOrNot">
	<input id="id" name="id" add="true" type="hidden" value="${tPmFundsBudget.id }">
	<input id="projDeclareId" name="projDeclareId" type="hidden" value="${tPmFundsBudget.projDeclareId }" />
	<table style="width: 300px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">年度:<font color="red">*</font></label>
			</td>
			<td class="value">
			    <input id="budgetYear" name="budgetYear" type="text" style="width: 150px"
			    	class="Wdate" onClick="WdatePicker({dateFmt:'yyyy'})" value='${tPmFundsBudget.budgetYear}' >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">年度</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">设备费:</label>
			</td>
			<td class="value">
		     	<input id="equipFunds" name="equipFunds" type="text"
		     		class="easyui-numberbox inputxt" data-options="min:0,precision:2" value='${tPmFundsBudget.equipFunds}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">设备费</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">材料费:</label>
			</td>
			<td class="value">
			    <input id="materialFunds" name="materialFunds" type="text" 
			    	class="easyui-numberbox inputxt" data-options="min:0,precision:2" value='${tPmFundsBudget.materialFunds}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">材料费</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">外协费:</label>
			</td>
			<td class="value">
		     	<input id="outsideFunds" name="outsideFunds" type="text" 
		     		class="easyui-numberbox inputxt" data-options="min:0,precision:2" value='${tPmFundsBudget.outsideFunds}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">外协费</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">业务费:</label>
			</td>
			<td class="value">
		     	<input id="businessFunds" name="businessFunds" type="text" 
		     		class="easyui-numberbox inputxt" data-options="min:0,precision:2" value='${tPmFundsBudget.businessFunds}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">业务费</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">备注:</label>
			</td>
			<td class="value">
		     	<input id="memo" name="memo" type="text" style="width: 150px" class="inputxt" 
		     		value='${tPmFundsBudget.memo}' maxlength="150" />
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
		$("#formobj").attr("action", "tPmFundsBudgetController.do?doUpdate");
		$("#budgetYear").attr("disabled", "disabled");
		$("#id").attr("add", "false");
	}
});

function reloadBudget(data){
	frameElement.api.opener.reloadBudget();
	frameElement.api.opener.showMsg(data.msg);
    frameElement.api.close();
}

function checkOrNot(){
	var flag = true;
	if($("#id").attr("add") == "true"){
		flag = checkAllIn();
	}
	return flag;
}

function checkAllIn(){
	// 检查年度是否已存在
	var year = $("#budgetYear").val();
	var flag = true;
	if(!year){
		$.Showmsg("请填写年度");
    	flag = false;
	}else{
		$.ajax({
			type:'post',
			async:false,
			url:'tPmFundsBudgetController.do?isExistYear',
			data:{
				budgetYear:year,
				projDeclareId:$("#projDeclareId").val()
			},
			success:function(data){
				if(data == "true"){
					$.Showmsg(year+"年度已存在预算!");
			    	flag = false;
				}
			}
		});
	}
	return flag;
}
</script>
