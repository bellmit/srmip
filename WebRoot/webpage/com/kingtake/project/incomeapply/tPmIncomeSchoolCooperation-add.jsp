<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>校内协作经费录入</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function getSchoolCooperationInfo(){
        var schoolCooperationData = [];
        schoolCooperationData[0] = $("#projectId").val();
        schoolCooperationData[1] = $("#projectName").val();
        schoolCooperationData[2] = $("#applyAmount").val();
        return schoolCooperationData;
    }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmIncomePage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tPmIncomePage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPmIncomePage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPmIncomePage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPmIncomePage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPmIncomePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPmIncomePage.updateDate }">
					<input id="projectId" name="project.id" type="hidden" value="${tPmIncomePage.project.id }">
		<table style="width: 400px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>						
        				<td align="right"><label class="Validform_label">
            				项目名称:<font color="red">*</font>
          				</label></td>
        				<td class="value"><input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt" value='${tPmIncomePage.project.projectName}' datatype="*"> <t:chooseProject
            				inputName="projectName" inputId="projectId" mode="single" all="subProject"></t:chooseProject> <span class="Validform_checktip"></span> <label class="Validform_label"
            				style="display: none;">项目名称</label></td>
      				</tr>
      				<tr>
      					<td align="right" width="120px"><label class="Validform_label"> 分配金额(元): </label> <font color="red">*</font></td>
          				<td class="value"><input id="applyAmount" name="applyAmount" type="text" style="width: 200px; text-align: right;" class="easyui-numberbox"
              				data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmIncomeApplyPage.applyAmount}'> <span class="Validform_checktip"></span> <label
              				class="Validform_label" style="display: none;">分配金额</label></td>
      				</tr>
			</table>
		</t:formvalid>
 </body>	