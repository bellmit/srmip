<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>专家评审主表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tErReviewMainController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tErReviewMainPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tErReviewMainPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tErReviewMainPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tErReviewMainPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tErReviewMainPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tErReviewMainPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tErReviewMainPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								依据文号:
							</label>
						</td>
						<td class="value">
						     	 <input id="accordingNum" name="accordingNum" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tErReviewMainPage.accordingNum}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">依据文号</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								呈批文件号:
							</label>
						</td>
						<td class="value">
						     	 <input id="docNum" name="docNum" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tErReviewMainPage.docNum}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">呈批文件号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								评审标题:
							</label>
						</td>
						<td class="value">
						     	 <input id="reviewTitle" name="reviewTitle" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tErReviewMainPage.reviewTitle}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审标题</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								评审内容:
							</label>
						</td>
						<td class="value">
						     	 <input id="reviewContent" name="reviewContent" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tErReviewMainPage.reviewContent}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审内容</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								计划评审时间:
							</label>
						</td>
						<td class="value">
									  <input id="planReviewDate" name="planReviewDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tErReviewMainPage.planReviewDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">计划评审时间</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								评审方式:
							</label>
						</td>
						<td class="value">
						     	 <input id="reviewMode" name="reviewMode" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tErReviewMainPage.reviewMode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">评审方式</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/expert/reviewmain/tErReviewMain.js"></script>		