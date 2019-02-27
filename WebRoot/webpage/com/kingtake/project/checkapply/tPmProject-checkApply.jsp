<%@ page language="java" import="java.util.*,com.kingtake.common.constant.ProjectConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.legendFont {
	font-size: 12px;
	font-weight: bold;
}
</style>
<title>产品检验申请表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<style>
.btn {
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.42857143;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
      touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
}
.btn:focus,
.btn:active:focus,
.btn.active:focus,
.btn.focus,
.btn:active.focus,
.btn.active.focus {
  outline: thin dotted;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
.btn:hover,
.btn:focus,
.btn.focus {
  color: #333;
  text-decoration: none;
}
.btn:active,
.btn.active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
}
.btn-primary {
  color: #fff;
  background-color: #337ab7;
  border-color: #2e6da4;
}
.btn-primary:hover,
.btn-primary:focus,
.btn-primary.focus,
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary {
  color: #fff;
  background-color: #286090;
  border-color: #204d74;
}
.btn-primary:active,
.btn-primary.active,
.open > .dropdown-toggle.btn-primary {
  background-image: none;
}
</style>
<script type="text/javascript">
$(function(){
    $('#file_upload1').uploadify({
        buttonText : '浏览',
        auto : false,
        progressData : 'speed',
        multi : true,
        height : 25,
        width : 100,
        overrideEvents : [ 'onDialogClose' ],
        fileTypeDesc : '文件格式:',
        queueID : 'filediv1',
        fileTypeExts : '*.*;',
        fileSizeLimit : '2GB',
        successTimeout:120,
        swf : 'plug-in/uploadify/uploadify.swf',
        uploader : 'commonController.do?saveUploadFilesToFTP&businessType=checkApply',
        onUploadStart : function(file) {
          var bid = $('#bid').val();
          $('#file_upload1').uploadify("settings", "formData", {
            'bid' : bid
          });
        },
        onQueueComplete : function(queueData) {
            $("#diskList").datagrid("reload");
        },
        onUploadSuccess : function(file, data, response) {
            var d = $.parseJSON(data);
//             updateUploadSuccess("fileShow1",d,file,response);
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
    
    if(location.href.indexOf("load=detail")!=-1){
    	//隐藏保存按钮
    	$("#saveBtn").css("display","none");
    	//隐藏上传按钮
    	$("#filesUploadDiv").css("display","none");
	}
});

function goDelete(id) {
	if(location.href.indexOf("load=detail")!=-1){
    	return;
	}
		$.messager.confirm('确认', '您确认删除所选文件吗？', function(r) {
			if (r) {
				$.ajax({
					url : 'commonController.do?delFTPFile',
					data : {
						id : id
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						tip(data.msg);
						$("#diskList").datagrid('reload');
					}
				});
			}
		});
}

</script>
</head>

<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmCheckApplyController.do?doAddOrUpdate" tiptype="1">
    <div style="position: fixed; top: 0; left: 0; height: 30px; width: 100%; background: #4782d8; color:#fff; border-bottom: solid 1px #95B8E7;">
      <h1 align="center">产品检验申请表</h1>
      <span><input id="saveBtn" type="button" style="position: fixed; right: 5px; top: 3px; display:none;" value="保存"></span>
    </div>
    <br />
    <br />
    <input id="id" name="id" type="hidden" value="${tPmCheckApplyPage.id }">
    <input id="projectId" name="projectId" type="hidden" value="${tPmCheckApplyPage.projectId }">
<%--     <input id="attachmentCode" name="attachmentCode" type="hidden" value="${tPmCheckApplyPage.attachmentCode }"> --%>
    <fieldset style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #A52A2A">最终产品检验申请表</span>
      </legend>
      <div>
        <table style="width: 100%;" cellpadding="0" cellspacing="0" class="formtable">
          <tr>
            <td align="right"><label class="Validform_label"> 编号: </label></td>
            <td class="value"><input id="applyNo" name="applyNo" type="text" style="width: 222px" datatype="byterange" max="32" min="0" class="inputxt" value="${tPmCheckApplyPage.applyNo }"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">编号</label></td>
            <td align="right"><label class="Validform_label"> 产品名称: <font color="red">*</font></label></td>
            <td class="value"><input id="productName" name="productName" type="text" style="width: 222px" datatype="byterange" max="100" min="1" class="inputxt" value="${tPmCheckApplyPage.productName }"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">产品名称</label></td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 产品代码: </label></td>
            <td class="value"><input id="productCode" name="productCode" type="text" style="width: 222px" datatype="byterange" max="100" min="0" class="inputxt" value="${tPmCheckApplyPage.productCode }"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">产品代码</label></td>
            <td align="right"><label class="Validform_label"> 产品编号: </label></td>
            <td class="value"><input id="productNo" name="productNo" type="text" style="width: 222px" datatype="byterange" max="100" min="0" class="inputxt" value="${tPmCheckApplyPage.productNo }"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">产品编号</label></td>
          </tr>          
        </table>
      </div>
    </fieldset>
    <fieldset style="border-color: #F5F5F5;">
      <legend onclick="show_hide('baseData','showTxt')">
        <span class="legendFont" style="color: #228B22">合格证信息</span>
      </legend>
      <div>
        <table style="width: 100%;" cellpadding="0" cellspacing="0" class="formtable">
          <tr>
            <td align="right"><label class="Validform_label"> 验收时间: </label></td>
            <td class="value"><input id="checkTime" name="checkTime" type="text" ignore="ignore" datatype="date" style="width: 222px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmCheckApplyPage.checkTime}' 
                  type="date" pattern="yyyy-MM-dd"/>'> <span
              class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">验收时间</label></td>
            <td align="right"><label class="Validform_label"> 验收结果: </label></td>
            <td class="value">
            <select name="checkResult" id="checkResult" value='${tPmCheckApplyPage.checkResult}' style="width: 222px"> 
            	<option value="">---请选择---</option> 
            	<option value="1">合格</option> 
            	<option value="2">不合格</option></select>
            <label class="Validform_label" style="display: none;">验收结果</label>
            </td>
          </tr>
          <tr>
            <td align="right"><label class="Validform_label"> 合格证书编号: </label></td>
            <td class="value"><input id="qualificationNo" name="qualificationNo" type="text" style="width: 222px" datatype="byterange" max="100" min="0" class="inputxt" value="${tPmCheckApplyPage.qualificationNo }"> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">合格证书编号</label></td>
          </tr> 
          <tr>
            <td align="right"><label class="Validform_label"> 检验记录: </label></td>
            <td class="value"><textarea id="checkRecord" name="checkRecord" style="width: 422px; height: 50px;" datatype="byterange" max="500" min="0">${tPmCheckApplyPage.checkRecord }</textarea> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">检验记录</label></td>
          </tr>            
        </table>
      </div>
      <div class="easyui-layout" fit="true" style="height:200px; margin:auto auto;">
  	 <div region="center" style="padding:1px;">
  		<t:datagrid name="diskList" checkbox="true" fitColumns="true" title="附件列表" 
  			actionUrl="tPmCheckApplyController.do?datagridForCheckApply&businessType=checkApply&attachmentCode=${tPmCheckApplyPage.attachmentCode }"
 	 		idField="id" fit="true" queryMode="group">
   			<t:dgCol title="主键"  field="id"  hidden="true" queryMode="group"  width="120"></t:dgCol>
   			<t:dgCol title="文件名"  field="attachmenttitle" query="true"  queryMode="single" width="220" extendParams="formatter:fileFormatter,"></t:dgCol>
   			<t:dgCol title="文件后缀名"  field="extend" hidden="true"  queryMode="group" width="120" ></t:dgCol>
   			<t:dgCol title="上传时间"  field="createdate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="single" width="120"></t:dgCol>
   			<t:dgCol title="文件路径"  field="realpath" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   			<t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   			<t:dgFunOpt title="删除" funname="goDelete(id)"></t:dgFunOpt>
  		</t:datagrid>
  	 </div>
  </div>
  <div region="north" style="height: 120px;" split="true" id="filesUploadDiv">
  	<table id="materiaTab" style="width: 100%;margin-top: 30px;" cellpadding="0" cellspacing="1" class="formtable">
  		<tr>
        	<td align="right" style="width: 120px;">
          		<label class="Validform_label" style="font-size: 20px;"> 上传附件: </label>
        	</td>
        	<td class="value" style="width: 120px;">
          		<input type="hidden" id="bid" name="attachmentCode" value='${tPmCheckApplyPage.attachmentCode }'/>
          		<table style="max-width: 360px;" id="fileShow1">
          		</table>
          		<div>
            		<span id="file_upload1span">
              			<input type="file" name="fiels" id="file_upload1" />
            		</span>
          		</div>
        	</td>
        	<td style="width: 150px;">
            	<input type="button" value="上传" onclick='$("#file_upload1").uploadify("upload", "*");' style="width: 105px; height:30px; position: relative; top: -5px; border-radius:20px" class="btn btn-primary">
        	</td>
        	<td>
        		<div class="form jeecgDetail" id="filediv1"></div>
        	</td>
      	</tr>
      	<tr>
      		<td colspan="4"><span style="margin-left:40px;"><font color="red" style="font-size: 20px;">说明：请上传最终产品检验申请单，上传合格证书签署页，其他附件（例如：合格证书检验记录等）。</font></span></td>
      	</tr>
      </table>
  </div>
    </fieldset>
  </t:formvalid>
  
</div>
</body>
<script type="text/javascript" src="webpage/com/kingtake/office/disk/disk.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var resultSelectValue = "${tPmCheckApplyPage.checkResult}";
	$("#checkResult option[value=" + resultSelectValue + "]").attr("selected", true);
});
</script>