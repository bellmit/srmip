<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!-- <!DOCTYPE html> -->
<!-- <html> -->
<!--  <head> -->
<!--   <title>T_O_SEND_BILL</title> -->
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--   <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script> -->
<!--   <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script> -->
  <link href="webpage/com/kingtake/office/sendbill/sendBillForm.css" rel="stylesheet">
  <script type="text/javascript">
  //编写自定义JS代码
  function formCheck(){
	  var form = $("#formobj").Validform();
      var obj = form.check(false);
      return obj;
  }
  
  function uploadFile(data){
		$("#bid").val(data.obj.id);
		if($(".uploadify-queue-item").length>0){
			upload();
		}else{
			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}
	
	function close(){
		frameElement.api.close();
	}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" callback="@Override uploadFile"  usePlugin="password" layout="table" action="tOSendBillController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tOSendBillPage.id }">
					<input id="nuclearDraftUserid" name="nuclearDraftUserid" type="hidden" value="${tOSendBillPage.nuclearDraftUserid }">
					<input id="contactId" name="contactId" type="hidden" value="${tOSendBillPage.contactId }">
					<input id="archiveFlag" name="archiveFlag" type="hidden" value="${tOSendBillPage.archiveFlag }">
					<input id="archiveUserid" name="archiveUserid" type="hidden" value="${tOSendBillPage.archiveUserid }">
					<input id="archiveUsername" name="archiveUsername" type="hidden" value="${tOSendBillPage.archiveUsername }">
					<input id="archiveDate" name="archiveDate" type="hidden" value="${tOSendBillPage.archiveDate }">
					<input id="createBy" name="createBy" type="hidden" value="${tOSendBillPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tOSendBillPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="<fmt:formatDate value='${tOSendBillPage.createDate }' type="date" pattern="yyyy-MM-dd"/>">
					<input id="updateBy" name="updateBy" type="hidden" value="${tOSendBillPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tOSendBillPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tOSendBillPage.updateDate }">
					<input id="backUserid" name="backUserid" type="hidden" value="${tOSendBillPage.backUserid }">
					<input id="backUsername" name="backUsername" type="hidden" value="${tOSendBillPage.backUsername }">
					<input id="backSuggestion" name="backSuggestion" type="hidden" value="${tOSendBillPage.backSuggestion }">
					<div align="center" style="font-size: 24px;color: #FF0000;height: 50px;">海军工程大学发文呈批单</div>
					<div align="center" style="color: #FF0000;"><input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.fileNumPrefix}' readonly="readonly">
				﹝20<input id="sendYear" name="sendYear" datatype="*1-2" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendYear}' readonly="readonly">﹞
				<input id="sendNum" name="sendNum" datatype="*1-20" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendNum}' readonly="readonly">号</div>
<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
  <tr>
    <td width="90" align="center" class="title2">单位(文种)</td>
    <td class="title3" width="280">
    <t:departComboTree id="dd" name="dd" idInput="sendUnitId" nameInput="sendUnit" width="155" lazy="false" value="${tOSendBillPage.sendUnit }"></t:departComboTree>(<t:convert codeType="1" code="GWZL" value="${tOSendBillPage.sendTypeCode}"></t:convert>)
    <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }">
<%--     (<t:codeTypeSelect id="sendTypeCode" name="sendTypeCode" defaultVal="${tOSendBillPage.sendTypeCode}"  --%>
<%--                 type="select" code="GWZL" codeType="1" extendParam="{'style':'width:100px;'}"></t:codeTypeSelect>) --%>
    <input id="sendTypeName" name="sendTypeName" type="hidden" style="width: 50px" class="inputxt" value='${tOSendBillPage.sendTypeName}'>
<%--     <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }"> --%>
    <input id="sendUnitId" name="sendUnitId" type="hidden" value="${tOSendBillPage.sendUnitId }">
    <input id="sendUnit" name="sendUnit" type="hidden" style="width: 150px" class="inputxt" value='${tOSendBillPage.sendUnit}'>
    </td>
    <td width="90" align="center" class="title2">密级</td>
    <td class="title3">
    <t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOSendBillPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;'}"></t:codeTypeSelect>
