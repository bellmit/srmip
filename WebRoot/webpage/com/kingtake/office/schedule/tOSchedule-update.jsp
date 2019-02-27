<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
<title>T_O_SCHEDULE</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
    var color = $("#color_hidden").val();
    if(color!=""){
        $("#color").combobox("setValue",color);
    }else{
        $("#color").combobox("setValue","2f96b4");
    }
});

function formatItem(row){
     var s = '<span><div style="background-color:#'+row.code+';height:10px;width:10px; display:inline-block;margin-right:5px;"></div>' + row.name + '</span>';
     return s;       
}
</script>
</head>
<body>
<div class="easyui-tabs" fit="true">
<div title="日程安排信息">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" 
    layout="table" action="tOScheduleController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tOSchedulePage.id }">
    <table cellpadding="0" cellspacing="1" class="formtable" >
       <tr>
        <td align="right">
        	<label class="Validform_label">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:<font color="red">*</font></label>
        </td> 
        <td class="value" align="left" colspan="3">
          <input id="title" name="title" datatype="*1-100" class="inputxt"
          	value="${tOSchedulePage.title}" type="text" style="width: 450px"> 
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">标题</label>
        </td>
       </tr>
       
       <tr>
        <td align="right"><label class="Validform_label">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:&nbsp;&nbsp;</label>
        </td>
        <td class="value" align="left" colspan="3">
          <input id="address" name="address" maxlength="75" value="${tOSchedulePage.address}" 
          	type="text" style="width: 450px" class="inputxt"> 
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">地址</label></td>
      </tr>
      
       <tr>
        <td align="right"><label class="Validform_label">关联项目:&nbsp;&nbsp;</label></td>
        <td class="value" align="left" colspan="3">
            <input type="hidden" id="projectId" name="projectId" value="${tOSchedulePage.projectId }">
            <input id="projectName" name="projectName" value="${projectName }" 
            	type="text" style="width: 200px" class="inputxt" readonly="readonly">
            <t:chooseProject inputId="projectId"  inputName="projectName" icon="icon-search" 
	        	title="关联项目" isclear="true" mode="single"></t:chooseProject>
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">关联项目</label>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label class="Validform_label">关联文号:&nbsp;&nbsp;</label></td>
        <td class="value" colspan="3"><input id="fileNum" name="fileNum" type="text" style="width:450px" class="inputxt"
          readonly="readonly" value="${tOSchedulePage.fileNum}"> <t:choose
            url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px"
            icon="icon-search" title="收发文列表列表" textname="mergeFileNum" inputTextname="fileNum" isclear="true"></t:choose> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">关联文号</label></td>
      </tr>
      
      <%-- <tr>
        <td align="right"><label class="Validform_label">关联人员:&nbsp;&nbsp;</label></td>
        <td class="value" align="left" colspan="3">
        	<input id="relateUserid" name="relateUserid" type="hidden" value="${tOSchedulePage.relateUserid }">
          	<input id="relateUsername" name="relateUsername" value='${tOSchedulePage.relateUsername}' 
          		type="text" style="width: 200px;" class="inputxt" readonly="readonly"></input>
	      	<t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" 
               	inputTextname="relateUserid,relateUsername" idInput="relateUserid"></t:chooseUser>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">关联人员</label>
        </td>
      </tr> --%>
     
      <tr>
        <td align="right"><label class="Validform_label">开始时间:<font color="red">*</font></label></td>
        <td class="value" align="left">
          <input id="beginTime" name="beginTime" type="text" style="width: 150px" class="Wdate" datatype="date"
                  value="<fmt:formatDate value='${tOSchedulePage.beginTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>" 
                  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endTime\')}'})">
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">开始时间</label>
        </td>
        <td align="right"><label class="Validform_label">结束时间:<font color="red">*</font></label>
        </td>
        <td class="value" align="left">
          <input id="endTime" name="endTime" type="text" style="width: 150px" class="Wdate" datatype="date"
                value="<fmt:formatDate value='${tOSchedulePage.endTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss'/>"
                onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'beginTime\')}'})"> 
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">结束时间</label>
        </td>
      </tr>
     
      <tr>
        <td align="right"><label class="Validform_label">是否公开:&nbsp;&nbsp;</label></td>
        <td class="value" align="left">
          <t:codeTypeSelect id="openStatus" name="openStatus" defaultVal="${tOSchedulePage.openStatus}" type="select" code="GKZT" codeType="1"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">是否公开</label>
        </td>
        <td align="right"><label class="Validform_label">日程类型:&nbsp;&nbsp;</label></td>
        <td class="value" align="left">
          <t:codeTypeSelect id="scheduleType" name="scheduleType" defaultVal="${tOSchedulePage.scheduleType}" type="select" code="SXLX" codeType="1"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">日程类型</label>
        </td>
      </tr>
      
       <tr>
        <td align="right"><label class="Validform_label">是否已完成:</label></td>
        <td class="value" align="left">
          <t:codeTypeSelect id="finishedFlag" name="finishedFlag" defaultVal="${tOSchedulePage.finishedFlag}" 
          	type="select" code="SFBZ" codeType="0"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">是否已完成</label>
        </td>
        <td align="right"><label class="Validform_label">展示颜色:&nbsp;&nbsp;</label></td>
        <td class="value" align="left">
          <input type="hidden" id="color_hidden" value="${tOSchedulePage.color }"  />
          <input id="color" name="color" class="easyui-combobox" data-options="url:'tBCodeTypeController.do?getComboboxListByKey&codeType=1&code=RCAPJJCD&validFlag=1',valueField:'code',textField:'name',formatter:formatItem" style="width:156px;">
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">展示颜色</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            提醒时间点:
          </label></td>
        <td class="value"><input id="warnTimePoint" name="warnTimePoint" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt: 'HH:mm'})" 
            value='${tOSchedulePage.warnTimePoint}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒时间点</label></td>
        <td align="right"><label class="Validform_label">提醒频率:</label></td>
        <td class="value"><t:codeTypeSelect id="warnFrequency" name="warnFrequency" codeType="0" code="WARNFREQUENCY" type="select" defaultVal="${tOSchedulePage.warnFrequency}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提醒频率</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:&nbsp;&nbsp;</label></td>
        <td class="value" align="left" colspan="3">
          <textarea id="content" style="width: 80%;" datatype="byterange" max="4000" min="0" class="inputxt" rows="7" 
          	name="content">${tOSchedulePage.content}</textarea>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">内容</label>
        </td>
      </tr>
    </table>
</t:formvalid> 
</div>
<div title="回复发送人"  data-options='href:"tOScheduleController.do?goMyResponseList&scheduleId=${tOSchedulePage.id }",fit:true' >
</div>
<div title="留言"  data-options='href:"tOScheduleController.do?goResponseList&scheduleId=${tOSchedulePage.id }",fit:true' >
</div>
</div>
</body>

<script type="text/javascript">
$(function(){
	/* $('#color').ColorPicker({
		onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
		},
		onBeforeShow: function () {
			$(this).ColorPickerSetColor(this.value);
		}
	})
	.bind('keyup', function(){
		$(this).ColorPickerSetColor(this.value);
	}); */
});

function reloadSchedule(data){
	frameElement.api.opener.reloadSchedule();
	frameElement.api.opener.showMsg(data.msg);
    frameElement.api.close();
}
</script>	