<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function bpmStatusFormatter(value, row, index) {
        if (value == '1') {
            return '待提交';
        } else if (value == '2') {
            return '流转中(' + row.taskName + ')';
        } else if (value == '3') {
            return '已完成';
        } else if (value == '4') {
            return '作废';
        } else if (value == '5') {
            return '已驳回(' + row.taskName + ')';
        }
    }
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <input id="role" type="hidden" value="${role}">
    <t:datagrid name="tBLearningTeamList" checkbox="true" fitColumns="true" title="学术团体信息表" actionUrl="tBLearningTeamController.do?datagrid&role=${role}&businessCode=learning_team" idField="id"
      fit="true" queryMode="group" onDblClick="dblDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="团体名称" field="teamName" isLike="true" query="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="团体业务主管单位" field="manageDepart" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="兼任领导职务名称" field="leaderJob" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="批准单位" field="approveUnit" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="社团类别" field="collegeType" codeDict="1,STLB" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="是否在民政部门注册" field="isRegister" replace="是_1,否_0" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="参加团体时间" field="collegeTime" formatter="yyyy" queryMode="group" width="200"></t:dgCol>
      <t:dgCol title="审核状态" field="auditStatus" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" queryMode="group" width="120" extendParams="formatter:bpmStatusFormatter,"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="任务名称" field="taskName" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="分配人" field="assigneeName" hidden="true" queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
      <c:if test="${role eq 'research'}">
        <t:dgDelOpt title="删除" exp="bpmStatus#eq#1" url="tBLearningTeamController.do?doDel&id={id}" />
        <t:dgToolBar title="录入" icon="icon-add" url="tBLearningTeamController.do?goAddUpdate" funname="add" width="600" height="350"></t:dgToolBar>
        <t:dgFunOpt title="编辑" exp="bpmStatus#eq#1" funname="edit(id)" />
        <t:dgFunOpt title="提交流程" exp="bpmStatus#eq#1" funname="startProcess(id)" />
        <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1&&bpmStatus#ne#5" funname="viewHistory(processInstId)" />
        <t:dgConfOpt exp="bpmStatus#eq#5&&assigneeName#empty#true" url="activitiController.do?claim&taskId={taskId}" message="确定签收?" title="签收"></t:dgConfOpt>
        <t:dgFunOpt exp="bpmStatus#eq#5&&assigneeName#empty#false" funname="openhandleMix(taskId,taskName)" title="办理"></t:dgFunOpt>
        <t:dgFunOpt exp="bpmStatus#eq#5&&assigneeName#empty#false" funname="selectEntruster(taskId,taskName)" title="委托"></t:dgFunOpt>
      </c:if>
      <c:if test="${role eq 'depart'}">
        <t:dgFunOpt title="查看流程" exp="bpmStatus#ne#1" funname="viewHistory(processInstId)" />
      </c:if>
      <t:dgToolBar title="查看" icon="icon-search" url="tBLearningTeamController.do?goAddUpdate" funname="detail" width="600" height="350"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
  <input type="hidden" id="id">
  <input type="hidden" id="businessName">
</div>
<script src="webpage/com/kingtake/project/learning/tBLearningTeamList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tBLearningTeamListtb").find("input[name='collegeTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningTeamListtb").find("input[name='collegeTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningTeamListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningTeamListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningTeamListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningTeamListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBLearningTeamController.do?upload', "tBLearningTeamList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBLearningTeamController.do?exportXls", "tBLearningTeamList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBLearningTeamController.do?exportXlsByT", "tBLearningTeamList");
    }

    //编辑
    function edit(id) {
        var title = '编辑';
        var url = "tBLearningTeamController.do?goAddUpdate&id=" + id;
        createwindow(title, url, '600px', '350px');
    }
    //提交流程
    function startProcess(id, index) {
        var rows = $("#tBLearningTeamList").datagrid("getRows");
        var businessName = rows[index]['teamName'];
        $("#businessName").val("学术团体[" + businessName + "]");
        $("#id").val(id);
        //流程对应表单URL
        var url = 'tBLearningThesisController.do?goSelectOperator';
        $.dialog.confirm("确定提交流程吗？", function() {
            add("选择审批人", url, "tBLearningTeamList", 640, 180);
        }, function() {
        }).zindex();
    }

    function getId() {
        var id = $("#id").val();
        return id;
    }

    function getTableName() {
        return "t_b_learning_team";
    }

    function getBusinessName() {
        var businessName = $("#businessName").val();
        return businessName;
    }

    function getFormUrl() {
        var formUrl = 'tBLearningTeamController.do?goAudit';
        return formUrl;
    }

    function getBusinessCode() {
        return "learning_team";
    }

    //查看流程
    function viewHistory(processInstanceId) {
        goProcessHisTab(processInstanceId, '学术团体审批流程');
    }

    function dblDetail(rowIndex, rowDate) {
        var title = "查看";
        var width = '600px';
        var height = '350px';
        var role = $("#role").val();
        var url = "tBLearningTeamController.do?goAddUpdate&load=detail&id=" + rowDate.id;
        createdetailwindow(title, url, width, height);
    }
    
  //刷新
    function customReload(){
        reloadTable();
    }
</script>