<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>推荐鉴定委员会成员</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	function callback(data){
		var win = W.$.dialog.list['meetingDialog'].content;
		win.reloadMemberTables();
		frameElement.api.close();
	}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAppraisalMeetingController.do?addMember" callback="@Override callback" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tBAppraisalMemberPage.id }">
    <input  id="meetingId" name="meetingId" type="hidden" value="${meetingId}">
    <input  id="memberType" name="memberType" type="hidden" value="${memberType}">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 工作单位:<font color="red">*</font></label>
        </td>
        <td class="value">
          <input id="workUnit" name="workUnit" type="text" datatype="*" style="width: 150px" class="inputxt" value='${tBAppraisalMemberPage.workUnit}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">工作单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 姓名:<font color="red">*</font></label>
        </td>
        <td class="value">
          <input id="memberNames" name="memberNames" type="text" datatype="*" style="width: 150px" class="inputxt" plaseholder="可填多个人名(以'，'分隔)" value='${tBAppraisalMemberPage.memberName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">姓名</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>