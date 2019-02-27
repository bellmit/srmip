<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="callBackType" type="hidden" />
<input id="inOrOutContract" type="hidden" value="<%=ProjectConstant.INCOME_CONTRACT%>"/>

<t:tabs id="addContractInfo" iframe="false" tabPosition="top">
	<t:tab href="tPmIncomeContractApprController.do?goAdd&project.id=${projectId}" icon="icon-search"
		title="项目内容" id="tPmIncomeContractAppr" ></t:tab>
	<t:tab href="tPmContractNodeController.do?tPmContractNode&contractId=index" icon="icon-search"
		title="项目节点信息" id="tPmContractNode"></t:tab>
</t:tabs>
