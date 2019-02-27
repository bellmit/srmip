<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
.datagrid-cell{
	word-wrap:break-word;
}
</style>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tOMeetingList" checkbox="true" fitColumns="true" title="会议信息列表" 
			actionUrl="tOMeetingController.do?datagrid" sortName="beginDate" sortOrder="desc" 
			idField="id" queryMode="group" extendParams="nowrap:false," onDblClick="dblClickDetail">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="会议议题" field="meetingTitle" query="true" queryMode="single" isLike="true" width="150"></t:dgCol>
			<%-- <t:dgCol title="会议室id" field="meetingRoomId" hidden="true" queryMode="group" dictionary="t_o_meeting_room,id,id" width="120"></t:dgCol> --%>
			<t:dgCol title="会议室名称" field="meetingRoomName" hidden="false" query="true" queryMode="single" isLike="true" dictionary="t_o_meeting_room,room_name,room_name" width="120"></t:dgCol>
			<t:dgCol title="开始时间" field="beginDate" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="90" align='center'></t:dgCol>
			<t:dgCol title="结束时间" field="endDate" formatter="yyyy-MM-dd hh:mm:ss" query="false" queryMode="group" width="90" align='center'></t:dgCol>
			<t:dgCol title="关联项目" field="projectName" queryMode="group" width="150" ></t:dgCol>
			<%-- <t:dgCol title="主办单位id" field="hostUnitId" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="主办单位" field="hostUnitName" query="false" queryMode="single" width="120" isLike="true"></t:dgCol>
			<t:dgCol title="与会单位数" field="attendUnitNum" hidden="true" queryMode="group" width="70" align='right'></t:dgCol>
			<%-- <t:dgCol title="参会人员id" field="attendeesId" hidden="true" queryMode="group" width="200"></t:dgCol> --%>
			<t:dgCol title="参会人员姓名" field="attendeesName" queryMode="group" width="200"></t:dgCol>
			<t:dgCol title="会议内容简介" field="meetingContent" hidden="true" queryMode="group" width="200"></t:dgCol>
			<t:dgCol title="备注" field="memo" hidden='true' queryMode="group" width="120" ></t:dgCol>
			
			<t:dgCol title="操作" field="opt" width="60"></t:dgCol>
			<t:dgDelOpt title="删除" url="tOMeetingController.do?doDel&id={id}" />
			
			<t:dgToolBar title="会议录入" icon="icon-add" url="tOMeetingController.do?goAdd" 
				funname="add" width="630" height="500"></t:dgToolBar>
			<t:dgToolBar title="会议编辑" icon="icon-edit" url="tOMeetingController.do?goUpdate" 
				funname="update" width="630" height="500"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove" url="tOMeetingController.do?doBatchDel" 
				funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看会议信息" icon="icon-search" url="tOMeetingController.do?goUpdate" 
				funname="detail" width="630" height="500" ></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
			
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/office/meeting/tOMeetingList.js"></script>		
<script type="text/javascript">
//双击查看方法
function dblClickDetail(rowIndex, rowData){
	var title = "查看会议信息";
	var url = "tOMeetingController.do?goUpdate&load=detail&id=" + rowData.id;
	var width = 630;
	var height = 450;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
 	$("#tOMeetingListtb").find("select[name='meetingRoomName']").attr("style","height:23px;width:120px;vertical-align: bottom;");
	//给时间控件加上样式
 	$("#tOMeetingListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 	$("#tOMeetingListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 	$("#tOMeetingListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 	$("#tOMeetingListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOMeetingController.do?upload', "tOMeetingList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOMeetingController.do?exportXls","tOMeetingList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOMeetingController.do?exportXlsByT","tOMeetingList");
}
</script>