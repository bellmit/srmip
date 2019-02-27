<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmContractCheckList" checkbox="false" fitColumns="true" 
			actionUrl="tPmContractCheckController.do?datagrid&datagridType=${datagridType}&projectId=${projectId}" 
		idField="id" fit="true" queryMode="group" pagination="false">
		
		<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="110"></t:dgCol>
		<t:dgCol title="合同名称" field="contract_name" query="false" queryMode="single" width="100"></t:dgCol>
		<t:dgCol title="申请单位" field="apply_unit" queryMode="group" width="100"></t:dgCol>
		<t:dgCol title="对方单位" field="approval_unit" queryMode="group" width="100"></t:dgCol>
		<t:dgCol title="开始时间" field="start_time" hidden="true" formatter="yyyy-MM-dd" query="false" queryMode="group" width="90" align="center"></t:dgCol>
		<t:dgCol title="截止时间" field="end_time" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
		<t:dgCol title="总经费(元)" field="total_funds" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
		<t:dgCol title="合同审批状态" field="contract_status" codeDict="1,SPZT" hidden="true" queryMode="group" width="80" align="right"></t:dgCol>
		<t:dgCol title="创建人" field="create_by" queryMode="group" hidden="true" width="80" align="right"></t:dgCol>
		<t:dgCol title="验收主表id" field="check_id" hidden="true" queryMode="group" width="80" align="right"></t:dgCol>
		<t:dgCol title="验收状态" field="check_status" codeDict="1,SPZT" queryMode="group" width="80" align="right"></t:dgCol>
		
		<t:dgCol title="合同验收报告操作" field="opt" width="180" frozenColumn="true"></t:dgCol>
		<t:dgFunOpt exp="check_status#eq#u" funname="addOrUpdateCheck(contract_status,id,check_id,create_by)" 
			title="录入"></t:dgFunOpt>
		<t:dgFunOpt exp="check_status#ne#u&&check_status#ne#1&&check_status#ne#2" funname="addOrUpdateCheck(contract_status,id,check_id,create_by)" 
			title="编辑"></t:dgFunOpt>
		<t:dgFunOpt exp="check_status#ne#u" funname="sendCheckAppr(check_id, check_status)" 
			title="发送审批"></t:dgFunOpt>
		
		<t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false" 
			funname="detail" height="600" width="725"></t:dgToolBar> 
		<t:dgToolBar title="查看合同验收报告" icon="icon-search" url="tPmContractCheckController.do?goUpdate" 
			funname="detailCheck" height="640" width="700"></t:dgToolBar> 
		
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
	//查看合同验收报告方法
	function detailCheck(title,url, id,width,height){
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
		if(rowsData[0].contract_status == "<%=ApprovalConstant.APPRSTATUS_FINISH%>"){
			if(rowsData[0].check_id == ""){
				tip("尚未录入合同验收报告，请录入后再进行查看！");
			}else{
	    		url += '&load=detail&id='+rowsData[0].check_id;
	    		createdetailwindow(title,url,width,height);
			}
		}else{
			tip("合同审批尚未完成，不能查看合同验收报告！");
		}
	}
	
	//录入或更新合同验收
	function addOrUpdateCheck(contractStatus,contractId,checkId,creatBy){
		var title = "录入合同验收信息";
		var gname = "tPmContractCheckList";
		var width = 700;
		var height = '100%';
		var dialogId = "apprInfo";
		
		if(contractStatus == "<%=ApprovalConstant.APPRSTATUS_FINISH%>"){
			//合同审批已完成
			if(checkId == ""){
				//尚未生成表单
				var url = "tPmContractCheckController.do?goAdd&contractId=" + contractId;
				addFun(title, url, gname, width, height, dialogId);
			}else{
				title = "编辑合同验收信息";
				var judgeUrl = "tPmContractCheckController.do?updateFlag";
				var updateUrl = "tPmContractCheckController.do?goUpdate";
				var unEditUrl = updateUrl + "&load=detail&tip=true";
				judgeUpdate(title,width,height,checkId,judgeUrl,updateUrl,unEditUrl,dialogId);
			}
		}else{
			tip("合同审批尚未完成，不能进行合同验收！");
		}
	}
	
	//审批tab页
	function sendCheckAppr(checkId, finishFlag){
		var title = "审批";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
				'&apprId=' + checkId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_CHECK%>';
		var width = 900;
		var height = '100%';
		var dialogId = "apprInfo";
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("合同验收报告审批流程已完成，不能再发送审批");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑合同验收信息！");
		}
		
		sendApprDialog(title, url, width, height, checkId, finishFlag, '<%=ApprovalConstant.APPR_TYPE_CHECK%>');
	}
	
	//更新预算审批状态
	function updateCheckOperateStatus(checkId){
		var url1 = 'tPmContractCheckController.do?updateOperateStatus';
		var url2 = url1 + '2';
		updateFinishFlag(checkId, url1, url2);
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