<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div id="contract_div" class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<input id="income" type="hidden" value="${income}" />
		<t:datagrid name="tPmContractNodeList" checkbox="false" fitColumns="true" title="${title}"
			actionUrl="tPmContractNodeController.do?datagrid&projectId=${projectId}&inOutFlag=${inOutFlag}" 
			idField="id" fit="true" queryMode="group" pagination="false" sortName="inOutContractid"
			onLoadSuccess="selectRow">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<c:if test="${income}">
				 <t:dgCol title="合同名称" field="inOutContractid" dictionary="T_PM_INCOME_CONTRACT_APPR,ID,CONTRACT_NAME" 
				 	queryMode="single" width="120"></t:dgCol>
			</c:if>
			<c:if test="${!income}">
				 <t:dgCol title="合同名称" field="inOutContractid" dictionary="T_PM_OUTCOME_CONTRACT_APPR,ID,CONTRACT_NAME" 
				 	queryMode="single" width="120"></t:dgCol>
			</c:if>
			<t:dgCol title="支付（或来款）节点主键" field="projPayNodeId" hidden="true" queryMode="group" align="right" width="120"></t:dgCol>
			<t:dgCol title="节点名称" field="contractNodeName" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="类型" field="prjType" hidden="true" queryMode="group" width="80" codeDict="1,HTJDLX"></t:dgCol>
			<t:dgCol title="计划/合同标志" field="planContractFlag" hidden="true" queryMode="group" replace="计划_1,合同_2" width="80"></t:dgCol>
			<t:dgCol title="进出帐标志" field="inOutFlag" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="成果形式" field="resultForm" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="评价方法" field="evaluationMethod" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="完成时间" field="completeDate" hidden="false" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="备注" field="remarks" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createName" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			<c:if test="${income}">
				 <t:dgToolBar title="查看合同信息" icon="icon-search" funname="detailContractFun" 
				 	url="tPmIncomeContractApprController.do?goUpdateTab" height="100%" width="750"></t:dgToolBar>
			</c:if>
			<c:if test="${!income}">
				 <t:dgToolBar title="查看合同信息" icon="icon-search" funname="detailContractFun" 
				 	url="tPmOutcomeContractApprController.do?goUpdateTab" height="600" width="750"></t:dgToolBar>
			</c:if>
			<t:dgToolBar title="查看合同节点信息" icon="icon-search" url="tPmContractNodeController.do?goUpdate" funname="detail" width="600" height="380"></t:dgToolBar>
			</t:datagrid>
	</div>
</div>
<div style="width:450px; overflow: hidden;" id="eastPanel"
	data-options = 
		"
			region:'east',
			title:'mytitle',
			collapsed:true,
			split:true,
			border:false,
			onExpand : function(){
			 li_east2 = 1;
			},
			onCollapse : function() {
			 li_east2 = 0;
			}
		">
		<c:if test="${income}">
			 <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="inComeNode"
			></div>
		</c:if>
		<c:if test="${!income}">
			 <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="payNode"
			></div>
		</c:if>
