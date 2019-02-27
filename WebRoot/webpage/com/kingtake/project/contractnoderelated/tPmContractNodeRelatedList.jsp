<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmContractNodeRelatedList" checkbox="true" fitColumns="false" title="T_PM_CONTRACT_NODE_RELATED" actionUrl="tPmContractNodeRelatedController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同节点表主键"  field="contractNodeId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="指定金额"  field="amount"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="关联进出账节点id"  field="incomePayNodeId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
<%--    <t:dgDelOpt title="删除" url="tPmContractNodeRelatedController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tPmContractNodeRelatedController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tPmContractNodeRelatedController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmContractNodeRelatedController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tPmContractNodeRelatedController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/contractnoderelated/tPmContractNodeRelatedList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmContractNodeRelatedController.do?upload', "tPmContractNodeRelatedList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmContractNodeRelatedController.do?exportXls","tPmContractNodeRelatedList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmContractNodeRelatedController.do?exportXlsByT","tPmContractNodeRelatedList");
}
 </script>