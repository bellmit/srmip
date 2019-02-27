<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>技术服务类价款计算书：设计费</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>

<style>
	td{
		height:30px; line-height: 30px;
	}
	.panel-header, .panel-body{
		border-color:#ccc;
	}
	.hand{
		cursor: pointer;
	}
	input{
		height:30px; line-height:30px;
	}
</style>
 
<body>
<div class="easyui-layout" fit="true">
	<div region="north" style="height:100px;" border="false">
	 	<input id="tpId" value="${cover.id }" type="hidden" />
	 	<p style="font-size:20px; text-align: center;">技术服务报价、审核表</p>
	 	<div>
	 		<span style="float:left; margin-left: 1%;">合同名称：${cover.contract}</span>
			<span style="float:right; margin-right: 1%;">金额单位：元</span>
	 	</div>
	</div>
 	
 	<div region="center" border="false">
	 	<div id="tb">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addPurchaseRow(this)">添加</a>
		</div>
	 	<table id="tech" class="easyui-datagrid" fit="true"
	       	data-options="url:'tPmContractPriceTechController.do?datagrid&tpId=${cover.id }',
	       		idField:'id', singleSelect:true, striped:true, nowrap:false, toolbar:'#tb',
	       		onDblClickRow:dbClickRow,
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'purchase');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'purchase');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'purchase');}">   
		    <thead frozen="true">   
		        <tr> 
		        	<th rowspan="1" data-options="field:'serialNum',align:'left', width:100">序号</th>
		            <th rowspan="1" data-options="field:'content', align: 'center', width:100" editor="text">技术服务内容</th>
		            <th rowspan="1" data-options="field:'explain', align: 'center', width:100" editor="text">报价构成说明</th>
		            <th rowspan="1" data-options="field:'priceAmount', align: 'center', width:150" editor="text">报价栏</th>
		            <th rowspan="1" data-options="field:'auditAmount', align: 'center', width:100" editor="text">审价栏</th>
		            <th rowspan="1" data-options="field:'memo', align: 'center', width:100" editor="text">备注</th>
                    <c:if test="${read != '1' }">
                       <th rowspan="1" data-options="field:'addChildFlag', width:150" formatter='formatOPT2'>操作</th> 
                    </c:if>
	            </tr>
	         </thead>
		</table>
	</div>
</div>
</body> 	

<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/price/price.js"></script>
<script type="text/javascript">
var editId = "";
$(document).ready(function(){
    
});

/**
 * 添加子节点
 */
function addPurchaseRow(a){
	var tableId = "tech";
	var url = "tPmContractPriceTechController.do?";
	
	// 保存之前处于编辑状态的节点
	saveNode(tableId, url+"doUpdate");
	
	// 添加节点到数据库
	$.ajax({
		type: "POST",
		url: url+"doAdd",
		data: {
			tpId:$("#tpId").val(),
		},
	   	success: function(data){
	   		var json = $.parseJSON(data);
	   		var newNode = $.parseJSON(json.obj);
	   		editId = newNode.id;
	   		
	   		// 添加节点到页面
			$('#'+tableId).datagrid('appendRow', newNode);
	   		// 获得index
	   		var index = $('#'+tableId).datagrid('getRowIndex', newNode.id);
			// 将节点变为编辑状态
			$('#'+tableId).datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			bindCal(index);
	   	}
	});
}

/**
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "tech";
	var url = "tPmContractPriceTechController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	bindCal(index);
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'tech';
		var url = "tPmContractPriceTechController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		bindCal(index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "tech";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "tech";
	var url = "tPmContractPriceTechController.do?doUpdate";
	
	save(a, tableId, url);
}


/**
 * 删除一行
 */
function deleteRow(a){
	var tableId = "tech";
	var url = 'tPmContractPriceTechController.do?doDel';
	
	deleteNode(a, tableId, url);
}


function updateParent(oldData, newData){
}

function check(index){
	if(commonCheck('tech', index, 'content', '技术服务内容', 250)
			&& commonCheck('tech', index, 'explain', '报价构成说明', 250)
			&& commonCheck('tech', index, 'memo', '备注', 250)){
		return true;
	}
	return false;
}
</script>

</html>