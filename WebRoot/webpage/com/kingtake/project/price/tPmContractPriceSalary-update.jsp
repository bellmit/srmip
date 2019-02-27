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
 	<table id="salary" class="easyui-datagrid" fit="true"
       	data-options="url:'tPmContractPriceSalaryController.do?datagrid&tpId=${cover.id }&typeId=${detail.typeId }',
    		idField:'id', singleSelect:true, striped:true, nowrap:false, fitColumns:true,
    		<c:if test="${read != '1' }"> 
    			onDblClickRow:dbClickRow,
    		</c:if>
			onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'salary');},
	    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'salary');},
	    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'salary');}">   
	    <thead>   
	        <tr> 
	        	<th rowspan="2" data-options="field:'serialNum',align:'left', width:100">序号</th>
	            <th rowspan="2" data-options="field:'unitDepart', align: 'center', width:100" editor="text">单位/部门2</th>
	            <th rowspan="2" data-options="field:'workContent',align:'center', width:100" editor="text">工作内容</th>
	            <th colspan="4" data-options="align:'center'" width="260">报价栏</th>   
	            <th colspan="4" data-options="align:'center'" width="260">审核栏</th>
	            <th rowspan="2" data-options="field:'memo', align:'center', width:150" editor="text">备注</th>
	            <c:if test="${read != '1' }">
	            	<th rowspan="2" data-options="field:'addChildFlag', width:120" formatter='formatOPT'>操作</th>  
	        	</c:if>
	        </tr> 
	        <tr> 
	        	<th data-options="field:'pricePlanPeople',align:'center', width:60"
	        		editor="{type:'numberbox', options:{min:0, max:99999}}">预计人数</th>
	            <th data-options="field:'pricePlanDays',align:'center', width:60"
	            	editor="{type:'numberbox', options:{min:0, max:99999}}">预计天数</th>
	            <th data-options="field:'pricePlanCost',align:'right', width:60"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:9999.99}}" 
	            	formatter="formatCurrency">元/人天</th>
	            <th data-options="field:'pricePlanAmount',align:'right', width:80"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
	            	formatter="formatCurrency">金额</th>
	            <th data-options="field:'auditPlanPeople',align:'center', width:60"
	            	editor="{type:'numberbox', min:0}">预计人数</th>
	            <th data-options="field:'auditPlanDays',align:'center', width:60"
	            	editor="{type:'numberbox', options:{min:0, max:99999}}">预计天数</th>   
	            <th data-options="field:'auditPlanCost',align:'right', width:60"
	            	editor="{type:'numberbox', options:{precision:2, min:0, max:9999.99}}" 
	            	formatter="formatCurrency">元/人天</th>
	            <th data-options="field:'auditPlanAmount',align:'right', width:80"
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
	var tableId = "salary";
	var url = "tPmContractPriceSalaryController.do?";
	
	addRow(a, tableId, url);
}

/**
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "salary";
	var url = "tPmContractPriceSalaryController.do?doUpdate";
	
	var index = edit(a, tableId, url);
	bindCal(index);
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'salary';
		var url = "tPmContractPriceSalaryController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
		bindCal(index);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "salary";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "salary";
	var url = "tPmContractPriceSalaryController.do?doUpdate";
	
	save(a, tableId, url);
}


/**
 * 删除一行
 */
function deleteRow(a){
	var tableId = "salary";
	var url = 'tPmContractPriceSalaryController.do?doDel';
	
	deleteNode(a, tableId, url);
}


function updateParent(oldData, newData, parentId){
	var index = $("#salary").datagrid('getRowIndex', parentId);
	if(index >= 0){
		var data = $("#salary").datagrid('selectRow', index).datagrid('getSelected');
		$("#salary").datagrid('updateRow', {
			index: index,
			row: {
				pricePlanAmount: (parseFloat(data.pricePlanAmount) 
						+ parseFloat(newData.pricePlanAmount) 
						- parseFloat(oldData.pricePlanAmount)),
				auditPlanAmount: (parseFloat(data.auditPlanAmount) 
						+ parseFloat(newData.auditPlanAmount) 
						- parseFloat(oldData.auditPlanAmount))
			}
		});
		
		if(data.parentTypeid){
			updateParent(oldData, newData, data.parentTypeid);
		}
	}
}


function bindCal(index){
	// 报价栏：金额=人数*天数*工资
	calculate(index, 'pricePlanPeople', 'pricePlanDays', 'pricePlanCost', 'pricePlanAmount');
	// 审价栏：金额=人数*天数*工资
	calculate(index, 'auditPlanPeople', 'auditPlanDays', 'auditPlanCost', 'auditPlanAmount');
}


/**
 * 计算：s=x*y*z
 */
function calculate(index, x, y, z, s){
	var xEditor = $("#salary").datagrid('getEditor', 
			{index:index, field:x});
	var yEditor = $("#salary").datagrid('getEditor', 
			{index:index, field:y});
	var zEditor = $("#salary").datagrid('getEditor', 
			{index:index, field:z});
	var sEditor = $("#salary").datagrid('getEditor', 
			{index:index, field:s});
	xEditor.target.bind("change", function(){
		$(sEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val()*zEditor.target.val());
	}); 
	yEditor.target.bind("change", function(){
		$(sEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val()*zEditor.target.val());
	});
	zEditor.target.bind("change", function(){
		$(sEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val()*zEditor.target.val());
	});
}

function check(index){ 
	if(commonCheck('salary', index, 'unitDepart', '单位/部门', 30)
			&& commonCheck('salary', index, 'workContent', '工作内容', 150)
			&& commonCheck('salary', index, 'memo', '备注', 100)){
		return true;
	}
	return false;
}
</script>

</html>