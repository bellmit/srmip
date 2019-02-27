<%@page import="org.apache.commons.lang3.StringUtils"%>
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
});
</script>
<t:tabs id="tt" iframe="false" tabPosition="top" >
	
	<!-- 预算审核表信息 -->
	<% if(StringUtils.isNotEmpty((String)request.getAttribute("id1"))){ %>
		<%-- 	<t:tab href="tPmApprLogsController.do?goApprInfo&apprId=${id1}&apprType=${apprType}&type=1" icon="icon-search" title="总预算（单位：元）" id="appr"></t:tab>	 --%>
		<t:tab iframe="webpage/budget/templates/budgetDashbord/totalBudget2.html?T_P_ID=${tpId}&ysId=${id1}&pageType=1" icon="icon-search" title="总预算（单位：元）" id="appr"></t:tab>
	<%} %>
	<!-- 合同类预算表一 -->
	<% if(StringUtils.isNotEmpty((String)request.getAttribute("id2"))){ %>
		<%-- <t:tab iframe="tPmProjectFundsApprController.do?goYearFundsUpdate&id=${id2}&isEdit=2" icon="icon-search" title="年度预算（单位：元）" id="o"></t:tab> --%>
		<t:tab iframe="webpage/budget/templates/budgetDashbord/yearBudget.html?T_P_ID=${tpId}&ysId=${id2}&pageType=1" icon="icon-search" title="年度预算（单位：元）" id="o"></t:tab>
	<%} %>
</t:tabs>