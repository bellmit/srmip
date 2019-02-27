<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <t:base type="jquery,tools"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" layout="div" dialog="true" action="dbController.do?saveTable">
   <input name="id" type="hidden" " value="${table.id }">
   <input name="TSType.id" type="hidden" value="${table.TSType.id}">
   <input name="entityKey" type="hidden" value="${table.entityKey}">
   <fieldset class="step">
    <div class="form">
     <label class="Validform_label">
      数据表名称:
     </label>
     <input name="tableName" class="inputxt"  value="${table.tableName }" datatype="s2-50" >
     <span class="Validform_checktip">类型范围在2~50位字符,且不为空</span>
    </div>
    <div class="form">
     <label class="Validform_label">
      对应实体:
     </label>
     <input name="entityName" class="inputxt"  value="${table.entityName}"  datatype="s2-200">
     <span class="Validform_checktip">类型编码在2~50位字符,且不为空</span>
    </div>
     <div class="form">
     <label class="Validform_label">
      描述:
     </label>
     <input name="tableTitle" class="inputxt"   value="${table.tableTitle}">
    </div>
   </fieldset>
  </t:formvalid>
 </body>
</html>
