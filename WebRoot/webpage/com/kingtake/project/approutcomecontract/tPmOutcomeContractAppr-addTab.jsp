<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script src = "webpage/com/kingtake/project/approutcomecontract/tPmOutcomeContractAppr.js"></script>

<input id="callBackType" type="hidden" />
<input id="inOrOutContract" type="hidden" value="<%=ProjectConstant.OUTCOME_CONTRACT%>"/>

<t:tabs id="addContractInfo" iframe="false" tabPosition="top">
	<t:tab href="tPmOutcomeContractApprController.do?goAdd&project.id=${projectId}" icon="icon-search"
		title="合同信息" id="tPmOutcomeContractAppr" ></t:tab>
	<t:tab href="tPmContractNodeController.do?tPmContractNode&contractId=index" icon="icon-search"
		title="合同节点信息" id="tPmContractNode"></t:tab>
</t:tabs>
