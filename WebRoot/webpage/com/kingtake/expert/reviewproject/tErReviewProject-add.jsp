<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>评审项目信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tErReviewProjectController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tErReviewProjectPage.id }">
					<input id="tEId" name="tEId" type="hidden" value="${tErReviewProjectPage.tEId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属项目:
						</label>
					</td>
					<td class="value">
					     	 <input id="projectId" name="projectId" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属项目</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属类型:
						</label>
					</td>
					<td class="value">
					     	 <input id="reviewType" name="reviewType" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属类型</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属阶段:
						</label>
					</td>
					<td class="value">
					     	 <input id="projectStage" name="projectStage" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属阶段</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							评审结果:
						</label>
					</td>
					<td class="value">
					     	 <input id="reviewResult" name="reviewResult" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审结果</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							评审分数:
						</label>
					</td>
					<td class="value">
					     	 <input id="reviewScore" name="reviewScore" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审分数</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							评审意见:
						</label>
					</td>
					<td class="value">
					     	 <input id="reviewSuggestion" name="reviewSuggestion" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审意见</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							结果录入时间:
						</label>
					</td>
					<td class="value">
							   <input id="resultInputDate" name="resultInputDate" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结果录入时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							录入人id:
						</label>
					</td>
					<td class="value">
					     	 <input id="resultInputUserid" name="resultInputUserid" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">录入人id</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							录入人姓名:
						</label>
					</td>
					<td class="value">
					     	 <input id="resultInputUsername" name="resultInputUsername" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">录入人姓名</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/expert/reviewproject/tErReviewProject.js"></script>		