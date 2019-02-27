//提交
function doBorrowBillSubmit(id) {
    $.messager.confirm('确认', '您确认提交审批吗？', function(r) {
        if (r) {
             submit(id);
        }
    });
}

function submit(id) {
    var url = "tOBorrowBillController.do?doSubmit";
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
                $("#tOBorrowBillList").datagrid('reload');
            }
        }
    });
}

//通过
function doPass(id) {
    var url = "tOBorrowBillController.do?goRegSelect&id=" + id;
    var title = "选择收文";
    $.dialog({
        content : 'url:' + url,
        title : title,
        lock : true,
        opacity : 0.3,
        width : window.top.document.body.offsetWidth,
        height : window.top.document.body.offsetHeight-100,
        ok : function() {
            iframe = this.iframe.contentWindow;
            var checked = iframe.selectReg();
            if(checked==null){
                return false;
            }
            var ids = checked.join(",");
            String 
            $.ajax({
                url : 'tOBorrowBillController.do?doPass',
                data : {
                    id : id,
                    ids : ids
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    if (data.success) {
                        reloadTable();
                    }
                }
            });
        },
        cancel : function() {
            reloadTable();
        },
    }).zindex();
}

//审核不通过
function doReturn(id) {
    if (typeof (windowapi) == 'undefined') {
        $.dialog.confirm("确定驳回吗？", function() {
            openMsgDialog(id);
        }, function() {
        }).zindex();
    } else {
        W.$.dialog.confirm("确定驳回吗？", function() {
            openMsgDialog(id);
        }, function() {
        }, windowapi).zindex();
    }
}

//打开弹窗填写修改意见
function openMsgDialog(id) {
    var url = "tOBorrowBillController.do?goPropose&id=" + id;
    var title = "填写意见";
    $.dialog({
        content : 'url:' + url,
        title : '提出意见',
        lock : true,
        opacity : 0.3,
        width : '450px',
        height : '120px',
        ok : function() {
            iframe = this.iframe.contentWindow;
            var msgText = iframe.getMsgText();
            var proposeIframe = iframe;
            $.ajax({
                url : 'tOBorrowBillController.do?doPropose',
                data : {
                    id : id,
                    msgText : msgText
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    if (data.success) {
                        reloadTable();
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
    gridname = 'tOBorrowBillList';
    var url = 'tOBorrowBillController.do?goUpdate';
    url += '&load=detail&id='+rowData.id;
    var title = '查看借阅申请';
    var width = "100%";
    var height = "100%";
    createdetailwindow(title,url,width,height);
}

//检查是否有效
function checkValid(id){
    var checkUrl = "tOBorrowBillController.do?checkValid&id="+id;
    $.ajax({
        url:checkUrl,
        data:{id:id},
        cache:false,
        type:'POST',
        dataType:'json',
        success:function(data){
            if(data.success){
                doViewBill(id);
            }else{
                tip(data.msg);
            }
        }
    })
}

//查看公文
function doViewBill(id){
    var url = "tOBorrowBillController.do?goViewBorrowRecBill&id="+id;
    createdetailwindow("查看借阅公文", url,"100%","100%");
}