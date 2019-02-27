<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<%
String lang = org.jeecgframework.core.util.BrowserUtils.getBrowserLanguage(request);
String langurl = "plug-in/mutiLang/" + lang +".js";
%>

<html>
<head>
<title>欢迎使用科研管理信息化平台</title>
<link rel="shortcut icon" href="resources/fc/images/icon/favicon.ico">
<script src=<%=langurl%> type="text/javascript"></script>
<!--[if lt IE 9]>
   <script src="plug-in/login/js/html5.js"></script>
  <![endif]-->
<!--[if lt IE 7]>
  <script src="plug-in/login/js/iepng.js" type="text/javascript"></script>
  <script type="text/javascript">
	EvPNG.fix('div, ul, img, li, input'); //EvPNG.fix('包含透明PNG图片的标签'); 多个标签之间用英文逗号隔开。
</script>
  <![endif]-->
<!-- 
<link href="plug-in/login/css/zice.style.css" rel="stylesheet" type="text/css" />
<link href="plug-in/login/css/buttons.css" rel="stylesheet" type="text/css" />
<link href="plug-in/login/css/icon.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="plug-in/login/css/tipsy.css" media="all" />
-->
<link rel="stylesheet" type="text/css" href="plug-in/login/css/login.css" />
<!--<script type="text/javascript" src="../../plug-in/jquery/jquery-1.8.3.min.js"></script>
 <script type="text/javascript" src="login.js"></script>  -->


<style type="text/css">

.alertMessage,#alertMessage
{
	-webkit-background-size: 40px 40px;
	-moz-background-size: 40px 40px;
	background-size: 40px 40px;
	background-image: -webkit-gradient(linear, left top, right bottom,
								color-stop(.25, rgba(255, 255, 255, .05)), color-stop(.25, transparent),
								color-stop(.5, transparent), color-stop(.5, rgba(255, 255, 255, .05)),
								color-stop(.75, rgba(255, 255, 255, .05)), color-stop(.75, transparent),
								to(transparent));
	background-image: -webkit-linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
							transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
							transparent 75%, transparent);
	background-image: -moz-linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
							transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
							transparent 75%, transparent);
	background-image: -ms-linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
							transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
							transparent 75%, transparent);
	background-image: -o-linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
							transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
							transparent 75%, transparent);
	background-image: linear-gradient(135deg, rgba(255, 255, 255, .05) 25%, transparent 25%,
							transparent 50%, rgba(255, 255, 255, .05) 50%, rgba(255, 255, 255, .05) 75%,
							transparent 75%, transparent);
	-moz-box-shadow: inset 0 -1px 0 rgba(255,255,255,.4);
	-webkit-box-shadow: inset 0 -1px 0 rgba(255,255,255,.4);
	box-shadow: inset 0 -1px 0 rgba(255,255,255,.4);
	font-size:13px;
	text-transform:capitalize;
/*	font-weight:bold;*/
	border: 1px solid!important;
	color: #fff;
	padding: 15px;
	text-shadow: 0 1px 0 rgba(0,0,0,.5);
	-webkit-animation: animate-bg 5s linear infinite;
	-moz-animation: animate-bg 5s linear infinite;
	margin-bottom: 10px;
}
#alertMessage{
	position:fixed;
	/*	display:none;
	position: absolute;
	opacity:0;
	filter:alpha(opacity=0);*/
	right: -90px;
	text-transform: uppercase;
	bottom: 35px;
	z-index:100001;
	cursor:pointer;
	border: 1px solid;
	display: none;
	}

.alertMessage.info ,#alertMessage.info 
{
		 border-color: #3b8eb5!important;
	background-color:#C2E3F0;
	color:#34667A!important;
	text-shadow:1px 1px 1px #C8EBFB;
}

.alertMessage.error ,#alertMessage.error
{
	border-color: #c43d3d!important;
	background-color:#fc4a48;
	color:#5A0000 !important;
	text-shadow:1px 1px 1px #E64040;
}
.alertMessage.warning ,#alertMessage.warning 		 
{
		 border-color: #d99a36 !important;
	background-color:#fcdb72;
	color:#987402 !important;
	text-shadow:1px 1px 1px #e7b104;
}
.alertMessage.success ,#alertMessage.success 
{
		 background-color: #61b832;
		 border-color: #55a12c !important;
/*	color:#324e03 !important;*/
	text-shadow:1px 1px 1px #5c9201;
}

