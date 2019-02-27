<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>成果鉴定接收记录表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	function callBack(){
		frameElement.api.opener.reloadCurrentPage();
	}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAppraisalApplyController.do?doSend" callback="@Override callBack" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBAppraisalReceiveLogsPage.id }">
    <input id="operateTime" name="operateTime" type="hidden" value="${tBAppraisalReceiveLogsPage.operateTime }">
    <input id="operateStatus" name="operateStatus" type="hidden" value="${tBAppraisalReceiveLogsPage.operateStatus }">
    <input id="sendReceiveId" name="sendReceiveId" type="hidden" value="${tBAppraisalReceiveLogsPage.sendReceiveId}">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 接收人姓名: </label>
        </td>
        <td class="value">
          <input id="receiveUserid" name="receiveUserid" type="hidden" value="${tBAppraisalReceiveLogsPage.receiveUserid }">
          <input id="receiveUsername" name="receiveUsername" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalReceiveLogsPage.receiveUsername}'>
          <input id="receiveDepartid" name="receiveDepartid" type="hidden" value="${tBAppraisalReceiveLogsPage.receiveDepartid }">
          <input id="receiveDepartname" name="receiveDepartname" type="hidden" class="inputxt" value='${tBAppraisalReceiveLogsPage.receiveDepartname}'>
          <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName" isclear="true" inputTextname="receiveUserid,receiveUsername,receiveDepartid,receiveDepartname" idInput="receiveUserid"
            mode="single" departIdInput="receiveDepartid"></t:chooseUser>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">接收人姓名</label>
        </td>
      </tr>
      <%-- <tr>
        <td align="right">
          <label class="Validform_label"> 意见编码: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect name="suggestionCode" type="radio" codeType="1" code="SPYJ" id="suggestionCode" defaultVal="1"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">意见编码</label>
        </td>
      </tr> --%>
      <tr>
        <td align="right">
          <label class="Validform_label"> 意见内容: </label>
        </td>
        <td class="value">
          <textarea id="suggestionContent" style="width: 80%;" class="inputxt" rows="6" name="suggestionContent">${tBAppraisalReceiveLogsPage.suggestionContent}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">意见内容</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>