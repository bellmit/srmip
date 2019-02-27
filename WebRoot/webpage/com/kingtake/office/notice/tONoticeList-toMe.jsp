<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="main_depart_list">
  <div region="center" style="padding:1px;" >
  <t:datagrid name="tONoticeList" checkbox="false" pageSize="100" pageList="[100,200,300]"
  	fitColumns="true" title="通知公告" actionUrl="tONoticeController.do?datagridToMe" 
  	idField="id" fit="true" queryMode="group" 
  	onDblClick="godetailview" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="发送人id"  field="SENDER_ID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="内容"  field="content"   queryMode="group" width="300" ></t:dgCol>
   <t:dgCol title="发送人"  field="sendName"  query="true" queryMode="single"  width="60" ></t:dgCol>
   <t:dgCol title="关联项目"  field="projName"  queryMode="single"  width="120" hidden="false" isLike="true"></t:dgCol>
   <t:dgCol title="发送时间"  field="sendTime" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group" align="center" width="120"></t:dgCol>
   <t:dgCol title="关联文号"  field="fileNum"   queryMode="single"  width="100" hidden="false" isLike="true"></t:dgCol> 
   <t:dgCol title="是否阅读"  field="READFLAG" replace="否_0,是_1"  width="60"></t:dgCol>
<%--    <t:dgCol title="关联项目id"  field="projId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <%-- 
   <t:dgCol title="操作" field="opt" width="80"></t:dgCol>
   <t:dgDelOpt title="删除" url="tONoticeController.do?doDel&id={id}" />
   <t:dgFunOpt title="接收人信息" funname="queryReceiver(id)"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tONoticeController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tONoticeController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tONoticeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search"  funname="godetailview" ></t:dgToolBar>
   <t:dgToolBar title="回复发送人" icon="icon-redo"  funname="goResponse" ></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
  </div>
 <div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
</div>
</div>
 <script src = "webpage/com/kingtake/office/notice/tONoticeList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
	 var li_east = 0;
 	 $("#tONoticeListtb").find("input[name='PROJ_NAME']").attr("style","width:150px;");
	 //给时间控件加上样式
	 $("#tONoticeListtb").find("input[name='sendTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 $("#tONoticeListtb").find("input[name='sendTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function godetailview(){
		var row = $('#tONoticeList').datagrid('getSelections')[0];
		var id = row.id;
		$.dialog({
			content: 'url:tONoticeController.do?goUpdate&flag=0&load=detail&id='+id,
			lock : true,
			//zIndex:1990,
			width:800,
			height:400,
			title:"查看",
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtONoticeList();
		    }
		}).zindex();
	}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tONoticeController.do?upload', "tONoticeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tONoticeController.do?exportXls","tONoticeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tONoticeController.do?exportXlsByT","tONoticeList");
}

function queryReceiver(id){
    var title = '<t:mutiLang langKey="member.list"/>';
    if(li_east == 0||$('#main_depart_list').layout('panel','east').panel('options').title != title){
        $('#main_depart_list').layout('expand','east');
    }
    <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
    $('#main_depart_list').layout('panel','east').panel('setTitle', title);
    $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
    $('#userListpanel').panel("refresh", "tONoticeController.do?editUserList&noticeid=" + id);
}

//回复
function goResponse(){
    var tabId = "tONoticeList";
    gridname=tabId;
	var rowsData = $('#'+tabId).datagrid('getSelections');
	if(rowsData.length!=1){
	    tip("请选择一条通知公告进行回复！");
	    return false;
	}
	var id = rowsData[0].id;
	var title="回复发送人";
    var url = "tONoticeController.do?goMyResponseList&noticeId="+id;
    createdetailwindow(title,url,"100%","100%");
}
 </script>