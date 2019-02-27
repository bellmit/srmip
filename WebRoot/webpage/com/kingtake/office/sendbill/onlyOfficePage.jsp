<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<body onload="showMain()">
<input id="realPath" name="realPath" type="hidden" value="${file.realpath}">

<div id="officecontrol"  style="display:block;height:100%;">
<!-- 		<a class="easyui-linkbutton" href="javascript:saveAs()" icon="icon-save">另存为</a> -->
		<object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="100%" height="95%">
        <param name="IsUseUTF8URL" value="-1">
        <param name="IsUseUTF8Data" value="-1">
        <param name="BorderStyle" value="1">
		<param name="BorderColor" value="14402205">
        <param name="TitlebarColor" value="15658734">
		<param name="TitlebarTextColor" value="0">
		<param name="Menubar" value="-1">
		<param name="FileNew" value="0">
		<param name="FileOpen" value="0">
		<param name="FileClose" value="0">
		<param name="FileSave" value="0">
		<param name="FileSaveAs" value="-1">
		<param name="FilePrint" value="0">
		<param name="FilePrintPreview" value="0">
		<param name="FilePageSetup" value="0">
		<param name="FileProperties" value="0">
		<param name="ToolBars" value="0">
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
function showMain(){
// 	$('#officecontrol').attr('style','display:block;height:800px;');
	var realPath = $('#realPath').val();
	if(realPath){
		TANGER_OCX_openFromUrl('<%=rootPath%>/upload/template/fwcpdTH.docx',false);
		taoHong();
	}
}

function taoHong(){
// 	OFFICE_CONTROL_OBJ.SetReadOnly(false);
	OFFICE_CONTROL_OBJ.SetBookmarkValue("单位","${tOSendBillPage.sendUnit }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("文种","<t:convert codeType='1' code='GWZL' value='${tOSendBillPage.sendTypeCode}'></t:convert>");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("前缀","${tOSendBillPage.fileNumPrefix }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("年度","${tOSendBillPage.sendYear }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("文号","${tOSendBillPage.sendNum }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("密级","<t:convert codeType="0" code="XMMJ" value="${tOSendBillPage.secrityGrade}"></t:convert>");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("印数","${tOSendBillPage.printNum }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("公文标题","${tOSendBillPage.sendTitle }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("拟稿说明","${tOSendBillPage.draftExplain }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("年","<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='yyyy'/>");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("月","<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='MM'/>");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("日","<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='dd'/>");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("承办单位","${tOSendBillPage.undertakeUnitName }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("联系人","${tOSendBillPage.contactName }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("电话","${tOSendBillPage.contactPhone }");
	
// 		OFFICE_CONTROL_OBJ.Activedocument.Application.Selection.Goto(-1,0,0,"校首长阅批");
	checkFile('/signature/${tOSendBillPage.id }_sendBillPresident.jpg',"校首长批示",false,100);
// 		OFFICE_CONTROL_OBJ.Activedocument.Application.Selection.Goto(-1,0,0,"机关部领导阅批");
	checkFile('/signature/${tOSendBillPage.id }_sendBillDepartmentLeader.jpg',"机关部领导批示",false,100);
	checkFile('/signature/${tOSendBillPage.id }_sendBillNuclearDraftUsername.jpg',"核稿人",true,50);
  var realPath = $('#realPath').val();
  OFFICE_CONTROL_OBJ.ActiveDocument.Application.Selection.EndKey(6);

  OFFICE_CONTROL_OBJ.AddTemplateFromURL('<%=rootPath%>/'+realPath);
  
	OFFICE_CONTROL_OBJ.SetReadOnly(true);
}

function saveAs(){
	OFFICE_CONTROL_OBJ.FileSaveAs = true;
	OFFICE_CONTROL_OBJ.ActiveDocument.Application.Dialogs(84).Show();//调用word另存为功能
}

function checkFile(relativePath,bookmark,fly,size){
	var flag = false;
	$.ajax({
		url:'tOReceiveBillController.do?ifFileExists&relativePath='+relativePath,
		type : 'POST',
		timeout : 3000,
		dataType : 'json',
		success:function(data){
			flag = data.obj;
			if(flag){
				OFFICE_CONTROL_OBJ.SetReadOnly(false);
				OFFICE_CONTROL_OBJ.Activedocument.Application.Selection.Goto(-1,0,0,bookmark);
				OFFICE_CONTROL_OBJ.AddPicFromURL('<%=rootPath%>/'+relativePath,fly,0,0,1,size,1);
				OFFICE_CONTROL_OBJ.SetReadOnly(true);
			}
		}
	});
	return flag;
}
</script>
</div>
</body>
</html>