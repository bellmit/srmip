//----------------------------------------基础方法------------------------------------

//-------------第一层弹出框：4种（tab）-------------

//-------1：按钮（关闭）-------
function tabDetailDialog(title, url, width, height, dialogId){
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
//			zIndex:1977,
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
//			zIndex:1977,
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

//-------2：按钮（确定、关闭）-------
function tabDialog(title, url, width, height, dialogId){
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
//			zIndex:1977,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	$("#callBackType", iframe.document).val("close");
				saveObj();
				return false;
		    },
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
			content:'url:'+url,
			title:title,
			width:width,
			height:height,
			lock : true,
			opacity : 0.3,
			cache:false,
//			zIndex:1977,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	$("#callBackType", iframe.document).val("close");
				saveObj();
				return false;
		    },
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

function tabDialogNew(title, url, width, height, dialogId){
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
//			zIndex:1977,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	$("#callBackType", iframe.document).val("close");
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: function(){
		    /*	alert("必须填写相关信息并且保存才可关闭");
		    	return false;*/
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
			content:'url:'+url,
			title:title,
			width:width,
			height:height,
			lock : true,
			opacity : 0.3,
			cache:false,
//			zIndex:1977,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	$("#callBackType", iframe.document).val("close");
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: function(){
		    	/*alert("必须填写相关信息并且保存才可关闭");
		    	return false;*/
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

//-------3：按钮（审核、关闭）-------
function tabApprDialog(title, url, width, height, dialogId, apprUrl){
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
                createChildWindow2('审核', apprUrl, 450, 230, this);
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

//-------2：按钮（提交、关闭）-------
function tabDialog2(title, url, width, height, dialogId){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	$.dialog({	
		id:dialogId,
		content:'url:'+url,
		title:title,
		width:width,
		height:height,
		lock : true,
		opacity : 0.3,
		cache:false,
		zIndex:1977,
		button : [{
	    	name : '提交',
	    	callback : function() {
	    		iframe = this.iframe.contentWindow;
		    	$("#callBackType", iframe.document).val("close");
				saveObj();
				return false;
			},
			focus : true
		}],
	    cancelVal: '关闭',
	    cancel: function(){
	    	//对列表信息进行了修改，需要reload
	    	iframe = this.iframe.contentWindow;
	    	var changeFlag = $("#changeFlag", iframe.document).val();
	    	if(changeFlag == "true"){
	    		reloadTable();
	    	}
	    }
	});
}

//-------4：按钮（驳回、确定、关闭）-------
function tabRebutDialog(title, url, width, height, dialogId, apprUrl){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	$.dialog({	
		id:dialogId,
		content:'url:'+url,
		title:title,
		width:width,
		height:height,
		lock : true,
		opacity : 0.3,
		cache:false,
		zIndex:1977,
		button : [{
	    	name : '通过',
	    	callback : function() {
	    		$.ajax({
	    			cache : false,
	    			type : 'GET',
	    			url : apprUrl,
	    			success : function(data) {
	    				var d = $.parseJSON(data);
	    				reloadTable();
	    				tip(d.msg);
	    				var that = this;
	    				that.close();
	    			}
	    		});
				return false;
			},
			focus : true
		},{
	    	name : '驳回',
	    	callback : function() {
				return false;
			}
		}],
	    cancelVal: '关闭',
	    cancel: true
	});
}


//-------5：按钮（完成流程/取消完成流程、关闭）-------
function tabUpdateStateDialog(
		title, url, width, height, dialogId, 
		buttonName, apprId, updateStateUrl){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	$.dialog({	
		id:dialogId,
		content:'url:'+url,
		title:title,
		width:width,
		height:height,
		lock : true,
		opacity : 0.3,
		cache:false,
		zIndex:1977,
		button : [{
	    	name : buttonName,
	    	callback : function() {
	    		var apprInfoDialog = this;
	    		$.ajax({
	    			cache : false,
	    			type : 'POST',
	    			url : updateStateUrl,
	    			data : {"id":apprId},
	    			success : function(data) {
	    				var d = $.parseJSON(data);
	    				if(d.success){
	    					tip(d.msg);
	    					reloadTable();
	    					apprInfoDialog.close();
	    				}else{
	    					//处理失败有两种情况
	    					if(d.obj == "1"){
	    						//失败情况一:还有有效、未处理的审核记录，提示是否确定完成
	    						apprInfoDialog.content.$.dialog({
	    							title:"确认",
	    							icon: 'confirm.gif',
	    							content: d.msg,
	    							lock : true,
	    							opacity : 0.3,
	    							cache:false,
	    							parent:apprInfoDialog,
	    							zIndex:3000,
	    						    ok: function(){
	    						    	$.ajax({
	    									cache : false,
	    									type : 'POST',
	    									url : updateStateUrl+"2",
	    									data : {"id":apprId},
	    									success : function(data) {
	    										var d = $.parseJSON(data);
	    										tip(d.msg);
	    										reloadTable();
	    										//var confirmDialog = this;
	    										apprInfoDialog.close();
	    										//confirmDialog.close();
	    									}
	    								});
	    						    },
	    						    cancelVal: '取消',
	    						    cancel: true
	    						});
	    					}else if(d.obj == "2"){
	    						//失败情况二：流程状态被其他操作改变：提示刷新重新操作
	    						tip(d.msg);
		    					reloadTable();
		    					apprInfoDialog.close();
	    					}
	    				}
	    			}
	    		});
				return false;
			},
			focus : true
		}],
	    cancelVal: '关闭',
	    cancel: true
	});
}




//-------------第二层弹出框：3种-------------

//-------1：确认框-------
function confirmChildDialog(msg){
	var windowapi = frameElement.api;
	$.dialog.setting.zIndex = 1990;
	if (typeof (windowapi) == 'undefined') {
		$.dialog({
			title:"确认",
			icon: 'confirm.gif',
			content: msg,
			lock : true,
			opacity : 0.3,
			cache:false,
			zIndex:3000,
		    ok: function(){
				$('#btn_sub').click();//点击当前页面的确定按钮，是保存审核主表信息
		    },
		    cancelVal: '取消',
		    cancel: true
		});
	} else {
		$.dialog({
			title:"确认",
			icon: 'confirm.gif',
			content: msg,
			lock : true,
			opacity : 0.3,
			cache:false,
			parent:windowapi,
			zIndex:3000,
		    ok: function(){
				$('#btn_sub').click();//点击当前页面的确定按钮，是保存审核主表信息
		    },
		    cancelVal: '取消',
		    cancel: true
		});
	}
}

//-------2：删除确认框-------
function delChildDialog(url, gridName){
	var windowapi = frameElement.api;
	$.dialog.setting.zIndex = 1990;
	if (typeof (windowapi) == 'undefined') {
		$.dialog({
			title:"删除确认",
			icon: 'confirm.gif',
			content: "确定删除该记录吗 ?",
			lock : true,
			opacity : 0.3,
			cache:false,
			zIndex:3000,
		    ok: function(){
		    	doSubmit(url,gridname);
				rowid = '';
		    },
		    cancelVal: '取消',
		    cancel: true
		}).zindex();
	} else {
		$.dialog({
			title:"删除确认",
			icon: 'confirm.gif',
			content: "确定删除该记录吗 ?",
			lock : true,
			opacity : 0.3,
			cache:false,
			zIndex:3000,
			parent:windowapi,
		    ok: function(){
		    	doSubmit(url,gridname);
				rowid = '';
		    },
		    cancelVal: '取消',
		    cancel: true
		});
	}
}

//-------3：在第一层对话框中写的方法-------
function createChildWindow(title, url, width, height){
	var windowapi = frameElement.api;
	
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	if (typeof (windowapi) == 'undefined') {
		$.dialog({
			content:'url:'+url,
			width:width,
			height:height,
			title:title,
			lock : true,
			opacity : 0.3,
			zIndex:3000,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true
		});
	} else {
		$.dialog({
			content: 'url:'+url,
			width:width,
			height:height,
			title:title,
			lock : true,
			opacity : 0.3,
			zIndex:3000,
			parent:windowapi,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true 
		});
	}
}

//-------4：在底层窗口写的方法-------
function createChildWindow2(title, url, width, height, parent){
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
							dialog.content.$.messager.alert('警告','驳回不能结束流程，请点击继续流转！');  
							return false;
						}
						var param = {};
						param['suggestionCode'] = suggestionCode;
						param['suggestionContent'] = suggestionContent;
						param['apprId'] = apprId;
						param['apprType'] = apprType;
						param['recId'] = recId;
						dialog.content.$.messager.confirm('确认','您确认结束该流程,不再需要其他人审核吗？',function(r){    
						    if (r){    
						    	updateFinishFlag(param);   
						    }    
						});  
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
							dialog.content.$.messager.alert('警告','驳回不能结束流程，请点击继续流转！');  
							return false;
						}
						var param = {};
						param['suggestionCode'] = suggestionCode;
						param['suggestionContent'] = suggestionContent;
						param['apprId'] = apprId;
						param['apprType'] = apprType;
						param['recId'] = recId;
						dialog.content.$.messager.confirm('确认','您确认结束该流程,不再需要其他人审核吗?',function(r){    
						    if (r){    
						    	updateFinishFlag(param);   
						    }    
						});  
						return false;
					}
				}],
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
}

