<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>office模板文件</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
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
	function initPage(){
		OFFICE_CONTROL_OBJ=document.all("TANGER_OCX");
<%-- 		OFFICE_CONTROL_OBJ.openFromUrl("<%=rootPath%>/${tSOfficeModelFilesPage.realpath}"); --%>
	}
	
	$(function() {
		$("#combox").combobox({
			onSelect:function(data){
				$('#id').val(data.ID);
				$('#templateFileId').val(data.TEMPLATEFILEID);
				$('#modelName').val(data.MODELNAME);
        TANGER_OCX_openFromUrl("<%=rootPath%>/"+data.REALPATH);
			},
			onLoadSuccess : function(data) {
				$("#combox").combobox('setValue', data[0].ID);
				$('#id').val(data[0].ID);
				$('#templateFileId').val(data[0].TEMPLATEFILEID);
				$('#modelName').val(data[0].MODELNAME);
        TANGER_OCX_openFromUrl("<%=rootPath%>/"+data[0].REALPATH);
			}
		});
	});
	
	function getFileByModelId(id){
		$.ajax({
			url:'tSOfficeModelFilesController.do?getFileByModelId&id='+id,
			type : 'POST',
			timeout : 3000,
			dataType : 'json',
			success:function(data){
			}
		});
	}
	
	function checkFile(){
		if($('#templateFileId').val()){
			return true;
		}
		$.Showmsg("请选择模板！");
		return false;
	}
	
	function deleteTemplate(){
	  if(OFFICE_CONTROL_OBJ)
	    OFFICE_CONTROL_OBJ.Close();
		$.ajax({
			url:'tSOfficeModelFilesController.do?deleteTemplate&id='+$('#id').val(),
			type : 'POST',
			timeout : 3000,
			dataType : 'json',
			success:function(data){
				alert("操作成功！");
				frameElement.api.reload(frameElement);
			}
		});
	}
  </script>
 </head>
 <body onload="initPage()"  class="easyui-layout">
 <div data-options="region:'west',split:true" style="width:300px;">
 
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSOfficeModelFilesController.do?doUpdate"  callback="@Override uploadFile"  beforeSubmit="saveToUrl()" tiptype="1">
					<input id="id" name="id" type="hidden" style="width: 280px;" value="${tSOfficeModelFilesPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tSOfficeModelFilesPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tSOfficeModelFilesPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tSOfficeModelFilesPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSOfficeModelFilesPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tSOfficeModelFilesPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSOfficeModelFilesPage.updateDate }">
					<input id="codeDetailId" name="codeDetailId" type="hidden" value="${tSOfficeModelFilesPage.codeDetailId }">
					<input id="templateFileId" name="templateFileId" type="hidden" value="${tSOfficeModelFilesPage.templateFileId }">
					<input id="modelName" name="modelName" type="hidden" value="${tSOfficeModelFilesPage.modelName }">
					<input id="extend" name="extend" type="hidden" value="${tSOfficeModelFilesPage.extend }">
					<input id="realpath" name="realpath" type="hidden" value="${tSOfficeModelFilesPage.realpath }">
<%-- 					<input id="businesskey" name="businesskey" type="hidden" value="${tSOfficeModelFilesPage.businesskey}"> --%>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							公文种类:
						</label>
					</td>
					<td class="value">
					     	 <input id="filetype" name="filetype" type="hidden" style="width: 150px" class="inputxt" >
					     	 <t:codeTypeSelect name="filetype" type="select" codeType="1" code="GWZL" id="codetype" defaultVal="${code}" extendParam="{'disabled':'disabled'}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">文件类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
						  <label class="Validform_label">模板名称:
						  </label>
					    </td>
					    <td class="value">
					      <input id="combox" name="combox" class="easyui-combobox" editable="false" data-options='valueField: "ID",textField: "MODELNAME",data: ${array}' >
					    </td>
					</tr>
					<tr>
						<td align="right">
						  <label class="Validform_label">
						  </label>
					    </td>
					    <td class="value">
					    	<a class="easyui-linkbutton" href="javascript:deleteTemplate()" icon="icon-cancel">删除当前模板</a>
					    </td>
					</tr>
			</table>
		</t:formvalid>
		</div>
		<div data-options="region:'center'" style="padding:5px;background:#eee;">
		
		<div id="officecontrol"  style="height: 800px;">
<!-- 		<a class="easyui-linkbutton" href="javascript:saveToUrl()" icon="icon-save">保存到服务器</a> -->
		<object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="100%" height="95%">
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
		<param name="FileSave" value="0">
		<param name="ProductCaption" value="<%=ProductCaption%>">
		<param name="ProductKey" value="<%=ProductKey%>">
		<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
</object>
<!--  <script src="webpage/com/kingtake/officeonline/officeControl.js"></script> -->
<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
	setFileOpenedOrClosed(false);
</script>
<script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
	setFileOpenedOrClosed(true);
</script>
		</div>
		</div> 
		<script type="text/javascript">
var OFFICE_CONTROL_OBJ;//控件对象
var IsFileOpened;      //控件是否打开文档
var fileType ;
var fileTypeSimple;



function setFileOpenedOrClosed(bool)
{
	OFFICE_CONTROL_OBJ=document.all("TANGER_OCX");
	IsFileOpened = bool;
	fileType = OFFICE_CONTROL_OBJ.DocType ;
}

function TANGER_OCX_OnDocActivated(IsActivated){
	
}


function TANGER_OCX_openFromUrl(templateUrl)
{
	OFFICE_CONTROL_OBJ=document.all("TANGER_OCX");
	OFFICE_CONTROL_OBJ.openFromUrl(templateUrl);
}

function saveToUrl(){
	if(OFFICE_CONTROL_OBJ){
		var url="<%=basePath%>/tOOfficeOnlineFilesController.do?uploadOfficeonlineFiles&cusPath=template&businesskey=toSendReceiveTemplateFile&id="+$('#templateFileId').val();
		var result = OFFICE_CONTROL_OBJ.SaveToURL(url,"EDITFILE");
		result = $.parseJSON(result);
		if(result.success){
			$('#templateFileId').val(result.obj.id);
		}else{
			alert("保存失败，请刷新页面后重新操作！");
		}
	}
	var fileFlag = checkFile();
	if(!fileFlag){
		return false;
	}
}


</script>
 </body>
  <script src = "webpage/com/kingtake/officeonline/tSOfficeModelFiles.js"></script>		