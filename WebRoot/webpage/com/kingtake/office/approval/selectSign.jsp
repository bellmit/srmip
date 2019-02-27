<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>选择印章</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  function selectSign(){
     var sign = $("#sign").val();
     if(sign==""){
         tip("请先选择一个印章.");
         return;
     }
     window.opener.addSign(sign);
     window.close();
  }
  
 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tOApprovalController.do?send"  callback="@Override submitParent" btnsub="saveBtn" > 
		<table  cellpadding="0" cellspacing="1" class="formtable" style="margin-top:20px;line-height: 30px;font-size: 50px;">
				<tr>
					<td align="right" >
						<label class="Validform_label">
							请选择印章:<font color="red">*</font>
						</label>
					</td>
					<td class="value" >
						<select id="sign">
                           <c:forEach items="${signList}" var="sign">
                           <option value="${sign.esppath}">${sign.name}</option>
                           </c:forEach>
                        </select>
					</td>
				</tr>
			    <tr>
					<td align="right">
						<input type="button" value="确定" id="saveBtn" onclick="selectSign()">
					</td>
					<td></td>
					</tr>
			</table>
		</t:formvalid>
 </body>
