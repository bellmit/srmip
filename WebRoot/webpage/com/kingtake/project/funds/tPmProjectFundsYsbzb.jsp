<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input id="projectId" value="${projectId }" type="hidden"/>
<div class="easyui-layout" fit="flase" style="height:450px;width: 930px;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmAbateList" checkbox="true" fitColumns="true"  
  	actionUrl="tPmProjectFundsApprController.do?ysbzGrid" title="减免列表"
  	idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目代码"  field="xmdm"   query="true" queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="序号"  field="xh" query="true" queryMode="single" isLike="true"  width="120"></t:dgCol>
     <t:dgCol title="编制日期"  field="bzrq"  query="true"  queryMode="single"  width="100" align="right"></t:dgCol>
     <t:dgCol title="预算类型"  field="yslx"  query="true" isLike="true" codeDict="1,FUNSTYPE"  queryMode="single"  width="150"></t:dgCol>
     <t:dgToolBar url="tPmProjectFundsApprController.do?goAddOrUpdate&projectId=${projectId}&changeFlag=0&type=${type }"
				title="录入预算" icon="icon-add" funname="addFun" width="800" height="100%"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
//录入预算之前进行判断
function addFun(title,addurl,gname,width,height){
	var rowsData = $('#tPmAbateList').datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择预算编制包');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条预算编制包');
		return;
	}
	add(title,addurl,gname,width,height);
}


$(document).ready(function(){
	//设置datagrid的title
 	var projectName = window.parent.getParameter();
	var title = "";
	title = '选择预算编制包';
  $("#tPmAbateList").datagrid({
 		title:title
  });
});
 
 </script>