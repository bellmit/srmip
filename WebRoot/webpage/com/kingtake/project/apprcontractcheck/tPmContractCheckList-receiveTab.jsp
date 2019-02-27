<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="top" >
	<t:tab href="tPmContractCheckController.do?tPmContractCheck&type=0" icon="icon-search"
		title="未完成" id="unfinish"></t:tab>
	<t:tab href="tPmContractCheckController.do?tPmContractCheck&type=1" icon="icon-search"
		title="已完成" id="finish"></t:tab>
</t:tabs>
