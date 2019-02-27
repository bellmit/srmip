<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<!-- <!DOCTYPE html> -->
<!-- <html> -->
 <head>
<!--   <title>发文呈批单</title> -->
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="webpage/com/kingtake/office/sendbill/sendBillForm.css" rel="stylesheet">
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  $('#sendTypeName').val($('#sendTypeCode').find("option:selected").text());
	  $('#sendTypeCode').change(function(){
		  $('#sendTypeName').val($('#sendTypeCode').find("option:selected").text());
	  });
  });
  
  function goFirstSend(){
		if(formCheck()&&checkContent()){
			var iWidth=900; //弹出窗口的宽度;
		    var iHeight=500; //弹出窗口的高度;
		    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		    var openUrl = "tOApprovalController.do?goFirstSend";
		    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
		    	
		 }
	}
  
  function backSubmit(receiverid,departId,departName,realName,leaderReview){
	  $('#receiverid').val(receiverid);
	  $('#departId').val(departId);
	  $('#departName').val(departName);
	  $('#realName').val(realName);
	  $('#leaderReview').val(leaderReview);
	  $('#subBtn').click();
  }
  function reloadParent(){
	  var parentPage = window.parent.regList;
	  $('#reloadBtn',parentPage.document).click();
  }
  function formCheck(){
	  var form = $("#formobj").Validform();
      var obj = form.check(false);
      return obj;
  }
  
  function uploadFile(data){
	  frameElement.api.opener.reloadTable();
		$("#bid").val(data.obj.id);
		if($(".uploadify-queue-item").length>0){
			upload();
		}else{
// 			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}
	
	function close(){
		frameElement.api.close();
	}
	
	function checkForm(){
		if($('.uploadify-queue-item').size()>0){
			return true;
		}
		$.Showmsg("请添加附件！");
		return false;
	}
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" callback="@Override uploadFile"  usePlugin="password" layout="table" action="tOSendBillController.do?doAdd" tiptype="1" beforeSubmit="saveToUrl()"  btnsub="subBtn">
					<input id="id" name="id" type="hidden" value="${tOSendBillPage.id }">
					<input id="registerType" name="registerType" value="${tOSendBillPage.registerType}" type="hidden" class="inputxt">
					<input id="regId" name="regId" type="hidden" value="${tOSendBillPage.regId}">
					<input id="nuclearDraftStatus" name="nuclearDraftStatus" type="hidden" value="${tOSendBillPage.nuclearDraftStatus}">
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
					
					<!-- 接收人信息 -->
					<input type="hidden" name="receiverid" id="receiverid">
					<input type="hidden" name="departId" id="departId" >
					<input type="hidden" name="departName" id="departName" >
					<input type="hidden" name="realName" id="realName" >
					<input type="hidden" name="leaderReview" id="leaderReview" >
					<!-- 提交按钮 -->
					<input  type="hidden" name="subBtn" id="subBtn">
					
					<input id="realPath" name="realPath" type="hidden" value="${officeFile.realpath}">
					<input id="contentFileId" name="contentFileId" type="hidden" value="${tOSendBillPage.contentFileId}">
					<div align="center" style="font-size: 24px;color: #FF0000;height: 50px;">海军工程大学发文呈批单</div>
					<div align="center" style="color: #FF0000;"><%-- <input id="sendNum" name="sendNum" type="text" style="border-style: none none solid none;width:150px;text-align:right;"  datatype="*1-20"  value='${tOSendBillPage.sendNum}'/> --%>
					<input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.fileNumPrefix}' readonly="readonly">
				﹝20<input id="sendYear" name="sendYear" datatype="*1-2" ignore="ignore" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendYear}' readonly="readonly">﹞
				<input id="sendNum" name="sendNum" datatype="*1-20" type="text" ignore="ignore" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendNum}' readonly="readonly">
					号</div>
<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
  <tr>
    <td width="90" align="center" class="title2">单位(文种)</td>
    <td class="title3" width="280">
    <input id="sendUnit" name="sendUnit" width="155"  value="${tOSendBillPage.sendUnit}">(<t:convert codeType="1" code="GWZL" value="${tOSendBillPage.sendTypeCode}"></t:convert>)
    <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }">
<%--     (<t:codeTypeSelect id="sendTypeCode" name="sendTypeCode" defaultVal="${tOSendBillPage.sendTypeCode}"  --%>
<%--                 type="select" code="GWZL" codeType="1" extendParam="{'style':'width:100px;','readonly':'readonly'}"></t:codeTypeSelect>) --%>
    <input id="sendTypeName" name="sendTypeName" type="hidden" style="width: 50px" class="inputxt" value='${tOSendBillPage.sendTypeName}'>
