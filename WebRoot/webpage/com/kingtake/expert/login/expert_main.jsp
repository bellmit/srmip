<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
<head>
<title><t:mutiLang langKey="srmip.platform"/></title>
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
</style>
<script type="text/javascript">
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
    		    $('#expertHome').attr('src', $('#expertHome').attr('src'));
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
                if (t != '专家评审首页') {
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
    
    function createFrame(url) {
    	var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:99.5%;overflow-y:hidden;"></iframe>';
    	return s;
    }
</script>
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
                    <span style="color: #386780">当前登录用户:</span> <span
                      style="color: #FFFFFF">${expert.userName }</span>&nbsp;&nbsp;&nbsp;&nbsp; 
                  </div>
                  <div style="float: left; margin-left: 18px;">
                    <div style="right: 0px; bottom: 0px;">
                      <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu"
                        iconCls="icon-comturn" style="color: #FFFFFF"> <t:mutiLang langKey="common.control.panel" />
                      </a>&nbsp;&nbsp; <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu"
                        iconCls="icon-exit" style="color: #FFFFFF"> <t:mutiLang langKey="common.logout" />
                      </a>
                    </div>
                    <div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
                      <div onclick="createwindow('专家信息','tErExpertController.do?id=${expert.id}&goUpdate&load=detail','100%','100%')">
                        <t:mutiLang langKey="common.profile" />
                      </div>
                      <div class="menu-sep"></div>
                      <div
                        onclick="add('<t:mutiLang langKey="common.change.password"/>','expertLoginController.do?changepassword','',500,150)">
                        <t:mutiLang langKey="common.change.password" />
                      </div>
                      <%-- <div class="menu-sep"></div>
                      <div onclick="add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle')">
                        <t:mutiLang langKey="common.change.style" />
                      </div> --%>
                    </div>
                    <div id="layout_north_zxMenu" style="width: 100px; display: none;">
                      <div onclick="exit('expertLoginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);">
                        <t:mutiLang langKey="common.exit" />
                      </div>
                    </div>
                  </div>
                  <div style="float: left; margin-left: 8px; margin-right: 5px; margin-top: 5px;">
                    <img src="plug-in/easyui/themes/default/images/layout_button_up.gif" style="cursor: pointer"
                      onclick="panelCollapase()" />
                  </div>
                </div>
              </td>
            </tr>
            <tr style="height: 80px;">
              <td colspan="2">
                <ul class="shortcut">
                  <!-- 动态生成并赋值过来 -->
                </ul>
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
     <div title="专家评审首页" style="padding: 2px; overflow: hidden;">
        <iframe id="expertHome" scrolling="auto" frameborder="0" src="expertLoginController.do?goExpertReviewMainListForExpert" style="width:100%;height:100%;"></iframe>
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