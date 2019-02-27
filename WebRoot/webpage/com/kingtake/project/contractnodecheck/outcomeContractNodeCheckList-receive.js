function handlerAppr1(title, url, width, height, apprStatus, finish, receiveId, rebutValidFlag){
	//已处理记录，查看详情
	if(apprStatus == undefined){
		url += '&send=false&tip=true';
		$("#tipMsg").val("审核已处理，不能再发送审核");
		tabDetailDialog1(title, url, width, height);
		return;
	}
	
	//审核流程被完成，只能查看详情
	if(apprStatus == finish){
		url += '&send=false&tip=true';
		$("#tipMsg").val("审核流程状态被设置为已完成，不能再进行审核");
		tabDetailDialog1(title, url, width, height);
		return;
	}
	
	var dialogId = "apprInfo";
	var apprUrl = 'tPmApprLogsController.do?goUpdate&id=' + receiveId;
	if(rebutValidFlag == false){
		apprUrl += '&rebutValidFlag=' + rebutValidFlag;
	}
	tabApprDialog1(title, url, width, height, dialogId, apprUrl);
}

function tabDetailDialog1(title, url, width, height, dialogId){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			id:dialogId,
			content:'url:'+url,
			title:title,
			width:width,
			height:height,
			lock : true,
			opacity : 0.3,
			cache:false,
			cancelVal: '关闭',
			cancel: function(){
				//对列表信息进行了修改，需要reload
				iframe = this.iframe.contentWindow;
				var changeFlag = $("#changeFlag", iframe.document).val();
				if(changeFlag == "true"){
					reloadTable();
				}
			}
		}).zindex();
	}else{
		W.$.dialog({
			id:dialogId,
			parent:windowapi,
			content:'url:'+url,
			title:title,
			width:width,
			height:height,
			lock : true,
			opacity : 0.3,
			cache:false,
			cancelVal: '关闭',
			cancel: function(){
				//对列表信息进行了修改，需要reload
				iframe = this.iframe.contentWindow;
				var changeFlag = $("#changeFlag", iframe.document).val();
				if(changeFlag == "true"){
					reloadTable();
				}
			}
		}).zindex();
	}
}

function tabApprDialog1(title, url, width, height, dialogId, apprUrl){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	var dialog = $.dialog({	
		id:dialogId,
		content:'url:'+url,
		title:title,
		width:width,
		height:height,
		lock: true,
		opacity : 0.3,
		cache:false
	});
	if(url.indexOf("&suggestion=false")==-1){
	    dialog.button({
            name : '填写审核意见',
            callback : function() {
            	createChildWindowb('审核', apprUrl, 450, 230, this);
                return false;
            },
            focus : true
        });
	}
	dialog.button({
        name:'关闭',
        callback:function(){
            iframe = this.iframe.contentWindow;
            var changeFlag = $("#changeFlag", iframe.document).val();
            if(changeFlag == "true"){
                var iframes = $("iframe", parent.document);
                for (var i = 0; i < iframes.length; i++) {
                    var currTab = iframes[i]; //获得iframe
                    var src = $(currTab).attr('src');
                    $(currTab).attr("src", src);
                }
            }
        }
    });
}

