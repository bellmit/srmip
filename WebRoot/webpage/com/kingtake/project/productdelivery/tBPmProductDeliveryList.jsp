<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPmProductDeliveryList" checkbox="true" fitColumns="false" title="产品交接清单信息" actionUrl="tBPmProductDeliveryController.do?datagrid&projectId=${projectId}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目基本信息主键" field="projectId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="关联合同id" field="contractId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同编号" field="contractCode" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同名称" field="contractName" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="交付单位" field="deliverUnit" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="交付人" field="deliverName" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="交付日期" field="deliverDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="接收单位" field="receiveUnit" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="接收人" field="receiveName" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="接收日期" field="receiveDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
      <t:dgDelOpt title="删除" url="tBPmProductDeliveryController.do?doDel&id={id}" />
      <t:dgToolBar title="录入" icon="icon-add" url="tBPmProductDeliveryController.do?goAdd&projectId=${projectId}" funname="add" width="740" height="500"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBPmProductDeliveryController.do?goUpdate&projectId=${projectId}" funname="update" width="740" height="500"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPmProductDeliveryController.do?goUpdate&projectId=${projectId}" funname="detail" width="740" height="500"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<input type="hidden" id="projectId" name="projectId" value="${projectId}"/>
<script src="webpage/com/kingtake/project/productdelivery/tBPmProductDeliveryList.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    //给时间控件加上样式
    $("#tBPmProductDeliveryListtb").find("input[name='deliverDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPmProductDeliveryListtb").find("input[name='receiveDate']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPmProductDeliveryListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPmProductDeliveryListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPmProductDeliveryListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPmProductDeliveryListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
  });

  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tBPmProductDeliveryController.do?upload', "tBPmProductDeliveryList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tBPmProductDeliveryController.do?exportXls", "tBPmProductDeliveryList");
  }

  //模板下载
  function ExportXlsByT() {
    JeecgExcelExport("tBPmProductDeliveryController.do?exportXlsByT", "tBPmProductDeliveryList");
  }
</script>