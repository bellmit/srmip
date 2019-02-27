

//--------------------删除附件--------------------------
function delFTPFile(url,obj){
	var windowapi = frameElement.api;
	if (typeof (windowapi) == 'undefined') {
		$.dialog({
			title:"删除确认",
			icon: 'confirm.gif',
			content: "确定删除该记录吗 ?",
			lock : true,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	$.ajax({
					cache : false,
					type : 'POST',
					url : url,// 请求的action路径
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
						    var msg = d.msg;
						    tip(msg);
						    $(obj).closest("tr").hide("slow");
						}
					}
				});
		    },
		    cancelVal: '取消',
		    cancel: true
		}).zindex();
	} else {
		W.$.dialog({
			title:"删除确认",
			icon: 'confirm.gif',
			content: "确定删除该记录吗 ?",
			lock : true,
			opacity : 0.3,
			cache:false,
			parent:windowapi,
		    ok: function(){
		    	$.ajax({
					cache : false,
					type : 'POST',
					url : url,// 请求的action路径
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
						    var msg = d.msg;
						    tip(msg);
						    $(obj).closest("tr").hide("slow");
						}
					}
				});
		    },
		    cancelVal: '取消',
		    cancel: true
		}).zindex();
	}
}



//---------------------附件上传成功方法--------------------
function uploadSuccess(d,file,response){
    var html="";
    html += 
    	'<tr style="height: 30px;">'+
        	'<td>' +
            	'<a href="javascript:void(0);" >' + d.attributes.fileName + '</a>&nbsp;&nbsp;&nbsp;' +
            '</td>' +
        	'<td style="width:40px;">' +
        	'<a href="commonController.do?viewFTPFile' +
        			'&fileid=' + d.attributes.fileid + 
        			'&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0"' + 
        			'title="下载">下载</a>' +
            '</td>' +
           
            '<td style="width:40px;">' +
            	'<a href="javascript:void(0)" class="jeecgDetail" ' +
            			'onclick="delFTPFile(\'commonController.do?delFTPFile&id=' + d.attributes.fileid +'\',this)">删除</a>' +
            '</td>'+
        '</tr>';
    $("#fileShow").append(html);
}

function updateUploadSuccessTable(tableId,d,file,response){
    var html="";
    html += 
        '<tr style="height: 30px;">'+
            '<td>' +
                '<a href="javascript:void(0);" >' + d.attributes.fileName + '</a>&nbsp;&nbsp;&nbsp;' +
            '</td>' +
            '<td style="width:40px;">' +
            '<a href="commonController.do?viewFTPFile' +
                    '&fileid=' + d.attributes.fileid + 
                    '&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0"' + 
                    'title="下载">下载</a>' +
            '</td>' +
           
            '<td style="width:40px;">' +
                '<a href="javascript:void(0)" class="jeecgDetail" ' +
                        'onclick="delFTPFile(\'commonController.do?delFTPFile&id=' + d.attributes.fileid +'\',this)">删除</a>' +
            '</td>'+
        '</tr>';
    $("#"+tableId).append(html);
}

/*$('#file_upload').uploadify({
    buttonText : '添加文件',
    auto : true,
    progressData : 'speed',
    multi : true,
    uploadLimit : 10,
    queueSizeLimit : 10,
    height : 25,
    successTimeout : 120,
    overrideEvents : [ 'onDialogClose' ],
    fileTypeDesc : '文件格式:',
    queueID : 'filediv',
    fileTypeExts : '*.*',
    fileSizeLimit : '2GB',
    swf : 'plug-in/uploadify/uploadify.swf',
    uploader : 'commonController.do?saveUploadFilesToFTP',
    onUploadStart : function(file) {
        var bid = $('#bid').val();
        var businessType = $('#businessType').val();
        var projectId = $('#projectId').val();
        $('#file_upload').uploadify("settings", "formData", {
            'bid' : bid,
            'businessType':businessType,
            'projectId':projectId
        });
    },
    onQueueComplete : function(queueData) {
    },
    onUploadSuccess : function(file, data, response) {
        var d = $.parseJSON(data);
        updateUploadSuccess(d,file,response);
    },
    onFallback : function() {
        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
    },
    onSelectError : function(file, errorCode, errorMsg) {
        switch (errorCode) {
        case -100:
            tip("上传的文件数量已经超出系统限制的" + $('#file_upload').uploadify('settings', 'queueSizeLimit') + "个文件！");
            break;
        case -110:
            tip("文件 [" + file.name + "] 大小超出系统限制的" + $('#file_upload').uploadify('settings', 'fileSizeLimit') + "大小！");
            break;
        case -120:
            tip("文件 [" + file.name + "] 大小异常！");
            break;
        case -130:
            tip("文件 [" + file.name + "] 类型不正确！");
            break;
        case -240:
            tip("文件在上传中，不能再上传！");
            break;
        }
    },
    onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
    }
});*/