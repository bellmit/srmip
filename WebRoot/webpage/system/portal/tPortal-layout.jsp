<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>代办配置表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  function checkInput(){
	  var layout = $("input[name=layoutId]").val();
	  if(layout==""||layout==undefined){
	  tip("请选择一种布局!");
	  return false;
	  }
  }
  
  </script>
 </head>
 <body>
 <input type="hidden" value="${selectedLayout}"/>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPortalController.do?setLayout" tiptype="1" beforeSubmit="checkInput" callback="callback">
		<table style="width: 500px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="center">
						<label class="Validform_label">
							用户:
						</label>
					</td>
					<td class="value">
					     	 <input id="userName" name="userName" type="text" style="width: 150px" class="inputxt"  
								   value="${userName}" readOnly="true">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">用户名</label>
						</td>
				<tr>
					<td align="center">
						<label class="Validform_label">
							布局:
						</label>
					</td>
					<td>
					<c:forEach items="${layoutList}" var="layout" varStatus="status">
					    <input id="layout" name="layoutId" type="radio" style="width: 150px;height:15px;vertical-align:middle;" value="${layout.id}" <c:if test="${layout.id==selectedLayout}">checked</c:if> />${layout.layoutName}
					    <c:if test="${!status.last}"><hr/><br/></c:if>
					    
					</c:forEach>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">布局</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
</html>