//-------------------------弹出第二层数据详情--------------------------------
function createDetailChildWindow(title, url, width, height){
	var windowapi = frameElement.api;
	
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	if (typeof (windowapi) == 'undefined') {
		$.dialog({
			content:'url:'+url,
			width:width,
			height:height,
			title:title,
			lock : true,
			opacity : 0.3,
			zIndex:3000,
			cache:false
		}).zindex();
	} else {
		$.dialog({
			content: 'url:'+url,
			width:width,
			height:height,
			title:title,
			lock : true,
			opacity : 0.3,
			zIndex:3000,
			parent:windowapi,
			cache:false
		}).zindex();
	}
}



//----------------------------------------业务方法------------------------------------

//-------------录入+更新+详情+完成流程 方法（一般是第一层：添加id方便找到）-------------

//-------1：录入方法-------
function addFun(title,url,gname,width,height,dialogId){
	gridname = gname;
	tabDialog(title, url, width, height, dialogId);
}

function addFunNew(title,url,gname,width,height,dialogId){
	gridname = gname;
	tabDialogNew(title, url, width, height, dialogId);
}

//-------2：判断+更新方法-------
function judgeAndUpdateFun(title, updateUrl, gname, width, height, judgeUrl, unEditUrl, dialogId){
	gridname = gname;
	
	//是否选中一条记录
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
	
	judgeUpdate(title,width,height,rowsData[0].id,judgeUrl,updateUrl,unEditUrl,dialogId);
}

