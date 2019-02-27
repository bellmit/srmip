<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="projectId" value="${projectId }" type="hidden"/>
<div class="easyui-layout" fit="false" style="height:300px;width: 1000px;margin: 0 auto;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmPayfirstList" title="垫资信息" checkbox="true" fitColumns="true"  
  	actionUrl="tPmPayfirstController.do?datagrid&projectId=${projectId}" 
  	idField="id" fit="false" queryMode="group" onDblClick="dblDetail">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目_主键"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="垫支经费额度(元)"  field="payFunds"  queryMode="group"  width="150" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="垫支理由"  field="reason"    queryMode="group"  width="250"></t:dgCol>
     <t:dgCol title="垫支来源"  field="paySource"  replace="大学_1,责任单位_2,承研单位_3"  queryMode="group"  width="250"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" width="100" queryMode="group" dictionary="bpm_status"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true"></t:dgCol>
      
   <t:dgToolBar title="查看" icon="icon-search" url="tPmPayfirstController.do?goAddUpdate" 
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
	var url = "tPmPayfirstController.do?goAddUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmPayfirstController.do?upload', "tPmPayfirstList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmPayfirstController.do?exportXls&projectId="+$("#projectId").val(),
			"tPmPayfirstList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmPayfirstController.do?exportXlsByT","tPmPayfirstList");
}

 </script>