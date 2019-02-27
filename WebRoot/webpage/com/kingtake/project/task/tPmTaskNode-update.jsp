<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务节点管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  function saveNodeCallback(data){
	    var win;
		var dialog = W.$.dialog.list['processDialog'];
	    if (dialog == undefined) {
	    	win = frameElement.api.opener;
	    } else {
	    	win = dialog.content;
	    }
	    win.tip(data.msg);
	    if(data.success){
	      win.reloadTable();
	      frameElement.api.close();
	    }
  }
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tPmTaskController.do?doTaskNodeUpdate" callback="@Override saveNodeCallback">
					<input id="id" name="id" type="hidden" value="${tPmTaskNodePage.id }">
					<input id="taskId" name="tpId" type="hidden" value="${tPmTaskNodePage.tpId }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">任务节点名称:<font color="red">*</font></label>
			</td>
			<td class="value">
		     	 <input id="taskNodeName" name="taskNodeName" type="text" style="width: 150px" class="inputxt"  
					               datatype="*2-100" value='${tPmTaskNodePage.taskNodeName}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">任务节点名称</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">开始时间:<font color="red">*</font></label>
			</td>
			<td class="value">
							<input id="planTime" name="planTime" type="text" style="width: 150px"  datatype="date"
						     class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})"value='<fmt:formatDate value='${tPmTaskNodePage.planTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开始时间</label>
						</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">计划完成时间:<font color="red">*</font></label>
			</td>
			<td class="value">
							<input id="endTime" name="endTime" type="text" style="width: 150px" datatype="date"
						     class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'planTime\')}'})"value='<fmt:formatDate value='${tPmTaskNodePage.endTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">计划完成时间</label>
						</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">任务内容:<font color="red">*</font></label>
			</td>
			<td class="value">
			<textarea rows="3" cols="3" id="taskContent"  name="taskContent" style="width: 350px;height: 80px;" datatype="*2-2000">${tPmTaskNodePage.taskContent}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">任务内容</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">成果形式:</label>
			</td>
			<td class="value">
			<textarea rows="3" cols="3" id="cgxs"  name="cgxs" style="width: 350px;height: 80px;" datatype="*0-500">${tPmTaskNodePage.cgxs}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">成果形式</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">评价方法:</label>
			</td>
			<td class="value">
			<textarea rows="3" cols="3" id="pjff"  name="pjff" style="width: 350px;height: 80px;" datatype="*0-500">${tPmTaskNodePage.pjff}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">评价方法</label>
			</td>
		</tr>
			</table>
			</t:formvalid>
 </body>
