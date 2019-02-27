<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<t:datagrid name="tOVehicleUseList" checkbox="false" fitColumns="true" actionUrl="tOVehicleUseController.do?datagrid&vehicleId=${vehicleId}" 
		idField="id" fit="true" queryMode="group" sortName="outTime" sortOrder="desc">
	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="车辆主键" field="tOId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="用车人id" field="useId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="用车人" field="useName" query="true" queryMode="single" width="55"></t:dgCol>
	<t:dgCol title="申请时间" field="applyTime" hidden="true" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="120" align="center"></t:dgCol>
	<t:dgCol title="批准人id" field="approverId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="批准人" field="approverName" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="批准时间" field="approverTime" hidden="true" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="120" align="center"></t:dgCol>
	<t:dgCol title="司机id" field="driverId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="司机姓名" field="driverName" hidden="true" query="false" queryMode="single" width="55"></t:dgCol>
	<t:dgCol title="到达地点" field="acheivePlace" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="行驶里程" field="driverDistance" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="油耗" field="fuelUse" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="出场时间" field="outTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="回场时间" field="backTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="150" align="center"></t:dgCol>
	<t:dgCol title="出车事由" field="outReason" queryMode="group" width="160"></t:dgCol>
	<t:dgCol title="备注" field="remark" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
</t:datagrid>
<script type="text/javascript">
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tOVehicleUseListtb").find("input[name='applyTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='applyTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='approverTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='approverTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='outTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='outTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='backTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("#tOVehicleUseListtb").find("input[name='backTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	}); 

</script>
<script src = "webpage/com/kingtake/office/vehicle/tOVehicleUseList.js"></script>		
