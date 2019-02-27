<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目来款申请信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
	<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmIncomeApplyNodeList" checkbox="false" fitColumns="false" 
  	actionUrl="tPmContractNodeController.do?datagridNode&projectId=${projectId }" 
  	idField="id" fit="true" queryMode="group" pagination="false" sortName="createDate">
   <t:dgCol title="主键"  field="ID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同主键"  field="IN_OUT_CONTRACTID"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付（或来款）节点主键"  field="PROJ_PAY_NODE_ID" hidden="true" queryMode="group"  align="right"  width="120"></t:dgCol>
   <t:dgCol title="节点名称"  field="NODE_NAME"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="计划/合同标志"  field="PLAN_CONTRACT_FLAG"  hidden="true"  queryMode="group" replace="计划_1,合同_2" width="80"></t:dgCol>
   <t:dgCol title="完成时间"  field="COMPLETE_DATE" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="成果形式"  field="RESULT_FORM" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评价方法"  field="EVALUATION_METHOD"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="当前节点百分比"  field="PAY_PERCENT" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="累计比例"  field="CUMULATIVE_PROPORTION" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="节点金额"  field="PAY_AMOUNT" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="REMARKS"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="CREATE_NAME" hidden="true"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="创建时间"  field="CREATE_DATE" formatter="yyyy-MM-dd"  hidden="true" queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgToolBar title="查看合同节点" icon="icon-search" url="tPmContractNodeController.do?goUpdate" 
   	funname="detailChildFun" width="600" height="450"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
<script type="text/javascript">
function getJe(){
	var rowsData = $('#tPmIncomeApplyNodeList').datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择节点');
		return;
	}
	if (rowsData.length>1) {
		tip('不可选择多条');
		return;
	}
	return rowsData[0].PAY_AMOUNT;
}
</script>