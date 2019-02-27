<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmFundsLogsList" checkbox="true" fitColumns="false" title="预算审批流转记录表" actionUrl="tPmFundsLogsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="预算审批表主键"  field="fundsApprId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作人id"  field="operateUserid"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作人姓名"  field="operateUsername"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="operateDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作人部门id"  field="operateDepartid"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作人部门名称"  field="operateDepartname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作人ip地址"  field="operateIp"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送意见"  field="senderTip"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPmFundsLogsController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tPmFundsLogsController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPmFundsLogsController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmFundsLogsController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmFundsLogsController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/fundsuggest/tPmFundsLogsList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmFundsLogsListtb").find("input[name='operateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmFundsLogsListtb").find("input[name='operateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmFundsLogsController.do?upload', "tPmFundsLogsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmFundsLogsController.do?exportXls","tPmFundsLogsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmFundsLogsController.do?exportXlsByT","tPmFundsLogsList");
}
 </script>