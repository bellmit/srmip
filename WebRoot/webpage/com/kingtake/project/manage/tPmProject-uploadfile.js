function initUpload(id,queueId,tableName,txt,queueComplete){
	$('#'+id).uploadify(
			{   buttonText:txt,
				auto:false,
				progressData:'speed',
				multi:true,
				height:20,
				width:120,
				overrideEvents:['onDialogClose'],
				fileTypeDesc:'文件格式:',
				queueID:queueId,
				fileTypeExts:'*.doc;*.docx;*.xls;*.xlsx;*.pdf;',
				fileSizeLimit:'500MB',swf:'plug-in/uploadify/uploadify.swf',	
				uploader:'commonController.do?saveUploadFiles&convertFile=0&businessType='+tableName+'&sessionId='+new Date().getTime(),
				onDialogClose:function(queueData){},
				onUploadStart:function(queueData){
					//debugger;
				},
				onQueueComplete:queueComplete, //function(queueData) {} 
				onUploadSuccess : function(file, data, response) {
					//var d=$.parseJSON(data);
				},
				onFallback : function(){
					tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");
				},
				onSelectError : function(file, errorCode, errorMsg){
					switch(errorCode) {
						case -100:tip("上传的文件数量已经超出系统限制的"+$('#file_upload').uploadify('settings','queueSizeLimit')+"个文件！");break;
						case -110:tip("文件 ["+file.name+"] 大小超出系统限制的"+$('#file_upload').uploadify('settings','fileSizeLimit')+"大小！");break;
						case -120:tip("文件 ["+file.name+"] 大小异常！");break;
						case -130:tip("文件 ["+file.name+"] 类型不正确！");break;
						}
					},
				onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
				}
	});
}

function upload(id) {	
	$('#'+id).uploadify('upload', '*');		
	//return flag;
}
function cancel(id) {
	$('#'+id).uploadify('cancel', '*');
}

function uploadFile(data){

    $("#bid").val(data.obj.id);
    fileCount = $(".uploadify-queue-item").length;
    if(fileCount>0){
    	var bid= {'bid':$('#bid').val()} ;
    	
        setTimeout(function(){
        	$('#file_upload_schoolCooperationFlag').uploadify("settings","formData", bid);
        	upload("file_upload_schoolCooperationFlag")}, 100);
        setTimeout(function(){
        	$('#file_upload_lhsb').uploadify("settings", "formData", bid);
        	upload("file_upload_lhsb")}, 100);
        setTimeout(function(){
    		$('#file_upload').uploadify("settings", "formData", bid);
    		upload("file_upload")}, 100);
    }else{
        //frameElement.api.opener.reloadTable();
    	if(assignFlag){
    		try{
    			//项目立项，刷新项目列表
    			frameElement.api.opener.tPmProjectListsearch();
    		}catch(error){
    		}
    	}
		closeWin();
		doRefresh();
        frameElement.api.close();
        return;
    }
}

/**此方法仅用地测试文件上传*/
function testUpload(){
	var data = { obj:{id:"gt100000"} };
	uploadFile(data);
}

/**记录上传成功的文件数，判断上传成功则关窗**/
var counter = 0;
var fileCount = 0;
function queueComplete(queueData){
	counter+=queueData.uploadsSuccessful;
	closeWin();
}
function closeWin(){
	if(counter==fileCount){
		if(window.location.href.indexOf("goUpdateUnapproval")>=0){
			window.location.reload(true);
		}else{
			try{
				 var win = frameElement.api.opener  
				 win.reloadtPmProjectList();
		    }catch(e){
		    }
		    
		    frameElement.api.close();
		}
	}
}

/**初始化文件上传控件*/
$(function(){  
	initUpload("file_upload_schoolCooperationFlag","filediv_schoolCooperationFlag","tPmProject_schoolCooperationFlag","上传校内协作文件",queueComplete);
	initUpload("file_upload_lhsb","filediv_lhsb","tPmProject_lhsb","上传联合申报附件",queueComplete);
	initUpload("file_upload","filediv","tPmProject","上传文件",queueComplete);
	
	$("input[name='schoolCooperationFlag']").click(function(){
		if($(this).val()=="1"){
			$("#file_upload_schoolCooperationFlag").parent().show();
		}else{
			$("#file_upload_schoolCooperationFlag").parent().hide();
		}
	});
	
	$("input[name='lhsb']").click(function(){
		if($(this).val()=="1"){
			$("#file_upload_lhsb").parent().show();
		}else{
			$("#file_upload_lhsb").parent().hide();
		}
	});
});