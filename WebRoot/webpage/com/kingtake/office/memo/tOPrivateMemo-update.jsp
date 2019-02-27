<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>备忘录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <style type="text/css">
    #memoContent {
    display: block;
    /* margin:0 auto; */
    overflow: hidden;
    width: 550px;
    font-size: 14px;
    height: 150px;
    line-height: 24px;
    padding:2px;
    }
    textarea {
    outline: 0 none;
    border-color: rgba(82, 168, 236, 0.8);
    /* box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1), 0 0 8px rgba(82, 168, 236, 0.6); */
    }
  </style>
  <script type="text/javascript">
  //编写自定义JS代码

  /**
   * 文本框根据输入内容自适应高度
   * @param {HTMLElement} 输入框元素
   * @param {Number} 设置光标与输入框保持的距离(默认0)
   * @param {Number} 设置最大高度(可选)
   */
  var autoTextarea = function(elem, extra, maxHeight) {
      extra = extra || 0;
      var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window, isOpera = !!window.opera
              && !!window.opera.toString().indexOf('Opera'), addEvent = function(type, callback) {
          elem.addEventListener ? elem.addEventListener(type, callback, false) : elem.attachEvent(
                  'on' + type, callback);
      }, getStyle = elem.currentStyle ? function(name) {
          var val = elem.currentStyle[name];
          if (name === 'height' && val.search(/px/i) !== 1) {
              var rect = elem.getBoundingClientRect();
              return rect.bottom - rect.top - parseFloat(getStyle('paddingTop'))
                      - parseFloat(getStyle('paddingBottom')) + 'px';
          }
          ;
          return val;
      } : function(name) {
          return getComputedStyle(elem, null)[name];
      }, minHeight = parseFloat(getStyle('height'));
      elem.style.resize = 'none';
      var change = function() {
          var scrollTop, height, padding = 0, style = elem.style;
          if (elem._length === elem.value.length)
              return;
          elem._length = elem.value.length;
          if (!isFirefox && !isOpera) {
              padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
          }
          ;
          scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
          elem.style.height = minHeight + 'px';
          if (elem.scrollHeight > minHeight) {
              if (maxHeight && elem.scrollHeight > maxHeight) {
                  height = maxHeight - padding;
                  style.overflowY = 'auto';
              } else {
                  height = elem.scrollHeight - padding;
                  style.overflowY = 'hidden';
              }
              ;
              style.height = height + extra + 'px';
              scrollTop += parseInt(style.height) - elem.currHeight;
              document.body.scrollTop = scrollTop;
              document.documentElement.scrollTop = scrollTop;
              elem.currHeight = parseInt(style.height);
          }
          ;
      };
      addEvent('propertychange', change);
      addEvent('input', change);
      addEvent('focus', change);
      change();
  };
  function uploadFile(data){
      $("#bid").val(data.obj.id);
      if($(".uploadify-queue-item").length>0){
          upload();
      }else{
          frameElement.api.opener.reloadTable();
          frameElement.api.close();
      }
  }
  
  function close(){
      frameElement.api.close();
  }
  
  $(function() {
    //查看模式情况下,删除和上传附件功能禁止使用
		if(location.href.indexOf("load=detail")!=-1){
			$("#jeecgDetail").hide();
		} 
	});
 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" callback="@Override uploadFile" 
  		action="tOPrivateMemoController.do?doUpdate" tiptype="1">
		<input id="id" name="id" type="hidden" value="${tOPrivateMemoPage.id }">
		<table cellpadding="0" cellspacing="1" class="formtable">
      <tr>
          <td align="right"><label class="Validform_label">录入时间:</label></td>
          <td class="value">
              <input id="createDate" name="createDate" type="text" style="width: 200px"  readonly="readonly"
              	value='<fmt:formatDate value="${tOPrivateMemoPage.createDate }" type="date" 
              	pattern="yyyy-MM-dd HH:mm:ss"/>'>    
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">录入时间</label>
          </td>
      </tr>
      <tr>
      <td align="right"><label class="Validform_label"> 关联文号:</label></td>
        	<td class="value" >
          <input id="accordingNum" name="accordingNum" type="text" style="width: 200px" class="inputxt" 
          	 value="${tOPrivateMemoPage.accordingNum}">
            <t:choose url="tOSendReceiveRegController.do?selectReg" 
            	tablename="tOSendReceiveRegList" 
            	width="800px" height="450px" icon="icon-search" 
            	title="收发文列表" textname="mergeFileNum" 
            	inputTextname="accordingNum" isclear="true" left="50%" top="50%"></t:choose>
          <span class="Validform_checktip"></span> 
          <label class="Validform_label" style="display: none;">关联文号</label>
        	</td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label">关联项目:</label></td>
          <td class="value">
            <input id="projectId" name="projectId" type="hidden" value="${tOPrivateMemoPage.projectId }">
              <input id="projectName" name="projectName" type="text" style="width: 400px" class="inputxt" 
                readonly="readonly" value='${tOPrivateMemoPage.projectName}'>
              <t:choose url="tPmProjectController.do?projectSelect" name="projectList" width="1000px" height="460px" 
                  icon="icon-search" title="项目列表" textname="id,projectName" 
                  inputTextname="projectId,projectName" isclear="true" left="50%" top="50%">
              </t:choose>
              <span class="Validform_checktip"></span>
              <label class="Validform_label" style="display: none;">关联项目</label>
          </td>
      </tr>
			<tr>
				<td align="right"><label class="Validform_label">备忘内容:</label></td>
				<td class="value">
                    <textarea id="memoContent" name="memoContent"  style="width: 500px;" 
                    	class="inputxt" rows="6" >${tOPrivateMemoPage.memoContent}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备忘内容</label>
				</td>
			</tr>
      <tr>
          <td align="right"><label class="Validform_label">附&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;件:&nbsp;&nbsp;</label></td>
          <td colspan="3" class="value"><input type="hidden" value="${tOPrivateMemoPage.id}" id="bid" name="bid" />
            <table style="max-width:550px;">
              <c:forEach items="${tOPrivateMemoPage.certificates}" var="file" varStatus="idx">
                <tr style="height: 30px; background-color:#F2F7FE;">
                  <td><a href="javascript:void(0);"
                    onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tOPrivateMemoPage.id}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>&nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a
                    href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity"
                    title="下载">下载</a></td>
                  <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail"
                    onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
              <div class="form" id="filediv"></div>
              <t:upload queueID="filediv" name="fiels" id="file_upload" buttonText="添加文件" formData="bid"
                uploader="commonController.do?saveUploadFiles&businessType=tOPrivateMemo">
              </t:upload>
            </div>
          </td>
      </tr>
			</table>
		</t:formvalid>
    <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
    <script>
      var text = document.getElementById("memoContent");
      autoTextarea(text);// 调用
  	</script>
 </body>