function judgeUpdate(title,width,height,id,judgeUrl,updateUrl,unEditUrl,dialogId){
	//判断是否可编辑
	$.ajax({
		cache : false,
		type : 'POST',
		url : judgeUrl,
		data : {"id":id},
		success : function(data) {
			var d = $.parseJSON(data);
			if (!d.success) {
				//不可编辑
				$("#tipMsg").val(d.msg);
				unEditUrl += '&id='+id;
				tabDetailDialog(title, unEditUrl, width, height);
			}else{
				//可编辑
				updateUrl += '&id='+id;
				tabDialog(title,updateUrl,width,height,dialogId);
			}
		}
	});
}

function judgeUpdate2(
		send,finish,rebut,
		judgeUrl,
		title,url,width,height,dialogId,
		updateStateUrl){
	//判断是否可编辑
	$.ajax({
		cache : false,
		type : 'POST',
		url : judgeUrl,
		success : function(data) {
			var d = $.parseJSON(data);
			var apprStatus = d.attributes.apprStatus;
			var apprId = d.attributes.apprId;
			if(send == apprStatus){
				//审核状态为已发送（完成流程按钮/不可编辑/tip）
				url += '&load=detail&tip=true&apprId='+apprId;
				$("#tipMsg").val(d.msg);
				tabUpdateStateDialog(title, url, width, height, dialogId, 
						'完成', apprId, updateStateUrl);
			}else if(finish == apprStatus){
				//审核状态为已完成（取消完成按钮/不可编辑/tip）
				url += '&load=detail&tip=true&send=false&apprId='+apprId;
				$("#tipMsg").val(d.msg);
				tabUpdateStateDialog(title, url, width, height, dialogId, 
						'取消完成', apprId, updateStateUrl);
			}else if(rebut == apprStatus){
				//审核状态为被驳回（确定按钮/可编辑/tip）
				url += '&tip=true&idFlag=false&apprId='+apprId;
				$("#tipMsg").val(d.msg);
				tabDialog2(title, url, width, height, dialogId);
			}else{
				//审核状态为未发送（提交/可编辑）
				if(apprId != null){
					url += '&idFlag=false&apprId='+apprId;
				}
				tabDialog2(title, url, width, height, dialogId);
			}
		}
	});
}

