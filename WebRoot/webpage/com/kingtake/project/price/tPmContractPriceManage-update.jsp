<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
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
	 	<input id="typeId" value="${detail.typeId }" type="hidden" />
	 	<input id="id" value="${detail.id }" type="hidden" />
	 	<p id="title" style="font-size:20px; text-align: center;">${detail.typeName }计算书</p>
	 	<div>
	 		<span style="float:left; margin-left: 10px;">合同名称：${cover.contract}</span>
			<span style="float:right; margin-right: 10px;">金额单位：元</span>
	 	</div>
	</div>
	
	<div region="center" border="false">
	 	<table id="manage" class="easyui-datagrid" fit="true"
	       	data-options="url:'tPmContractPriceManageController.do?datagrid&tpId=${cover.id }&typeId=${detail.typeId }',
				idField:'id', singleSelect:true, striped:true, nowrap:false, fitColumns:true,
				<c:if test="${read != '1' }">
				onDblClickRow:dbClickRow,
				</c:if>
				onBeforeEdit:function(index, row){row.editing=true; freshRow(index, 'manage');},
		    	onAfterEdit:function(index, row){row.editing=false; freshRow(index, 'manage');},
		    	onCancelEdit:function(index, row){row.editing=false; freshRow(index, 'manage');}">   
		    <thead>   
		        <tr> 
		        	<th data-options="field:'serialNum',align:'left', width:100">序号</th>
		            <th data-options="field:'typeName', align:'center', width:150" editor="text">项目</th>
		            <th data-options="field:'priceExplain',align:'center', width:150" editor="text">报价说明</th>   
		            <th data-options="field:'priceAmount',align:'right', width:100" 
		            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
		            	formatter="formatCurrency">报价栏</th>   
		            <th data-options="field:'auditAmount',align:'right', width:100" 
		            	editor="{type:'numberbox', options:{precision:2, min:0, max:999999.99}}" 
		            	formatter="formatCurrency">审核栏</th>
		            <th data-options="field:'memo', align:'center', width:150" editor="text">备注</th>
		            <c:if test="${read != '1' }">
		            	<th data-options="field:'addChildFlag', width:120" formatter='formatOPT'>操作</th> 
		            </c:if> 
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
	var tableId = "manage";
	var url = "tPmContractPriceManageController.do?";
	
	addRow(a, tableId, url);
}


/**
 * 开始编辑
 */
function beginEdit(a){
	var tableId = "manage";
	var url = "tPmContractPriceManageController.do?doUpdate";
	
	edit(a, tableId, url);
}

function dbClickRow(index, row){
	if(row.addChildFlag == "2" || row.addChildFlag == "3"){
		var tableId = 'manage';
		var url = "tPmContractPriceManageController.do?doUpdate";
		
		editCommon(tableId, url, index, row.id);
	}
}

/**
 * 取消编辑
 */
function cancelEdit(a){
	var tableId = "manage";
	
	cancel(a, tableId);
}

/**
 * 保存编辑记录
 */
function saveEdit(a){
	var tableId = "manage";
	var url = "tPmContractPriceManageController.do?doUpdate";
	
	save(a, tableId, url);
}

/**
 * 删除一行
 */
function deleteRow(a){
	var tableId = "manage";
	var url = 'tPmContractPriceManageController.do?doDel';
	
	deleteNode(a, tableId, url);
}


function updateParent(oldData, newData, parentId){
	var index = $("#manage").datagrid('getRowIndex', parentId);
	if(index >= 0){
		var data = $("#manage").datagrid('selectRow', index).datagrid('getSelected');
		$("#manage").datagrid('updateRow', {
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
}


function check(index){  
	if(commonCheck('manage', index, 'typeName', '项目', 15)
			&& commonCheck('manage', index, 'priceExplain', '报价说明', 100)
			&& commonCheck('manage', index, 'memo', '备注', 100)){
		return true;
	}
	return false;
}
</script>

</html>