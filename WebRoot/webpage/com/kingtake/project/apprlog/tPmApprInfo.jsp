<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- 审批表信息 -->
<c:if test="${not empty url}">
	<iframe src="${url}" width="100%" height="100%" frameborder=0></iframe>
</c:if>
<c:if test="${empty  url}">
 <br> <br> <br> <br>
   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无法显示附加页面
</c:if>