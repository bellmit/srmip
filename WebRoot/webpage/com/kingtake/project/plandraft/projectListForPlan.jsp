<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectList" checkbox="true" fitColumns="true" title="项目列表" actionUrl="tPmPlanDraftController.do?datagridForProject" idField="id" fit="true"
      queryMode="group" pagination="false">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目编号" field="projectNo" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="起始日期" field="beginDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="截止日期" field="endDate" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="负责人" field="projectManager" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目类型" field="projectType_projectTypeName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="会计编码" field="accountingCode" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="承研部门" field="devDepart_departname" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="责任部门" field="dutyDepart_departname" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="总经费" field="allFee" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目状态" field="projectStatus" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划状态" field="planFlag" query="true" replace="未生成_0,已生成_1" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="申报类型" field="delareType" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="申报信息主键" field="delareId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="申报信息主键" field="delareId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectController.do?goUpdateForResearchGroup" funname="detail" width="100%" height="100%"></t:dgToolBar>
      <t:dgToolBar title="生成计划草案" icon="icon-edit" funname="createPlanDraft" ></t:dgToolBar>
    </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmPlanDraftListtb").find("input[name='planTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='planTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmPlanDraftListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmPlanDraftController.do?upload', "tPmPlanDraftList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmPlanDraftController.do?exportXls","tPmPlanDraftList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmPlanDraftController.do?exportXlsByT","tPmPlanDraftList");
}

//生成计划草案
function createPlanDraft(){
    var rows = $("#tPmProjectList").datagrid("getChecked");
    if(rows.length==0){
        tip("请先勾选需要生成计划草案的项目!");
        return false;
    }
    for(var i=0;i<rows.length;i++){
        if(rows[i].planFlag=='1'){
            tip("请不要勾选已生成计划草案的项目！");
            return false;
        }
    }
    var title = "生成计划草案";
    var url = "tPmPlanDraftController.do?goUpdate";
	var width = window.top.document.body.offsetWidth;
	var height =window.top.document.body.offsetHeight-100;
    if (typeof (windowapi) == 'undefined') {
                dialog = $.dialog({
                    id : 'planDialog',
                    content : 'url:' + url,
                    data : {
                        'list' : rows
                    },
                    lock : true,
                    width : width,
                    height : height,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        saveObj();
                        return false;
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            } else {
                dialog = W.$.dialog({
                    id : 'planDialog',
                    content : 'url:' + url,
                    data : {
                        'list' : rows
                    },
                    lock : true,
                    width : width,
                    height : height,
                    parent : windowapi,
                    title : title,
                    opacity : 0.3,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        saveObj();
                        return false;
                    },
                    cancelVal : '关闭',
                    cancel : true
                }).zindex();
            }
}
 </script>