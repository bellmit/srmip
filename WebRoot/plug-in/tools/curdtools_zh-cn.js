
/*window.onerror = function() {
	return true;
};*/
var iframe;// iframe操作对象
var win;//窗口对象
var gridname="";//操作datagrid对象名称
var windowapi,W;
if(frameElement){
    windowapi = frameElement.api;
}
if(windowapi){
    W = windowapi.opener;
}
function upload(curform) {
	upload();
}
/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function add(title,addurl,gname,width,height) {
	gridname=gname;
	createwindow(title, addurl,width,height);
}
/**
 * 树列表添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function addTreeNode(title,addurl,gname) {
	if (rowid != '') {
		addurl += '&id='+rowid;
	}
	gridname=gname;
	createwindow(title, addurl);
}
/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function update(title,url, id,width,height) {
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

/**
 * 如果页面是详细查看页面，
 */
$(function(){
	if(location.href.indexOf("load=detail")!=-1){
		//无效化所有表单元素，只能进行查看
		$(":input").attr("disabled","true");
		$(":input").css("background-color","#FFFFCC");
		//隐藏选择框和清空框
		$("a[icon='icon-search']").css("display","none");
		$("a[icon='icon-redo']").css("display","none");
		$("a[icon='icon-save']").css("display","none");
		$("a[icon='icon-ok']").css("display","none");
		//隐藏下拉框箭头
		$(".combo-arrow").css("display","none");
		//隐藏添加附件
		$("#filediv").parent().css("display","none");
		//隐藏附件的删除按钮
		$(".jeecgDetail").parent().css("display","none");
		//隐藏easyui-linkbutton
		$(".easyui-linkbutton").css("display","none");
		//$(":input").attr("style","border:0;border-bottom:1 solid black;background:white;");
	}
});

/**
 * 查看详细事件打开窗口
 * @param title 查看框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function detail(title,url, id,width,height) {
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
	createdetailwindow(title,url,width,height);
}

/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelect(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    debugger;
    if (rows.length > 0) {
    	$.dialog.confirm('你确定永久删除该数据吗?', function(r) {
    		
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要删除的数据");
	}
}

/**
 * 批量归档
 * @param title
 * @param url
 * @param gname
 * @return
 */
function gdALLSelect(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.confirm('你确定归档吗?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = $.parseJSON(data);
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要归档的数据");
	}
}

/**
 * 查看时的弹出窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createdetailwindow(title, addurl,width,height) {
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
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			title:title,
			opacity : 0.3,
			fixed:true,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			fixed:true,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
	
}
/**
 * 全屏编辑
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function editfs(title,url) {
	var name=gridname;
	 if (rowid == '') {
		tip('请选择编辑项目');
		return;
	}
	url += '&id='+rowid;
	openwindow(title,url,name,800,500);
}
// 删除调用函数
function delObj(url,name) {
	gridname=name;
	createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
}
// 删除调用函数
function confuploadify(url, id) {
	$.dialog.confirm('确定删除吗', function(){
		deluploadify(url, id);
	}, function(){
	}).zindex();
}
/**
 * 执行删除附件
 * 
 * @param url
 * @param index
 */
function deluploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				$("#" + id).remove();// 移除SPAN
				m.remove(id);// 移除MAP对象内字符串
			}

		}
	});
}
// 普通询问操作调用函数
function dialogConfirm(url, content,name) {
	createdialog('提示信息 ', content, url,name);
}
/**
 * 提示信息
 */
function tip_old(msg) {
	$.dialog.setting.zIndex = 1980;
	$.dialog.tips(msg, 1);
}
/**
 * 提示信息
 */
function tip(msg) {
	$.dialog.setting.zIndex = 1980;
	if($.messager){
		$.messager.show({
			title : '提示信息',
			msg : msg,
			timeout : 1000 * 6
		});
	}else if(layer){
		layer.open({
			content:msg ,
            time:1000*3
        })
	}
}
/**
 * 提示信息像alert一样
 */
