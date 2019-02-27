<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>合同节点考核</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
</head>
<body>
<script type="text/javascript">
	//编写自定义JS代码
	function formCheck() {
		var form = $("#formobj").Validform();
		var obj = form.check(false);
		return obj;
	}
	
	$(function(){
		//如果页面是详细查看页面(审批查看 详情)
		if(location.href.indexOf("edit=false")!=-1){
			//无效化所有表单元素，只能进行查看
			$(":input").attr("disabled","true");
			//隐藏选择框和清空框
			$("a[icon='icon-search']").css("display","none");
			$("a[icon='icon-redo']").css("display","none");
			//隐藏下拉框箭头
			$(".combo-arrow").css("display","none");
			//隐藏添加附件
			$("#filediv").parent().css("display","none");
			//隐藏附件的删除按钮
			$(".jeecgDetail").parent().css("display","none");
			//将发送蛇皮设为可点
			$(".sendButton").removeAttr("disabled");
		}
		
		//编辑时审批已处理：提示不可编辑
		if(location.href.indexOf("load=detail&tip=true") != -1){
			var parent = frameElement.api.opener;
			var msg = $("#tipMsg", parent.document).val();
			tip(msg);
		}
	});
	
	function saveCallback(data){
	    var win = W.$.dialog.list['processDialog'].content;
	    win.tip(data.msg);
	    if(data.success){
	       win.reloadTable();
	       frameElement.api.close();
	    }
	} 
	
