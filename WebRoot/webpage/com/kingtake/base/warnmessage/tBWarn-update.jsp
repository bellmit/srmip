<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>消息提醒</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBWarnController.do?doAddUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tBWarnPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tBWarnPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tBWarnPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tBWarnPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBWarnPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tBWarnPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBWarnPage.updateDate }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" style="width:35%;">
							<label class="Validform_label">
								提醒类别:
							</label>
						</td>
        <td class="value" style="width: 65%;">
        <select id="warnProperty" name="warnProperty.id">
            <c:forEach items="${properties}" var="property" varStatus="status">
              <option value="${property.id}"
                <c:if test="${property.id==tBWarnPage.warnProperty.id}" >selected="selected"</c:if>>${property.businessname}</option>
            </c:forEach>
        </select>
        <span class="Validform_checktip"></span> 
        <label class="Validform_label" style="display: none;">提醒类别</label>
        </td>
        </tr>
        <tr>
            <td align="right">
              <label class="Validform_label">
                                  提醒频率:
              </label>
            </td>
            <td class="value">
                   <t:codeTypeSelect id="warnFrequency" name="warnFrequency" codeType="0"
                         code="WARNFREQUENCY" type="select" defaultVal="${tBWarnPage.warnFrequency}"></t:codeTypeSelect>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">提醒频率</label>
            </td>
          </tr>
          <tr>
            <td align="right">
              <label class="Validform_label">
                                   提醒时间点:
              </label>
            </td>
            <td class="value">
                    <input id="warnTimePoint" name="warnTimePoint" type="text" style="width: 150px" 
                              class="Wdate" onClick="WdatePicker({dateFmt: 'HH:mm'})"
                               value='${tBWarnPage.warnTimePoint}'>    
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">提醒时间点</label>
            </td>
          </tr>
          <tr>
						<td align="right">
							<label class="Validform_label">
								提醒时间范围:
							</label>
						</td>
						<td class="value">
									  <input id="warnBeginTime" name="warnBeginTime" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
						      						 value='<fmt:formatDate value='${tBWarnPage.warnBeginTime}' type="date" pattern="yyyy-MM-dd"/>'>
                               <span class="Validform_checktip"></span>
                               <label class="Validform_label" style="display: none;">开始提醒时间</label> 
                                     ~
                                     <input id="warnEndTime" name="warnEndTime" type="text" style="width: 150px" 
                                                    class="Wdate" onClick="WdatePicker()"
                                                     value='<fmt:formatDate value='${tBWarnPage.warnEndTime}' type="date" pattern="yyyy-MM-dd"/>'>   
							  <span class="Validform_checktip"></span>
							  <label class="Validform_label" style="display: none;">结束提醒时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								提醒内容:
							</label>
						</td>
						<td class="value">
						  	 	<textarea id="warnContent" style="width:600px;" class="inputxt" rows="6" name="warnContent">${tBWarnPage.warnContent}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">提醒内容</label>
						</td>
                    </tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/warnMessage/tBWarn.js"></script>		