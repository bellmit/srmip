<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tBAppraisalMeetingList" fitColumns="true"
			title="鉴定会信息表"
			actionUrl="tBAppraisalMeetingController.do?datagridForApply&operateStatus=${operateStatus}"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="鉴定申请id" field="applyId" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="鉴定会时间" field="meetingDate" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="鉴定会地点" field="meetingAddr" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="联系人" field="contactName" query="true" isLike="true"
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="联系电话" field="contactPhone" queryMode="single"
				width="120"></t:dgCol>
			<t:dgCol title="报到时间" field="registerTime" formatter="yyyy-MM-dd"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="报到地点" field="registerAddr" queryMode="group"
				width="120"></t:dgCol>
			<t:dgCol title="主持单位id" field="hostDepartid" hidden="true"
				queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="主持单位" field="hostDepartname" query="true" 
				queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="通知编号" field="noticeNum" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审查状态" field="meetingStatus" codeDict="1,SPZT"
				queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
			<t:dgFunOpt exp="meetingStatus#eq#1" title="审核" funname="audit(id)"></t:dgFunOpt>
			<t:dgFunOpt exp="meetingStatus#eq#2" title="查看审核" funname="view(id)"></t:dgFunOpt>
			<t:dgToolBar title="查看鉴定会信息" icon="icon-search"
				url="tBAppraisalMeetingController.do?goMeetingForDepart"
				funname="detail" height="100%" width="100%"></t:dgToolBar>
			<%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
		</t:datagrid>
	</div>
</div>
<script
	src="webpage/com/kingtake/project/appraisalmeeting/tBAppraisalMeetingList.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				//给时间控件加上样式
				$("#tBAppraisalMeetingListtb").find(
						"input[name='meetingDate_begin']").attr("class",
						"Wdate").attr("style", "height:20px;width:90px;")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("#tBAppraisalMeetingListtb").find(
						"input[name='meetingDate_end']").attr("class", "Wdate")
						.attr("style", "height:20px;width:90px;").click(
								function() {
									WdatePicker({
										dateFmt : 'yyyy-MM-dd'
									});
								});
				$("#tBAppraisalMeetingListtb").find(
						"input[name='registerTime_begin']").attr("class",
						"Wdate").attr("style", "height:20px;width:90px;")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
				$("#tBAppraisalMeetingListtb").find(
						"input[name='registerTime_end']")
						.attr("class", "Wdate").attr("style",
								"height:20px;width:90px;").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd'
							});
						});
			});

	function audit(id) {
		var auditDialog = $.dialog({
					id : 'meetingDialog',
					content : 'url:tBAppraisalMeetingController.do?goMeetingForDepart&id='
							+ id,
					title : '鉴定会审查',
					lock : true,
					opacity : 0.3,
					width : window.top.document.body.offsetWidth,
					height : window.top.document.body.offsetHeight - 100,
					okVal : '通过',
					ok : function(data) {
						iframe = this.iframe.contentWindow;
						$.ajax({
							url : 'tBAppraisalMeetingController.do?doPass',
							data : {
								id : id
							},
							dataType : 'json',
							type : 'post',
							success : function(data) {
								tip(data.msg);
								if (data.success) {
									reloadTable();
									auditDialog.close();
								}
							}
						});
						return false;
					},
					button : [ {
						name : '驳回',
						callback : function(data) {
							var rebutDialog = $.dialog({
												content : 'url:tBAppraisalApplyController.do?goPropose&type=2&id='
														+ id,
												title : '填写修改意见',
												lock : true,
												opacity : 0.3,
												width : '450px',
												height : '120px',
												ok : function() {
													iframe = this.iframe.contentWindow;
													var msgText = iframe
															.getMsgText();
													var proposeIframe = iframe;
													$.ajax({
																url : 'tBAppraisalMeetingController.do?doPropose',
																data : {
																	id : id,
																	msgText : msgText
																},
																dataType : 'json',
																type : 'post',
																success : function(
																		data) {
																	tip(data.msg);
																	if (data.success) {
																		reloadTable();
																		auditDialog
																				.close();
																	}
																}
															});
												},
												cancel : function() {

												},
											}).zindex();
							return false;
						}
					} ],
					cancel : function() {
						reloadTable();
					},
				});
	}

	function view(id) {
		$.dialog({
					content : 'url:tBAppraisalMeetingController.do?goMeetingForDepart&id='
							+ id,
					title : '查看鉴定会信息',
					lock : true,
					opacity : 0.3,
					width : window.top.document.body.offsetWidth,
					height : window.top.document.body.offsetHeight - 100,
					cancel : function() {
						reloadTable();
					},
				});
	}
</script>