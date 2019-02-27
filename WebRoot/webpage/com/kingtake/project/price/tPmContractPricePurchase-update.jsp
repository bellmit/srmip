<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>采购类价款计算书：设计费</title>
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
	input{
		height:30px; line-height:30px;
	}
</style>
 
<body>
<div class="easyui-layout" fit="true">
	<div region="north" style="height:100px;" border="false">
	 	<input id="tpId" value="${cover.id }" type="hidden" />
	 	<p style="font-size:20px; text-align: center;">市场购买产品报价、审核表</p>
	 	<div>
	 		<span style="float:left; margin-left: 1%;">合同名称：${cover.contract}</span>
			<span style="float:right; margin-right: 1%;">金额单位：元</span>
	 	</div>
	</div>
 	
 	<div region="center" border="false">
	 	<div id="tb">
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addPurchaseRow(this)">添加</a>
		</div>
	 	<table id="purchase" class="easyui-datagrid" fit="true"
	       	data-options="url:'tPmContractPricePurchaseController.do?datagrid&tpId=${cover.id }',
	       		idField:'id', singleSelect:true, striped:true, nowrap:false, toolbar:'#tb',
	       		onDblClickRow:dbClickRow,
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'purchase');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'purchase');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'purchase');}">   
		    <thead frozen="true">   
		        <tr> 
		        	<th rowspan="2" data-options="field:'serialNum',align:'left', width:100">序号</th>
		        	<th rowspan="2" data-options="field:'purchaseCategory',align:'center', width:100" 
		        		editor="{type:'combobox', options:{
		        			url:'tBCodeTypeController.do?getComboboxListByKey&code=CGZL&codeType=1&validFlag=1', 
		        			valueField:'code', textField:'name', readonly:true, height:30}}"
		        		formatter="formatCategory">种类</th>
		            <th rowspan="2" data-options="field:'categoryName', align: 'center', width:100" editor="text">名称</th>
		            <th rowspan="2" data-options="field:'categoryBrandModel', align: 'center', width:150" editor="text">品牌、规格型号</th>
		            <th rowspan="2" data-options="field:'produceUnit', align: 'center', width:100" editor="text">生产厂家</th>
		            <th rowspan="2" data-options="field:'calculateUnit', align: 'center', width:100" editor="text">计量单位</th>
	            </tr>
	         </thead>
	         <thead>
	            <tr>
	            	<th rowspan="2" data-options="field:'produceNum', align: 'center', width:100"
		            	editor="{type:'numberbox', min:0}">数量</th>
		            <th colspan="2" data-options="align:'center'" editor="text">报价栏</th>   
		            <th colspan="2" data-options="align:'center'" editor="text">审核栏</th>
		            <th rowspan="2" data-options="field:'memo', align:'center', width:150" editor="text">备注</th>
		            <c:if test="${read != '1' }">
		           		<th rowspan="2" data-options="field:'addChildFlag', width:150" formatter='formatOPT2'>操作</th> 
		            </c:if> 
		        </tr> 
		        <tr> 
		            <th data-options="field:'priceUnitprice',align:'right', width:80"
		            	editor="{type:'numberbox', min:0, options:{precision:2}}" formatter="formatCurrency">单价</th>
		            <th data-options="field:'priceAmount',align:'right', width:80"
		            	editor="{type:'numberbox', min:0, options:{precision:2}}" formatter="formatCurrency">总价</th>
		            <th data-options="field:'auditUnitprice',align:'right', width:80"
		            	editor="{type:'numberbox', min:0, options:{precision:2}}" formatter="formatCurrency">单价</th>
		            <th data-options="field:'auditAmount',align:'right', width:80"
		            	editor="{type:'numberbox', min:0, options:{precision:2}}" formatter="formatCurrency">总价</th>
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
var categories = {};
$(document).ready(function(){
	$.ajax({
		url:'tBCodeTypeController.do?getComboboxListByKey&code=CGZL&codeType=1',
		async:false,
		success:function(data){
			categories = $.parseJSON(data);
		}
	});
});

function formatCategory(value, row, index){
	if(value){
		for(var i in categories){
			if(categories[i].code == value){
				return categories[i].name;
			}
		}
	}
}

/**
 * 添加子节点
 */
function addPurchaseRow(a){
	var tableId = "purchase";
	var url = "tPmContractPricePurchaseController.do?";
	
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
	var tableId = "purchase";
	var url = "tPmContractPricePurchaseController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	bindCal(index);
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'purchase';
		var url = "tPmContractPricePurchaseController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		bindCal(index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "purchase";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "purchase";
	var url = "tPmContractPricePurchaseController.do?doUpdate";
	
	save(a, tableId, url);
}


/**
 * 删除一行
 */
function deleteRow(a){
	var tableId = "purchase";
	var url = 'tPmContractPricePurchaseController.do?doDel';
	
	deleteNode(a, tableId, url);
}


function updateParent(oldData, newData){
}


function bindCal(index){
	// 报价栏：总价=数量*单价
	calculate(index, 'produceNum', 'priceUnitprice', 'priceAmount');
	// 审价栏：总价=数量*单价
	calculate(index, 'produceNum', 'auditUnitprice', 'auditAmount');
}


/**
 * 计算：s=x*y*z
 */
function calculate(index, x, y, z){
	var xEditor = $("#purchase").datagrid('getEditor', 
			{index:index, field:x});
	var yEditor = $("#purchase").datagrid('getEditor', 
			{index:index, field:y});
	var zEditor = $("#purchase").datagrid('getEditor', 
			{index:index, field:z});
	xEditor.target.bind("blur", function(){
		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
	}); 
	yEditor.target.bind("blur", function(){
		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
	});
}

function check(index){
	if(commonCheck('purchase', index, 'categoryName', '名称', 25)
			&& commonCheck('purchase', index, 'categoryBrandModel', '品牌、规格型号', 50)
			&& commonCheck('purchase', index, 'produceUnit', '生产厂家', 30)
			&& commonCheck('purchase', index, 'calculateUnit', '计量单位', 5)
			&& commonCheck('purchase', index, 'produceNum', '数量', 6)
			&& commonCheck('purchase', index, 'memo', '备注', 150)){
		return true;
	}
	return false;
}
</script>

</html>