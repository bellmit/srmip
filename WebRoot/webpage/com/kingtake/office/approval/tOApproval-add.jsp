<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>呈批件信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link href="webpage/com/kingtake/office/approval/approvalForm.css" rel="stylesheet">
  
<script type="text/javascript">
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
  function formCheck(){
	  var form = $("#formobj").Validform();
      var obj = form.check(false);
      return obj;
  }
  </script>
  <script type="text/javascript">
  //编写自定义JS代码
  
  function choose_297e201048183a730148183ad85c0001(inputId,inputName) {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:departController.do?selectDepartTree&scientificInstitutionFlag=0', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, width: 500, height: 350, opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: function (){
                	iframe = this.iframe.contentWindow;
                    var departname = iframe.getdepartListSelections('text');
                    if ($('#'+inputName).length >= 1) {
                        $('#'+inputName).val(departname);
                        $('#'+inputName).blur();
                    }
                    if ($("input[name='"+inputName+"']").length >= 1) {
                        $("input[name='"+inputName+"']").val(departname);
                        $("input[name='"+inputName+"']").blur();
                    }
                    var id = iframe.getdepartListSelections('id');
                    if (id !== undefined && id != "") {
                        $('#'+inputId).val(id);
                        $("input[name='"+inputId+"']").val(id);
                    }
                }, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:departController.do?selectDepartTree&scientificInstitutionFlag=0', zIndex: 2100, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, parent: windowapi, width: 500, height: 350, opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: function(){
                	iframe = this.iframe.contentWindow;
                    var departname = iframe.getdepartListSelections('text');
                    if ($('#'+inputName).length >= 1) {
                        $('#'+inputName).val(departname);
                        $('#'+inputName).blur();
                    }
                    if ($("input[name='"+inputName+"']").length >= 1) {
                        $("input[name='"+inputName+"']").val(departname);
                        $("input[name='"+inputName+"']").blur();
                    }
                    var id = iframe.getdepartListSelections('id');
                    if (id !== undefined && id != "") {
                        $('#'+inputId).val(id);
                        $("input[name='"+inputId+"']").val(id);
                    }
                }, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
  
  function uploadFile(data){
	  	frameElement.api.opener.reloadTable();
		$("#bid").val(data.obj.id);
		if($(".uploadify-queue-item").length>0){
			upload();
		}else{
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password"  callback="@Override uploadFile" layout="table" action="tOApprovalController.do?doAdd" tiptype="1"  beforeSubmit="saveToUrl()" btnsub="subBtn">
					<input id="registerType" name="registerType" value="${tOApprovalPage.registerType}" type="hidden" class="inputxt">
					<input id="regId" name="regId" type="hidden" value="${tOApprovalPage.regId}">
					<input id="archiveFlag" name="archiveFlag" type="hidden" value="${tOApprovalPage.archiveFlag }">
					<input id="archiveUserid" name="archiveUserid" type="hidden" value="${tOApprovalPage.archiveUserid }">
					<input id="archiveUsername" name="archiveUsername" type="hidden" value="${tOApprovalPage.archiveUsername }">
					<input id="archiveDate" name="archiveDate" type="hidden" value="${tOApprovalPage.archiveDate }">
					<input id="createBy" name="createBy" type="hidden" value="${tOApprovalPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tOApprovalPage.createName }">
<%-- 					<input id="createDate" name="createDate" type="hidden" value="${tOApprovalPage.createDate }"> --%>
					<input id="updateBy" name="updateBy" type="hidden" value="${tOApprovalPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tOApprovalPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tOApprovalPage.updateDate }">
					<input id="backUserid" name="backUserid" type="hidden" value="${tOApprovalPage.backUserid }">
					<input id="backUsername" name="backUsername" type="hidden" value="${tOApprovalPage.backUsername }">
					<input id="backSuggestion" name="backSuggestion" type="hidden" value="${tOApprovalPage.backSuggestion }">
					<input id="title" name="title" type="hidden" value="${tOApprovalPage.title}">
					<input id="regType" name="regType" type="hidden" value="${regType}">
					<input id="realPath" name="realPath" type="hidden" value="${officeFile.realpath }">
					<input id="contentFileId" name="contentFileId" type="hidden" value="${tOApprovalPage.contentFileId}">
					<input id="id" name="id" type="hidden" value="${tOApprovalPage.id }">
<%-- 					<input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"> --%>
					<input id="contactId" name="contactId" type="hidden" value="${tOApprovalPage.contactId }">
					
					<!-- 接收人信息 -->
					<input type="hidden" name="receiverid" id="receiverid">
					<input type="hidden" name="departId" id="departId" >
					<input type="hidden" name="departName" id="departName" >
					<input type="hidden" name="realName" id="realName" >
					<input type="hidden" name="leaderReview" id="leaderReview" >
					<!-- 提交按钮 -->
					<input  type="hidden" name="subBtn" id="subBtn">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><p class="STYLE1">校首长批示：</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p></td>
    <td align="right" valign="top"><t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOApprovalPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;font-weight: bold;font-size: 14px;'}"></t:codeTypeSelect></td>
  </tr>
  <tr>
    <td><p class="STYLE1">部(院)领导批示:</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    </td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="10" cellpadding="0">
  <tr>
    <td colspan="3" align="center" class="STYLE2">呈　批　件</td>
  </tr>
  <tr>
    <td colspan="3" align="center"><%-- <input id="applicationFileno" name="applicationFileno"  type="text" style="border-style: none none solid none;width: 40px;text-align:right;"  datatype="*1-20"  value='${tOApprovalPage.applicationFileno}'/> --%>
    <input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.fileNumPrefix}' datatype="byterange" max="20" min="1" ignore="ignore" readonly="readonly">
				﹝20<input id="approvalYear" name="approvalYear" datatype="*1-2" ignore="ignore" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.approvalYear}' datatype="n1-2" readonly="readonly">﹞
				<input id="applicationFileno" name="applicationFileno" datatype="*1-20" ignore="ignore" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.applicationFileno}' datatype="n1-4" readonly="readonly">号</td>
  </tr>
  <tr>
    <td><span class="STYLE1">承办单位：</span><input id="undertakeUnitName" name="undertakeUnitName" type="text" style="border-style: none none solid none;width: 120px" class="inputxt" value='${tOApprovalPage.undertakeUnitName}' datatype="*" readonly="readonly">
    <input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"></td>
