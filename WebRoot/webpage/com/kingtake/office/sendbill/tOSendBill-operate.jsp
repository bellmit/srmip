<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<%@page import="java.io.File"%>
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
	
	function callback(data){
	    var win = frameElement.api.opener;
	    if(data.success==true){
	        frameElement.api.close();
	        win.tip(data.msg);
	        win.refresh();
	     }else{
	            win.tip(data.msg);
	            return false;
	     }
	}
	
	 function circulate(rid,type){
		 pass();
		 var flag=checkReview();
		 if(flag){
		     var iWidth=900; //弹出窗口的宽度;
		     var iHeight=500; //弹出窗口的高度;
		     var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		     var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		     var openUrl = "tOSendBillController.do?circulateOrgExt&id="+rid+"&ifcirculate=1&suggestionType="+type;
		     window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
		}else{
			alert("请先填写意见！");
		}
	 }
	 
	 function checkReview(){
	     if (formobj.president.Modify || formobj.departmentLeader.Modify || formobj.nuclearDraftUser.Modify) {
             return true;
         } else {
             return false;
        }
     }

            function pass() {
                $("#suggestionFlag").val("1");//签阅
            }

            function goback() {
                $("#suggestionFlag").val("0");//退回
            }

            function doSubmit() {
                $("#btn_sub").click();
            }
            //---------------------------电子签章相关方法---------------------------------------		 
            //作用：SendOut控件打开签章窗口

            function openSignature(key) {
                if (key == '1') {
                    //formobj.president.ShowDate("", 2);//显示日期，2为下方
                    if (formobj.president.OpenSignature()) {
                        //alert("签章、签批成功");
                    } else {
                        alert(formobj.president.Status);
                    }
                }else if(key=='2') {
                    //formobj.departmentLeader.ShowDate("", 2);
                    if (formobj.departmentLeader.OpenSignature()) {
                        //alert("签章、签批成功");
                    } else {
                        alert(formobj.departmentLeader.Status);
                    }
                }else if(key=='3') {
                    //formobj.nuclearDraftUser.ShowDate("", 2);
                    if (formobj.nuclearDraftUser.OpenSignature()) {
                        //alert("签章、签批成功");
                    } else {
                        alert(formobj.nuclearDraftUser.Status);
                    }
                }
            }

            //作用：SendOut控件打开签章窗口
            function SendEditTypeChange() {
                if (formobj.president.EditType == 0) {
                    formobj.president.EditType = 1;
                    alert("当前为文字签批状态");
                } else {
                    formobj.president.EditType = 0;
                    alert("当前为手写签批状态");
                }
                if (formobj.departmentLeader.EditType == 0) {
                    formobj.departmentLeader.EditType = 1;
                    alert("当前为文字签批状态");
                } else {
                    formobj.departmentLeader.EditType = 0;
                    alert("当前为手写签批状态");
                }
                if (formobj.nuclearDraftUser.EditType == 0) {
                    formobj.nuclearDraftUser.EditType = 1;
                    alert("当前为文字签批状态");
                } else {
                    formobj.nuclearDraftUser.EditType = 0;
                    alert("当前为手写签批状态");
                }
            } 

            //删除当前用户的签批
            function delUserSignature(key) {
                try {
                    if (key == '1') {
                        var mResult = formobj.president.DeleteUserSignature(formobj.president.UserName);
                    } else if(key == '2') {
                        var mResult = formobj.departmentLeader.DeleteUserSignature(formobj.departmentLeader.UserName);
                    } else if(key == '3') {
                        var mResult = formobj.nuclearDraftUser.DeleteUserSignature(formobj.nuclearDraftUser.UserName);
                    }
                } catch (e) {
                    alert(e.description); //显示出错误信息
                }
            }

            function SaveSignature() {
                var id = $("#id").val();
                var parentPath = $("#parentPath").val();

                if (formobj.departmentLeader.Modify) { //判断签章数据信息是否有改动
                    if (!formobj.departmentLeader.SaveSignature()) { //保存签章数据信息
                        return false;
                    } 
                }
                if (formobj.president.Modify) { //判断签章数据信息是否有改动
                    if (!formobj.president.SaveSignature()) { //保存签章数据信息
                        return false;
                    } 
                }
                if (formobj.nuclearDraftUser.Modify) { //判断签章数据信息是否有改动
                    if (!formobj.nuclearDraftUser.SaveSignature()) { //保存签章数据信息
                        return false;
                    } 
                    recordNuclearDraft();//记录核稿人
                }
                return true;
            }
            
            //记录核稿人
            function recordNuclearDraft(){
            	var id = $('#id').val();
            	$.ajax({
            		url:'tOSendBillController.do?recordNuclearDraft&id='+id,
            		dataType : 'json',
            		success:function(data){
            			tip(data.msg);
            		}
            	})
            	
            }

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
  <t:formvalid formid="formobj" dialog="true" callback="@Override callback"  usePlugin="password" layout="table" action="tOSendBillController.do?doNothing" tiptype="1" beforeSubmit="SaveSignature">
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
                    <input id="suggestionType" name="suggestionType" type="hidden" >
					<input id="realPath" name="realPath" type="hidden" value="${file.realpath}">
					<div align="center" style="font-size: 24px;color: #FF0000;height: 50px;">海军工程大学发文呈批单</div>
					<div align="center" style="color: #FF0000;"><input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.fileNumPrefix}' readonly="readonly">
				﹝20<input id="sendYear" name="sendYear" datatype="*1-2" ignore="ignore" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendYear}' readonly="readonly">﹞
				<input id="sendNum" name="sendNum" datatype="*1-20" ignore="ignore" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOSendBillPage.sendNum}' readonly="readonly">号</div>
