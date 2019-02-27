/**
 * 操作按钮
 */
function formatOPT(value, row, index){
	var s = "";
	var id = row.ID;
	var num = row.NUM;
	
	var add = "<a href='javascript:void(0)' nodeid='"+id+"' num='"+num+"'  onclick='addRow(this)'>添加</a> ";
	var edit = "<a href='javascript:void(0)' nodeid='"+id+"' num='"+num+"' onclick='editRow(this)'>编辑</a> ";
	var save = "<a href='javascript:void(0)' onclick='saveNode()'>保存</a> ";
	var cancel = "<a href='javascript:void(0)' nodeid='"+id+"' num='"+num+"' onclick='cancel(this)'>取消</a>";
	var del = "<a href='javascript:void(0)' nodeid='"+id+"' num='"+num+"'  onclick='deleteRow(this)'>删除</a> ";
	
	if(value != '0'){
		s += add;
	}
	//TODO 特殊处理
	var nums = ["10"];
	/*if(value == '1' || $.inArray(row.NUM,nums) >= 0){
		// 可编辑
		if(!row.children || row.children.length == 0){
			if(row.editing){
	 			s += (save + cancel);
	 		}else{
	 			s += edit;
	 		}
		}else if(row.NUM == '10'){
			if(row.editing){
	 			s += (save + cancel);
	 		}else{
	 			s += edit;
	 		}
		}
 	}else */
	if(row.NUM == "10"){
		if(row.editing){
 			s += (save + cancel);
 		}else{
 			s += edit;
 		}
	}else if(value == '3'){
		// 可编辑、可删除
 		if(!row.children || row.children.length == 0){
 			if(row.editing){
 				s += (save + cancel);
 	 		}else{
 	 			s += (edit);
 	 		}
 			
 			var historyMoney = row.HISTORYMONEY ? parseFloat(row.HISTORYMONEY) : 0;
 			if(historyMoney == 0){
 				s += del;
 			}
 		}
 	}
 	
 	return s;
}

function freshRow(id){
	$("#center").treegrid('refresh', id);
}

function formatTotalFundsMoney(value){
	if(value != "--"){
		return formatCurrency(value);
	}
	return value;
}

/**
 * 金额正数：蓝色
 * 金额负数：红色
 */
function formatMoney(value){
   	if(parseFloat(value) < 0){
   		return "<font color='red'>"+formatCurrency(value)+"</font>";
   	}else if(parseFloat(value) > 0){
   		return "<font color='blue'>"+formatCurrency(value)+"</font>";
   	}else{
   		return formatCurrency(value);
   	}
}


function addRow(a){
	// 保存之前处于编辑状态的节点
	if(!saveNode()){
		return;
	}
	
	// 添加子节点到数据库
	var parentId = $(a).attr("nodeid");
	var num = $(a).attr("num");
	$.ajax({
		type: "POST",
		url: httpUrl + "doAdd",
		data: {
			parent : parentId,
			apprId : $("#tpId").val(),
			pid : $("#apprId").val(),
			addChildFlag : '3',
			num:num
		},
	   	success: function(data){
	   		var json = $.parseJSON(data);
	   		var newData = $.parseJSON(json.obj);
	   		
	   		// 添加子节点到页面
			$('#center').treegrid('append',{
				parent : parentId,
				data : [{
					ID : newData.id,
					NUM : newData.num,
					ADDCHILDFLAG : newData.addChildFlag,
					PARENT : parentId
				}]
			});
			
			// 将当前节点变为编辑状态
			$("#center").treegrid('select', newData.id).treegrid('beginEdit', newData.id);
			editId = newData.id;
		   	//bindCal(newData.id);
		   	
		   	// 刷新父节点
		   	freshRow(parentId);
	   	}
	});
}

function editRow(a){
	if(!saveNode()){
		return;
	}
	
	var id = $(a).attr("nodeid");
	$("#center").treegrid('beginEdit', id);
	editId = id;
   	//bindCal(id);	
}

function dbClickRow(row){
	if(row.ID != editId && (row.ADDCHILDFLAG == "2" || row.ADDCHILDFLAG == "3") 
			&& (!row.children || row.children.length == 0)){
		if(!saveNode()){
			return;
		}
		
		$("#center").treegrid('beginEdit', row.ID);
		editId = row.ID;
	   	//bindCal(row.ID);	
	}
}

