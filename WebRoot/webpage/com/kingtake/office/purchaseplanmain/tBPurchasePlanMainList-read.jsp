<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPurchasePlanMainList" checkbox="false" fitColumns="true" onDblClick="dblClickDetail" title="科研采购计划" actionUrl="tBPurchasePlanMainController.do?datagrid" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="责任单位id" field="dutyDepartId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="责任单位名称" field="dutyDepartName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="负责人id" field="managerId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="负责人" field="managerName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目编码" field="projectCode" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" query="true" isLike="true" queryMode="single" width="200"></t:dgCol>
      <t:dgCol title="总经费" field="totalFunds" hidden="false"  queryMode="group" width="120" extendParams="formatter:formatCurrency," align="right"></t:dgCol>
      <t:dgCol title="计划时间" field="planDate" formatter="yyyy-MM" hidden="false" queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="采购计划名称" field="planName" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="采购概算" field="purchaseEstimates" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购方式" field="purchaseMode" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购来源" field="purchaseSource" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPurchasePlanMainController.do?goUpdate" funname="detail" width="780"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMainList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script> 
<script type="text/javascript">
  $(document).ready(function() {
    //给时间控件加上样式
    $("#tBPurchasePlanMainListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
  });
  
  function dblClickDetail(){
    var rows = $("#tBPurchasePlanMainList").datagrid("getSelections");
    if (rows.length == 0) {
      tip("请至少选择一条数据进行查看！");
      return false;
    }
    var id = rows[0].id;
    W.$.dialog({
      content : 'url:tBPurchasePlanMainController.do?goUpdate&id=' + id + '&load=detail',
      lock : true,
      width : '780px',
      height : '400px',
      title : "查看",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      parent:windowapi,
      cancel : function() {
      }
    }).zindex();
  }
  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tBPurchasePlanMainController.do?upload', "tBPurchasePlanMainList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tBPurchasePlanMainController.do?exportXls", "tBPurchasePlanMainList");
  }

  //模板下载
  function ExportXlsByT() {
    JeecgExcelExport("tBPurchasePlanMainController.do?exportXlsByT", "tBPurchasePlanMainList");
  }
</script>