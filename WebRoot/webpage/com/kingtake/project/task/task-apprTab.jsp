<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
        //编辑时审批已处理：提示不可编辑
        if (location.href.indexOf("tip=true") != -1) {
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
    });
</script>
<t:tabs id="addTaskInfo" iframe="false" tabPosition="top">
  <t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${apprId}&apprType=09&send=${send}&idFlag=${idFlag}&sptype=${sptype}" icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
  <t:tab id="tPmTaskAppr" title="任务书/任务节点" href="tPmTaskController.do?goUpdateTaskForDepartment&id=${apprId}&load=detail"></t:tab>
  <t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=09&send=false" icon="icon-search" title="审批记录表" id="apprLogs2"></t:tab>
</t:tabs>
