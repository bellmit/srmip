<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>申请文件</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<c:if test="${sqwjPage.applyStatus ne 1 and sqwjPage.applyStatus ne 3}">
<style type="text/css">
.ftd{
border: solid skyblue 1px;
width:600px;
height:160px;
}
.txt{
font-size: 30px;
color: red;
}
</style>
</c:if>
<c:if test="${sqwjPage.applyStatus eq 1 or sqwjPage.applyStatus eq 3}">
<style type="text/css">
.ftd{
border: solid skyblue 1px;
width:600px;
height:80px;
}
.txt{
font-size: 20px;
color: red;
}
</style>
</c:if>
<script type="text/javascript" src="webpage/com/kingtake/zscq/sqwj/tZSqwj.js"></script>
<script type="text/javascript">
    $(function(){
        var opt = $("#opt").val();
        var apprStatus = $("#apprStatus").val();
        if(opt==""){
        if(apprStatus=='1'){
            tip("申请文件已发送审查，不可修改！");
        }if(apprStatus=='2'){
            tip("申请文件被驳回修改，请先修改再重新提交！");
        }else if(apprStatus=='3'){
            tip("申请文件已审查完毕，不可修改！");
        }   
       }
    });
    
  //查看修改意见
    function viewMsg(){
        var msgText = $("#msgText").val();
        $.messager.alert('修改意见',msgText);    
    }
</script>
</head>
<body>
  <b><c:if test="${sqwjPage.applyStatus eq '2'}"><a href="#" style="color: red; cursor: pointer; text-decoration: underline;" onclick="viewMsg()">查看修改意见</a></c:if></b>
  <div style="margin: 0 auto; width: 800px;">
    <c:if test="${role eq 'fmr' }">
    <c:if test="${sqwjPage.applyStatus eq '0'||sqwjPage.applyStatus eq '2'}">
      <a class="easyui-linkbutton" style="float:right;" data-options="plain:true,iconCls:'icon-ok'" onclick='submitFile("${sqwjPage.id }")'>提交</a>
    </c:if>
    </c:if>
    <c:if test="${role eq 'depart' }">
    <c:if test="${sqwjPage.applyStatus eq '1'}">
      <a class="easyui-linkbutton" style="float:right;" data-options="plain:true,iconCls:'icon-ok'" onclick='goConfirm("${sqwjPage.id }")'>确认</a>
    </c:if>
    </c:if>
    <input id="id" name="id" type="hidden" value="${sqwjPage.id }">
    <input id="opt" type="hidden" value="${opt}">
    <textarea id="msgText" style="display:none;" >${sqwjPage.msgText }</textarea>
    <input id="apprStatus" type="hidden" value="${sqwjPage.applyStatus}">
    <table>
      <tr>
        <td><span class="txt">请求书：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.qqs }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">说明书摘要：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.smszy }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">摘要附图：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.zyft }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">权利要求书：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.qlyqs }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">说明书：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.sms }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">说明书附图：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.smsft }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">实质审查请求书：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.szscqqs }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">密级证明：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.mjzm }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">费用减缓请求书：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.fyjhqqs }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">费用减缓请求证明：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.fyjhqqzm }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"></iframe></td>
      </tr>
      <tr>
        <td><span class="txt">专利代理委托书：</span></td><td class="ftd"><iframe src="tZSqwjController.do?goFileUpload&bid=${sqwjPage.zldlwts }&id=${sqwjPage.id }&opt=${opt}" frameborder="0" scrolling="no" style="width: 100%; height: 99%;"> </iframe></td>
      </tr>
    </table>
  </div>
</body>