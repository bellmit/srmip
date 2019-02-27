<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBPmReportReqList" checkbox="true" fitColumns="false" title="申报需求信息表" actionUrl="tBPmReportReqController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="关联项目id"  field="projectId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="序号"  field="reqNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="projectName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="立项需求及研究总体要求"  field="researchReq"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起始时间"  field="beginDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="结束时间"  field="endDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="申报单位"  field="reportUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="责任单位"  field="manageUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="经费需求"  field="feeReq"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="申请人"  field="applicantor"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="流程处理状态"  field="bpmStatus"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBPmReportReqController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBPmReportReqController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBPmReportReqController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBPmReportReqController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBPmReportReqController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/reportreq/tBPmReportReqList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBPmReportReqListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBPmReportReqListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBPmReportReqController.do?upload', "tBPmReportReqList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBPmReportReqController.do?exportXls","tBPmReportReqList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBPmReportReqController.do?exportXlsByT","tBPmReportReqList");
}
 </script>