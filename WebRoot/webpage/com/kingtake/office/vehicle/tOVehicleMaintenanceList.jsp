<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="tOVehicleMaintenanceList" checkbox="true" fitColumns="true" 
	actionUrl="tOVehicleMaintenanceController.do?datagrid&vehicleId=${vehicleId}" onDblClick="dblClickVehicleMaintenanceDetail"
	idField="id" fit="true" queryMode="group" sortName="maintenanceTime" sortOrder="desc">
	
	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆主键" field="vehicleId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="类型" field="opType" queryMode="group" width="40" codeDict="1,WXBYLX"></t:dgCol>
	<t:dgCol title="维护人id" field="maintainUserId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="维护人" field="maintainUserName" query="true" queryMode="single" isLike="true" width="70"></t:dgCol>
	<t:dgCol title="维护时间" field="maintenanceTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="费用(元)" field="maintainUserPay" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="维护事项" field="maintenanceItems" queryMode="group" width="200"></t:dgCol>
	
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt title="删除" url="tOVehicleMaintenanceController.do?doDel&id={id}" />
	
	<t:dgToolBar title="录入" icon="icon-add" url="tOVehicleMaintenanceController.do?goAdd&vehicleId=${vehicleId}" 
		funname="add" width="420" height="370"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tOVehicleMaintenanceController.do?goUpdate" 
		funname="update" width="420" height="370"></t:dgToolBar>
	<t:dgToolBar title="批量删除" icon="icon-remove" url="tOVehicleMaintenanceController.do?doBatchDel" 
		funname="deleteALLSelect"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="tOVehicleMaintenanceController.do?goUpdate" 
		funname="detail" width="420" height="370"></t:dgToolBar>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	
</t:datagrid>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleMaintenanceList.js"></script>		
<script type="text/javascript">
//双击查看方法
function dblClickVehicleMaintenanceDetail(rowIndex, rowData){	
	var title = "查看车辆维护信息";
	var url = "tOVehicleMaintenanceController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 420;
	var height = 370;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tOVehicleMaintenanceListtb").find("input[name='maintenanceTime_begin']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleMaintenanceListtb").find("input[name='maintenanceTime_end']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOVehicleMaintenanceController.do?upload', "tOVehicleMaintenanceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOVehicleMaintenanceController.do?exportXls&vehicleId=${vehicleId}","tOVehicleMaintenanceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOVehicleMaintenanceController.do?exportXlsByT","tOVehicleMaintenanceList");
}
</script>