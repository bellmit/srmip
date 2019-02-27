<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>蓝色讲坛信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  //编写自定义JS代码
  function uploadFile(data) {
      frameElement.api.opener.reloadTable();
      frameElement.api.close();
  }
  function close() {
    frameElement.api.close();
  }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBBlueRoomController.do?doSave" tiptype="1" callback="@Override uploadFile">
    <input id="id" name="id" type="hidden" value="${tBBlueRoomPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBBlueRoomPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBBlueRoomPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBBlueRoomPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBBlueRoomPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBBlueRoomPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBBlueRoomPage.updateDate }">
    <input id="confirmFlag" name="confirmFlag" type="hidden" value="${tBBlueRoomPage.confirmFlag }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td width="108" align="right">
          <label class="Validform_label"> 名称: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="roomName" name="roomName" type="text" placeholder="请输入名称" style="width: 450px" datatype="byterange" max="80" min="1"  class="inputxt" value='${tBBlueRoomPage.roomName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 级别: </label>
        </td>
        <td width="184" class="value">
          <t:codeTypeSelect name="roomLevel" type="select" codeType="1" code="LSJTJB" id="roomLevel">
          </t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">级别</label>
        </td>
        <td width="109" align="right">
          <label class="Validform_label"> 活动规模: </label>
        </td>
        <td width="192" class="value">
          <input id="activityScale" name="activityScale" type="text" placeholder="参加活动的人数" style="width: 150px" datatype="n0-6"  class="inputxt" value='${tBBlueRoomPage.activityScale}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">活动规模</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 举办单位名称: </label>
        </td>
        <td class="value">
          <input id="holdUnitId" name="holdUnitId" type="hidden" value="${tBBlueRoomPage.holdUnitId }">
          <input id="holdUnitName" name="holdUnitName" value="${tBBlueRoomPage.holdUnitName }" type="hidden">
          <t:departComboTree id="departCombo1" nameInput="holdUnitName" width="155" idInput="holdUnitId" value="${tBBlueRoomPage.holdUnitName}"></t:departComboTree>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">举办单位名称</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 主办单位名称: </label>
        </td>
        <td class="value">
          <input id="hostUnitId" name="hostUnitId" type="hidden" value="${tBBlueRoomPage.hostUnitId }">
          <input id="hostUnitName" name="hostUnitName" value="${tBBlueRoomPage.hostUnitName }" type="hidden">
          <t:departComboTree id="departCombo2" nameInput="hostUnitName" width="155" idInput="hostUnitId" value="${tBBlueRoomPage.hostUnitName}"></t:departComboTree>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">主办单位名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 举办地点: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="holdAddress" name="holdAddress" type="text" style="width: 450px" datatype="byterange" max="150" min="0"  class="inputxt" value='${tBBlueRoomPage.holdAddress}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">举办地点</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 报告时间: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="beginReportTime" name="beginReportTime" type="text" datatype="*"  style="width: 150px" class="Wdate" onClick="WdatePicker()"
            value='<fmt:formatDate value='${tBBlueRoomPage.beginReportTime}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">报告时间</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 推荐单位名称: </label>
        </td>
        <td class="value" >
          <input id="recommendUnitId" name="recommendUnitId" type="hidden" value="${tBBlueRoomPage.recommendUnitId }">
          <input id="recommendUnitName" name="recommendUnitName" value="${tBBlueRoomPage.recommendUnitName }" type="hidden">
          <t:departComboTree id="departCombo3" nameInput="recommendUnitName" width="155" idInput="recommendUnitId" value="${tBBlueRoomPage.recommendUnitName}"></t:departComboTree>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">推荐单位名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 报告人姓名: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="reporterName" name="reporterName" type="text" style="width: 150px" datatype="byterange" max="36" min="1" class="inputxt" value='${tBBlueRoomPage.reporterName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">报告人姓名</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 担任职务: </label>
        </td>
        <td class="value" colspan="3">
          <input id="postPosition" name="postPosition" type="text" style="width: 150px" datatype="byterange" max="20" min="0" class="inputxt" value='${tBBlueRoomPage.postPosition}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">担任职务</label>
        </td>
      <tr>
        <td align="right">
          <label class="Validform_label"> 报告内容: </label>
          <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="reportContent" name="reportContent" placeholder="请填写简要报告内容" datatype="byterange" max="500" min="1" style="width: 450px" class="inputxt" rows="3">${tBBlueRoomPage.reportContent}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">报告内容</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 备注: </label>
        </td>
        <td class="value" colspan="3">
          <textarea id="memo" name="memo" placeholder="请填写备注信息" datatype="byterange" max="500" min="1" style="width: 450px" class="inputxt" rows="3">${tBBlueRoomPage.memo}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">备注</label>
        </td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件: </label></td>
          <td class="value">
            <table id="fileShow" style="max-width: 92%;">
              <c:forEach items="${tBBlueRoomPage.certificates }" var="file" varStatus="idx">
                <tr>
                  <td><a href="javascript:void(0)" >${file.attachmenttitle}</a></td>
                  <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
                  <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <input type="hidden" value="${tBBlueRoomPage.attachmentCode}" id="bid" name="attachmentCode">
              <div class="form" id="filediv"></div>
              <t:upload name="fiels" id="file_upload"  buttonText="添加文件"  formData="bid" auto="true" dialog="false" onUploadSuccess="uploadSuccess" 
                uploader="commonController.do?saveUploadFilesToFTP&businessType=tBBlueRoom" ></t:upload>
            </div> <span class="Validform_checktip"> </span> <label class="Validform_label" style="display: none;"> 附件 </label>
          </td>
        </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/blueroom/tBBlueRoom.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>