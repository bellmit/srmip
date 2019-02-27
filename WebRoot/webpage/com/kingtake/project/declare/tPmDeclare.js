

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

function uploadFile(data) {
  $("#bid").val(data.obj.id);
  if ($(".uploadify-queue-item").length > 0) {
      upload();
  }else{
      //tip(data.msg);
      $.messager.alert('提示',data.msg,function(){
          window.location.reload();
      });
  }
}

//上传成功后，刷新
function uploadCallback(data){
    tip("申报书保存成功!");
    setTimeout(function(){
        window.location.reload();
    },2000);
}

function showMsg(msg){
  $.messager.show({
    title:'提示',
    msg:msg,
    timeout:5000,
    showType:'slide'
  });
}

//提交流程
function startProcess(tableName){
    $("#tableName").val(tableName);
    var url = 'tPmDeclareController.do?goSelectOperator2';
    //流程对应表单URL
   if(typeof(windowapi) == 'undefined'){
        $.dialog.confirm("确定提交流程吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180);
        }, function() {
        }).zindex();
    }else{
        W.$.dialog.confirm("确定提交流程吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180);
        }, function() {
        },windowapi).zindex();
    }
   
}

function submitAudit(nextUser){
   var url = "tBLearningThesisController.do?startProcess";
   var id =  $("#id").val();
   var businessCode =  "declare";
   var projectId = $("#tpId").val();
   var tableName = $("#tableName").val();
   //流程对应表单URL
   var formUrl = 'tPmDeclareController.do?goDeclareAudit&projectId='+projectId;
   var businessName = $("#projectName").val();
   var paramsData = {"id":id,"businessCode":businessCode,"formUrl":formUrl,"businessName":businessName,"tableName":tableName,"nextUser":nextUser};
   $.ajax({
       async : false,
       cache : false,
       type : 'POST',
       data : paramsData,
       url : url,// 请求的action路径
       success : function(data) {
           var d = $.parseJSON(data);
           tip(d.msg);
           if (d.success) {
               var msg = d.msg;
               setTimeout(function(){
                   window.location.reload();
               },2000);
           }
       }
   });
}

//打开选择人窗口
function openOperatorDialog(title, url, width, height) {
    var width = width ? width : 700;
    var height = height ? height : 400;
    if (width == "100%") {
        width = window.top.document.body.offsetWidth;
    }
    if (height == "100%") {
        height = window.top.document.body.offsetHeight - 100;
    }

    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var operator = iframe.getOperator();
                if (operator == "" || operator ==undefined) {
                    return false;
                }
                submitAudit(operator);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    } else {
        W.$.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            parent : windowapi,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var operator = iframe.getOperator();
                if (operator == "" || operator ==undefined) {
                    return false;
                }
                submitAudit(operator);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
}


function getId() {
    var id = $("#id").val();
    return id;
}

function getTableName() {
    return $("#tableName").val();
}

function getBusinessName() {
    var businessName = $("#projectName").val();
    return businessName;
}

function getFormUrl() {
    var projectId = $("#tpId").val();
    //流程对应表单URL
    var formUrl = 'tPmDeclareController.do?goDeclareAudit&projectId='+projectId;
    return formUrl;
}

function getBusinessCode() {
    return "declare";
}

// 查看流程
function viewHistory(processInstanceId) {
    goProcessHisTab(processInstanceId,'项目申报书流程');
}


/**
 * 查看流程意见
 */
function viewRemark(){
    var processInsId = $("#processInsId").val();
    var url = "tPmDeclareController.do?goViewProcess&processInstId="+processInsId;
    createdetailwindow("查看流程意见", url,null,null);
}

//重新提交
    function compeleteProcess() {
        var taskId = $("#taskId").val();
        var url;
        var data;
        if(taskId!=""){
            url="activitiController.do?processComplete";
            data={
                "taskId" : taskId,
                "nextCodeCount" : 1,
                "model" : '1',
                "reason" : "重新提交",
                "option" : "重新提交"
            };
        }else{
            url="tPmDeclareController.do?doResubmit";
            var id = $("#id").val();
            data={"declareId":id,"declareType":"declare"}
        }
        if(typeof(windowapi)=='undefined'){
            $.dialog.confirm('您确定修改好，重新提交申报书吗?', function() {
                    $.ajax({
                        url : url,
                        type : "POST",
                        dataType : "json",
                        data : data,
                        async : false,
                        cache : false,
                        success : function(data) {
                            if (data.success) {
                                window.location.reload();
                            }
                        }
                    });
            },function(){});
        }else{
            W.$.dialog.confirm('您确定修改好，重新提交申报书吗?', function() {
                $.ajax({
                    url : url,
                    type : "POST",
                    dataType : "json",
                    data : data,
                    async : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            window.location.reload();
                        }
                    }
                });
        },function(){},windowapi);
        }
    }