<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/context/officeControl.jsp"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant" %>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
 <head>
  <title>收文阅批单信息表</title>
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
  <link href="webpage/com/kingtake/office/receivebill/receiveBillForm.css" rel="stylesheet">
 <script type="text/javascript">
  $(document).ready(function(){
	$('#tt').tabs({
	   onSelect:function(title){
	       $('#tt .panel-body').css('width','auto');
		}
	});
	$(".tabs-wrap").css('width','100%');
  });
  
  //检查是否签批
  function checkReview(){
	  if (formobj.president.Modify || formobj.departmentLeader.Modify || formobj.dutyOption.Modify) {
          return true;
      } else {
          return false;
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
	
	function pass(){
		$("#suggestionFlag").val("1");//签阅
	}
	
	function goback(){
		$("#suggestionFlag").val("0");//退回
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
	  
	<!--------------------------------------签章相关方法------------------------------->
	//作用：控件打开签章窗口
    function openSignature(key) {
        if (key == '1') {
            //formobj.president.ShowDate("", 2);
            if (formobj.president.OpenSignature()) {
            } else {
                alert(formobj.president.Status);
            }
        } else if(key=='2') {
            //formobj.departmentLeader.ShowDate("", 2);
            if (formobj.departmentLeader.OpenSignature()) {
                //alert("签章、签批成功");
            } else {
                alert(formobj.departmentLeader.Status);
            }
        }else if(key=='3'){
            //formobj.dutyOption.ShowDate("", 2);
            if (formobj.dutyOption.OpenSignature()) {
                //alert("签章、签批成功");
            } else {
                alert(formobj.dutyOption.Status);
            }
        }
    }

    //作用：控件打开签章窗口
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
        if (formobj.dutyOption.EditType == 0) {
            formobj.dutyOption.EditType = 1;
            alert("当前为文字签批状态");
        } else {
            formobj.dutyOption.EditType = 0;
            alert("当前为手写签批状态");
        }
    }

    //删除当前用户的签批
    function delUserSignature(key) {
        try {
            if (key == '1') {
                var mResult = formobj.president.DeleteUserSignature(formobj.president.UserName);
            } else if(key=='2') {
                var mResult = formobj.departmentLeader.DeleteUserSignature(formobj.departmentLeader.UserName);
            }else if(key=='3'){
                var mResult = formobj.dutyOption.DeleteUserSignature(formobj.dutyOption.UserName);
            }
        } catch (e) {
            alert(e.description); //显示出错误信息
        }
    }

    function SaveSignature() {
        if (formobj.dutyOption.Modify) { //判断签章数据信息是否有改动
            if (!formobj.dutyOption.SaveSignature()) { //保存签章数据信息
                alert("保存签批内容失败！");
                return false;
            } 
        }
        if (formobj.departmentLeader.Modify) { //判断签章数据信息是否有改动
            if (!formobj.departmentLeader.SaveSignature()) { //保存签章数据信息
                alert("保存签批内容失败！");
                return false;
            } 
        }
        if (formobj.president.Modify) { //判断签章数据信息是否有改动
            if (!formobj.president.SaveSignature()) { //保存签章数据信息
                alert("保存签批内容失败！");
                return false;
            } 
        }
        return true;
    }

    //初始化时，默认加载签章
    $(function() {
        var id = $("#id").val();
        if (id != "") {
             formobj.president.LoadSignature();
             formobj.departmentLeader.LoadSignature();
             formobj.dutyOption.LoadSignature();
        }
    });

    function load() {
        formobj.president.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
        formobj.departmentLeader.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
        formobj.dutyOption.InputList = "同意\r\n不同意\r\n请上级批示\r\n请速办理";
    }
    
    
 </script>
  
 </head>
 <body onload="load()">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  action="tOReceiveBillController.do?doNothing" callback="@Override callback" tiptype="1" beforeSubmit="SaveSignature">
  					<input type="button" id="subbtn" name="subbtn" style="display: none;" onclick="doSubmit()">
  					<input type="button" id="closebtn" name="subbtn" style="display: none;" onclick="close()">
  					<input id="suggestionFlag" name="suggestionFlag" type="hidden">
					<input id="registerType" name="registerType" value="${tOReceiveBillPage.registerType}" type="hidden" class="inputxt">
					<input id="id" name="id" type="hidden" value="${tOReceiveBillPage.id }">
<%-- 					<input id="receiveUnitId" name="receiveUnitId" type="hidden" value="${tOReceiveBillPage.receiveUnitId }"> --%>
					<input id="contactId" name="contactId" type="hidden" value="${tOReceiveBillPage.contactId }">
					<input id="registerId" name="registerId" type="hidden" value="${tOReceiveBillPage.registerId }">
					<input id="registerDepartId" name="registerDepartId" type="hidden" value="${tOReceiveBillPage.registerDepartId }">
					<input id="archiveUserid" name="archiveUserid" type="hidden" value="${tOReceiveBillPage.archiveUserid }">
					<input id="createBy" name="createBy" type="hidden" value="${tOReceiveBillPage.createBy }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tOReceiveBillPage.updateBy }">
					<input id="registerTime" name="registerTime" type="hidden" value='<fmt:formatDate value='${tOReceiveBillPage.registerTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<input id="registerName" name="registerName" type="hidden" value="${tOReceiveBillPage.registerName }">
					<input id="registerDepartName" name="registerDepartName" type="hidden" value="${tOReceiveBillPage.registerDepartName }">
					<input id="archiveFlag" name="archiveFlag" type="hidden" value="${tOReceiveBillPage.archiveFlag }">
					<input id="archiveUsername" name="archiveUsername" type="hidden" value="${tOReceiveBillPage.archiveUsername }">
					<input id="archiveDate" name="archiveDate" type="hidden" value="${tOReceiveBillPage.archiveDate }">
					<input id="createName" name="createName" type="hidden" value="${tOReceiveBillPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value='<fmt:formatDate value='${tOReceiveBillPage.createDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<input id="updateName" name="updateName" type="hidden" value="${tOReceiveBillPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tOReceiveBillPage.updateDate }">
					<input id="suggestionType" name="suggestionType" type="hidden">
					<input id="suggestionContent" name="suggestionContent" type="hidden">
					<input id="realPath" name="realPath" type="hidden" value="${file.realpath}">
  <div align="center" style="font-size: 24px;color: #FF0000;height: 60px;">海军工程大学收文阅批单</div>
<%--   <c:if test="${editFlag=='1'}"><div align="right"><input type="button" value="填写意见" onclick="inputSuggestion('${tOReceiveBillPage.id}')"></div></c:if> --%>
		<table width="100%" border="0" cellspacing="0" cellpadding="5" style='border-collapse:collapse;'>
				<tr>
					<td width="90" align="center" class="title2">
      				来文<br>
    				单位</td>
					<td class="title3">
<%-- 							<t:departComboTree id="dd" name="" idInput="receiveUnitId" nameInput="receiveUnitName" width="155" lazy="false" value="${tOReceiveBillPage.receiveUnitId }"></t:departComboTree> --%>
							 <input id="receiveUnitId" name="receiveUnitId" type="hidden" value="${tOReceiveBillPage.receiveUnitId }">
							 <input id="receiveUnitName" name="receiveUnitName" type="text" value="${tOReceiveBillPage.receiveUnitName }" readonly="readonly" style="border-style: none none solid none;width: 280px;">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">来文单位名</label>
						</td>
					<td width="90" align="center" class="title2">公文<br>
   						 编号</td>
					<td class="title3">
					     	 <input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.fileNumPrefix}'  readonly="readonly">
				﹝20<input id="fileNumYear" name="fileNumYear"  type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.fileNumYear}' readonly="readonly">﹞
				<input id="billNum" name="billNum"  type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOReceiveBillPage.billNum}' readonly="readonly">号
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">公文编号</label>
						</td>
					<td width="90" align="center" class="title2">密级</td>
					<td class="title3">
