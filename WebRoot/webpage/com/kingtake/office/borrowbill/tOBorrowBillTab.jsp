<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<t:tab iframe="tOBorrowBillController.do?tOBorrowBillAudit&auditType=1" 
	icon="icon-search" title="未处理" id="o"></t:tab>
<t:tab iframe="tOBorrowBillController.do?tOBorrowBillAudit&auditType=2" 
	icon="icon-search" title="已处理" id="t"></t:tab>
</t:tabs>