//-------3：详情方法-------
function detailFun(title, url, gname, width, height, dialogId) {
	gridname = gname;
	
	var rowsData = $('#'+gname).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
			
	url += '&id='+rowsData[0].id;
	tabDetailDialog(title, url, width, height);
}

//-------4：完成流程-------
function updateFinishFlag(param){
	var url1="tPmApprLogsController.do?updateFinishFlag";
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


//-------------录入+更新+详情 附表一般是第二层）-------------

//-------1：录入附表数据方法-------
function addChildFun(title,addurl,gname,width,height){
	gridname = gname;
	createChildWindow(title, addurl, width, height);
}

//-------2：更新附表数据方法-------
function updateChildFun(title, url, id, width, height){
	gridname = id;
	
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
	createChildWindow(title, url, width, height);
}

//-------2：详情附表数据方法-------
function detailChildFun(title, url, id, width, height){
	var rowsData = $('#'+id).datagrid('getSelections');
	
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看项目');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
	
    url += '&load=detail&id='+rowsData[0].id;
    createChildWindow(title, url, width, height);
}

//-------------处理审核方法-------------
function handlerAppr(title, url, width, height, apprStatus, finish, receiveId, rebutValidFlag){
	//已处理记录，查看详情
	if(apprStatus == undefined){
		url += '&send=false&tip=true';
		$("#tipMsg").val("审核已处理，不能再发送审核");
		tabDetailDialog(title, url, width, height);
		return;
	}
	
	//审核流程被完成，只能查看详情
	if(apprStatus == finish){
		url += '&send=false&tip=true';
		$("#tipMsg").val("审核流程状态被设置为已完成，不能再进行审核");
		tabDetailDialog(title, url, width, height);
		return;
	}
	
	var dialogId = "apprInfo";
	var apprUrl = 'tPmApprLogsController.do?goUpdate&id=' + receiveId;
	if(rebutValidFlag == false){
		apprUrl += '&rebutValidFlag=' + rebutValidFlag;
	}
	tabApprDialog(title, url, width, height, dialogId, apprUrl);
}


//-------------日期转换方法-------------
function parseDateToString(date, fmt){ //author: meizz   
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}


//-------------------------------------附件上传方法--------------------------

//--------------------删除附件--------------------------
function del(url,obj){
	var windowapi = frameElement.api;
	if (typeof (windowapi) == 'undefined') {
		$.dialog({
			title:"删除确认",
			icon: 'confirm.gif',
			content: "确定删除该记录吗 ?",
			lock : true,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					url : url,// 请求的action路径
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
						    var msg = d.msg;
						    tip(msg);
						    $(obj).closest("tr").hide("slow");
						}
					}
				});
		    },
		    cancelVal: '取消',
		    cancel: true
		}).zindex();
	} else {
		W.$.dialog({
			title:"删除确认",
			icon: 'confirm.gif',
			content: "确定删除该记录吗 ?",
			lock : true,
			opacity : 0.3,
			cache:false,
			parent:windowapi,
		    ok: function(){
		    	$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					url : url,// 请求的action路径
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
						    var msg = d.msg;
						    tip(msg);
						    $(obj).closest("tr").hide("slow");
						}
					}
				});
		    },
		    cancelVal: '取消',
		    cancel: true
		}).zindex();
	}
}

//---------------------附件上传成功方法--------------------
function addUploadSuccess(d,file,response){
	var oldValue = $("#fileKeys").val();
    var newValue = oldValue + d.attributes.fileKey + ",";
    $("#fileKeys").val(newValue);
    
    var html="";
    html += 
    	'<tr style="height: 30px;">'+
        	'<td style="text-align:left;">' + d.attributes.fileName + '</td>' +
        	'<td>&nbsp;&nbsp;&nbsp;</td>' +
            '<td style="width:40px;">' +
            	'<a href="commonController.do?viewFile' +
            			'&fileid=' + d.attributes.fileid + 
            			'&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"' + 
            			'title="下载">下载</a>' +
            '</td>' +
            '<td style="width:40px;">' +
            	'<a href="javascript:void(0)" class="jeecgDetail" ' +
            			'onclick="del(\'commonController.do?delFile&id=' + d.attributes.fileid +'\',this)">删除</a>' +
            '</td>'+
        '</tr>';
    $("#fileShow").append(html);
}

