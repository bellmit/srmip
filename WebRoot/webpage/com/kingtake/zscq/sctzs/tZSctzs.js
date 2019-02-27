$('#file_upload').uploadify({
    buttonText : '添加文件',
    auto : true,
    progressData : 'speed',
    multi : false,
    uploadLimit : 1,
    queueSizeLimit : 1,
    height : 25,
    overrideEvents : [ 'onDialogClose' ],
    fileTypeDesc : '文件格式:',
    queueID : 'filediv',
    fileTypeExts : '*.*',
    fileSizeLimit : '500MB',
    swf : 'plug-in/uploadify/uploadify.swf',
    uploader : 'commonController.do?saveUploadFiles&businessType=sctzs',
    onUploadStart : function(file) {
        var bid = $('#bid').val();
        $('#file_upload').uploadify("settings", "formData", {
            'bid' : bid
        });
    },
    onQueueComplete : function(queueData) {
    },
    onUploadSuccess : function(file, data, response) {
        var d = $.parseJSON(data);
        updateUploadSuccessTab("fileShow",d,file,response);
    },
    onFallback : function() {
        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试");
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
});

$('#file_upload2').uploadify({
    buttonText : '添加文件',
    auto : true,
    progressData : 'speed',
    multi : false,
    uploadLimit : 1,
    queueSizeLimit : 1,
    height : 25,
    overrideEvents : [ 'onDialogClose' ],
    fileTypeDesc : '文件格式:',
    queueID : 'filediv2',
    fileTypeExts : '*.*',
    fileSizeLimit : '500MB',
    swf : 'plug-in/uploadify/uploadify.swf',
    uploader : 'commonController.do?saveUploadFiles&businessType=sctzs',
    onUploadStart : function(file) {
        var bid = $('#scyj').val();
        $('#file_upload2').uploadify("settings", "formData", {
            'bid' : bid
        });
    },
    onQueueComplete : function(queueData) {
    },
    onUploadSuccess : function(file, data, response) {
        var d = $.parseJSON(data);
        updateUploadSuccessTab("fileShow2",d,file,response);
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
        case -240:
            tip("文件在上传中，不能再上传！");
            break;
        }
    },
    onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
    }
});