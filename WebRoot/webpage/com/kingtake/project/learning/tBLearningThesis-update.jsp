<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>学术论文信息表</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
<script type="text/javascript">
    var flag = false;
    var fileitem = "";
    var fileKey = "";
    var serverMsg = "";
    var uploadCount = 0;
    var m = new Map();
    $(function() {
        $('#file_upload1').uploadify(
                {
                    buttonText : '上传全文电子版',
                    auto : true,
                    progressData : 'speed',
                    multi : true,
                    height : 25,
                    width : 120,
                    overrideEvents : [ 'onDialogClose' ],
                    fileTypeDesc : '文件格式:',
                    queueID : 'filediv1',
                    fileTypeExts : '*.*;',
                    fileSizeLimit : '500MB',
                    swf : 'plug-in/uploadify/uploadify.swf',
                    uploader : 'commonController.do?saveUploadFilesToFTP&businessType=eEdition',
                    onUploadStart : function(file) {
                        var bid = $('#bid').val();
                        var projectId = $('#projectId').val();
                        $('#file_upload1').uploadify("settings", "formData", {
                            'bid' : bid,
                            'projectId':projectId
                        });
                    },
                    onQueueComplete : function(queueData) {
                    },
                    onUploadSuccess : function(file, data, response) {
                        var d = $.parseJSON(data);
                        updateUploadSuccessTable("filediv1",d,file,response);
                    },
                    onFallback : function() {
                        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                    },
                    onSelectError : function(file, errorCode, errorMsg) {
                        switch (errorCode) {
                        case -100:
                            tip("上传的文件数量已经超出系统限制的" + $('#file_upload1').uploadify('settings', 'queueSizeLimit')
                                    + "个文件！");
                            break;
                        case -110:
                            tip("文件 [" + file.name + "] 大小超出系统限制的"
                                    + $('#file_upload1').uploadify('settings', 'fileSizeLimit') + "大小！");
                            break;
                        case -120:
                            tip("文件 [" + file.name + "] 大小异常！");
                            break;
                        case -130:
                            tip("文件 [" + file.name + "] 类型不正确！");
                            break;
                        }
                    },
                    onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                    }
                });

        $('#file_upload2').uploadify(
                {
                    buttonText : '上传期刊封面扫描照片',
                    auto : true,
                    progressData : 'speed',
                    multi : true,
                    height : 25,
                    width : 150,
                    overrideEvents : [ 'onDialogClose' ],
                    fileTypeDesc : '文件格式:',
                    queueID : 'filediv2',
                    fileTypeExts : '*.*;',
                    fileSizeLimit : '500MB',
                    swf : 'plug-in/uploadify/uploadify.swf',
                    uploader : 'commonController.do?saveUploadFilesToFTP&businessType=coverPic',
                    onUploadStart : function(file) {
                        var bid = $('#bid').val();
                        var projectId = $('#projectId').val();
                        $('#file_upload2').uploadify("settings", "formData", {
                            'bid' : bid,
                            'projectId':projectId
                        });
                    },
                    onQueueComplete : function(queueData) {
                    },
                    onUploadSuccess : function(file, data, response) {
                        var d = $.parseJSON(data);
                        updateUploadSuccessTable("filediv2",d,file,response);
                    },
                    onFallback : function() {
                        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                    },
                    onSelectError : function(file, errorCode, errorMsg) {
                        switch (errorCode) {
                        case -100:
                            tip("上传的文件数量已经超出系统限制的" + $('#file_upload2').uploadify('settings', 'queueSizeLimit')
                                    + "个文件！");
                            break;
                        case -110:
                            tip("文件 [" + file.name + "] 大小超出系统限制的"
                                    + $('#file_upload2').uploadify('settings', 'fileSizeLimit') + "大小！");
                            break;
                        case -120:
                            tip("文件 [" + file.name + "] 大小异常！");
                            break;
                        case -130:
                            tip("文件 [" + file.name + "] 类型不正确！");
                            break;
                        }
                    },
                    onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                    }
                });

        $('#file_upload3').uploadify(
                {
                    buttonText : '上传期刊目录扫描照片',
                    auto : true,
                    progressData : 'speed',
                    multi : true,
                    height : 25,
                    width : 150,
                    overrideEvents : [ 'onDialogClose' ],
                    fileTypeDesc : '文件格式:',
                    queueID : 'filediv3',
                    fileTypeExts : '*.*;',
                    fileSizeLimit : '500MB',
                    swf : 'plug-in/uploadify/uploadify.swf',
                    uploader : 'commonController.do?saveUploadFilesToFTP&businessType=directoryPic',
                    onUploadStart : function(file) {
                        var bid = $('#bid').val();
                        var projectId = $('#projectId').val();
                        $('#file_upload3').uploadify("settings", "formData", {
                            'bid' : bid,
                            'projectId':projectId
                        });
                    },
                    onQueueComplete : function(queueData) {
                    },
                    onUploadSuccess : function(file, data, response) {
                        var d = $.parseJSON(data);
                        updateUploadSuccessTable("filediv3",d,file,response);
                    },
                    onFallback : function() {
                        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                    },
                    onSelectError : function(file, errorCode, errorMsg) {
                        switch (errorCode) {
                        case -100:
                            tip("上传的文件数量已经超出系统限制的" + $('#file_upload3').uploadify('settings', 'queueSizeLimit')
                                    + "个文件！");
                            break;
                        case -110:
                            tip("文件 [" + file.name + "] 大小超出系统限制的"
                                    + $('#file_upload3').uploadify('settings', 'fileSizeLimit') + "大小！");
                            break;
                        case -120:
                            tip("文件 [" + file.name + "] 大小异常！");
                            break;
                        case -130:
                            tip("文件 [" + file.name + "] 类型不正确！");
                            break;
                        }
                    },
                    onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                    }
                });

        $('#file_upload4').uploadify(
                {
                    buttonText : '上传论文首页扫描照片',
                    auto : true,
                    progressData : 'speed',
                    multi : true,
                    height : 25,
                    width : 150,
                    overrideEvents : [ 'onDialogClose' ],
                    fileTypeDesc : '文件格式:',
                    queueID : 'filediv4',
                    fileTypeExts : '*.*;',
                    fileSizeLimit : '500MB',
                    swf : 'plug-in/uploadify/uploadify.swf',
                    uploader : 'commonController.do?saveUploadFilesToFTP&businessType=homePagePic',
                    onUploadStart : function(file) {
                        var bid = $('#bid').val();
                        var projectId = $('#projectId').val();
                        $('#file_upload4').uploadify("settings", "formData", {
                            'bid' : bid,
                            'projectId':projectId
                        });
                    },
                    onQueueComplete : function(queueData) {
                    },
                    onUploadSuccess : function(file, data, response) {
                        var d = $.parseJSON(data);
                        updateUploadSuccessTable("filediv4",d,file,response);
                    },
                    onFallback : function() {
                        tip("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试")
                    },
                    onSelectError : function(file, errorCode, errorMsg) {
                        switch (errorCode) {
                        case -100:
                            tip("上传的文件数量已经超出系统限制的" + $('#file_upload4').uploadify('settings', 'queueSizeLimit')
                                    + "个文件！");
                            break;
                        case -110:
                            tip("文件 [" + file.name + "] 大小超出系统限制的"
                                    + $('#file_upload4').uploadify('settings', 'fileSizeLimit') + "大小！");
                            break;
                        case -120:
                            tip("文件 [" + file.name + "] 大小异常！");
                            break;
                        case -130:
                            tip("文件 [" + file.name + "] 类型不正确！");
                            break;
                        }
                    },
                    onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                    }
                });

        var researchEdit = $("#researchEdit").val();
        var bpmStatus = $("#bpmStatus").val();
        if (researchEdit == '1') {
            //无效化所有表单元素，只能进行查看
            $("#departArea").find(":input").attr("disabled", "true");
        }
        if (bpmStatus == '3') {
            //无效化所有表单元素，只能进行查看
            $(":input").attr("disabled", "true");
            //隐藏选择框和清空框
            $("a[icon='icon-search']").css("display", "none");
            $("a[icon='icon-redo']").css("display", "none");
            $("a[icon='icon-save']").css("display", "none");
            $("a[icon='icon-ok']").css("display", "none");
            //隐藏下拉框箭头
            $(".combo-arrow").css("display", "none");
            //隐藏添加附件
            $("#filediv").parent().css("display", "none");
            //隐藏附件的删除按钮
            $(".jeecgDetail").parent().css("display", "none");
            //隐藏easyui-linkbutton
            $(".easyui-linkbutton").css("display", "none");
        }
    });

    function uploadFiles(data) {
            frameElement.api.opener.reloadTable();
            frameElement.api.opener.tip(data.msg);
            frameElement.api.close();
    }
    function cancel() {
        $('#file_upload1').uploadify('cancel', '*');
        $('#file_upload2').uploadify('cancel', '*');
        $('#file_upload3').uploadify('cancel', '*');
        $('#file_upload4').uploadify('cancel', '*');
    }

