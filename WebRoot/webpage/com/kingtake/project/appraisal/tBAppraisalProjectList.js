/**
 * 格式化起止时间
 */
function formatStartEndDate(value, row){
	return value.substring(0, 10) + "-" + row.projectEndDate.substring(0, 10);
}

/**
 * 格式化联系人
 */
function formatManager(value, row){
	var phone = row.projectManagerPhone ? "联系电话："+row.projectManagerPhone : "";
	if(row && phone){
		phone = "<br/>" +phone;
	}
	return value + phone;
}

/**
 * 格式化任务来源（文件号）
 */
function formatSource(value, row){
	var num = row.projectAccordingNum ? "文件号："+row.projectAccordingNum : "";
	if(row && num){
		num = "<br/>" +num;
	}
	return value + num;
}


/**
 * 格式化鉴定联系单位、联系人、联系方式
 */
function formatAppraisal(value, row){
	var contract = row.appraisalContact ? "联系人："+row.appraisalContact : "";
	var phone = row.appraisalContactPhone ? "联系电话："+row.appraisalContactPhone : "";
	
	if(value){
		if(contract){
			contract = "</br>" + contract;
		}
		if(phone){
			phone = "<br/>" + phone;
		}
	}else if(!value){
		if(contract && phone){
			phone = "<br/>" + phone;
		}
	}
	return value + contract + phone;
}


function showMsg(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}