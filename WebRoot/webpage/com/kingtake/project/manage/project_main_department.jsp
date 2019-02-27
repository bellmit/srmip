<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title>机关项目管理</title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
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
.shadow
{ 
  text-shadow: 
  1px 1px 0 #CCC, 
  2px 2px 0 #CCC, /* end of 2 level deep grey shadow */ 
  3px 3px 0 #444, 
  4px 4px 0 #444, 
  5px 5px 0 #444, 
  6px 6px 0 #444; /* end of 4 level deep dark shadow */ 
} 
</style>
<SCRIPT type="text/javascript">
    $(function() {
       
        tabClose();//监听tab上菜单事件
        tabCloseEven();

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
    		var url = $(currTab.panel('options').content).attr('src');
    		if(url==undefined){
    		    $('#projectHome').attr('src', $('#projectHome').attr('src'));
    		}else{
    		  $('#maintabs').tabs('update', {
    			tab : currTab,
    			options : {
    				content : createFrame(url)
    			}
    		  });
    		}
    	});
    	// 关闭当前
    	$('#mm-tabclose').click(function() {
    		var currtab_title = $('#mm').data("currtab");
    		$('#maintabs').tabs('close', currtab_title);
    	});
    	// 全部关闭
    	$('#mm-tabcloseall').click(function() {
    		$('.tabs-inner span').each(function(i, n) {
    			var t = $(n).text();
                if (t != '项目首页') {
                    $('#maintabs').tabs('close', t);
                }
    		});
    	});
    	// 关闭除当前之外的TAB
    	$('#mm-tabcloseother').click(function() {
    		$('#mm-tabcloseright').click();
    		$('#mm-tabcloseleft').click();
    	});
    	// 关闭当前右侧的TAB
    	$('#mm-tabcloseright').click(function() {
    		var nextall = $('.tabs-selected').nextAll();
    		if (nextall.length == 0) {
    			return false;
    		}
    		nextall.each(function(i, n) {
    			var t = $('a:eq(0) span', $(n)).text();
    			$('#maintabs').tabs('close', t);
    		});
    		return false;
    	});
    	// 关闭当前左侧的TAB
    	$('#mm-tabcloseleft').click(function() {
    		var prevall = $('.tabs-selected').prevAll();
    		if (prevall.length == 0) {
    			return false;
    		}
    		prevall.each(function(i, n) {
    			var t = $('a:eq(0) span', $(n)).text();
                if (t != '首页') {
                    $('#maintabs').tabs('close', t);
                }
    		});
    		return false;
    	});

    	// 退出
    	$("#mm-exit").click(function() {
    		$('#mm').menu('hide');
    	});
    }
    
    function closeWebPage(){
        window.close();
    }
    
    function mouseover(){
        $("#btn").find("span").css("color","black");
    }

    function mouseout(){
        $("#btn").find("span").css("color","#FFFFFF");
    }
    
    function createFrame(url) {
    	var s = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:99.5%;overflow-y:hidden;"></iframe>';
    	return s;
    }
</SCRIPT>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
  <!-- 顶部-->
  <div region="north" border="false" title=""
    style="BACKGROUND: #A8D7E9; height: 100px; padding: 1px; overflow: hidden;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" style="vertical-align: text-bottom"><img src="plug-in/login/images/logo_new.jpg"> <!--
            <img src="plug-in/login/images/toplogo.png" width="550" height="52" alt="">
        <div style="position: absolute; top: 75px; left: 33px;">JEECG Framework <span style="letter-spacing: -1px;">3.4.3 GA</span></div>-->
        </td>
        <td align="right" nowrap>
          <table border="0" cellpadding="0" cellspacing="0">
            <tr style="height: 25px;" align="right">
              <td style="" colspan="2">
                <div style="background: url(plug-in/login/images/top_bg.jpg) no-repeat right center; float: right;">
                  <div style="float: left; line-height: 25px; margin-left: 70px;">
                    <span style="color: #386780"><t:mutiLang langKey="common.user" />:</span> <span
                      style="color: #FFFFFF">${userName }</span>&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: #386780"><t:mutiLang langKey="current.org" />:</span> <span
                      style="color: #FFFFFF">${currentOrgName }</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
                      style="color: #386780"><t:mutiLang langKey="common.role" />:</span> <span style="color: #FFFFFF">${roleName }</span>
                  </div>
                  <div style="float: left; margin-left: 18px;">
                    <a id="btn" href="#" class="easyui-linkbutton" title="关闭机关页" data-options="iconCls:'icon-no',plain:true" onclick="closeWebPage()" onmouseover="mouseover()" onmouseout="mouseout()"><span style="color: #FFFFFF">关闭</span></a>
                  </div>
                  <div style="float: left; margin-left: 8px; margin-right: 5px; margin-top: 5px;">
                    <img src="plug-in/easyui/themes/default/images/layout_button_up.gif" style="cursor: pointer"
                      onclick="panelCollapase()" />
                  </div>
                </div>
              </td>
            </tr>
            <tr style="height: 80px; ">
              <td colspan="2" style="border：1px solid;">
                <span  class="shadow" style="word-spacing:20px;font:italic bold 40px/30px 'Times New Roman',arial,sans-serif;color: #8400FF;"> 机 关 项 目 管 理  </span>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <!-- 中间-->
  <div id="mainPanle" region="center" style="overflow: hidden;">
    <div id="maintabs" class="easyui-tabs" fit="true" border="false">
     <div title="项目首页" style="padding: 2px; overflow: hidden;">
        <iframe id="projectHome" scrolling="yes" frameborder="0"  src="projectMgrController.do?projectMgrForDepartment" style="width:100%;height:100%;"></iframe>
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
            onclick='add("添加提醒","tOWarnController.do?goCommonWarn&type=3");'>添加提醒</a><span
            style="height: 100%; vertical-align: middle; display: inline-block;"></span>
        </div>
      </div>
  </div>
  
  <!-- 底部 -->
  <div region="south" border="false" style="height: 25px; overflow: hidden;">
    <div align="center" style="color: #1fa3e5; padding-top: 2px">
      &copy;
      <t:mutiLang langKey="common.copyright" />
      <span class="tip"> 海军工程大学科研部 </span>
    </div>
  </div>

  <div id="mm" class="easyui-menu" style="width: 150px;">
    <div id="mm-tabupdate">
      <t:mutiLang langKey="common.refresh" />
    </div>
    <div id="mm-tabclose">
      <t:mutiLang langKey="common.close" />
    </div>
    <div id="mm-tabcloseall">
      <t:mutiLang langKey="common.close.all" />
    </div>
    <div id="mm-tabcloseother">
      <t:mutiLang langKey="common.close.all.but.this" />
    </div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright">
      <t:mutiLang langKey="common.close.all.right" />
    </div>
    <div id="mm-tabcloseleft">
      <t:mutiLang langKey="common.close.all.left" />
    </div>
  </div>
</body>
</html>