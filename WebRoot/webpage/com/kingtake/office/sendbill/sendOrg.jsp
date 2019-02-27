<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>发文呈批单发送</title>
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
 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOSendBillController.do?send" callback="@Override submitParent" btnsub="saveBtn"> 
		<input type="hidden" name="id" id="id" value="${id}">
  		<input type="hidden" name="rid" id="rid" value="${rid}">
		<table  cellpadding="0" cellspacing="1" class="formtable" style="margin-left: 200px;margin-top: 120px;line-height: 30px;font-size: 50px;">
				<tr>
					<td align="right"  width="100px" >
						<label class="Validform_label">
							审批意见:<font color="red">*</font>
						</label>
					</td>
					<td class="value" >
						<t:codeTypeSelect name="opinion" type="radio" codeType="1" code="SPYJ" id="opinion" defaultVal="1"></t:codeTypeSelect>
					</td>
				</tr>
				<tr>
					<td align="right" width="80px">
						<label class="Validform_label">
							接&nbsp;&nbsp;收&nbsp;&nbsp;人:<font color="red">*</font>
						</label>
					</td>
					<td class="value" >
				<input type="hidden" name="receiverid" id="receiverid" value="${tOReceiveBillPage.contactId}" >
				<input type="hidden" name="departId" id="departId" value="${tOReceiveBillPage.undertakeUnitId}" >
				<input type="hidden" name="departName" id="departName" value="${tOReceiveBillPage.undertakeUnitName}" > 
				<input id="realName" style="width: 120px;" class="inputxt" name="realName"  readonly="readonly" datatype="*" value="${tOReceiveBillPage.contactName}" >
				<t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName" isclear="true"  inputTextname="receiverid,realName,departId,departName" idInput="receiverid" mode="single" departIdInput="departId"></t:chooseUser>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接收人</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;
              <br/>
              (200字以内)
						</label>
					</td>
					<td class="value">
					     	 <textarea id="leaderReview" name="leaderReview" datatype="*0-200" style="width: 300px;" rows="5" class="inputxt"  placeholder="除批阅意见外的其他提醒事项"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<tr>
					<td align="right">
						<input type="button" value="提交" id="saveBtn">
					</td>
					<td></td>
					</tr>
			</table>
		</t:formvalid>
 </body>
