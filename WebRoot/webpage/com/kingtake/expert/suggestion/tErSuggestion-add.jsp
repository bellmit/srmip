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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tErSuggestionController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tErSuggestionPage.id }">
					<input id="reviewProjectId" name="reviewProjectId" type="hidden" value="${tErSuggestionPage.reviewProjectId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							评审专家id:
						</label>
					</td>
					<td class="value">
					     	 <input id="expertId" name="expertId" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审专家id</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							评审专家姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="expertName" name="expertName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
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
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审时间</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							专家打分:
						</label>
					</td>
					<td class="value">
					     	 <input id="expertScore" name="expertScore" type="text" style="width: 150px" class="inputxt"  
								               
								               >
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
					     	 <input id="expertResult" name="expertResult" type="text" style="width: 150px" class="inputxt"  
								               
								               >
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
					     	 <input id="expertContent" name="expertContent" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">理由或意见建议</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/expert/suggestion/tErSuggestion.js"></script>		