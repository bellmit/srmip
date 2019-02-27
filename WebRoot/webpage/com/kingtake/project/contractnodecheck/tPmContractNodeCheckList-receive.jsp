<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmContractNodeCheckList" checkbox="false" fitColumns="false" 
			actionUrl="tPmContractNodeCheckController.do?datagrid&datagridType=${datagridType}&operateStatus=${operateStatus}" 
			idField="id" fit="true" queryMode="group" onDblClick="dblhandlerNodeCheckAppr">
		
			<t:dgCol title="接受log表主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目主键" field="projectid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="project_name" query="true" queryMode="single" width="120" extendParams="formatter:detailProjectInfo,"></t:dgCol>
			<t:dgCol title="合同名称" field="contract_name" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="节点名称" field="node_name" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同价款(元)" field="contract_amount" query="false" queryMode="single" width="80" 
				align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="节点价款(元)" field="node_amount" queryMode="group" width="80" 
				align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="组织单位" field="organization_unitname" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="节点时间" field="node_time" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="节点验收时间" field="check_time" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="验收审批状态" field="operation_status" hidden="true" query="false" queryMode="group" codeDict="1,SPZT" width="80"></t:dgCol>
			<t:dgCol title="合同主键" field="in_out_contractid" hidden="true" query="false" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="合同节点主键" field="contract_node_id" hidden="true" query="false" queryMode="group" width="80"></t:dgCol>
			
			<t:dgCol title="审批表主键" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="发送人" field="operate_username" queryMode="group" width="80" ></t:dgCol>
			<t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
			<t:dgCol title="审批类型" field="label" queryMode="group" width="120"></t:dgCol>
			
			<!-- 已处理 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="审批意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="handlerNodeCheckAppr(appr_id)" ></t:dgFunOpt>
			</c:if>
			
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="审批" funname="handlerNodeCheckAppr(appr_id,id,operation_status)" ></t:dgFunOpt>
			</c:if>
			
			<t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false" 
				funname="detailContract" height="600" width="750"></t:dgToolBar>
			<t:dgToolBar title="查看合同节点" icon="icon-search" url="tPmContractNodeController.do?goUpdate" 
				funname="detailNode" width="600" height="380"></t:dgToolBar>
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	//双击处理或查看审批
	function dblhandlerNodeCheckAppr(rowIndex, rowDate){
		if(${operateStatus eq YES}){
			handlerNodeCheckAppr(rowDate.appr_id);
		}else if(${operateStatus eq NO}){
			handlerNodeCheckAppr(rowDate.appr_id, rowDate.id, rowDate.operation_status);
		}
	}
	
	//处理进账合同审批
	function handlerNodeCheckAppr(apprId,receiveId,apprStatus){
		var title = "合同节点审批信息";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
				'&apprId=' + apprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_NODECHECK%>';
		var width = 900;
		var height = 500;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
	}
	
	//查看合同信息
	function detailContract(title,url,id,width,height) {
		var rowsData = $('#'+id).datagrid('getSelections');
		
		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
	 url += '&load=detail&id='+rowsData[0].in_out_contractid;
	 tabDetailDialog(title,url,width,height);
	}
	
	//查看合同节点信息
	function detailNode(title,url,id,width,height) {
		var rowsData = $('#'+id).datagrid('getSelections');
		
		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
		url += '&load=detail&id='+rowsData[0].contract_node_id;
		tabDetailDialog(title,url,width,height);
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