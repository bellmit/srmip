<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>收文阅批单发送</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!--   <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script> -->
<!--   <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script> -->
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOReceiveBillController.do?doback&rid=${id}"> 
		<table  cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							系统消息:
						</label>
					</td>
					<td class="value">
					     	 <textarea id="backMessage" name="backMessage"  datatype="*1-100"  style="width: 300px;" rows="5" class="inputxt" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">系统消息</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
