//提交交班材料
function doWorkPointSubmit(id) {
    var operateUrl = 'tOHolidayPlanController.do?goSelectOperator&mode=multiply';
    $.messager.confirm('确认', '您确认发送该记录吗？', function(r) {
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
    var url = "tOSummaryController.do?doSubmit";
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
                $("#tOSummaryList").datagrid('reload');
            }
        }
    });
}

// 接收交班材料
function doRecieve(id) {
    var url = "tOSummaryController.do?doRecieve";
    $.messager.confirm('确认', '您确认该总结材料处理完毕了吗？', function(r) {
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
                        $("#tOSummaryList").datagrid('reload');
                    }
                }
            });
        }
    });
}
// 退回交班材料
function doReturn(id) {
    var url = "tOSummaryController.do?doReturn";
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
                        $("#tOSummaryList").datagrid('reload');
                    }
                }
            });
        }
    });
}

function dbClickRow(rowIndex, rowData) {
    gridname = 'tOSummaryList';
    var url = 'tOSummaryController.do?goUpdate';
    url += '&load=detail&id='+rowData.id;
    var title = '查看总结材料';
    var width = '100%';
    var height = '100%';
    createdetailwindow(title,url,width,height);
}

function goViewReceiveList(){
    var rows = $("#tOSummaryList").datagrid("getChecked");
    if(rows.length==0||rows.length>1){
        tip("请选择一行记录查看！");
        return ;
    }
    var row = rows[0];
    var id = row.id;
    createdetailwindow("查看处理情况","tOSummaryController.do?goReceiveLogList&researchId="+id+"&sendType=summary", 700,400);
}
