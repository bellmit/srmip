<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="main_depart_list">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tOMessageList" checkbox="true" sortName="sendTime" sortOrder="desc" 
			fitColumns="true" title="系统消息" pageSize="100" pageList="[100,200,300]"
			actionUrl="tOMessageController.do?datagrid" idField="id" fit="true" queryMode="group"
			onDblClick="dblDetail">
		
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="发送人id" field="senderId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="消息类型" field="type"  queryMode="group" width="60" align="center"></t:dgCol>
			<t:dgCol title="标题" field="title" query="true" queryMode="single" isLike="true" width="150"></t:dgCol>
			<t:dgCol title="内容" field="content" hidden="false" queryMode="group" width="300" overflowView="true"></t:dgCol>
			<t:dgCol title="发送人" field="senderName" query="true" isLike="true" queryMode="single" width="50"></t:dgCol>
			<t:dgCol title="发送时间" field="sendTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="150" align="center"></t:dgCol>
			<%-- <t:dgCol title="发送人删除标志" field="delFlag" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="删除时间" field="delTime" formatter="yyyy-MM-dd hh:mm:ss" hidden="true" queryMode="group" width="150" align="center"></t:dgCol> --%>
			<t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
			<%-- <t:dgDelOpt title="删除" url="tOMessageController.do?doDel&id={id}" /> --%>
			<t:dgFunOpt title="接收情况" funname="queryReceiver(id)"/>
			
			<%-- <t:dgToolBar title="录入" icon="icon-add" url="tOMessageController.do?goAdd" 
				funname="add" height="300"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tOMessageController.do?goUpdate" 
				funname="update" height="300"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove" url="tOMessageController.do?doBatchDel" 
				funname="deleteALLSelect"></t:dgToolBar> --%>
			<t:dgToolBar title="查看" icon="icon-search" url="tOMessageController.do?goView" 
				funname="detail" height="300"></t:dgToolBar>
		
		</t:datagrid>
		</div>
	</div>
	<div data-options = 
		"
			region:'east',
			title:'<t:mutiLang langKey="接收人列表"/>',
			collapsed:true,
			split:true,
			border:false,
			onExpand : function(){
				li_east = 1;
			},
			onCollapse : function() {
			 li_east = 0;
			}
		"
		style="width: 400px; overflow: hidden;" id="eastPanel">
		<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
	</div>
</div>
<script src = "webpage/com/kingtake/office/message/tOMessageList.js"></script>		
<script type="text/javascript">
	function dblDetail(rowIndex, rowDate){
		var title = "查看";
		var url = "tOMessageController.do?goView&id=" + rowDate.id;
		var width = null;
		var height = 300;
		createdetailwindow(title,url,width,height);
	}
	 
	$(document).ready(function(){
		$("#tOMessageListtb").find("input[name='title']").attr("style","width:150px;");
		
		//给时间控件加上样式
		$("#tOMessageListtb").find("input[name='sendTime_begin']")
			.attr("class","Wdate").attr("style","height:25px;width:100px;")
			.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOMessageListtb").find("input[name='sendTime_end']")
			.attr("class","Wdate").attr("style","height:25px;width:100px;")
			.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	 
	function queryReceiver(id){
		var title = '<t:mutiLang langKey="接收人列表"/>';
		if(li_east == 0||$('#main_depart_list').layout('panel','east').panel('options').title != title){
		 	$('#main_depart_list').layout('expand','east');
		}
		$('#main_depart_list').layout('panel','east').panel('setTitle', title);
		$('#main_depart_list').layout('panel','east').panel('resize', {width: 400});
		$('#userListpanel').panel("refresh", "tOMessageController.do?editUserList&messageid=" + id);
	}
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tOMessageController.do?upload', "tOMessageList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tOMessageController.do?exportXls","tOMessageList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tOMessageController.do?exportXlsByT","tOMessageList");
	}
</script>