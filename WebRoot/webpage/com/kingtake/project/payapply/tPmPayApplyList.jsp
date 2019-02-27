<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%--<div class="easyui-layout" fit="false" style="height: 300px;">--%>
  <%--<div region="center" style="padding: 1px;">--%>
<div style="width: 100%;height: 300px;">
    <t:datagrid name="tPmContractNodeCheckList" checkbox="false" fitColumns="false" title="支付申请信息"
      actionUrl="tPmPayApplyController.do?payApplyDatagrid&projectId=${projectId}" idField="id" fit="false" queryMode="group" pagination="false">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="合同名称" field="contractName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同节点名称" field="contractNodeName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="承研单位" field="devdepartName" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="项目负责人" field="projectManagerName" queryMode="single" width="100"></t:dgCol>
      <t:dgCol title="合同开始时间" field="contractStartTime" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同结束时间" field="contractEndTime" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同乙方" field="contractUnitnameb" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="本期付款金额" field="currentPayAmount" queryMode="single" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
      <t:dgCol title="合同总价款" field="contractTotalAmount" extendParams="formatter:formatCurrency," queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="支付申请状态" field="operateStatus" query="false" queryMode="group" codeDict="1,SPZT" width="80"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="240" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="pay_status#ne#u" funname="viewPayApply(id)" title="查看支付申请"></t:dgFunOpt>
     <%--  <t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmOutcomeContractApprController.do?goUpdateTab&load=detail&node=false" funname="detailContract" height="600" width="750"></t:dgToolBar>
      <t:dgToolBar title="查看合同节点" icon="icon-search" url="tPmContractNodeController.do?goUpdate" funname="detail" width="600" height="380"></t:dgToolBar> --%>

    </t:datagrid>
    <input id="tipMsg" type="hidden" value="" />
  <%--</div>--%>
</div>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tPmIncomeContractApprListtb").find("input[name='startTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='startTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='endTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='endTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });

                $("#tPmContractNodeCheckList").datagrid({
                    height:300
                });
            });


    //查看支付申请
    function viewPayApply(id){
        var url = "tPmPayApplyController.do?goAddUpdate";
        url += '&load=detail&id='+id;
    	createdetailwindow("查看支付申请",url,'100%','100%');
    }
</script>