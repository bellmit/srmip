<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>计划草案</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
        
    });

    function setValue() {
        var rows = $("#tPmProjectList").datagrid("getRows");
        var rowStr = JSON.stringify(rows);
        $("#planDetailStr").val(rowStr);
    }
    
    //移除
    function removeRow(){
        var checked = $("#tPmProjectList").datagrid("getChecked");
        for(var i=checked.length-1;i>=0;i--){
            var index = $("#tPmProjectList").datagrid("getRowIndex",checked[i]);
            $("#tPmProjectList").datagrid("deleteRow",index);
        }
    }
    
    function detailDeclare(){
    	var rowsData = $('#tPmProjectList').datagrid('getSelections');
    	if (!rowsData || rowsData.length == 0) {
    		tip('请选择一行记录');
    		return;
    	}
    	if (rowsData.length > 1) {
    		tip('请选择一条记录再查看');
    		return;
    	}
    	var url = "tPmPlanDraftController.do?goDeclareInfo";
        url += '&load=detail&declareType='+rowsData[0].declareType+'&id='+rowsData[0].declareId+"&projectId="+rowsData[0].id;
    	createdetailwindow("查看申报书/申报需求",url,"100%","100%");
    }
</script>
</head>
<body>
  <div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north'" style="height: 220px;">
      <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPlanDraftController.do?doUpdate" tiptype="1" beforeSubmit="setValue">
        <input id="id" name="id" type="hidden" value="${tPmPlanDraftPage.id }">
        <input id="createBy" name="createBy" type="hidden" value="${tPmPlanDraftPage.createBy }">
        <input id="createName" name="createName" type="hidden" value="${tPmPlanDraftPage.createName }">
        <input id="createDate" name="createDate" type="hidden" value="${tPmPlanDraftPage.createDate }">
        <input id="updateBy" name="updateBy" type="hidden" value="${tPmPlanDraftPage.updateBy }">
        <input id="updateName" name="updateName" type="hidden" value="${tPmPlanDraftPage.updateName }">
        <input id="updateDate" name="updateDate" type="hidden" value="${tPmPlanDraftPage.updateDate }">
        <textarea id="planDetailStr" name="planDetailStr" cols="1" rows="1" style="display: none;">${planDetailStr }</textarea>
        <table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
          <tr>
            <td align="right" style="width: 100px;"><label class="Validform_label">
                计划名称: <font color="red">*</font>
              </label></td>
            <td class="value" style="width: 260px;"><input id="planName" name="planName" type="text" style="width: 150px" datatype="*1-50" class="inputxt" value='${tPmPlanDraftPage.planName}'>
              <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">计划名称</label></td>
            <td align="right" style="width: 100px;"><label class="Validform_label"> 编制时间: </label></td>
            <td class="value" style="width: 260px;"><input id="planTime" name="planTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
                value='<fmt:formatDate value='${tPmPlanDraftPage.planTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
                style="display: none;">编制时间</label></td>
          </tr>

          <tr>
            <td align="right"><label class="Validform_label"> 备注: </label></td>
            <td class="value" colspan="3"><textarea id="remark" style="width: 550px;" class="inputxt" rows="6" name="remark">${tPmPlanDraftPage.remark}</textarea> <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">备注</label></td>
          </tr>
        </table>
      </t:formvalid>
    </div>
    <div data-options="region:'center'">
      <t:datagrid name="tPmProjectList" checkbox="true" fitColumns="true" title="项目列表" actionUrl="tPmPlanDraftController.do?datagridForProject&planId=${tPmPlanDraftPage.id}" idField="id" fit="true" queryMode="group"
        pagination="false">
        <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
        <t:dgCol title="项目编号" field="projectNo" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="项目名称" field="projectName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
		
		<t:dgCol title="责任部门" field="dutyDepart_departname" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
   		<t:dgCol title="承研部门"  field="devDepart_departname" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>        
   		<t:dgCol title="负责人"  field="projectManager"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
   		<t:dgCol title="总经费" field="allFee" queryMode="group" width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
   		<t:dgCol title="来源单位" field="sourceUnit" hidden="flase" isLike="true" queryMode="single"  width="120"></t:dgCol>
   		<t:dgCol title="项目类型id"  field="projectType_id" hidden="true"  width="100"></t:dgCol>
   		<t:dgCol title="项目类型"  field="projectType_projectTypeName" queryMode="single"  width="100"></t:dgCol>
   		<t:dgCol title="经费类型" field="feeType_fundsName" hidden="false" queryMode="group" width="120"></t:dgCol>
   		<t:dgCol title="起始日期"  field="beginDate" query="true" queryMode="group" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>
   		<t:dgCol title="截止日期"  field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="100" align="center"></t:dgCol>      
   		<t:dgCol title="外来编号" field="outsideNo" hidden="false" queryMode="single" width="120"></t:dgCol>
   		<t:dgCol title="秘密等级" field="secretDegree" hidden="false" queryMode="group" codeDict="0,XMMJ" width="120"></t:dgCol>
   		<t:dgCol title="母项目编码" field="parentPmProject_projectNo" hidden="false" queryMode="group" width="120"></t:dgCol>
   		<t:dgCol title="会计编码"  field="accountingCode"    queryMode="group"  width="100"></t:dgCol>
   		<t:dgCol title="项目来源"  field="projectSource"    queryMode="group"  width="100"></t:dgCol>      
   		<t:dgCol title="确认状态"  field="approvalStatus" codeDict="1,LXZT" width="70"></t:dgCol>
   
<%--         <t:dgCol title="起始日期" field="beginDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol> --%>
<%--         <t:dgCol title="截止日期" field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol> --%>
<%--         <t:dgCol title="负责人" field="projectManager" queryMode="group" width="120"></t:dgCol> --%>
<%--         <t:dgCol title="项目类型" field="projectType_projectTypeName" queryMode="group" width="120"></t:dgCol> --%>
<%--         <t:dgCol title="会计编码" field="accountingCode" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
<%--         <t:dgCol title="承研部门" field="devDepart_departname" queryMode="group" width="120"></t:dgCol> --%>
<%--         <t:dgCol title="责任部门" field="dutyDepart_departname" queryMode="group" width="120"></t:dgCol> --%>
<%--         <t:dgCol title="总经费" field="allFee" queryMode="group" width="80"></t:dgCol> --%>
        <t:dgCol title="项目状态" field="projectStatus" hidden="true" queryMode="group" width="120"></t:dgCol>
        <%-- <t:dgCol title="计划状态" field="planFlag" query="true" replace="未生成_0,已生成_1" queryMode="single" width="120"></t:dgCol> --%>
        <t:dgCol title="申报类型" field="declareType" hidden="true" queryMode="single" width="120"></t:dgCol>
        <t:dgCol title="申报信息主键" field="declareId" hidden="true"  queryMode="single" width="120"></t:dgCol>
        <t:dgToolBar title="查看项目基本信息" icon="icon-search" url="tPmProjectController.do?goUpdateForResearchGroup" funname="detail" width="100%" height="100%"></t:dgToolBar>
        <t:dgToolBar title="查看项目申报信息" icon="icon-search"  funname="detailDeclare" width="100%" height="100%"></t:dgToolBar>
        <t:dgToolBar title="移除" icon="icon-remove" funname="removeRow"></t:dgToolBar>
        <%-- <t:dgToolBar title="生成计划草案" icon="icon-edit" funname="createPlanDraft"></t:dgToolBar> --%>
      </t:datagrid>
    </div>
  </div>
  </div>
  <script type="text/javascript" src="webpage/common/util.js"></script>
</body>