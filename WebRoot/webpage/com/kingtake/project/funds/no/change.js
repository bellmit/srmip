/**
 * 操作按钮
 */
function formatOPT(value,row){
	var add = "[<a href='#' nodeid='"+row.ID+"' treeid='"+row.TREEID+"' onclick='addRow(this)' >添加</a>]";
	var edit = "[<a href='#' nodeid='"+row.ID+"' treeid='"+row.TREEID+"' onclick='editRow(this)' >编辑</a>]";
	var save = "[<a href='#' nodeid='"+row.ID+"' treeid='"+row.TREEID+"' onclick='saveRow(this)' >保存</a>]";
	var cancel = "[<a href='#' nodeid='"+row.ID+"' treeid='"+row.TREEID+"' onclick='cancel(this)' >取消</a>]";
	var del = "[<a href='#' nodeid='"+row.ID+"' treeid='"+row.TREEID+"' onclick='deleteRow(this)' >删除</a>]";
	
	var s = "";
	if(value == '1'){
		// 添加节点
		s = add;
	}else if(value == '2'){
		// 可编辑节点
		if(row.editing){
			s = save + cancel;
		}else{
			s = edit;
		}
	}else if(value == '3'){
		// 可编辑可删除节点
		if(row.editing){
			s = save + cancel + del;
		}else{
			s = edit + del;
		}
	}
	return s;
}

function freshRow(row){
	$("#"+row.TREEID).treegrid('refresh', row.ID);
}


/**
 * 调整过的金额变为红色
 */
function formatMoney(value){
   	if(parseFloat(value) != 0){
   		return "<font color='red'>"+formatCurrency(value)+"</font>";
   	}else{
   		return formatCurrency(value);
   	}
}


/**
 * 计算可调整金额和调整金额
 */
function countMoney(row, data){
	var variableMoney = 0;
	var money = 0;
	for(var i in data){
		variableMoney += parseFloat(data[i].VARIABLE_MONEY);
		money += parseFloat(data[i].MONEY);
	}
	variableMoney = parseFloat($("#variableSum").attr("real")) + variableMoney;
	money = parseFloat($("#sum").attr("real")) + money;
	$("#variableSum").attr("real", variableMoney).val(formatCurrency(variableMoney));
	$("#sum").attr("real", money).val(formatCurrency(money));
}

function addRow(a){
	// 获得当前节点的id
	var parentId = $(a).attr("nodeid");
	var treeId = $(a).attr("treeid");
	
	var flag = true;
	// 当前没有处于编辑状态的行：直接添加一行
	// 当前有处于编辑状态的行：先将其他处于编辑状态的行保存，再添加一行
	if(editId){
		if(saveUpdate()){
			// 成功保存处于编辑状态的行
			$("#"+editTreeId).treegrid('endEdit', editId);
		}else{
			// 未成功保存处于编辑状态的行
			flag = false;
		}
	}
	
	if(flag){
		add(parentId, treeId);
	}
}


function add(parentId, treeId){
	var url = "tPmContractFundsController.do?doAdd";
	var data = {
		parent:parentId,
   		money:0,
   		apprId:$("#apprId").val(),
   		addChildFlag:'3',
   		variableMoney:0
   	};
	if(treeId == "one"){
		url = "tPmPlanFundsOneController.do?doAdd";
		data.equipmentName = "新预算";
	}else{
		data.content = "新预算";
	}
	
	$.ajax({
		type: "POST",
		url: url,
		data: data,
	   	success: function(result){
	   		var obj = $.parseJSON($.parseJSON(result).obj);
	   		console.info(obj);
	   		var newData = {
	   			ID: obj.id,
	   			MONEY: obj.money,
	   			ADD_CHILD_FLAG : obj.addChildFlag,
	   			VARIABLE_MONEY : obj.variableMoney,
	   			NUM: obj.num,
	   			TREEID: treeId
	   		};
	   		if(treeId == "one"){
	   			newData.EQUIPMENT_NAME = obj.equipmentName;
	   		}else{
	   			newData.CONTENT = obj.content;
	   		}
	   		
	   		// 添加子节点到页面
			$('#'+treeId).treegrid('append',{
				parent: parentId,
				data: [newData]
			});
			// 将子节点变为选中状态, 并编辑
			$("#"+treeId).treegrid('select', obj.id).treegrid('beginEdit', obj.id);
			editId = obj.id;
			editTreeId = treeId;
	   	}
	});
}

