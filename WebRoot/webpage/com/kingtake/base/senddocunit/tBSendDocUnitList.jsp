<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBSendDocUnitList" checkbox="true" fitColumns="true" title="来文单位" actionUrl="tBSendDocUnitController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="单位全称"  field="unitName"   query="true" isLike="true" queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="单位简称"  field="unitShortName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="contact"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系电话"  field="contactPhone"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBSendDocUnitController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBSendDocUnitController.do?goAdd" funname="add" width="500" height="180"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBSendDocUnitController.do?goUpdate" funname="update" width="500" height="180"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBSendDocUnitController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBSendDocUnitController.do?goUpdate" funname="detail" width="500" height="180"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/base/senddocunit/tBSendDocUnitList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBSendDocUnitListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBSendDocUnitListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBSendDocUnitListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBSendDocUnitListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBSendDocUnitController.do?upload', "tBSendDocUnitList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBSendDocUnitController.do?exportXls","tBSendDocUnitList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBSendDocUnitController.do?exportXlsByT","tBSendDocUnitList");
}
 </script>