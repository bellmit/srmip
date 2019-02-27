<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="tOVehicleExpenseList" checkbox="true" fitColumns="true" 
	actionUrl="tOVehicleExpenseController.do?datagrid&vehicleId=${vehicleId}" onDblClick="dblClickVehicleExpenseDetail"
	idField="id" fit="true" queryMode="group" sortName="payTime" sortOrder="desc">
	
	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆主键" field="vehicleId" hidden="true" queryMode="group" width="70"></t:dgCol>
	<t:dgCol title="车辆使用登记表id" field="useId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="缴费人员id" field="payerId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="缴费人员" field="payerName" query="true" queryMode="single" isLike="true" width="70"></t:dgCol>
	<t:dgCol title="缴费时间" field="payTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="费用类型" field="expenseType" queryMode="group" codeDict="1,FYLX" width="70"></t:dgCol>
	<t:dgCol title="加油量(L)" field="fuelCharge" hidden="true" queryMode="group" width="70" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="缴费类型" field="payType" queryMode="group" codeDict="1,JYJFLX" width="70"></t:dgCol>
	<t:dgCol title="金额(元)" field="money" queryMode="group" width="70" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="备注" field="remark" hidden="true" queryMode="group" width="120"></t:dgCol>
	
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt title="删除" url="tOVehicleExpenseController.do?doDel&id={id}" />
	
	<t:dgToolBar title="录入" icon="icon-add" url="tOVehicleExpenseController.do?goAdd&vehicleId=${vehicleId}" 
		funname="add" width="460" height="400"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="tOVehicleExpenseController.do?goUpdate" 
		funname="update" width="460" height="400"></t:dgToolBar>
	<t:dgToolBar title="批量删除" icon="icon-remove" url="tOVehicleExpenseController.do?doBatchDel" 
		funname="deleteALLSelect"></t:dgToolBar>
	<t:dgToolBar title="查看" icon="icon-search" url="tOVehicleExpenseController.do?goUpdate" 
		funname="detail" width="460" height="400"></t:dgToolBar>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	
</t:datagrid>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleExpenseList.js"></script>		
<script type="text/javascript">
//双击查看方法
function dblClickVehicleExpenseDetail(rowIndex, rowData){	
	var title = "查看车辆费用信息";
	var url = "tOVehicleExpenseController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 460;
	var height = 400;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tOVehicleExpenseListtb").find("input[name='payTime_begin']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("#tOVehicleExpenseListtb").find("input[name='payTime_end']")
		.attr("class","Wdate")
		.attr("style","height:20px;width:160px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOVehicleExpenseController.do?upload', "tOVehicleExpenseList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOVehicleExpenseController.do?exportXls&vehicleId=${vehicleId}","tOVehicleExpenseList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOVehicleExpenseController.do?exportXlsByT","tOVehicleExpenseList");
}
</script>