<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
ul li{
list-style: none;
margin-top: 10px;
}

li label{
width: 150px;
float:left;
}
</style>
<title>专利奖励录入</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
  <div style="margin: 0 auto; width: 400px;">
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tZZljlController.do?doAdd" tiptype="1">
  <input type="hidden" id="ids" name="ids">
    <ul>
    <li><label><b style="color:red">专利类型</b></label><label><b style="color:red">奖励金额</b></label></li>
    <c:forEach items="${detailList}" var="detail">
    <li><label>${detail.name}:</label><input type="text" name="code_${detail.code}" class="easyui-numberbox" style="width: 150px;" data-options="min:0,max:999999999,groupSeparator:',',value:0"></li>
    </c:forEach>
    </ul>
    </t:formvalid>
  </div>
</body>
<script type="text/javascript">
    $(function(){
        var ids = frameElement.api.data;
        $("#ids").val(ids);
    });
</script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>