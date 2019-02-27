<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<!DOCTYPE html>
<html>
<head>
<title>上报材料信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<style>
.btn-primary {
  color: #fff;
  background-color: #337ab7;
  border-color: #2e6da4;
}

.meetingTip{
color: red;
font-size: 25px;
}
</style>
</head>
<body>
<%
request.setAttribute("send", ApprovalConstant.APPRSTATUS_SEND);
request.setAttribute("unsend", ApprovalConstant.APPRSTATUS_UNSEND);
request.setAttribute("finish", ApprovalConstant.APPRSTATUS_FINISH);
request.setAttribute("rebut", ApprovalConstant.APPRSTATUS_REBUT);
%>
<c:if test="${!empty tBAppraisalReportMaterialPage.msgText and tBAppraisalReportMaterialPage.checkFlag eq rebut}">
    <div align="center"><img alt="已驳回" title="已驳回" src="plug-in/easyui/themes/icons/cancel.png"><span class="meetingTip"><a href="#" style="color: red; cursor: pointer; text-decoration: underline;" onclick="viewMsg()">查看修改意见</a></span></div>
    <textarea id="msgTextArea" rows="5" cols="5" style="display: none;" >${tBAppraisalReportMaterialPage.msgText}</textarea>
  </c:if>
     <c:if test="${tBAppraisalReportMaterialPage.checkFlag eq send }"><div align="center"><img alt="已提交" src="plug-in/easyui/themes/icons/ok.png"><span class="meetingTip">已提交</span></div></c:if>
     <c:if test="${tBAppraisalReportMaterialPage.checkFlag eq finish }"><div align="center"><img alt="已通过" src="plug-in/easyui/themes/icons/ok.png"><span class="meetingTip">已通过</span></div></c:if>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tBAppraisalReportMaterialController.do?doUpdate" tiptype="1" callback="@Override formCallback" btnsub="saveBtn">
    <input id="id" name="id" type="hidden" value="${tBAppraisalReportMaterialPage.id }">
    <input id="projectId" type="hidden" value="${projectId }">
    <input id="createBy" name="createBy" type="hidden" value="${tBAppraisalReportMaterialPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBAppraisalReportMaterialPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBAppraisalReportMaterialPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBAppraisalReportMaterialPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBAppraisalReportMaterialPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBAppraisalReportMaterialPage.updateDate}">
    <input id="checkFlag" name="checkFlag" type="hidden" value="${tBAppraisalReportMaterialPage.checkFlag}">
    <input id="msgText" name="msgText" type="hidden" value="${tBAppraisalReportMaterialPage.msgText}">
    <input id="applyId" name="applyId" type="hidden" value="${tBAppraisalReportMaterialPage.applyId}">
    <input id="submitFlag" type="hidden" >
    <fieldset>
    <legend>上传会议材料</legend>
    <table id="materiaTab" style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" width="100px">
          <label class="Validform_label"> 水&nbsp;平&nbsp;评&nbsp;价: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect name="levelEvaluation" type="select" codeType="1" code="SPPJ" id="levelEvaluation"  extendParam="{class:'inputxt','datatype':'*'}" defaultVal="${tBAppraisalReportMaterialPage.levelEvaluation}">
          </t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">水平评价</label>
        </td>
      </tr>
      <tr>
        <td align="right" rowspan="2" >
          <label class="Validform_label"> 会议材料附件: </label>
        </td>
        <td class="value">
          <input type="hidden" value="${tBAppraisalReportMaterialPage.attachmentCode }" id="bid" name="attachmentCode" />
          <table style="max-width: 360px;" id="fileShow1">
            <c:forEach items="${tBAppraisalReportMaterialPage.meetingMaterials}" var="file" varStatus="idx">
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
        </td>
     </tr>
     <tr>
        <td>
        <table>
          <tr>
            <td colspan="2">
              <div class="form jeecgDetail" id="filediv1"></div>
              <span id="file_upload1span"></span>
            </td>
          </tr>
          <tr>
            <td width="220px">
              <input type="file" name="fiels" id="file_upload1" />
            </td>
          </tr>
       </table>
      </td>
     </tr>
     <tr>
        <td class="value" colspan="3" >
          <label style="background: #F1F8FE">
                                      附件说明：
            <br/>
            1、请上传会议准备材料（会议须知，汇报PPT，国防科技报告，鉴定证书及相关文件）
            <br>
            2、上传鉴定证书和鉴定数据
          </label>
        </td>
     </tr>
     <tr>
      <td align="left" colspan="2" height="30px" style="padding-left: 20px;">&nbsp;</td>
     </tr>
    </table>
    </fieldset>
    <fieldset>
    <legend>证书信息</legend>
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" width="100px">
          <label class="Validform_label"> 批&nbsp;准&nbsp;时&nbsp;间: </label>
        </td>
        <td class="value">
          <input id="approveDate" name="approveDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBAppraisalReportMaterialPage.approveDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">批准时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 鉴定证书号: </label>
        </td>
        <td class="value">
          (
          <input id="certificateYear" name="certificateYear" type="text" style="width: 60px" value='${tBAppraisalReportMaterialPage.certificateYear}'>
          )
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定证书年度</label>
          <t:codeTypeSelect name="certificateFromUnit" type="select" codeType="1" code="ZSXDDW" id="certificateFromUnit" extendParam="{class:'inputxt'}" defaultVal="${tBAppraisalReportMaterialPage.certificateFromUnit}">
          </t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定证书下达机关</label>
          <label>鉴字第</label>
          <input id="certificateNumber" name="certificateNumber" type="text" style="width: 60px" class="inputxt" value='${tBAppraisalReportMaterialPage.certificateNumber}' datatype="n1-30">
          <label>号</label>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定证书号</label>
        </td>
      </tr>
      <tr>
        <td align="right" rowspan="2">
          <label class="Validform_label"> 上传鉴定证书: </label>
        </td>
        <td class="value">
          <table style="max-width: 360px;" id="fileShow2">
            <c:forEach items="${tBAppraisalReportMaterialPage.certificateScans}" var="file" varStatus="idx">
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
         </td>
       </tr>
       <tr>
         <td>
            <table>
              <tr >
                <td colspan="2">
                  <div class="form jeecgDetail" id="filediv2" ></div>
                </td>
             </tr>
             <tr>
                <td width="220px">
                  <span id="file_upload2span">
                    <input type="file" name="fiels" id="file_upload2" />
                  </span>
                </td>
              </tr>
            </table>
        </td>
      </tr>
      <tr>
        <td class="value" colspan="3">
          <lable style="background: #F1F8FE">
                             附件说明： 可上传正式鉴定证书扫描件
          </lable>
        </td>
      </tr>
      <tr>
        <td align="left" colspan="2" height="30px" style="padding-left: 20px;">&nbsp;</td>
      </tr>
    </table>
    </fieldset>
    <fieldset>
    <legend>证书领取信息</legend>
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 证书领取人: </label>
        </td>
        <td class="value">
          <input id="certificateReceiptor" name="certificateReceiptor" type="text" style="width: 150px" class="inputxt" value='${tBAppraisalReportMaterialPage.certificateReceiptor}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">证书领取人</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 证书领取时间: </label>
        </td>
        <td class="value">
          <input id="receiptorReceiveDate" name="receiptorReceiveDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBAppraisalReportMaterialPage.receiptorReceiveDate}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">证书领取时间</label>
        </td>
      </tr>
    </table>
    </fieldset>
    <div align="center" style="margin-top: 10px;">
    <c:if test="${empty tBAppraisalReportMaterialPage.checkFlag or tBAppraisalReportMaterialPage.checkFlag eq unsend or tBAppraisalReportMaterialPage.checkFlag eq rebut }">
    <a id="saveBtn" class="easyui-linkbutton" >保存</a>
    </c:if>
     <c:if test="${!empty tBAppraisalReportMaterialPage.id and tBAppraisalReportMaterialPage.checkFlag eq unsend or tBAppraisalReportMaterialPage.checkFlag eq rebut}">
    <a id="subBtn" class="easyui-linkbutton" onclick="submitProcess()" >提交</a>
    </c:if>
    </div>
  </t:formvalid>
  <script type="text/javascript">
  
  $(function(){
		//如果页面是详细查看页面
				var localUrl = location.href;
	      if(localUrl.indexOf("load=detail")!=-1){
	    	//无效化所有表单元素，只能进行查看
	    	$("input").attr("disabled","true");
	  		$(".inputxt").attr("disabled","true");
	  		$(".Wdate").attr("disabled","true");
	  		//隐藏选择框和清空框
	  		$("a[icon='icon-search']").css("display","none");
	  		$("a[icon='icon-redo']").css("display","none");
	  		//隐藏下拉框箭头
	  		$(".combo-arrow").css("display","none");
	  		//隐藏添加附件
	  		$("#filediv").parent().css("display","none");
	  		//隐藏附件的删除按钮
	   		$(".jeecgDetail").parent().css("display","none");
	  		$("input[type='button']").removeAttr("disabled");
	      }
		
		//编辑时审批已处理：提示不可编辑
	    if(localUrl.indexOf("tip=true") != -1){
	      var msg = $("#tabMsg").val();
	      tip(msg);
	    } 
		
		var checkFlag = $("#checkFlag").val();
		/* if(checkFlag=='1'||checkFlag=='2'){
		    $("#materiaTab input").attr("disabled",'true');
		    $("#materiaTab select").attr("disabled",'true');
		    $("#materiaTab .jeecgDetail").parent().css("display","none");
		}
		if(checkFlag=='3'||checkFlag=='4'){
		  //无效化所有表单元素，只能进行查看
	  		$(".inputxt").attr("disabled","true");
	  		$("input").attr("disabled","true");
	  		$(".Wdate").attr("disabled","true");
	  		//隐藏选择框和清空框
	  		$("a[icon='icon-search']").css("display","none");
	  		$("a[icon='icon-redo']").css("display","none");
	  		//隐藏下拉框箭头
	  		$(".combo-arrow").css("display","none");
	  		//隐藏添加附件
	  		$("#filediv").parent().css("display","none");
	  		//隐藏附件的删除按钮
	   		$(".jeecgDetail").parent().css("display","none");
	  		$("input[type='button']").removeAttr("disabled");
		} */
		
		initUpload();//初始化附件
	});

