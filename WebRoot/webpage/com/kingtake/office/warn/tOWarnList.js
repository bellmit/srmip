var li_east = 0;
//查看类型链接
function queryTypeValue(id){
    var title = '录入接收人';
    if(li_east == 0){
        $('#main_list').layout('expand','east');
    }
    $('#main_list').layout('panel','east').panel('setTitle', title);
    $('#subListpanel').panel("refresh", "tOWarnController.do?goReceiveList&id=" + id);
}

//子表加载完成
function loadSuccess() {
    $('#main_codeType_list').layout('panel','east').panel('setTitle', "");
    $('#main_codeType_list').layout('collapse','east');
    $('#userListpanel').empty();
}