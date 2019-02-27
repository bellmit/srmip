<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant" %>
<%
String mServerUrl="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/webpage/com/kingtake/office/signature/iWebServer.jsp";
TSUser user = ResourceUtil.getSessionUserName();
String clsId = ResourceUtil.getConfigByName("SIGNATURE.clsid");
String codeBase = ResourceUtil.getConfigByName("SIGNATURE.codebase");
String userName = "";
if(user!=null){
    userName = user.getUserName();
    request.setAttribute("userName", userName);
}
String realPath = "http://"+request.getServerName()+":"+request.getServerPort()+"/signature/";
%>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link href="webpage/com/kingtake/office/sendbill/sendBillForm.css" rel="stylesheet">
  <script type="text/javascript">
  
  //编写自定义JS代码
  function formCheck(){
	  var form = $("#formobj").Validform();
      var obj = form.check(false);
      return obj;
  }
  
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
	
	 function circulate(rid,type){
		 pass();
		 var flag=checkReview();
		 if(flag){
		 	add("传阅","tOSendBillController.do?goCirculate&id="+rid+"&ifcirculate=1&suggestionType="+type,"",520,220);
		}else{
			alert("请先填写意见！");
		}
	 }
	 
	 function checkReview(){
		  if($.trim($('#leaderReview').val())==''&&$.trim($('#officeReview').val())==''){
		  	return false;
		  }else{
			  return true;
		  }
	  }
	 
	 function pass(){
			$("#suggestionFlag").val("1");//签阅
		}
		
		function goback(){
			$("#suggestionFlag").val("0");//退回
		}
		
		 function doSubmit(){
			 $("#btn_sub").click();
		 }
//---------------------------电子签章相关方法---------------------------------------		 
		 //初始化时，默认加载签章
         $(function() {
             var id = $("#id").val();
             if (id != "") {
                    formobj.president.LoadSignature();
                    formobj.departmentLeader.LoadSignature();
                    formobj.nuclearDraftUser.LoadSignature();
             }
         });
         
         function load() {
             formobj.president.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
             formobj.departmentLeader.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
             formobj.nuclearDraftUser.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
         }
  </script>
 </head>
 <body onload="load()">
  <t:formvalid formid="formobj" dialog="true" callback="@Override uploadFile"  usePlugin="password" layout="table" action="tOSendBillController.do?doOperate" tiptype="1" beforeSubmit="SaveSignature">
					<input type="button" id="subbtn" name="subbtn" style="display: none;" onclick="doSubmit()">
  					<input type="button" id="closebtn" name="subbtn" style="display: none;" onclick="close()">
  					<input id="suggestionFlag" name="suggestionFlag" type="hidden">
					<input id="id" name="id" type="hidden" value="${tOSendBillPage.id }">
					<input id="nuclearDraftUserid" name="nuclearDraftUserid" type="hidden" value="${tOSendBillPage.nuclearDraftUserid }">
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
					<input id="realPath" name="realPath" type="hidden" value="${file.realpath}">
          <c:if test="${flowShow eq '1' }">
					<div align="center" style="font-size: 24px;color: #FF0000;height: 50px;">海军工程大学发文呈批单</div>
					<div align="center" style="color: #FF0000;"><input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.fileNumPrefix}' readonly="readonly">
				﹝20<input id="sendYear" name="sendYear" datatype="*1-2" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendYear}' readonly="readonly">﹞
				<input id="sendNum" name="sendNum" datatype="*1-20" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendNum}' readonly="readonly">号</div>
