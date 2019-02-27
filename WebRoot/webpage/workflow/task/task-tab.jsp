<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goTaskForm&taskId=${taskId }" icon="icon-search" title="附加页面" id="formPage"></t:tab>
	<t:tab  iframe="taskController.do?goTaskOperate&taskId=${taskId }" icon="icon-search" title="任务处理" id="taskOperate"></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }" icon="icon-search" title="流程图" id="processPicture"></t:tab>
</t:tabs>


