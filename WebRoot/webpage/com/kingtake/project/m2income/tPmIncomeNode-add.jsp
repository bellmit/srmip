<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>来款节点管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmIncomeNodeController.do?doAdd" 
  	tiptype="1" tipSweep="true" callback="@Override uploadFile">
					<input id="id" name="id" type="hidden" value="${tPmIncomeNodePage.id }">
					<input id="tpId" name="tpId" type="hidden" value="${tPmIncomeNodePage.tpId}">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							来款时间:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<input id="incomeTime" name="incomeTime" type="text" style="width: 150px" 
				      		datatype="date"	class="Wdate" onClick="WdatePicker()" >    
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">来款时间</label>
					</td>
				<!-- </tr>
				<tr> -->
					<td align="right">
						<label class="Validform_label">
							来款金额:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="incomeAmount" name="incomeAmount" type="text"
				     		style="width:135px; text-align:right;" class="easyui-numberbox"
							data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">来款金额</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							来款说明:<font color="red">*</font>
						</label>
					</td>
					<td class="value" colspan="3">
				     	<textarea id="incomeExplain" name="incomeExplain"  style="width:438px;height:80px;" 
				     		datatype="*1-150"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">来款说明</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;
						</label>
					</td>
      				<td class="value" colspan="3">
      					<input type="hidden" value="${tPmIncomeNodePage.id}" id="bid" name="bid" />
						<div>
							<div class="form" id="filediv"></div>
							<t:upload name="fiels" id="file_upload"   extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" 
								formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tPmIncomeNode"></t:upload>
						</div>
      				</td>
    			</tr>
			</table>
		</t:formvalid>
 </body>
 <script src = "webpage/com/kingtake/project/m2income/tPmIncomeNode.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>