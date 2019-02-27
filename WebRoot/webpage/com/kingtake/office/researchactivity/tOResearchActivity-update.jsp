<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@include file="/context/officeControl.jsp"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String userName = user.getRealName();
%>
<!DOCTYPE html>
<html>
<head>
<title>科研动态</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
     $(function(){
         var contentFileId = $("#contentFileId").val();
         if(contentFileId!=""){
             addFileContent();
         }
     });
     
     function ntkoSet(){
         OFFICE_CONTROL_OBJ.TrackRevisions(true);//设置痕迹保留
     }
     
     function uploadFile(data){
     	$("#bid").val(data.obj.id);
     	if($(".uploadify-queue-item").length>0){
     		upload();
     	}else{
     	    var win = frameElement.api.opener;
     		win.tip(data.msg);
     		if(data.success){
     		 win.reloadTable();
     		 frameElement.api.close();
     		}
     	}
     }
     
     function uploadCallback(){
        var win = frameElement.api.opener;
  		win.tip("保存成功！");
  		win.reloadTable();
  	    frameElement.api.close();
     }
</script>
</head>
<body onload="ntkoSet()">
    <input id="realPath" name="realPath" type="hidden" value="${file.realpath}">
    <input id="userName" type="hidden" value="<%=userName%>">
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOResearchActivityController.do?doUpdate" tiptype="1" beforeSubmit="saveToUrl()" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tOResearchActivityPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tOResearchActivityPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tOResearchActivityPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tOResearchActivityPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tOResearchActivityPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tOResearchActivityPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tOResearchActivityPage.updateDate }">
    <input id="contentFileId" name="contentFileId" type="hidden" value="${tOResearchActivityPage.contentFileId}">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="width: 100px;"><label class="Validform_label">
            标题: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="title" name="title" type="text" value='${tOResearchActivityPage.title}' datatype="*" style="width: 800px;"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">标题</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            时间: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="beginTime" name="beginTime" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" dataType="date"
            value='<fmt:formatDate value='${tOResearchActivityPage.beginTime}' type="date" pattern="yyyy-MM-dd"/>'>~<input id="endTime" name="endTime" type="text" style="width: 180px"
            class="Wdate" onClick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" dataType="date"
            value='<fmt:formatDate value='${tOResearchActivityPage.endTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            单位: <font color="red">*</font>
          </label></td>
        <td class="value"><t:departComboTree id="dept" nameInput="deptName" idInput="deptId" lazy="false" value="${tOResearchActivityPage.deptId}" width="185"></t:departComboTree> <input
            id="deptId" name="deptId" type="hidden" class="inputxt" value='${tOResearchActivityPage.deptId}' dataType="*"> <input id="deptName" name="deptName" type="hidden"
            value='${tOResearchActivityPage.deptName}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">单位</label></td>
      </tr>
      <tr>
              <td align="right">
                <label class="Validform_label"> 附件:&nbsp;&nbsp; </label>
              </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${tOResearchActivityPage.id }" id="bid" name="bid" />
                <table style="max-width: 360px;" id="fileViewDiv">
                  <c:forEach items="${tOResearchActivityPage.certificates}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);"
                          onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tOResearchActivityPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a>
                      </td>
                      <td style="width: 60px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload"  buttonText="添加文件" formData="bid"
                    uploader="commonController.do?saveUploadFiles&businessType=researchActivity" callback="uploadCallback" dialog="false"></t:upload>
                </div>
              </td>
            </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            正文: &nbsp;&nbsp;&nbsp;
          </label></td>
        <td class="value">
          <input type="button" id="selectHandoverBtn"  onclick="selectHandover()" value="选择交班材料">
          <input type="button" id="showBtn"  onclick="showNtko()" value="显示/隐藏文档">
          <div id="officecontrol" style="height: 400px;width:100%;">
            <object id="TANGER_OCX" classid="clsid:<%=ClsID%>" codebase="<%=basePath%>/OfficeControl/OfficeControl.cab#version=<%=VerSion%>" width="100%" height="95%">
              <param name="IsUseUTF8URL" value="-1">
              <param name="IsUseUTF8Data" value="-1">
              <param name="BorderStyle" value="1">
              <param name="BorderColor" value="14402205">
              <param name="TitlebarColor" value="15658734">
              <param name="TitlebarTextColor" value="0">
              <param name="Menubar" value="1">
              <param name="FileNew" value="0">
              <param name="FileOpen" value="1">
              <param name="FileClose" value="0">
              <param name="FileSave" value="0">
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
		var result = OFFICE_CONTROL_OBJ.SaveToURL("<%=basePath%>/tOOfficeOnlineFilesController.do?uploadOfficeonlineFiles&businesskey=researchActivity&cusPath=office&id="+$('#contentFileId').val(),"EDITFILE");
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

//选择交班材料
function selectHandover(){
    $("#officecontrol").show();
    TANGER_OCX_openFromUrl('<%=rootPath%>/upload/template/kydt.docx',false);
    OFFICE_CONTROL_OBJ.FileSave=false;
    var title="选择交班材料";
    var width = window.top.document.body.offsetWidth;
    var height= window.top.document.body.offsetHeight-100;
    var url = "tOResearchActivityController.do?goHandoverList";
    W.$.dialog({
        content: 'url:'+url,
        lock : true,
        width:width,
        height:height,
        parent:windowapi,
        title:title,
        opacity : 0.3,
        cache:false,
        ok: function(){
            iframe = this.iframe.contentWindow;
            var rows = iframe.getSelectedRows();
            if(rows.length==0){
                iframe.tip("请选择交班材料！");
                return false;
            }
            var bzgzyd="";
            var xzgzjh="";
            var zyrw="";
            var today = new Date();      
            var day = today.getDate();      
            var month = today.getMonth() + 1;      
            var year = today.getFullYear();      
            var date = year + "-" + month + "-" + day; 
            var timeStr = (new Date()).format("yyyy-MM-dd",date);
            for(var i=0;i<rows.length;i++){
                bzgzyd = bzgzyd + rows[i].handoverContent+"("+rows[i].handoverName+","+rows[i].handoverTime+","+rows[i].handoverDepartName+")\n";
                xzgzjh = xzgzjh + rows[i].nextWeekWorkContent+"("+rows[i].handoverName+","+rows[i].handoverTime+","+rows[i].handoverDepartName+")\n";
                zyrw = zyrw + rows[i].mainTask+"("+rows[i].handoverName+","+rows[i].handoverTime+","+rows[i].handoverDepartName+")\n";
            }
            OFFICE_CONTROL_OBJ.SetBookmarkValue("时间",timeStr);
            OFFICE_CONTROL_OBJ.SetBookmarkValue("本周工作内容",bzgzyd);
            OFFICE_CONTROL_OBJ.SetBookmarkValue("下周工作计划",xzgzjh);
            OFFICE_CONTROL_OBJ.SetBookmarkValue("任务情况",zyrw);
        },
        cancelVal: '关闭',
        cancel: true /*为true等价于function(){}*/
    }).zindex();
}

function showNtko(){
    $("#officecontrol").toggle();
}
     </script>
          </div>
           <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">正文</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/office/researchactivity/tOResearchActivity.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>