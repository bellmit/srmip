<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOTravelReportList" checkbox="true" fitColumns="false" title="差旅-出差报告信息表" actionUrl="tOTravelReportController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="差旅主键"  field="tOId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="出差事由"  field="travelReason"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="参加人员"  field="relateUsername"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起始时间"  field="startTime" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="截止时间"  field="endTime" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="地点"  field="address"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="校首长阅批"  field="chiefApproval"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="部领导阅批"  field="departApproval"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处领导阅批"  field="sectionApproval"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="传阅"  field="circelRead"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="报告提交日期"  field="submitTime" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOTravelReportController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tOTravelReportController.do?goAdd" funname="add" width="670"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tOTravelReportController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOTravelReportController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOTravelReportController.do?goUpdate" funname="addUpdateDetail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/travel/tOTravelReportList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOTravelReportListtb").find("input[name='startTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOTravelReportListtb").find("input[name='startTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOTravelReportListtb").find("input[name='endTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOTravelReportListtb").find("input[name='endTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOTravelReportListtb").find("input[name='submitTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOTravelReportListtb").find("input[name='submitTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOTravelReportController.do?upload', "tOTravelReportList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOTravelReportController.do?exportXls","tOTravelReportList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOTravelReportController.do?exportXlsByT","tOTravelReportList");
}
 </script>