<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tErReviewTypeList" checkbox="true" fitColumns="true" title="评审内容类型" actionUrl="tErReviewTypeController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="标题"  field="title"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="url地址"  field="url"    queryMode="group"  width="240"></t:dgCol>
   <t:dgCol title="排序码"  field="sortCode"    queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="80"></t:dgCol>
   <t:dgDelOpt title="删除" url="tErReviewTypeController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tErReviewTypeController.do?goAdd" funname="add" height="140" width="400"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tErReviewTypeController.do?goUpdate" funname="update" height="140" width="400"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tErReviewTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tErReviewTypeController.do?goUpdate" funname="detail" height="140" width="400"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/expert/reviewtype/tErReviewTypeList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tErReviewTypeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tErReviewTypeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tErReviewTypeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tErReviewTypeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tErReviewTypeController.do?upload', "tErReviewTypeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tErReviewTypeController.do?exportXls","tErReviewTypeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tErReviewTypeController.do?exportXlsByT","tErReviewTypeList");
}
 </script>