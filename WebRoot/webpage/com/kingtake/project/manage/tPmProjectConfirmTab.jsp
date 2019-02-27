<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="top">
  <t:tab href="tPmProjectController.do?tPmProjectStateList" icon="icon-search" title="待审批" id="unConfirm"></t:tab>
  <t:tab href="tPmProjectController.do?tPmProjectConfirmList" icon="icon-search" title="已审批" id="confirm"></t:tab>
</t:tabs>
