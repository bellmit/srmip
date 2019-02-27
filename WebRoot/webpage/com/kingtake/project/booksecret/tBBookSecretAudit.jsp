<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<body onload="showMain()">
  <div id="officecontrol" style="display: block; height: 100%;">
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
function showMain(){
	TANGER_OCX_openFromUrl('<%=basePath%>/export/template/zzbmspb.docx',false);
	taoHong();
}

function taoHong(){
	OFFICE_CONTROL_OBJ.SetBookmarkValue("移动联系电话","${tBBookSecretPage.mobileTelephone }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("编号","${tBBookSecretPage.reviewNumber}");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("著作名称","${tBBookSecretPage.bookName }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("第一作者姓名","${tBBookSecretPage.firstAuthor }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("具体单位","${tBBookSecretPage.concreteDeptName }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("单位","${tBBookSecretPage.concreteDeptName }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("密级","<t:convert codeType="0" code="XMMJ" value="${tBBookSecretPage.secretDegree}"></t:convert>");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("发行范围","${tBBookSecretPage.issueScope }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("出版社","${tBBookSecretPage.press }");
	OFFICE_CONTROL_OBJ.SetBookmarkValue("著作内容要点","${tBBookSecretPage.bookContentKey }");
	
	OFFICE_CONTROL_OBJ.SetReadOnly(true);
}

</script>
  </div>
</body>
</html>