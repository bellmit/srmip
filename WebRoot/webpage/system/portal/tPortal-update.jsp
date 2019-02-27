<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>代办配置表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPortalController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPortalPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tPortalPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tPortalPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tPortalPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tPortalPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPortalPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPortalPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="title" name="title" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPortalPage.title}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								面板高度:
							</label>
						</td>
						<td class="value">
						     	 <input id="height" name="height" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPortalPage.height}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">面板高度</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								内容地址:
							</label>
						</td>
						<td class="value">
						     	 <input id="url" name="url" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPortalPage.url}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">内容地址</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								排序:
							</label>
						</td>
						<td class="value">
						     	 <input id="sort" name="sort" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tPortalPage.sort}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排序</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/system/portal/tPortal.js"></script>		