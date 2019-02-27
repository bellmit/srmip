<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>价格法规库</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
  //编写自定义JS代码
function saveCallback(data) {
        $("#bid").val(data.obj.id);
        if ($(".uploadify-queue-item").length > 0) {
            upload();
        } else {
            var win;
            var dialog = W.$.dialog.list['processDialog'];
            if(dialog==undefined){
                win = frameElement.api.opener;
            }else{
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBJgFgController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tBJgFgPage.id }">
    <input id="createBy" name="createBy" type="hidden" value="${tBJgFgPage.createBy }">
    <input id="createDate" name="createDate" type="hidden" value="${tBJgFgPage.createDate }">
    <input id="createName" name="createName" type="hidden" value="${tBJgFgPage.createName }">
    <input id="updateName" name="updateName" type="hidden" value="${tBJgFgPage.updateName }">
    <input id="updateBy" name="updateBy" type="hidden" value="${tBJgFgPage.updateBy }">
    <input id="updateDate" name="updateDate" type="hidden" value="${tBJgFgPage.updateDate }">
    <table style="width: 100%;" cellpadding="0" cellspacing="5" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 法规名称: </label></td>
        <td class="value"><input id="fgmc" name="fgmc" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgFgPage.fgmc}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">法规名称</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 文号: </label></td>
        <td class="value"><input id="wh" name="wh" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="80" value='${tBJgFgPage.wh}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">文号</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 发布单位: </label></td>
        <td class="value"><input id="fbdw" name="fbdw" type="text" style="width: 400px" class="inputxt" datatype="byterange" min="0" max="200" value='${tBJgFgPage.fbdw}'> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">发布单位</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 发布时间: </label></td>
        <td class="value"><input id="fbsj" name="fbsj" type="text" style="width: 400px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgFgPage.fbsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">发布时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 摘要: </label></td>
        <td class="value"><textarea id="zy" style="width: 400px;" class="inputxt" rows="6" name="zy" datatype="byterange" min="0" max="4000">${tBJgFgPage.zy}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">摘要</label></td>
      <tr>
        <td align="right"><label class="Validform_label"> 施行时间: </label></td>
        <td class="value"><input id="sxsj" name="sxsj" type="text" style="width: 400px" class="Wdate" onClick="WdatePicker()"
          value='<fmt:formatDate value='${tBJgFgPage.sxsj}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">施行时间</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value"><textarea id="beiz" style="width: 400px;" class="inputxt" rows="6" name="beiz" datatype="byterange" min="0" max="1000">${tBJgFgPage.beiz}</textarea> <span class="Validform_checktip"></span> <label
          class="Validform_label" style="display: none;">备注</label></td>
        <td align="right"><label class="Validform_label"> </label></td>
        <td class="value"></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
        <td class="value"><input type="hidden" value="${tBJgFgPage.id}" id="bid" name="bid" />
          <table style="max-width: 430px;">
            <c:forEach items="${tBJgFgPage.certificates}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);"
                    onclick="createDetailChildWindow('预览','commonController.do?priceView&bid=${tBJgFgPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                <td style="width: 40px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload queueID="filediv" name="fiels" id="file_upload" extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;" dialog="false" buttonText="添加文件" formData="bid"
              uploader="commonController.do?saveUploadFiles&businessType=tBJgfg" callback="uploadCallback">
            </t:upload>
          </div></td>
      </tr>      
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/tbjgfg/tBJgFg.js"></script>