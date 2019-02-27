<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">

function dblclick(rowIndex,rowData){
    var url = "tPmProjectController.do?goEditForResearchGroup";
    url += '&load=detail&id='+rowData.id;
	createdetailwindow("查看",url,'100%','100%');
}
</script>

<input type="hidden" id="applying" value="${applying }"/>
<input type="hidden" id="executing" value="${executing }"/>
<input type="hidden" id="yes" value="${yes }"/>
<input type="hidden" id="no" value="${no }"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectList" fitColumns="false" title="项目列表" onDblClick="dblclick" pageSize="20" pageList="[20,40,60]"
  	actionUrl="tPmProjectController.do?datagridXmsp&approvalStatus=0" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc" view="scrollview">
   <t:dgCol title="主键"  field="id" queryMode="group" hidden="true"  width="100"></t:dgCol>
   <t:dgCol title="审批表主键" field="sp_id" hidden="true" queryMode="group" width="100"></t:dgCol>
   <t:dgCol title="审批记录主键" field="jl_id" hidden="true"  queryMode="group" width="100"></t:dgCol>
   <t:dgCol title="项目编号"  field="project_no" queryMode="group"  width="150"></t:dgCol>
   <t:dgCol title="项目申请号"  field="cxm" queryMode="single" width="200"></t:dgCol>
   <t:dgCol title="项目名称"  field="project_name" query="true" isLike="true" width="120"></t:dgCol>
   <t:dgCol title="项目类型" field="xmlx" query="true" isLike="true" queryMode="single" codeDict="0,XMLX" width="120"></t:dgCol>
   <t:dgCol title="申报人"  field="project_manager"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="来源单位" field="source_unit" hidden="flase" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="起始日期"  field="begin_date" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>
   <t:dgCol title="截止日期"  field="end_date" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>      
   <t:dgCol title="秘密等级" field="secret_degree" hidden="false" queryMode="group" codeDict="0,XMMJ" width="120"></t:dgCol>
   <t:dgCol title="项目状态"  field="project_status" replace="申请中_01,申报书提交_02,审查报批_03,正在执行_04,暂停执行_05,已验收_06,已结题_07,未立项_99,完成状态_98,立项_97" width="70"></t:dgCol>
   <t:dgCol title="项目来源"  field="xmly" codeDict="0,XMLY" queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="项目简介"  field="project_abstract"  queryMode="group" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt exp="approvalStatus#ne#1&&approvalStatus#ne#2" funname="goSend(sp_id,id,auditStatus)" title="办理" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectController.do?goEditForResearchGroup" 
   	funname="detail" width="100%" height="100%"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
 <script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//给时间控件加上样式
	$("#tPmProjectListtb").find("input[name='beginDate_begin']")
		.attr("class","Wdate").attr("style","height:20px;width:90px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmProjectListtb").find("input[name='beginDate_end']")
		.attr("class","Wdate").attr("style","height:20px;width:90px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});

$(function(){
	var input = $("#tPmProjectListtb input[name='projectType.projectTypeName']");
	input.combotree({
		width : 180,
		url : 'tPmProjectController.do?getProjectType',
		valueField : 'id',
		textField : 'projectTypeName'
	});
});


//处理进账合同审核
function goSend(apprId,id,apprStatus){
	var title = "项目申报审核信息";
	var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
		'&apprId=' + id +
		'&apprType=<%=ApprovalConstant.APPR_TYPE_XM%>';
	var width = 900;
	var height = window.top.document.body.offsetHeight-100;
	var finish = '<%=ApprovalConstant.APPR_TYPE_XM%>';
	
	handlerAppr(title, url, width, height, apprStatus, finish, apprId);
}


</script>