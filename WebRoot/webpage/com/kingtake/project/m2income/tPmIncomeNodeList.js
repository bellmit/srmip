function updateAuditFlag(id){
	$.ajax({
        cache : false,
        type : 'POST',
        data : {"id":id},
        url : "tPmIncomeNodeController.do?doUpdateAuditFlag",
        success : function(data) {
        	var d = $.parseJSON(data);
        	if(d.success){
        		var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					reloadtPmIncomeNodeList();
				}
        	}
        }
    });
}