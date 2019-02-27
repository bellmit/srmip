//提交交班材料
function doWorkPointSubmit(id) {
    var operateUrl = 'tPmDeclareController.do?goSelectOperator2';
    $.messager.confirm('确认', '您确认提交该记录吗？', function(r) {
        if (r) {
            // 打开选择人窗口
            var width = 640;
            var height = 180;
            var title = "选择接收人";

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
                        if (realName == "") {
                            return false;
                        }
                        submit(id, userId, realName);
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
                        if (realName == "") {
                            return false;
                        }
                        submit(id, userId, realName);
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            }
        }
    });
}

function submit(id, userId, realName) {
    var url = "tOWorkPointController.do?doSubmit";
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        data : {
            'id' : id,
            'receiverId' : userId,
            'receiverName' : realName
        },
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            var d = $.parseJSON(data);
            var msg = d.msg;
            tip(msg);
            if (d.success) {
                $("#tOWorkPointList").datagrid('reload');
            }
        }
    });
}

// 接收交班材料
function doRecieve(id) {
    var url = "tOWorkPointController.do?doRecieve";
    $.ajax({
        async : false,
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
                $("#tOWorkPointList").datagrid('reload');
            }
        }
    });
}
// 退回交班材料
function doReturn(id) {
    var url = "tOWorkPointController.do?doReturn";
    $.messager.confirm('确认', '您确认退回该记录吗？', function(r) {
        if (r) {
            $.ajax({
                async : false,
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
                        $("#tOWorkPointList").datagrid('reload');
                    }
                }
            });
        }
    });
}

function dbClickRow(rowIndex, rowData) {
    gridname = 'tOWorkPointList';
    var url = 'tOWorkPointController.do?goUpdate';
    url += '&load=detail&id='+rowData.id;
    var title = '查看工作要点';
    var width = null;
    var height = null;
    createdetailwindow(title,url,width,height);
}
