<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBReportHallUseList" fitColumns="true" onDblClick="goDetail()" title="学术报告厅信息登记表" actionUrl="tBReportHallUseController.do?datagrid&type=${type}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="学术报告厅" field="reportHallId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="编号" field="reviewNumber" query="true" isLike="true" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="使用单位id" field="useDepartId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="使用单位名称" field="useDepartName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="使用目的" field="usePurpose" queryMode="single" width="180"></t:dgCol>
      <t:dgCol title="使用时间" field="beginUseTime" formatter="yyyy-MM-dd" queryMode="single" width="70"></t:dgCol>
      <%-- <t:dgCol title="结束使用时间"  field="endUseTime" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol> --%>
      <t:dgCol title="联系人id" field="contactId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="联系人姓名" field="contactName" query="true" isLike="true" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="联系电话" field="contactPhone" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="拟参加人员" field="attendeeName" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="需学术报告厅准备事宜" field="prepareThings" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审查状态" field="checkFlag" queryMode="group" codeDict="1,SCZT" width="40"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
      <!-- 表格操作列 -->
      <!-- type=1表示机关用户，能看到审查按钮 -->
      <c:if test="${type == '1'}">
        <t:dgFunOpt exp="checkFlag#eq#0" funname="checkBook(id)" title="审查"></t:dgFunOpt>
      </c:if>
      <c:if test="${type != '1'}">
        <t:dgFunOpt exp="checkFlag#eq#0" funname='goEdit(id)' title="编辑"></t:dgFunOpt>
      </c:if>
      <t:dgDelOpt exp="checkFlag#eq#0" title="删除" url="tBReportHallUseController.do?doDel&id={id}" />
      <t:dgFunOpt funname="exportTemplate(id)" title="导出"></t:dgFunOpt>
      <!-- 表格上方按钮 -->
      <!-- type!=1表示课题组用户，需要录入按钮 -->
      <c:if test="${type != '1'}">
        <t:dgToolBar title="录入" icon="icon-add" url="tBReportHallUseController.do?goEdit" funname="add" width="595" height="460"></t:dgToolBar>
      </c:if>
      <t:dgToolBar title="查看" icon="icon-search" url="tBReportHallUseController.do?goEdit" funname="detail" width="595" height="460"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>

    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/reporthall/tBReportHallUseList.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
  });

  //导出
  function ExportXls() {
    JeecgExcelExport("tBReportHallUseController.do?exportXls", "tBReportHallUseList");
  }

  //编辑
  function goEdit(id) {
    $.dialog({
      content : 'url:tBReportHallUseController.do?goEdit&id=' + id,
      lock : true,
      width : '595px',
      height : '460px',
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
    var rows = $("#tBReportHallUseList").datagrid("getSelections");
    if (rows.length == 0) {
      tip("请至少选择一条数据进行查看！");
      return false;
    }
    var id = rows[0].id;
    $.dialog({
      content : 'url:tBReportHallUseController.do?goEdit&id=' + id + '&load=detail',
      lock : true,
      width : '595px',
      height : '460px',
      title : "查看",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }

  //机关审查(checkFlag=1，其它值均为编辑)
  function checkBook(id) {
    $.dialog({
      content : 'url:tBReportHallUseController.do?goEdit&checkFlag=1&id=' + id,
      lock : true,
      width : '595px',
      height : '460px',
      title : "学术报告厅使用审查",
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

  //模板导出
  function exportTemplate(id) {
    $.dialog({
      content : 'url:tBReportHallUseController.do?goExport&id=' + id,
      lock : true,
      width : window.top.document.body.offsetWidth,
      height : window.top.document.body.offsetHeight - 100,
      left : '0%',
      title : "导出",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      cancel : function() {

      }
    });
  }
</script>