.alertMessage h3
{
		 margin: 0  !important;
		 border:none;
		line-height: 25px !important;
		color:#FFF;
}
.text_success {
	font-size:14px;
	text-align:center;
	margin:0px 0 35px 0;
	line-height:25px;
	position: absolute;
	left: 50%;
	width: 200px;
	top: 50%;
	z-index: 50;
	margin-left: -100px;
	text-transform: uppercase;
	padding: 20px 0px;
	margin-top: -100px;
	display: none;
}
.text_success img {
}
.text_success span {
	display: block;
	padding: 10px;
	color: #FFF;
}
/*
	loading layout
*/
#overlay {
	width:100%;
	height:100%;
	position:fixed;
	top:0;
	left:0;
	background-color:#111;
	/*	opacity:0.1;
	filter:alpha(opacity=10);*/
	z-index:1000;
	display: none;
}
#preloader {
	background: #000000 url(../images/preloader.gif) no-repeat 12px 10px;
	font-size: 11px;
	height: 20px;
	left: 50%;
	line-height: 20px;
	margin: -20px 0 0 -45px;
	padding: 10px;
	position: fixed;
	text-align: left;
	text-indent: 36px;
	top: 50%;
	width: 90px;
	z-index: 1209;
	opacity:0.8;
	filter:alpha(opacity=80);
	-moz-border-radius: 8px;
	-webkit-border-radius: 8px;
	border-radius: 8px;
	color: #FFF;
	text-shadow:none;
	display: none;
}
#pageload {
	background: #000000;
	font-size: 11px;
	height: 20px;
	left: 50%;
	line-height: 20px;
	margin: -20px 0 0 -55px;
	padding: 10px;
	position: fixed;
	text-align: center;
	top: 50%;
	width: 120px;
	z-index: 1006;
	opacity:0.8;
	filter:alpha(opacity=80);
	-moz-border-radius: 12px;
	-webkit-border-radius: 12px;
	border-radius: 12px;
	color: #FFF;
	text-shadow:none;
	display: none;
}

