<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>成果奖励基本信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBResultRewardController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tBResultRewardPage.id }">
					<input id="finishUserid" name="finishUserid" type="hidden" value="${tBResultRewardPage.finishUserid }">
					<input id="finishDepartid" name="finishDepartid" type="hidden" value="${tBResultRewardPage.finishDepartid }">
					<input id="summary" name="summary" type="hidden" value="${tBResultRewardPage.summary }">
					<input id="sourceProjectIds" name="sourceProjectIds" type="hidden" value="${tBResultRewardPage.sourceProjectIds }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								项目名称:<font color="red">*</font>
							</label>
						</td>
						<td class="value" colspan="3">
						    <input id="rewardName" name="rewardName"  datatype="byterange" max="120" min="1" type="text" style="width: 70%" class="inputxt" value='${tBResultRewardPage.rewardName}' >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								主要完成人:<font color="red">*</font>
							</label>
						</td>
						<td class="value" colspan="3">
						    <input id="finishUsername" name="finishUsername"  datatype="byterange" max="100" min="1" type="text" style="width: 70%" class="inputxt" value='${tBResultRewardPage.finishUsername}'><t:chooseUser idInput="finishUserid" inputTextname="finishUserid,finishUsername,finishDepartid,finishDepartname" icon="icon-search" title="人员列表" textname="id,realName,departId,departName" isclear="true" mode="single"></t:chooseUser>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">主要完成人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								主要完成单位:<font color="red">*</font>
							</label>
						</td>
						<td class="value" colspan="3">
						    <input id="finishDepartname" name="finishDepartname" type="text" datatype="byterange" max="300" min="1" style="width: 70%" class="inputxt" value='${tBResultRewardPage.finishDepartname}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">主要完成单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								任务来源:<font color="red">*</font>
							</label>
						</td>
						<td class="value" colspan="3">
						    <input id="taskSource" name="taskSource" type="text" style="width: 70%;"  datatype="byterange" max="1000" min="1" class="inputxt" value='${tBResultRewardPage.taskSource}' readonly="readonly"><t:chooseProject inputName="taskSource" inputId="sourceProjectIds" isclear="true"></t:chooseProject>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">任务来源</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								拟申报奖励类别:
							</label>
						</td>
						<td class="value">
						    <t:codeTypeSelect name="reportRewardLevel" type="select" codeType="1" code="SBJLLB" id="reportRewardLevel" defaultVal="${tBResultRewardPage.reportRewardLevel}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">拟申报奖励类别</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								拟申报等级:
							</label>
						</td>
						<td class="value">
						    <t:codeTypeSelect name="reportLevel" type="select" codeType="1" code="SBDJ" id="reportLevel" defaultVal="${tBResultRewardPage.reportLevel}"></t:codeTypeSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">拟申报等级</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								创新点:
							</label>
						</td>
						<td class="value">
						     	 <input id="innovationPoint" name="innovationPoint" type="text" style="width: 150px" class="inputxt" value='${tBResultRewardPage.innovationPoint}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">创新点</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								总投资额:
							</label>
						</td>
						<td class="value" colspan="3">
						    <input id="investedAmount" name="investedAmount" type="text"    value='${tBResultRewardPage.investedAmount}'style="width:150px;text-align:right;"
						     class="easyui-numberbox" data-options="min:0,max:99999999.99,precision:2,groupSeparator:','">元
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">总投资额</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								起始日期:
							</label>
						</td>
						<td class="value">
							<input id="beginDate" name="beginDate" type="text" style="width: 150px" 
						     class="Wdate" onClick="WdatePicker()"value='<fmt:formatDate value='${tBResultRewardPage.beginDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								截止日期:
							</label>
						</td>
						<td class="value">
							<input id="endDate" name="endDate" type="text" style="width: 150px" 
						     class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBResultRewardPage.endDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">截止日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								联系人:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="contacts" name="contacts" type="text"  datatype="byterange" max="36" min="1" style="width: 150px" class="inputxt"  value='${tBResultRewardPage.contacts}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系人</label>
						</td>
						<td align="right"> 
							<label class="Validform_label">
								联系电话:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						    <input id="contactPhone" name="contactPhone" type="text" style="width: 150px" class="inputxt" value='${tBResultRewardPage.contactPhone}' datatype="n1-30">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">联系电话</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								我校在项目中的贡献:
							</label>
						</td>
						<td class="value" colspan="3">
							<textarea rows="3" style="width: 70%;" id="hgdDevote" name="hgdDevote">${tBResultRewardPage.hgdDevote}</textarea>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/resultreward/tBResultReward.js"></script>		