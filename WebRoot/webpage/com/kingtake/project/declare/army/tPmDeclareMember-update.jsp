<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>项目人员信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
 </head>
 
 <body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
	callback="@Override reloadMember"
	action="tPmDeclareMemberController.do?doAdd" tiptype="1">
	<input id="id" name="id" type="hidden" value="${tPmDeclareMember.id }" />
	<input id="projDeclareId" name="projDeclareId" type="hidden" value="${tPmDeclareMember.projDeclareId }" />
	<table style="width: 600px; background-color: white;" cellpadding="0" cellspacing="1" class="formtable" >
		<tr>
			<td align="right">
				<label class="Validform_label">姓名:<font color="red">*</font></label>
			</td>
			<td class="value" colspan="3">
				<input id="userid" name="userid" type="hidden" value="${tPmDeclareMember.userid }" />
				<input id="name" name="name" type="text" style="width: 150px" class="inputxt"
					value="${tPmDeclareMember.name }" maxlength="18" />
				<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="userid"
					textname="id,realName"  inputTextname="userid,name" fun="uidChange"></t:chooseUser>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">姓名</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">性别:</label>
			</td>
			<td class="value">
		     	<t:codeTypeSelect id="sex" name="sex" type="select" codeType="0" code="SEX" 
		     		defaultVal="${tPmDeclareMember.sex }"></t:codeTypeSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">性别</label>
			</td>
			<td align="right">
				<label class="Validform_label">出生日期:</label>
			</td>
			<td class="value">
			   	<input id="birthday" name="birthday" type="text" style="width: 150px" 
	      				class="Wdate" onClick="WdatePicker()" 
	      				value="<fmt:formatDate value='${tPmDeclareMember.birthday}' type='date' pattern='yyyy-MM-dd'/>" />    
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">出生年月</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">学位:</label>
			</td>
			<td class="value">
		     	<t:codeTypeSelect id="degree" name="degree" type="select" codeType="0" 
		     		code="XWLB" defaultVal="${tPmDeclareMember.degree }"></t:codeTypeSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">学位</label>
			</td>
			<td align="right">
				<label class="Validform_label">军线电话/手机:</label>
			</td>
			<td class="value">
		     	<input id="contactPhone" name="contactPhone" type="text" style="width: 150px" 
		     		class="inputxt" value="${tPmDeclareMember.contactPhone }" max/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">军线电话/手机</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">所属单位:</label>
			</td>
			<td class="value">
				<input type="hidden" name="superUnit" id= "superUnit" value="${tPmDeclareMember.superUnit }" />
		     	<t:departComboTree id="cc" value="${tPmDeclareMember.superUnit }" idInput="superUnit" 
		     		lazy="true" width="155" height="27"></t:departComboTree>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">所属单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">邮编:</label>
			</td>
			<td class="value">
		     	<input id="postCode" name="postCode" type="text" style="width: 150px" class="easyui-numberbox inputxt" 
		     		value="${tPmDeclareMember.postCode }" data-options="min:0, max:999999"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">邮编</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">通信地址:</label>
			</td>
			<td class="value">
		     	<input id="postalAddress" name="postalAddress" type="text" style="width: 150px" 
		     		class="inputxt" value="${tPmDeclareMember.postalAddress }" maxlength="40"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">通信地址</label>
			</td>
			<td align="right">
				<label class="Validform_label">职称职务:</label>
			</td>
			<td class="value">
				<t:codeTypeSelect id="position" name="position" type="select" codeType="0" 
					code="PROFESSIONAL" defaultVal="${tPmDeclareMember.position }"></t:codeTypeSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">职称职务</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">任务分工:</label>
			</td>
			<td class="value">
		     	<input id="taskDivide" name="taskDivide" type="text" style="width: 150px" class="inputxt" 
		     		value="${tPmDeclareMember.taskDivide }" maxlength="1000"/>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">任务分工</label>
			</td>
			<td align="right">
				<label class="Validform_label">负责人标志:</label>
			</td>
			<td class="value">
		     	<t:codeTypeSelect id="projectManager" name="projectManager" type="select" 
		     		codeType="0" code="SFBZ" defaultVal="${tPmDeclareMember.projectManager }"></t:codeTypeSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">负责人标志</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>


<script type="text/javascript">
$(document).ready(function(){
	var id = $("#id").val();
	if(id){
		$("#formobj").attr("action", "tPmDeclareMemberController.do?doUpdate");
	}
});

function uidChange(){
	var uid = $('#userid').val();
	$.ajax({
		type : 'post',
		async : false,
		url:'tPmProjectMemberController.do?findInfoById',
		data:{'uid':uid},
		success : function(data) {
			var d = $.parseJSON(data);
			var user = d.attributes.user;
			$('#sex').val(user.sex);
			$('#birthday').val(d.attributes.birthday ? d.attributes.birthday.substring(0, 10) : "");
			$('#contactPhone').val(user.officePhone ? user.officePhone : user.mobilePhone);
		}
	})
}

function reloadMember(data){
	frameElement.api.opener.reloadMember();
	frameElement.api.opener.showMsg(data.msg);
    frameElement.api.close();
}

</script>
