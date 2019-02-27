<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" import="com.kingtake.common.constant.ProjectConstant" 
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
<% 
TSUser user = ResourceUtil.getSessionUserName();
String sessionId = request.getSession().getId();
String id = "";
if(user!=null){
    id = user.getId();
}
%>
var id="<%=id %>";
var sessionId="<%=sessionId %>";
</script>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div style="width: 100%;height: 300px;">
	<a href="" id="test" style="color:#f90;font-weight: 900;font-size: 20px;">请点击此处</a>
</div>
<script>
$('#test').on('click', function() {
	$(this).attr("href","thunder:?id="+id+"&sessionId="+sessionId+"");
});
</script>