<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
  <tr>
    <td width="90" align="center" class="title2">单位(文种)</td>
    <td class="title3" width="280" nowrap="nowrap">
<%--     <t:departComboTree id="dd" name="dd" idInput="sendUnitId" nameInput="sendUnit" width="155" lazy="false" value="${tOSendBillPage.sendUnitId }"></t:departComboTree> --%>
    <input id="sendUnit" name="sendUnit" type="text" style="width: 155px" class="inputxt" value='${tOSendBillPage.sendUnit}' readonly="readonly">(<t:convert codeType="1" code="GWZL" value="${tOSendBillPage.sendTypeCode}"></t:convert>)
    <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }">
<%--     (<t:codeTypeSelect id="sendTypeCode" name="sendTypeCode" defaultVal="${tOSendBillPage.sendTypeCode}"  --%>
<%--                 type="select" code="GWZL" codeType="1" extendParam="{'style':'width:100px;','disabled':'disabled'}"></t:codeTypeSelect>) --%>
    <input id="sendTypeName" name="sendTypeName" type="hidden" style="width: 50px" class="inputxt" value='${tOSendBillPage.sendTypeName}'>
<%--     <input id="sendTypeCode" name="sendTypeCode" type="hidden" value="${tOSendBillPage.sendTypeCode }"> --%>
    <input id="sendUnitId" name="sendUnitId" type="hidden" value="${tOSendBillPage.sendUnitId }">
    </td>
    <td width="90" align="center" class="title2">密级</td>
    <td class="title3">
    <t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOSendBillPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;','disabled':'disabled'}"></t:codeTypeSelect>
<%--     <input id="secrityGrade" name="secrityGrade" type="text" style="width: 100px" class="inputxt" value='${tOSendBillPage.secrityGrade}'></td> --%>
    <td width="90" align="center" class="title2">印数</td>
    <td class="title3"><input  id="printNum" name="printNum" datatype="n1-4" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.printNum}' readonly="readonly"></td>
  </tr>
  <tr>
    <td align="center" class="title2">公文<br>标题</td>
    <td colspan="5" class="title3"><input id="sendTitle" name="sendTitle" type="text" style="border-style: none none solid none;width: 620px" class="inputxt"  value='${tOSendBillPage.sendTitle}' readonly="readonly"></td>
  </tr>
