<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="com.kingtake.common.constant.ProjectConstant" 
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="planContractFlag" value="${planContractFlag }" type="hidden" />
<input id="constantFlag" value="${PLANFLAG }" type="hidden" />
<input id="projectId" value="${projectId}" type="hidden" />

<div class="easyui-layout" fit="true" id="main_funds_list" style="height:400px;">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmProjectFundsApprList" checkbox="true" fitColumns="true" pagination="false"
			actionUrl="tPmProjectFundsApprController.do?datagrid&projectId=${projectId}&datagridType=${datagridType}" 
			idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目_主键" field="tpId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目的主要内容及技术指标" field="content" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="承研单位审核意见" field="developerAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="责任单位审核意见" field="dutyAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="科研部计划处审核意见" field="researchAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="校务部财务处审核意见" field="financeAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="科研部审核意见" field="developerApprovalOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			
			<c:if test="${planContractFlag != PLANFLAG}">
				<t:dgCol title="到账凭证号" field="voucherNum" query="true" queryMode="single" width="120"></t:dgCol>
				<t:dgCol title="发票号" field="invoiceNum" query="true" queryMode="single" width="120"></t:dgCol>
			</c:if>
			<t:dgCol title="编制时间" field="createDate" formatter="yyyy-MM-dd" query="true"
				queryMode="group" width="60" align="center"></t:dgCol>
			<t:dgCol title="总经费" field="allFee" queryMode="group" width="60" align="right" 
				extendParams="formatter:formatCurrency,"></t:dgCol>
		    <t:dgCol title="预算金额" field="totalFunds" queryMode="group" width="60" align="right"
				extendParams="formatter:formatCurrency,"></t:dgCol>
			<c:if test="${planContractFlag != PLANFLAG}">
			<t:dgCol title="到账经费" field="accountFunds" queryMode="group" width="60" align="right" 
				extendParams="formatter:formatCurrency,"></t:dgCol>
			</c:if>
			<c:if test="${planContractFlag == PLANFLAG}">
			<t:dgCol title="年度经费" field="yearFundsPlan" queryMode="group" width="80" align="right" hidden="true"
				extendParams="formatter:formatCurrency,"></t:dgCol>
		    <t:dgCol title="计划下达经费" field="incomeplanAmount" queryMode="group" width="80" align="right"
				extendParams="formatter:formatCurrency,"></t:dgCol>
			</c:if>
			<t:dgCol title="归垫经费" field="reinFundsPlan" queryMode="group" width="80" align="right" 
				extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="预算类型" field="fundsType" width="50" codeDict="1,FUNSTYPE" align="center"></t:dgCol>
			<t:dgCol title="是否预算变更" field="changeFlag" width="40" codeDict="0,SFBZ" align="center"></t:dgCol>
			<t:dgCol title="审核状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="100" align="center"></t:dgCol>
			
			<t:dgCol title="操作" field="opt" width="250" frozenColumn="true"></t:dgCol>
			<t:dgFunOpt funname="goFundsTab(id, changeFlag)" title="编制预算明细"></t:dgFunOpt>
			<t:dgFunOpt title="发送审核" funname="sendFundAppr(id, finishFlag)" ></t:dgFunOpt>
			<t:dgToolBar title="查看" icon="icon-search" url="tPmProjectFundsApprController.do?goAddOrUpdate" 
				funname="detail" width="800" height="100%"></t:dgToolBar>
			<t:dgToolBar title="预算编制" icon="icon-search" url="tPmProjectFundsApprController.do?tPmProjectFundsAppr&projectId=${id}" 
				funname="openBudgetManager" width="850" height="560"></t:dgToolBar>
		</t:datagrid>
		
		<input id="tipMsg" type="hidden" value=""/>
	</div>
</div>
 
 
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>		
<script type="text/javascript">
var planContractFlag=${planContractFlag} + "";

//预算管理
function openBudgetManager(title, url,id,width,height){
	createdetailwindow(title,url,width,height);
}

