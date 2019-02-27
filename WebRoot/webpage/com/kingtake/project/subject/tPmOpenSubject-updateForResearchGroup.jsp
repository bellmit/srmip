<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@include file="/webpage/com/kingtake/common/flow.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>开题报告</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  $(function() {
    $("#saveBtn").click(function() {
      $("#btn_sub").click();
    });
    
    if ($("#read").val() == '1'||$("#bpmStatus").val()=='2'||$("#bpmStatus").val()=='3'||$("#bpmStatus").val()=='4') {
        $("input").attr("disabled", "disabled").css("background-color", "#EBEBE4");
        $("textarea").attr("disabled", "disabled");
        $("select").attr("disabled", "disabled");
        $(".jeecgDetail").hide();
    }
    
  });
  
    
	function uploadFile(data) {
		var submitFlag = $("#submitFlag").val();
	    if(submitFlag=="1"){
	    	if(data.success){
	    		$("#submitFlag").val("");
	    		submitProcess();
	    	}else{
	           $.messager.alert('提示',data.msg);
	    	}
	    }else{
	    	if (data.success) {
				window.location.reload();
				tip(data.msg);
			}
	    }				
	}

	//上传文件成功后刷新
	function refresh() {
		window.location.reload();
	}
	
	function submitProcessAndSave(){
		$("#submitFlag").val("1");	
		$("#saveBtn").click();
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
		                if (operator == "" || operator ==undefined) {
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
	function getTableName() {
		return "t_b_pm_open_subject";
	}

	//业务名称
	function getBusinessName() {
		var projectName = $("#projectName").val();
		return "开题报告[" + projectName + "]";
	}

	//业务代码
	function getBusinessCode() {
		return "openSubject";
	}

	//表单url
	function getFormUrl() {
		var projectId = $("#projectId").val();
		return "tPmOpenSubjectController.do?goAudit&projectId=" + projectId;
	}

	function getId() {
		return $("#id").val();
	}
</script>
</head>
<body>
  <%-- <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
    <h1 align="center">${tPmOpenSubjectPage.project.projectName }-开题报告</h1>
    <span>
      <input id="saveBtn" type="button" style="position: fixed; right: 5px; top: 3px;" value="保存开题报告">
    </span>
  </div> --%>
  <input id="submitFlag" type="hidden" > 
  <div style="height: 450px;margin:0px auto;">
  <div style="height: 30px; width: 100%; background: #E5EFFF; border-bottom: solid 1px #95B8E7;">
      <table style="margin: 0 auto;width:100%;" cellpadding="0" cellspacing="0" border="0">
        <tr>
          <td align="center">
            <b>${tPmOpenSubjectPage.project.projectName }-开题报告</b>
          </td>
          <td width="25%">
            <c:if test="${(empty tPmOpenSubjectPage.bpmStatus or tPmOpenSubjectPage.bpmStatus eq '1' or tPmOpenSubjectPage.bpmStatus eq '5') and opt ne 'audit'}">
              <a id="saveBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px; " title="保存">保存 </a>
            </c:if>
            <c:if test="${tPmOpenSubjectPage.bpmStatus eq '1' and opt ne 'audit'}">
              <a id="auditBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px; " title="提交审核" onclick="submitProcessAndSave();">提交审核 </a>
            </c:if>
            <c:if test="${tPmOpenSubjectPage.bpmStatus eq '5' && read ne '1' }">
              <a href="#" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="修改完成后重新提交流程" onclick="compeleteProcess();">修改提交</a>
              <a href="#" class="easyui-linkbutton" plain="true" style="border: solid 1px;" title="查看历史" onclick="viewRemark();">查看历史</a>
            </c:if>
            <c:if test="${!empty tPmOpenSubjectPage.bpmStatus and tPmOpenSubjectPage.bpmStatus ne '1' and tPmOpenSubjectPage.bpmStatus ne '5' and opt ne 'audit'}">
              <a id="viewBtn" class="easyui-linkbutton" plain="true" style="border: solid 1px;" onclick="viewHistory('${processInstId }','开题报告审核流程');">查看流程</a>
            </c:if>
          </td>
          </tr>
          </table>
    </div>
  <br>
  <br>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" postonce="false" action="tPmOpenSubjectController.do?doUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tPmOpenSubjectPage.id }">
    <input id="projectId" name="tpId" type="hidden" value="${tPmOpenSubjectPage.project.id}">
    <input id="projectName" type="hidden" value="${tPmOpenSubjectPage.project.projectName }">
    <input id="createBy" name="createBy" type="hidden" value="${tPmOpenSubjectPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tPmOpenSubjectPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tPmOpenSubjectPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tPmOpenSubjectPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tPmOpenSubjectPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tPmOpenSubjectPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td style="width: 25%" align="right">
          <label class="Validform_label"> 关键技术指标: </label>
        </td>
        <td style="width: 75%" class="value">
          <textarea id="technicalIndicator" style="width: 80%;" datatype="*0-800" maxlength="800" class="inputxt" rows="6" name="technicalIndicator">${tPmOpenSubjectPage.technicalIndicator}</textarea>
          <spanclass="Validform_checktip"> </span> <label class="Validform_label" style="display: none;">关键技术指标</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 主要研究内容: </label>
        </td>
        <td class="value">
          <textarea id="researchContents" style="width: 80%;" maxlength="800" class="inputxt" rows="6" name="researchContents">${tPmOpenSubjectPage.researchContents}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主要研究内容</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 流程处理状态: </label>
        </td>
        <td class="value">
         <t:dictSelect field="bpm_status" type="text" defaultVal="${tPmOpenSubjectPage.bpmStatus }" typeGroupCode="bpm_status" extendJson="{'readonly':'readonly'}" hasLabel="false"></t:dictSelect>
         <input id="bpmStatus" value="${tPmOpenSubjectPage.bpmStatus }" type="hidden" class="inputxt" />
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">流程处理状态</label>
        </td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
        <td class="value"><input type="hidden" value="${tPmOpenSubjectPage.attachmentCode}" id="bid" name="attachmentCode">
          <table style="max-width: 92%;" id="fileShow">
            <c:forEach items="${tPmOpenSubjectPage.attachments}" var="file" varStatus="idx">
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
		    	formData="bid,projId" uploader="commonController.do?saveUploadFilesToFTP&businessType=openSubject" >
            </t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
  </div>
</body>
<script src="webpage/com/kingtake/project/subject/tPmOpenSubject.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
</html>