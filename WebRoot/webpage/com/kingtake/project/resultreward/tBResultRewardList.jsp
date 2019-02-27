<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" style="height:400px;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBResultRewardList" fitColumns="true" title="${projectName }：成果奖励" actionUrl="tBResultRewardController.do?datagrid&projectId=${projectId}&datagridType=${datagridType}" idField="id" fit="true" onDblClick="dbClick" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="rewardName" query="true" isLike="true" queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="主要完成人id"  field="finishUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要完成人"  field="finishUsername"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要完成单位id"  field="finishDepartid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要完成单位名称"  field="finishDepartname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="简介"  field="summary"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创新点"  field="innovationPoint"   hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟申报奖励类别"  field="reportRewardLevel" codeDict="1,SBJLLB"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟申报等级"  field="reportLevel"  codeDict="1,SBDJ"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="任务来源"  field="taskSource"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="总投资额"  field="investedAmount"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起始日期"  field="beginDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="截止日期"  field="endDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="contacts"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="联系电话"  field="contactPhone"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="我校在项目中的贡献"  field="hgdDevote"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目主键"  field="sourceProjectIds"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="80" align="right"></t:dgCol>
   <t:dgCol title="完成时间" field="finishTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="220" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt exp="finishFlag#eq#0" title="删除" url="tBResultRewardController.do?doDel&id={id}" />
   <t:dgFunOpt  title="发送审核" funname="sendResultRewardAppr(id, finishFlag)"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="tBResultRewardController.do?goAdd&projectId=${projectId }" width="770" height="500" funname="add"></t:dgToolBar>
   <t:dgFunOpt title="编辑" exp="finishFlag#eq#0"    funname="goUpdate"></t:dgFunOpt>
   <t:dgFunOpt title="编辑" exp="finishFlag#eq#3"    funname="goUpdate"></t:dgFunOpt>
   <t:dgFunOpt title="导出"  funname="goExport(id)"></t:dgFunOpt>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBResultRewardController.do?doBatchDel&projectId=${projectId }" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBResultRewardController.do?goUpdate" funname="detail" width="770" height="500"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  <input id="tipMsg" type="hidden" value=""/>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/resultreward/tBResultRewardList.js"></script>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
 <script type="text/javascript" src="webpage/common/util.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
	//给时间控件加上样式
		$("#tBResultRewardListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='finishTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='finishTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//发送成果奖励审核
	function sendResultRewardAppr(apprId, finishFlag){
		var title = "成果奖励审核";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
				'&apprId=' + apprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_RESULT_REWARD%>';
		var width = 900;
		var height = '100%';
		var dialogId = "apprInfo";
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("流程已完成，不能再发送审核");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑审核信息！");
		}
		
		sendApprDialog(title, url, width, height, apprId, finishFlag, '<%=ApprovalConstant.APPR_TYPE_RESULT_REWARD%>');
	}
	
	//更新成果奖励审核状态
	function updateIncomeApprFinishFlag(incomeApprId){
		var url1 = 'tBResultRewardController.do?updateFinishFlag';
		var url2 = url1 + '2';
		updateFinishFlag(incomeApprId, url1, url2);
	}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBResultRewardController.do?upload', "tBResultRewardList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBResultRewardController.do?exportXls","tBResultRewardList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBResultRewardController.do?exportXlsByT","tBResultRewardList");
}

function goUpdate(){
	var url="tBResultRewardController.do?goUpdate&projectId=${projectId }";
	var width=770 ;
	var height=500;
	var title = "编辑";
	update(title,url, "tBResultRewardList" ,width,height);
	
}


//双击查看
function dbClick(rowIndex, rowData){
	gridname = "tBResultRewardList";
	var url="tBResultRewardController.do?goUpdate&id="+rowData.id+"&load=detail";
	var width=770 ;
	var height=500;
	var title = "查看";
	createdetailwindow(title,url,width,height);
}

//导出word
function goExport(id){
	var url="tBResultRewardController.do?exportWord&id="+id;
    window.open(url);
}
 </script>