<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>添加公共提醒</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    $(function(){
        //填充url
       var type = $("#type").val();
       var url = "";
       if(type=="1"||type=="3"){
       var jq = top.jQuery;
       var tab = jq('#maintabs').tabs("getSelected"); 
       url = jq(tab.panel('options').content).attr('src');
       if (url) {
            var idx = url.indexOf("&clickFunctionId");
            if (idx != -1) {
                url = url.substring(0, idx);
            }
        }
       }else if(type=="2"){
           var win = frameElement.api.opener;
           url = win.$("#contentFrame").attr("src");
       }
        $("#formUrl").val(url);
    });

    function setParam() {
        var select = $("input[name='warnWaySelect']");
        var warnWayArr = [];
        for (var i = 0; i < select.length; i++) {
            if ($(select[i]).attr('checked') == "checked") {
                warnWayArr.push($(select[i]).val());
            }
        }
        if (warnWayArr.length > 0) {
            $("#warnWay").val(warnWayArr.join(","));
        }
    }
</script>
</head>
<body style="overflow-x: hidden;">
<input id="type" type="hidden" value="${type}">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tOWarnController.do?doAddUpdate" beforeSubmit="setParam">
    <input id="id" name="id" type="hidden" value="${tOWarnPage.id }">
    <input id="warnStatus" name="warnStatus" type="hidden" value="0">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width: 15%;" align="right"><label class="Validform_label">类型:</label></td>
        <td style="width: 35%;" class="value"><t:codeTypeSelect id="warnType" name="warnType" codeType="0"
            code="WARNTYPE" type="select" defaultVal="${tOWarnPage.warnType}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">类型</label></td>
          <input id="formUrl" name="formUrl" type="hidden" value="${tOWarnPage.formUrl}" datatype="*" nullMsg="未获取到提醒地址，请确定添加提醒页面是否正确">
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提醒时间点:<font color="red">*</font></label></td>
        <td class="value"><input id="warnTimePoint" name="warnTimePoint" type="text" style="width: 150px"
          class="Wdate" onClick="WdatePicker({dateFmt: 'HH:mm'})" datatype="*"
          value='${tOWarnPage.warnTimePoint}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒时间点</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提醒频率:</label></td>
        <td class="value"><t:codeTypeSelect id="warnFrequency" name="warnFrequency" codeType="0"
            code="WARNFREQUENCY" type="select" defaultVal="${tOWarnPage.warnFrequency}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒频率</label></td>
      </tr>
      <tr>
       <td  align="right"><label class="Validform_label">接收人:<font color="red">*</font></label></td>
        <td class="value"><input type="hidden" id="receiveUserids" name="receiveUserids"
          value="${receiveUserIds }" datatype="*"> <input type="text" id="receiveUsernames" name="receiveUsernames"
          value="${receiveUserNames }" class="inputxt"> <t:chooseUser idInput="receiveUserids"
            inputTextname="receiveUserids,receiveUsernames" icon="icon-search" title="用户列表" textname="id,realName"
            isclear="true"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">接收人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提醒方式:<font color="red">*</font></label></td>
        <td class="value"><input id="warnWay" type="hidden" name="warnWay"><t:codeTypeSelect id="" name="warnWaySelect" codeType="0" type="checkbox"
            code="WARNWAY" defaultVal="1"></t:codeTypeSelect> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">提醒方式</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提醒时限范围:</label></td>
        <td class="value" colspan="3"><input id="warnBeginTime" name="warnBeginTime" type="text"
          style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"
          value='<fmt:formatDate value='${begin}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒范围开始时间</label>~
          <input id="warnEndTime" name="warnEndTime" type="text" style="width: 150px" class="Wdate"
          onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})"
          value='<fmt:formatDate value='${end}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒范围结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提醒内容:</label></td>
        <td colspan="3" class="value"><textarea id="warnContent" name="warnContent" style="width: 85%" rows="4" datatype="*2-100"
            name="warnContent">${tOWarnPage.warnContent}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">提醒内容</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/warn/tOWarn.js"></script>