<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专利申请</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    
</script>
</head>
<body>
<div style="margin: 0 auto;width: 600px;">
  <t:formvalid formid="formobj" dialog="true" layout="table" action="tZZlsqController.do?doUpdate" tiptype="1">
    <input id="id" name="id" type="hidden" value="${tZZlsqPage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 归档号: </label></td>
        <td class="value"><input id="gdh" name="gdh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.gdh}' readonly="readonly"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">归档号</label></td>
        <td align="right"><label class="Validform_label"> 完成单位: <font color="red">*</font></label></td>
        <td class="value">
        <input id="wcdw" name="wcdw" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.wcdw}' readonly="readonly">
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">完成单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 类型: <font color="red">*</font></label></td>
        <td class="value">
        <input name="lx" type="text" value="<t:convert codeType="1" code="ZLLX" value="${tZZlsqPage.lx}"></t:convert>" style="width: 150px" class="inputxt" readonly="readonly">
        <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">类型</label></td>
        <td align="right"><label class="Validform_label"> 关联项目: </label></td>
        <td class="value">
        <input type="hidden" id="glxmId" name="glxmId" value="${tZZlsqPage.glxmId}">
        <input id="glxm" name="glxm" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.glxm}' readonly="readonly">
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">关联项目</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 名称: <font color="red">*</font></label></td>
        <td class="value"><input id="mc" name="mc" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.mc}' datatype="*1-100" readonly="readonly"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">名称</label></td>
        <td align="right"><label class="Validform_label"> 发明人: <font color="red">*</font></label></td>
        <td class="value"><input id="fmr" name="fmr" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.fmr}' datatype="*1-250" readonly="readonly"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">发明人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 第一发明人<br/>身份证号: <font color="red">*</font></label></td>
        <td class="value"><input id="dyfmrsfzh" name="dyfmrsfzh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.dyfmrsfzh}' datatype="idcard" readonly="readonly"> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一发明人身份证号</label></td>
        <td align="right"><label class="Validform_label"> 代理机构: <font color="red">*</font></label></td>
        <td class="value">
        <input id="dljgId" name="dljgId" type="hidden" value='${tZZlsqPage.dljgId}' datatype="*">
        <input id="dljgName" type="text" style="width:150px;" readonly="readonly">
        <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">代理机构</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 联系人: <font color="red">*</font></label></td>
        <td class="value"><input id="lxr" name="lxr" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.lxr}' datatype="*1-250" readonly="readonly"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人</label></td>
        <td align="right"><label class="Validform_label"> 联系人电话: <font color="red">*</font></label></td>
        <td class="value"><input id="lxrdh" name="lxrdh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.lxrdh}' datatype="*1-25" readonly="readonly"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人电话</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="bz" style="width: 460px;" class="inputxt" rows="3" name="bz" datatype="byterange" min="0" max="4000" readonly="readonly">${tZZlsqPage.bz}</textarea> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">备注</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 附件:&nbsp;&nbsp; </label></td>
        <td colspan="3" class="value"><input type="hidden" value="${tZZlsqPage.fjbm }" id="bid" name="fjbm" />
        <span style="color:red;font-size: 10px;">1、海军工程大学专利申请保密审批表-纸质版<br/>2、技术交底书</span>
          <table style="max-width: 360px;" id="fileShow">
            <c:forEach items="${tZZlsqPage.attachments}" var="file" varStatus="idx">
              <tr style="height: 30px;">
                <td><a href="javascript:void(0);" >${file.attachmenttitle}</a>
                  &nbsp;&nbsp;&nbsp;</td>
                <td style="width: 40px;"><a href="commonController.do?viewFTPFile&fileid=${file.id}&subclassname=org.jeecgframework.web.system.pojo.base.TSFilesEntity&gzip=0" title="下载">下载</a></td>
              </tr>
            </c:forEach>
          </table>
          </td>
      </tr>
    </table>
  </t:formvalid>
  <div style="margin-top: 10px;border-top: solid skyblue 1px;text-align: center;padding-top: 10px;">
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='sendApplyFile("${tZZlsqPage.id}","${tZZlsqPage.wjzt}")'>递交申请文件</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='addSl("${tZZlsqPage.id}")'>受理</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='goSc("${tZZlsqPage.id}")'>审查</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='goSq("${tZZlsqPage.id}")'>授权</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='addZsdj("${tZZlsqPage.id}")'>证书登记</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='goZhyy("${tZZlsqPage.id}")'>转化应用</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='goJL("${tZZlsqPage.id}")'>奖励</a>
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='goZz("${tZZlsqPage.id}")'>终止</a>
  </div>
  </div>
  
