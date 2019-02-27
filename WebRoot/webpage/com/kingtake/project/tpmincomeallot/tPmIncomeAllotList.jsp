<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmIncomeAllotList" fitColumns="true"  actionUrl="tPmIncomeAllotController.do?datagrid&income.id=${incomeId}" onDblClick="godetail" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目主键"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="projectName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="负责人ID"  field="projectManagerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="负责人"  field="projectManager"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="负责人单位"  field="projectMgrDept"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分配单位"  field="incomeDept"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分配金额(元)"  field="amount"    queryMode="group"  width="120" align="right" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="分配时间"  field="createDate" formatter="yyyy-MM-dd" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="可分配金额"  field="balance"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="到账信息表主键"  field="incomeId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPmIncomeAllotController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" funname="goAdd" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" funname="goUpdate"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmIncomeAllotController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeAllotController.do?goUpdate&incomeId=${incomeId}" width="400" height="280" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
<!--  <script src = "webpage/com/kingtake/project/tpmincomeallot/tPmIncomeAllotList.js"></script>		 -->
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmIncomeAllotListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeAllotListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeAllotListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeAllotListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });

function goAdd(){
	$.dialog({
		content: 'url:tPmIncomeAllotController.do?goAdd&incomeId=${incomeId}',
		lock : true,
		width:400,
		height:280,
		title:"来款分配",
		opacity : 0.3,
		cache:false,
		ok:function(){
			iframe = this.iframe.contentWindow;
			saveObj();
			var flag = iframe.getCheckFlag();
			if(flag){
				reloadtPmIncomeList();
// 				$('#tPmIncomeList').datagrid('reload');
			}
			return false;
		},
	    cancelVal: '关闭',
	    cancel: function(){
	    }
	});
}

function goUpdate(){
	var row = $('#tPmIncomeAllotList').datagrid('getSelected');
	if(row){
	var id = row.id;
	$.dialog({
		content: 'url:tPmIncomeAllotController.do?goUpdate&incomeId=${incomeId}&id='+id,
		lock : true,
		width:400,
		height:280,
		title:"来款分配",
		opacity : 0.3,
		cache:false,
		ok:function(){
			iframe = this.iframe.contentWindow;
			saveObj();
			var flag = iframe.getCheckFlag();
			if(flag){
				reloadtPmIncomeList();
// 				$('#tPmIncomeList').datagrid('reload');
			}
			return false;
		},
	    cancelVal: '关闭',
	    cancel: function(){
	    }
	});
		
	}else{
		tip("请选择一行后进行操作！");
	}
}

function godetail(rowIndex,rowData){
	var id = rowData.id;
	createdetailwindow("查看",'tPmIncomeAllotController.do?goUpdate&incomeId=${incomeId}&id='+id,'400px','280px');
}

 
// //导入
// function ImportXls() {
// 	openuploadwin('Excel导入', 'tPmIncomeAllotController.do?upload', "tPmIncomeAllotList");
// }

// //导出
// function ExportXls() {
// 	JeecgExcelExport("tPmIncomeAllotController.do?exportXls","tPmIncomeAllotList");
// }

// //模板下载
// function ExportXlsByT() {
// 	JeecgExcelExport("tPmIncomeAllotController.do?exportXlsByT","tPmIncomeAllotList");
// }

//刷新表格
function refreshTab(){
    $("#tPmIncomeList").datagrid("reload");
    $("#tPmIncomeAllotList").datagrid("reload");
}
 </script>