<%--     <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }"> --%>
    <input id="sendUnitId" name="sendUnitId" type="hidden" value="${tOSendBillPage.sendUnitId }">
    </td>
    <td width="90" align="center" class="title2">密级</td>
    <td class="title3">
    <t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOSendBillPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;'}"></t:codeTypeSelect>
<%--     <input id="secrityGrade" name="secrityGrade" type="text" style="width: 100px" class="inputxt" value='${tOSendBillPage.secrityGrade}'></td> --%>
    <td width="90" align="center" class="title2">印数</td>
    <td class="title3"><input  id="printNum" name="printNum" datatype="n1-4" type="text" style="border-style: none none solid none;width:100px;" value='${tOSendBillPage.printNum}'></td>
  </tr>
  <tr>
    <td align="center" class="title2">公文<br>标题</td>
    <td colspan="5" class="title3"><input id="sendTitle" name="sendTitle" style="border-style: none none solid none;width:620px;" datadype="*" type="text"  value='${tOSendBillPage.sendTitle}'></td>
  </tr>
<!--   <tr> -->
<!-- 				<td align="center" class="title2">正文 -->
<!-- 				</td> -->
<!-- 				<td class="title3" colspan="5"><a class="easyui-linkbutton"  onclick="addMain()" href="#officecontrol" icon="icon-add">添加正文</a></td> -->
<!-- 			</tr> -->
  <tr>
    <td align="center" class="title2">
      <p>校<br>
      首<br>
      长<br>
      批<br>
      示</p>
    </td>
    <td colspan="5" class="title3"><textarea id="leaderReview" name="leaderReview" rows="9" style="width: 95%;" class="inputxt" disabled="disabled">${tOSendBillPage.draftExplain}</textarea></td>
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
    <td colspan="5" class="title3"><textarea id="officeReview" name="officeReview" rows="9" style="width: 95%;" class="inputxt" disabled="disabled">${tOSendBillPage.draftExplain}</textarea></td>
  </tr>
  <tr>
    <td colspan="6" valign="top" class="title2"><p>拟稿说明</p>
      <p><textarea id="draftExplain" name="draftExplain" rows="7" style="width: 95%;"  class="inputxt"  datadype="*1-50" >${tOSendBillPage.draftExplain}</textarea></p>
    <p align="right"><input id="draftDate" name="draftDate" type="text" style="width: 150px" 
					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='yyyy-MM-dd'/>"></p></td>
  </tr>
  
				
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td>承办单位：
    <input id="undertakeUnitName" name="undertakeUnitName" type="text" style="width: 150px" class="inputxt" value='${tOSendBillPage.undertakeUnitName}' readonly="readonly">
    <input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOSendBillPage.undertakeUnitId }" datatype="*">
    </td>
    <td>核稿人：
    <input id="nuclearDraftUsername" name="nuclearDraftUsername" type="text" style="border-style: none none solid none;width:100px;" class="inputxt" value='${tOSendBillPage.nuclearDraftUsername}' readonly="readonly">
    <input id="nuclearDraftUserid" name="nuclearDraftUserid" type="hidden" value="${tOSendBillPage.nuclearDraftUserid }">
    <input id="nuclearDraftDepartId" name="nuclearDraftDepartId" type="hidden" value="${tOSendBillPage.nuclearDraftDepartId }">
    <input id="nuclearDraftDepartName" name="nuclearDraftDepartName" type="hidden" value="${tOSendBillPage.nuclearDraftDepartName }">
<%--     <t:chooseUser idInput="nuclearDraftUserid" inputTextname="nuclearDraftUserid,nuclearDraftUsername,nuclearDraftDepartId,nuclearDraftDepartName" icon="icon-search" title="选择核稿人" textname="id,realName,departId,departName" mode="single"></t:chooseUser> --%>
    <label class="Validform_label" style="display: none;">核稿人</label>
    </td>
    <td>联系人：<input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width:120px;" class="inputxt" value='${tOSendBillPage.contactName}' datatype="*" readonly="readonly">
    <label class="Validform_label" style="display: none;">联系人</label>
    </td>
    <td>电话：<input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;width:100px;" class="inputxt" value='${tOSendBillPage.contactPhone}' datatype="*" readonly="readonly">
    <label class="Validform_label" style="display: none;">电话</label>
    </td>
  </tr>
</table>
<table style="width: 100%">
  <tr>
     <td width="10%">正文：</td>
                <c:if test="${empty tOSendBillPage.contentFileId}">
                <td colspan="3"><a class="easyui-linkbutton" data-options="plain:true" onclick="addMain()" href="#officecontrol" icon="icon-add"><c:if test="${empty contentFileId}">添加正文</c:if></a></td>
                </c:if>
                <c:if test="${!empty tOSendBillPage.contentFileId}">
                <td title="正文格式：${officeFile.extend} 创建人：${officeFile.createName} 创建时间：<fmt:formatDate value='${officeFile.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"><font color="black">(${officeFile.extend}格式)&nbsp;${officeFile.createName}&nbsp;<fmt:formatDate value='${officeFile.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</font><a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td>
                </c:if>
  </tr>
