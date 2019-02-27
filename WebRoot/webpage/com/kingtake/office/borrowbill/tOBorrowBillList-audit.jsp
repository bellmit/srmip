<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOBorrowBillList" checkbox="true" fit="true" fitColumns="true" title="收文借阅申请" actionUrl="tOBorrowBillController.do?datagridAudit&auditType=${auditType}" idField="id"  onDblClick="dbClickRow" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="公文标题" field="title" queryMode="group" width="120" frozenColumn="true"></t:dgCol>
      <t:dgCol title="公文编号" field="fileNum" queryMode="group" width="250" frozenColumn="true"></t:dgCol>
      <t:dgCol title="发文单位" field="sendUnit" hidden="true" ></t:dgCol>
      <t:dgCol title="申请人" field="applyUserName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="承研单位" field="undertakeUnitName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="借阅开始时间" field="borrowBeginTime" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="借阅结束时间" field="borrowEndTime" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="借阅单位" field="borrowUnitName"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审批状态" field="auditStatus" replace="未提交_0,已提交_1,通过_2,驳回修改_3" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <c:if test="${auditType eq '1' }">
      <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="auditStatus#eq#1" title="通过" funname="doPass(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="auditStatus#eq#1" title="退回" funname="doReturn(id)" ></t:dgFunOpt>
      </c:if>
      <t:dgToolBar title="查看" icon="icon-search" url="tOBorrowBillController.do?goUpdate" funname="detail" width="100%" height="100%"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/borrowbill/tOBorrowBillList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tOWorkPointListtb").find("input[name='time_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='time_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOWorkPointController.do?upload', "tOWorkPointList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tOWorkPointController.do?exportXls", "tOWorkPointList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOWorkPointController.do?exportXlsByT", "tOWorkPointList");
    }
    
 
  //数据编辑
  function goUpdate(id){
      var url = "tOBorrowBillController.do?goUpdate&id="+id;
      createwindow("编辑",url,"100%","100%");
  }
  
</script>
<script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>