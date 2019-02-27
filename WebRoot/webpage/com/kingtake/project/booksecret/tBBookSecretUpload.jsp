<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">
	function alertMsg(data){
		var win = frameElement.api.opener;
		if(data.success==true)
		{
			win.$.messager.alert('导入结果', data.msg);
			frameElement.api.close();			
		}else{
			if(data.responseText==''||data.responseText==undefined){
				$.messager.alert('错误', data.msg);$.Hidemsg();
			}else{
				try{
					var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),data.responseText.indexOf('错误信息')); 
					$.messager.alert('错误',emsg);
					$.Hidemsg();
				}catch(ex){
					$.messager.alert('错误',data.responseText+"");
					$.Hidemsg();
				}
			} 
			return false;
		}
		win.reloadTable();
	}
</script>

</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload" >
	<fieldset class="step">
	<div class="form"><t:upload name="fiels" buttonText="选择要导入的文件" uploader="tBBookSecretController.do?importExcel" extend="*.xls;*.xlsx" id="file_upload" formData="documentTitle" onUploadSuccess="alertMsg" multi="false"></t:upload></div>
	<div class="form" id="filediv" style="height: 50px"></div>
	</fieldset>
</t:formvalid>
</body>

</html>
