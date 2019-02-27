<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>减免垫支信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(document).ready(function() {
    });
</script>
<style type="text/css">
#amountTab {
	border: solid 1px blue;
	border-collapse: collapse;
	width: 436px;
}

#amountTab tr td {
	border: solid 1px blue;
	text-align: center;
}

#amountTab input {
	border: solid 1px red;
    width: 100px;
}
</style>
<script type="text/javascript">
    $(function() {
	   var paySource = $("#paySourceHidden").val();
	   if(paySource!=""){
		   $("#paySource").val(paySource);
	   }
    });
    
    function saveCallback(data) {
            var win;
            var dialog = W.$.dialog.list['processDialog'];
            if(dialog==undefined){
                win = frameElement.api.opener;
            }else{
                win = dialog.content;
            }
            if (data.success) {
                win.reloadTable();
                frameElement.api.close();
            }
    }

    function checkAmount(){
    	var balance = $("#balance").val();
    	if(balance==""){
    		$("#balance").numberbox("setValue",0.00);
    	}
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPayfirstController.do?doUpdate" tiptype="1" beforeSubmit="checkAmount" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tPmPayfirstPage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${tPmPayfirstPage.projectId }">
    <input id="bpmStatus" type="hidden" value="${tPmPayfirstPage.bpmStatus}">
  
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 垫支经费额度： </label> <font color="red">*</font></td>
        <td class="value"><input id="payFunds" name="payFunds" type="text" style="width: 150px; text-align: right;" class="easyui-numberbox" datatype="*"
            data-options="min:0,max:9999999999,precision:2,groupSeparator:','" value='${tPmPayfirstPage.payFunds}'><span class="Validform_checktip">元</span></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 垫资来源:</label><font color="red">*</font></td>
        <td class="value">
        <input id="paySourceHidden" value="${tPmPayfirstPage.paySource}" type="hidden">
        <select id="paySource" name="paySource" style="width:155px;" datatype="*">
          <option value="">请选择</option>
          <option value="1">大学</option>
          <option value="2">责任单位</option>
          <option value="3">承研单位</option>
        </select>
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">垫资来源</label>
       </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">年度：</label><font color="red">*</font></td>
        <td class="value"><input id="payYear" style="width: 150px;" class="inputxt" datatype="*1-4" name="payYear" value='${tPmPayfirstPage.payYear}'></input><img alt="会计年度，导出数据包给财务时将按照此年度来导出" title="会计年度，导出数据包给财务时将按照此年度来导出" src="plug-in\easyui1.4.2\themes\icons\tip.png">
          <span class="Validform_checktip"></span></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">垫支科目代码：</label><font color="red">*</font></td>
        <td class="value"><input id="paySubjectcode" style="width: 150px;" class="inputxt" datatype="*1-50" name="paySubjectcode" value='${tPmPayfirstPage.paySubjectcode}'></input>
          <span class="Validform_checktip"></span></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 余额:</label></td>
        <td class="value">
        <input id="balance" name="balance" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" style="width:150px;" class="easyui-numberbox" value="${tPmPayfirstPage.balance}">
            <span class="Validform_checktip"></span> 
            <label class="Validform_label" style="display: none;">余额</label>
       </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">垫支理由：</label></td>
        <td class="value"><textarea id="reason" style="width: 430px;" class="inputxt" rows="6" ignore="ignore" datatype="*2-400" name="reason">${tPmPayfirstPage.reason}</textarea>
          <span class="Validform_checktip"></span></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td class="value"><input type="hidden" value="${tPmPayfirstPage.attachmentCode}" id="bid" name="attachmentCode" />
          <table style="max-width: 430px;" id="fileShow">
            <c:forEach items="${tPmPayfirstPage.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload queueID="filediv" name="fiels" id="file_upload"  buttonText="添加文件" 
	      	                   formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess"
              uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmPayfirst" >
            </t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>