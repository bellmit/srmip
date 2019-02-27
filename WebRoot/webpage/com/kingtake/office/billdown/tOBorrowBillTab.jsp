<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<t:tab iframe="tOSendDownBillController.do?tOMyBillList" 
	icon="icon-search" title="我的发文" id="o"></t:tab>
<t:tab iframe="tOSendDownBillController.do?tOMyReceiveList" 
	icon="icon-search" title="接收的发文" id="t"></t:tab>
</t:tabs>
