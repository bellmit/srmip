<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="false" style="height: 400px;">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmIncomeNodeList" checkbox="true" fitColumns="true" actionUrl="tPmIncomeNodeController.do?datagrid&tpId=${projectId}" idField="id" fit="true" queryMode="group"
      sortName="createDate" sortOrder="desc" onDblClick="dblDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目基_主键" field="tpId" hidden="true" query="false" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="来款金额(元)" field="incomeAmount" queryMode="group" width="100" align="right" extendParams="formatter:transformAmount,"></t:dgCol>
      <t:dgCol title="来款说明" field="incomeExplain" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人id" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createName" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" queryMode="group" width="150" align="center"></t:dgCol>
      <t:dgCol title="审核标志" field="auditFlag" queryMode="group" width="80" codeDict="0,SFBZ"></t:dgCol>
      <t:dgCol title="审核人id" field="auditUserid" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审核人" field="auditUsername" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="审核时间" field="auditTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" width="150" align="center"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>

      <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="auditFlag#eq#0" title="编辑" funname="updateIncomeNode(id)" />
      <t:dgDelOpt exp="auditFlag#eq#0" title="删除" url="tPmIncomeNodeController.do?doDel&id={id}" />

      <t:dgToolBar title="录入" icon="icon-add" url="tPmIncomeNodeController.do?goAdd&tpId=${projectId}" funname="add" width="600" height="350" operationCode="addContractNode"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeNodeController.do?goUpdate" funname="detail" width="600" height="400"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/m2income/tPmIncomeNodeList.js"></script>
<script type="text/javascript">
  //双击查看详情
  function dblDetail(rowIndex, rowDate) {
    var title = "查看";
    var width = 600;
    var height = 400;
    var url = "tPmIncomeNodeController.do?goUpdate&load=detail&id=" + rowDate.id;
    createdetailwindow(title, url, width, height);
  }

  $(document).ready(function() {
    //设置datagrid的title
    var projectName = window.parent.getParameter();
    $("#tPmIncomeNodeList").datagrid({
      title : projectName + '-来款节点管理'
    });

    //给时间控件加上样式
    $("#tPmIncomeNodeListtb").find("input[name='incomeTime_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tPmIncomeNodeListtb").find("input[name='incomeTime_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
  });

  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tPmIncomeNodeController.do?upload', "tPmIncomeNodeList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tPmIncomeNodeController.do?exportXls&tpId=${projectId}", "tPmIncomeNodeList");
  }

  //模板下载
  function ExportXlsByT() {
    JeecgExcelExport("tPmIncomeNodeController.do?exportXlsByT", "tPmIncomeNodeList");
  }

  //更新
  function updateIncomeNode(id) {
    gridname = 'tPmIncomeNodeList';
    var url = 'tPmIncomeNodeController.do?goUpdate';
    url += '&id=' + id;
    createwindow('编辑', url, 600, 400);
  }
</script>