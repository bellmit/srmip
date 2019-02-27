<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
  $('#addTBPurchasePlanDetailBtn').linkbutton({
    iconCls : 'icon-add'
  });
  $('#delTBPurchasePlanDetailBtn').linkbutton({
    iconCls : 'icon-remove'
  });
  $('#addTBPurchasePlanDetailBtn').bind('click', function() {
    var tr = $("#add_tBPurchasePlanDetail_table_template tr").clone();
    $("#add_tBPurchasePlanDetail_table").append(tr);
    resetTrNum('add_tBPurchasePlanDetail_table');
    return false;
  });
  $('#delTBPurchasePlanDetailBtn').bind('click', function() {
    $("#add_tBPurchasePlanDetail_table").find("input:checked").parent().parent().remove();
    resetTrNum('add_tBPurchasePlanDetail_table');
    return false;
  });
  $(document).ready(function() {
    $(".datagrid-toolbar").parent().css("width", "auto");
    if (location.href.indexOf("load=detail") != -1) {
      $(":input").attr("disabled", "true");
      $(".datagrid-toolbar").hide();
    }
    //将表格的表头固定
    $("#tBPurchasePlanDetail_table").createhftable({
      height : '300px',
      width : 'auto',
      fixFooter : false
    });
  });
</script>
<div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar">
  <a id="addTBPurchasePlanDetailBtn" href="#">添加</a>
  <a id="delTBPurchasePlanDetailBtn" href="#">删除</a>
</div>
<table border="0" cellpadding="2" cellspacing="0" id="tBPurchasePlanDetail_table">
  <tr bgcolor="#E6E6E6">
    <td align="center" bgcolor="#EEEEEE">序号</td>
    <td align="center" bgcolor="#EEEEEE">操作</td>
    <td align="left" bgcolor="#EEEEEE">采购计划名称</td>
    <td align="left" bgcolor="#EEEEEE">采购概算</td>
    <td align="left" bgcolor="#EEEEEE">采购方式</td>
    <td align="left" bgcolor="#EEEEEE">采购来源</td>
  </tr>
  <tbody id="add_tBPurchasePlanDetail_table">
    <c:if test="${fn:length(tBPurchasePlanDetailList)  <= 0 }">
      <tr>
        <td align="center">
          <div style="width: 25px;" name="xh">1</div>
        </td>
        <td align="center">
          <input style="width: 20px;" type="checkbox" name="ck" />
        </td>
        <input name="tBPurchasePlanDetailList[0].createBy" type="hidden" />
        <input name="tBPurchasePlanDetailList[0].createName" type="hidden" />
        <input name="tBPurchasePlanDetailList[0].createDate" type="hidden" />
        <input name="tBPurchasePlanDetailList[0].updateBy" type="hidden" />
        <input name="tBPurchasePlanDetailList[0].updateName" type="hidden" />
        <input name="tBPurchasePlanDetailList[0].id" type="hidden" />
        <input name="tBPurchasePlanDetailList[0].purchasePlanId" type="hidden" />
        <td align="left">
          <input name="tBPurchasePlanDetailList[0].planName" datatype="*1-200" maxlength="200" type="text" class="inputxt" style="width: 220px;">
          <label class="Validform_label" style="display: none;">采购计划名称</label>
        </td>
        <td align="left">
          <input name="tBPurchasePlanDetailList[0].purchaseEstimates" maxlength="12" type="text" 
            datatype="/^([1-9]\d{0,11}|0)(\.\d{1,2})?$/" errorMsg="请输入一个整数部分最多10位，小数部分最多2位的金额" style="width: 70px; text-align: right;">
          <label class="Validform_label" style="display: none;">采购概算</label>
        </td>
        <td align="left">
          <t:codeTypeSelect name="tBPurchasePlanDetailList[0].purchaseMode" labelText="请选择" type="select" codeType="1" code="CGFS" id="purchaseMode" extendParam="{'datatype':'*','style':'width:125px;'}"></t:codeTypeSelect>
          <label class="Validform_label" style="display: none;">采购方式</label>
        </td>
        <td align="left">
          <input name="tBPurchasePlanDetailList[0].purchaseSource" maxlength="200" datatype="*0-1000" type="text" class="inputxt" style="width: 250px;">
          <label class="Validform_label" style="display: none;">采购计划选取理由</label>
        </td>
        <td align="left">
          <input name="tBPurchasePlanDetailList[0].purchaseSource" maxlength="200" datatype="*0-200" type="text" class="inputxt" style="width: 250px;">
          <label class="Validform_label" style="display: none;">采购来源</label>
        </td>
      </tr>
    </c:if>
    <c:if test="${fn:length(tBPurchasePlanDetailList)  > 0 }">
      <c:forEach items="${tBPurchasePlanDetailList}" var="poVal" varStatus="stuts">
        <tr>
          <td align="center">
            <div style="width: 25px;" name="xh">${stuts.index+1 }</div>
          </td>
          <td align="center">
            <input style="width: 20px;" type="checkbox" name="ck" />
          </td>
          <input name="tBPurchasePlanDetailList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }" />
          <input name="tBPurchasePlanDetailList[${stuts.index }].createName" type="hidden" value="${poVal.createName }" />
          <input name="tBPurchasePlanDetailList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }" />
          <input name="tBPurchasePlanDetailList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }" />
          <input name="tBPurchasePlanDetailList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }" />
          <input name="tBPurchasePlanDetailList[${stuts.index }].id" type="hidden" value="${poVal.id }" />
          <input name="tBPurchasePlanDetailList[${stuts.index }].purchasePlanId" type="hidden" value="${poVal.purchasePlanId }" />
          <td align="left">
            <input name="tBPurchasePlanDetailList[${stuts.index }].planName"  datatype="*1-200" maxlength="200" type="text" class="inputxt" style="width: 220px;" value="${poVal.planName }">
            <label class="Validform_label" style="display: none;">采购计划名称</label>
          </td>
          <td align="left">
            <input name="tBPurchasePlanDetailList[${stuts.index }].purchaseEstimates"  maxlength="12" type="text"
            datatype="/^([1-9]\d{0,11}|0)(\.\d{1,2})?$/" errorMsg="请输入一个整数部分最多10位，小数部分最多2位的金额" style="width: 70px; text-align: right;" value="${poVal.purchaseEstimates }">
            <label class="Validform_label" style="display: none;">采购概算</label>
          </td>
          <td align="left">
            <t:codeTypeSelect name="tBPurchasePlanDetailList[${stuts.index }].purchaseMode" labelText="请选择" type="select" codeType="1" code="CGFS" id="purchaseMode"
              extendParam="{'datatype':'*','style':'width:125px;'}" defaultVal="${poVal.purchaseMode }"></t:codeTypeSelect>
            <label class="Validform_label" style="display: none;">采购方式</label>
          </td>
          <td align="left">
            <input name="tBPurchasePlanDetailList[${stuts.index }].purchaseSource" maxlength="200" datatype="*0-200" type="text" class="inputxt" style="width: 250px;" value="${poVal.purchaseSource }">
            <label class="Validform_label" style="display: none;">采购来源</label>
          </td>
        </tr>
      </c:forEach>
    </c:if>
  </tbody>
</table>
