function goDelete(id){
	$.ajax({
        cache : false,
        type : 'POST',
        url : "tOVehicleUseController.do?goDel",
        data : {"id":id},
        success : function(data) {
        	var d = $.parseJSON(data);
        	if(d.success){
	        	$.dialog.confirm(d.msg, function(){
	        		$.ajax({
	        			cache : false,
	        			type : 'GET',
	        			url : "tOVehicleUseController.do?doDel",// 请求的action路径
	        			data : {"id":id},
	        			success : function(data) {
	        				var d = $.parseJSON(data);
	        				if (d.success) {
	        					var msg = d.msg;
	        					tip(msg);
	        					reloadTable();
	        				}
	        			}
	        		});
	        	}, function(){
	        	}).zindex();
        	}
        }
    });
}