function alertTip(msg,title) {
	$.dialog.setting.zIndex = 1980;
	title = title?title:"提示信息";
	$.dialog({
			title:title,
			icon:'tips.gif',
			content: msg
		}).zindex();
}
/**
 * 创建添加或编辑窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl,width,height,backFn) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	
	    		iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	if(backFn){
		    		backFn(iframe);
		    	}else{
					saveObj();
					return false;
		    	}
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
    //--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	
}
/**
 * 创建上传页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url,name,width, height) {
	gridname=name;
	if(typeof(windowapi) == 'undefined'){
	$.dialog({
	    title : title,
	    content: 'url:'+url,
	    cache:false,
	    button: [
	        {
	            name: "开始上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.upload();
					return false;
	            },
	            focus: true
	        },
	        {
	            name: "取消上传",
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.cancel();
	            }
	        }
	    ]
	}).zindex();
	}else{
        W.$.dialog({
            title : title,
            content: 'url:'+url,
            cache:false,
            parent:windowapi,
            button: [
                {
                    name: "开始上传",
                    callback: function(){
                        iframe = this.iframe.contentWindow;
                        iframe.upload();
                        return false;
                    },
                    focus: true
                },
                {
                    name: "取消上传",
                    callback: function(){
                        iframe = this.iframe.contentWindow;
                        iframe.cancel();
                    }
                }
            ]
        }).zindex();
	}
}
/**
 * 创建查询页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function opensearchdwin(title, url, width, height) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		height : height,
		cache:false,
		width : width,
		opacity : 0.3,
		button : [ {
			name : '查询',
			callback : function() {
				iframe = this.iframe.contentWindow;
				iframe.searchs();
			},
			focus : true
		}, {
			name : '取消',
			callback : function() {

			}
		} ]
	}).zindex();
}
/**
 * 创建不带按钮的窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openwindow(title, url,name, width, height) {
	gridname=name;
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	if (typeof (width) == 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true,
				width: 'auto',
			    height: height
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				parent:windowapi,
				lock : true,
				width: 'auto',
			    height: height
			}).zindex();
		}
	}
	if (typeof (height) == 'undefined'&&typeof (width) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: 'auto'
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: 'auto'
			}).zindex();
		}
	}
	if (typeof (width) == 'undefined'&&typeof (height) == 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: 'auto',
				cache:false,
			    height: 'auto'
			}).zindex();
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: 'auto',
				cache:false,
			    height: 'auto'
			}).zindex();
		}
	}
	
	if (typeof (width) != 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true
			}).zindex();
		}else{
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				parent:windowapi,
				title : title,
				cache:false,
				lock : true
			}).zindex();
		}
	}
}
//带id的dialog
function openWindowById(id,title, url,name, width, height) {
    gridname=name;
    if(width=="100%"){
        width = window.top.document.body.offsetWidth;
    }
    if(height=="100%"){
        height =window.top.document.body.offsetHeight-100;
    }
    if (typeof (width) == 'undefined'&&typeof (height) != 'undefined')
    {
        if(typeof(windowapi) == 'undefined'){
            $.dialog({
                id:id,
                content: 'url:'+url,
                title : title,
                cache:false,
                lock : true,
                width: 'auto',
                height: height
            }).zindex();
        }else{
            W.$.dialog({
                id:id,
                content: 'url:'+url,
                title : title,
                cache:false,
                parent:windowapi,
                lock : true,
                width: 'auto',
                height: height
            }).zindex();
        }
    }
    if (typeof (height) == 'undefined'&&typeof (width) != 'undefined')
    {
        if(typeof(windowapi) == 'undefined'){
            $.dialog({
                id:id,
                content: 'url:'+url,
                title : title,
                lock : true,
                width: width,
                cache:false,
                height: 'auto'
            }).zindex();
        }else{
            W.$.dialog({
                id:id,
                content: 'url:'+url,
                title : title,
                lock : true,
                parent:windowapi,
                width: width,
                cache:false,
                height: 'auto'
            }).zindex();
        }
    }
    if (typeof (width) == 'undefined'&&typeof (height) == 'undefined')
    {
        if(typeof(windowapi) == 'undefined'){
            $.dialog({
                id:id,
                content: 'url:'+url,
                title : title,
                lock : true,
                width: 'auto',
                cache:false,
                height: 'auto'
            }).zindex();
        }else{
            W.$.dialog({
                id:id,
                content: 'url:'+url,
                title : title,
                lock : true,
                parent:windowapi,
                width: 'auto',
                cache:false,
                height: 'auto'
            }).zindex();
        }
    }
    
    if (typeof (width) != 'undefined'&&typeof (height) != 'undefined')
    {
        if(typeof(windowapi) == 'undefined'){
            $.dialog({
                id:id,
                width: width,
                height:height,
                content: 'url:'+url,
                title : title,
                cache:false,
                lock : true
            }).zindex();
        }else{
            W.$.dialog({
                id:id,
                width: width,
                height:height,
                content: 'url:'+url,
                parent:windowapi,
                title : title,
                cache:false,
                lock : true
            }).zindex();
        }
    }
}

/**
 * 创建询问窗口
 * 
 * @param title
 * @param content
 * @param url
 */
