<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>课题组项目管理</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/project/manage/projectMgr.js?${tm}"></script>
<link rel="shortcut icon" href="images/favicon.ico">
<link rel="stylesheet" href="plug-in/accordion/css/icons.css" type="text/css"></link>
<style type="text/css">
a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
}

.tree-node-selected {
	background: #eaf2ff;
}

.shadow {
	text-shadow: 1px 1px 0 #CCC, 2px 2px 0 #CCC,
		/* end of 2 level deep grey shadow */ 
  3px 3px 0 #444, 4px 4px 0 #67E, 5px 5px 0 #67E, 6px 6px 0 #67E;
	/* end of 4 level deep dark shadow */
}
</style>
<SCRIPT type="text/javascript">
  $(function() {
    tabClose();//监听tab上菜单事件
    tabCloseEven();
    initMenu();//初始化菜单
    
    //鼠标经过时切换图片
    $("#swithImg span img").mouseover( function() {
      this.src = this.src.substring(0,this.src.indexOf('.png'))+"-b.png";
     }).mouseout( function(){
       this.src = this.src.substring(0,this.src.indexOf('.png') - 2)+".png";
     });
  });
  var onlineInterval;

  function easyPanelCollapase() {
    window.clearTimeout(onlineInterval);
  }
  function easyPanelExpand() {
    onlineInterval = window.setInterval(function() {
      $('#layout_jeecg_onlineDatagrid').datagrid('load', {});
    }, 1000 * 20);
  }

  function panelCollapase() {
    $(".easyui-layout").layout('collapse', 'north');
  }

  function tabClose() {
    /* 双击关闭TAB选项卡 */
    $(".tabs-inner").dblclick(function() {
      var subtitle = $(this).children(".tabs-closable").text();
      $('#maintabs').tabs('close', subtitle);
    });
    /* 为选项卡绑定右键 */
    $(".tabs-inner").bind('contextmenu', function(e) {
      $('#mm').menu('show', {
        left : e.pageX,
        top : e.pageY
      });

      var subtitle = $(this).children(".tabs-closable").text();

      $('#mm').data("currtab", subtitle);
      // $('#maintabs').tabs('select',subtitle);
      return false;
    });
  }
  // 绑定右键菜单事件
  function tabCloseEven() {
    // 刷新
    $('#mm-tabupdate').click(function() {
      var currTab = $('#maintabs').tabs('getSelected');
      var url = $(currTab).find("iframe").attr('src');
      if (url != undefined) {
        $(currTab).find("iframe").attr("src", url);
      }
    });
  }

  function closeWebPage() {
    window.location = "loginController.do?login";
  }

  function mouseover() {
    $("#btn").find("span").css("color", "black");
  }

  function mouseout() {
    $("#btn").find("span").css("color", "#FFFFFF");
  }

  function createFrame(url) {
    var s = '<iframe scrolling="no" frameborder="0"  src="' + url + '" style="width:100%;height:99.5%;overflow-y:hidden;"></iframe>';
    return s;
  }

  var projectProcessSrc = "";
  var projectInfoSrc = "";
  function clickNode(node) {
    var departFlag = $("#departFlag").val();
    var url = "";
    var src = "";
    if (node.id.indexOf("treeId") != -1) {//树父节点
      $("#projectId").val("");//设置项目节点id为空
      $("#processtree").tree("toggle", node.target);
    } else {//叶子节点跳转
      projectInfoSrc = "tPmProjectInfoController.do?tPmProjectInfo&projectId=" + node.id;
      projectProcessSrc = "tPmProjectInfoController.do?projectProcessMgr&projectId=" + node.id;
      var tab = $('#maintabs').tabs('getSelected');
      var index = $('#maintabs').tabs('getTabIndex',tab);
	  if(index==0){
	      $("#projectInfoFrame").attr("src", projectInfoSrc);
	  }else{
	      $('#maintabs').tabs('select',1);
	  } 
      var pcFlag = $("#planContractFlag").val();//获取上次计划合同标志

      $("#planContractFlag").val(node.attributes.planContractFlag);//保存点击项目计划合同标志存下来
      $("#projectNameNode").val(node.text);//将项目名称保存下来
      $("#projectId").val(node.id);//将项目节点id保存下来
      /**
       *1) src不存在或者选中叶子节点计划合同标志与之前选中叶子节点标志不一致时，跳转到项目基本信息界面
       *2) 其他情况修改传入项目ID参数，并重新请求该项目立项管理模块下拉菜单
       */
      if (src == "" || src == undefined) {
        src = url;
      } else {
        var idx = src.indexOf("&projectId");
        if (idx != -1) {
          src = src.substring(0, idx) + "&projectId=" + node.id + "&planContractFlag=" + node.attributes.planContractFlag;
        } else {
          src = url;
        }
      }
    }
  }

  //初始化菜单
  function initMenu() {
    var departFlag = $("#departFlag").val();
    //监听树的点击事件
    var url = 'projectMgrController.do?projectTree&sortKey=1';
    $("#processtree").tree({
      url : url,
      onLoadSuccess : function(node, data) {
    	  //var nodeId = $("#projectId").val();
          //focusNode("processtree", nodeId);
      },
      onBeforeLoad : function(node, param) {
        var projectIds = window.top.projectIds;
        if (projectIds != "") {
          param['ids'] = projectIds;
        }
      },
      onClick : function(node) {
        clickNode(node);
      }
    });
    
  	//草稿箱
    initTree("unapprovalTree");
    
    //待审批
    initTree("approvalTree",1);
  }
 
  //加载树
  function initTree(treeId,auditStatus){
	    var projectName = $("#search").searchbox("getValue");
	    $("#"+treeId).tree({
	        url:'projectMgrController.do?unApprovalProjectList'+(auditStatus ? "&auditStatus=1" : "")+'&pName='+projectName,
	        onLoadSuccess:function(){
	            var nodeId = $("#projectId").val();
	            focusNode(treeId, nodeId);
	        },
	        onSelect:function(node){
	            if(node.id!='treeId'){
	            	if(auditStatus){
	            		$("#ApprovalFrame").attr("src","tPmProjectController.do?goUpdateUnapproval&showMode=readonly&id="+node.id);
	            		$("#projectProcessFrame_1").attr("src","tPmProjectInfoController.do?projectProcessMgr&showMode=readonly&projectId=" + node.id);
	            	}else{
               		 	var url = "tPmProjectController.do?goUpdateUnapproval&id="+node.id;
               		 	$("#unApprovalFrame").attr("src",url);
	            	}
	            }
	        }
	    });
  }
  
  //已审批 详情tab页
  function selectTab(title, index) {
        if(projectInfoSrc==""){
            projectInfoSrc = "webpage/com/kingtake/project/manage/welcome/welcome.htm";
        }
        if(projectProcessSrc==""){
            projectProcessSrc = "webpage/com/kingtake/project/manage/welcome/welcome.htm";
        }
        if (index == 0) {
            $("#projectInfoFrame").attr("src", projectInfoSrc);
        }else if (index == 1) {
            $("#projectProcessFrame").attr("src", projectProcessSrc);
        }
    }
  
  function treeCollapase(){
      $("#centerPanel").layout("collapse","west");
  }
  
  //树区tab点击，联动右区显示对应的详情界面
  function treeTabSelect(title,index){
	  var ids = ["unApprovalDiv","ApprovalDiv","maintabs"];
	  
	  for(var i=0; i<ids.length; i++){
		  $("#"+ids[i]).hide();
	  }
	  
	  $("#"+ids[index]).show();
	  
	  if("已审批"!=title){
		  $("#copyBtn").hide();
	  }else{
		  $("#copyBtn").show();
	  }
	  
	  var h=$("#treeTabs").tabs("getTab",title).panel("options").height;
	  $("#treeTabs").tabs("getTab",title).panel("resize",{height:h-60});
	  debugger;
	  doRefresh();
  }
  
