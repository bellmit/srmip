<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tZSqrxxList" checkbox="true" fitColumns="false" title="申请人信息" actionUrl="tZSqrxxController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="姓名"  field="xm"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="dh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="居民身份证件号码"  field="idno"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电子邮箱"  field="dzyx"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="国籍"  field="gj"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="居所地"  field="jsd"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="邮政编码"  field="yzbm"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="详细地址"  field="xxdz"    queryMode="group"  width="120" overflowView="true"></t:dgCol>
   <t:dgCol title="所属部门"  field="ssbm"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tZSqrxxController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tZSqrxxController.do?goUpdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tZSqrxxController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tZSqrxxController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tZSqrxxController.do?goUpdate" funname="detail"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/zscq/sqrxx/tZSqrxxList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tZSqrxxController.do?upload', "tZSqrxxList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tZSqrxxController.do?exportXls","tZSqrxxList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tZSqrxxController.do?exportXlsByT","tZSqrxxList");
}
 </script>