<!--   <tr> -->
<!-- 	<td align="center" class="title2">正文</td> -->
<%-- 	<td class="title3" colspan="5">(${file.extend}格式)&nbsp;${file.createName}&nbsp;<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;<a class="easyui-linkbutton"  onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td> --%>
<!--   </tr> -->
  <tr>
    <td align="center" class="title2">
      <p>校<br>
      首<br>
      长<br>
      批<br>
      示</p>
    </td>
         <td class="title3" height="200px" colspan="5">
          <div style="float: right;">
                                       <a style="cursor:pointer;" onClick="openSignature('1')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口">签章</a><br />
                                       <a style="cursor:pointer;" onClick="delUserSignature('1')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注">删除</a>
                                       <!-- <a class="LinkButton" style="cursor:pointer; " onClick="SaveSignature()">[保存批注]</a> -->
                                       </div>
                                        <OBJECT name="president" classid="<%=clsId %>" codebase="<%=codeBase %>" width=94% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOSendBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="sendBillPresident">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
                                          <param name="UserName" value="<%=userName%>">    <!-- UserName:签名用户名称 -->
                                          <param name="Enabled" value="1">  <!-- Enabled:是否允许修改，0:不允许 1:允许  默认值:1  -->
                                          <!-- <param name="PenColor" value="FF0066"> -->     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
                                          <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
                                          <param name="EditType" value="1">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
                                          <param name="ShowPage" value="1">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
                                          <param name="InputText" value="">    <!-- InputText:设置署名信息，  为空字符串则默认信息[用户名+时间]内容  -->
                                          <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
                                          <param name="FontSize" value="16">    <!-- FontSize:文字大小，默认值:11 -->
                                          <param name="FontName" value="华文行楷">    <!-- FontName:文字字体-->
                                          <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
                                          <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n请速办理">    <!-- InputList:设置文字批注信息列表  -->
                                        </OBJECT>
        </td>
    <%-- </td> --%>
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
    <td class="title3" height="200px" colspan="5">
          <div style="float: right;">
                                       <a style="cursor:pointer;" onClick="openSignature('2')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口">签章</a><br />
                                       <a style="cursor:pointer;" onClick="delUserSignature('2')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注">删除</a>
                                       <!-- <a class="LinkButton" style="cursor:pointer; " onClick="SaveSignature()">[保存批注]</a> -->
                                       </div>
                                        <OBJECT name="departmentLeader" classid="<%=clsId %>" codebase="<%=codeBase %>" width=94% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOSendBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="sendBillDepartmentLeader">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
                                          <param name="UserName" value="<%=userName%>">    <!-- UserName:签名用户名称 -->
                                          <param name="Enabled" value="1">  <!-- Enabled:是否允许修改，0:不允许 1:允许  默认值:1  -->
                                          <!-- <param name="PenColor" value="FF0066"> -->     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
                                          <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
                                          <param name="EditType" value="1">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
                                          <param name="ShowPage" value="1">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
                                          <param name="InputText" value="">    <!-- InputText:设置署名信息，  为空字符串则默认信息[用户名+时间]内容  -->
                                          <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
                                          <param name="FontSize" value="16">    <!-- FontSize:文字大小，默认值:11 -->
                                          <param name="FontName" value="华文行楷">    <!-- FontName:文字字体-->
                                          <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
                                          <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n请速办理">    <!-- InputList:设置文字批注信息列表  -->
                                        </OBJECT>
                                      </td>
  </tr>
  <tr>
    <td colspan="6" valign="top" class="title2"><p>拟稿说明</p>
      <p><textarea id="draftExplain" name="draftExplain" rows="4" style="width:95%;" class="inputxt" readonly="readonly">${tOSendBillPage.draftExplain}</textarea></p>
    <p align="right"><input id="draftDate" name="draftDate" type="text" style="width: 150px"  disabled="disabled"
					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOSendBillPage.draftDate}' type='date' pattern='yyyy-MM-dd'/>"></p></td>
  </tr>
  
				
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="10">
  <tr>
    <td>承办单位：<input id="undertakeUnitName" name="undertakeUnitName" type="text" style="border-style: none none solid none;width: 155px" class="inputxt" value='${tOSendBillPage.undertakeUnitName}' readonly="readonly">
