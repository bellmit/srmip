<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding: 1px;">
<t:datagrid name="jeecgNoteList" title="单表模型" actionUrl="jeecgNoteController.do?datagrid" idField="id" fit="true" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="年龄" field="age" query="true" extend="{data-options:{required:false,groupSeparator:\"','\"},class:{value:'easyui-numberbox'}}"></t:dgCol>
	<t:dgCol title="生日" field="birthday" formatter="yyyy-MM-dd"></t:dgCol>
	<t:dgCol title="出生日期" field="createdt" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" extend="{class:{value:'easyui-datebox'}}"></t:dgCol>
	<t:dgCol title="用户名" field="name" query="true" extend="{style:{width:'300px'}}"></t:dgCol>
	<t:dgCol title="工资" field="salary"></t:dgCol>
	<t:dgCol title="流程状态" field="bpmStatus" dictionary="bpm_status"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgFunOpt exp="bpmStatus#eq#1" funname="startUserDefinedProcess(id)" title="提交流程" />
	<t:dgDelOpt title="删除" url="jeecgNoteController.do?del&id={id}" />
	<t:dgToolBar title="录入" icon="icon-add" url="jeecgNoteController.do?addorupdate" funname="add" operationCode="add"></t:dgToolBar>
	<t:dgToolBar title="录入无默认按钮" icon="icon-add" url="jeecgNoteController.do?addorupdate" funname="noteAdd" operationCode="add"></t:dgToolBar>
	<t:dgToolBar title="编辑" icon="icon-edit" url="jeecgNoteController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid>

</div>
</div>
<script type="text/javascript">

    function noteAdd(){
        openwindow("自定义按钮","jeecgNoteController.do?addorupdateNoBtn","jeecgNoteList",600,400);
    }
    
    //启动流程
    function startUserDefinedProcess(id,tableName,formUrl){
    	//业务表名
    	var tableName = 'jg_person';
    	//流程对应表单URL
    	var formUrl = 'jeecgNoteController.do?addorupdate';
    	confirm('activitiController.do?startUserDefinedProcess&id='+id+'&tableName='+tableName+'&formUrl='+formUrl,'确定提交流程吗？','jeecgNoteList');
    }
</script>