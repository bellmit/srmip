<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="fundAndQuota">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBFundsPropertyList" checkbox="false" fitColumns="true" 
  	title="经费类型表" actionUrl="tBFundsPropertyController.do?datagrid2" 
  	idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="性质编码"  field="fundsCode"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="经费类型"  field="fundsName"    queryMode="group"  width="120"></t:dgCol>
   <!-- 2017年1月16日添加绩效与机动费信息 -->
   <t:dgCol title="预算类型"  field="budgetCategoryName"    queryMode="group"  width="80" ></t:dgCol>
   <t:dgCol title="间接费计算方式"  field="INDIRECTFEECALU"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="大学比例"  field="universityProp"    queryMode="group"  width="60" ></t:dgCol>
   <t:dgCol title="责任单位比例"  field="unitProp"    queryMode="group"  width="60"></t:dgCol> 
   <t:dgCol title="承研单位比例"  field="devUnitProp"    queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="项目组比例"  field="projectGroupProp"    queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="备注"  field="memo"    queryMode="group"  width="80"></t:dgCol>
   
   <t:dgToolBar title="录入" icon="icon-add" url="tBFundsPropertyController.do?goAdd" 
  	funname="add" width="500" height="330"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBFundsPropertyController.do?goUpdate" 
   	funname="update" width="500" height="330"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBFundsPropertyController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBFundsPropertyController.do?goUpdate" 
   	funname="detail" width="500" height="330"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
  </div>
 <script src = "webpage/com/kingtake/base/fund/tBFundsPropertyList.js"></script>		
 <script type="text/javascript">
 
 /**
  * 显示左边面板的方法
  */
 function showQuotaInfoPanel(id){
	 var title = "经费限额设置";
	 var url = "tBFundsBudgetRelaController.do?tBFundsBudgetRelaTab&fundId="+id;
     if(li_east == 0){
         $('#fundAndQuota').layout('expand','east');
     }
     $('#fundAndQuota').layout('panel','east').panel('setTitle', title);
     $('#quotaInfo').panel("refresh", url);
 }
 
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBFundsPropertyController.do?upload', "tBFundsPropertyList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBFundsPropertyController.do?exportXls","tBFundsPropertyList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBFundsPropertyController.do?exportXlsByT","tBFundsPropertyList");
}
 </script>