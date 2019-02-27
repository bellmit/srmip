<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tErReviewProjectList" checkbox="true" fitColumns="false" title="评审项目信息表" actionUrl="tErReviewProjectController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专家评_主键"  field="tEId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属项目"  field="projectId"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="projectName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属类型"  field="reviewType"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属阶段"  field="projectStage"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审结果"  field="reviewResult"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审分数"  field="reviewScore"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评审意见"  field="reviewSuggestion"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="结果录入时间"  field="resultInputDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="录入人id"  field="resultInputUserid"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="录入人姓名"  field="resultInputUsername"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tErReviewProjectController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tErReviewProjectController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tErReviewProjectController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tErReviewProjectController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tErReviewProjectController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/expert/reviewproject/tErReviewProjectList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tErReviewProjectListtb").find("input[name='resultInputDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tErReviewProjectListtb").find("input[name='resultInputDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tErReviewProjectController.do?upload', "tErReviewProjectList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tErReviewProjectController.do?exportXls","tErReviewProjectList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tErReviewProjectController.do?exportXlsByT","tErReviewProjectList");
}
 </script>