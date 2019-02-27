<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="zlzzList" checkbox="true" fitColumns="true" title="专利终止" actionUrl="tZZlzzController.do?datagrid&zlsqId=${zlsqId }" idField="id" fit="true" queryMode="group" onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发文日"  field="fwr"  formatter="yyyy-MM-dd" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="终止状态"  field="zzzt" replace="已恢复_0,已终止_1" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="录入时间"  field="createDate" formatter="yyyy-MM-dd" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <c:if test="${role eq 'depart' }">
   <t:dgDelOpt  title="删除" url="tZZlzzController.do?doDel&id={id}" />
   <t:dgToolBar title="录入终止通知书" icon="icon-add" url="tZZlzzController.do?goUpdate&zzzt=1&zlsqId=${zlsqId}" funname="add" height="200"></t:dgToolBar>
   <t:dgToolBar title="录入恢复通知书" icon="icon-add" url="tZZlzzController.do?goUpdate&zzzt=0&zlsqId=${zlsqId}" funname="add" height="200"></t:dgToolBar>
   <t:dgFunOpt  title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tZZlzzController.do?goUpdate" funname="detail" height="200"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//编辑
function goUpdate(id){
    var url = "tZZlzzController.do?goUpdate&id="+id;
    createwindow("编辑",url,null,"200");
}

//查看
function goDetail(rowIndex, rowData){
    createdetailwindow("查看", "tZZlzzController.do?goUpdate&load=detail&id="+rowData.id,null,200);
}

 </script>
 <script type="text/javascript" src="webpage/common/util.js"></script> 