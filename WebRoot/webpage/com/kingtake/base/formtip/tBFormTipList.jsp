<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBFormTipList" checkbox="true" fitColumns="false" title="表单填写说明" actionUrl="tBFormTipController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="业务编码"  field="businessCode"  query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="业务名称"  field="businessName" query="true" isLike="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBFormTipController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBFormTipController.do?goAddUpdate" funname="add" width="860" height="480"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBFormTipController.do?goAddUpdate" funname="update" width="860" height="480"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBFormTipController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBFormTipController.do?goAddUpdate" funname="detail" width="860" height="480"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
 </script>