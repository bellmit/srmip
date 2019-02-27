<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="tOVehicleAccidentList" checkbox="true" fitColumns="true" 
	actionUrl="tOVehicleAccidentController.do?datagrid&vehicleId=${vehicleId}" onDblClick="dblClickVehicleAccidentDetail"
	idField="id" fit="true" queryMode="group" sortName="occurTime" sortOrder="desc">
	
	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆主键" field="vehicleId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆使用登记表id" field="useId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="事故人id" field="personId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="事故人" field="personName" query="true" queryMode="single" isLike="true" width="80"></t:dgCol>
	<t:dgCol title="事故时间" field="occurTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="事故发生地点" field="occurAddress" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="处理方式" field="handleMode" queryMode="group" codeDict="1,CLFS" width="70"></t:dgCol>
	<t:dgCol title="交警处理结果" field="result" queryMode="group" width="200"></t:dgCol>
	<t:dgCol title="备注" field="remark" hidden="true" queryMode="group" width="120"></t:dgCol>
	
	<t:dgCol title="操作" field="opt" width="60"></t:dgCol>
	<t:dgDelOpt title="删除" url="tOVehicleAccidentController.do?doDel&id={id}" />
	
	<t:dgToolBar title="录入" icon="icon-add" url="tOVehicleAccidentController.do?goAdd&vehicleId=${vehicleId}" 
		funname="add" width="410" height="390"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tOVehicleAccidentController.do?goUpdate" 
		funname="update" width="410" height="390"></t:dgToolBar>
	<t:dgToolBar title="批量删除" icon="icon-remove" url="tOVehicleAccidentController.do?doBatchDel" 
		funname="deleteALLSelect"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="tOVehicleAccidentController.do?goUpdate" 
		funname="detail" width="410" height="390"></t:dgToolBar>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	
</t:datagrid>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleAccidentList.js"></script>		
<script type="text/javascript">
//双击查看方法
function dblClickVehicleAccidentDetail(rowIndex, rowData){
	var title = "查看车辆信息";
	var url = "tOVehicleAccidentController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 410;
	var height = 390;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tOVehicleAccidentListtb").find("input[name='occurTime_begin']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleAccidentListtb").find("input[name='occurTime_end']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOVehicleAccidentController.do?upload', "tOVehicleAccidentList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOVehicleAccidentController.do?exportXls&vehicleId=${vehicleId}","tOVehicleAccidentList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOVehicleAccidentController.do?exportXlsByT","tOVehicleAccidentList");
}
</script>