//-------4：在底层窗口写的方法-------
function createChildWindowb(title, url, width, height, parent){
	width = width?width:700;
	height = height?height:400;
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	var dialog;
	if (typeof (windowapi) == 'undefined') {
		dialog = $.dialog({
			id:'auditDialog',
			content:'url:'+url,
			width:width,
			height:height,
			title:title,
			lock : true,
			opacity : 0.3,
			cache:false,
			button:[{
				name:'继续流转',
				focus:true,
				callback:function(){
					iframe = this.iframe.contentWindow;
					var suggestionCode = $("input:radio[name=suggestionCode]:checked",iframe.document).val();
					var suggestionContent = $("#suggestionContent",iframe.document).val();
					var apprId = $("#apprId",iframe.document).val();
					var apprType = $("#apprType",iframe.document).val();
					apprType = apprType.substring(0,2);
					var recId = $("#id",iframe.document).val();
					var senderId = $("#senderId",iframe.document).val();
					var senderName = $("#senderName",iframe.document).val();
					var param = {};
					param['suggestionCode'] = suggestionCode;
					param['suggestionContent'] = suggestionContent;
					param['apprId'] = apprId;
					param['apprType'] = apprType;
					param['recId'] = recId;
					param['senderId'] = senderId;
					param['senderName'] = senderName;
					sendAppr(param);
				}
				},{
					name:'结束流程',
					callback:function(){
						iframe = this.iframe.contentWindow;
						var apprId = $("#apprId",iframe.document).val();
						var apprType = $("#apprType",iframe.document).val();
						apprType = apprType.substring(0,2);
						var recId = $("#id",iframe.document).val();
						var suggestionCode = $("input:radio[name=suggestionCode]:checked",iframe.document).val();
						var suggestionContent = $("#suggestionContent",iframe.document).val();
						if(suggestionCode=='0'){
							dialog.content.$.messager.alert('警告','结束流程是指整个审核流程通过，审核意见为驳回不能结束流程！');  
							return false;
						}
						var param = {};
						param['suggestionCode'] = suggestionCode;
						param['suggestionContent'] = suggestionContent;
						param['apprId'] = apprId;
						param['apprType'] = apprType;
						param['recId'] = recId;
						var operateUrl = 'tPmDeclareController.do?goSelectOperator2';
					     // 打开选择人窗口
					        var width = 640;
					        var height = 180;
					        var title = "请选择计划处接收人";
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
					                    param['receiverId']=userId;
					                    param['receiverName']=realName;
					                    updateFinishFlag1(param);  
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
					                    param['receiverId']=userId;
					                    param['receiverName']=realName;
					                    updateFinishFlag1(param);  
					                },
					                cancelVal : '关闭',
					                cancel : true
					            }).zindex();
					        }  
						return false;
					}
				}],
		    cancelVal: '关闭',
		    cancel: true
		}).zindex();
	} else {
		dialog = W.$.dialog({
			id:'auditDialog',
			content: 'url:'+url,
			width:width,
			height:height,
			title:title,
			lock : true,
			opacity : 0.3,
			parent:windowapi,
			cache:false,
			button:[{
				name:'继续流转',
				focus:true,
				callback:function(){
					iframe = this.iframe.contentWindow;
					var suggestionCode = $("input:radio[name=suggestionCode]:checked",iframe.document).val();
					var suggestionContent = $("#suggestionContent",iframe.document).val();
					var apprId = $("#apprId",iframe.document).val();
					var apprType = $("#apprType",iframe.document).val();
					apprType = apprType.substring(0,2);
					var recId = $("#id",iframe.document).val();
					var senderId = $("#senderId",iframe.document).val();
					var senderName = $("#senderName",iframe.document).val();
					var param = {};
					param['suggestionCode'] = suggestionCode;
					param['suggestionContent'] = suggestionContent;
					param['apprId'] = apprId;
					param['apprType'] = apprType;
					param['recId'] = recId;
					param['senderId'] = senderId;
					param['senderName'] = senderName;
					sendAppr(param);
				}
				},{
					name:'结束流程',
					callback:function(){
						iframe = this.iframe.contentWindow;
						var apprId = $("#apprId",iframe.document).val();
						var apprType = $("#apprType",iframe.document).val();
						apprType = apprType.substring(0,2);
						var recId = $("#id",iframe.document).val();
						var suggestionCode = $("input:radio[name=suggestionCode]:checked",iframe.document).val();
						var suggestionContent = $("#suggestionContent",iframe.document).val();
						if(suggestionCode=='0'){
							dialog.content.$.messager.alert('警告','结束流程是指整个审核流程通过，审核意见为驳回不能结束流程！');  
							return false;
						}
						var param = {};
						param['suggestionCode'] = suggestionCode;
						param['suggestionContent'] = suggestionContent;
						param['apprId'] = apprId;
						param['apprType'] = apprType;
						param['recId'] = recId;
						var operateUrl = 'tPmDeclareController.do?goSelectOperator2';
					     // 打开选择人窗口
					        var width = 640;
					        var height = 180;
					        var title = "请选择计划处接收人";
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
					                    param['receiverId']=userId;
					                    param['receiverName']=realName;
					                    updateFinishFlag1(param);  
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
					                    param['receiverId']=userId;
					                    param['receiverName']=realName;
					                    updateFinishFlag1(param);  
					                },
					                cancelVal : '关闭',
					                cancel : true
					            }).zindex();
					        }  
						return false;
					   }
				}],
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
}

