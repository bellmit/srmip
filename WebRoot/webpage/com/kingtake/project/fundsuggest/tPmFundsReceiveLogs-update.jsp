<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>预算审批流转接收表</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	<script type="text/javascript">
	//编写自定义JS代码
	//选择常用意见
	function changeContent(){
		$("#suggestionContent").text(
			$("#commonSuggestion").find('option:selected').text()
		);
	}
	
	/**
	 * 审批完成以后刷新合同页面
	 */
	function reloadApprInfo(data){
		if (data.success == true) {
			var apprInfo = window.parent.apprInfo;
			var logsFrame = $("#apprLogs", apprInfo.document);
			logsFrame.attr("src", logsFrame.attr("src"));
			var li = $(".tabs li:last", apprInfo.document);
			li.click();
			var apprInfo = frameElement.api.opener;
			apprInfo.tip(data.msg);
			apprInfo.frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}
	</script>
	</head>
	<body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
			action="tPmFundsReceiveLogsController.do?doUpdate" tiptype="1" tipSweep="true" 
			callback="@Override reloadApprInfo">
			<input id="id" name="id" type="hidden" value="${tPmFundsReceiveLogsPage.id }">
			<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							审批类型：
						</label>
					</td>
					<td class="value">
					    <%-- <t:codeTypeSelect id="suggestionType" name="suggestionType" type="select"  codeType="1" code="YSSPLX"
							defaultVal='${tPmFundsReceiveLogsPage.suggestionType}' extendParam="{disabled:'disabled'}"></t:codeTypeSelect> --%>
						<t:convert codeType="1" code="YSSPLX" value="${tPmFundsReceiveLogsPage.suggestionType}"></t:convert>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">审批类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							审批意见:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<!--处理状态为已处理为真时，审批意见不可更改  -->
						<c:if test="${statusFlag}">
							<t:convert codeType="1" code="SPYJ" value="${tPmFundsReceiveLogsPage.suggestionCode}"></t:convert>
						</c:if>
					    <c:if test="${!statusFlag}">
							<t:codeTypeSelect id="suggestionCode" name="suggestionCode" type="radio" codeType="1" code="SPYJ"
					    		defaultVal="<%=ReceiveBillConstant.AUDIT_PASS%>"></t:codeTypeSelect>
						</c:if>
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
							 extendParam="{onclick:'changeContent();'}"></t:codeTypeSelect>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							意见内容:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
					    <textarea id="suggestionContent" name="suggestionContent" style="width:300px;height:100px;"
					    	datatype="*1-25"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">意见内容</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
	</body>
<script src = "webpage/com/kingtake/project/fundsuggest/tPmFundsReceiveLogs.js"></script>		