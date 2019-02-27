<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
 <div region="center" style="padding:1px;">
 <t:datagrid name="tBAppraisalApplyList" checkbox="false" fitColumns="false" 
 	actionUrl="tBAppraisalApplyController.do?datagridForDepart&type=${type}" 
 	idField="id" fit="true" queryMode="group" onDblClick="dbClick">
  <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
  <t:dgCol title="鉴定计划主键" field="appraisalProject_id" hidden="true" queryMode="single" width="80"></t:dgCol>
  <t:dgCol title="项目名称" field="appraisalProject_projectName" query="true" queryMode="single" width="120"></t:dgCol>
  <t:dgCol title="成果名称" field="appraisalProject_achievementName" query="true" queryMode="single" width="120"></t:dgCol>
  <t:dgCol title="成果完成单位" field="finishUnit" queryMode="single" width="120"></t:dgCol>
  <t:dgCol title="工作开始时间" field="beginTime" hidden="true" queryMode="single" width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
  <t:dgCol title="工作截止时间" field="endTime" hidden="true" queryMode="group" width="90" formatter="yyyy-MM-dd" align="center"></t:dgCol>
  <t:dgCol title="归档号" field="archiveNum" hidden="true" queryMode="group" width="80"></t:dgCol>
  <t:dgCol title="成果完成单位联系人" field="finishContactName" queryMode="group" width="80"> </t:dgCol>
  <t:dgCol title="联系人电话" field="finishContactPhone" hidden="true" queryMode="group" width="80" align="right"> </t:dgCol>
  <t:dgCol title="主持鉴定单位联系人" field="appraisalContactName" queryMode="group" width="80"></t:dgCol>
  <t:dgCol title="联系人电话" field="appraisalContactPhone" hidden="true" queryMode="group" width="80" ></t:dgCol>
  <%-- <t:dgCol title="登记编号" field="registerCode" queryMode="group" width="80" ></t:dgCol> --%>
  <t:dgCol title="成果类别" field="resultType" queryMode="group" width="80" codeDict="1,CGLB"></t:dgCol>
  <t:dgCol title="鉴定形式" field="appraisalForm" queryMode="group" width="80" codeDict="1,JDXS"></t:dgCol>
  <t:dgCol title="鉴定时间" field="appraisalTime" queryMode="group" width="90" formatter="yyyy-MM-dd"></t:dgCol>
  <t:dgCol title="鉴定地点" field="appraisalAddress" queryMode="group" width="100" ></t:dgCol>
  <t:dgCol title="审查状态" field="auditStatus" codeDict="1,CGSCZT" queryMode="group" width="100" ></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
  <t:dgFunOpt exp="auditStatus#eq#1" title="审查" funname="audit(id)" ></t:dgFunOpt>
  <t:dgFunOpt exp="auditStatus#eq#2" title="填写批复意见" funname="doFinish(id)" ></t:dgFunOpt>
  <t:dgFunOpt exp="auditStatus#eq#3" title="查看批复意见" funname="view(id)" ></t:dgFunOpt>
 </t:datagrid>
 <input id="tipMsg" type="hidden" value=""/>
 </div>
 </div>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>		
<script type="text/javascript">
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
	
	function dbClick(rowIndex,rowData){
		$.dialog({
			content : 'url:tBAppraisalApplyController.do?goApplyForDepart&load=detail&id='+rowData.id,
			title : '鉴定申请审查',
			lock : true,
			opacity : 0.3,
			width : '700px',
			height : '400px',
			cancel:function(){
	
			},
		});
	}
	
	function audit(id,index){
	  	$.dialog({
							content : 'url:tBAppraisalApplyController.do?goApplyForDepart&load=detail&id='+id,
							title : '鉴定申请审查',
							lock : true,
							opacity : 0.3,
							width : '700px',
							height : '400px',
							okVal:'通过',
							ok:function(){
								iframe = this.iframe.contentWindow;
								$.ajax({
									url:'tBAppraisalApplyController.do?doAudit&id='+id,
									type:'post',
									dataType:'json',
									success:function(data){
										tip(data.msg);
										reloadTable();
										iframe.close();
									}
								})
								return false;
							},
							button:[{
								name:'提出修改意见',
								callback:function(data){
								  	$.dialog({
														content : 'url:tBAppraisalApplyController.do?goPropose&type=1&id='+id,
														title : '提出修改意见',
														lock : true,
														opacity : 0.3,
														width : '450px',
														height : '120px',
														ok:function(){
															iframe = this.iframe.contentWindow;
															var msgText = iframe.getMsgText();
															var proposeIframe= iframe;
															$.ajax({
																url:'tBAppraisalApplyController.do?doPropose',
																data:{
																	id:id,
																	msgText:msgText
																},
																type:'post',
																success:function(data){
																	data = $.parseJSON(data);
																	tip(data.msg);
																}
															})
														},
														cancel:function(){
												
														},
													}).zindex();
								  	return false;
								}
							}],
							cancel:function(){
									reloadTable();
							},
						});
	}
	
	function view(id){
		$.dialog({
			content : 'url:tBAppraisalApplyController.do?goApplyForDepart&id='+id,
			title : '鉴定申请审查',
			lock : true,
			opacity : 0.3,
			width : '700px',
			height : '400px',
			cancel:function(){
			},
		});
	}
	
	//填写批复意见
	function doFinish(id){
		$.dialog({
			content : 'url:tBAppraisalApplyController.do?goApplyForDepart&id='+id,
			title : '鉴定申请审查',
			lock : true,
			opacity : 0.3,
			width : '700px',
			height : '400px',
			ok:function(){
				iframe = this.iframe.contentWindow;
				$('#saveBtn2',iframe.document).click();
				return false;
			},
			cancel:function(){
	
			},
		});
	}
	
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