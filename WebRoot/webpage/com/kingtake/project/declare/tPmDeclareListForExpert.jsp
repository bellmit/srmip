<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <input id="projectId" name="projectId" type="hidden" value="${projectId}">
    <t:datagrid name="tPmDeclareList" checkbox="true" fitColumns="true" 
      actionUrl="tPmDeclareController.do?datagrid&projectId=${projectId }" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="所属项目" field="projectName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="研究时限开始时间" field="beginDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="研究时限结束时间" field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="项目来源" field="projectSrc" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="主要研究内容" field="researchContent" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="研究进度及成果形式" field="scheduleAndAchieve" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="流程流转状态" field="bpmStatus" queryMode="group" dictionary="bpm_status" width="120"></t:dgCol>
      <t:dgCol title="流程实例id" field="processInstId" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search"
        url="tPmDeclareController.do?goUpdateForResearchGroup&projectId=${projectId }" funname="detail"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/declare/tPmDeclareList.js"></script>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
              	//设置datagrid的title
             	var projectName = window.parent.getParameter();
             	$("#tPmDeclareList").datagrid({
             	    title:projectName+'-项目申报'
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
    function startProcess(id, name) {
        //业务表名
        var tableName = 't_b_pm_declare';
        var businessName = name;
        //流程对应表单URL
        var formUrl = 'tPmDeclareController.do?goUpdateForResearchGroupAudit';
        dialogConfirm('activitiController.do?startProcess&id=' + id + '&tableName=' + tableName + '&formUrl=' + formUrl
                + '&businessName=' + businessName + "&businessCode=declare", '确定提交流程吗？', 'tPmDeclareList');
    }

    //查看流程
    function viewHistory(processInstanceId) {
        var url = "";
        var title = "流程历史";
        url = "activitiController.do?viewProcessInstanceHistory&processInstanceId=" + processInstanceId + "&isIframe"
        addTab(title, url);
    }
</script>