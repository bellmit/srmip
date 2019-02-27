<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>年度成果鉴定计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function saveCallback(data){
    var win;
    var dialog = W.$.dialog.list['processDialog'];
    if(dialog==undefined){
        win = frameElement.api.opener;
    }else{
        win = dialog.content;
    }
    win.tip(data.msg);
    if(data.success){
        win.reloadTable();
        frameElement.api.close();
    }
}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" beforeSubmit="checkCon" layout="table" action="tBAppraisalProjectController.do?doAdd" tiptype="1" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tBAppraisalProject.id }">
    <input id="totalAmount" name="totalAmount" type="hidden" value="${tBAppraisalProject.totalAmount}">
    <input id="projectId" name="projectId" type="hidden" value="${tBAppraisalProject.projectId }" />
    <script type="text/javascript">
$(function(){
    if($('#localPanelUrl').val()){
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
      $(".easyui-linkbutton").css("display","none");
      //将发送蛇皮设为可点
      $(".sendButton").removeAttr("disabled");
    }
    
  //编辑时审批已处理：提示不可编辑
  /* if($('#localPanelUrl').val().indexOf("tip=true") != -1){
//      var parent = frameElement.api.opener;
    var msg = $("#tabMsg").val();
    tip(msg);
  } */ 
})
</script>
    <table style="width: 100%;" cellpadding="0" cellspacing="0" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 成&nbsp;&nbsp;果&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;称:</label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="achievementName" name="achievementName" type="text" style="width: 462px" class="inputxt" value="${tBAppraisalProject.achievementName }" datatype="byterange" max="100" min="1">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">成果名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">
            鉴定计划年度:<font color="red">*</font>
          </label>
        </td>
        <td class="value">
          <input id="planYear" name="planYear" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" value="${tBAppraisalProject.planYear }">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定计划年度</label>
        </td>
        <td align="right">
          <label class="Validform_label">承&nbsp;&nbsp;研&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;位: </label>
        </td>
        <td class="value">
          <input id="projectDeveloperDepartid" name="projectDeveloperDepartid" type="hidden" value="${tBAppraisalProject.projectDeveloperDepartid }">
          <input id="projectDeveloperDepartname" name="projectDeveloperDepartname" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.projectDeveloperDepartname }"
            readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">承研单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">研制开始时间:&nbsp;&nbsp;</label>
        </td>
        <td class="value">
          <input id="projectBeginDate" name="projectBeginDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value="<fmt:formatDate value='${tBAppraisalProject.projectBeginDate}' 
						type='date' pattern='yyyy-MM-dd'/>">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">研制开始时间</label>
        </td>
        <td align="right">
          <label class="Validform_label">研制截止时间: </label>
        </td>
        <td class="value">
          <input id="projectEndDate" name="projectEndDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value="<fmt:formatDate value='${tBAppraisalProject.projectEndDate }' 
						type='date' pattern='yyyy-MM-dd'/>">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">研制截止时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">项&nbsp;目&nbsp;负&nbsp;责&nbsp;人:&nbsp;&nbsp; </label>
        </td>
        <td class="value">
          <input id="projectManagerId" name="projectManagerId" type="hidden" value="${tBAppraisalProject.projectManagerId }">
          <input id="projectManagerName" name="projectManagerName" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.projectManagerName }" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目负责人</label>
        </td>
        <td align="right">
          <label class="Validform_label">负&nbsp;责&nbsp;人&nbsp;电&nbsp;话: </label>
        </td>
        <td class="value">
          <input id="projectManagerPhone" name="projectManagerPhone" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.projectManagerPhone }" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目负责人电话</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 任&nbsp;&nbsp;务&nbsp;&nbsp;&nbsp;来&nbsp;&nbsp;源:&nbsp;&nbsp;</label>
        </td>
        <td class="value">
          <input id="projectSourceUnit" name="projectSourceUnit" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.projectSourceUnit }" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">任务来源</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 依&nbsp;&nbsp;据&nbsp;&nbsp;&nbsp;文&nbsp;&nbsp;号:</label>
        </td>
        <td class="value">
          <input id="projectAccordingNum" name="projectAccordingNum" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.projectAccordingNum }" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">依据文号</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">主持鉴定单位:&nbsp;&nbsp; </label>
        </td>
        <td class="value">
          <input id="appraisalUnit" name="appraisalUnit" type="text" style="width: 150px" value="${tBAppraisalProject.appraisalUnit }" maxlength="30">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主持鉴定单位</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 鉴&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;形&nbsp;&nbsp;式:</label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="appraisalForm" name="appraisalForm" type="select" code="JDXS" codeType="1" defaultVal="${tBAppraisalProject.appraisalForm}" extendParam="{style:'width:155px'}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定形式</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">主持鉴定联系人: </label>
        </td>
        <td class="value">
          <input id="appraisalContact" name="appraisalContact" type="text" style="width: 150px" value="${tBAppraisalProject.appraisalContact}" maxlength="25">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主持鉴定联系人</label>
        </td>
        <td align="right">
          <label class="Validform_label">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话: </label>
        </td>
        <td class="value">
          <input id="appraisalContactPhone" name="appraisalContactPhone" type="text" maxlength="15" style="width: 150px" class="inputxt" value="${tBAppraisalProject.appraisalContactPhone}">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">电话</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 鉴&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;间:&nbsp;&nbsp;</label>
        </td>
        <td class="value">
          <input id="appraisalTime" name="appraisalTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value="<fmt:formatDate value='${tBAppraisalProject.appraisalTime}' 
						type='date' pattern='yyyy-MM-dd'/>">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定时间</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 鉴&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;地&nbsp;&nbsp;点:</label>
        </td>
        <td class="value">
          <input id="appraisalAddress" name="appraisalAddress" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.appraisalAddress }" maxlength="50">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">鉴定地点</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">组织鉴定单位:&nbsp;&nbsp; </label>
        </td>
        <td class="value">
          <t:codeTypeSelect name="organizeUnit" type="select" codeType="1" code="ZZJDDW" id="organizeUnit" defaultVal="${tBAppraisalProject.organizeUnit }"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">组织鉴定单位</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 关&nbsp;&nbsp;联&nbsp;&nbsp;&nbsp;项&nbsp;&nbsp;目:</label>
        </td>
        <td class="value">
          <input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt" value="${tBAppraisalProject.projectName }" readonly="readonly">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关联项目</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 其他项目来源:&nbsp;&nbsp; </label>
        </td>
        <td class="value" colspan="3">
          <input id="otherSourceProjectIds" name="otherSourceProjectIds" type="hidden" value="${tBAppraisalProject.otherSourceProjectIds}">
          <input id="otherSourceProjectNames" name="otherSourceProjectNames" type="text" style="width: 302px" class="inputxt" value="${tBAppraisalProject.otherSourceProjectNames }">
          <t:chooseProject inputName="otherSourceProjectNames" inputId="otherSourceProjectIds" all="true" isclear="true"></t:chooseProject>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">其他项目来源</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp; </label>
        </td>
        <td class="value" colspan="3">
          <textarea id="memo" name="memo" style="width: 462px" class="inputxt" rows="3" maxlength="150">${tBAppraisalProject.memo}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script type="text/javascript">
	$(document).ready(
			function() {
				var id = $("#id").val();
				if (id) {
					$("#formobj").attr("action",
							"tBAppraisalProjectController.do?doUpdate");
				}
			});

	function checkCon() {
		var planYear = $("#planYear").val();
		if (!planYear) {
			$.Showmsg('年度不能为空!');
			return false;
		}
		return true;
	}
</script>