//双击查看详情
function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = 700;
	var height = 658;
	var url = "tPmProjectFundsApprController.do?goAddOrUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

//录入预算之前进行判断
function addFun(title,addurl,gname,width,height){
	var total = $('#tPmProjectFundsApprList').datagrid('getData').total;
	if(total > 0){
		add(title,addurl,gname,width,height);
	}else{
	  // 到后台获得项目的会计编码
	  $.ajax({
	    url : 'tPmProjectController.do?findInfoById',
	    type : 'post',
	    data : {Id : $("#projectId").val()},
	    success : function(result){
	      result = $.parseJSON(result);
	      if( result.attributes.accountingCode == null ){
	        $.dialog.alert('请在项目基本信息中完善会计编码后，再进行录入预算操作！');
	      }else{
	        add(title,addurl,gname,width,height);
	      }
	    }
	  });
	}
}

$(document).ready(function(){
	 //给时间控件加上样式
	$("#tPmProjectFundsApprListtb").find("input[name='createDate_begin']")
		.attr("class", "Wdate")
		.attr("style", "height:20px;width:120px;")
		.click(function(){
			WdatePicker({dateFmt : 'yyyy-MM-dd'});
		});
	$("#tPmProjectFundsApprListtb").find("input[name='createDate_end']")
		.attr("class", "Wdate")
		.attr("style", "height:20px;width:120px;")
		.click(function() {
			WdatePicker({dateFmt : 'yyyy-MM-dd'});
	});
	
	//设置datagrid的title
	$("#tPmProjectFundsApprList").datagrid({
		title:'${projectName}-项目预算管理（单位：元）'
	});
	 
	//给时间控件加上样式
	$("#tPmProjectFundsApprListtb").find("input[name='startYear_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy'});});
	$("#tPmProjectFundsApprListtb").find("input[name='startYear_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy'});});
	
	loadButton();
	
});

/**
 * 加载按钮：录入预算，录入预算变更
 */
function loadButton(){
	// 到后台获得未审核完成的预算条数
	$.ajax({
		url : 'tPmProjectFundsApprController.do?getCount',
		type : 'post',
		data : {projectId : $("#projectId").val()},
		success : function(result){
			result = $.parseJSON(result);
			if(result.noFinish == 0){
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[0]).show();
			}else{
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[0]).hide();
			}

			if(result.noFinish == 0 && result.finish > 0){
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[1]).show();
			}else{
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[1]).hide();
			}
			
			
		}
	});
}

function doDelAppr(id){
	$.messager.confirm('确认', '确认删除该条记录吗？', function(r){
		if(r){
			$.ajax({
				url : 'tPmProjectFundsApprController.do?doDel',
				type : 'post',
				data : {id : id},
				success : function(result){
					loadButton();
					reloadTable();
					result = $.parseJSON(result);
					showMsg(result.msg);
				}
			});
		}
	})
}

//审核tab页
function sendFundAppr(fundApprId, finishFlag){
	var title = "审核";
	var url = 'tPmApprLogsController.do?goApprTab' + 
			'&apprId=' + fundApprId +
			"&planContractFlag="+planContractFlag +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_FUNDS%>';
	var width = 900;
	var height = '100%';
	var dialogId = "apprInfo";
	
	if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("预算审核流程已完成，不能再发送审核");
	}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
		url += '&tip=true';
		$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑预算信息！");
	}
	
	sendApprDialog(title, url, width, height, fundApprId, finishFlag, '<%=ApprovalConstant.APPR_TYPE_FUNDS%>');
}

//更新预算审核状态
function updateFundApprFinishFlag(fundApprId){
	var url1 = 'tPmProjectFundsApprController.do?updateFinishFlag';
	var url2 = url1 + '2';
	updateFinishFlag(fundApprId, url1, url2);
}

/**
 * 判断审核信息是否可编辑
 * 如果可编辑，进入编辑页面
 * 不可编辑则提示用户
 */
