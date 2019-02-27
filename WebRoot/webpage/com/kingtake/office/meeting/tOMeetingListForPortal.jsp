<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px; border: 0px">
  <t:datagrid name="tOMeetingList" fitColumns="false" actionUrl="tOMeetingController.do?datagrid" 
  	sortName="beginDate" sortOrder="desc" idField="id" queryMode="group" extendParams="nowrap:false,"
    onDblClick="detail('查看会议','tOMeetingController.do?goUpdate','tOMeetingList',600,500)">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="会议议题"  field="meetingTitle"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="会议室id"  field="meetingRoomId"  hidden="true"  queryMode="group" dictionary="t_o_meeting_room,id,id" width="120"></t:dgCol>
   <t:dgCol title="会议室名称"  field="meetingRoomName"   queryMode="group" dictionary="t_o_meeting_room,room_name,room_name" width="120"></t:dgCol>
   <t:dgCol title="关联项目" field="projectName" queryMode="group" width="150" ></t:dgCol>
   <t:dgCol title="主办单位id"  field="hostUnitId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主办单位"  field="hostUnitName"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="与会单位数"  field="attendUnitNum"  hidden="true"  queryMode="group"  width="80" align='right'></t:dgCol>
   <t:dgCol title="开始时间"  field="beginDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="90" align='center'></t:dgCol>
   <t:dgCol title="结束时间"  field="endDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="90" align='center'></t:dgCol>
   <t:dgCol title="参会人员id"  field="attendeesId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="参会人员姓名"  field="attendeesName"    queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="会议内容简介"  field="meetingContent" hidden='true' queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="备注"  field="memo"  hidden='true' queryMode="group"  width="200" ></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/meeting/tOMeetingList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
	//给时间控件加上样式
	$("#tOMeetingListtb").find("input[name='beginDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOMeetingListtb").find("input[name='beginDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOMeetingListtb").find("input[name='endDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tOMeetingListtb").find("input[name='endDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
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

//表格行数据双击事件
$("#tOMeetingList").datagrid({
	"onDblClickRow":function(rowIndex, rowData){
		var url = "tOMeetingController.do?goUpdate&load=detail&id="+rowData.id;
		createdetailwindow("查看会议", url, 600, 460);
	}
});
 </script>
