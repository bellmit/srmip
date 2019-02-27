<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>申报需求信息表</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function() {
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });
    
    if (($("#read").val() == '1'||$("#bpmStatus").val()=='2'||$("#bpmStatus").val()=='3'||$("#bpmStatus").val()=='4') && $("#planStatus").val()!='2') {
        $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
        $("textarea").attr("disabled", "disabled");
        $("select").attr("disabled", "disabled");
        $(".jeecgDetail").hide();
    }
  });
  
  function uploadFile(data) {
    tip(data.msg);
    if(data.success){
    	window.location.reload();
    }
  }

  //上传文件成功后刷新
  function refresh() {
    window.location.reload();
  }
  
  
//提交流程
  function submitProcess(){
	  var tableName = "t_b_pm_report_req";
      var url = 'tPmDeclareController.do?goSelectOperator2';
      //流程对应表单URL
     if(typeof(windowapi) == 'undefined'){
          $.dialog.confirm("确定提交流程吗？", function() {
              openOperatorDialog("选择审批人", url, 640, 180);
          }, function() {
          }).zindex();
      }else{
          W.$.dialog.confirm("确定提交流程吗？", function() {
              openOperatorDialog("选择审批人", url, 640, 180);
          }, function() {
          },windowapi).zindex();
      }
  }
  
  function submitAudit(nextUser){
	   var url = "tBLearningThesisController.do?startProcess";
	   var id =  $("#id").val();
	   var businessCode =  getBusinessCode();
	   var tableName = getTableName();
	   //流程对应表单URL
	   var formUrl = getFormUrl();
	   var businessName = getBusinessName();
	   var paramsData = {"id":id,"businessCode":businessCode,"formUrl":formUrl,"businessName":businessName,"tableName":tableName,"nextUser":nextUser};
	   $.ajax({
	       cache : false,
	       type : 'POST',
	       data : paramsData,
	       url : url,// 请求的action路径
	       success : function(data) {
	           var d = $.parseJSON(data);
	           tip(d.msg);
	           if (d.success) {
	               var msg = d.msg;
	               setTimeout(function(){
	                   window.location.reload();
	               },2000);
	           }
	       }
	   });
	}

	//打开选择人窗口
	function openOperatorDialog(title, url, width, height) {
	    var width = width ? width : 700;
	    var height = height ? height : 400;
	    if (width == "100%") {
	        width = window.top.document.body.offsetWidth;
	    }
	    if (height == "100%") {
	        height = window.top.document.body.offsetHeight - 100;
	    }

	    if (typeof (windowapi) == 'undefined') {
	        $.dialog({
	            content : 'url:' + url,
	            lock : true,
	            width : width,
	            height : height,
	            title : title,
	            opacity : 0.3,
	            cache : false,
	            ok : function() {
	                iframe = this.iframe.contentWindow;
	                var operator = iframe.getOperator();
	                if (operator == ""  || operator ==undefined) {
	                    return false;
	                }
	                submitAudit(operator);
	            },
	            cancelVal : '关闭',
	            cancel : true
	        }).zindex();
	    } else {
	        W.$.dialog({
	            content : 'url:' + url,
	            lock : true,
	            width : width,
	            height : height,
	            parent : windowapi,
	            title : title,
	            opacity : 0.3,
	            cache : false,
	            ok : function() {
	                iframe = this.iframe.contentWindow;
	                var operator = iframe.getOperator();
	                if (operator == "" || operator ==undefined) {
	                    return false;
	                }
	                submitAudit(operator);
	            },
	            cancelVal : '关闭',
	            cancel : true
	        }).zindex();
	    }
	}
   
   //获取表格名称
   function getTableName(){
       return "t_b_pm_report_req";
   }
   
   //业务名称
   function getBusinessName(){
       var projectName = $("#projectName").val();
       return "申报需求["+projectName+"]";
   }
   
   //业务代码
   function getBusinessCode(){
       return "reportReq";
   }
   
   function getFormUrl(){
       var projectId = $("#projectId").val();
       return "tBPmReportReqController.do?goAudit&projectId="+projectId;
   }
   
   function getId(){
       return $("#id").val();
   }

