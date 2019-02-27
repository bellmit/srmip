var li_east = 0;
//查看类型链接
function queryTypeValue(id){
    var title = '录入环境试验费标准明细';
    if(li_east == 0){
        $('#main_Hjsy_list').layout('expand','east');
    }
    $('#main_Hjsy_list').layout('panel','east').panel('setTitle', title);
    $('#userListpanel').panel("refresh", "tBJgbzHjsyController.do?tBJgbzHjsymxList&id=" + id);
}
