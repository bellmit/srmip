<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>价款计算书：设计费</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
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
</style>
 
<body>
<div class="easyui-layout" fit="true">
	<div region="north" style="height:100px;" border="false">
	 	<input id="tpId" value="${cover.id }" type="hidden" />
	 	<input id="typeId" value="${detail.typeId }" type="hidden" />
	 	<input id="id" value="${detail.id }" type="hidden" />
	 	<p id="title" style="font-size:20px; text-align: center;">${detail.typeName }计算书</p>
	 	<div>
	 		<span style="float:left; margin-left: 10px;">合同名称：${cover.contract}</span>
			<span style="float:right; margin-right: 10px;">金额单位：元</span>
	 	</div>
 	</div>
 	
 	<div region="center" border="false">
 	<table id="design" class="easyui-datagrid" fit="true"
       	data-options="url:'tPmContractPriceDesignController.do?datagrid&tpId=${cover.id }&typeId=${detail.typeId }',
 				idField:'id', singleSelect:true, striped:true, nowrap:false, fitColumns:true,
 				<c:if test="${read != '1' }">
 				onDblClickRow:dbClickRow,
 				</c:if>
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'design');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'design');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'design');}">   
	    <thead>   
	        <tr> 
	        	<th rowspan="2" data-options="field:'serialNum',align:'left', width:100">序号</th>
	            <th rowspan="2" data-options="field:'typeName', align: 'center', width:100">项目</th>
	            <th rowspan="2" data-options="field:'priceExplain',align:'center', width:100" editor="text">报价说明</th>
	            <th colspan="4" data-options="align:'center', width:260">报价栏</th>   
	            <th colspan="4" data-options="align:'center', width:260">审核栏</th>
	            <th rowspan="2" data-options="field:'memo', align:'center', width:150" editor="text">备注</th>
	            <c:if test="${read != '1' }">
	           		<th rowspan="2" data-options="field:'addChildFlag', width:120" formatter='formatOPT'>操作</th> 
	            </c:if> 
	        </tr> 
	        <tr> 
	        	<th data-options="field:'priceUnit',align:'center', width:60" editor="text">计量单位</th>
	            <th data-options="field:'priceNumber',align:'center', width:60"
	            	editor="{type:'numberbox', options:{min:0, max:99999}}">计量数量</th>
	            <th data-options="field:'priceCost',align:'right', width:60"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:99999.99}}" 
	            	formatter="formatCurrency">计划成本</th>
	            <th data-options="field:'priceAmount',align:'right', width:80"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
	            	formatter="formatCurrency">金额</th>
	            	
	            <th data-options="field:'auditUnit',align:'center', width:60" editor="text">计量单位</th>
	            <th data-options="field:'auditNumber',align:'center', width:60"
	            	editor="{type:'numberbox', options:{min:0, max:99999}}">计量数量</th>   
	            <th data-options="field:'auditCost',align:'right', width:60"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:99999.99}}" 
	            	formatter="formatCurrency">计划成本</th>
	            <th data-options="field:'auditAmount',align:'right', width:80"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
	            	formatter="formatCurrency">金额</th>
	        </tr> 
	    </thead>
	</table>
	</div>
	<div region="south" style="height:100px;" border="false">
	<div>
	 		<span style="float:left; margin-left: 10px;">备注：${budget.memo}</span>
	 	</div>
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
function addChild(a){
	var tableId = "design";
	var url = "tPmContractPriceDesignController.do?";
	
	add(a, tableId, url);
}

/**
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "design";
	var url = "tPmContractPriceDesignController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	bindCal(index);
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'design';
		var url = "tPmContractPriceDesignController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		bindCal(index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "design";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "design";
	var url = "tPmContractPriceDesignController.do?doUpdate";
	
	save(a, tableId, url);
}


/**
 * 删除一行
 */
function deleteRow(a){
	var tableId = "design";
	var url = 'tPmContractPriceDesignController.do?doDel';
	
	deleteNode(a, tableId, url);
}


function updateParent(oldData, newData, parentId){
	var index = $("#design").datagrid('getRowIndex', parentId);
	if(index >= 0){
		var data = $("#design").datagrid('selectRow', index).datagrid('getSelected');
		$("#design").datagrid('updateRow', {
			index: index,
			row: {
				priceAmount: (parseFloat(data.priceAmount) 
						+ parseFloat(newData.priceAmount) 
						- parseFloat(oldData.priceAmount)),
				auditAmount: (parseFloat(data.auditAmount) 
						+ parseFloat(newData.auditAmount) 
						- parseFloat(oldData.auditAmount))
			}
		});
		
		if(data.parentTypeid){
			updateParent(oldData, newData, data.parentTypeid);
		}
	}
}


function bindCal(index){
	// 报价栏：金额=计量数量*计划成本
	calculate(index, 'priceNumber', 'priceCost', 'priceAmount');
	// 审价栏：金额=计量数量*计划成本
	calculate(index, 'auditNumber', 'auditCost', 'auditAmount');
}


/**
 * 计算：z=x*y
 */
function calculate(index, x, y, z){
	var xEditor = $("#design").datagrid('getEditor', 
			{index:index, field:x});
	var yEditor = $("#design").datagrid('getEditor', 
			{index:index, field:y});
	var zEditor = $("#design").datagrid('getEditor', 
			{index:index, field:z});
	xEditor.target.bind("change", function(){
		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
	}); 
	yEditor.target.bind("change", function(){
		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
	});
}

function check(index){
	if(commonCheck('design', index, 'priceExplain', '报价说明', 100)
			&& commonCheck('design', index, 'priceUnit', '报价栏计量单位', 10)
			&& commonCheck('design', index, 'auditUnit', '审核栏计量单位', 10)
			&& commonCheck('design', index, 'memo', '备注', 100)){
		return true;
	}
	return false;
}
</script>

</html>