<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<!DOCTYPE html>
<html>
 <head>
  <title>流程参数信息</title>
  <t:base type="jquery,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?savePro">
   <input name="id" type="hidden" value="${processpro.id }">
   <input name="TPProcess.id" type="hidden" value="${processid}">
   <fieldset class="step">
    <legend>
     流程参数信息
    </legend>
    <div class="form">
      <label class="Validform_label">
      参数编码:
     </label>
     <input name="processprokey" value="${processpro.processprokey }" datatype="s3-20" class="inputxt">
     <span class="Validform_checktip">参数编码3-20位字符!</span>
    </div>
     <div class="form">
      <label class="Validform_label">
      参数名称:
     </label>
     <input name="processproname" class="inputxt" value="${processpro.processproname}" >
    </div>
    <div class="form">
     <label class="Validform_label">
      变量类型:
     </label>
     <select name="processprotype">
      <option value="default" <c:if test="${processpro.processprotype=='default'}">selected="selected"</c:if>>
        普通变量
      </option>
      <option value="opt" <c:if test="${processpro.processprotype=='opt'}">selected="selected"</c:if>>
       流转控制
      </option>
     </select>
    </div>
    <div class="form">
      <label class="Validform_label">
      所属节点:
     </label>
     <select name="TPProcessnode.id">
      <c:forEach items="${nodeList}" var="node">
       <option value="${node.id}" <c:if test="${node.id==processpro.TPProcessnode.id}">selected="selected"</c:if>>
        ${node.processnodename}
       </option>
      </c:forEach>
     </select>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
