<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgFgList" checkbox="true" fitColumns="false" title="价格法规库" actionUrl="tBJgFgController.do?datagrid" 
  idField="id" fit="true" queryMode="group" onDblClick="dblDetail" sortName="createDate" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="法规名称"  field="fgmc"   query="true" isLike="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="文号"  field="wh"   query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="发布单位"  field="fbdw"   query="true" isLike="true" queryMode="single"  width="250"></t:dgCol>
   <t:dgCol title="发布时间"  field="fbsj" query="true" align="center" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="摘要"  field="zy"    queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="施行时间"  field="sxsj" query="true" align="center" formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="beiz"    queryMode="single"  width="420"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgFgController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgFgController.do?goUpdate" height="600" width="580" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgFgController.do?goUpdate" height="600" width="580" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgFgController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgFgController.do?goUpdate" height="600" width="580" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/tbjgfg/tBJgFgList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgFgListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgFgListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 580;
	 var height = 510;
	 var url = "tBJgFgController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBJgFgController.do?upload', "tBJgFgList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgFgController.do?exportXls","tBJgFgList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgFgController.do?exportXlsByT","tBJgFgList");
}
 </script>