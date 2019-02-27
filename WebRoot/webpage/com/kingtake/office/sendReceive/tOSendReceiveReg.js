$(function() {
    $("#projectType").combobox({
        url : 'tBProjectTypeController.do?getProjectTypeListWithNull',
        valueField : 'id',
        textField : 'projectTypeName',
        onLoadSuccess : function() {
            var projectType = $("#projectType").val();
            if (projectType != "") {
                $("#projectType").combobox('setValue', projectType);
            } else {
                var data = $("#projectType").combobox('getData');
                if (data.length > 0) {
                    $("#projectType").combobox('setValue', data[0].id);
                }
            }
        }
    });
});

function addMain(){
    var regType = $("#regType").val();
    var registerType = $("#registerType").val();
    if(registerType=='1'){
    $.ajax({
        url:'tOSendReceiveRegController.do?getTemplateByRegType',
        data:{regType:regType},
        cache:false,
        type:'POST',
        dataType:'json',
        success:function(data){
            if(data.length>1){
                $.dialog({
                    title: "模板选择",
                    max: false,
                    min: false,
                    drag: false,
                    resize: false,
                    content: 'url:tOSendReceiveRegController.do?findModelByRegType&regType=' + $('#regType').val(),
                    lock:true,
                    button : [ {
                        name : "确定",
                        callback : function() {
                            iframe = this.iframe.contentWindow;
                            var templateRealpath = $('#templateRealpath', iframe.document).val();
                            $('#realPath').val(templateRealpath);
                            addTemplateToMain();
                            this.close();
                            return false;
                        }
                    }],
                    close: function(){
                        window.scrollTo(0,800);
                    }
                }).zindex();
            }else if(data.length>0){
                $('#realPath').val(data[0].FILEPATH);
                addTemplateToMain();
            }else{
                addTemplateToMain();
            }
          }
    });
    }else{
        addTemplateToMain();
    }
}


function uploadFile(data){
	$("#bid").val(data.obj.id);
	if($(".uploadify-queue-item").length>0){
		upload();
	}else{
		frameElement.api.opener.reloadTable();
		frameElement.api.close();
	}
}
	
function close(){
	frameElement.api.close();
}

$.dialog.setting.zIndex = 1990;
function del(url, obj) {
    $.dialog.confirm("确认删除该条记录?", function() {
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            url : url,// 请求的action路径
            error : function() {// 请求失败处理函数
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    $(obj).closest("tr").hide("slow");
                }
            }
        });
    }, function() {});
}



//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

//获取公文编号
function getFileNum(){
	var isUnion = $("input[name='isUnion']:checked").val();
    var regType = $("#regType").val();
    if(regType==""){
        tip("请先选择公文种类！");
        return ;
    }
    var undertakeDeptId = $("#undertakeDeptId").val();
    if(undertakeDeptId==""){
        tip("请先选择承办单位！");
        return ;
    }
    $.ajax({
          async : false,
          cache : false,
          type : 'POST',
          data : {
        	  isUnion:isUnion,
              regType:regType,
              undertakeDeptId:undertakeDeptId
          },
          url : 'tOSendBillController.do?getFileNum',// 请求的action路径
          dataType:'json',
          success : function(data) {
              $("#fileNumPrefix").val(data.prefix);
              $("#fileNumYear").val(data.year);
              $("#fileNum").val(data.fileNum);
          }
      });
}