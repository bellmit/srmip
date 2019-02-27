<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>供方名录审核</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function callback(data) {
    $("#bid").val(data.obj.id);
    if ($(".uploadify-queue-item").length > 0) {
      upload();
    }else{
        var win = frameElement.api.opener;
        if (data.success) {
            win.reloadTable();
            frameElement.api.close();
            win.tip(data.msg);
        }else{
            tip(data.msg);
        }
    }
  }
  
function refresh(){
    var win = frameElement.api.opener;
    win.reloadTable();
    frameElement.api.close();
    win.tip("保存资质审核信息成功！");
}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tPmQualitySupplierController.do?doUpdate" tiptype="1" callback="@Override callback">
    <input id="id" name="id" type="hidden" value="${tPmQualitySupplierPage.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label">
            审核人: <font color="red">*</font>
          </label></td>
        <td class="value" ><input id="auditUserId" name="auditUserId" type="hidden"  class="inputxt"
            value='${tPmQualitySupplierPage.auditUserId}'> <input id="auditUserName" name="auditUserName" type="text" readonly="readonly" style="width: 165px;" class="inputxt" datatype="*"
            value='${tPmQualitySupplierPage.auditUserName}'> <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName" isclear="true" inputTextname="auditUserId,auditUserName"
            idInput="auditUserId" mode="single"></t:chooseUser> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审核人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 审核时间: <font color="red">*</font></label></td>
        <td class="value"><input id="auditTime" name="auditTime" type="text" readonly="true" style="width: 165px" class="Wdate" onClick="WdatePicker()" datatype="*"
            value='<fmt:formatDate value='${tPmQualitySupplierPage.auditTime}' type="date" pattern="yyyy-MM-dd"/>'> <span
            class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">审核时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 失效时间:</label><font color="red">*</font></td>
        <td class="value" ><input id="supplierTime" name="supplierTime" type="text" readonly="true"  style="width: 165px" class="Wdate" onClick="WdatePicker()" datatype="*"
            value='<fmt:formatDate value='${tPmQualitySupplierPage.supplierTime}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">失效时间</label></td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label">
            资质材料审核情况: <font color="red">*</font>
          </label></td>
        <td class="value"><textarea id="qualityContent" name="qualityContent"  datatype="byterange" max="4000" min="1" style="width: 508px;height: 100px;" class="inputxt"
            >${tPmQualitySupplierPage.qualityContent}</textarea> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">资质材料审核情况</label></td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label"> 附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;&nbsp; </label>
        </td>
        <td colspan="3" class="value">
          <input type="hidden" value="${tPmQualitySupplierPage.id}" id="bid" name="bid">
          <table style="max-width: 80%;">
            <c:forEach items="${tPmQualitySupplierPage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px; background-color: #F2F7FE;">
                <td>
                  <a href="javascript:void(0);"
                    onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tPmQualitySupplierPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;
                </td>
                <td style="width: 40px;">
                  <a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a>
                </td>
                <td style="width: 40px;">
                  <a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a>
                </td>
              </tr>
            </c:forEach>
          </table>
          <div class="form" id="filediv"></div>
          <t:upload name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" buttonText="添加文件" formData="bid"
            uploader="commonController.do?saveUploadFiles&businessType=supplier" dialog="false" callback="refresh">
          </t:upload>
        </td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/project/supplier/tPmQualitySupplier.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>