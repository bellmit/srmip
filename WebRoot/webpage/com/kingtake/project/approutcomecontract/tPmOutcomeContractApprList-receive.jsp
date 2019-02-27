<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmOutcomeContractApprList" checkbox="false" fitColumns="false" 
			actionUrl="tPmOutcomeContractApprController.do?datagrid&operateStatus=${operateStatus}&datagridType=${datagridType}"
			onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group" sortName="operate_date">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目主键" field="projectid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同状态" field="finish_flag" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectname_subjectcode" query="true" queryMode="single" width="120" extendParams="formatter:detailProjectInfo,"></t:dgCol>
			<t:dgCol title="合同编号" field="contractCode" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
            <t:dgCol title="合同名称" field="contract_name" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同申请单位" field="apply_unit" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同对方单位" field="approval_unit" hidden="false" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>
			<t:dgCol title="总经费(元)" field="total_funds" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="开始时间" field="start_time" queryMode="group" width="90" formatter="yyyy-MM-dd" align='center'></t:dgCol>
			<t:dgCol title="结束时间" field="end_time" queryMode="group" width="90" formatter="yyyy-MM-dd" align='center'></t:dgCol>
			<t:dgCol title="采购方法" field="acquisition_method" queryMode="group" width="120" codeDict="1,CGFS"></t:dgCol>
			<t:dgCol title="合同类型" field="contract_type" queryMode="group" width="80" align="right" codeDict="1,HTLB" ></t:dgCol>
			
			<t:dgCol title="预算审批表主键" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审批类型" field="label" queryMode="group" width="120"></t:dgCol>
			
			<!-- 已处理 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="审批意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="handlerOutcomeAppr(appr_id, contract_type)" ></t:dgFunOpt>
			</c:if>
			
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="发送人" field="operate_username" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="审批" funname="handlerOutcomeAppr(appr_id,contract_type,id,finish_flag)" ></t:dgFunOpt>
			</c:if>
		
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>

<script src = "webpage/com/kingtake/project/approutcomecontract/tPmOutcomeContractApprList.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		if(${operateStatus eq YES}){//已处理
			handlerOutcomeAppr(rowData.appr_id, rowData.contract_type);
		}else{
			handlerOutcomeAppr(rowData.appr_id, rowData.contract_type, rowData.id, rowData.finish_flag);
		}
	}

	//处理出账合同审批
	function handlerOutcomeAppr(apprId,contractType,receiveId,apprStatus){
		var title = "出账合同审批信息";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&contractType=' + contractType +
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_OUTCOME%>';
		var width = '100%';
		var height = '100%';
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
	}
 
 	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmOutcomeContractApprListtb").find("input[name='finishTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='finishTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='startTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='startTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='endTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='endTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmOutcomeContractApprListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 });
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmOutcomeContractApprController.do?upload', "tPmOutcomeContractApprList");
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("tPmOutcomeContractApprController.do?exportXls","tPmOutcomeContractApprList");
	}

	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmOutcomeContractApprController.do?exportXlsByT","tPmOutcomeContractApprList");
	}
</script>