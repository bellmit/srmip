<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<%-- 	<t:tab iframe="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType=1" icon="icon-search" --%>
<%-- 		title="计划类" id="PROJECT_PLAN"></t:tab> --%>
<%-- 	<t:tab iframe="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType=2" icon="icon-search" --%>
<%-- 		title="合同类" id="PROJECT_CONTRACT"></t:tab> --%>
	<t:tab iframe="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType=3" icon="icon-search"
		title="生产订货类合同价款" id="CONTRACT1"></t:tab>
	<t:tab iframe="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType=4" icon="icon-search"
		title="研制类合同价款" id="CONTRACT2"></t:tab>
	<t:tab iframe="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType=5" icon="icon-search"
		title="采购类合同价款" id="CONTRACT3"></t:tab>
	<t:tab iframe="tBApprovalBudgetRelaController.do?tBApprovalBudgetRela&projectType=6" icon="icon-search"
		title="技术服务类合同价款" id="CONTRACT4"></t:tab>
</t:tabs>