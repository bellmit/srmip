<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true" style="height:400px;">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tBAppraisalProjectList" fitColumns="true" title="${projectName }：成果鉴定计划"
			actionUrl="tBAppraisalProjectController.do?datagrid&projectId=${projectId }"
			idField="id" fit="true" queryMode="group" onClick="isEdit" onDblClick="goAppraisalTab">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group"></t:dgCol>
			<t:dgCol title="年度" field="planYear" query="true" queryMode="single" width="50"></t:dgCol>
			<t:dgCol title="成果名称" field="achievementName" query="true" isLike="true" queryMode="single" width="100"></t:dgCol>
            <t:dgCol title="承研单位" field="projectDeveloperDepartname" width="100"></t:dgCol>
			<t:dgCol title="项目负责人" field="projectManagerName" width="100"></t:dgCol>
			<t:dgCol title="鉴定主持单位" field="appraisalUnit" hidden="true" width="100"></t:dgCol>
			<t:dgCol title="鉴定时间" field="appraisalTime" formatter="yyyy-MM-dd" width="90" align="center"></t:dgCol>
			<t:dgCol title="鉴定地点" field="appraisalAddress" hidden="true" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="鉴定形式" field="appraisalForm" queryMode="group" width="100" codeDict="1,JDXS"></t:dgCol>
			<t:dgCol title="备注" field="memo" hidden="true"  queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="状态" field="state" codeDict="1,JHZT" width="100"></t:dgCol>

			<t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
			<t:dgDelOpt title="删除" exp="state#eq#0" url="tBAppraisalProjectController.do?doDel&id={id}" />
			<t:dgFunOpt	title="提交" exp="state#eq#0" funname="submit(id)"></t:dgFunOpt>
			<t:dgFunOpt	exp="state#ne#8" title="开启鉴定流程" funname="openAppraisalProcess(id)"></t:dgFunOpt>
			<t:dgFunOpt title="提交未完成说明" exp="state#eq#1" funname="openNoFinishWin(id)"></t:dgFunOpt>
			<t:dgFunOpt title="未完成说明" exp="state#eq#8" funname="openNoFinishWin(id)"></t:dgFunOpt>
			<%-- <t:dgFunOpt title="完成" exp="state#eq#1" funname="finish(id)"></t:dgFunOpt> --%>
<%-- 			<t:dgFunOpt title="鉴定申请审批表" exp="state#eq#2" funname="approval(id)"></t:dgFunOpt> --%>
<%-- 			<t:dgFunOpt title="鉴定申请表" exp="state#eq#2" funname="apply(id)"></t:dgFunOpt> --%>
		    <!--  * "0"：未发送  * "1"：已发送 * "2"：已完成 * "3"：被驳回 -->

			<t:dgToolBar title="录入" icon="icon-add" url="tBAppraisalProjectController.do?goAdd&projectId=${projectId }"
				height="450" width="650" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tBAppraisalProjectController.do?goUpdate" 
				height="450" width="650" funname="update"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tBAppraisalProjectController.do?goUpdate"
				height="450" width="650" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
		</t:datagrid>
		<input type="hidden" id="tipMsg" />
	</div>
</div>

<script src = "webpage/com/kingtake/project/appraisal/tBAppraisalProjectList.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
//双击查看详情
function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = 650;
	var height = 450;
	var url = "tBAppraisalProjectController.do?goUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

function goAppraisalTab(rowIndex, rowDate){
    var title = "成果鉴定";
	if(typeof(windowapi)=='defined'){
	    $.dialog({
			id:'apprInfo',
			content: 'url:tBAppraisalProjectController.do?goAppraisalTab&id=' + rowDate.id,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
	 		left : '0%',
	 		top : '0%',
			title:"成果鉴定",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		}).zindex();
	}else{
	 W.$.dialog({
		id:'apprInfo',
		content: 'url:tBAppraisalProjectController.do?goAppraisalTab&id=' + rowDate.id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
 		left : '0%',
 		top : '0%',
 		parent:windowapi,
		title:"成果鉴定",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    }
	   }).zindex();
	}
}

function openAppraisalProcess(id,rowIndex){
    var title = "成果鉴定";
	if(typeof(windowapi)=='defined'){
	    $.dialog({
			id:'apprInfo',
			content: 'url:tBAppraisalProjectController.do?goAppraisalTab&id=' + id,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
	 		left : '0%',
	 		top : '0%',
			title:"成果鉴定",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		        var win = W.$.dialog.list['processDialog'].content;
		        win.reloadTable();
		    }
		}).zindex();
	}else{
	 W.$.dialog({
		id:'apprInfo',
		content: 'url:tBAppraisalProjectController.do?goAppraisalTab&id=' + id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
 		left : '0%',
 		top : '0%',
 		parent:windowapi,
		title:"成果鉴定",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	        var win = W.$.dialog.list['processDialog'].content;
	        win.reloadTable();
	    }
	   }).zindex();
	}
}

