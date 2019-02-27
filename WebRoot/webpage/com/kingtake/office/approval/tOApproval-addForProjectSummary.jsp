<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>呈批件信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<link href="webpage/com/kingtake/office/approval/approvalForm.css" rel="stylesheet">
<script type="text/javascript">
	//编写自定义JS代码
	$(function(){
		$("#saveBtn").click(function(){
	        $("#btn_sub").click();
	    });
		$("#saveAndSend").click(function(){
			 if(formCheck()==true){
				var addForm = $('#formobj').serialize();
				 $.post('tOApprovalProjectSummaryController.do?doAdd&projectIds=${projectIds}',addForm,function(data){
					data = JSON.parse(data);
					uploadFile(data);
					var id = data.obj.id;
					$.dialog({
						content: 'url:tOApprovalController.do?goSend&id='+id,
						lock : true,
						width:520,
						height:220,
						title:"发送",
						opacity : 0.3,
						cache:false,
						okVal: '发送',
					    ok: function(){
					    	iframe = this.iframe.contentWindow;
							saveObj();
// 							window.parent.parent.location.reload();
							return false;
					    },
					    cancel: true 
					 }).zindex();
				}); 
			} 
	    });
	})
	
	
	function formCheck() {
		var form = $("#formobj").Validform();
		var obj = form.check(false);
		return obj;
	}
	function choose_297e201048183a730148183ad85c0001(inputId, inputName) {
		if (typeof (windowapi) == 'undefined') {
			$
					.dialog({
						content : 'url:departController.do?selectDepartTree&scientificInstitutionFlag=0',
						zIndex : 2100,
						title : '<t:mutiLang langKey="common.department.list"/>',
						lock : true,
						width : 500,
						height : 350,
						opacity : 0.4,
						button : [
								{
									name : '<t:mutiLang langKey="common.confirm"/>',
									callback : function() {
										iframe = this.iframe.contentWindow;
										var departname = iframe
												.getdepartListSelections('text');
										if ($('#' + inputName).length >= 1) {
											$('#' + inputName).val(departname);
											$('#' + inputName).blur();
										}
										if ($("input[name='" + inputName + "']").length >= 1) {
											$("input[name='" + inputName + "']")
													.val(departname);
											$("input[name='" + inputName + "']")
													.blur();
										}
										var id = iframe
												.getdepartListSelections('id');
										if (id !== undefined && id != "") {
											$('#' + inputId).val(id);
											$("input[name='" + inputId + "']")
													.val(id);
										}
									},
									focus : true
								},
								{
									name : '<t:mutiLang langKey="common.cancel"/>',
									callback : function() {
									}
								} ]
					});
		} else {
			$
					.dialog({
						content : 'url:departController.do?selectDepartTree&scientificInstitutionFlag=0',
						zIndex : 2100,
						title : '<t:mutiLang langKey="common.department.list"/>',
						lock : true,
						parent : windowapi,
						width : 500,
						height : 350,
						opacity : 0.4,
						button : [
								{
									name : '<t:mutiLang langKey="common.confirm"/>',
									callback : function() {
										iframe = this.iframe.contentWindow;
										var departname = iframe
												.getdepartListSelections('text');
										if ($('#' + inputName).length >= 1) {
											$('#' + inputName).val(departname);
											$('#' + inputName).blur();
										}
										if ($("input[name='" + inputName + "']").length >= 1) {
											$("input[name='" + inputName + "']")
													.val(departname);
											$("input[name='" + inputName + "']")
													.blur();
										}
										var id = iframe
												.getdepartListSelections('id');
										if (id !== undefined && id != "") {
											$('#' + inputId).val(id);
											$("input[name='" + inputId + "']")
													.val(id);
										}
									},
									focus : true
								},
								{
									name : '<t:mutiLang langKey="common.cancel"/>',
									callback : function() {
									}
								} ]
					});
		}
	}

	function uploadFile(data) {
		$("#bid").val(data.obj.id);
		if ($(".uploadify-queue-item").length > 0) {
			upload();
		} else {
			frameElement.api.opener.reloadTable();
			frameElement.api.close();
		}
	}

	function close() {
		frameElement.api.close();
	}
