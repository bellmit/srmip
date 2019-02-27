<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>表单参数信息</title>
  <t:base type="jquery,easyui,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" action="processController.do?saveFPro" tabtitle="基本配置,高级配置">
   <input name="id" type="hidden" value="${formpro.id}">
   <input name="TPForm.id" type="hidden" value="${formid}">
   <fieldset class="step">
    <legend>
     基础配置
    </legend>
    <div class="form">
      <label class="Validform_label">
      参数名称:
     </label>
     <input name="formproname" value="${formpro.formproname}" datatype="s2-50" errormsg="参数名称范围在2~50位字符!">
     <span class="Validform_checktip">参数名称范围在2~50位字符,且不为空</span>
    </div>
    <div class="form">
     <label class="Validform_label">
      参数值:
     </label>
     <input name="formproval" value="${formpro.formproval}">
    </div>
    <div class="form">
      <label class="Validform_label">
      参数id:
     </label>
     <input name="formprokey" value="${formpro.formprokey}">
    </div>
    <div class="form">
      <label class="Validform_label">
      参数编码:
     </label>
     <input name="formprocode" value="${formpro.formprocode}">
    </div>
    <div class="form">
     <label class="Validform_label">
      是否操作:
     </label>
     <select name="processopt">
      <option value="yes" <c:if test="${formpro.processopt=='yes'}">selected="selected"</c:if>>
       是
      </option>
      <option value="no" <c:if test="${formpro.processopt=='no'}">selected="selected"</c:if>>
       否
      </option>
     </select>
    </div>
    <div class="form">
     <label class="Validform_label">
      参数状态:
     </label>
     <select name="formprostate">
      <option value="true" <c:if test="${formpro.formprostate==true}">selected="selected"</c:if>>
       启用
      </option>
      <option value="false" <c:if test="${formpro.formprostate==false}">selected="selected"</c:if>>
       禁止
      </option>
     </select>
    </div>
    <div class="form">
     <label class="Validform_label">
      参数类型:
     </label>
     <select name="formprotype" id="formprotype">
      <option value="text" <c:if test="${formpro.formprotype=='text'}">selected="selected"</c:if>>
       text
      </option>
      <option value="select" <c:if test="${formpro.formprotype=='select'}">selected="selected"</c:if>>
       select
      </option>
      <option value="hidden" <c:if test="${formpro.formprotype=='hidden'}">selected="selected"</c:if>>
       hidden
      </option>
      <option value="button" <c:if test="${formpro.formprotype=='button'}">selected="selected"</c:if>>
       button
      </option>
      <option value="checkbox" <c:if test="${formpro.formprotype=='checkbox'}">selected="selected"</c:if>>
       checkbox
      </option>
      <option value="radio" <c:if test="${formpro.formprotype=='radio'}">selected="selected"</c:if>>
       radio
      </option>
      <option value="textarea" <c:if test="${formpro.formprotype=='textarea'}">selected="selected"</c:if>>
       textarea
      </option>
     </select>
    </div>
   </fieldset>
   <fieldset class="step">
    <legend>
     高级配置
    </legend>
    <div class="form">
     <label class="Validform_label">
      参数事件:
     </label>
     <input name="formprofun" value="${formpro.formprofun }">
    </div>
    <div class="form">
     <label class="Validform_label">
      参数函数:
     </label>
     <textarea name="formproscript" rows="5" cols="40">${formpro.formproscript}</textarea>
    </div>
    <div class="form">
      <label class="Validform_label">
      表单参数样式:
     </label>
     <textarea name="formprocss" rows="5" cols="40">${formpro.formprocss}</textarea>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
