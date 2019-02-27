<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" id="contentContainer" fit="true" style="height:500px;">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmIncomeContractApprList" checkbox="false" fitColumns="true" title="${projectName}-进账合同审核"
			actionUrl="tPmIncomeContractApprController.do?datagrid&project.id=${projectId}&datagridType=${datagridType}" 
			onDblClick="dblClickDetail" idField="id"  queryMode="group" sortName="startTime" sortOrder="desc" >
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" hidden="true" queryMode="group" width="200"></t:dgCol>
			<t:dgCol title="申请单位" field="applyUnit" queryMode="group" width="220"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName" query="true" queryMode="single"  width="120"></t:dgCol>
			<%-- <t:dgCol title="合同类别" field="contractType" codeDict="1,HTLB" hidden="false" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="其他合同类别" field="contractTypeContent" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="对方单位" field="approvalUnit" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同第三方" field="theThird" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="100" align="center"></t:dgCol>
			<t:dgCol title="截止时间" field="endTime" formatter="yyyy-MM-dd" queryMode="group" width="100" align="center"></t:dgCol>
			<t:dgCol title="总经费(元)" field="totalFunds" queryMode="group" width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			
			<t:dgCol title="审核状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="80" align="right"></t:dgCol>
			<t:dgCol title="上传合同正本" field="contractBookFlag" hidden="true" replace="未上传_0,已上传_1,已确认_2"  queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="完成时间" field="finishTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			
			<t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
			
			<t:dgCol title="操作" field="opt" width="180" frozenColumn="true"></t:dgCol>
			<t:dgFunOpt exp="finishFlag#eq#0" title="发送审核" funname="sendIncomeAppr(id, finishFlag)" ></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#3" title="发送审核" funname="sendIncomeAppr(id, finishFlag)" ></t:dgFunOpt>
			<t:dgDelOpt exp="finishFlag#eq#0" title="删除" url="tPmIncomeContractApprController.do?doDel&id={id}" />
			<t:dgFunOpt exp="finishFlag#eq#2" title="查看审核" funname="sendIncomeAppr(id, finishFlag)" ></t:dgFunOpt>
			<t:dgFunOpt exp="finishFlag#eq#2" title="合同正本" funname="uploadBook(id)" ></t:dgFunOpt>
			
			<t:dgToolBar title="录入" icon="icon-add" url="tPmIncomeContractApprController.do?goAddTab&projectId=${projectId}" 
				funname="addIncomeAppr" height="600" width="750"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tPmIncomeContractApprController.do?goUpdateTab" 
				funname="updateIncomeAppr" height="600" width="750"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeContractApprController.do?goUpdateTab" 
				funname="detailIncomeAppr" height="600" width="750"></t:dgToolBar>
			
		</t:datagrid>
		<input id="tipMsg" type="hidden" value=""/>
		
		</div>
	</div>
</div>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	var li_east = 0;
	//查看来款节点
	function detailIncomeNode(id){
		if(li_east == 0){
	        $('#contentContainer').layout('expand','east');
	    }
	    $('#contentContainer').layout('panel','east').panel('setTitle', "来款节点");
	    $('#incomeNodeList').panel("refresh", 'tPmIncomeNodeController.do?incomeContracToIncomeNode&contractId='+id);
	}
	
	//双击查看方法
	function dblClickDetail(rowIndex, rowData){
		var title = "查看";
		var url = "tPmIncomeContractApprController.do?goUpdateTab&load=detail&node=false&id=" + rowData.id;
		var width = 750;
		var height = window.top.document.body.offsetHeight-100;
		tabDetailDialog(title, url, width, height);
	}

	//发送进账合同审核
	function sendIncomeAppr(incomeApprId, finishFlag){
		$.ajax({
	        url : 'tPmApprLogsController.do?getIsHaveContractNode',
	        data : {"incomeApprId":incomeApprId},
	        type : 'post',
	        dataType : 'json',
	        success : function(data) {
	            if(data.success==true){
	            	var title = "审核";
	        		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
	        				'&apprId=' + incomeApprId +
	        				'&apprType=<%=ApprovalConstant.APPR_TYPE_INCOME%>';
	        		var width = 900;
	        		var height = window.top.document.body.offsetHeight-100;
	        		
	        		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
	        			url += '&send=false';
	        			//$("#tipMsg").val("进账合同审核流程已完成，不能再发送审核");
	        		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
	        			url += '&tip=true';
	        			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑进账审核信息！");
	        		}
	        		sendApprDialog(title, url, width, height, incomeApprId,finishFlag,'<%=ApprovalConstant.APPR_TYPE_INCOME%>' );
	            }else{
	            	tip(data.msg);
	            }
	        }
	    });
		
	}
	
	//更新进账合同审核状态
	function updateIncomeApprFinishFlag(incomeApprId){
		var url1 = 'tPmIncomeContractApprController.do?updateFinishFlag';
		var url2 = url1 + '2';
		updateFinishFlag(incomeApprId, url1, url2);
	}
	
	//录入进账合同
	function addIncomeAppr(title,url,gname,width,height){
		addFun(title,url,gname,width,height,'mainInfo');
	}
	
	//编辑进账合同
	function updateIncomeAppr(title,url,gname,width,height){
		var judgeUrl = 'tPmIncomeContractApprController.do?updateFlag';
		var unEditUrl = url + '&load=detail&tip=true&node=false';
		judgeAndUpdateFun(title, url, gname, width, height, judgeUrl, unEditUrl, 'mainInfo');
	}
	
	//查看进账合同
	function detailIncomeAppr(title,url,gname,width,height){
		url += '&load=detail&node=false';
		detailFun(title, url, gname, width, height, 'mainInfo');
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
	
	//上传合同正本
	function uploadBook(id,index){
		var url = "tPmIncomeContractApprController.do?tPmContractUpload&id="+id+"&inoutFlag=i";
		var width = 450;
		var height = 160;
		var title = "上传合同正本";
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				height:height,
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
		}else{
			W.$.dialog({
				content: 'url:'+url,
				lock : true,
				width:width,
				height:height,
				parent:windowapi,
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
	}
</script>