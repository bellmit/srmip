<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectMemberList" checkbox="true" fitColumns="false" title="项目组成员" actionUrl="tPmProjectMemberController.do?datagrid&project.id=${projectId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属项目"  field="projectName" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="name"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="性别"  field="sex" codeDict="0,SEX"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="出生年月"  field="birthday" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="学位"  field="degree" codeDict="0,XWLB"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="职称职务"  field="position"  codeDict="0,PROFESSIONAL"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="任务分工"  field="taskDivide"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属单位"  field="superUnitName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否负责人"  field="projectManager" codeDict="0,SFBZ"   queryMode="group"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/member/tPmProjectMemberList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tPmProjectMemberListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectMemberListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectMemberListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectMemberListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectMemberListtb").find("input[name='birthday_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tPmProjectMemberListtb").find("input[name='birthday_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmProjectMemberController.do?upload', "tPmProjectMemberList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmProjectMemberController.do?exportXls","tPmProjectMemberList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmProjectMemberController.do?exportXlsByT","tPmProjectMemberList");
}
 </script>