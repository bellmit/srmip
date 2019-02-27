<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>价款计算书：封面</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  
<style type="text/css">
*{padding:0; margin:0}
.tdLabel{
	font-size:20px;
	color:#5E7595;
	font-weight: bold;
	height:60px;
	line-height:60px;
}
.tdInput{
	font-size:20px;
	width:300px;
	height:30px;
	line-height:30px;
}
.divLabel{
	font-size:14px;
	color:#5E7595;
	font-weight: bold;
	height:20px;
	line-height:20px;
}
.divInput{
	font-size:14px;
	width:150px;
	height:20px;
	line-height:20px;
}
</style>
</head>
 
<body>
<div>
<input type="hidden" id="read" value="${read }"/>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
	btnsub="saveBtn" callback="@Override saveAfter" 
	action="tPmContractPriceCoverController.do?doUpdate" tiptype="1">
	<input type="hidden" id="id" name="id" value="${cover.id }" />
	
	<div style="height:30px; line-height:30px; margin-right:20px;" align="right">
		<label class="divLabel">密级:</label><font color="red">*</font>
		<t:codeTypeSelect id="secrityGrade" name="secrityGrade" type="select" codeType="0"
		     		code="XMMJ" defaultVal="${cover.secrityGrade }"></t:codeTypeSelect>
	</div>
	<div style="height:30px; line-height:30px; margin-right:20px;" align="right">
		<label class="divLabel">编号:</label>
		<input id="serialNumber" name="serialNumber" type="text" class="divInput" 
			value='${cover.serialNumber}' maxlength="10">
	</div>
	
	<p style="padding:0 0 10px 0; text-align:center; border-bottom:1px solid #ccc;
		font-size: 30px; color:#5E7595; font-weight: bold;">${title }计算书</p>
	
	<table cellpadding="0" cellspacing="0" style="width:100%;">
		<tr>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">项目名称:</label>
			</td>
			<td colspan="3">
		     	<input id="projectName" type="text" class="tdInput" style="width:800px"
		     		value='${project.projectName}' readonly="readonly">
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">合同名称:</label>
			</td>
			<td colspan="3">
				<input id="contractId" name="contractId" type="hidden" 
					value='${cover.contractId}'>
		     	<input id="contract" name="contract" type="text" class="tdInput" style="width:800px"
		     		value='${cover.contract}' readonly="readonly">
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">合同甲方:</label>
			</td>
			<td>
	     	 	<input id="contractPartyA" name="contractPartyA" type="text" 
	     	 		class="tdInput" value='${cover.contractPartyA}' maxlength="50">
			</td>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">合同乙方:</label>
			</td>
			<td>
	     	 	<input id="contractPartyB" name="contractPartyB" type="text" 
	     	 		class="tdInput" value='${cover.contractPartyB}' maxlength="50">
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">报价人姓名:</label>
			</td>
			<td>
				<input id="priceUserid" name="priceUserid" type="hidden" 
					value='${cover.priceUserid}'>
			    <input id="priceUsername" name="priceUsername" type="text" 
			    	class="tdInput" value='${cover.priceUsername}' maxlength="25">
			</td>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">报价时间:</label>
			</td>
			<td>
			  	<input id="priceDate" name="priceDate" type="text" class="Wdate tdInput" 
			  		onClick="WdatePicker()"
   					value='<fmt:formatDate value='${cover.priceDate}' type="date" 
   					pattern="yyyy-MM-dd"/>'>    
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">审核人姓名:</label>
			</td>
			<td>
				<input id="auditUserid" name="auditUserid" type="hidden" 
					value='${cover.auditUserid}'>
		     	<input id="auditUsername" name="auditUsername" type="text" 
		     		class="tdInput" value='${cover.auditUsername}' maxlength="25">
			</td>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">审核时间:</label>
			</td>
			<td>
				<input id="auditDate" name="auditDate" type="text" 
					class="Wdate tdInput" onClick="WdatePicker()"
   					value='<fmt:formatDate value='${cover.auditDate}' 
   					type="date" pattern="yyyy-MM-dd"/>'>    
			</td>
		</tr>
		<tr>
			<td align="right" style="padding-right:10px;">
				<label class="tdLabel">提交时间:</label>
			</td>
			<td>
		  		<input id="submitDate" name="submitDate" type="text" 
		  			class="Wdate tdInput" onClick="WdatePicker()"
			 		value='<fmt:formatDate value='${cover.submitDate}' 
			 		type="date" pattern="yyyy-MM-dd"/>'>    
			</td>
		</tr>
		<c:if test="${read != '1' }">
			<tr>
				<td colspan="4" align="right" style="height:50px;">
					<a id="saveBtn" href="#" style="margin-right: 15%;" 
						class="easyui-linkbutton" 
						data-options="iconCls:'icon-save'">保存</a>  
				</td>
			</tr>
		</c:if>
	</table>
</t:formvalid>
</div>
</body>

<script type="text/javascript" src="webpage/com/kingtake/project/price/price.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if($("#read").val() == "1"){
		$("input").attr("disabled", "disabled");
		$('select').attr("disabled", "disabled");
	}
});

function saveAfter(data){
	$("#Validform_msg").hide();
	showMsg(data.msg);
}
</script>
</html>


