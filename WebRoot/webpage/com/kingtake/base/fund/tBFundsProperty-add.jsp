<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>经费类型表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
	  $(function() {
		  	
			//预算类型
				 $("#yslx").combotree({
						url : 'tBXmlbController.do?getbudgetCategory',
						valueField : 'id',
						textField : 'text'
				 });
		  });
		function beforeSubmit(){
			var budgetCategory = $("#yslx").combobox('getValue');
			var budgetCategoryName = $("#yslx").combobox('getText');
			
			$("#budgetCategory").val(budgetCategory);
			$("#budgetCategoryName").val(budgetCategoryName);
		}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFundsPropertyController.do?doAdd" tiptype="1" beforeSubmit="beforeSubmit()">
					<input id="id" name="id" type="hidden" value="${tBFundsPropertyPage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
					<%-- <tr>
						<td align="right">
							<label class="Validform_label">
								性质编码:
							</label>
						</td>
						<td class="value">
						     	 <input id="fundsCode" name="fundsCode" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tBFundsPropertyPage.fundsCode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性质编码</label>
						</td>
					</tr> --%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经费类型:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="fundsName" name="fundsName" type="text" style="width: 150px" class="inputxt"  
								value='${tBFundsPropertyPage.fundsName}' >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费类型</label>
						</td>
					</tr>
					
					<tr>
			        <td align="right"><label class="Validform_label"> 预算类型 : </label></td>
			        <td class="value" colspan="5">
			        	<input id="budgetCategory" name="budgetCategory" type="hidden"  value='${tBFundsPropertyPage.budgetCategory}' >
			        	<input id="budgetCategoryName" name="budgetCategoryName" type="hidden"  value='${tBFundsPropertyPage.budgetCategoryName}' >
			        	
			            <input id="yslx" value="${tBFundsPropertyPage.budgetCategoryName}">
		            	<span class="Validform_checktip"></span>
		            	<label class="Validform_label" style="display: none;">预算类型</label>
		            </td>
			    </tr>
				<tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							间接计算方式:
						</label>
					</td>
					<td class="value">
				     	<input id="indirectFeeCalu" name="indirectFeeCalu" type="text" style="width: 150px" class="inputxt" value='${tBFundsPropertyPage.indirectFeeCalu}'>
						<label class="Validform_label" style="display: none;">间接计算方式</label>
					</td>
				</tr>
				
				<tr>
			        <td align="right"><label class="Validform_label"> 大学比例 : </label></td>
			        <td class="value" colspan="5">
			        	<input id="universityProp" name="universityProp" type="text" style="width: 150px" class="inputxt" value='${tBFundsPropertyPage.universityProp}'> 
			        	<span class="Validform_checktip"></span> 
			        	<label class="Validform_label" style="display: none;">大学比例</label>
		        	</td>
			    </tr>
			    
			    <tr>
					<td align="right">
						<label class="Validform_label">
							责任单位比例:
						</label>
					</td>
					<td class="value">
				     	<input id="universityProp" name="unitProp" type="text" style="width: 150px" class="inputxt" value='${tBFundsPropertyPage.unitProp}'>
						<label class="Validform_label" style="display: none;">责任单位比例</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							承研单位比例:
						</label>
					</td>
					<td class="value">
				     	<input id="devUnitProp" name="devUnitProp" type="text" style="width: 150px" class="inputxt" value='${tBFundsPropertyPage.devUnitProp}'>
						<label class="Validform_label" style="display: none;">承研单位比例</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目组比例:
						</label>
					</td>
					<td class="value">
				     	<input id="projectGroupProp" name="projectGroupProp" type="text" style="width: 150px" class="inputxt" value='${tBFundsPropertyPage.projectGroupProp}'>
						<label class="Validform_label" style="display: none;">项目组比例</label>
					</td>
				</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
							<textarea id="memo" name="memo" style="width:150px; height:60px;" datatype="*1-100" 
								ignore="ignore">${tBFundsPropertyPage.memo}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/base/fund/tBFundsProperty.js"></script>		