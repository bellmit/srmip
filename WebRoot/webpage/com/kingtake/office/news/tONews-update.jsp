<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>要讯</title>
<t:base type="jquery,easyui,tools,DatePicker,ckfinder,ckeditor"></t:base>
<script type="text/javascript">
    
</script>
</head>
<body>
  <div style="margin-left: auto; margin-right: auto; width: 950px;">
    <b><c:if test="${tONewsPage.submitFlag eq '2'}">
        <a href="#" style="color: red; cursor: pointer; text-decoration: underline;" onclick="viewMsg()">查看修改意见</a>
      </c:if></b>
    <textarea id="msgText" style="display: none;">${tONewsPage.msgText }</textarea>
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tONewsController.do?doUpdate" tiptype="1">
      <input id="id" name="id" type="hidden" value="${tONewsPage.id }">
      <table style="width: 950px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right"><label class="Validform_label">
              标题: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="title" name="title" type="text" style="width: 180px" class="inputxt" dataType="*1-100" value='${tONewsPage.title}'> <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">标题</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              单位: <font color="red">*</font>
            </label></td>
          <td class="value"><t:departComboTree id="departId" nameInput="departName" idInput="departId" lazy="false" value="${tONewsPage.departId}" width="185"></t:departComboTree> <input
              id="departId" name="departId" type="hidden" class="inputxt" value='${tONewsPage.departId}' dataType="*"> <input id="departName" name="departName" type="hidden"
              value='${tONewsPage.departName}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              时间: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="time" name="time" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()" dataType="date"
              value='<fmt:formatDate value='${tONewsPage.time}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
              style="display: none;">时间</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              撰稿人: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="writer" name="writer" type="text" style="width: 180px" class="inputxt" dataType="*1-10" value='${tONewsPage.writer}' readonly="true"> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">撰稿人</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              电话: <font color="red">*</font>
            </label></td>
          <td class="value"><input id="phone" name="phone" type="text" style="width: 180px" class="inputxt" datatype="byterange" min="1" max="30" value='${tONewsPage.phone}'> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">电话</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label">
              核稿人: <font color="red">*</font>
            </label></td>
          <td class="value">
          <input id="checkUserId" name="checkUserId" type="hidden" value='${tONewsPage.checkUserId}' >
          <input id="checkUserName" name="checkUserName" type="text" style="width: 180px" class="inputxt" dataType="*1-10" value='${tONewsPage.checkUserName}' >
          <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" 
                        isclear="true" inputTextname="checkUserId,checkUserName" 
                        idInput="checkUserId" mode="single"></t:chooseUser>
           <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">核稿人</label></td>
        </tr>
        <tr>
          <td align="right"><label class="Validform_label"> 主要内容: </label></td>
          <td class="value"><t:ckeditor name="content" isfinder="true" value="${tONewsPage.content}" type="width:750"></t:ckeditor> <span class="Validform_checktip"></span> <label
              class="Validform_label" style="display: none;">主要内容</label></td>
        </tr>
      </table>
    </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/office/news/tONews.js"></script>