<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="taskList" checkbox="false" fitColumns="false" actionUrl="tOTravelLeaveController.do?auditList&operateStatus=${operateStatus}&datagridType=${datagridType}" onDblClick="dblClickDetail"
      idField="id" fit="true" queryMode="group" sortName="operate_date">

      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="差旅请假id" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="请假人id" field="leaveId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="请假人名称" field="leaveName" query="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="请假时间" field="leaveTime" formatter="yyyy-MM-dd" queryMode="single"  width="150"></t:dgCol>
      <t:dgCol title="审批状态" field="auditStatus" hidden="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="审批类型" field="label" queryMode="group" width="150"></t:dgCol>

      <!-- 已处理 -->
      <c:if test="${operateStatus eq YES}">
        <t:dgCol title="审批意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="查看详情" funname="handlerTravelAppr(appr_id)"></t:dgFunOpt>
      </c:if>

      <c:if test="${operateStatus eq NO}">
        <t:dgCol title="发送人" field="operate_username" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="审批" funname="handlerTravelAppr(appr_id,id,auditStatus)"></t:dgFunOpt>
      </c:if>

    </t:datagrid>
    <input id="tipMsg" type="hidden" value="" />
  </div>
</div>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		if(${operateStatus eq YES}){//已处理
		  handlerTravelAppr(rowData.appr_id);
		}else{
		    handlerTravelAppr(rowData.appr_id,rowData.id,rowData.finish_flag);
		}
	}

	//处理差旅管理审批
	function handlerTravelAppr(apprId,receiveId,apprStatus){
		var title = "差旅管理审批信息";
		var url = 'tPmApprLogsController.do?goApprTab' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_TRAVEL%>';
		var width = 900;
		var height = 600;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
	}
	
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmIncomeContractApprListtb").find("input[name='startTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='startTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='endTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='endTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmIncomeContractApprListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmIncomeContractApprController.do?upload', "tPmIncomeContractApprList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmIncomeContractApprController.do?exportXls","tPmIncomeContractApprList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmIncomeContractApprController.do?exportXlsByT","tPmIncomeContractApprList");
	}
</script>