<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,tools,easyui,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="bottom">
	<t:tab href="tPmDeclareController.do?goCheckListForDepartment" icon="icon-search" title="待审核申报书" id="default"></t:tab>
	<t:tab href="tPmDeclareController.do?goHistoryListForDepartment" icon="icon-search" title="已审核申报书" id="autocom"></t:tab>
</t:tabs>

