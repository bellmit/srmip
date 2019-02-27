<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <head>
  <t:base type="jquery,easyui,tools"></t:base>
  <title>流程节点列表</title>
 </head>
 <body>
  <t:datagrid name="processnodeList" title="流程节点管理" actionUrl="processController.do?datagridNode&processid=${processid }" idField="processnodeid">
   <t:dgCol title="id" field="id" width="30" hidden="true"></t:dgCol>
   <t:dgCol title="节点名称" field="processnodename" width="70"></t:dgCol>
   <t:dgCol title="节点代码" field="processnodecode" width="70"></t:dgCol>
   <t:dgCol title="表单地址" field="modelandview" width="70"></t:dgCol>
   <t:dgCol title="所属流程" field="TPProcess_processname" width="70"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt url="processController.do?delNode&id={id}" title="删除"></t:dgDelOpt>
   <t:dgFunOpt funname="conifgRole(id)" title="权限配置"></t:dgFunOpt>
  </t:datagrid>
  <div id="processnodeListtb" style="padding:3px; height:25px">
   <div style="float: left;">
    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-add" onclick="add('添加流程节点','processController.do?addorupdateNode&processid=${processid }','processnodeList')">添加流程节点</a>
    <a href="#" class="easyui-linkbutton" plain="true" icon="icon-edit" onclick="update('编辑流程节点','processController.do?addorupdateNode&processid=${processid }','processnodeList')">编辑流程节点</a>
   </div>
  </div>
  <script type="text/javascript">
	function conifgRole(id){
		add('权限配置','processController.do?nodeFun&nodeId='+id);
	}
</script>
 </body>



