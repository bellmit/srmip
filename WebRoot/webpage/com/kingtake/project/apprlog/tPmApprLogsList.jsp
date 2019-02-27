<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<script type="text/javascript">
//操作列格式化
function optFormatter(value,row,index){
	var userId = $("#userId").val();
	var optUserId =  row['apprSendLog.operateUserid'];
	var operateStatus =  row.operateStatus;
	var recId = row.id;
	if(optUserId==userId&&operateStatus=='0'){
		return '<a href="#" onclick=cancelAppr("'+recId+'")>[撤回]</a>';
	}
	return "";
}
</script>
<input id="userId" type="hidden" value="${userId}">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmApprReceiveLogsList" checkbox="false" fitColumns="true" 
  	 actionUrl="tPmApprLogsController.do?datagrid&apprId=${apprId}&apprType=${apprType}" 
  	 idField="id" fit="true" queryMode="group" sortName="apprSendLog.operateDate" pagination="false">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="审核表主键"  field="apprId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核节点"  field="suggestionType" hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="审核节点"  field="suggestionTypeName"   queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="发送人"  field="apprSendLog.operateUsername"   queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="发起人id"  field="apprSendLog.operateUserid" hidden="true"  queryMode="group"  width="50"></t:dgCol>
   <t:dgCol title="发送时间"  field="apprSendLog.operateDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="100" align="center"></t:dgCol>
   <t:dgCol title="接收人id"  field="receiveUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核人"  field="receiveUsername"    queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="接收时间"  field="receiveTime"  formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="接收人部门id"  field="receiveDepartid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审核人部门"  field="receiveDepartname"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处理状态"  field="operateStatus"  replace="待审核_0,已处理_1"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="处理时间"  field="operateTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="100" align="center"></t:dgCol>
   <t:dgCol title="审核意见"  field="suggestionCode"  codeDict="1,SPYJ"  queryMode="group"  width="50"></t:dgCol>
   <t:dgCol title="意见内容"  field="suggestionContent"  overflowView="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt1" width="60" frozenColumn="true" extendParams="formatter:optFormatter,"></t:dgCol>
   <%-- <t:dgFunOpt exp="apprSendLog.operateUserid#eq\#${userId}&&operateStatus#eq#0" funname="sendAudit(id,auditStatus)" title="撤回"></t:dgFunOpt> --%>
   <%-- <c:if test="${send}">
       <t:dgToolBar title="发送审核" icon="icon-add" url="tPmApprLogsController.do?goAdd"
       	funname="sendAppr" width="460" height="230"></t:dgToolBar>
   </c:if> --%>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript">
function sendAppr(title,url,gname,width,height){
	gridname = gname;
	url += "&apprId=${apprId}&apprType=${apprType}";
	createChildWindow(title, url, width, height);
}
$(document).ready(function(){
	gridname = 'tPmApprReceiveLogsList';
	//给时间控件加上样式
	$("#tPmContractReceiveLogsListtb").find("input[name='operateTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmContractReceiveLogsListtb").find("input[name='operateTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmContractReceiveLogsController.do?upload', "tPmContractReceiveLogsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmContractReceiveLogsController.do?exportXls","tPmContractReceiveLogsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmContractReceiveLogsController.do?exportXlsByT","tPmContractReceiveLogsList");
}

function cancelAppr(recId){
	$.messager.confirm('确认','您确认要撤回该发送记录吗？',function(r){    
	    if (r){    
	    	$.ajax({
	    		url:'tPmApprLogsController.do?cancel',
	    	    data:{"recId":recId},
	    		cache:false,
	    		type:'POST',
	    		dataType:'json',
	    		success:function(data){
	    			tip(data.msg);
	    			if(data.success){
	    				reloadTable();
	    			}
	    		}
	    	});   
	    }    
	});  
}
</script>