<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBFundsBudgetRelaList" checkbox="true" fitColumns="true" 
  	actionUrl="tBFundsBudgetRelaController.do?datagrid&projectType=${projectType}&fundsPropertyCode=${fundId}" 
  	idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="经费类型"  field="fundsPropertyCode"    queryMode="group" 
   	dictionary="T_B_FUNDS_PROPERTY,ID,FUNDS_NAME" width="60"></t:dgCol>
   <t:dgCol title="预算类型"  field="approvalBudgetRelaId"    queryMode="group" 
   	dictionary="T_B_APPROVAL_BUDGET_RELA,ID,BUDGET_NAE" width="100"></t:dgCol>
   <t:dgCol title="100万以上比例值"  field="millionUp"    queryMode="group"  width="120" 
   	extendParams="formatter:changeNum," align="right"></t:dgCol>
   <t:dgCol title="100万及以下比例值"  field="millionDown"    queryMode="group"  width="120" 
   	extendParams="formatter:changeNum," align="right"></t:dgCol>
   <t:dgCol title="备注"  field="memo"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBFundsBudgetRelaController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" 
   	url="tBFundsBudgetRelaController.do?goAddOrUpdate&projectType=${projectType}&fundsPropertyCode=${fundId}" 
   	funname="add" width="370" height="230"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBFundsBudgetRelaController.do?goAddOrUpdate&projectType=${projectType}" 
   	funname="update" width="370" height="230"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBFundsBudgetRelaController.do?doBatchDel" 
   	funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBFundsBudgetRelaController.do?goAddOrUpdate&projectType=${projectType}" 
   	funname="detail" width="370" height="230"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/base/quota/tBFundsBudgetRelaList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
 function changeNum(value,row,index){
	 return value+"%";
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBFundsBudgetRelaController.do?upload', "tBFundsBudgetRelaList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBFundsBudgetRelaController.do?exportXls","tBFundsBudgetRelaList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBFundsBudgetRelaController.do?exportXlsByT","tBFundsBudgetRelaList");
}
 </script>