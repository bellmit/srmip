<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOApprovalProjectSummaryList" checkbox="true" fitColumns="false" title="呈批件项目汇总" actionUrl="tOApprovalProjectSummaryController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属项目id"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属项目名称"  field="projectName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目编号"  field="projectNo"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="负责人id"  field="projectManagerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="负责人名称"  field="projectManager"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起始日期"  field="beginDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="截止日期"  field="endDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="项目类型"  field="projectType"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="经费类型"  field="feeType"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承研部门"  field="developerDepart"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分管部门"  field="manageDepart"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目来源"  field="projectSource"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="依据文号"  field="accordingNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="总经费"  field="allFee"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="关联呈批件信息表主键"  field="approvalId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOApprovalProjectSummaryController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tOApprovalProjectSummaryController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tOApprovalProjectSummaryController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOApprovalProjectSummaryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOApprovalProjectSummaryController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/approval/tOApprovalProjectSummaryList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOApprovalProjectSummaryListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalProjectSummaryListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalProjectSummaryListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalProjectSummaryListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOApprovalProjectSummaryController.do?upload', "tOApprovalProjectSummaryList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOApprovalProjectSummaryController.do?exportXls","tOApprovalProjectSummaryList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOApprovalProjectSummaryController.do?exportXlsByT","tOApprovalProjectSummaryList");
}
 </script>