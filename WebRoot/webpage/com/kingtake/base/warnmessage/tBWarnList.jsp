<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBWarnList" checkbox="true" fitColumns="true" title="消息提醒" actionUrl="tBWarnController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒配置业务"  field="warnProperty_businessname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="开始提醒时间"  field="warnBeginTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="结束提醒时间"  field="warnEndTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒时间点"  field="warnTimePoint"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒内容"  field="warnContent"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒频率"  field="warnFrequency"  codeDict="0,WARNFREQUENCY"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBWarnController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBWarnController.do?goAddUpdate" funname="add" height="300" width="720"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBWarnController.do?goAddUpdate" funname="update" height="300" width="720"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBWarnController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBWarnController.do?goAddUpdate" funname="detail" height="300" width="720"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/base/warnMessage/tBWarnList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBWarnListtb").find("input[name='warnBeginTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='warnBeginTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='warnEndTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='warnEndTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='warnTimePoint_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='warnTimePoint_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBWarnListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBWarnController.do?upload', "tBWarnList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBWarnController.do?exportXls","tBWarnList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBWarnController.do?exportXlsByT","tBWarnList");
}
 </script>