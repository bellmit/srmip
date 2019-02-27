var li_east = 0;

/**
 * 查看车辆使用信息
 */
function queryVehicleUse(id){
	var title = "车辆使用信息";
	var url = "tOVehicleUseController.do?tOVehicleUse&vehicleId=" + id;
	showVehicleOtherInfoPanel(title,url);
}

/**
 * 查看车辆维护信息
 */
function queryVehicleMaintenance(id){
	var title = "车辆维护信息";
	var url = "tOVehicleMaintenanceController.do?tOVehicleMaintenance&vehicleId=" + id;
	showVehicleOtherInfoPanel(title,url);
}

/**
 * 查看车辆费用信息
 */
function queryVehicleExpense(id){
	var title = "车辆使用费用信息";
	var url = "tOVehicleExpenseController.do?tOVehicleExpense&vehicleId=" + id;
	showVehicleOtherInfoPanel(title,url);
}

/**
 * 查看车辆违章信息
 */
function queryVehicleViolation(id){
	var title = "车辆违章信息";
	var url = "tOVehicleViolationController.do?tOVehicleViolation&vehicleId=" + id;
	showVehicleOtherInfoPanel(title,url);
}

/**
 * 查看车辆事故信息
 */
function queryVehicleAccident(id){
	var title = "车辆事故信息";
	var url = "tOVehicleAccidentController.do?tOVehicleAccident&vehicleId=" + id;;
	showVehicleOtherInfoPanel(title,url);
}

/**
 * 显示左边面板的方法
 */
function showVehicleOtherInfoPanel(title,url){
    if(li_east == 0){
        $('#tOVehicleListContent').layout('expand','east');
    }
    $('#tOVehicleListContent').layout('panel','east').panel('setTitle', title);
    	/*$('#mainInfo').parent().panel('resize',{width:1000})*/
    $('#vehicleOtherInfo').panel("refresh", url);
}

/**
 * 列表加载完成
 */
function loadSuccess() {
    $('#tOVehicleListContent').layout('panel','east').panel('setTitle', "");
    $('#tOVehicleListContent').layout('collapse','east');
    $('#vehicleOtherInfo').empty();
}