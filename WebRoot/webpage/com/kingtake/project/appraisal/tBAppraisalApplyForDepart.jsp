<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <%@page import="org.jeecgframework.workflow.common.WorkFlowGlobals"%> --%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>鉴定申请表</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
</head>
<body>
  <%
request.setAttribute("unaudit", ApprovalConstant.APPLYSTATUS_UNSEND);
request.setAttribute("send", ApprovalConstant.APPLYSTATUS_SEND);
request.setAttribute("audited", ApprovalConstant.APPLYSTATUS_AUDITED);
request.setAttribute("approvalView", ApprovalConstant.APPLYSTATUS_APPROVAL_VIEW);
request.setAttribute("finish", ApprovalConstant.APPLYSTATUS_FINISH);
%>

  <t:formvalid formid="formobj" dialog="true" usePlugin="password" beforeSubmit="saveMembersBeforeSubmit" layout="table" action="tBAppraisalApplyController.do?doUpdate" tiptype="1"
    callback="@Override uploadFile" btnsub="saveBtn">
    <script type="text/javascript">
			function uploadFile(data){
				$("#bid").val($.parseJSON(data.obj).id);
				if($(".uploadify-queue-item").length>0){
					upload();
				}else{
					$("#menu").tree('reload');
					$("#Validform_msg").remove();
				}
			}
				
			function close(){
				frameElement.api.close();
			}
			
			function reloadCurrentPage(){
				$("#menu").tree('reload');
				$("#Validform_msg").remove();
			}
			
			</script>
    <input id="id" name="id" type="hidden" value="${tBAppraisalApply.id }">
    <input id="projectId" type="hidden" value="${projectId }">
    <input id="auditStatus" name="auditStatus" type="hidden" value="${tBAppraisalApply.auditStatus}">
    <input id="bpmStatus" name="bpmStatus" type="hidden" value="${tBAppraisalApply.bpmStatus}">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label">成&nbsp;&nbsp;果&nbsp;&nbsp;名&nbsp;&nbsp;称&nbsp;:</label>
        </td>
        <td class="value" colspan="3">
          <input id="appraisalProjectId" name="appraisalProject.id" type="hidden" value='${tBAppraisalApply.appraisalProject.id}'>
          <input id="projectName" name="projectName" type="hidden" style="width: 465px" class="inputxt" readonly="readonly" value='${tBAppraisalApply.appraisalProject.projectName}'>
          <input id="achievementName" name="achievementName" type="text" style="width: 465px" class="inputxt" readonly="readonly" value='${tBAppraisalApply.appraisalProject.achievementName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">成果名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">起&nbsp;&nbsp;止&nbsp;&nbsp;时&nbsp;&nbsp;间&nbsp;: </label>
        </td>
        <td class="value">
          <input id="beginTime" name="beginTime" type="hidden" value='${tBAppraisalApply.appraisalProject.projectBeginDate}'>
          <input id="endTime" name="endTime" type="hidden" value='${tBAppraisalApply.appraisalProject.projectEndDate}'>
          <input id="dateRange" type="text" style="width: 180px" class="inputxt" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">起止时间</label>
        </td>
        <%-- <td align="right">
          <label class="Validform_label">归&nbsp;&nbsp;档&nbsp;&nbsp;号: </label>
        </td>
        <td class="value">
          <input id="archiveNum" name="archiveNum" type="text" style="width: 180px" class="inputxt" value='${tBAppraisalApply.archiveNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">归档号(HJ-502-2015-001)</label>
        </td> --%>
      </tr>
      </table>
      <fieldset style="border-color: gray;border-style: dotted;">
        <legend style="color: red;">成果完成单位</legend>
        <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right">
            <label class="Validform_label">成果完成单位: </label>
          </td>
          <td class="value" colspan="3">
            <input id="finishUnit" name="finishUnit" type="text" style="width: 490px" class="inputxt" value='${tBAppraisalApply.finishUnit}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果完成单位</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label">
              成果完成单位&nbsp;<br />联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人:
            </label>
          </td>
          <td class="value">
            <input id="finishContactId" name="finishContactId" type="hidden" value='${tBAppraisalApply.finishContactId}'>
            <input id="finishContactName" name="finishContactName" type="text" style="width: 180px" class="inputxt" value='${tBAppraisalApply.finishContactName}'>
            <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" inputTextname="finishContactId,finishContactName" idInput="finishContactId" mode="single"></t:chooseUser>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果完成单位联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label">联系电话: </label>
          </td>
          <td class="value">
            <input id="finishContactPhone" name="finishContactPhone" type="text" style="width: 180px" class="inputxt" value='${tBAppraisalApply.finishContactPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        </table>
        </fieldset>
        <fieldset style="border-color: gray;border-style: dotted;">
        <legend style="color: red;">主持鉴定单位</legend>
        <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right">
            <label class="Validform_label">主持鉴定单位: </label>
          </td>
          <td class="value" colspan="3">
            <input id="hostUnit" name="hostUnit" type="text" style="width: 490px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.hostUnit}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">主持鉴定单位</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label">
              主持鉴定单位&nbsp;<br />联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人:
            </label>
          </td>
          <td class="value">
            <input id="appraisalContactName" name="appraisalContactName" type="text" style="width: 180px" class="inputxt" value='${tBAppraisalApply.appraisalContactName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">主持鉴定单位联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label">联系电话: </label>
          </td>
          <td class="value">
            <input id="appraisalContactPhone" name="appraisalContactPhone" type="text" style="width: 180px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.appraisalContactPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        </table>
        </fieldset>
        <fieldset style="border-color: gray;border-style: dotted;">
        <legend style="color: red;">组织鉴定完成单位</legend>
        <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right">
            <label class="Validform_label">组织鉴定完成单位: </label>
          </td>
          <td class="value" colspan="3">
            <input id="organizeUnit" name="organizeUnit" type="text" style="width: 478px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.organizeUnit}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">组织鉴定完成单位</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label">
              组织鉴定完成单位&nbsp;<br />联&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人:
            </label>
          </td>
          <td class="value">
            <input id="organizeContactName" name="organizeContactName" type="text" style="width: 180px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.organizeContactName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">组织鉴定完成单位联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label">联系电话: </label>
          </td>
          <td class="value">
            <input id="organizeContactPhone" name="organizeContactPhone" type="text" style="width: 180px" class="inputxt" datatype="*0-25" value='${tBAppraisalApply.organizeContactPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        </table>
        </fieldset>
      <table>
      <%-- <tr>
        <td align="right">
          <label class="Validform_label"> 登&nbsp;&nbsp;记&nbsp;&nbsp;编&nbsp;&nbsp;号&nbsp;:</label>
        </td>
        <td class="value" colspan="3">
          <input id="registerCode" name="registerCode" type="text" style="width: 465px" class="inputxt" value='${tBAppraisalApply.registerCode}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">登记编号</label>
        </td>
      </tr> --%>
      <tr>
        <td align="right">
          <label class="Validform_label">
            基层编号: <font color="red">*</font>
          </label>
        </td>
        <td class="value" colspan="3">
          <input id="basicNum" name="basicNum" type="text" style="width: 360px;" datatype="*" class="inputxt" readonly="readonly" value='${tBAppraisalApply.basicNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">基层编号</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 成&nbsp;&nbsp;果&nbsp;&nbsp;类&nbsp;&nbsp;别&nbsp;:</label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="resultType" name="resultType" type="select" code="CGLB" codeType="1" defaultVal="${tBAppraisalApply.resultType}" extendParam="{style:'width:180px',class:'inputxt'}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">成果类别</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 鉴定形式:</label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="appraisalForm" name="appraisalForm" type="select" code="JDXS" codeType="1" defaultVal="${appraisalProject.appraisalForm}"
            extendParam="{style:'width:180px',class:'inputxt'}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定形式</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 鉴&nbsp;&nbsp;定&nbsp;&nbsp;时&nbsp;&nbsp;间&nbsp;:</label>
        </td>
        <td class="value">
          <input id="appraisalTime" name="appraisalTime" type="text" style="width: 180px;" class="Wdate" onClick="WdatePicker()" disabled="disabled"
            value="<fmt:formatDate value='${tBAppraisalApply.appraisalProject.appraisalTime}' 
							type='date' pattern='yyyy-MM-dd'/>">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定时间</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 鉴定地点:</label>
        </td>
        <td class="value">
          <input id="appraisalAddress" name="appraisalAddress" type="text" style="width: 180px" class="inputxt" readonly="readonly" value="${tBAppraisalApply.appraisalProject.appraisalAddress}">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定地点</label>
        </td>
      </tr>
      <%-- <tr>
          <td align="right">
            <label class="Validform_label">审核人：<font color="red">*</font></label>
          </td>
          <td class="value" colspan="3">
            <input id="checkUserid" name="checkUserid" datatype="*" type="hidden" value='${tBAppraisalApply.checkUserid}'>
            <input id="checkDepartid" name="checkDepartid" type="hidden" value='${tBAppraisalApply.checkDepartid}'>
            <input id="checkUsername" name="checkUsername" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalApply.checkUsername}'>
            <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departId" isclear="true" inputTextname="checkUserid,checkUsername,checkDepartid" idInput="checkUserid" mode="single" departIdInput="checkDepartid"></t:chooseUser>
          </td>
        </tr> --%>
      <tr>
        <td align="right">
          <label class="Validform_label"> 附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件&nbsp;: </label>
        </td>
        <td class="value" colspan="3">
          <input type="hidden" value="${tBAppraisalApply.id}" id="bid" name="bid" />
          <table style="max-width: 92%;">
            <c:forEach items="${tBAppraisalApply.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td>
                  <a href="javascript:void(0);"
                    onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBAppraisalApply.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a>
                </td>
                <td style="width: 40px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid,projectId"
              uploader="commonController.do?saveUploadFiles&businessType=tBAppraisalApply"></t:upload>
          </div>
        </td>
      </tr>
    </table>
    <!-- 修改日志表用 -->
    <input id="sendIds" name="sendIds" type="hidden">
    <input type="hidden" id="memberStr" name="memberStr" />

  </t:formvalid>
  <script type="text/javascript">
  function sendApply(){
  	$.dialog({
						content : 'url:tBAppraisalApplyController.do?sendApply&applyId='+$('#id').val(),
						title : '鉴定申请提交审核',
						lock : true,
						opacity : 0.3,
						zIndex:zIndex,
						width : '700px',
						height : '400px',
						ok:function(){
							iframe = this.iframe.contentWindow;
							saveObj();
							return false;
						},
						cancel:function(){
				
						},
					});
	}
  </script>
  <table id="memberList" style="width: 670px;"></table>
  <c:if test="${tBAppraisalApply.auditStatus eq audited or tBAppraisalApply.auditStatus eq approvalView or tBAppraisalApply.auditStatus eq finish}">
    <script type="text/javascript">