//查看流程
function viewHistory(processInstanceId, title) {
    goProcessHisTab(processInstanceId, title);
}

/**
 * 查看流程意见
 */
function viewRemark() {
    var processInsId = $("#processInsId").val();
    var url = "tPmDeclareController.do?goViewProcess&processInstId=" + processInsId;
    createdetailwindow("查看流程意见", url, null, null);
}

// 重新提交
function compeleteProcess() {
    var taskId = $("#taskId").val();
    var url;
    var data;
    if(taskId!=""){
        url="activitiController.do?processComplete";
        data={
            "taskId" : taskId,
            "nextCodeCount" : 1,
            "model" : '1',
            "reason" : "重新提交",
            "option" : "重新提交"
        };
    }else{
        url="tPmDeclareController.do?doResubmit";
        var id = $("#id").val();
        data={"declareId":id,"declareType":"reportReq"}
    }
    if(typeof(windowapi)=='undefined'){
        $.dialog.confirm('您确定已经修改完毕，重新提交吗?', function(r) {
            if (r) {
                $.ajax({
                    url : url,
                    type : "POST",
                    dataType : "json",
                    data : data,
                    async : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            window.location.reload();
                        }
                    }
                });
            }
        },function(){});
    }else{
        W.$.dialog.confirm('您确定已经修改完毕，重新提交吗?', function(r) {
            if (r) {
                $.ajax({
                    url : url,
                    type : "POST",
                    dataType : "json",
                    data : data,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            window.location.reload();
                        }
                    }
                });
            }
        },function(){},windowapi);
    }
    
}

//查看计划草案驳回修改意见
function viewMsgText(id){
    $.ajax({
       url:'tPmPlanDetailController.do?getMsgText',
       type:'POST',
       cache:false,
       data:{'id':id},
       dataType:'json',
       success:function(data){
           W.$.dialog({
              content: '<textarea id="msgTextArea" rows="5" cols="5" style="width: 327px; height: 188px;" readonly="true">'+data.msg+'</textarea>',
              lock : true,
              width:350,
              height:200,
              parent:windowapi,
              title:'查看修改意见',
              opacity : 0.3,
              cache:false,
              cancelVal: '关闭',
              cancel: true 
          }).zindex();
       }
    });
       
    }
