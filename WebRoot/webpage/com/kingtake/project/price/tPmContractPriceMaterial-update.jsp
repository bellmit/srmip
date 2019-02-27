<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>价款计算书：材料费</title>
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
 		<div id="tb" style="padding-left:20px;">
			<div style="margin-top:5px;">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" 
					onclick="searchMaterial()">原材料比较</a>
			</div>
		</div>
	 	<table id="material" class="easyui-datagrid" fit="true"
	       	data-options="
	       		url:'tPmContractPriceMaterialController.do?datagrid&tpId=${cover.id }&typeId=${detail.typeId }',
   				idField:'id', singleSelect:true, striped:true, nowrap:false, toolbar:'#tb',fitColumns:true,
   				<c:if test="${read != '1' }"> 
   					onDblClickRow:dbClickRow,
   				</c:if>
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'material');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'material');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'material');}">   
			<thead>
		        <tr> 
		        	<th rowspan="2" data-options="field:'serialNum',align:'left', width:100">序号</th>
		            <th rowspan="2" data-options="field:'typeName', align:'center', width:100">种类</th>
		            <th rowspan="2" data-options="field:'priceName',align:'center', width:100" editor="text">名称</th>
		            <th rowspan="2" data-options="field:'prodModel',align:'center', width:60" editor="text">规格型号</th>
		            <th rowspan="2" data-options="field:'calculateUnit',align:'center', width:60" editor="text">计量单位</th>
		            <th rowspan="2" data-options="field:'supplyUnit',align:'center', width:100" editor="text">供货单位</th>
		            <th colspan="3" data-options="align:'center'" editor="text" width="200">报价栏</th>   
		            <th colspan="3" data-options="align:'center'" editor="text" width="200">审核栏</th>
		            <th rowspan="2" data-options="field:'memo', align:'center', width:150" editor="text">备注</th>
		            <c:if test="${read != '1' }"> 
		            	<th rowspan="2" data-options="field:'addChildFlag', width:120" formatter='formatOPT'>操作</th> 
		        	</c:if>
		        </tr> 
		        <tr> 
		            <th data-options="field:'pricePlanNum',align:'center', width:60" 
		            	editor="{type:'numberbox', options:{min:0, max:99999}}">计划数量</th>
		            <th data-options="field:'pricePlanUnitprice',align:'right', width:60" 
		            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
		            	formatter="formatCurrency">计划单价</th>
		            <th data-options="field:'pricePlanAmount',align:'right', width:80" 
		            	editor="{type:'numberbox', options:{precision:2, min:0, max:9999999.99}}" 
		            	formatter="formatCurrency">金额</th>
		            <th data-options="field:'priceAuditNum',align:'center', width:60" 
		            	editor="{type:'numberbox', options:{min:0, max:99999}}">计划数量</th>
		            <th data-options="field:'priceAuditUnitprice',align:'right', width:60" 
		            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
		            	formatter="formatCurrency">计划单价</th>   
		            <th data-options="field:'priceAuditAmount',align:'right', width:80" 
		            	editor="{type:'numberbox', options:{precision:2, min:0, max:9999999.99}}" 
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

/**
 * 添加子节点
 */
function addChild(a){
	var tableId = "material";
	var url = "tPmContractPriceMaterialController.do?";
	
	addRow(a, tableId, url);
}


/**
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "material";
	var url = "tPmContractPriceMaterialController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	bindCal(index);	
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'material';
		var url = "tPmContractPriceMaterialController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		bindCal(index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "material";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "material";
	var url = "tPmContractPriceMaterialController.do?doUpdate";
	
	save(a, tableId, url);
}

/**
 * 删除一行
 */
function deleteRow(a){
	var tableId = "material";
	var url = 'tPmContractPriceMaterialController.do?doDel';
	
	deleteNode(a, tableId, url);
}


function updateParent(oldData, newData, parentId){
	var index = $("#material").datagrid('getRowIndex', parentId);
	if(index >= 0){
		var data = $("#material").datagrid('selectRow', index).datagrid('getSelected');
		$("#material").datagrid('updateRow', {
			index: index,
			row: {
				pricePlanAmount: (parseFloat(data.pricePlanAmount) 
						+ parseFloat(newData.pricePlanAmount) 
						- parseFloat(oldData.pricePlanAmount)),
				priceAuditAmount: (parseFloat(data.priceAuditAmount) 
						+ parseFloat(newData.priceAuditAmount) 
						- parseFloat(oldData.priceAuditAmount))
			}
		});
		
		if(data.parentTypeid){
			updateParent(oldData, newData, data.parentTypeid);
		}
	}
}


function bindCal(index){
	// 报价栏：金额=计划数量*计划单价
	calculate(index, 'pricePlanNum', 'pricePlanUnitprice', 'pricePlanAmount');
	// 审价栏：金额=计划数量*计划单价
	calculate(index, 'priceAuditNum', 'priceAuditUnitprice', 'priceAuditAmount');
}

/**
 * 计算：z=x*y
 */
function calculate(index, x, y, z){
	var xEditor = $("#material").datagrid('getEditor', 
			{index:index, field:x});
	var yEditor = $("#material").datagrid('getEditor', 
			{index:index, field:y});
	var zEditor = $("#material").datagrid('getEditor', 
			{index:index, field:z});
	xEditor.target.bind("change", function(){
		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
	}); 
	yEditor.target.bind("change", function(){
		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
	});
}

function searchMaterial(){
	window.parent.frameElement.api.opener.$.dialog({
		content : 'url:tPmMaterialController.do?tPmMaterialRead',
		lock : true,
		parent : window.parent.frameElement.api,
		width : 900,
		height : 500,
		title : '原材料比较'
	});
}

function check(index){
	if(commonCheck('material', index, 'priceName', '名称', 20)
			&& commonCheck('material', index, 'prodModel', '规格型号', 15)
			&& commonCheck('material', index, 'calculateUnit', '计量单位', 5)
			&& commonCheck('material', index, 'supplyUnit', '供货单位', 30)
			&& commonCheck('material', index, 'memo', '备注', 100)){
		return true;
	}
	return false;
}
</script>
</html>