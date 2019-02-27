<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,tools,easyui,DatePicker"></t:base>
<t:tabs id="tt" iframe="true" tabPosition="bottom">
	<t:tab href="tBLearningThesisController.do?goCheckList&businessCode=learning_thesis" icon="icon-search" title="待审核学术论文" id="default"></t:tab>
	<%-- <t:tab href="tBLearningThesisController.do?goGroupTaskList&businessCode=${businessCode}" icon="icon-search" title="组任务" id="autocom"></t:tab> --%>
	<t:tab href="tBLearningThesisController.do?tBLearningThesis&role=depart&businessCode=learning_thesis" icon="icon-search" title="已审核学术论文" id="autoSelect"></t:tab>
</t:tabs>

