<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>发送审批</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
<script type="text/javascript">
//编写自定义JS代码
var nodeList;
$(function(){
	var data = frameElement.api.data;
	if(data!=undefined){
		var suggestionCode = data.suggestionCode;
		var suggestionContent = data.suggestionContent;
		$("#suggestionCode").val(suggestionCode);
		$("#suggestionContent").val(suggestionContent);
	}
	var nodeListStr = $("#nodeList").val();
	nodeList = JSON.parse(nodeListStr);
	for(var i=0;i<nodeList.length;i++){
	   $("#receiveUserSelect").append('<option value="'+nodeList[i].senderId+'_'+nodeList[i].suggestionType+'">'+nodeList[i].senderName+'</option>');
	}
	$("#receiveUseIds").val(nodeList[0].senderId);
	$("#receiveUseNames").val(nodeList[0].senderName);
	/* $("#receiveUseDepartIds").val(nodeList[0].senderDepartId); */
	/* $("#receiveUseDepartNames").val(nodeList[0].senderDepartName); */
	$("#suggestionType").val(nodeList[0].suggestionType);
	$("#suggestionTypeName").val(nodeList[0].suggestionTypeName);
	
	$("#receiveUserSelect").change(function(){
		var selected = $("#receiveUserSelect").val();
		for(var i=0;i<nodeList.length;i++){
			var str = nodeList[i].senderId+'_'+nodeList[i].suggestionType;
			if(str==selected){
				$("#receiveUseIds").val(nodeList[i].senderId);
				$("#receiveUseNames").val(nodeList[i].senderName);
				/* $("#receiveUseDepartIds").val(nodeList[i].senderDepartId); */
				/* $("#receiveUseDepartNames").val(nodeList[i].senderDepartName); */
				$("#suggestionType").val(nodeList[i].suggestionType);
				$("#suggestionTypeName").val(nodeList[i].suggestionTypeName);
				break;
			}
		}
	});
});


function refreshSuggestTable(data){
	if(data.success){
		var apprInfoDialog = window.parent.apprInfo;
		
		var apprType = $("#suggestionType").val();
		/* var handlerType = $("#handlerType").val(); */
		
		var sendLog = data.attributes.send;
		if($("#apprId").val() == ""){
			var oldValue = $("#sendIds", apprInfoDialog.document).val();
			var newValue = oldValue + sendLog.id + ",";
			$("#sendIds", apprInfoDialog.document).val(newValue);
		}
		var receiveLogs = data.attributes.receive;
		var input = $("#"+apprType+"input", apprInfoDialog.document);
		<%-- if(handlerType == '<%=ApprovalConstant.HANDLER_TYPE_HANDLER%>'){ --%>
			var td = input.parent();
			var rowNum = td.attr("rowspan");
			if(rowNum == 1){
				td.prev().remove();
				td.parent().prepend(
					'<td class="value" style="width:18%">审批人</td>' + 
					'<td class="value" style="width:23%">审批时间</td>' + 
					'<td class="value">意见内容</td>'
				);
			}
			
			var table = $("#"+apprType+"table", apprInfoDialog.document);
			var addRowNum = receiveLogs.length;
			for(var i=0; i<addRowNum; i++){
				var receiveLog = receiveLogs[i];
				var trData = '<tr title="发送人:'+receiveLog.operateUsername+',发送时间:'+(receiveLog.operateDate==null ? "" : parseDateToString(new Date(receiveLog.operateDate), "yyyy-MM-dd hh:mm:ss"))+'">';
				trData +=
					'<td class="value">' +
						receiveLog.receiveUsername +
					'</td>' +
					'<td class="value">' +
						(receiveLog.operateTime==null ? "" : parseDateToString(new Date(receiveLog.operateTime), "yyyy-MM-dd hh:mm:ss")) +
					'</td>' +
					'<td class="value"><font color="red">' +
					(receiveLog.suggestionCode==null ? "" : 
						(receiveLog.suggestionCode=='<%=ReceiveBillConstant.AUDIT_PASS%>' ? '[通过]' : '[驳回]')
					) +"</font>"+(receiveLog.suggestionContent==null ? "" : receiveLog.suggestionContent) +
					'</td></tr>';
				table.append(trData);
			}
			
			td.attr("rowspan", parseInt(rowNum) + addRowNum);
	 
			if(data.attributes.opt=='audit'){
				var auditDialog = window.parent.auditDialog;
				$("#btn_sub", auditDialog.document).click();
			}
		
		if($("#changeFlag", apprInfoDialog.document).val() == ""){
			$("#changeFlag", apprInfoDialog.document).val(data.attributes.changeFlag);
		}
	}
	if($("#apprId").val() != ""){
		apprInfoDialog.tip(data.msg);
	}
	if(apprInfoDialog.gridname == 'tPmApprReceiveLogsList'){
		apprInfoDialog.reloadtPmApprReceiveLogsList();
	}
	frameElement.api.close();
}
	
	function closeTable(data){
		var win = frameElement.api.opener;
		win.tip(data.msg);
		if(data.success){
			var apprInfo = W.$.dialog.list['apprInfo'];
			apprInfo.close();
			win.reloadTable();
			frameElement.api.close();
		}
	}
</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmApprLogsController.do?doBack" tiptype="1" callback="@Override closeTable">
			<input id="recId" name="recId" type="hidden" value="${recId }">
			<input id="apprId" name="apprId" type="hidden" value="${apprId}">
			<input id="suggestionCode" name="suggestionCode" type="hidden" >
			<input id="suggestionContent" name="suggestionContent" type="hidden" >
			<textarea id="nodeList"  cols="1" rows="1" style="display: none;">${nodeList}</textarea>
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						审批节点:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
							<input id="suggestionTypeName" style="width: 186px;" readonly="readonly">
							<input id="suggestionType" name="suggestionType" type="hidden">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批节点</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						驳&nbsp;回&nbsp;给&nbsp;:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
			     	<input id="receiveUseNames" name="receiveUseNames" type="hidden"  />
			     	<input id="receiveUseIds" name="receiveUseIds" type="hidden" datatype="*"/>
			     	<!-- <input id="receiveUseDepartIds" name="receiveUseDepartIds" type="hidden" /> -->
			     	<!-- <input id="receiveUseDepartNames" name="receiveUseDepartNames" type="hidden" /> -->
				  	<select id="receiveUserSelect" style="width: 190px">
				  	
				  	</select>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">驳回人</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						系统消息:&nbsp;&nbsp;
					</label>
				</td>
				<td class="value">
					<textarea id="senderTip" name="senderTip" style="width:260px;height:100px;"
				    	datatype="*1-200" ignore="ignore">${tPmApprSendLogsPage.senderTip }</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">系统消息内容</label>
				</td>
			</tr>
		</table>
  </t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/apprlog/tPmApprLogs.js"></script>		