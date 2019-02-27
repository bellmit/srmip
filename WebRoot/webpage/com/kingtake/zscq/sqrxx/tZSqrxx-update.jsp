<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>申请人信息</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function clearXm(){
        $("#xm").val('');
        $("#userId").val('');
    }
    
    function uidChange(){
    	var uid = $('#userId').val();
    	$.ajax({
    		type : 'post',
    		async : false,
    		url:'tPmProjectMemberController.do?findInfoById',
    		data:{'uid':uid},
    		success : function(data) {
    			var d = $.parseJSON(data);
    			var user = d.attributes.user;
    			$('#dh').val(user.officePhone ? user.officePhone : user.mobilePhone);
    		}
    	});
    }
    
    //回调函数
    function saveCallback(data){
        var dialog = W.$.dialog.list['zlsqDialog'].content;
        dialog.tip(data.msg);
        if(data.success){
           dialog.reloadTable();
        }
        frameElement.api.close();
    }
</script>
</head>
<body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tZSqrxxController.do?doUpdate" tiptype="1" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tZSqrxxPage.id }">
    <input id="zlsqId" name="zlsqId" type="hidden" value="${tZSqrxxPage.zlsqId }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
      <tr>
        <td align="right"><label class="Validform_label"> 姓名: <font color="red">*</font></label></td>
        <td class="value">
        <input id="userId" name="userId" type="hidden" value="${tZSqrxxPage.userId }"> 
        <input id="xm" name="xm" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.xm}' datatype="*1-10" onfocus="clearXm();">
        <t:chooseUser icon="icon-search" title="人员列表" textname="id,realName,departName" 
                        isclear="true" inputTextname="userId,xm,ssbm" 
                        idInput="userId" mode="single" fun="uidChange"></t:chooseUser>
         <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">姓名</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 电话: <font color="red">*</font></label></td>
        <td class="value"><input id="dh" name="dh" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.dh}' datatype="byterange" min="1" max="30"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">电话</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 居民身份证件号码: </label></td>
        <td class="value"><input id="idno" name="idno" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.idno}' datatype="idcard" ignore="ignore"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">居民身份证件号码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 电子邮箱: </label></td>
        <td class="value"><input id="dzyx" name="dzyx" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.dzyx}' datatype="e" ignore="ignore"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">电子邮箱</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 国籍: </label></td>
        <td class="value"><input id="gj" name="gj" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.gj}' datatype="*0-25"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">国籍</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 居所地: </label></td>
        <td class="value"><input id="jsd" name="jsd" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.jsd}' datatype="*0-100"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">居所地</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 邮政编码: </label></td>
        <td class="value"><input id="yzbm" name="yzbm" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.yzbm}' datatype="*0-25"> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">邮政编码</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 详细地址: </label></td>
        <td class="value"><textarea id="xxdz" name="xxdz" style="width: 450px;" class="inputxt" cols="3" rows="5" datatype="*0-500">${tZSqrxxPage.xxdz}</textarea> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">详细地址</label></td>
      </tr>
      <tr>
        <td align="right"><label class="Validform_label"> 所属部门: </label></td>
        <td class="value"><input id="ssbm" name="ssbm" type="text" style="width: 150px" class="inputxt" value='${tZSqrxxPage.ssbm}'> <span class="Validform_checktip"></span> <label
            class="Validform_label" style="display: none;">所属部门</label></td>
      </tr>
    </table>
  </t:formvalid>
</body>
<script src="webpage/com/kingtake/zscq/sqrxx/tZSqrxx.js"></script>