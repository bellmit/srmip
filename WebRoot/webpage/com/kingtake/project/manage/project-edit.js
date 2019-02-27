//选择母项目后回调
function parentProjectSelectCallback(){
	var projectName = iframe.getprojectListSelections('projectName');
	var projectTypeName = iframe.getprojectListSelections('projectType_projectTypeName');
	var feeTypeId = iframe.getprojectListSelections('feeType_id');
	var feeTypeName = iframe.getprojectListSelections('feeType_fundsName');
	$("#feeType").val(feeTypeId);
	$("#feeTypeName").val(feeTypeName);
	$("#parentProjectName").val(projectName+"("+projectTypeName+")");
}