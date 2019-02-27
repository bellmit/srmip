<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:tabs id="tt" iframe="false" tabPosition="bottom">
 <t:tab href="processController.do?processnode&processid=${processid}" icon="icon-search" title="流程节点" id="pnode"></t:tab>
 <t:tab href="processController.do?processpro&processid=${processid }" icon="icon-search" title="流程变量" id="ppro"></t:tab>
 <t:tab href="processController.do?busbase&processid=${processid }" icon="icon-search" title="业务关联" id="bpro"></t:tab>
</t:tabs>