//-------4：完成流程-------
function updateFinishFlag1(param){
	var url1="tPmContractNodeCheckController.do?updateFinishFlag";
	$.ajax({
		cache : false,
		type : 'POST',
		url : url1,
		data : param,
		success : function(data) {
			var d = $.parseJSON(data);
			tip(d.msg);
			if(d.success){
				reloadTable();
				var apprInfo = $.dialog.list['apprInfo'];
				apprInfo.close();
				var auditDialog = $.dialog.list['auditDialog'];
				auditDialog.close();
			}
		}
	});
}

function tabDetailDialog2(title, url, width, height, flag ,apprId){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	var buttons=[];
	if(flag=='1'){
		buttons = [{
			 name : '同意付款',
			 focus:true,
	        callback : function() {
	       	 doPassPay(apprId);
	        }
		},{
			name:"不同意付款",
			callback:function(){
				openMsgDialog(apprId);
				return false;
			}
			
		}];
	}
	
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			id:"apprInfo",
			content:'url:'+url,
			title:title,
			width:width,
			height:height,
			lock : true,
			opacity : 0.3,
			cache:false,
			button:buttons,
			cancelVal: '关闭',
			cancel: function(){
				//对列表信息进行了修改，需要reload
				iframe = this.iframe.contentWindow;
				var changeFlag = $("#changeFlag", iframe.document).val();
				if(changeFlag == "true"){
					reloadTable();
				}
			}
		}).zindex();
	}else{
		W.$.dialog({
			id:"apprInfo",
			parent:windowapi,
			content:'url:'+url,
			title:title,
			width:width,
			height:height,
			lock : true,
			opacity : 0.3,
			cache:false,
			button:buttons,
			cancelVal: '关闭',
			cancel: function(){
				//对列表信息进行了修改，需要reload
				iframe = this.iframe.contentWindow;
				var changeFlag = $("#changeFlag", iframe.document).val();
				if(changeFlag == "true"){
					reloadTable();
				}
			}
		}).zindex();
	}
	
}

function doPassPay(id){
	$.ajax({
		url:'tPmContractNodeCheckController.do?doPassPay',
		cache:false,
		data:{id:id},
		type:'POST',
		dataType:'json',
		success:function(data){
			 tip(data.msg);
             if (data.success) {
                 reloadTable();
             }
		}
	});
}

//打开弹窗填写修改意见
function openMsgDialog(id){
    var url = "tPmContractNodeCheckController.do?goPropose&id="+id;
    var title = "填写修改意见";        
  $.dialog({
  content : 'url:' + url,
          title : '提出修改意见',
          lock : true,
          opacity : 0.3,
          width : '450px',
          height : '120px',
          ok : function() {
              iframe = this.iframe.contentWindow;
              var msgText = iframe.getMsgText();
              var proposeIframe = iframe;
              $.ajax({
                  url : 'tPmContractNodeCheckController.do?doPropose',
                  data : {
                      id : id,
                      msgText : msgText
                  },
                  type : 'post',
                  dataType:'json',
                  success : function(data) {
                	  var dialog = $.dialog.list['apprInfo'];
                      tip(data.msg);
                      reloadTable();
                      dialog.close();
                  }
              });
          },
          cancel : function() {
	           reloadTable();
          },
      }).zindex();
  }
