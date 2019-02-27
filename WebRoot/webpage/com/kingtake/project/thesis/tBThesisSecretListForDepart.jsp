<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBThesisSecretList" onDblClick="goDetail" checkbox="false" fitColumns="true" title="论文保密申请信息表" actionUrl="tBThesisSecretController.do?datagridForDepart" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="论文题目"  field="thesisTitle"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="作者姓名"  field="firstAuthor" query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="编号"  field="reviewNumber" query="true" isLike="true" width="120"></t:dgCol>
   <t:dgCol title="所属院id"  field="subordinateDeptId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属院系"  field="subordinateDeptName" query="true"  queryMode="single" isLike="true"  width="120"></t:dgCol>
   <t:dgCol title="字数"  field="wordCount"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="具体单位"  field="concreteDeptName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位及地点"  field="undertakeUnitName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="密级"  field="secretDegree" codeDict="0,XMMJ"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟发表刊物名称"  field="publicationName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟发表刊物分区级别"  field="publicationLevel"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="会议名称"  field="meetingName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="征文名称"  field="articleName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="征文单位"  field="articleDepart"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="固定联系电话"  field="fixTelephone"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系电话"  field="mobileTelephone"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="其他作者姓名"  field="otherAuthor"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="memo"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="论文内容要点"  field="thesisContentKey"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="流程处理状态"  field="bpmStatus" hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查状态"  field="checkFlag" queryMode="group" codeDict="1,SCZT" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="220" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt  exp="checkFlag#eq#0" title="删除" url="tBThesisSecretController.do?doDel&id={id}" />
   <t:dgFunOpt  exp="checkFlag#eq#0" funname="checkThesis(id)" title="审查"></t:dgFunOpt>
   <t:dgFunOpt funname="exportTemplate(id)" title="导出审批表"></t:dgFunOpt>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tBThesisSecretController.do?goUpdate" funname="add" width="800" height="650"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBThesisSecretController.do?goUpdate" funname="update" width="800" height="650"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBThesisSecretController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" funname="goDetail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/project/thesis/tBThesisSecretList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//模板导出
function exportTemplate(id){
	$.dialog({
		content: 'url:tBThesisSecretController.do?goOfficePage&id='+id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
 		left : '0%',
 		top : '0%',
		title:"导出",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	
	    }
	});
}

function checkThesis(id){
	$.dialog({
		content: 'url:tBThesisSecretController.do?goCheck&id='+id,
		lock : true,
		width : '800px',
		height : window.top.document.body.offsetHeight-100,
 		top : '0%',
		title:"论文保密审查",
		opacity : 0.3,
		cache:false,
		ok:function(){
			iframe = this.iframe.contentWindow;
			saveObj();
			return false;
		},
	    cancelVal: '关闭',
	    cancel: function(){
	    	
	    }
	});
}

function goDetail(rowIndex){
	var rows = $("#tBThesisSecretList").datagrid("getSelections");
    if(rows.length==0){
    	tip("请至少选择一条数据进行查看！");
        return false;
    }
	var id = rows[0].id;
	$.dialog({
		content: 'url:tBThesisSecretController.do?goUpdate&id='+id+'&load=detail&opt=depart',
		lock : true,
		width : '800px',
		height : window.top.document.body.offsetHeight-100,
 		top : '0%',
		title:"查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	
	    }
	});
}
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBThesisSecretController.do?upload', "tBThesisSecretList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBThesisSecretController.do?exportXls","tBThesisSecretList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBThesisSecretController.do?exportXlsByT","tBThesisSecretList");
}
 </script>