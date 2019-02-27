//弹出出差报告
function goAddUpdate(title,url,id,width,height){
    gridname=id;
    var rowsData = $('#'+id).datagrid('getSelections');
    if (!rowsData || rowsData.length==0) {
        tip('请选择编辑项目');
        return;
    }
    if (rowsData.length>1) {
        tip('请选择一条记录再编辑');
        return;
    }
    url += '&id='+rowsData[0].id;
    createwindow(title,url,width,height);
}

//列表弹出出差报告
function goReportAddUpdate(id){
    var width = 800;
    var title = '出差情况阅批单';
    gridname=id;
    url = "tOTravelReportController.do?goAddUpdate&id="+id ;
    createwindow(title,url,width);
}