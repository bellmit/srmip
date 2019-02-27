<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" id="contentContainer" fit="true" style="height:500px;">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeContractApprList" checkbox="false" fitColumns="true" title="关联进账合同列表"
			actionUrl="tPmOutcomeContractApprController.do?relatedIncomeListDatagrid" 
			onDblClick="dblClickDetail" idField="id"  queryMode="group" sortName="startTime" sortOrder="desc" >
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="申请单位" field="applyUnit" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName" query="true" queryMode="single" frozenColumn="true" width="120"></t:dgCol>
			<%-- <t:dgCol title="合同类别" field="contractType" codeDict="1,HTLB" hidden="false" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="其他合同类别" field="contractTypeContent" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="对方单位" field="approvalUnit" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>
			<t:dgCol title="合同第三方" field="theThird" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="截止时间" field="endTime" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="总经费(元)" field="totalFunds" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			
			<%-- <t:dgCol title="审批状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="80" align="right"></t:dgCol> --%>
			<t:dgCol title="完成时间" field="finishTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			
			<t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
		
		</div>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	var li_east = 0;
	//查看来款节点
	function detailIncomeNode(id){
		if(li_east == 0){
	        $('#contentContainer').layout('expand','east');
	    }
	    $('#contentContainer').layout('panel','east').panel('setTitle', "来款节点");
	    $('#incomeNodeList').panel("refresh", 'tPmIncomeNodeController.do?incomeContracToIncomeNode&contractId='+id);
	}
	
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		var title = "查看";
		var url = "tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false&id=" + rowData.id;
		var width = 750;
		var height = 600;
		tabDetailDialog(title, url, width, height);
	}

	//查看进账合同
	function detailIncomeAppr(title,url,gname,width,height){
		url += '&load=detail&node=false';
		detailFun(title, url, gname, width, height, 'mainInfo');
	}
	
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmIncomeContractApprListtb").find("input[name='startTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='startTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='endTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='endTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmIncomeContractApprController.do?upload', "tPmIncomeContractApprList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmIncomeContractApprController.do?exportXls","tPmIncomeContractApprList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmIncomeContractApprController.do?exportXlsByT","tPmIncomeContractApprList");
	}
</script>