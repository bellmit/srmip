<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
	<t:datagrid name="roleList" title="角色列表" fit="true" fitColumns="true" 
		actionUrl="roleController.do?roleGrid" idField="id">
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="角色编码" field="roleCode" width="100"></t:dgCol>
		<t:dgCol title="角色名称" field="roleName" query="true" isLike="true" width="100"></t:dgCol>
		<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
		<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="配置portal"></t:dgFunOpt>
	</t:datagrid>
</div>
</div>
<div region="east" style="width: 600px;" split="true">
<div tools="#tt" class="easyui-panel" title='设置portal' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>
<script type="text/javascript">
function setfunbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '待办设置',
			href:"tPortalController.do?goRolePortalSet&roleId=" + id
		}
	);
	$('#function-panel').panel("refresh" );
	
}

</script>
