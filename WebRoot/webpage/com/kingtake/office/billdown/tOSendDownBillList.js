function doBorrowBillSubmit(id,index) {
    var operateUrl = 'tOSendDownBillController.do?goSelectOperator&mode=multiply&regId='+id+'&role=send';
            // 打开选择人窗口
            var width = 640;
            var height = 180;
            var title = "发文下达";
            if (typeof (windowapi) == 'undefined') {
                $.dialog({
                    content : 'url:' + operateUrl,
                    lock : true,
                    width : width,
                    height : height,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        var realName = iframe.getRealName();
                        var userId = iframe.getUserId();
                        var showFlag = iframe.getShowFlag();
                        if (realName == "") {
                            return false;
                        }
                        var param = {
                                'regId' : id,
                                'receiverId' : userId,
                                'receiverName' : realName,
                                'showFlag' : showFlag
                            }
                            submit(param);
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            } else {
                W.$.dialog({
                    content : 'url:' + operateUrl,
                    lock : true,
                    width : width,
                    height : height,
                    parent : windowapi,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        var realName = iframe.getRealName();
                        var userId = iframe.getUserId();
                        var showFlag = iframe.getShowFlag();
                        if (realName == "") {
                            return false;
                        }
                        var param = {
                                'regId' : id,
                                'receiverId' : userId,
                                'receiverName' : realName,
                                'showFlag' : showFlag
                            }
                            submit(param);
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            }
}

function doSendDownSubmit(id,downId,index) {
    var operateUrl = 'tOSendDownBillController.do?goSelectOperator&mode=multiply&regId='+id;
    // 打开选择人窗口
    var width = 640;
    var height = 180;
    var title = "发文下达";
    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:' + operateUrl,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var realName = iframe.getRealName();
                var userId = iframe.getUserId();
                var showFlag = iframe.getShowFlag();
                if (realName == "") {
                    return false;
                }
                var param = {
                        'regId' : id,
                        'id' : downId,
                        'receiverId' : userId,
                        'receiverName' : realName,
                        'showFlag' : showFlag
                    }
                    submit(param);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    } else {
        W.$.dialog({
            content : 'url:' + operateUrl,
            lock : true,
            width : width,
            height : height,
            parent : windowapi,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var realName = iframe.getRealName();
                var userId = iframe.getUserId();
                var showFlag = iframe.getShowFlag();
                if (realName == "") {
                    return false;
                }
                var param = {
                    'regId' : id,
                    'id' : downId,
                    'receiverId' : userId,
                    'receiverName' : realName,
                    'showFlag' : showFlag
                }
                submit(param);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
}

function submit(param) {
    var url = "tOSendDownBillController.do?doSend";
    $.ajax({
        cache : false,
        type : 'POST',
        data : param,
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            var d = $.parseJSON(data);
            var msg = d.msg;
            tip(msg);
            if (d.success) {
                $("#tOSendDownBillList").datagrid('reload');
                $('#userListpanel').panel("refresh");
            }
        }
    });
}

// 接收
function doRecieve(id) {
    var url = "tOSendDownBillController.do?doRecieve";
    $.ajax({
        cache : false,
        type : 'POST',
        data : {
            id : id
        },
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            var d = $.parseJSON(data);
            var msg = d.msg;
            tip(msg);
            if (d.success) {
                $("#tOSendDownBillList").datagrid('reload');
            }
        }
    });
}


function dbClickRow(rowIndex, rowData) {
    gridname = 'tOSendDownBillList';
    doViewBill(rowData.id,rowData.downId,rowIndex);
}

function dbClickRow2(rowIndex, rowData) {
    gridname = 'tOSendDownBillList';
    doViewBill2(rowData.id,rowIndex);
}

//查看公文
function doViewBill(id,downId,index){
    var userId = $("#userId").val();
    var title = "下达公文";
    var url = "tOSendDownBillController.do?goViewDownBill&id="+id+"&downId="+downId;
    detailWindow(title, url,"100%","100%");
}
//查看公文
function doViewBill2(id,index){
    var userId = $("#userId").val();
    var title = "下达公文";
    var url = "tOSendDownBillController.do?goViewDownBill&id="+id;
    detailWindow(title, url,"100%","100%");
}

function detailWindow(title, addurl,width,height){
    width = width?width:700;
    height = height?height:400;
    if(width=="100%"){
        width = window.top.document.body.offsetWidth;
    }
    if(height=="100%"){
        height =window.top.document.body.offsetHeight-100;
    }
    if(typeof(windowapi) == 'undefined'){
        $.dialog({
            content: 'url:'+addurl,
            lock : true,
            width:width,
            height: height,
            title:title,
            opacity : 0.3,
            fixed:true,
            cache:false, 
            cancelVal: '关闭',
            cancel: function(){
                $("#tOSendDownBillList").datagrid('reload');
            }
        }).zindex();
    }else{
        W.$.dialog({
            content: 'url:'+addurl,
            lock : true,
            width:width,
            height: height,
            parent:windowapi,
            title:title,
            opacity : 0.3,
            fixed:true,
            cache:false, 
            cancelVal: '关闭',
            cancel: function(){
                $("#tOSendDownBillList").datagrid('reload');
            }
        }).zindex();
    }
}


var li_east = 0;
//查看类型链接
function queryTypeValue(id) {
    var title = '查看接收情况';
    if (li_east == 0) {
        $('#sendDownLayout').layout('expand', 'east');
    }
    $('#sendDownLayout').layout('panel', 'east').panel('setTitle', title);
    $('#userListpanel').panel("refresh", "tOSendDownBillController.do?goReceiveLogList&billId=" + id);
}

//下达标志格式化
function downFlagFormatter(value,row,index){
    if(value=='1'){
        return '<font color="red">已下达</font>';
    }else{
        return '<font color="skyblue">未下达</font>';
    }
}
