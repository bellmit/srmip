<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:base type="jquery,easyui,tools"></t:base>
  <t:datagrid name="${type.typecode}List" title="数据表列表" actionUrl="dbController.do?tableGrid&typeid=${type.id }" idField="id">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="表名称" field="tableName" width="15"></t:dgCol>
   <t:dgCol title="实体KEY" field="entityKey" width="15"></t:dgCol>
   <t:dgCol title="实体名称" field="entityName" width="50"></t:dgCol>
   <t:dgCol title="描述" field="tableTitle"></t:dgCol>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dbController.do?aouTable" funname="update"></t:dgToolBar>
   <%-- <t:dgToolBar title="加载" icon="icon-edit" url="dbController.do?reloadData" funname="doSubmit"></t:dgToolBar> --%>
 </t:datagrid>
