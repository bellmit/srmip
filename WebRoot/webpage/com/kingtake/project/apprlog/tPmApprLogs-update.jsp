<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>处理审批</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
//编写自定义JS代码
	debugger;
	//选择常用意见
	function changeContent(){
		var selectedVal = $("#commonSuggestion").find('option:selected').val();
		if(selectedVal!=""){
			var selectedText = $("#commonSuggestion").find('option:selected').text();
		    $("#suggestionContent").text(selectedText);
		}
	}
	
	function refreshSuggestTable(data){
		if(data.success){
			var apprInfoDialog = window.parent.apprInfo;
			var receiveLog = data.attributes.receive;
			var tr = $("#"+receiveLog.id, apprInfoDialog.document);
			tr.attr("title",'发送人:'+receiveLog.operateUsername+',发送时间:'+(receiveLog.operateDate==null ? "" : parseDateToString(new Date(receiveLog.operateDate), "yyyy-MM-dd hh:mm:ss")));
			var tds = tr.children("td");
			var length = tds.length;
			tds.eq(0).html(receiveLog.receiveUsername);
			tds.eq(1).html(receiveLog.operateTime==null ? "" : parseDateToString(new Date(receiveLog.operateTime), "yyyy-MM-dd hh:mm:ss"));
			var suggestionCode = (receiveLog.suggestionCode==null ? "" : 
				(receiveLog.suggestionCode=='<%=ReceiveBillConstant.AUDIT_PASS%>' ? '[通过]' : '[驳回]'));
			tds.eq(2).html("<font color='red'>"+suggestionCode+"</font>"+(receiveLog.suggestionContent==null ? "" : receiveLog.suggestionContent));
			
			$("#changeFlag", apprInfoDialog.document).val(data.attributes.changeFlag);
		}
		
		apprInfoDialog.tip(data.msg);
		if(apprInfoDialog.gridname == 'tPmApprReceiveLogsList'){
			apprInfoDialog.reloadtPmApprReceiveLogsList();
		}
		frameElement.api.close();
	}
	
	function getApprId(){
		return $("#apprId").val();
	}
	
	function getApprType(){
		var apprType = $("#apprType").val();
		return apprType.substring(0,2);
	}
	
	function getRecId(){
		return $("#id").val();
	}
	
	//获取审批意见
	function getSuggestionCode(){
		return $("#suggestionCode").val();
	}
	
	//获取意见内容
	function getSuggestionContent(){
		return $("#suggestionContent").val();
	}
</script>
 </head>
 <body>
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
    	action="tPmApprLogsController.do?doUpdate&rebutValidFlag=${rebutValidFlag}" 
    	tiptype="1" tipSweep="true" callback="@Override refreshSuggestTable">
        <input id="id" name="id" type="hidden" value="${tPmApprReceiveLogsPage.id }">
        <input id="apprId" name="apprId" type="hidden" value="${tPmApprReceiveLogsPage.apprId }">
        <input id="apprType" name="apprType" type="hidden" value="${tPmApprReceiveLogsPage.suggestionType }">
        <input id="senderId" name="senderId" type="hidden" value="${tPmApprReceiveLogsPage.apprSendLog.operateUserid }">
        <input id="senderName" name="senderName" type="hidden" value="${tPmApprReceiveLogsPage.apprSendLog.operateUsername }">
        <input id="senderDepartid" name="senderDepartid" type="hidden" value="${tPmApprReceiveLogsPage.apprSendLog.operateDepartid }">
        <input id="senderDepartName" name="senderDepartName" type="hidden" value="${tPmApprReceiveLogsPage.apprSendLog.operateDepartname }">
	    
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						审批节点：
					</label>
				</td>
				<td class="value">
					${apprTypeInfo.LABEL }
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批节点</label>
				</td>
			</tr>
		<!-- 是否可驳回 -->
			<tr>
				<td align="right">
					<label class="Validform_label">
						审批意见:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
						<t:codeTypeSelect id="suggestionCode" name="suggestionCode" type="radio" codeType="1" code="SPYJ"
				    		defaultVal="<%=ReceiveBillConstant.AUDIT_PASS%>" extendParam=""></t:codeTypeSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批意见</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						常用意见：
					</label>
				</td>
				<td class="value">
				    <t:codeTypeSelect id="commonSuggestion" name="" type="select" codeType="1" code="CYYJ"
						 extendParam="{onclick:'changeContent();'}" labelText="请选择"></t:codeTypeSelect>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						意见内容:
					</label>
				</td>
				<td class="value">
				    <textarea id="suggestionContent" name="suggestionContent" style="width:300px;height:100px;"
				    	datatype="*1-200">${tPmApprReceiveLogsPage.suggestionContent}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">意见内容</label>
				</td>
			</tr>
		</table>
    </t:formvalid>
 </body>
