<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
// $(function(){
// 	//编辑时审批已处理：提示不可编辑
// 	if(location.href.indexOf("tip=true") != -1){
// 		var parent = frameElement.api.opener;
// 		var msg = $("#tipMsg", parent.document).val();
// 		tip(msg);
// 	}
// });
$(function(){
	
	//实例化树形菜单
	$("#menu").tree({
		url : 'tBAppraisalProjectController.do?loadApprTree&apprid=${tBAppraisalProject.id}',
		onClick : function (node) {
			if (node.attributes) {
				$("#Validform_msg").remove();
				if($('#centerPanel #file_upload').length>0){//销毁uploadify元素
				    $('#centerPanel #file_upload').uploadify('destroy');
				}
				if($('#centerPanel #file_upload1').length>0){//销毁uploadify元素
				    $('#centerPanel #file_upload1').uploadify('destroy');
				}
				if($('#centerPanel #file_upload2').length>0){//销毁uploadify元素
				    $('#centerPanel #file_upload2').uploadify('destroy');
				}
				$('#centerPanel').panel("refresh", node.attributes.url);
				$('#localPanelUrl').val(node.attributes.url);
				$('#tabMsg').val(node.attributes.apprMsg);
				$('#currentNodeId').val(node.id);
			}
		},
		onLoadSuccess:function(node,data){
			if(data[0]){//加载成功后默认选中第一个节点若为刷新后的页面则选择之前节点
				var nodeid= data[0].id;
				if($('#currentNodeId').val()){
					nodeid = $('#currentNodeId').val();
				}
				var selectedNode = $('#menu').tree('find', nodeid);
				$('#menu').tree('select', selectedNode.target);
				$('#currentNodeId').val(nodeid);
				$('#centerPanel').panel("refresh", selectedNode.attributes.url);
				$('#localPanelUrl').val(selectedNode.attributes.url);
				$('#tabMsg').val(selectedNode.attributes.apprMsg);
			}
		}
	});
});
</script>
<body class="easyui-layout">
<input type="hidden" id="tabMsg"/>
<input type="hidden" id="localPanelUrl">
<input type="text" id="currentNodeId">
<%-- <input id="jsonArray" value="${array}" type="hidden"> --%>
<!-- <div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>    -->
<!--     <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>    -->
<!--     <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>    -->
    <div data-options="region:'west',title:'鉴定环节',split:true" style="width:260px;">
    	<div id="menu"></div>
    </div>   
    <div data-options="region:'center'" style="padding:5px;">
    	<div class="easyui-panel" style="padding: 1px;overflow: auto;" fit="true" border="false" id="centerPanel"></div>
    </div>
</body>
