<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uid = user.getId();
request.setAttribute("uid", uid);
request.setAttribute("temporary", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("sendOff", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function transaction(rowIndex){
	var rowData = $('#tOSendBillList').datagrid('getData').rows[rowIndex];
	if(rowData.type=='1'){
	   sendDoubleClick(rowIndex,rowData);
	}else if(rowData.type=='2'){
	   receiveDoubleClick(rowIndex,rowData);
	}else if(rowData.type=='3'){
        approvalDoubleClick(rowIndex,rowData);
    }
}


//双击事件
function doubleClick(rowIndex,rowData){
    if(rowData.type=='1'){
        sendDoubleClick(rowIndex,rowData);
    }else if(rowData.type=='2'){
        receiveDoubleClick(rowIndex,rowData);
    }else if(rowData.type=='3'){
        approvalDoubleClick(rowIndex,rowData);
    }
}

function sendDoubleClick(rowIndex,rowData){
    var operateStatus = '0';
	var id = rowData.id;
	var rid = rowData.rid;
		$.dialog({
			id:'inputPage',
			content: 'url:tOSendBillController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&rid='+rid,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"发文呈批单处理",
			opacity : 0.3,
			cache:false,
			okVal:'发送',
			ok:function(){
			    iframe = this.iframe.contentWindow;
				goSendBillSend(id,rid);
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadTable();
		    }
		});
}

function receiveDoubleClick(rowIndex,rowData){
    var operateStatus = '0';
	var id = rowData.id;
	var rid = rowData.rid;
		$.dialog({
			id:'inputPage',
			content: 'url:tOReceiveBillController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&rid='+rid,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"阅批单处理",
			opacity : 0.3,
			cache:false,
			okVal:'发送',
			ok:function(){
			    iframe = this.iframe.contentWindow;
				goReceiveSend(id,rid);
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadTable();
		    }
		});
}

function approvalDoubleClick(rowIndex,rowData){
	var operateStatus = '0';
	var id = rowData.id;
	var rid = rowData.rid;
		$.dialog({
			id:'inputPage',
			content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&rid='+rid,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"呈批件处理",
			opacity : 0.3,
			cache:false,
			okVal:'发送',
			ok:function(){
			    iframe = this.iframe.contentWindow;
			    goApprovalSend(id,rid);
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadTable();
		    }
		});
}

function goApprovalSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOApprovalController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

function goSendDetail(rowIndex){
	var rowsData = $('#tOSendBillList').datagrid('getSelections');
	var id = rowsData[0].id;
	$.dialog({
		id:'detailPage',
		content: 'url:tOSendBillController.do?goDetail&id='+id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
    	left : '0%',
    	top : '0%',
		title:"呈批单查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	        reloadTable();
	    }
	});
}

function goReceiveDetail(rowIndex){
	var rowsData = $('#tOReceiveBillList').datagrid('getSelections');
	var id = rowsData[0].id;
	$.dialog({
		id:'detailPage',
		content: 'url:tOReceiveBillController.do?goDetail&id='+id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
    	left : '0%',
    	top : '0%',
		title:"阅批单查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadTable();
	    }
	});
}

function goSendBillSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOSendBillController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

function goReceiveSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOReceiveBillController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

//刷新
function refresh(){
   /*  var jq = top.jQuery;
    var tab = jq('#maintabs').tabs("getSelected"); 
	tab.panel('refresh'); */
	reloadTable();
}


    function goFinish(id, rid,type) {
        if (type == '1') {
            var iWidth = 900; //弹出窗口的宽度;
            var iHeight = 500; //弹出窗口的高度;
            var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
            var openUrl = "tOSendBillController.do?goFinish&id=" + id + "&rid=" + rid;
            createwindow("完成审批",openUrl,iWidth,iHeight);
//             window.open(openUrl, "", "height=" + iHeight + ", width=" + iWidth + ", top=" + iTop + ", left=" + iLeft);
        } else if(type=='2'){
            $.dialog.confirm("是否确定将公文置为完成状态？", function() {
                $.ajax({
                    url : 'tOReceiveBillController.do?doFinish&id=' + id + '&rid=' + rid,
                    type : 'POST',
                    timeout : 3000,
                    dataType : 'json',
                    success : function(data) {
                        tip(data.msg);
                        refresh();
                    }
                });
            }, function() {
            });
        }else{
            var iWidth=900; //弹出窗口的宽度;
            var iHeight=500; //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
            var openUrl = "tOApprovalController.do?goFinish&id="+id+"&rid="+rid;
            createwindow("完成审批",openUrl,iWidth,iHeight);
//             window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
        }
    }

    function doFinish(id, rid) {
        $.dialog.confirm("是否确定将公文置为完成状态？", function() {
            $.ajax({
                url : 'tOSendBillController.do?doFinish&id=' + id + '&rid=' + rid,
                type : 'POST',
                timeout : 3000,
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    refresh();
                }
            });
        }, function() {
        });

    }
