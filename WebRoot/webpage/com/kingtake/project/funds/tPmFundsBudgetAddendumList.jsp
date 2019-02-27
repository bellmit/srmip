<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" id="apprId"  value="${apprId }"/>

<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:0px;" >
    <table id="center" class="easyui-treegrid" fit="true" 
   		data-options="url : 'tPmFundsBudgetAddendumController.do?datagrid&pid=${apprId }',
   			idField : 'ID', treeField : 'CONTENT', 
   			<c:if test="${edit }"> 
   			onDblClickRow : dbClickRow, 
   			</c:if>
	  		onBeforeEdit : function(row){row.editing=true; freshRow(row.ID);},
	    	onAfterEdit : function(row){row.editing=false; freshRow(row.ID);},
	    	onCancelEdit : function(row){row.editing=false; freshRow(row.ID);}">
        <thead>   
            <tr> 
            	<th data-options="field:'CONTENT', width:100, editor:{type:'text'}">开支内容</th>  
                <th data-options="field:'NUM', width:100">序号</th>   
                <th data-options="field:'MODEL', width:'100', align:'center', editor:{type:'text'}">型号</th>
                <th data-options="field:'ACCOUNT',align:'center', width:'100', 
                	editor:{type:'numberbox',options:{min:0}}">数量</th>  
                <th data-options="field:'PRICE', width:'100', align:'right', 
                	editor:{type:'numberbox',options:{precision:2, min:0}}" formatter="formatCurrency">单价</th>   
                <th data-options="field:'MONEY', width:'100', align:'right',
                	editor:{type:'numberbox',options:{precision:2, min:0, max:9999999.99}}" formatter="formatCurrency">金额</th> 
                <th data-options="field:'REMARK',width:200, editor:{type:'text'}">备注</th>  
            	<c:if test="${edit }"> 
                	<th data-options="field:'ADDCHILDFLAG', width:'200', formatter:formatOPT2">操作</th>
                </c:if> 
            </tr>   
        </thead>
    </table>
  </div>
</div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript" src="webpage/com/kingtake/project/funds/funds.js"></script>
<script type="text/javascript">
var editId = null;
var httpUrl = "tPmFundsBudgetAddendumController.do?";
$(document).ready(function(){
});

function formatOPT2(value, row, index){
	var s = "";
	var id = row.ID;
	
	var add = "[<a href='javascript:void(0)' nodeid='"+id+"'  onclick='addRow(this)'>添加</a>] ";
	var edit = "[<a href='javascript:void(0)' nodeid='"+id+"' onclick='editRow(this)'>编辑</a>] ";
	var save = "[<a href='javascript:void(0)' onclick='saveNode()'>保存</a>] ";
	var cancel = "[<a href='javascript:void(0)' nodeid='"+id+"' onclick='cancel(this)'>取消</a>]";
	var del = "[<a href='javascript:void(0)' nodeid='"+id+"'  onclick='deleteRow(this)'>删除</a>] ";
	
	
	if(value == '1'){
		// 可添加
		s += add;
 	}else if(value == '3'){
		// 可编辑、可删除
		if(row.editing){
			s += (save + cancel + del);
 		}else{
 			s += (edit + del);
 		}
 	}
 	
 	return s;
}

function getSaveData(data){
	var saveData = {
			id : data.ID,
			content : data.CONTENT,
			model : data.MODEL,
			account : data.ACCOUNT,
			price : data.PRICE,
			money : data.MONEY,
			remark : data.REMARK,
	};
	return saveData;
}

function bindCal(index){
  	// 金额=数量*单价
  	calculate(index, 'ACCOUNT', 'PRICE', 'MONEY');
}
  
/**
 * 计算：z=x*y
 */
function calculate(id, x, y, z){
  	var xEditor = $('#center').datagrid('getEditor', {index:id, field:x});
  	var yEditor = $("#center").datagrid('getEditor', {index:id, field:y});
  	var zEditor = $("#center").datagrid('getEditor', {index:id, field:z});
  	xEditor.target.bind("change", function(){
  		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
  	}); 
  	yEditor.target.bind("change", function(){
  		$(zEditor.target).numberbox('setValue', xEditor.target.val()*yEditor.target.val());
  	});
}
</script>