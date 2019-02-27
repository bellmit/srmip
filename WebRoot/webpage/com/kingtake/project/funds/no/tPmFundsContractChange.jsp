<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" id="apprId" value="${tpId }" />

<div class="easyui-layout" fit="true">
	<div region="north" style="height:40px; padding:5px;">
	  	<div style="padding-right:30px;">
			<label>可调整金额总数为：</label>
			<input id="variableSum" value="0" readonly="readonly" real="0" style="width:80px; color:red; border:none;" />
			&nbsp;&nbsp;<label>已调整金额总数为：</label>
			<input id="sum" value="0" readonly="readonly" real="0" style="width:80px; color:red; border:none;" />
			<label style="float:right; margin-left:20px; margin-top:5px;">金额单位：元</label>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" 
				onclick="saveAll()" style="float:right;">保存</a>
		</div>
	</div>
	
	<div region="center" style="padding:0px;" >
		<table id="center" fit="true"  class="easyui-treegrid" 
	        data-options="url:'tPmContractFundsController.do?datagridChange&apprId=${tpId }&treeId=center',
	        	idField:'ID',treeField:'CONTENT',toolbar: '#tb',
	        	onDblClickRow: dbClickRowCon, onLoadSuccess: countMoney,
	        	onBeforeEdit:function(row){row.editing=true; freshRow(row);},
		    	onAfterEdit:function(row){row.editing=false; freshRow(row);},
		    	onCancelEdit:function(row){row.editing=false; freshRow(row);}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'CONTENT'">项目、内容</th>   
		            <th data-options="field:'NUM',align:'center', width:'80'">序号</th>   
		            <th data-options="field:'VARIABLE_MONEY', width:'80', align:'right'" formatter="formatCurrency">可调整金额</th>  
		            <th data-options="field:'MONEY', width:'80', align:'right'" formatter="formatMoney"
		            	editor="{type:'numberbox', options:{precision:2}}">调整金额</th>   
		            <th data-options="field:'REMARK', width:'200'" editor="text">描述</th> 
		            <th data-options="field:'ADD_CHILD_FLAG', width:168" formatter='formatOPT'>操作</th>
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
var changeData = {contract:new Array()};

function dbClickRowCon(row){
	dbClickRow(row, "center");
}

/**
 * 获得变更的数据
 */
function getData(){
	var data = $("#center").treegrid('getData');
	getData2(data);
}
 
</script>
