<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>修改公文权限</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
form{
 margin-top: 60px;
}
</style>
  <script type="text/javascript">
  
  </script>
 </head>
 <body>
 <div align="center" style="vertical-align: middle;margin-top: 30px;">
   <t:formvalid formid="formobj" dialog="true" layout="table" action="tOSendDownBillController.do?updateOperation" tiptype="1" >
   <input id="id" name="id" type="hidden" value="${billDown.id }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="height: 50px;"><label class="Validform_label">
            显示内容:
          </label></td>
        <td class="value" width="200px">
        <c:if test="${empty parentDown}">
        <input type="checkbox" name="flowShow" value="1" <c:if test="${billDown.flowShow eq '1'}">checked="checked"</c:if>>阅批单 
        <input type="checkbox" name="contentShow" value="1" <c:if test="${billDown.contentShow eq '1'}">checked="checked"</c:if>>正文
        <input type="checkbox" name="attachementShow" value="1" <c:if test="${billDown.attachementShow eq '1'}">checked="checked"</c:if>>附件
        </c:if>
        <c:if test="${!empty parentDown}">
        <c:if test="${parentDown.flowShow eq '1' }">
        <input type="checkbox" name="flowShow" value="1" <c:if test="${billDown.flowShow eq '1'}">checked="checked"</c:if>>阅批单
        </c:if>
        <c:if test="${parentDown.contentShow eq '1' }">
        <input type="checkbox" name="contentShow" value="1" <c:if test="${billDown.contentShow eq '1'}">checked="checked"</c:if>>正文
        </c:if>
        <c:if test="${parentDown.attachementShow eq '1' }">
        <input type="checkbox" name="attachementShow" value="1" <c:if test="${billDown.attachementShow eq '1'}">checked="checked"</c:if>>附件
        </c:if>
        </c:if>
        </td>
      </tr>
    </table>
    </t:formvalid>
  </div>
 </body>
