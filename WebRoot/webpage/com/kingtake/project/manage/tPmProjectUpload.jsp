<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">
	function alertMsg(data){
		if(data.success==true)
		{
			var msg=$.parseJSON(data.msg);
			var rownum = msg.rownum;
			var rownumSuccess = msg.rownumSuccess;
			var errs = msg.errs;
			
			var ms1 = '操作完毕！数据记录数：'+rownum+",导入成功记录数："+rownumSuccess+"<br>"+( errs ? "\n有以下数据未导入成功:<br>"+errs.replace(/#/g,"<br>") : "");
			$.dialog({
			    title : "操作结果",
			    content: ms1,
			    parent:windowapi,
			    button: [
			        {
			            name: "确定",
			            callback: function(){
			            	var ifr = window.top.$("iframe[name='projectPlan']");
			            	var ifrm = ifr.get(0);
			            	if(ifrm.contentWindow.tPmProjectListsearch){
			            		ifrm.contentWindow.tPmProjectListsearch();
			            	}
			            	frameElement.api.close();
			            }
			        }
			    ]
			}).zindex();
		}else{
			if(data.responseText==''||data.responseText==undefined){
				//$.messager.alert('错误', data.msg);
				alert('导入失败：'+data.msg);
				//$.Hidemsg();
			}else{
				try{
					var emsg = data.responseText.substring(data.responseText.indexOf('错误描述'),data.responseText.indexOf('错误信息')); 
					//$.messager.alert('错误',emsg);
					alert('导入失败：'+emsg);
					//$.Hidemsg();
				}catch(ex){
					//$.messager.alert('错误',data.responseText+"");
					alert('导入失败：'+data.responseText);
					//$.Hidemsg();
				}
			} 
			//return false;
		}
	}
	
	function test(){}
</script>

</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload" >
	<fieldset class="step">
	<div class="form"><t:upload name="fiels" buttonText="选择要导入的文件" uploader="tPmProjectController.do?importExcel&lxStatus=${lxStatus }&projectPlanId=${projectPlanId }" extend="*.xls;*.xlsx" id="file_upload" formData="documentTitle" onQueueComplete="test" onUploadSuccess="alertMsg" multi="false"></t:upload></div>
	<div class="form" id="filediv" style="height: 50px"></div>
	</fieldset>
</t:formvalid>
</body>

</html>
