<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/project/manage/tPmProjectList.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<input type="hidden" value="${type}" id="type">
		<t:datagrid name="tPmProjectList" checkbox="false" fitColumns="true" title="待审任务节点项目列表"
			actionUrl="tPmProjectController.do?datagridForProject&key=task" idField="id" fit="true" 
			queryMode="group" onDblClick="detailProjectInfo">
			
		<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="项目编号" field="projectNo" query="true" queryMode="single" width="120"></t:dgCol>
		<%-- <t:dgCol title="项目状态" field="projectStatus" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="项目名称" field="projectName" query="true" queryMode="single" width="120"></t:dgCol>
		<%-- <t:dgCol title="项目简介" field="projectAbstract" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="起始日期" field="beginDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
		<t:dgCol title="截止日期" field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
		<%-- <t:dgCol title="分管部门" field="manageDepart" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="负责人" field="projectManager" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="负责人电话" field="managerPhone" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="联系人" field="contact" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="联系人电话" field="contactPhone" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="计划合同标志" field="planContractFlag" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="项目类型" field="projectTypeName" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="外来编号" field="outsideNo" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="经费类型" field="feeType_fundsName" hidden="true" codeDict="1,XMJFLX" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="子类型" field="subType" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="会计编码" field="accountingCode" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="合同计划文号" field="planContractRefNo" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<%-- <t:dgCol title="合同日期" field="contractDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol> --%>
		<%-- <t:dgCol title="合同计划名称" field="planContractName" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="来源单位" field="sourceUnit" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="项目来源" field="projectSource" hidden="true" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="承研部门" field="devDepartName" queryMode="group" width="120"></t:dgCol>
		<t:dgCol title="责任部门" field="dutyDepart" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="经费单列" field="feeSingleColumn" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="项目密级" field="secretDegree" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="是否需要鉴定" field="appraisalFlag" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		<t:dgCol title="总经费" field="allFee" hidden="true" queryMode="group" width="120"></t:dgCol>
		<%-- <t:dgCol title="所属母项目" field="parentPmProject_projectName" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
		  
		<t:dgCol title="计划处审核状态" field="planCheckStatus" replace="已审核_1,未审核_0" query="true" queryMode="single" width="120"></t:dgCol>
		<t:dgCol title="质量办审核状态" field="qualityCheckStatus" replace="已审核_1,未审核_0" query="true" queryMode="single" width="120"></t:dgCol>
		
		<t:dgCol title="操作" field="oper" width="100" extendParams="formatter:formatter,"></t:dgCol>
		
		<t:dgToolBar title="查看项目基本信息" icon="icon-search" url="tPmProjectController.do?goUpdateForResearchGroup" 
			funname="detail" width="100%" height="100%"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	//查看项目信息
	function detailProjectInfo(rowIndex, rowDate){
		if (!rowDate.id) {
            return '';
        }
        var type = $("#type").val();
        if (type == "1") {
            planAudit(rowDate.id, type);
        } else {
            qualityAudit(rowDate.id, type);
        }
	}
	
	function formatter(value, row, index) {
        if (!row.id) {
            return '';
        }
        var href = '';
        var type = $("#type").val();
        if (type == "1") {
            href += "[<a href='#' onclick=planAudit('" + row.id + "','" + type + "')>";
            href += "任务节点审核</a>]";
        } else {
            href += "[<a href='#' onclick=qualityAudit('" + row.id + "','" + type + "')>";
            href += "任务节点审核</a>]";
        }
        return href;
    }
	
	$(document).ready(function(){
	    //给时间控件加上样式
	    $("#tPmProjectListtb").find("input[name='beginDate_begin']")
	    	.attr("class", "Wdate").attr("style","height:24px;width:100px;")
	    	.click(function(){WdatePicker({dateFmt : 'yyyy-MM-dd'});
	    });
	    $("#tPmProjectListtb").find("input[name='beginDate_end']")
	    	.attr("class", "Wdate").attr("style","height:24px;width:100px;")
	    	.click(function(){WdatePicker({dateFmt : 'yyyy-MM-dd'});
	    });
	});

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tPmProjectController.do?upload', "tPmProjectList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tPmProjectController.do?exportXls", "tPmProjectList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tPmProjectController.do?exportXlsByT", "tPmProjectList");
    }

    //计划处审核
    function planAudit(id, type) {
        createdetailwindow("计划处任务节点审核", "tPmTaskController.do?goUpdateTaskForDepartment&project.id=" + id+"&type="+type, "1000px", "300px");
    }

    //质量审核
    function qualityAudit(id, type) {
        createdetailwindow("质量办任务节点审核", "tPmTaskController.do?goUpdateTaskForDepartment&project.id=" + id+"&type="+type, "1000px", "300px");
    }
</script>