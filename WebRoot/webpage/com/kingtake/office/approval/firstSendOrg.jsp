<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>呈批单发送</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function submitParent(data){
      var parentPage = window.opener.iframe;
      $('#subbtn',parentPage.document).click();
	  window.close();
  }
  
  function checkReceive(){
	  var flag = true;
	  var receiverids = $('#receiverid').val();
	  var departIds = $('#departId').val();
	  $.ajax({
		  async : false,
          cache : false,
          type : 'POST',
		  data:{'id':'${id}','receiverids':receiverids,'departIds':departIds},
		  url:"tOReceiveBillController.do?checkReceive",
		  success:function(data){
			  data = JSON.parse(data);
			  if(!data.obj){
				  flag=false;
				  $.Showmsg('所选接收人对于该公文有未处理记录，请重新选择接收人！');
				  $('#receiverid').val("");
				  $('#departId').val("");
				  $('#departName').val("");
				  $('#realName').val("");
			  }
		  }
	  });
	  return flag;
  }
  
  function backFill(){
	  
	  var receiverid = $('#receiverid').val();
	  var departId = $('#departId').val();
	  var departName = $('#departName').val();
	  var realName = $('#realName').val();
	  var leaderReview = $('#leaderReview').val();
	  window.opener.backSubmit(receiverid,departId,departName,realName,leaderReview);
	  window.close();
  }
 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOReceiveBillController.do?send"  callback="@Override submitParent"> 
<%--   		<input type="hidden" name="id" id="id" value="${id}"> --%>
<%--   		<input type="hidden" name="rid" id="rid" value="${rid}"> --%>
		<table  cellpadding="0" cellspacing="1" class="formtable" style="margin-left: 200px;margin-top: 120px;line-height: 30px;font-size: 50px;">
				<tr>
					<td align="right" width="80px">
						<label class="Validform_label">
							接收人:<font color="red">*</font>
						</label>
					</td>
					<td class="value" >
				<input type="hidden" name="receiverid" id="receiverid" value="${tOReceiveBillPage.contactId}" >
				<input type="hidden" name="departId" id="departId" value="${tOReceiveBillPage.dutyId}" >
				<input type="hidden" name="departName" id="departName" value="${tOReceiveBillPage.dutyName}" > 
				<input id="realName" style="width: 120px;" class="inputxt" name="realName"  readonly="readonly" datatype="*" value="${tOReceiveBillPage.contactName}" ></input>
				<t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName" isclear="true"  inputTextname="receiverid,realName,departId,departName" idInput="receiverid" mode="single" departIdInput="departId"></t:chooseUser>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							填写意见:
						</label>
					</td>
					<td class="value">
					     	 <textarea id="leaderReview" name="leaderReview"  style="width: 300px;" rows="5" class="inputxt" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">填写意见</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<input type="button" value="提交" id="saveBtn" onclick="backFill()">
					</td>
					<td></td>
					</tr>
			</table>
		</t:formvalid>
 </body>
