<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
request.setAttribute("travelApprType", ApprovalConstant.APPR_TYPE_TRAVEL);
%>
<script type="text/javascript">
    $(function() {
        //编辑时审核已处理：提示不可编辑
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
<t:tabs id="apprInfo" iframe="false" tabPosition="top">
  <t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${apprId}&apprType=${travelApprType}&send=${send}&idFlag=${idFlag}" icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
  <t:tab id="tPmTaskAppr" title="差旅请假" iframe="tOTravelLeaveController.do?goUpdate&id=${apprId}&load=detail"></t:tab>
  <t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=${travelApprType}&send=false" icon="icon-search" title="审核记录" id="apprLogs2"></t:tab>
</t:tabs>
