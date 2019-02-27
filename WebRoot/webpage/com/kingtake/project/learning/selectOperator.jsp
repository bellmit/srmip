<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>选择审批人</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
form{
 margin-top: 60px;
}
</style>
  <script type="text/javascript">
  //编写自定义JS代码
  
  function beforeSubmit(){
      var receiverid = $("#receiverid").val();
      if(receiverid==""){
          tip("请先选择审批人");
          return false;
      }
      var win = frameElement.api.opener;
      //业务表名
      var tableName = win.getTableName();
      var id = win.getId();
      var businessName = win.getBusinessName();
      //流程对应表单URL
      var formUrl = win.getFormUrl();
      var businessCode = win.getBusinessCode();
      $("#id").val(id);
      $("#tableName").val(tableName);
      $("#businessName").val(businessName);
      $("#formUrl").val(formUrl);
      $("#businessCode").val(businessCode);
  }
  
  //回调函数
  function callback(data){
      var win = frameElement.api.opener;
      win.customReload();
      win.tip(data.msg);
      frameElement.api.close();
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLearningThesisController.do?startProcess"  beforeSubmit="beforeSubmit" callback="@Override callback">
  <input id="id" name="id"  type="hidden">
  <input id="tableName" name="tableName"  type="hidden">
  <input id="formUrl" name="formUrl"  type="hidden">
  <input id="businessName" name="businessName"  type="hidden">
  <input id="businessCode" name="businessCode"  type="hidden">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="30%">
						<label class="Validform_label">
							审批人:<font color="red">*</font>
						</label>
					</td>
					<td class="value" width="70%">
				<input type="hidden" name="nextUser" id="nextUser" >
				<input id="realName" style="width: 120px;" class="inputxt" name="realName"  readonly="readonly" datatype="*"></input>
				<t:chooseUser icon="icon-search" title="人员列表" textname="userName,realName" isclear="true" inputTextname="nextUser,realName" idInput="nextUser" mode="single"></t:chooseUser>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">审批人</label>
						</td>
					</tr>
			</table>
      </t:formvalid>
 </body>
