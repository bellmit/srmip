<!-- 预算审核页面（待处理审核和已处理审核 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="top" >
	<t:tab href="tPmProjectFundsApprController.do?tPmProjectFundsAppr&type=0" icon="icon-search"
		title="未处理" id="unfinish"></t:tab>
	<t:tab href="tPmProjectFundsApprController.do?tPmProjectFundsAppr&type=1" icon="icon-search"
		title="已处理" id="finish"></t:tab>
</t:tabs>
