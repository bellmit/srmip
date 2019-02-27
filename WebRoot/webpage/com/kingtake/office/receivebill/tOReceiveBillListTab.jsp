<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
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

function seletTab(i){
    $("#tt").tabs("select",i);
}
</script>
<t:tabs id="tt" iframe="true" tabPosition="top" >
<t:tab href="tOReceiveBillController.do?tOReceiveBillListToMe&operateStatus=${untreated}&registerType=${registerType}" 
	icon="icon-search" title="未处理" id="o"></t:tab>
<t:tab href="tOReceiveBillController.do?tOReceiveBillListToMe&operateStatus=${treated}&registerType=${registerType}" 
	icon="icon-search" title="已处理" id="t"></t:tab>
</t:tabs>
