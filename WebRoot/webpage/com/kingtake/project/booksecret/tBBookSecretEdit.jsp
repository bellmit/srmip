<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专著保密信息表</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  function uploadFile(data) {
    $("#bid").val(data.obj.id);
    if ($(".uploadify-queue-item").length > 0) {
      upload();
    } else {
      frameElement.api.opener.reloadTable();
      frameElement.api.close();
    }
  }
  function close() {
    frameElement.api.close();
  }
  function getSerialNum() {
          $.ajax({
              url : 'tBBookSecretController.do?getSerectCode',
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
  <t:formvalid formid="bookSecretEditForm" dialog="true" layout="table" action="tBBookSecretController.do?doSave" tiptype="1" >
    <input id="id" name="id" type="hidden" value="${tBBookSecretPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBBookSecretPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBBookSecretPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBBookSecretPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBBookSecretPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBBookSecretPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBBookSecretPage.updateDate }">
    <table style="width: 625px;" cellpadding="0" cellspacing="0" border="0" class="formtable">
      <tr>
        <td align="right" width="139">
          <label class="Validform_label"> 编号: </label>
          <c:if test="${checkFlag == '1'}">
            <font color="red">*</font>
          </c:if>
        </td>
        <td class="value" colspan="3">
          <c:if test="${checkFlag == '1'}">
            <input id="reviewNumber" name="reviewNumber" value="${tBBookSecretPage.reviewNumber }" datatype="byterange" max="50" min="1" type="text" style="width: 150px" class="inputxt" />
            <span class="Validform_checktip"> </span>
            <label class="Validform_label" style="display: none;"> 编号 </label>
            <a id="numBtn" class="easyui-linkbutton" href="javascript:getSerialNum();" icon="icon-edit">生成编号</a>
            <input id="checkFlag" name="checkFlag" type="hidden" value="1" />
          </c:if>
          <c:if test="${checkFlag != '1'}">
            <input id="checkFlag" name="checkFlag" type="hidden" value="${tBBookSecretPage.checkFlag }">
            <input id="reviewNumber" name="reviewNumber" placeholder="由机关填写" type="text" style="width: 250px" class="inputxt" value='${tBBookSecretPage.reviewNumber}' readonly="true">
          </c:if>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 著作名称: </label>
          <font color="red"> * </font>
        </td>
        <td class="value" colspan="3">
          <input id="bookName" name="bookName" type="text" style="width: 460px" class="inputxt" datatype="byterange" max="200" min="1" value='${tBBookSecretPage.bookName}'>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 著作名称 </label>
        </td>
      </tr>
      <tr>
        <td align="right" width="139">
          <label class="Validform_label"> 具体单位: </label>
          <font color="red"> * </font>
        </td>
        <td width="178" class="value">
          <input id="concreteDeptId" name="concreteDeptId" type="hidden" value="${tBBookSecretPage.concreteDeptId }" datatype="*">
          <input id="concreteDeptName" name="concreteDeptName" type="hidden" value="${tBBookSecretPage.concreteDeptName }">
          <input id="concreteDeptCombo" name="concreteDeptCombo" value="${tBBookSecretPage.concreteDeptName }" type="text" style="width: 145" class="inputxt">
          <script type="text/javascript">
                      //选择承研单位时，将承研单位的父单位加入责任单位
                      $(function() {
                        $('#concreteDeptCombo').combotree({
                          url : 'departController.do?getDepartTree&lazy=false',
                          width : '145',
                          height : '27',
                          multiple : false,
                          onSelect : function(record) {
                            $("#concreteDeptId").val(record.id);
                            $("#concreteDeptName").val(record.text);
                            var tree = $('#concreteDeptCombo').combotree('tree');
                            var parent = tree.tree('getParent', record.target);
                            if (parent != null) {
                              $("#subordinateDeptId").val(parent.id);
                              $("#subordinateDeptName").val(parent.text);
                              $("#departCombo").combotree('setValue', parent.text);
                            }
                          }
                        });
                      });
                    </script>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 具体单位 </label>
        </td>
        <td align="right" width="136">
          <label class="Validform_label"> 所属院系: </label>
          <font color="red"> * </font>
        </td>
        <td width="172" class="value">
          <input id="subordinateDeptId" name="subordinateDeptId" type="hidden" value="${tBBookSecretPage.subordinateDeptId }" datatype="*">
          <input id="subordinateDeptName" name="subordinateDeptName" value="${tBBookSecretPage.subordinateDeptName }" type="hidden">
          <t:departComboTree id="departCombo" nameInput="subordinateDeptName" width="145" idInput="subordinateDeptId" value="${tBBookSecretPage.subordinateDeptName}"></t:departComboTree>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 所属院系 </label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 出版社: </label>
          <font color="red"> * </font>
        </td>
        <td class="value" colspan="3">
          <input id="press" name="press" type="text" style="width: 460px" datatype="byterange" max="120" min="1" class="inputxt" value='${tBBookSecretPage.press}'>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 出版社 </label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 密级: </label><font color="red">*</font>
        </td>
        <td class="value">
          <t:codeTypeSelect name="secretDegree" type="select" codeType="0" code="XMMJ" id="secretDegree">
          </t:codeTypeSelect>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 密级 </label>
        </td>
        <td align="right">
          <label class="Validform_label"> 发行范围:</label>
        </td>
        <td class="value">
          <input id="issueScope" name="issueScope" type="text" datatype="byterange" max="200" min="0" style="width: 140px" class="inputxt" value='${tBBookSecretPage.issueScope}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">发行范围</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 第一作者姓名: </label>
          <font color="red"> * </font>
        </td>
        <td class="value">
          <input id="firstAuthor" name="firstAuthor" type="text" datatype="byterange" max="36" min="1" style="width: 140px" class="inputxt" value='${tBBookSecretPage.firstAuthor}'>
          <t:chooseUser icon="icon-search" title="人员列表" isclear="true" mode="single" idInput="authorFirstId" textname="id,realName" inputTextname="firstAuthorId,firstAuthor"></t:chooseUser>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 第一作者姓名 </label>
        </td>
        <td align="right">
          <label class="Validform_label"> 其他作者姓名: </label>
        </td>
        <td class="value">
          <input id="otherAuthor" name="otherAuthor" type="text" datatype="byterange" max="50" min="0" style="width: 140px" class="inputxt" value='${tBBookSecretPage.otherAuthor}'>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 其他作者姓名 </label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 固定联系电话: </label>
        </td>
        <td class="value">
          <input id="fixTelephone" name="fixTelephone" type="text" style="width: 140px" placeholder="027-88888888" datatype="byterange" max="20" min="0" class="inputxt"
            value='${tBBookSecretPage.fixTelephone}'>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 固定联系电话 </label>
        </td>
        <td align="right">
          <label class="Validform_label"> 移动联系电话: </label>
          <font color="red"> * </font>
        </td>
        <td class="value">
          <input id="mobileTelephone" name="mobileTelephone" type="text" placeholder="13388888888" datatype="m" max="20" min="1" style="width: 140px" class="inputxt"
            value='${tBBookSecretPage.mobileTelephone}'>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 移动联系电话 </label>
        </td>
      </tr>
      <c:if test="${checkFlag eq '1'}">
      <tr>
	      <td align="right"><label class="Validform_label"> 审查专家:</label></td>
		  <td class="value"><input id="checkExpert" name="checkExpert" value="${tBBookSecretPage.checkExpert }" datatype="byterange" max="100" min="0" type="text" style="width: 150px" class="inputxt"> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审查专家</label></td>
	  </tr>
	  </c:if>
      <tr>
        <td align="right">
          <label class="Validform_label"> 著作内容要点: </label>
          <font color="red"> * </font>
        </td>
        <td class="value" colspan="3">
          <textarea id="bookContentKey" name="bookContentKey" placeholder="请填写著作内容要点" datatype="byterange" max="500" min="1" style="width: 460px" class="inputxt" rows="3">${tBBookSecretPage.bookContentKey}</textarea>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 著作内容要点 </label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 备注: </label>
        </td>
        <td class="value" colspan="3">
          <textarea id="memo" name="memo" placeholder="请填写备注" datatype="byterange" max="500" min="0" style="width: 460px" class="inputxt" rows="3">${tBBookSecretPage.memo}</textarea>
          <span class="Validform_checktip"> </span>
          <label class="Validform_label" style="display: none;"> 备注 </label>
        </td>
      </tr>
			<tr>
				<td align="right"><label class="Validform_label">
						上传著作电子版: </label></td>
				<td colspan="2" class="value"><input type="hidden"
					value="${tBBookSecretPage.attachmentCode}" id="bid"
					name="attachmentCode" />
					<table id="fileShow" style="max-width: 515px;">
						<c:forEach items="${tBBookSecretPage.certificates}" var="file"
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
							uploader="commonController.do?saveUploadFilesToFTP&businessType=tBBookSecret"></t:upload>
					</div></td>
			</tr>
		</table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/booksecret/tBBookSecret.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    $("#secretDegree").attr("style", "width:145px;");
  });
</script>
</html>