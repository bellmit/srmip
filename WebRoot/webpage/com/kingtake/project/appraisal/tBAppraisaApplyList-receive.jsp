<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
 <div region="center" style="padding:1px;">
 <t:datagrid name="tBAppraisaApprovalList" checkbox="false" fitColumns="false" 
 	actionUrl="tBAppraisalApplyController.do?datagrid&operateStatus=${operateStatus}" 
 	idField="id" fit="true" queryMode="group" onDblClick="dblhandlerCheckAppr">
  <t:dgCol title="接收表主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
  <t:dgCol title="鉴定计划主键" field="t_b_id" hidden="true" queryMode="single" width="80"></t:dgCol>
  <t:dgCol title="项目名称" field="project_name" query="true" queryMode="single" width="120" ></t:dgCol>
  <t:dgCol title="项目性质" field="project_type" queryMode="single" width="120" codeDict="1,CGJDXMXZ"></t:dgCol>
  <t:dgCol title="开始时间" field="begin_time" hidden="true" queryMode="group" width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
  <t:dgCol title="结束时间" field="end_time" hidden="true" queryMode="group" width="90" formatter="yyyy-MM-dd" align="center"> </t:dgCol>
  <t:dgCol title="总经费（元）" field="total_amount" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"> </t:dgCol>
  <t:dgCol title="鉴定时间" field="appraisa_time" queryMode="group" width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
  <t:dgCol title="鉴定地址" field="appraisa_address" queryMode="group" width="120" ></t:dgCol>
  <t:dgCol title="联系人" field="contact_name" queryMode="group" width="80" ></t:dgCol>
  <t:dgCol title="联系电话" field="contact_phone" queryMode="group" width="100" ></t:dgCol>
  
  <t:dgCol title="审核表主键" field="appr_id" hidden="true" queryMode="single" width="120"></t:dgCol>  
  <t:dgCol title="审核状态" field="audit_status" hidden="true" queryMode="group" width="80" ></t:dgCol>
  <t:dgCol title="审核类型" field="label" queryMode="group" width="120"></t:dgCol>
  <t:dgCol title="审核处理类型" field="handler_type" hidden="true" queryMode="group" width="80"></t:dgCol>
  <t:dgCol title="是否可驳回" field="rebut_flag" hidden="true" queryMode="group" width="80"></t:dgCol>
  
  <!-- 已处理 -->
  <c:if test="${operateStatus eq YES}">
	  <t:dgCol title="审核意见" field="suggestion_code" queryMode="group" codeDict="1,SPYJ" width="80"></t:dgCol>
	  <t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
	  <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
  	  <t:dgFunOpt title="查看详情" funname="handlerCheckAppr(appr_id)" ></t:dgFunOpt>
  </c:if>
  
  <c:if test="${operateStatus eq NO}">
	  <t:dgCol title="发送人" field="operate_username" queryMode="group" width="80" ></t:dgCol>
	  <t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
	  <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
	  <t:dgFunOpt funname="handlerCheckAppr(appr_id,id,handler_type,audit_status)" title="审批"></t:dgFunOpt>
  </c:if>
 
 </t:datagrid>
 <input id="tipMsg" type="hidden" value=""/>
 </div>
 </div>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>		
<script type="text/javascript">
	//双击处理或查看审核
	function dblhandlerCheckAppr(rowIndex, rowDate){
		if(${operateStatus eq YES}){//已处理
			handlerCheckAppr(rowDate.appr_id);
		}else{
			handlerCheckAppr(rowDate.appr_id, rowDate.id, rowDate.handler_type, rowDate.audit_status);
		}
	}
	
	//处理鉴定申请审批
	function handlerCheckAppr(apprId,receiveId,handlerType,apprStatus){
		var title = "鉴定申请审批";
		var width = 900;
		var height = '100%';
		var dialogId = "apprInfo";
		var url = 'tPmApprLogsController.do?goApprTab&role=depart&load=detail' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY%>';
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId, false);
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