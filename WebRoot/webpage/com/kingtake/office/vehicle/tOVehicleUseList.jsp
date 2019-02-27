<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<t:datagrid name="tOVehicleUseList" checkbox="true" fitColumns="true" 
	actionUrl="tOVehicleUseController.do?datagrid&vehicleId=${vehicleId}" onDblClick="dblClickVehicleUseDetail"
	idField="id" fit="true" queryMode="group" sortName="outTime" sortOrder="desc">

	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆主键" field="tOId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="用车人id" field="useId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="用车人" field="useName" query="true" queryMode="single" isLike="true" width="55"></t:dgCol>
	<t:dgCol title="申请时间" field="applyTime" hidden="true" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="120" align="center"></t:dgCol>
	<t:dgCol title="批准人id" field="approverId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="批准人" field="approverName" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="批准时间" field="approverTime" hidden="true" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="120" align="center"></t:dgCol>
	<t:dgCol title="司机id" field="driverId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="司机" field="driverName" query="false" queryMode="single" width="55"></t:dgCol>
	<t:dgCol title="到达地点" field="acheivePlace" queryMode="group" width="120" ></t:dgCol>
	<t:dgCol title="行驶里程(km)" field="driverDistance" hidden="true" queryMode="group" width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="油耗(L)" field="fuelUse" hidden="true" queryMode="group" width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="出场时间" field="outTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="回场时间" field="backTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="出车事由" field="outReason" hidden="true" queryMode="group" width="150"></t:dgCol>
	<t:dgCol title="备注" field="remark" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
	
	<t:dgCol title="操作" field="opt" width="50"></t:dgCol>
	<t:dgFunOpt funname="goDelete(id)" title="删除"></t:dgFunOpt>
	
	<t:dgToolBar title="录入" icon="icon-add" url="tOVehicleUseController.do?goAdd&vehicleId=${vehicleId}" 
		funname="add" width="620" height="390"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tOVehicleUseController.do?goUpdate" 
		funname="update" width="620" height="390"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="tOVehicleUseController.do?goUpdate" 
		funname="detail" width="620" height="390"></t:dgToolBar>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	
</t:datagrid>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleUseList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
//双击查看方法
function dblClickVehicleUseDetail(rowIndex, rowData){	
	var title = "查看车辆使用信息";
	var url = "tOVehicleUseController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 620;
	var height = 390;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	/* $("#tOVehicleUseListtb").find("input[name='applyTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleUseListtb").find("input[name='applyTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleUseListtb").find("input[name='approverTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleUseListtb").find("input[name='approverTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});}); */
	$("#tOVehicleUseListtb").find("input[name='outTime_begin']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleUseListtb").find("input[name='outTime_end']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	/* $("#tOVehicleUseListtb").find("input[name='backTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleUseListtb").find("input[name='backTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});}); */
}); 
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOVehicleUseController.do?upload', "tOVehicleUseList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOVehicleUseController.do?exportXls&vehicleId=${vehicleId}","tOVehicleUseList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOVehicleUseController.do?exportXlsByT","tOVehicleUseList");
}
</script>
