<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
  $('#addTBPmProductDetailBtn').linkbutton({
    iconCls : 'icon-add'
  });
  $('#delTBPmProductDetailBtn').linkbutton({
    iconCls : 'icon-remove'
  });
  $('#addTBPmProductDetailBtn').bind('click', function() {
    var tr = $("#add_tBPmProductDetail_table_template tr").clone();
    $("#add_tBPmProductDetail_table").append(tr);
    resetTrNum('add_tBPmProductDetail_table');
    return false;
  });
  $('#delTBPmProductDetailBtn').bind('click', function() {
    $("#add_tBPmProductDetail_table").find("input:checked").parent().parent().remove();
    resetTrNum('add_tBPmProductDetail_table');
    return false;
  });
  $(document).ready(function() {
    $(".datagrid-toolbar").parent().css("width", "auto");
    if (location.href.indexOf("load=detail") != -1) {
      $(":input").attr("disabled", "true");
      $(".datagrid-toolbar").hide();
    }
    //将表格的表头固定
    $("#tBPmProductDetail_table").createhftable({
      height : '200px',
      width : 'auto',
      fixFooter : false
    });
  });
</script>
<div style="padding: 3px; height: 25px; width: auto;" class="datagrid-toolbar">
  <a id="addTBPmProductDetailBtn" href="#">添加</a>
  <a id="delTBPmProductDetailBtn" href="#">删除</a>
</div>
<table border="0" cellpadding="2" cellspacing="0" id="tBPmProductDetail_table">
  <tr bgcolor="#E6E6E6">
    <td align="center" bgcolor="#EEEEEE" width="30px">操作</td>
    <td align="left" bgcolor="#EEEEEE" width="30px">序号</td>
    <td align="left" bgcolor="#EEEEEE" width="120px">产品名称</td>
    <td align="left" bgcolor="#EEEEEE" width="120px">规格型号</td>
    <td align="left" bgcolor="#EEEEEE" width="60px">单位</td>
    <td align="left" bgcolor="#EEEEEE" width="60px">交付数量</td>
    <td align="left" bgcolor="#EEEEEE" width="60px">接收数量</td>
    <td align="left" bgcolor="#EEEEEE" width="120px">备注</td>
  </tr>
  <tbody id="add_tBPmProductDetail_table">
    <c:if test="${fn:length(tBPmProductDetailList)  <= 0 }">
      <tr>
        <td align="center">
          <input style="width: 30px;" type="checkbox" name="ck" />
        </td>
        <input name="tBPmProductDetailList[0].id" type="hidden" />
        <input name="tBPmProductDetailList[0].proeuctDeliveryId" type="hidden" />
        <input name="tBPmProductDetailList[0].createBy" type="hidden" />
        <input name="tBPmProductDetailList[0].createName" type="hidden" />
        <input name="tBPmProductDetailList[0].createDate" type="hidden" />
        <input name="tBPmProductDetailList[0].updateBy" type="hidden" />
        <input name="tBPmProductDetailList[0].updateName" type="hidden" />
        <input name="tBPmProductDetailList[0].updateDate" type="hidden" />
        <td align="left">
          <input name="tBPmProductDetailList[0].serialNum" maxlength="4" value="1" type="text" class="inputxt" style="width: 30px;" datatype="n1-4">
          <label class="Validform_label" style="display: none;">序号</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[0].productName" maxlength="100" type="text" class="inputxt" style="width: 120px;" datatype="*1-100">
          <label class="Validform_label" style="display: none;">产品名称</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[0].specificationModel" maxlength="80" type="text" class="inputxt" style="width: 120px;" datatype="*1-80">
          <label class="Validform_label" style="display: none;">规格型号</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[0].productUnit" maxlength="20" type="text" class="inputxt" style="width: 60px;" datatype="*1-20">
          <label class="Validform_label" style="display: none;">单位</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[0].deliverNum" maxlength="12" type="text" class="inputxt" style="width: 60px;" datatype="n1-8">
          <label class="Validform_label" style="display: none;">交付数量</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[0].receiveNum" maxlength="12" type="text" class="inputxt" style="width: 60px;" datatype="n1-8">
          <label class="Validform_label" style="display: none;">接收数量</label>
        </td>
        <td align="left">
          <input name="tBPmProductDetailList[0].memo" maxlength="500" type="text" class="inputxt" style="width: 120px;">
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
    </c:if>
    <c:if test="${fn:length(tBPmProductDetailList)  > 0 }">
      <c:forEach items="${tBPmProductDetailList}" var="poVal" varStatus="stuts">
        <tr>
          <td align="center">
            <input style="width: 30px;" type="checkbox" name="ck" />
          </td>
          <input name="tBPmProductDetailList[${stuts.index }].id" type="hidden" value="${poVal.id }" />
          <input name="tBPmProductDetailList[${stuts.index }].proeuctDeliveryId" type="hidden" value="${poVal.proeuctDeliveryId }" />
          <input name="tBPmProductDetailList[${stuts.index }].createBy" type="hidden" value="${poVal.createBy }" />
          <input name="tBPmProductDetailList[${stuts.index }].createName" type="hidden" value="${poVal.createName }" />
          <input name="tBPmProductDetailList[${stuts.index }].createDate" type="hidden" value="${poVal.createDate }" />
          <input name="tBPmProductDetailList[${stuts.index }].updateBy" type="hidden" value="${poVal.updateBy }" />
          <input name="tBPmProductDetailList[${stuts.index }].updateName" type="hidden" value="${poVal.updateName }" />
          <input name="tBPmProductDetailList[${stuts.index }].updateDate" type="hidden" value="${poVal.updateDate }" />
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].serialNum" maxlength="4" type="text" class="inputxt" style="width: 25px;" value="${poVal.serialNum }" datatype="n1-4">
            <label class="Validform_label" style="display: none;">序号</label>
          </td>
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].productName" maxlength="100" type="text" class="inputxt" style="width: 120px;" value="${poVal.productName }"  datatype="*1-100">
            <label class="Validform_label" style="display: none;">产品名称</label>
          </td>
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].specificationModel" maxlength="80" type="text" class="inputxt" style="width: 120px;" value="${poVal.specificationModel }" datatype="*1-80">
            <label class="Validform_label" style="display: none;">规格型号</label>
          </td>
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].productUnit" maxlength="20" type="text" class="inputxt" style="width: 60px;" value="${poVal.productUnit }" datatype="*1-20">
            <label class="Validform_label" style="display: none;">单位</label>
          </td>
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].deliverNum" maxlength="12" type="text" class="inputxt" style="width: 60px;" value="${poVal.deliverNum }" datatype="n1-8">
            <label class="Validform_label" style="display: none;">交付数量</label>
          </td>
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].receiveNum" maxlength="12" type="text" class="inputxt" style="width: 60px;" value="${poVal.receiveNum }" datatype="n1-8">
            <label class="Validform_label" style="display: none;">接收数量</label>
          </td>
          <td align="left">
            <input name="tBPmProductDetailList[${stuts.index }].memo" maxlength="500" type="text" class="inputxt" style="width: 120px;" value="${poVal.memo }">
            <label class="Validform_label" style="display: none;">备注</label>
          </td>
        </tr>
      </c:forEach>
    </c:if>
  </tbody>
</table>
