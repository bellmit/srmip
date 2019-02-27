<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <%String appPath = application.getContextPath(); %> --%>
<html>
<head>
<title>附件查看</title>
<t:base type="jquery"></t:base>
</head>
<body>
<c:if test='${empty isconvert}'>
	该附件正在转换，暂时无法预览！
</c:if>
<c:if test='${not empty isconvert}'>
	该附件格式无法预览！
</c:if>
</body>
</html>



















