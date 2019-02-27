<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>发送审批</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
<script type="text/javascript">
//编写自定义JS代码
$(function(){

	var data = frameElement.api?frameElement.api.data:undefined;
	if(data!=undefined){
		var suggestionCode = data.suggestionCode;
		var suggestionContent = data.suggestionContent;
		$("#suggestionCode").val(suggestionCode);
		$("#suggestionContent").val(suggestionContent);
	}
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
		var win;
		var dialog = W.$.dialog.list['processDialog'];
	    if (dialog == undefined) {
	    	win = frameElement.api.opener;
	    } else {
	    	win = dialog.content;
	    }
		win.tip(data.msg);
		if(data.success){
			var apprInfo = W.$.dialog.list['apprInfo'];
			if(apprInfo) {
                apprInfo.close();
            }
            if(win.reloadTable)win.reloadTable();
			frameElement.api.close();
		}
	}
</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmApprLogsController.do?doPass" tiptype="1" callback="@Override closeTable">
			<input id="id" name="id" type="hidden" value="${tPmApprSendLogsPage.id }">
			<input id="apprId" name="apprId" type="hidden" value="${apprId}">
			<input id="recId" name="recId" type="hidden" value="${recId}">
			<input id="tableName" name="tableName" type="hidden" value="${tPmApprSendLogsPage.tableName}">
			<input id="suggestionCode" name="suggestionCode" type="hidden" >
			<input id="suggestionContent" name="suggestionContent" type="hidden" >
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr style="display:<%= "24".equals(request.getParameter("apprType")) ?  "none" : ""  %>;">
				<td align="right">
					<label class="Validform_label">
						审批节点:<font color="red">*</font>
					</label>
				</td>
				<td class="value" >
							<select id="suggestionType" name="suggestionType" style="width: 186px;">
								<c:forEach items="${apprNodes}" var="apprTypeInfo">
									<option value="${apprTypeInfo.ID}" <c:if test="${apprTypeInfo.select eq '1' }">selected="selected"</c:if> >${apprTypeInfo.LABEL}</option>
								</c:forEach>
							</select>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批节点</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						接&nbsp;收&nbsp;人&nbsp;:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
			     	<input id="receiveUseNames" name="receiveUseNames" type="text" style="width: 180px" class="inputxt" readonly="readonly"/>
			     	<input id="receiveUseIds" name="receiveUseIds" type="hidden" datatype="*"/>
			     	<!-- <input id="receiveUseDepartIds" name="receiveUseDepartIds" type="hidden" /> -->
			     	<!-- <input id="receiveUseDepartNames" name="receiveUseDepartNames" type="hidden" /> -->
				  	<t:chooseUser icon="icon-search" title="人员列表" isclear="true"  idInput="receiveUseIds"
						textname="id,realName"  mode="single"
						inputTextname="receiveUseIds,receiveUseNames" ></t:chooseUser>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">接收人</label>
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