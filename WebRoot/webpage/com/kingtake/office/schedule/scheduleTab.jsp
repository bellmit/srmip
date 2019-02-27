<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
<t:tabs id="addContractInfo"  tabPosition="bottom">
	<t:tab href="tOScheduleController.do?tOScheduleMgr" 
			icon="icon-search" title="日程安排" id="scheduleMgr"></t:tab>
	<t:tab href="tOScheduleController.do?goSheduleList" icon="icon-tip"
		title="日程列表" id="scheduleList" ></t:tab>
</t:tabs>
