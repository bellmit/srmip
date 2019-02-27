<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>项目组成员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
    //编写自定义JS代码
    function uidChange(){
    	var uid = $('#userId').val();
    	var tpId = $('#tpId').val();
    	var id = $('#id').val();
    	$.ajax({
    		async : false,
			cache : false,
    		url:'tPmProjectMemberController.do?ifMemberExist',
    		data:{'uid':uid,'pid':tpId,'id':id},
    		success : function(data) {
    			var d = $.parseJSON(data);
    			if(!d.obj){
    		    	$.ajax({
    		    		async : false,
    					cache : false,
    		    		url:'tPmProjectMemberController.do?findInfoById',
    		    		data:{'uid':uid},
    		    		success : function(data) {
    		    			var d = $.parseJSON(data);
    		    			$('#sex').val(d.attributes.user.sex);
    		    			$('#birthday').val(d.attributes.birthday);
    		    			$('#position').val(d.attributes.user.duty);
    		    			$('#cc').combotree('setValue', $('#superUnitName').val());
    		    		}
    		    	});
    			}else{
    				$.Showmsg('成员'+$('#name').val()+'已存在，请重新选择！');
    				$('#userId').val("");
    				$('#name').val("");
    			}
    		}
    	});
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
    action="tPmProjectMemberController.do?doUpdate" tiptype="1">
    <input id="createBy" name="createBy" type="hidden" value="${tPmProjectMemberPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmProjectMemberPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmProjectMemberPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmProjectMemberPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmProjectMemberPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmProjectMemberPage.updateDate }">
    <input id="id" name="id" type="hidden" value="${tPmProjectMemberPage.id }">
    <input type="hidden" id="tpId" name="tpId" value="${projectId}">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width: 20%;" align="right"><label class="Validform_label"> 姓名: <font color="red">*</font></label></td>
        <td style="width: 80%;" class="value">
        <input id="userId" name="userId" type="hidden" value="${tPmProjectMemberPage.userId}"> 
        <input id="name" name="name" type="text" style="width: 150px" value="${tPmProjectMemberPage.name}" class="inputxt" datatype="*2-12" readonly="readonly">
        <t:chooseUser idInput="userId" icon="icon-search" title="选择人员" inputTextname="userId,name,superUnitId" textname="id,realName,departId" isclear="true" fun="uidChange"></t:chooseUser>
           <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">姓名</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 性别: <font color="red">*</font></label></td>
        <td class="value"><t:codeTypeSelect id="sex" name="sex" type="select" codeType="0" labelText="---请选择---" extendParam="{'datatype': '*' }"
            code="SEX" defaultVal="${tPmProjectMemberPage.sex}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">性别</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 出生年月:&nbsp;&nbsp;&nbsp;</label></td>
        <td class="value">
        	<input id="birthday" name="birthday" type="text" style="width: 150px" class="Wdate"
         		readonly="readonly" onClick="WdatePicker()"
          		value='<fmt:formatDate value='${tPmProjectMemberPage.birthday}' type="date" pattern="yyyy-MM-dd"/>'> 
          	<span class="Validform_checktip"></span> 
          	<label class="Validform_label" style="display: none;">出生年月</label>
      	</td>
      <tr>
        <td align="right"><label class="Validform_label"> 学位: <font color="red">*</font></label></td>
        <td class="value"><t:codeTypeSelect name="degree" type="select" codeType="0" labelText="---请选择---" extendParam="{'datatype': '*' }"
            code="XWLB" id="degree" defaultVal="${tPmProjectMemberPage.degree}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">学位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 职称职务:<font color="red">*</font></label></td>
        <td class="value"><t:codeTypeSelect id="position" name="position" labelText="---请选择---" extendParam="{'datatype': '*' }"
            type="select" codeType="0" code="PROFESSIONAL" defaultVal="${tPmProjectMemberPage.position}"></t:codeTypeSelect> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">职称职务</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 任务分工:&nbsp;&nbsp;&nbsp;</label></td>
        <td class="value"><textarea rows="3" id="taskDivide" name="taskDivide"
            style="width: 250px">${tPmProjectMemberPage.taskDivide}</textarea> <span class="Validform_checktip"></span> <label class="Validform_label"
          style="display: none;">任务分工</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 所属单位:&nbsp;&nbsp;&nbsp;</label></td>
        <td class="value"><input id="superUnitId" name="superUnitId" type="hidden" value="${tPmProjectMemberPage.superUnit.id}"> 
        <input id="superUnitName" name="superUnitName" width="250" type="text" readonly="readonly" value="${tPmProjectMemberPage.superUnit.departname}">
<%--           <t:departComboTree id="cc" idInput="superUnitId" width="250" value="${tPmProjectMemberPage.superUnit.id}" lazy="false"></t:departComboTree>  --%>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属单位</label></td>
      </tr> 
      <tr>
        <td align="right"><label class="Validform_label"> 是否负责人:</label></td>
        <td class="value"><t:codeTypeSelect id="projectManager"
            name="projectManager" type="radio" codeType="0" code="SFBZ" defaultVal="${tPmProjectMemberPage.projectManager}" extendParam="{'disabled':'disabled'}"></t:codeTypeSelect> <span
          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">是否负责人</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/member/tPmProjectMember.js"></script>