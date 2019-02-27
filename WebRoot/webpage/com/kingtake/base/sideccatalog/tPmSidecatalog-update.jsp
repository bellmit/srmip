<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目模块配置表</title>
<t:base type="ckfinder,ckeditor,jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    //编写自定义JS代码
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmSidecatalogController.do?doAddUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tPmSidecatalogPage.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" border="0" class="formtable">
      <tr>
        <td align="right" width="70px"><label class="Validform_label"> 类型: </label></td>
        <td class="value" colspan="5"><input type="radio" id="baseInfo" name="moduleType" value="1"
            <c:if test="${tPmSidecatalogPage.moduleType eq '1' or empty tPmSidecatalogPage.moduleType }">checked="checked"</c:if>>基本信息 <input type="radio" id="processMgr" name="moduleType"
            value="2" <c:if test="${tPmSidecatalogPage.moduleType eq '2' }">checked="checked"</c:if>>过程管理 <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">类型</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 标题: </label></td>
        <td class="value" colspan="5"><input id="title" name="title" type="text" style="width: 500px" class="inputxt" value='${tPmSidecatalogPage.title}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 页面url: </label></td>
        <td class="value" colspan="5"><input id="url" name="url" type="text" style="width: 500px" class="inputxt" value='${tPmSidecatalogPage.url}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">页面url</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 等级: </label></td>
        <td class="value" width="150px"><input id="nodelevel" name="level" type="text" style="width: 100px" class="inputxt" value='${tPmSidecatalogPage.level}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">等级</label></td>
        <td align="right" width="80px"><label class="Validform_label"> 序号: </label></td>
        <td class="value"><input id="seriaNumber" name="index" type="text" style="width: 100px" class="inputxt" value='${tPmSidecatalogPage.index}'> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">序号</label></td>
        <td align="right" width="90"><label class="Validform_label"> 业务代码: </label></td>
        <td class="value"><input id="businessCode" name="businessCode" type="text" style="width: 100px" class="inputxt" value='${tPmSidecatalogPage.businessCode}'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">业务代码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">宽度: </label></td>
        <td class="value" width="150px"><input id="width" name="width" type="text" style="width: 100px" class="inputxt easyui-numberbox" value='${tPmSidecatalogPage.width}'
            data-options="min:0,max:99999"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">宽度</label></td>
        <td align="right" ><label class="Validform_label"> 高度: </label></td>
        <td class="value" colspan="3"><input id="height" name="height" type="text" style="width: 100px" class="inputxt easyui-numberbox" value='${tPmSidecatalogPage.height}'
            data-options="min:0,max:99999"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">高度</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="5">
          <%-- <textarea id="memo" name="memo" style="width: 500px;height:100px;" class="inputxt">${tPmSidecatalogPage.memo}</textarea> --%> <t:ckeditor name="memo" isfinder="true"
            value="${tPmSidecatalogPage.memo}" type="width:750"></t:ckeditor> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/base/sideccatalog/tPmSidecatalog.js"></script>