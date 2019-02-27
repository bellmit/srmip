<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <input id="projectId" name="projectId" type="hidden" value="${projectId}">
    <input id="nextUser" type="hidden">
    <t:datagrid name="tPmDeclareList" checkbox="true" fitColumns="true"
      actionUrl="tPmDeclareController.do?datagrid&projectId=${projectId }" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="所属项目" field="project_projectName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="研究时限开始时间" field="beginDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="研究时限结束时间" field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="项目来源" field="projectSrc" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="主要研究内容" field="researchContent" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="研究进度及成果形式" field="scheduleAndAchieve" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程流转状态" field="bpmStatus" queryMode="group" dictionary="bpm_status" width="120"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="编辑" exp="bpmStatus#ne#2&&bpmStatus#ne#3&&bpmStatus#ne#4" funname="edit(id)" />
      <t:dgDelOpt title="删除" exp="bpmStatus#eq#1" url="tPmDeclareController.do?doDel&id={id}" />
      <t:dgFunOpt title="提交流程" exp="bpmStatus#eq#1" funname="startProcess" />
      <t:dgFunOpt title="修改提交" exp="bpmStatus#eq#5" funname="compeleteProcess(taskId)" />
      <t:dgFunOpt title="查看历史" exp="bpmStatus#eq#5" funname="viewRemark(processInstId)" />
      <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1&&bpmStatus#ne#5" funname="viewHistory(processInstId)" />
      <t:dgToolBar title="录入" icon="icon-add"
        url="tPmDeclareController.do?goUpdateForResearchGroup&projectId=${projectId }" funname="add"></t:dgToolBar>
<%--       <t:dgToolBar title="编辑" icon="icon-edit"
        url="tPmDeclareController.do?goUpdateForResearchGroup&projectId=${projectId }" funname="update"></t:dgToolBar>
 --%>      <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmDeclareController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
      <t:dgToolBar title="查看" icon="icon-search"
        url="tPmDeclareController.do?goUpdateForResearchGroup&projectId=${projectId }" funname="detail"></t:dgToolBar>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/declare/tPmDeclareList.js"></script>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
              	//设置datagrid的title
             	$("#tPmDeclareList").datagrid({
             	    title:'${projectName}-申报书管理'
             	});
             	
                //给时间控件加上样式
                $("#tPmDeclareListtb").find("input[name='beginDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='beginDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='endDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='endDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmDeclareListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tPmDeclareController.do?upload', "tPmDeclareList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tPmDeclareController.do?exportXls", "tPmDeclareList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tPmDeclareController.do?exportXlsByT", "tPmDeclareList");
    }

    function edit(id) {
        var projectId = $("#projectId").val();
        var title = '编辑';
        var url = 'tPmDeclareController.do?goUpdateForResearchGroup&projectId='+projectId+"&id="+id;
        createwindow(title,url,null,null);
    }

    //提交流程
    function submitProcess(index,nextUser) {
        var rows = $("#tPmDeclareList").datagrid("getRows");
        //业务表名
        var tableName = 't_b_pm_declare';
        var businessName = rows[index]['project_projectName'];
        var id = rows[index].id;
        //流程对应表单URL
        var formUrl = 'tPmDeclareController.do?goDeclareAudit';
        doSubmit('tBLearningThesisController.do?startProcess&id=' + id + '&tableName=' + tableName + '&formUrl=' + formUrl
                + '&businessName=' + businessName + "&businessCode=declare&nextUser="+nextUser,"tPmDeclareList")
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
        goProcessHisTab(processInstanceId,'项目申报书流程');
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
            W.$.dialog.confirm('您确定修改好，重新提交申报书吗?', function() {
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
</script>