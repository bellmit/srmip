<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<script type="text/javascript">
function optFormatter(value,row,index){
	var innerHtml = "";
	if (row.id!=""){
		if(row.auditFlag=="1"){
			innerHtml = innerHtml+'<input type="checkbox" checked="checked" onclick=updateAuditFlag("'+row.rid+'")>';
		}else{
			innerHtml = innerHtml+'<input type="checkbox" onclick=updateAuditFlag("'+row.rid+'")>';
		}
	} 
	return innerHtml;
}

function updateAuditFlag(id){
	$.ajax({
        cache : false,
        type : 'POST',
        data : {"id":id},
        url : "tPmContractNodeRelatedController.do?doUpdateAuditFlag",
        success : function(data) {
        	var d = $.parseJSON(data);
        	if(d.success){
        		var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					reloadtPmIncomeNodeList();
				}
        	}
        }
    });
}
</script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeNodeList" fitColumns="true" onLoadSuccess="getSelectCount"
			actionUrl="tPmContractNodeRelatedController.do?datagrid&tpId=${projectId}&contractNodeId=${contractNodeId}" idField="id" fit="true" queryMode="group"
			sortName="createDate" sortOrder="desc" pagination="false"  >
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="关联表主键" field="rid"  hidden="true"  queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="来款时间" field="Time" formatter="yyyy-MM-dd" query="false" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="来款金额(元)" field="balance" width="80" align="right" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="指定金额(元)" field="Amount" width="80" align="right" extendParams="formatter:transformAmount,"></t:dgCol>
			<t:dgCol title="审核状态" field="auditFlag"  replace="已审核_1,未审核_0" queryMode="group" width="120"></t:dgCol>
			<c:if test="${checkFlag eq 1}">
			<t:dgCol title="来款节点审核" field="audit" width="100" extendParams="formatter:optFormatter,"></t:dgCol>
			</c:if>
			<c:if test="${checkFlag eq 0}">
			<t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
<%-- 			<t:dgFunOpt funname="doDelete" title="提交审核"></t:dgFunOpt> --%>
			<t:dgFunOpt exp="auditFlag#eq#0" funname="doDelete" title="删除"></t:dgFunOpt>
			<t:dgToolBar title="指定" icon="icon-edit" funname="goAssign"  width="400" height="300"></t:dgToolBar>
			</c:if>
<%-- 			<t:dgToolBar title="修改" icon="icon-edit" funname="goUpdateAssign"  width="400" height="300"></t:dgToolBar> --%>
			<t:dgToolBar title="查看来款节点" icon="icon-search" url="tPmIncomeNodeController.do?goUpdate" funname="detail" width="550" height="400"></t:dgToolBar>
<%-- 			<t:dgToolBar title="保存" icon="icon-save" funname="updateContract" ></t:dgToolBar> --%>
		
		</t:datagrid>
		<input id="selectRow" type="hidden" />
		<span id="span">&nbsp;</span>
	</div>
</div>
<!-- <script src = "webpage/com/kingtake/project/m2income/tPmIncomeNodeList.js"></script>		 -->
<script type="text/javascript">
	
	//加载完成后选中第一行合同节点
	function clickContractFirstRow(){
		$('#tPmContractNodeList').datagrid('selectRow',0);
	}
	function getSelectCount(){
		var rowsData = $('#tPmIncomeNodeList').datagrid('getRows');
		var count =0.00;
		for(var i=0; i<rowsData.length; i++){
			count += Number(rowsData[i].Amount);
		}
		var a=document.getElementById ("count");
		count = transformAmount(count);
		if(a){
			a.innerHTML='汇总金额：'+count+'元';
		}else{
			$("#tPmIncomeNodeListtb").append("<span id='count' style='float:right;'>汇总金额："+count+"元</span>");
		}
	}
	
	function goAssign(){
		var contractNodeId = $('#tPmContractNodeList').datagrid('getSelected').id;
		$.dialog({
			content: 'url:tPmContractNodeRelatedController.do?goAssign&tpid=${projectId}&contractNodeId='+contractNodeId,
			lock : true,
			width:800,
			height:300,
			title:"节点指定",
			opacity : 0.3,
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtPmIncomeNodeList();
		    }
		});
	}
	
function goUpdateAssign(){
	var selected = $('#tPmIncomeNodeList').datagrid('getSelected');
	if(selected){
		var rid = selected.rid;
		$.dialog({
			content: 'url:tPmContractNodeRelatedController.do?goUpdate&tpid=${projectId}&id='+rid,
			lock : true,
			width:400,
			height:200,
			title:"节点指定修改",
			opacity : 0.3,
			cache:false,
			ok:function(){
				iframe = this.iframe.contentWindow;
				saveObj();
				reloadtPmIncomeNodeList();
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	
		    }
		}); 
	}else{
			tip("请选择一行后进行修改！");
	}
}
	
	function doDelete(index){
		var rid= $("#tPmIncomeNodeList").datagrid("getRows")[index]['rid'];
		$.ajax({
			type : 'POST',
			url : "tPmContractNodeRelatedController.do?doDel",// 请求的action路径
			data : {
				"id" : rid,
			},
			success : function(data) {
				var d = $.parseJSON(data);
				tip(d.msg);
				$('#tPmIncomeNodeList').datagrid('reload');
				return;
			}
		});
	}
	//保存合同节点中指定的来款节点
	function updateContract(){
		getSelectCount();
		var contractNodeId = $('#tPmContractNodeList').datagrid('getSelected').id;
		
		var incomeNodeIds = "";
		var rowsData = $('#tPmIncomeNodeList').datagrid('getSelections');
		
		for(var i=0; i<rowsData.length; i++){
			incomeNodeIds += rowsData[i].id + ",";
			
		}
		
		incomeNodeIds = incomeNodeIds.substr(0,(incomeNodeIds.length-1));
		$.ajax({
			type : 'POST',
			url : "tPmContractNodeController.do?doUpdate",// 请求的action路径
			data : {
				//"id" : "${contractId}",
				"id" : contractNodeId,
				"projPayNodeId" : incomeNodeIds
			},
			success : function(data) {
				var d = $.parseJSON(data);
				tip(d.msg);
				$('#tPmContractNodeList').datagrid('reload');
				return;
			}
		});
	}
	
	
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmIncomeNodeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeNodeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		
// 		getSelectCount();
	});
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmIncomeNodeController.do?upload', "tPmIncomeNodeList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmIncomeNodeController.do?exportXls","tPmIncomeNodeList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmIncomeNodeController.do?exportXlsByT","tPmIncomeNodeList");
	}
</script>