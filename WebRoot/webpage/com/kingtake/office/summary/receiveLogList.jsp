<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base> 
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;"> 
  <t:datagrid name="tOReceiveLogList" checkbox="true" fitColumns="true"   
  	 idField="id" fit="true"  actionUrl="tOResearchActivityController.do?logDatagrid&researchId=${researchId }" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送人id"  field="senderId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送人名称"  field="senderName"     width="120"></t:dgCol>
   <t:dgCol title="发送时间"  field="sendTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人id"  field="receiverId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人名称"  field="receiverName"     width="120"></t:dgCol>
   <t:dgCol title="处理时间"  field="receiveTime"  formatter="yyyy-MM-dd hh:mm:ss"   width="120"></t:dgCol>
   <t:dgCol title="是否处理"  field="receiveFlag" replace="未处理_0,已处理_1" width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/information/tOHandoverList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
		$("#tOHandoverListtb").find("input[name='handoverTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='handoverTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='recieveTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='recieveTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//双击查看方法
function dblClickDetail(rowIndex, rowData){
    var title = "查看交班材料信息";
    var url = "tOHandoverController.do?goUpdate&load=detail&id=" + rowData.id;
    var width = 700;
    var height = 350;
    createdetailwindow(title,url,width,height);
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOHandoverController.do?upload', "tOHandoverList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOHandoverController.do?exportXls","tOHandoverList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOHandoverController.do?exportXlsByT","tOHandoverList");
}

function getSelectedRows(){
   var rows = $("#tOHandoverList").datagrid("getChecked");
   return rows;
}
 </script>