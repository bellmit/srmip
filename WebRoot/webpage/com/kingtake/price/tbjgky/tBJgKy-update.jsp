<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>科研价格库</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgKyController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgKyPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgKyPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgKyPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgKyPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgKyPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgKyPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgKyPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="5" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 种类: </label></td>
        <td class="value"><input id="fl" name="fl" type="text" style="width: 200px" class="inputxt" datatype="byterange" min="0" max="10" value='${tBJgKyPage.fl}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">种类</label></td>
        <td align="right"><label class="Validform_label"> 名称: </label></td>
        <td class="value"><input id="mc" name="mc" type="text" style="width: 300px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgKyPage.mc}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 规格型号: </label></td>
        <td class="value"><input id="ggxh" name="ggxh" type="text" style="width: 200px" class="inputxt" datatype="byterange" min="0" max="100" value='${tBJgKyPage.ggxh}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">规格型号</label></td>
        <td align="right"><label class="Validform_label"> 计量单位: </label></td>
        <td class="value"><input id="jldw" name="jldw" type="text" style="width: 300px" class="inputxt" datatype="byterange" min="0" max="30" value='${tBJgKyPage.jldw}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">计量单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 单价: </label></td>
        <td class="value"><input id="dj" name="dj" type="text" style="width: 200px; text-align: right;" class="easyui-numberbox" data-options="min:0,max:9999999999,precision:2,groupSeparator:','"
          class="inputxt" value='${tBJgKyPage.dj}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单价</label></td>
        <td align="right"><label class="Validform_label"> 采购单位: </label></td>
        <td class="value"><input id="cgdw" name="cgdw" type="text" style="width: 300px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgKyPage.cgdw}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">采购单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 采购时间: </label></td>
        <td class="value"><input id="cgsj" name="cgsj" type="text" style="width: 200px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgKyPage.cgsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">采购时间</label></td>
        <td align="right"><label class="Validform_label"> 生产厂家: </label></td>
        <td class="value"><input id="sccj" name="sccj" type="text" style="width: 300px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgKyPage.sccj}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">生产厂家</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 来源: </label></td>
        <td class="value" colspan="3"><input id="ly" name="ly" type="text" style="width: 95.5%" class="inputxt" value='${tBJgKyPage.ly}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">来源</label></td>
      </tr>
      <td align="right"><label class="Validform_label"> 来源项目: </label></td>
      <td class="value" colspan="3"><textarea id="lyxm" style="width: 95.5%;" class="inputxt" rows="6" name="lyxm" datatype="byterange" min="0" max="300">${tBJgKyPage.lyxm}</textarea> <span
        class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来源项目</label></td>
      <tr>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="beiz" style="width: 95.5%;" class="inputxt" rows="6" name="beiz" datatype="byterange" min="0" max="1000">${tBJgKyPage.beiz}</textarea> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgky/tBJgKy.js"></script>