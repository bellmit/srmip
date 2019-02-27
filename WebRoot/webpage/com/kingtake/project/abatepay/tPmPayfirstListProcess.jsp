<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="projectId" value="${projectId }" type="hidden"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmPayfirstList" checkbox="true" fitColumns="true" 
  	actionUrl="tPmPayfirstController.do?datagrid&projectId=${projectId}" 
  	idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目_主键"  field="projectId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="垫支经费额度(元)"  field="payFunds"  queryMode="group"  width="100" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
     <t:dgCol title="年度"  field="payYear"    queryMode="group"  width="40"></t:dgCol>
     <t:dgCol title="垫支科目代码"  field="paySubjectcode"    queryMode="group"  width="100"></t:dgCol>
     <t:dgCol title="垫支理由"  field="reason"    queryMode="group"  width="200"></t:dgCol>
     <t:dgCol title="垫支来源"  field="paySource" replace="大学_1,责任单位_2,承研单位_3" queryMode="group" width="100"></t:dgCol>
     <t:dgCol title="财务审核状态"  field="cwStatus" queryMode="group" width="100" extendParams="formatter:formatCwStatus,"></t:dgCol>
     <t:dgCol title="归垫状态"  field="gdStatus" hidden="true" replace="未归垫_0,归垫中_1,已归垫_2" queryMode="group" width="100"></t:dgCol>
  <%--  <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol> --%>
      <t:dgCol title="流程状态" field="bpmStatus" width="150" queryMode="group" dictionary="bpm_status"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" width="100" hidden="true"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" width="100" hidden="true"></t:dgCol>
      
   <t:dgCol title="操作" field="opt" width="300" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt title="提交流程" exp="bpmStatus#eq#1" funname="startProcess" />
   <t:dgFunOpt title="编辑" exp="bpmStatus#eq#1" funname="goUpdate(id)" />
   <t:dgFunOpt title="编辑" exp="bpmStatus#eq#5" funname="goUpdate(id)" />
   <t:dgDelOpt title="删除" exp="bpmStatus#eq#1" url="tPmPayfirstController.do?doDel&id={id}" />
   <%--<t:dgFunOpt title="归垫" exp="cwStatus#eq#1&&gdStatus#eq#0" funname="gdProcess(id)" />--%>
   <t:dgFunOpt title="查看归垫" exp="cwStatus#eq#1&&gdStatus#eq#0" funname="gdDeatal(projectId,paySubjectcode,payYear)" />
   <t:dgFunOpt title="查看历史" exp="bpmStatus#eq#5" funname="viewRemark(processInstId)" />
   <t:dgFunOpt title="修改提交" exp="bpmStatus#eq#5" funname="compeleteProcess(taskId)" />
   <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1&&bpmStatus#ne#5" funname="viewHistory(processInstId)" />
   <t:dgToolBar title="录入" icon="icon-add" funname="add" width="720" height="480" url="tPmPayfirstController.do?goAddUpdate&projectId=${projectId}" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmPayfirstController.do?goAddUpdate" funname="detail" width="720" height="480"></t:dgToolBar>
   <%-- <t:dgDelOpt title="删除" url="tPmPayfirstController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" funname="add" width="720" height="480"
   	url="tPmPayfirstController.do?goAddUpdate&projectId=${projectId}" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" funname="update" width="720" height="480"
   	url="tPmPayfirstController.do?goAddUpdate"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmPayfirstController.do?doBatchDel" 
   	funname="deleteALLSelect"></t:dgToolBar> --%>
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
	var title = "";
	title = '${projectName}-垫支信息表';
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
	JeecgExcelExport("tPmPayfirstController.do?exportXls&projectId="+$("#projectId").val(),
			"tPmPayfirstList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmPayfirstController.do?exportXlsByT","tPmPayfirstList");
}

function goUpdate(id,index) {
    createwindow('编辑垫支信息', 'tPmPayfirstController.do?goAddUpdate&id=' + id, '720px', '480px');
}

/**
 * 处理归垫申请
 */
function gdProcess(id) {
	W.$.dialog.confirm('您确定要申请归垫吗?', function() {
        $.ajax({
            url : "tPmPayfirstController.do?doGd",
            type : "POST",
            dataType : "json",
            data : {
                "id" : id
            },
            async : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    reloadTable();
                }
            },
            error : function() {
            	W.$.dialog.alert("系统故障，执行归垫申请失败，请稍后再试");
            }
        });
    },function(){},windowapi);
}

//提交流程
function submitProcess(index,nextUser) {
    var rows = $("#tPmPayfirstList").datagrid("getRows");
    //业务表名
    var tableName = 't_b_pm_payfirst';
    var businessName = '项目[${projectName}]垫支';
    var id = rows[index].id;
    //流程对应表单URL
    var formUrl = 'tPmPayfirstController.do?goAudit';
    var data = {'id':id,'tableName':tableName,'formUrl':formUrl,'businessName':businessName,'businessCode':'payFirst','nextUser':nextUser};
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

/**
 * 格式化财务审核状态
 */
function formatCwStatus(value, row, index) {
	var status = {
		"1": "已审核",
		"3": "已否决",
		"4": "已提交"
	};
	
	if (value == "") {
		return "未提交";
	} else {
		return status[value] ? status[value] : "未知";
	}
}
/***
 *  查看归垫
 *  2018.10.11 add by lyz
 *
 * **/
function  gdDeatal(id,paySubjectcode,payYear){
    var title = "查看归垫";
    var width = 1000;
    var height = 500;
    var url = 'tPmPayfirstController.do?tPmPayfirstListGdDetail&projectId='+id+'&paySubjectcode='+paySubjectcode+'&payYear='+payYear;
    createdetailwindow(title,url,width,height);
}
 </script>