<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>Excel导入</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	
	function uploadSuccess2(d, file, response){
		window.tip(d.msg);
		window.close();
	}
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
		<form action="dbImportController.do?importXml"
			  enctype="multipart/form-data" method="POST">
			<input type="file" name="file1" />
			<input type="submit" value="提交" />
		</form>

</body>
</html>
