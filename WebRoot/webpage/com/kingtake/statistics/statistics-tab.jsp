<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<t:tabs id="tabsId" fit="true" tabPosition="bottom" >
<t:tab title="签订合同数量趋势图" icon="icon-search" id="numTab" href="ContractStatisticsController.do?goOutcomeContractNumLine"></t:tab>
<t:tab title="签订合同额度趋势图" icon="icon-search" id="sumTab" href="ContractStatisticsController.do?goOutcomeContractAmountLine"></t:tab>
<t:tab title="采购方式签订合同数量趋势图" icon="icon-search" id="cgfsTab" href="ContractStatisticsController.do?goOutcomeContractCgfsLine"></t:tab>
</t:tabs>

<script type="text/javascript">
</script>
