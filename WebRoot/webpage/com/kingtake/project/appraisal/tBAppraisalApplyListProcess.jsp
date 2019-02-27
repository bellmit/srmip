<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function statusFormatter(value,row,index){
	if(value==""){
		return "未录入";
	}else if(value=="0"){
		return "未提交";
	}else if(value=="1"){
		return "已提交";
	}else if(value=="2"){
		return "通过";
	}else if(value=="3"){
		return "不通过";
	}
}
</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tBAppraisalApplyList" checkbox="false"
			fitColumns="false" 
			actionUrl="tBAppraisalApplyController.do?datagridForApply&projectId=${projectId}"
			title="成果鉴定申请列表" idField="id" fit="true" queryMode="group"
			onDblClick="goDetail">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" query="true" isLike="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="项目id" field="projectId" hidden="true" width="120"></t:dgCol>
			<t:dgCol title="成果名称" field="achievementName" query="true" isLike="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="成果完成单位" field="finishUnit" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="工作开始时间" field="beginTime" hidden="true"
				 width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
			<t:dgCol title="工作截止时间" field="endTime" hidden="true"
				 width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
			<t:dgCol title="归档号" field="archiveNum" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="成果完成单位联系人" field="finishContactName"  width="80">
			</t:dgCol>
			<t:dgCol title="联系人电话" field="finishContactPhone" hidden="true"
				 width="80" align="right">
			</t:dgCol>
			<t:dgCol title="主持鉴定单位联系人" field="appraisalContactName"
				width="80"></t:dgCol>
			<t:dgCol title="联系人电话" field="appraisalContactPhone" hidden="true"
				width="80"></t:dgCol>
			<%-- <t:dgCol title="登记编号" field="registerCode" 
				width="80"></t:dgCol> --%>
			<t:dgCol title="成果类别" field="resultType"  width="80"
				codeDict="1,CGLB"></t:dgCol>
			<t:dgCol title="鉴定形式" field="appraisalForm" 
				width="80" codeDict="1,JDXS"></t:dgCol>
			<t:dgCol title="鉴定时间" field="appraisalTime" 
				width="90" formatter="yyyy-MM-dd"></t:dgCol>
			<t:dgCol title="鉴定地点" field="appraisalAddress" queryMode="group"
				width="100"></t:dgCol>
			<t:dgCol title="鉴定申请状态" field="auditStatus" codeDict="1,SPZT"
				width="100"></t:dgCol>
			<t:dgCol title="鉴定会状态" field="meetingStatus" extendParams="formatter:statusFormatter,"
				 width="100"></t:dgCol>
			<t:dgCol title="鉴定材料状态" field="materialStatus" extendParams="formatter:statusFormatter,"
				 width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
			<t:dgToolBar title="录入鉴定申请" icon="icon-add"
				url="tBAppraisalApplyController.do?goUpdate&projectId=${projectId }"
				height="100%" width="100%" funname="add"></t:dgToolBar>
			<t:dgToolBar title="鉴定会" icon="icon-edit"
				url="tBAppraisalMeetingController.do?goUpdate"
				height="100%" width="100%" funname="goMeeting"></t:dgToolBar>
			<t:dgToolBar title="鉴定材料登记及归档" icon="icon-edit"
				url="tBAppraisalReportMaterialController.do?goUpdate"
				height="100%" width="100%" funname="goMaterial"></t:dgToolBar>
			<t:dgFunOpt exp="auditStatus#eq#0" title="编辑"  funname="goUpdate(id)"></t:dgFunOpt>
			<t:dgFunOpt exp="auditStatus#eq#3" title="编辑"  funname="goUpdate(id)"></t:dgFunOpt>
			<t:dgDelOpt title="删除" exp="auditStatus#eq#0" url="tBAppraisalApplyController.do?doDel&id={id}" />
			<t:dgFunOpt title="发送审核" funname="sendAppraisalAppr(id, auditStatus)" ></t:dgFunOpt>
			<t:dgFunOpt  title="导出"  funname="goExport(id)"></t:dgFunOpt>
			<t:dgToolBar title="查看" icon="icon-search"
				url="tBAppraisalApplyController.do?goUpdate" height="100%"
				width="100%" funname="detail"></t:dgToolBar>
		</t:datagrid>
	</div>
	<input id="tipMsg" type="hidden" value=""/>
</div>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				//给时间控件加上样式
				$("#tPmIncomeContractApprListtb").find(
						"input[name='startTime_begin']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='startTime_end']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='endTime_begin']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='endTime_end']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='createDate_begin']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='createDate_end']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='updateDate_begin']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("#tPmIncomeContractApprListtb").find(
						"input[name='updateDate_end']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
			});

	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmIncomeContractApprController.do?upload',
				"tPmIncomeContractApprList");
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("tPmIncomeContractApprController.do?exportXls",
				"tPmIncomeContractApprList");
	}

	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmIncomeContractApprController.do?exportXlsByT",
				"tPmIncomeContractApprList");
	}
	
	function goUpdate(id){
		var url="tBAppraisalApplyController.do?goUpdate&id="+id;
		var title = "编辑鉴定申请";
		var width = "100%";
		var height = "100%";
		createwindow(title,url,width,height);
	}
	
	//导出word
	function goExport(id){
		var url="tBAppraisalApplyController.do?exportWord&id="+id;
	    window.open(url);
	}
	
	function goDetail(rowIndex, rowData){
		var url="tBAppraisalApplyController.do?goUpdate&id="+rowData.id+"&load=detail";
		var title = "查看鉴定申请";
		var width = "100%";
		var height = "100%";
		createdetailwindow(title,url,width,height);
	}
	
	//发送进账合同审核
	function sendAppraisalAppr(apprId, finishFlag){
		var title = "审核";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
				'&apprId=' + apprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY%>';
		var width = 900;
		var height = window.top.document.body.offsetHeight-100;
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("鉴定申请审核流程已完成，不能再发送审核");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑鉴定申请信息！");
		}
		sendApprDialog(title, url, width, height, apprId,finishFlag,'<%=ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY%>' );
	}
	
	//打开鉴定会窗口
	function goMeeting(title,url,tableid,width,height){
		var rowsData = $('#'+tableid).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一行');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData[0].auditStatus!=2) {
			tip('鉴定申请审核通过才能录入鉴定会信息！');
			return;
		}
		url = url+"&applyId="+rowsData[0].id;
		if(width=="100%"){
			width = window.top.document.body.offsetWidth;
		}
		if(height=="100%"){
			height =window.top.document.body.offsetHeight-100;
		}
		W.$.dialog({
			id:'meetingDialog',
			content: 'url:'+url,
			lock : true,
			width:width,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
	
	//打开上报材料窗口
	function goMaterial(title,url,tableid,width,height){
		var rowsData = $('#'+tableid).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一行');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录');
			return;
		}
		if (rowsData[0].auditStatus!=2) {
			tip('鉴定申请审核通过才能录入上报材料信息！');
			return;
		}
		url = url+"&applyId="+rowsData[0].id;
		if(width=="100%"){
			width = window.top.document.body.offsetWidth;
		}
		if(height=="100%"){
			height =window.top.document.body.offsetHeight-100;
		}
		W.$.dialog({
			id:'materiaDialog',
			content: 'url:'+url,
			lock : true,
			width:width,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
</script>