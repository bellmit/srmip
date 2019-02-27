<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="com.kingtake.common.constant.ProjectConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="main_funds_list">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmProjectFundsApprList" checkbox="false" fitColumns="false" 
			actionUrl="tPmProjectFundsApprController.do?getFundsAppr&datagridType=${datagridType}&operateStatus=${operateStatus}" 
			idField="id" fit="true" queryMode="group" sortName="fundsLog.operateDate" >
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目ID" field="projectid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="project_name" query="true" queryMode="single" width="220" extendParams="formatter:detailProjectInfo,"></t:dgCol>
			<t:dgCol title="会计编号" field="accounting_code" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="总经费" field="total_funds" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="年度经费" field="year_funds" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="审核状态" field="audit_status" replace="未审核_0,待审核_1,通过_2,不通过_3"  queryMode="group" width="80"></t:dgCol>
			
			<!-- 待审核 -->
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="发送人" field="submit_userName" queryMode="group" width="80" ></t:dgCol>
				<t:dgCol title="发送时间" field="submit_time" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
				<t:dgCol title="发送备注" field="submit_msg" queryMode="group" width="150" align="center"></t:dgCol>
				
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="审核" funname="handlerFundsAppr(id,funds_appr_id)" ></t:dgFunOpt>
			</c:if>
		
			<!-- 已审核 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="审核人" field="audit_userName" queryMode="group" width="80" ></t:dgCol>
				<t:dgCol title="审核时间" field="audit_time" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="150" align="center"></t:dgCol>
				<t:dgCol title="审核意见" field="audit_msg" queryMode="group" width="150"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="dblClickDetail(id,funds_appr_id)" ></t:dgFunOpt>
			</c:if>
		
			<t:dgCol title="预算对像ID" field="funds_appr_id"  queryMode="single" hidden="true"  width="120"></t:dgCol>
			<%--
			<t:dgCol title="发票号" field="invoice_num" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="项目主键" field="projectid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="预算审核表主键" field="appr_id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同或计划标志" field="plan_contract_flag" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开始年度" field="start_year" formatter="yyyy" queryMode="group" width="60" align="center"></t:dgCol>
			<t:dgCol title="截止年度" field="end_year" formatter="yyyy" queryMode="group" width="60" align="center"></t:dgCol>
			<t:dgCol title="计划下达经费" field="INCOMEPLAN_AMOUNT" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="年度经费" field="year_funds_plan" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol> 
			<t:dgCol title="归垫经费" field="rein_funds_plan" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="审核状态" field="finish_Flag" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
			<t:dgCol title="审核类型" field="label" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审核处理类型" field="handler_type" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="是否可驳回" field="rebut_flag" hidden="true" queryMode="group" width="80"></t:dgCol>--%>
		</t:datagrid>
		<input id="tipMsg" type="hidden" >
	</div>
</div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
<script type="text/javascript">
	function dblClickDetail(id,funds_appr_id){
		handlerFundsAppr(id,funds_appr_id, -1);
	}
	
	//处理预算审核
	function handlerFundsAppr(id,funds_appr_id, flg){
		var title = "预算审核信息";
		var url = 'tPmApprLogsController.do?goApprTab'+'&apprId='+funds_appr_id+"&planContractFlag=1&apprType=02";
		var finish = "<%=ApprovalConstant.APPRSTATUS_FINISH%>";
		var buttons = [
        	{
        		focus : true, name : '通过', callback : function() { 
        			//debugger;
        			auditFundsAppr(id, funds_appr_id, 2,"");
        		} 
			},
			{
     			name : '不通过', callback : function() { 
     				$.messager.confirm('确认', '确认审核不通过吗？', function(r){
     					//debugger;
     					if(r){
     						var boo = typeof(windowapi) == 'undefined';
     				    	var jq = boo ? $ : W.$;
     				    	//$.dialog.confirm("确定审核不通过吗？", function() {
     				    		var url = "tPmIncomeApplyController.do?goPropose";
	     				       	var title = "填写修改意见";        
	     				   	    $.dialog({
	     				   	  		   content : 'url:' + url,
	     				               title : '提出修改意见',
	     				               lock : true,
	     				               opacity : 0.3,
	     				               width : '450px',
	     				               height : '120px',
	     				               ok : function() {
	     				                   iframe = this.iframe.contentWindow;
	     				                   var msgText = iframe.getMsgText();
	     				                   auditFundsAppr(id,funds_appr_id,3,msgText)
	     				               },
	     				               cancel : function() {
	     				               },
	     				           }).zindex();
     				        //}, function(){}, (boo ? windowapi : null) ).zindex();
     					}
     				});
     			}, 
			}
         ];
		
		
		url = "url:"+url;
    	var jq = typeof (windowapi) == 'undefined' ? $ : W.$ ;
    	jq.dialog({
             id : 'incomeApply',
             content : url,
             lock : true,
             width : 1000,
             height : 500,
             title : "审核",
             opacity : 0.3,
             cache : false,
             button: flg==-1?[]:buttons
         });
	}

	function auditFundsAppr(id, funds_appr_id, audit_status,msgText){
		//debugger;
		$.ajax({
			url : 'tPmProjectFundsApprController.do?auditFundsAppr',
				type : 'post',
				dataType : 'json',
				data : {id:id, funds_appr_Id:funds_appr_id, audit_status:audit_status, audit_msg:msgText},
				success : function(data){
					//debugger;
					$.messager.alert("提示信息",data.msg);
					tPmProjectFundsApprListsearch();
				}
			});
	}
	
	$(document).ready(function(){
		//给时间控件加上样式
		$("#tPmProjectFundsApprListtb").find("input[name='startYear_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy'});});
		$("#tPmProjectFundsApprListtb").find("input[name='startYear_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy'});});
	});
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmProjectFundsApprController.do?upload', "tPmProjectFundsApprList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmProjectFundsApprController.do?exportXls","tPmProjectFundsApprList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmProjectFundsApprController.do?exportXlsByT","tPmProjectFundsApprList");
	}
</script>