//---------------------附件上传成功方法--------------------
function updateUploadSuccess(d,file,response){
    var html="";
    html += 
    	'<tr style="height: 30px;">'+
        	'<td>' +
            	'<a href="javascript:void(0);" onclick="createChildWindow(\'预览\',\'commonController.do?goAccessoryTab' +
        			'&bid=' + $('#bid').val() + '&index=' + $("#fileShow tr").length +
        			'&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity\',' +
        			'1000,700)">' + d.attributes.fileName + '</a>&nbsp;&nbsp;&nbsp;' +
            '</td>' +
        	'<td style="width:40px;">' +
        	'<a href="commonController.do?viewFile' +
        			'&fileid=' + d.attributes.fileid + 
        			'&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"' + 
        			'title="下载">下载</a>' +
            '</td>' +
           
            '<td style="width:40px;">' +
            	'<a href="javascript:void(0)" class="jeecgDetail" ' +
            			'onclick="del(\'commonController.do?delFile&id=' + d.attributes.fileid +'\',this)">删除</a>' +
            '</td>'+
        '</tr>';
    $("#fileShow").append(html);
}

function updateUploadSuccessTab(tableId,d,file,response){
    var html="";
    html += 
        '<tr style="height: 30px;">'+
            '<td>' +
                '<a href="javascript:void(0);" onclick="createChildWindow(\'预览\',\'commonController.do?goAccessoryTab' +
                    '&bid=' + $('#bid').val() + '&index=' + $("#fileShow tr").length +
                    '&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity\',' +
                    '1000,700)">' + d.attributes.fileName + '</a>&nbsp;&nbsp;&nbsp;' +
            '</td>' +
            '<td style="width:40px;">' +
            '<a href="commonController.do?viewFile' +
                    '&fileid=' + d.attributes.fileid + 
                    '&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"' + 
                    'title="下载">下载</a>' +
            '</td>' +
           
            '<td style="width:40px;">' +
                '<a href="javascript:void(0)" class="jeecgDetail" ' +
                        'onclick="del(\'commonController.do?delFile&id=' + d.attributes.fileid +'\',this)">删除</a>' +
            '</td>'+
        '</tr>';
    $("#"+tableId).append(html);
}

//---------------------查看项目信息--------------------
function detailProjectInfo(value,row,index){
	var html = "<a href=\"#\" onclick=\"openwindow('项目基本信息', 'tPmProjectController.do?goUpdateForResearchGroup&load=detail&id="+row.projectid+"', 'tPmFundsReceiveLogsList', '100%', '100%')\" "+
		"style=\"color:blue;\" "+">"+value+"</a>"
	return html;
}

//发送审核
function sendAppr(param){
	  var apprId = param['apprId'];
	  var apprType = param['apprType'];
	  var recId = param['recId'];
	  var suggestionCode = param['suggestionCode'];
	  var url,title;
	  if(suggestionCode=='1'){
		  url = "tPmApprLogsController.do?goApprSend&apprId="+apprId+"&apprType="+apprType+"&recId="+recId;
		  title = "发送";
	  }else{
		  url = "tPmApprLogsController.do?goApprBack&apprId="+apprId+"&apprType="+apprType+"&recId="+recId;
		  title = "驳回";
	  }
	  if (typeof (windowapi) == 'undefined') {
	  $.dialog({
	        id : 'apprSend',
	        content : 'url:' + url,
	        lock : true,
	        background : '#000', /* 背景色 */
	        opacity : 0.5, /* 透明度 */
	        width : 520,
	        height : 260,
	        data : param,
	        title : title,
	        ok : function() {
	        	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
	        },
	        cancelVal : '关闭',
	        cancel : function(){
	        	
	        }
	      }).zindex();
	  }else{
		  W.$.dialog({
		        id : 'apprSend',
		        content : 'url:' + url,
		        lock : true,
		        background : '#000', /* 背景色 */
		        opacity : 0.5, /* 透明度 */
		        width : 520,
		        height : 260,
		        data : param,
		        title : title,
		        parent:windowapi,
		        ok : function() {
		        	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
		        },
		        cancelVal : '关闭',
		        cancel : function(){
		        	
		        }
		      }).zindex();
	  }
}

