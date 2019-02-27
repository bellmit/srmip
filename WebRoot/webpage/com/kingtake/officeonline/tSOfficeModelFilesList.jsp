<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tSOfficeModelFilesList" checkbox="false" fitColumns="true" title="office模板文件" actionUrl="tSOfficeModelFilesController.do?datagrid" idField="id" fit="true" queryMode="group"  pageSize="20">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="主键"  field="officeId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="公文类型"  field="name"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="公文类型编码"  field="code"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="文件名"  field="attachmenttitle" hidden="true" queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="扩展名"  field="extend" hidden="true"   queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="存储路径"  field="realpath"   queryMode="group"  width="220"></t:dgCol> --%>
<%--    <t:dgCol title="业务编码"  field="businesskey"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="80"></t:dgCol> --%>
<%--    <t:dgCol title="创建人姓名"  field="createName"    queryMode="group"  width="80"></t:dgCol> --%>
<%--    <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
<%--    <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="goAdd(id,code)" title="添加模板"></t:dgFunOpt>
   <t:dgFunOpt funname="goUpdate(id,code)" title="修改模板"></t:dgFunOpt>
<%--    <t:dgDelOpt title="删除" url="tSOfficeModelFilesController.do?doDel&id={id}" /> --%>
<%--    <t:dgFunOpt funname="addOrEditTemplate(id,officeId,realpath)" title="模板管理"></t:dgFunOpt> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tSOfficeModelFilesController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tSOfficeModelFilesController.do?goUpdate" funname="update" width="100%" height="100%"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tSOfficeModelFilesController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tSOfficeModelFilesController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/officeonline/tSOfficeModelFilesList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tSOfficeModelFilesListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tSOfficeModelFilesListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tSOfficeModelFilesListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tSOfficeModelFilesListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function addOrEditTemplate(id,officeId,realpath){
	 if(realpath){
		 goUpdate(id,officeId,realpath);
	 }else{
		 goAdd(id);
	 }
 }
 
 function goAdd(id,code){
	 $.dialog({
			content: 'url:tSOfficeModelFilesController.do?goAdd&codeDetailId='+id+'&code='+code,
			lock : true,
			width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
			title:"模板管理",
			opacity : 0.3,
			cache:false,
			okVal:'确定',
			ok:function(){
				iframe = this.iframe.contentWindow;
				saveObj();
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtSOfficeModelFilesList();
		    }
		});
 }
 
 function goUpdate(id,code){
	 $.dialog({
			content: 'url:tSOfficeModelFilesController.do?goUpdate&codeDetailId='+id+'&code='+code,
			lock : true,
			width : window.top.document.body.offsetWidth,
    		height : window.top.document.body.offsetHeight-100,
            left : '0%',
            top : '0%',
			title:"模板管理",
			opacity : 0.3,
			cache:false,
			okVal:'确定',
			ok:function(){
				iframe = this.iframe.contentWindow;
				saveObj();
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtSOfficeModelFilesList();
		    }
		});
 }
 
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tSOfficeModelFilesController.do?upload', "tSOfficeModelFilesList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tSOfficeModelFilesController.do?exportXls","tSOfficeModelFilesList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tSOfficeModelFilesController.do?exportXlsByT","tSOfficeModelFilesList");
}
 </script>