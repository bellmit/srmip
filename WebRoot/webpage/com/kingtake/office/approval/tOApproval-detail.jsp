<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant" %>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
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
 <head>
  <title>呈批件信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <link href="webpage/com/kingtake/office/approval/approvalForm.css" rel="stylesheet">
  <script type="text/javascript">
  //编写自定义JS代码
  function formCheck(){
	  var form = $("#formobj").Validform();
      var obj = form.check(false);
      return obj;
  }
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
		 	add("传阅","tOApprovalController.do?goCirculate&id="+rid+"&ifcirculate=1&suggestionType="+type,"",520,220);
		}else{
			alert("请先填写意见！");
		}
	 }
	 
	 function pass(){
			$("#suggestionFlag").val("1");//签阅
		}
		
		function goback(){
			$("#suggestionFlag").val("0");//退回
		}
		
		function checkReview(){
		    if (formobj.president.Modify || formobj.departmentLeader.Modify) {
		          return true;
		      } else {
		          return false;
		     }
		  }
		
		 function doSubmit(){
			 $("#btn_sub").click();
		 }
		 
		 <!--------------------------------------签章相关方法------------------------------->
		    //初始化时，默认加载签章
		    $(function() {
		        var id = $("#id").val();
		        if (id != "") {
		             formobj.president.LoadSignature();
		             formobj.departmentLeader.LoadSignature();
		        }
		    });

		    function load() {
		        formobj.president.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
		        formobj.departmentLeader.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
		    }
  </script>
 </head>
 <body onload="load()">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password"  callback="@Override uploadFile" layout="table" action="tOApprovalController.do?doOperate" tiptype="1">
  					<input type="button" id="subbtn" name="subbtn" style="display: none;" onclick="doSubmit()">
  					<input type="button" id="closebtn" name="subbtn" style="display: none;" onclick="close()">
  					<input id="suggestionFlag" name="suggestionFlag" type="hidden">
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
					<input id="realPath" name="realPath" type="hidden" value="${file.realpath}">
					
					<input id="id" name="id" type="hidden" value="${tOApprovalPage.id }">
<%-- 					<input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"> --%>
					<input id="contactId" name="contactId" type="hidden" value="${tOApprovalPage.contactId }">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="200px"><p class="STYLE1">校首长批示：</p>
    <div style="float: right;display: none;">
                                       <a style="cursor:pointer;" onClick="openSignature('1')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口"></a><br />
                                       <a style="cursor:pointer;" onClick="delUserSignature('1')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注"></a>
                                       </div>
                                        <OBJECT name="president" classid="<%=clsId %>" codebase="<%=codeBase %>" width="100%" height="200">
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOApprovalPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="approvalBillPresident">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
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
    <td align="right" valign="top"><t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOApprovalPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;font-weight: bold;font-size: 14px;','disabled':'disabled'}"></t:codeTypeSelect></td>
  </tr>
  <tr>
    <td height="200px"><p class="STYLE1">部(院)领导批示:</p>
                                        <OBJECT name="departmentLeader" classid="<%=clsId %>" codebase="<%=codeBase %>" width="100%" height="200">
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOApprovalPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="approvalDepartmentLeader">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
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
    <td>&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="10" cellpadding="0">
  <tr>
    <td colspan="3" align="center" class="STYLE2">呈　批　件</td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.fileNumPrefix}'  readonly="readonly">
				﹝20<input id="approvalYear" name="approvalYear" datatype="*1-2" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.approvalYear}' readonly="readonly">﹞
				<input id="applicationFileno" name="applicationFileno" datatype="*1-20" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.applicationFileno}' readonly="readonly">号</td>
  </tr>
  <tr>
    <td><span class="STYLE1">承办单位：</span><%-- <t:departComboTree id="bb" name="bb" idInput="undertakeUnitId" nameInput="undertakeUnitName" width="155" lazy="false" value="${tOApprovalPage.undertakeUnitId }"></t:departComboTree> --%><input id="undertakeUnitName" name="undertakeUnitName" type="type" style="width: 120px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.undertakeUnitName}' readonly="readonly"><input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"></td>
    <td><span class="STYLE1">联系人：</span><input id="contactName" name="contactName" type="text" style="width: 100px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.contactName}' readonly="readonly"></td>
    <td><span class="STYLE1">电话：</span><input id="contactPhone" name="contactPhone" type="text" style="width: 100px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.contactPhone}' readonly="readonly"></td>
  </tr>
  <tr>
    <td height="3" colspan="3" bgcolor="#339966"></td>
  </tr>
