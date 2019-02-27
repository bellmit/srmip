<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
        //编辑时审批已处理：提示不可编辑
        if (location.href.indexOf("tip=true") != -1) {
            var parent = frameElement.api.opener;
            var msg = $("#tipMsg", parent.document).val();
            tip(msg);
        }
    });
</script>
<t:tabs id="addFinishApplyInfo" iframe="false" tabPosition="top">
  <t:tab href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${apprId}&apprType=14&send=${send}&idFlag=${idFlag}" icon="icon-search" title="审核信息" id="apprLogs"></t:tab>
  <t:tab id="tOSealUse" title="印章使用申请" iframe="tOSealUseController.do?goUpdateRealUse4Department&id=${apprId}&load=detail"></t:tab>
  <t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=14&send=false" icon="icon-search" title="审核记录" id="apprLogs2"></t:tab>
</t:tabs>