function subMeeting(id,operator,userId,departId){
	$.ajax({
		url:'tBAppraisalReportMaterialController.do?doSubmit',
		data:{'id':id,'operator':operator,'userId':userId,'departId':departId},
		type:'post',
		cache:false,
		dataType:'json',
		success:function(data){
			if(data.success){
				window.location.reload();
			}
		}
	});
}

function submitProcess(){
	$("#submitFlag").val("1");
	$("#saveBtn").click();
}

//提交流程
function startProcess(){
  var id = $("#id").val();
    //流程对应表单URL
    var url = 'tPmDeclareController.do?goSelectOperator2';
   if(typeof(windowapi) == 'undefined'){
        $.dialog.confirm("确定提交上报材料吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180,id);
        }, function() {
        }).zindex();
    }else{
        W.$.dialog.confirm("确定提交上报材料吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180,id);
        }, function() {
        },windowapi).zindex();
    }
}	

//打开选择人窗口
function openOperatorDialog(title,url,width,height,id){
	    var width = width?width:700;
	  	var height = height?height:400;
	  	if(width=="100%"){
	  		width = window.top.document.body.offsetWidth;
	  	}
	  	if(height=="100%"){
	  		height =window.top.document.body.offsetHeight-100;
	  	}
	  	
	  	if(typeof(windowapi) == 'undefined'){
	  		$.dialog({
	  			content: 'url:'+url,
	  			lock : true,
	  			width:width,
	  			height:height,
	  			title:title,
	  			opacity : 0.3,
	  			cache:false,
	  		    ok: function(){
	  		      iframe = this.iframe.contentWindow;
			    	var operator = iframe.getRealName();
			    	var userId = iframe.getUserId();
			    	var departId = iframe.getDepartId();
			    	if(operator==""){
			    	    return false;
			    	}
			    	subMeeting(id,operator,userId,departId);
	  		    },
	  		    cancelVal: '关闭',
	  		    cancel: true 
	  		}).zindex();
	  	}else{
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
			    	var operator = iframe.getRealName();
			    	var userId = iframe.getUserId();
			    	var departId = iframe.getDepartId();
			    	if(operator==""){
			    	    return false;
			    	}
			    	subMeeting(id,operator,userId,departId);
	  		    },
	  		    cancelVal: '关闭',
	  		    cancel: true 
	  		}).zindex();
	  	}
	  }
	  
