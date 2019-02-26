<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" uri="/easyui-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@page import="org.jeecgframework.core.util.DateUtils" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
String rootPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
%>
<c:set var="webRoot" value="<%=basePath%>" />
<c:set var="tm" value="<%=DateUtils.tm%>" />
