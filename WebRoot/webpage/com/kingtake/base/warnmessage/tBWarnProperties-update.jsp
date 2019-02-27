<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>提醒配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBWarnPropertiesController.do?doAddUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tBWarnPropertiesPage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" style="width:25%;">
							<label class="Validform_label">
								业务名称:
							</label>
						</td>
						<td class="value" style="width:75%;">
						     	 <input id="businessname" name="businessname" type="text" style="width: 150px" class="inputxt"  
									                 value='${tBWarnPropertiesPage.businessname}' placeholder="请输入业务名称。">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">提醒业务名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								配置sql:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="sqlstr" style="width:600px;" class="inputxt" rows="6" name="sqlstr" placeholder="请根据业务输入查询接收人的sql，userId表示接收人id。">${tBWarnPropertiesPage.sqlstr}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">配置sql</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								提醒跳转url:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="url" style="width:600px;" class="inputxt" rows="6" name="url" placeholder="请根据业务输入点击提醒时跳转的界面url。">${tBWarnPropertiesPage.url}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">提醒跳转url</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/warnMessage/tBWarnProperties.js"></script>		