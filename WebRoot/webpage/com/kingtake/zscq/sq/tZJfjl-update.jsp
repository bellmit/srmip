<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>缴费记录</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
        var role = $("#role").val();
        var jnfs = $("#jnfs_hid").val();
        if (jnfs != "") {
            $("#jnfs").val(jnfs);
        }else{
            if(role=='fmr'){
                $("#jnfs").val('1');
            }else{
                $("#jnfs").val('2');
            }
        }
        
    });

    function clearXm() {
        $("#hkrmc").val('');
        $("#hkrId").val('');
    }

    //回调函数
    function formCallback(data) {
        var dialog = W.$.dialog.list['jfjlDialog'].content;
        if (data.success) {
            dialog.tip(data.msg);
            dialog.reloadJfjl();
            frameElement.api.close();
        } else {
            tip(data.msg);
        }
    }
</script>
</head>
<body>
  <div style="margin: 0 auto; width: 600px;">
    <t:formvalid formid="formobj" dialog="true" layout="table" action="tZSqController.do?doJfjlUpdate" tiptype="1" callback="@Override formCallback">
      <input id="id" name="id" type="hidden" value="${jfjlPage.id }">
      <input id="sqId" name="sqId" type="hidden" value="${jfjlPage.sqId }">
      <input id="role" type="hidden" value="${role }">
      <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right"><label class="Validform_label"> 缴纳方式: </label></td>
          <td class="value"><input type="hidden" id="jnfs_hid" value="${jfjlPage.jnfs}"> <select id="jnfs" name="jnfs">
              <c:choose>
                <c:when test="${opt eq 'add' && role eq 'fmr'}">
                  <option value="1">发明人缴纳</option>
                </c:when>
                <c:otherwise>
                  <option value="1">发明人缴纳</option>
                  <option value="2">办公室缴纳</option>
                </c:otherwise>
              </c:choose>
          </select> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">缴纳方式</label></td>
        </tr>
        <c:if test="${role eq 'depart' }">
          <tr>
            <td align="right"><label class="Validform_label">
                费用审批时间<font color="red">(机关录入)</font>:
              </label></td>
            <td class="value"><input id="fyspsj" name="fyspsj" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date"
                value='<fmt:formatDate value='${jfjlPage.fyspsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
                style="display: none;"> 费用审批时间</label></td>
          </tr>
        </c:if>
        <tr>
          <td align="right"><label class="Validform_label">
              汇款人账号: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="hkrzh" name="hkrzh" type="text" style="width: 150px" class="inputxt" value='${jfjlPage.hkrzh}' datatype="*1-100"> <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">汇款人账号</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              汇款人: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="hkrId" name="hkrId" type="hidden" value="${jfjlPage.hkrId }"> <input id="hkrmc" name="hkrmc" type="text" style="width: 150px" class="inputxt"
              value='${jfjlPage.hkrmc}' datatype="*1-10" onfocus="clearXm();"> <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" inputTextname="hkrId,hkrmc"
              idInput="hkrId" mode="single"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">汇款人</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              报销凭证上交时间: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="bxpzsjsj" name="bxpzsjsj" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date"
              value='<fmt:formatDate value='${jfjlPage.bxpzsjsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
              style="display: none;"> 报销凭证上交时间</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              缴纳金额: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="jnje" name="jnje" type="text" style="width: 150px" class="easyui-numberbox inputxt" value='${jfjlPage.jnje}'
              data-options="min:0,max:999999999,groupSeparator:','" datatype="*1-10"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">缴纳金额</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              联系方式: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="lxfs" name="lxfs" type="text" style="width: 150px" class="inputxt" value='${jfjlPage.lxfs}' datatype="*1-100"> <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系方式</label></td>
        </tr>
      </table>
    </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>