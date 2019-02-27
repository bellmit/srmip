<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>论文保密申请信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
	
	function close(){
		frameElement.api.close();
	}
	
	//获取编号
    function getSerialNum() {
        var concreteDeptId = $("#concreteDeptId").val();
        var subordinateDeptId = $("#subordinateDeptId").val();
        $.ajax({
            url : 'tBThesisSecretController.do?getSerectCode',
            data : {
                  'concreteDeptId' : concreteDeptId,
                  'subordinateDeptId' : subordinateDeptId
               },
            cache : false,
            type : 'POST',
            dataType:'json',
            success : function(data) {
                  $('#reviewNumber').val(data.obj);
            }
        });
      }
</script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBThesisSecretController.do?doCheck" tiptype="1"  >
					<input id="id" name="id" type="hidden" value="${tBThesisSecretPage.id }">
					<input id="createBy" name="createBy" type="hidden" value="${tBThesisSecretPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${tBThesisSecretPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${tBThesisSecretPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBThesisSecretPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${tBThesisSecretPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBThesisSecretPage.updateDate }">
					<input id="checkFlag" name="checkFlag" type="hidden" value="${tBThesisSecretPage.checkFlag }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right" width="20%"><label class="Validform_label"> 编号: </label></td>
				<td class="value" colspan="3"><input id="reviewNumber" name="reviewNumber" value="${tBThesisSecretPage.reviewNumber }" datatype="byterange" max="50" min="0" type="text" style="width: 150px" class="inputxt" readonly="readonly"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">编号</label>
				<a id="getNumBtn" class="easyui-linkbutton" href="javascript:getSerialNum();" icon="icon-edit">生成编号</a>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 论文题目: <font color="red">*</font></label></td>
				<td class="value" colspan="3"><input id="thesisTitle" name="thesisTitle" value="${tBThesisSecretPage.thesisTitle }" datatype="byterange" max="200" min="1" type="text" style="width: 525px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">论文题目</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 具体单位: <font color="red">*</font></label></td>
				<td class="value">
				<input id="concreteDeptId" name="concreteDeptId" type="hidden" value="${tBThesisSecretPage.concreteDeptId }"datatype="*">
				<input id="concreteDeptName" name="concreteDeptName" type="hidden" value="${tBThesisSecretPage.concreteDeptName }">
				<input id="concreteDeptCombo" name="concreteDeptCombo" value="${tBThesisSecretPage.concreteDeptName }"  type="text" style="width: 150px" class="inputxt">
				<script type="text/javascript">
		                	//选择承研单位时，将承研单位的父单位加入责任单位
							$(function(){
								$('#concreteDeptCombo').combotree({
									url : 'departController.do?getDepartTree&lazy=false',
									width : '155',
									height : '27',
									multiple : false,
									onSelect : function(record){
										$("#concreteDeptId").val(record.id);
										$("#concreteDeptName").val(record.text);
										var tree = $('#concreteDeptCombo').combotree('tree');
										var parent = tree.tree('getParent', record.target);
										if(parent != null){
											$("#subordinateDeptId").val(parent.id);
											$("#subordinateDeptName").val(parent.text);
											$("#departCombo").combotree('setValue', parent.text);
										}
									}
								});
							});
						</script>
				 <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">具体单位</label></td>
				<td align="right"><label class="Validform_label"> 所属院、直属系: </label></td>
				<td class="value">
				<input id="subordinateDeptId" name="subordinateDeptId" type="hidden" value="${tBThesisSecretPage.subordinateDeptId }">
				<input id="subordinateDeptName" name="subordinateDeptName" value="${tBThesisSecretPage.subordinateDeptName }" type="hidden">
				<t:departComboTree id="departCombo" nameInput="subordinateDeptName" width="150px" idInput="subordinateDeptId" value="${tBThesisSecretPage.subordinateDeptName}"></t:departComboTree>
				<span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">所属院、直属系</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 字数: </label></td>
				<td class="value"><input id="wordCount" name="wordCount" value="${tBThesisSecretPage.wordCount }" datatype="n0-8" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">字数</label></td>
				<td align="right"><label class="Validform_label"> 密级: </label><font color="red">*</font></td>
				<td class="value"><t:codeTypeSelect name="secretDegree" type="select" codeType="0" code="XMMJ" id="secretDegree" defaultVal="${tBThesisSecretPage.secretDegree }"></t:codeTypeSelect><span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">密级</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 承办单位及地点</label></td>
				<td class="value" colspan="3"><input id="undertakeUnitName" name="undertakeUnitName" value="${tBThesisSecretPage.undertakeUnitName }" datatype="byterange" max="60" min="0" type="text" style="width: 525px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">承办单位及地点</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 拟发表刊物名称</label></td>
				<td class="value" colspan="3"><input id="publicationName" name="publicationName" value="${tBThesisSecretPage.publicationName }" datatype="byterange" max="200" min="0" type="text" style="width: 525px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">拟发表刊物名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 会议名称: </label></td>
				<td class="value" colspan="3"><input id="meetingName" name="meetingName" value="${tBThesisSecretPage.meetingName }" datatype="byterange" max="200" min="0" type="text" style="width: 525px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">会议名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 征文名称: </label></td>
				<td class="value" colspan="3"><input id="articleName" name="articleName" value="${tBThesisSecretPage.articleName }" datatype="byterange" max="200" min="0" type="text" style="width: 525px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">征文名称</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 征文单位: </label></td>
				<td class="value" colspan="3"><input id="articleDepart" name="articleDepart" value="${tBThesisSecretPage.articleDepart }" datatype="byterange" max="50" min="0" type="text" style="width: 525px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">征文单位</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 固定联系电话:</label></td>
				<td class="value"><input id="fixTelephone" name="fixTelephone" value="${tBThesisSecretPage.fixTelephone }" datatype="byterange" max="20" min="0" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">移动联系电话</label></td>
				<td align="right"><label class="Validform_label"> 移动联系电话:<font color="red">*</font></label></td>
				<td class="value"><input id="mobileTelephone" name="mobileTelephone" value="${tBThesisSecretPage.mobileTelephone }" datatype="byterange" max="20" min="1" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">移动联系电话</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 第一作者姓名:<font color="red">*</font> </label></td>
				<td class="value"><input id="firstAuthor" name="firstAuthor" value="${tBThesisSecretPage.firstAuthor }" datatype="byterange" max="36" min="1" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">作者姓名</label></td>
				<td align="right"><label class="Validform_label"> 第二作者姓名:</label></td>
				<td class="value"><input id="otherAuthor" name="otherAuthor" value="${tBThesisSecretPage.otherAuthor }" datatype="byterange" max="36" min="0" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">作者姓名</label></td>
			</tr>
			<tr>
			    <td align="right"><label class="Validform_label"> 审查专家:</label></td>
				<td class="value"><input id="checkExpert" name="checkExpert" value="${tBThesisSecretPage.checkExpert }" datatype="byterange" max="100" min="0" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审查专家</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 论文内容要点: </label></td>
				<td class="value" colspan="3"><textarea id="thesisContentKey" name="thesisContentKey"  style="width: 525px" class="inputxt" rows="3">${tBThesisSecretPage.thesisContentKey }</textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">论文内容要点</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label></td>
				<td class="value" colspan="3"><textarea id="memo" name="memo"  style="width: 525px" class="inputxt" rows="3">${tBThesisSecretPage.memo}</textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 附件: </label>
				</td>
				<td colspan="2" class="value"><input type="hidden"
					value="${tBThesisSecretPage.attachmentCode}" id="bid"
					name="attachmentCode" />
					<table id="fileShow" style="max-width: 515px;">
						<c:forEach items="${tBThesisSecretPage.certificates}" var="file"
							varStatus="idx">
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
						<t:upload queueID="filediv" name="fiels" id="file_upload"
							buttonText="添加文件" formData="bid" auto="true" dialog="false"
							onUploadSuccess="uploadSuccess"
							uploader="commonController.do?saveUploadFilesToFTP&businessType=tBThesisSecret"></t:upload>
					</div></td>
			</tr>
		</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/kingtake/project/thesis/tBThesisSecret.js"></script>
  <script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>		