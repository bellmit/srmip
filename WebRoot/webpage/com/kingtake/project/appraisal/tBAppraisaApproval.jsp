<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>

<!DOCTYPE html>
<html>
<head>
<title>T_B_APPRAISA_APPROVAL</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script> -->
</head>
<body>
<%
request.setAttribute("unappr", ApprovalConstant.APPRSTATUS_UNAPPR);
request.setAttribute("unsend", ApprovalConstant.APPRSTATUS_UNSEND);
request.setAttribute("apprstatus_send", ApprovalConstant.APPRSTATUS_SEND);
request.setAttribute("finish", ApprovalConstant.APPRSTATUS_FINISH);
request.setAttribute("rebut", ApprovalConstant.APPRSTATUS_REBUT);
%>
<script type="text/javascript">
    $(function(){
      //如果页面是详细查看页面（这个是tab页）
      var localUrl = $('#localPanelUrl').val();
      if(location.href.indexOf("load=detail")!=-1||localUrl.indexOf("load=detail")!=-1){
        //无效化所有表单元素，只能进行查看
        $(":input").attr("disabled","true");
        //隐藏选择框和清空框
        $("a[icon='icon-search']").css("display","none");
        $("a[icon='icon-redo']").css("display","none");
        //隐藏下拉框箭头
        $(".combo-arrow").css("display","none");
        //隐藏添加附件
        $("#filediv").parent().css("display","none");
        //隐藏附件的删除按钮
        $(".jeecgDetail").parent().css("display","none");
        $("#formobj .easyui-linkbutton").css("display","none");
        //将发送按钮设为可点
        $(".sendButton").removeAttr("disabled");
        //将操作按钮设为可操作
        $("#finishBtn").removeAttr("disabled");
      }
      
    //编辑时审批已处理：提示不可编辑
    if(localUrl.indexOf("tip=true") != -1){
      var msg = $("#tabMsg").val();
      tip(msg);
    } 
    
  //加载项目类型
    $("#projectCharacter").combotree({
	    url : 'tPmProjectController.do?getProjectType',
		valueField : 'id',
		textField : 'projectTypeName',
		multiple:true,
		onLoadSuccess : function() {
			var projectType = $("#projectCharacter_hidden").val();
			if (projectType != "") {
				$("#projectCharacter").combotree('setValues', projectType.split(","));
			} 
		}
	}); 
  
    });
    
    function reloadCurrentPage(){
		$("#menu").tree('reload');
		$("#Validform_msg").remove();
	}
    
    //课题组长审批
    function researchAudit(apprId){
        var apprUrl = 'tPmApprLogsController.do?goUpdate&id=' + apprId;
        createChildWindow2('审批', apprUrl, 450, 230, windowapi);
    }
    </script>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAppraisaApprovalController.do?doAddUpdate" tiptype="1" callback="@Override uploadFile" btnsub="saveBtn">
    <c:if test="${empty tBAppraisaApprovalPage.auditStatus or tBAppraisaApprovalPage.auditStatus eq unsend or tBAppraisaApprovalPage.auditStatus eq rebut}">
      <input id="saveBtn" value="保存" type="button" style="position: fixed; right: 50;">
    </c:if>
<%--     <c:if test="${tBAppraisaApprovalPage.auditStatus eq finish}"> --%>
<!--       <input id="finishBtn" value="取消完成" type="button"  onclick="updateApprovalFinishFlag()" style="position: fixed; right: 50;"/> -->
<%--     </c:if> --%>
    <div style="margin-left: 120px;">
      <input id="id" name="id" type="hidden" value="${tBAppraisaApprovalPage.id }">
      <input id="projectId" type="hidden" value='${projectId}'>
      <input id="tBId" name="appraisalProject.id" type="hidden" value='${tBAppraisaApprovalPage.appraisalProject.id}'>
      <table style="width: 710px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
          <td align="right">
            <label class="Validform_label"> 成果名称: </label>
          </td>
          <td class="value" colspan="3">
            <input id="projectName" type="hidden" style="width: 510px;" class="inputxt" readonly="readonly" value='${tBAppraisaApprovalPage.appraisalProject.projectName}'>
            <input id="achievementName" type="text" style="width: 510px;" class="inputxt" readonly="readonly" value='${tBAppraisaApprovalPage.appraisalProject.achievementName}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">成果名称</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 申请单位: </label>
          </td>
          <td class="value" colspan="3">
            <input id="approvalUnitName" type="text" style="width: 510px;" class="inputxt" readonly="readonly" value='${tBAppraisaApprovalPage.appraisalProject.planUnitname}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">申请单位</label>
          </td>
        </tr>
        <%-- <tr>
          <td align="right">
            <label class="Validform_label"> 基层编号: </label>
          </td>
          <td class="value" colspan="3">
            <input id="basicNum" name="basicNum" type="text" style="width: 360px;" datatype="*" class="inputxt" readonly="readonly" value='${tBAppraisaApprovalPage.basicNum}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">基层编号</label>
            <a class="easyui-linkbutton" href="javascript:getSerialNum();" icon="icon-edit">获取基层编号</a>
          </td>
        </tr> --%>
        <tr>
          <td align="right">
            <label class="Validform_label"> 项目类型: </label>
          </td>
          <td class="value" colspan="3">
            <input id="projectCharacter_hidden" type="hidden" value="${tBAppraisaApprovalPage.projectCharacter}" >
            <input id="projectCharacter" name="projectCharacter" style="width: 516px;">
            <%-- <t:codeTypeSelect name="projectCharacter" type="checkbox" codeType="1" code="CGJDXMXZ" id="projectCharacter" extendParam="{datatype:*}" defaultVal="${tBAppraisaApprovalPage.projectCharacter}"></t:codeTypeSelect> --%>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">项目类型</label>
          </td>
        </tr>
