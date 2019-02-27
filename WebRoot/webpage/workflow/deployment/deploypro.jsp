<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
  <title>部署流程</title>
  <t:base type="jquery,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true">
   <fieldset class="step">
    <legend>
     部署流程
    </legend>
    <div class="form">
     <label class="Validform_label">
      流程名称:
     </label>
     <input name="note" id="note" type="text" />
    </div>
    <div class="form">
     <t:upload name="部署流程" uploader="activitiController.do?deployProcess" extend="*.bpmn;*.bar;*.zip*" id="file_upload" formData="note"></t:upload>
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
