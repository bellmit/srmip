<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<!-- <div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;"> -->
  <t:datagrid name="tOReceiveLogList" checkbox="false" fitColumns="true"   
  	 idField="id" fit="true"  actionUrl="tOSendDownBillController.do?logDatagrid&billId=${billId }" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人id"  field="receiverId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人名称"  field="receiverName"     width="120"></t:dgCol>
   <t:dgCol title="接收时间"  field="receiveTime"  formatter="yyyy-MM-dd hh:mm:ss"   width="120"></t:dgCol>
   <t:dgCol title="是否接收"  field="status" replace="未接收_0,已接收_1" width="120"></t:dgCol>
   <t:dgToolBar title="公文权限" icon="icon-edit" funname="editBillShow()" ></t:dgToolBar>
  </t:datagrid>
  </div>
 <!-- </div> -->
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
		$("#tOHandoverListtb").find("input[name='handoverTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='handoverTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='recieveTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tOHandoverListtb").find("input[name='recieveTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
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

//更新公文权限
function editBillShow(){
   var selections = $("#tOReceiveLogList").datagrid("getSelections");
   if(selections.length==0){
       tip("请先选择一行接收记录");
       return false;
   }
   var id = selections[0].id;
   add("更新公文权限","tOSendDownBillController.do?goOperation&id="+id,"tOReceiveLogList",300,150);
}
 </script>