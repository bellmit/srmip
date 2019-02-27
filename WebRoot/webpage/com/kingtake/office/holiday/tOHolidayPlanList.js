function doHolidayPlanSubmit(id) {
    var operateUrl = 'tOHolidayPlanController.do?goSelectOperator';
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
    var url = "tOHolidayPlanController.do?doSubmit";
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
                $("#tOHolidayPlanList").datagrid('reload');
            }
        }
    });
}

// 接收交班材料
function doRecieve(id) {
    var url = "tOHolidayPlanController.do?doRecieve";
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
                $("#tOHolidayPlanList").datagrid('reload');
            }
        }
    });
}
// 退回交班材料
function doReturn(id) {
    $.messager.confirm('确认', '您确认退回该记录吗？', function(r) {
        if (r) {
            openMsgDialog(id);
        }
    });
}

//打开弹窗填写修改意见
function openMsgDialog(id){
  var url = "tOHolidayPlanController.do?goPropose&id="+id;
  var title = "填写修改意见";    
  $.dialog({
        content : 'url:' + url,
        title : '提出修改意见',
        lock : true,
        opacity : 0.3,
        width : '450px',
        height : '120px',
        ok : function() {
            iframe = this.iframe.contentWindow;
            var msgText = iframe.getMsgText();
            var proposeIframe = iframe;
            $.ajax({
                async : false,
                cache : false,
                type : 'POST',
                data : {
                    id : id,
                    msgText:msgText
                },
                url : "tOHolidayPlanController.do?doReturn",// 请求的action路径
                error : function() {// 请求失败处理函数
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    var msg = d.msg;
                    tip(msg);
                    if (d.success) {
                        $("#tOHolidayPlanList").datagrid('reload');
                    }
                }
            });
        },
        cancel : function() {
           reloadTable();
        },
    }).zindex();
}


function dbClickRow(rowIndex, rowData) {
    gridname = 'tOHolidayPlanList';
    var url = 'tOHolidayPlanController.do?goUpdate';
    url += '&load=detail&id='+rowData.id;
    var title = '查看假前工作计划';
    var width = null;
    var height = null;
    createdetailwindow(title,url,width,height);
}

//数据编辑
function goUpdate(id){
    var url = "tOHolidayPlanController.do?goUpdate&id="+id;
    createwindow("编辑",url,700,350);
}

//查看修改意见
function viewMsgText(id){
    $.dialog({
        content : 'url:tOHolidayPlanController.do?goPropose&id='+id,
        title : '提出修改意见',
        lock : true,
        opacity : 0.3,
        width : '450px',
        height : '120px',
        cancel : function() {
        },
    }).zindex();
}

