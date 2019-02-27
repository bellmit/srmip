<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>公用提醒信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    function setParam(){
       var select = $("input[name='warnWaySelect']"); 
       var warnWayArr = [];
       for(var i=0;i<select.length;i++){
           if($(select[i]).attr('checked')=="checked"){
               warnWayArr.push($(select[i]).val());
           }
       }
       if(warnWayArr.length>0){
           $("#warnWay").val(warnWayArr.join(","));
       }else{
           $("#warnWay").val("1");
       }
    }
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" tiptype="1" beforeSubmit="setParam"
    action="tOWarnController.do?doAddUpdate">
    <input id="id" name="id" type="hidden" value="${tOWarnPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tOWarnPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOWarnPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOWarnPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOWarnPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOWarnPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOWarnPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
        	<label class="Validform_label">
        	类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:
        	</label>
        </td>
        <td class="value"><t:codeTypeSelect id="warnType" name="warnType" codeType="0"
            code="WARNTYPE" type="select" defaultVal="${tOWarnPage.warnType}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">类型</label></td>
      </tr>
     <input id="formUrl" name="formUrl" type="hidden" datatype="*"
          value="${tOWarnPage.formUrl}" style="width: 150px" class="inputxt">
      <tr>
        <td align="right"><label class="Validform_label">提醒时限范围:</label></td>
        <td class="value" colspan="3"><input id="warnBeginTime" name="warnBeginTime" type="text"
          style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
          value='<fmt:formatDate value='${tOWarnPage.warnBeginTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒范围开始时间</label>~
          <input id="warnEndTime" name="warnEndTime" type="text" style="width: 150px" class="Wdate"
          onClick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"
          value='<fmt:formatDate value='${tOWarnPage.warnEndTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒范围结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;&nbsp;醒&nbsp;&nbsp;状&nbsp;&nbsp;态:&nbsp;</label></td>
        <td class="value"><t:codeTypeSelect id="warnStatus" name="warnStatus" codeType="0" type="select"
            code="WARNSTATUS" defaultVal="${tOWarnPage.warnStatus}">
          </t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒状态</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;&nbsp;醒&nbsp;&nbsp;频&nbsp;&nbsp;率&nbsp;:</label></td>
        <td class="value"><t:codeTypeSelect id="warnFrequency" name="warnFrequency" codeType="0"
            code="WARNFREQUENCY" type="select" defaultVal="${tOWarnPage.warnFrequency}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒频率</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;醒&nbsp;时&nbsp;间&nbsp;点:</label></td>
        <td class="value"><input id="warnTimePoint" name="warnTimePoint" type="text" style="width: 150px"
          class="Wdate" onClick="WdatePicker({dateFmt: 'HH:mm'})"
          value='${tOWarnPage.warnTimePoint}'> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒时间点</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;&nbsp;醒&nbsp;&nbsp;方&nbsp;&nbsp;式:&nbsp;</label></td>
        <td class="value"><input id="warnWay" type="hidden" name="warnWay"><t:codeTypeSelect id="" name="warnWaySelect" codeType="0" type="checkbox"
            code="WARNWAY" defaultVal="${tOWarnPage.warnWay}"></t:codeTypeSelect> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">提醒方式</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">提&nbsp;&nbsp;醒&nbsp;&nbsp;内&nbsp;&nbsp;容:&nbsp;</label></td>
        <td colspan="3" class="value"><textarea id="warnContent" name="warnContent" style="width: 85%" rows="4"
            name="warnContent">${tOWarnPage.warnContent}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">提醒内容</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/warn/tOWarn.js"></script>