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
					reloadtPmPayNodeList();
				}
        	}
        }
    });
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmPayNodeList"  fitColumns="true"   onLoadSuccess="getSelectCount"
  	actionUrl="tPmContractNodeRelatedController.do?datagrid&tpId=${projectId}&contractNodeId=${contractNodeId}" idField="id" fit="true" queryMode="group"
  	sortName="createDate" sortOrder="desc" pagination="false"  onClick="getSelectCount">
   <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="关联表主键" field="rid" hidden="true" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="支付时间"  field="Time" formatter="yyyy-MM-dd"  query="false"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="支付金额(元)"  field="balance"    queryMode="group"  width="80" align="right" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="指定金额(元)" field="Amount" width="80" align="right" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="审核状态" field="auditFlag"  replace="已审核_1,未审核_0" queryMode="group" width="120"></t:dgCol>
   <c:if test="${checkFlag eq 1}">
   <t:dgCol title="支付节点审核" field="audit" width="100" extendParams="formatter:optFormatter,"></t:dgCol>
   </c:if>
   <c:if test="${checkFlag eq 0}">
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgToolBar title="指定" icon="icon-edit" funname="goAssign"  width="400" height="300"></t:dgToolBar>
   </c:if>
   <t:dgFunOpt exp="auditFlag#eq#0" funname="doDelete" title="删除"></t:dgFunOpt>
<%--    <t:dgToolBar title="修改" icon="icon-edit" funname="goUpdateAssign"  width="400" height="300"></t:dgToolBar> --%>
   <t:dgToolBar title="查看支付节点" icon="icon-search" url="tPmPayNodeController.do?goUpdate" funname="detail"  width="550" height="400"></t:dgToolBar>
<%--    <t:dgToolBar title="保存" icon="icon-save" funname="updateContract" ></t:dgToolBar> --%>
  </t:datagrid>
  <input id="selectRow" type="hidden" value="${rowIndex}" />
  <span id="span">&nbsp;</span>
  </div>
 </div>
<!--  <script src = "webpage/com/kingtake/project/m2pay/tPmPayNodeList.js"></script>		 -->
<script type="text/javascript">
	//加载完成后选中第一行合同节点
	function clickContractFirstRow(){
		$('#tPmContractNodeList').datagrid('selectRow',0);
		getSelectCount();
	}
	function getSelectCount(){
		var rowsData = $('#tPmPayNodeList').datagrid('getRows');
		var count =0.00;
		for(var i=0; i<rowsData.length; i++){
			count += Number(rowsData[i].Amount);
		}
		var a=document.getElementById ("count");
		count = transformAmount(count);
		if(a){
			a.innerHTML='汇总金额：'+count+'元';
		}else{
			$("#tPmPayNodeListtb").append("<span id='count' style='float:right;'>汇总金额："+count+"元<span>");
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
		    	reloadtPmPayNodeList();
		    }
		});
	}
function goUpdateAssign(){
	var selected = $('#tPmPayNodeList').datagrid('getSelected');
	if(selected){
		var rid = selected.rid;
		$.dialog({
			content: 'url:tPmContractNodeRelatedController.do?goUpdate&tpid=${projectId}&id='+rid,
			lock : true,
			width:800,
			height:300,
			title:"节点指定修改",
			opacity : 0.3,
			cache:false,
			ok:function(){
				iframe = this.iframe.contentWindow;
				saveObj();
				reloadtPmPayNodeList();
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
	var rid= $("#tPmPayNodeList").datagrid("getRows")[index]['rid'];
	$.ajax({
		type : 'POST',
		url : "tPmContractNodeRelatedController.do?doDel",// 请求的action路径
		data : {
			"id" : rid,
		},
		success : function(data) {
			var d = $.parseJSON(data);
			tip(d.msg);
			$('#tPmPayNodeList').datagrid('reload');
			return;
		}
	});
}	
	//保存合同节点中指定的来款节点
	function updateContract(){
		getSelectCount();
		var contractNodeId = $('#tPmContractNodeList').datagrid('getSelected').id;
		
		var payNodeIds = "";
		var rowsData = $('#tPmPayNodeList').datagrid('getSelections');
		for(var i=0; i<rowsData.length; i++){
			payNodeIds += rowsData[i].id + ",";
		}
		
		payNodeIds = payNodeIds.substr(0,(payNodeIds.length-1));
		$.ajax({
			type : 'POST',
			url : "tPmContractNodeController.do?doUpdate",// 请求的action路径
			data : {
				"id" : contractNodeId,
				"projPayNodeId" : payNodeIds
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
		$("#tPmPayNodeListtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='auditTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='auditTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmPayNodeController.do?upload', "tPmPayNodeList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmPayNodeController.do?exportXls","tPmPayNodeList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmPayNodeController.do?exportXlsByT","tPmPayNodeList");
	}
</script>