<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
  <tr>
    <td width="90" align="center" class="title2">单位(文种)</td>
    <td class="title3" width="280" nowrap="nowrap">
    <input id="sendUnit" name="sendUnit" type="text" style="width: 155px" class="inputxt" value='${tOSendBillPage.sendUnit}' readonly="readonly">(<t:convert codeType="1" code="GWZL" value="${tOSendBillPage.sendTypeCode}"></t:convert>)
    <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }">
    <input id="sendTypeName" name="sendTypeName" type="hidden" style="width: 50px" class="inputxt" value='${tOSendBillPage.sendTypeName}'>
    <input id="sendUnitId" name="sendUnitId" type="hidden" value="${tOSendBillPage.sendUnitId }">
    </td>
    <td width="90" align="center" class="title2">密级</td>
    <td class="title3">
    <t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOSendBillPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;','disabled':'disabled'}"></t:codeTypeSelect>
    <td width="90" align="center" class="title2">印数</td>
    <td class="title3"><input  id="printNum" name="printNum" datatype="n1-4" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.printNum}' readonly="readonly"></td>
  </tr>
  <tr>
    <td align="center" class="title2">公文<br>标题</td>
    <td colspan="5" class="title3"><input id="sendTitle" name="sendTitle" type="text" style="border-style: none none solid none;width: 620px" class="inputxt"  value='${tOSendBillPage.sendTitle}' readonly="readonly"></td>
  </tr>
  <tr>
    <td align="center" class="title2">
      <p>校<br>
      首<br>
      长<br>
      批<br>
      示</p>
    </td>
    <td height="200px" colspan="9" class="title3">
          <div style="text-align: right;vertical-align: top;">
                                       </div>
                                        <OBJECT name="president" classid="<%=clsId %>" codebase="<%=codeBase %>" width=100% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOSendBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="sendBillPresident">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
                                          <param name="UserName" value="<%=userName%>">    <!-- UserName:签名用户名称 -->
                                          <param name="Enabled" value="0">  <!-- Enabled:是否允许修改，0:不允许 1:允许  默认值:1  -->
                                          <param name="PenColor" value="FF0066">     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
                                          <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
                                          <param name="EditType" value="1">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
                                          <param name="ShowPage" value="0">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
                                          <param name="InputText" value="">    <!-- InputText:设置署名信息，  为空字符串则默认信息[用户名+时间]内容  -->
                                          <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
                                          <param name="FontSize" value="11">    <!-- FontSize:文字大小，默认值:11 -->
                                          <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
                                          <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n请速办理">    <!-- InputList:设置文字批注信息列表  -->
                                        </OBJECT>
        </td>
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
    <td height="200px" colspan="9" class="title3">
          <div style="text-align: right;vertical-align: top;">
                                       </div>
                                        <OBJECT name="departmentLeader" classid="<%=clsId %>" codebase="<%=codeBase %>" width=100% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOSendBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="sendBillDepartmentLeader">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
                                          <param name="UserName" value="<%=userName%>">    <!-- UserName:签名用户名称 -->
                                          <param name="Enabled" value="0">  <!-- Enabled:是否允许修改，0:不允许 1:允许  默认值:1  -->
                                          <param name="PenColor" value="FF0066">     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
                                          <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
                                          <param name="EditType" value="1">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
                                          <param name="ShowPage" value="0">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
                                          <param name="InputText" value="">    <!-- InputText:设置署名信息，  为空字符串则默认信息[用户名+时间]内容  -->
                                          <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
                                          <param name="FontSize" value="11">    <!-- FontSize:文字大小，默认值:11 -->
                                          <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
                                          <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n请速办理">    <!-- InputList:设置文字批注信息列表  -->
                                        </OBJECT>
                                      </td>
  </tr>
  <tr>
    <td colspan="6" valign="top" class="title2"><p>拟稿说明</p>
      <p><textarea id="draftExplain" name="draftExplain" rows="7" style="width: 95%" class="inputxt" readonly="readonly">${tOSendBillPage.draftExplain}</textarea></p>
    <p align="right"><input id="draftDate" name="draftDate" type="text" style="width: 150px"  disabled="disabled"
					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='yyyy-MM-dd'/>"></p></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td>承办单位：<input id="undertakeUnitName" name="undertakeUnitName" type="text" style="border-style: none none solid none;width: 155px" class="inputxt" value='${tOSendBillPage.undertakeUnitName}' readonly="readonly">
    <input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOSendBillPage.undertakeUnitId }">
    </td>
    <td style="width:10%;"><span>核稿人：</span></td>
    <td style="width:15%;height:50px;">
    <OBJECT name="nuclearDraftUser" classid="<%=clsId %>" codebase="<%=codeBase %>" width="100%" height="100%">
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOSendBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="sendBillNuclearDraftUsername">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
                                          <param name="UserName" value="<%=userName%>">    <!-- UserName:签名用户名称 -->
                                          <param name="Enabled" value="0">  <!-- Enabled:是否允许修改，0:不允许 1:允许  默认值:1  -->
                                          <param name="PenColor" value="FF0066">     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
                                          <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
                                          <param name="EditType" value="1">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
                                          <param name="ShowPage" value="0">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
                                          <param name="InputText" value="">    <!-- InputText:设置署名信息，  为空字符串则默认信息[用户名+时间]内容  -->
                                          <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
                                          <param name="FontSize" value="11">    <!-- FontSize:文字大小，默认值:11 -->
                                          <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
                                          <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n请速办理">    <!-- InputList:设置文字批注信息列表  -->
                                        </OBJECT>
                                        </td>
    <td>联系人：<input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.contactName}' readonly="readonly"></td>
    <td>电话：<input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.contactPhone}' readonly="readonly">
    </td>
  </tr>
</table>
</c:if>
<c:if test="${contentShow eq '1' }">
<table style="width: 100%">
  <tr>
    <td>正文：</td>
    <td title="正文格式：${file.extend} 创建人：${file.createName} 创建时间：<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">(${file.extend}格式)&nbsp;${file.createName}&nbsp;<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;<a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" data-options="plain:true" href="#officecontrol" icon="icon-add">查看正文</a></td>
  </tr>
</table>
</c:if>
<c:if test="${attachementShow eq '1' }">
<table style="width:100%;">
<tr>
				<td><p>&nbsp;</p>
      				<p>附件</p>
    				<p>&nbsp;</p></td>
     			<td>
     			<input type="hidden" value="${tOSendBillPage.id }" id="bid" name="bid" />
     <table>
        <c:forEach items="${tOSendBillPage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOSendBillPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
          </tr>
        </c:forEach>
      </table>
      </td>
    </tr>
</table>
</c:if>
<div id="officecontrol"  style="width: 1px;height: 1px;">
		<object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="1px" height="1px">
        <param name="IsUseUTF8URL" value="-1">
        <param name="IsUseUTF8Data" value="-1">
        <param name="BorderStyle" value="1">
		<param name="BorderColor" value="14402205">
        <param name="TitlebarColor" value="15658734">
		<param name="TitlebarTextColor" value="0">
		<param name="Menubar" value="0">
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
</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/sendbill/tOSendBill.js"></script>		