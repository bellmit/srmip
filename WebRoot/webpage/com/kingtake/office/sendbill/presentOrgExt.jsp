<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>发送</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--   <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script> -->
<!--   <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script> -->
<style type="text/css">
form{
 margin-top: 60px;
}
</style>
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
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOSendBillController.do?send&id=${id}" callback="@Override submitParent" beforeSubmit="checkReceive" btnsub="saveBtn"> 
		<input id="ifcirculate" name="ifcirculate" type="hidden" value="${ifcirculate}">
		<input id="suggestionType" name="suggestionType" type="hidden" value="${suggestionType}">
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right" width="30%">
						<label class="Validform_label">
							接收人:<font color="red">*</font>
						</label>
					</td>
					<td class="value" width="70%">
				<input type="hidden" name="receiverid" id="receiverid" >
				<input type="hidden" name="departId" id="departId" >
				<input type="hidden" name="departName" id="departName" > 
				<input id="realName" style="width: 120px;" class="inputxt" name="realName"  readonly="readonly" datatype="*"></input>
				<t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName" isclear="true" inputTextname="receiverid,realName,departId,departName" idInput="receiverid" departIdInput="departId" url="commonUserController.do?commonUserMini" width="840px"></t:chooseUser>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							填写意见:<font color="red">*</font>
						</label>
					</td>
					<td class="value">
					     	 <textarea id="leaderReview" name="leaderReview"  datatype="*1-100"  style="width: 300px;" rows="5" class="inputxt" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">填写意见</label>
						</td>
					</tr>
                    <tr>
                    <td>
                    </td>
                    <td>
                     <input type="button" value="提交" id="saveBtn" >
                     </td>
                    </tr>
			</table>
		</t:formvalid>
 </body>
