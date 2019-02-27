<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学术著作信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function uploadFile(data) {
            frameElement.api.opener.reloadTable();
            frameElement.api.opener.tip(data.msg);
            frameElement.api.close();
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tBLearningBookController.do?doAddUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBLearningBookPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBLearningBookPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBLearningBookPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBLearningBookPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBLearningBookPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBLearningBookPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBLearningBookPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label"> 著作名:</label> <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="bookName" name="bookName" type="text" style="width: 80%;" class="inputxt" value='${tBLearningBookPage.bookName}' datatype="byterange" max="300" min="1" 
          title="300字以内" placeholder="300字以内">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">著作名</label>
        </td>
        <td width="15%"></td>
        <td width="35%"></td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 关联项目: </label>
        </td>
        <td class="value">
          <input id="projectId" name="project.id" type="hidden" value='${tBLearningBookPage.project.id}'>
          <input id="projectName" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.project.projectName}' readonly="true">
          <t:chooseProject inputId="projectId" inputName="projectName" mode="single"></t:chooseProject>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关联项目</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 关联保密: </label>
        </td>
        <td class="value">
          <input id="thesisSecret" name="secretNumber" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.secretNumber}' placeholder="请输入保密证明编号" datatype="*"
            ignore="ignore" >
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关联保密</label>
        </td>
      </tr>
      <tr>
        <td align="right" style="width: 15%;">
          <label class="Validform_label">第一作者:</label><font color="red">*</font>
        </td>
        <td class="value"  style="width: 35%;">
          <input id="authorFirstId" name="authorFirst.id" type="hidden"  value='${tBLearningThesisPage.authorFirst.id}' >
          <input id="authorFirst" name="authorFirstName" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.authorFirstName}' datatype="*1-18">
          <t:chooseUser icon="icon-search" title="人员列表" isclear="false" mode="single" idInput="authorFirstId"
                    textname="id,realName" inputTextname="authorFirstId,authorFirstName"></t:chooseUser>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一作者</label>
        </td>
        <td align="right" style="width: 15%;">
          <label class="Validform_label">第一作者所属院系:</label><font color="red">*</font>
        </td>
        <td class="value" style="width: 35%;">
          <t:departComboTree name="authorFirstDepartName" id="authorFirstDepartName" idInput="authorFirstDepartId" lazy="false" value="${tBLearningBookPage.authorFirstDepart.departname}" width="155"></t:departComboTree>
          <input id="authorFirstDepartId" name="authorFirstDepart.id" type="hidden" value='${tBLearningBookPage.authorFirstDepart.id}' datatype="*">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一作者所属院系</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 第二作者: </label>&nbsp;
        </td>
        <td class="value">
          <input id="authorSecond" name="authorSecond" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.authorSecond}' datatype="*1-18" ignore="ignore" title="若无可不填"
            placeholder="若无可不填">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第二作者</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 第三作者: </label>
        </td>
        <td class="value">
          <input id="authorThird" name="authorThird" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.authorThird}' datatype="*1-18" ignore="ignore" title="若无可不填"
            placeholder="若无可不填">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第三作者</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 其他作者: </label>
        </td>
        <td class="value">
          <input id="authorOther" name="authorOther" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.authorOther}' datatype="*1-60" ignore="ignore" title="若无可不填"
            placeholder="若无可不填">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">其他作者</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 具体单位: </label>&nbsp;
        </td>
        <td class="value">
          <input id="concreteDepart" name="concreteDepart" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.concreteDepart}' datatype="*1-30" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">具体单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 中图分类号: </label>
        </td>
        <td class="value">
          <input id="classNum" name="classNum" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.classNum}' datatype="*1-15" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">中图分类号</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 密级: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="secretCode" name="secretCode" type="select" codeType="0" code="WXBMDJ" defaultVal="${tBLearningBookPage.secretCode}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">密级</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 类型: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect name="bookType" type="select" codeType="1" code="XSZZLX" id="bookType" defaultVal="${tBLearningBookPage.bookType}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">类型</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 出版社: </label>
        </td>
        <td class="value">
          <input id="publisher" name="publisher" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.publisher}' datatype="*1-30" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">出版社</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> ISBN号: </label>
        </td>
        <td class="value">
          <input id="isbnNum" name="isbnNum" type="text" style="width: 150px" class="inputxt" value='${tBLearningBookPage.isbnNum}' datatype="byterange" max="20" min="1" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">ISBN号</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 出版年份: </label>
        </td>
        <td class="value">
          <input id="publishYear" name="publishYear" type="text" datatype="n" ignore="ignore" style="width: 150px" class="inputxt Wdate" onClick="WdatePicker({dateFmt: 'yyyy'})"
            value='${tBLearningBookPage.publishYear}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">出版年份</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">关键词:</label> <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="keyword" style="width: 80%" class="inputxt" name="keyword" datatype="byterange" max="200" min="1" title="多个关键词请用分号分隔，如关键词1；关键词2" placeholder="多个关键词请用分号分隔，如关键词1；关键词2">${tBLearningBookPage.keyword}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关键词</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">内容摘要:</label><font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="summary" style="width: 80%;" class="inputxt" rows="6" name="summary" datatype="byterange" max="800" min="1" title="800字以内" placeholder="800字以内">${tBLearningBookPage.summary}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">内容摘要</label>
        </td>
        <td align="right">
          <label class="Validform_label"> </label>
        </td>
        <td class="value"></td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBLearningBookPage.uploadFiles }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBLearningBookPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=learningBook" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/learning/tBLearningBook.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>