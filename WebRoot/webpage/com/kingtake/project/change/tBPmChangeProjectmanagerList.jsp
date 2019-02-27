<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" value="${projectId }" id="projectId"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBPmChangeProjectmanagerList" checkbox="false" fitColumns="true" 
  	title="项目负责人变更信息表"  idField="id" fit="true" queryMode="group"
  	actionUrl="tBPmChangeProjectmanagerController.do?datagrid&project.id=${projectId}" onDblClick="dblDetail">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目_主键"  field="project.id"  hidden="true"  queryMode="group"></t:dgCol>
   
   <t:dgCol title="项目名称"  field="project.projectName" queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="变更前项目负责人"  field="beforeProjectManager.realName" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="变更后项目负责人"  field="afterProjectManager.realName" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="变更原因"  field="changeReason"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="变更依据"  field="changeAccording"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="变更时间"  field="changeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="100" align="center"></t:dgCol>
   <t:dgCol title="流程状态"  field="bpmStatus"   queryMode="group" dictionary="bpm_status" width="100"></t:dgCol>
   <t:dgCol title="流程实例id"  field="processInstId" hidden="true" ></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt title="提交流程" exp="bpmStatus#eq#1" funname="startProcess"/>
   <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1" funname="viewHistory(processInstId)"/>
   <t:dgFunOpt title="编辑" exp="bpmStatus#eq#1" funname="goupdate(id)"/>
   <t:dgDelOpt title="删除" exp="bpmStatus#eq#1" url="tBPmChangeProjectmanagerController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBPmChangeProjectmanagerController.do?goAdd&projectId=${projectId}" funname="add"  width="650" height="360"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBPmChangeProjectmanagerController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBPmChangeProjectmanagerController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBPmChangeProjectmanagerController.do?goUpdate&read=1" funname="detail"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/change/tBPmChangeProjectmanagerList.js"></script>		
 <script type="text/javascript">
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
 	var title = "查看";
 	var width = null;
 	var height = null;
 	var url = "tBPmChangeProjectmanagerController.do?goUpdate&load=detail&id=" + rowDate.id;
 	createdetailwindow(title,url,width,height);
 }
 
 $(document).ready(function(){
	//给时间控件加上样式
	$("#tBPmChangeProjectmanagerListtb").find("input[name='changeTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tBPmChangeProjectmanagerListtb").find("input[name='changeTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tBPmChangeProjectmanagerListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tBPmChangeProjectmanagerListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tBPmChangeProjectmanagerListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tBPmChangeProjectmanagerListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//提交流程
 function startProcess(index){
	var data= $("#tBPmChangeProjectmanagerList").datagrid("getRows");
	var id = data[index]['id'];
	var name = data[index]['project.projectName'];
 	//业务表名
 	var tableName = 't_b_pm_change_projectmanager';
 	var businessName = name;
 	//流程对应表单URL
 	var formUrl = 'tBPmChangeProjectmanagerController.do?goUpdate';
 	dialogConfirm('activitiController.do?startProcess&id='+id+'&tableName='+tableName+'&formUrl='+formUrl+'&businessName='+businessName+"&businessCode=changeProjectManager",'确定提交流程吗？','tBPmChangeProjectmanagerList');
 }

 //查看流程
 function viewHistory(processInstanceId){
     goProcessHisTab(processInstanceId,'项目变更流程');
 }
 
 function goupdate(id){
		createdetailwindow('项目负责人变更申请修改','tBPmChangeProjectmanagerController.do?goUpdate&id='+id,700,400);
	}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBPmChangeProjectmanagerController.do?upload', "tBPmChangeProjectmanagerList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBPmChangeProjectmanagerController.do?exportXls&project.id="+$("#projectId").val(),
			"tBPmChangeProjectmanagerList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBPmChangeProjectmanagerController.do?exportXlsByT","tBPmChangeProjectmanagerList");
}
 </script>