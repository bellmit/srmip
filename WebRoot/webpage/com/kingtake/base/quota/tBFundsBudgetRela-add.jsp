<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>经费限额设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $("#approvalBudgetRelaId").combobox({
		  url:'tBApprovalBudgetRelaController.do?getBudgetCombobox&projectType=${projectType}',
		  valueField: 'id',
	      textField: 'text', 
	      panelHeight:150,
	      editable:false,
	      onChange:function(newValue, oldValue){
	    	  $("#validBud").val(newValue);
	    	  $("#validBud").attr("ajaxurl","tBFundsBudgetRelaController.do?valiBud"
	    			  +"&id=${tBFundsBudgetRelaPage.id}"
	    			  +"&fundsPropertyCode=${tBFundsBudgetRelaPage.fundsPropertyCode}"
	    			  +"&approvalBudgetRelaId="+newValue);
	      }
	  });
  });
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tBFundsBudgetRelaController.do?doAdd" tiptype="1" tipSweep="true" >
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						经费类型:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
			     	<input id="fundsPropertyCode" name="fundsPropertyCode" type="hidden" 
			     		style="width: 150px" class="inputxt" 
			     		value="${tBFundsBudgetRelaPage.fundsPropertyCode}">
			     	<input type="text" style="width: 150px" class="inputxt" value="${fundName}"
			     	 	readonly="readonly">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">经费类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						预算类型:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
					<input id="validBud" type="hidden" datatype="*">
			     	<input id="approvalBudgetRelaId" name="approvalBudgetRelaId" type="text" 
			     	style="width: 155px" class="inputxt" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">预算类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						100万以上比例值:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
			     	<input id="millionUp" name="millionUp" type="text" 
			     		style="width:135px; text-align:right;" class="easyui-numberbox"
			     		data-options="min:0,max:99.99,precision:2,groupSeparator:','">%
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">100万以上比例值</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						100万及以下比例值:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
			     	<input id="millionDown" name="millionDown" type="text" 
			     		style="width:135px; text-align:right;" class="easyui-numberbox"
			     		data-options="min:0,max:99.99,precision:2,groupSeparator:','">%
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">100万及以下比例值</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						备注:
					</label>
				</td>
				<td class="value">
			     	<textarea id="memo" name="memo" style="width:150px; height:60px;" 
			     		datatype="*1-100" ignore="ignore"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
		</table>
	</t:formvalid>
 </body>
 <script src = "webpage/com/kingtake/base/quota/tBFundsBudgetRela.js"></script>		