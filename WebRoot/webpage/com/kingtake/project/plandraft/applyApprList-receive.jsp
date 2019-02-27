<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="planDraftApplyList" checkbox="false" fitColumns="false" actionUrl="tPmPlanDraftController.do?auditList&operateStatus=${operateStatus}&datagridType=${datagridType}"
      onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group" sortName="operate_date">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划草案id" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划名称" field="PLAN_NAME" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="编制时间" field="PLAN_TIME" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="备注" field="REMARK" queryMode="group" width="200"></t:dgCol>
      <t:dgCol title="流程状态" field="auditStatus" codeDict="1,SPZT" queryMode="group" width="80"></t:dgCol>
      <!-- 已处理 -->
      <c:if test="${operateStatus eq YES}">
        <t:dgCol title="审核意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="查看详情" funname="handlerApplyAppr(appr_id)"></t:dgFunOpt>
        <t:dgFunOpt funname="ExportProjectXls(appr_id)" title="导出项目基本信息"></t:dgFunOpt>
      </c:if>

      <c:if test="${operateStatus eq NO}">
        <t:dgCol title="发送人" field="operate_username" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="审核" funname="handlerApplyAppr(appr_id,id,auditStatus)"></t:dgFunOpt>
        <t:dgFunOpt funname="ExportProjectXls(appr_id)" title="导出项目基本信息"></t:dgFunOpt>
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
		  handlerApplyAppr(rowData.appr_id);
		}else{
		  handlerApplyAppr(rowData.appr_id,rowData.id,rowData.finish_flag);
		}
	}

	//处理审核
	function handlerApplyAppr(apprId,receiveId,apprStatus){
		var title = "计划草案审核信息";
		var url = 'tPmApprLogsController.do?goApprTab' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_PLAN_DRAFT%>';
		var width = 1024;
		var height = '100%';
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
	}
	
	$(document).ready(function(){
	});
	
	//导出项目基本信息
	function ExportProjectXls(id) {
		JeecgExcelExport("tPmPlanDraftController.do?exportProjectXls&id=" + id,"planDraftApplyList");
	}
</script>