<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
<t:datagrid name="deploymentInProcesskeyList" title="流程${processkey}已发布流程" actionUrl="activitiController.do?processDeploymentInProcesskeyGrid&processkey=${processkey}" idField="id" pagination="false">
 <t:dgCol title="ProcessDefinitionId" field="id" width="100" hidden="true"></t:dgCol>
 <t:dgCol title="DeploymentId" field="deploymentId" width="70" hidden="true"></t:dgCol>
 <t:dgCol title="名称" field="name" width="70"></t:dgCol>
 <t:dgCol title="流程KEY" field="key" width="70"></t:dgCol>
 <t:dgCol title="版本号" field="version"></t:dgCol>
 <t:dgCol title="状态" field="suspensionState" replace="已激活_1,已挂起_2"></t:dgCol>
 <t:dgCol title="图片名" field="diagramResourceName" hidden="true"></t:dgCol>
 <t:dgCol title="资源名字" field="resourceName" hidden="true"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgFunOpt funname="deploymentimg(deploymentId,diagramResourceName,name)" title="流程图"></t:dgFunOpt>
 <t:dgConfOpt exp="suspensionState#eq#1" url="activitiController.do?setProcessState&state=suspend&processDefinitionId={id}" title="挂起" message="是否挂起?"></t:dgConfOpt>
 <t:dgConfOpt exp="suspensionState#eq#2" url="activitiController.do?setProcessState&state=active&processDefinitionId={id}" title="激活" message="是否激活?"></t:dgConfOpt>
 <t:dgDelOpt url="activitiController.do?deleteProcess&key={key}&deploymentId={deploymentId}" title="删除"></t:dgDelOpt>
 <%-- <t:dgFunOpt funname="downloadXml(deploymentId,resourceName,name)" title="下载流程"></t:dgFunOpt> --%>
 <t:dgToolBar title="流程部署" icon="icon-edit" url="activitiController.do?openDeployProcess" funname="openuploadwin"></t:dgToolBar>
</t:datagrid>
</div>
</div>