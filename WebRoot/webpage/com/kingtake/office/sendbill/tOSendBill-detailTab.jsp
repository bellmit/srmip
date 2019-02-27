<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant" %>
<%
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
%>
<style>
a{
	color:green;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="top">
	<c:forEach items="${list}" var="bill" varStatus="s">
		<c:choose>
			<c:when test="${bill.archiveFlag eq rebut}">
				<t:tab title='${bill.sendTitle}<font color="red">(被驳回)</font>' href="tOSendBillController.do?goDetail&id=${bill.id}" icon="icon-search" id="${bill.id}"></t:tab>
			</c:when>
			<c:otherwise>
				<t:tab title="${bill.sendTitle}" href="tOSendBillController.do?goDetail&id=${bill.id}" icon="icon-search" id="${bill.id}"></t:tab>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</t:tabs>
