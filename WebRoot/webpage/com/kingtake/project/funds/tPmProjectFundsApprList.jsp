<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="com.kingtake.common.constant.ProjectConstant" 
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="planContractFlag" value="${planContractFlag }" type="hidden" />
<input id="constantFlag" value="${PLANFLAG }" type="hidden" />
<input id="projectId" value="${projectId}" type="hidden" />

<%--<div class="easyui-layout" fit="false" id="main_funds_list" style="height:400px;">--%>
	<%--<div region="center" style="padding:1px;">--%>
<style>
	.a{
		background-color:#41a2d5;-moz-border-radius:5px;-webkit-border-radius:5px;color:#fff; border-radius:5px;
		min-width:30px;min-height:20px;padding:1px 6px;border:0px solid #a8d7e9;float:left;
		cursor:pointer;font-size:14px;text-decoration:none;margin:0px 2px;}
	.a:hover{background-color:#FF7E00;}
</style>
<div style="height:30px;z-index: 10000;padding:5px;" >
	<a href="#" onclick="subimtAudit()" 
		style="float:right;margin-right:50px;width:60px;padding:2px 10px;" class="a">发送审核</a>
</div>	
<script>
function subimtAudit(){
	
	 var data = {};
	 //总预算
	 var  rows = $("#tPmProjectFundsApprList").datagrid("getRows");
	 if(rows.length>0){
		 for(var i=0; i<rows.length; i++){
			 var r = rows[i];
			 if(r.finishFlag=="0"){
				 data.total_funds_id = r.id;
				 data.total_funds = r.totalFunds;
				 data.t_p_id = r.tpId;
				 break;
			 }
		 }
	 }
	 //年度预算
	 rows = $("#tPmProjectYearFundsApprList").datagrid("getRows"); 
	 if(rows.length>0){
		 for(var i=0; i<rows.length; i++){
			 var r = rows[i];
			 if(r.finishFlag == "9"){//新增了年度预算，但是没有进行编制
				 $.messager.alert("提示信息","年度预算还有未进行编制的记录！");
				 return false;
			 }
			 if(r.finishFlag == "0"){
				 data.year_funds_id = r.id;
				 data.year_funds = r.totalFunds;
				 data.t_p_id = r.tpId;
				 break;
			 }
		 }
	 }
	 
	 if(!data.total_funds_id && !data.year_funds_id){
		 $.messager.alert("提示信息","没有未发送审核的记录！");
		 return false;
	 }
	 
	 var url = "tPmApprLogsController.do?goApprSend&apprId=1&apprType=24";
	 setTimeout(function(){
		 //frameElement.api.close();
	 },1000);
	 var that = this;
	 createwindow("发送审核",url,520,260,function(iframe){
		 //iframe = that.iframe.contentWindow;
     	data.recive_userIds = iframe.$("#receiveUseIds").val();
     	data.recive_userNames = iframe.$("#receiveUseNames").val();
     	data.submit_msg = iframe.$("#senderTip").val();
     	try{
     		 $.ajax({
     			 url : 'tPmProjectFundsApprController.do?submitFundsAppr',
     		        data : data,
     		        type : 'post',
     		        dataType : 'json',
     		        success : function(data) {
     		        	$.messager.alert("提示信息",data.msg);
     		        	tPmProjectFundsApprListsearch();
     		        	tPmProjectYearFundsApprListsearch();
     		        }
     		 });
     	}catch(e){}
     	
     	tPmProjectFundsApprListsearch();
     	tPmProjectYearFundsApprListsearch();
			try{
				var apprInfo = window.$.dialog.list['apprSend'];
				apprInfo.close();
			}catch(e){}
				
			return false;
	 }); 
} 

</script>
	
	
<div style="width: 100%;">
		<t:datagrid name="tPmProjectFundsApprList" checkbox="false" fitColumns="false" pagination="false" 
			actionUrl="tPmProjectFundsApprController.do?datagrid&projectId=${projectId}&datagridType=${datagridType}&&fundsCategory=1" 
			idField="id" fit="false" queryMode="group" onDblClick="dblDetail">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目_主键" field="tpId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目的主要内容及技术指标" field="content" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="承研单位审核意见" field="developerAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="责任单位审核意见" field="dutyAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="科研部计划处审核意见" field="researchAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="校务部财务处审核意见" field="financeAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="科研部审核意见" field="developerApprovalOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			
			<%-- 
			<c:if test="${planContractFlag != PLANFLAG}">
				<t:dgCol title="到账凭证号" field="voucherNum" query="true" queryMode="single" width="120"></t:dgCol>
				<t:dgCol title="发票号" field="invoiceNum" query="true" queryMode="single" width="120"></t:dgCol>
			</c:if>
			--%>
		
			<t:dgCol title="预算类别" field="fundsCategory" width="100" replace="总预算_1,年度预算_2" codeDict="1,FUNDS_CATEGORY" align="center"></t:dgCol>	
			<t:dgCol title="预算类型" field="fundsType" width="100" replace="零基预算_5,开支计划_6,调整预算_7" codeDict="1,FUNSTYPE" align="center"></t:dgCol>
			<t:dgCol title="预算金额" field="totalFunds" queryMode="group" width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<%-- 
			<t:dgCol title="总经费" field="allFee" queryMode="group" width="150" align="right" 
				extendParams="formatter:formatCurrency,"></t:dgCol>
			<c:if test="${planContractFlag != PLANFLAG}">
			<t:dgCol title="到账经费" field="accountFunds" queryMode="group" width="80" align="right" 
				extendParams="formatter:formatCurrency,"></t:dgCol>
			</c:if>
			<c:if test="${planContractFlag == PLANFLAG}">
			<t:dgCol title="年度经费" field="yearFundsPlan" queryMode="group" width="80" align="right" hidden="true"
				extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="计划下达经费" field="incomeplanAmount" queryMode="group" width="100" align="right"
				extendParams="formatter:formatCurrency,"></t:dgCol>
			</c:if>
			<t:dgCol title="归垫经费" field="reinFundsPlan" queryMode="group" width="80" align="right" 
				extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="是否预算变更" field="changeFlag" width="120" codeDict="0,SFBZ" hidden="true" align="center"></t:dgCol>
				--%>
			
			<t:dgCol title="科研审核状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="100" align="center"></t:dgCol>
				<t:dgCol title="财务审核状态" field="cwStatus" replace="未审核_0,通过_1,不通过_2" queryMode="group" width="100" align="center"></t:dgCol>
		    <t:dgCol title="科目代码" field="accountingCode" width="100"  align="center"></t:dgCol>	
			<t:dgCol title="编制时间" field="createDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="120" align="center"></t:dgCol>
			
			
			<%--   
			<t:dgCol title="操作" field="opt" width="100" frozenColumn="true">
				<t:dgFunOpt exp="finishFlag#eq#0" funname="doDelAppr(id)" title="删除"></t:dgFunOpt>
			</t:dgCol>
			
			
			<t:dgFunOpt funname="goFundsTab(id, changeFlag)" title="编制预算明细"></t:dgFunOpt>
			--%>
			<%-- <t:dgFunOpt title="发送审核" funname="sendFundAppr(id, finishFlag)" ></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#1" title="完成" funname="updateFundApprFinishFlag(id)" ></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#2" title="取消完成" funname="updateFundApprFinishFlag(id)" ></t:dgFunOpt> --%>
		
			
			<t:dgToolBar onclick="addFouds()"
				title="录入总预算" icon="icon-add"  width="800" height="200"  funname="add"></t:dgToolBar>
<%-- 			<t:dgToolBar url="tPmProjectFundsApprController.do?goAddOrUpdate&projectId=${projectId}&changeFlag=1" --%>
<%-- 				title="录入预算变更" icon="icon-add" funname="add" width="800" height="100%"></t:dgToolBar> --%>
			<t:dgToolBar onclick="editFouds()" 
			      title="编辑预算" icon="icon-edit" width="1024" height="600"  funname="add"></t:dgToolBar>
			<%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="tPmProjectFundsApprController.do?doBatchDel&projectId=${projectId}" 
				funname="deleteALLSelect"></t:dgToolBar> --%>
				
			<t:dgToolBar title="查看预算" icon="icon-search" url="tPmProjectFundsApprController.do?goAddOrUpdate" 
				funname="detail" width="1024" height="500"></t:dgToolBar>
			
			<t:dgToolBar title="删除预算"  onclick="doDelAppr(id)" icon="icon-remove"
				funname="detail" width="1024" height="500"></t:dgToolBar>
			<%-- 
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			--%>
		</t:datagrid>
		
		<input id="tipMsg" type="hidden" value=""/>
	<%--</div>--%>
</div>

<div style="width: 100%;height: 300px;">
		<t:datagrid name="tPmProjectYearFundsApprList" checkbox="false" fitColumns="true" pagination="false" 
			actionUrl="tPmProjectFundsApprController.do?datagrid&projectId=${projectId}&fundsCategory=2&datagridType=0" 
			idField="id" fit="false" queryMode="group" onDblClick="toYearFundsTotalDetail"><!-- onDblClick="toYearFundsTotalDetail" -->
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目_主键" field="tpId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目的主要内容及技术指标" field="content" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="承研单位审核意见" field="developerAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="责任单位审核意见" field="dutyAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="科研部计划处审核意见" field="researchAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="校务部财务处审核意见" field="financeAuditOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="科研部审核意见" field="developerApprovalOpinion" hidden="true" queryMode="group" width="120"></t:dgCol>
			
			<t:dgCol title="预算类别" field="fundsCategory" width="100" replace="总预算_1,年度预算_2" codeDict="1,FUNDS_CATEGORY" align="center"></t:dgCol>	
			<t:dgCol title="预算类型" field="fundsType" width="80" replace="零基预算_5,开支计划_6,调整预算_7" codeDict="1,FUNSTYPE" align="center"></t:dgCol>
			<t:dgCol title="预算金额" field="totalFunds" queryMode="group" width="120" align="right"
				extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="是否预算变更" field="changeFlag" width="120" codeDict="0,SFBZ" hidden="true" align="center"></t:dgCol>
			<t:dgCol title="科研审核状态" field="finishFlag" replace="未发送_0,已发送_1,已完成_2,被驳回_3,待编制_9" queryMode="group" width="100" align="center"></t:dgCol>
			<t:dgCol title="财务审核状态" field="cwStatus" replace="未审核_0,通过_1,不通过_2" queryMode="group" width="100" align="center"></t:dgCol>
			<t:dgCol title="编制时间" field="createDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="120" align="center"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
			<t:dgFunOpt exp="finishFlag#eq#9" funname="goYearFundsUpdate(id,totalFunds)" title="编辑"></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#9" funname="doYearFundsDelAppr(id)" title="删除"></t:dgFunOpt>
			
			<t:dgToolBar url="tPmProjectFundsApprController.do?checkYearYsType&projectId=${projectId}" 
				title="录入年度预算" icon="icon-add" funname="addYearFun" width="800" height="200" ></t:dgToolBar>
				<t:dgToolBar title="查看预算" icon="icon-search" url="tPmProjectFundsApprController.do?goYearFundsUpdate" 
				funname="detail" width="800" height="658"></t:dgToolBar>
			
		</t:datagrid>
		<input id="YearFundtipMsg" type="hidden" value=""/>
</div>
 
 
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>		
<script type="text/javascript">
var planContractFlag="${planContractFlag}";

//双击查看详情
function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = 800;
	var height = 658;
	var url = "tPmProjectFundsApprController.do?goAddOrUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

//双击查看
function toYearFundsTotalDetail(rowIndex, rowDate){
	var updateUrl = "tPmProjectFundsApprController.do?goYearFundsUpdate&load=detail&id="+rowDate.id + "&totalFunds=" + rowDate.totalFunds;;
	createdetailwindow("查看", updateUrl, 800, 658);
}


function addFouds(){
	$.ajax({
        url : 'tPmProjectFundsApprController.do?checkBudget',
        data : {"projectId":"${projectId }"},
        type : 'post',
        dataType : 'json',
        success : function(data) {
        	
        	if(!data.checkpass){
        		 $.messager.alert('提示', data.msg);
	              $.Hidemsg();
        	}else{ 
        		createdetailwindow("录入预算","tPmProjectFundsApprController.do?goAddOrUpdate&projectId=${projectId}&changeFlag=0&type=1&operType="+data.fundsType,950,600);
        	}
        }
    });
}

function addYearFun(title,addurl,gname,width,height){
	 var rows = $("#tPmProjectYearFundsApprList").datagrid("getRows"); 
	 if(rows.length>0){
		 for(var i=0; i<rows.length; i++){
			 var r = rows[i];
			 var cwStatus = r.cwStatus || "-1";
			 if(cwStatus != "1"){//新增了年度预算，但是没有进行编制
				 $.messager.alert("提示信息","年度预算还有未完成编制、审核！");
				 return false;
			 }
		 }
	 }
	
	add(title,addurl,gname,width,height);
}

function editFouds(){
	
	var row = $('#tPmProjectFundsApprList').datagrid('getSelected');
	
	var foundsType = row.fundsType;
	var id = row.id;
	if(foundsType == '2'){
		tip("年度总预算不可修改，请修改年度预算");
		return false;
	}
	createdetailwindow("编辑预算","tPmProjectFundsApprController.do?goAddOrUpdate&id="+id,950,600);
}

//录入预算之前进行判断
function addFun(title,addurl,gname,width,height){
	/* var total = $('#tPmProjectFundsApprList').datagrid('getData').total;
	if(total > 0){
		add(title,addurl,gname,width,height);
	}else{
	  // 到后台获得项目的会计编码
	  $.ajax({
	    url : 'tPmProjectFundsApprController.do?checkFunds',
	    type : 'post',
	    data : {id : $("#projectId").val()},
	    success : function(result){
	      result = $.parseJSON(result);
	      if( !result.success ){
	        $.dialog.alert(result.msg);
	      }else{
	        add(title,addurl,gname,width,height);
	      }
	    }
	  });
	} */
	add(title,addurl,gname,width,height);
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
	
	 //给时间控件加上样式
	$("#tPmProjectYearFundsApprListtb").find("input[name='createDate_begin']")
		.attr("class", "Wdate")
		.attr("style", "height:20px;width:120px;")
		.click(function(){
			WdatePicker({dateFmt : 'yyyy-MM-dd'});
		});
	$("#tPmProjectYearFundsApprListtb").find("input[name='createDate_end']")
		.attr("class", "Wdate")
		.attr("style", "height:20px;width:120px;")
		.click(function() {
			WdatePicker({dateFmt : 'yyyy-MM-dd'});
	});

	setTimeout(function(){
		$("#tPmProjectFundsApprList").datagrid({
				width:$(window).width()-30,
				height:200
		}); 
		$("#tPmProjectYearFundsApprList").datagrid({
			width:$(window).width()-30,
			height:300
	});
	},200);
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
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[0]).show();
			}

			if(result.noFinish == 0 && result.finish > 0){
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[1]).show();
			}else{
				$($("#tPmProjectFundsApprListtb").find("a[icon='icon-add']")[1]).show();
			}
			
			
		}
	});
}

