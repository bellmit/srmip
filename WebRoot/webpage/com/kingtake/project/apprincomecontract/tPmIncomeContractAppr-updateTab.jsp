<%@page import="com.kingtake.common.constant.ProjectConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审批已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
		var parent;
        var dialog = W.$.dialog.list['processDialog'];
        if (dialog == undefined) {
            parent = frameElement.api.opener;
        } else {
            parent = dialog.content;
        }
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}
});
</script>
<script src = "webpage/com/kingtake/project/apprincomecontract/tPmIncomeContractAppr.js"></script>
<input id="callBackType" type="hidden"/>
<input id="inOrOutContract" type="hidden" value="<%=ProjectConstant.INCOME_CONTRACT%>"/>
<t:tabs id="addContractInfo" iframe="false" tabPosition="top">
	<t:tab href="tPmIncomeContractApprController.do?goUpdate&id=${contractId}" icon="icon-search"
		title="合同信息" id="tPmIncomeContractAppr" ></t:tab>
	<t:tab href="tPmContractNodeController.do?tPmContractNode&contractId=${contractId}&node=${node}" 
		icon="icon-search" title="合同节点信息" id="tPmContractNode"></t:tab>
</t:tabs>
