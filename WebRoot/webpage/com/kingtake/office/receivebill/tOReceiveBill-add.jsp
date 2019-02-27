<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<!-- <!DOCTYPE html> -->
<!-- <html> -->
<!--  <head> -->
<!--   <title>收文阅批单信息表</title> -->
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<link href="webpage/com/kingtake/office/receivebill/receiveBillForm.css" rel="stylesheet">
<script type="text/javascript">
	$(document).ready(function() {
		$('#tt').tabs({
			onSelect : function(title) {
				$('#tt .panel-body').css('width', 'auto');
			}
		});
		$(".tabs-wrap").css('width', '100%');
	});
	function formCheck() {
		var form = $("#formobj").Validform();
		var obj = form.check(false);
		return obj;
	}

	function reloadParent() {
		var parentPage = window.parent.regList;
		windowapi.opener.reloadTable();
		$('#reloadBtn', parentPage.document).click();
	}

	function uploadFile(data) {
		frameElement.api.opener.reloadTable();
		$("#bid").val(data.obj.id);
		if ($(".uploadify-queue-item").length > 0) {
			upload();
		} else {
			// 			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}

	function close() {
		frameElement.api.close();
	}

	
	function parse(data) {
		var parsed = [];
		$.each(data.rows, function(index, row) {
			parsed.push({
				data : row,
				result : row,
				value : row.id
			});
		});
		return parsed;
	}
	/**
	 * 选择后回调 
	 * 
	 * @param {Object} data
	 */
	function callBack(data) {
		$("#receiveUnitName").val(data.unitName);
		$("#receiveUnitName1").val(data.unitName);
	}

	/**
	 * 每一个选择项显示的信息
	 * 
	 * @param {Object} data
	 */
	function formatItem(data) {
		return data.unitName;
	}

	function checkForm() {
		if ($('.uploadify-queue-item').size() > 0) {
			return true;
		}
		$.Showmsg("请添加附件！");
		return false;
	}
	
	function checkContent(){
		if($('#contentFileId').val()){
			return true;
		}
		$.Showmsg("请添加正文！");
		return false;
	}
	
	function uidChange(){
		var uid = $('#contactId').val();
		$.ajax({
    		async : false,
			cache : false,
    		url:'tPmProjectMemberController.do?findInfoById',
    		data:{'uid':uid},
    		success : function(data) {
    			var d = $.parseJSON(data);
    			$('#contactTel').val(d.attributes.user.officePhone);
    		}
    	});
	}
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" callback="@Override uploadFile" usePlugin="password" layout="table" action="tOReceiveBillController.do?doAdd" tiptype="1" beforeSubmit="saveToUrl()">
		<%-- 					<input id="id" name="id" type="hidden" value="${tOReceiveBillPage.id }"> --%>
		<input id="registerType" name="registerType" value="${tOReceiveBillPage.registerType}" type="hidden" class="inputxt">
		<input id="regType" name="regType" type="hidden" value="${tOReceiveBillPage.regType}">
		<input id="regId" name="regId" type="hidden" value="${tOReceiveBillPage.regId}">
		<input id="registerId" name="registerId" type="hidden" value="${tOReceiveBillPage.registerId}">
		<input id="registerDepartId" name="registerDepartId" type="hidden" value="${tOReceiveBillPage.registerDepartId }">
		<input id="archiveUserid" name="archiveUserid" type="hidden" value="${tOReceiveBillPage.archiveUserid }">
		<input id="createBy" name="createBy" type="hidden" value="${tOReceiveBillPage.createBy}">
		<input id="updateBy" name="updateBy" type="hidden" value="${tOReceiveBillPage.updateBy }">
		<input id="registerTime" name="registerTime" type="hidden" value='<fmt:formatDate value='${tOReceiveBillPage.registerTime}' type="date" pattern="yyyy-MM-dd"/>'>
		<input id="registerName" name="registerName" type="hidden" value="${tOReceiveBillPage.registerName }">
		<input id="registerDepartName" name="registerDepartName" type="hidden" value="${tOReceiveBillPage.registerDepartName }">
		<input id="archiveFlag" name="archiveFlag" type="hidden" value="${tOReceiveBillPage.archiveFlag }">
		<input id="archiveUsername" name="archiveUsername" type="hidden" value="${tOReceiveBillPage.archiveUsername }">
		<input id="archiveDate" name="archiveDate" type="hidden" value="${tOReceiveBillPage.archiveDate }">
		<input id="createName" name="createName" type="hidden" value="${tOReceiveBillPage.createName }">
		<input id="createDate" name="createDate" type="hidden" value='<fmt:formatDate value='${tOReceiveBillPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
		<input id="updateName" name="updateName" type="hidden" value="${tOReceiveBillPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tOReceiveBillPage.updateDate }">
		<input id="realPath" name="realPath" type="hidden" value="${officeFile.realpath }">
		<input id="contentFileId" name="contentFileId" type="hidden" value="${tOReceiveBillPage.contentFileId}">
		<!-- <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="60" align="center" class="title4"><p align="center" >海军工程大学收文阅批单</p></td>
  </tr>
</table> -->
		<div align="center" style="font-size: 24px; height: 60px;">海军工程大学收文阅批单</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse: collapse;'>
			<tr>
				<td width="90" align="center" class="title2">来文<br>单位
				</td>
				<td class="title3" nowrap="nowrap"><input id="receiveUnitName" name="receiveUnitName" type="text" style="border-style: none none solid none;width: 280px;" value="${tOReceiveBillPage.receiveUnitName }" readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">来文单位名</label></td>
				<td width="90" align="center" class="title2">公文<br> 编号
				</td>
				<td class="title3" nowrap="nowrap">
					<input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.fileNumPrefix}' readonly="readonly"> ﹝20<input id="fileNumYear" name="fileNumYear" datatype="*0-2" type="text" style="width: 20px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.fileNumYear}' readonly="readonly">﹞ <input id="billNum" name="billNum" 
					type="text" style="width: 50px; border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.billNum}' readonly="readonly">号 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">公文编号</label>
				</td>
				<td width="90" align="center" class="title2">密级</td>
				<td class="title3" nowrap="nowrap">
				<t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOReceiveBillPage.secrityGrade}" type="select" code="XMMJ" codeType="0"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">密级</label>
				</td>
			</tr>
			<tr>
				<td align="center" class="title2">公文<br> 标题
				</td>
				<td class="title3" colspan="5"><input id="title" name="title" type="text" datatype="*1-200" style="border-style: none none solid none; width: 85%;" class="inputxt" value="${tOReceiveBillPage.title}"  readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">标题</label></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td align="center" class="title2">正文 -->
<!-- 				</td> -->
<!-- 				<td class="title3" colspan="5"><a class="easyui-linkbutton"  onclick="addMain()" href="#officecontrol" icon="icon-add">添加正文</a><a id="roll" href="#officecontrol"></a></td> -->
<!-- 			</tr> -->
			<tr>
				<td align="center" class="title2">
					<p>
						校<br> 首<br> 长<br> 阅<br> 批
					</p>
				</td>
				<td class="title3" colspan="5"><textarea id="leaderReview" name="leaderReview" style="width: 95%;" rows="7" class="inputxt" disabled="disabled"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">校首长阅批</label></td>
			</tr>
			<tr>
				<td align="center" class="title2">
					<p>
						机<br> 关<br> 部<br> (院)<br> 领<br> 导<br> 阅<br> 批
					</p>
				</td>
				<td class="title3" colspan="5"><textarea id="officeReview" name="officeReview" style="width: 95%;" rows="9" class="inputxt" disabled="disabled"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">领导阅批</label></td>
			</tr>
			<tr>
				<td align="center" class="title2">
					<p>
						承<br> 办<br> 单<br> 位<br> 意<br> 见
					</p>
				</td>
				<td class="title3" colspan="5"><textarea id="dutyOpinion" name="dutyOpinion" style="width: 95%;" rows="8" class="inputxt" disabled="disabled"></textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办单位意见</label></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="10">
			<tr valign="top">
				<td>承办单位： 
<%-- 				<t:departComboTree id="cc" name="cc" idInput="dutyId" nameInput="dutyName" lazy="true" ></t:departComboTree>  --%>
				<input id="dutyId" name="dutyId" type="hidden" style="width: 150px" class="inputxt" value="${tOReceiveBillPage.dutyId}"> 
				<input id="dutyName" name="dutyName" type="text" style="width: 150px" class="inputxt" value="${tOReceiveBillPage.dutyName}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办单位</label>
				</td>
				<td>联系人： <input id="contactId" name="contactId" type="hidden" value="${tOReceiveBillPage.contactId}"> 
				<input id="contactName" name="contactName" type="text" style="border-style: none none solid none; width: 150px" class="inputxt"  datatype="byterange" max="36" min="1" value="${tOReceiveBillPage.contactName}">
				 <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId,departName,mobilePhone" inputTextname="contactId,contactName,dutyId,dutyName,contactTel"  isclear="true" idInput="contactId" mode="single" fun="uidChange"></t:chooseUser> 
				 <span class="Validform_checktip"></span> 
				 <label class="Validform_label" style="display: none;">联系人姓名</label>
				</td>
				<td>电话： <input id="contactTel" name="contactTel" type="text" datatype="n1-16" style="border-style: none none solid none; width: 150px" class="inputxt" value="${tOReceiveBillPage.contactTel}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">电话</label>
				</td>
			</tr>
		</table>
		<table style="width: 100%">
  <tr>
     <td width="10%">正文：</td>
                <c:if test="${empty tOReceiveBillPage.contentFileId}">
                <td><a class="easyui-linkbutton"  onclick="addMain()" href="#officecontrol" icon="icon-add"><c:if test="${empty contentFileId}">添加正文</c:if></a></td>
                </c:if>
                <c:if test="${!empty tOReceiveBillPage.contentFileId}">
                <td title="正文格式：${officeFile.extend} 创建人：${officeFile.createName} 创建时间：<fmt:formatDate value='${officeFile.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"><font color="black">(${officeFile.extend}格式)&nbsp;${officeFile.createName}&nbsp;<fmt:formatDate value='${officeFile.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</font><a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td>
                </c:if>
  </tr>
</table>
		<table style="width: 100%;">
			<tr>
				<td width="10%"><font color="black">附件</font></td>
				<td width="90%"><input type="hidden" value="${tOReceiveBillPage.id }" id="bid" name="bid" />
					<table>
						<c:forEach items="${files}" var="file" varStatus="idx">
							<tr>
								<td style="width: 60%; white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${file.bid}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
								<td style="width: 10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
								<%--             <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td> --%>
							</tr>
						</c:forEach>
					</table> <script type="text/javascript">
						$.dialog.setting.zIndex = 1990;
						function del(url, obj) {
							$.dialog.confirm("确认删除该条记录?", function() {
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
							}, function() {
							});
						}
					</script>
					<div>
						<div class="form" id="filediv"></div>
						<t:upload queueID="filediv" name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOReceiveBill">
						</t:upload>
					</div></td>
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
</div>
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
// 	$('#roll').click();
}

function saveToUrl(){
	if(OFFICE_CONTROL_OBJ){
		var result = OFFICE_CONTROL_OBJ.SaveToURL("<%=basePath%>/tOOfficeOnlineFilesController.do?uploadOfficeonlineFiles&businesskey=tOReceiveBill&cusPath=office&id="+$('#contentFileId').val(),"EDITFILE");
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
</body>
<script src="webpage/com/kingtake/office/receivebill/tOReceiveBill.js"></script>
