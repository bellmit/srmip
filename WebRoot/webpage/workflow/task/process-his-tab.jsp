<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs  id="tt" iframe="false" tabPosition="top">
	<t:tab  href="taskController.do?goProcessHisForm&processInstanceId=${processInstanceId }" icon="icon-search" title="附加单据页面" id="formPage"></t:tab>
	<t:tab  iframe="taskController.do?goProcessHisOperate&processInstanceId=${processInstanceId }" icon="icon-search" title="任务处理记录" id="taskOperate" ></t:tab>
	<t:tab  href="activitiController.do?viewProcessInstanceHistory&processInstanceId=${processInstanceId }&businessCode=${businessCode }" icon="icon-search" title="流程图" id="processPicture"></t:tab>
</t:tabs>