<!-- 					     	 <input id="secrityGrade" name="secrityGrade" type="text" style="width: 150px" class="inputxt"> -->
					     	 <t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOReceiveBillPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'disabled':'true'}"></t:codeTypeSelect> 
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">密级</label>
						</td>
					</tr>
				<tr>
					<td align="center" class="title2">
      					公文<br>
     					 标题</td>
					<td class="title3" colspan="5">
					     	 <input id="title" name="title" type="text" datatype="*1-200"  style="border-style: none none solid none;width: 85%;" class="inputxt" value="${tOReceiveBillPage.title }" readonly="readonly">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">标题</label>
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 				<td align="center" class="title2">正文 -->
<!-- 				</td> -->
<%-- 				<td class="title3" colspan="5">(${file.extend}格式)&nbsp;${file.createName}&nbsp;<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;<a class="easyui-linkbutton"  onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td> --%>
<!-- 			</tr> -->
				<tr>
					<td align="center" class="title2">
      <p>校<br>
      首<br>
      长<br>
      阅<br>
      批</p>
    </td>
					<td class="title3" colspan="5" height="200px">
                    <div style="float: right;">
                                       <a style="cursor:pointer;" onClick="openSignature('1')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口">签章</a><br />
                                       <a style="cursor:pointer;" onClick="delUserSignature('1')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注">删除</a>
                                       </div>
                                        <OBJECT name="president" classid="<%=clsId %>" codebase="<%=codeBase %>" width=94% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOReceiveBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="receiveBillPresident">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
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
					<td align="center" class="title2">
    <p>机<br>
      关<br>
      部<br>
      (院)<br>
      领<br>
      导<br>
      阅<br>
      批</p>
   </td>
					<td class="title3" colspan="5" height="200px">
                    <div style="float: right;">
                                       <a style="cursor:pointer;" onClick="openSignature('2')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口">签章</a><br />
                                       <a style="cursor:pointer;" onClick="delUserSignature('2')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注">删除</a>
                                       </div>
                                        <OBJECT name="departmentLeader" classid="<%=clsId %>" codebase="<%=codeBase %>" width=94% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOReceiveBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="receiveBillDepartmentLeader">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
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
					<td align="center" class="title2">
      <p>承<br>
        办<br>
        单<br>
        位<br>
        意<br>
    见</p>
    </td>
					<td class="title3" colspan="5" height="200px">
					<div style="float: right;">
                                       <a style="cursor:pointer;" onClick="openSignature('3')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" title="打开签章窗口">签章</a><br />
                                       <a style="cursor:pointer;" onClick="delUserSignature('3')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'" title="删除批注">删除</a>
                                       </div>
                                        <OBJECT name="dutyOption" classid="<%=clsId %>" codebase="<%=codeBase %>" width=94% height=100%>
                                          <param name="WebUrl" value="<%=mServerUrl%>">    <!-- WebUrl:系统服务器路径，与服务器交互操作，如打开签章信息 -->
                                          <param name="RecordID" value="${tOReceiveBillPage.id }">    <!-- RecordID:本文档记录编号 -->
                                          <param name="FieldName" value="receiveBillDutyOption">         <!-- FieldName:签章窗体可以根据实际情况再增加，只需要修改控件属性 FieldName 的值就可以 -->
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
					</table>
					<table width="100%" border="0" cellspacing="0" cellpadding="10">
					<tr>
					<td class="value"><font color="red">承办单位:</font>
