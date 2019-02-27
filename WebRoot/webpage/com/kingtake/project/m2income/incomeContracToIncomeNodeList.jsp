<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeNodeList" checkbox="false" fitColumns="true" 
			actionUrl="tPmIncomeNodeController.do?datagrid&contractId=${contractId}" 
			idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc" pagination="false">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" query="false" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="来款金额(元)" field="incomeAmount" align="right" queryMode="group" width="80" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="来款说明" field="incomeExplain" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createName" queryMode="group" width="60"></t:dgCol>
			
			<t:dgToolBar title="查看来款节点" icon="icon-search" url="tPmIncomeNodeController.do?goUpdate" 
				funname="detail" width="550" height="400"></t:dgToolBar>
		
		</t:datagrid>
	</div>
</div>