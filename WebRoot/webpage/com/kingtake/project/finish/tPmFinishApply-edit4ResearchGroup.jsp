<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>项目结题申请信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function() {
    //保存申请
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });

    //发送审批
    var auditStatus = $("#auditStatus").val();
    $("#auditBtn").click(function() {
      $("#saveBtn").hide();
      $("#submitFlag").val("1");
      $("#btn_sub").click();
    });

    if (auditStatus == '1' || auditStatus == '2') {
        disableForm();
    }
    
    initBtn();//控制按钮状态
  });
  
  function initBtn(){
      var auditStatus = $("#auditStatus").val();
      if(auditStatus==""){//初始化
          $("#saveBtn").show();
      }else if(auditStatus=="0"){//已保存未发送
          $("#saveBtn").show();
          $("#auditBtn").show();
      }else if(auditStatus=="1"){//已发送
          $("#msgLabel").show();
          $("#auditBtn").show();
      }else if(auditStatus=="2"){//已完成
    	  $("#msgLabel").text("流程已完成，不可修改");
          $("#msgLabel").show();
          $("#auditBtn").show();
      }else if(auditStatus=="3"){//已驳回
          $("#auditBtn").show();
          $("#saveBtn").show();
      }
  }
  
  function submitProcess(){
	  var id = $("#id").val();
	  var auditStatus = $("#auditStatus").val();
      if (auditStatus == '0' || auditStatus == '3') {
        W.$.dialog({
          id : 'apprInfo',
          lock : true,
          background : '#000', /* 背景色 */
          opacity : 0.5, /* 透明度 */
          width : 950,
          height : window.top.document.body.offsetHeight-100,
          title : '结题申请审核',
          parent : windowapi,
          content : 'url:tPmFinishApplyController.do?goAudit&id=' + id,
          ok : function() {
        	  openSendAudit(id,auditStatus,'<%=ApprovalConstant.APPR_TYPE_FINISH_APPLY%>');
        	  return false;
          },
          okVal : "发送审核",
          cancelVal : '关闭',
          cancel : true
        });
      } else if (auditStatus == '1'||auditStatus == '2') {
        W.$.dialog({
          id : 'apprInfo',
          lock : true,
          background : '#000', /* 背景色 */
          opacity : 0.5, /* 透明度 */
          width : 950,
          height : window.top.document.body.offsetHeight-100,
          title : '结题申请审核',
          parent : windowapi,
          content : 'url:tPmFinishApplyController.do?goAudit&id=' + id,
          cancelVal : '关闭',
          cancel : true
        });
      }
  }
  
  //将表单元素禁用
  function disableForm(){
      $("#formobj :input").attr("disabled", "true");
      //隐藏添加附件
      $("#filediv").parent().css("display", "none");
      //隐藏附件的删除按钮
      $(".jeecgDetail").parent().css("display", "none");
      //隐藏easyui-linkbutton
      $(".easyui-linkbutton").css("display", "none");
  }

  //更新审批状态
  function updateFinishApplyFinishFlag(apprId) {
    var url1 = 'tPmFinishApplyController.do?updateFinishFlag';
    var url2 = url1 + '2';
    updateFinishFlag(apprId, url1, url2);
  }

  //完成或取消完成后，刷新界面
  function reloadTable() {
      window.location.reload();
  }

  //apprUtil.js中需要调用执行的方法，根据业务需要可以补充相应代码
  function loadButton() {
  }
  
  
    function saveCallback(data) {
    	    var submitFlag = $("#submitFlag").val();
            if (data.success) {
            	 if(submitFlag=='1'){
            		 disableForm();
            		 submitProcess();
            	 }else{
            	   tip(data.msg);
            	   setTimeout(function(){
                      window.location.reload();
            	   },1000);
            	 }
            }else{
            	tip(data.msg);
            }
    }

