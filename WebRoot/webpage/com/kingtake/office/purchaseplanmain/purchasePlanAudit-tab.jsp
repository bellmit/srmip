<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="applyInfo" iframe="true" tabPosition="top">
  <t:tab href="tBPurchasePlanMainController.do?goAuditForDepartment&type=1" icon="icon-search" title="已提交的采购计划" id="applyTab1"></t:tab>
  <t:tab href="tBPurchasePlanMainController.do?goAuditForDepartment&type=2" icon="icon-search" title="本人审核的采购计划" id="applyTab2"></t:tab>
</t:tabs>
