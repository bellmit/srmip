<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>价款计算书：主表</title>
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
	 	<p style="font-size:20px; text-align: center;">合同价款报价、审核表</p>
	 	<div>
	 		<span style="float:left; margin-left: 1%;">合同名称：${cover.contract}</span>
			<span style="float:right; margin-right: 1%;">金额单位：元</span>
	 	</div>
	</div>
	 	
	<div region="center" border="false">
	 	<table id="master" class="easyui-datagrid" fit="true"
			data-options="url:'tPmContractPriceMasterController.do?datagrid&tpId=${cover.id }', 
				idField:'id', singleSelect:true, striped:true, nowrap:false, fitColumns:true,
				<c:if test="${read != '1' }"> 
					onDblClickRow:dbClickRow,
				</c:if>
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'master');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'master');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'master');}">
			<thead>   
		        <tr>   
		            <th data-options="field:'sortCode', align:'left', width:50">序号</th>
					<th data-options="field:'priceBudgetName', align:'center', width:100">项目</th>
					<th data-options="field:'priceColumn', align:'right', width:80" 
						formatter="formatCurrency">报价栏</th>
					<th data-options="field:'auditColumn', align:'right', width:80" 
						formatter="formatCurrency">审价栏</th>
					<th data-options="field:'valuationColumn', align:'right', width:80"
						editor="{type:'numberbox', options:{precision:2, min:0, max:9999999.99}}" 
						formatter="formatCurrency">计价栏</th>
					<th data-options="field:'priceDiffColumn', align:'right', width:80" 
						formatter="formatCurrency">差价栏</th>
					<th data-options="field:'memo', align:'center', width:150"
						editor="{type:'text'}">备注</th>
					<c:if test="${read != '1' }"> 
						<th data-options="field:'parent', width:100" formatter='formatOpt'>操作</th>
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

function formatOpt(value, row, index){
	var edit = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='beginEdit(this)'>编辑</a>] ";
	var save = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='saveEdit(this)'>保存</a>] ";
	var cancel = "[<a href='javascript:void(0)' nodeid='"+row.id+"' onclick='cancelEdit(this)'>取消</a>] ";
	var s = "";
	
	if(value || index > 0){
		// 可编辑节点
		if(row.editing){
			s = save + cancel;
		}else{
			s = edit;
		}
	}
	return s;
}


/**
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "master";
	var url = "tPmContractPriceMasterController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	
	$("#master").datagrid('selectRow', index);
}

/**
 * 双击编辑
 */
function dbClickRow(index, row){
	if(row.parent || index > 0){
		var tableId = 'master';
		var url = "tPmContractPriceMasterController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		
		$("#master").datagrid('selectRow', index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "master";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "master";
	var url = "tPmContractPriceMasterController.do?doUpdate";
	
	save(a, tableId, url);
}



function updateParent(oldData, newData){
	var index = $("#master").datagrid('getRowIndex', oldData.parent);
	if(index >= 0){
		var data = $("#master").datagrid('selectRow', index).datagrid('getSelected');
		$("#master").datagrid('updateRow', {
			index: index,
			row: {
				priceColumn: (parseFloat(data.priceColumn) 
						+ parseFloat(newData.priceColumn) 
						- parseFloat(oldData.priceColumn)),
				auditColumn: (parseFloat(data.auditColumn) 
						+ parseFloat(newData.auditColumn) 
						- parseFloat(oldData.auditColumn)),
				valuationColumn: (parseFloat(data.valuationColumn) 
						+ parseFloat(newData.valuationColumn) 
						- parseFloat(oldData.valuationColumn)),
				priceDiffColumn: (parseFloat(data.priceDiffColumn) 
						+ parseFloat(newData.priceDiffColumn) 
						- parseFloat(oldData.priceDiffColumn))
			}   
		});
	}
}

function check(index){
	return commonCheck('master', index, 'memo', '备注', 100);
}
</script>

</html>