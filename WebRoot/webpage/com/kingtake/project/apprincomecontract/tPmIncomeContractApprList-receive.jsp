<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeContractApprList" checkbox="false" fitColumns="false" 
			actionUrl="tPmIncomeContractApprController.do?datagrid&operateStatus=${operateStatus}&datagridType=${datagridType}"
			onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group" sortName="operate_date">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目主键" field="projectid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同主键" field="contract_id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同状态" field="finish_flag" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="project_name" query="true" queryMode="single" width="150" extendParams="formatter:detailProjectInfo,"></t:dgCol>
			<t:dgCol title="合同名称" field="contract_name" query="true" queryMode="single" width="150"></t:dgCol>
			<%-- <t:dgCol title="合同类别" field="contract_type" hidden="false" queryMode="group" width="150" codeDict="1,HTLB"></t:dgCol>
			<t:dgCol title="其他合同类别" field="contract_type_content" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="合同申请单位" field="apply_unit"  queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="合同对方单位" field="approval_unit" hidden="false" queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="总经费(元)" field="total_funds" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			
			<t:dgCol title="预算审核表主键" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审核类型" field="label" queryMode="group" width="150"></t:dgCol>
			
			<!-- 已处理 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="审核意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="handlerIncomeAppr(appr_id)" ></t:dgFunOpt>
			</c:if>
			
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="发送人" field="operate_username" queryMode="group" width="80" ></t:dgCol>
				<t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="审核" funname="handlerIncomeAppr(appr_id,id,finish_flag)" ></t:dgFunOpt>
			</c:if>
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script src = "webpage/com/kingtake/project/apprincomecontract/tPmIncomeContractApprList.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		if(${operateStatus eq YES}){//已处理
			handlerIncomeAppr(rowData.appr_id);
		}else{
			handlerIncomeAppr(rowData.appr_id,rowData.id,rowData.finish_flag);
		}
	}

	//处理进账合同审核
	function handlerIncomeAppr(apprId,receiveId,apprStatus){
		var title = "进账合同审核信息";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_INCOME%>';
		var width = 900;
		var height = window.top.document.body.offsetHeight-100;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
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