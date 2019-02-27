<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOPrivateMemoList" checkbox="true" sortName="createDate" sortOrder="desc" 
  	fitColumns="true" title="个人备忘录" actionUrl="tOPrivateMemoController.do?datagrid" 
  	idField="id" fit="true" queryMode="group" pageSize="100" pageList="[100,200,300]"
  	onDblClick="dblDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"></t:dgCol>
   <t:dgCol title="备忘内容"  field="memoContent" query="true" isLike="true"   queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="关联文号"  field="accordingNum"  query="true"  queryMode="single" isLike="true" width="150"></t:dgCol>
   <t:dgCol title="关联项目"  field="projectName"  query="true"  queryMode="single" isLike="true" width="150"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate"  formatter="yyyy-MM-dd hh:mm:ss" query="true" 
   	queryMode="group" align="center" width="120"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOPrivateMemoController.do?doDel&id={id}" />
   <t:dgToolBar  title="录入" icon="icon-add" url="tOPrivateMemoController.do?goAdd" 
   	funname="add" width="660" height="350"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tOPrivateMemoController.do?goUpdate" 
   	funname="update" width="660" height="350"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOPrivateMemoController.do?doBatchDel" 
   	funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOPrivateMemoController.do?goUpdate" 
   	funname="detail"  width="660" height="350"></t:dgToolBar>
   	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <script type="text/javascript">
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 660;
	 var height = 350;
	 var url = "tOPrivateMemoController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);
 }
 
//导出
 function ExportXls() {
 	JeecgExcelExport("tOPrivateMemoController.do?exportXls","tOPrivateMemoList");
 }
 
 $(document).ready(function(){
    $("#tOPrivateMemoListtb").find("input[name='projectName']").attr("style","width:150px;");
   	//给时间控件加上样式
 	$("#tOPrivateMemoListtb").find("input[name='createDate_begin']").attr("class","Wdate")
 		.attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 	$("#tOPrivateMemoListtb").find("input[name='createDate_end']").attr("class","Wdate")
 		.attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 </script>