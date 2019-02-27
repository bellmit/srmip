<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>价格库系统_外场试验费标准</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  </script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzWcsyController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBJgbzWcsyPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzWcsyPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzWcsyPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzWcsyPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzWcsyPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzWcsyPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzWcsyPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 差旅费: </label></td>
        <td class="value"><input id="clf" name="clf" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="100" value='${tBJgbzWcsyPage.clf}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">差旅费</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 外场试验人员补助费: </label></td>
        <td class="value"><textarea id="wcsyrybzf" style="width: 400px;" class="inputxt" rows="4" name="wcsyrybzf" datatype="byterange" min="0" max="2000">${tBJgbzWcsyPage.wcsyrybzf}</textarea> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">外场试验人员补助费</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 动态飞行架次费用: </label></td>
        <td class="value"><textarea id="dtfxjcf" style="width: 400px;" class="inputxt" rows="4" name="dtfxjcf" datatype="byterange" min="0" max="500">${tBJgbzWcsyPage.dtfxjcf}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">动态飞行架次费用</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 风洞试验: </label></td>
        <td class="value"><textarea id="fdsy" style="width: 400px;" class="inputxt" rows="4" name="fdsy" datatype="byterange" min="0" max="500">${tBJgbzWcsyPage.fdsy}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">风洞试验</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 靶场时间标准: </label></td>
        <td class="value"><textarea id="bcsjbz" style="width: 400px;" class="inputxt" rows="4" name="bcsjbz" datatype="byterange" min="0" max="500">${tBJgbzWcsyPage.bcsjbz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">靶场时间标准</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 地方船只使用标准: </label></td>
        <td class="value"><textarea id="dfczsybz" style="width: 400px;" class="inputxt" rows="4" name="dfczsybz" datatype="byterange" min="0" max="500">${tBJgbzWcsyPage.dfczsybz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">地方船只使用标准</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 运输费: </label></td>
        <td class="value"><textarea id="ysf" style="width: 400px;" class="inputxt" rows="4" name="ysf" datatype="byterange" min="0" max="500">${tBJgbzWcsyPage.ysf}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">运输费</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzwcsy/tBJgbzWcsy.js"></script>