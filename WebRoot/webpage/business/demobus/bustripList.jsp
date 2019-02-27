<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<t:datagrid name="bustrip" title="请假列表" actionUrl="busController.do?bustripGrid" idField="id" queryMode="group">
 <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
 <t:dgCol title="申请人" field="TSUser_userName"></t:dgCol>
 <t:dgCol title="出差地点" field="bustriplocale"></t:dgCol>
 <t:dgCol title="开始时间" field="begintime" query="true"></t:dgCol>
 <t:dgCol title="结束时间" field="endtime" query="true"></t:dgCol>
 <t:dgCol title="状态" field="TSPrjstatus_description"></t:dgCol>
 <t:dgCol hidden="true" title="状态ID(该字段隐藏)" field="TSPrjstatus_code"></t:dgCol>
 <t:dgCol title="业务配置ID" field="TSBusConfig_id" hidden="true"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgConfOpt exp="TSPrjstatus_code#eq#new" url="activitiController.do?startBusProcess&businessKey={id}&busconfigKey={TSBusConfig_id}" message="确认完毕,提交申请?" title="提交申请"></t:dgConfOpt>
 <t:dgFunOpt exp="TSPrjstatus_code#eq#doing" funname="progress(id,TSUser_userName)" title="项目进度"></t:dgFunOpt>
 <t:dgDelOpt exp="TSPrjstatus_code#eq#new" url="busController.do?delBustrip&id={id}" title="删除"></t:dgDelOpt>
 <t:dgToolBar operationCode="add" title="出差申请" icon="icon-add" url="busController.do?aorubustrip" funname="add"></t:dgToolBar>
 <t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="busController.do?aorubustrip" funname="update"></t:dgToolBar>
 <t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="busController.do?aorubustrip" funname="detail"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
$(document).ready(function(){
		$("input[name='begintime']").focus(function(){
		  WdatePicker({isShowClear:false,readOnly:true});
		 });
		$("input[name='endtime']").attr("class","easyui-datebox");
	});
</script>