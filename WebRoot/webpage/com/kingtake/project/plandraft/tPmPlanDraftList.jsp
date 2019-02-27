<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmPlanDraftList" checkbox="true" fitColumns="true" title="计划草案" actionUrl="tPmPlanDraftController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="计划名称"  field="planName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="编制时间"  field="planTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark"    queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="流程状态"  field="planStatus" codeDict="1,SPZT"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" exp="planStatus#eq#0" url="tPmPlanDraftController.do?doDel&id={id}" />
   <t:dgToolBar title="拟制草案" icon="icon-add" url="tPmPlanDraftController.do?goUpdate" funname="add" width="100%" height="100%"></t:dgToolBar>
   <t:dgFunOpt title="编辑" exp="planStatus#eq#0" funname="goUpdate(id)" ></t:dgFunOpt>
   <t:dgFunOpt title="编辑" exp="planStatus#eq#3" funname="goUpdate(id)" ></t:dgFunOpt>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmPlanDraftController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看草案" icon="icon-search" url="tPmPlanDraftController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
   <t:dgFunOpt title="发送审核" funname="sendPlanAppr(id, planStatus)" ></t:dgFunOpt>
   <%-- <t:dgFunOpt title="完成审核" funname="updateFinishFlag(id)" ></t:dgFunOpt> --%>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <input id="tipMsg" type="hidden" value="" />
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmPlanDraftListtb").find("input[name='planTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='planTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmPlanDraftController.do?upload', "tPmPlanDraftList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmPlanDraftController.do?exportXls","tPmPlanDraftList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmPlanDraftController.do?exportXlsByT","tPmPlanDraftList");
}

//跳转到编辑界面
function goUpdate(id){
    var url = "tPmPlanDraftController.do?goUpdate&id="+id;
    createwindow("编辑", url,"100%","100%");
}

//发送计划草案审核
function sendPlanAppr(apprId, finishFlag){
	var title = "审核";
	var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_PLAN_DRAFT%>';
	var width = window.top.document.body.offsetWidth;
	var height = window.top.document.body.offsetHeight-100;
	var dialogId = "apprInfo";
	
	if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("计划草案审核流程已完成，不能再发送审核");
	}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
		url += '&tip=true';
		$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑计划草案审核信息！");
	}
	
	sendApprDialog(title, url, width, height, apprId, finishFlag, '<%=ApprovalConstant.APPR_TYPE_PLAN_DRAFT%>');
}

//更新审核状态
function updateFinishFlag(apprId) {
  var url1 = 'tPmPlanDraftController.do?updateFinishFlag';
  var url2 = url1 + '2';
  updateFinishFlag(apprId, url1, url2);
}
 </script>