function deleteRow(a){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
 	    if (r){  
 	    	var id = $(a).attr("nodeid");
 	    	var data = $("#center").treegrid('find', id);
 	    	var parent = $("#center").treegrid('getParent', id);//父类数据
 	    	parent.historyMoney -= data.variableMoney;
 	    	parent.variableMoney += data.variableMoney;
 	    	var oldMoney = data.money ? parseFloat(data.money) : 0;
	    	deleteNode(id, oldMoney);
 	    }    
 	});
}

function deleteNode(id, oldMoney){
 	var newMoney = 0;
 	
	// 后台删除节点
	$.ajax({
		type : "POST",
		url : httpUrl + "doDel",
		data : "id="+id,
	   	success : function(msg){
	   		if(oldMoney != newMoney){
	   			updateParents(id, oldMoney, newMoney);
	   		}
	   		
	    	var parent = $("#center").treegrid('getParent', id);
	  		// 前台删除节点
	    	$('#center').treegrid('remove', id);
	    	// 刷新父节点
	   		freshRow(parent.ID);
	    	
	    	if(id == editId){
	    		editId = "";
	    	}
	   	}
	});
}

function cancel(){
	$('#center').treegrid('cancelEdit', editId);
	var data = $("#center").treegrid('find', editId);
	var oldMoney = data.money ? parseFloat(data.money) : 0;
	if(!data.CONTENT){
		deleteNode(editId, oldMoney);
	}
	editId = "";
}

/**
 * 保存节点
 * @param url
 */
function saveNode(){
	if(editId){
		//当前节点
		var data = $("#center").treegrid('find', editId);	//节点数据
		var dataMoney = data.money ? parseFloat(data.money) : 0;//总金额
		var dataHistoryMoney = data.historyMoney ? parseFloat(data.historyMoney) : 0;//已编制金额
		var dataVariableMoney = data.variableMoney ? parseFloat(data.variableMoney) : 0;	//金额
		//父节点
		var parent = $("#center").treegrid('getParent', editId);//父类数据
		var parenMoney =  parent.money ? parseFloat(parent.money) : 0;//总金额
		var parentHistoryMoney = parent.historyMoney ? parseFloat(parent.historyMoney) : 0;
		var parentVariableMoney = parent.variableMoney ? parseFloat(parent.variableMoney) : 0;
		//根节点
		var root = $("#center").treegrid('getRoot');
		
		var content = $('#center').treegrid('getEditor', {index:editId, field:'CONTENT'}).target.val();//预算内容
		if(!content){
			showMsg('请填写预算内容！');
			return false;
		}
		var money = parseFloat($('#center').treegrid('getEditor', {index:editId, field:'variableMoney'}).target.val() || 0);//预算金额
		
		
		var balance = money - dataVariableMoney;
		var parentNewHistoryMoney = parentHistoryMoney + balance;
		var parentNewHariableMoney = parentVariableMoney - balance;
		//判断数据合理性
		
		if((parenMoney >= 0 && money > (parenMoney - parentHistoryMoney)) || money > totalBuget){
			showMsg('当前编辑金额已经大于本次可编辑金额！');
	 		return false;
		}else if(totalBuget > 0 && money < 0){
			showMsg('非调整金额，单项金额不能为负数！');
	 		return false;
		}
		totalBuget = totalBuget - balance;
		
		
		//计算子节点
		/*if(!data.money && parseFloat(data.ADDCHILDFLAG) >= 3 ){
			data.money = money;
		}*/
		data.content = content;
		data.variableMoney = money;
		//间接费子项计算
		if(data.NUM == "10"){
			for(var i = 0 ; i < data.children.length ; i++){
				var children = data.children[i];
				var childData = $("#center").treegrid('find', children.ID);	//节点数据
				var childMoney = 0;
				if(children.NUM == "1001"){
					childMoney = money * parseFloat($("#dxbl").val()) / 100;
				}else if(children.NUM == "1002"){
					childMoney = money * parseFloat($("#zrdwbl").val()) / 100;
				}else if(children.NUM == "1003"){
					childMoney = money * parseFloat($("#cydwbl").val()) / 100;
				}else if(children.NUM == "1004"){
					childMoney = money * parseFloat($("#xmzbl").val()) / 100;
				}
				$('#center').treegrid('update', {
					id : children.ID,
					row : {
						variableMoney: childMoney
					}
				});
				doUpdateNode(childData);
			}
			// 保存前台数据
			$("#center").treegrid('endEdit', data.ID);
			$('#center').treegrid('update', {
				id : data.ID,
				row : {
					variableMoney : 0
				}
			});
		}else{
			var rootVariableMoney = root.variableMoney - money;
			root.variableMoney = rootVariableMoney;
			// 保存前台数据
			$("#center").treegrid('endEdit', data.ID);
			$('#center').treegrid('update', {
				id : root.ID,
				row : {
					variableMoney : rootVariableMoney
				}
			});
			//更新跟节点
		 	doUpdateNode(root);
			//保存节点
		 	doUpdateNode(data);
		}
	}
	return true;
}

