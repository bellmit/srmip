<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/project/manage/projectMgr.js"></script>
<script>
$(function() {
    var departFlag = $("#departFlag").val();
    //监听树的点击事件
    var url = 'projectMgrController.do?projectTree&sortKey=1';
    $("#processtree").tree({
        url : url,
        onLoadSuccess : function(node, data){
            var nodeId = $("#projectId").val();
            focusNode("processtree", nodeId);
        },
        onBeforeLoad :function(node, param){
            var projectIds = window.top.projectIds;
            if(projectIds!=""){
               param['ids']=projectIds;
            }
        },
        onClick : function(node) {
        	clickNode(node);
        }
    });
    appendDivListForProject('a1','m1','立项管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag,'1');
    appendDivListForProject('a2','m2','计划管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag, '2');
    appendDivListForProject('a3','m3','执行管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag, '3');
    appendDivListForProject('a4','m4','控制管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag, '4');
    appendDivListForProject('a5','m5','鉴定验收', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag, '5');
    appendDivListForProject('a6','m6','成果管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag, '6');
    appendDivListForProject('a7','m7','知识产权管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag, '7');
    //appendDivListForProject('a8','m8','质量管理', 'tPmProjectApprovalController.do?getApprovalMenu', '8');
});


function clickNode(node){
	var url = "";
    if (node.id.indexOf("treeId") != -1) {//树父节点
        $("#projectId").val("");//设置项目节点id为空
    } else {//叶子节点跳转
    	 var src = $("#contentFrame").attr("src");
     	 url = "tPmProjectController.do?goUpdateForResearchGroup&id="+node.id;
     	 var pcFlag = $("#planContractFlag").val();//获取上次计划合同标志
     	 
        $("#planContractFlag").val(node.attributes.planContractFlag);//保存点击项目计划合同标志存下来
         $("#projectNameNode").val(node.text);//将项目名称保存下来
         $("#projectId").val(node.id);//将项目节点id保存下来
     	 /**
     	 *1) src不存在或者选中叶子节点计划合同标志与之前选中叶子节点标志不一致时，跳转到项目基本信息界面
     	 *2) 其他情况修改传入项目ID参数，并重新请求该项目立项管理模块下拉菜单
     	 */
         if(src==""||src==undefined){
             src = url;
         }else{
             var idx = src.indexOf("&projectId");
             if(idx!=-1){
                 src = src.substring(0, idx)+"&projectId="+node.id+"&planContractFlag="+node.attributes.planContractFlag;
             }else{
                 src = url;
             }
         }
     	 appendDivListForProject('a1','m1','立项管理', 'tPmProjectApprovalController.do?getApprovalMenu&departFlag='+departFlag,'1');//根据计划合同标志请求项目立项管理下拉菜单
         $("#contentFrame").attr("src", src);
    }
}
</script>

  <input id="sortType" type="hidden" value="1">
  <input id="projectId" type="hidden">
  <input id="planContractFlag" type="hidden">
  <input id="ids" type="hidden" value="${ids}">
  <input id="departFlag" type="hidden" value="${departFlag}">
  <div id="layoutDiv" class="easyui-layout" data-options="fit:true">
    <div region="center" style="padding: 1px;" id="process-panelss">
     
      <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north'" style="height: 33px; text-align: center; vertical-align: middle;">
          <span><a id="a0" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-basicPM'" onclick="goBase()">基本信息</a></span>
          <span><a id="a1"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-approvalPM'" >立项管理</a></span>
          <span><a id="a2"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-planPM'" >计划管理</a></span>
          <span><a id="a3"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-execPM'" >执行管理</a></span>
          <span><a id="a4"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-controlPM'" >控制管理</a></span>
          <span><a id="a5"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-checkPM'" >验收管理</a></span>
          <span><a id="a6"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-resultPM'" >成果管理</a></span>
          <span><a id="a7"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-rightPM'" >知识产权管理</a></span>
          <!-- <span><a id="a8"  href="#" class="easyui-menubutton" data-options="iconCls:'icon-reload'" >质量管理</a></span> -->
          <span style="vertical-align: middle; display: inline-block; height: 100%"></span>
          <div id="m1" style="width:150px"></div>
          <div id="m2" style="width:150px"></div>
          <div id="m3" style="width:150px"></div>
          <div id="m4" style="width:150px"></div>
          <div id="m5" style="width:150px"></div>
          <div id="m6" style="width:150px"></div>
          <div id="m7" style="width:150px"></div>
          <div id="m8" style="width:150px"></div>
        </div>
        <div data-options="region:'center'" style="border: 0px">
          <input id="projectNameNode" type="hidden">
          <iframe id="contentFrame" style="border: 0; width: 100%; height: 99.4%;"></iframe>
        </div>
      </div>
    </div>

	<!-- 左侧项目树 -->
    <div region="west" minWidth="200" style="width: 205px;" title="项目列表" split="true">
      <div class="easyui-layout" data-options="fit:true,border:false" style="padding: 10px;">
        <div data-options="region:'north'" style="height: 32px;text-align: center;padding-top:2px; vertical-align: top; overflow: hidden;border-style: solid none solid none">
          <input id="search" class="easyui-searchbox" style="height:26px;" 
            data-options="prompt:'输入项目名称',searcher:doSearch,width:130">
          <a id="addBtn" class="easyui-linkbutton" onclick="addProject()" data-options="iconCls:'icon-add',plain:true" title="添加项目" 
          style="vertical-align: top;"></a><a id="refreshBtn" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true"
          onclick="doRefresh()"  title="刷新项目树" style="vertical-align: top;"></a>
        </div>
        <div id="treeDiv" data-options="region:'center'" style="border:0px;">
          <ul id="processtree" class="easyui-tree" >
          </ul>
        </div>
        <div data-options="region:'south'" style="height: 32px;
        	border-style: solid none none none; padding-top: 2px;text-align: center;">
          	<span>
          		<img src="plug-in/easyui/themes/icons/lxxs.png"  style="width: 43px;"
            		onclick="changeSortType(1)"></img>
            </span>
            <span>
	            <img src="plug-in/easyui/themes/icons/year.png" style="width: 43px;"
	           	 	onclick="changeSortType(2)"></img>
           	</span> 
            <span>
            	<img  src="plug-in/easyui/themes/icons/lb.png" style="width: 43px;"
             		onclick="changeSortType(3)"></img>
             </span> 
             <span>
             	<img src="plug-in/easyui/themes/icons/xz.png" style="width: 43px;"
             		onclick="changeSortType(4)"></img>
             </span>
        </div>
      </div>
    </div>
    
  	<!-- 右侧 -->
	<div collapsed="true" region="east" iconCls="icon-reload" title="辅助工具"
    	split="true" style="width: 193px;">
      	<div data-options="region:'south'" style="height: 40px">
        	<div class="easyui-panel" data-options="fit:true,border:false"
          		style="text-align: center; vertical-align: middle;">
          		<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
            		onclick='add("添加提醒","tOWarnController.do?goCommonWarn&type=2");'>添加提醒</a>
            	<span style="height: 100%; vertical-align: middle; display: inline-block;"></span>
        	</div>
      	</div>
	</div>
</div>
