<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmFundsReceiveLogsList" checkbox="false" fitColumns="false" 
			actionUrl="tPmFundsReceiveLogsController.do?datagrid&projectFundsAppr.id=${fundApprId}" 
			idField="id" fit="true" queryMode="group"
			sortName="fundsLog.operateDate" sortOrder="desc" pagination="false">
			<t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
			<t:dgCol title="审批类型"  field="suggestionType"  codeDict="1,YSSPLX"  queryMode="group"  width="120"></t:dgCol>
			<t:dgCol title="发起人"  field="fundsLog.operateUsername"   queryMode="group"  width="50"></t:dgCol>
			<t:dgCol title="发起时间"  field="fundsLog.operateDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="150" align="center"></t:dgCol>
			<t:dgCol title="审批人"  field="receiveUsername"    queryMode="group"  width="50"></t:dgCol>
			<t:dgCol title="审批人部门"  field="receiveDepartname"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
			<t:dgCol title="处理状态"  field="operateStatus"  replace="待审批_0,已处理_1"  queryMode="group"  width="50"></t:dgCol>
			<t:dgCol title="处理时间"  field="operateTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="150" align="center"></t:dgCol>
			<t:dgCol title="审批意见"  field="suggestionCode"  codeDict="1,SPYJ"  queryMode="group"  width="50"></t:dgCol>
			<t:dgCol title="意见内容"  field="suggestionContent"    queryMode="group"  width="120"></t:dgCol>
			<t:dgCol title="有效标志"  field="validFlag"  hidden="true"  queryMode="group"  width="50"></t:dgCol>
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/project/fundsuggest/tPmFundsReceiveLogsList.js"></script>		
<script type="text/javascript">
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmFundsReceiveLogsListtb").find("input[name='operateTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmFundsReceiveLogsListtb").find("input[name='operateTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmFundsReceiveLogsController.do?upload', "tPmFundsReceiveLogsList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmFundsReceiveLogsController.do?exportXls","tPmFundsReceiveLogsList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmFundsReceiveLogsController.do?exportXlsByT","tPmFundsReceiveLogsList");
	}
</script>