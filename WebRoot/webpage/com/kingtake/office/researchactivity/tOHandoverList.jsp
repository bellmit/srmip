<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOHandoverList" checkbox="true" fitColumns="true" title="交班材料信息"  
  	sortName="handoverTime" sortOrder="desc" idField="id" fit="true" onDblClick="dblClickDetail"
  	actionUrl="tOHandoverController.do?datagrid" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建id"  field="handoverId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="handoverName"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人单位"  field="handoverDepartName"   query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="提交时间"  field="handoverTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="本周工作内容"  field="handoverContent"   queryMode="group"  width="120" overflowView="true"></t:dgCol>
   <t:dgCol title="下周工作计划"  field="nextWeekWorkContent"    queryMode="group"  width="120" overflowView="true"></t:dgCol>
   <t:dgCol title="承担主要任务情况"  field="mainTask"    queryMode="group"  width="120" overflowView="true"></t:dgCol>
   <%-- <t:dgCol title="接收人"  field="receiver" queryMode="group"  width="120" dictionary="t_s_base_user,id,realname"></t:dgCol> --%>
   <t:dgCol title="接收人"  field="receiverName" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="接收时间"  field="recieveTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="交班状态"  field="submitFlag"   queryMode="group"  width="80" codeDict="1,TJZT"></t:dgCol>
   <t:dgCol title="状态变更时间"  field="submitTime" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgToolBar title="查看交班材料" icon="icon-search" url="tOHandoverController.do?goUpdate" funname="detail" height="500"></t:dgToolBar>
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