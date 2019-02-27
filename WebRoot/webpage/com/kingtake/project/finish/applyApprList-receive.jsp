<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="finishApplyList" checkbox="false" fitColumns="false" actionUrl="tPmFinishApplyController.do?auditList&operateStatus=${operateStatus}&datagridType=${datagridType}"
      onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group" sortName="operate_date">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="结题申请id" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目主键" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" query="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="审批状态" field="auditStatus" hidden="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="审批类型" field="label" queryMode="group" width="150"></t:dgCol>

      <!-- 已处理 -->
      <c:if test="${operateStatus eq YES}">
        <t:dgCol title="审批意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="查看详情" funname="handlerApplyAppr(appr_id)"></t:dgFunOpt>
      </c:if>

      <c:if test="${operateStatus eq NO}">
        <t:dgCol title="发送人" field="operate_username" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="审批" funname="handlerApplyAppr(appr_id,id,auditStatus)"></t:dgFunOpt>
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

	//处理审批
	function handlerApplyAppr(apprId,receiveId,apprStatus){
		var title = "结题申请审批信息";
		var url = 'tPmApprLogsController.do?goApprTab' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_FINISH_APPLY%>';
		var width = 820;
		var height = 500;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
	}
	
	$(document).ready(function(){
	});
	 
</script>