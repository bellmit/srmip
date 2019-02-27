<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<t:tab iframe="tOApprovalController.do?goOperate&id=${id}&operateStatus=${operateStatus}&editFlag=${editFlag}&ifcirculate${ifcirculate}" icon="icon-search" title="呈批单" id="o"></t:tab>
<c:forEach items="${list}" var="s">
<t:tab iframe="${s.controller}.do?goBusinessForApproval&approvalId=${approvalId}" icon="icon-search" title = "${s.title}" id="o"></t:tab>
</c:forEach>

</t:tabs>