//更新节点
function doUpdateNode(data){
	var param = getSaveData(data);
	$.ajax({
		async:false,
		type: "POST",
		url: httpUrl + "doUpdate",
		data: param,
	   	success: function(result){
	   		
	   	}
	});
	editId = "";
}

/**
 * 更新
 * @param id:当前节点ID
 * @param oldMoney
 * @param newMoney
 */
function updateParents(id, oldMoney, newMoney) {
 	var parent = $('#center').treegrid('getParent', id);
 	if(parent){
 		var money = parent.MONEY ? parseFloat(parent.MONEY) : 0;
 		$('#center').treegrid('update', {
 			id : parent.ID,
 			row : {
 				MONEY : money - oldMoney + newMoney
 			}
 		});
 		updateParents(parent.ID, oldMoney, newMoney);
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

function loadTips(){
	$.ajax({
		url : 'tPmProjectFundsApprController.do?getFundsLimit',
		type : 'post',
		data : {id : $("#apprId").val()},
		success : function(result){
			result = $.parseJSON(result);
			var limits = $.parseJSON(result.obj.limits);
			var tb = $("#tb");
			var index = 0;
			
			var changeFlag = $("#changeFlag").val();
			var sum = 0;
			if(changeFlag == "1"){
				sum++;
			}
			if(limits && limits.length > 0){
				sum += limits.length;
			}
			
			if(changeFlag == '1'){
				var p = $("<p style='color:red;'></p>");
				if(sum > 1){
					p.html("提示"+(++index)+"：预算调整的合计应为0");
				}else{
					p.html("提示：预算调整的合计应为0")
				}
				tb.append(p);
			}
			
			var funds = parseFloat(result.obj.funds);
			if(limits && limits.length > 0){
				for(var i in limits){
					var p = $("<p id='"+limits[i].ID+"' style='color:red;'></p>");
					
					var money = funds > 1000000 ? 
							formatCurrency(funds * parseFloat(limits[i].UP) * 0.01) : 
								formatCurrency(funds * parseFloat(limits[i].DOWN) * 0.01);
					if(sum > 1){
						p.html("提示"+(++index)+"："+limits[i].NAME + "不能超过：" +money);
					}else{
						p.html("提示："+limits[i].NAME + "不能超过：" +money);
					}
					
					tb.append(p);
				}
			}
		}
	});
}

function submitData(){
	var sumMoney = $("#center").treegrid('getRoot').money;//根节点合计数 
	if(totalBuget != 0){
		showMsg('当前编制预算合计不等于本次编制预算金额！');
		return false;
	}
	
	$.ajax({
        url : 'tPmProjectFundsApprController.do?submitYearFunds',
        data : {"apprId":$("#apprId").val()},
        type : 'post',
        dataType : 'json',
        success : function(data) {
            if(data.success==true){
           		tip(data.msg);
           		frameElement.api.config.parent.iframe.contentWindow.tPmProjectFundsApprListsearch();
           		frameElement.api.config.parent.iframe.contentWindow.tPmProjectYearFundsApprListsearch();
           		frameElement.api.close();
            }else{
            	tip(data.msg);
            }
        }
    });
	
}



