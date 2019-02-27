//复制功能
function copy(title,url, id,width,height){
    gridname=id;
    var rowsData = $('#'+id).datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择复制的登记项');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再复制');
        return;
    }
    var id = rowsData[0].id;
    $.dialog({
           content: 'url:tOSendReceiveRegController.do?goCopy&id='+id,
           lock : true,
           title:"复制",
           width : window.top.document.body.offsetWidth,
           height : window.top.document.body.offsetHeight-100,
           opacity : 0.3,
           cache:false,
           okVal:'保存',
           ok:function(){
               iframe = this.iframe.contentWindow;
               saveObj();
               reloadtOSendReceiveRegList();
               return false;
           },
           cancelVal: '关闭',
           cancel: function(){
               reloadtOSendReceiveRegList();
           }
       }).zindex();
}

//查看历史版本
function goHistory(title,url, id,width,height){
    gridname=id;
    var rowsData = $('#'+id).datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择查看的公文');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再查看');
        return;
    }
    var id = rowsData[0].id;
        var url = "tOSendReceiveRegController.do?goHistory&regId="+id;
        $.dialog({
            content : 'url:'+url,
            lock : true,
            width : window.top.document.body.offsetWidth,
            height : window.top.document.body.offsetHeight - 100,
            left : '0%',
            top : '0%',
            title : "查看公文历史版本",
            opacity : 0.3,
            cache : false,
            cancelVal : '关闭',
            cancel : true
        });
}