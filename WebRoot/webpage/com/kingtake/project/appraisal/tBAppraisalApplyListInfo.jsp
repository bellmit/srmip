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
<div class="easyui-layout" fit="false" style="width: 100%;height:300px;">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tBAppraisalApplyList" checkbox="false"
			fitColumns="false"
			actionUrl="tBAppraisalApplyController.do?datagridForApply&projectId=${projectId}"
			title="成果鉴定申请列表" idField="id" fit="true" 
			onDblClick="goDetail">
			<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" query="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="项目id" field="projectId" hidden="true" width="120"></t:dgCol>
			<t:dgCol title="成果名称" field="achievementName" query="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="成果完成单位" field="finishUnit" 
				width="120"></t:dgCol>
			<t:dgCol title="工作开始时间" field="beginTime" hidden="true"
				queryMode="single" width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
			<t:dgCol title="工作截止时间" field="endTime" hidden="true"
				queryMode="group" width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
			<t:dgCol title="归档号" field="archiveNum" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="成果完成单位联系人" field="finishContactName"
				 width="80">
			</t:dgCol>
			<t:dgCol title="联系人电话" field="finishContactPhone" hidden="true"
				 width="80" align="right">
			</t:dgCol>
			<t:dgCol title="主持鉴定单位联系人" field="appraisalContactName"
				 width="80"></t:dgCol>
			<t:dgCol title="联系人电话" field="appraisalContactPhone" hidden="true"
				queryMode="group" width="80"></t:dgCol>
			<%-- <t:dgCol title="登记编号" field="registerCode" 
				width="80"></t:dgCol> --%>
			<t:dgCol title="成果类别" field="resultType"  width="80"
				codeDict="1,CGLB"></t:dgCol>
			<t:dgCol title="鉴定形式" field="appraisalForm" 
				width="80" codeDict="1,JDXS"></t:dgCol>
			<t:dgCol title="鉴定时间" field="appraisalTime" 
				width="90" formatter="yyyy-MM-dd"></t:dgCol>
			<t:dgCol title="鉴定地点" field="appraisalAddress" 
				width="100"></t:dgCol>
			<t:dgCol title="鉴定申请状态" field="auditStatus" codeDict="1,CGSCZT"
				 width="100"></t:dgCol>
	        <t:dgCol title="鉴定会状态" field="meetingStatus" extendParams="formatter:statusFormatter,"
				 width="100"></t:dgCol>
			<t:dgCol title="鉴定材料状态" field="materialStatus" extendParams="formatter:statusFormatter,"
				 width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
			<t:dgToolBar title="录入" icon="icon-add"
				url="tBAppraisalApplyController.do?goUpdate&projectId=${projectId }"
				height="100%" width="100%" funname="add"></t:dgToolBar>
			<t:dgFunOpt exp="auditStatus#eq#0" title="编辑"  funname="goUpdate(id)"></t:dgFunOpt>
			<t:dgDelOpt title="删除" exp="auditStatus#eq#0" url="tBAppraisalApplyController.do?doDel&id={id}" />
			<t:dgToolBar title="查看" icon="icon-search"
				url="tBAppraisalApplyController.do?goUpdate" height="100%"
				width="100%" funname="detail"></t:dgToolBar>
		</t:datagrid>
	</div>
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
	
	function goDetail(rowIndex, rowData){
		var url="tBAppraisalApplyController.do?goUpdate&id="+rowData.id+"&load=detail";
		var title = "查看鉴定申请";
		var width = "100%";
		var height = "100%";
		createdetailwindow(title,url,width,height);
	}
</script>