<%--     <t:departComboTree id="bb" name="bb" idInput="undertakeUnitId" nameInput="undertakeUnitName" width="155" lazy="false" value="${tOApprovalPage.undertakeUnitName }"></t:departComboTree> --%>
    <td><span class="STYLE1">联系人：</span><input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOApprovalPage.contactName}' datatype="byterange" max="50" min="1" readonly="readonly"></td>
    <td><span class="STYLE1">电话：</span><input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOApprovalPage.contactPhone}' datatype="byterange" max="30" min="1"></td>
  </tr>
  <tr>
    <td height="3" colspan="3" bgcolor="#339966"></td>
  </tr>
</table>
<table style="width: 100%">
  <tr>
     <td width="10%">正文：</td>
                <c:if test="${empty tOApprovalPage.contentFileId}">
                <td colspan="3"><a class="easyui-linkbutton" data-options="plain:true" onclick="addMain()" href="#officecontrol" icon="icon-add"><c:if test="${empty contentFileId}">添加正文</c:if></a></td>
                </c:if>
                <c:if test="${!empty tOApprovalPage.contentFileId}">
                <td title="正文格式：${officeFile.extend} 创建人：${officeFile.createName} 创建时间：<fmt:formatDate value='${officeFile.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"><font color="black">(${officeFile.extend}格式)&nbsp;${officeFile.createName}&nbsp;<fmt:formatDate value='${officeFile.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</font><a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td>
                </c:if>
  </tr>
</table>
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
            content: 'url:tOSendReceiveRegController.do?findModelByRegType&regType=' + $('#regType').val(),
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
            	window.scrollTo(0,500);
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
    if($('#contentFileId').val()){
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
<table width="100%" border="0" cellspacing="10" cellpadding="0">
<!--   <tr> -->
<%--     <td align="center"><input placeholder="请输入标题" id="title" name="title" class="STYLE3" style="border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" value='${tOApprovalPage.title}' datatype="byterange" max="200" min="1"></td> --%>
<!--   </tr> -->
<!--   <tr> -->
<%--     <td><p><input id="receiveUnitid" name="receiveUnitid" type="hidden" value="${tOApprovalPage.receiveUnitid }"> --%>
<%--     <input placeholder="请输入接收单位(双击选择)" id="receiveUnitname" name="receiveUnitname" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;" ondblclick="choose_297e201048183a730148183ad85c0001('receiveUnitid','receiveUnitname')" value='${tOApprovalPage.receiveUnitname}'  datatype="*">： <br> --%>
<!--        </p> -->
<%--       <p><textarea id="applicationContent" name="applicationContent" rows="7" style="width: 750px;" placeholder="请输入内容" datatype="byterange" max="4000" min="1">${tOApprovalPage.applicationContent}</textarea></p> --%>
<%--     <p align="right"><input id="signUnitid" name="signUnitid" type="hidden" value="${tOApprovalPage.signUnitid }"> --%>
<%--     <input placeholder="请输入落款单位(双击选择)" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;width:142px;"  id="signUnitname" name="signUnitname" value='${tOApprovalPage.signUnitname}' ondblclick="choose_297e201048183a730148183ad85c0001('signUnitid','signUnitname')" datatype="*"></p> --%>
<!--     <p align="right"><input id="createDate" name="createDate" type="text" style="width: 140px"  -->
<%-- 					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOApprovalPage.createDate}' type='date' pattern='yyyy-MM-dd'/>"></td> --%>
<!--   </tr> -->
  <tr><td><label class="Validform_label">  呈批项目: </label>
  <input id="projectRelaName" name="projectRelaName" type="text" style="width: 450px" class="inputxt" value="${tOApprovalPage.projectRelaName }">
  <input id="projectRelaId" name="projectRelaId" type="hidden" value="${tOApprovalPage.projectRelaId }">
  <span class="Validform_checktip" style="display: none;"></span>
  </td></tr>
  
  <tr>
  <td><span class="STYLE1">附件</span>
  <input type="hidden" value="${tOApprovalPage.id }" id="bid" name="bid"/>
  <table>
        <c:forEach items="${files}" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${file.bid}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
            <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
          </tr>
        </c:forEach>
      </table>
      </td>
  </tr>
  <tr>
      <td><script type="text/javascript">
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
      <t:upload  queueID="filediv" name="fiels" id="file_upload"  buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOApproval">
  </t:upload>
  </div>
 </td>
</tr>
</table>
		</t:formvalid>
 </body>
  <script type="text/javascript"  src = "webpage/com/kingtake/office/approval/tOApproval.js"></script>		