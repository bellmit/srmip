<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="main_depart_list">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tONoticeList" checkbox="false" sortName="sendTime" sortOrder="desc" fitColumns="true" 
  title="通知公告" actionUrl="tONoticeController.do?datagrid" idField="id" fit="true" queryMode="group" pageSize="100" pageList="[100,200,300]"
  onDblClick="detail('查看','tONoticeController.do?goUpdate','tONoticeList',800,null)">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送人id"  field="senderId"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="标题"  field="title" sortable="true"   query="true" queryMode="single"  width="150" isLike="true"></t:dgCol>
   <t:dgCol title="内容"  field="content"    queryMode="group" width="250" hidden="false" ></t:dgCol>
   <t:dgCol title="发送人"  field="senderName"    queryMode="single" query="true" width="60" isLike="true"></t:dgCol>
   <t:dgCol title="发送时间"  field="sendTime"  formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center" width="120"></t:dgCol>
   <t:dgCol title="关联项目"  field="projName"  query="true" queryMode="single" hidden="false" width="120"  isLike="true"></t:dgCol> 
   <t:dgCol title="关联文号"  field="fileNum"  query="true" queryMode="single" hidden="false"  width="100"  isLike="true"></t:dgCol> 
<%--    <t:dgCol title="关联项目id"  field="projId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgFunOpt title="删除" funname="delNotice(id)" />
   <t:dgFunOpt title="接收人信息" funname="queryReceiver(id)"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tONoticeController.do?goAdd" funname="add" width="800"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tONoticeController.do?goUpdate" funname="update"></t:dgToolBar>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tONoticeController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tONoticeController.do?goUpdate" funname="detail" width="800"></t:dgToolBar>
   <t:dgToolBar title="留言" icon="icon-redo"  funname="goResponse" ></t:dgToolBar>
   <t:dgToolBar title="添加提醒" icon="icon-edit"  funname="goWarn" ></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
  </div>
 <div data-options="region:'east',
	title:'<t:mutiLang langKey="接收人列表"/>',
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
	$("#tONoticeListtb").find("input[name='projName']").attr("style","width:150px;");
	
	var li_east = 0;
	//给时间控件加上样式
	$("#tONoticeListtb").find("input[name='sendTime_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tONoticeListtb").find("input[name='sendTime_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function delNotice(id){
	 $.ajax({
		 url:'tONoticeController.do?relevance&id='+id,
		 success:function(data){
			 var data = JSON.parse(data);
			 if(data.obj=='1'){
				 $.dialog.confirm(data.msg,function(){//
					 doDelNotice(id);
				 },function(){
					  
				 });
			 }else{
				 doDelNotice(id);
			 }
		 }
	 });
 }
 
 function doDelNotice(id){
	 $.ajax({
		 url:'tONoticeController.do?doDel&id='+id,
		 success:function(data){
			 var data = JSON.parse(data);
			 tip(data.msg);
			 reloadtONoticeList();
		 }
	 });
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
    var title = '<t:mutiLang langKey="接收人列表"/>';
    if(li_east == 0||$('#main_depart_list').layout('panel','east').panel('options').title != title){
        $('#main_depart_list').layout('expand','east');
    }
    <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
    $('#main_depart_list').layout('panel','east').panel('setTitle', title);
    $('#main_depart_list').layout('panel','east').panel('resize', {width: 400});
    $('#userListpanel').panel("refresh", "tONoticeController.do?editUserList&noticeid=" + id);
}

//回复
function goResponse(){
    var tabId = "tONoticeList";
    gridname=tabId;
	var rowsData = $('#'+tabId).datagrid('getSelections');
	if(rowsData.length!=1){
	    tip("请选择一条通知公告进行留言！");
	    return false;
	}
	var id = rowsData[0].id;
	var title="留言";
    var url = "tONoticeController.do?goResponseList&noticeId="+id;
    createdetailwindow(title,url,"100%","100%");
}

//公共提醒
function goWarn(){
    var tabId = "tONoticeList";
    gridname=tabId;
	var rowsData = $('#'+tabId).datagrid('getSelections');
	if(rowsData.length!=1){
	    tip("请选择一条通知公告！");
	    return false;
	}
	var id = rowsData[0].id;
	var title="提醒";
    var url = "tONoticeController.do?goWarn&noticeId="+id;
    createwindow(title,url,600,200);
}
 </script>