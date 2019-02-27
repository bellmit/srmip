<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" id="apprId" value="${tpId }" />

<div class="easyui-layout" fit="true">
	<div region="north" style="height:40px; padding:5px;">
		<div id="tb" style="padding-right:30px;">
			<label>可调整金额总数为：</label>
			<input id="variableSum" value="0" readonly="readonly" real="0" style="width:80px; color:red; border:none;" />
			&nbsp;&nbsp;<label>已调整金额总数为：</label>
			<input id="sum" value="0" readonly="readonly" real="0" style="width:80px; color:red; border:none;" />
			<label style="float:right; margin-left:20px; margin-top:5px;">金额单位：元</label>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" 
				onclick="saveAll()" style="float:right;">保存</a>
		</div>
	</div>
	<div region="west" title="预算表一" split="true" style="width:800px;">
		<table id="one" fit="true"  class="easyui-treegrid" 
		       data-options="url:'tPmPlanFundsOneController.do?datagridChange&apprId=${tpId }&treeId=one',
		    	idField:'ID',treeField:'EQUIPMENT_NAME',
		    	onDblClickRow: dbClickRowOne, onLoadSuccess: countMoney,
		    	onBeforeEdit:function(row){row.editing=true; freshRow(row)},
		    	onAfterEdit:function(row){row.editing=false; freshRow(row)},
		    	onCancelEdit:function(row){row.editing=false; freshRow(row)}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'EQUIPMENT_NAME'">设备名称</th>   
		            <th data-options="field:'NUM',align:'center', width:'50'">序号</th>   
	             	<th data-options="field:'VARIABLE_MONEY', width:'80', align:'right'" formatter="formatCurrency">可调整金额</th>
	             	<th data-options="field:'MODEL',align:'center'" editor="text">型号</th>
	             	<th data-options="field:'UNIT',align:'center', width:'50'" editor="text">单位</th>
	             	<th data-options="field:'AMOUNT',align:'center', width:'50'" editor="numberbox">数量</th>
	             	<th data-options="field:'PRICE',align:'right', width:'50'" formatter="formatCurrency"
	             		editor="{type:'numberbox', options:{precision:2}}">单价</th>
		            <th data-options="field:'MONEY', width:'70', align:'right'" formatter="formatMoney"
		            	editor="{type:'numberbox', options:{precision:2}}">调整金额</th>   
		            <th data-options="field:'ADD_CHILD_FLAG', width:140" formatter='formatOPT'>操作</th>
		         </tr>   
		    </thead>
		</table>
	</div>
	<div region="center" title="预算表二">
		<table id="two" fit="true"  class="easyui-treegrid" 
		       data-options="url:'tPmContractFundsController.do?datagridChange&apprId=${tpId }&treeId=two',
		    	idField:'ID',treeField:'CONTENT',
		    	onDblClickRow: dbClickRowTwo, onLoadSuccess: countMoney,
		    	onBeforeEdit:function(row){row.editing=true; freshRow(row)},
		    	onAfterEdit:function(row){row.editing=false; freshRow(row)},
		    	onCancelEdit:function(row){row.editing=false; freshRow(row)}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'CONTENT'">项目、内容</th>   
		            <th data-options="field:'NUM',align:'center', width:'60'">序号</th>   
		            <th data-options="field:'VARIABLE_MONEY', width:'80', align:'right'" formatter="formatCurrency">可调整金额</th>  
		            <th data-options="field:'MONEY', width:'80', align:'right'" formatter="formatMoney"
		            	editor="{type:'numberbox', options:{precision:2}}">调整金额</th>   
		            <th data-options="field:'REMARK', width:'150'" editor="text">描述</th> 
		            <th data-options="field:'ADD_CHILD_FLAG', width:140" formatter='formatOPT'>操作</th>
		         </tr>   
		    </thead>
		</table>
	</div>
</div>

<style type="text/css">
.hand{cursor: pointer;}
</style>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/funds/change.js"></script>
<script type="text/javascript">
var editTreeId = "";
var editId = "";
var changeData = {plan:new Array(), contract:new Array()};

function dbClickRowOne(row){
	dbClickRow(row, "one");
}

function dbClickRowTwo(row){
	dbClickRow(row, "two")
}


/**
 * 获得变更的数据
 */
function getData(){
	var data1 = $("#one").treegrid('getData');
	var data2 = $("#two").treegrid('getData');
	
	getData1(data1);
	getData2(data2);
}

</script>
