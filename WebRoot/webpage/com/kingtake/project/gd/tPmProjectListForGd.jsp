<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/project/manage/tPmProjectList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmProjectList" checkbox="true" fitColumns="false" title="项目基本信息表" actionUrl="tPmProjectController.do?datagridGd" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目编号" frozenColumn="true" field="projectNo"  width="120"></t:dgCol>
      <t:dgCol title="项目名称" frozenColumn="true" field="projectName"  width="250" overflowView="true"></t:dgCol>
      <t:dgCol title="项目类型" field="projectType_projectTypeName"  width="120" overflowView="true"></t:dgCol>
      <t:dgCol title="经费类型" field="feeType_fundsName" hidden="false" width="120"></t:dgCol>
      <t:dgCol title="外来编号" field="outsideNo" hidden="false" width="120"></t:dgCol>
      <t:dgCol title="秘密等级" field="secretDegree" hidden="false" codeDict="0,XMMJ" width="120"></t:dgCol>
      <t:dgCol title="责任部门" field="dutyDepart_departname"  width="120"></t:dgCol>
      <t:dgCol title="承研部门" field="devDepart_departname"  width="120"></t:dgCol>
      <t:dgCol title="负责人" field="projectManager"  width="120"></t:dgCol>
      <t:dgCol title="项目状态" field="projectStatus" query="true" hidden="flase" replace="申请中_01,申报书提交_02,审查报批_03,正在执行_04,暂停执行_05,已验收_06,已结题_07,未立项_99,完成状态_98" width="120"></t:dgCol>
      <t:dgCol title="母项目编码" field="parentPmProject_projectNo" hidden="false" width="120"></t:dgCol>
      <t:dgCol title="总经费" field="allFee" hidden="false"  width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
      <t:dgCol title="起始日期" field="beginDate" hidden="false" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>
      <t:dgCol title="截止日期" field="endDate" hidden="false" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>
      <t:dgCol title="会计编码" field="accountingCode" hidden="flase" width="120" ></t:dgCol>
      <t:dgCol title="来源单位" field="sourceUnit" hidden="flase"  width="120"></t:dgCol>
	  <t:dgCol title="归档状态" field="gdStatus" replace="未归档_0,已归档_1" width="60" ></t:dgCol>
      <t:dgCol title="确认状态" field="approvalStatus" hidden="true" width="60"></t:dgCol>
      <t:dgCol title="项目简介" field="projectAbstract" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="分管部门" field="manageDepart" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="联系人" field="contact" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="联系人电话" field="contactPhone" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="计划合同标志" field="planContractFlag" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="子类型" field="subType" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="合同计划文号" field="planContractRefNo" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="合同日期" field="contractDate" formatter="yyyy-MM-dd" hidden="true" width="90" align="center"></t:dgCol>
      <t:dgCol title="合同计划名称" field="planContractName" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="项目来源" field="projectSource" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="负责人电话" field="managerPhone" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="经费单列" field="feeSingleColumn" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="是否需要鉴定" field="appraisalFlag" hidden="true" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="查看科技档案" funname="viewKjda(id)"></t:dgFunOpt>
      <t:dgToolBar title="确认归档" icon="icon-remove" url="tPmKjdaController.do?qrgd" 
				funname="gdALLSelect"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tPmProjectListtb").find("input[name='beginDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmProjectListtb").find("input[name='beginDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmProjectListtb").find("input[name='endDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmProjectListtb").find("input[name='endDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmProjectListtb").find("input[name='contractDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmProjectListtb").find("input[name='contractDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //查看科技档案
    function viewKjda(id, index) {
        var rows = $("#tPmProjectList").datagrid("getRows");
        var projectName = rows[index]['projectName'];
        addTab(projectName, "tPmKjdaController.do?tPmKjda&projectId=" + id, "default");
    }
</script>