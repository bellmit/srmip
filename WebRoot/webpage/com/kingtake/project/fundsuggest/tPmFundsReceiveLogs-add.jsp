<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>预算审批流转接收表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmFundsReceiveLogsController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmFundsReceiveLogsPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							经费预_主键:
						</label>
					</td>
					<td class="value">
					     	 <input id="tPId" name="tPId" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费预_主键</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							接收人id:
						</label>
					</td>
					<td class="value">
					     	 <input id="receiveUserid" name="receiveUserid" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人id</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							接收人姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="receiveUsername" name="receiveUsername" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人姓名</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							接收人部门id:
						</label>
					</td>
					<td class="value">
					     	 <input id="receiveDepartid" name="receiveDepartid" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人部门id</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							接收人部门名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="receiveDepartname" name="receiveDepartname" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人部门名称</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							处理时间:
						</label>
					</td>
					<td class="value">
							   <input id="operateTime" name="operateTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							处理状态（0：未处理；1：已处理）:
						</label>
					</td>
					<td class="value">
					     	 <input id="operateStatus" name="operateStatus" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理状态（0：未处理；1：已处理）</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							意见类型（00：承研单位意见；01：责任单位意见；02：计划处意见；03：校务部财务处意见；04：科研部意见）:
						</label>
					</td>
					<td class="value">
					     	 <input id="suggestionType" name="suggestionType" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">意见类型（00：承研单位意见；01：责任单位意见；02：计划处意见；03：校务部财务处意见；04：科研部意见）</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							意见内容:
						</label>
					</td>
					<td class="value">
					     	 <input id="suggestionContent" name="suggestionContent" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">意见内容</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							有效标志:
						</label>
					</td>
					<td class="value">
					     	 <input id="validFlag" name="validFlag" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">有效标志</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							意见编码（0：驳回；1：同意）:
						</label>
					</td>
					<td class="value">
					     	 <input id="suggestionCode" name="suggestionCode" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">意见编码（0：驳回；1：同意）</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							关联预算审批表主键:
						</label>
					</td>
					<td class="value">
					     	 <input id="fundsApprId" name="fundsApprId" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">关联预算审批表主键</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/fundsuggest/tPmFundsReceiveLogs.js"></script>		