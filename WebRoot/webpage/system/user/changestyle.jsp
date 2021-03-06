<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>修改首页样式</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" refresh="false" dialog="true" action="userController.do?savestyle" layout="table" callback="refreshMainpage">
	<table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
		<tbody>
			<tr>
				<td class="value"><input type="radio" value="default" name="indexStyle" id="classicStyle"/> <label for="classicStyle" >经典风格</label></td>
			</tr>
			<!--
			<tr>
				<td class="value"><input type="radio" value="bootstrap" name="indexStyle" /> <span>BootStrap风格</span></td>
			</tr>
			-->
			<!-- update-start--Author:gaofeng  Date:2014-01-10 for:新增首页风格  -->
			<tr>
				<td class="value"><input type="radio" value="shortcut" name="indexStyle" id="shortcutStyle"/> <label for="shortcutStyle" >ShortCut风格</label></td>
			</tr>
			<!-- update-start--Author:gaofeng  Date:2014-01-24 for:新增首页风格  -->
			<tr>
				<td class="value"><input type="radio" value="sliding" name="indexStyle" id="slidingStyle"/><label for="slidingStyle">Sliding云桌面</label></td>
			</tr>
			<!-- update-end--Author:gaofeng  Date:2014-01-24 for:新增首页风格  -->	
		</tbody>
	</table>
</t:formvalid>
</body>
<script type="text/javascript">
		$(function(){
			var val = "${indexStyle}";
			$("input[name='indexStyle']").each(function(){
				if($(this).val()==val){
					$(this).attr("checked",true);
					return false;
				}
			});
		});
	</script>
</html>
