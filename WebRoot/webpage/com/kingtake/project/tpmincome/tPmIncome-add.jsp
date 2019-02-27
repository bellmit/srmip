<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>到账信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmIncomeController.do?doAdd" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tPmIncomePage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tPmIncomePage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tPmIncomePage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tPmIncomePage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tPmIncomePage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tPmIncomePage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tPmIncomePage.updateDate }">
					<input id="projectId" name="project.id" type="hidden" value="${tPmIncomePage.project.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
						<td align="right">
							<label class="Validform_label">
								到账年度:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						     	 <input id="incomeYear" name="incomeYear" type="text" style="width: 150px" class="inputxt" datatype="/[0-9]{1,4}/" errormsg="请输入1-4位数字" value='${tPmIncomePage.incomeYear}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">到账年度</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								到账日期:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
									  <input id="incomeTime" name="incomeTime" type="text" style="width: 150px" datatype="*"
						      						class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmIncomePage.incomeTime}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">到账日期</label>
						</td>						
					</tr>
					<tr>						
						<td align="right">
							<label class="Validform_label">
								凭证号:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						     	 <input id="certificate" name="certificate" type="text" style="width: 150px" class="inputxt" datatype="/[a-zA-Z0-9]{1,20}/" errormsg="请输入1-20位数字或字母" value='${tPmIncomePage.certificate}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">凭证号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								到账顺序号:
							</label>
						</td>
						<td class="value">
						     	 <input id="incomeNo" name="incomeNo" type="text" style="width: 150px" class="inputxt" value='${tPmIncomePage.incomeNo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">到账顺序号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								到账金额:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						     	 <input id="incomeAmount" name="incomeAmount" type="text" style="width: 150px;text-align:right;" class="easyui-numberbox inputxt"
              data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" datatype="*" min="1" datatype="n1-10"  value='${tPmIncomePage.incomeAmount}'>(元)
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">到账金额</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								到账借贷:<font color="red">*</font>
							</label>
						</td>
						<td class="value">
						     	 <input id="incomeBorrow" name="incomeBorrow" type="text" style="width: 150px;text-align:right;" class="easyui-numberbox inputxt"
              data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" datatype="*" min="1" datatype="n1-10"  value='${tPmIncomePage.incomeBorrow}'>(元)
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">到账借贷</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								到款科目:
							</label>
						</td>
						<td class="value">
						     	 <input id="incomeSubject" name="incomeSubject" type="text" style="width: 150px" class="inputxt" datatype="*0-12" errormsg="请输入不要超过12位" value='${tPmIncomePage.incomeSubject}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">到款科目</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								会计年度:
							</label>
						</td>
						<td class="value">
						     	 <input id="accountingYear" name="accountingYear" type="text" style="width: 150px" class="inputxt" datatype="n0-4" errormsg="请输入不超过4位的数字" value='${tPmIncomePage.accountingYear}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">会计年度</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								摘要:
							</label>
						</td>
						<td class="value" colspan="3">
						  	 	<textarea id="digest" datatype="byterange" max="500" min="0" style="width:450px;" class="inputxt" rows="2" name="digest">${tPmIncomePage.digest}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">摘要</label>
						</td>
					</tr>					
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value" colspan="3">
						  	 	<textarea id="remark" style="width:450px;" datatype="byterange" max="1000" min="0" class="inputxt" rows="3" name="remark">${tPmIncomePage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<c:if test="${loadType eq 'JH'}">
					<tr>
        				<td align="right"><label class="Validform_label">
            				项目名称:<font color="red">*</font>
          				</label></td>
        				<td class="value"><input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt" value='${tPmIncomePage.project.projectName}' datatype="*"> <t:chooseProject
            				inputName="projectName" inputId="projectId" mode="single" all="true"></t:chooseProject> <span class="Validform_checktip"></span> <label class="Validform_label"
            				style="display: none;">项目名称</label></td>
      				</tr>
      				</c:if>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/tpmincome/tPmIncome.js"></script>		