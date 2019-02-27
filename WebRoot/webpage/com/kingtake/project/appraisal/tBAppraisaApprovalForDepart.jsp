<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>T_B_APPRAISA_APPROVAL</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
//编写自定义JS代码
$(function(){
	//编辑时审批已处理：提示不可编辑
	if(location.href.indexOf("tip=true") != -1){
		var parent = frameElement.api.opener;
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	}
});

</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
    action="tBAppraisaApprovalController.do?doAddUpdate" tiptype="1" callback="@Override uploadFile">
    <script type="text/javascript">
	    //如果页面是详细查看页面（这个是tab页）
	    if(location.href.indexOf("load=detail")!=-1){
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
	    	//将发送蛇皮设为可点
	    	$(".sendButton").removeAttr("disabled");
	    }
    </script>
    <div style="margin-left:120px;">
	    <input id="id" name="id" type="hidden" value="${tBAppraisaApprovalPage.id }">
	    <input id="projectId" type="hidden" value='${projectId}'>
      <input id="tBId" name="appraisalProject.id" type="hidden" value='${tBAppraisaApprovalPage.appraisalProject.id}'>
	    <table style="width:710px;" cellpadding="0" cellspacing="1" class="formtable">
	      <tr>
	        <td align="right"><label class="Validform_label"> 项目名称: </label></td>
	        <td class="value" colspan="3"><input id="projectName"  type="text" style="width: 510px;" 
	          class="inputxt" readonly="readonly" value='${tBAppraisaApprovalPage.appraisalProject.projectName}'> <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">项目名称</label></td>
	      </tr>
	      <tr>
	        <td align="right"><label class="Validform_label"> 申请单位: </label></td>
	        <td class="value" colspan="3"><input id="approvalUnitName"  type="text" style="width: 510px;"
	          class="inputxt" readonly="readonly" value='${tBAppraisaApprovalPage.appraisalProject.planUnitname}'> <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">申请单位</label></td>
	      </tr>
	      <tr>
	        <td align="right"><label class="Validform_label"> 项目性质: </label></td>
	        <td class="value" colspan="3">
	          <t:codeTypeSelect name="projectCharacter" type="radio" codeType="1" 
	          	code="CGJDXMXZ" id="projectCharacter" extendParam="{datatype:*}" 
	          	defaultVal="${tBAppraisaApprovalPage.projectCharacter}" ></t:codeTypeSelect> 
	          <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">项目性质</label>
	        </td>
	      </tr>
<%-- 	      <tr>
	        <td align="right"><label class="Validform_label"> 鉴定条件: </label></td>
	        <td class="value" colspan="3">
	          <t:codeTypeSelect
	            name="appraisaCondition" type="radio" codeType="1" code="JDTJ" id="appraisaCondition"
	            defaultVal="${tBAppraisaApprovalPage.appraisaCondition}" extendParam="{datatype:*}"></t:codeTypeSelect> <span
	          class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">鉴定条件</label>
	        </td>
	      </tr> --%>
	      <tr>
	        <td align="right"><label class="Validform_label"> 起止时间: </label></td>
	        <td class="value"><input readonly="readonly" type="text" style="width: 150px" 
	          value='${tBAppraisaApprovalPage.appraisalProject.projectBeginEndDate}' >
	          <input type="hidden" name="beginTime" value="${tBAppraisaApprovalPage.appraisalProject.projectBeginDate}">
	          <input type="hidden" name="endTime" value="${tBAppraisaApprovalPage.appraisalProject.projectEndDate}">
	          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">开始时间</label></td>
	        <td align="right"><label class="Validform_label"> 总&nbsp;经&nbsp;费&nbsp;: </label></td>
	        <td class="value"><input id="totalAmount" name="totalAmount" type="text" style="width:135px; text-align:right;" class="easyui-numberbox"
				data-options="min:0,max:99999999.99,precision:2,groupSeparator:','" value='${tBAppraisaApprovalPage.totalAmount}'>元 <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">总经费</label></td>
	      </tr>
	      <tr>
	        <td align="right"><label class="Validform_label"> 鉴定时间: </label></td>
	        <td class="value"><input id="appraisaTime" name="appraisaTime" type="text" style="width: 150px" readonly="readonly"
	          value='<fmt:formatDate value='${tBAppraisaApprovalPage.appraisalProject.appraisalTime}' type="date" pattern="yyyy-MM-dd"/>'>
	          <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">鉴定时间</label></td>
	        <td align="right"><label class="Validform_label"> 鉴定地点: </label></td>
	        <td class="value"><input id="appraisaAddress" name="appraisaAddress" type="text" style="width: 150px" readonly="readonly"
	          class="inputxt" value='${tBAppraisaApprovalPage.appraisalProject.appraisalAddress}'> <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">鉴定地点</label></td>
	      </tr>
	      <tr>
	        <td align="right"><label class="Validform_label"> 联&nbsp;系&nbsp;人&nbsp;: </label></td>
	        <td class="value"><input id="contactUsername" name="contactUsername" type="text" style="width: 150px" readonly="readonly"
	          class="inputxt" value='${tBAppraisaApprovalPage.appraisalProject.projectManagerName}'>
	          <input id="contactUserid" name="contactUserid" type="hidden" 
	          value='${tBAppraisaApprovalPage.appraisalProject.projectManagerId}'> <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">联系人</label></td>
	        <td align="right"><label class="Validform_label"> 联系电话: </label></td>
	        <td class="value"><input id="contactPhone" name="contactPhone" type="text" style="width: 150px" readonly="readonly"
	          class="inputxt" value='${tBAppraisaApprovalPage.appraisalProject.projectManagerPhone}'> <span class="Validform_checktip"></span>
	          <label class="Validform_label" style="display: none;">联系电话</label></td>
	      </tr>
	      <tr>
			<td align="right">
				<label class="Validform_label">
					附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:
				</label>
			</td>
			<td class="value" colspan="3">
 				<input type="hidden" value="${tBAppraisaApprovalPage.id}" id="bid" name="bid" />
		        <table style="max-width:82%;">
		            <c:forEach items="${tBAppraisaApprovalPage.certificates}" var="file" varStatus="idx">
			            <tr style="height: 30px;">
				            <td><a href="javascript:void(0);"
				              onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBAppraisaApprovalPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
				            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
				            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
			            </tr>
		            </c:forEach>
		        </table>
				<div>
					<div class="form" id="filediv"></div>
					<t:upload name="fiels" id="file_upload"   extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" 
						formData="bid,projectId" uploader="commonController.do?saveUploadFiles&businessType=tBAppraisaApproval"></t:upload>
				</div>
			</td>
		  </tr>
	    </table>
      	<!-- 修改日志表用 -->
   		<input id="sendIds" name="sendIds" type="hidden" >
    </div>
  </t:formvalid>
  	<div style="width: auto; height: 200px;">
		<div style="width:880px; height: 1px;">
			<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
				<t:tab
					href="tPmApprLogsController.do?tPmApprLogsTable&apprId=${tBAppraisaApprovalPage.id}&apprType=${apprType}&send=${send}&idFlag=${idFlag}" 
					icon="icon-search" title="审核信息" id="apprLogs" width="700"></t:tab>
			</t:tabs>
	 	</div>
	 </div>
<script src="webpage/com/kingtake/project/appraisal/tBAppraisaApproval.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
</body>