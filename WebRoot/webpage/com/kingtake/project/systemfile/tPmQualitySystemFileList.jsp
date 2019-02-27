<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmQualitySystemFileList" checkbox="true" fitColumns="true" title="体系文件信息表" 
			actionUrl="tPmQualitySystemFileController.do?datagrid" idField="id" fit="true" 
			queryMode="group" onDblClick="dblDetailtPmQualitySystemFileList">
				
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="名称" field="fileName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="发布时间" field="releaseTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="实施时间" field="executeTime" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="册数" field="volumeNum" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="出版年份" field="publishYear" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="编制人id" field="writingUserid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="编制人姓名" field="writingUsername" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			
			<t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
			<t:dgDelOpt title="删除" url="tPmQualitySystemFileController.do?doDel&id={id}" />
			
			<t:dgToolBar title="录入" icon="icon-add" url="tPmQualitySystemFileController.do?goAdd" 
				funname="add" width="600" height="250"/>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tPmQualitySystemFileController.do?goUpdate" 
				funname="update" width="600" height="250"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove" url="tPmQualitySystemFileController.do?doBatchDel" 
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tPmQualitySystemFileController.do?goUpdate" 
				funname="detail" width="600" height="300"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/project/systemfile/tPmQualitySystemFileList.js"></script>		
<script type="text/javascript">
function dblDetailtPmQualitySystemFileList(rowIndex, rowDate){
	var title = "查看";
	var url = "tPmQualitySystemFileController.do?goUpdate&id="+rowDate.id+"&load=detail";
	var width = 600;
	var height = 300;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tPmQualitySystemFileListtb").find("input[name='releaseTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='releaseTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='executeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='executeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySystemFileListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmQualitySystemFileController.do?upload', "tPmQualitySystemFileList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmQualitySystemFileController.do?exportXls","tPmQualitySystemFileList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmQualitySystemFileController.do?exportXlsByT","tPmQualitySystemFileList");
}
</script>