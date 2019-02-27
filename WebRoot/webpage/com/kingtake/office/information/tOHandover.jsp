<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>交班材料信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
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
    
    $(function() {
      //查看模式情况下,删除和上传附件功能禁止使用
		if(location.href.indexOf("load=detail")!=-1){
			$("#jeecgDetail").hide();
		} 
  	});
    
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
  action="tOHandoverController.do?doAddUpdate" tiptype="1" callback="@Override uploadFile">
  	<input id="id" name="id" type="hidden" value="${tOHandoverPage.id }">
    <input id="submitFlag"  name="submitFlag" type="hidden" value="${tOHandoverPage.submitFlag }">
    <input id="submitTime"  name="submitTime" type="hidden" value="${tOHandoverPage.submitTime }">
	<table  cellpadding="0" cellspacing="1" class="formtable">
  
      <tr>
        <td align="right" width="80px"><label class="Validform_label"> 创&nbsp;建&nbsp;人&nbsp;:</label><font color="red">*</font></td>
        <td class="value" width="200px">
  	      <input id="handoverId" name="handoverId" type="hidden" value="${tOHandoverPage.handoverId }">
          <input id="handoverName" name="handoverName" type="text" style="width: 150px" readonly="true"
          	class="inputxt" value='${tOHandoverPage.handoverName}' datatype="*">
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">创建人</label>
        </td>
        <td align="right" width="80px"><label class="Validform_label"> 交班人单位:</label><font color="red">*</font></td>
        <td class="value" width="200px">
  	      <input id="handoverDepartId" name="handoverDepartId" type="hidden" value="${tOHandoverPage.handoverDepartId }">
          <input id="handoverDepartName" name="handoverDepartName" type="text" style="width: 150px" readonly="true"
          	class="inputxt" value='${tOHandoverPage.handoverDepartName}' datatype="*"> 
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">交班人单位</label>
        </td>
      </tr>
      
      <tr>
       <td align="right" width="80px"><label class="Validform_label"> 提交时间:&nbsp;&nbsp;</label></td>
        <td class="value" width="200px"><input id="handoverTime" name="handoverTime" type="text" style="width: 150px" datatype="*"
          class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
          value='<fmt:formatDate value='${tOHandoverPage.handoverTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'> 
          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">提交时间</label>
        </td>
        <%-- <td align="right" ><label class="Validform_label"> 接&nbsp;收&nbsp;人&nbsp;:</label><font color="red">*</font></td>
        <td class="value" colspan="3">
          <input id="receiver" name="receiver" type="hidden"  value='${tOHandoverPage.receiver}'>
          <input id="receiverName" name="receiverName" type="text" style="width: 150px" datatype="*" class="inputxt" readonly="readonly"
          	value='${tOHandoverPage.receiverName}'> 
          <t:chooseUser idInput="receiver" url="commonUserController.do?commonUser" mode="single"
	          tablename="selectedUserList" icon="icon-search" title="人员列表" textname="id,realName"
	          inputTextname="receiver,receiverName" isclear="true"></t:chooseUser>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">接收人</label>
        </td> --%>
      </tr>
      <tr>
      <td></td>
      <td colspan="2"><span><font color="skyblue">**注：以下输入框中，两条内容间请使用回车键</font></span></td>
      <td></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">本周工作 &nbsp;&nbsp;<br/>内 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:&nbsp;&nbsp;</label></td>
        <td class="value" colspan="3">
          <textarea id="handoverContent" style="width: 550px;" class="inputxt" rows="4" maxlength="2000"
            name="handoverContent">${tOHandoverPage.handoverContent}</textarea> 
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">工作内容</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 下周工作&nbsp;&nbsp;<br/>计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;划:&nbsp;&nbsp;</label></td>
        <td class="value" colspan="3">
          <textarea id="nextWeekWorkContent" style="width: 550px;" class="inputxt" rows="4" maxlength="2000"
            name="nextWeekWorkContent">${tOHandoverPage.nextWeekWorkContent}</textarea> 
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">下周工作计划</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 承担主要&nbsp;<br/>任务情况: </label></td>
        <td class="value" colspan="3">
          <textarea id="mainTask" style="width: 550px;" class="inputxt" rows="4" maxlength="2000"
            name="mainTask">${tOHandoverPage.mainTask}</textarea> 
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">下周工作计划</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 留&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;言:&nbsp;&nbsp; </label></td>
        <td class="value" colspan="3">
          <textarea id="remark" style="width: 550px;" maxlength="200" class="inputxt" rows="4" name="remark">${tOHandoverPage.remark}</textarea>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">留言</label>
        </td>
      </tr>
      
      <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td colspan="3" class="value"><input type="hidden" value="${tOHandoverPage.id}" id="bid" name="bid" />
          <table style="max-width:550px;">
            <c:forEach items="${tOHandoverPage.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px; background-color:#F2F7FE;">
                <td><a href="javascript:void(0);"
                  onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tOHandoverPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a
                  href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"
                  title="下载">下载</a></td>
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail"
                  onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload queueID="filediv" name="fiels" id="file_upload" buttonText="添加文件" formData="bid"
              uploader="commonController.do?saveUploadFiles&businessType=tOHandover">
            </t:upload>
          </div>
        </td>
      </tr>
    </table>
   </t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/information/tOHandover.js"></script>
  <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
</html>	