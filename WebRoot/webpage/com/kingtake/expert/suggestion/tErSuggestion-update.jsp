<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>评审意见表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tErSuggestionController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tErSuggestionPage.id }">
		<input id="reviewProjectId" name="reviewProject.id" type="hidden" value="${tErSuggestionPage.reviewProject.id }">
		<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" style="width: 30%">
							<label class="Validform_label">
								评审专家姓名:
							</label>
						</td>
						<td class="value" style="width: 70%">
                                 <input id="expertId" name="expertId" type="hidden" value='${tErSuggestionPage.expertId}'>
						     	 <input id="expertName" name="expertName" type="text" style="width: 150px" class="inputxt"  
									                 value='${tErSuggestionPage.expertName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审专家姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								评审时间:
							</label>
						</td>
						<td class="value">
									  <input id="expertTime" name="expertTime" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
						      						 value='<fmt:formatDate value='${tErSuggestionPage.expertTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								专家打分(总分10分):
							</label>
						</td>
						<td class="value">
						     	 <input id="expertScore" name="expertScore" type="text" style="width: 150px" class="inputxt"  
									                 value='${tErSuggestionPage.expertScore}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">专家打分</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								专家结论:
							</label>
						</td>
						<td class="value">
                         <t:codeTypeSelect name="expertResult" type="select" codeType="0" code="PSJL" id="expertResult" defaultVal="${tErSuggestionPage.expertResult}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">专家结论</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								理由或意见建议:
							</label>
						</td>
						<td class="value">
                            <textarea id="expertContent" name="expertContent" rows="5" cols="" style="width: 400px">${tErSuggestionPage.expertContent}</textarea>   
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">理由或意见建议</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/expert/suggestion/tErSuggestion.js"></script>		