<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" style="height:400px;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBResultSpreadList" checkbox="true" fitColumns="true" title="${projectName}:成果推广基本信息表" actionUrl="tBResultSpreadController.do?datagrid&projectId=${projectId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="技术开发单位"  field="techDevUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="成果完成人id"  field="finishUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="成果完成人姓名"  field="finishUsername"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="技术简介"  field="techSummary"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专利状态"  field="patentStatus"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="获奖情况"  field="rewardInfo"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="技术状态"  field="techStatus"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="可应用领域"  field="applyScope"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="转化预期"  field="changeExpect"  hidden="true"    queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="投入需求(万)"  field="devotionRequirement"    queryMode="group"  width="120" extendParams="formatter:formatCurrency,"></t:dgCol>
   <t:dgCol title="预期效益"  field="expectBenefit"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="resultContact"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="resultPhone"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="转化进行情况"  field="changeInfo"  codeDict="1,CGZHQK"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合作单位"  field="cooperativeUnit"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="转让形式"  field="transferForm"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同情况"  field="contractInfo"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同期限"  field="contractDeadline"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同金额"  field="contractAmount"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="年度收益"  field="contractIncome"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="需解决困难"  field="resolveDifficult"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="changeContact"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="changePhone"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <%-- <t:dgDelOpt title="删除" url="tBResultSpreadController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBResultSpreadController.do?goAdd&projectId=${projectId}" funname="add" width="800" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBResultSpreadController.do?goUpdate&projectId=${projectId}" funname="update" width="800" height="500"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBResultSpreadController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBResultSpreadController.do?goUpdate" funname="detail" width="800" height="500"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/resultspread/tBResultSpreadList.js"></script>
 <script type="text/javascript" src="webpage/common/util.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBResultSpreadController.do?upload', "tBResultSpreadList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBResultSpreadController.do?exportXls&projectId=${projectId}","tBResultSpreadList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBResultSpreadController.do?exportXlsByT","tBResultSpreadList");
}
 </script>