<%--     <input id="secrityGrade" name="secrityGrade" type="text" style="width: 100px" class="inputxt" value='${tOSendBillPage.secrityGrade}'></td> --%>
    <td width="90" align="center" class="title2">印数</td>
    <td class="title3"><input  id="printNum" name="printNum" datatype="n1-4" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.printNum}'></td>
  </tr>
  <tr>
    <td align="center" class="title2">公文<br>标题</td>
    <td colspan="5" class="title3"><input id="sendTitle" name="sendTitle"  datadype="*"  type="text" style="border-style: none none solid none;width: 620px" class="inputxt"  value='${tOSendBillPage.sendTitle}'></td>
  </tr>
  <tr>
    <td align="center" class="title2">
      <p>校<br>
      首<br>
      长<br>
      批<br>
      示</p>
    </td>
    <td colspan="5" class="title3">
    <textarea id="leaderReview" name="leaderReview" rows="7" style="width: 620px" class="inputxt" readonly="readonly"></textarea>
    </td>
  </tr>
  <tr>
    <td align="center" class="title2">
      <p>机<br>
      关<br>
      部<br>
      (院)<br>
      领<br>
      导<br>
      批<br>
      示</p>
    </td>
    <td colspan="5" class="title3">
    <textarea id="officeReview" name="officeReview" rows="9" style="width: 620px" class="inputxt" readonly="readonly"></textarea>
    </td>
  </tr>
					
  <tr>
    <td colspan="6" valign="top" class="title2"><p>拟稿说明</p>
      <p><textarea id="draftExplain" name="draftExplain" rows="4" style="width: 720px"  datadype="s1-50"  class="inputxt">${tOSendBillPage.draftExplain}</textarea></p>
    <p align="right"><input id="draftDate" name="draftDate" type="text" style="width: 150px" 
					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='yyyy-MM-dd'/>"></p></td>
  </tr>
  
				
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td><font color="red">承办单位：</font><t:departComboTree id="bb" name="bb" idInput="undertakeUnitId" nameInput="undertakeUnitName" width="155" lazy="false" value="${tOSendBillPage.undertakeUnitName }"></t:departComboTree>
    <input id="undertakeUnitName" name="undertakeUnitName" type="hidden" style="width: 120px" class="inputxt" value='${tOSendBillPage.undertakeUnitName}'>
    <input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOSendBillPage.undertakeUnitId }">
    </td>
    <td nowrap="nowrap"><font color="red">核稿人：</font><input id="nuclearDraftUsername" name="nuclearDraftUsername" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.nuclearDraftUsername}'  readonly="readonly" datatype="*">
     <t:chooseUser idInput="nuclearDraftUserid" inputTextname="nuclearDraftUserid,nuclearDraftUsername" icon="icon-search" title="选择核稿人" textname="id,realName" mode="single"></t:chooseUser>
    </td>
    <td><font color="red">联系人：</font><input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.contactName}'></td>
    <td><font color="red">电话：</font><input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.contactPhone}'>
    </td>
  </tr>
</table>
<table style="width: 100%;">
<tr>
				<td><p>&nbsp;</p>
      				<p>附件</p>
    				<p>&nbsp;</p></td>
     			<td>
     			<input type="hidden" value="${tOSendBillPage.id }" id="bid" name="bid" />
      <table>
        <c:forEach items="${tOSendBillPage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOSendBillPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
            <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
          </tr>
        </c:forEach>
      </table>
      <script type="text/javascript">
          $.dialog.setting.zIndex =2111;
          function del(url,obj){
            $.dialog.confirm("确认删除该条记录?", function(){
                $.ajax({
                async : false,
                cache : false,
                type : 'POST',
                url : url,// 请求的action路径
                success : function(data) {
                  var d = $.parseJSON(data);
                  if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    $(obj).closest("tr").hide("slow");
                  }
                }
              });  
            }, function(){
            });
          }
          </script>
      <div>
 <div class="form" id="filediv"></div>
      <t:upload  queueID="filediv" name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOSendBill">
  </t:upload>
  </div>
 </td>
</tr>
</table>
<div style="width: auto; height: 200px;">
			<div style="width: 880px; height: 1px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tOSendBillController.do?getStepList&id=${tOSendBillPage.id}"
					icon="icon-search" title="流转步骤" id="tOReceiveBillStep"></t:tab>
			</t:tabs>
			</div>
		</div>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/sendbill/tOSendBill.js"></script>		