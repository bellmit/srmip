<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>专利申请</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function(){
        $("#dljgName").combobox({
            url:'tZDljgxxController.do?getDljgList',
            editable:false,
            valueField:'id',
            textField:'text',
            onLoadSuccess:function(){
                var dljgId = $("#dljgId").val();
                if(dljgId!=""){
                    $(this).combobox('setValue',dljgId);
                }
            },
            onChange:function(newValue, oldValue){
                $("#dljgId").val(newValue);
            }
        });
    });
    
    function clearProj(){
        $("#glxmId").val('');
        $("#glxm").val('');
    }
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
        <input id="wcdw" name="wcdw" type="hidden" value='${tZZlsqPage.wcdw}' >
        <t:departComboTree id="wcdwId" name="wcdwId" idInput="wcdwId" nameInput="wcdw" value="${tZZlsqPage.wcdwId}"
                        lazy="false" width="156" ></t:departComboTree>
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">完成单位</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 类型: <font color="red">*</font></label></td>
        <td class="value">
        <t:codeTypeSelect name="lx" type="select" codeType="1" code="ZLLX" id="lx" defaultVal="${tZZlsqPage.lx}" extendParam='{style:"width: 156px","datatype":"*"}'></t:codeTypeSelect> 
        <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">类型</label></td>
        <td align="right"><label class="Validform_label"> 关联项目: </label></td>
        <td class="value">
        <input type="hidden" id="glxmId" name="glxmId" value="${tZZlsqPage.glxmId}">
        <input id="glxm" name="glxm" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.glxm}' onfocus="clearProj()">
        <t:choose url="tPmProjectController.do?projectSelect" width="1000px" height="460px" left="10%" top="10%"
              name="projectList" icon="icon-search" title="项目列表" textname="id,projectName" inputTextname="glxmId,glxm"
              isclear="true"></t:choose>
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">关联项目</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 名称: <font color="red">*</font></label></td>
        <td class="value"><input id="mc" name="mc" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.mc}' datatype="*1-100"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">名称</label></td>
        <td align="right"><label class="Validform_label"> 发明人: <font color="red">*</font></label></td>
        <td class="value"><input id="fmr" name="fmr" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.fmr}' datatype="*1-250"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">发明人</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 第一发明人<br/>身份证号: <font color="red">*</font></label></td>
        <td class="value"><input id="dyfmrsfzh" name="dyfmrsfzh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.dyfmrsfzh}' datatype="idcard"> <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">第一发明人身份证号</label></td>
        <td align="right"><label class="Validform_label"> 代理机构: <font color="red">*</font></label></td>
        <td class="value">
        <input id="dljgId" name="dljgId" type="hidden" value='${tZZlsqPage.dljgId}' datatype="*">
        <input id="dljgName" type="text" style="width:156px;">
        <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">代理机构</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 联系人: <font color="red">*</font></label></td>
        <td class="value"><input id="lxr" name="lxr" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.lxr}' datatype="*1-250"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人</label></td>
        <td align="right"><label class="Validform_label"> 联系人电话: <font color="red">*</font></label></td>
        <td class="value"><input id="lxrdh" name="lxrdh" type="text" style="width: 150px" class="inputxt" value='${tZZlsqPage.lxrdh}' datatype="*1-25"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">联系人电话</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 备注: </label></td>
        <td class="value" colspan="3"><textarea id="bz" style="width: 460px;" class="inputxt" rows="3" name="bz" datatype="byterange" min="0" max="4000">${tZZlsqPage.bz}</textarea> <span class="Validform_checktip"></span> <label
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
                <td style="width: 60px;"><a href="javascript:void(0)" class="jeecgDetail" onclick="delFTPFile('commonController.do?delFTPFile&id=${file.id}',this)">删除</a></td>
              </tr>
            </c:forEach>
          </table>
          <div>
            <div class="form" id="filediv"></div>
            <t:upload name="fiels" id="file_upload" buttonText="添加文件" formData="bid" uploader="commonController.do?saveUploadFilesToFTP&businessType=zlsq" dialog="false" auto="true"
              onUploadSuccess="uploadSuccess"></t:upload>
          </div></td>
      </tr>
    </table>
  </t:formvalid>
  <div style="margin-top: 10px;border-top: solid skyblue 1px;text-align: center;padding-top: 10px;">
  <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick='sendApplyFile("${tZZlsqPage.id}")'>递交申请文件</a>
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
function sendApplyFile(id) {
    /* var buttons = [];
    buttons.push({
        name:'提交',
        callback:function(){
            
        }
    },{
        name : '取消'
    }); */
    var url = "tZSqwjController.do?goSqwj&role=depart&zlsqId=" + id+"&opt=edit";
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
    var url = "tZSltzsController.do?goUpdate&zlsqId="+id;
    var title = "受理通知书";
    createwindow(title,url,null,null);
}

//跳转到审查界面
function goSc(id){
    var url = "tZSctzsController.do?tZSctzs&zlsqId="+id+"&role=depart";
    addTab("["+mc+"]审查", url, "default");
}
//跳转到授权界面
function goSq(id){
    var url = "tZSqController.do?goUpdate&zlsqId="+id+"&role=depart";
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
	        name:"缴费记录",
	        callback:function(){
	            iframe = this.iframe.contentWindow;
	            var sqId = iframe.getId();
	            if(sqId==""){
	                tip("请先保存授权信息！");
	                return false;
	            }
	            var addurl = "tZSqController.do?goJfjlList&sqId="+sqId+"&role=depart";
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
	    },{
	        name:"确定",
	        focus:true,
	        callback:function(){
		    	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    }
	    }],
	    cancelVal: '关闭',
	    cancel: true 
	}).zindex();
}

//证书登记
function addZsdj(id){
    var url = "tZZsdjController.do?goUpdate&zlsqId="+id;
    var title = "证书登记";
    createwindow(title,url,null,null);
}

//跳转到转化应用界面
function goZhyy(id){
    var url = "tZZhyyController.do?goZhyyList&zlsqId="+id+"&role=depart";
    addTab("["+mc+"]转化应用", url, "default");
}

//奖励
function goJL(id){
    var url = "tZZljlController.do?goZljlList&zlsqId="+id+"&role=depart";
    addTab("["+mc+"]专利奖励", url, "default");
}

//终止
function goZz(id){
    var url = "tZZlzzController.do?goList&zlsqId="+id+"&role=depart";
    addTab("["+mc+"]专利终止", url, "default");
}
</script>
<script src="webpage/com/kingtake/zscq/zlsq/tZZlsq.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script> 
<script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
<script src="webpage/com/kingtake/common/upload/fileUpload.js"></script>