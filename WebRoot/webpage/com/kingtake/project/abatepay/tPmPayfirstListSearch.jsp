<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="flase" style="height:300px;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmPayfirstList" checkbox="true" fitColumns="true"  
  	actionUrl="tPmPayfirstController.do?datagrid&bpmStatus=2" title="垫资列表"
  	idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目_主键"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目名称"  field="projectName" query="true" queryMode="single" isLike="true"  width="120" extendParams="formatter:detailProjectInfo,"></t:dgCol>
     <t:dgCol title="垫支经费额度(元)"  field="payFunds" query="true"  queryMode="group"  width="150" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="垫支理由"  field="reason"  query="true"  queryMode="single" isLike=""  width="250"></t:dgCol>
     <t:dgCol title="垫支来源"  field="paySource"    queryMode="group"  width="250"></t:dgCol>
  <%--  <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol> --%>
      <t:dgCol title="流程状态" field="bpmStatus" width="100" queryMode="group" dictionary="bpm_status"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true"></t:dgCol>
      
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt title="查看历史" exp="bpmStatus#eq#5" funname="viewRemark(processInstId)" />
   <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1&&bpmStatus#ne#5" funname="viewHistory(processInstId)" />
   <%-- <t:dgDelOpt title="删除" url="tPmPayfirstController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" funname="add" width="720" height="480"
   	url="tPmPayfirstController.do?goAddUpdate&projectId=${projectId}" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" funname="update" width="720" height="480"
   	url="tPmPayfirstController.do?goAddUpdate"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmPayfirstController.do?doBatchDel" 
   	funname="deleteALLSelect"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="录入" icon="icon-add" funname="add" width="720" height="480" url="tPmPayfirstController.do?goAddUpdate" ></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmPayfirstController.do?goAddUpdate" 
   	funname="detail" width="720" height="480"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
//双击查看详情
function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = 720;
	var height = 480;
	var url = "tPmPayfirstController.do?goAddUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
	//设置datagrid的title
 	var projectName = window.parent.getParameter();
	var title = "";
	  title = projectName+'-垫支信息表';
    $("#tPmPayfirstList").datagrid({
   		title:title
    });
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmPayfirstController.do?upload', "tPmPayfirstList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmPayfirstController.do?exportXls",
			"tPmPayfirstList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmPayfirstController.do?exportXlsByT","tPmPayfirstList");
}

//提交流程
function submitProcess(index,nextUser) {
    var rows = $("#tPmPayfirstList").datagrid("getRows");
    //业务表名
    var tableName = 't_b_pm_payfirst';
    var businessName = '项目[${projectName}]垫支';
    var id = rows[index].id;
    //流程对应表单URL
    var formUrl = 'tBPmProjectChangeController.do?goAudit';
    var data = {'id':id,'tableName':tableName,'formUrl':formUrl,'businessName':businessName,'businessCode':'payfirst','nextUser':nextUser};
    doSubmit('tBLearningThesisController.do?startProcess',"tPmPayfirstList",data)
} 

//提交流程
function startProcess(index){
    //流程对应表单URL
    var url = 'tPmDeclareController.do?goSelectOperator2';
   if(typeof(windowapi) == 'undefined'){
        $.dialog.confirm("确定提交流程吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180,index);
        }, function() {
        }).zindex();
    }else{
        W.$.dialog.confirm("确定提交流程吗？", function() {
            openOperatorDialog("选择审批人", url, 640, 180,index);
        }, function() {
        },windowapi).zindex();
    }
   
}

//打开选择人窗口
function openOperatorDialog(title,url,width,height,index){
var width = width?width:700;
	var height = height?height:400;
	if(width=="100%"){
		width = window.top.document.body.offsetWidth;
	}
	if(height=="100%"){
		height =window.top.document.body.offsetHeight-100;
	}
	
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+url,
			lock : true,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		    	var operator = iframe.getOperator();
		    	if(operator=="" || operator ==undefined){
		    	    return false;
		    	}
		    	submitProcess(index,operator);
		    },
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+url,
			lock : true,
			width:width,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		      iframe = this.iframe.contentWindow;
	    	var operator = iframe.getOperator();
	    	if(operator=="" || operator ==undefined){
	    	    return false;
	    	}
	    	submitProcess(index,operator);
		    },
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
}

//查看流程
function viewHistory(processInstanceId) {
    goProcessHisTab(processInstanceId,'项目垫支申请流程');
}

/**
 * 查看流程意见
 */
function viewRemark(processInstId,index){
    var url = "tPmDeclareController.do?goViewProcess&processInstId="+processInstId;
    createdetailwindow("查看流程意见", url,null,null);
}

//重新提交
function compeleteProcess(taskId,index) {
        W.$.dialog.confirm('您确定修改好，重新提交垫支申请吗?', function() {
                $.ajax({
                    url : "activitiController.do?processComplete",
                    type : "POST",
                    dataType : "json",
                    data : {
                        "taskId" : taskId,
                        "nextCodeCount" : 1,
                        "model" : '1',
                        "reason" : "重新提交",
                        "option" : "重新提交"
                    },
                    async : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            reloadTable();
                        }
                    }
                });
            },function(){},windowapi);
    }
    
function detailProjectInfo(value,row,index){
	var html = "<a href=\"#\" onclick=\"openwindow('项目基本信息', 'tPmProjectController.do?goUpdateForResearchGroup&load=detail&id="+row.projectId+"', 'tPmPayfirstList', '100%', '100%')\" "+
		"style=\"color:blue;\" "+">"+value+"</a>"
	return html;
}
 </script>