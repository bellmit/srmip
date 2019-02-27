<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<!DOCTYPE html >
<html>
 <head>
  <title>流程图预览</title>
 </head>
 <body style="overflow-y: hidden;" scroll="no">
 
  <div>
   <c:if test="${tag eq 'task'}">
    <img src='activitiController.do?processPic&resourceType=image&processInstanceId=${processInstanceId}' style='position: absolute; left: 0px; top: 0px;' />
   </c:if>
   <c:if test="${tag eq 'deployment'}">
    <img src='activitiController.do?processPicByDeploy&deploymentId=${deploymentId}&resourceName=${resourceName}' style='position: absolute; left: 0px; top: 0px;' />
   </c:if>
   <c:if test="${tag eq 'project'}">
    <img src='activitiController.do?processPic&resourceType=image&processInstanceId=${processInstanceId}' style='position: absolute; left: 0px; top: 0px;' />
   </c:if>
  </div>
  <%--
  
	  <div style="position:absolute; border:2px solid red;left:${activityImpl.x}px;top:${activityImpl.y }px;width:${activityImpl.width }px;height:${activityImpl.height }px;"></div>  
	
 --%>
 </body>
</html>
