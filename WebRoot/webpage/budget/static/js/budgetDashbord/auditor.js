//协作分配金额提交审核
function submitAudit(id) {
	$.ajax({
        url:path + 'tPmIncomeApplyController.do?checkInfo',
        type:'POST',
        dataType:'json',
        cache:'false',
        data:{'id':id},
        success:function(data){
           if(!data.success){
        	   tip(data.msg);
           }               
           if(data.success){
        	   var url = 'tPmDeclareController.do?goSelectOperator2';
               if(typeof(windowapi) == 'undefined'){
                   $.dialog.confirm("确定提交审核吗？", function() {
                       openOperatorDialog("选择审批人", url, 640, 180, id);
                   }, function() {
                   }).zindex();
               }else{
                   W.$.dialog.confirm("确定提交审核吗？", function() {
                       openOperatorDialog("选择审批人", url, 640, 180, id);
                   }, function() {
                   },windowapi).zindex();
               }
           }
        }
    });        
}
//打开选择人窗口
function openOperatorDialog(title, url, width, height,id) {
    var width = width ? width : 700;
    var height = height ? height : 400;
    if (width == "100%") {
        width = window.top.document.body.offsetWidth;
    }
    if (height == "100%") {
        height = window.top.document.body.offsetHeight - 100;
    }

    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var userId = iframe.getUserId();
                if (userId == "") {
                    return false;
                }
                var realname = iframe.getRealName();
                var deptId = iframe.getDepartId();
                doAudit(id,userId,realname,deptId);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    } else {
        W.$.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            parent : windowapi,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var userId = iframe.getUserId();
                if (userId == "") {
                    return false;
                }
                var realname = iframe.getRealName();
                var deptId = iframe.getDepartId();
                doAudit(id,userId,realname,deptId);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
}

//审核
function doAudit(id,userId,realname,deptId){
  $.ajax({
      url:'tPmIncomeApplyController.do?doAudit',
      type:'POST',
      dataType:'json',
      cache:'false',
      data:{'id':id,"opt":'submit','userId':userId,'realname':realname,'deptId':deptId},
      success:function(data){
         tip(data.msg);
         saveOrUpdateKz(id);
         if(data.success){
        	 table.reload("projectSchoolTable", {
                 T_P_ID : budgetTotalObj.ID,
                 FEE_TYPE: budgetTotalObj.FEE_TYPE
             });
        	 
         }
      }
  });
}
//添加开支
function saveOrUpdateKz(id){
	for(var i = 0 ; i < projectSchoolList.length ;i++){
		var data = projectSchoolList[i];
		if(id == data.INCOME_ID){
			xzFundData = data;
		}
	}
	var param = {
    	PROJECT_NO: budgetTotalObj.PROJECT_NO,
    	KJPZH: xzFundData.PROJECT_NO,
    	PROJECT_NAME: xzFundData.PROJECT_NAME,
    	APPLY_AMOUNT: xzFundData.D_SH_MONEY,
    	APPLY_YEAR: xzFundData.APPLY_YEAR,
    	NUM: '13',
    	isExdit: false
	};
	$.ajax({
        url : path + "ysController.do?saveOrUpdateKz",
        type : "post",
        data : param,
        success : function(r) {
        	table.reload("projectSchoolTable", {
                T_P_ID : budgetTotalObj.ID,
                FEE_TYPE: budgetTotalObj.FEE_TYPE
            });
        	xzFundData = null;
        }
    });
}