<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Exception!</title>

<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
function showMsg(){
   $("#detail").toggle("slow"); 
}
</script>
<style>
.title3 {
  font-size: 14px;
  line-height: 180%;
  color: #999999;
  font-family:"Microsoft YaHei", "微软雅黑",;
}
</style>
</head>
<body>
<img alt="发生异常" src="images/alert_icon.png" style="width:100px;height:100px;"></img>
<% Exception e = (Exception)request.getAttribute("ex"); %>
<H2>错误异常: <%= e.getClass().getSimpleName()%></H2>
<hr />
<P>错误描述：</P>
<font color="red"><%= e.getMessage()%></font>
<p><a href="#" onclick="showMsg()" class="title3" >查看详细信息</a></p>
<p id="detail" style="display:none;margin-left: 5px;">
<font color="red"><% e.printStackTrace(new java.io.PrintWriter(out)); %></font>
</p>
</body>
</html>