function createdialog(title, content, url, name) {
    if (typeof (windowapi) == 'undefined') {
        $.dialog.confirm(content, function() {
            doSubmit(url, name);
            rowid = '';
        }, function() {
        }).zindex();
    } else {
        W.$.dialog.confirm(content, function() {
            doSubmit(url, name);
            rowid = '';
        }, function() {
        }, windowapi).zindex();
    }
}
/**
 * 执行保存
 * 
 * @param url
 * @param gridname
 */
function saveObj() {
	$('#btn_sub', iframe.document).click();
}

/**
 * 执行AJAX提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxSubForm(url) {
	$('#myform', iframe.document).form('submit', {
		url : url,
		onSubmit : function() {
			iframe.editor.sync();
		},
		success : function(r) {
			tip('操作成功');
			reloadTable();
		}
	});
}
/**
 * 执行查询
 * 
 * @param url
 * @param gridname
 */
function search() {

	$('#btn_sub', iframe.document).click();
	iframe.search();
}

/**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doSubmit(url,name,data) {
	gridname=name;
	//--author：JueYue ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	var paramsData = data;
	if(!paramsData){
		paramsData = new Object();
		if (url.indexOf("&") != -1) {
			var str = url.substr(url.indexOf("&")+1);
			url = url.substr(0,url.indexOf("&"));
			var strs = str.split("&");
			for(var i = 0; i < strs.length; i ++) {
				paramsData[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
			}
		}      
	}
	//--author：JueYue ---------date：20140227---------for：把URL转换成POST参数防止URL参数超出范围的问题
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : paramsData,
		url : url,// 请求的action路径
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				tip(msg);
				reloadTable();
			}
		}
	});
	
	
}
/**
 * 退出确认框
 * 
 * @param url
 * @param content
 * @param index
 */
function exit(url, content) {
	$.dialog.confirm(content, function(){
		window.location = url;
	}, function(){
	}).zindex();
}
/**
 * 模板页面ajax提交
 * 
 * @param url
 * @param gridname
 */
function ajaxdoSub(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
			editor.sync();
		},
		success : function(r) {
			tip('操作成功');
		}
	});
}
/**
 * ajax提交FORM
 * 
 * @param url
 * @param gridname
 */
function ajaxdoForm(url, formname) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
		},
		success : function(r) {
			tip('操作成功');
		}
	});
}

function opensubwin(title, url, saveurl, okbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	}).zindex();
}

