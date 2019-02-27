<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>填写意见</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--   <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script> -->
<!--   <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script> -->
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $('#suggestionContent')[0].innerHTML = $('#suggestionType').find("option:selected").text();
	  $('#suggestionType').change(function(){
		  $('#suggestionContent')[0].innerHTML=$('#suggestionType').find("option:selected").text();
	  });
  });
	  
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOSendBillController.do?doSuggestion&rid=${receive.id}&sCode=1"> 
		<input type="hidden" name="receiveid" id="receiveid" value="${receive.id}">
		<table  cellpadding="0" cellspacing="1" class="formtable">
		<tr>
					<td align="right">
						<label class="Validform_label">
							常用意见：
						</label>
					</td>
					<td class="value">
					     	 <t:codeTypeSelect id="suggestionType" name="suggestionType" defaultVal="0" 
                type="select" code="CYYJ" codeType="1"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">填写意见</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<label class="Validform_label">
							审批状态：
						</label>
					</td>
					<td class="value">
					     	 <t:codeTypeSelect id="suggestionCode" name="suggestionCode" defaultVal="1" 
                type="radio" code="SPYJ" codeType="1"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">审批意见</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							${suggestionType}：
						</label>
					</td>
					<td class="value">
					     	 <textarea id="suggestionContent" name="suggestionContent"  datatype="*1-50"  style="width: 300px;" rows="5" class="inputxt" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">填写意见</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
