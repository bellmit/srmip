<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>合同节点考核</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>

<style type="text/css">
.radioclass {
	width: 18px;
	height: 18px;
}
</style>
	<h1 align="center">合同节点考核审批表（重要合同）</h1>
	<c:if test="${!empty tPmContractNodeCheckPage.msgText and tPmContractNodeCheckPage.payFlag eq '2' }">
    <a href="#" style="color: red; cursor: pointer; text-decoration: underline;" onclick="viewMsg()">查看支付申请修改意见</a>
    <textarea id="msgTextArea" rows="5" cols="5" style="width:100%;height:100%;display: none;" >${tPmContractNodeCheckPage.msgText}</textarea>
    </c:if>
	<t:formvalid formid="formobj" dialog="true" layout="table"
		action="tPmContractNodeCheckController.do?doUpdateOutcomeNodeCheck" tiptype="1" callback="@Override saveCallback">
		<input id="finishUserid" name="finishUserid" type="hidden"
			value="${tPmContractNodeCheckPage.finishUserid }">
		<input id="createBy" name="createBy" type="hidden"
			value="${tPmContractNodeCheckPage.createBy }">
		<input id="createName" name="createName" type="hidden"
			value="${tPmContractNodeCheckPage.createName }">
		<input id="createDate" name="createDate" type="hidden"
			value="${tPmContractNodeCheckPage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden"
			value="${tPmContractNodeCheckPage.updateBy }">
		<input id="updateName" name="updateName" type="hidden"
			value="${tPmContractNodeCheckPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden"
			value="${tPmContractNodeCheckPage.updateDate }">
		<input id="contractNodeId" name="contractNodeId" type="hidden"
			value="${tPmContractNodeCheckPage.contractNodeId }">
		<input id="contractId" name="contractId" type="hidden"
			value="${tPmContractNodeCheckPage.contractId }">
		<input id="id" name="id" type="hidden"
			value="${tPmContractNodeCheckPage.id }">
		<input id="importantFlag" name="importantFlag" type="hidden"
			value="${tPmContractNodeCheckPage.importantFlag }">
		<input id="projectId" name="projectId" type="hidden"
			value="${tPmContractNodeCheckPage.projectId }">
		<fieldset style="border-color: #F5F5F5;">
			<legend>
				<span class="legendFont" style="color: #A52A2A">科研项目信息</span>
			</legend>
			<table style="width: 100%;" cellpadding="0" cellspacing="1"
				class="formtable">
				<tr>
					<td align="right"><label class="Validform_label">
							责任单位: </label></td>
					<td class="value" colspan="3"><input id="zrdw" name="zrdw"
						type="text" style="width: 482px" class="inputxt" datatype="*"
						value='${tPmContractNodeCheckPage.zrdw}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">责任单位</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							项目名称: </label></td>
					<td class="value" colspan="3"><input id="projectName"
						name="projectName" type="text" style="width: 482px"
						class="inputxt" datatype="*"
						value='${tPmContractNodeCheckPage.projectName}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">项目名称</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							项目组长: </label></td>
					<td class="value"><input id="xmzz" name="xmzz" type="text"
						style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.xmzz}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">项目组长</label></td>
					<td align="right"><label class="Validform_label">
							联系电话: </label></td>
					<td class="value"><input id="xmzzPhone" name="xmzzPhone"
						type="text" style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.xmzzPhone}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">项目组长联系电话</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label"> 联系人:
					</label></td>
					<td class="value"><input id="lxr" name="lxr" type="text"
						style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.lxr}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">联系人</label></td>
					<td align="right"><label class="Validform_label">
							联系电话: </label></td>
					<td class="value"><input id="lxrPhone" name="lxrPhone"
						type="text" style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.lxrPhone}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">联系人联系电话</label></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="border-color: #F5F5F5;">
			<legend>
				<span class="legendFont" style="color: #A52A2A">合同信息</span>
			</legend>
			<table style="width: 100%;" cellpadding="0" cellspacing="1"
				class="formtable">
				<tr>
					<td align="right"><label class="Validform_label">
							合同名称: </label></td>
					<td class="value" colspan="3"><input id="contractName"
						name="contractName" type="text" style="width: 490px"
						class="inputxt" value='${tPmContractNodeCheckPage.contractName}'>
						<span class="Validform_checktip"></span> <label
						class="Validform_label" style="display: none;">合同名称</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							合同编号: </label></td>
					<td class="value"><input id="contractCode" name="contractCode"
						type="text" style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.contractCode}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">合同编号</label></td>
					<td align="right"><label class="Validform_label">
							合同总金额: </label></td>
					<td class="value"><input id="contractTotalFunds"
						name="contractTotalFunds" type="text" style="width: 150px"
						class="inputxt"
						value='${tPmContractNodeCheckPage.contractTotalFunds}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">合同总金额</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							合同类型: </label></td>
					<td class="value" colspan="3"><t:codeTypeSelect name="htlx"
							type="select" codeType="1" code="HTLB" id="htlx"
							defaultVal="${tPmContractNodeCheckPage.htlx}"
							extendParam='{"style":"width:156px;"}'></t:codeTypeSelect> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">合同类型</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							乙方名称: </label></td>
					<td class="value" colspan="3"><input id="yfmc" name="yfmc"
						type="text" style="width: 490px" class="inputxt"
						value='${tPmContractNodeCheckPage.yfmc}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">乙方名称</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							乙方地址: </label></td>
					<td class="value" colspan="3"><input id="yfdz" name="yfdz"
						type="text" style="width: 490px" class="inputxt"
						value='${tPmContractNodeCheckPage.yfdz}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">乙方地址</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							乙方种类: </label></td>
					<td class="value" colspan="3"><input id="yfzl_hidden" 
						type="hidden" value='${tPmContractNodeCheckPage.yfzl}'> <input
						type="radio" name="yfzl" id="yfzlXx" class="radioclass"
						value="1" <c:if test="${tPmContractNodeCheckPage.yfzl eq '1'}">checked="checked"</c:if>>学校供方 <input type="radio" name="yfzl"
						id="yfzlLs" class="radioclass" value="2" <c:if test="${tPmContractNodeCheckPage.yfzl eq '2'}">checked="checked"</c:if>>临时供方 <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">乙方种类</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							乙方联系人: </label></td>
					<td class="value"><input id="yflxr" name="yflxr" type="text"
						style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.yflxr}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">乙方联系人</label></td>
					<td align="right"><label class="Validform_label">
							乙方联系电话: </label></td>
					<td class="value"><input id="yflxdh" name="yflxdh" type="text"
						style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.yflxdh}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">乙方联系电话</label></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="border-color: #F5F5F5;">
			<legend>
				<span class="legendFont" style="color: #A52A2A">节点信息</span>
			</legend>
			<table style="width: 100%;" cellpadding="0" cellspacing="1"
				class="formtable">
				<tr>
					<td align="right"><label class="Validform_label">
							节点名称: </label></td>
					<td class="value" colspan="3"><input id="jdmc" name="jdmc"
						type="text" style="width: 484px" class="inputxt"
						value='${tPmContractNodeCheckPage.jdmc}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">节点名称</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							完成时间: </label></td>
					<td class="value"><input id="wcsj" name="wcsj" type="text"
						style="width: 150px" class="Wdate" onClick="WdatePicker()"
						value='<fmt:formatDate value='${tPmContractNodeCheckPage.wcsj}' 
                  type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span> <label
						class="Validform_label" style="display: none;">完成时间</label></td>
					<td align="right"><label class="Validform_label">
							节点金额: </label></td>
					<td class="value"><input id="jdje" name="jdje" type="text"
						style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.jdje}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">节点金额</label></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="border-color: #F5F5F5;">
			<legend>
				<span class="legendFont" style="color: #A52A2A">评审前已完成工作</span>
			</legend>
			<table style="width: 100%;" cellpadding="0" cellspacing="1"
				class="formtable">
				<tr>
					<td align="right"><label class="Validform_label"> </label></td>
					<td class="value" colspan="2"><textarea id="psqywcgz"
							name="psqywcgz" style="width: 436px; height: 60px;" cols="3" placeholder="将已完成的工作填入本栏"
							rows="2">${tPmContractNodeCheckPage.psqywcgz}</textarea> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">评审前已完成工作</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							附&nbsp;&nbsp;&nbsp;&nbsp;件<img alt="上传相关记录及技术文件资料等佐证材料"
							title="上传相关记录及技术文件资料等佐证材料"
							src="plug-in\easyui1.4.2\themes\icons\tip.png">:
					</label></td>
					<td class="value"><input type="hidden"
						value="${tPmContractNodeCheckPage.attachmentCode}" id="bid"
						name="attachmentCode" />
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
							<t:upload name="fiels" id="file_upload" buttonText="添加文件"
								formData="bid,projectId" auto="true" dialog="false"
								onUploadSuccess="uploadSuccess" fileSizeLimit="2GB"
								uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmOutcomeContractNodeCheck"></t:upload>
						</div></td>
				</tr>
			</table>
		</fieldset>
		<fieldset style="border-color: #F5F5F5;">
			<legend>
				<span class="legendFont" style="color: #A52A2A">评审信息</span>
			</legend>
			<table style="width: 100%;" cellpadding="0" cellspacing="1"
				class="formtable">
				<tr>
					<td align="right"><label class="Validform_label">
							评审方式: </label></td>
					<td class="value"><input id="psfs_hidden" type="hidden"
						value='${tPmContractNodeCheckPage.psfs}'> <input
						type="radio" name="psfs" id="psfsHy" class="radioclass"
						value="1" <c:if test="${tPmContractNodeCheckPage.psfs eq '1'}">checked="checked"</c:if>><label for="psfsHy">会议评审 </label><input
						type="radio" name="psfs" id="psfsHq" class="radioclass"
						value="2" <c:if test="${tPmContractNodeCheckPage.psfs eq '2'}">checked="checked"</c:if>><label for="psfsHq">会签评审</label> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">评审方式</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							评审时间: </label></td>
					<td class="value"><input id="pssj" name="pssj" type="text"
						style="width: 150px" class="Wdate" onClick="WdatePicker()"
						value='<fmt:formatDate value='${tPmContractNodeCheckPage.pssj}' 
                  type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span> <label
						class="Validform_label" style="display: none;">评审时间</label></td>
					<td align="right"><label class="Validform_label">
							评审地点: </label></td>
					<td class="value"><input id="psdd" name="psdd" type="text"
						style="width: 150px" class="inputxt"
						value='${tPmContractNodeCheckPage.psdd}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">评审地点</label></td>

				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							参评人员: </label></td>
					<td class="value" colspan="3"><input id="pscpry" name="pscpry"
						type="text" style="width: 490px" class="inputxt"
						value='${tPmContractNodeCheckPage.pscpry}'> <span
						class="Validform_checktip"></span> <label class="Validform_label"
						style="display: none;">参评人员</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							评审结果: </label></td>
					<td class="value" colspan="3"><textarea id="psjg" name="psjg" placeholder="简述评审的内容、过程"
							cols="3" rows="3" style="width: 490px; height: 50px;">${tPmContractNodeCheckPage.psjg}</textarea>
						<span class="Validform_checktip"></span> <label
						class="Validform_label" style="display: none;">评审结果</label></td>
				</tr>
				<tr>
					<td align="right"><label class="Validform_label">
							附&nbsp;&nbsp;&nbsp;&nbsp;件<img alt="上传形成的成果，如专家评审意见、检验验收记录等"
							title="上传形成的成果，如专家评审意见、检验验收记录等"
							src="plug-in\easyui1.4.2\themes\icons\tip.png">:
					</label></td>
					<td class="value">
						<table id="fileShow1" style="max-width: 305px;">
							<c:forEach items="${tPmContractNodeCheckPage.psjgFj}"
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
							<div class="form" id="filediv1"></div>
							<t:upload name="fiels" id="file_upload1" queueID="filediv1" buttonText="添加文件"
								formData="bid,projectId" auto="true" dialog="false"
								onUploadSuccess="uploadSuccess1" fileSizeLimit="2GB"
								uploader="commonController.do?saveUploadFilesToFTP&businessType=tPmOutcomeContractNodeCheckPsjg"></t:upload>
						</div></td>
				</tr>
			</table>
		</fieldset>
	</t:formvalid>
	<script type="text/javascript">
	function saveCallback(data){
	    var win = W.$.dialog.list['processDialog'].content;
	    win.tip(data.msg);
	    if(data.success){
	       win.reloadTable();
	       frameElement.api.close();
	    }
	} 
	
	function uploadSuccess1(d,file,response){
		updateUploadSuccessTable("fileShow1",d,file,response);
	}
</script>
<script
	src="webpage/com/kingtake/project/contractnodecheck/tPmContractNodeCheck.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>
</body>