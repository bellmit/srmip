<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker" ></t:base>
<div class="easyui-layout" fit="true" id="tOVehicleListContent">
	<div id="mainInfo" region="center" style="padding:1px;">
		<t:datagrid name="tOVehicleList" checkbox="false" fitColumns="false" title="车辆基本信息" 
			actionUrl="tOVehicleController.do?datagrid" onDblClick="dblClickVehicleDetail" onLoadSuccess="loadSuccess"
			idField="id" fit="true" queryMode="group" sortName="purchaseTime" sortOrder="desc">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="车牌号" field="licenseNo" query="true" queryMode="single" isLike="true" frozenColumn="true" width="70"></t:dgCol>
			<t:dgCol title="司机id" field="driverId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="司机姓名" field="driverName" query="false" queryMode="single" width="65"></t:dgCol>
			<t:dgCol title="车辆状态" field="vehicleState" query="false" queryMode="single" codeDict="1,CLZT" width="65"></t:dgCol>
			<t:dgCol title="车辆类型" field="type" query="false" queryMode="single" codeDict="1,CLLX" width="65"></t:dgCol>
			<t:dgCol title="军车标志" field="militaryFlag" queryMode="group" codeDict="0,SFBZ" width="65"></t:dgCol>
			<t:dgCol title="加油卡卡号" field="refuelNo" queryMode="group" width="75"></t:dgCol>
			<t:dgCol title="余额(元)" field="refuelBalance" queryMode="group" width="70" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="所属处室id" field="sectionId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="所属处室" field="sectionName" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
			<t:dgCol title="购置时间" field="purchaseTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="购保标志" field="insuranceFlag" hidden="true" queryMode="group" codeDict="1,GBBZ" width="120"></t:dgCol>
			<t:dgCol title="购保时间" field="insuranceTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="年检标志" field="annualSurveyFlag" hidden="true" queryMode="group" codeDict="1,NJBZ" width="120"></t:dgCol>
			<t:dgCol title="年检时间" field="annualSurveyTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="备注" field="remark" hidden="true" queryMode="group" width="120"></t:dgCol>
			
			<t:dgCol title="操作" field="opt" width="240"></t:dgCol>
			<t:dgDelOpt title="删除" url="tOVehicleController.do?doDel&id={id}" />
			<t:dgFunOpt funname="queryVehicleUse(id)" title="使用"></t:dgFunOpt>
			<t:dgFunOpt funname="queryVehicleMaintenance(id)" title="维护"></t:dgFunOpt>
			<t:dgFunOpt funname="queryVehicleExpense(id)" title="费用"></t:dgFunOpt>
			<t:dgFunOpt funname="queryVehicleViolation(id)" title="违章"></t:dgFunOpt>
			<t:dgFunOpt funname="queryVehicleAccident(id)" title="事故"></t:dgFunOpt>
			
			<t:dgToolBar title="录入" icon="icon-add" url="tOVehicleController.do?goAdd" 
				funname="add" width="630" height="330"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tOVehicleController.do?goUpdate" 
				funname="update" width="630" height="330"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tOVehicleController.do?goUpdate" 
				funname="detail" width="630" height="330"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			
		</t:datagrid>
		
		</div>
	</div>
	<!--车辆其他信息（使用、维修、费用、违章、事故） -->
	<div 
		data-options=
		 	"
		 		region:'east',
		 		title:'mytitle',
				collapsed:true,
				split:true,
				border:false,
				onExpand : function(){
					li_east = 1;
				},
				onCollapse : function() {
				 li_east = 0;
				}
			"
		style="width:750px; overflow:hidden;" id="eastPanel">
		<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="vehicleOtherInfo"></div>
	</div>
</div>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
//双击查看方法
function dblClickVehicleDetail(rowIndex, rowData){
	var title = "查看车辆信息";
	var url = "tOVehicleController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 630;
	var height = 330;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tOVehicleListtb").find("input[name='purchaseTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOVehicleListtb").find("input[name='purchaseTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOVehicleListtb").find("input[name='insuranceTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOVehicleListtb").find("input[name='insuranceTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOVehicleListtb").find("input[name='annualSurveyTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOVehicleListtb").find("input[name='annualSurveyTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOVehicleController.do?upload', "tOVehicleList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOVehicleController.do?exportXls","tOVehicleList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOVehicleController.do?exportXlsByT","tOVehicleList");
}
 </script>