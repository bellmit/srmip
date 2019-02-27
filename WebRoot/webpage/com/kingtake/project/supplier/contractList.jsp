<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="contractList" fitColumns="true" title='合同列表' 
			actionUrl="tPmQualitySupplierController.do?datagridForCooperationList" idField="contractId" fit="true" queryMode="group" 
            checkbox="true" onDblClick="detailTPmQualitySupplierList">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目id" field="projectId" hidden="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="项目名称" field="projectName" query="true"  queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="合同id" field="contractId" hidden="true" queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="合同编号" field="contractCode" query="true"  queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="会计编号" field="accountingCode" query="true" queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="合同名称" field="contractName" query="true"  queryMode="single" width="60"></t:dgCol>
			<t:dgCol title="负责人" field="projectManager"  queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="学校单位" field="applyUnit"  queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="合同经费" field="totalFunds" queryMode="group" width="60"></t:dgCol>
			<t:dgCol title="签订时间" field="contractSigningTime" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
		</t:datagrid>
    </div>
	</div>
</div>
<script type="text/javascript">
function getChecked(){
    var checked = $("#contractList").datagrid("getChecked");
    if(checked.length==0){
        tip("请先选择需要关联的合同！");
        return null;
    }
    return checked;
}
</script>
 </body>
 </html>