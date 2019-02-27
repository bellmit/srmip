<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_travel_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <%-- <div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        add-begin--Author:zhangguoming  Date:20140427 for：添加查询条件 关联项目
        <span style="display:-moz-inline-box; display:inline-block;">
            <span style="vertical-align:middle; display:-moz-inline-box; display:inline-block; width: 80px; text-align:right;" 
            title="关联项目"> 关联项目：</span>
            <input id="projectId" name="projectId" type="hidden">
            <input readonly="true" type="text" id="projectName" style="width:200px; height:23px; vertical-align:bottom;" onclick="choose_40288a85504513790150462885f80032()"/>
        </span>
    </div>
  </div> --%>
              
  <t:datagrid name="tOTravelLeaveList" checkbox="true" fitColumns="true" title="差旅-人员请假信息表" sortName="leaveTime" sortOrder="desc"
  actionUrl="tOTravelLeaveController.do?datagrid" idField="id" fit="true" queryMode="group"
  onDblClick="dblDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="请假人员id"  field="leaveId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="标题"  field="title" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="请假人员"  field="leaveName" query="true" queryMode="single" width="120"></t:dgCol>
   <%-- <t:dgCol title="请假人员单位id"  field="departId"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="请假人员单位名称"  field="departName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="请假时间"  field="leaveTime" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="关联项目"  field="projectName" query="true" queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="职务技术等级"  field="duty"  hidden="true"  queryMode="group"  width="120" codeDict="0,ZYJSZW"></t:dgCol>
   <t:dgCol title="呈报单位意见"  field="unitOpinion"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="意见人id"  field="opinionUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="意见人姓名"  field="opinionUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="意见时间"  field="opinionDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="部领导批示"  field="departInstruc"  hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="批示人姓名"  field="instrucUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="批示人id"  field="instrucUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="批示时间"  field="instrucDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="销假情况"  field="backLeaveState"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="销假时间"  field="backLeaveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="备注"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否补办"  field="reissuedFlag"    queryMode="group"  width="60" codeDict="0,SFBZ"></t:dgCol>
   <t:dgCol title="补办时间"  field="reissuedTime" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="补办理由"  field="reissuedReason"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审批状态"  field="apprStatus" codeDict="1,SPZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="编辑状态"  field="editStatus" hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt exp="apprStatus#eq#0&&editStatus#eq#1" title="删除" url="tOTravelLeaveController.do?doDel&id={id}" />
   <t:dgFunOpt exp="apprStatus#ne#1&&apprStatus#ne#2&&editStatus#eq#1" title="编辑"  funname="goUpdate(id)" />
   <%-- <t:dgFunOpt  funname="goReportAddUpdate(id)" title="出差情况阅批单"></t:dgFunOpt> --%>
   <%-- <t:dgOpenOpt title="查看出差报告" operationCode="addUpdateDetail" url="tOTravelReportController.do?goAddUpdate&id={id}"></t:dgOpenOpt> --%>
   <t:dgToolBar title="录入请假信息" icon="icon-add" url="tOTravelLeaveController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar>
   <%-- <t:dgToolBar title="编辑请假信息" icon="icon-edit" url="tOTravelLeaveController.do?goUpdate" funname="update" width="800"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOTravelLeaveController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看请假信息" icon="icon-search" url="tOTravelLeaveController.do?goUpdate" funname="detail"  width="100%" height="100%"></t:dgToolBar>
   <t:dgFunOpt exp="editStatus#eq#1" title="发送审批" funname="sendTravelAppr(id, apprStatus)" ></t:dgFunOpt>
   <t:dgToolBar title="出差情况阅批单" icon="icon-report" url="tOTravelReportController.do?goAddUpdate" funname="goAddUpdate" width="100%" height="100%"></t:dgToolBar> 
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <input id="tipMsg" type="hidden">
 <script src = "webpage/com/kingtake/office/travel/tOTravelLeaveList.js"></script>	
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
 <script type="text/javascript">
 //双击查看详情方法
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width="100%";
	 var height="100%";
	 var url = "tOTravelLeaveController.do?goUpdate&load=detail&id=" + rowDate.id;
	 createdetailwindow(title,url,width,height);
 }
 
 $(document).ready(function(){
     /* $("#tOTravelLeaveListtb").find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()); */
	 //给时间控件加上样式
	 $("#tOTravelLeaveListtb").find("input[name='leaveTime_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 $("#tOTravelLeaveListtb").find("input[name='leaveTime_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 $("#tOTravelLeaveListtb").find("input[name='opinionDate_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='opinionDate_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='instrucDate_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='instrucDate_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='backLeaveDate_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='backLeaveDate_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='reissuedTime_begin']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
  	 $("#tOTravelLeaveListtb").find("input[name='reissuedTime_end']").attr("class","Wdate").attr("style","height:23px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOTravelLeaveController.do?upload', "tOTravelLeaveList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOTravelLeaveController.do?exportXls","tOTravelLeaveList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOTravelLeaveController.do?exportXlsByT","tOTravelLeaveList");
}

//发送差旅请假审批
function sendTravelAppr(id, apprStatus){
	var title = "审批";
	var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + id +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_TRAVEL%>';
	var width = '100%';
	var height = window.top.document.body.offsetHeight-100;
	var dialogId = "apprInfo";
	
	if(apprStatus == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("差旅请假审批流程已完成，不能再发送审批");
	}else if(apprStatus == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
		url += '&tip=true';
		$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑出账审批信息！");
	}
	
	sendApprDialog(title, url, width, height, id,apprStatus,'<%=ApprovalConstant.APPR_TYPE_TRAVEL%>');
}

//更新差旅请假审批状态
function updateApprStatus(id){
	var url1 = 'tOTravelLeaveController.do?updateFinishFlag';
	var url2 = url1 + '2';
	updateFinishFlag(id, url1, url2);
}

//跳转到编辑界面
function goUpdate(){
    var url = "tOTravelLeaveController.do?goUpdate";
    update("编辑",url,"tOTravelLeaveList","100%","100%")
}
 </script>