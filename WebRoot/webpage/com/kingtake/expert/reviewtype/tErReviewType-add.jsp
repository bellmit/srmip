<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>评审内容类型</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tErReviewTypeController.do?doAdd" tiptype="1">
					<input id="createBy" name="createBy" type="hidden" value="${tErReviewTypePage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tErReviewTypePage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tErReviewTypePage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tErReviewTypePage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tErReviewTypePage.updateName }">
					<input id="id" name="id" type="hidden" value="${tErReviewTypePage.id }">
		<table cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							标题:
						</label>
					</td>
					<td class="value">
					     	 <input id="title" name="title" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							url地址:
						</label>
					</td>
					<td class="value">
					     	 <input id="url" name="url" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">url地址</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排序码:
						</label>
					</td>
					<td class="value">
					     	 <input id="sortCode" name="sortCode" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排序码</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/expert/reviewtype/tErReviewType.js"></script>		