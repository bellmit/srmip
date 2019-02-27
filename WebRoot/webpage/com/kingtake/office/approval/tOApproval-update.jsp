<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
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
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password"  callback="@Override uploadFile" layout="table" action="tOApprovalController.do?doUpdate" tiptype="1">
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
					
					
					<input id="id" name="id" type="hidden" value="${tOApprovalPage.id }">
<%-- 					<input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"> --%>
					<input id="contactId" name="contactId" type="hidden" value="${tOApprovalPage.contactId }">
		
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><p class="STYLE1">校首长批示：</p>
    <p><c:forEach items="${slist.llist}" var="ll">${ll.receiveUsername} : ${ll.suggestionContent}。(<fmt:formatDate value='${ll.operateTime}' type="date" pattern="yyyy-MM-dd HH:mm"/>)<br></c:forEach></p>
    <p>&nbsp;</p>
    <p>&nbsp;</p></td>
    <td align="right" valign="top"><t:codeTypeSelect id="secrityGrade" name="secrityGrade" defaultVal="${tOApprovalPage.secrityGrade}" 
                type="select" code="XMMJ" codeType="0" extendParam="{'style':'width:100px;font-weight: bold;font-size: 14px;'}"></t:codeTypeSelect></td>
  </tr>
  <tr>
    <td><p class="STYLE1">部(院)领导批示:</p>
    <p><c:forEach items="${slist.olist}" var="ol">${ol.receiveUsername} : ${ol.suggestionContent}。(<fmt:formatDate value='${ol.operateTime}' type="date" pattern="yyyy-MM-dd HH:mm"/>)<br></c:forEach></p>
    <p>&nbsp;</p>
    </td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="10" cellpadding="0">
  <tr>
    <td colspan="3" align="center" class="STYLE2">呈　批　件</td>
  </tr>
  <tr>
    <td colspan="3" align="center"><input id="fileNumPrefix" name="fileNumPrefix" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.fileNumPrefix}' datatype="byterange" max="20" min="1" ignore="ignore" readonly="readonly">
				﹝20<input id="approvalYear" name="approvalYear" datatype="n1-2" ignore="ignore" type="text" style="width: 20px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.approvalYear}' readonly="readonly">﹞
				<input id="applicationFileno" name="applicationFileno" datatype="n1-4" ignore="ignore" type="text" style="width: 50px;border-style: none none solid none;border-color:#54A5D5;border-width: 1px;" class="inputxt" value='${tOApprovalPage.applicationFileno}' readonly="readonly">号</td>
  </tr>
  <tr>
    <td><span class="STYLE1">承办单位：</span><t:departComboTree id="bb" name="bb" idInput="undertakeUnitId" nameInput="undertakeUnitName" width="155" lazy="false" value="${tOApprovalPage.undertakeUnitName }"></t:departComboTree><input id="undertakeUnitName" name="undertakeUnitName" type="hidden" style="width: 120px" class="inputxt" value='${tOApprovalPage.undertakeUnitName}' datatype="*"><input id="undertakeUnitId" name="undertakeUnitId" type="hidden" value="${tOApprovalPage.undertakeUnitId }"></td>
    <td><span class="STYLE1">联系人：</span><input id="contactName" name="contactName" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOApprovalPage.contactName}' datatype="byterange" max="50" min="1"></td>
    <td><span class="STYLE1">电话：</span><input id="contactPhone" name="contactPhone" type="text" style="border-style: none none solid none;width: 100px" class="inputxt" value='${tOApprovalPage.contactPhone}' datatype="byterange" max="30" min="1"></td>
  </tr>
  <tr>
    <td height="3" colspan="3" bgcolor="#339966"></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="10" cellpadding="0">
  <tr>
    <td align="center"><input placeholder="请输入标题" id="title" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;" name="title" class="STYLE3" value='${tOApprovalPage.title}'  datatype="byterange" max="200" min="1"></td>
  </tr>
  <tr>
    <td><p><input id="receiveUnitid" name="receiveUnitid" type="hidden" value="${tOApprovalPage.receiveUnitid }">
    <input placeholder="请输入接收单位(双击选择)" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;" id="receiveUnitname" name="receiveUnitname" ondblclick="choose_297e201048183a730148183ad85c0001('receiveUnitid','receiveUnitname')" value='${tOApprovalPage.receiveUnitname}' datatype="*">： <br>
       </p>
      <p><textarea id="applicationContent" name="applicationContent" rows="7" style="width: 750px;" placeholder="请输入内容" datatype="byterange" max="4000" min="1">${tOApprovalPage.applicationContent}</textarea></p>
    <p align="right"><input id="signUnitid" name="signUnitid" type="hidden" value="${tOApprovalPage.signUnitid }">
    <input placeholder="请输入落款单位(双击选择)" style="border-style: none none solid none;border-color: #54A5D5;border-width: 1px;width:142px;"  id="signUnitname" name="signUnitname" value='${tOApprovalPage.signUnitname}' ondblclick="choose_297e201048183a730148183ad85c0001('signUnitid','signUnitname')" datatype="*"></p>
    <p align="right"><input id="createDate" name="createDate" type="text" style="width: 140px" 
					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<fmt:formatDate value='${tOApprovalPage.createDate}' type='date' pattern='yyyy-MM-dd'/>"></td>
  </tr>
  <tr>
  <td><span class="STYLE1">附件</span>
  <input type="hidden" value="${tOApprovalPage.id }" id="bid" name="bid" />
  <table>
        <c:forEach items="${tOApprovalPage.certificates }" var="file"  varStatus="idx">
          <tr>
            <td style="width:60%;white-space: nowrap;"><a href="javascript:void(0)" onclick="createdetailwindow('预览','commonController.do?goAccessoryTab&bid=${tOApprovalPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,500)">${file.attachmenttitle}</a></td>
            <td style="width:10%;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
            <td style="width:10%;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
          </tr>
        </c:forEach>
      </table></td>
  </tr>
  <tr>
      <td><script type="text/javascript">
          $.dialog.setting.zIndex =2111;
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
 <div class="form" id="filediv"></div>
      <t:upload  queueID="filediv" name="fiels" id="file_upload"  buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFiles&businessType=tOApproval">
  </t:upload>
  </div>
 </td>
</tr>
</table>
		
		<div style="width: auto; height: 200px;">
			<div style="width: 880px; height: 1px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tOApprovalController.do?getStepList&id=${tOApprovalPage.id}"
					icon="icon-search" title="流转步骤" id="tOReceiveBillStep"></t:tab>
			</t:tabs>
			</div>
		</div>
		</t:formvalid>
 </body>
  <script type="text/javascript"  src = "webpage/com/kingtake/office/approval/tOApproval.js"></script>		