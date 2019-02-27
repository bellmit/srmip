<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="projectId" value="${projectId }" type="hidden"/>
<div class="easyui-layout" fit="false" style="height:300px;width: 1000px;margin: 0 auto;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmAbateList" checkbox="true"  fitColumns="true"  title="减免信息"
  	actionUrl="tPmAbateController.do?datagrid&projectId=${projectId}" 
  	idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目_主键"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="减免经费额度(元)"  field="payFunds"    queryMode="group"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="减免理由"  field="Reason"    queryMode="group"  width="150"></t:dgCol>
     <t:dgCol title="减免具体意见"  field="Suggestion"    queryMode="group"  width="150"></t:dgCol>
     <t:dgCol title="指定分承包减免额"  field="zdfcbjme"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="指定外协减免额"  field="zdwxjme"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="校内协作减免额"  field="xnxzjme"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="大学预留比例%" hidden="true" field="dxylbl"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="大学预留金额" hidden="true" field="dxylje"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="学院预留比例%" hidden="true" field="xyylbl"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="学院预留金额" hidden="true" field="xyylje"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="系预留比例%" hidden="true" field="xylbl"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="系预留金额" hidden="true" field="xylje"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="教研室预留比例%" hidden="true" field="jysylbl"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="教研室预留金额" hidden="true" field="jysylje"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" width="100" queryMode="group" dictionary="bpm_status"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" width="100" hidden="true"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" width="100" hidden="true"></t:dgCol>
      
   <t:dgToolBar title="查看" icon="icon-search" url="tPmAbateController.do?goAddUpdate" 
   	funname="detail" width="720" height="480"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
//双击查看详情
function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = 720;
	var height = 480;
	var url = "tPmAbateController.do?goAddUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmAbateController.do?upload', "tPmAbateList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmAbateController.do?exportXls&projectId="+$("#projectId").val(),
			"tPmAbateList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmAbateController.do?exportXlsByT","tPmAbateList");
}

 </script>