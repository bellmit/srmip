//提交交班材料
function doNewsSubmit(id) {
    var operateUrl = 'tPmDeclareController.do?goSelectOperator2';
    $.messager.confirm('确认', '您确认提交吗？', function(r) {
        submit(id);
    });
};

function submit(id) {
    var url = "tONewsController.do?doSubmit";
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
                $("#tONewsList").datagrid('reload');
            }
        }
    });
}

// 接收交班材料
function doRecieve(id) {
    var url = "tONewsController.do?doRecieve";
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
                $("#tONewsList").datagrid('reload');
            }
        }
    });
}
// 退回交班材料
function doReturn(id) {
    var url = "tONewsController.do?doReturn";
    $.messager.confirm('确认', '您确认退回该要讯吗？', function(r) {
        if (r) {
            var proposerUrl = "tONewsController.do?goPropose&id=" + id;
            var title = "填写修改意见";
            $.dialog({
                content : 'url:' + proposerUrl,
                title : '提出修改意见',
                lock : true,
                opacity : 0.3,
                width : '450px',
                height : '120px',
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var msgText = iframe.getMsgText();
                    $.ajax({
                        async : false,
                        cache : false,
                        type : 'POST',
                        data : {
                            id : id,
                            msgText : msgText

                        },
                        url : url,// 请求的action路径
                        error : function() {// 请求失败处理函数
                        },
                        success : function(data) {
                            var d = $.parseJSON(data);
                            var msg = d.msg;
                            tip(msg);
                            if (d.success) {
                                $("#tONewsList").datagrid('reload');
                            }
                        }
                    });
                },
                cancel : function() {
                    reloadTable();
                },
            }).zindex();
        }
    });
}

function dbClickRow(rowIndex, rowData) {
    gridname = 'tONewsList';
    var url = 'tONewsController.do?goUpdate';
    url += '&load=detail&id=' + rowData.id;
    var title = '查看工作要点';
    var width = 1000;
    var height = '100%';
    createdetailwindow(title, url, width, height);
}
