<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="main_codeType_list">
  <div region="center" style="padding:1px;">
  <input id="codeType" type="hidden" value="${codeType}"> 
  <t:datagrid name="tBCodeTypeList" checkbox="true" fitColumns="true" title="基础标准代码类别表" 
  		actionUrl="tBCodeTypeController.do?datagrid&codeType=${codeType}" 
 	 	idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccess" pageSize="25">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="类别代码"  field="code"  query="true"  queryMode="single" isLike="true" width="120"></t:dgCol>
   <t:dgCol title="类别名称"  field="name"  query="true"  queryMode="single" isLike="true"  width="120"></t:dgCol>
   <t:dgCol title="所属代码集"  field="codeType"  hidden="true"   queryMode="group" dictionary="codeType" width="120"></t:dgCol>
   <t:dgCol title="有效标记"  field="validFlag"  hidden="true"   queryMode="group" dictionary="validStatus" width="120"></t:dgCol>
   <t:dgCol title="createBy"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="createName"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="createDate"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="updateBy"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="updateName"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="updateDate"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBCodeTypeController.do?doDel&id={id}" />
   <t:dgFunOpt funname="queryTypeValue(id)" title="查看参数值"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="tBCodeTypeController.do?goAddUpdate&codeType=${codeType}" width="300" height="100" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBCodeTypeController.do?goAddUpdate&codeType=${codeType}" width="300" height="100" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBCodeTypeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="tBCodeTypeController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%> 
  </t:datagrid>
  </div>
  </div>
  <div data-options="region:'east',
	title:'mytitle',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 380px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
    </div>
</div>
 <script src = "webpage/com/kingtake/base/code/tBCodeTypeList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBCodeTypeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBCodeTypeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBCodeTypeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBCodeTypeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBCodeTypeController.do?upload&codeType='+$("#codeType").val(), "tBCodeTypeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBCodeTypeController.do?exportXls&codeType="+$("#codeType").val(),"tBCodeTypeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBCodeTypeController.do?exportXlsByT","tBCodeTypeList");
}
 </script>