</script>
</head>
<body>
<div style="height: 550px;margin:0px auto;">
    <div style="height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
      <table style="margin: 0 auto;width:100%;" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td align="center">
            <b>${tBPmReportReqPage.projectName }-申报需求信息</b>
          </td>
          <td width="30%">
            <c:if test="${(empty tBPmReportReqPage.bpmStatus or tBPmReportReqPage.bpmStatus eq '1' or tBPmReportReqPage.bpmStatus eq '5' or tBPmReportReqPage.planStatus eq '2') and opt ne 'audit'}">
              <a id="saveBtn" class="easyui-linkbutton" plain="true"  title="保存">保存 </a>
            </c:if>
            <c:if test="${tBPmReportReqPage.bpmStatus eq '1' and opt ne 'audit'}">
              <a id="auditBtn" class="easyui-linkbutton" plain="true"  title="提交审核" onclick="submitProcess();">提交审核 </a>
            </c:if>
            <c:if test="${tBPmReportReqPage.bpmStatus eq '5' && read ne '1' }">
              <a href="#" class="easyui-linkbutton" plain="true"  title="修改完成后重新提交流程" onclick="compeleteProcess();">修改提交</a>
              <a href="#" class="easyui-linkbutton" plain="true"  title="查看历史" onclick="viewRemark();">查看历史</a>
            </c:if>
            <c:if test="${tBPmReportReqPage.planStatus eq '2' && read ne '1' }">
                <a href="#" class="easyui-linkbutton" plain="true"  title="修改完成后重新提交" onclick="compeleteProcess();">修改提交</a>
                <a href="#" class="easyui-linkbutton" plain="true"  title="查看计划草案驳回意见" onclick="viewMsgText('${tBPmReportReqPage.id}');">查看意见</a>
            </c:if>
            <c:if test="${!empty tBPmReportReqPage.bpmStatus and tBPmReportReqPage.bpmStatus ne '1' and tBPmReportReqPage.bpmStatus ne '5' and tBPmReportReqPage.planStatus ne '2' and opt ne 'audit'}">
              <a id="viewBtn" class="easyui-linkbutton" plain="true"  onclick="viewHistory('${tBPmReportReqPage.processInstId }','申报需求审核流程');">查看流程</a>
            </c:if>
          </td>
          </tr>
          </table>
    </div>
    <br/>
  <br>
  <t:formvalid formid="formobj" dialog="true" layout="table" postonce="false" action="tBPmReportReqController.do?doUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBPmReportReqPage.id }">
    <input id="read" type="hidden" value="${read }">
    <input id="processInsId" type="hidden" value="${processInstId }" />
    <input id="projectId" name="projectId" type="hidden" value="${tBPmReportReqPage.projectId }">
    <input id="projectName" name="projectName" type="hidden" value="${tBPmReportReqPage.projectName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBPmReportReqPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBPmReportReqPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBPmReportReqPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBPmReportReqPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBPmReportReqPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBPmReportReqPage.updateDate }">
    <input id="reqNum" name="reqNum" type="hidden" value="${tBPmReportReqPage.reqNum }">
    <input id="processInsId" type="hidden" value="${processInstId }" />
    <input id="taskId" type="hidden" value="${taskId }" />
    <input id="read" type="hidden" value="${read }" />
    <table style="width: 600px;margin: 0 auto;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 项目名称: </label>
        </td>
        <td class="value">
          <input id="projectName" name="projectName" type="text" disabled="disabled" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.projectName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 起止时间: </label>
        </td>
        <td class="value">
          <input id="beginDate" name="beginDate" type="text" style="width: 165px" readonly="true" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBPmReportReqPage.beginDate}' type="date" pattern="yyyy-MM-dd"/>'>
          -
          <input id="endDate" name="endDate" type="text" style="width: 165px" readonly="true" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBPmReportReqPage.endDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">起始时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 申报单位: </label>
        </td>
        <td class="value">
          <input id="reportUnitName" name="reportUnitName" type="text" readonly="true" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.reportUnit.departname}'>
          <input id="reportUnit" name="reportUnit.id" type="hidden" value='${tBPmReportReqPage.reportUnit.id}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申报单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 责任单位: </label>
        </td>
        <td class="value">
          <input id="manageUnitName" name="manageUnitName" type="text" readonly="true" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.manageUnit.departname}'>
          <input id="manageUnit" name="manageUnit.id" type="hidden" value='${tBPmReportReqPage.manageUnit.id}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">责任单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 经费需求: </label>
        </td>
        <td class="value">
          <input id="feeReq" name="feeReq" type="text" style="width: 350px" readonly="true" class="inputxt" value='${tBPmReportReqPage.feeReq}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">经费需求</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 申请人: </label>
        </td>
        <td class="value">
          <input id="applicantor" name="applicantor" type="text" readonly="true" style="width: 350px" class="inputxt" value='${tBPmReportReqPage.applicantor}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">申请人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 流程处理状态: </label>
        </td>
        <td class="value">
         <input id="declareStatus" type="text" value="${declareStatus}" readonly="readonly">
         <input id="bpmStatus" name="bpmStatus" value="${tBPmReportReqPage.bpmStatus }" type="hidden" class="inputxt" />
         <input id="planStatus" name="planStatus" value="${tBPmReportReqPage.planStatus }" type="hidden" class="inputxt" />
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">流程处理状态</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">
            立项需求及
            <br>
            研究总体要求:
          </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <textarea id="researchReq" name="researchReq" type="text" datatype="*2-250" style="width: 350px; height: 80px;" class="inputxt">${tBPmReportReqPage.researchReq}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">立项需求及研究总体要求</label>
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBPmReportReqPage.attachments }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBPmReportReqPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid,projectId" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=reportReq" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/project/reportreq/tBPmReportReq.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>