</table>
<table style="width: 100%">
  <tr>
    <td>正文：</td>
    <td title="正文格式：${file.extend} 创建人：${file.createName} 创建时间：<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">(${file.extend}格式)&nbsp;${file.createName}&nbsp;<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>&nbsp;<a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td>
  </tr>
</table>
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
// 	window.scrollTo(0,700); 
}
</script>
</div>
<table width="100%" border="0" cellspacing="10" cellpadding="0">
<!--   <tr> -->
<%--     <td align="center"><input placeholder="请输入标题" id="title" name="title" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;" class="STYLE3" value='${tOApprovalPage.title}'  readonly="readonly"></td> --%>
<!--   </tr> -->
<!--   <tr> -->
<%--     <td><p><input id="receiveUnitid" name="receiveUnitid" type="hidden" value="${tOApprovalPage.receiveUnitid }"> --%>
<%--     <input placeholder="请输入接收单位(双击选择)" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;" id="receiveUnitname" name="receiveUnitname" value='${tOApprovalPage.receiveUnitname}'  readonly="readonly">： <br> --%>
<!--        </p> -->
<%--       <p><textarea id="applicationContent" name="applicationContent" rows="7" style="width: 750px;" placeholder="请输入内容"  readonly="readonly">${tOApprovalPage.applicationContent}</textarea></p> --%>
<%--     <p align="right"><input id="signUnitid" name="signUnitid" type="hidden" value="${tOApprovalPage.signUnitid }"> --%>
<%--     <input placeholder="请输入落款单位(双击选择)" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;width:142px;" id="signUnitname" name="signUnitname" value='${tOApprovalPage.signUnitname}'  readonly="readonly"></p> --%>
<!--     <p align="right"><input id="createDate" name="createDate" type="text" style="width: 140px"  -->
<%-- 					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOApprovalPage.createDate}' type='date' pattern='yyyy-MM-dd'/>" disabled="disabled"></td> --%>
<!--   </tr> -->
  <tr>
  <td><span class="STYLE1">附件</span>
  <input type="hidden" value="${tOApprovalPage.id }" id="bid" name="bid" />
  <table>
        <c:forEach items="${tOApprovalPage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOApprovalPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
<%--             <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td> --%>
          </tr>
        </c:forEach>
      </table></td>
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
<!--  <div class="form" id="filediv"></div> -->
<%--       <t:upload  queueID="filediv" name="fiels" id="file_upload"   extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOApproval"> --%>
<%--   </t:upload> --%>
  </div>
 </td>
</tr>
</table>
<c:if test="${tOApprovalPage.createBy eq userName}">
	<div style="width: auto; height: 200px;">
	<div style="width: 100%; height: 1px;">
		<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
			<t:tab href="tOApprovalController.do?getStepList&id=${tOApprovalPage.id}" icon="icon-search" title="流转步骤" id="tOReceiveBillStep"></t:tab>
			<c:forEach items="${list}" var="s">
				<t:tab iframe="${s.controller}.do?goBusinessForApproval&approvalId=${tOApprovalPage.id}" icon="icon-search" title = "${s.title}" id="d"></t:tab>
			</c:forEach>
		</t:tabs>
	</div>
	</div>
</c:if>
		</t:formvalid>
 </body>
  <script type="text/javascript"  src = "webpage/com/kingtake/office/approval/tOApproval.js"></script>		