</script>
<script type="text/javascript">

function goDetailView(index){
	var rows = $("#tOSendBillList").datagrid("getSelections");
    if(rows.length==0){
    	tip("请至少选择一条数据进行查看！");
        return false;
    }
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rows[0].id;
	
	$.dialog({
		id:'inputPage',
		content: 'url:tOSendBillController.do?goDetail&id='+id+'&operateStatus='+operateStatus+'&load=detail',
		lock : true,
		width:900,
		height:520,
		title:"呈批单查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOSendBillList();
	    }
	}).zindex();
	
}



function doSignIn(id,index){
	$.post("tOSendBillController.do?doSignIn&id="+id,"",function(data){
		reloadtOSendBillList();
		data = $.parseJSON(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg
        // 							timeout : 1000 * 6
        });
	}); 
}


function toupdate(id,index){
	$.dialog({
		content: 'url:tOSendBillController.do?goUpdate&id='+id,
		lock : true,
		//zIndex:1990,
		width:900,
		height:520,
		title:'呈批单修改',
		opacity : 0.3,
		cache:false,
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOSendBillList();
	    }
	    
	}).zindex();
}

function send(id){
	
	$.ajax({
		url:'tOSendBillController.do?ifaccessory&id='+id,
		type : 'POST',
		timeout : 3000,
		dataType : 'json',
		success:function(data){
			var accessoryFlag = data.obj;//0-没有附件，1-有附件
			if(accessoryFlag=='1'){
				add("发送","tOSendBillController.do?goSend&id="+id+"&ifcirculate=0","tOSendBillList",480,200);
			}else{
				$.dialog.confirm("未上传附件不可发送，是否现在上传？",function(){
					toupdate(id,"");
				},function(){});
			}
		}
	});
	
}

function archive(id,index){
	$.dialog.confirm('归档后该文件将无法再次发送或审批，是否确定执行此操作？', function(){
	$.post("tOSendBillController.do?archive&id="+id,"",function(data){
		reloadtOSendBillList();
		data = $.parseJSON(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg
        // 							timeout : 1000 * 6
        });
// 		console.info(data);
	}); 
	},function(){
	});
}


function inputSuggestion(id){
	 $.dialog({
			content: 'url:tOSendBillController.do?goInput&id='+id,
			lock : true,
			//zIndex:1990,
			width:600,
			height:220,
			title:"意见填写",
			opacity : 0.3,
			cache:false,
	 	    ok: function(){
	 	    	iframe = this.iframe.contentWindow;
	 	    	var suggesForm = $('#formobj', iframe.document).serialize();
	 	    	var rid = $('#receiveid', iframe.document).val();
	 	    	var reloadPage = this.get("inputPage");
	 	    	$.post('tOSendBillController.do?doSuggestion&rid='+rid,suggesForm,function(data){
	 	    		data = $.parseJSON(data);
	 	    		reloadPage.location.reload();
	 	    	});
	 	    },
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		}).zindex();
}

</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;height: 400px;">
  <t:datagrid name="tOSendBillList"  onDblClick="doubleClick" fit="true" fitColumns="false" 
  	actionUrl="tOSendBillController.do?sendReceiveDatagridPortal" 
  	idField="id" queryMode="group" extendParams="nowrap:false, ">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="接收主键"  field="rid"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="类型"  field="type" replace="发文_1,收文_2,呈批件_3"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="公文标题"  frozenColumn="true"  field="TITLE"   queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="公文编号"  field="FILE_NUM"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="公文种类"  field="REG_TYPE"  codeDict="1,GWZL"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="密级"  field="SECRITY_GRADE"   codeDict="0,XMMJ"   queryMode="group"  width="40"></t:dgCol>
   <t:dgCol title="承办单位"  field="DUTY_NAME"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="公文状态"  field="ARCHIVE_FLAG"   codeDict="1,YPDZT"   queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="联系人id"  field="CONTACT_ID"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="创建人"  field="CREATE_BY"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="CREATE_NAME"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="修改人"  field="UPDATE_BY"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="UPDATE_NAME"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="修改时间"  field="UPDATE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="80" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130"></t:dgCol>
   <t:dgFunOpt  title="办理"  funname="transaction"/>
   <t:dgFunOpt exp="CONTACT_ID#eq\#${uid}" title="完成"  funname="goFinish(id,rid,type)"></t:dgFunOpt> 
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/sendbill/tOSendBillList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOSendBillListtb").find("input[name='draftDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='draftDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='archiveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='archiveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("select[name='SEND.TYPE.CODE']").attr("style","height:23px;width:100px;;vertical-align: bottom;");
 			var input = $("#tOSendBillListtb input[name='SEND.TYPE.CODE']");
 			input.combobox({
 				width : 100,
 				url : 'tBCodeTypeController.do?getDetailList&codeTypeId=40288af64e56bf48014e57136e1f001a',
 				valueField : 'code',
 				textField : 'name'
 			});
 });

 </script>