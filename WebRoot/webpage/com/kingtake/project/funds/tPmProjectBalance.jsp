<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
//是否已做预算状态格式化
function ysStatusFormat(value,row,index){
	  if(value=="0"){
		  return "否";
	  }else if(value=="1"){
		  return "是";
	  }
}
</script>

<input id="projectId" value="${projectId}" type="hidden"/>
<div class="easyui-layout" fit="true" style="height:450px;width: 930px;">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectBalanceList" checkbox="true" fitColumns="true"  
  	actionUrl="tPmProjectFundsApprController.do?balanceGrid&projectId=${projectId}" title="项目余额列表"
  	idField="id" fit="true" queryMode="group" onDblClick="dblDetail">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目主键"  field="tpid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="项目代码"  field="projectNo"   query="true" queryMode="single"  width="120"></t:dgCol>
     <t:dgCol title="明细代码"  field="balanceNo" query="true" queryMode="single" isLike="true"  width="120"></t:dgCol>
     <t:dgCol title="明细名称"  field="balanceName"  query="true"  queryMode="single" isLike="true"  width="100" align="right"></t:dgCol>
     <t:dgCol title="历次金额"  field="lcAmount" queryMode="single"  width="100" align="right"></t:dgCol>
     <t:dgCol title="预算金额"  field="ysAmount" queryMode="single"  width="100" align="right"></t:dgCol>
     <t:dgCol title="执行金额"  field="zxAmount" queryMode="single"  width="100" align="right"></t:dgCol>
     <t:dgCol title="借款金额"  field="jkAmount" queryMode="single"  width="100" align="right"></t:dgCol>
     <t:dgCol title="预算余额"  field="balanceAmount" queryMode="single"  width="100" align="right"></t:dgCol>    
     <t:dgCol title="是否已做预算" field="ysStatus" extendParams="formatter:ysStatusFormat," queryMode="single" width="80"></t:dgCol> 
     <t:dgToolBar url="tPmProjectFundsApprController.do?goAddOrUpdate&projectId=${projectId}&changeFlag=0&type=${type }"
				title="录入预算" icon="icon-add" funname="addFun" width="800" height="100%"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
<script type="text/javascript" src="webpage/common/util.js"></script>		
<script type="text/javascript">
//录入预算之前进行判断
function addFun(title,addurl,gname,width,height){
	var rowsData = $('#tPmProjectBalanceList').datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择项目余额');
		return;
	}
	if (rowsData.length>1) {
		tip('请选择一条项目余额');
		return;
	}
	addurl += '&balanceId='+rowsData[0].id;
	add(title,addurl,gname,width,height);
}


$(document).ready(function(){
	//设置datagrid的title
//  	var projectName = window.parent.getParameter();
// 	var title = "";
// 	title = '选择预算编制包';
//   $("#tPmAbateList").datagrid({
//  		title:title
//   });
});
 
 </script>