// 判断当前是否可编辑
function isEdit(index, row){
	if(row.state == "0"){
		$("#tBAppraisalProjectListtb").find("a[icon='icon-edit']").show();
	}else{
		$("#tBAppraisalProjectListtb").find("a[icon='icon-edit']").hide();
	}
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tBAppraisalProjectListtb").find("input[name='planYear']")
		.attr("class","Wdate").attr("style","height:20px;width:90px;")
		.click(function(){WdatePicker({dateFmt:'yyyy'});});
});

//导出
function ExportXls() {
	JeecgExcelExport("tBAppraisalProjectController.do?exportXls&projectId=${projectId }", "tBAppraisalProjectList");
}

/**
 * 提交鉴定计划给机关
 */
function submit(id){
	$.messager.confirm('确认','您确认将鉴定计划提交吗？',function(r){    
	    if (r){    
	        $.ajax({
	        	type : 'post',
	        	data : {id : id},
	        	url : "tBAppraisalProjectController.do?doSubmit",
	        	success : function(result){
	        		result = $.parseJSON(result);
	        		$('#tBAppraisalProjectList').datagrid('reload');    
	        		showMsg(result.msg);
	        	}
	        }); 
	    }    
	});  
}


/**
 * 完成鉴定计划
 */
function finish(id){
	$.messager.confirm('确认','确认完成鉴定计划吗？',function(r){    
	    if (r){    
	        $.ajax({
	        	type : 'post',
	        	data : {id : id},
	        	url : "tBAppraisalProjectController.do?finish",
	        	success : function(result){
	        		result = $.parseJSON(result);
	        		reloadTable();   
	        		showMsg(result.msg);
	        	}
	        }); 
	    }    
	});  
}

/**
 * 未完成鉴定计划
 */
function openNoFinishWin(id,index){
    if(typeof(windowapi)=='undefined'){
        $.dialog({
    	    lock: true,
    	    background: '#000', /* 背景色 */
    	    opacity: 0.5,       /* 透明度 */
    	    title: '未完成鉴定计划情况说明',
    	    width : 700,
    	    height : 400,
    	    content: 'url:tBAppraisalNoFinishController.do?goUpdate&tbId='+id
    	}); 
    }else{
        W.$.dialog({
    	    lock: true,
    	    background: '#000', /* 背景色 */
    	    opacity: 0.5,       /* 透明度 */
    	    parent:windowapi,
    	    title: '未完成鉴定计划情况说明',
    	    width : 700,
    	    height : 400,
    	    content: 'url:tBAppraisalNoFinishController.do?goUpdate&tbId='+id
    	}); 
    }
	
}


function openApplyWin(id){
	$.dialog({
	    lock: true,
	    background: '#000', /* 背景色 */
	    opacity: 0.5,       /* 透明度 */
	    width:'620px',
	    height:window.top.document.body.offsetHeight-100,
	    title: '鉴定申请表',
	    content: 'url:tBAppraisalApplyController.do?goUpdate&appraisalProject.id='+id
	});
}


function reloadTable(){
	$('#tBAppraisalProjectList').datagrid('reload'); 
}

function goApproval(id){
	
}

//申请审批表
function approval(id){
	var send = '<%=ApprovalConstant.APPRSTATUS_SEND%>';
	var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
	var rebut = '<%=ApprovalConstant.APPRSTATUS_REBUT%>';
	
	var judgeUrl = 'tBAppraisaApprovalController.do?getOperateStatus&appraisalProject.id='+id;
	
	var title = "鉴定申请审批表";
	//var url = "tBAppraisaApprovalController.do?goApproval&appraisalProject.id="+id;
	var url = 'tPmApprLogsController.do?goApprTab' + 
				'&appraisalProjectId=' + id +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_APPRAISAL%>';
	var width = 900;
	var height = '100%';
	var dialogId = 'apprInfo';
	var updateStatusUrl = "tBAppraisaApprovalController.do?updateFinishFlag"
	
	judgeUpdate2(
			send,finish,rebut,
			judgeUrl,
			title,url,width,height,dialogId,
			updateStatusUrl);
}

//鉴定申请表
function apply(id){
	var send = '<%=ApprovalConstant.APPRSTATUS_SEND%>';
	var finish = '<%=ApprovalConstant.APPRSTATUS_FINISH%>';
	var rebut = '<%=ApprovalConstant.APPRSTATUS_REBUT%>';
	
	var judgeUrl = 'tBAppraisalApplyController.do?getOperateStatus&appraisalProject.id='+id;
	
	var title = "鉴定申请表";
	//var url = "tBAppraisalApplyController.do?goUpdate&appraisalProject.id="+id;
	var url = 'tPmApprLogsController.do?goApprTab' + 
				'&appraisalProjectId=' + id +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_APPRAISAL_APPLY%>';
	var width = 900;
	var height = '100%';
	var dialogId = 'apprInfo';
	var updateStatusUrl = "tBAppraisalApplyController.do?updateFinishFlag"
	
	judgeUpdate2(
			send,finish,rebut,
			judgeUrl,
			title,url,width,height,dialogId,
			updateStatusUrl);
}
</script>