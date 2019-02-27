<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAppraisalMemberList" checkbox="true" fitColumns="false" title="推荐鉴定委员会成员" actionUrl="tBAppraisalMemberController.do?datagrid&applyId=${applyId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="memberName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="职称"  field="memberPosition" codeDict="1,ZHCH"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专业"  field="memberProfession"  codeDict="1,MAJOR"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="工作单位"  field="workUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="memo"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
<%--    <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
<%--    <t:dgDelOpt title="删除" url="tBAppraisalMemberController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tBAppraisalMemberController.do?goAdd" funname="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="测试" icon="icon-add"  funname="getUnchecked"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tBAppraisalMemberController.do?goUpdate" funname="update"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBAppraisalMemberController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="查看" icon="icon-search" url="tBAppraisalMemberController.do?goUpdate" funname="detail"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
<!--  <script src = "webpage/com/kingtake/project/appraisalmeeting/tBAppraisalMemberList.js"></script>		 -->
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBAppraisalMemberListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalMemberListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalMemberListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAppraisalMemberListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 function getMemberChecked(){
	 return $('#tBAppraisalMemberList').datagrid('getChecked');
 }
 
 function getMemberRows(){
	 return $('#tBAppraisalMemberList').datagrid('getRows');
 }
 Array.prototype.del=function(index){
	 if(isNaN(index)||index>=this.length){
	 	return false;
	 }
	 for(var i=0,n=0;i<this.length;i++){
	   if(this[i]!=this[index]){
	 	   this[n++]=this[i];
	   }
	 }
	 this.length-=1;
};
 function getUnchecked(){
	 var rows = $('#tBAppraisalMemberList').datagrid('getRows');
	 var checkedRows = $('#tBAppraisalMemberList').datagrid('getChecked');
	 var checkIndex = new Array(checkedRows.length);
	 for(var i=0;i<checkedRows.length;i++){
		 checkIndex[i] = $('#tBAppraisalMemberList').datagrid('getRowIndex',checkedRows[i]);
	 }
	 for(var j=0;j<checkIndex.length;j++){
	 	rows.del(checkIndex[j]);
	 }
	 return rows;
// 		for(var k=0;k<rows.length;k++){
// 			console.log(rows[k].memberName);
// 		}
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBAppraisalMemberController.do?upload', "tBAppraisalMemberList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBAppraisalMemberController.do?exportXls","tBAppraisalMemberList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBAppraisalMemberController.do?exportXlsByT","tBAppraisalMemberList");
}
 </script>