</table>
<table style="width:100%;">
<tr>
				<td width="10%"><font color="black">附件</font></td></td>
     			<td width="90%">
     			<input type="hidden" value="${tOSendBillPage.id }" id="bid" name="bid" />
     			<table>
        <c:forEach items="${files}" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${file.bid}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
<%--             <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td> --%> 
          </tr>
        </c:forEach>
      </table>
      <script type="text/javascript">
          $.dialog.setting.zIndex =1990;
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
		</t:formvalid>
		<div id="officecontrol"  style="width: 1px;height: 1px;">
<!-- 		<a class="easyui-linkbutton" href="javascript:saveToUrl()" icon="icon-save">保存到服务器</a> -->
		<object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="1px" height="1px">
        <param name="IsUseUTF8URL" value="-1">
        <param name="IsUseUTF8Data" value="-1">
        <param name="BorderStyle" value="1">
		<param name="BorderColor" value="14402205">
        <param name="TitlebarColor" value="15658734">
		<param name="TitlebarTextColor" value="0">
		<param name="MenubarColor" value="14402205">
		<param name="MenuButtonColor" VALUE="16180947">
		<param name="MenuBarStyle" value="3">
		<param name="MenuButtonStyle" value="7">	 
		<param name="Caption" value="">
		<param name="ProductCaption" value="<%=ProductCaption%>">
		<param name="ProductKey" value="<%=ProductKey%>">
		<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
</object>
 <script src="webpage/com/kingtake/officeonline/officeControl.js"></script>
<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
	setFileOpenedOrClosed(false);
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
	setFileOpenedOrClosed(true);
</script>
<script type="text/javascript">
function addMain(){
	if(${fn:length(list)>1}){
		$.dialog({
            title: "模板选择",
            max: false,
            min: false,
            drag: false,
            resize: false,
            content: 'url:tOSendReceiveRegController.do?findModelByRegType&regType=' + $('#sendTypeCode').val(),
            lock:true,
            button : [ {
                name : "确定",
                callback : function() {
                    iframe = this.iframe.contentWindow;
                    var templateRealpath = $('#templateRealpath', iframe.document).val();
                    $('#realPath').val(templateRealpath);
                    addTemplateToMain();
                    this.close();
                    return false;
                },
            }],
            close: function(){
            	window.scrollTo(0,800);
            }
        }).zindex();
	}else if(${fn:length(list)>0}){
		$('#realPath').val('${list[0].FILEPATH}');
		addTemplateToMain();
	}else{
		addTemplateToMain();
	}
}

function addTemplateToMain(){
	$('#officecontrol').attr('style','display:block;height:800px;');
	OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
	OFFICE_CONTROL_OBJ.width="100%";
	OFFICE_CONTROL_OBJ.height="95%";
	addPDFPlugin();
	var realPath = $('#realPath').val();
	if(realPath){
		TANGER_OCX_openFromUrl('<%=rootPath%>/'+realPath,false);
		OFFICE_CONTROL_OBJ.FileSave=false;
	}else{
		alert("此公文种类模板不存在，请在收发文模板管理中上传模板！");
	}
// 	window.scrollTo(0,500); 
}

function saveToUrl(){
	if(OFFICE_CONTROL_OBJ){
		var result = OFFICE_CONTROL_OBJ.SaveToURL("<%=basePath%>/tOOfficeOnlineFilesController.do?uploadOfficeonlineFiles&businesskey=tOSendBill&cusPath=office&id="+$('#contentFileId').val(),"EDITFILE");
		result = $.parseJSON(result);
		if(result.success){
			$('#contentFileId').val(result.obj.id);
		}else{
			alert("保存失败，请刷新页面后重新操作！");
		}
	}
	var contentFlag = checkContent();
	if(!contentFlag){
		return false;
	}
}

function checkContent(){
    var contentFileId = $('#contentFileId').val();
    if(contentFileId){
		return true;
	}else if(OFFICE_CONTROL_OBJ){
	    return true;
	}
	$.Showmsg("请添加正文！");
	return false;
}

function showMain(){
  	$('#officecontrol').attr('style','display:block;height:800px;');
  	OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
	  OFFICE_CONTROL_OBJ.width="100%";
	  OFFICE_CONTROL_OBJ.height="95%";
  	addPDFPlugin();
  	var realPath = $('#realPath').val();
  	if(realPath){
  		TANGER_OCX_openFromUrl('<%=rootPath%>/'+realPath,false);
  		OFFICE_CONTROL_OBJ.SetReadOnly(true);
  	}
  }
</script>
</div>
 </body>
  <script src = "webpage/com/kingtake/office/sendbill/tOSendBill.js"></script>		