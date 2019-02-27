<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>T_B_PROJECT_TYPE</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  $(function(){
      var id = $("#id").val();
      var url = 'tBProjectTypeController.do?getParentTypeList';
      if(id!=''){
          url = url+'&id='+id;
      }
      $("#parentType").combobox({
          url:url,
          valueField: 'id',    
          textField: 'text'
      }); 
   });
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBProjectTypeController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tBProjectTypePage.id }">
		<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目类型：<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="projectTypeName" name="projectTypeName" type="text" style="width： 150px" class="inputxt" 
				     		datatype="*1-25"  value="${tBProjectTypePage.projectTypeName}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">项目类型</label>
					</td>
				</tr>
                <tr>
                    <td align="right">
                    <label class="Validform_label">父类型：</label>
                    </td>
                    <td class="value">
                    <select id="parentType" name="parentType.id"  style="width: 156px" >
                    </select>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">项目类型</label>
                    </td>
                </tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							立项形式：<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<select name="approvalCode" id="approvalCode">
							<c:if test="${PROJECT_PLAN}">
								<option value=<%=ProjectConstant.PROJECT_CONTRACT %> >
									<%=ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT)%></option>
								<option value=<%=ProjectConstant.PROJECT_PLAN %> selected="selected">
									<%=ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN)%></option>
							</c:if>
							<c:if test="${!PROJECT_PLAN}">
								<option value=<%=ProjectConstant.PROJECT_CONTRACT %> selected="selected">
									<%=ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_CONTRACT)%></option>
								<option value=<%=ProjectConstant.PROJECT_PLAN %> >
									<%=ProjectConstant.planContractMap.get(ProjectConstant.PROJECT_PLAN)%></option>
							</c:if>
						</select>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display:none;">立项形式</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							经费性质：<font color="red">*</font>
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="fundsPropertyId" type="list" 
				     		dictTable="T_B_FUNDS_PROPERTY" dictField="ID" dictText="FUNDS_NAME"
				     		hasLabel="false"  title="经费性质"
				     		defaultVal="${tBProjectTypePage.fundsPropertyId}"
							extendJson="{\"datatype\":\"*\"}"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">经费性质</label>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							负&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;责&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
							该类项目&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
							机关参谋：&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<input id="officer" name="officer" type="hidden" value="${tBProjectTypePage.officer}">
				     	<input id="realname" type="text" style="width: 150px" class="inputxt" readonly="readonly"
				     		value="${office_realname}"><br>
				     	<t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single"
				     		idInput="officer" textname="id,realName" inputTextname="officer,realname"></t:chooseUser>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">负责该类项目机关参谋</label>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排&nbsp;序&nbsp;&nbsp;码：<font color="red">*</font>
						</label>
					</td>
					<td class="value">
				     	<input id="sortCode" name="sortCode" type="text" style="width: 150px" class="inputxt" 
				     		datatype="n1-6" value="${tBProjectTypePage.sortCode}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">排序码</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：&nbsp;&nbsp;
						</label>
					</td>
					<td class="value">
				     	<textarea id="memo" name="memo" style="width: 150px; height: 60px;" 
				     		datatype="*1-100" ignore="ignore">${tBProjectTypePage.memo}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">备注</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/base/projecttype/tBProjectType.js"></script>		