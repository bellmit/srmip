<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>预算审批流转记录表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
     <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" 
     	action="tPmFundsLogsController.do?doAdd" tiptype="1" callback="@Override refreshOrClose">
		 <input id="id" name="id" type="hidden" value="${tPmFundsLogsPage.id }">
		 <input id="projectFundsApprId" name="projectFundsAppr.id" type="hidden" value="${tPmFundsLogsPage.projectFundsAppr.id}">
		 <table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable">
		 	<tr>
				<td align="right">
					<label class="Validform_label">
						审批类型:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
					<t:codeTypeSelect id="suggestionType" name="suggestionType" type="select" codeType="1" code="YSSPLX" ></t:codeTypeSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						接收人:<font color="red">*</font>
					</label>
				</td>
				<td class="value">
			     	<input id="receiveUseNames" name="receiveUseNames" type="text" style="width: 150px" class="inputxt" readonly="readonly"/>
			     	<input id="receiveUseIds" name="receiveUseIds" type="hidden" datatype="*"/>
			     	<input id="receiveUseDepartIds" name="receiveUseDepartIds" type="hidden" />
			     	<input id="receiveUseDepartNames" name="receiveUseDepartNames" type="hidden" />
				  	<t:chooseUser icon="icon-search" title="人员列表" isclear="true"  idInput="receiveUseIds"
						textname="id,realName,departId,departName"  
						inputTextname="receiveUseIds,receiveUseNames,receiveUseDepartIds,receiveUseDepartNames" ></t:chooseUser>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">接收人</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						系统消息内容:
					</label>
				</td>
				<td class="value">
					<textarea id="senderTip" name="senderTip" style="width:260px;height:100px;"
				    	datatype="*1-50" ignore="ignore">${tPmContractFlowLogsPage.senderTip }</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">系统消息内容</label>
				</td>
			</tr>
		 </table>
 	 </t:formvalid>
 	 <div style="overflow-y:auto;width:930px;height:300px;">
 	 	<input id="closeOrRefreshFlag" type="hidden"/>
 	 	<t:datagrid name="tPmFundsReceiveLogsList" checkbox="false" fitColumns="false" title="审批记录" 
 		  actionUrl="tPmFundsReceiveLogsController.do?datagrid&projectFundsAppr.id=${tPmFundsLogsPage.projectFundsAppr.id}" idField="id" fit="true" queryMode="group"
 		  sortName="fundsLog.operateDate" sortOrder="desc" pagination="false">
		  <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
		  <t:dgCol title="审批类型"  field="suggestionType"  codeDict="1,YSSPLX"  queryMode="group"  width="130"></t:dgCol>
		  <t:dgCol title="发起人"  field="fundsLog.operateUsername"   queryMode="group"  width="70"></t:dgCol>
		  <t:dgCol title="发起时间"  field="fundsLog.operateDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="150" align="center"></t:dgCol>
		  <t:dgCol title="审批人"  field="receiveUsername"    queryMode="group"  width="70"></t:dgCol>
		  <t:dgCol title="审批人部门"  field="receiveDepartname"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
		  <t:dgCol title="处理状态"  field="operateStatus"  replace="待审批_0,已处理_1"  queryMode="group"  width="70"></t:dgCol>
		  <t:dgCol title="处理时间"  field="operateTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="150" align="center"></t:dgCol>
		  <t:dgCol title="审批意见"  field="suggestionCode"  codeDict="1,SPYJ"  queryMode="group"  width="70"></t:dgCol>
		  <t:dgCol title="意见内容"  field="suggestionContent"    queryMode="group"  width="130"></t:dgCol>
		 </t:datagrid>
 	 </div>
 </body>
 <script src = "webpage/com/kingtake/project/fundsuggest/tPmFundsLogs.js"></script>
 
</html>	