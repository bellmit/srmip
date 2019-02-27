<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmSidecatalogList" checkbox="true" fitColumns="false" title="项目模块配置表" actionUrl="tPmSidecatalogController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="moduleType" query="true" queryMode="single" replace="基本信息_1,过程管理_2"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="等级"  field="level"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="业务代码"  field="businessCode"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="页面url"  field="url"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="序号"  field="index"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPmSidecatalogController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tPmSidecatalogController.do?goAddUpdate" funname="add" width="850" height="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPmSidecatalogController.do?goAddUpdate" funname="update" width="850" height="100%"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmSidecatalogController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmSidecatalogController.do?goAddUpdate" funname="detail" width="850" height="100%"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/base/sideccatalog/tPmSidecatalogList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmSidecatalogController.do?upload', "tPmSidecatalogList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmSidecatalogController.do?exportXls","tPmSidecatalogList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmSidecatalogController.do?exportXlsByT","tPmSidecatalogList");
}
 </script>