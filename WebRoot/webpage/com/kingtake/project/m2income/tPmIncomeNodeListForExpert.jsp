<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeNodeList" checkbox="false" fitColumns="true" 
			actionUrl="tPmIncomeNodeController.do?datagrid&tpId=${projectId}" idField="id" fit="true" queryMode="group"
			sortName="createDate" sortOrder="desc">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目基_主键" field="tpId" hidden="true" query="false" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="来款金额(元)" field="incomeAmount" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="来款说明" field="incomeExplain"  queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人id" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人" field="createName" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group" width="150" align="center"></t:dgCol>
			<t:dgCol title="审核标志" field="auditFlag" queryMode="group" width="80" codeDict="0,SFBZ"></t:dgCol>
			<t:dgCol title="审核人id" field="auditUserid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审核人" field="auditUsername"  queryMode="group" width="80" ></t:dgCol>
			<t:dgCol title="审核时间" field="auditTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group" width="150" align="center"></t:dgCol>
			<t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" hidden="true" formatter="yyyy-MM-dd"  queryMode="group" width="90" align="center"></t:dgCol>
			
			<t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeNodeController.do?goUpdate" 
				funname="detail" width="550" height="400"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/project/m2income/tPmIncomeNodeList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
$(document).ready(function(){
	//设置datagrid的title
	var projectName = window.parent.getParameter();
	$("#tPmIncomeNodeList").datagrid({
		title:projectName+'-来款节点管理'
	});
  
	//给时间控件加上样式
	$("#tPmIncomeNodeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmIncomeNodeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmIncomeNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmIncomeNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmIncomeNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmIncomeNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
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