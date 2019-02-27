<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
	<t:tab iframe="tZSqwjController.do?goApplyAuditDepartment&type=0" icon="icon-search"
		title="未处理" id="unfinishTab"></t:tab>
	<t:tab iframe="tZSqwjController.do?goApplyAuditDepartment&type=1" icon="icon-search"
		title="已处理" id="finishTab"></t:tab>
</t:tabs>
