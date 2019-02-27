<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>预算类别表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(function() {
    $("#parentId").combotree({
      url : 'tBApprovalBudgetRelaController.do?getApprovalBudgetRelaTree&projApproval=${tBApprovalBudgetRelaPage.projApproval}',
      value : '${tBApprovalBudgetRelaPage.TBApprovalBudgetRelaEntity.id}',
      panelHeight : 250,
      panelWidth:300
    });
  });

  function refreshParentWindow(data) {
    var win;//获取父窗口
    if(W.$.dialog.list['openWindowDialog']){
      win = W.$.dialog.list['openWindowDialog'].content;
    }else{
      win = frameElement.api.opener;
    }
    win.reloadTable();//刷新父窗口
    frameElement.api.close();//关闭当前窗口
  }
  

	
	function selectShowFlag() {
		var value = $("#showFlag").val();
		if (value == "1") {
			$("#priceimgid").attr("src", "images/pricetype/gll.png");
		} else if (value == "2") {
			$("#priceimgid").attr("src", "images/pricetype/cll.png");
		} else if (value == "3") {
			$("#priceimgid").attr("src", "images/pricetype/gzl.png");
		} else if (value == "4") {
			$("#priceimgid").attr("src", "images/pricetype/lrl.png");
		} else if (value == "5") {
			$("#priceimgid").attr("src", "images/pricetype/sjl.png");
		} else if (value == "6") {
			$("#priceimgid").attr("src", "images/pricetype/wxl.png");
		} else if (value == "7") {
			$("#priceimgid").attr("src", "images/pricetype/syl.png");
		} else {
			$("#priceimgid").attr("src", "");
		}
	}
	
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tBApprovalBudgetRelaController.do?doAdd" tiptype="1" tipSweep="true" callback="@Override refreshParentWindow">
    <input id="id" name="id" type="hidden" value="${tBApprovalBudgetRelaPage.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label">
            <c:if test="${tBApprovalBudgetRelaPage.projApproval eq '3' || tBApprovalBudgetRelaPage.projApproval eq '4' || tBApprovalBudgetRelaPage.projApproval eq '5'}">
                            价款类型:<font color="red">*</font>
            </c:if>
            <c:if test="${ tBApprovalBudgetRelaPage.projApproval ne '3' && tBApprovalBudgetRelaPage.projApproval ne '4' && tBApprovalBudgetRelaPage.projApproval ne '5'}">
							经费类型:<font color="red">*</font>
            </c:if>
          </label>
        </td>
        <td class="value">
          <input id="projApproval" name="projApproval" type="hidden" value="${tBApprovalBudgetRelaPage.projApproval}">
          <input type="text" value="${fundPropertyName}" style="width: 300px" class="inputxt" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">经费类型</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">
            <c:if test="${tBApprovalBudgetRelaPage.projApproval eq '3' || tBApprovalBudgetRelaPage.projApproval eq '4' || tBApprovalBudgetRelaPage.projApproval eq '5'}">
                            价款类型名称:<font color="red">*</font>
            </c:if>
            <c:if test="${ tBApprovalBudgetRelaPage.projApproval ne '3' && tBApprovalBudgetRelaPage.projApproval ne '4' && tBApprovalBudgetRelaPage.projApproval ne '5'}">
              预算类型名称:<font color="red">*</font>
            </c:if>
          </label>
        </td>
        <td class="value">
          <input id="budgetNae" name="budgetNae" type="text" style="width: 300px" class="inputxt" datatype="*1-40">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">

            <c:if test="${tBApprovalBudgetRelaPage.projApproval eq '3' || tBApprovalBudgetRelaPage.projApproval eq '4' || tBApprovalBudgetRelaPage.projApproval eq '5'}">
                            价款类型名称
            </c:if>
            <c:if test="${ tBApprovalBudgetRelaPage.projApproval ne '3' && tBApprovalBudgetRelaPage.projApproval ne '4' && tBApprovalBudgetRelaPage.projApproval ne '5'}">
              预算类型名称:<font color="red">*</font>
            </c:if>
          </label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 所属上级: </label>
        </td>
        <td class="value">
          <input id="parentId" name="TBApprovalBudgetRelaEntity.id" type="text" style="width: 300px" class="inputxt">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">所属上级</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 是否比例项: <font color="red">*</font>
        </label></td>
        <td class="value"><t:codeTypeSelect id="scaleFlag" name="scaleFlag" type="select" codeType="0" code="SFBZ"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">是否比例项</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 可执行操作: <font color="red">*</font>
        </label></td>
        <td class="value"><t:codeTypeSelect id="addChildFlag" name="addChildFlag" type="select" codeType="0" code="KZXCZ"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">可执行操作</label></td>
      </tr>
      <c:if test="${ tBApprovalBudgetRelaPage.projApproval ne '3' && tBApprovalBudgetRelaPage.projApproval ne '4' && tBApprovalBudgetRelaPage.projApproval ne '5'}">
        <tr>
          <td align="right"><label class="Validform_label"> 是否显示审批: <font color="red"></font>
          </label></td>
          <td class="value"><select id="showFlag" name="showFlag">
              <option value="">不显示</option>
              <option value="1">显示</option>
          </select> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标志位</label></td>
        </tr>
      </c:if>
      
      <c:if test="${tBApprovalBudgetRelaPage.projApproval eq '3' || tBApprovalBudgetRelaPage.projApproval eq '4' || tBApprovalBudgetRelaPage.projApproval eq '5'}">
        <tr>
          <td align="right"><label class="Validform_label"> 价款类别: <font color="red"></font>
          </label></td>
          <td class="value"><select id="showFlag" name="showFlag" onchange="selectShowFlag()">

              <option value="">--无--</option>
              <option value="1">管理类</option>
              <option value="2">材料类</option>
              <option value="3">工资类</option>
              <option value="4">利润类</option>
              <option value="5">设计类</option>
              <option value="6">外协类</option>
              <option value="7">试验类</option>
          </select> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标志位</label></td>
        </tr>
      </c:if>
      <tr>
        <td align="right">
          <label class="Validform_label"> 备注: </label>
        </td>
        <td class="value">
          <textarea id="memo" name="memo" style="width: 300px; height: 100px;" datatype="*1-2000" ignore="ignore"></textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
      <c:if test="${tBApprovalBudgetRelaPage.projApproval eq '3' || tBApprovalBudgetRelaPage.projApproval eq '4' || tBApprovalBudgetRelaPage.projApproval eq '5'}">
      <tr>
        <td align="right"><label class="Validform_label"> 价款类别表头预览: </label></td>
      </tr>
      <tr>
        <td align="center" colspan="3"><img id="priceimgid" src=""  style="cursor: pointer; width:600px; height: 150px;" /></td>
      </tr>
      </c:if>

    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/base/budget/tBApprovalBudgetRela.js"></script>