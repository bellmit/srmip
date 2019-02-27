<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tOMessageList" fitColumns="true" title="系统消息" 
			actionUrl="tOMessageController.do?datagridToMe&df=1" idField="id" 
		 	fit="true" queryMode="group" pageSize="100" pageList="[100,200,300]"
		 	onDblClick="dblDetail" checkbox="true">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="发送人id" field="SENDER_ID" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="标题" field="title"  query="true" queryMode="single" width="150"></t:dgCol>
			<t:dgCol title="内容" field="content"   queryMode="group" width="400" extendParams="formatter:contentFormatter,"></t:dgCol>
			<t:dgCol title="发送人" field="sendName"  query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="发送时间" field="sendTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150" align="center"></t:dgCol>			
			<t:dgCol title="消息类型" field="type"  queryMode="group" width="100" align="center" ></t:dgCol>			
			<t:dgCol title="是否阅读" field="READFLAG" replace="否_0,是_1" width="70"></t:dgCol>
			<t:dgToolBar title="查看" icon="icon-search" funname="godetailview2"></t:dgToolBar>
            <t:dgToolBar title="恢复" icon="icon-redo" funname="doBack"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/office/message/tOMessageList.js"></script>		
<script type="text/javascript">
	function dblDetail(rowIndex, rowDate){
		godetailview(rowDate.id);
	}
	
	$(document).ready(function(){
		$("#tOMessageListtb").find("input[name='title']").attr("style","width:150px;");
		//给时间控件加上样式
		$("#tOMessageListtb").find("input[name='SEND_TIME_begin']")
			.attr("class","Wdate").attr("style","height:25px;width:100px;")
			.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'SEND_TIME_end\')}'});});
		$("#tOMessageListtb").find("input[name='SEND_TIME_end']")
			.attr("class","Wdate").attr("style","height:25px;width:100px;")
			.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'SEND_TIME_begin\')}'});});
	});
	 
	function godetailview(id){
		$.dialog({
			content: 'url:tOMessageController.do?goView&flag=0&id='+id,
			lock : true,
			width:700,
			height:270,
			title:"查看",
			cache:false,
			cancelVal: '关闭',
			cancel: function(){
				reloadtOMessageList();
			}
		}).zindex();
	}
	
	function godetailview2(){
		var row = $('#tOMessageList').datagrid('getSelections')[0];
		var id = row.id;
		godetailview(id);
	}
	
	function goReaded(){
	  var row = $('#tOMessageList').datagrid('getSelections');
	  console.log(row);
	  if(row.length <= 0){
	    alert('请至少选择一条消息记录！');
	    return;
	  }else{
	    var ids = [];
	    for(var i = 0; i < row.length; i++){
	      ids.push(row[i].id);
	    }
	    ids = ids.join(',');
  	  $.ajax({
        url : 'tOMessageController.do?doReaded',
        data : {
            ids : ids
        },
        type : 'post',
        dataType : 'json',
        success : function(data) {
          tip(data.msg);
          reloadtOMessageList();
        }
      });
	  }
	}
	
	
    function goDelete() {
        var row = $('#tOMessageList').datagrid('getSelections');
        if (row.length <= 0) {
            tip('请至少选择一条消息记录！');
            return;
        } else {
            $.messager.confirm('确认', '您确认隐藏勾选的系统消息吗？', function(r) {
                if (r) {
            var ids = [];
            for (var i = 0; i < row.length; i++) {
                ids.push(row[i].id);
            }
            ids = ids.join(',');
            $.ajax({
                url : 'tOMessageController.do?doHide',
                data : {
                    ids : ids
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    reloadtOMessageList();
                }
            });
        }
       });
    }
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOMessageController.do?upload', "tOMessageList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tOMessageController.do?exportXls", "tOMessageList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOMessageController.do?exportXlsByT", "tOMessageList");
    }

    //消息内容
    function contentFormatter(value, row, index) {
        if (row.READFLAG == '0') {
            return '<span style="color:blue">' + value + '</span>';
        } else {
            return value;
        }
    }
    
  //回收站
    function goRecycle(){
        var title="我的回收站";
        var url = "tOScheduleController.do?goRecycle";
        var width = window.top.document.body.offsetWidth;
        var height = window.top.document.body.offsetHeight-100;
        $.dialog({
    		content: 'url:'+url,
    		lock : true,
    		width:width,
    		height:height,
    		title:title,
    		opacity : 0.3,
    		cache:false,
    	    cancelVal: '关闭',
    	    cancel: function(){
    	        $("#scheduleList").datagrid('reload');
    	    }
    	}).zindex();
    }
  
  //回收站
    function doBack(){
        var row = $('#tOMessageList').datagrid('getSelections');
        if (row.length <= 0) {
            tip('请至少选择一条记录！');
            return;
        } else {
            $.messager.confirm('确认', '您确认恢复勾选的系统消息吗？', function(r) {
                if (r) {
            var ids = [];
            for (var i = 0; i < row.length; i++) {
                ids.push(row[i].id);
            }
            ids = ids.join(',');
            $.ajax({
                url : 'tOMessageController.do?doBack',
                data : {
                    ids : ids
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    $("#tOMessageList").datagrid('reload');
                }
            });
        }
        });
        }
    }
</script>