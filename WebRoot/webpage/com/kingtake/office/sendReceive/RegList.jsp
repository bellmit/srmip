<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOSendReceiveRegList" fitColumns="true" sortName="registerDate" sortOrder="desc" extendParams="border:false,"
  	actionUrl="tOSendReceiveRegController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"></t:dgCol>
   <t:dgCol title="公文编号"  field="mergeFileNum" queryMode="single" sortable="false"  width="120" query="true" isLike="true"></t:dgCol>
   <t:dgCol title="标题"  field="title" query="true" sortable="false" isLike="true" queryMode="single"  width="150"></t:dgCol>
<%--    <t:dgCol title="关键字"  field="keyname"    queryMode="group"  width="80"></t:dgCol> --%>
<%--    <t:dgCol title="机关"  field="office"    queryMode="group"  width="80"></t:dgCol> --%>
   <t:dgCol title="案卷号"  field="filesId" sortable="false" queryMode="group"  width="100"></t:dgCol>
<%--    <t:dgCol title="序号"  field="num"    queryMode="group"  width="60"></t:dgCol> --%>
   <t:dgCol title="份数"  field="count" sortable="false"    queryMode="group"  width="60"></t:dgCol>
<%--    <t:dgCol title="号数"  field="referenceNum"  queryMode="group" width="60"></t:dgCol> --%>
   <t:dgCol title="收发文标志" field="registerType" codeDict="1,SRFLAG" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="收发文日期"  field="registerDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="90" align="center"></t:dgCol>
<%--    <t:dgCol title="收者签名"  field="receiveSign" queryMode="group" width="80"></t:dgCol> --%>
<%--    <t:dgCol title="签名日期"  field="signTime" formatter="yyyy-MM-dd" queryMode="group"  width="80"></t:dgCol> --%>
<%--    <t:dgCol title="关联项目类型"  field="projectType" queryMode="group"  width="80"></t:dgCol> --%>
   <t:dgCol title="密级"  field="securityGrade"   codeDict="0,XMMJ"   queryMode="group"  width="60"></t:dgCol>
<%--    <t:dgCol title="办理情况"  field="condition"  queryMode="group" width="80"></t:dgCol> --%>
<%--    <t:dgCol title="处理结果"  field="result" queryMode="group"  width="60"></t:dgCol> --%>
  <%--  
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOSendReceiveRegController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tOSendReceiveRegController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tOSendReceiveRegController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOSendReceiveRegController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOSendReceiveRegController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
    --%>
  </t:datagrid>
  </div>
 </div>
<script src = "webpage/com/kingtake/office/sendReceive/tOSendReceiveRegList.js"></script>		
<script type="text/javascript">
 $(document).ready(function(){
	//给时间控件加上样式
	$("#tOSendReceiveRegListtb").find("input[name='registerDate_begin']").attr("class","Wdate")
		.attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOSendReceiveRegListtb").find("input[name='registerDate_end']").attr("class","Wdate")
		.attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOSendReceiveRegController.do?upload', "tOSendReceiveRegList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOSendReceiveRegController.do?exportXls","tOSendReceiveRegList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOSendReceiveRegController.do?exportXlsByT","tOSendReceiveRegList");
}
</script>