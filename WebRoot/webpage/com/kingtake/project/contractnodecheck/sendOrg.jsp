<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<head>
<title>合同节点考核</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--   <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script> -->
<!--   <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script> -->
<script type="text/javascript">
	//编写自定义JS代码
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmContractNodeCheckController.do?doSend&nodeCheckId=${nodeCheckId}">
		<table cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 意见类型: </label></td>
				<td class="value"><t:codeTypeSelect id="suggestionType" name="suggestionType" defaultVal="1" type="select" code="JDKHLX" codeType="1"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">填写意见</label></td>
			</tr>
			<tr>
				<td align="right" width="80px"><label class="Validform_label">接&nbsp;&nbsp;收&nbsp;&nbsp;人:<font color="red">*</font>
				</label></td>
				<td class="value"><input type="hidden" name="receiverid" id="receiverid"> <input type="hidden" name="departId" id="departId"> <input type="hidden" name="departName" id="departName"> <input id="realName" style="width: 120px;" class="inputxt" name="realName" readonly="readonly" datatype="*"></input> <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName" isclear="true" inputTextname="receiverid,realName,departId,departName" idInput="receiverid" departIdInput="departId"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">接收人</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 填写意见: </label></td>
				<td class="value"><textarea id="leaderReview" name="leaderReview" datatype="*1-100" style="width: 300px;" rows="5" class="inputxt"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">填写意见</label></td>
			</tr>
		</table>
	</t:formvalid>
</body>
