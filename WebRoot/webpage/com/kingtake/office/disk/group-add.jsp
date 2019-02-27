<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>群组</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function reloadTree(data){
    var win = frameElement.api.opener;
    win.tip(data.msg);
    if(data.success){
        win.loadTree();
        frameElement.api.close();
    }
}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tODiskController.do?doAddGroup" tiptype="1" callback="@Override reloadTree">
    <input id="id" name="id" type="hidden" value="${tOGroupPage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            群组名:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="name" name="name" type="text" style="width: 150px" class="inputxt" value="${tOGroupPage.name}"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">名称</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 群备注: &nbsp;</label></td>
        <td class="value"><textarea id="remark" name="remark" cols="3" rows="3" style="width: 450px; height: 150px;" datatype="byterange" max="4000" min="0">${tOGroupPage.remark}</textarea> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">群备注</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
</html>