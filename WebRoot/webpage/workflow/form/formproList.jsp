<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
  <t:datagrid name="formproList" title="表单管理" actionUrl="processController.do?datagridFPro&formid=${form.id }" idField="id">
   <t:dgCol title="参数id" field="id" width="50" hidden="true"></t:dgCol>
   <t:dgCol title="参数名称" field="formproname" width="70"></t:dgCol>
   <t:dgCol title="参数值" field="formproval" width="70"></t:dgCol>
   <t:dgCol title="参数编码" field="formprocode" width="70"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt url="processController.do?delFPro&id={id}" title="删除"></t:dgDelOpt>
   <t:dgToolBar title="添加表单控件" icon="icon-add" url="processController.do?addorupdateFPro&formid=${form.id }" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑表单控件" icon="icon-edit" url="processController.do?addorupdateFPro&formid=${form.id }" funname="update"></t:dgToolBar>
  </t:datagrid>
</div>
</div>