$("#Validform_msg").remove();
</script>
    <div style="height: 100px">
      <t:formvalid formid="formobj1" dialog="true" usePlugin="password" beforeSubmit="saveMembersBeforeSubmit" layout="table" action="tBAppraisalApplyController.do?updateAttached&role=depart"
        tiptype="1" callback="@Override uploadFile2" btnsub="saveBtn2">
        <br />
        <fieldset style="border-style: solid none none none; border-top: 3px double #CACACA;">
          <legend>
            <b>申请表审批信息</b>
          </legend>
          <table style="width: 100%;">
            <tr>
              <td align="right" width="20%">
                <label class="Validform_label"> 海军机关批复意见:</label>
              </td>
              <td class="value" width="80%" colspan="3">
                <textarea id="navalAuthorityView" name="navalAuthorityView" style="width: 80%" rows="3" class="input">${tBAppraisalApplyAttachedPage.navalAuthorityView}</textarea>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label"> 附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件&nbsp;: </label>
              </td>
              <td class="value" colspan="3">
                <input type="hidden" value="" id="bid2" name="bid2" />
                <table style="max-width: 92%;">
                  <c:forEach items="${tBAppraisalApplyAttachedPage.certificates}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);"
                          onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBAppraisalApplyAttachedPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a>
                      </td>
                      <td style="width: 40px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>
                </table>
                <div class="form jeecgDetail" id="filediv2"></div>
                <span id="file_upload2span"> <input type="file" name="fiel" id="file_upload2" />
                </span>
              </td>
            </tr>
          </table>
        </fieldset>
        <script type="text/javascript">
      		//$(":input").attr("disabled","true");
      		//隐藏选择框和清空框
      		$("a[icon='icon-search']").css("display","none");
      		$("a[icon='icon-redo']").css("display","none");
      		$("a[icon='icon-save']").css("display","none");
      		$("a[icon='icon-ok']").css("display","none");
      		//隐藏下拉框箭头
      		$(".combo-arrow").css("display","none");
      		//隐藏添加附件
      		$("#filediv").parent().css("display","none");
      		//隐藏easyui-linkbutton
      		$(".easyui-linkbutton").css("display","none");
        </script>
          <table style="width: 100%;">
            <input id="id" name="id" type="hidden" value="${tBAppraisalApplyAttachedPage.id }">
            <input id="applyId" name="applyId" type="hidden" value="${tBAppraisalApply.id }">
            <tr>
              <td align="right">
                <label class="Validform_label"> 申批号: </label>
              </td>
              <td class="value" colspan="3">
                <t:codeTypeSelect name="applyPrefix" type="SELECT" codeType="1" code="ZSXDDW" id="applyPrefix" extendParam="{style='width:80px;',class='departInput'}"
                  defaultVal="${tBAppraisalApplyAttachedPage.applyPrefix}"></t:codeTypeSelect>
                ( <input id="applyYear" name="applyYear" type="text" datatype="n1-4" ignore="ignore" style="width: 60px" value='${tBAppraisalApplyAttachedPage.applyYear}'>
                ) 鉴审批字<input id="applyNum" name="applyNum" type="text" style="width: 80px" value='${tBAppraisalApplyAttachedPage.applyNum}'>号
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  申请鉴定单&nbsp;<br>位批准时间:
                </label>
              </td>
              <td class="value" colspan="3">
                <input id="applyApproveDate" name="applyApproveDate" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()"
                  value="<fmt:formatDate value='${tBAppraisalApplyAttachedPage.applyApproveDate}' type='date' pattern='yyyy-MM-dd'/>">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">申请鉴定单位批准时间</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  主持鉴定单&nbsp;<br>位批准时间:
                </label>
              </td>
              <td class="value" colspan="3">
                <input id="hostApproveDate" name="hostApproveDate" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()"
                  value="<fmt:formatDate value='${tBAppraisalApplyAttachedPage.hostApproveDate}' type='date' pattern='yyyy-MM-dd'/>">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">主持鉴定单位批准时间</label>
              </td>
            </tr>
            <tr>
              <td align="right">
                <label class="Validform_label">
                  组织鉴定单&nbsp;<br>位批准时间:
                </label>
              </td>
              <td class="value" colspan="3">
                <input id="organizeApproveDate" name="organizeApproveDate" type="text" style="width: 180px" class="Wdate" onClick="WdatePicker()"
                  value="<fmt:formatDate value='${tBAppraisalApplyAttachedPage.organizeApproveDate}' type='date' pattern='yyyy-MM-dd'/>">
                <span class="Validform_checktip"></span>
              </td>
            </tr>
            <tr>
              <td colspan="4" align="center">
                <input id="saveBtn2" value="提交" type="button" style="display: none;">
              </td>
            </tr>
          </table>
        </fieldset>
        <script type="text/javascript">
      $(function(){
    	  $('#file_upload2').uploadify(
                  {
                      buttonText : '上传鉴定申请表扫描件',
                      auto : false,
                      progressData : 'speed',
                      multi : true,
                      height : 25,
                      width : 150,
                      overrideEvents : [ 'onDialogClose' ],
                      fileTypeDesc : '文件格式:',
                      queueID : 'filediv2',
                      fileTypeExts : '*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.jpg;*.zip;',
                      fileSizeLimit : '500MB',
                      swf : 'plug-in/uploadify/uploadify.swf',
                      uploader : 'commonController.do?saveUploadFiles&businessType=apppraisalApplyAttach',
                      onUploadStart : function(file) {
                          var bid = $('#bid2').val();
                          var projectId = $('#projectId').val();
                          $('#file_upload2').uploadify("settings", "formData", {
                              'bid' : bid,
                              'projectId':projectId                            
                          });
                      },
                      onQueueComplete : function(queueData) {
                          var win = frameElement.api.opener;
                          win.reloadTable();
       				      frameElement.api.close();
                      },
                      onUploadSuccess : function(file, data, response) {
                         
                      },
                      onFallback : function() {
                          tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                      },
                      onSelectError : function(file, errorCode, errorMsg) {
                          switch (errorCode) {
                          case -100:
                              tip("上传的文件数量已经超出系统限制的" + $('#file_upload2').uploadify('settings', 'queueSizeLimit')
                                      + "个文件！");
                              break;
                          case -110:
                              tip("文件 [" + file.name + "] 大小超出系统限制的"
                                      + $('#file_upload2').uploadify('settings', 'fileSizeLimit') + "大小！");
                              break;
                          case -120:
                              tip("文件 [" + file.name + "] 大小异常！");
                              break;
                          case -130:
                              tip("文件 [" + file.name + "] 类型不正确！");
                              break;
                          }
                      },
                      onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                      }
                  });
      })
     
      
      function uploadFile2(data){
			$("#bid2").val(data.obj);
			if($(".uploadify-queue-item").length>0){
				$('#file_upload2').uploadify('upload', '*');
			}else{
			    var win = frameElement.api.opener;
				win.tip(data.msg);
			    if(data.success){
				   win.reloadTable();
				   frameElement.api.close();
			    }
			}
		}
		function close(){
			frameElement.api.close();
		}
		
      </script>
      </t:formvalid>
    </div>
  </c:if>
  <script type="text/javascript">
	$(document).ready(function(){
		$("#dateRange").val($("#beginTime").val().substring(0, 10) + "~" + $("#endTime").val().substring(0, 10));
		if(!$("#finishUnit").val()){
			$("#finishUnit").val("海军工程大学");
		}
		
		/* loadButton($("#id").val(), $("#state").val()); */
		extend();
		initTab();
	});

	//初始化成员表格
	function initTab(){
		var toolbar = [];
	    $('#memberList').datagrid({
	        title:'推荐鉴定委员会成员',
	        fitColumns : true,
	        nowrap : true,
	        striped : true,
	        remoteSort : false,
	        idField : 'id',
// 	        width : '670px',
	        editRowIndex:-1,
	        toolbar : toolbar,
	        columns : [ [ 
	         {
	            field : 'id',
	            title : 'id',
	            width : 100,
	            hidden : true
	        }, {
	            field : 'memberName',
	            title : '姓名',
	            width : 100,
	            editor:{
	                type:'validatebox',
	                
	                options:{
	                    required:true,
	                    validType:'maxLength[25]'
	                }
				}
	        }, {
	            field : 'memberPosition',
	            title : '技术职称',
	            width : 100,
	            formatter: function(value,row,index){
	 	           if(value!=""&&value!=undefined){
	 	               return row.memberPositionStr;
	 	           }else{
	 	               return "";
	 	           }      
	             }, 
	            editor:{
	                type : 'combobox',  
	                options : {  
	                url:'tBCodeTypeController.do?typeCombo&codeType=1&code=ZHCH',  
	                valueField: 'CODE',    
	                textField: 'NAME',    
	                panelHeight: 'auto',  
	                required: true ,  
	                editable:false  
				}
	          }
	        },
	        {
	            field : 'memberProfession',
	            title : '专业',
	            width : 100,
	            formatter: function(value,row,index){
		           if(value!=""&&value!=undefined){
		               return row.memberProfessionStr;
		           }else{
		               return "";
		           }      
	            },
	            editor:{
	                type : 'combobox',  
	                options : {  
	                url:'tBCodeTypeController.do?typeCombo&codeType=1&code=MAJOR',  
	                valueField: 'CODE',    
	                textField: 'NAME',    
	                panelHeight: 'auto',  
	                required: true ,  
	                editable:false
	                }  
				}
	        },
	        {
	            field : 'workUnit',
	            title : '工作单位',
	            width : 100,
	            editor:{
	                type:'validatebox',
	                options:{
	                    required:true,
	                    validType:'maxLength[180]'
	                }
				}
	        },{
	            field : 'memo',
	            title : '备注',
	            width : 180,
	            editor:{
	                type:'validatebox',
	                options:{
	                    validType:'maxLength[180]'
	                }
				}
	        } ] ],
	            pagination : false,
	            rownumbers : true,
	            onLoadSuccess : function() {
	            }
	        });
	
	    loadData();

    }
    
    function loadData(){
      //加载数据
        var id = $("#id").val();
        if (id != "") {
            var x_url = "tBAppraisalApplyController.do?getMemberListByApply&tbId=" + id;
            $('#memberList').datagrid('options').url = x_url;
            setTimeout(function() {
                $('#memberList').datagrid('load');
            }, 0);

        }
    }

    function parse(data) {
        var parsed = [];
        $.each(data.rows, function(index, row) {
            parsed.push({
                data : row,
                result : row,
                value : row.id
            });
        });
        return parsed;
    }

    /**
     * 每一个选择项显示的信息
     * 
     * @param {Object} data
     */
    function formatItem(data) {
        return data.memberName+","+data.workUnit;
    }
     
    function saveMembersBeforeSubmit(){
    	var rows = $('#memberList').datagrid("getRows");
        for (var i = 0; i < rows.length; i++) {
            if ($('#memberList').datagrid('validateRow', i)) {
                $('#memberList').datagrid("endEdit", i);
            } else {
                return false;
            }
        }
        rows = $('#memberList').datagrid("getRows");
        var memberRows = [];
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].memberName != "" && rows[i].memberName != undefined) {
                memberRows.push(rows[i]);
            }
        }
        var memberStr = JSON.stringify(memberRows);
    	$("#memberStr").val(memberStr);
    }

    /**
     * 保存或更新
     */
    function saveOrUpdate() {
        var rows = $('#memberList').datagrid("getRows");
        for (var i = 0; i < rows.length; i++) {
            if ($('#memberList').datagrid('validateRow', i)) {
                $('#memberList').datagrid("endEdit", i);
            } else {
                return false;
            }
        }
        rows = $('#memberList').datagrid("getRows");
        var memberRows = [];
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].memberName != "" && rows[i].memberName != undefined) {
                memberRows.push(rows[i]);
            }
        }
        var memberStr = JSON.stringify(memberRows);
        var data = $("#formobj").serialize();
        data = data + '&memberStr=' + memberStr;
        $.ajax({
            type : 'post',
            url : 'tBAppraisalApplyController.do?doUpdate',
            data : data,
            success : function(result) {
                result = $.parseJSON(result);
                tip(result.msg);
                frameElement.api.opener.reloadTable();

                // 将id保存到页面
                if (!$("#id").val()) {
                    var obj = $.parseJSON(result.obj);
                    $("#id").val(obj.id);
                    $("#state").val(obj.state);
                }
                loadButton($("#id").val(), $("#state").val());
                loadData();
            }
        });
        return false;
    }

    function loadButton(id, state) {
        frameElement.api.button();
        if (!id) {
            // 新建：保存、关闭
            frameElement.api.button({
                name : '保存',
                callback : saveOrUpdate,
                focus : true
            }, {
                name : '关闭'
            });
        } else if (state == "0") {
            // 未发送：保存、提交、关闭
            frameElement.api.button({
                name : '保存',
                callback : saveOrUpdate,
            }, {
                name : '提交',
                //callback:toManager,
                focus : true
            }, {
                name : '关闭'
            });
        } else {
            // 已提交
            frameElement.api.button({
                name : '关闭'
            });
        }
    }

    //校验拓展
    function extend() {
        $.extend($.fn.validatebox.defaults.rules, {
            maxLength : {
                validator : function(value, param) {
                    return value.length <= param[0];
                },
                message : '输入长度不能超过{0}'
            }
        });
    }
</script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
</body>