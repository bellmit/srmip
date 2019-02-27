function  validRoomFree() {
    var flag = true;
    var roomName = $("#meetingRoomName").combobox("getValues")[0];
    var beginDate = $("#beginDate").val();
    var endDate = $("#endDate").val();
    var id = $("#id").val();
    if(!beginDate && !endDate){
        flag = false;
    }else{
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            data : {"beginDate":beginDate,"endDate":endDate,"roomName":roomName,"id":id},
            url : "tOMeetingController.do?doTimeValidate",
            success : function(result) {
                if($.parseJSON(result) == false){
                    $.Showmsg('会议室正在使用中，请选择其他会议室！');
                    flag = false;
                }
            }
        });
    }
    return flag;
}
    


//提交前验证
function valiBeforeSubmit(){
	var roomName = $("#meetingRoomName").combobox("getValues")[0];
	var reg = /^[\w\W]{1,15}$/;
	if(!reg.test(roomName)){
		$.Showmsg('请选择或填写会议室(名称范围1~15位中文或字符，中间不能有空格)！');
		return false;
	}
	//验证会议室是否空闲
	return validRoomFree();
}

//绑定会议室的验证事件
function valiRoomName(){
	var roomName = $("#meetingRoomName").combobox("getValues")[0];
	var reg = /^[\w\W]{1,30}$/;
	if(reg.test(roomName)){
		return true;
	}else{
		$.Showmsg('请选择或填写会议室(名称范围1~30位中文或字符，中间不能有空格)！');
		return false;
	}
}

//绑定主办单位的验证事件
function valiHostUnitId(){
	var departId = $("#hostUnitId").combotree("getValues")[0];
	if(!departId){
		$.Showmsg('请选择主办单位！');
		return false;
	}
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