</script>
</head>
<body>
<!-- <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;"> -->
<!-- 		<h1 align="center">呈批件信息</h1> -->
<!-- 		<span><input id="saveAndSend" type="button" style="position: fixed; right: 75px; top: 3px;" value="保存并发送"><input id="saveBtn" type="button" style="position: fixed; right: 5px;top: 3px;" value="保存" ></span> -->
<!-- 	</div> -->
<!-- 	<br/> -->
<!-- 	<br/> -->
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" callback="@Override uploadFile" layout="table" action="tOApprovalProjectSummaryController.do?doAdd&projectIds=${projectIds}" tiptype="1">
		<input id="archiveFlag" name="archiveFlag" type="hidden" value="${tOApprovalPage.archiveFlag }">
		<input id="archiveUserid" name="archiveUserid" type="hidden" value="${tOApprovalPage.archiveUserid }">
		<input id="archiveUsername" name="archiveUsername" type="hidden" value="${tOApprovalPage.archiveUsername }">
		<input id="archiveDate" name="archiveDate" type="hidden" value="${tOApprovalPage.archiveDate }">
		<input id="createBy" name="createBy" type="hidden" value="${tOApprovalPage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tOApprovalPage.createName }">
		<input id="registerType" name="registerType" type="hidden" value="1">
		<%-- 					<input id="createDate" name="createDate" type="hidden" value="${tOApprovalPage.createDate }"> --%>
		<input id="updateBy" name="updateBy" type="hidden" value="${tOApprovalPage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tOApprovalPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tOApprovalPage.updateDate }">
		<input id="backUserid" name="backUserid" type="hidden" value="${tOApprovalPage.backUserid }">
		<input id="backUsername" name="backUsername" type="hidden" value="${tOApprovalPage.backUsername }">
		<input id="backSuggestion" name="backSuggestion" type="hidden" value="${tOApprovalPage.backSuggestion }">
		<input id="businessTablename" name="businessTablename" type="hidden" value="${tOApprovalPage.businessTablename }">
		<input id="id" name="id" type="hidden" value="${tOApprovalPage.id }">
		<%-- 					<input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"> --%>
		<input id="contactId" name="contactId" type="hidden" value="${tOApprovalPage.contactId }">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td  valign="top"><p class="STYLE1" >校首长批示：</p>
					<p>&nbsp;</p>
					<p>&nbsp;</p></td>
				<td align="right" valign="top"><t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOApprovalPage.secrityGrade}" type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;font-weight: bold;font-size: 14px;'}"></t:codeTypeSelect></td>
			</tr>
			<tr>
				<td  valign="top"><p class="STYLE1">部(院)领导批示:</p>
					<p>&nbsp;</p>
					<p>&nbsp;</p></td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="10" cellpadding="0">
			<tr>
				<td colspan="3" align="center" class="STYLE2">呈 批 件</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.fileNumPrefix}' datatype="byterange" max="20" min="1">〔20<input id="approvalYear" name="approvalYear" type="text" style="border-style: none none solid none;border-color:#54A5D5;border-width: 1px; width: 20px;" datatype="n1-2" value='${tOApprovalPage.approvalYear}' />〕<input id="applicationFileno" name="applicationFileno" type="text" style="border-style: none none solid none;border-color:#54A5D5;border-width: 1px; width: 40px;" datatype="n1-4" value='${tOApprovalPage.applicationFileno}' />号
				</td>
			</tr>
			<tr>
				<td><span class="STYLE1">承办单位：</span>
				<t:departComboTree id="bb" name="bb" idInput="undertakeUnitId" nameInput="undertakeUnitName" width="155" lazy="false" value="${tOApprovalPage.undertakeUnitName }"></t:departComboTree><input id="undertakeUnitName" name="undertakeUnitName" type="hidden" style="width: 120px" class="inputxt" value='${tOApprovalPage.undertakeUnitName}' datatype="*"><input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"></td>
				<td><span class="STYLE1">联系人：</span><input id="contactName" name="contactName" type="text" style="border-style: none none solid none;border-color:#54A5D5;border-width: 1px; width: 100px" class="inputxt" value='${tOApprovalPage.contactName}' datatype="byterange" max="50" min="1"></td>
				<td><span class="STYLE1">电话：</span><input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;border-color:#54A5D5;border-width: 1px; width: 100px" class="inputxt" value='${tOApprovalPage.contactPhone}' datatype="byterange" max="30" min="1"></td>
			</tr>
			<tr>
				<td height="3" colspan="3" bgcolor="#339966"></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="10" cellpadding="0">
			<tr>
				<td align="center"><input placeholder="请输入标题" id="title" name="title" class="STYLE3" style="border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" value='${tOApprovalPage.title}'  datatype="byterange" max="200" min="1"></td>
			</tr>
			<tr>
				<td><p>
						<input id="receiveUnitid" name="receiveUnitid" type="hidden" value="${tOApprovalPage.receiveUnitid }"> <input placeholder="请输入接收单位(双击选择)" id="receiveUnitname" name="receiveUnitname" style="border-style: none none solid none; border-color: #54A5D5; border-width: 1px;" ondblclick="choose_297e201048183a730148183ad85c0001('receiveUnitid','receiveUnitname')" value='${tOApprovalPage.receiveUnitname}' datatype="*">： <br>
					</p>
					<p>
						<textarea id="applicationContent" name="applicationContent" rows="7" style="width: 750px;" placeholder="请输入内容" datatype="byterange" max="4000" min="1">${tOApprovalPage.applicationContent}</textarea>
					</p>
					<p align="right">
						<input id="signUnitid" name="signUnitid" type="hidden" value="${tOApprovalPage.signUnitid }"> <input placeholder="请输入落款单位(双击选择)" style="border-style: none none solid none; border-color: #54A5D5; border-width: 1px;width:142px;" id="signUnitname" name="signUnitname" value='${tOApprovalPage.signUnitname}' ondblclick="choose_297e201048183a730148183ad85c0001('signUnitid','signUnitname')" datatype="*">
					</p>
					<p align="right">
						<input id="createDate" name="createDate" type="text" style="width: 140px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOApprovalPage.createDate}' type='date' pattern='yyyy-MM-dd'/>"></td>
			</tr>
			<tr>
				<td><span class="STYLE1">附件</span> <input type="hidden" value="${tOApprovalPage.id }" id="bid" name="bid" />
					<table>
						<c:forEach items="${tOApprovalPage.certificates }" var="file" varStatus="idx">
							<tr style="height: 30px;">
								<td style="width: 60%; white-space: nowrap;">${file.attachmenttitle}</td>
								<td style="width: 10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
								<td style="width: 10%;"><a href="javascript:void(0);" onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tOApprovalPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity','fList','800','700')">预览</a></td>
								<td style="width: 10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
							</tr>
						</c:forEach>
					</table></td>
			</tr>
			<tr>
				<td>
					<div>
						<div class="form" id="filediv"></div>
						<t:upload queueID="filediv" name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOApproval">
						</t:upload>
					</div></td>
			</tr>
		</table>
		
		<div style="width: 880px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
					<t:tab iframe="tOApprovalProjectSummaryController.do?gotPmProjectListForApproval&projectIds=${projectIds}" icon="icon-search" title="项目信息" id="t" heigth="400"></t:tab>
			</t:tabs>
		</div>
	</t:formvalid>
		
</body>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/office/approval/tOApproval.js"></script>