<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" id="apprId" value="${apprId }"/>
<input type="hidden" id="tpId" value="${tpId }"/>

<input type="hidden" id="dxbl" value="${tPmProject.dxbl }"/>
<input type="hidden" id="zrdwbl" value="${tPmProject.zrdwbl }"/>
<input type="hidden" id="cydwbl" value="${tPmProject.cydwbl }"/>
<input type="hidden" id="xmzbl" value="${tPmProject.xmzbl }"/>
<c:if test="${edit }">
<div style="width:100%;height:30px;">
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" style='border-collapse:collapse;'>
			<tr>
				<td align="right" style="height:30px;">
				         本次可编辑金额：
				     <input id="totalBuget" name="totalBuget" type="text" style="border-style: none none solid none;" value="${totalFunds}" readonly>
				        单位：元
				</td>
				<td align="right">
			    	<input type="button" value="提交" id="subButton" class="Button" onclick="submitData()" />
				</td>
			</tr>
	  </table>           	
</div>
</c:if>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:0px;" >
		<div id="tb"><font color="red">${tip}</font></div>
		<table style="height:" id="center" fit="true"  class="easyui-treegrid" 
		       data-options="url:'tPmContractFundsController.do?datagrid&apprId=${apprId}&tpId=${tpId }',
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
		            <th data-options="field:'CONTENT', width:'250'" editor="{type:'validatebox'}">项目、内容</th>   
		            <th data-options="field:'NUM', width:'100'">序号</th> 
		            <th data-options="field:'money', width:'100', align:'right'" formatter="formatTotalFundsMoney">总预算金额</th>
		            <th data-options="field:'historyMoney', width:'100', align:'right'" formatter="formatCurrency">已编制金额</th>
		            <th data-options="field:'variableMoney', width:'100', align:'right'" 
		            	editor="{type:'numberbox', options:{precision:2, max:9999999999.99}}" formatter="formatMoney">金额</th>
		            <th data-options="field:'REMARK', width:'300'" editor="text">描述</th> 
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
var totalBuget = parseFloat($("#totalBuget").val());//可编辑金额

var editId = "";
var httpUrl = "tPmContractFundsController.do?";
function loadCallback(){
	debugger;
}

function getSaveData(data){
	var saveData = {
			id : data.ID,
			content : data.CONTENT,
			money : data.money,
			historyMoney: data.historyMoney,
			variableMoney: data.variableMoney,
			remark : data.REMARK,
			unit : data.UNIT,
			price : data.PRICE,
			amount : data.AMOUNT,
			addChildFlag: data.ADDCHILDFLAG
	};
	return saveData;
}
</script>