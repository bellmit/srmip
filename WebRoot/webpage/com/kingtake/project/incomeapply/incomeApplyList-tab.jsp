<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="applyInfo" iframe="false" tabPosition="top">
  <t:tab href="tPmIncomeApplyController.do?incomeAllotList&projectId=${projectId}&loadType=HT" icon="icon-search" title="到账信息" id="applyTab1"></t:tab>
  <t:tab href="tPmIncomeApplyController.do?incomeApplyList&projectId=${projectId}&schoolCooperationFlag=${schoolCooperationFlag}&planContractFlag=${planContractFlag}" icon="icon-search" title="来款申请" id="applyTab2"></t:tab>
</t:tabs>
