<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBWarnPropertiesList" checkbox="true" fitColumns="true" title="提醒配置" actionUrl="tBWarnPropertiesController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒业务名称"  field="businessname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="配置sql"  field="sqlstr"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒跳转url"  field="url"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBWarnPropertiesController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBWarnPropertiesController.do?goAddUpdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBWarnPropertiesController.do?goAddUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBWarnPropertiesController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBWarnPropertiesController.do?goAddUpdate" funname="detail"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/warnMessage/tBWarnPropertiesList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBWarnPropertiesController.do?upload', "tBWarnPropertiesList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBWarnPropertiesController.do?exportXls","tBWarnPropertiesList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBWarnPropertiesController.do?exportXlsByT","tBWarnPropertiesList");
}
 </script>