<%--     <t:departComboTree id="bb" name="bb" idInput="undertakeUnitId" nameInput="undertakeUnitName" width="155" lazy="false" value="${tOSendBillPage.undertakeUnitId }" ></t:departComboTree> --%>
    <input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOSendBillPage.undertakeUnitId }">
    </td>
    <td style="width:12%;"><span>核稿人：<a href="#" class="easyui-linkbutton" onClick="openSignature('3')" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口"></a><a style="cursor:pointer;" onClick="delUserSignature('3')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注"></a></span></td>
    <td style="width:15%;height:50px;">
    <OBJECT name="nuclearDraftUser" classid="<%=clsId %>" codebase="<%=codeBase %>" width="100%" height="100%">
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOSendBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="sendBillNuclearDraftUsername">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
                                          <param name="UserName" value="<%=userName%>">    <!-- UserName:签名用户名称 -->
                                          <param name="Enabled" value="1">  <!-- Enabled:是否允许修改，0:不允许 1:允许  默认值:1  -->
                                          <!-- <param name="PenColor" value="FF0066"> -->     <!-- PenColor:笔的颜色，采用网页色彩值  默认值:#000000  -->
                                          <param name="BorderStyle" value="0">    <!-- BorderStyle:边框，0:无边框 1:有边框  默认值:1  -->
                                          <param name="EditType" value="1">    <!-- EditType:默认签章类型，0:签名 1:文字  默认值:0  -->
                                          <param name="ShowPage" value="1">    <!-- ShowPage:设置默认显示页面，0:电子印章,1:网页签批,2:文字批注  默认值:0  -->
                                          <param name="InputText" value="">    <!-- InputText:设置署名信息，  为空字符串则默认信息[用户名+时间]内容  -->
                                          <param name="PenWidth" value="2">     <!-- PenWidth:笔的宽度，值:1 2 3 4 5   默认值:2  -->
                                          <param name="FontSize" value="16">    <!-- FontSize:文字大小，默认值:11 -->
                                          <param name="FontName" value="华文行楷">    <!-- FontName:文字字体-->
                                          <param name="SignatureType" value="0">    <!-- SignatureType:签章来源类型，0表示从服务器数据库中读取签章，1表示从硬件密钥盘中读取签章，2表示从本地读取签章，并与ImageName(本地签章路径)属性相结合使用  默认值:0 -->
                                          <param name="InputList" value="同意\r\n不同意\r\n请上级批示\r\n请速办理">    <!-- InputList:设置文字批注信息列表  -->
                                        </OBJECT>
                                        </td>
    <td>联系人：<input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.contactName}' readonly="readonly"></td>
    <td>电话：<input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOSendBillPage.contactPhone}' readonly="readonly">
    </td>
  </tr>
</table>
<table style="width: 100%">
  <tr>
    <td>正文：</td>
    <td title="正文格式：${file.extend} 创建人：${file.createName} 创建时间：<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">(${file.extend}格式)&nbsp;${file.createName}&nbsp;<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>&nbsp;<a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td>
  </tr>
</table>

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
<%--             <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td> --%>
          </tr>
        </c:forEach>
      </table>
      </td>
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
<div style="width: auto; height: 200px;">
			<div style="width: 100%; height: 1px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tOSendBillController.do?getStepList&id=${tOSendBillPage.id}"
					icon="icon-search" title="流转步骤" id="tOReceiveBillStep" ></t:tab>
			</t:tabs>
			</div>
		</div>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/sendbill/tOSendBill.js"></script>		