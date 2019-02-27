<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<%--<div class="easyui-layout" fit="false" style="height: 500px;">--%>
  <%--<div region="center" style="padding:1px; border: 1px;">--%>
<div style="width: 100%;height: 300px;">
    <t:datagrid name="tPmIncomeApplyList" onDblClick="goDetail" fitColumns="false"
                title="来款申请" actionUrl="tPmIncomeApplyController.do?datagrid&projectId=${projectId}"
                idField="id" fit="false" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="到账凭证号" field="voucherNo" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="发票号" field="invoice_invoiceNum1" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款金额" field="incomeAmount" queryMode="single" extend="{style:'width:200px;'}"></t:dgCol>
      <t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" queryMode="single" width="100"></t:dgCol>
      <t:dgCol title="申请人" field="applyUser"  queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="申请单位" field="applyDept"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款说明" field="incomeRemark" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="审核状态" field="auditStatus" replace="未提交_0,已提交_1,通过_2,未通过_3" queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="修改意见" field="msgText" hidden="true" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeApplyController.do?goEdit" funname="detail" width="900" height="600"></t:dgToolBar>
    </t:datagrid>
</div>
  <%--</div>--%>
<%--</div>--%>

<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
        gridname="tPmIncomeApplyList";

        $("#tPmIncomeApplyList").datagrid({
            height:300
        });
    });

    //查看
    function goDetail(rowIndex,rowData) {
        var id = rowData.id;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&id=' + id,
                lock : true,
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&id=' + id,
                lock : true,
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
     } 
</script>