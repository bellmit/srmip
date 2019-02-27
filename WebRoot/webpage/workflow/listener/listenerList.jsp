<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
<t:datagrid name="listenerList" actionUrl="processController.do?listenerGrid" idField="id">
 <t:dgCol title="id" hidden="true" field="id"></t:dgCol>
 <t:dgCol title="名称" field="listenername" width="20"></t:dgCol>
 <t:dgCol title="监听类型" field="typeid" width="20" dictionary="listen_typeid"></t:dgCol>
 <t:dgCol title="事件" field="listenereven" width="20"></t:dgCol>
 <t:dgCol title="执行类型" field="listenertype" width="20" dictionary="listenertype"></t:dgCol>
 <t:dgCol title="执行内容" field="listenervalue" width="40"></t:dgCol>
 <t:dgCol title="状态" field="listenerstate" replace="已禁用_0,已启用_1"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="20"></t:dgCol>
 <t:dgConfOpt exp="listenerstate#eq#0" url="processController.do?setListeren&id={id}&status=1" message="是否启用?" title="启用"/>
 <t:dgConfOpt exp="listenerstate#eq#1" url="processController.do?setListeren&id={id}&status=0"  message="是否禁用?" title="禁用"/>
 <t:dgDelOpt  exp="listenerstate#eq#0" url="processController.do?delListeren&id={id}" title="删除"></t:dgDelOpt>
 <t:dgToolBar title="添加监听" icon="icon-add" url="processController.do?aouListener" funname="add"></t:dgToolBar>
 <t:dgToolBar title="修改监听" icon="icon-edit" url="processController.do?aouListener" funname="update"></t:dgToolBar>
</t:datagrid>
</div>
</div>
