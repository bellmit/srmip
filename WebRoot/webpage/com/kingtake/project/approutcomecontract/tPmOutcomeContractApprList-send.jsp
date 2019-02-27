<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div style="width: 100%;height: 300px;">
		<t:datagrid name="tPmOutcomeContractApprList" checkbox="false" fitColumns="false" 
			actionUrl="tPmOutcomeContractApprController.do?datagrid&project.id=${projectId}&datagridType=${datagridType}" 
			onDblClick="dblClickDetail" idField="id" fit="false" queryMode="group" sortName="startTime" sortOrder="desc">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectnameSubjectcode" hidden="true" queryMode="group" width="120"></t:dgCol> 
			<t:dgCol title="申请单位" field="applyUnit" queryMode="group" width="180"></t:dgCol>
			<t:dgCol title="合同编号" field="contractCode" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
            <t:dgCol title="合同名称" field="contractName" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="对方单位" field="approvalUnit" queryMode="single" width="180" query="true" isLike="true"></t:dgCol>
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
			
			<t:dgCol title="操作" field="opt" width="140" frozenColumn="true"></t:dgCol>
			<t:dgDelOpt exp="finishFlag#eq#0" title="删除" url="tPmOutcomeContractApprController.do?doDel&id={id}"/>
			<t:dgFunOpt title="价款计算书" funname="openPriceWin(id, contractType)"></t:dgFunOpt>
			<%-- <t:dgFunOpt title="发送审批" funname="sendOutcomeAppr(id, contractType, finishFlag)" ></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#1" title="完成" funname="updateOutcomeApprFinishFlag(id)" ></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#2" title="取消完成" funname="updateOutcomeApprFinishFlag(id)" ></t:dgFunOpt> --%>
			<%-- <t:dgFunOpt title="查看支付节点" funname="detailIncomeNode(id)" ></t:dgFunOpt> --%>
			
			<t:dgToolBar title="录入" icon="icon-add" url="tPmOutcomeContractApprController.do?goAddTab&projectId=${projectId}" 
				funname="addOutcomeAppr" height="600" width="950"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tPmOutcomeContractApprController.do?goUpdateTab" 
				funname="updateOutcomeAppr" height="600" width="950"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tPmOutcomeContractApprController.do?goUpdateTab" 
				funname="detailOutcomeAppr" height="600" width="950"></t:dgToolBar>
		    <t:dgToolBar title="生成合同文本" icon="icon-putout" funname="ExportWord"></t:dgToolBar>
		</t:datagrid>
        </div>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
	//查看来款节点
	function detailIncomeNode(id){
		if(li_east == 0){
	        $('#contentContainer').layout('expand','east');
	    }
	    $('#contentContainer').layout('panel','east').panel('setTitle', "支付节点");
	    $('#payNodeList').panel("refresh", 'tPmPayNodeController.do?outcomeContracToPayNode&contractId='+id);
	}

	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		var title = "查看";
		var url = "tPmOutcomeContractApprController.do?goUpdateTab&load=detail&node=false&id=" + rowData.id;
		var width = 750;
		var height = window.top.document.body.offsetHeight-100;
		tabDetailDialog(title, url, width, height);
	}

	//发送出账合同审批
	function sendOutcomeAppr(outcomeApprId, contractType, finishFlag){
		var title = "审批";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
				'&contractType=' + contractType +
				'&apprId=' + outcomeApprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_OUTCOME%>';
		var width = '100%';
		var height = window.top.document.body.offsetHeight-100;;
		var dialogId = "apprInfo";
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("出账合同审批流程已完成，不能再发送审批");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑出账审批信息！");
		}
		
		tabDetailDialog(title, url, width, height, dialogId);
	}
	
	//更新出账合同审批状态
	function updateOutcomeApprFinishFlag(outcomeApprId){
		var url1 = 'tPmOutcomeContractApprController.do?updateFinishFlag';
		var url2 = url1 + '2';
		updateFinishFlag(outcomeApprId, url1, url2);
	}
	
	//录入出账合同
	function addOutcomeAppr(title,url,gname,width,height){
		addFun(title,url,gname,width,height,'mainInfo');
	}
	
	//编辑出账合同
	function updateOutcomeAppr(title,url,gname,width,height){
		var judgeUrl = 'tPmOutcomeContractApprController.do?updateFlag';
		var unEditUrl = url + '&load=detail&tip=true&node=false';
		judgeAndUpdateFun(title, url, gname, width, height, judgeUrl, unEditUrl, 'mainInfo');
	}
	
	//查看出账合同
	function detailOutcomeAppr(title,url,gname,width,height){
		url += '&load=detail&node=false';
		detailFun(title, url, gname, width, height, 'mainInfo');
	}
	
	// 打开价款计算书页面
	function openPriceWin(id, type){
		gridname = 'tPmOutcomeContractApprList';
		var title = "价款计算书";
		var url = 'tPmContractPriceCoverController.do?tPmContractPriceCover'+
				'&contractType='+type+'&contractId='+id;
		var width = "100%";
		var height = "100%";
		
		//判断是否可编辑
		$.ajax({
			type : 'POST',
			url : 'tPmOutcomeContractApprController.do?updateFlag',
			data : {"id":id},
			success : function(data) {
				var d = $.parseJSON(data);
				if (!d.success) {
					//不可编辑
					$("#tipMsg").val(d.msg);
					url += "&read=1&tip=true"
				}
				tabDetailDialog(title, url, width, height);
			}
		});
	}
	

	$(document).ready(function(){
		//设置datagrid的title
		var projectName = window.parent.getParameter();
		$("#tPmOutcomeContractApprList").datagrid({
			title:projectName+'-出账合同审批',
			height:300
		});
	  	
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
	
	//导出WORD
    function ExportWord(title,url,gname,width,height) {
    	gridname = gname;
    	
    	var rowsData = $('#'+gname).datagrid('getSelections');
    	if (!rowsData || rowsData.length==0) {
    		tip('请选择生成合同文本的记录');
    		return;
    	}
    	if (rowsData.length>1) {
    		tip('请选择一条记录再生成合同文本');
    		return;
    	}
    			
    	var id = rowsData[0].id;
    	JeecgExcelExport("tPmOutcomeContractApprController.do?ExportWord&id=" + id,gridname);       
    }
	
</script>