//发起人发送
function sendApprDialog(title, url, width, height, id, auditStatus, apprType){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	var buttons = [];
	if(auditStatus=='0'||auditStatus=='3'){
		buttons.push({
			name:'发送审核',
			focus:true,
			callback:function(){
				iframe = this.iframe.contentWindow;
				openSendAudit(id,auditStatus,apprType);
		         return false;
			}
		});
	}
	buttons.push({
		name:'取消',
		callback:function(){
			var win,dialog;
			if(W!=undefined){
				dialog = W.$.dialog.list['processDialog'];
			}
		    if (dialog == undefined) {
		    	reloadTable();
		    } else {
		    	win = dialog.content;
		    	win.reloadTable();
		    }
		}
	});
	
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
			button:buttons
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
			button:buttons
		}).zindex();
	}
}

//发起人弹出发送窗口
function openSendAudit(id,auditStatus,apprType){
	  var url = "tPmApprLogsController.do?goApprSend&apprId="+id+"&apprType="+apprType;
	  if(typeof(windowapi) == 'undefined'){
	  $.dialog({
	        id : 'apprSend',
	        lock : true,
	        background : '#000', /* 背景色 */
	        opacity : 0.5, /* 透明度 */
	        width : 520,
	        height : 260,
	        title : '发送审核',
	        content : 'url:' + url,
	        ok : function() {
	        	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
	        },
	        cancelVal : '关闭',
	        cancel : function(){
	          reloadTable();
	        }
	      });
	  }else{
		  W.$.dialog({
		        id : 'apprSend',
		        lock : true,
		        background : '#000', /* 背景色 */
		        opacity : 0.5, /* 透明度 */
		        width : 520,
		        height : 260,
		        title : '发送审核',
		        parent : windowapi,
		        content : 'url:' + url,
		        ok : function() {
		        	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
		        },
		        cancelVal : '关闭',
		        cancel : function(){
		          reloadTable();
		        }
		      });
	  }
}

function sendApprDialog2(title, url, width, height, id, auditStatus, apprType){
	width = width?width:700;
	height = height?height:400;
	
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	var buttons = [];
	if(auditStatus=='0'||auditStatus=='3'){
		buttons.push({
			name:'发送审核',
			focus:true,
			callback:function(){
				iframe = this.iframe.contentWindow;
				openSendAudit2(id,auditStatus,apprType);
		         return false;
			}
		});
	}
	buttons.push({
		name:'取消',
		callback:function(){
			var win,dialog;
			if(W!=undefined){
				dialog = W.$.dialog.list['processDialog'];
			}
		    if (dialog == undefined) {
		    	reloadTable();
		    } else {
		    	win = dialog.content;
		    	win.reloadTable();
		    }
		}
	});
	
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
			button:buttons
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
			button:buttons
		}).zindex();
	}
}

function openSendAudit2(id,auditStatus,apprType){
	  var url = "tPmApprLogsController.do?goApprSend&apprId="+id+"&apprType="+apprType;
	  if(typeof(windowapi) == 'undefined'){
	  $.dialog({
	        id : 'apprSend',
	        lock : true,
	        background : '#000', /* 背景色 */
	        opacity : 0.5, /* 透明度 */
	        width : 520,
	        height : 260,
	        title : '发送审核',
	        content : 'url:' + url,
	        ok : function() {
	        	iframe = this.iframe.contentWindow;
				saveObj();
				window.location.reload();
				return false;
	        },
	        cancelVal : '关闭',
	        cancel : function(){
	          reloadTable();
	        }
	      });
	  }else{
		  W.$.dialog({
		        id : 'apprSend',
		        lock : true,
		        background : '#000', /* 背景色 */
		        opacity : 0.5, /* 透明度 */
		        width : 520,
		        height : 260,
		        title : '发送审核',
		        parent : windowapi,
		        content : 'url:' + url,
		        ok : function() {
		        	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
		        },
		        cancelVal : '关闭',
		        cancel : function(){
		          reloadTable();
		        }
		      });
	  }
}