</script>
</head>
<body>
  <input id="researchEdit" type="hidden" value="${researchEdit}">
  <input id="bpmStatus" type="hidden" value="${tBLearningThesisPage.bpmStatus}">
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tBLearningThesisController.do?doAddUpdate" tiptype="1" callback="@Override uploadFiles">
    <input id="createBy" name="createBy" type="hidden" value="${tBLearningThesisPage.createBy }">
    <input id="createName" name="createName" type="hidden" value="${tBLearningThesisPage.createName }">
    <input id="createDate" name="createDate" type="hidden" value="${tBLearningThesisPage.createDate }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBLearningThesisPage.updateBy }">
    <input id="updateName" name="updateName" type="hidden" value="${tBLearningThesisPage.updateName }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBLearningThesisPage.updateDate }">
    <input id="id" name="id" type="hidden" value="${tBLearningThesisPage.id }">
    
    <table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right">
          <label class="Validform_label">中文题名:</label><font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="titleCn" name="titleCn" type="text" style="width: 85%" class="inputxt" value='${tBLearningThesisPage.titleCn}' datatype="byterange" max="300" min="1"  
          title="300字以内" placeholder="300字以内">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">中文题名</label>
        </td>
       </tr>
       <tr>
        <td align="right">
          <label class="Validform_label"> 英文题名:</label>&nbsp;
        </td>
        <td class="value" colspan="3">
          <input id="titleEn" name="titleEn" type="text" style="width: 85%" class="inputxt" value='${tBLearningThesisPage.titleEn}' datatype="*1-300" ignore="ignore"
          placeholder="300字以内">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">英文题名</label>
        </td>
      </tr>
      <tr>
        <td align="right" style="width: 20%;">
          <label class="Validform_label"> 关联项目:</label>&nbsp;
        </td>
        <td class="value" style="width: 35%;" >
          <input id="projectId" name="project.id" type="hidden" value='${tBLearningThesisPage.project.id}'>
          <input id="projectName" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.project.projectName}' readonly="true">
          <t:chooseProject inputId="projectId" inputName="projectName" mode="single"></t:chooseProject>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关联项目</label>
        </td>
        <td align="right" style="width: 15%;">
          <label class="Validform_label"> 关联保密: </label>
        </td>
        <td class="value" style="width: 30%;" >
          <input id="thesisSecret" name="secretNumber" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.secretNumber}' placeholder="请输入保密证明编号" datatype="*"
            ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关联保密</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 期刊分类: </label>&nbsp;
        </td>
        <td class="value">
          <t:codeTypeSelect name="periodicalType" type="select" codeType="1" code="QKFL" id="periodicalType" defaultVal="${tBLearningThesisPage.periodicalType}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">期刊分类</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 会议论文分类: </label>
        </td>
        <td class="value">
          <t:codeTypeSelect name="articleType" type="select" codeType="1" code="HYLWFL" id="articleType" defaultVal="${tBLearningThesisPage.articleType}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">会议论文分类</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">密级: <font color="red">*</font>
          </label>
        </td>
        <td class="value">
          <t:codeTypeSelect id="secretCode" name="secretCode" type="select" codeType="0" code="WXBMDJ" defaultVal="${tBLearningThesisPage.secretCode}"></t:codeTypeSelect>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">密级</label>
        </td>
        <td align="right">
          <label class="Validform_label">具体单位:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="concreteDepart" name="concreteDepart" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.concreteDepart}' datatype="*1-30">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">具体单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">第一作者:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="authorFirstId" name="authorFirst.id" type="hidden"  value='${tBLearningThesisPage.authorFirst.id}' >
          <input id="authorFirst" name="authorFirstName" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.authorFirstName}' datatype="*1-18">
          <t:chooseUser icon="icon-search" title="人员列表" isclear="false" mode="single" idInput="authorFirstId"
                    textname="id,realName" inputTextname="authorFirstId,authorFirstName"></t:chooseUser>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一作者</label>
        </td>
        <td align="right">
          <label class="Validform_label">第一作者所属院系:</label><font color="red">*</font>
        </td>
        <td class="value">
          <t:departComboTree name="authorFirstDepartName" id="authorFirstDepartName" idInput="authorFirstDepartId" lazy="false" value="${tBLearningThesisPage.authorFirstDepart.departname}" width="155"></t:departComboTree>
          <input id="authorFirstDepartId" name="authorFirstDepart.id" type="hidden" value='${tBLearningThesisPage.authorFirstDepart.id}' datatype="*">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一作者所属院系</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 第二作者: </label>&nbsp;
        </td>
        <td class="value">
          <input id="authorSecond" name="authorSecond" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.authorSecond}' datatype="*1-18" ignore="ignore" title="若无可不填"
            placeholder="若无可不填">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第二作者</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 第三作者: </label>
        </td>
        <td class="value">
          <input id="authorThird" name="authorThird" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.authorThird}' datatype="*1-18" ignore="ignore" title="若无可不填"
            placeholder="若无可不填">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第三作者</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 其他作者: </label>&nbsp;
        </td>
        <td class="value">
          <input id="authorOther" name="authorOther" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.authorOther}' datatype="*1-60" ignore="ignore" title="若无可不填"
            placeholder="若无可不填">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">其他作者</label>
        </td>
        <td align="right">
          <label class="Validform_label">发表时间:</label><font color="red">*</font>
        </td>
        <td class="value">
          <input id="publishTime" name="publishTime" type="text" onClick="WdatePicker({dateFmt: 'yyyy-MM-dd'})" datatype="date" style="width: 150px" class="inputxt Wdate"
            value='<fmt:formatDate value='${tBLearningThesisPage.publishTime}' type="date" pattern="yyyy-MM-dd"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">发表时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 期刊名称:</label><font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <input id="magazineName" name="magazineName" type="text" style="width: 85%" class="inputxt" value='${tBLearningThesisPage.magazineName}' datatype="byterange" max="60" min="1" 
          title="60字以内" placeholder="60字以内">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">期刊名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 卷: </label>&nbsp;
        </td>
        <td class="value">
          <input id="volumeNum" name="volumeNum" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.volumeNum}' datatype="n1-10" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">卷</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 期: </label>
        </td>
        <td class="value">
          <input id="phaseNum" name="phaseNum" type="text" style="width: 150px" class="inputxt" datatype="n1-5" ignore="ignore" value='${tBLearningThesisPage.phaseNum}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">期</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">文章页码(区间值):  </label><font color="red">*</font>
         
        </td>
        <td class="value">
          <input id="pageNum" name="pageNum" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.pageNum}' datatype="*1-15">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">文章页码</label>
        </td>
        <td align="right">
          <label class="Validform_label"> 资助基金: </label>
        </td>
        <td class="value">
          <input id="sustentationFund" name="sustentationFund" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.sustentationFund}' datatype="*1-60" ignore="ignore">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">资助基金</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 收录情况: </label>&nbsp;
        </td>
        <td class="value" colspan="3">
          <table style="width: 100%">
            <tr>
              <td style="width: 80px;">索引名称：</td>
              <td>
                <t:codeTypeSelect id="indexName" name="indexName" type="select" codeType="1" code="SLQKSYMC" defaultVal="${tBLearningThesisPage.indexName}"></t:codeTypeSelect>
                <label class="Validform_label" style="display: none;">索引名称</label>
              </td>
            </tr>
            <tr>
              <td>收录号：</td>
              <td>
                <input id="collectionNum" name="collectionNum" type="text" style="width: 150px" class="inputxt" value='${tBLearningThesisPage.collectionNum}' datatype="byterange" max="25" min="0" ignore="ignore">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">收录号</label>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 关键词:</label> <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="keyword" name="keyword" style="width: 85%" class="inputxt" datatype="byterange" max="200" min="1" title="多个关键词请用分号分隔，如关键词1；关键词2" placeholder="多个关键词请用分号分隔，如关键词1；关键词2">${tBLearningThesisPage.keyword}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">关键词</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 摘要:</label> <font color="red">*</font>
        </td>
        <td class="value" colspan="3">
          <textarea id="summary" style="width: 85%;" class="inputxt" rows="6" name="summary" datatype="byterange" max="800" min="1">${tBLearningThesisPage.summary}</textarea>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">摘要</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 上传全文电子版:&nbsp;&nbsp; </label>
        </td>
        <td colspan="3" class="value">
          <input type="hidden" value="${tBLearningThesisPage.attachmentCode }" id="bid" name="attachmentCode" />
          <table style="max-width: 360px;">
            <c:forEach items="${tBLearningThesisPage.editions}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td>
                  <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                </td>
                <td style="width: 60px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>

          </table>
          <div>
            <div class="form jeecgDetail" id="filediv1"></div>
            <span id="file_upload1span">
              <input type="file" name="fiels" id="file_upload1" />
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 上传期刊封面扫描照片:&nbsp;&nbsp; </label>
        </td>
        <td colspan="3" class="value">
          <table style="max-width: 360px;">
            <c:forEach items="${tBLearningThesisPage.coverPictures}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td>
                  <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                </td>
                <td style="width: 60px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form jeecgDetail" id="filediv2"></div>
            <span id="file_upload2span">
              <input type="file" name="fiels" id="file_upload2" />
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 上传期刊目录扫描照片:&nbsp;&nbsp; </label>
        </td>
        <td colspan="3" class="value">
          <table style="max-width: 360px;">
            <c:forEach items="${tBLearningThesisPage.directoryPictures}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td>
                  <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                </td>
                <td style="width: 60px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form jeecgDetail" id="filediv3"></div>
            <span id="file_upload3span">
              <input type="file" name="fiels" id="file_upload3" />
            </span>
          </div>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 上传论文首页扫描照片:&nbsp;&nbsp; </label>
        </td>
        <td colspan="3" class="value">
          <table style="max-width: 360px;">
            <c:forEach items="${tBLearningThesisPage.homePagePictures}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td>
                  <a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a>
                </td>
                <td style="width: 60px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form jeecgDetail" id="filediv4"></div>
            <span id="file_upload4span">
              <input type="file" name="fiels" id="file_upload4" />
            </span>
          </div>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/learning/tBLearningThesis.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src = "webpage/com/kingtake/common/upload/fileUpload.js"></script>