<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tZDljgxxList" checkbox="true" fitColumns="false" title="代理机构信息" actionUrl="tZDljgxxController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="机构名称"  field="jgmc"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="代号"  field="dh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="lxr"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系电话"  field="lxdh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tZDljgxxController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tZDljgxxController.do?goUpdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tZDljgxxController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tZDljgxxController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tZDljgxxController.do?goUpdate" funname="detail"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/zscq/dljgxx/tZDljgxxList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tZDljgxxController.do?upload', "tZDljgxxList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tZDljgxxController.do?exportXls","tZDljgxxList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tZDljgxxController.do?exportXlsByT","tZDljgxxList");
}
 </script>