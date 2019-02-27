<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>鉴定计划未完成情况说明</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
</head>

<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password"
	layout="table" action="tBAppraisalNoFinishController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
	<input id="id" name="id" type="hidden" value="${tBAppraisalNoFinish.id }">
	<input id="tbId" name="tbId" type="hidden" value='${appraisalProject.id}'>
	<input id="projectId" type="hidden" value='${appraisalProject.projectId}'>
  <input id="projectManagerId" name="projectManagerId" type="hidden" 
		value="${appraisalProject.projectManagerId }"> 
	<input id="userId" name="userId" type="hidden" value="${userId }"/>
		
	<table cellpadding="0" cellspacing="0" class="formtable" style="width:610px; margin:auto;">
		<tr>
			<td align="right"><label class="Validform_label">成&nbsp;&nbsp;果&nbsp;&nbsp;名&nbsp;&nbsp;称&nbsp;: </label></td>
			<td class="value" colspan="3">
				<input id="projectName" name="projectName" type="text" style="width: 466px"
					value='${appraisalProject.projectName}' readonly="readonly">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">成果名称</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">项&nbsp;&nbsp;目&nbsp;&nbsp;来&nbsp;&nbsp;源&nbsp;: </label></td>
			<td class="value" colspan="3">
				<input id="projectSourceUnit" name="projectSourceUnit" type="text" style="width: 466px"
					value='${appraisalProject.projectSourceUnit}' readonly="readonly">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">项目来源</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">项目起止时间: </label></td>
			<td class="value" colspan="3">
				<input type="text" style="width: 466px"
					value='<fmt:formatDate value='${appraisalProject.projectBeginDate}' 
						type="date" pattern="yyyy-MM-dd"/>-<fmt:formatDate 
						value='${appraisalProject.projectEndDate}' 
						type="date" pattern="yyyy-MM-dd"/>' readonly="readonly">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">项目来源</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">计划鉴定时间: </label></td>
			<td class="value">
				<input id="appraisalTime" name="appraisalTime" type="text" style="width: 150px"
					class="Wdate" onClick="WdatePicker()" disabled="disabled"
					value='<fmt:formatDate value='${appraisalProject.appraisalTime}' type="date" pattern="yyyy-MM-dd"/>'>
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">计划鉴定时间</label>
			</td>
			<td align="right"><label class="Validform_label">拟更改鉴定时间: </label><font color="red">*</font></td>
			<td class="value">
				<input id="changeAppraisaTime" name="changeAppraisaTime" type="text" style="width: 150px"
					class="Wdate" onClick="WdatePicker()"
					value='<fmt:formatDate value='${tBAppraisalNoFinish.changeAppraisaTime}' type="date" pattern="yyyy-MM-dd"/>'>
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">拟更改鉴定时间</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费: </label></td>
			<td class="value">
				<input id="totalAmount" name="totalAmount" 
					value='${appraisalProject.totalAmount}' readonly="readonly"
					style="width:135px;text-align:right;" class="easyui-numberbox"
		     		data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">总经费</label>
			</td>
			<td align="right"><label class="Validform_label">项&nbsp;&nbsp;&nbsp;&nbsp;目&nbsp;&nbsp;&nbsp;&nbsp;组&nbsp;&nbsp;&nbsp;&nbsp;长:&nbsp;&nbsp; </label></td>
			<td class="value">
				<input id="projectManagerName" name="projectManagerName" type="text" style="width: 150px"
					value='${appraisalProject.projectManagerName}' readonly="readonly">
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">项目组长</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态: </label></td>
			<td class="value" colspan="3">
				<t:codeTypeSelect id="state" name="state" type="select" codeType="1" code="TJZZZT" 
					defaultVal="${tBAppraisalNoFinish.state }"></t:codeTypeSelect>
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">状态</label>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">未完成鉴定原因: </label></td>
			<td class="value" colspan="3">
				<textarea id="noFinishReason" name="noFinishReason" style="width: 466px; height:80px;"
				class="inputxt">${tBAppraisalNoFinish.noFinishReason}</textarea>
				<span class="Validform_checktip"></span> 
				<label class="Validform_label" style="display: none;">未完成鉴定原因</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:
				</label>
			</td>
			<td class="value" colspan="3">
 				<input type="hidden" value="${tBAppraisalNoFinish.id}" id="bid" name="bid" />
		        <table style="max-width:466px;">
		            <c:forEach items="${tBAppraisalNoFinish.certificates}" var="file" varStatus="idx">
			            <tr style="height: 30px;">
				            <td><a href="javascript:void(0);"
				              onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBAppraisalNoFinish.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
				            <td style="width:40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
				            <td style="width:40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
			            </tr>
		            </c:forEach>
		        </table>
				<div>
					<div class="form" id="filediv"></div>
					<t:upload name="fiels" id="file_upload" dialog="false" callback="uploadCallback"  extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" 
						formData="bid,projectId" uploader="commonController.do?saveUploadFiles&businessType=tBAppraisalNoFinish"></t:upload>
				</div>
			</td>
		</tr>
	</table>
</t:formvalid>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("#state").attr("disabled", "disabled");
	console.info($("#state").val());
	loadButton($("#id").val(), $("#state").val(), $("#userId").val(), $("#projectManagerId").val());
});

