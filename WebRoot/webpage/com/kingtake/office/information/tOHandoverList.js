//提交交班材料
function doHandoverSubmit(id){
    gridname=id;
    var operateUrl = 'tPmDeclareController.do?goSelectOperator2';
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
                        doSubHandover(id, userId, realName);
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
                        doSubHandover(id, userId, realName);
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            }
}

function doSubHandover(id, userId, realName){
    var url = "tOHandoverController.do?doSubmit";
    $.ajax({
        cache : false,
        type : 'POST',
        data : {
            id:id,
            receiver:userId,
            receiverName:realName
        },
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
            var d = $.parseJSON(data);
            if (d.success) {
                var msg = d.msg;
                tip(msg);
                $("#tOHandoverList").datagrid('reload');
            }
        }
    });
}

//接收交班材料
function doRecieve(id){
    gridname=id;
    url = "tOHandoverController.do?doRecieve";
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        data : {id:id},
        url : url,// 请求的action路径
        error : function() {// 请求失败处理函数
        },
        success : function(data) {
        	 var d = $.parseJSON(data);
             if (d.success) {
                 var msg = d.msg;
                 tip(msg);
                 $("#tOHandoverList").datagrid('reload');
             }
        }
    });
}
//退回交班材料
function doReturn(id){
    gridname=id;
    url = "tOHandoverController.do?doReturn";
    $.messager.confirm('确认','您确认退回该记录吗？',function(r){    
        if (r){    
            $.ajax({
                async : false,
                cache : false,
                type : 'POST',
                data : {id:id},
                url : url,// 请求的action路径
                error : function() {// 请求失败处理函数
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        tip(msg);
                        $("#tOHandoverList").datagrid('reload');
                    }
                }
            });
        }    
    }); 
}
    
function dbClickRow(rowIndex, rowData) {
    rowid = rowData.id;
    gridname = 'tOHandoverList';
    detail('查看交班材料','tOHandoverController.do?goUpdate','tOHandoverList',null,350)
}

function goCopy(title,url,grid,width,height){
    var checked = $("#"+grid).datagrid("getChecked");
    if(checked.length==0){
        tip("请勾选需要复制的交班材料！");
        return;
    }
    var ids = [];
    for(var i=0;i<checked.length;i++){
        ids.push(checked[i].id);
    }
    add(title,"tOHandoverController.do?goCopy&ids="+ids,grid,width,height);
}
