<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="fundAndQuota">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBFundsPropertyList" checkbox="false" fitColumns="true" 
  	title="经费类型表" actionUrl="tBFundsPropertyController.do?datagrid" 
  	idField="id" fit="true" queryMode="group" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="性质编码"  field="fundsCode"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="经费类型"  field="fundsName"    queryMode="group"  width="150"></t:dgCol>
   <!-- 2017年1月16日添加绩效与机动费信息 -->
   <t:dgCol title="绩效费"  field="proforFlag"    queryMode="group"  width="60" replace="无_0,有_1"></t:dgCol>
   <t:dgCol title="绩效费默认比例"  field="proforRatio"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="机动费"  field="motoFlag"    queryMode="group"  width="60" replace="无_0,有_1"></t:dgCol>
   <t:dgCol title="机动费默认比例"  field="motoRatio"    queryMode="group"  width="100"></t:dgCol> 
   <t:dgCol title="备注"  field="memo"    queryMode="group"  width="120"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgOpenOpt url="tBApprovalBudgetRelaController.do?tBApprovalBudgetRelaFromFund&fundProperty={id}" title="预算类别"></t:dgOpenOpt>
   <t:dgDelOpt title="删除" url="tBFundsPropertyController.do?doDel&id={id}" />
   <t:dgFunOpt funname="showQuotaInfoPanel(id)" title="设置经费限额"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="tBFundsPropertyController.do?goAdd" 
  	funname="add" width="500" height="280"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBFundsPropertyController.do?goUpdate" 
   	funname="update" width="500" height="280"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBFundsPropertyController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBFundsPropertyController.do?goUpdate" 
   	funname="detail" width="500" height="280"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
  </div>
  <!--车辆其他信息（使用、维修、费用、违章、事故）  -->
  <div data-options=
	  	"
	  		region:'east',
	  		title:'mytitle',
			collapsed:true,
			split:true,
			border:false,
			onExpand : function(){
				li_east = 1;
			},
			onCollapse : function() {
			    li_east = 0;
			}
		"
	     style="width: 600px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="quotaInfo"></div>
  </div>
 <script src = "webpage/com/kingtake/base/fund/tBFundsPropertyList.js"></script>		
 <script type="text/javascript">
 
 /**
  * 显示左边面板的方法
  */
 function showQuotaInfoPanel(id){
	 var title = "经费限额设置";
	 var url = "tBFundsBudgetRelaController.do?tBFundsBudgetRelaTab&fundId="+id;
     if(li_east == 0){
         $('#fundAndQuota').layout('expand','east');
     }
     $('#fundAndQuota').layout('panel','east').panel('setTitle', title);
     $('#quotaInfo').panel("refresh", url);
 }
 
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBFundsPropertyController.do?upload', "tBFundsPropertyList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBFundsPropertyController.do?exportXls","tBFundsPropertyList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBFundsPropertyController.do?exportXlsByT","tBFundsPropertyList");
}
 </script>