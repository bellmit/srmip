<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>结题申请审核</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(document).ready(function() {
    });
</script>
</head>
<body>
  <!-- <div style="width: 880px;"> -->
    <t:tabs id="tt" iframe="false" tabPosition="top">
      <t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${id}&apprType=12&send=${send}&idFlag=${idFlag}&sptype=${sptype}" icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
      <t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${id}&apprType=12&send=false" icon="icon-search" title="审批记录表" id="apprLogs2"></t:tab>
    </t:tabs>
    <input id="#sendIds" type="hidden">
  <!-- </div> -->
</body> 