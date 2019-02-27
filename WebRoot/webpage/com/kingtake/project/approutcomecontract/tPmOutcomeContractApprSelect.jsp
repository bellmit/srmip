<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" id="contentContainer" style="height:430px;width:790px;" fit="false">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmOutcomeContractApprList" checkbox="false" fitColumns="false" 
			actionUrl="tPmOutcomeContractApprController.do?datagrid4Select&project.id=${projectId}" 
			onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group" sortName="startTime" sortOrder="desc">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectnameSubjectcode" hidden="true" queryMode="group" width="120"></t:dgCol> 
			<t:dgCol title="申请单位" field="applyUnit" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="合同编号" field="contractCode" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="对方单位" field="approvalUnit" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>
			<t:dgCol title="合同第三方" field="theThird" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="截止时间" field="endTime" formatter="yyyy-MM-dd"  queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="总经费(元)" field="totalFunds" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="合同类型" field="contractType" hidden="false" queryMode="group" width="80" align="right" codeDict="1,HTLB" ></t:dgCol>
			
			<t:dgCol title="审批状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="完成时间" field="finishTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90"></t:dgCol>
			
			<t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" queryMode="group" hidden="true" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90"></t:dgCol>
			
			<t:dgToolBar title="查看" icon="icon-search" url="tPmOutcomeContractApprController.do?goUpdateTab" 
				funname="detailOutcomeAppr" height="600" width="750"></t:dgToolBar>
		
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
		</div>
	</div>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		var title = "查看";
		var url = "tPmOutcomeContractApprController.do?goUpdateTab&load=detail&node=false&id=" + rowData.id;
		var width = 750;
		var height = 600;
		tabDetailDialog(title, url, width, height);
	}

	//查看出账合同
	function detailOutcomeAppr(title,url,gname,width,height){
		url += '&load=detail&node=false';
		detailFun(title, url, gname, width, height, 'mainInfo');
	}
</script>