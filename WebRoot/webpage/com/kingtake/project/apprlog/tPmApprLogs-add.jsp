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
				debugger;
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
	 
		/* }else{
			var span = input.next();
			
			span.html(input.val());
			
			input.val("撤回");
			input.attr("status", "recall");
			
			$("#" + apprType + "receiveId", apprInfoDialog.document).val(receiveLogs[0].id);
		} */
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
</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
  	action="tPmApprLogsController.do?doAdd" tiptype="1" callback="@Override refreshSuggestTable">
			<input id="id" name="id" type="hidden" value="${tPmApprSendLogsPage.id }">
			<input id="apprId" name="apprId" type="hidden" value="${tPmApprSendLogsPage.apprId}">
			<input id="tableName" name="tableName" type="hidden" value="${tPmApprSendLogsPage.tableName}">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						审批类型:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
					<c:choose>
						<c:when test="${empty apprTypeInfo}">
							<select id="suggestionType" name="suggestionType">
								<c:forEach items="${apprTypeInfos}" var="apprTypeInfo">
									<option value="${apprTypeInfo.ID}">${apprTypeInfo.LABEL}</option>
								</c:forEach>
							</select>
						</c:when>
						<c:otherwise>
							${apprTypeInfo.LABEL}
					     	<input type="hidden" id="suggestionType" name="suggestionType" value="${apprTypeInfo.ID}"/>
						</c:otherwise>
					</c:choose>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						接&nbsp;收&nbsp;人&nbsp;:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
				<%-- <c:choose>
					<c:when test="${ifStaff eq 'yes'}">
						<input id="receiveUseIds" name="receiveUseIds" type="hidden" datatype="*" value="${receiver.id}"/>
						<input id="receiveUseNames" name="receiveUseNames" type="text" style="width: 150px" class="inputxt" readonly="readonly" value="${receiver.realName}"/>
					</c:when>
					<c:otherwise> --%>
			     	<input id="receiveUseNames" name="receiveUseNames" type="text" style="width: 150px" class="inputxt" readonly="readonly"/>
			     	<input id="receiveUseIds" name="receiveUseIds" type="hidden" datatype="*"/>
			     	<!-- <input id="receiveUseDepartIds" name="receiveUseDepartIds" type="hidden" /> -->
			     	<!-- <input id="receiveUseDepartNames" name="receiveUseDepartNames" type="hidden" /> -->
				  	<t:chooseUser icon="icon-search" title="人员列表" isclear="true"  idInput="receiveUseIds"
						textname="id,realName"  mode="single"
						inputTextname="receiveUseIds,receiveUseNames" ></t:chooseUser>
					<%-- </c:otherwise>
				</c:choose> --%>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">接收人</label>
				</td>
			</tr>
			<%-- <tr>
				<td align="right">
					<label class="Validform_label">
						接收人是&nbsp;&nbsp;&nbsp;<br>否可驳回:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
					<c:choose>
						<c:when test="${empty apprTypeInfo.REBUT_FLAG}">
							<t:codeTypeSelect id="rebutFlag" name="rebutFlag" type="radio" 
								codeType="0" code="SFBZ" defaultVal="<%=SrmipConstants.YES%>"></t:codeTypeSelect>
						</c:when>
						<c:otherwise>
							<t:codeTypeSelect id="rebutFlag" name="rebutFlag" type="radio" 
								codeType="0" code="SFBZ" defaultVal="${apprTypeInfo.REBUT_FLAG}"></t:codeTypeSelect>
						</c:otherwise>
					</c:choose>
				</td>
			</tr> --%>
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