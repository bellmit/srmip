<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>印章使用</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  function setAuditStatus(){
    $('#auditStatus').val($('#auditStatusSelect').val());
  }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tOSealUseController.do?doSave" tiptype="1" beforeSubmit="setAuditStatus">
    <input id="id" name="id" type="hidden" value="${tOSealUsePage.id }">
    <input id="operatorDepartid" name="operatorDepartid" type="hidden" value="${tOSealUsePage.operatorDepartid }">
    <input id="operatorId" name="operatorId" type="hidden" value="${tOSealUsePage.operatorId }">
    <input id="createBy" name="createBy" type="hidden" value="${tOSealUsePage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOSealUsePage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOSealUsePage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOSealUsePage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOSealUsePage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOSealUsePage.updateDate }">
    <table style="width: 720px;" cellpadding="0" cellspacing="1" border="0" class="formtable">
      <tr>
        <td colspan="6" align="center" >
          <label style="font-size: 24px;">印章使用审批表</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 编号: </label>
        </td>
        <td class="value" colspan="3">
          (
          <input id="numberWord" name="numberWord" type="text" style="width: 50px" class="inputxt" value='${tOSealUsePage.numberWord}'>
          )
          <label>字 第</label>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">字</label>
          <input id="numberSymbol" name="numberSymbol" type="text" style="width: 50px" class="inputxt" value='${tOSealUsePage.numberSymbol}'>
          <label>号</label>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">号</label>
        </td>
        <td class="value" colspan="2" align="right">
          <input id="applyDate" name="applyDate" type="text" style="width: 130px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tOSealUsePage.applyDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申请时间</label>
        </td>
      </tr>
      <tr>
        <td align="right" width="100px">
          <label class="Validform_label"> 材料名称: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="5">
          <input id="materialName" name="materialName" type="text" style="width: 580px" datatype="*1-200" class="inputxt" value='${tOSealUsePage.materialName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">材料名称</label>
        </td>
      </tr>
      <tr>
      <td align="right" width="100px">
          <label class="Validform_label"> 流水号: </label>
        </td>
        <td class="value" colspan="5">
          <input id="searialNum" name="searialNum" type="text" style="width: 120px" class="inputxt" disabled="disabled" value='${tOSealUsePage.searialNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">流水号</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 页数: </label>
        </td>
        <td class="value">
          <input id="pagesNum" name="pagesNum" type="text" style="width: 120px" class="easyui-numberbox" data-options="min:0" datatype="n0-4"  value='${tOSealUsePage.pagesNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">页数</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 份数: </label>
        </td>
        <td class="value">
          <input id="copiesNum" name="copiesNum" type="text" style="width: 120px" class="easyui-numberbox" data-options="min:0" datatype="n0-4"  value='${tOSealUsePage.copiesNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">份数</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 密级: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" extendParam="{'style':'width:195px;'}" codeType="0" code="XMMJ" defaultVal="${tOSealUsePage.secretDegree}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">密级</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 印章类型: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="sealType" name="sealType" type="select" codeType="1" code="YZLX" extendParam="{'style':'width:126px;'}" defaultVal="${tOSealUsePage.sealType}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">印章类型</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 经办人: </label>
        </td>
        <td class="value" colspan="3">
          <input id="operatorName" name="operatorName" type="text" style="width: 380px" readonly="true" class="inputxt" value='${tOSealUsePage.operatorName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">经办人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 主要内容: </label>
        </td>
        <td class="value" colspan="5">
          <textarea id="mainContent" name="mainContent" style="width: 580px; height: 100px;" datatype="*0-2000" ignore="ignore"></textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主要内容</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 依据: </label>
        </td>
        <td class="value" colspan="5">
          <input id="accordings" name="accordings" type="text" style="width: 580px" class="inputxt" value='${tOSealUsePage.accordings}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">依据</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 流程状态: </label>
        </td>
        <td class="value" colspan="5">
          <t:codeTypeSelect code="SPZT" codeType="1" id="auditStatusSelect" name="" type="select" defaultVal="${tOSealUsePage.auditStatus }" extendParam="{'disabled':'true'}"></t:codeTypeSelect>
          <input id="auditStatus" name="auditStatus" value="${tOSealUsePage.auditStatus }" type="hidden" />
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/seal/tOSealUse.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>