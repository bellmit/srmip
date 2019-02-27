<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:datagrid name="userList" title="user.manage" actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id">--%>
<%--	<t:dgCol title="common.id" field="id" hidden="true" ></t:dgCol>--%>
<%--	<t:dgCol title="common.username" sortable="false" field="userName" width="5"></t:dgCol>--%>
<%--	<t:dgCol title="common.real.name" field="realName" width="5"></t:dgCol>--%>
<%--</t:datagrid>--%>

<input id="roleId" value="${roleId }" type="hidden"/>
<t:datagrid name="roleUserList" title="common.operation"
            actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true" queryMode="single" isLike="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true" queryMode="single" isLike="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgFunOpt funname="romoveRoleUser(id)" title="移除用户"></t:dgFunOpt>
    <%-- <t:dgDelOpt title="common.delete" url="userController.do?del&id={id}&userName={userName}" /> --%>
	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate&roleId=${roleId}" funname="add" width="1050" height="550"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate&roleId=${roleId}" funname="update" width="1050" height="550"></t:dgToolBar>
	<t:dgToolBar title="common.add.exist.user" icon="icon-add" 
		url="roleController.do?goAddUserToRole&roleId=${roleId}" funname="add" width="500"></t:dgToolBar>
</t:datagrid>

<script type="text/javascript">
function romoveRoleUser(id) {
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){ 
	        $.ajax({
				type:'post',
				url:"roleController.do?romoveRoleUser",
				data:'userId='+id+"&roleId="+$("#roleId").val(),
				success:function(result){
					result = $.parseJSON(result);
					$("#roleUserList").datagrid('reload');
					$.messager.show({
						title:'提示',
						msg:result.msg,
						timeout:5000,
						showType:'slide'
					});
				}
			});
	    }    
	});  

}
</script>