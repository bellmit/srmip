<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="finishApplyList" checkbox="true" fitColumns="false" actionUrl="tBPurchasePlanMainController.do?auditList&operateStatus=${operateStatus}&datagridType=${datagridType}"
      onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group" >
       <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
       <t:dgCol title="申请id" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划时间" field="planDate" formatter="yyyy-MM" query="true"  queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="责任单位id" field="dutyDepartId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="责任单位" field="dutyDepartName" query="true"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="负责人id" field="managerId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="负责人" field="managerName" query="true" queryMode="single" isLike="true"  width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" query="true" isLike="true"  queryMode="single" width="200"></t:dgCol>
      <t:dgCol title="项目编码" field="projectCode" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="总经费" field="totalFunds"   queryMode="group" width="120" extendParams="formatter:formatCurrency," align="right"></t:dgCol>
      <t:dgCol title="审核状态" field="auditStatus"  codeDict="1,SPZT" queryMode="group" width="120" align="right"></t:dgCol>
      <!-- 已处理 -->
      <c:if test="${operateStatus eq YES}">
        <t:dgCol title="审核意见" field="suggestion_code" codeDict="1,SPYJ" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="意见内容" field="suggestion_content" queryMode="group" width="150"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="查看详情" funname="handlerApplyAppr(appr_id)"></t:dgFunOpt>
      </c:if>

      <c:if test="${operateStatus eq NO}">
        <t:dgCol title="发送人" field="operate_username" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="发送时间" field="operate_date" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt title="审核" funname="handlerApplyAppr(appr_id,id,auditStatus)"></t:dgFunOpt>
        <t:dgToolBar title="批量生成编号" icon="icon-edit"  funname="goCreateCode" width="100%" height="100%"></t:dgToolBar>
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
		var title = "采购计划审核信息";
		var url = 'tPmApprLogsController.do?goApprTab' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_CGJH%>';
		var width = 820;
		var height = 500;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		
		handlerAppr(title, url, width, height, apprStatus, finish, receiveId);
	}
	
	//生成编号
	function goCreateCode(){
		var rows = $("#finishApplyList").datagrid("getRows");
		if(rows.length==0){
			tip("请选择需要生成编号的记录");
			return false;
		}
		var ids = [];
		for(var i = 0;i<rows.length;i++){
			ids.push(rows[i].appr_id);
		}
		var url = "tBPurchasePlanMainController.do?goCode";
		var width = 400;
		var height = 100;
		var title = "生成编号";
		$.dialog({
			id : 'codeDialog',
			content: 'url:'+url,
			lock : true,
			width:width,
			height:height,
			data:{
				planIds:ids
			},
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
	
	 
</script>