<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>开题信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmOpenSubjectController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmOpenSubjectPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tPmOpenSubjectPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPmOpenSubjectPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPmOpenSubjectPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPmOpenSubjectPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPmOpenSubjectPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPmOpenSubjectPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							关键技术指标:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="technicalIndicator" name="technicalIndicator"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">关键技术指标</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							主要研究内容:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="researchContents" name="researchContents"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">主要研究内容</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/subject/tPmOpenSubject.js"></script>		