</body>
<script type="text/javascript">
var mc = $("#mc").val();
//提交申请文件
function sendApplyFile(id, apprStatus) {
    /* var buttons = [];
    if(apprStatus=="0"||apprStatus=="2"){
    buttons.push({
            name : '提交',
            focus : true,
            callback : function() {
                submitFile(id);
                return false;
            }
        });
    }
    buttons.push({
        name : '取消'
    }); */
    var url = "tZSqwjController.do?goSqwj&role=fmr&zlsqId=" + id;
    var title = "["+mc+"]递交申请文件";
    addTab(title, url, "default");
    /* var  width = window.top.document.body.offsetWidth;
    var  height = window.top.document.body.offsetHeight - 100;
    if (typeof (windowapi) == 'undefined') {
        $.dialog({
            id:'sqwjDialog',
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            fixed : true,
            cache : false,
            button : buttons
        }).zindex();
    } else {
        W.$.dialog({
            id:'sqwjDialog',
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            parent : windowapi,
            title : title,
            opacity : 0.3,
            fixed : true,
            cache : false,
            button : buttons
        }).zindex();
    } */
}



//受理
function addSl(id){
    var url = "tZSltzsController.do?goUpdate&zlsqId="+id+"&load=detail";
    var title = "受理通知书";
    createdetailwindow(title,url,null,null);
}

//跳转到审查界面
function goSc(id){
    var url = "tZSctzsController.do?tZSctzs&zlsqId="+id+"&role=fmr";
    addTab("["+mc+"]审查", url, "default");
}

//证书登记
function addZsdj(id){
    var url = "tZZsdjController.do?goUpdate&zlsqId="+id+"&load=detail";
    var title = "证书登记";
    createdetailwindow(title,url,null,null);
}

//跳转到授权界面
function goSq(id){
    var url = "tZSqController.do?goUpdate&zlsqId="+id+"&role=fmr";
    var title = "授权";
    var width = 700;
    var height =window.top.document.body.offsetHeight-100;
    $.dialog({
		content: 'url:'+url,
		lock : true,
		width:width,
		height:height,
		title:title,
		opacity : 0.3,
		cache:false,
	    button:[{
	        focus:true,
	        name:"缴费记录",
	        callback:function(){
	            iframe = this.iframe.contentWindow;
	            var sqId = iframe.getId();
	            if(sqId==""){
	                tip("请先保存授权信息！");
	                return false;
	            }
	            var addurl = "tZSqController.do?goJfjlList&sqId="+sqId+"&role=fmr";
	            $.dialog({
	                id:'jfjlDialog',
	    			content: 'url:'+addurl,
	    			lock : true,
	    			width:900,
	    			height:window.top.document.body.offsetHeight-100,
	    			parent:windowapi,
	    			title:"缴费记录",
	    			opacity : 0.3,
	    			cache:false,
	    		    cancelVal: '关闭',
	    		    cancel: true /*为true等价于function(){}*/
	    		}).zindex();
	            return false;
	        }
	    }],
	    cancelVal: '关闭',
	    cancel: true 
	}).zindex();
}

//跳转到转化应用界面
function goZhyy(id){
    var url = "tZZhyyController.do?goZhyyList&zlsqId="+id+"&role=fmr";
    addTab("["+mc+"]转化应用", url, "default");
}

//奖励
function goJL(id){
    var url = "tZZljlController.do?goZljlList&zlsqId="+id+"&role=fmr";
    addTab("["+mc+"]专利奖励", url, "default");
}

//终止
function goZz(id){
    var url = "tZZlzzController.do?goList&zlsqId="+id+"&role=fmr";
    addTab("["+mc+"]专利终止", url, "default");
}
</script>
<script src="webpage/com/kingtake/zscq/zlsq/tZZlsq.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script> 
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>