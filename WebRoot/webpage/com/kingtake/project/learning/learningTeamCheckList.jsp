<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBLearningTeamList" checkbox="true" fitColumns="true" title="学术著作信息表" actionUrl="tBLearningTeamController.do?checkList&businessCode=learning_team" pagination="false"
      idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="团体名称" field="teamName" isLike="true" query="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="团体业务主管单位" field="manageDepart" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="兼任领导职务名称" field="leaderJob" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="批准单位" field="approveUnit" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="社团类别" field="collegeType" codeDict="1,STLB" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="是否在民政部门注册" field="isRegister" replace="是_1,否_0" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="参加团体时间" field="collegeTime" formatter="yyyy-MM-dd" queryMode="group" width="200"></t:dgCol>
      <t:dgCol title="审核状态" field="auditStatus" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程状态" field="bpmStatus" hidden="true" queryMode="group" width="70" ></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="任务id" field="taskId" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="任务名称" field="taskName" hidden="true" width="40"></t:dgCol>
      <t:dgCol title="分配人" field="assigneeName" hidden="true" queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
      <t:dgConfOpt exp="assigneeName#empty#true" url="activitiController.do?claim&taskId={taskId}" message="确定签收?" title="签收"></t:dgConfOpt>
      <t:dgFunOpt exp="assigneeName#empty#false" funname="openhandleMix(taskId,taskName)" title="办理"></t:dgFunOpt>
      <t:dgFunOpt exp="assigneeName#empty#false" funname="selectEntruster(taskId,taskName)" title="委托"></t:dgFunOpt>
    </t:datagrid>
  </div>
  <input type="hidden" id="id">
  <input type="hidden" id="businessName">
</div>
<script src="webpage/com/kingtake/project/learning/tBLearningBookList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tBLearningThesisListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningThesisListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningThesisListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tBLearningThesisListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    function dblDetail(rowIndex, rowDate) {
        var title = "查看";
        var width = '100%';
        var height = '100%';
        var role = $("#role").val();
        var url = "tBLearningTeamController.do?goAddUpdate&load=detail&role=" + role + "&id=" + rowDate.id;
        createdetailwindow(title, url, width, height);
    }
</script>