<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/project/manage/tPmProjectList.js"></script>
<div class="easyui-layout" fit="true" style="height: 400px;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectList"  onDblClick="dbcl"  checkbox="false" fitColumns="false" 
  	actionUrl="tOApprovalProjectSummaryController.do?datagridForApproval&projectIds=${projectIds}" 
  	title="项目基本信息表"  idField="id" fit="true" queryMode="group" height="400">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目编号"  field="projectNo"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目状态"  field="projectStatus"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="projectName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="负责人"  field="projectManager"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目简介"  field="projectAbstract"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起始日期"  field="beginDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="截止日期"  field="endDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="分管部门"  field="manageDepart"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="负责人电话"  field="managerPhone"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人电话"  field="contactPhone"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="计划合同标志"  field="planContractFlag"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目类型"  field="projectType_projectTypeName" codeDict="1,XMLX"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="外来编号"  field="outsideNo"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="经费类型"  field="feeType_fundsName"   codeDict="1,XMJFLX" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="子类型"  field="subType"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="会计编码"  field="accountingCode"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同计划文号"  field="planContractRefNo"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同日期"  field="contractDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="合同计划名称"  field="planContractName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="来源单位"  field="sourceUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目来源"  field="projectSource"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承研部门"  field="devDepart_departname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="责任部门"  field="dutyDepart_departname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="经费单列"  field="feeSingleColumn"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目密级"  field="secretDegree"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否需要鉴定"  field="appraisalFlag"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="总经费"  field="allFee" align="right" queryMode="group"  width="120" extendParams="formatter:formatCurrency,"></t:dgCol>
   <t:dgCol title="所属母项目"  field="parentPmProject_projectName"    queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <%-- <t:dgDelOpt title="作废" url="tPmProjectController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tPmProjectController.do?goAdd" funname="add" width="890" height="500"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tPmProjectController.do?goUpdate" funname="update" width="890" height="500"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmProjectController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectController.do?goUpdate" funname="detail" width="890" height="500"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看立项论证" icon="icon-search" funname="viewApproval"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 
 <script type="text/javascript" src="webpage/common/util.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmProjectListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectListtb").find("input[name='contractDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectListtb").find("input[name='contractDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmProjectController.do?upload', "tPmProjectList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmProjectController.do?exportXls","tPmProjectList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmProjectController.do?exportXlsByT","tPmProjectList");
}

function dbcl(rowIndex,rowData){
	$.dialog.setting.zIndex =2000;
	detail('项目基本信息','tPmProjectController.do?goUpdateForResearchGroup','tPmProjectList','100%','100%');
}
 </script>