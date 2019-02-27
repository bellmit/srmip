<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目申报</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript">
$(function(){
    var projectId = $("#projectId").val();
    var id = $("#id").val();
    var url = "";
    if(projectId!=""){
        url = "tPmDeclareController.do?goDetailAudit&projectId=" + projectId;
    }else if(id!=""){
      url = "tPmDeclareController.do?goDetailAudit&id=" + id;
    }
    $("#declareFrame").attr("src",url);
   
});

//跳转编辑
function goEdit(){
    var projectId = $("#projectId").val();
    var id = $("#id").val();
    var url = "";
    if(projectId!=""){
        url = "tPmDeclareController.do?goEditAudit&projectId=" + projectId;
    }else if(id!=""){
        url = "tPmDeclareController.do?goEditAudit&id=" + id;
    }
    window.open(url,"申报书编辑");
    
}
</script>
</head>
<body>
<input id="projectId" type="hidden" value="${projectId}">
<input id="id" type="hidden" value="${id}">
<%--   <c:if test="${read ne 1 }">
  <a id="edit" onclick="goEdit()" style="cursor: pointer;float:right;text-decoration: underline;">点击编辑</a>
 </c:if>
 --%>  <iframe id="declareFrame" frameborder="0" style="border:0;width:100%;height:500px;"></iframe>
  <t:authFilter></t:authFilter>
</body>
