<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>价款计算书：封面</title>
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
	 	<p id="title" style="font-size:20px; text-align: center;">${title }计算书</p>
	 	<div>
	 		<span style="float:left; margin-left: 20px;">合同名称：${cover.contract}</span>
			<span style="float:right; margin-right: 20px;">金额单位：元</span>
	 	</div>
 	</div>
 	
 	<div region="center" border="false">
	 	<table id="profit" class="easyui-datagrid" fit="true"
			data-options="url:'tPmContractPriceProfitController.do?datagrid&tpId=${cover.id }',
				idField:'id', singleSelect:true, striped:true, nowrap:false, fitColumns:true,
				<c:if test="${read != '1' }"> 
				onDblClickRow:dbClickRow,
				</c:if>
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'profit');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'profit');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'profit');}">
			<thead>   
		        <tr>   
		            <th rowspan="2" data-options="field:'serialNum', align:'left', width:100">序号</th>
					<th rowspan="2" data-options="field:'typeName', width:100, align:'center'">项目</th>
					<th rowspan="2" data-options="field:'priceExplain', width:150, align:'center'" editor="text">提取条件说明</th>
					<th colspan="2" data-options="align:'center'" width="160">报价栏</th>
					<th colspan="2" data-options="align:'center'" width="160">审核栏</th>
					<th rowspan="2" data-options="field:'memo', width:150, align:'center'" editor="text">备注</th>
					<c:if test="${read != '1' }">
						<th rowspan="2" data-options="field:'addChildFlag', width:100" formatter='formatOPT'>操作</th>    
		        	</c:if>
		        </tr> 
		        <tr>   
					<th data-options="field:'pricePercent', width:80, align:'center'"
						editor="{type:'numberbox', options:{min:0, max:5}}">百分比(%)</th>
					<th data-options="field:'priceAmount', width:80, align:'right'"
						editor="{type:'numberbox', options:{precision:2, min:0, max:9999999.99}}" 
						formatter="formatCurrency">金额</th>
					<th data-options="field:'auditPercent', width:80, align:'center'"
						editor="{type:'numberbox', options:{min:0, max:99}}">百分比(%)</th>
					<th data-options="field:'auditAmount', width:80, align:'right'"
						editor="{type:'numberbox', options:{precision:2, min:0, max:9999999.99}}" 
						formatter="formatCurrency">金额</th>
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
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "profit";
	var url = "tPmContractPriceProfitController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	bindCal(index);
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'profit';
		var url = "tPmContractPriceProfitController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		bindCal(index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "profit";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "profit";
	var url = "tPmContractPriceProfitController.do?doUpdate";
	
	save(a, tableId, url);
}

function updateParent(){}


function bindCal(index){
	var data = $("#profit").datagrid('selectRow', 0).datagrid('getSelected');
	// 报价栏：金额=百分比*报价栏成本/100
	calculate(index, 'pricePercent', 'priceAmount', data.priceAmount);
	// 审价栏：金额=百分比*审核栏成本/100
	calculate(index, 'auditPercent', 'auditAmount', data.auditAmount);
	$("#profit").datagrid('selectRow', index);
}

/**
 * 计算：y=x*sum/100
 */
function calculate(index, x, y, sum){
	var xEditor = $("#profit").datagrid('getEditor', 
			{index:index, field:x});
	var yEditor = $("#profit").datagrid('getEditor', 
			{index:index, field:y});
	xEditor.target.bind("change", function(){
		$(yEditor.target).numberbox('setValue', xEditor.target.val()*sum/100);
	}); 
}

function check(index){ 
	if(commonCheck('profit', index, 'priceExplain', '提取条件说明', 100)
			&& commonCheck('profit', index, 'memo', '备注', 100)){
		return true;
	}
	return false;
}
</script>

</html>