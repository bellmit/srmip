//查看立项论证
function viewApproval(title,url,list,width,height){
    var rows = getSelectRows();
    if(rows.length!=1){
        tip("请选择一条记录！");
        return;
    }
    var row = rows[0];
    var projectId = row.id;
    if (projectId != "") {
        addTab('立项管理', 'tPmProjectApprovalController.do?goApprovalMgr&menu=2&projectId='+projectId, 'default');
    }
    
}