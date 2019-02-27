<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">

</script>
<t:datagrid name="tBCodeDetailList" checkbox="false" title="基础标准代码参数值表" actionUrl="tBCodeTypeController.do?datagridDetailList&codeTypeId=${codeTypeId}" fitColumns="false"  fit="true" treegrid="true" pagination="false" queryMode="group" extendParams="remoteSort:false,">
   <t:dgCol title="主键"  field="id" treefield="id" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属代码集"  field="codeTypeId" treefield="fieldMap.codeTypeId" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="代码"  field="code"  treefield="text"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="名称"  field="name"  treefield="fieldMap.name"  queryMode="group"  width="150" sortable="false" ></t:dgCol>
   <t:dgCol title="所属上级代码"  field="parentCode" treefield="parentText"  hidden="true"  queryMode="group" sortable="false" width="90"></t:dgCol>
   <t:dgCol title="排序码"  field="sortFlag"  treefield="order" hidden="true" queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="有效标记"  field="validFlag" treefield="src"  hidden="true"  queryMode="group" dictionary="validStatus" width="80"></t:dgCol>
   <t:dgCol title="备注"  field="memo"  treefield="fieldMap.memo" hidden="true" queryMode="group" sortable="false" width="180"></t:dgCol>
   <t:dgCol title="操作" field="opt" treefield="opt" width="55"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBCodeTypeController.do?doDelDetail&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBCodeTypeController.do?goAddUpdateDetail&codeTypeId=${codeTypeId}" width="550" height="200" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBCodeTypeController.do?goAddUpdateDetail" funname="update" width="550" height="200" ></t:dgToolBar>
  </t:datagrid>