<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,tools,easyui,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="bottom">
	<t:tab href="tBPmChangeProjectnameController.do?goMyTaskList&businessCode=${businessCode}" icon="icon-search" title="我的任务" id="default"></t:tab>
	<t:tab href="tBPmChangeProjectnameController.do?goGroupTaskList&businessCode=${businessCode}" icon="icon-search" title="组任务" id="autocom"></t:tab>
	<t:tab href="tBPmChangeProjectnameController.do?goHistoryTaskList&businessCode=${businessCode}" icon="icon-search" title="历史任务" id="autoSelect"></t:tab>
</t:tabs>