function openauditwin(title, url, saveurl, okbutton, backbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = $.dialog.open.origin;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : backbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#formobj', iframe.document).form('submit', {
					url : saveurl + "&code=exit",
					onSubmit : function() {
						$('#code').val('exit');
					},
					success : function(r) {
						$.dialog.tips('操作成功', 2);
						win.location.reload();
					}
				});

			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	}).zindex();
}
/*获取Cookie值*/
function getCookie(c_name)
{
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=")
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1
			c_end = document.cookie.indexOf(";", c_start)
			if (c_end == -1)
				c_end = document.cookie.length
			return unescape(document.cookie.substring(c_start, c_end))
		}
	}
	return ""
}
// 添加标签
function addOneTab(subtitle, url, icon) {
	var indexStyle = getCookie("JEECGINDEXSTYLE");
	if(indexStyle=='sliding'||indexStyle=='bootstrap'){
		//shortcut和bootstrap风格的tab跳转改为直接跳转
		window.location.href=url;
	}else{
		if (icon == '') {
			icon = 'icon folder';
		}
		window.top.$.messager.progress({
			text : '页面加载中....',
			interval : 300
		});
		window.top.$('#maintabs').tabs({
			onClose : function(subtitle, index) {
				window.top.$.messager.progress('close');
			}
		});
		if (window.top.$('#maintabs').tabs('exists', subtitle)) {
			window.top.$('#maintabs').tabs('select', subtitle);
			if (url.indexOf('isHref') != -1) {
				window.top.$('#maintabs').tabs('update', {
					tab : window.top.$('#maintabs').tabs('getSelected'),
					options : {
						title : subtitle,
						href:url,
						//content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						closable : true,
						icon : icon
					}
				});
			}else {
				window.top.$('#maintabs').tabs('update', {
					tab : window.top.$('#maintabs').tabs('getSelected'),
					options : {
						title : subtitle,
						content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						//content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
						closable : true,
						icon : icon
					}
				});
			}
		} else {
			if (url.indexOf('isHref') != -1) {
				window.top.$('#maintabs').tabs('add', {
					title : subtitle,
					href:url,
					closable : true,
					icon : icon
				});
			}else {
				window.top.$('#maintabs').tabs('add', {
					title : subtitle,
					content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
					closable : true,
					icon : icon
				});
			}
		}
	}
}
// 关闭自身TAB刷新父TABgrid
function closetab(title) {
	//暂时先不刷新
	//window.top.document.getElementById('tabiframe').contentWindow.reloadTable();
	//window.top.document.getElementById('maintabs').contentWindow.reloadTable();
	window.top.$('#maintabs').tabs('close', title);
	//tip("添加成功");
}

