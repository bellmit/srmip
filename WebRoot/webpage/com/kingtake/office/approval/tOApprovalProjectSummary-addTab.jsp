<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<t:tab iframe="tOApprovalProjectSummaryController.do?goAdd&projectIds=${projectIds}" icon="icon-search" title="呈批件信息" id="o"></t:tab>
<t:tab iframe="tOApprovalProjectSummaryController.do?gotPmProjectListForApproval&projectIds=${projectIds}" icon="icon-search" title="项目信息" id="t"></t:tab>
</t:tabs>
