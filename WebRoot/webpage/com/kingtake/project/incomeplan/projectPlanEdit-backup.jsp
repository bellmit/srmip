<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划下达编辑</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-x: hidden;" class="easyui-layout" fit="false" >
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" 
    action="tPmProjectPlanController.do?doSave"  callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmProjectPlanPage.id }">
    <table cellpadding="0" cellspacing="1" class="mytable">
      <tr>
        <td align="right"><label class="Validform_label">文件号:</label><font color="red">*</font></td>
        <td class="value">
        	<input id="documentNo" name="documentNo"  value="${tPmProjectPlanPage.documentNo}" datatype="*"> 
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">文件号</label>                                  
        </td>
        <td align="right"><label class="Validform_label">文件名:</label><font color="red">*</font></td>
        <td class="value">
        	<input id="documentName" name="documentName"  value="${tPmProjectPlanPage.documentName}" datatype="*"> 
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">文件名</label>                                  
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">发文时间:</label><font color="red">*</font></td>
        <td class="value" colspan="3">
          <input id="documentTime" name="documentTime" type="text" style="width: 150px" class="Wdate" datatype="date"
          onClick="WdatePicker()"  value='<fmt:formatDate value='${tPmProjectPlanPage.documentTime}' type="date" pattern="yyyy-MM-dd"/>'> 
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发文时间</label></td>        
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">来源经费科目:</label><font color="red">*</font></td>
        <td class="value">         
          <input id="fundsSubject" name="fundsSubject" type="text" value="${tPmProjectPlanPage.fundsSubject}" style="width: 150px" class="inputxt" datatype="*"> 
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">来源经费科目</label></td>                    
        <td align="right"><label class="Validform_label"> 计划下达总金额(元): </label> <font color="red">*</font></td>
          <td class="value"><input id="amount" name="amount" type="text" class="easyui-numberbox"
              data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" datatype="*" min="1" value='${tPmProjectPlanPage.amount}'> <span class="Validform_checktip"></span> 
              <label class="Validform_label" style="display: none;">计划下达总金额</label></td>
      </tr>            
    </table>
  </t:formvalid> 
</body>
</html>