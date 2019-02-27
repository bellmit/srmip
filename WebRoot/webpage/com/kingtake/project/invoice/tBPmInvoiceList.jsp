<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<%--<div class="easyui-layout" fit="false" style="height: 300px;">--%>
  <%--<div region="center" style="padding: 1px;">--%>
<div style="width: 100%;height:300px;">
    <t:datagrid name="tBPmInvoiceList" onDblClick="goDetail()" fitColumns="true" title="项目发票信息" actionUrl="tBPmInvoiceController.do?datagrid&projectId=${projectId}" idField="id" fit="false" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="申请时间" field="applyDate" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="发票号1" field="invoiceNum1" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="发票号2" field="invoiceNum2" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="发票金额" field="invoiceAmount" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="备注" field="memo" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt funname='goEdit(id)' title="编辑"></t:dgFunOpt>
      <t:dgDelOpt title="删除" url="tBPmInvoiceController.do?doDel&id={id}" />
      <t:dgToolBar title="录入" icon="icon-add" url="tBPmInvoiceController.do?goEdit&projectId=${projectId}" funname="add" width="400" height="380"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPmInvoiceController.do?goEdit" funname="detail" width="400" height="380"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  <%--</div>--%>
</div>
<script src="webpage/com/kingtake/project/invoice/tBPmInvoiceList.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    //给时间控件加上样式
    $("#tBPmInvoiceList").datagrid({
      height:300
    });
  });

  //导出
  function ExportXls() {
    JeecgExcelExport("tBPmInvoiceController.do?exportXls&projectId=${projectId}", "tBPmInvoiceList");
  }

  //编辑
  function goEdit(id) {
    $.dialog({
      content : 'url:tBPmInvoiceController.do?goEdit&id=' + id,
      lock : true,
      width : '400px',
      height : '500px',
      top : '10%',
      title : "修改",
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        saveObj();
        return false;
      },
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }
  //查看
  function goDetail() {
    var rows = $("#tBPmInvoiceList").datagrid("getSelections");
    if (rows.length == 0) {
      tip("请至少选择一条数据进行查看！");
      return false;
    }
    var id = rows[0].id;
    $.dialog({
      content : 'url:tBPmInvoiceController.do?goEdit&id=' + id + '&load=detail',
      lock : true,
      width : '400px',
      height : '380px',
      top : '0%',
      title : "查看",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }
</script>