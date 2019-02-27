<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmContractCheckList" checkbox="false" fitColumns="false" 
			actionUrl="tPmContractCheckController.do?datagrid&datagridType=${datagridType}&operateStatus=${operateStatus}" 
			idField="id" fit="true" queryMode="group" onDblClick="dblhandlerCheckAppr">
		
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="130"></t:dgCol>
			<t:dgCol title="项目主键" field="projectid" hidden="true" queryMode="group" width="130"></t:dgCol>
			<t:dgCol title="审批表主键" field="appr_id" hidden="true" query="false" queryMode="single" width="130"></t:dgCol>
			<t:dgCol title="项目名称" field="project_name" query="true" queryMode="single" width="120" extendParams="formatter:detailProjectInfo,"></t:dgCol>
			<t:dgCol title="合同名称" field="contract_name" query="false" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="责任单位" field="duty_departname" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="组织单位" field="organization_unitname" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="验收时间" field="check_time" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="合同价款(元)" field="contract_amount" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="已付价款(元)" field="paid_money" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"> </t:dgCol>
			<t:dgCol title="代付价款(元)" field="wait_money" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="验收审批状态" field="operation_status" hidden="true" codeDict="1,SPZT" queryMode="group" width="80" ></t:dgCol>
			<t:dgCol title="合同主键id" field="contract_id" hidden="true" queryMode="group" width="80" ></t:dgCol>
			
			<t:dgCol title="审批类型" field="label" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审批处理类型" field="handler_type" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="是否可驳回" field="rebut_flag" hidden="true" queryMode="group" width="80"></t:dgCol>
			
			<!-- 已处理 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="审批意见" field="suggestion_code" queryMode="group" codeDict="1,SPYJ" width="80"></t:dgCol>
				<t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="handlerCheckAppr(appr_id)" ></t:dgFunOpt>
			</c:if>
			
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="发送人" field="operate_username" queryMode="group" width="80" ></t:dgCol>
			<t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt funname="handlerCheckAppr(appr_id,id,handler_type,operation_status,rebut_flag)" title="审批"></t:dgFunOpt>
			</c:if>
			
			<t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmIncomeContractApprController.do?goUpdateTab" 
				funname="detailIncomeAppr" height="600" width="750"></t:dgToolBar> 
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>		
<script type="text/javascript">
	//双击处理或查看审批
	function dblhandlerCheckAppr(rowIndex, rowDate){
		if(${operateStatus eq YES}){
			handlerCheckAppr(rowDate.appr_id);
		}else if(${operateStatus eq NO}){
			handlerCheckAppr(rowDate.appr_id, rowDate.id, rowDate.handler_type, rowDate.operation_status, rowDate.rebut_flag);
		}
	}
	
	//处理合同验收审批
	function handlerCheckAppr(apprId,receiveId,handlerType,apprStatus,rebutFlag){
		var title = "合同验收报告审批";
		var width = 900;
		var height = 500;
		var dialogId = "apprInfo";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_CHECK%>';
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
		
	}
	
	//查看进账合同（要修改）
	function detailIncomeAppr(title,url,gname,width,height){
		url += '&load=detail&node=false&receive=false';
		detailFun(title, url, gname, width, height, 'mainInfo')
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