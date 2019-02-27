function setDepart(){
    var id = $('#leaveId').val();
    var url = "departController.do?getOrgCombobox&id=" + encodeURIComponent(encodeURIComponent(id));
    $('#cc').combobox('reload', url); 
}

function uploadFile(data){
    $("#bid").val(data.obj);
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
    
//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId);
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
			if(onclick_str!=null){
				if (onclick_str.indexOf("#index#") >= 0){
					$this.attr("onclick",onclick_str.replace(/#index#/g,i));
				}else{
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i+1);
	});
}
//通用弹出式文件上传
function commonUpload(callback,inputId){
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
               iframe.uploadCallback(callback,inputId);
               return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
//通用弹出式文件上传-回调
function commonUploadDefaultCallBack(url,name,inputId){
	$("#"+inputId+"_href").attr('href',url).html('下载');
	$("#"+inputId).val(url);
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


//自定义选择函数
var windowapi = frameElement.api, W = windowapi.opener;
var g_array = new Array();
//参数名称 用户ID，序号，输出ID接收对象，输出用户名接受对象
function custom_choose() {
	if(arguments.length < 4)
	{
		console.error("error argument count");
		return;
	}

	var len = g_array.length;
	for(i=0;i<len;i++)
	{
		g_array.pop();
	}

	//保存参数
	g_array.push(arguments[1]);
	g_array.push(arguments[2]);
	g_array.push(arguments[3]);

	var userIds = $('#'+arguments[0]).val();
	var url = 'commonUserController.do?commonUser';
	if (userIds != '') {
		url = url + '&userIds=' + userIds;
	}
	var departIds = $('#deptId_hidden_').val();
	if (departIds != '') {
		url = url + '&departIds=' + departIds;
	}
	if (typeof(windowapi) == 'undefined') {
		$.dialog({
			content : 'url:' + url,
			zIndex : 3100,
			title : '人员列表',
			lock : true,
			width : '735px',
			height : '400px',
			left : '44%',
			top : '40%',
			opacity : 0.4,
			button : [{
				name : '确定',
				callback : custom_clickcallback,
				focus : true
			}, {
				name : '取消',
				callback : function () {}

			}
			]
		});
	} else {
		$.dialog({
			content : 'url:' + url,
			zIndex : 99999,
			title : '人员列表',
			lock : true,
			parent : windowapi,
			width : '735px',
			height : '400px',
			left : '44%',
			top : '40%',
			opacity : 0.4,
			button : [{
				name : '确定',
				callback : custom_clickcallback,
				focus : true
			}, {
				name : '取消',
				callback : function () {}

			}
			]
		});
	}
}

//序号，处理的对象ID
function custom_clearAll() {
	if(arguments.length != 2)
	{
		console.error("error argument count");
		return;
	}

	var id = arguments[1]+arguments[0];

	if ($('#'+id).length >= 1) {
		$('#'+id).val('');
		$('#'+id).blur();
	}
	//if ($("input[name='"+withPeopleID\\[0\\]']").length >= 1) {
	//	$("input[name='withPeopleID\\[0\\]']").val('');
	//	$("input[name='withPeopleID\\[0\\]']").blur();
	//	$("#deptId_hidden_").val('');
	//}
}
function custom_clickcallback() {
	if(g_array.length != 3)
	{
		console.log("error argument count,not 3 but "+g_array.length);
		return;
	}

	var p_id = g_array[1]+g_array[0];
	var p_name = g_array[2]+g_array[0];

	iframe = this.iframe.contentWindow;
	var id = iframe.getselectedUserListSelections('id');

	if ($('#'+p_id).length >= 1) {
		$('#'+p_id).val(id);
		$('#'+p_id).blur();
	}

	var userId = iframe.getselectedUserListSelections('id');
	var userDepartId = iframe.getselectedUserListSelections('departId');

	//if ($("input[name='withPeopleID\\[0\\]']").length >= 1) {
	//	$("input[name='withPeopleID\\[0\\]']").val(id);
	//	$("input[name='withPeopleID\\[0\\]']").blur();
	//	var userId = iframe.getselectedUserListSelections('id');
	//	var userDepartId = iframe.getselectedUserListSelections('departId');
	//	$("#deptId_hidden_").val(userDepartId);
	//}
	var realName = iframe.getselectedUserListSelections('realName');
	if ($('#'+p_name).length >= 1) {
		$('#'+p_name).val(realName);
		$('#'+p_name).blur();
	}

	g_array.pop();
	g_array.pop();
	g_array.pop();
	//if ($("input[name='withPeopleName\\[0\\]']").length >= 1) {
	//	$("input[name='withPeopleName\\[0\\]']").val(realName);
	//	$("input[name='withPeopleName\\[0\\]']").blur();
	//	var userId = iframe.getselectedUserListSelections('id');
	//	var userDepartId = iframe.getselectedUserListSelections('departId');
	//	$("#deptId_hidden_").val(userDepartId);
	//}
	$.post("commonUserController.do?saveUserContact", {
		'contactIds' : userId,
		'departIds' : userDepartId
	});
}