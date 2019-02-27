<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" value="${projectId }" id="projectId"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBPmChangeProjectnameList" checkbox="false" fitColumns="true" 
  	actionUrl="tBPmChangeProjectnameController.do?datagrid&projectId=${projectId}" 
  	idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="流程实例id"  field="processInstId"    hidden="true" ></t:dgCol>
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group" ></t:dgCol>
   <t:dgCol title="项目_主键"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    
   <t:dgCol title="流程状态"  field="bpmStatus" width="100"  queryMode="group" dictionary="bpm_status" ></t:dgCol>
   <t:dgCol title="变更前项目名称"  field="beforeProjectName" width="120"   queryMode="group" ></t:dgCol>
   <t:dgCol title="变更后项目名称"  field="afterProjectName" width="120"   queryMode="group" ></t:dgCol>
   <t:dgCol title="变更原因"  field="changeReason"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="变更依据"  field="changeAccording"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="变更时间"  field="changeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
  
   <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
   <!-- bpmStatus:流程状态1.未提交2.已提交3.流程结束 -->
   <t:dgFunOpt title="提交流程" exp="bpmStatus#eq#1" funname="startProcess"/>
   <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1" funname="viewHistory(processInstId)"/>
   <t:dgFunOpt title="编辑" exp="bpmStatus#eq#1" funname="goupdate(id)"/>
   <t:dgDelOpt title="删除" exp="bpmStatus#eq#1" url="tBPmChangeProjectnameController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBPmChangeProjectnameController.do?goAdd&projectId=${projectId}" funname="add" width="650" height="360"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBPmChangeProjectnameController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBPmChangeProjectnameController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBPmChangeProjectnameController.do?goUpdate&read=1" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/change/tBPmChangeProjectnameList.js"></script>		
 <script type="text/javascript">
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
 	var title = "查看";
 	var width = 650;
 	var height = 360;
 	var url = "tBPmChangeProjectnameController.do?goUpdate&read=1&load=detail&id=" + rowDate.id;
 	createdetailwindow(title,url,width,height);
 }
 
 $(document).ready(function(){
     //设置datagrid的title
     var projectName = window.parent.getParameter();
     $("#tBPmChangeProjectnameList").datagrid({
   		title:projectName+'-项目名称变更'
     });
       
	 //给时间控件加上样式
	 $("#tBPmChangeProjectnameListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 $("#tBPmChangeProjectnameListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	 $("#tBPmChangeProjectnameListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tBPmChangeProjectnameListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tBPmChangeProjectnameListtb").find("input[name='changeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#tBPmChangeProjectnameListtb").find("input[name='changeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBPmChangeProjectnameController.do?upload', "tBPmChangeProjectnameList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBPmChangeProjectnameController.do?exportXls&projectId="+$("#projectId").val(),
			"tBPmChangeProjectnameList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBPmChangeProjectnameController.do?exportXlsByT","tBPmChangeProjectnameList");
}

function goupdate(id){
	createdetailwindow('项目名称变更申请修改','tBPmChangeProjectnameController.do?goUpdate&id='+id,600,360);
}

//提交流程
function startProcess(rowIndex){
	var rowsData = $('#tBPmChangeProjectnameList').datagrid('getData').rows[rowIndex];
	var id = rowsData.id;
	var name = rowsData.beforeProjectName;
	//业务表名
	var tableName = 't_b_pm_change_projectname';
	var businessName = name;
	//流程对应表单URL
	var formUrl = 'tBPmChangeProjectnameController.do?goUpdate';
	dialogConfirm('activitiController.do?startProcess&id='+id+'&tableName='+tableName+'&formUrl='+formUrl+'&businessName='+businessName+"&businessCode=changeProjectName",'确定提交流程吗？','tBPmChangeProjectnameList');
}

//查看流程
function viewHistory(processInstanceId){
    goProcessHisTab(processInstanceId,'项目变更流程');
}
 </script>