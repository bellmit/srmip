<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>支付申请</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmPayApplyController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmPayApplyPage.id }">
					<input id="contractNodeId" name="contractNodeId" type="hidden" value="${tPmPayApplyPage.contractNodeId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							承研单位名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="devdepartName" name="devdepartName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">承研单位名称</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							项目负责人名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="projectManagerName" name="projectManagerName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目负责人名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目名称</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							会计编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="accountingCode" name="accountingCode" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">会计编码</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							项目编码:
						</label>
					</td>
					<td class="value">
					     	 <input id="projectCode" name="projectCode" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目编码</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							合同名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="contractName" name="contractName" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同名称</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同编号:
						</label>
					</td>
					<td class="value">
					     	 <input id="contractCode" name="contractCode" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同编号</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							合同乙方:
						</label>
					</td>
					<td class="value">
					     	 <input id="contractUnitnameb" name="contractUnitnameb" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同乙方</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同开始时间:
						</label>
					</td>
					<td class="value">
							   <input id="contractStartTime" name="contractStartTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同开始时间</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							合同结束时间:
						</label>
					</td>
					<td class="value">
							   <input id="contractEndTime" name="contractEndTime" type="text" style="width: 150px" 
					      						class="Wdate" onClick="WdatePicker()"
								                
								               >    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同结束时间</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同总价款:
						</label>
					</td>
					<td class="value">
					     	 <input id="contractTotalAmount" name="contractTotalAmount" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同总价款</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							已付合同款:
						</label>
					</td>
					<td class="value">
					     	 <input id="contractPaidAmouont" name="contractPaidAmouont" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">已付合同款</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							本期付款金额:
						</label>
					</td>
					<td class="value">
					     	 <input id="currentPayAmount" name="currentPayAmount" type="text" style="width: 150px" class="inputxt"  
								               
								               >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">本期付款金额</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							本期付款要求:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="payDemand" name="payDemand"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">本期付款要求</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							合同执行情况:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="executeState" name="executeState"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">合同执行情况</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							项目组意见:
						</label>
					</td>
					<td class="value">
						  	 <textarea style="width:600px;" class="inputxt" rows="6" id="projectTeamSuggestion" name="projectTeamSuggestion"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目组意见</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/base/payapply/tPmPayApply.js"></script>		