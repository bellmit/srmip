var li_east = 0;
//查看类型链接
function viewDetail(id){
    var title = '录入采购计划明细';
    if(li_east == 0){
        $('#planLayout').layout('expand','east');
    }
    $('#planLayout').layout('panel','east').panel('setTitle', title);
    $('#userListpanel').panel("refresh", "tBPurchasePlanMainController.do?goForDetail&purchasePlanId=" + id);
}

//子表加载完成
function loadSuccess() {
    $('#planLayout').layout('panel','east').panel('setTitle', "");
    $('#planLayout').layout('collapse','east');
    $('#userListpanel').empty();
}