//popup  
//object: this  name:需要选择的列表的字段  code:动态报表的code
function inputClick(obj,name,code) {
	 $.dialog.setting.zIndex = 2000;
	 if(name==""||code==""){
		 alert("popup参数配置不全");
		 return;
	 }
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:cgReportController.do?popup&id="+code,
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
					    var str = "";
				    	$.each( selected, function(i, n){
					    	if (i==0)
					    	str+= n[name];
					    	else
				    		str+= ","+n[name];
				    	});
				    	$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: "url:cgReportController.do?popup&id="+code,
				lock : true,
				title:"选择",
				width:800,
				height: 400,
				parent:windowapi,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
					    var str = "";
				    	$.each( selected, function(i, n){
					    	if (i==0)
					    	str+= n[name];
					    	else
				    		str+= ","+n[name];
				    	});
				    	$(obj).val("");
				    	//$('#myText').searchbox('setValue', str);
					    $(obj).val(str);
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}
/*
	自定义url的弹出
	obj:要填充的控件,可以为多个，以逗号分隔
	name:列表中对应的字段,可以为多个，以逗号分隔（与obj要对应）
	url：弹出页面的Url
*/
function popClick(obj,name,url) {
	 $.dialog.setting.zIndex = 2001;
	var names = name.split(",");
	var objs = obj.split(",");
	 if(typeof(windowapi) == 'undefined'){
		 $.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择",
				width:700,
				height: 400,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
							if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("input[name='"+objs[i1]+"']").val("");
								$("input[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					 
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}else{
			$.dialog({
				content: "url:"+url,
				lock : true,
				title:"选择",
				width:700,
				height: 400,
				parent:windowapi,
				cache:false,
			     ok: function(){
			    	iframe = this.iframe.contentWindow;
			    	var selected = iframe.getSelectRows();
			    	if (selected == '' || selected == null ){
				    	alert("请选择");
			    		return false;
				    }else {
				    	for(var i1=0;i1<names.length;i1++){
						    var str = "";
					    	$.each( selected, function(i, n){
						    	if (i==0)
						    	str+= n[names[i1]];
						    	else{
									str+= ",";
									str+=n[names[i1]];
								}
					    	});
					    	if($("#"+objs[i1]).length>=1){
								$("#"+objs[i1]).val("");
								$("#"+objs[i1]).val(str);
							}else{
								$("[name='"+objs[i1]+"']").val("");
								$("[name='"+objs[i1]+"']").val(str);
							}
						 }
				    	return true;
				    }
					
			    },
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			}).zindex();
		}
}
/**
 * Jeecg Excel 导出
 * 代入查询条件
 */
function JeecgExcelExport(url,datagridId,ids){
	/*var queryParams = $('#'+datagridId).datagrid('options').queryParams;
	$('#'+datagridId+'tb').find('*').each(function() {
		//var value = encodeURIComponent($(this).val());
		var value = $(this).val();
		if ($(this).attr('isLike') == 'true') {
			queryParams[$(this).attr('name')] = '*'+ value + '*';
		} else {
			queryParams[$(this).attr('name')] = value;
		}
	});
	$.each(queryParams, function(key, val){
		params+='&'+key+'='+val;
	}); */
	
	//字段
	var fields = '&field=';
	$.each($('#'+ datagridId).datagrid('options').columns[0], function(i, val){
		if(val.field != 'opt'){
			fields += val.field+',';
		}
	});
	
	//查询参数
	var params = '';
	var winName = "tmpWin";
	var tempForm = document.createElement("form");    
    tempForm.id="tempForm1";    
    tempForm.method="post";    
    tempForm.action=url;    
    tempForm.target=winName;  
    if(ids==null){
	$('#'+datagridId+'tb').find('*').each(function() {
		var key = $(this).attr('name');
		var value = $(this).val();
		if(value != "" && $(this).attr('isLike') == 'true'){
            value = '*'+ value + '*';
        }
		var hideInput = document.createElement("input");    
		    hideInput.type="hidden";    
		    hideInput.name= key;  
		    hideInput.value= value;  
		    tempForm.appendChild(hideInput);  
	});
    }else{
        var hideInput = document.createElement("input");    
        hideInput.type="hidden";    
        hideInput.name= "ids";  
        hideInput.value= ids;  
        tempForm.appendChild(hideInput);  
    }
	 $(tempForm).submit(function(){
	     openTmpWindow(winName);
	 });  
	    document.body.appendChild(tempForm);    
	    tempForm.submit();  
	    document.body.removeChild(tempForm);
}
//打开临时窗口
function openTmpWindow(winName){
    window.open('about:blank',winName,'height=100, width=100, top=0, left=0, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
}
/**
 * 自动完成的解析函数
 * @param data
 * @returns {Array}
 */
function jeecgAutoParse(data){
	var parsed = [];
    	$.each(data.rows,function(index,row){
    		parsed.push({data:row,result:row,value:row.id});
    	});
			return parsed;
}
/**
 * 改变主题风格后自动刷新界面函数
 */
function refreshMainpage()
{
    window.location.replace(window.location.href);
}

//金额转化为千分位
function transformAmount(value) {
    var v = parseFloat(value);
    var sign = '';
    if(v<0){
       sign = "-";
    }
    val = (Math.round(Math.abs(v)*Math.pow(10,2))/Math.pow(10,2)).toFixed(2).toString();
    result = sign + val.replace(/(\d{1,3})(?=(?:\d{3})+(?:\.))/g,'$1,');
    return result;
}

//去掉千分位
function reTransAmount(amount){
    amount= amount.replace(/,/g,'');
    return amount;
}

function xmlbAdd(title,addurl,gname,width,height) {
	gridname=gname;
	createwindowXmlb(title, addurl,width,height);
}

function xmlbUpdate(title,url, id,width,height) {
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
	createwindowXmlb(title,url,width,height,rowsData[0].id);
}

function createwindowXmlb(title, addurl,width,height,id) {
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
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var msg;
		    	$.ajax({
					url : "tBXmlbController.do?wybhCheck",
					type : 'post',
					data : {
						"wybh" : iframe.$("#wybh").val(),
						"id":id
					},
					cache : false,
					async: false,
					success : function(data) {
						var d = $.parseJSON(data);
						msg=d.msg;
					}
				});
		    	if(msg==1){
		    		iframe.tipWybh();
		    		return false;
		    	}else{
		    		saveObj();
					return false;
		    	}
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
}