function doDelAppr(id){
	var row = $('#tPmProjectFundsApprList').datagrid('getSelected');
	var id = row.id;
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
	
	tabDetailDialog(title, url, width, height, dialogId);
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

$(document).ready(function(){
	//设置datagrid的title
	//var parentWin = window.parent;
	var title = title = '${projectName}'+'-1项目预算管理（单位：元）';
	/* if(parentWin){
		var projectName = window.parent.getParameter();
		title = projectName+'-项目预算管理（单位：元）';
	} */
	
	$("#tPmProjectFundsApprList").datagrid({
		title:title
	});
	 
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
			openwindow(title, updateUrl, 'tPmProjectFundsApprList', 1350, 500);
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

/**
 * 进入年度预算编制页面
 */
function goYearFundsUpdate(id,totalFunds){
	var title = "年度预算编制";
	var updateUrl = "tPmProjectFundsApprController.do?goYearFundsUpdate&isEdit=1&id=" + id + "&totalFunds=" + totalFunds;
	createdetailwindow(title, updateUrl, 1350, 500);
}

function doYearFundsDelAppr(id){
	$.messager.confirm('确认', '确认删除该条记录吗？', function(r){
		if(r){
			$.ajax({
				url : 'tPmProjectFundsApprController.do?doDel',
				type : 'post',
				data : {id : id},
				success : function(result){
					reloadTable();
					result = $.parseJSON(result);
					showMsg(result.msg);
				}
			});
		}
	});
}


 </script>