/*
html {
	background-image: none;
}

label.iPhoneCheckLabelOn span {
	padding-left: 0px
}

#versionBar {
	background-color: #212121;
	position: fixed;
	width: 100%;
	height: 35px;
	bottom: 0;
	left: 0;
	text-align: center;
	line-height: 35px;
	z-index: 11;
	-webkit-box-shadow: black 0px 10px 10px -10px inset;
	-moz-box-shadow: black 0px 10px 10px -10px inset;
	box-shadow: black 0px 10px 10px -10px inset;
}

.copyright {
	text-align: center;
	font-size: 10px;
	color: #CCC;
}

.copyright a {
	color: #A31F1A;
	text-decoration: none
}

.on_off_checkbox {
	width: 0px;
}

#login .logo {
	width: 500px;
	height: 51px;
}*/
</style>
</head>
<body>
    <div id="alertMessage"></div>
    <div id="successLogin"></div>
    <div class="text_success"><img src="plug-in/login/images/loader_green.gif" alt="Please wait" /> <span><t:mutiLang langKey="common.login.success.wait"/></span></div>
    
    <div id="login">
    
        <!-- 
        <div class="ribbon" style="background-image: url(plug-in/login/images/typelogin.png);"></div>
        <div class="inner">
            <div class="logo"><img src="plug-in/login/images/head.png" /><img src="plug-in/login/images/foot.png" /></div>
            <div class="formLogin">
                <form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
                    <input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
                    <div class="tip">
                        <input class="userName" name="userName" type="text" id="userName" title="" iscookie="true" value="admin" nullmsg="" />
                    </div>
                    <div class="tip">
                        <input class="password" name="password" type="password" id="password" title="" value="123456" nullmsg="" />
                    </div>
                    <%--update-begin--Author:zhangguoming  Date:20140226 for：添加验证码--%>
                    <div>
                        <div style="float: right; margin-left:-150px; margin-right: 30px;">
                            <img id="randCodeImage" src="randCodeImage" />
                        </div>
                        <input class="randCode" name="randCode" type="text" id="randCode" title="" value="" nullmsg="" />
                    </div>
                    <%--update-end--Author:zhangguoming  Date:20140226 for：添加验证码--%>
                   
                    <div class="loginButton">
                        <div style="float: left; margin-left: -9px;">
                            <input type="checkbox" id="on_off" name="remember" checked="ture" class="on_off_checkbox" value="0" />
                            <span class="f_help"><t:mutiLang langKey="common.remember.user"/></span>
                        </div>                        
                        <div style="float: right; padding: 3px 0; margin-right: -12px;">
                            <div>
                                <ul class="uibutton-group">
                                    <li><a class="uibutton normal" href="#" id="but_login"><t:mutiLang langKey="common.login"/></a></li>
                                    <li><a class="uibutton normal" href="#" id="forgetpass"><t:mutiLang langKey="common.reset"/></a></li>
                                </ul>
                            </div>
                            <%-- 
                            <div style="float: left; margin-left: 30px;"><a href="init.jsp"><span class="f_help"><t:mutiLang langKey="common.init.data"/></span></a></div>
                            --%>
                            <%--update-begin--Author:ken  Date:20140629 for：添加语言选择--%>
                            <br>
                            <div  style="display:none;">                         
                                <t:dictSelect id="langCode" field="langCode" typeGroupCode="lang" hasLabel="false" defaultVal="zh-cn"></t:dictSelect>
                            </div>
                            <%--update-begin--Author:ken  Date:20140629 for：添加语言选择--%>
                        </div>
                    </div>
                </form>
            </div>
       
      <div style="position: relative; margin:auto;width: 500px;">
        <table style="line-height: 150%;width: 100%;">
          <tr>
            <td ><font color="red"><a href="./expertLoginController.do?login" target="_blank">点击进入【专家评审系统】</a></font></td>
            <td style="text-align: right;"><font color="red"><a href="./priceLoginController.do?login" target="_blank">点击进入【价格库系统】</a></font></td>
          </tr>
          <tr>
            <td><font color="red"><a href="./webpage/login/plugin.jsp" target="_blank">插件下载</a></font></td>
            <td style="text-align: right;"><font color="red"><a href="./webpage/login/doc.jsp" target="_blank">文档下载</a></font></td>
          </tr>
        </table>
      </div>
    </div>-->   
    
	    <div class="minPart">
	    	<form name="formLogin" id="formLogin" action="loginController.do?login" check="loginController.do?checkuser" method="post">
                <input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
			    <div class="loginBlock">
			        <div class="inputDiv inputDiv-account">
			            <input  name="userName" type="text" id="userName" title="" iscookie="true"  value="用户名 Username"  nullmsg="" 
			             type="text" onFocus="if(this.value=='用户名 Username') {this.value=''};this.style.color='#fff';" onBlur="if(this.value=='') {this.value='用户名 Username';this.style.color='#96bae8';}" style="color:#96bae8;"/>
			        </div>
			        <div class="inputDiv inputDiv-password" style="top:100px;">
			            <input name="password" type="text" id="password" title="" value="密码 Password"  nullmsg="" style="color:#96bae8;" />
			        </div>
					<div class="inputDiv inputDiv-identify" style="top:150px;">
                        <input class="randCode" name="randCode" type="text" id="randCode" title="" value="" nullmsg="" style="color:#fff;" />
			        </div>
					<img class="img-identify" id="randCodeImage"  src="randCodeImage"/>
					<input type="hidden" id="on_off" name="remember" value="0" />
					<div class="rememberaccout"><span class="rememberaccout-active fn-rememberaccout">记住用户名</span></div>
			        <div class="loginBtn-outer" id="but_login"><a class="loginBtn">登&nbsp;录</a></div>
			        <div  style="display:none;">                         
                                <t:dictSelect id="langCode" field="langCode" typeGroupCode="lang" hasLabel="false" defaultVal="zh-cn"></t:dictSelect>
                    </div>
			        <!--<div class="forgetPassword"><a>重置密码</a></div>    -->        
			    </div>
		    </form>
		</div>
		
		
        <div class="shadow"></div>
    </div>
    
    <div class="btn-group">
			<a class="btn-group-item1" href="./expertLoginController.do?login" target="_blank">专家评审系统</a>	
			<a class="btn-group-item1" href="./priceLoginController.do?login" target="_blank">价格库系统</a>	
			<a class="btn-group-item2" href="./webpage/login/plugin.jsp" target="_blank">插件下载</a>	
			<a class="btn-group-item2" href="./webpage/login/doc.jsp" target="_blank">文档下载</a>	
	 </div>
    <!--Login div-->
    <div class="clear"></div>
    <!-- 
    <div id="versionBar">
        <div class="copyright">&copy; <t:mutiLang langKey="common.copyright"/> <span class="tip">海军工程大学科研部<t:mutiLang langKey="common.browser.recommend"/></span></div>
    </div> -->
    
    <div class="login-copyright">
	    <div class="login-copyright-inner">
	        <font class="login-copyright-inner-font">海军工程大学&nbsp;&nbsp;版权所有</font>
	    </div>
	</div>
    
    <!-- Link JScript-->
    <script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
   
    <script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery-jrumble.js"></script>
    <script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>
    <script type="text/javascript" src="plug-in/login/js/iphone.check.js"></script>
    <script type="text/javascript" src="plug-in/login/js/login_init.js"></script>
    <script type="text/javascript" src="plug-in/login/js/login.js"></script>
    <script type="text/javascript" src="plug-in/lhgDialog/lhgdialog.min.js"></script>
</body>
</html>