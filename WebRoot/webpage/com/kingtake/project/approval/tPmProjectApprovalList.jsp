<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectApprovalList" checkbox="true" fitColumns="false" title="立项论证" actionUrl="tPmProjectApprovalController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属项目"  field="projectName"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="建议等级"  field="suggestGrade"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="建议单位"  field="suggestUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="研究时限"  field="studyTime"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目来源"  field="projectSrc"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="研究的必要性"  field="studyNecessity"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="国内外军内外有关情况分析"  field="situationAnalysis"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要研究内容"  field="studyContent"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="研究进度与成果形式"  field="scheduleAndAchieve"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPmProjectApprovalController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tPmProjectApprovalController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPmProjectApprovalController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmProjectApprovalController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectApprovalController.do?goUpdate" funname="detail"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/approval/tPmProjectApprovalList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmProjectApprovalListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectApprovalListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectApprovalListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectApprovalListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmProjectApprovalController.do?upload', "tPmProjectApprovalList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmProjectApprovalController.do?exportXls","tPmProjectApprovalList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmProjectApprovalController.do?exportXlsByT","tPmProjectApprovalList");
}
 </script>