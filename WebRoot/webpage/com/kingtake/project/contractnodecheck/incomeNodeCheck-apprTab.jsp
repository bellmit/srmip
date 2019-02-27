<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审核已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
		var win;
		var dialog = W.$.dialog.list['processDialog'];
	    if (dialog == undefined) {
	    	win = frameElement.api.opener;
	    } else {
	    	win = dialog.content;
	    }
		var msg = $("#tipMsg", win.document).val();
		tip(msg);
	}
	
	$.ajax({
		   url:'tPmContractNodeCheckController.do?checkIsProjectNodeAppr',
		   data:{"apprId":"${apprId}"},
		   type:'POST',
		   dataType:'json',
		   success:function(data){
		       if(data.isAppr=='1'){
		           $("#contractNodeInfo").tabs({tools:[{
			       		iconCls:'icon-ok',
			       		text:'课题组组长审核',
			       		handler:function(){
			       		 researchAudit(data.receiveId);
			       		}
			       	}]});
		       }
		   }
		});
});

//课题组长审核
function researchAudit(receiveId){
    var apprUrl = 'tPmApprLogsController.do?goUpdate&id=' + receiveId;
    createChildWindowb('审核', apprUrl, 450, 230, windowapi);
}
</script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/project/contractnodecheck/outcomeContractNodeCheckList-receive.js"></script>
<t:tabs id="contractNodeInfo" iframe="false" tabPosition="top">
	<!-- 审核信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${apprId}&apprType=${apprType}&send=${send}" 
			icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
	
	<!-- 合同节点验收信息 -->
	<t:tab iframe="tPmContractNodeCheckController.do?goIncomeContractCheckUpdate&id=${apprId}&load=detail" icon="icon-search"
		title="进账合同节点验收信息" id="tPmContractNodeCheck" ></t:tab>
		
	<!-- 审核信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=${apprType}&send=${send}" 
			icon="icon-search" title="审核记录" id="apprLogs2"></t:tab>
</t:tabs>
<script type="text/javascript">
//-------4：完成流程-------
function updateFinishFlag1(param){
	var url1="tPmContractNodeCheckController.do?updateFinishFlag";
	$.ajax({
		cache : false,
		type : 'POST',
		url : url1,
		data : param,
		success : function(data) {
			var d = $.parseJSON(data);
			tip(d.msg);
			if(d.success){
				var win = W.$.dialog.list['processDialog'];
				win.content.reloadTable();
				var apprInfo = W.$.dialog.list['apprInfo'];
				apprInfo.close();
				var auditDialog = W.$.dialog.list['auditDialog'];
				auditDialog.close();
			}
		}
	});
}
</script>