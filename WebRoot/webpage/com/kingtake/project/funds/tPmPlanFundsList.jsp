<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" id="apprId" value="${apprId }">
<input type="hidden" id="changeFlag" value="${changeFlag }">

<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;">
		<div id="tb"><font color="red">${tip}</font></div>
		<table id="center" fit="true" class="easyui-treegrid"
		 	data-options="url:'tPmPlanFundsController.do?datagrid&apprId=${apprId }&tableFlag=${tableFlag }&tpId=${tpId}',
		 		idField : 'ID', treeField : 'CONTENT', 
		 		<c:if test="${edit}">
		 		onDblClickRow : dbClickRow, 
		 		</c:if>
		 		toolbar:'#tb',
		 		onBeforeEdit : function(row){row.editing=true; freshRow(row.ID);},
		   		onAfterEdit : function(row){row.editing=false; freshRow(row.ID);},
		   		onCancelEdit : function(row){row.editing=false; freshRow(row.ID);}">   
			     <thead>   
			         <tr>   
			             <th data-options="field:'CONTENT', width:200" editor="text">项目、内容</th>   
			             <th data-options="field:'NUM',align:'left', width:100">序号</th> 
			             <th data-options="field:'HISTORYMONEY',align:'right', width:100" formatter="formatCurrency">历次金额</th>  
			             <th data-options="field:'MODEL',align:'center', width:100" editor="text">型号</th>
			             <th data-options="field:'UNIT',align:'center', width:100" editor="text">单位</th>
			             <th data-options="field:'AMOUNT',align:'center', width:100" 
			             	editor="{type:'numberbox', options:{max:999}}">数量</th>  
			             <th data-options="field:'PRICE', width:'100', align:'right'" 
			             		editor="{type:'numberbox', options:{precision:2, min:0}}" 
			             		formatter="formatCurrency">单价</th>   
			             <th data-options="field:'MONEY', width:'100', align:'right'" 
			             		editor="{type:'numberbox', options:{precision:2, max:9999999999.99}}" 
			             		formatter="formatMoney">金额</th>
			             <th data-options="field:'REMARK', width:200" editor="text">备注</th> 
			             <c:if test="${edit}">
			             	<th data-options="field:'ADDCHILDFLAG', width:200" formatter='formatOPT'>操作</th>
			             </c:if>
			         </tr>   
			     </thead>
		 </table>
	</div>
</div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/funds/funds.js"></script>
<script type="text/javascript">
var editId = "";
var httpUrl = "tPmPlanFundsController.do?";
$(document).ready(function(){
	loadTips();
});
  
function getSaveData(data){
	var saveData = {
			id : data.ID,
			content : data.CONTENT,
			model : data.MODEL,
			unit : data.UNIT,
			amount : data.AMOUNT,
			price : data.PRICE,
			money : data.MONEY,
			remark : data.REMARK,
	};
	return saveData;
}

function bindCal(id){
	// 金额=数量*单价
	calculate(id, 'AMOUNT', 'PRICE', 'MONEY');
}
  
/**
 * 计算：z=x*y
 */
function calculate(id, x, y, z){
	var xEditor = $('#center').treegrid('getEditor', {index:id, field:x});
  	var yEditor = $("#center").treegrid('getEditor', {index:id, field:y});
  	var zEditor = $("#center").treegrid('getEditor', {index:id, field:z});
  	xEditor.target.bind("change", function(){
  		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
  	}); 
  	yEditor.target.bind("change", function(){
  		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
  	});
}
</script>