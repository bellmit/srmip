<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>供方名录失效年限</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function getSxnx() {
        var sxnx = $("#sxnx").val();
        if (sxnx == "") {
	      tip("请输入失效年限！");
        } else if(/^[0-9]*$/.test(sxnx)){
            return sxnx;
        }else{
            tip("请输入数字！");
        }
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmQualitySupplierController.do?doAdd" tiptype="1">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            供方失效年限: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="sxnx" name="sxnx" type="text" datatype="n1-2" style="width: 150px" class="inputxt" value='${sxnx}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">供方失效年限</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
</html>