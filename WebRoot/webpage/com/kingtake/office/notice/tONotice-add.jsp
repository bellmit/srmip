<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>通知公告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	$(document).ready(function() {
		$('#tt').tabs({
			onSelect : function(title) {
				$('#tt .panel-body').css('width', 'auto');
			}
		});
		$(".tabs-wrap").css('width', '100%');
	});
	
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

</script>
</head>
<body style="overflow-x: hidden;">
	<t:formvalid formid="formobj" dialog="true" 
		layout="table" tiptype="1"  action="tONoticeController.do?doAdd">
		<input id="id" name="id" type="hidden" value="${tONoticePage.id }">
		<input id="senderId" name="senderId" type="hidden"
			value="${user.id}">
		<table cellpadding="0" cellspacing="1" style="margin:auto;width:600px;">
			<tr>
				<td align="right"><label class="Validform_label">发&nbsp;送&nbsp;人&nbsp;:&nbsp;&nbsp;</label></td>
				<td class="value"><input id="senderName" name="senderName"
					type="text" style="width: 150px" class="inputxt" readonly="readonly" value="${user.realName}"> <span
					class="Validform_checktip"></span> <label class="Validform_label"
					style="display: none;">发送人</label></td>
				<td align="right"><label class="Validform_label">发送时间:&nbsp;&nbsp;</label>
				</td>
				<td class="value"><input id="sendTime" name="sendTime"  datatype="*"
					type="text" style="width: 150px" class="Wdate" value="${now}"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"> <span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">发送时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:<font color="red">*</font></label></td>
				<td class="value" colspan="3"> 
                    <input id="title" name="title" type="text" style="width:455px" class="inputxt"  datatype="byterange" max="100" min="1"> 
                    <span class="Validform_checktip"></span> 
                    <label class="Validform_label"style="display: none;">标题</label>
                </td>
            </tr>
            <tr>
				<td align="right"><label class="Validform_label">关联项目:&nbsp;&nbsp;</label>
				</td>
				<td class="value" colspan="3">
                  <input type="hidden" id="projectId" name="projId1">
                  <input id="projName1" name="projName1" type="text" style="width:330px" class="inputxt" readonly="readonly">
				  <t:chooseProject inputId="projId1" inputName="projName1" isclear="true" mode="single"></t:chooseProject>
                  <span class="Validform_checktip"></span> 
                  <label class="Validform_label" style="display: none;">关联项目</label>
              </td>
			</tr>
			<tr>
			  <td align="right"><label class="Validform_label">关联文号:&nbsp;&nbsp;</label>
			  </td>
			  <td class="value" colspan="3">
                  <input id="fileNum" name="fileNum" type="text" style="width:330px" class="inputxt" readonly="readonly"  >
                  <t:choose url="tOSendReceiveRegController.do?selectReg" tablename="tOSendReceiveRegList" width="800px"
                  icon="icon-search" title="收发文列表" textname="mergeFileNum" inputTextname="fileNum" isclear="true"></t:choose>
                  <span class="Validform_checktip"></span> 
                  <label class="Validform_label" style="display: none;">关联文号</label>
              </td>
             </tr>
             <tr>
              <td align="right"><label class="Validform_label">接&nbsp;收&nbsp;人&nbsp;:<font color="red">*</font></label></td>
              <td class="value" colspan="3">
                <input type="hidden" name="receiverid" id="receiverid" > 
                <input id="realName" style="width: 330px;" class="inputxt" name="realName"  readonly="readonly"  datatype="*"></input>
                <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" inputTextname="receiverid,realName" idInput="receiverid"></t:chooseUser>
                <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">接收人</label>
              </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:<font color="red">*</font></label></td>
				<td class="value" colspan="3">
                    <textarea id="content" style="width:455px;" class="inputxt" rows="6" name="content"   datatype="byterange" max="4000" min="1"></textarea>
					<span class="Validform_checktip"></span> 
                    <label class="Validform_label" style="display: none;">内容</label>
                </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
     			<td colspan="3" class="value">
     			<input type="hidden" value="${tONoticePage.attachmentCode}" id="bid" name="attachmentCode" />
      <table id="fileShow">
        <c:forEach items="${tONoticePage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
            <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
          </tr>
        </c:forEach>
      </table>
      <div>
	 	  <div class="form" id="filediv"></div>
	      <t:upload  queueID="filediv" name="fiels" id="file_upload"   
	      	buttonText="添加文件" formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" fileSizeLimit="2GB"
	      	uploader="commonController.do?saveUploadFilesToFTP&businessType=tONotice" >
	  	  </t:upload>
	  </div>
      </td>
    </tr>
		</table>
		<%-- <div style="width: auto; height: 455px;">
			增加一个div，用于调节页面大小，否则默认太小
			<div style="width: 800px; height: 1px;"></div>
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tONoticeController.do?tONoticeReceiveList&id=${tONoticePage.id}"
					icon="icon-search" title="通知公告子表" id="tONoticeReceive"></t:tab>
			</t:tabs>
		</div> --%>
	</t:formvalid>
	<!-- 添加 附表明细 模版 -->
	<table style="display: none">
		<tbody id="add_tONoticeReceive_table_template">
			<tr>
				<td align="center"><div style="width: 25px;" name="xh"></div></td>
				<td align="center"><input style="width: 20px;" type="checkbox"
					name="ck" /></td>
				<td align="left"><input
					name="tONoticeReceiveList[#index#].receiverId" maxlength="32"
					type="text" class="inputxt" style="width: 120px;"> <label
					class="Validform_label" style="display: none;">接收人id</label></td>
				<td align="left"><input
					name="tONoticeReceiveList[#index#].receiverName" maxlength="50"
					type="text" class="inputxt" style="width: 120px;"> <label
					class="Validform_label" style="display: none;">接收人姓名</label></td>
				<td align="left"><input
					name="tONoticeReceiveList[#index#].readFlag" maxlength="1"
					type="text" class="inputxt" style="width: 120px;"> <label
					class="Validform_label" style="display: none;">是否阅读</label></td>
				<td align="left"><input
					name="tONoticeReceiveList[#index#].readTime" maxlength="0"
					type="text" class="Wdate" onClick="WdatePicker()"
					style="width: 120px;"> <label class="Validform_label"
					style="display: none;">阅读时间</label></td>
			</tr>
		</tbody>
	</table>
</body>
<script src="webpage/com/kingtake/office/notice/tONotice.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
