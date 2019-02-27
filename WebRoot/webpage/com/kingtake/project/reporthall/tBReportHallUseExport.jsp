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
	TANGER_OCX_openFromUrl('<%=basePath%>/export/template/xsbgtsysqb.docx', false);
  taoHong();
}

          function taoHong() {
            OFFICE_CONTROL_OBJ.SetBookmarkValue("编号", "${tBReportHallUsePage.reviewNumber}");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("学术报告厅", '<t:convert codeType="1" code="XSBGT" value="${tBReportHallUsePage.reportHallId}"></t:convert>');
            OFFICE_CONTROL_OBJ.SetBookmarkValue("使用单位", "${tBReportHallUsePage.useDepartName }");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("使用时间", '<fmt:formatDate value="${tBReportHallUsePage.beginUseTime}" type="date" pattern="yyyy-MM-dd"/>');
            OFFICE_CONTROL_OBJ.SetBookmarkValue("使用目的", "${tBReportHallUsePage.usePurpose }");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("联系人", "${tBReportHallUsePage.contactName }");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("联系电话", "${tBReportHallUsePage.contactPhone}");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("拟参加人员", "${tBReportHallUsePage.attendeeName }");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("需学术报告厅准备事宜", "${tBReportHallUsePage.prepareThings }");
            OFFICE_CONTROL_OBJ.SetBookmarkValue("备注", "${tBReportHallUsePage.memo }");

             var attachJSON = eval('${attachJSON}');
            var attachStr = "";
             $.each(attachJSON, function(n, value) {
               attachStr += value.attachmenttitle+"("+value.extend+")"+"\r";
            });
            OFFICE_CONTROL_OBJ.SetBookmarkValue("附件", attachStr);
            OFFICE_CONTROL_OBJ.SetReadOnly(true);
          }
        </script>
  </div>
</body>
</html>