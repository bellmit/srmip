<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tErSuggestionList" checkbox="true" fitColumns="false" title="评审意见表" actionUrl="tErSuggestionController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审项目信息表主键"  field="reviewProjectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审专家id"  field="expertId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审专家姓名"  field="expertName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审时间"  field="expertTime" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="专家打分"  field="expertScore"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专家结论"  field="expertResult"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="理由或意见建议"  field="expertContent"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tErSuggestionController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tErSuggestionController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tErSuggestionController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tErSuggestionController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tErSuggestionController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/expert/suggestion/tErSuggestionList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tErSuggestionListtb").find("input[name='expertTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tErSuggestionListtb").find("input[name='expertTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tErSuggestionController.do?upload', "tErSuggestionList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tErSuggestionController.do?exportXls","tErSuggestionList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tErSuggestionController.do?exportXlsByT","tErSuggestionList");
}
 </script>