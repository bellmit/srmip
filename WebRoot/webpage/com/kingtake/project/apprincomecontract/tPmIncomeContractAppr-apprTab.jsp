<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审核已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
	    var parent;
        var dialog = W.$.dialog.list['processDialog'];
        if (dialog == undefined) {
            parent = frameElement.api.opener;
        } else {
            parent = dialog.content;
        }
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}
	$.ajax({
	   url:'tPmIncomeContractApprController.do?checkIsAppr',
	   data:{"apprId":"${apprId}"},
	   type:'POST',
	   dataType:'json',
	   success:function(data){
	       if(data.isAppr=='1'){
	           $("#addContractInfo").tabs({tools:[{
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
    createChildWindow2('审核', apprUrl, 450, 230, windowapi);
}
</script>
<t:tabs id="addContractInfo" iframe="false" tabPosition="top" >
	<!-- 审核信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${apprId}&apprType=${apprType}&send=${send}&sptype=${sptype}" 
			icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
			
	<t:tab href="tPmIncomeContractApprController.do?goUpdate&id=${apprId}" icon="icon-search"
		title="合同信息" id="tPmOutcomeContractAppr" ></t:tab>
	<t:tab href="tPmContractNodeController.do?tPmContractNode&contractId=${apprId}&node=false" icon="icon-search"
		title="合同节点信息" id="tPmContractNode"></t:tab>
		
	<!-- 审核信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=${apprType}&send=${send}" 
			icon="icon-search" title="审核记录" id="apprLogs2"></t:tab>
</t:tabs>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
function updateFinishFlag(param){
	var url1="tPmApprLogsController.do?updateFinishFlag";
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