<%--         <tr>
          <td align="right">
            <label class="Validform_label"> 鉴定条件: </label>
          </td>
          <td class="value" colspan="3">
            <t:codeTypeSelect name="appraisaCondition" type="radio" codeType="1" code="JDTJ" id="appraisaCondition" defaultVal="${tBAppraisaApprovalPage.appraisaCondition}" extendParam="{datatype:*}"></t:codeTypeSelect>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">鉴定条件</label>
          </td>
        </tr> --%>
        <tr>
          <td align="right">
            <label class="Validform_label"> 起止时间: </label>
          </td>
          <td class="value">
            <input readonly="readonly" type="text" style="width: 150px" value='${tBAppraisaApprovalPage.appraisalProject.projectBeginEndDate}'>
            <input type="hidden" name="beginTime" value="${tBAppraisaApprovalPage.appraisalProject.projectBeginDate}">
            <input type="hidden" name="endTime" value="${tBAppraisaApprovalPage.appraisalProject.projectEndDate}">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">开始时间</label>
          </td>
          <td align="right">
            <label class="Validform_label"> 总&nbsp;经&nbsp;费&nbsp;: </label>
          </td>
          <td class="value">
            <input id="totalAmount" name="totalAmount" type="text" style="width: 135px; text-align: right;" class="easyui-numberbox" data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
              value='${tBAppraisaApprovalPage.totalAmount}'>元
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">总经费</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 鉴定时间: </label>
          </td>
          <td class="value">
            <input id="appraisaTime" name="appraisaTime" type="text" style="width: 150px" readonly="readonly"
              value='<fmt:formatDate value='${tBAppraisaApprovalPage.appraisalProject.appraisalTime}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">鉴定时间</label>
          </td>
          <td align="right">
            <label class="Validform_label"> 鉴定地点: </label>
          </td>
          <td class="value">
            <input id="appraisaAddress" name="appraisaAddress" type="text" style="width: 150px" readonly="readonly" class="inputxt" value='${tBAppraisaApprovalPage.appraisalProject.appraisalAddress}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">鉴定地点</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: </label>
          </td>
          <td class="value">
            <input id="contactUsername" name="contactUsername" type="text" style="width: 150px" readonly="readonly" class="inputxt"
              value='${tBAppraisaApprovalPage.appraisalProject.projectManagerName}'>
            <input id="contactUserid" name="contactUserid" type="hidden" value='${tBAppraisaApprovalPage.appraisalProject.projectManagerId}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系人</label>
          </td>
          <td align="right">
            <label class="Validform_label"> 联系电话: </label>
          </td>
          <td class="value">
            <input id="contactPhone" name="contactPhone" type="text" style="width: 150px" readonly="readonly" class="inputxt" value='${tBAppraisaApprovalPage.appraisalProject.projectManagerPhone}'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">联系电话</label>
          </td>
        </tr>
        <tr>
          <td align="right">
            <label class="Validform_label"> 附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件: </label>
          </td>
          <td class="value" colspan="3">
            <input type="hidden" value="${tBAppraisaApprovalPage.id}" id="bid" name="bid" />
            <table style="max-width: 82%;">
              <c:forEach items="${tBAppraisaApprovalPage.certificates}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td>
                    <a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBAppraisaApprovalPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
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
              <t:upload name="fiels" id="file_upload" dialog="false" callback="reloadCurrentPage" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid,projectId"
                uploader="commonController.do?saveUploadFiles&businessType=tBAppraisaApproval"></t:upload>
            </div>
          </td>
        </tr>
      </table>
      <!-- 修改日志表用 -->
      <input id="sendIds" name="sendIds" type="hidden">
    </div>
  </t:formvalid>
  <div id="tab-tools" >
    <c:if test="${isAppr eq '1'}">
      <a id="auditBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'"  onclick="researchAudit('${receiveId}')" >课题组长审批</a>
    </c:if>
    <c:if test="${tBAppraisaApprovalPage.auditStatus eq apprstatus_send}">
      <a id="finishBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'"  onclick="confirmFinish()" >完成审批</a>
    </c:if>
  </div>
  <c:if test="${tBAppraisaApprovalPage.id ne ''}">
  <div style="width: auto; height: 200px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false" tools="#tab-tools">
				<t:tab
					href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${tBAppraisaApprovalPage.id}&apprType=${apprType}&send=${send}&idFlag=${idFlag}" 
					icon="icon-search" title="审核信息" id="apprLogs" width="700"></t:tab>
			</t:tabs>
	 </div>
  </c:if>
   <script type="text/javascript">
   function getSerialNum(){
		  $.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : 'tBAppraisaApprovalController.do?getApprApprovalNum',// 请求的action路径
				success : function(data) {
					var d = $.parseJSON(data).obj;
					var year = d.substr(0,4);
					var num = d.substr(4,7);
					$('#basicNum').val('HJ-'+'502-'+year+'-'+num);
				}
			});
	}
   
   function uploadFile(data){
		$("#bid").val(data.obj);
		if($(".uploadify-queue-item").length>0){
			upload();
		}else{
			$("#menu").tree('reload');
			$("#Validform_msg").remove();
// 			frameElement.api.close();
		}
	}
   
 //-------4：完成流程-------
 function confirmFinish(){
     if (typeof (windowapi) == 'undefined') {
			$.dialog.confirm("确定该鉴定申请审批流程完成吗？", function(){
			    updateApprovalFinishFlag();
     	}, function(){
     	}).zindex();
		}else{
			W.$.dialog.confirm("确定该鉴定申请审批流程完成吗？", function(){
			    updateApprovalFinishFlag();
     	}, function(){
     	},windowapi).zindex();
		}
 }
   function updateApprovalFinishFlag(){
	 var id=$('#id').val();
	 var url1 = 'tBAppraisaApprovalController.do?updateFinishFlag';
	 var url2 = 'tBAppraisaApprovalController.do?updateFinishFlag2';
   	$.ajax({
   		cache : false,
   		type : 'POST',
   		url : url1,
   		data : {"id":id},
   		success : function(data) {
   			var d = $.parseJSON(data);
   			if(d.success){
   				tip(d.msg);
   				$("#menu").tree('reload');
//    				reloadTable();
//    				loadButton();
   				return;
   			}else{
   				//处理失败有两种情况
   				if(d.obj == "1"){
   					//失败情况一:还有有效、未处理的审批记录，提示是否确定完成
   					if(typeof(windowapi)=='undefined'){
   					$.dialog.confirm(d.msg, function(){
   						$.ajax({
   							cache : false,
   							type : 'POST',
   							url : url2,
   							data : {"id":id},
   							success : function(data) {
   								var d = $.parseJSON(data);
   								tip(d.msg);
   								$("#menu").tree('reload');
//    								reloadTable();
//    								loadButton();
   								return;
   							}
   						});
   		        	}, function(){
   		        	}).zindex();
   					}else{
   					 W.$.dialog.confirm(d.msg, function(){
    						$.ajax({
    							cache : false,
    							type : 'POST',
    							url : url2,
    							data : {"id":id},
    							success : function(data) {
    								var d = $.parseJSON(data);
    								tip(d.msg);
    								$("#menu").tree('reload');
//     								reloadTable();
//     								loadButton();
    								return;
    							}
    						});
    		        	}, function(){
    		        	},windowapi).zindex();
   					}
   				}else if(d.obj == "2"){
   					//失败情况二：流程状态被其他操作改变：提示刷新重新操作
   					$.dialog.alert(d.msg);
   				}
   			}
   		}
   	});
   }
   
   function refreshCurrentPanel(tBAppraisalProjectId){
	   $.ajax({
		   async:false,
		   cache:false,
		   type:'post',
		   data:{'tBAppraisalProjectId':tBAppraisalProjectId},
		   url:'tBAppraisalProjectController.do?getApprovalOperateStatus',
			 success:function(data){
				 var d = $.parseJSON(data).obj;
			 }
	   })
   }
		
	function close(){
		frameElement.api.close();
	}
   </script>
<!-- <script src="webpage/com/kingtake/project/appraisal/tBAppraisaApproval.js"></script> -->

<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
</body>