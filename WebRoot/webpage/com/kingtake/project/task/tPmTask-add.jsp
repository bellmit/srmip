<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
 </script>
 </head>
 <body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tPmTaskController.do?doAdd">
					<input id="id" name="id" type="hidden" value="${tPmTaskPage.id }">
					<input id="tPId" name="tPId" type="hidden" value="${tPmTaskPage.tPId }">
					<input id="createBy" name="createBy" type="hidden" value="${tPmTaskPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPmTaskPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPmTaskPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPmTaskPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPmTaskPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPmTaskPage.updateDate }">
	<table cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">标题:</label>
			</td>
			<td class="value">
		     	 <input id="taskTitle" name="taskTitle" type="text" style="width: 150px" class="inputxt"  
					               datatype="*2-100"
					               >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">标题</label>
			</td>
		</tr>
	</table>
			<div style="width: auto;height: 200px;">
				<%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
				<div style="width:800px;height:1px;"></div>
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				 <t:tab href="tPmTaskController.do?tPmTaskNodeList&id=${tPmTaskPage.id}" icon="icon-search" title="任务节点管理" id="tPmTaskNode"></t:tab>
				</t:tabs>
			</div>
			</t:formvalid>
			<!-- 添加 附表明细 模版 -->
	<table style="display:none">
	<tbody id="add_tPmTaskNode_table_template">
		<tr>
			 <td align="center"><div style="width: 25px;" name="xh"></div></td>
			 <td align="center"><input style="width:20px;" type="checkbox" name="ck"/></td>
				  <td align="left">
							<input name="tPmTaskNodeList[#index#].planTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					               >  
					  <label class="Validform_label" style="display: none;">计划时间</label>
				  </td>
				  <td align="left">
							<input name="tPmTaskNodeList[#index#].finishTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					               >  
					  <label class="Validform_label" style="display: none;">完成时间</label>
				  </td>
				  <td align="left">
					       	<input name="tPmTaskNodeList[#index#].taskContent" maxlength="400" 
						  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">任务内容</label>
				  </td>
				  <td align="left">
					  	<input name="tPmTaskNodeList[#index#].planCheckFlag" maxlength="1" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">计划处检查标记</label>
				  </td>
				  <td align="left">
					  	<input name="tPmTaskNodeList[#index#].planCheckUsername" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">计划处检查人姓名</label>
				  </td>
				  <td align="left">
							<input name="tPmTaskNodeList[#index#].planCheckTie" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					               >  
					  <label class="Validform_label" style="display: none;">计划处检查时间</label>
				  </td>
				  <td align="left">
					  	<input name="tPmTaskNodeList[#index#].qualityCheckFlag" maxlength="1" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">质量办检查标记</label>
				  </td>
				  <td align="left">
					  	<input name="tPmTaskNodeList[#index#].qualityCheckUsername" maxlength="50" 
					  		type="text" class="inputxt"  style="width:120px;"
					               
					               >
					  <label class="Validform_label" style="display: none;">质量办检查人姓名</label>
				  </td>
				  <td align="left">
							<input name="tPmTaskNodeList[#index#].qualityCheckTime" maxlength="0" 
					  		type="text" class="Wdate" onClick="WdatePicker()"  style="width:120px;"
					                
					               >  
					  <label class="Validform_label" style="display: none;">质量办检查时间</label>
				  </td>
			</tr>
		 </tbody>
		</table>
 </body>
 <script src = "webpage/com/kingtake/project/task/tPmTask.js"></script>	