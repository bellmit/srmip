<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px; border: 0px">
  <t:datagrid name="tONoticeList" fitColumns="true" actionUrl="tONoticeController.do?datagridToMe" idField="id" fit="true">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送人id"  field="SENDER_ID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single" sortable="false"  width="150"></t:dgCol>
   <t:dgCol title="内容"  field="content"  sortable="false"  queryMode="group" width="300" ></t:dgCol>
   <t:dgCol title="发送人"  field="SENDER_NAME"  query="true" queryMode="single"  width="60" ></t:dgCol>
   <t:dgCol title="发送时间"  field="SEND_TIME" formatter="yyyy-MM-dd hh:mm:ss"  query="true" queryMode="group" align="center" width="120"></t:dgCol>
   <t:dgCol title="关联项目"  field="PROJ_NAME"  queryMode="single"  width="120" hidden="false"></t:dgCol>
   <t:dgCol title="关联文号"  field="FILE_NUM"   queryMode="single"  width="100" hidden="false"></t:dgCol> 
<%--    <t:dgCol title="关联项目id"  field="projId"  hidden="true"  width="120"></t:dgCol> --%>
  </t:datagrid>
  </div>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/notice/tONoticeList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			/* $("#tONoticeListtb").find("input[name='sendTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tONoticeListtb").find("input[name='sendTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});}); */
 });
 
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
    if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
        $('#main_depart_list').layout('expand','east');
    }
    <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
    $('#main_depart_list').layout('panel','east').panel('setTitle', title);
    $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
    $('#userListpanel').panel("refresh", "tONoticeController.do?userList&id=" + id);
}

//表格行数据双击事件
$("#tONoticeList").datagrid({
	"onDblClickRow":function(rowIndex, rowData){
		var url = "tONoticeController.do?goUpdate&&flag=0&load=detail&id="+rowData.id;
		createdetailwindow("查看通知", url, 800);
	}
});
 </script>