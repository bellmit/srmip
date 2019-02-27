<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
 <input id="selectRow" type="hidden"/>
<div id="income_div" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<%--   <t:datagrid name="tPmIncomeList" fitColumns="true" title="到账信息表" actionUrl="tPmIncomeController.do?datagrid"  idField="id" fit="true" queryMode="group" onLoadSuccess="refreshIncomeList"> --%>
  <t:datagrid name="tPmIncomeList" fitColumns="true" title="到账信息表" actionUrl="tPmIncomeController.do?datagrid&loadType=${loadType}"  idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="到账年度"  field="incomeYear"  query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到账日期"  field="incomeTime" query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="凭证号"  field="certificate" query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到账顺序号"  field="incomeNo" query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到款科目"  field="incomeSubject" query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="到账金额(元)"  field="incomeAmount"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="可分配余额(元)"  field="balance"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="到账借贷(元)"  field="incomeBorrow"    queryMode="group" align="right" width="120" extendParams="formatter:transformAmount,"></t:dgCol>
   <t:dgCol title="摘要"  field="digest"  query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="会计年度"  field="accountingYear" query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol> --%>
   <c:if test="${loadType eq 'HT'}">
   <t:dgCol title="项目名称"  field="project.projectName" hidden="true"  width="120"></t:dgCol>
   </c:if>
   <c:if test="${loadType eq 'JH'}">
   <t:dgCol title="项目名称"  field="project.projectName" query="true" isLike="true"  queryMode="single" width="120"></t:dgCol>
   </c:if>
<%--    <t:dgCol title="备注"  field="remark"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPmIncomeController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tPmIncomeController.do?goAdd&loadType=${loadType}" funname="add" width="600" height="300"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPmIncomeController.do?goUpdate&loadType=${loadType}" funname="update" width="600" height="300"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmIncomeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeController.do?goUpdate" funname="detail" width="600" height="300"></t:dgToolBar>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <c:if test="${loadType eq 'HT'}">
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
   </c:if>
   <c:if test="${loadType eq 'HT'}">
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   </c:if>
  </t:datagrid>
   
  </div>
  </div>
<!--   <div style="width:650px; overflow: hidden;" id="eastPanel" -->
<!-- 	data-options =  -->
<!-- 		" -->
<!-- 			region:'east', -->
<!-- 			title:'到账分配信息列表', -->
<!-- 			collapsed:true, -->
<!-- 			split:true, -->
<!-- 			border:false, -->
<!-- 			onExpand : function(){ -->
<!-- 			 li_east = 1; -->
<!-- 			}, -->
<!-- 			onCollapse : function() { -->
<!-- 			 li_east = 0; -->
<!-- 			} -->
<!-- 		"> -->
<!-- 			 <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="allotPanel" -->
<!-- 			></div> -->
<!--  </div> -->
 <script src = "webpage/com/kingtake/project/tpmincome/tPmIncomeList.js"></script>		
 <script type="text/javascript">
 var li_east = 0;
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmIncomeListtb").find("input[name='incomeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='incomeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmIncomeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//绑定合同节点select事件
// 	$("#tPmIncomeList").datagrid({
// 		onSelect : refreshAllot
// 	});
	
// 	function refreshIncomeList(data){
// 		var rows = data.rows;
// 		if(rows.length>0){
// 			var selectRow = $("#selectRow").val();
// 			if(selectRow){
// 				$('#tPmIncomeList').datagrid('selectRow',selectRow);
// 			}else{
// 				//第一次加载页面，选中第一行，在来款节点或支付节点加载完成后选中
// 				$('#tPmIncomeList').datagrid('selectRow',0);
// 				$("#selectRow").val(0);
// 				var rowData = $('#tPmIncomeList').datagrid('getSelected');
// 				if(rowData){
// 					refreshAllot(0,rowData);
// 				}
// 				return;
// 			}
// 		}
// 	}
 
//  function refreshAllot(rowIndex, rowData){
// 	 var incomeId = rowData.id;
// 	 var url = "tPmIncomeAllotController.do?tPmIncomeAllot&incomeId="+incomeId;
// 	 $('#allotPanel').panel("refresh", url);
// 	 if(li_east == 0){
// 		  $('#income_div').layout('expand','east');
// 	 }
// 	 $("#selectRow").val(rowIndex);
//  }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmIncomeController.do?upload', "tPmIncomeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmIncomeController.do?exportXls","tPmIncomeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmIncomeController.do?exportXlsByT","tPmIncomeList");
}
 </script>