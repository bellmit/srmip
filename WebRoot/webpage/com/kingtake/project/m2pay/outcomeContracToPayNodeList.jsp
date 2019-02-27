<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmPayNodeList" checkbox="false" fitColumns="true" idField="id" fit="true" queryMode="group"
			actionUrl="tPmPayNodeController.do?datagrid&contractId=${contractId}"
			sortName="createDate" sortOrder="desc" pagination="false">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="支付时间" field="payTime" formatter="yyyy-MM-dd" query="false" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="支付金额(元)" field="payAmount" queryMode="group" width="80" align="right"></t:dgCol>
			<t:dgCol title="备注" field="memo" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createName" queryMode="group" width="80"></t:dgCol>
			
			<t:dgToolBar title="查看支付节点" icon="icon-search" url="tPmPayNodeController.do?goUpdate" 
				funname="detail" width="550" height="400"></t:dgToolBar>
				
		</t:datagrid>
	</div>
</div>
