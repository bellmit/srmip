<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgbzWzjgxxList" checkbox="true" fitColumns="false" title="物资价格信息" actionUrl="tBJgbzWzjgxxController.do?datagrid"  onDblClick="dblDetail" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="当前期数"  field="dqqs"   query="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="总第期数"  field="zqs"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgbzWzjgxxController.do?doDel&id={id}" />
   <t:dgDelOpt title="查看附件" url="tBJgbzWzjgxxController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgbzWzjgxxController.do?goUpdate" height="200" width="380" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgbzWzjgxxController.do?goUpdate" height="200" width="380" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgbzWzjgxxController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgbzWzjgxxController.do?goUpdate" height="200" width="380" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/tbjgbzwzjgxx/tBJgbzWzjgxxList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgbzWzjgxxListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzWzjgxxListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 580;
	 var height = 510;
	 var url = "tBJgbzWzjgxxController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBJgbzWzjgxxController.do?upload', "tBJgbzWzjgxxList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgbzWzjgxxController.do?exportXls","tBJgbzWzjgxxList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgbzWzjgxxController.do?exportXlsByT","tBJgbzWzjgxxList");
}
 </script>