<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
function optFormatter(value,row,index){
	var innerHtml = "";
	if (row.id!=""){
		if(row.auditFlag=="1"){
			innerHtml = innerHtml+'<input type="checkbox" checked="checked" onclick=updateAuditFlag("'+row.id+'")>';
		}else{
			innerHtml = innerHtml+'<input type="checkbox" onclick=updateAuditFlag("'+row.id+'")>';
		}
	} 
	return innerHtml;
}
</script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<t:datagrid name="tPmPayNodeList" checkbox="false" fitColumns="false" 
	actionUrl="tPmPayNodeController.do?datagrid&tpId=${projectId}" idField="id" 
	fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
	
	<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="项目_主键" field="tPId" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="支付时间" field="payTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
	<t:dgCol title="支付金额(元)" field="payAmount" queryMode="group" width="90" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
	<t:dgCol title="备注" field="memo" queryMode="group" width="200"></t:dgCol>
	<t:dgCol title="审核人id" field="auditUserid" hidden="true" queryMode="group" width="120"></t:dgCol>
	<t:dgCol title="审核标志" field="auditFlag" hidden="true" queryMode="group" width="80" codeDict="0,SFBZ"></t:dgCol>
	<t:dgCol title="审核人" field="auditUsername" hidden="true" queryMode="group" width="80"></t:dgCol>
	<t:dgCol title="审核时间" field="auditTime" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
	<t:dgCol title="支付节点审核" field="audit" width="100" extendParams="formatter:optFormatter,"></t:dgCol>
	
	<t:dgFunOpt title="审核" exp="auditFlag#ne#1" funname="updateAuditFlag(id)"></t:dgFunOpt>
	<t:dgFunOpt title="取消审核" exp="auditFlag#eq#1" funname="updateAuditFlag(id)"></t:dgFunOpt>
	
	<t:dgToolBar title="查看" icon="icon-search" url="tPmPayNodeController.do?goUpdate" funname="detail" width="550" height="400"></t:dgToolBar>
	<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>

</t:datagrid>
 
<script src = "webpage/com/kingtake/project/m2pay/tPmPayNodeList.js"></script>
<script type="text/javascript">
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