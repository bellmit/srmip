<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>产品交接清单信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $('#tt').tabs({
      onSelect : function(title) {
        $('#tt .panel-body').css('width', 'auto');
      }
    });
    $(".tabs-wrap").css('width', '100%');
  });

  function refreshParentWindow(data) {
    var win = W.$.dialog.list['processDialog'].content;//获取父窗口
    win.reloadTable();//刷新父窗口
    frameElement.api.close();//关闭当前窗口
  }
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tBPmProductDeliveryController.do?doAdd" callback="@Override refreshParentWindow">
    <input id="id" name="id" type="hidden" value="${tBPmProductDeliveryPage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${projectId }">
    <input id="contractId" name="contractId" type="hidden" value="${tBPmProductDeliveryPage.contractId }">
    <input id="createBy" name="createBy" type="hidden" value="${tBPmProductDeliveryPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBPmProductDeliveryPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBPmProductDeliveryPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBPmProductDeliveryPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBPmProductDeliveryPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBPmProductDeliveryPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <caption>
        <strong style="font-size: xx-large;">科研采购合同产品交接清单</strong>
      </caption>
      <tr>
        <td align="right">
          <label class="Validform_label">合同编号:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="contractCode" name="contractCode" type="text" style="width: 150px" class="inputxt" readonly="true" datatype="*" min="1" placeholder="请选择合同">
          <t:choose url="tPmOutcomeContractApprController.do?selectRec&projectId=${projectId}" textname="id,contractCode,contractName" inputTextname="contractId,contractCode,contractName"
            tablename="tPmOutcomeContractApprList" width="800px" height="450px" icon="icon-search" title="出账合同列表" isclear="true" left="50%" top="50%"></t:choose>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">合同编号</label>
        </td>
        <td align="right">
          <label class="Validform_label">合同名称:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="contractName" name="contractName" type="text" style="width: 150px" class="inputxt" readonly="true" datatype="*" min="1">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">合同名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">交付单位:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="deliverUnit" name="deliverUnit" type="text" style="width: 150px" class="inputxt" datatype="byterange" max="120" min="1" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">交付单位</label>
        </td>
        <td align="right">
          <label class="Validform_label">接收单位:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="receiveUnit" name="receiveUnit" type="text" style="width: 150px" class="inputxt" datatype="byterange" max="120" min="1" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">接收单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">交付人:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="deliverName" name="deliverName" type="text" style="width: 150px" class="inputxt" datatype="byterange" max="36" min="1" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">交付人</label>
        </td>
        <td align="right">
          <label class="Validform_label">接收人:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="receiveName" name="receiveName" type="text" style="width: 150px" class="inputxt" datatype="byterange" max="36" min="1" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">接收人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">交付日期:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="deliverDate" name="deliverDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">交付日期</label>
        </td>
        <td align="right">
          <label class="Validform_label">接收日期:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="receiveDate" name="receiveDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">接收日期</label>
        </td>
      </tr>
    </table>
    <div style="width: auto; height: 200px;">
      <%-- 增加一个div，用于调节页面大小，否则默认太小 --%>
      <div style="width: 800px; height: 1px;"></div>
      <t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
        <t:tab href="tBPmProductDeliveryController.do?tBPmProductDetailList&id=${tBPmProductDeliveryPage.id}" icon="icon-search" title="产品交接清单明细" id="tBPmProductDetail"></t:tab>
      </t:tabs>
    </div>
  </t:formvalid>
  <!-- 添加 附表明细 模版 -->
  <table style="display: none">
    <tbody id="add_tBPmProductDetail_table_template">
      <tr>
        <td align="center">
          <input style="width: 25px;" type="checkbox" name="ck" />
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].serialNum" maxlength="4" type="text" class="inputxt" style="width: 30px;" datatype="n1-4">
          <label class="Validform_label" style="display: none;">序号</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].productName" maxlength="100" type="text" class="inputxt" style="width: 120px;" datatype="*1-100">
          <label class="Validform_label" style="display: none;">产品名称</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].specificationModel" maxlength="80" type="text" class="inputxt" style="width: 120px;" datatype="*1-80">
          <label class="Validform_label" style="display: none;">规格型号</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].productUnit" maxlength="20" type="text" class="inputxt" style="width: 60px;" datatype="*1-20">
          <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].deliverNum" maxlength="12" type="text" class="inputxt" style="width: 60px;" datatype="n1-8">
          <label class="Validform_label" style="display: none;">交付数量</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].receiveNum" maxlength="12" type="text" class="inputxt" style="width: 60px;" datatype="n1-8">
          <label class="Validform_label" style="display: none;">接收数量</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[#index#].memo" maxlength="500" type="text" class="inputxt" style="width: 120px;">
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
    </tbody>
  </table>
</body>
<script src="webpage/com/kingtake/project/productdelivery/tBPmProductDelivery.js"></script>