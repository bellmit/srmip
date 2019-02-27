<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>任务节点完成情况</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(document).ready(function() {
      	//判断是否完成，完成：1 ; 未完成：非1
        var flag = $('input[name="finishFlag"]:checked').val();
        if(flag == 1){
            $('.finishTimeInfo').show();
        }
        //是否完成绑定完成时间
        $('#isFinshFlag').change(function() {
            var flag = $('input[name="finishFlag"]:checked').val();
            if (flag == 1) {
            	$('#finishTime').attr("datatype","date");
                $('.finishTimeInfo').show('slow', 'linear');
            } else {
                $('input[name="finishTime"]').removeAttr("datatype");
                $('.finishTimeInfo').hide('slow', 'linear');
                $('#finishTime').val("");
            }
        });
    });

    function uploadFile(data) {
        $("#bid").val(data.obj.id);
        if ($(".uploadify-queue-item").length > 0) {
            upload();
        } else {
            tip(data.msg);
            if (data.success) {
                var win = frameElement.api.opener;
                frameElement.api.close();
                win.location.reload();
            }
        }
    }

    //上传成功后，刷新
    function uploadCallback() {
        var win = frameElement.api.opener;
        frameElement.api.close();
        win.location.reload();
    }
</script>
</head>
<body style="overflow-x: hidden;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tPmTaskController.do?doFinish" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmTaskNodePage.id }">
    <input id="projectId"  type="hidden" value="${task.project.id }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">是否完成:</label></td>
        <td class="value" id="isFinshFlag"><input id="isFinish" type="radio" name="finishFlag" errormsg="请选择是否完成！" value="1" <c:if test="${tPmTaskNodePage.finishFlag eq '1'}">checked="checked"</c:if>> <label
            for="isFinish">是</label> <input id="noFinish" type="radio" name="finishFlag" value="0" <c:if test="${tPmTaskNodePage.finishFlag ne '1'}">checked="checked"</c:if>> <label
            for="noFinish">否</label></td>
      </tr>
      <tr class="finishTimeInfo" style="display: none;">
        <td align="right"><label class="Validform_label">实际完成时间:</label><font color="red">*</font></td>
        <td class="value">
            <input id="finishTime" name="finishTime" type="text" style="width: 150px"  class="Wdate" datatype="date"
            value='<fmt:formatDate value="${tPmTaskNodePage.finishTime}" type="date" pattern="yyyy-MM-dd"/>' onClick="WdatePicker({ dateFmt:'yyyy-MM-dd'})" >    
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">实际完成时间</label>
        </td>
      </tr> 
      <tr>
        <td align="right"><label class="Validform_label">备注:</label></td>
        <td class="value"><textarea id="remark" name="remark" rows="5" cols="10" style="width: 500px;" datatype="*1-1000" ignore="ignore">${tPmTaskNodePage.remark}</textarea></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
        <td class="value"><input type="hidden" value="${tPmTaskNodePage.attachmentCode}" id="bid" name="attachmentCode">
          <table style="max-width: 92%;" id="fileShow">
            <c:forEach items="${tPmTaskNodePage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" buttonText="添加文件"  auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
		    	formData="bid,projectId" uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmTaskNode" >
            </t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/task/tPmTask.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>