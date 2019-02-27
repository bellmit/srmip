<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学术活动信息表</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLearningActivityController.do?doAddUpdate" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBLearningActivityPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBLearningActivityPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBLearningActivityPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBLearningActivityPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBLearningActivityPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBLearningActivityPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBLearningActivityPage.updateDate }">
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" width="15%">
          <label class="Validform_label">名称:</label><font color="red">*</font>
        </td>
        <td class="value" width="35%">
          <input id="activityName" name="activityName" type="text" style="width: 150px" class="inputxt" value='${tBLearningActivityPage.activityName}' datatype="byterange" max="120" min="1">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">名称</label>
        </td>
        <td align="right" width="15%">
          <label class="Validform_label"> 级别: </label>
        </td>
        <td class="value" width="35%">
          <t:codeTypeSelect name="activityLevel" type="select" codeType="1" code="XSHDJB" id="activityLevel" defaultVal="${tBLearningActivityPage.activityLevel}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">级别</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 主办单位: </label>
        </td>
        <td class="value">
          <input id="hostUnit" name="hostUnit" type="text" style="width: 150px" class="inputxt" value='${tBLearningActivityPage.hostUnit}' datatype="*1-60" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主办单位</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 承办单位: </label>
        </td>
        <td class="value">
          <input id="holdingUnit" name="holdingUnit" type="text" style="width: 150px" class="inputxt" value='${tBLearningActivityPage.holdingUnit}' datatype="*1-60" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">承办单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 举办地点:</label> <font color="red">*</font>
        </td>
        <td class="value">
          <input id="address" name="address" type="text" style="width: 150px" class="inputxt" value='${tBLearningActivityPage.address}' datatype="*1-50">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">举办地点</label>
        </td>
        <td align="right">
          <label class="Validform_label">活动规模:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="activityScope" name="activityScope" type="text" style="width: 150px" class="inputxt" value='${tBLearningActivityPage.activityScope}' datatype="n1-20" class="easyui-tooltip"
            placeholder="填写参加活动的人数" title="填写参加活动的人数">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">活动规模</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">活动时间:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="activityTime" name="activityTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date"
            value='<fmt:formatDate value='${tBLearningActivityPage.activityTime}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">活动时间</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 担任职务: </label>
        </td>
        <td class="value">
          <input id="heldJob" name="heldJob" type="text" style="width: 150px" class="inputxt" value='${tBLearningActivityPage.heldJob}' datatype="*1-20" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">担任职务</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">参加人员姓名:</label><font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="attendeeName" name="attendeeName" style="width: 86%;" class="inputxt" datatype="*1-500">${tBLearningActivityPage.attendeeName}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">参加人员姓名</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">活动内容:</label><font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="activityContent" name="activityContent" datatype="byterange" max="2000" min="1" style="width: 86%;">${tBLearningActivityPage.activityContent}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">活动内容</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 备注: </label>
        </td>
        <td class="value" colspan="3">
          <textarea id="memo" name="memo" datatype="byterange" max="1000" min="1" ignore="ignore" style="width: 86%;">${tBLearningActivityPage.memo}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value" colspan="3">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBLearningActivityPage.uploadFiles }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBLearningActivityPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=learningActivity" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/learning/tBLearningActivity.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>