<%-- 							<t:departComboTree id="cc" name="cc" idInput="dutyId" nameInput="dutyName" width="155"  value="${tOReceiveBillPage.dutyName }"></t:departComboTree> --%>
					     	<input id="dutyId" name="dutyId" type="hidden" style="width: 150px" class="inputxt" value="${tOReceiveBillPage.dutyId }">
					     	<input id="dutyName" name="dutyName" type="text" style="width: 150px" class="inputxt"  value="${tOReceiveBillPage.dutyName }" readonly="readonly">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">承办单位</label>
						</td>
					<td class="value"><font color="red">联系人:</font>
					     	 <input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 150px" class="inputxt" value="${tOReceiveBillPage.contactName }"  readonly="readonly">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人姓名</label>
						</td>
					<td class="value"><font color="red">电话:</font>
					     	 <input id="contactTel" name="contactTel" type="text"  datatype="*1-16"  style="border-style: none none solid none;width: 150px" class="inputxt" value="${tOReceiveBillPage.contactTel }" readonly="readonly">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">电话</label>
						</td>
					</tr>
					
			</table>
<table style="width: 100%">
  <tr>
    <td >正文：</td>
    <td title="正文格式：${file.extend} 创建人：${file.createName} 创建时间：<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"><font color="black">(${file.extend}格式)&nbsp;${file.createName}&nbsp;<fmt:formatDate value='${file.createDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>&nbsp;</font><a class="easyui-linkbutton" data-options="plain:true" onclick="showMain()" href="#officecontrol" icon="icon-add">查看正文</a></td>
  </tr>
