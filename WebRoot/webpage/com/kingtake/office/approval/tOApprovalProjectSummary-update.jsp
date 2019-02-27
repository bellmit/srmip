<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>呈批件项目汇总</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tOApprovalProjectSummaryController.do?doUpdate" tiptype="1">
					<input id="id" name="id" type="hidden" value="${tOApprovalProjectSummaryPage.id }">
					<input id="projectId" name="projectId" type="hidden" value="${tOApprovalProjectSummaryPage.projectId }">
					<input id="projectManagerId" name="projectManagerId" type="hidden" value="${tOApprovalProjectSummaryPage.projectManagerId }">
					<input id="approvalId" name="approvalId" type="hidden" value="${tOApprovalProjectSummaryPage.approvalId }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								所属项目名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.projectName}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">所属项目名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								项目编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="projectNo" name="projectNo" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.projectNo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								负责人名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="projectManager" name="projectManager" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.projectManager}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">负责人名称</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								起始日期:
							</label>
						</td>
						<td class="value">
									  <input id="beginDate" name="beginDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tOApprovalProjectSummaryPage.beginDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起始日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								截止日期:
							</label>
						</td>
						<td class="value">
									  <input id="endDate" name="endDate" type="text" style="width: 150px" 
						      						class="Wdate" onClick="WdatePicker()"
									                
						      						 value='<fmt:formatDate value='${tOApprovalProjectSummaryPage.endDate}' type="date" pattern="yyyy-MM-dd"/>'>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">截止日期</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								项目类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="projectType" name="projectType" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.projectType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								经费类型:
							</label>
						</td>
						<td class="value">
						     	 <input id="feeType" name="feeType" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.feeType}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">经费类型</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								承研部门:
							</label>
						</td>
						<td class="value">
						     	 <input id="developerDepart" name="developerDepart" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.developerDepart}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">承研部门</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								分管部门:
							</label>
						</td>
						<td class="value">
						     	 <input id="manageDepart" name="manageDepart" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.manageDepart}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">分管部门</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								项目来源:
							</label>
						</td>
						<td class="value">
						     	 <input id="projectSource" name="projectSource" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.projectSource}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">项目来源</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								依据文号:
							</label>
						</td>
						<td class="value">
						     	 <input id="accordingNum" name="accordingNum" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.accordingNum}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">依据文号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								总经费:
							</label>
						</td>
						<td class="value">
						     	 <input id="allFee" name="allFee" type="text" style="width: 150px" class="inputxt"  
									               
									                 value='${tOApprovalProjectSummaryPage.allFee}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">总经费</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/office/approval/tOApprovalProjectSummary.js"></script>		