/**
 * 双击行：编辑
 */
function dbClickRow(row, treeId){
	if(row.ADD_CHILD_FLAG == '2' || row.ADD_CHILD_FLAG == '3'){
		edit(row.ID, treeId);
	}
}


/**
 * 点击编辑节点链接事件
 */
function editRow(a){
	// 获得当前节点的id
	var id = $(a).attr("nodeid");
	var treeId = $(a).attr("treeid");
	
	edit(id, treeId);
}


function saveRow(a){
	// 获得当前节点的id
	var id = $(a).attr("nodeid");
	var treeId = $(a).attr("treeid");
	
	if(saveUpdate()){
		$("#"+treeId).treegrid('endEdit', id);
		editId = "";
		editTreeId = "";
	}
}


/**
 * 取消编辑
 */
function cancel(a){
	// 获得当前节点的id
	var id = $(a).attr("nodeid");
	var treeId = $(a).attr("treeid");
	
	// 判断当前行是否处于编辑状态
	if(editId == id){
		$('#'+treeId).treegrid('cancelEdit', id);
		editId = "";
		editTreeId = "";
	}
}

/**
 * 删除记录
 * @param a
 */
function deleteRow(a){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){
			// 获得当前节点的id
			var id = $(a).attr("nodeid");
			var treeId = $(a).attr("treeid");
			
			var url = "tPmContractFundsController.do?doDel";
			if(treeId == "one"){
				url = "tPmPlanFundsOneController.do?doDel";
			}
			
			// 获得保存之前的数据
	    	var data = $('#'+treeId).treegrid('find', id);
	    	var oldMoney = parseFloat(data.MONEY);
	    	var oldVariableMoney = parseFloat(data.VARIABLE_MONEY);
	    	// 删除之后的数据
	    	var newMoney = 0;
	    	var newVariableMoney = 0;
			
			$.ajax({
				type: "POST",
				url: url,
				data: {id:id},
			   	success: function(result){
			   		updateParent(treeId, id, oldMoney, newMoney, oldVariableMoney, newVariableMoney);
   			  		// 前台删除节点
   			    	$('#'+treeId).treegrid('remove', id);
   			    	showMsg($.parseJSON(result).msg);
   			    	editId = "";
   			    	editTreeId = "";
			   	}
			});
	    }
	});
}


/**
 * 保存所有
 */
function saveAll(){
	// 判断当前是否有处于编辑状态的节点
	if(editId){
		if(saveUpdate()){
			$("#"+editTreeId).treegrid('endEdit', editId);
			editId = "";
			editTreeId = "";
		}else{
			return;
		}
	}
	
	if(check()){
		// 获得传递到后台的数据
		getData();
		var data = {};
		for(var index in changeData){
			data[index] = JSON.stringify(changeData[index]);
		}
		
		$.ajax({
			type : 'post',
			url : 'tPmContractFundsController.do?batSaveChange',
			data : data,
			dataType: "json",
			traditional : true,
			success : function(data){
				frameElement.api.opener.showMsg(data.msg);
			    frameElement.api.close();
			}
		});
	};
}



/**
 * 编辑
 * @param id
 * @param treeId
 */
function edit(id, treeId){
	// 当前行处于编辑状态：什么都不做
	// 当前行没有处于编辑状态：先将其他处于编辑状态的行保存，再将当前行变为编辑状态
	if(editId != id){
		if(editId){
			if(saveUpdate()){
				$("#"+editTreeId).treegrid('endEdit', editId);
				// 将当前行变为编辑状态
				$("#"+treeId).treegrid('beginEdit', id);
				editId = id;
				editTreeId = treeId;
			}
		}else{
			// 将当前行变为编辑状态
			$("#"+treeId).treegrid('beginEdit', id);
			editId = id;
			editTreeId = treeId;
		}
	}
}



