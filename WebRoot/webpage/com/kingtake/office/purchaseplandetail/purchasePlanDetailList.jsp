<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPurchasePlanDetailList" checkbox="true" onDblClick="dbClickRow" extendParams="singleSelect:true," fitColumns="true" title="科研采购计划明细" actionUrl="tBPurchasePlanMainController.do?datagridForDetail&purchasePlanId=${purchasePlanId}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="主表记录id" field="purchasePlanId" hidden="true"  queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="采购计划名称" field="planName"   queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="采购概算" field="purchaseEstimates"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购方式" field="purchaseMode"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购来源" field="purchaseSource"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add" url="tBPurchasePlanMainController.do?goUpdateDetail&purchasePlanId=${purchasePlanId}" funname="add" width="780"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBPurchasePlanMainController.do?goUpdateDetail" funname="update" width="780"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPurchasePlanMainController.do?goUpdateDetail" funname="detail" width="780"></t:dgToolBar>
      <t:dgToolBar title="批量删除" icon="icon-remove" url="tBPurchasePlanMainController.do?doBatchDetailDel" funname="deleteALLSelect"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMainList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script> 
<script type="text/javascript">
  $(document).ready(function() {
  });
  
  function dbClickRow(rowIndex, rowData) {
    gridname = 'tBPurchasePlanDetailList';
    var url = 'tBPurchasePlanMainController.do?goUpdateDetail';
    url += '&load=detail&id='+rowData.id;
    var title = '查看采购计划明细';
    var width = '780px';
    var height = '400px';
    createdetailwindow(title,url,width,height);
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
  
    
    function goEdit(id){
        var url = "tBPurchasePlanMainController.do?goUpdate&id="+id;
        var title = "编辑";
        var width = '100%';
        var height = '100%';
        createwindow(title,url,width,height);
    }
    
  //查看审批意见
    function viewSuggestion(id,index){
        var url = "tBPurchasePlanMainController.do?goPropose&id="+id;
        createdetailwindow("查看修改意见",url,450,120);
    }
</script>