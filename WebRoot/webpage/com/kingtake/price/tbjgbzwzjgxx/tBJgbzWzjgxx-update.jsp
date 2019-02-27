<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>物资价格信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	//编写自定义JS代码
	function saveCallback(data) {
		$("#bid").val(data.obj.id);
		if ($(".uploadify-queue-item").length > 0) {
			upload();
		} else {
			var win;
			var dialog = W.$.dialog.list['processDialog'];
			if (dialog == undefined) {
				win = frameElement.api.opener;
			} else {
				win = dialog.content;
			}
			if (data.success) {
				win.reloadTable();
				frameElement.api.close();
			}
		}

	}

	function uploadCallback(data) {
		var win = frameElement.api.opener;
		win.reloadTable();
		frameElement.api.close();
		win.tip("保存成功!");
	}
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgbzWzjgxxController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tBJgbzWzjgxxPage.id }">
    <input id="createName" name="createName" type="hidden" value="${tBJgbzWzjgxxPage.createName }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgbzWzjgxxPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgbzWzjgxxPage.createDate }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgbzWzjgxxPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgbzWzjgxxPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgbzWzjgxxPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 当前期数: </label></td>
        <td class="value"><input id="dqqs" name="dqqs" type="text" style="width: 200px" class="inputxt" datatype="byterange" min="0" max="50" value='${tBJgbzWzjgxxPage.dqqs}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">当前期数</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 总第期数: </label></td>
        <td class="value"><input id="zqs" name="zqs" type="text" style="width: 200px" class="inputxt" datatype="byterange" min="0" max="50" value='${tBJgbzWzjgxxPage.zqs}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">总第期数</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td class="value"><input type="hidden" value="${tBJgbzWzjgxxPage.id}" id="bid" name="bid" />
          <table style="max-width: 430px;">
            <c:forEach items="${tBJgbzWzjgxxPage.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);"
                  onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tBJgbzWzjgxxPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload queueID="filediv" name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" dialog="false" buttonText="添加文件" formData="bid"
              uploader="commonController.do?saveUploadFiles&businessType=tBJgwzxx" callback="uploadCallback">
            </t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgbzwzjgxx/tBJgbzWzjgxx.js"></script>