function updateFundAppr(title, url, gname, width, height){
	var judgeUrl = 'tPmProjectFundsApprController.do?updateFlag';
	var unEditUrl = url + '&load=detail&tip=true';
	judgeAndUpdateFun(title, url, gname, width, height, judgeUrl, unEditUrl, 'addFundInfoIframe');
}

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmProjectFundsApprController.do?upload', "tPmProjectFundsApprList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmProjectFundsApprController.do?exportXls&tpId="+$("#projectId").val(),
			"tPmProjectFundsApprList");
}

/**
 * 进入预算详情页面
 */
function goFundsTab(fundsId, changeFlag){
	var title = "项目预算";
	if(changeFlag == "1"){
		title = "项目预算变更";
	}
	var updateUrl = "tPmProjectFundsApprController.do?goFundsTab&tpId="+fundsId+
			"&planContractFlag="+planContractFlag+"&changeFlag="+changeFlag;
	//判断是否可编辑
	$.ajax({
		type : 'POST',
		url : 'tPmProjectFundsApprController.do?updateFlag',
		data : {"id":fundsId},
		success : function(data) {
			var d = $.parseJSON(data);
			if (!d.success) {
				//不可编辑
				$("#tipMsg").val(d.msg);
				updateUrl += "&tip=true&edit=false";
			}
			openWindowById('fundsDialog',title, updateUrl, 'tPmProjectFundsApprList', 1350, 500);
		}
	});
}

function goFundsBillOne(fundsId) {
	//附表类型标记
	var width = 800;
	var height = 400;
	var title = '合同类项目经费预算表一';
	var url = "";
	gridname=fundsId;
	if(planContractFlag == <%=ProjectConstant.PROJECT_CONTRACT%>) {
		url = "tPmContractFundsController.do?tPmContractFunds&tpId="+fundsId+"&tableFlag=c1";
	}else if(planContractFlag == <%=ProjectConstant.PROJECT_PLAN%>) {
		url = "tPmPlanFundsOneController.do?tPmPlanFundsOne&tpId="+fundsId+"&tableFlag=p1";
	}
	openwindow(title,url,gridname,width,height);
}

function goFundsBillTwo(fundsId) {
 	//附表类型标记
	var width = 800;
	var height = 400;
	var title = '合同类项目经费预算表二';
	var url = "";
	gridname=fundsId;
	url = "tPmContractFundsController.do?tPmContractFunds&tpId="+fundsId+"&tableFlag=p2";
	openwindow(title,url,gridname,width,height);
}

function goFundsBillAddum(fundsId) {
 	//附表类型标记
	var width = 850;
	var height = 400;
	var title1 = '合同类项目经费预算附表';
	var title2 = '计划类项目经费预算附表';
	var url = "";
	gridname=fundsId;
	if(planContractFlag == <%=ProjectConstant.PROJECT_CONTRACT%>) {
		url = "tPmFundsBudgetAddendumController.do?tPmFundsBudgetAddendum&tpId="+fundsId+"&tableFlag=c3";
		openwindow(title1,url,gridname,width,height);
	} else if(planContractFlag == <%=ProjectConstant.PROJECT_PLAN%>) {
		url = "tPmFundsBudgetAddendumController.do?tPmFundsBudgetAddendum&tpId="+fundsId+"&tableFlag=p3";
		openwindow(title2,url,gridname,width,height);
	}
}

/**
 * 打开预算变更页面
 */
function change(title, url, gridname){
	// 判断已有预算的总数
	$.ajax({
		url : 'tPmProjectFundsApprController.do?getCounts',
		data : {projectId: $("#projectId").val()},
		type : 'post',
		success : function(result){
			result = $.parseJSON(result);
			if(result.success){
				$.ajax({
					url : url,
					type : 'post',
					success : function(result){
						$("#"+gridname).datagrid('reload');
						goFundsTab(result, "1");
					}
				});
			}else{
				showMsg(result.msg);
			}
		}
	});
}

function showMsg(msg){
	$.messager.show({
		title:'提示信息',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}
 </script>