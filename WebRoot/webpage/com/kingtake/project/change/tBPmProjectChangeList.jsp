<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" value="${projectId }" id="projectId" />
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPmProjectChangeList" checkbox="false" fitColumns="true" actionUrl="tBPmProjectChangeController.do?datagrid&projectId=${projectId}" idField="id" fit="true" queryMode="group"
      onDblClick="dblDetail">
      <t:dgCol title="id" field="id" hidden="true" queryMode="group"></t:dgCol>
      <t:dgCol title="项目_主键" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="变更原因" field="changeReason" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="变更依据" field="changeAccording" queryMode="group" width="100"></t:dgCol>
      <%-- <t:dgCol title="变更时间" field="changeTime" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol> --%>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" width="100" queryMode="group" dictionary="bpm_status"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="350" frozenColumn="true"></t:dgCol>
      <!-- bpmStatus:流程状态1.未提交2.已提交3.流程结束 -->
      <t:dgFunOpt title="提交流程" exp="bpmStatus#eq#1" funname="startProcess" />
      <t:dgFunOpt title="修改提交" exp="bpmStatus#eq#5" funname="compeleteProcess(taskId)" />
      <t:dgFunOpt title="查看历史" exp="bpmStatus#eq#5" funname="viewRemark(processInstId)" />
      <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1&&bpmStatus#ne#5" funname="viewHistory(processInstId)" />
      <t:dgFunOpt title="编辑" exp="bpmStatus#eq#1" funname="goUpdate(id)" />
      <t:dgFunOpt title="编辑" exp="bpmStatus#eq#5" funname="goUpdate(id)" />
      <t:dgDelOpt title="删除" exp="bpmStatus#eq#1" url="tBPmProjectChangeController.do?doDel&id={id}" />
      <t:dgFunOpt title="导出WORD" funname="ExportWord(id)" />
      <t:dgToolBar title="录入" icon="icon-add" url="tBPmProjectChangeController.do?goProjectUpdate&id=${projectId}" funname="goAdd" width="100%" height="100%"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPmProjectChangeController.do?goProjectChangeInfo&opt=edit" funname="detail" height="100%" width="100%"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/change/tBPmChangeProjectnameList.js"></script>
<script type="text/javascript">
    //双击查看详情
    function dblDetail(rowIndex, rowDate) {
        var title = "查看";
        var width = '100%';
        var height = '100%';
        var url = 'tBPmProjectChangeController.do?goProjectChangeInfo&opt=edit&load=detail&id='+ rowDate.id;
        createdetailwindow(title, url, width, height);
    }

    $(document).ready(
            function() {
                //设置datagrid的title
                var projectName = window.parent.getParameter();
                $("#tBPmChangeProjectnameList").datagrid({
                    title : projectName + '-项目名称变更'
                });

                //给时间控件加上样式
                $("#tBPmChangeProjectnameListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBPmChangeProjectnameListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBPmChangeProjectnameListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBPmChangeProjectnameListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBPmChangeProjectnameListtb").find("input[name='changeTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBPmChangeProjectnameListtb").find("input[name='changeTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBPmChangeProjectnameController.do?upload', "tBPmChangeProjectnameList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBPmChangeProjectnameController.do?exportXls&projectId=" + $("#projectId").val(),
                "tBPmChangeProjectnameList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBPmChangeProjectnameController.do?exportXlsByT", "tBPmChangeProjectnameList");
    }

    //录入
    function goAdd(title,url,gname,width,height) {
        gridname = gname;
        width = window.top.document.body.offsetWidth;
        height = window.top.document.body.offsetHeight - 100;
        title = "项目信息变更录入";
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id:'projectUpdateDialog',
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                title : title,
                opacity : 0.3,
                cache : false,
                button:[{
                    name: '生成项目变更明细',
                    focus: true,
                    callback: function(){
                        iframe = this.iframe.contentWindow;
                        saveObj();
                        return false;
                    }
                }],
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        } else {
            W.$.dialog({
                id:'projectUpdateDialog',
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                parent : windowapi,
                title : title,
                opacity : 0.3,
                cache : false,
                button:[{
                    name: '生成项目变更明细',
                    focus: true,
                    callback: function(){
                        iframe = this.iframe.contentWindow;
                        saveObj();
                        return false;
                    }
                }],
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }

    function goUpdate(id,index) {
        createwindow('项目名称变更申请修改', 'tBPmProjectChangeController.do?goProjectChangeInfo&opt=edit&id=' + id, '100%', '100%');
    }

  //提交流程
    function submitProcess(index,nextUser) {
        var rows = $("#tBPmProjectChangeList").datagrid("getRows");
        //业务表名
        var tableName = 't_b_pm_project_change';
        var businessName = '项目['+rows[index]['projectName']+']信息变更';
        var id = rows[index].id;
        //流程对应表单URL
        var formUrl = 'tBPmProjectChangeController.do?goAudit';
        var data = {'id':id,'tableName':tableName,'formUrl':formUrl,'businessName':businessName,'businessCode':'projectChange','nextUser':nextUser};
        doSubmit('tBLearningThesisController.do?startProcess',"tBPmProjectChangeList",data)
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
        goProcessHisTab(processInstanceId,'项目信息变更流程');
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
  //导出WORD
    function ExportWord(id) {
    	JeecgExcelExport("tBPmProjectChangeController.do?ExportWord&id=" + id,"tBPmProjectChangeList");       
    }
</script>