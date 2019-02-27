<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
 <body>
<t:datagrid name="processproList" title="流程变量管理" actionUrl="processController.do?processproList&processid=${processid }" idField="id">
 <t:dgCol title="Id" field="id" width="20" hidden="true"></t:dgCol>
 <t:dgCol title="变量编码" field="processprokey" width="70"></t:dgCol>
 <t:dgCol title="变量名称" field="processproname" width="70"></t:dgCol>
 <t:dgCol title="所属流程节点" field="TPProcessnode_processnodename" width="70"></t:dgCol>
 <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
 <t:dgDelOpt url="processController.do?delPro&id={id}" title="删除"></t:dgDelOpt>
</t:datagrid>
<div id="processproListtb" style="padding:3px; height:25px">
 <div style="float: left;">
  <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('流程变量录入','processController.do?addorupdatePro&processid=${processid }','processproList')">添加变量</a>
  <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('流程变量编辑','processController.do?addorupdatePro&processid=${processid }','processproList')">编辑变量</a>
 </div>
</div>
</body>
