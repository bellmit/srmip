<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>表单信息</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" layout="div" action="processController.do?saveForm">
   <input name="id" type="hidden" value="${form.id}">
   <fieldset class="step">
    <div class="form">
     <label class="Validform_label">
      表单名称:
     </label>
     <input name="formname" value="${form.formname }" datatype="s3-50" class="inputxt">
     <span class="Validform_checktip">表单名称范围在3~50位字符</span>
    </div>
    <div class="form">
     <label class="Validform_label">
      提交路径:
     </label>
     <input name="formaction" value="${form.formaction}" class="inputxt">
    </div>
    <div class="form">
     <label class="Validform_label">
      表单类型:
     </label>
     <select name="TSType.id">
      <c:forEach items="${typeList}" var="t">
       <option value="${t.id }" <c:if test="${t.id==form.TSType.id}">selected="selected"</c:if>>
        ${t.typename}
       </option>
      </c:forEach>
     </select>
    </div>
    <div class="form">
     <label class="Validform_label">
      表单key值:
     </label>
     <input name="formkey" value="${form.formkey }" class="inputxt">
    </div>
    <div class="form">
     <label class="Validform_label">
      表单编码:
     </label>
     <input name="formcode" value="${form.formcode}" class="inputxt">
    </div>
    <div class="form">
     <label class="Validform_label">
      表单注释:
     </label>
     <TEXTAREA name="formnote">${form.formnote}</TEXTAREA>
     <input">
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
