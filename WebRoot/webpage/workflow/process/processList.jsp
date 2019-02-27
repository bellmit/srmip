<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<SCRIPT type="text/javascript">
	function processcofig(id) {
		addOneTab('流程配置', 'processController.do?processTabs&processid=' + id);
	}
</SCRIPT>
<t:datagrid name="processList" title="流程管理" actionUrl="processController.do?processGrid&typeid=${typeid}" idField="id">
 <t:dgCol title="流程id" field="id" width="100" hidden="true"></t:dgCol>
 <t:dgCol title="流程名称" query="true" field="processname" width="70"></t:dgCol>
 <t:dgCol title="流程KEY" field="processkey" width="70"></t:dgCol>
 <t:dgCol title="流程类型" field="TSType.typename" width="70"></t:dgCol>
 <t:dgCol title="流程状态" field="processstate" replace="已发布_1,未发布_0" width="70"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgFunOpt funname="processcofig(id)" title="配置"></t:dgFunOpt>
 <t:dgConfOpt url="processController.do?deployProcess&processid={id}" message="确认部署该流程" title="发布"></t:dgConfOpt>
 <t:dgDelOpt  exp="processstate#eq#0" url="processController.do?delprocess&id={id}" title="删除"></t:dgDelOpt>
</t:datagrid>
<div id="processListtb" style="padding: 3px; height: 25px">
 <div style="float: left;">
  <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="openwindow('新建流程','processController.do?processDesigner','processList',800,500)">新建流程</a>
  <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="editfs('流程编辑','processController.do?processDesigner')">编辑流程</a>
 </div>
</div>