</script>
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmContractNodeCheckController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
		<input id="finishUserid" name="finishUserid" type="hidden" value="${tPmContractNodeCheckPage.finishUserid }">
		<input id="createBy" name="createBy" type="hidden" value="${tPmContractNodeCheckPage.createBy }">
		<input id="createName" name="createName" type="hidden" value="${tPmContractNodeCheckPage.createName }">
		<input id="createDate" name="createDate" type="hidden" value="${tPmContractNodeCheckPage.createDate }">
		<input id="updateBy" name="updateBy" type="hidden" value="${tPmContractNodeCheckPage.updateBy }">
		<input id="updateName" name="updateName" type="hidden" value="${tPmContractNodeCheckPage.updateName }">
		<input id="updateDate" name="updateDate" type="hidden" value="${tPmContractNodeCheckPage.updateDate }">
		<input id="contractNodeId" name="contractNodeId" type="hidden" value="${tPmContractNodeCheckPage.contractNodeId }">
		<input id="contactId" name="contactId" type="hidden" value="${tPmContractNodeCheckPage.contactId }">
		<input id="dutyDepartid" name="dutyDepartid" type="hidden" value="${tPmContractNodeCheckPage.dutyDepartid }">
		<input id="id" name="id" type="hidden" value="${tPmContractNodeCheckPage.id }">
		<input id="projectId"  type="hidden" value="${projectId }">
    <input id="organizationUnitid" name="organizationUnitid" type="hidden" value="${tPmContractNodeCheckPage.organizationUnitid }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right" style="width: 88px;"><label class="Validform_label"> 合同编号: </label></td>
				<td class="value"><input id="contactNum" name="contactNum" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.contactNum}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同编号</label></td>
				<td align="right" style="width: 88px;"><label class="Validform_label"> 会计编码: </label></td>
				<td class="value"><input id="accountingCode" name="accountingCode" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.accountingCode}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会计编码</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 项目名称: </label></td>
				<td class="value"><input id="projectName" name="projectName" type="text" style="width: 150px" class="inputxt" datatype="*" value='${tPmContractNodeCheckPage.projectName}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">项目名称</label></td>
				<td align="right"><label class="Validform_label"> 密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级: </label><font color="red">*</font></td>
				<td class="value"><t:codeTypeSelect name="secretDegree" type="select" codeType="0" code="XMMJ" id="secretDegree" defaultVal="${tPmContractNodeCheckPage.secretDegree}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">密级</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 合同名称: </label></td>
				<td class="value"><input id="contractName" name="contractName" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.contractName}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同名称</label></td>
               
                <c:if test="${inOutFlag eq 'i' }">
				<td align="right"><label class="Validform_label"> 合同类型: </label></td>
				<td class="value"><t:codeTypeSelect name="contractType" type="select" codeType="1" code="HTLB" id="contractType" defaultVal="${tPmContractNodeCheckPage.contractType}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同类型</label></td>
                </c:if>
                <c:if test="${inOutFlag eq 'o' }">
				<td align="right"><label class="Validform_label"> 合同类别: </label></td>
				<td class="value"><input value="科研采购合同" readonly="readonly" style="width: 150px;"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同类别</label></td>
                </c:if>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 合同乙方: </label></td>
				<td class="value"><input id="contactSecondParty" name="contactSecondParty" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.contactSecondParty}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同乙方</label></td>
				<td align="right"><label class="Validform_label"> 合同价款: </label></td>
				<td class="value"><input id="contactAmount" name="contactAmount" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.contactAmount}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">合同价款</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 签订时间: </label></td>
				<td class="value"><input id="contractSigningTime" name="contractSigningTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmContractNodeCheckPage.contractSigningTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">签订时间</label></td>
				<td align="right"><label class="Validform_label"> 节点价款: </label></td>
				<td class="value"><input id="nodeAmount" name="nodeAmount" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.nodeAmount}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">节点价款</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 节点时间: </label></td>
				<td class="value"><input id="nodeTime" name="nodeTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmContractNodeCheckPage.nodeTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">节点时间</label></td>
				<td align="right"><label class="Validform_label"> 验收时间: </label></td>
				<td class="value"><input id="checkTime" name="checkTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tPmContractNodeCheckPage.checkTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">验收时间</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 组织单位: </label></td>
				<td class="value"><input id="organizationUnitname" name="organizationUnitname" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.organizationUnitname}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">组织单位名称</label></td>
				<td align="right"><label class="Validform_label"> 责任单位: </label></td>
				<td class="value"><input id="dutyDepartname" name="dutyDepartname" type="text" style="width: 150px" class="inputxt" value='${tPmContractNodeCheckPage.dutyDepartname}'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">责任单位名称</label></td>
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
                <table style="max-width: 360px;" id="fileShow">
                  <c:forEach items="${tPmContractNodeCheckPage.attachments}" var="file" varStatus="idx">
                    <tr style="height: 30px;">
                      <td>
                        <a href="javascript:void(0);"
                          onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tPmContractNodeCheckPage.attachmentCode}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                        &nbsp;&nbsp;&nbsp;
                      </td>
                      <td style="width: 40px;">
                        <a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a>
                      </td>
                      <td style="width: 60px;">
                        <a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a>
                      </td>
                    </tr>
                  </c:forEach>

                </table>
                <div>
                  <div class="form" id="filediv"></div>
                  <t:upload name="fiels" id="file_upload"  buttonText="添加文件" formData="bid,projectId"
                    uploader="commonController.do?saveUploadFiles&businessType=outcomeNodeCheck" dialog="false" auto="true" onUploadSuccess="updateUploadSuccess"></t:upload>
                </div>
              </td>
            </tr>
		</table>
		<%-- <div style="width: auto; height: 200px;">
			<div style="width: 780px; height: 1px;">
				<t:tabs id="tt" iframe="false" tabPosition="top" fit="false">
					<t:tab href="tPmContractNodeCheckController.do?getStepList&id=${tPmContractNodeCheckPage.id}" icon="icon-search" title="流转步骤" id="tOReceiveBillStep" width="700"></t:tab>
				</t:tabs>
			</div>
		</div> --%>
	</t:formvalid>
</body>
<script src="webpage/com/kingtake/project/contractnodecheck/tPmContractNodeCheck.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>