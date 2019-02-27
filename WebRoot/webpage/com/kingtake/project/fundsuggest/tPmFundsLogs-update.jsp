<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>预算审批流转记录表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmFundsLogsController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmFundsLogsPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								预算审批表主键:
							</label>
						</td>
						<td class="value">
						     	 <input id="fundsApprId" name="fundsApprId" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.fundsApprId}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">预算审批表主键</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								操作人id:
							</label>
						</td>
						<td class="value">
						     	 <input id="operateUserid" name="operateUserid" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.operateUserid}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">操作人id</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								操作人姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="operateUsername" name="operateUsername" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.operateUsername}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">操作人姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								操作时间:
							</label>
						</td>
						<td class="value">
									  <input id="operateDate" name="operateDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tPmFundsLogsPage.operateDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">操作时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								操作人部门id:
							</label>
						</td>
						<td class="value">
						     	 <input id="operateDepartid" name="operateDepartid" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.operateDepartid}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">操作人部门id</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								操作人部门名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="operateDepartname" name="operateDepartname" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.operateDepartname}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">操作人部门名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								操作人ip地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="operateIp" name="operateIp" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.operateIp}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">操作人ip地址</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								发送意见:
							</label>
						</td>
						<td class="value">
						     	 <input id="senderTip" name="senderTip" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPmFundsLogsPage.senderTip}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发送意见</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/fundsuggest/tPmFundsLogs.js"></script>		