<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>计划草案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPlanDraftController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmPlanDraftPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tPmPlanDraftPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPmPlanDraftPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPmPlanDraftPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPmPlanDraftPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPmPlanDraftPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPmPlanDraftPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							计划名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="planName" name="planName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">计划名称</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							编制时间:
						</label>
					</td>
					<td class="value">
							   <input id="planTime" name="planTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">编制时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="remark" name="remark"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				<tr>
					<td align="right">
						<label class="Validform_label">
							流程状态:
						</label>
					</td>
					<td class="value">
					     	 <input id="planStatus" name="planStatus" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">流程状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/base/plandraft/tPmPlanDraft.js"></script>		