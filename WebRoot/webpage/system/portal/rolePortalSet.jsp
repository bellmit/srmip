<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script type="text/javascript">
	$(function() {
	    $('#portalTab').datagrid({    
	        url:'tPortalController.do?dataList&field=id,title',
	        checkbox : 'true',
	        idField:'id',
	        fitColumns: true,
	        columns:[[    
	            {field:'ck',checkbox:true,width:100},    
	            {field:'id',title:'id',hidden:'true',width:100},    
	            {field:'title',title:'待办名称',width:100},    
	        ]],
	        onLoadSuccess:function(){
	            var portalIds = $("#portalIds").val();
	            var ids = portalIds.split(",");
	            var rows = $('#portalTab').datagrid('getRows');
	            for(var i = 0;i<ids.length;i++){
	                $('#portalTab').datagrid('selectRecord',ids[i]);
	            }
	        }
	    });
		
	});
	function mysubmit() {
		var roleId = $("#rid").val();
		var s = getNode();
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : {'portals':s,'roleId':roleId},
			url : "tPortalController.do?setRolePortal",// 请求的action路径
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
				}
			}
		});
	}
	function getNode() {
		var node = $('#portalTab').datagrid('getChecked');
		var cnodes = '';
		for ( var i = 0; i < node.length; i++) {
		    cnodes = cnodes + node[i].id+",";
		}
		return cnodes;
	};
	
	
	function selecrAll() {
		$('#portalTab').datagrid('checkAll');
	}
	
	function reset() {
	    $('#portalTab').datagrid('uncheckAll');
	}

	$('#selecrAllBtn').linkbutton({   
	}); 
	
	$('#resetBtn').linkbutton({   
	});   
</script>
<input id="portalIds" value="${portalIds}" type="hidden">
<div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel">
<input type="hidden" name="roleId" value="${roleId}" id="rid"> 
<a id="selecrAllBtn" onclick="selecrAll();" data-options="iconCls:'icon-ok',plain:'true'">全选</a> 
<a id="resetBtn" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:'true'" onclick="reset();">重置</a>
<a id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:'true'" onclick="mysubmit();">保存</a>
<div id="portalTab"></div>
</div>
