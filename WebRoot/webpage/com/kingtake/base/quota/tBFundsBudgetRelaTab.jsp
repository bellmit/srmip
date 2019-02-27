<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<t:tabs id="tt" iframe="false" tabPosition="top" >
	<t:tab iframe="tBFundsBudgetRelaController.do?tBFundsBudgetRela&fundId=${fundId}&projectType=1" 
		icon="icon-search" title="计划类" id="PROJECT_PLAN"></t:tab>
	<t:tab iframe="tBFundsBudgetRelaController.do?tBFundsBudgetRela&fundId=${fundId}&projectType=2" 
		icon="icon-search" title="合同类" id="PROJECT_CONTRACT"></t:tab>
</t:tabs>