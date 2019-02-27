<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	$('.tabs-inner').click(function(){
		var tab = $('#tt').tabs('getSelected');  
    	var iframeTab = tab.find('iframe');
    	var src = iframeTab.attr("src");
    	iframeTab.attr("src", src);
	});
	
});
</script>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<t:tab iframe="tOApprovalController.do?tOApprovalListToMe&operateStatus=${untreated}&registerType=${registerType}" 
	icon="icon-search" title="未处理" id="o"></t:tab>
<t:tab iframe="tOApprovalController.do?tOApprovalListToMe&operateStatus=${treated}&registerType=${registerType}" 
	icon="icon-search" title="已处理" id="t"></t:tab>
</t:tabs>
