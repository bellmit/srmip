<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<div id="tb" style="padding-left:20px;">
			<div style="margin-top:5px;">
				<label>名称：</label>
				<input name="materialName" id="materialName"
					style="height:25px; line-height:25px; border:1px solid #CACACA;"/>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="reloadMaterial()">查询</a>
			</div>
		</div>
		<table id="material" fit="true" class="easyui-datagrid"
			data-options="singleSelect:true, pagination:true, rownumbers:true, border:false, toolbar:'#tb',
				url:'tPmMaterialController.do?datagrid&field=materialName,materialModel,materialFactory,materialUnit,materialPrice,supplyDate,materialResource'">
			<thead>
				<tr>
					<th data-options="field:'materialName', width:125">名称</th>
					<th data-options="field:'materialModel', width:125">规格、型号</th>
					<th data-options="field:'materialFactory', width:125">生产厂家</th>
					<th data-options="field:'materialUnit', width:100">计量单位</th>
					<th data-options="field:'materialPrice',align:'right', width:100"
						formatter="formatCurrency">单价</th>
					<th data-options="field:'supplyDate', width:'125',
	              		formatter:function(value){
	              			return new Date().format('yyyy-MM-dd',value);
	              		}">录入日期</th>
	              	<th data-options="field:'materialResource', width:'100',
	              		formatter:function(value){
	              			for(var i in lrly){
	              				if(lrly[i].code == value){
	              					return lrly[i].name;
	              				}
	              			}
	              		}">来源</th>
				</tr>
			</thead>
		</table>
	</div>
</div>

<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
var lrly = {};
$(document).ready(function(){
	$.ajax({
		type:'post',
		url:'tBCodeTypeController.do?getComboboxListByKey',
		data:{
			codeType:'1',
			code:'LRLY'
		},
		success:function(data){
			lrly = $.parseJSON(data);
		}
	});
	
	reloadMaterial();
});

function reloadMaterial(){
	$("#material").datagrid('load', {
		materialName:$("#materialName").val()
	});
}

function doDel(){
	var data = $("#material").datagrid('getSelected');
	if(data){
		$.messager.confirm('确认', '确认删除该条记录吗？', function(r){
			if(r){
				$.ajax({
					type:'post',
					url:'tPmMaterialController.do?doDel',
					data:'id='+data.id,
					success:function(result){
						result = $.parseJSON(result);
						showMsg(result.msg);
						reloadMaterial();
					}
				});
			}
		});
	}else{
		showMsg('请选择需要删除的记录！');
	}
}

function showMsg(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:3000,
		showType:'slide'
	});

}
</script>