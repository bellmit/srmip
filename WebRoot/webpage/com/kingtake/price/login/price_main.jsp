<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jeecgframework.core.util.SessionKeylogin,org.jeecgframework.core.util.ResourceUtil" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title><t:mutiLang langKey="srmip.platform"/></title>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<%
    String strSessionKey = "";
    String user = (String)request.getAttribute("userName");
    if (null != user && !"".equals(user)) {
        SessionKeylogin login = new SessionKeylogin();
        strSessionKey = login.getSessionKey(user);
    }
    String serverIp = ResourceUtil.getConfigByName("rtx.server.ip");
%>
<link rel="shortcut icon" href="images/favicon.ico">
<style type="text/css">
a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: black;
	text-decoration: none;
}
/*update-start--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色*/
.tree-node-selected {
	background: #eaf2ff;
}
/*update-end--Author:zhangguoming  Date:20140622 for：左侧树调整：加大宽度、更换节点图标、修改选中颜色*/
</style>
<SCRIPT type="text/javascript">
    $(function() {
        $('#layout_jeecg_onlineDatagrid').datagrid(
                {
                    url : 'systemController.do?datagridOnline&field=ip,logindatetime,user.userName,user.realName,',
                    title : '',
                    iconCls : '',
                    fit : true,
                    fitColumns : true,
                    pagination : true,
                    pageSize : 10,
                    pageList : [ 10 ],
                    nowarp : false,
                    border : false,
                    idField : 'id',
                    sortName : 'logindatetime',
                    sortOrder : 'desc',
                    frozenColumns : [ [ {
                        title : '<t:mutiLang langKey="common.code"/>',
                        field : 'id',
                        width : 150,
                        hidden : true
                    } ] ],
                    columns : [ [ {
                        title : '<t:mutiLang langKey="common.login.name"/>',
                        field : 'user.realName',
                        width : 130,
                        align : 'center',
                        sortable : true,
                        formatter : function(value, rowData, rowIndex) {
                            return formatString('<span title="{0}">{1}</span>', value, value);
                        }
                    }, {
                        title : 'IP',
                        field : 'ip',
                        width : 150,
                        align : 'center',
                        sortable : true,
                        formatter : function(value, rowData, rowIndex) {
                            return formatString('<span title="{0}">{1}</span>', value, value);
                        }
                    }, {
                        title : '<t:mutiLang langKey="common.login.time"/>',
                        field : 'logindatetime',
                        width : 150,
                        sortable : true,
                        formatter : function(value, rowData, rowIndex) {
                            return formatString('<span title="{0}">{1}</span>', value, value);
                        },
                        hidden : true
                    } ] ],
                    onClickRow : function(rowIndex, rowData) {
                    },
                    onLoadSuccess : function(data) {
                        $('#layout_jeecg_onlinePanel').panel('setTitle',
                                '( ' + data.total + ' )' + ' <t:mutiLang langKey="lang.user.online"/>');
                    }
                }).datagrid('getPager').pagination({
            showPageList : false,
            showRefresh : false,
            beforePageText : '',
            afterPageText : '/{pages}',
            displayMsg : ''
        });

        $('#layout_jeecg_onlinePanel').panel({
            tools : [ {
                iconCls : 'icon-reload',
                handler : function() {
                    $('#layout_jeecg_onlineDatagrid').datagrid('load', {});
                }
            } ]
        });

        $('#layout_east_calendar').calendar({
            fit : true,
            current : new Date(),
            border : false,
            onSelect : function(date) {
                $(this).calendar('moveTo', new Date());
            }
        });
        $(".layout-expand").click(
                function() {
                    $('#layout_east_calendar').css("width", "auto");
                    $('#layout_east_calendar').parent().css("width", "auto");
                    $("#layout_jeecg_onlinePanel").find(".datagrid-view").css("max-height", "200px");
                    $("#layout_jeecg_onlinePanel .datagrid-view .datagrid-view2 .datagrid-body").css("max-height",
                            "180px").css("overflow-y", "auto");
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
    function getMessage() {
        $.ajax({
            url : 'tOMessageController.do?getSystemMessage',
            type : 'POST',
            data : {},
            success : function(data) {
               var msgjson = JSON.parse(data);
                 if (msgjson.attributes != null) {
//                             $.dialog.setting.zIndex = 1980;
                            $.dialog({
                                id: 'msg',
                                title: '您有新的消息！',
                                content: msgjson.attributes.CONTENT,
                                width: 330,
                                height: 100,
                                left: '100%',
                                top: '100%',
                                //time: 10,
                                min:false,
                                fixed: true,
                                drag: false,
                                resize: false,
                                close: function(){
                                	$.ajax({
                                		url : 'tOMessageController.do?changeMessageShow',
                                        type : 'POST',
                                        data : {'rid':msgjson.attributes.RID},
                                        success : function(data) {
                                        	
                                        }
                                	});
                                }
                            });
                    }
                 if(msgjson.obj>0){
                    $("#bell").attr("src","images/bell1.gif");
                 }else{
                    $("#bell").attr("src","images/bell.gif");
                 }
                 $('#msgdiv').html('(' + msgjson.obj + ')');
                //window.location.reload();
            },
            //请求失败遇到异常触发
            error : function(xhr, errorInfo, ex) {
//             	alert(errorInfo);
//                 show.append('error invoke!errorInfo:' + errorInfo + '<br/>');
            },

            //是否使用异步发送
            async : true
        });
    }
    window.setInterval('getMessage()',10000);
</SCRIPT>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
  <!-- 顶部-->
  <div region="north" border="false" title=""
    style="BACKGROUND: #A8D7E9; height: 100px; padding: 1px; overflow: hidden;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td align="left" style="vertical-align: text-bottom"><img src="plug-in/login/images/priceLogo.png"> <!--
            <img src="plug-in/login/images/toplogo.png" width="550" height="52" alt="">
        <div style="position: absolute; top: 75px; left: 33px;">JEECG Framework <span style="letter-spacing: -1px;">3.4.3 GA</span></div>-->
        </td>
        <td align="right" nowrap>
          <table border="0" cellpadding="0" cellspacing="0">
            <tr style="height: 25px;" align="right">
              <td style="" colspan="2">
                <div style="background: url(plug-in/login/images/top_bg.jpg) no-repeat right center;float: right;">
                  <div style="float: left; line-height: 25px; margin-left: 70px;">
                    <span style="color: #386780;font-size:15px;"><t:mutiLang langKey="common.user" />:</span> <span
                      style="color: #FFFFFF;font-size:15px;">${realName }</span>&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: #386780;font-size:15px;"><t:mutiLang langKey="current.org" />:</span> <span
                      style="color: #FFFFFF;font-size:15px;">${currentOrgName}</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
                      style="color: #386780;font-size:15px;"><t:mutiLang langKey="common.role" />:</span> <span style="color: #FFFFFF;font-size:15px;"><c:if test="${fn:length(roleName)>10 }"><span title="${roleName}">${fn:substring(roleName,0,10)}...</span></c:if><c:if test="${fn:length(roleName)<=10 }">${roleName}</c:if></span>
                  </div>
                  <div style="float: left; margin-left: 18px;">
                     <div style="right: 0px; bottom: 0px;">
                      <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu"
                        iconCls="icon-comturn" style="color: #FFFFFF;"> <span style="font-size:15px;"><t:mutiLang langKey="common.control.panel" /></span>
                      </a>&nbsp;&nbsp; <a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_zxMenu"
                        iconCls="icon-exit" style="color: #FFFFFF;font-size:15px;"> <span style="font-size:15px;"><t:mutiLang langKey="common.logout" /></span>
                      </a>
                    </div> 
                    <div id="layout_north_kzmbMenu" style="width: 100px; display: none;font-size:15px;">
                      <div onclick="openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')">
                        <t:mutiLang langKey="common.profile" />
                      </div>
                      <div class="menu-sep"></div>
                      <div
                        onclick="add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword',null,500,150)">
                        <t:mutiLang langKey="common.change.password" />
                      </div>
                      <%-- <div class="menu-sep"></div>
                      <div onclick="add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle')">
                        <t:mutiLang langKey="common.change.style" />
                      </div> --%>
                    </div>
                    <div id="layout_north_zxMenu" style="width: 100px; display: none;font-size:15px;">
                      <div onclick="exit('loginController.do?logout','<t:mutiLang langKey="common.exit.confirm"/>',1);">
                        <t:mutiLang langKey="common.exit" />
                      </div>
                    </div>
                  </div>
                  <%--update-begin--Author:JueYue  Date:20140616 for：首页上方可以折叠--%>
                  <div style="float: left; margin-left: 8px; margin-right: 5px; margin-top: 5px;">
                    <img src="plug-in/easyui/themes/default/images/layout_button_up.gif" style="cursor: pointer"
                      onclick="panelCollapase()" />
                  </div>
                  <%--update-end--Author:JueYue  Date:20140616 for：首页上方可以折叠--%>
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
    <!-- rtx专用 -->
    <div style="display:none">
      <input type="hidden" id="strSessionKey" value="<%=strSessionKey%>">
      <input type="hidden" id="userName" value="${userName}">
      <input type="hidden" id="serverIp" value="<%=serverIp%>">
      <OBJECT classid=clsid:5EEEA87D-160E-4A2D-8427-B6C333FEDA4D id=RTXAX></OBJECT>
    </div>
  </div>
  <!-- 左侧-->
  <div id="westDiv" region="west" split="true" href="priceLoginController.do?shortcut_top"
    title="<t:mutiLang langKey="common.navegation"/>" style="width: 200px; padding: 1px;"></div>
  <!-- 中间-->
  <div id="mainPanle" region="center" style="overflow: hidden;">
    <div id="maintabs" class="easyui-tabs" fit="true" border="false">
      <div class="easyui-tab" title="<t:mutiLang langKey="common.dash_board"/>" href="loginController.do?home"
        style="padding: 2px; overflow: hidden;"></div>
      <c:if test="${map=='1'}">
        <div class="easyui-tab" title="<t:mutiLang langKey="common.map"/>" style="padding: 1px; overflow: hidden;">
          <iframe name="myMap" id="myMap" scrolling="no" frameborder="0" src="mapController.do?map"
            style="width: 100%; height: 99.5%;"></iframe>
        </div>
      </c:if>
    </div>
  </div>
  <!-- 右侧 -->
  <div collapsed="true" region="east" iconCls="icon-reload" title="<t:mutiLang langKey="common.assist.tools"/>"
    split="true" style="width: 193px;"
    data-options="onCollapse:function(){easyPanelCollapase()},onExpand:function(){easyPanelExpand()}">
    <div id="toolContainer" class="easyui-layout" data-options="fit:'true'">
      <div data-options="region:'north'" style="height:171px">
        <div id="tabs" class="easyui-tabs" border="false" style="height:169px">
          <div title='<t:mutiLang langKey="common.calendar"/>' style="padding: 0px; overflow: hidden; color: red;">
            <div id="layout_east_calendar"></div>
          </div>
        </div>
      </div>
      <div data-options="region:'center'">
        <div id="layout_jeecg_onlinePanel" data-options="fit:true,border:true"
          title=<t:mutiLang langKey="common.online.user"/>>
          <table id="layout_jeecg_onlineDatagrid"></table>
        </div>
      </div>
      <!-- <div data-options="region:'south'" style="height: 40px;">
        <div class="easyui-panel" data-options="fit:true,border:false"
          style="text-align: center; vertical-align: middle;">
          <a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
            onclick='add("添加提醒","tOWarnController.do?goCommonWarn&type=1");'>添加提醒</a><span
            style="height: 100%; vertical-align: middle; display: inline-block;"></span>
        </div>
      </div> -->
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
  <script type="text/javascript">
            
        </script>
</body>
</html>