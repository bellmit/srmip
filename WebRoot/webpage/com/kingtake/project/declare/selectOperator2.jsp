<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>选择接收人</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
form{
 margin-top: 60px;
}
</style>
  <script type="text/javascript">
  function getOperator(){
      var nextUser = $("#nextUser").val();
      if(nextUser==""){
          tip("请选择接收人！");
      }else{
          return nextUser;
      }
  }
  
  function getUserId(){
      var nextUser = $("#userId").val();
      if(nextUser==""){
          tip("请选择接收人！");
      }else{
          return nextUser;
      }
  }
  
  function getRealName(){
      var realName = $("#realName").val();
      if(realName==""){
          tip("请选择接收人！");
      }else{
          return realName;
      }
  }
  
  function getDepartId(){
      var departId = $("#departId").val();
      if(departId==""){
          tip("请选择接收人！");
      }else{
          return departId;
      }
  }
  </script>
 </head>
 <body>
 <div align="center" style="vertical-align: middle;margin-top: 60px;">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="60px">
						<label class="Validform_label">
							接收人:<font color="red">*</font>
						</label>
					</td>
					<td class="value" width="200px">
				<input type="hidden" name="nextUser" id="nextUser" >
				<input id="realName" style="width: 120px;" class="inputxt" name="realName"  readonly="readonly" datatype="*"></input>
				<input id="userId"  type="hidden"></input>
				<input id="departId"  type="hidden"></input>
				<t:chooseUser icon="icon-search" title="人员列表" textname="id,userName,realName,departId" isclear="true" inputTextname="userId,nextUser,realName,departId" idInput="userId" mode="single"></t:chooseUser>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人</label>
						</td>
					</tr>
			</table>
      </div>
 </body>
