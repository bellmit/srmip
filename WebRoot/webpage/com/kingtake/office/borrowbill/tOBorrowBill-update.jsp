<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收文借阅</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
//查看修改意见
function viewMsg(){
    var msgText = $("#msgText").val();
    $.messager.alert('修改意见',msgText);    
}
</script>
</head>
<body>
  <b><c:if test="${tOBorrowBillPage.auditStatus eq '3'}"><a href="#" style="color: red; cursor: pointer; text-decoration: underline;" onclick="viewMsg()">查看修改意见</a></c:if></b>
  <textarea id="msgText" style="display:none;" >${tOBorrowBillPage.msgText }</textarea>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOBorrowBillController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tOBorrowBillPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tOBorrowBillPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOBorrowBillPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOBorrowBillPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOBorrowBillPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOBorrowBillPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOBorrowBillPage.updateDate }">
    <table style="width: 1000px;margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            文件名称: <font color="red">*</font>
          </label></td>
        <td class="value" colspan="3"><input id="title" name="title" type="text" style="width: 700px" datatype="*1-50" value='${tOBorrowBillPage.title}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">文件名称</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            文件号: 
          </label></td>
        <td class="value"><input id="fileNum" name="fileNum" type="text" style="width: 180px"  value='${tOBorrowBillPage.fileNum}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">文件号</label></td>
        <td align="right"><label class="Validform_label">
            发文单位: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="sendUnit" name="sendUnit" type="text" style="width: 180px" dataType="*1-25" value='${tOBorrowBillPage.sendUnit}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">发文单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            发文开始时间: 
          </label></td>
        <td class="value"><input id="sendBeginTime" name="sendBeginTime" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()" dataType="date" ignore="ignore"
            value='<fmt:formatDate value='${tOBorrowBillPage.sendBeginTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">发文开始时间</label></td>
        <td align="right"><label class="Validform_label">
            发文结束时间: 
          </label></td>
        <td class="value"><input id="sendEndTime" name="sendEndTime" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()" dataType="date" ignore="ignore"
            value='<fmt:formatDate value='${tOBorrowBillPage.sendEndTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">发文结束时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            申请人: 
          </label></td>
        <td class="value">
        <input id="applyUserId" name="applyUserId" type="hidden" value="${tOBorrowBillPage.applyUserId }">
		<input id="applyUserName" name="applyUserName" type="text" style="width: 180px" value="${tOBorrowBillPage.applyUserName }">
		<t:chooseUser icon="icon-search" title="人员列表" isclear="true"  idInput="applyUserId"
				     		textname="id,realName"  inputTextname="applyUserId,applyUserName" ></t:chooseUser>
            <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">申请人</label></td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label"> 承研单位: </label> </td>
              <td class="value">
              <input id="undertakeUnitId" name="undertakeUnitId" value="${tOBorrowBillPage.undertakeUnitId}" style="width:185px;"> 
              <input name="undertakeUnitName" id="undertakeUnitName" type="hidden">
              <script type="text/javascript">
                              //选择承研单位时，将承研单位的父单位加入责任单位
                              $(function() {
                                $('#undertakeUnitId').combotree({
                                  url : 'departController.do?getDepartTree&lazy=false',
                                  height : '27',
                                  multiple : false,
                                  onSelect : function(record) {
                                    $("#undertakeUnitName").val(record.text);
                                    var tree = $('#undertakeUnitId').combotree('tree');
                                    var parent = tree.tree('getParent', record.target);
                                    $("#dutyUnitId").val(parent.id);
                                    $("#dutyUnitName").combotree('setValue', parent.text);
                                  }
                                });
                              });
                            </script> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">承研单位</label></td>
              <td align="right"><label class="Validform_label"> 责任单位: </label></td>
              <td class="value"><t:departComboTree name="dutyUnitName" id="dutyUnitName" idInput="dutyUnitId" lazy="false" value="${tOBorrowBillPage.dutyUnitName}" width="185"></t:departComboTree>
                <input id="dutyUnitId" name="dutyUnitId" type="hidden" class="inputxt" value='${tOBorrowBillPage.dutyUnitId}'> <span class="Validform_checktip"></span> <label
                class="Validform_label" style="display: none;">责任部门</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            借阅单位: <font color="red">*</font>
          </label></td>
        <td class="value"><t:departComboTree id="dept" nameInput="borrowUnitName" idInput="borrowUnitId" lazy="false" value="${tOBorrowBillPage.borrowUnitId}" width="185"></t:departComboTree> <input id="borrowUnitId"
            name="borrowUnitId" type="hidden" class="inputxt" value='${tOBorrowBillPage.borrowUnitId}' dataType="*"> <input id="borrowUnitName" name="borrowUnitName" type="hidden" value='${tOBorrowBillPage.borrowUnitName}'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">借阅单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            借阅事由: <font color="red">*(500字以内)</font>
          </label></td>
        <td class="value" colspan="3"><textarea id="borrowReason" style="width: 700px;" class="inputxt" rows="6" cols="3" name="borrowReason" dataType="*1-500" >${tOBorrowBillPage.borrowReason}</textarea>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">借阅事由</label></td>
      </tr>
      <tr>
       <%--  <td align="right"><label class="Validform_label">
            借阅开始时间: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="borrowBeginTime" name="borrowBeginTime" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()" dataType="date"
            value='<fmt:formatDate value='${tOBorrowBillPage.borrowBeginTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">借阅开始时间</label></td> --%>
        <td align="right"><label class="Validform_label">
            归还时间: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="borrowEndTime" name="borrowEndTime" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()" dataType="date"
            value='<fmt:formatDate value='${tOBorrowBillPage.borrowEndTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">归还时间</label></td>
      </tr>
      
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/borrowbill/tOBorrowBillList.js"></script>