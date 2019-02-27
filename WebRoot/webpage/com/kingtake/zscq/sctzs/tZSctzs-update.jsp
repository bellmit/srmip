<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>审查通知书</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/uploadify/css/uploadify.css" type="text/css"></link>
<script type="text/javascript" src="plug-in/uploadify/jquery.uploadify-3.1.js"></script>
<script type="text/javascript" src="plug-in/tools/Map.js"></script>
</head>
<body>
  <div style="margin: 0 auto; width: 650px;">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tZSctzsController.do?doUpdate&opt=${opt}" tiptype="1" beforeSubmit="checkFile">
    <input id="id" name="id" type="hidden" value="${tZSctzsPage.id }">
    <input id="zlsqId" name="zlsqId" type="hidden" value="${tZSctzsPage.zlsqId }">
    <input id="qrzt" type="hidden" value="${tZSctzsPage.qrzt}">
    <input id="opt" type="hidden" value="${opt}">
    <div id="tzsDiv" class="easyui-panel" data-options="title:'通知书信息'" style="height:300px;">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="width: 238px;"><label class="Validform_label">
            通知类型: <font color="red">*</font>
          </label></td>
        <td class="value"><t:codeTypeSelect name="tzlx" type="select" codeType="1" code="ZLSCTZLX" id="tzlx" defaultVal="${tZSctzsPage.tzlx}"></t:codeTypeSelect> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">通知类型</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label">
            发文日: <font color="red">*</font>
          </label></td>
        <td class="value"><input id="fwr" name="fwr" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="date"
            value='<fmt:formatDate value='${tZSctzsPage.fwr}' type="date" pattern="yyyy-MM-dd"/>'> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">发文日</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 要求回复时间: &nbsp;&nbsp;</label></td>
        <td class="value"><input id="yqhfsj" name="yqhfsj" type="text" style="width: 50px;border-top: none;border-left: none;border-right: none;"  datatype="n" ignore="ignore"
            value='${tZSctzsPage.yqhfsj}'>个月<span style="color: red; font-size: 10px;">(不填则不用回复)</span> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">要求回复时间</label></td>
      </tr>
      <tr>
          <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp;&nbsp;&nbsp; </label></td>
          <td colspan="3" class="value"><input type="hidden" value="${tZSctzsPage.fjbm }" id="bid" name="fjbm" /> <span style="color: red; font-size: 10px;">通知书电子稿</span>
            <table style="max-width: 360px;" id="fileShow">
              <c:forEach items="${tZSctzsPage.attachments}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td><a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tZSltzsPage.fjbm}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                  <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
            <div class="form" id="filediv"></div>
            <span id="file_uploadspan"><input type="file" id="file_upload" /></span>
          </div></td>
        </tr>
    </table>
    </div>
    <c:if test="${!empty tZSctzsPage.yqhfsj}">
    <c:if test="${tZSctzsPage.qrzt eq 3}">
    <div id="hfDiv" class="easyui-panel" data-options='title:"回复信息<a onclick=viewMsgText()  style=\"color:red;cursor: pointer;text-decoration: underline;\">查看修改意見</a>"' style="height:250px;">
    </c:if>
    <c:if test="${tZSctzsPage.qrzt ne 3}">
    <div id="hfDiv" class="easyui-panel" data-options='title:"回复信息"' style="height:250px;">
    </c:if>
    <textarea id="xgyj" rows="1" cols="1" style="display: none;">${tZSctzsPage.xgyj}</textarea>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right" style="width: 238px;"><label class="Validform_label">
            是否放弃: <font color="red">*</font>
          </label></td>
        <td class="value">
        <input id="sffq_hidden" type="hidden" value="${tZSctzsPage.sffq}">
        <input id="isFq" type="radio" name="sffq" onclick="fq()" value="1" style="width: 20px;height: 20px;"><label for="isFq" >是</label>
        <input id="notFq" type="radio" name="sffq" onclick="nfq()" value="0" style="width: 20px;height: 20px;"><label for="notFq">否</label>
         <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">是否放弃</label></td>
      </tr>
      <tr id="fqyyTr">
        <td align="right"><label class="Validform_label"> 放弃原因: <font color="red">*</font></label></td>
        <td class="value"><textarea id="fqyy" name="fqyy" rows="3" cols="5" style="height: 50px; width: 300px;">${tZSctzsPage.fqyy }</textarea> <span class="Validform_checktip"></span> <label class="Validform_label"
            style="display: none;">放弃原因</label></td>
      </tr>
      <tr id="yjTR">
          <td align="right"><label class="Validform_label"> 上传意见:<font color="red">*</font> </label></td>
          <td colspan="3" class="value"><input type="hidden" value="${tZSctzsPage.scyj }" id="scyj" name="scyj" /> 
            <table style="max-width: 360px;" id="fileShow2">
              <c:forEach items="${tZSctzsPage.yjattachments}" var="file" varStatus="idx">
                <tr style="height: 30px;">
                  <td><a href="javascript:void(0);"
                      onclick="createDetailChildWindow('预览','commonController.do?goAccessoryTab&bid=${tZSltzsPage.fjbm}&index=${idx.index}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity',1000,700)">${file.attachmenttitle}</a>
                    &nbsp;&nbsp;&nbsp;</td>
                  <td style="width: 40px;"><a href="commonController.do?viewFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity" title="下载">下载</a></td>
                  <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="del('commonController.do?delFile&id=${file.id}',this)">删除</a></td>
                </tr>
              </c:forEach>
            </table>
            <div>
            <div class="form" id="filediv2"></div>
            <span id="file_uploadspan2"><input type="file" id="file_upload2" /></span>
          </div></td>
        </tr>
    </table>
    </div>
    </c:if>
  </t:formvalid>
  </div>
</body>
<script type="text/javascript" src="webpage/com/kingtake/zscq/sctzs/tZSctzs.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
    $(function() {
        if(location.href.indexOf("load=detail")!=-1){
            $("#filediv2").parent().css("display","none");
        }
        var opt = $("#opt").val();
        if(opt=="reply"){
           $("#tzsDiv").find(":input").attr("disabled", "true");
           //隐藏添加附件
           $("#tzsDiv").find("#filediv").parent().css("display", "none");
           //隐藏附件的删除按钮
           $("#tzsDiv").find(".jeecgDetail").parent().css("display", "none");
        }
        //初始化是否放弃
        var sffq_hidden = $("#sffq_hidden").val();
        if(sffq_hidden=="1"){
            $("#isFq").attr("checked","checked");
            $("#yjTR").hide();
            $("#fqyyTr").show();
        }else if(sffq_hidden=="0"){
            $("#notFq").attr("checked","checked");
            $("#yjTR").show();
            $("#fqyyTr").hide();
        }
    });
    
    //放弃
    function fq(){
        $("#yjTR").hide();
        $("#fqyyTr").show();
    }
    
    //不放弃
    function nfq(){
        $("#yjTR").show();
        $("#fqyyTr").hide();
    }
    
    //查看修改意見
    function viewMsgText(){
        var xgyj = $("#xgyj").val();
        $.messager.alert("修改意見",xgyj);
    }
    
    //检查是否上传附件
    function checkFile(){
        if($("input[name='sffq']").length>0){
        var sffq = $("input:checked[name='sffq']").val();
        if(sffq=='0'){
            var len = $("#fileShow2").find("tr").length;
            if(len==0){
                tip("请先上传意见！");
                return false;
            }
        }
      }
    }
</script>