/**
 * 保存当前正在编辑的行
 */
function saveUpdate(){
	var moneyEditor = $("#"+editTreeId).treegrid('getEditor', {
			index:editId, field:'MONEY'
		});
	
	var data = $("#"+editTreeId).treegrid('find', editId);
	// 可以调整的金额
	var variableMoney = parseFloat(data.VARIABLE_MONEY);
	// 修改之前的数据
	var oldMoney = parseFloat(data.MONEY);
	// 修改之后的数据
	var newMoney = parseFloat(moneyEditor.target.val());	
	
	if(newMoney < 0 && Math.abs(newMoney) > variableMoney){
		$("#"+editTreeId).treegrid('select', editId);
		showMsg('减少金额大于可调整金额');
		return false;
	}else if(newMoney > 0 && newMoney > parseFloat($("#variableSum").attr("real"))){
		$("#"+editTreeId).treegrid('select', editId);
		showMsg('增加金额大于可调整总金额');
		return false;
	}
	
	if(oldMoney != newMoney){
		updateParent(editTreeId, editId, oldMoney, newMoney, 0, 0);
	}
	return true;
}


/**
 * 子节点的调整金额变化，更新父节点
 */
function updateParent(treeId, id, oldMoney, newMoney, oldVariableMoney, newVariableMoney){
	var parent = $('#'+treeId).treegrid('getParent', id);
	if(parent){
		$('#'+treeId).treegrid('update', {
			id : parent.ID,
			row : {
				MONEY : parseFloat(parent.MONEY) - oldMoney + newMoney,
				VARIABLE_MONEY : parseFloat(parent.VARIABLE_MONEY) - oldVariableMoney + newVariableMoney
			}
		});
		updateParent(treeId, parent.ID, oldMoney, newMoney, oldVariableMoney, newVariableMoney);
	}else{
		var money = parseFloat($("#sum").attr("real")) + newMoney - oldMoney;
		var variableMoney = parseFloat($("#variableSum").attr("real")) + newVariableMoney - oldVariableMoney;
		$("#sum").attr("real", money).val(formatCurrency(money));
		$("#variableSum").attr("real", variableMoney).val(formatCurrency(variableMoney));
	}
}



/**
 * 验证数据的正确性
 */
function check(){
	var sum = $("#sum").attr("real");
	if(parseFloat(sum) == 0){
		return true;
	}
	
	showMsg('已调整的总金额不为0');
	return false;
}


function getData1(data){
	for(var i in data){
		changeData.plan.push({
			id : data[i].ID, 
			apprId : data[i].T_P_ID,
			num : data[i].NUM,
			equipmentId : data[i].EQUIPMENT_ID,
			equipmentName : data[i].EQUIPMENT_NAME,
			price : data[i].PRICE,
			amount : data[i].AMOUNT,
			unit : data[i].UNIT,
			model : data[i].MODEL,
			money : data[i].MONEY, 
			parent : data[i].PARENT,
			addChildFlag : data[i].ADD_CHILD_FLAG,
			variableMoney : data[i].VARIABLE_MONEY
		});
		if(data[i].children){
			getData1(data[i].children);
		}
	}
}


function getData2(data){
	for(var i in data){
		changeData.contract.push({
			id : data[i].ID, 
			apprId : data[i].T_P_ID,
			num : data[i].NUM,
			contentId : data[i].CONTENT_ID,
			content : data[i].CONTENT,
			money : data[i].MONEY, 
			remark : data[i].REMARK,
			parent : data[i].PARENT,
			addChildFlag : data[i].ADD_CHILD_FLAG,
			variableMoney : data[i].VARIABLE_MONEY
		});
		if(data[i].children){
			getData2(data[i].children);
		}
	}
}

function showMsg(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}