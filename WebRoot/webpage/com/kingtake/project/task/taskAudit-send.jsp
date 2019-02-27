<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务书审核</title>
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
		   url:'tPmTaskController.do?checkIsAppr',
		   data:{"apprId":"${id}"},
		   type:'POST',
		   dataType:'json',
		   success:function(data){
		       if(data.isAppr=='1'){
		           $("#taskTab").tabs({tools:[{
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
</head>
<body>
  <!-- <div style="width: 880px;"> -->
    <t:tabs id="taskTab" iframe="false" tabPosition="top">
      <t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${id}&apprType=09&send=${send}&idFlag=${idFlag}&sptype=${sptype}" icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
      <t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${id}&apprType=09&send=false" icon="icon-search" title="审批记录表" id="apprLogs2"></t:tab>
    </t:tabs>
    <input id="#sendIds" type="hidden">
  <!-- </div> -->
</body>
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