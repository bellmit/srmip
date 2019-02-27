<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>流程参数信息</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?saveNode">
   <input name="id" type="hidden" value="${processnode.id}">
   <input name="TPProcess.id" type="hidden" value="${processid}">
   <fieldset class="step">
    <legend>
     	流程节点信息
    </legend>
    <div class="form">
      <label class="Validform_label">
      	节点名称:
     </label>
     <input name="processnodename" value="${processnode.processnodename }" datatype="s3-50" >
     <span class="Validform_checktip">节点名称范围在3~50位字符</span>
    </div>
    <div class="form">
     <label class="Validform_label">
      	节点编码:
     </label>
     <input name="processnodecode" value="${processnode.processnodecode}" class="inputxt">
    </div>
      <div class="form">
      <label class="Validform_label">
     	 表单URL:
     </label>
     <input name="modelandview" value="${processnode.modelandview}" class="inputxt" style="width:400px">
    </div>
    
    <%--  表单不再使用，全部采用链接的方式配置
    <div class="form">
     <label class="Validform_label">
      内部表单:
     </label>
     <select name="fromid" style="width:140px">
      <option value="0">-请选择内部表单-</option>
      <c:forEach items="${formList }" var="form">
       <option value="${form.id }" <c:if test="${form.id==processnode.TPForm.id}">selected="selected"</c:if>>
        ${form.formname }
       </option>
      </c:forEach>
     </select>
    </div>
     --%>
   </fieldset>
  </t:formvalid>
 </body>
</html>
