<%@page import="com.kingtake.common.constant.SrmipConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function showFlagFormatter1(value,row,index){
    if(value==""||value==null){
        return "不显示";
    }else if(value=="1"){
       return "显示";
    }
    return "";
}

function showFlagFormatter2(value,row,index){
    if(value=="1"){
        return "管理类";
    }else if(value=="2"){
       return "材料类";
    }else if(value=="3"){
       return "工资类";
    }else if(value=="4"){
       return "利润类";
    }else if(value=="5"){
       return "设计类";
    }else if(value=="6"){
       return "外协类";
    }else if(value=="7"){
       return "试验类";
    }
    return "";
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<%--   <t:datagrid name="tBApprovalBudgetRelaList"  title="类型信息-${projectTypeName}" fitColumns="true" --%>
<%--   	actionUrl="tBApprovalBudgetRelaController.do?datagridTree&projApproval=${projectType}"  --%>
<%--   	idField="id" treegrid="true" pagination="false"> --%>
  <t:datagrid name="tBApprovalBudgetRelaList"  title="类型信息-${fundPropertyName}" fitColumns="true"
  	actionUrl="tBApprovalBudgetRelaController.do?datagridTree&projApproval=${fundProperty}" 
  	idField="id" treegrid="true" pagination="false">
   <t:dgCol title="主键"  field="id"  treefield="id" hidden="true" width="100"></t:dgCol>
   <%-- <t:dgCol title="立项形式"  field="projApproval"  treefield="code" hidden="false"></t:dgCol> --%>
   <!-- 3\4\5表示的是价款计算书，其它为项目类型 -->
   <c:if test="${projectType eq '3' || projectType eq '4' || projectType eq '5'}">
    <t:dgCol title="价款类型名称"  field="budgetNae" treefield="text" width="150"></t:dgCol>
   </c:if>
   <c:if test="${projectType ne '3' && projectType ne '4' && projectType ne '5'}">
    <t:dgCol title="预算类型名称"  field="budgetNae" treefield="text" width="150"></t:dgCol>
   </c:if>
   <t:dgCol title="所属上级"  field="parentId"  treefield="parentId" hidden="true" width="150"></t:dgCol>
   <t:dgCol title="是否比例项"  field="scaleFlag" treefield="fieldMap.scaleFlag" replace="否_0,是_1" width="120"></t:dgCol>
   <t:dgCol title="可执行操作"  field="addChildFlag" treefield="fieldMap.addChildFlag" 
   			replace="不可操作_0, 可添加_1, 可添加、可编辑_2, 可添加、可编辑、可删除_3" width="150"></t:dgCol>
   <c:if test="${projectType ne '3' && projectType ne '4' && projectType ne '5'}">
    <t:dgCol title="是否显示审批"  field="showFlag"  treefield="fieldMap.showFlag" extendParams="formatter:showFlagFormatter1," width="60"></t:dgCol>
   </c:if>
   <c:if test="${projectType eq '3' || projectType eq '4' || projectType eq '5'}">
    <t:dgCol title="计算书类型"  field="showFlag"  treefield="fieldMap.showFlag" extendParams="formatter:showFlagFormatter2," width="60"></t:dgCol>
   </c:if>
   <t:dgCol title="序号"  field="sortCode"  treefield="order" width="60"></t:dgCol>
   <t:dgCol title="备注"  field="memo" treefield="src" hidden="true" width="200"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBApprovalBudgetRelaController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tBApprovalBudgetRelaController.do?goAdd&projApproval=${projectType}"  --%>
<%--    	funname="addFun" width="620" height="550"></t:dgToolBar> --%>
   <t:dgToolBar title="录入" icon="icon-add" url="tBApprovalBudgetRelaController.do?goAddFromFund&fundProperty=${fundProperty}" 
   	funname="addFun" width="620" height="510"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBApprovalBudgetRelaController.do?goUpdate" funname="update" width="620" height="620"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBApprovalBudgetRelaController.do?goUpdateFromFund" funname="update" width="620" height="520"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBApprovalBudgetRelaController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tBApprovalBudgetRelaController.do?goUpdate" funname="detail" width="620" height="620"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tBApprovalBudgetRelaController.do?goUpdateFromFund" funname="detail" width="620" height="620"></t:dgToolBar>
<%--    <t:dgToolBar title="从其它类型复制" icon="icon-put" funname="copyFromOther"></t:dgToolBar> --%>
   <t:dgToolBar title="从其它类型复制" icon="icon-put" funname="copyFromOtherFund"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/base/budget/tBApprovalBudgetRelaList.js"></script>		
 <script type="text/javascript">
 
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBApprovalBudgetRelaController.do?upload', "tBApprovalBudgetRelaList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBApprovalBudgetRelaController.do?exportXls","tBApprovalBudgetRelaList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBApprovalBudgetRelaController.do?exportXlsByT","tBApprovalBudgetRelaList");
}

//给录入方法增加参数
function addFun(title, url, id, width, height) {
	var rowData = $('#'+id).datagrid('getSelected');
	if (rowData) {
		url += '&TBApprovalBudgetRelaEntity.id='+rowData.id;
	}
	add(title,url,id,width,height);
}
/**
 * 选择从哪个项目类型复制相应的预算
 */
