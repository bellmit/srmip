<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="departUserList" title="common.operation"
            actionUrl="departController.do?userDatagrid&departid=${departid}" 
            fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" width="100" sortable="false" field="userName" isLike="true" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" width="100" field="realName" isLike="true" query="true"></t:dgCol>
	<t:dgCol title="common.status" width="80" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	
	<t:dgCol title="common.operation" field="opt" width="120"></t:dgCol>
	<t:dgDelOpt title="移除用户" 
		url="departController.do?romoveDepartUser&userId={id}&userName={userName}&departId=${departid}" />
	
	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" 
		url="userController.do?addorupdate&departid=${departid}" 
		funname="add" width="1000" height="500"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" 
		url="userController.do?addorupdate&departid=${departid}" 
		funname="update" width="1000" height="500"></t:dgToolBar>
    <%--添加有客户--%>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" 
		url="departController.do?goAddUserToOrg&orgId=${departid}" 
		funname="add" width="500" height="480"></t:dgToolBar>
</t:datagrid>
