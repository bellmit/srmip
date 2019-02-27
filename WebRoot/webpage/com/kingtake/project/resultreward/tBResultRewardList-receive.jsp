<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBResultRewardList"  fitColumns="false" onDblClick="dblClickDetail" actionUrl="tBResultRewardController.do?datagrid&operateStatus=${operateStatus}&datagridType=${datagridType}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="rewardName" query="true"   queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="主要完成人id"  field="finishUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要完成人"  field="finishUsername"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要完成单位id"  field="finishDepartid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主要完成单位名称"  field="finishDepartname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="简介"  field="summary"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创新点"  field="innovationPoint"   hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟申报奖励类别"  field="reportRewardLevel" codeDict="1,SBJLLB"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟申报等级"  field="reportLevel"  codeDict="1,SBDJ"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="任务来源"  field="taskSource"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="总投资额"  field="investedAmount"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="起始日期"  field="beginDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="截止日期"  field="endDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="contacts"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系电话"  field="contactPhone"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="我校在项目中的贡献"  field="hgdDevote"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目主键"  field="sourceProjectIds"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="成果奖励表主键" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="审核类型" field="label" queryMode="group" width="150"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <!-- 已处理 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="审核意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
				<t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="handlerIncomeAppr(appr_id)" ></t:dgFunOpt>
			</c:if>
			
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="发送人" field="operate_username" queryMode="group" width="80" ></t:dgCol>
				<t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="审核" funname="handlerIncomeAppr(appr_id,id,finish_flag)" ></t:dgFunOpt>
			</c:if>
<%--    <t:dgDelOpt title="删除" url="tBResultRewardController.do?doDel&id={id}" /> --%>
<%--    <t:dgFunOpt title="发送审核" funname="sendResultRewardAppr(id, finishFlag)" ></t:dgFunOpt> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tBResultRewardController.do?goAdd&projectId=${projectId }" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBResultRewardController.do?goUpdate&projectId=${projectId }" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBResultRewardController.do?doBatchDel&projectId=${projectId }" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tBResultRewardController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
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
	 $("#tBResultRewardListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='finishTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tBResultRewardListtb").find("input[name='finishTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		if(${operateStatus eq YES}){//已处理
			handlerIncomeAppr(rowData.appr_id);
		}else{
			handlerIncomeAppr(rowData.appr_id,rowData.id,rowData.finish_flag);
		}
	}

	
	//处理进账合同审核
	function handlerIncomeAppr(apprId,receiveId,apprStatus){
		var title = "成果奖励审核信息";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_RESULT_REWARD%>';
		var width = 900;
		var height = 600;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
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
 </script>