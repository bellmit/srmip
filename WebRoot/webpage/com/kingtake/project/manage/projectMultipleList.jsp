<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input id="excludeIds" type="hidden" value="${excludeIds}">
  <t:datagrid name="projectList" checkbox="true" fitColumns="false" title="项目基本信息表" actionUrl="tPmProjectController.do?projectList&excludeIds=${excludeIds}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目编号"  field="projectNo"    query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
   <t:dgCol title="项目名称"  field="projectName"  query="true"  queryMode="single" isLike="true"  width="120"></t:dgCol>
   <t:dgCol title="项目状态"  field="projectStatus"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
