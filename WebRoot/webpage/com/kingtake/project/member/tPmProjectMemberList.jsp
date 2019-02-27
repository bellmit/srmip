<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%request.setAttribute("no", SrmipConstants.NO);%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
	  <div id="tempSearchColums" style="display: none">
	      <div name="searchColums">
	      	<span style="display: -moz-inline-box; display: inline-block;">
	           	<span style="vertical-align: middle; display: -moz-inline-box; 
	           		display: inline-block; width: 120px; text-align: right; 
	           		text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; 
	           		white-space: nowrap;" title="学位">学位：</span>
	           	<t:codeTypeSelect name="degree" type="select" codeType="0" code="XWLB" 
	           		labelText="全选" id=""></t:codeTypeSelect>
	      	</span>
	      </div>
	  </div>
	 
	<input id="projectId" value="${projectId }" type="hidden"/> 
	<t:datagrid name="tPmProjectMemberList" checkbox="false" fitColumns="true" 
		title="项目组成员" actionUrl="tPmProjectMemberController.do?datagrid&project.id=${projectId}" 
		onDblClick="dblClickDetail" idField="id" fit="true" queryMode="group">
	   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="姓名"  field="name" queryMode="single" isLike="true" query="true"  width="120"></t:dgCol>
	   <t:dgCol title="性别"  field="sex" codeDict="0,SEX"   queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="出生年月"  field="birthday" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
	   <t:dgCol title="学位"  field="degree" codeDict="0,XWLB"   queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="职称职务"  field="position"  codeDict="0,PROFESSIONAL"  queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="任务分工"  field="taskDivide"    queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="所属单位"  field="superUnitName"    queryMode="group"  width="120"></t:dgCol>
	   <t:dgCol title="是否负责人"  field="projectManager" codeDict="0,SFBZ"   queryMode="group"  width="120"></t:dgCol>
   
	   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
	   <t:dgDelOpt exp="projectManager#eq\#${no}" title="删除" url="tPmProjectMemberController.do?doDel&id={id}" />
	   <t:dgToolBar title="录入" icon="icon-add" width="450" height="370" url="tPmProjectMemberController.do?goAdd&projectId=${projectId}" funname="add"></t:dgToolBar>
	   <t:dgToolBar title="编辑" icon="icon-edit" width="450" height="370"  url="tPmProjectMemberController.do?goUpdate&projectId=${projectId}" funname="update"></t:dgToolBar>
		<%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmProjectMemberController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
	   <t:dgToolBar title="查看" width="450" height="370" icon="icon-search" url="tPmProjectMemberController.do?goUpdate" funname="detail"></t:dgToolBar>
	   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
	   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
	   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  	</t:datagrid>
  </div>
</div>
 
<script src = "webpage/com/kingtake/project/member/tPmProjectMemberList.js"></script>		
<script type="text/javascript">
//双击查看方法
function dblClickDetail(rowIndex, rowData){
	var title = "查看";
	var url = "tPmProjectMemberController.do?goUpdate&load=detail&id=" + rowData.id;
	createdetailwindow(title,url,null,null);
}

$(document).ready(function(){
	$("#tPmProjectMemberListtb").find("div[name='searchColums']").append(
			$("#tempSearchColums div[name='searchColums']").html());

	//设置datagrid的title
  	var projectName = window.parent.getParameter();
  	$("#tPmProjectMemberList").datagrid({
  	    title:projectName+'-项目组成员'
  	});
});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmProjectMemberController.do?upload', "tPmProjectMemberList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmProjectMemberController.do?exportXls&project.id="+$("#projectId").val()
			,"tPmProjectMemberList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmProjectMemberController.do?exportXlsByT","tPmProjectMemberList");
}
 </script>