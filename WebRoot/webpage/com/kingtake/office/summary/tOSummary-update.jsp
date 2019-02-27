<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@include file="/context/officeControl.jsp"%>
<%
    TSUser user = ResourceUtil.getSessionUserName();
			String userName = user.getRealName();
%>
<!DOCTYPE html>
<html>
<head>
<title>总结材料</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
     $(function(){
         var contentFileId = $("#contentFileId").val();
         if(contentFileId!=""){
             addFileContent();
         }
     });
     
     function ntkoSet(){
         OFFICE_CONTROL_OBJ.TrackRevisions(true);
     }
</script>
</head>
<body onload="ntkoSet()">
  <input id="realPath" name="realPath" type="hidden" value="${file.realpath}">
  <input id="userName" type="hidden" value="<%=userName%>">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOSummaryController.do?doUpdate" tiptype="1" beforeSubmit="saveToUrl()">
    <input id="id" name="id" type="hidden" value="${tOSummaryPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tOSummaryPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOSummaryPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOSummaryPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOSummaryPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOSummaryPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOSummaryPage.updateDate }">
    <input id="contentFileId" name="contentFileId" type="hidden" value="${tOSummaryPage.contentFileId}">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="width: 100px;"><label class="Validform_label">
            标题: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="title" name="title" type="text" value='${tOSummaryPage.title}' datatype="*" style="width: 180px;"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            单位: <font color="red">*</font>
          </label></td>
        <td class="value"><t:departComboTree id="dept" nameInput="deptName" idInput="deptId" lazy="false" value="${tOSummaryPage.deptId}" width="185"></t:departComboTree> <input id="deptId"
            name="deptId" type="hidden" class="inputxt" value='${tOSummaryPage.deptId}' dataType="*"> <input id="deptName" name="deptName" type="hidden" value='${tOSummaryPage.deptName}'>
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位</label></td>
      </tr>
      <tr>
        <td align="right" style="width: 100px;"><label class="Validform_label">
            材料类型: <font color="red">*</font>
          </label></td>
        <td class="value"><t:codeTypeSelect id="materiaType" name="materiaType" codeType="1" code="ZJCLLX" type="select" labelText="请选择" defaultVal="${tOSummaryPage.materiaType}" extendParam="{'datatype':'*','style':'width:185px;'}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">材料类型</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            正文: &nbsp;&nbsp;&nbsp;
          </label></td>
        <td class="value"><input type="button" id="showBtn" onclick="showNtko()" value="显示/隐藏文档">
          <div id="officecontrol" style="height: 400px; width: 100%;">
            <object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="100%" height="95%">
              <param name="IsUseUTF8URL" value="-1">
              <param name="IsUseUTF8Data" value="-1">
              <param name="BorderStyle" value="1">
              <param name="BorderColor" value="14402205">
              <param name="TitlebarColor" value="15658734">
              <param name="TitlebarTextColor" value="0">
              <param name="Menubar" value="1">
              <param name="FileNew" value="1">
              <param name="FileOpen" value="1">
              <param name="FileClose" value="1">
              <param name="FileSave" value="1">
              <param name="FileSaveAs" value="-1">
              <param name="FilePrint" value="0">
              <param name="FilePrintPreview" value="0">
              <param name="FilePageSetup" value="0">
              <param name="FileProperties" value="0">
              <param name="ToolBars" value="1">
              <param name="MenubarColor" value="14402205">
              <param name="MenuButtonColor" VALUE="16180947">
              <param name="MenuBarStyle" value="3">
              <param name="MenuButtonStyle" value="7">
              <param name="Caption" value="">
              <param name="ProductCaption" value="<%=ProductCaption%>">
              <param name="ProductKey" value="<%=ProductKey%>">
              <param name="WebUserName" value="<%=userName%>">
              <SPAN STYLE="color: red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
            </object>
            <script src="webpage/com/kingtake/officeonline/officeControl.js"></script>
            <script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
                setFileOpenedOrClosed(false);
             </script>
            <script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
                setFileOpenedOrClosed(true);
            </script>
             <script type="text/javascript">
function addFileContent(){
	var realPath = $('#realPath').val();
	if(realPath){
        $("#officecontrol").show();
		TANGER_OCX_openFromUrl('<%=rootPath%>/'+realPath,false);
		OFFICE_CONTROL_OBJ.FileSave=false;
	}
}

function saveToUrl(){
	if(OFFICE_CONTROL_OBJ){
		var result = OFFICE_CONTROL_OBJ.SaveToURL("<%=basePath%>/tOOfficeOnlineFilesController.do?uploadOfficeonlineFiles&businesskey=summary&cusPath=office&id="+$('#contentFileId').val(),"EDITFILE");
		result = $.parseJSON(result);
		if(result.success){
			$('#contentFileId').val(result.obj.id);
		}else{
			alert("保存失败，请刷新页面后重新操作！");
		}
	}else{
	    $.Showmsg("请添加正文！");
	}
	//TANGER_OCX_OBJ.Close();
}

     function showNtko() {
           $("#officecontrol").toggle();
       }
     
    </script>
          </div> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">正文</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/summary/tOSummary.js"></script>