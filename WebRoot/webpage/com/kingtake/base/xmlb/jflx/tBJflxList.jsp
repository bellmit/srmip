<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBJflxList" checkbox="true" fitColumns="false" title="项目类别" actionUrl="tBJflxController.do?getJflxTree" idField="id" fit="true" treegrid="true" treeId="id" treeField="jflxmc"  queryMode="group" 
      pagination="false" >
      <t:dgCol title="主键" field="id" treefield="id"  hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="经费类型名称" field="jflxmc" treefield="jflxmc" queryMode="group" width="250" overflowView="true"></t:dgCol>
      <t:dgCol title="排序码" field="code" treefield="code" queryMode="group" width="60" align="center"></t:dgCol>
      <t:dgCol title="操作" field="opt" treefield="opt" width="200"></t:dgCol>
      <t:dgFunOpt title="删除" funname="del(id)" />
      <t:dgToolBar title="录入" icon="icon-add" url="tBJflxController.do?goAdd" funname="add" width="350" height="300"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBJflxController.do?goUpdate" funname="update" width="350" height="300"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBJflxController.do?goUpdate" funname="detail" width="350" height="300"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
//删除
function del(id){
    var url = "tBJflxController.do?doDel&id="+id;
    var name="tBJflxList";
	createdialog('删除确认 ', '确定删除该经费类型及及子项吗 ?', url,name);
}
</script>