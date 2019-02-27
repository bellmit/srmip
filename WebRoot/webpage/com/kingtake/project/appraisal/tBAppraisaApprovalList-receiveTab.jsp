<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
	<t:tab iframe="tBAppraisaApprovalController.do?tBAppraisaApproval&type=0" icon="icon-search"
		title="未处理" id="unfinishContract"></t:tab>
	<t:tab iframe="tBAppraisaApprovalController.do?tBAppraisaApproval&type=1" icon="icon-search"
		title="已处理" id="finishContract"></t:tab>
</t:tabs>