</table>
<table style="width: 100%">
			<tr>
				<td><p>&nbsp;</p>
      				<p>附件</p>
    				<p>&nbsp;</p></td>
     			<td>
     			<input type="hidden" value="${tOReceiveBillPage.id }" id="bid" name="bid" />
     			<table>
        <c:forEach items="${tOReceiveBillPage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOReceiveBillPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
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
				<t:tab href="tOReceiveBillController.do?getStepList&id=${tOReceiveBillPage.id}"
					icon="icon-search" title="阅批单流转步骤" id="tOReceiveBillStep"></t:tab>
			</t:tabs>
			</div>
		</div>
		</t:formvalid>
 </body>
 
 <script type="text/javascript">
 function doSubmit(){
	 $("#btn_sub").click();
 }
 
 
 function circulate(rid,type){
	 pass();
	 var flag=checkReview();
	 if(flag){
	 	add("传阅","tOReceiveBillController.do?goCirculate&id="+rid+"&ifcirculate=1&suggestionType="+type,"",520,220);
	}else{
		alert("请先填写意见！");
	}
 }
 
 function present(rid,type){
	 pass();
	 var flag=checkReview();
	 if(flag){
	 	add("呈送","tOReceiveBillController.do?goPresent&id="+rid+"&ifcirculate=0&suggestionType="+type,"",520,220);
// 	 	$('#formobj').submit();
	}else{
		alert("请先填写意见！");
	}
 }
 
 
 function inputSuggestion(id){
	 $.dialog({
			content: 'url:tOReceiveBillController.do?goInput&id='+id,
			lock : true,
			//zIndex:1990,
			width:400,
			height:220,
			title:"意见填写",
			opacity : 0.3,
			cache:false,
	 	    ok: function(){
	 	    	iframe = this.iframe.contentWindow;
	 	    	saveObj();
	 	    	window.location.reload();
	 			return false;
	 	    },
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		}).zindex();
 }
 
  //编写自定义JS代码
  function fillContent(cid){
	  alert("${suggestionType}");
	  var temp = $("#"+cid)[0].innerHTML;
	  cont('意见填写', '请填写意见：', function(r){
			if (r){
				$("#"+cid)[0].innerHTML=temp+"\n"+r;
				$("#suggestionType").val(cid);
				$("#suggestionContent").val(r);
				$("#formobj").submit();
			}
		});
  }
  
  function cont(_243,msg,fn){
	  var _244="<div>"+msg+"</div>"+"<br/>"+"<div style=\"clear:both;\"/>"+"<div><textarea datatype=\"s0-50\"  style=\"width: 450px;\" rows=\"5\"  class=\"messager-input\"></textarea></div>";
	  var _245={};
	  _245[$.messager.defaults.ok]=function(){
	  win.window("close");
	  if(fn){
	  fn($(".messager-input",win).val());
	  return false;
	  }
	  };
	  _245[$.messager.defaults.cancel]=function(){
	  win.window("close");
	  if(fn){
	  fn();
	  return false;
	  }
	  };
	  var win=_237(_243,_244,_245);
	  win.children("input.messager-input").focus();
	  return win;
	  }
  
  function _237(_238,_239,_23a){
	  var win=$("<div class=\"messager-body\"></div>").appendTo("body");
	  win.append(_239);
	  if(_23a){
	  var tb=$("<div class=\"messager-button\"></div>").appendTo(win);
	  for(var _23b in _23a){
	  $("<a></a>").attr("href","javascript:void(0)").text(_23b).css("margin-left",10).bind("click",eval(_23a[_23b])).appendTo(tb).linkbutton();
	  }
	  }
	  win.window({title:_238,noheader:(_238?false:true),width:500,height:"auto",modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,onClose:function(){
	  setTimeout(function(){
	  win.window("destroy");
	  },100);
	  }});
	  win.window("window").addClass("messager-window");
	  win.children("div.messager-button").children("a:first").focus();
	  return win;
	  };

  
  
  </script>
  <script src = "webpage/com/kingtake/office/receivebill/tOReceiveBill.js"></script>		