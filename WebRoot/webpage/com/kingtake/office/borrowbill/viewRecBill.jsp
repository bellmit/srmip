<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.ftd{
}
.txt{
font-size: 20px;
color: red;
}
#contentTab{
margin: 0 auto;
width: 600px;
}

</style>
<title></title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script type="text/javascript">
  function showMain(realPath){
    	$('#officecontrol').attr('style','display:block;height:800px;');
    	OFFICE_CONTROL_OBJ=document.getElementById("TANGER_OCX");
    	OFFICE_CONTROL_OBJ.width="100%";
    	OFFICE_CONTROL_OBJ.height="95%";
    		TANGER_OCX_openFromUrl('<%=rootPath%>/' + realPath, false);
            OFFICE_CONTROL_OBJ.SetReadOnly(true);
    }
</script>
</head>
<body>
  <table id="contentTab" style="">
  <thead><tr><td>收文标题</td><td>操作</td></tr></thead>
  <c:forEach items="${recList}" var="rec">
    <tr>
      <td><span class="txt">${rec.title}：</span></td>
      <td class="ftd"><a href="#" onclick="showMain('${rec.path}')"><font color="red" style="text-decoration: underline;font-size: 15px;">点击查看正文</font></a></td>
    </tr>
  </c:forEach>
  </table>
  <div id="officecontrol" style="width: 1px;height: 1px;">
    <object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="1px" height="1px">
      <param name="IsUseUTF8URL" value="-1">
      <param name="IsUseUTF8Data" value="-1">
      <param name="BorderStyle" value="1">
      <param name="Menubar" value="0">
      <param name="ToolBars" value="0">
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
      <SPAN STYLE="color: red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
    </object>
    <script src="webpage/com/kingtake/officeonline/officeControl.js"></script>
    <script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
  setFileOpenedOrClosed(false);
                </script>
    <script language="JScript" for=TANGER_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
  setFileOpenedOrClosed(true);
                </script>
  </div>
</body>
<script src="webpage/com/kingtake/office/sendReceive/tOSendReceiveReg.js"></script>