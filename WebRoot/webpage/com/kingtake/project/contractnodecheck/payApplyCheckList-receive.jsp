<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="payApplyCheckList" checkbox="false" fitColumns="false" 
			actionUrl="tPmContractNodeCheckController.do?payApplyCheckDatagrid&datagridType=${type}" 
			idField="id" fit="true" queryMode="group" onDblClick="dblhandlerNodeCheckAppr">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目主键" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" query="false" queryMode="single" width="120" extendParams="formatter:detailProject,"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName" query="false" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同id" field="contractId" hidden="true" query="false" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同节点名称" field="jdmc" query="false" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="责任单位" field="zrdw"  queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="项目组长" field="xmzz"  queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="合同乙方" field="yfmc"  queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="节点金额" field="jdje"  queryMode="single" width="80" 
				align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="合同总金额" field="contractTotalFunds" extendParams="formatter:formatCurrency," queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="支付状态" field="payFlag" replace="未审核_0,审核通过_1,审核不通过_2" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="发送人" field="jhcshfsr" queryMode="group" width="80" ></t:dgCol>
			<t:dgCol title="发送时间" field="jhcshfssj" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
			
			<!-- 已处理 -->
			<c:if test="${type eq YES}">
				<t:dgCol title="意见内容" field="msgText" overflowView="true" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="viewNodeCheckAppr(id)" ></t:dgFunOpt>
			</c:if>
			
			<c:if test="${type eq NO}">
				<t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="审批" funname="handlerNodeCheckAppr(id)" ></t:dgFunOpt>
			</c:if>
			
			<t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmOutcomeContractApprController.do?goUpdateTab&load=detail&node=false" 
				funname="detailContract" height="600" width="750"></t:dgToolBar>
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/contractnodecheck/outcomeContractNodeCheckList-receive.js?${tm}"></script>		
<script type="text/javascript">
	//双击处理或查看审批
	function dblhandlerNodeCheckAppr(rowIndex, rowDate){
		if(${type eq YES}){
			viewNodeCheckAppr(rowDate.id);
		}else if(${type eq NO}){
			handlerNodeCheckAppr(rowDate.id);
		}
	}
	
	//处理进账合同审批
	function handlerNodeCheckAppr(apprId){
		var title = "支付申请审核";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
				'&apprId=' + apprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_NODECHECK%>';
		var width = 900;
		var height = 500;
		var flag = "1";
		tabDetailDialog2(title, url, width, height, flag ,apprId);
	}
	
	//处理进账合同审批
	function viewNodeCheckAppr(apprId){
		var title = "支付申请审核";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
				'&apprId=' + apprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_NODECHECK%>';
		var width = 900;
		var height = 500;
		var flag = "0";
		tabDetailDialog2(title, url, width, height, flag ,apprId);
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
	 url += '&load=detail&id='+rowsData[0].contractId;
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
	
	function detailProject(value,row,index){
		var html = "<a href=\"#\" onclick=\"openwindow('项目基本信息', 'tPmProjectController.do?goUpdateForResearchGroup&load=detail&id="+row.projectId+"', 'payApplyCheckList', '100%', '100%')\" "+
			"style=\"color:blue;\" "+">"+value+"</a>"
		return html;
	}
</script>