//查看修改意见
function viewMsg(){
  var msgText = $("#msgTextArea").val();
  $.messager.alert("修改意见",msgText);
}


function initUpload(){
	$('#file_upload1').uploadify({
	    buttonText : '浏览',
	    auto : true,
	    progressData : 'speed',
	    multi : true,
	    height : 25,
	    width : 100,
	    overrideEvents : [ 'onDialogClose' ],
	    fileTypeDesc : '文件格式:',
	    queueID : 'filediv1',
	    fileTypeExts : '*.*;',
	    fileSizeLimit : '500MB',
	    swf : 'plug-in/uploadify/uploadify.swf',
	    uploader : 'commonController.do?saveUploadFilesToFTP&businessType=meetingMaterial',
	    onUploadStart : function(file) {
	      var bid = $('#bid').val();
	      var projectId = $('#projectId').val(); 
	      $('#file_upload1').uploadify("settings", "formData", {
	        'bid' : bid,        
	        'projectId':projectId
	      });
	    },
	    onQueueComplete : function(queueData) {
	    },
	    onUploadSuccess : function(file, data, response) {
	        var d = $.parseJSON(data);
	        updateUploadSuccessTable("fileShow1",d,file,response);
	    },
	    onFallback : function() {
	      tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");
	    },
	    onSelectError : function(file, errorCode, errorMsg) {
	      switch (errorCode) {
	        case -100:
	          tip("上传的文件数量已经超出系统限制的" + $('#file_upload1').uploadify('settings', 'queueSizeLimit') + "个文件！");
	          break;
	        case -110:
	          tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload1').uploadify('settings', 'fileSizeLimit') + "大小！");
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

	  $('#file_upload2').uploadify({
	    buttonText : '浏览',
	    auto : true,
	    progressData : 'speed',
	    multi : true,
	    height : 25,
	    width : 100,
	    overrideEvents : [ 'onDialogClose' ],
	    fileTypeDesc : '文件格式:',
	    queueID : 'filediv2',
	    fileTypeExts : '*.*;',
	    fileSizeLimit : '500MB',
	    swf : 'plug-in/uploadify/uploadify.swf',
	    uploader : 'commonController.do?saveUploadFilesToFTP&businessType=certificateScan',
	    onUploadStart : function(file) {
	      var bid = $('#bid').val();
	      var projectId = $('#projectId').val();
	      $('#file_upload2').uploadify("settings", "formData", {
	        'bid' : bid,
	        'projectId':projectId
	      });
	    },
	    onQueueComplete : function(queueData) {
	    },
	    onUploadSuccess : function(file, data, response) {
	        var d = $.parseJSON(data);
	        updateUploadSuccessTable("fileShow2",d,file,response);
	    },
	    onFallback : function() {
	      tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");
	    },
	    onSelectError : function(file, errorCode, errorMsg) {
	      switch (errorCode) {
	        case -100:
	          tip("上传的文件数量已经超出系统限制的" + $('#file_upload2').uploadify('settings', 'queueSizeLimit') + "个文件！");
	          break;
	        case -110:
	          tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload2').uploadify('settings', 'fileSizeLimit') + "大小！");
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
}

function formCallback(data) {
	var submitFlag = $("#submitFlag").val();
    if(data.success){
    	 if(submitFlag=="1"){
			 startProcess();
    	     //window.location.reload();
		 }else{
    	   $.messager.alert('提示',data.msg,'info',function(){
    	      window.location.reload();
    	   });
		 }
    }else{
    	$.messager.alert('提示',data.msg);
    }
}

</script>
<script src="webpage/com/kingtake/project/reportmaterial/tBAppraisalReportMaterial.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>
</html>