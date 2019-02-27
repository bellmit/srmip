<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>器材设备价格库</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgQcsbController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgQcsbPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgQcsbPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgQcsbPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgQcsbPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgQcsbPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgQcsbPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgQcsbPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="10" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 平台名称: </label></td>
        <td class="value" colspan="3"><input id="ptmc" name="ptmc" type="text" style="width: 600px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgQcsbPage.ptmc}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">平台名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 系统设备名称: </label></td>
        <td class="value" colspan="3"><input id="xtsbmc" name="xtsbmc" type="text" style="width: 600px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgQcsbPage.xtsbmc}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">系统设备名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 器材设备名称: </label></td>
        <td class="value" colspan="3"><input id="qcsbmc" name="qcsbmc" type="text" style="width: 600px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgQcsbPage.qcsbmc}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">器材设备名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 规格型号: </label></td>
        <td class="value"><input id="xhgg" name="xhgg" type="text" style="width: 255px" class="inputxt" datatype="byterange" min="0" max="100" value='${tBJgQcsbPage.xhgg}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">规格型号</label></td>
        <td align="right"><label class="Validform_label"> 计量单位: </label></td>
        <td class="value"><input id="jldw" name="jldw" type="text" style="width: 175px" class="inputxt" datatype="byterange" min="0" max="30" value='${tBJgQcsbPage.jldw}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">计量单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 承制单位: </label></td>
        <td class="value" colspan="3"><input id="czdw" name="czdw" type="text" style="width: 600px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgQcsbPage.czdw}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">承制单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 单价: </label></td>
        <td class="value"><input id="dj" name="dj" type="text" style="width: 255px; text-align: right;" class="easyui-numberbox" 
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" class="inputxt" value='${tBJgQcsbPage.dj}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">单价</label></td>
        <td align="right"><label class="Validform_label"> 采购时间: </label></td>
        <td class="value"><input id="cgsj" name="cgsj" type="text" style="width: 175px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgQcsbPage.cgsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">采购时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 审价单位: </label></td>
        <td class="value" colspan="3"><input id="sjdw" name="sjdw" type="text" style="width: 600px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgQcsbPage.sjdw}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">审价单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 合同履行监督单位: </label></td>
        <td class="value" colspan="3"><input id="htlxjddw" name="htlxjddw" type="text" style="width: 600px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgQcsbPage.htlxjddw}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同履行监督单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="beiz" style="width: 600px;" class="inputxt" rows="6" name="beiz" datatype="byterange" min="0" max="1000">${tBJgQcsbPage.beiz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">备注</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgqcsb/tBJgQcsb.js"></script>