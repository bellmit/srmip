<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmContractNodeCheckList" checkbox="false" fitColumns="false" title="出账合同节点考核"
			actionUrl="tPmContractNodeCheckController.do?datagrid&datagridType=${datagridType}&projectId=${projectId}" 
			idField="id" fit="true" queryMode="group" pagination="false">
			
			<t:dgCol title="合同节点id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同主键id" field="in_out_contractid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同名称" field="contract_name" query="false" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="节点名称" field="node_name"  queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="完成时间" field="complete_date" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="验收状态" field="check_status" query="false" queryMode="group" codeDict="1,SPZT" width="80"></t:dgCol>
			<t:dgCol title="合同验收主键" field="check_id" hidden="true" query="false" queryMode="group" width="80"></t:dgCol>
			
			<t:dgCol title="节点验收操作" field="opt" width="240" frozenColumn="true"></t:dgCol>
			<t:dgFunOpt exp="check_status#eq#u" funname="addOrUpdateCheck(id,check_id)" title="录入"></t:dgFunOpt>
			<t:dgFunOpt exp="check_status#ne#u" funname="addOrUpdateCheck(id,check_id)" title="编辑"></t:dgFunOpt>
			<t:dgFunOpt exp="check_status#ne#u" funname="sendCheckAppr(check_id, check_status)" title="发送审批"></t:dgFunOpt>
			<t:dgFunOpt exp="check_status#eq#1" funname="updateCheckOperateStatus(check_id)" title="完成"></t:dgFunOpt>
			<t:dgFunOpt exp="check_status#eq#2" funname="updateCheckOperateStatus(check_id)" title="取消完成"></t:dgFunOpt>
			
			<t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false" 
				funname="detailContract" height="600" width="750"></t:dgToolBar>
			<t:dgToolBar title="查看合同节点" icon="icon-search" url="tPmContractNodeController.do?goUpdate" 
				funname="detail" width="600" height="380"></t:dgToolBar>
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>		
<script type="text/javascript">
	//录入或更新合同验收
	function addOrUpdateCheck(contractNodeId,checkId){
		var title = "录入合同节点验收信息";
		var gname = "tPmContractNodeCheckList";
		var width = 900;
		var height = 430;
		var dialogId = "apprInfo";
		if(checkId == ""){
			//尚未生成表单
			var url = "tPmContractNodeCheckController.do?goAdd&contractNodeId=" + contractNodeId;
			addFun(title, url, gname, width, height, dialogId);
		}else{
			title = "编辑合同节点验收信息";
			var judgeUrl = "tPmContractNodeCheckController.do?updateFlag";
			var updateUrl = "tPmContractNodeCheckController.do?goUpdate";
			var unEditUrl = updateUrl + "&load=detail&tip=true";
			judgeUpdate(title,width,height,checkId,judgeUrl,updateUrl,unEditUrl,dialogId);
		}
	}
	
	//审批tab页
	function sendCheckAppr(checkId, finishFlag){
		var title = "审批";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
				'&apprId=' + checkId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_NODECHECK%>';
		var width = 900;
		var height = 500;
		var dialogId = "apprInfo";
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("合同节点验收报告审批流程已完成，不能再发送审批");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑合同节点验收信息！");
		}
		
		tabDetailDialog(title, url, width, height, dialogId);
	}
	
	//更新预算审批状态
	function updateCheckOperateStatus(checkId){
		var url1 = 'tPmContractNodeCheckController.do?updateOperateStatus';
		var url2 = url1 + '2';
		updateFinishFlag(checkId, url1, url2);
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