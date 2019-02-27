<%@ page language="java" import="java.util.*,com.kingtake.common.constant.DepartConstant" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>论文保密审查编号设置</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
    //获取论文保密审查编号
    function getSecretCode() {
	     return $("#secretCode").val();
    }
    
    function closeDialog(){
        frameElement.api.close();
    }
</script>
</head>
<body>
  <label class="Validform_label"> 论文保密审查编号: </label>
  <input id="secretCode" name="secretCode" type="text" value="${secretCode}">
</body>
</html>
