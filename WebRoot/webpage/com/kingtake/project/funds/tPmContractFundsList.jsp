<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" id="apprId" value="${apprId }"/>
<input type="hidden" id="changeFlag" value="${changeFlag }"/>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;" >
		<div id="tb"><font color="red">${tip}</font></div>
		<table id="center" fit="true"  class="easyui-treegrid" 
		       data-options="url:'tPmContractFundsController.do?datagrid&apprId=${apprId }',
		       	idField:'ID', treeField:'CONTENT', 
		       	<c:if test="${edit }">
		       	onDblClickRow : dbClickRow,
		       	</c:if>
		       	toolbar:'#tb',
		  		onBeforeEdit:function(row){row.editing=true; freshRow(row.ID);},
		    	onAfterEdit:function(row){row.editing=false; freshRow(row.ID);},
		    	onCancelEdit:function(row){row.editing=false; freshRow(row.ID);}">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'CONTENT', width:'200'" editor="{type:'validatebox'}">项目、内容</th>   
		            <th data-options="field:'NUM', width:'100'">序号</th> 
		            <th data-options="field:'HISTORYMONEY', width:'100', align:'right'" formatter="formatCurrency">历次金额</th>  
		            <th data-options="field:'MONEY', width:'100', align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2, max:9999999999.99}}" formatter="formatMoney">金额</th>   
		            <th data-options="field:'UNIT', width:'100', align:'right'" editor="text">计量单位</th>  
		            <th data-options="field:'PRICE', width:'100', align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2, max:9999999999.99}}" formatter="formatMoney">单价</th>   
		            <th data-options="field:'AMOUNT',align:'center', width:100" 
			             	editor="{type:'numberbox', options:{max:999}}">数量</th>  
		            <th data-options="field:'REMARK', width:'250'" editor="text">描述</th> 
		            <c:if test="${edit }">
		            	<th data-options="field:'ADDCHILDFLAG', width:250" formatter='formatOPT'>操作</th>
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
var httpUrl = "tPmContractFundsController.do?";
$(document).ready(function(){
	loadTips();
});

function getSaveData(data){
	var saveData = {
			id : data.ID,
			content : data.CONTENT,
			money : data.MONEY,
			remark : data.REMARK,
			unit : data.UNIT,
			price : data.PRICE,
			amount : data.AMOUNT
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