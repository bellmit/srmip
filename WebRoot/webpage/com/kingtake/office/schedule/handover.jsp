<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
<title>生成交班材料</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
</head>
<body>

<t:formvalid formid="handoverForm" dialog="true" usePlugin="password" callback="@Override afterSubHandover"
    layout="table" action="tOScheduleController.do?doHandover" tiptype="1">
    <table cellpadding="0" cellspacing="1" class="formtable" >
      <%-- <tr>
        <td align="right"><label class="Validform_label">接收人:<font color="red">*</font></label></td>
        <td class="value" colspan="3">
          <input id="relateUserid" name="relateUserid" type="hidden" >
          <input id="relateUsername" name="relateUsername" type="text" style="width: 200px" 
          		datatype="*" class="inputxt" readonly="readonly"> 
          <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" mode="single"
               	inputTextname="relateUserid,relateUsername" idInput="relateUserid"></t:chooseUser>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">接收人</label>
        </td>
      </tr> --%>
      
      <tr>
        <td align="right"><label class="Validform_label">开始时间:<font color="red">*</font></label></td>
        <td class="value">
          <input id="beginTime" name="beginTime" type="text" style="width: 150px" class="Wdate" 
                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endTime\')}'})">
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">开始时间</label>
        </td>
        <td align="right"><label class="Validform_label">结束时间:<font color="red">*</font></label>
        </td>
        <td class="value">
          <input id="endTime" name="endTime" type="text" style="width: 150px" class="Wdate" 
                onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'beginTime\')}'})">  
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">结束时间</label>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label class="Validform_label">是否公开:<font color="red">*</font></label></td>
        <td class="value">
          <t:codeTypeSelect id="openStatus" name="openStatus" type="select" code="GKZT" codeType="1"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">是否公开</label>
        </td>
        <td align="right"><label class="Validform_label">日程类型:<font color="red">*</font></label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="scheduleType" name="scheduleType" type="select" code="SXLX" codeType="1"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">日程类型</label>
        </td>
      </tr>
      
    </table>
</t:formvalid>
</body>

<script type="text/javascript">
$(function() {
    var option1 = $("<option value='all'>全部</option>");
    option1.prependTo($("#openStatus"));
    $("#openStatus").val("all");
    
    var option2 = $("<option value='all'>全部</option>");
    option2.prependTo($("#scheduleType"));
    $("#scheduleType").val("all");
});

function afterSubHandover(data){
	frameElement.api.opener.showMsg(data.msg);
	frameElement.api.close();
}
</script>	