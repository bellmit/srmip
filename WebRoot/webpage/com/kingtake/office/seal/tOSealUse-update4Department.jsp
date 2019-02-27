<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
.Wdate {
	height: 14px;
}
</style>
<script type="text/javascript">
  $(document).ready(function() {
    if (location.href.indexOf("load=detail") != -1) {
      //无效化所有表单元素，只能进行查看
      $(":input").attr("disabled", "true");
    }
  });
</script>
</head>
<body>
  <div style="height: 500px; width: 100%;">
    <div style="position: fixed; top: 30; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
      <h1 align="center">印章使用申请</h1>
      <span> </span>
    </div>
    <br>
    <br>
    <input id="auditStatus" type="hidden" value="${tOSealUsePage.auditStatus}">
    <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tOSealUseController.do" beforeSubmit="checkDate">
      <input id="id" name="id" type="hidden" value="${tOSealUsePage.id }">
      <input id="createBy" name="createBy" type="hidden" value="${tOSealUsePage.createBy }">
      <input id="createName" name="createName" type="hidden" value="${tOSealUsePage.createName }">
      <input id="createDate" name="createDate" type="hidden" value="${tOSealUsePage.createDate }">
      <input id="updateBy" name="updateBy" type="hidden" value="${tOSealUsePage.updateBy }">
      <input id="updateName" name="updateName" type="hidden" value="${tOSealUsePage.updateName }">
      <input id="updateDate" name="updateDate" type="hidden" value="${tOSealUsePage.updateDate }">
      <table style="width: 700px;" cellpadding="0" cellspacing="1" border="0" class="formtable">
        <caption>
          <strong style="font-size: xx-large;">印章使用审批表</strong>
        </caption>
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
          <input id="applyDate" name="applyDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tOSealUsePage.applyDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申请时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 材料名称: </label>
        </td>
        <td class="value" colspan="5">
          <input id="materialName" name="materialName" type="text" style="width: 608px" class="inputxt" value='${tOSealUsePage.materialName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">材料名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 页数: </label>
        </td>
        <td class="value">
          <input id="pagesNum" name="pagesNum" type="text" style="width: 150px" class="inputxt" value='${tOSealUsePage.pagesNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">页数</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 份数: </label>
        </td>
        <td class="value">
          <input id="copiesNum" name="copiesNum" type="text" style="width: 150px" class="inputxt" value='${tOSealUsePage.copiesNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">份数</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 密级: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="secretDegree" name="secretDegree" type="select" codeType="0" code="XMMJ" defaultVal="${tOSealUsePage.secretDegree}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">密级</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 印章类型: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="sealType" name="sealType" type="select" codeType="1" code="YZLX" defaultVal="${tOSealUsePage.sealType}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">印章类型</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 经办人: </label>
        </td>
        <td class="value" colspan="3">
          <input id="operatorName" name="operatorName" type="text" style="width: 370px" readonly="true" class="inputxt" value='${tOSealUsePage.operatorName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">经办人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 主要内容: </label>
        </td>
        <td class="value" colspan="5">
          <textarea id="mainContent" name="mainContent" style="width: 608px; height: 100px;" datatype="*0-2000" ignore="ignore"></textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主要内容</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 依据: </label>
        </td>
        <td class="value" colspan="5">
          <input id="accordings" name="accordings" type="text" style="width: 608px" class="inputxt" value='${tOSealUsePage.accordings}'>
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
  </div>
</body>
<script src="webpage/com/kingtake/office/seal/tOSealUse.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>