</SCRIPT>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
  <input id="sortType" type="hidden" value="1">
  <input id="projectId" type="hidden">
  <input id="planContractFlag" type="hidden">
  <input id="ids" type="hidden" value="${ids}">
  <input id="departFlag" type="hidden" value="${departFlag}">
  <!-- 顶部-->
  <div region="north" border="false" title="" style="BACKGROUND:url(plug-in/login/images/header-bg.png) rgb(60, 126, 216); height: 100px; padding: 1px; overflow: hidden;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" style="vertical-align: text-bottom;width:400px;">
          <img src="plug-in/login/images/logo_new.png">
          <!--
            <img src="plug-in/login/images/toplogo.png" width="550" height="52" alt="">
        <div style="position: absolute; top: 75px; left: 33px;">JEECG Framework <span style="letter-spacing: -1px;">3.4.3 GA</span></div>-->
        </td>
        <td align="right" nowrap>
          <table border="0" cellpadding="0" cellspacing="0"  width="100%" style="position: absolute;top:0px; left:0px;">
            <tr style="height: 25px;" align="right">
              <td style="" colspan="2">
                <div style="float: right;">
                  <div style="float: left; line-height: 25px; margin-left: 70px;">
                    <span style="color: #bde8ff">
                      <t:mutiLang langKey="common.user" />
                      :
                    </span>
                    <span style="color: #FFFFFF;font-weight: bold;">${realName }</span>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="color: #bde8ff">
                      <t:mutiLang langKey="current.org" />
                      :
                    </span>
                    <span style="color: #FFFFFF;font-weight: bold;">${currentOrgName }</span>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <span style="color: #bde8ff">
                      <t:mutiLang langKey="common.role" />
                      :
                    </span>
                    <span style="color: #FFFFFF;font-weight: bold;">${roleName }</span>
                  </div>
                  <div style="float: left; margin-left: 18px;">
                    <a id="btn" href="#" class="easyui-linkbutton" title="返回" data-options="iconCls:'icon-undo',plain:true" onclick="closeWebPage()" onmouseover="mouseover()" onmouseout="mouseout()">
                      <span style="color: #FFFFFF;">返回</span>
                    </a>
                  </div>
                  <div style="float: left; margin-left: 8px; margin-right: 5px; margin-top: 5px;">
                    <img src="plug-in/easyui/themes/default/images/layout_button_up.gif" style="cursor: pointer" onclick="panelCollapase()" />
                  </div>
                </div>
              </td>
            </tr>
            <tr style="height: 80px;">
              <td colspan="2" style="text-align: right;">
                <span style="word-spacing: 10px; font:30px/30px 'Times New Roman', arial, sans-serif; color: #fff;font-family:微软雅黑;padding-right:30px;"> 课 题 组 项 目 管 理 </span>
                <!-- <span style="word-spacing:30px;font:italic bold 60px/30px 'Times New Roman',arial,sans-serif;color: #8400FF;text-shadow: 1px 1px 1px red;"> 机 关 </span><span style="word-spacing:3px;font:normal bold 30px/30px arial,sans-serif;">项 目 管 理</span> -->
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  
  
  <!-- 中间-->
  <div id="mainPanle" region="center" style="overflow: hidden; border: 0px;">
    <div id="centerPanel" class="easyui-layout" data-options="fit:true,border:false">
      <!-- 左侧项目树 -->
      <div id="treePanel" region="west" minWidth="200" style="width: 235px;overflow:hidden;" split="true" >
      
      <div data-options="region:'north'" style="height: 63px; text-align: left; vertical-align: top; overflow: hidden;padding-left:1px; ">
      	<div><input id="search" class="easyui-searchbox" style="height: 26px;width: 225px;" data-options="prompt:'输入项目名称',searcher:doRefresh,width:130"></div>
        
        <a id="addBtn" class="easyui-linkbutton" onclick="addProject()" data-options="iconCls:'icon-add',plain:true" title="新增项目" style="vertical-align: top;">新增</a>
        <a id="copyBtn" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="doCopy()" title="复制项目" style="vertical-align: top;">复制</a> 
        <a id="refreshBtn" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="doRefresh()" title="刷新项目树" style="vertical-align: top;">刷新</a> 
        
      </div>
     
      <div id="treeTabs" class="easyui-tabs" data-options="tools:'#tab-tools',onSelect:treeTabSelect,fit:true">

      	<div title="草稿箱" style="overflow-y:auto;"> 
        	<ul id="unapprovalTree" class="easyui-tree" style="height:75%"></ul>
        </div>
        
        <div title="待审批" style="overflow-y:auto;">
       		<ul id="approvalTree" class="easyui-tree"></ul>
        </div> 
      
        <div title="已审批"  style="padding: 0px;overflow-y:auto;"  selected>
        <div class="easyui-layout" data-options="fit:true,border:true">
          <div id="treeDiv" data-options="region:'center'" style="border: 0px;">
            <ul id="processtree" class="easyui-tree">
            </ul>
          </div>
       </div>
       </div>
     </div>
   </div>
      
      <!-- 详情区 -->
      <div region="center" id="centerRegion">
      	
      	<!-- 草稿 -->
      	<div id="unApprovalDiv" class="easyui-panel" data-options="fit:true,border:false">
           <iframe id="unApprovalFrame" scrolling="auto" frameborder="0" style="width: 100%; height: 99.5%;"></iframe>
        </div>
        
        <!-- 待审批 -->
        <div id="ApprovalDiv" class="easyui-tabs" data-options="fit:true,border:true" >
          <!-- 为了控制在浏览器窗口变小时不出现两个纵向滚动条，需要给div加上overflow样式          zhangls    2016-01-21 -->
          <div  title="项目信息录入及管理" style="overflow-y: hidden; overflow-x: auto;" >
            <iframe id="ApprovalFrame" frameborder="0" style="width: 100%; height: 99.5%;"></iframe>
          </div>
          <div title="项目过程管理" selected >
            <iframe id="projectProcessFrame_1" frameborder="0" style="width: 100%; height: 99.5%;" ></iframe>
          </div>
        </div>
        
        <!-- 已审批 -->
        <div id="maintabs" class="easyui-tabs" data-options="fit:true,border:true,onSelect:selectTab" >
          <!-- 为了控制在浏览器窗口变小时不出现两个纵向滚动条，需要给div加上overflow样式          zhangls    2016-01-21 -->
          <div id="projectInfoDiv" title="项目信息录入及管理" style="overflow-y: hidden; overflow-x: auto;" >
            <iframe id="projectInfoFrame" scrolling="no" frameborder="0" style="width: 100%; height: 99.5%;"></iframe>
          </div>
          <div id="projectProcessDiv" title="项目过程管理" selected style="overflow:hidden">
            <iframe id="projectProcessFrame" scrolling="no" frameborder="0" style="width: 100%; height: 99.5%;" ></iframe>
          </div>
        </div>
        
      </div>

      <!-- 右侧 -->
      <!-- <div collapsed="true" region="east" iconCls="icon-reload" title="辅助工具" split="true" style="width: 193px;">
        <div data-options="region:'south'" style="height: 40px">
          <div class="easyui-panel" data-options="fit:true,border:false" style="text-align: center; vertical-align: middle;">
            <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick='add("添加提醒","tOWarnController.do?goCommonWarn&type=2");'>添加提醒</a>
            <span style="height: 100%; vertical-align: middle; display: inline-block;"></span>
          </div>
        </div>
      </div> -->

      <!-- 底部 -->
      <div region="south" border="false" style="height: 25px; overflow: hidden;BACKGROUND:url(plug-in/login/images/header-bg.png) rgb(60, 126, 216);">
        <div align="center" style="color: #fff; padding-top: 2px">
          &copy;
          <t:mutiLang langKey="common.copyright" />
          <span class="tip"> 海军工程大学科研部 </span>
        </div>
      </div>

      <div id="mm" class="easyui-menu" style="width: 150px;">
        <div id="mm-tabupdate">
          <t:mutiLang langKey="common.refresh" />
        </div>
      </div>
      <!-- tab页按钮 -->
      <div id="tab-tools" style="border-left: none;">
        <img src="plug-in/easyui/themes/default/images/layout_button_left.gif" style="cursor: pointer;vertical-align: middle;" onclick="treeCollapase()" />
        <span style="vertical-align:middle;height:100%;display:inline-block;"></span>
      </div>

       <div id="swithImg" data-options="region:'south'" style="display:none;height: 37px;border-style: solid none none none; padding-top: 2px; text-align: center; ">
         <span>
           <img src="plug-in/easyui/themes/icons/lxxs.png" id="lxxs" style="width: 43px;cursor:pointer;"  onclick="changeSortType(1)"></img>
         </span>
         <span>
           <img src="plug-in/easyui/themes/icons/year.png" id="year" style="width: 43px;cursor:pointer;"  onclick="changeSortType(2)"></img>
         </span>
         <span>
           <img src="plug-in/easyui/themes/icons/lb.png" id="lb" style="width: 43px;cursor:pointer;"  onclick="changeSortType(3)"></img>
         </span>
         <span>
           <img src="plug-in/easyui/themes/icons/xz.png" id="xz" style="width: 43px;cursor:pointer;"  onclick="changeSortType(4)"></img>
         </span>
       </div>
</body>
</html>