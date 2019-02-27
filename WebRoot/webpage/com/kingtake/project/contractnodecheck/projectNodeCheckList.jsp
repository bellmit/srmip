<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <c:if test="${type eq '1'}">
      <t:datagrid name="projectNodeCheckList" checkbox="false" fitColumns="false" title="进账合同节点考核"
        actionUrl="tPmContractNodeCheckController.do?datagrid&datagridType=${datagridType}&projectId=${projectId}" idField="id" fit="true" queryMode="group" pagination="false">
        <t:dgCol title="合同节点id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="合同主键id" field="in_out_contractid" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="合同名称" field="contract_name" query="false" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="节点名称" field="node_name" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="完成时间" field="complete_date" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
        <t:dgCol title="验收状态" field="check_status" query="false" queryMode="group" codeDict="1,SPZT" width="80"></t:dgCol>
        <t:dgCol title="合同验收主键" field="check_id" hidden="true" query="false" queryMode="group" width="80"></t:dgCol>
        <t:dgCol title="节点验收操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt exp="check_status#eq#u" funname="addOrUpdateCheck(id,check_id)" title="录入"></t:dgFunOpt>
        <t:dgFunOpt exp="check_status#ne#u&&check_status#ne#1&&check_status#ne#2" funname="addOrUpdateCheck(id,check_id)" title="编辑"></t:dgFunOpt>
        <t:dgFunOpt exp="check_status#ne#u" funname="sendCheckAppr(check_id, check_status)" title="发送审核"></t:dgFunOpt>
        <t:dgToolBar title="查看合同信息" icon="icon-search"   funname="detailContractView"
             height="600" width="750"></t:dgToolBar>
        </t:datagrid>
    </c:if>
    <c:if test="${type eq '2'}">
      <t:datagrid name="projectNodeCheckList" checkbox="false" fitColumns="false" actionUrl="tPmTaskController.do?taskNodeApprDatagrid&datagridType=${datagridType}&projectId=${projectId}"
        idField="id" fit="true" queryMode="group" onDblClick="dblhandlerNodeCheckAppr">
        <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="任务书名称" field="TASK_TITLE" query="true" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="任务节点内容" field="TASK_CONTENT" query="true" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="计划时间" field="PLAN_TIME" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
        <t:dgCol title="计划完成时间" field="END_TIME" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
        <t:dgCol title="是否完成" field="FINISHFLAG" replace="是_1,否_0"  queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="实际完成时间" field="FINISH_TIME" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="验收状态" field="check_status"  queryMode="group" codeDict="1,SPZT" width="80"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
        <t:dgFunOpt exp="check_status#eq#u" funname="addOrUpdateTaskNodeCheck(id,FINISHFLAG)" title="录入"></t:dgFunOpt>
        <t:dgFunOpt exp="check_status#ne#u&&check_status#ne#1&&check_status#ne#2" funname="addOrUpdateTaskNodeCheck(id,FINISHFLAG)" title="编辑"></t:dgFunOpt>
        <t:dgFunOpt exp="check_status#ne#u" funname="sendTaskNodeCheckAppr(id, check_status)" title="发送审核"></t:dgFunOpt>
    </t:datagrid>
    </c:if>
    <input id="tipMsg" type="hidden" value="" />
  </div>
</div>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
//录入或更新合同验收
function addOrUpdateCheck(contractNodeId,checkId){
	var title = "录入进账合同节点考核信息";
	var gname = "projectNodeCheckList";
	var width = 900;
	var height = 430;
	var dialogId = "apprInfo";
	if(checkId == ""){
		//尚未生成表单
		var url = "tPmContractNodeCheckController.do?goIncomeContractCheckUpdate&contractNodeId=" + contractNodeId;
		addFun(title, url, gname, width, height, dialogId);
	}else{
		title = "编辑进账合同节点考核信息";
		var judgeUrl = "tPmContractNodeCheckController.do?updateFlag";
		var updateUrl = "tPmContractNodeCheckController.do?goIncomeContractCheckUpdate";
		var unEditUrl = updateUrl + "&load=detail&tip=true";
		judgeUpdate(title,width,height,checkId,judgeUrl,updateUrl,unEditUrl,dialogId);
	}
}

//录入或更新任务节点验收
function addOrUpdateTaskNodeCheck(nodeId,finishFlag){
	var title = "录入任务节点验收信息";
	var gname = "projectNodeCheckList";
	var width = 900;
	var height = 430;
	var dialogId = "apprInfo";
	if(finishFlag != "1"){
		//尚未生成表单
		var url = "tPmTaskController.do?goTaskNodeApprEdit&id=" + nodeId;
		addFun(title, url, gname, width, height, dialogId);
	}else{
		title = "编辑任务节点验收信息";
		var judgeUrl = "tPmTaskController.do?updateFlag";
		var updateUrl = "tPmTaskController.do?goTaskNodeApprEdit";
		var unEditUrl = updateUrl + "&load=detail&tip=true";
		judgeUpdate(title,width,height,nodeId,judgeUrl,updateUrl,unEditUrl,dialogId);
	}
}

//审核tab页
function sendCheckAppr(checkId, finishFlag){
	var title = "审核";
	var url = 'tPmApprLogsController.do?goApprTab&type=incomeNodeCheck&edit=false' + 
			'&apprId=' + checkId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_INCOME_CONTRACT_NODECHECK%>';
	var width = 900;
	var height = 500;
	var dialogId = "apprInfo";
	
	if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("进账合同节点考核流程已完成，不能再发送审核");
	}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>') {
        url += '&tip=true';
        $("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑进账合同节点考核信息！");
    }
        sendApprDialog(title, url, width, height, checkId, finishFlag,'<%=ApprovalConstant.APPR_TYPE_INCOME_CONTRACT_NODECHECK%>');
    }
    
//审核任务节点tab页
function sendTaskNodeCheckAppr(checkId, finishFlag){
	var title = "审核";
	var url = 'tPmApprLogsController.do?goApprTab&type=taskNodeCheck&edit=false' + 
			'&apprId=' + checkId +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_TASK_NODECHECK%>';
	var width = 900;
	var height = 500;
	var dialogId = "apprInfo";
	
	if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("项目节点审核流程已完成，不能再发送审核");
	}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>') {
        url += '&tip=true';
        $("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑项目节点信息！");
    }
	    sendApprDialog(title, url, width, height, checkId, finishFlag,'<%=ApprovalConstant.APPR_TYPE_TASK_NODECHECK%>');
    }


    //查看合同信息
    function detailContract(title, url, id, width, height) {
        var rowsData = $('#' + id).datagrid('getSelections');

        if (!rowsData || rowsData.length == 0) {
            tip('请选择查看项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再查看');
            return;
        }
        url += '&load=detail&id=' + rowsData[0].in_out_contractid;
        tabDetailDialog(title, url, width, height);
    }

    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tPmIncomeContractApprListtb").find("input[name='startTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='startTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='endTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='endTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });
    
  //查看合同信息
	function detailContractView(title, url, id, width, height){
		gridname = id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择一行记录');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再查看');
			return;
		}
	    url='tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false&load=detail&id='+ rowsData[0].in_out_contractid;
		createdetailwindow('合同信息',url,width, height);
	}
</script>