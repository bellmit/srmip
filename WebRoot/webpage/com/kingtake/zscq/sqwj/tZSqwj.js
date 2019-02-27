//提交申请
function submitFile(id) {
     $.dialog.confirm('您确认已经上传申请文件完毕，发送审查吗？', function() {
          doSubmit(id);
    },function(){},windowapi);

}

function doSubmit(id) {
    var url = "tZSqwjController.do?doSubmit";
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        data : {
            'id' : id
        },
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            var d = $.parseJSON(data);
            var msg = d.msg;
            tip(msg);
            if (d.success) {
                window.location.reload();
            }
        }
    });
}

//确认
function goConfirm(id){
    var url = "tZSqwjController.do?goConfirm&id="+id;
    var title = "确认";
    if(typeof(windowapi) == 'undefined'){
        $.dialog({
            content: 'url:'+url,
            lock : true,
            width:500,
            height: 200,
            title:title,
            opacity : 0.3,
            fixed:true,
            cache:false, 
            ok:function(){
                iframe = this.iframe.contentWindow;
                var qrzt = iframe.getQrzt();
                var xgyj = iframe.getMsgText();
                doConfirm(id,qrzt,xgyj);
            },
            okVal:'确认',
            cancelVal: '关闭',
            cancel: true 
        }).zindex();
    }else{
        W.$.dialog({
            content: 'url:'+url,
            lock : true,
            width:500,
            height: 200,
            parent:windowapi,
            title:title,
            opacity : 0.3,
            fixed:true,
            cache:false, 
            ok:function(){
                iframe = this.iframe.contentWindow;
                var qrzt = iframe.getQrzt();
                var xgyj = iframe.getMsgText();
                doConfirm(id,qrzt,xgyj);
            },
            okVal:'确认',
            cancelVal: '关闭',
            cancel: true 
        }).zindex();
    }
}

//确认
function doConfirm(id,qrzt,xgyj){
    $.ajax({
       url:'tZSqwjController.do?doConfirm',
       data:{id:id,qrzt:qrzt,xgyj:xgyj},
       cache:false,
       dataType:'json',
       success:function(data){
           tip(data.msg);
           if(data.success){
               window.location.reload();
           }
       }
    });
}