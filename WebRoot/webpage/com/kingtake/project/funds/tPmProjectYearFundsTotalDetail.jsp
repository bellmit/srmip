<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;" >
		<table style="height:" id="center" fit="true"  class="easyui-treegrid" 
		       data-options="url:'tPmContractFundsController.do?datagridForSum&projectId=${projectId }',
		       	idField:'ID', treeField:'CONTENT'">   
		    <thead>   
		        <tr>   
		            <th data-options="field:'CONTENT', width:'300'" editor="{type:'validatebox'}">项目、内容</th>   
		            <th data-options="field:'NUM', width:'100'">序号</th> 
		            <th data-options="field:'MONEY', width:'120', align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2, max:9999999999.99}}" formatter="formatMoney">金额</th> 
		            <th data-options="field:'TOTALMONEY', width:'120', align:'right'" formatter="formatTotalFundsMoney">总预算金额</th>
		            <!-- <th data-options="field:'HISTORYMONEY', width:'100', align:'right'" formatter="formatCurrency">已编制金额</th> -->

		         </tr>   
		    </thead>
		</table>
	</div>
</div>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/funds/funds.js"></script>	