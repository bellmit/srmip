<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<script type="text/javascript">
//发送审核方法
function sendApprType(apprTypeId){
	var title = "发送审核";
	var url = "tPmApprLogsController.do?goAdd"
		+ "&apprTypeId=" + apprTypeId;
	var width = 460;
	var height = 230;
	
	if("${idFlag}" == "true"){
		var apprId = $("#apprId").val();
		url += "&apprId=" + apprId;
	}
	createChildWindow(title, url, width, height);
}


//撤回方法
/* function recall(apprReceiveId, input){
	$.ajax({
		cache : false,
		type : 'POST',
		url : "tPmApprLogsController.do?doUpdateValid",
		data : {"id":apprReceiveId},
		success : function(data){
			var d = $.parseJSON(data);
			if(d.success){
				var span = $(input).next();
				
				$(input).val(span.html());
				$(input).attr("status", "send");
				
				span.html("");
				$("#apprReceiveId").val("");
			}
			tip(d.msg);
		}
	});
} */

//判断发送审核或撤回
/* function sendOrRecall(apprType, input){
	var inputStatus = $(input).attr("status");
	if(inputStatus == "send"){
		judgeBuforeSendCheckAppr(apprType);
	}else if(inputStatus == "recall"){
		var apprReceiveId = $("#" + apprType + "receiveId").val();
		recall(apprReceiveId, input);
	}
} */
</script>
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css"/>
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css"/>
<style>
.button{
    margin-left: 150px;
    background: #4782d8;
    border: 0px;
    border-radius: 4px;
    color: #fff;
}

.button:hover{
	background: #f89406;
}
</style>
<br/>
<c:if test="${!empty rebutCount}">
<img alt="" title="此申请被驳回过${rebutCount }次" style="margin-left: 10px;" src="plug-in/easyui/themes/icons/warning.png">
</c:if>
<br/>
<table  cellpadding="10" cellspacing="0" style="width:95%; margin:auto;">
	<input type="hidden" id="apprId" value="${apprId}"/>
	<input type="hidden" id="changeFlag" />
	<!-- <button onclick="ExportWord('${apprId}','${sptype}')" class="button">导出</button> -->
	<c:forEach items="${apprTypes}" var="apprType">
		<%-- <c:if test="${apprType.HANDLER_TYPE eq HANDLER_TYPE_HANDLER}"> --%>
			<tr>
				<td class="tableField" style="width:90px;word-spacing:2.6px;">
					<label class="Validform_label">
					${apprType.FIELD_LABEL}
					</label>
				</td>
				<td class="tableValue">
					<input type="hidden" id="rowsNum" value="1">
					<table id="${apprType.ID}table" cellpadding="10" cellspacing="1"
						style="width:100%; min-height:80px; table-layout:fixed; text-align:center; 
							background-color: #B8CCE2;">
						<tr>
							<c:choose>
								<c:when test="${empty apprType.count || apprType.count eq 1}">
								<td class="value"></td>
								</c:when>
								<c:otherwise>
									<!-- <td class="value" style="width:8%">发送人</td> -->
									<!-- <td class="value" style="width:13%">发送时间</td> -->
									<td class="value" style="width:18%;font-weight: bold;">审核人</td>
									<!-- <td class="value" style="width:8%">审核<br>状态</td> -->
									<td class="value" style="width:23%;font-weight: bold;">审核时间</td>
									<!-- <td class="value" style="width:8%">审核<br>意见</td> -->
									<td class="value" style="font-weight: bold;">意见内容</td>
								</c:otherwise>
							</c:choose>
							<%-- <c:if test="${send}">
								<td class="value" rowspan="${empty apprType.count ? 1 : apprType.count}" style="width:15%;">
									<input id="${apprType.ID}input" type="button" value="发送" class="sendButton"
										onclick="sendApprType('${apprType.ID}');">
								</td>
							</c:if> --%>
						</tr>
						<c:forEach items="${apprType.apprSendLogs}" var="apprSendLog">
							<c:forEach items="${apprSendLog.apprReceiveLogs}" var="apprReceiveLog" varStatus="i">
									<tr id='${apprReceiveLog.id}' title="发送人:${apprSendLog.operateUsername},发送时间:<fmt:formatDate value="${apprSendLog.operateDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
										<c:if test="${i.first}">
											<%-- <td class="value" rowspan="${apprSendLog.receiveCount}">
												${apprSendLog.operateUsername}
											</td> --%>
											<%-- <td class="value" rowspan="${apprSendLog.receiveCount}">
												<fmt:formatDate value="${apprSendLog.operateDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
											</td> --%>
										</c:if>
										<td class="value">
											${apprReceiveLog.receiveUsername}
										</td>
										<%-- <td class="value">
											<c:choose>
												<c:when test="${empty apprReceiveLog.operateTime}">
													待审核
												</c:when>
												<c:otherwise>
													已审核
												</c:otherwise>
											</c:choose>
										</td> --%>
										<td class="value">
											<fmt:formatDate value="${apprReceiveLog.operateTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<%-- <td class="value">
											<c:if test="${not empty apprReceiveLog.suggestionCode}">
												<t:convert codeType="1" code="SPYJ" value="${apprReceiveLog.suggestionCode}"></t:convert>
											</c:if>
										</td> --%>
										<td class="value" style="word-break:break-all;">
											<c:if test="${not empty apprReceiveLog.suggestionCode}">
                                    <font color="red">[<t:convert codeType="1" code="SPYJ" value="${apprReceiveLog.suggestionCode}"></t:convert>]</font>
                                    </c:if>
                                    ${apprReceiveLog.suggestionContent}
                                    </td>
									</tr>
							</c:forEach>
						</c:forEach>
					</table>
				</td>
			</tr>
		<%-- </c:if>
		<c:if test="${apprType.HANDLER_TYPE ne HANDLER_TYPE_HANDLER && apprType.buttonFlag eq 1}">
			<tr style="text-align:center;">
				<td colspan="2" class="value">
					<c:choose>
						<c:when test="${empty apprType.apprReceiveLog}">
							<input id="${apprType.ID}receiveId" type="hidden"/>
							<input id="${apprType.ID}input" type="button" value="${apprType.LABEL}" status="send"
								onclick="sendOrRecall('${apprType.ID}', this);"/>
							<span></span>
						</c:when>
						<c:otherwise>
							<input id="${apprType.ID}receiveId" type="hidden" value="${apprType.apprReceiveLog.id}"/>
							<input id="${apprType.ID}input" type="button" value="撤回" status="recall"
								onclick="sendOrRecall('${apprType.ID}', this);"/>
							<span>${apprType.LABEL}</span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:if> --%>
		</tr>
	</c:forEach>
</table>
<script type="text/javascript">
	//导出WORD
    function ExportWord(id,sptype) {
    	add('数据导出', "tPmOutcomeContractApprController.do?exportWordSp&id=" + id +"&sptype=" + sptype, "");   
    }
</script>