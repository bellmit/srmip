<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
    
</script>
<div class="easyui-layout" fit="true">
  <div region="west" title="群组" style="width: 200px;" data-options="">
    <div class="easyui-layout" fit="true">
      <div data-options="region:'center'">
        <ul id="grouptree" class="easyui-tree"></ul>
      </div>
      <div data-options="region:'south'" style="height: 50px; text-align: center;">
        <span style="display: inline-block; height: 100%; vertical-align: middle;"></span>
        <a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" style="width: 100px;" onclick="goAdd()">新建群组</a>
      </div>
    </div>
  </div>
  <div region="center" style="padding: 1px;">
    <div id="groupPanel" class="easyui-panel" data-options="fit:true">
      <iframe id="groupFrame" src="tODiskController.do?goGroupDiskMain" frameborder="0" scrolling="no" style="width:100%;height:99%;"></iframe>
    </div>
</div>

</div>
<script type="text/javascript">
    $(function() {
        $("#grouptree").tree({
            url : 'tODiskController.do?groupTree',
            onClick : function(node) {
                if(node.id!='root'){
                   $("#groupFrame").attr("src","tODiskController.do?goGroupDetail&id="+node.id);
                }
            }
        });
    });

    //创建群
    function goAdd() {
	  add("创建群组","tODiskController.do?goGroupDiskAdd","",null,null);
    }
    
    //重新加载
    function loadTree(){
           $("#grouptree").tree("reload");
    }
</script>