<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>立项论证管理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href='plug-in/accordion/css/icons.css' rel='stylesheet' type='text/css'>

<script>
    $(function() {
        var projectId = $("#projectId").val();
        $.ajax({
			async : false,
			cache : false,
			type : 'POST',
            url : "tPmProjectApprovalController.do?getApprovalMenu&menu="+${menu},
            data :{"projectId":projectId},
            datatype : 'html',
			success : function(data) {
				$("#processtree").html(eval(data));
				$("#processtree").tree({
				    onSelect : function(node) {
		                var url = $(node.target).find("a").attr("href");
		                $("#contentFrame").attr("src", url);
		            }
				});
			}
		});
        //更改图标
        $("#processtree").find("span.tree-icon").addClass("default"); 

        var menuKey = $("#menu").val();

        var node = $('#processtree').tree('find', menuKey);
        $('#processtree').tree("select", node.target);
    });
</script>
</head>
<body>
  <input id="menu" type="hidden" value="${menu}">
  <input id="projectId" type="hidden" value="${projectId}">
  <div id="layoutDiv" class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding: 1px;" id="process-panelss">
      <div class="easyui-layout" data-options="fit:'true'">
        <div data-options="region:'center'">
          <iframe id="contentFrame" name="contentFrame" src="tPmProjectController.do?tPmProject"
            style="border: 0; width: 100%; height: 99.4%;"></iframe>
        </div>
      </div>
    </div>

    <div region="west" minWidth="200" style="width: 200px;" title="项目管理" split="true">
      <div class="easyui-layout" data-options="fit:true,border:false" style="padding: 10px;">
        <div id="menuDiv" data-options="region:'center'">
          <ul id="processtree" >
          </ul>
        </div>
      </div>
    </div>
  </div>
</body>
</html>