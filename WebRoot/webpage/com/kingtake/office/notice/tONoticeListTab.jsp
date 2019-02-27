<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="top" >
<t:tab iframe="tONoticeController.do?tONoticeToMe" icon="icon-search" title="我接收的" id="t"></t:tab>
<t:tab iframe="tONoticeController.do?tONotice" icon="icon-search" title="我发出的" id="o"></t:tab>
</t:tabs>
