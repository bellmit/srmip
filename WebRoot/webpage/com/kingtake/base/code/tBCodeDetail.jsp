<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>基础标准代码参数值表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('#tt').tabs({
            onSelect : function(title) {
                $('#tt .panel-body').css('width', 'auto');
            }
        });
        $(".tabs-wrap").css('width', '100%');
        //设置初始值
        var parentCode = $("#parentCode_").val();
        if (parentCode != null && parentCode != "") {
            $("#parentCode").val(parentCode);
        }
    });
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" action="tBCodeTypeController.do?saveDetail">
    <input id="id" name="id" type="hidden" value="${tBCodeDetailPage.id }">
    <input id="codeTypeId" name="codeTypeId" type="hidden" value="${tBCodeDetailPage.codeTypeId }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            代码:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="code" name="code" type="text" style="width: 150px" class="inputxt" datatype="*1-20" value="${tBCodeDetailPage.code }"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">代码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            名称:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*1-20" value="${tBCodeDetailPage.name }"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">名称</label></td>
      </tr>

      <tr>
        <td align="right"><label class="Validform_label">所属上级代码:</label></td>
        <td class="value"><input id="parentCode_" type="hidden" value="${tBCodeDetailPage.parentCodeDetail.id }"> <%-- <t:comboBox parentDetailList url="tBCodeTypeController.do?getCodeDetailList&codeTypeId=${tBCodeDetailPage.codeTypeId}" name="parentCode" text="name" id="id"></t:comboBox> --%>
          <select id="parentCode" name="parentCode">
            <option value="">----无----</option>
            <c:forEach items="${parentDetailList}" var="codeDetail" varStatus="status">
              <option value="${codeDetail.id}" <c:if test="${codeDetail.id==tBCodeDetailPage.parentCodeDetail.id}" >selected="selected"</c:if>>${codeDetail.name}</option>
            </c:forEach>
        </select> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属上级代码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            排序码:<font color="red">*</font>
          </label></td>
        <td class="value"><input id="sortFlag" name="sortFlag" type="text" style="width: 150px" class="inputxt" datatype="n" value="${tBCodeDetailPage.sortFlag }"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">排序码</label></td>
        <input name="validFlag" value="1" type="hidden">
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">备注:</label></td>
        <td class="value"><input id="memo" name="memo" type="text" style="width: 150px" class="inputxt" datatype="s1-66" ignore="ignore" value="${tBCodeDetailPage.memo }"> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/base/code/tBCodeType.js"></script>