<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="noticeReceiverList" title="common.operation"
            actionUrl="tONoticeController.do?userDatagrid&noticeid=${noticeid}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"  width="120"></t:dgCol>
	<t:dgCol title="接收人id" field="receiverId" hidden="true"  width="120"></t:dgCol>
<%-- 	<t:dgCol title="common.username" sortable="false" field="userName" query="true" isLike="true"></t:dgCol> --%>
	<t:dgCol title="common.real.name" field="receiverName" query="true" isLike="true"  width="120"></t:dgCol>
	<t:dgCol title="是否阅读" sortable="true" field="readFlag" replace="否_0,是_1" align="center"  width="100"></t:dgCol>
	<t:dgCol title="阅读时间" sortable="true" field="readTime"  formatter="yyyy-MM-dd" width="120" align="center"></t:dgCol>
	<t:dgCol title="common.operation" field="opt" width="120"></t:dgCol>
	<t:dgDelOpt exp="readFlag#eq#0" title="common.delete" url="tONoticeController.do?delReceiver&userid={receiverId}&noticeid=${noticeid}" />
<%-- 	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&departid=${departid}" funname="add" width="1000" height="500"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&departid=${departid}" funname="update" width="1000" height="500"></t:dgToolBar> --%>
    <%--update-start--Author:zhangguoming  Date:20140826 for：添加有客户--%>
<%-- 	<t:dgToolBar title="添加接收人" icon="icon-add" url="tONoticeController.do?userList&noticeid=${noticeid}" funname="add" width="500"></t:dgToolBar> --%>
    <%--update-end--Author:zhangguoming  Date:20140826 for：添加有客户--%>
</t:datagrid>