function copyFromOther() {
  gridname = "tBApprovalBudgetRelaList";
  var projTypeId = "${projectType}" ;
  if (typeof (windowapi) == 'undefined') {
    $.dialog({
      id : 'projectTypeList',
      content : 'url:tBProjectTypeController.do?goSelect',
      lock : true,
      title : '请选择复制哪个项目类型的预算',
      width : 350,
      height : window.top.document.body.offsetHeight - 100,
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        var rows = iframe.getprojectTypeListSelections('id')[0];
        saveProjectTypeBudget(projTypeId, rows);
        return false;
      },
      cancelVal : '关闭',
      cancel : true
    }).zindex();
  } else {
    W.$.dialog({
      id : 'projectTypeList',
      content : 'url:tBProjectTypeController.do?goSelect',
      lock : true,
      title : '请选择复制哪个项目类型的预算',
      width : 350,
      height : window.top.document.body.offsetHeight - 100,
      parent : windowapi,
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        var rows = iframe.getprojectTypeListSelections('id')[0];
        saveProjectTypeBudget(projTypeId, rows);
        return false;
      },
      cancelVal : '关闭',
      cancel : true
    }).zindex();
  }
}
/**
 * 选择从哪个经费类型复制相应的预算
 */
function copyFromOtherFund() {
  gridname = "tBApprovalBudgetRelaList";
  var fundPropertyId = "${fundProperty}" ;
  if (typeof (windowapi) == 'undefined') {
    $.dialog({
      id : 'fundPropertyList',
      content : 'url:tBFundsPropertyController.do?goSelect',
      lock : true,
      title : '请选择复制哪个项目类型的预算',
      width : 350,
      height : window.top.document.body.offsetHeight - 100,
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        var rows = iframe.getfundPropertyListSelections('id')[0];
        saveFundPropertyBudget(fundPropertyId, rows);
        return false;
      },
      cancelVal : '关闭',
      cancel : true
    }).zindex();
  } else {
    W.$.dialog({
      id : 'fundPropertyList',
      content : 'url:tBFundsPropertyController.do?goSelect',
      lock : true,
      title : '请选择复制哪个项目类型的预算',
      width : 350,
      height : window.top.document.body.offsetHeight - 100,
      parent : windowapi,
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        var rows = iframe.getfundPropertyListSelections('id')[0];
        saveFundPropertyBudget(fundPropertyId, rows);
        return false;
      },
      cancelVal : '关闭',
      cancel : true
    }).zindex();
  }
}
//保存复制的项目预算数据
function saveProjectTypeBudget(id, rows) {
  if (typeof (windowapi) == 'undefined') {
    $.dialog.confirm('一旦确定将会把该类型现有的预算数据全部删除，然后再从相应的项目类型复制过来，该操作不可逆，确定复制？', function() {
      saveApprovalBudget(id,rows);
    }, function() {
    }).zindex();
} else {
    W.$.dialog.confirm('一旦确定将会把该类型现有的预算数据全部删除，然后再从相应的项目类型复制过来，该操作不可逆，确定复制？', function() {
      saveApprovalBudget(id,rows);
    }, function() {
    }, windowapi).zindex();
}
}
//保存复制的经费预算数据
function saveFundPropertyBudget(id, rows) {
  if (typeof (windowapi) == 'undefined') {
    $.dialog.confirm('一旦确定将会把该类型现有的预算数据全部删除，然后再从相应的经费类型复制过来，该操作不可逆，确定复制？', function() {
      saveFundBudget(id,rows);
    }, function() {
    }).zindex();
} else {
    W.$.dialog.confirm('一旦确定将会把该类型现有的预算数据全部删除，然后再从相应的经费类型复制过来，该操作不可逆，确定复制？', function() {
      saveFundBudget(id,rows);
    }, function() {
    }, windowapi).zindex();
}
}

function saveApprovalBudget(id,rows){
  var catalogListStr = JSON.stringify(rows);
  //alert(id + "+"+ rows);
  $.ajax({
      url : 'tBApprovalBudgetRelaController.do?saveApprovalBudgetFromCopy',
      data : {
          'id' : id,
          'fromProjectTypeId' : rows
      },
      type : 'POST',
      dataType : 'json',
      success : function(data) {
          if (data.success) {
              reloadTable();
              iframe.close();
          }
          tip(data.msg);
      }
  });
}
function saveFundBudget(id,rows){
	  var catalogListStr = JSON.stringify(rows);
	  //alert(id + "+"+ rows);
	  $.ajax({
	      url : 'tBApprovalBudgetRelaController.do?saveApprovalBudgetFromCopyFund',
	      data : {
	          'id' : id,
	          'fromFundPropertyId' : rows
	      },
	      type : 'POST',
	      dataType : 'json',
	      success : function(data) {
	          if (data.success) {
	              reloadTable();
	              iframe.close();
	          }
	          tip(data.msg);
	      }
	  });
	}
  </script>