</script>
</head>
<body>
  <div id="container">
    <div id="topTitle" style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #4782d8; color:#fff;border-bottom: solid 1px #95B8E7;">
      <h1 align="center" style="line-height: 100%;">${tPmFinishApplyPage.project.projectName }-科研项目结题申请表</h1>
      <span>
          <input id="saveBtn" type="button" style="position: fixed; right: 80px; top: 3px;display:none;" value="保存申请">
          <label id="msgLabel" style="position: fixed; right: 100px; top: 3px; color: red;display:none; line-height: 150%;">已提交流程，不可修改</label>
          <input id="auditBtn" type="button" style="position: fixed; right: 3px; top: 3px;display:none;" value="发送审批">
      </span>
    </div>
    <div id="centerContent" align="center" style="padding-top: 30px;">
      <t:formvalid formid="formobj" dialog="true" layout="table" action="tPmFinishApplyController.do?doAddUpdate" postonce="false" tiptype="1" callback="@Override saveCallback">
        <input id="id" name="id" type="hidden" value="${tPmFinishApplyPage.id }">
        <input id="projectId" name="project.id" type="hidden" value="${tPmFinishApplyPage.project.id }">
        <input id="createBy" name="createBy" type="hidden" value="${tPmFinishApplyPage.createBy }">
        <input id="createName" name="createName" type="hidden" value="${tPmFinishApplyPage.createName }">
        <input id="createDate" name="createDate" type="hidden" value="${tPmFinishApplyPage.createDate }">
        <input id="updateBy" name="updateBy" type="hidden" value="${tPmFinishApplyPage.updateBy }">
        <input id="updateName" name="updateName" type="hidden" value="${tPmFinishApplyPage.updateName }">
        <input id="updateDate" name="updateDate" type="hidden" value="${tPmFinishApplyPage.updateDate }">
        <input id="submitFlag" type="hidden" >
        <table style="width: 800px;" cellpadding="0" cellspacing="1" border="0" class="formtable">
          <caption>
            <strong style="font-size: xx-large;">科研项目结题申请表</strong>
          </caption>
          <tr>
          <tr>
        <td align="right">
          <label class="Validform_label"> 流程处理状态: </label>
        </td>
        <td class="value">
         <t:codeTypeSelect code="SPZT" codeType="1" id="auditStatusSelect" name="" type="select" defaultVal="${tPmFinishApplyPage.auditStatus }" extendParam="{'disabled':'true'}" ></t:codeTypeSelect>
         <input id="auditStatus" name="auditStatus" value="${tPmFinishApplyPage.auditStatus }" type="hidden" />
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">流程处理状态</label>
        </td>
        <td align="right">
              <label class="Validform_label"> 结题时间: </label>
            </td>
            <td width="150" class="value">
              <input id="finishDate" name="finishDate" type="text" style="width: 130px" class="Wdate" onClick="WdatePicker()"
                value='<fmt:formatDate value='${tPmFinishApplyPage.finishDate}' type="date" pattern="yyyy-MM-dd"/>'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">结题时间</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 项目名称: </label>
            </td>
            <td class="value" colspan="3">
              <input id="projectName" name="projectName" type="text" readonly="true" style="width: 630px" class="inputxt" value='${tPmFinishApplyPage.projectName}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目名称</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 项目来源: </label>
            </td>
            <td class="value" colspan="3">
              <input id="sourceUnit" name="sourceUnit" type="text" style="width: 630px" readonly="true" class="inputxt" value='${tPmFinishApplyPage.sourceUnit}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目来源</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 起止年度: </label>
            </td>
            <td class="value">
              <input id="beginYear" name="beginYear" type="text" style="width: 50px" readonly="true" class="inputxt" value='${tPmFinishApplyPage.beginYear}'>
              <span style="padding-left: 5px;">~</span>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">起始年度</label>
              <input id="endYear" name="endYear" type="text" style="width: 50px" readonly="true" class="inputxt" value='${tPmFinishApplyPage.endYear}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">截止年度</label>
            </td>
            <td width="90" align="right">
              <label class="Validform_label"> 经费性质: </label>
            </td>
            <td width="150" class="value">
              <input id="feeType" name="feeType" type="hidden" style="width: 130px" class="inputxt" value='${tPmFinishApplyPage.feeType}'>
              <input id="feeType" name="feeTypeName" type="text" readonly="true" style="width: 130px" class="inputxt" value='${tPmFinishApplyPage.feeTypeName}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">经费性质</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 研究单位: </label>
            </td>
            <td class="value">
              <input id="developerDepartId" name="developerDepartId" type="hidden" style="width: 150px" class="inputxt" value='${tPmFinishApplyPage.developerDepartId}'>
              <input id="developerDepartName" name="developerDepartName" type="text" readonly="true" style="width: 380px" class="inputxt" value='${tPmFinishApplyPage.developerDepartName}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">研究单位</label>
            </td>
            <td width="90" align="right">
              <label class="Validform_label"> 项目负责人: </label>
            </td>
            <td width="150" class="value">
              <input id="projectManagerId" name="projectManagerId" type="hidden" style="width: 150px" class="inputxt" value='${tPmFinishApplyPage.projectManagerId}'>
              <input id="projectManager" name="projectManager" type="text" readonly="true" style="width: 130px" class="inputxt" value='${tPmFinishApplyPage.projectManager}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目负责人</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 总经费: </label>
            </td>
            <td class="value">
              <input id="allFee" name="allFee" type="text" readonly="true" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" style="width: 380px; text-align: right;"
                value='${tPmFinishApplyPage.allFee}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">总经费</label>
            </td>
            <td width="90" align="right">
              <label class="Validform_label"> 剩余经费: </label>
            </td>
            <td width="150" class="value">
              <input id="extraFee" name="extraFee" type="text" data-options="min:0,precision:2,groupSeparator:','" style="width: 130px; text-align: right;" value='${tPmFinishApplyPage.extraFee}'>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">剩余经费</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 项目完成情况: </label>
              <font color="red">*</font>
            </td>
            <td class="value" colspan="3">
              <textarea id="projectFinishInfo" style="width: 630px;" datatype="byterange" max="800" min="1" maxlength="800" class="inputxt" rows="4" name="projectFinishInfo">${tPmFinishApplyPage.projectFinishInfo}</textarea>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">项目完成情况</label>
            </td>
          </tr>
          <tr>
            <td width="136" align="right">
              <label class="Validform_label"> 剩余经费处理意见: </label>
              <font color="red">*</font>
            </td>
            <td class="value" colspan="3">
              <textarea id="extraFeeSuggestion" style="width: 630px;" datatype="byterange" max="800" min="1" maxlength="800" class="inputxt" rows="4" name="extraFeeSuggestion">${tPmFinishApplyPage.extraFeeSuggestion}</textarea>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">剩余经费处理意见</label>
            </td>
          </tr>
          <tr>
              <td align="right">
                <label class="Validform_label"> 附件:&nbsp;&nbsp; </label>
              </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${tPmFinishApplyPage.attachmentCode }" id="bid" name="attachmentCode" />
                <table style="max-width: 360px;" id=fileShow>
                  <c:forEach items="${tPmFinishApplyPage.attachments}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                      </td>
                      <td style="width: 60px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload" buttonText="添加文件"  auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
		    	    formData="bid,projectId" uploader="commonController.do?saveUploadFilesToFTP&businessType=finishApply" ></t:upload>
                </div>
              </td>
            </tr>
        </table>
      </t:formvalid>
    </div>
  </div>
</body>
<script src="webpage/com/kingtake/project/finish/tPmFinishApply.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>