function closeWin(msg){
    var win;
    var dialog = W.$.dialog.list['processDialog'];
    if (dialog == undefined) {
      win = frameElement.api.opener;
    } else {
      win = dialog.content;
    }
	win.showMsg(msg);
	win.reloadTable();
}

function checkCon(){
	var appraisaTime = $("#changeAppraisaTime").val();
	if(!appraisaTime){
		$.Showmsg("拟更改鉴定时间不能为空！");
		return false;
	}
	return true;
}

/**
 * 保存或更新
 */
function saveOrUpdate(){
	var flag = checkCon();
	if(flag){
		$.ajax({
			async: false,
			type: 'post',
			url: 'tBAppraisalNoFinishController.do?doUpdate',
			data: $("#formobj").serialize(),
			success: function(data){
				data = $.parseJSON(data);
				$("#bid").val($.parseJSON(data.obj).id);
				if($(".uploadify-queue-item").length>0){
					upload();
				}else{
					frameElement.api.opener.reloadTable();
					frameElement.api.close();
				}
				closeWin(result.msg);
			}
		});
	}
	return flag;	
}

/**
 * 提交给组长
 */
function toManager(){
	var data = {id:$("#id").val(), state:'1'};
	managerAppr(data);
}

/**
 * 组长同意
 */
function managerYes(){
	var data = {id:$("#id").val(), state:'2'};
	managerAppr(data);
}

/**
 * 组长驳回
 */
function managerNo(){
	var data = {id:$("#id").val(), state:'3'};
	managerAppr(data);
}

/**
 * 提交组长、同意、驳回
 */
function managerAppr(data){
	$.ajax({
		async: false,
		type: 'post',
		url: 'tBAppraisalNoFinishController.do?managerAppr',
		data: data,
		success: function(result){
			result = $.parseJSON(result);
			closeWin(result.msg);
		}
	});
}

/**
 * 提交给机关组
 */
function doSubmit(){
	$.ajax({
		async: false,
		type: 'post',
		url: 'tBAppraisalNoFinishController.do?doSubmit',
		data: {id:$("#id").val()},
		success: function(result){
			var data = $.parseJSON(result);
			closeWin(data.msg)
		}
	});
}


function loadButton(id, state, userId, projectManagerId){
	if(!id){
		// 新建：保存、关闭
		frameElement.api.button({
    		name: '保存',
    		callback:saveOrUpdate,
    		focus: true
   		},{
   			name: '关闭'
   		});
	}else if(state == "0"){
		// 未发送：保存、取消、提交给组长
		frameElement.api.button({
    		name: '保存',
    		callback:saveOrUpdate,
   		},{
   			name: '提交组长签字',
    		callback:toManager,
    		focus: true
   		},{
   			name: '关闭'
   		});
	}else if(state == "1" && userId == projectManagerId){
		// 已发送：本人是组长
		frameElement.api.button({
			name: '组长同意签字',
    		callback:managerYes,
    		focus: true
		},{
			name: '组长不同意驳回',
    		callback:managerNo,
		},{
			name: '关闭'
		}); 
		disableForm();
	}else if(state == "1" && userId != projectManagerId){
		// 已发送：本人不是组长
		frameElement.api.button({name: '关闭'}); 
		disableForm();
	}else if(state == "2"){
		// 组长同意-已完成：关闭
		frameElement.api.button({
   			name: '关闭'
   		});
		disableForm();
	}else if(state == "3"){
		// 组长驳回-被驳回：保存、关闭、提交给组长
		frameElement.api.button({
    		name: '保存',
    		callback:saveOrUpdate,
   		},{
    		name: '提交组长签字',
    		callback:toManager,
    		focus: true
   		},{
   			name: '关闭'
   		});
	}
}

//禁用控件
function disableForm(){
    $(":input").attr("disabled",true);
    $("textarea").attr("disabled",true);
    $(".jeecgDetail").parent().hide();
    $("#file_upload").hide();
}

function saveCallback(data) {
    var win;
    var dialog = W.$.dialog.list['processDialog'];
    if (dialog == undefined) {
      win = frameElement.api.opener;
    } else {
      win = dialog.content;
    }
    win.tip(data.msg);
    if (data.success) {
      win.reloadTable();
      frameElement.api.close();
    }
  }
  
//提交表单后回调函数
function uploadFile(data) {
    $("#bid").val(data.obj.id);
    if ($(".uploadify-queue-item").length > 0) {
        upload();
    }else{
        var win;
        var dialog = W.$.dialog.list['processDialog'];
        if (dialog == undefined) {
          win = frameElement.api.opener;
        } else {
          win = dialog.content;
        }
        win.tip(data.msg);
        if (data.success) {
          win.reloadTable();
          frameElement.api.close();
        }
    }
  }
  
  //上传完成后调用回调函数
  function uploadCallback(){
      var win;
      var dialog = W.$.dialog.list['processDialog'];
      if (dialog == undefined) {
        win = frameElement.api.opener;
      } else {
        win = dialog.content;
      }
      win.tip("保存成功！");
      win.reloadTable();
      frameElement.api.close();
  }
</script>