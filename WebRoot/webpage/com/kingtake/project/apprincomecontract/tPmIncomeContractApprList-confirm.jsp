<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeContractApprList" checkbox="false" fitColumns="true" 
			actionUrl="tPmIncomeContractApprController.do?datagridForConfirm&operateStatus=${operateStatus}&datagridType=${datagridType}"
			onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="申请单位" field="applyUnit" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
			<%-- <t:dgCol title="合同类别" field="contractType" codeDict="1,HTLB" hidden="false" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="其他合同类别" field="contractTypeContent" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="对方单位" field="approvalUnit" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同第三方" field="theThird" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="截止时间" field="endTime" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="总经费(元)" field="totalFunds" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="审批状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="80" align="right"></t:dgCol>
			<t:dgCol title="是否已上传合同正本" field="contractBookFlag" replace="是_1,否_0,已确认_2" queryMode="group" width="80" align="right"></t:dgCol>
      
			<t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
			
			<!-- 已完成 -->
			<c:if test="${operateStatus eq YES}">
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="查看详情" funname="handlerIncomeAppr(id)" ></t:dgFunOpt>
			</c:if>
			<!-- 未完成 -->
			<c:if test="${operateStatus eq NO}">
				<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
				<t:dgFunOpt title="确认" funname="confirmFinish(id)" ></t:dgFunOpt>
			</c:if>
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
<script src = "webpage/com/kingtake/project/apprincomecontract/tPmIncomeContractApprList.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		    handlerIncomeAppr(rowData.id);
	}
	
	//处理进账合同审批
	function handlerIncomeAppr(apprId,receiveId,apprStatus){
		var title = "进账合同审批信息";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + apprId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_INCOME%>'+'&suggestion=false';
		var width = 900;
		var height = window.top.document.body.offsetHeight-100;
		var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
		handlerAppr(title, url, width, height, "send", finish, receiveId);
	}
	
	//查看
	function viewAppr(id){
	    var title = "查看";
		var url = "tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false&id=" + id;
		var width = 750;
		var height = window.top.document.body.offsetHeight-100;
		tabDetailDialog(title, url, width, height);
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
	
	//更新合同正本状态
	function updateContractBookFlag(incomeApprId){
		var url1 = 'tPmIncomeContractApprController.do?confirmContractBook&id='+incomeApprId;
		$.ajax({
			url:url1,
			dataType:"json",
			type:'POST',
			success:function(data){
				tip(data.msg);
				if(data.success){
					reloadTable();
				}
			}
		});
	}
	
	//确认完成审批
	function confirmFinish(incomeApprId){
	    if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确认已上传合同正本吗？", function() {
            	updateContractBookFlag(incomeApprId);
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确认已上传合同正本吗？", function() {
                updateContractBookFlag(incomeApprId);
            }, function() {
            },windowapi).zindex();
        }
	}
</script>