<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="tOVehicleViolationList" checkbox="true" fitColumns="true" 
	actionUrl="tOVehicleViolationController.do?datagrid&vehicleId=${vehicleId}" onDblClick="dblClickVehicleViolationDetail"
	idField="id" fit="true" queryMode="group" sortName="violationTime" sortOrder="desc">
		
	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆使用登记表id" field="useId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆信息表id" field="vehicleId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="违章人id" field="violationId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="违章人" field="violationName" query="true" queryMode="single" isLike="true" width="60"></t:dgCol>
	<t:dgCol title="违章时间" field="violationTime" query="true" queryMode="group" width="150" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
	<t:dgCol title="违章地点" field="violationAddr" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="违章代码" field="violationCode" queryMode="group" width="70"></t:dgCol>
	<t:dgCol title="违章描述" field="violationDesc" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="违章分数" field="violationScore" queryMode="group" width="70" align="right"></t:dgCol>
	<t:dgCol title="处罚金额(元)" field="fines" queryMode="group" width="90" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="备注" field="remark" hidden="true" queryMode="group" width="60"></t:dgCol>
	
	<t:dgCol title="操作" field="opt" width="60"></t:dgCol>
	<t:dgDelOpt title="删除" url="tOVehicleViolationController.do?doDel&id={id}" />
	
	<t:dgToolBar title="录入" icon="icon-add" url="tOVehicleViolationController.do?goAdd&vehicleId=${vehicleId}" 
		funname="add" width="420" height="480"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tOVehicleViolationController.do?goUpdate" 
		funname="update" width="420" height="480"></t:dgToolBar>
	<t:dgToolBar title="批量删除" icon="icon-remove" url="tOVehicleViolationController.do?doBatchDel" 
		funname="deleteALLSelect"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="tOVehicleViolationController.do?goUpdate" 
		funname="detail" width="420" height="480"></t:dgToolBar>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	
</t:datagrid>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleViolationList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
//双击查看方法
function dblClickVehicleViolationDetail(rowIndex, rowData){	
	var title = "查看车辆违章信息";
	var url = "tOVehicleViolationController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 420;
	var height = 480;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tOVehicleViolationListtb").find("input[name='violationTime_begin']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleViolationListtb").find("input[name='violationTime_end']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOVehicleViolationController.do?upload', "tOVehicleViolationList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOVehicleViolationController.do?exportXls&vehicleId=${vehicleId}","tOVehicleViolationList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOVehicleViolationController.do?exportXlsByT","tOVehicleViolationList");
}
</script>