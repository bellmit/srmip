//发送消息
function sendMsg(msgType){
    var url = 'commonMessageController.do?commonMsg';
    var width = 700;
    var height = 400;
    var title = "发送消息";
    if(typeof(windowapi) == 'undefined'){
        $.dialog({
            content: 'url:'+url,
            lock : true,
            width:width,
            height:height,
            title:title,
            data:{msgType:msgType},
            opacity : 0.3,
            cache:false,
            ok: function(){
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancelVal: '关闭',
            cancel: true /*为true等价于function(){}*/
        }).zindex();
    }else{
        W.$.dialog({
            content: 'url:'+url,
            lock : true,
            width:width,
            height:height,
            data:{msgType:msgType},
            parent:windowapi,
            title:title,
            opacity : 0.3,
            cache:false,
            ok: function(){
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancelVal: '关闭',
            cancel: true 
        }).zindex();
    }
}

//发送消息
function sendOnlineMsg(userId,userName,msgType){
    var url = 'commonMessageController.do?onlineMsg';
    var width = 450;
    var height = 250;
    var title = "向 ["+userName+"] 发送消息";
    if(typeof(windowapi) == 'undefined'){
        $.dialog({
            content: 'url:'+url,
            lock : true,
            width:width,
            height:height,
            title:title,
            data:{userId:userId,userName:userName,msgType:msgType},
            opacity : 0.3,
            cache:false,
            ok: function(){
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancelVal: '关闭',
            cancel: true /*为true等价于function(){}*/
        }).zindex();
    }else{
        W.$.dialog({
            content: 'url:'+url,
            lock : true,
            width:width,
            height:height,
            data:{userId:userId,userName:userName,msgType:msgType},
            parent:windowapi,
            title:title,
            opacity : 0.3,
            cache:false,
            ok: function(){
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancelVal: '关闭',
            cancel: true 
        }).zindex();
    }
}