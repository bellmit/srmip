<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>待办布局表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPortalLayoutController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPortalLayoutPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPortalLayoutPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPortalLayoutPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPortalLayoutPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tPortalLayoutPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPortalLayoutPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPortalLayoutPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								布局名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="layoutName" name="layoutName" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPortalLayoutPage.layoutName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">布局名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								布局代码:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="layoutCode" style="width:400px;" class="inputxt" rows="6" name="layoutCode">${tPortalLayoutPage.layoutCode}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">布局代码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								拆分列数:
							</label>
						</td>
						<td class="value">
						     	 <input id="split" name="split" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPortalLayoutPage.split}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">拆分列数</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/system/portal/tPortalLayout.js"></script>		