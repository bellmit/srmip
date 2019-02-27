/**
 * 操作按钮
 */
function formatOPT(value, row, index){
	var add = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='addChild(this)'>添加</a>] ";
	var edit = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='beginEdit(this)'>编辑</a>] ";
	var save = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='saveEdit(this)'>保存</a>] ";
	var cancel = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='cancelEdit(this)'>取消</a>] ";
	var del = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='deleteRow(this)'>删除</a>] ";
	
	var s = "";
	// '0'--不可操作
	if(value == '1'){
		// 可添加
		s = add;
	}else if(value == '2'){
		// 可编辑
		if(row.editing){
			s = save + cancel;
		}else{
			s = edit;
		}
	}else if(value == '3'){
		// 可删除，可编辑
		if(row.editing){
			s = save + cancel + del;
		}else{
			s = edit + del;
		}
	}
	
	return s;
}

/**
 * 操作按钮
 */
function formatOPT2(value, row, index){
	var edit = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='beginEdit(this)'>编辑</a>] ";
	var save = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='saveEdit(this)'>保存</a>] ";
	var cancel = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='cancelEdit(this)'>取消</a>] ";
	var del = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='deleteRow(this)'>删除</a>] ";
	
	var s = "";
	// 可删除，可编辑
	if(row.editing){
		s = save + cancel + del;
	}else{
		s = edit + del;
	}
	return s;
}

function freshRow(index, tableId){
	$("#"+tableId).datagrid('refreshRow', index);
}



/**
 * 提示框
 */
function showMsg(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}

function addRow(a, tableId, url){
	a = $(a);
	var nodeId = a.attr("nodeid");
	var index = $("#"+tableId).datagrid('getRowIndex', nodeId);
	
	// 保存之前处于编辑状态的节点
	saveNode(tableId, url+"doUpdate");
	
	// 添加子节点到数据库
	$.ajax({
		type: "POST",
		url: url+"doAdd",
		data: {
			parentTypeid:nodeId,
			tpId:$("#tpId").val(),
			addChildFlag:'3'
		},
	   	success: function(data){
	   		var json = $.parseJSON(data);
	   		var newNode = $.parseJSON(json.obj);
	   		
	   		var newIndex = index + parseInt(newNode.serialNum.substring(newNode.serialNum.length-2, newNode.serialNum.length));
	   		editId = newNode.id;
	   		
	   		// 添加子节点到页面
			$('#'+tableId).datagrid('insertRow',{
				index: newIndex,
				row: newNode
			});
			
			// 将子节点变为编辑状态
			$('#'+tableId).datagrid('selectRow', newIndex)
					.datagrid('beginEdit', newIndex);
			bindCal(newIndex);
	   	}
	});
}


/**
 * 编辑
 * @param a
 * @param tableId
 * @param url
 */
function edit(a, tableId, url){
	a = $(a);
	var id = a.attr("nodeid");
	var index = $("#"+tableId).datagrid('getRowIndex', id);
	
	editCommon(tableId, url, index, id);
	return index;
}

function editCommon(tableId, url, index, id){
	// 保存之前处于编辑状态的节点
	saveNode(tableId, url);
	
	editId = id;
	$("#"+tableId).datagrid('beginEdit', index).datagrid('selectRow', index);
	var data = $("#"+tableId).datagrid('getSelected');
	if(data.typeId){
		var ed = $("#"+tableId).datagrid('getEditor', {index:index, field:'typeName'});
		if(ed){
			ed.target.attr("disabled", "disabled");
		}
	}
}


/**
 * 保存
 * @param a
 * @param tableId
 * @param url
 */
function save(a, tableId, url){
	a = $(a);
	var nodeId = a.attr("nodeid");
	
	if(editId == nodeId){
		saveNode(tableId, url);
	}
}


/**
 * 取消
 * @param a
 * @param tableId
 */
function cancel(a, tableId){
	a = $(a);
	var nodeId = a.attr("nodeid");
	var index = $("#"+tableId).datagrid('getRowIndex', nodeId);
	
	if(editId = nodeId){
		editId = "";
		$("#"+tableId).datagrid('cancelEdit', index);
	}
}


function deleteNode(a, tableId, url){
	a = $(a);
	var nodeId = a.attr("nodeid");
	var index = $("#"+tableId).datagrid('getRowIndex', nodeId);
	// 选中删除行
	$("#"+tableId).datagrid('selectRow', index);
	
	$.messager.confirm('确认', '您确定删除该条记录吗？', function(r){
		if(r){
			// 后台删除行
			$.ajax({
				type: 'post',
				data: {id:nodeId, priceBudgetId:$("#typeId").val()},
				url: url,
				success:function(data){
					var result = $.parseJSON(data);
					// 判断删除行是否处于编辑状态
					if(editId == nodeId){
						editId = "";
					}
					// 更新父节点
					var oldData = $("#"+tableId).datagrid('getSelected');
					updateParent(oldData, $.parseJSON(result.obj), oldData.parentTypeid);
					// 页面删除行
					$("#"+tableId).datagrid('deleteRow', index);
					showMsg(result.msg);
				}
			});
		}
	});
}



/**
 * 保存节点
 */
function saveNode(tableId, url){
	if(editId){
		// 获得行索引
		var index = $("#"+tableId).datagrid('getRowIndex', editId);
		// 检查备注长度是否超长
		if(!check(index)){
			return;
		}
		// 选中编辑行    将编辑状态取消	获得新的行数据
		var data = $("#"+tableId).datagrid('selectRow', index)
				.datagrid('endEdit', index)
				.datagrid('getSelected');
		data.priceBudgetId = $("#typeId").val();
		if(tableId == "master"){
			$("#"+tableId).datagrid('updateRow', {
				index:index,
				row:{
					// 差价栏 = 报价栏 - 计价栏
					priceDiffColumn:(data.priceColumn ? parseFloat(data.priceColumn) : 0)
						-(data.valuationColumn ? parseFloat(data.valuationColumn) : 0)
				}
			})
		}
		
		// 保存数据到后台
		$.ajax({
			async:false,
			type: 'post',
			url: url,
			data: data,
			success: function(json){
				var result = $.parseJSON(json);
				var oldData = $.parseJSON(result.obj);
				editId = "";
				// 更新父节点
				if(oldData){
					updateParent(oldData, data, oldData.parentTypeid);
				}
			}
		}); 
	}
}


function commonCheck(tableId, index, fieldName, memo, length){
	var ed = $("#"+tableId).datagrid('getEditor', {
		index:index, 
		field:fieldName
	});
	if(ed){
		var name = ed.target.val();
		if(name && name.length > length){
			tip(memo+"长度不能超过"+length+"！");
			return false;
		}
	}
	return true;
}
