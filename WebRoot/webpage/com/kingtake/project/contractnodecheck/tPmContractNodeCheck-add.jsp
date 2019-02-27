<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>合同节点考核</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	function formCheck(){
		  var form = $("#formobj").Validform();
	      var obj = form.check(false);
	      return obj;
	  }
	
	function saveCallback(data){
	    var win = W.$.dialog.list['processDialog'].content;
	    win.tip(data.msg);
	    if(data.success){
	       win.reloadTable();
	       frameElement.api.close();
	    }
	} 
	
</script>
</head>
<body>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmContractNodeCheckController.do?doAdd" tiptype="1" callback="@Override saveCallback">
		<input id="finishUserid" name="finishUserid" type="hidden" value="${tPmContractNodeCheckPage.finishUserid }">
		<input id="createBy" name="createBy" type="hidden" value="${tPmContractNodeCheckPage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tPmContractNodeCheckPage.createName }">
		<input id="createDate" name="createDate" type="hidden" value="${tPmContractNodeCheckPage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tPmContractNodeCheckPage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tPmContractNodeCheckPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tPmContractNodeCheckPage.updateDate }">
		<input id="contractNodeId" name="contractNodeId" type="hidden" value="${tPmContractNodeCheckPage.contractNodeId }">
		<input id="contactId" name="contactId" type="hidden" value="${contractAppr.id}">
		
		<input id="id" name="id" type="hidden" value="${tPmContractNodeCheckPage.id }">
		<input id="projectId"  type="hidden" value="${projectId }">
    
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<!-- <tr>
				<td align="right"><label class="Validform_label"> 完成时间: </label></td>
				<td class="value"><input id="finishTime" name="finishTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">完成时间</label></td>
				<td align="right"><label class="Validform_label"> 完成操作人姓名: </label></td>
				<td class="value"><input id="finishUsername" name="finishUsername" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">完成操作人姓名</label></td>
			</tr> -->
			<tr>
				<td align="right" style="width: 88px;"><label class="Validform_label"> 合同编号: </label></td>
				<td class="value"><input id="contactNum" name="contactNum" type="text" style="width: 150px" class="inputxt" value="${contractAppr.contractCode}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同编号</label></td>
				<td align="right" style="width: 88px;"><label class="Validform_label"> 会计编码: </label></td>
				<td class="value"><input id="accountingCode" name="accountingCode" type="text" style="width: 150px" class="inputxt" value="${contractAppr.project.accountingCode}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会计编码</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 项目名称: </label></td>
				<td class="value"><input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt" datatype="*" value="${contractAppr.project.projectName}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
				<td align="right"><label class="Validform_label"> 密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级: </label><font color="red">*</font></td>
				<td class="value"><t:codeTypeSelect name="secretDegree" type="select" codeType="0" code="XMMJ" id="secretDegree" defaultVal="${contractAppr.project.secretDegree}"></t:codeTypeSelect><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">密级</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 合同名称: </label></td>
				<td class="value"><input id="contractName" name="contractName" type="text" style="width: 150px" class="inputxt" value="${contractAppr.contractName}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同名称</label></td>
				<c:if test="${inOutFlag eq 'i' }">
				<td align="right"><label class="Validform_label"> 合同类型: </label></td>
				<td class="value"><t:codeTypeSelect name="contractType" type="select" codeType="1" code="HTLB" id="contractType"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同类型</label></td>
                </c:if>
                <c:if test="${inOutFlag eq 'o' }">
				<td align="right"><label class="Validform_label"> 合同类别: </label></td>
				<td class="value"><input value="科研采购合同" readonly="readonly" style="width: 150px;"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同类别</label></td>
                </c:if>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 合同乙方: </label></td>
				<td class="value"><input id="contactSecondParty" name="contactSecondParty" type="text" style="width: 150px" class="inputxt" value="${contractAppr.approvalUnit}"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同乙方</label></td>
				<td align="right"><label class="Validform_label"> 合同价款: </label></td>
				<td class="value">
					<input id="contactAmount" name="contactAmount" type="text" 
						style="width:135px; text-align:right;" class="easyui-numberbox"
						data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
						value="${contractAppr.totalFunds}">元
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">合同价款</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 签订时间: </label></td>
				<td class="value"><input id="contractSigningTime" name="contractSigningTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${contractAppr.contractSigningTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">签订时间</label></td>
				<td align="right"><label class="Validform_label"> 节点价款: </label></td>
				<td class="value">
					<input id="nodeAmount" name="nodeAmount" type="text" 
						style="width:135px; text-align:right;" class="easyui-numberbox"
						data-options="min:0,max:99999999.99,precision:2,groupSeparator:','"
						value="${cost}">元 
					<span class="Validform_checktip"></span> 
					<label class="Validform_label" style="display: none;">节点价款</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 节点时间: </label></td>
				<td class="value"><input id="nodeTime" name="nodeTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmContractNodeCheckPage.nodeTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">节点时间</label></td>
				<td align="right"><label class="Validform_label"> 验收时间: </label></td>
				<td class="value"><input id="checkTime" name="checkTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmContractNodeCheckPage.checkTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">验收时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 组织单位: </label></td>
				<td class="value">
				<input id="organizationUnitid" name="organizationUnitid" type="hidden" value="${tPmContractNodeCheckPage.organizationUnitid }">
				<input id="organizationUnitname" name="organizationUnitname" type="hidden" style="width: 150px" class="inputxt"  value="${tPmContractNodeCheckPage.organizationUnitname }">
				<t:departComboTree id="o" idInput="organizationUnitid" nameInput="organizationUnitname" width="155" value="${tPmContractNodeCheckPage.organizationUnitname }"></t:departComboTree>
				 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">组织单位名称</label></td>
				<td align="right"><label class="Validform_label"> 责任单位: </label></td>
				<td class="value">
				<input id="dutyDepartid" name="dutyDepartid" type="hidden" value="${tPmContractNodeCheckPage.dutyDepartid }">
				<input id="dutyDepartname" name="dutyDepartname" type="hidden" style="width: 150px" class="inputxt"  value="${tPmContractNodeCheckPage.dutyDepartname }">
				<t:departComboTree id="d" idInput="dutyDepartid" nameInput="dutyDepartname" width="155" value="${tPmContractNodeCheckPage.dutyDepartname }"></t:departComboTree>
				 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">责任单位名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 节点要求&nbsp;<br>或&nbsp;内&nbsp;容&nbsp;: </label></td>
				<td class="value" colspan="3"><textarea rows="2" id="nodeContent" name="nodeContent" style="width: 525px">${tPmContractNodeCheckPage.nodeContent}</textarea><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">节点要求或内容</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 节点进度&nbsp;<br>或&nbsp;成&nbsp;果&nbsp;: </label></td>
				<td class="value" colspan="3"><textarea rows="2" id="nodeResult" name="nodeResult" style="width: 525px">${tPmContractNodeCheckPage.nodeResult}</textarea><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">节点进度或成果</label></td>
				</tr>
				<tr>
				<td align="right"><label class="Validform_label"> 备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注: </label></td>
				<td class="value" colspan="3"><textarea rows="2" id="memo" name="memo" style="width: 525px">${tPmContractNodeCheckPage.memo}</textarea><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
			</tr>
            <tr>
              <td align="right">
                <label class="Validform_label"> 附件:&nbsp;&nbsp; </label>
              </td>
              <td colspan="3" class="value">
                <input type="hidden" value="${tPmContractNodeCheckPage.attachmentCode }" id="bid" name="attachmentCode" />
                <table id="fileShow" style="max-width: 305px;">
							<c:forEach items="${tPmContractNodeCheckPage.attachments}"
								var="file" varStatus="idx">
								<tr style="height: 30px;">
									<td><a href="javascript:void(0);">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
									<td style="width: 40px;"><a
										href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0"
										title="下载">下载</a></td>
									<td style="width: 40px;"><a href="javascript:void(0)"
										class="jeecgDetail"
										onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
								</tr>
							</c:forEach>
						</table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload ame="fiels" id="file_upload" buttonText="添加文件"
								formData="bid,projectId" auto="true" dialog="false"
								onUploadSuccess="uploadSuccess" fileSizeLimit="2GB"
                    uploader="commonController.do?saveUploadFilesToFTP&businessType=incomeNodeCheck" ></t:upload>
                </div>
              </td>
            </tr>
		</table>
	</t:formvalid>
</body>
<script src="webpage/com/kingtake/project/contractnodecheck/tPmContractNodeCheck.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>