</div>
</div>
<script src = "webpage/com/kingtake/project/contractnode/tPmContractNodeList.js"></script>
<script type="text/javascript">
var li_east2 = 0;
	//查看合同信息
	function detailContractFun(title, url, id, width, height) {
		gridname = id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
				
		url += '&load=detail&node=false&id='+ rowsData[0].inOutContractid;
		createdetailwindow(title,url,width,height);
	}

	//页面加载完成的恢复到上次的状态
	function selectRow(data){
		var rows = data.rows;
		if(rows.length>0){
			var selectRow = $("#selectRow").val();
			if(selectRow){
				$('#tPmContractNodeList').datagrid('selectRow',selectRow);
			}else{
				//第一次加载页面，选中第一行，在来款节点或支付节点加载完成后选中
				$('#tPmContractNodeList').datagrid('selectRow',0);
				var rowData = $('#tPmContractNodeList').datagrid('getSelected');
				if(rowData){
					refreshInOrPayNode(0,rowData);
				}
				return;
			}
		}
	}
	
	//绑定合同节点select事件
	$("#tPmContractNodeList").datagrid({
		onSelect : refreshInOrPayNode
	});
	
	//刷新来款节点或支付节点的选中状态
	function refreshInOrPayNode(rowIndex, rowData){
		var contractNodeId = rowData.id;
		if(rowData){
			var title = "";
			if( $("#income").val() == "true" ){
				title = '指定来款节点';
				var url = 'tPmIncomeNodeController.do?tPmIncomeNodeAndContractNode&projectId=${projectId}&checkFlag=1';
				$('#inComeNode').panel("refresh", url+"&contractNodeId="+contractNodeId);
// 				var url = $('#inComeNode').attr("href");
// 				refreshIncomeNode(rowIndex, rowData);
			}else{
				title = '指定支付节点';
				var url = 'tPmPayNodeController.do?tPmPayNodeAndContractNode&projectId=${projectId}&checkFlag=1';
				$('#payNode').panel("refresh", url+"&contractNodeId="+contractNodeId);
// 				var url = $('#payNode').attr("href");
// 				refreshPayNode(rowIndex, rowData);
			}
			if(li_east2 == 0){
				  $('#contract_div').layout('expand','east');
				 }
				 $('#contract_div').layout('panel','east').panel('setTitle', title);
			$("#selectRow").val(rowIndex);
		}
	}
	
	//刷新来款节点选中状态
	function refreshIncomeNode(selectIndex, selectData){
		$("#selectRow").val(selectIndex);
		var incomeNodeIds = selectData.projPayNodeId;
		
		var rows = $('#tPmIncomeNodeList').datagrid('getData').rows;
		for(var i=0; i<rows.length; i++){
			if(incomeNodeIds.indexOf(rows[i].id) == -1){
				$('#tPmIncomeNodeList').datagrid('unselectRow',i);
			}else{
				$('#tPmIncomeNodeList').datagrid('selectRow',i);
			}
		}
		getSelectCount();
	}
	
	//刷新支付节点选中状态
	function refreshPayNode(selectIndex, selectData){
		$("#selectRow").val(selectIndex);
		var payNodeIds = selectData.projPayNodeId;
		
		var rows = $('#tPmPayNodeList').datagrid('getData').rows;
		for(var i=0; i<rows.length; i++){
			if(payNodeIds.indexOf(rows[i].id) == -1){
				$('#tPmPayNodeList').datagrid('unselectRow',i);
			}else{
				$('#tPmPayNodeList').datagrid('selectRow',i);
			}
		}
		getSelectCount();
	}
	
	/* //根据合同节点刷新来款节点或支付节点
	function refreshInOrPayNode(rowIndex, rowData){
		var url = "";
		var income = $("#income").val();
		if(income == "true"){
			url += "tPmIncomeNodeController.do?tPmIncomeNodeAndContractNode" +
				"&incomeNodeIds=" + rowData.projPayNodeId
		}else{
			url += "tPmPayNodeController.do?tPmPayNodeAndContractNode" +
				"&payNodeIds=" + rowData.projPayNodeId 
		}
	 	url += "&projectId=${projectId}" +
			"&contractId=" + rowData.id +
			"&rowIndex=" + rowIndex ;
		$('#incomeNode').panel("refresh", url);
	}
	
	//合同节点加载完成默认选中第一行合同节点，并刷新第一行合同节点的来款节点
	function selectRow(data){
		var rows = data.rows;
		if(rows.length>0){
			var selectRow = $("#selectRow").val();
			if(selectRow == undefined){
				selectRow = 0;
			}
			$('#tPmContractNodeList').datagrid('selectRow',selectRow);
			
			var url = "";
			var rowData = rows[selectRow];
			var income = $("#income").val();
			if(income == "true"){
				url += "tPmIncomeNodeController.do?tPmIncomeNodeAndContractNode" +
					"&incomeNodeIds=" + rowData.projPayNodeId
			}else{
				url += "tPmPayNodeController.do?tPmPayNodeAndContractNode" +
					"&payNodeIds=" + rowData.projPayNodeId 
			}
		 	url += "&projectId=${projectId}" +
				"&contractId=" + rowData.id +
				"&rowIndex=" + selectRow;
			$('#incomeNode').panel("refresh", url);
		}
	} */
	
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmContractNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmContractNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmContractNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmContractNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmContractNodeListtb").find("input[name='completeDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmContractNodeListtb").find("input[name='completeDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmContractNodeController.do?upload', "tPmContractNodeList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmContractNodeController.do?exportXls","tPmContractNodeList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmContractNodeController.do?exportXlsByT","tPmContractNodeList");
	}
</script>
