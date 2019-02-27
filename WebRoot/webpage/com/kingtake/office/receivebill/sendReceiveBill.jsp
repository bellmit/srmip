<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
<link href="webpage/com/kingtake/office/receivebill/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="webpage/com/kingtake/office/receivebill/css/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
<!--     <link href="webpage/com/kingtake/office/receivebill/css/plugins/morris.css" rel="stylesheet"> -->

    <!-- Custom Fonts -->
    <link href="webpage/com/kingtake/office/receivebill/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript">

function goadd(controller,title){
		$.dialog({
			content: 'url:'+controller+'.do?goAdd',
			lock : true,
			//zIndex:1990,
			width:1000,
			height:580,
			title:title+'录入',
			opacity : 0.3,
			cache:false,
			button:[{
				name:'保存并发送',
				callback:function(data){
					iframe = this.iframe.contentWindow;
					var flag =iframe.formCheck();
					if(flag==true){
					var addForm = $('#formobj', iframe.document).serialize();
					$.post(controller+'.do?doAdd',addForm,function(data){
						data = JSON.parse(data);
						iframe.uploadFile(data);
						var id = data.obj.id;
//	 					window.location.reload();
//	 					add("发送","tOSendBillController.do?goSend&id="+id,"",520,220);
						$.dialog({
							content: 'url:'+controller+'.do?goSend&id='+id,
							lock : true,
							//zIndex:1990,
							width:520,
							height:220,
							title:"发送",
							opacity : 0.3,
							cache:false,
							okVal: '发送',
						    ok: function(){
						    	iframe = this.iframe.contentWindow;
								saveObj();
// 								reloadtOSendBillList();
								return false;
						    },
//	 					    cancelVal: 'Close',
						    cancel: true /*为true等价于function(){}*/
						}).zindex();
						iframe.close();
						
					});
					}
					return false;
				}
			}],
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
// 				reloadtOSendBillList();
				saveObj();
				return false;
		    },
		    okVal:'保存',
		    cancelVal: '关闭',
		    cancel: function(){
// 		    	reloadtOSendBillList();
		    }
		    
		}).zindex();
}
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFEFF;
}
body,td,th {
	font-size: 12px;
	font-family:"Microsoft YaHei", "微软雅黑",;
}

A {
	font-size: 12px;
	line-height: 180%;
	TEXT-DECORATION: none;
	color: #3C3C3C;
}

A:link {
	font-size: 12px;
	line-height: 180%;
	TEXT-DECORATION: none;
	color: #3C3C3C;
}

A:hover {
	font-size: 12px;
	line-height: 180%;
	color: #0066FF;
}

.panel {
	margin-bottom: 20px;
	background-color: #fff;
	border: 0px solid transparent;
	border-radius: 4px;
	-webkit-box-shadow: 0 0px 0px rgba(0, 0, 0, .05);
	box-shadow: 0 0px 0px rgba(0, 0, 0, .05)
}
-->
</style>
</head>
<body style="overflow-x: hidden;">
<div id="globalMd" class="row">
					<div id="md1" class="col-lg-3 col-md-3">
                    <div class="panel">
                            <div align="center">
                                <a href="javascript:goadd('tOReceiveBillController','收文阅批单');"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_04.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="javascript:goadd('tOReceiveBillController','收文阅批单');"><font size="27">收文阅批单</font></a>
                            </div>
                    </div>
                    </div>
					<div id="md1" class="col-lg-3 col-md-3">
						<div class="panel">
                            <div align="center">
                                <a href="javascript:goadd('tOSendBillController','发文呈批单');"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_01.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="javascript:goadd('tOSendBillController','发文呈批单');"><font size="27">发文呈批单</font></a>
                            </div>
                       </div>
                    </div>
                    <div id="md1" class="col-lg-3 col-md-3" style="display: none;">
                    <div class="panel">
                            <div align="center">
                                <a href="#"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_02.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="#"><font size="27">办件会签单</font></a>
                            </div>
                    </div>
                    </div>
                    <div id="md1" class="col-lg-3 col-md-3">
                    <div class="panel">
                            <div align="center">
                                <a href="javascript:goadd('tOApprovalController','呈批件');"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_03.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="javascript:goadd('tOApprovalController','呈批件');"><font size="27">呈批件</font></a>
                            </div>
                    </div>
                    </div>
                    
                    <div id="md1" class="col-lg-3 col-md-3"style="display: none;">
                    <div class="panel">
                            <div align="center">
                                <a href="#"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_05.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="#"><font size="27">电话记录</font></a>
                            </div>
                    </div>
                    </div>
                    <div id="md1" class="col-lg-3 col-md-3"style="display: none;">
                    <div class="panel">
                            <div align="center">
                                <a href="#"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_06.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="#"><font size="27">通知</font></a>
                            </div>
                    </div>
                    </div>
                    <div id="md1" class="col-lg-3 col-md-3"style="display: none;">
                    <div class="panel">
                            <div align="center">
                                <a href="#"><img src="webpage/com/kingtake/office/receivebill/images/fw_icon_07.png"></a>
                            </div>
                            <div style="bgcolor:#DFE5E6;"  align="center">
                            	<a href="#"><font size="27">请求、报告</font></a>
                            </div>
                    </div>
                    </div>
                    
                    
                   </div>



<t:authFilter></t:authFilter>	
</body>

