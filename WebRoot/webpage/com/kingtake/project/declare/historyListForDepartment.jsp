<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmDeclareList"  fitColumns="true" actionUrl="tPmDeclareController.do?historyListDepartement&businessCode=declare" idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目id" field="projectId" hidden="true" width="90" align="center"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="当前任务节点名称" field="taskName"  width="80"></t:dgCol>
      <t:dgCol title="当前处理人" field="assignee" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="流程流转状态" field="bpmStatus" queryMode="group" width="120" extendParams="formatter:bpmStatusFormatter,"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1" funname="viewHistory(processInstId)" />
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/declare/tPmDeclareList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {

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
    //提交流程
    function startProcess(id, name) {
        //业务表名
        var tableName = 't_b_pm_declare';
        var businessName = name;
        //流程对应表单URL
        var formUrl = 'tPmDeclareController.do?goUpdateForDepartment';
        dialogConfirm('activitiController.do?startProcess&id=' + id + '&tableName=' + tableName + '&formUrl=' + formUrl
                + '&businessName=' + businessName, '确定提交流程吗？', 'tPmDeclareList');
    }

    //查看流程
    function viewHistory(processInstanceId) {
        var url = "";
        var title = "流程历史";
        url = "activitiController.do?viewProcessInstanceHistory&processInstanceId=" + processInstanceId + "&isIframe"
        addOneTab(title, url);
    }
    
    function dblDetail(rowIndex, rowDate) {
        var title = "查看";
        var width = '100%';
        var height = '100%';
        var url = "tPmDeclareController.do?tPmDeclareForResearchGroup&load=detail&projectId=" + rowDate.projectId;
        createdetailwindow(title, url, width, height);
    }
    
    //流程状态格式化
    function bpmStatusFormatter(value,row,index){
        if(value=="1"){
            return "待提交";
        }else if(value=="2"){
            return "处理中("+row.taskName+")";
        }else if(value=="3"){
            return "已完成";
        }else if(value=="4"){
            return "已作废";
        }else if(value="5"){
            return "已驳回("+row.taskName+")";
        }
    }
</script>