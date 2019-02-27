<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" id="contentContainer" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmOutcomeContractApprList" checkbox="false" fitColumns="false" actionUrl="tPmOutcomeContractApprController.do?datagrid4Statistics" onDblClick="dblClickDetail" idField="id"
      fit="true" queryMode="group" sortName="startTime" sortOrder="desc">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目id" field="project_id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectnameSubjectcode" query="true" isLike="true" queryMode="single" width="120" ></t:dgCol>
      <t:dgCol title="申请单位" field="applyUnit" query="true" isLike="true"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="合同编号" field="contractCode" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="合同名称" field="contractName" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="对方单位" field="approvalUnit" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>
      <t:dgCol title="合同第三方" field="theContractThird" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="开始时间" field="startTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="截止时间" field="endTime" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="签订时间" field="contractSigningTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="总经费(元)" field="totalFunds" query="true" queryMode="group" width="80" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
      <t:dgCol title="合同类型" field="contractType" hidden="false" queryMode="group" width="80" align="right" codeDict="1,HTLB" ></t:dgCol>

      <t:dgCol title="审批状态" field="finishFlag" codeDict="1,SPZT" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="完成时间" field="finishTime" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90"></t:dgCol>

      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" queryMode="group" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90"></t:dgCol>

      <t:dgCol title="操作" field="opt" width="180" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="关联项目" funname="openProjectWin(project_id, projectnameSubjectcode)"></t:dgFunOpt>
      <t:dgFunOpt title="价款计算书" funname="openPriceWin(id, contractType)"></t:dgFunOpt>
      <t:dgToolBar title="查看" icon="icon-search" url="tPmOutcomeContractApprController.do?goUpdateTab" funname="detailOutcomeAppr" height="600" width="750"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>

    </t:datagrid>
    <input id="tipMsg" type="hidden" value="" />
  </div>
</div>
</div>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>

<script type="text/javascript">
  //查看来款节点
  function detailIncomeNode(id) {
    if (li_east == 0) {
      $('#contentContainer').layout('expand', 'east');
    }
    $('#contentContainer').layout('panel', 'east').panel('setTitle', "支付节点");
    $('#payNodeList').panel("refresh", 'tPmPayNodeController.do?outcomeContracToPayNode&contractId=' + id);
  }

  //双击查看方法
  function dblClickDetail(rowIndex, rowData) {
    var title = "查看";
    var url = "tPmOutcomeContractApprController.do?goUpdateTab&load=detail&node=false&id=" + rowData.id;
    var width = 750;
    var height = window.top.document.body.offsetHeight - 100;
    ;
    tabDetailDialog(title, url, width, height);
  }

  //查看出账合同
  function detailOutcomeAppr(title, url, gname, width, height) {
    url += '&load=detail&node=false';
    detailFun(title, url, gname, width, height, 'mainInfo');
  }

  // 打开价款计算书页面
  function openPriceWin(id, type) {
    gridname = 'tPmOutcomeContractApprList';
    var title = "价款计算书";
    var url = 'tPmContractPriceCoverController.do?tPmContractPriceCover' + '&contractType=' + type + '&contractId=' + id;
    var width = "100%";
    var height = "100%";

    //判断是否可编辑
    $.ajax({
      type : 'POST',
      url : 'tPmOutcomeContractApprController.do?updateFlag',
      data : {
        "id" : id
      },
      success : function(data) {
        var d = $.parseJSON(data);
        if (!d.success) {
          //不可编辑
          $("#tipMsg").val(d.msg);
          url += "&read=1&tip=true"
        }
        tabDetailDialog(title, url, width, height);
      }
    });
  }

  $(document).ready(function() {
    //给时间控件加上样式
    $("#tPmOutcomeContractApprListtb").find("input[name='finishTime_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tPmOutcomeContractApprListtb").find("input[name='finishTime_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tPmOutcomeContractApprListtb").find("input[name='startTime_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tPmOutcomeContractApprListtb").find("input[name='startTime_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tPmOutcomeContractApprListtb").find("input[name='contractSigningTime_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tPmOutcomeContractApprListtb").find("input[name='contractSigningTime_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
  });
  //查看项目
  function openProjectWin(id,name){
      var projectName = name;
      var url = "tPmProjectInfoController.do?tPmProjectInfo&projectId="+id;
      addTab('查看项目['+projectName+']', url, 'default');
  }

  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tPmOutcomeContractApprController.do?upload', "tPmOutcomeContractApprList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tPmOutcomeContractApprController.do?exportXls4Statistics", "tPmOutcomeContractApprList");
  }
</script>