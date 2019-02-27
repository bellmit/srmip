<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta content="IE=8" http-equiv="X-UA-Compatible" />
<script type="text/javascript" src="plug-in/jquery/jquery-1.8.3.js"></script>
<link href="webpage/com/kingtake/office/sign/StyleSheet.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" language="JavaScript">
    //安全电子印章系统函数
    var ntkosignctl = null; //初始化印章管理控件对象
    var filename = ""; //磁盘印章文件名
    var Signname = "";//印章文件名称
    //初始化控件对象
    function init() {
        ntkosignctl = document.getElementById("ntkosignctl");
    }
    function CreateNewSign() {
        Signname = document.forms(0).SignName.value;
        document.forms(0).filename.value = Signname + ".esp";
        if ((Signname == '') || (undefined == typeof (Signname))) {
            alert('请输入印章名称!');
            return false;
        }
        var Signuser = document.forms(0).SignUser.value;
        if ((Signuser == '') || (undefined == typeof (Signuser))) {
            alert('请输入印章使用人!');
            return false;
        }
        var Password1 = document.forms(0).Password1.value;
        var Password2 = document.forms(0).Password2.value;
        if ((Password1 == '') || (Password2 == '') || (Password1 != Password2) || (undefined == typeof (Password1))) {
            alert('印章口令不能为空或者不一致!');
            return false;
        }
        if(Password1.length<6){
            alert('印章口令至少6位字符!');
            return false;
        }
        var Filename = document.forms(0).Filename.value;
        if ((Filename == '') || (undefined == typeof (Filename))) {
            alert('请选择印章图片，支持JPG,PNG,GIF格式！');
            return false;
        }
        //  alert("应该在此处增加代码，判断用户选择的源文件是否是图片文件。");
        ntkosignctl.CreateNew(Signname, Signuser, Password1, Filename);
        if (0 != ntkosignctl.StatusCode) {
            alert("创建印章错误.");
            return false;
        }
        return true;
    }
    
    //编辑印章文件
    function editesp(url) {
        ntkosignctl.OpenFromURL(url);
        document.forms(0).filename.value = url.substring(url.lastIndexOf("/") + 1, url.length);
        document.forms(0).SignName.value = ntkosignctl.SignName;
        document.forms(0).SignUser.value = ntkosignctl.SignUser;
        document.forms(0).Password1.value = ntkosignctl.PassWord;
        document.forms(0).Password2.value = ntkosignctl.PassWord;
        //ntkosignctl.height = ntkosignctl.SignHeight;
    }

    function savetourl() {
        var flag = CreateNewSign();
        if(!flag){
            return false;
        }
        //在后台，可以根据上传文件的inputname是否为"SIGNFILE"来判断
        //是否是印章控件上传的文件
        var Password1 = document.forms(0).Password1.value;
        var Password2 = document.forms(0).Password2.value;
        filename = document.forms(0).filename.value;
        if ((Password1 == '') || (Password2 == '') || (Password1 != Password2) || (undefined == typeof (Password1))) {
            alert('印章口令不能为空或者不一致');
            return false;
        }
        ntkosignctl.SignName = document.forms(0).SignName.value;
        ntkosignctl.SignUser = document.forms(0).SignUser.value;
        ntkosignctl.PassWord = document.forms(0).Password1.value;
        //SaveToURL方法保存印章文件
        var retStr = ntkosignctl.SaveToURL(document.forms(0).action, "SIGNFILE", "savetype=4", filename, 0);
        //判断是否保存成功，如果成功，刷新窗口
        if (ntkosignctl.StatusCode == 0) {
            alert("保存成功！");
            window.location.reload();
        } else {
            alert(retStr);
        }
    }
    
    //删除印章
    function deleteEsp(id){
        $.ajax({
           url:'signController.do?deleteSign',
           data:{id:id},
           cache:false,
           type:'POST',
           dataType:'json',
           success:function(data){
               if(data.success){
                   alert("删除成功");
                   window.location.reload();
               }else{
                   alert(data.msg);
               }
           }
        });
    }
    
    //查看印章
    /* function viewSign(id){
        $.ajax({
            url:'signController.do?viewSign',
            data:{id:id},
            cache:false,
            type:'POST',
            dataType:'json',
            success:function(data){
                    alert(data.url);
                    window.open(data.url);
            }
         });
    } */
</script>

</head>
<body onload="init()">
  <form id="sealform" method="post" enctype="multipart/form-data" action="signController.do?saveSignature" >
    <div id="default" class="divdefault">
      <div id="top" class="top"></div>
      <div id="maindiv_top" class="maindiv_top">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td></td>
          </tr>
          <tr>
          </tr>
          <tr>
            <td class="tablebackground"></td>
          </tr>
        </table>
      </div>
      <div id="maindiv_middle" class="maindiv_middle">
        <div style="display: none;">
          <script type="text/javascript" src="websigncontrol/signtoolcontrol.js"></script>
        </div>
        <div id="espobject">
          <table>
            <tr>
              <td width="19%">ESP文件名：</td>
              <td width="81%"><input id="filename" name="filename" value="" type="text" disabled="disabled" /></td>
            </tr>
            <tr>
              <td width="19%">印章名称：</td>
              <td width="81%"><input name="SignName" /></td>
            </tr>
            <tr>
              <td>印章使用者：</td>
              <td><input name="SignUser" /></td>
            </tr>
            <tr>
              <td>印章口令：</td>
              <td><input type="password" name="Password1" value=""></td>
            </tr>
            <tr>
              <td>确认口令：</td>
              <td><input type="password" name="Password2" value="" /></td>
            </tr>
            <tr>
              <td>印章源文件：</td>
              <td><input type="file" name="Filename" class="fileup" /></td>
            </tr>
          </table>
          <div id="esp_button_div" class="espbar">
            <ul>
              <li onclick="savetourl();">保存印章</li>
            </ul>
          </div>
        </div>
        <div id="wordlist" class="officelist">
          <span>电子印章文件列表:</span>
          <table class="tabletitle">
            <tr>
              <td width="25%">印&nbsp;章&nbsp;名&nbsp;称</td>
              <td width="25%">创&nbsp;建&nbsp;日&nbsp;期</td>
              <td width="25%">印章图片</td>
              <td width="25%" colspan="2">相&nbsp;关&nbsp;操&nbsp;作</td>
            </tr>
          <c:forEach items="${signList}" var="sign">
          <tr class=mouseout onmouseover='this.className="mouseover"'onmouseout='this.className="mouseout"'>
                  <TD width="25%">${sign.name }</TD>
                  <TD width="25%">${sign.createdate }</TD>
                  <TD width="25%"><img alt="印章" src="${sign.picpath }"></img></TD>
                  <TD width="25%"><A href='javascript:deleteEsp("${sign.id}");'>&nbsp;删除&nbsp;</A></td>
                  <%-- <TD width="25%"><A href='javascript:viewSign("${sign.id}");'>&nbsp;查看&nbsp;</A></td> --%>
                  </tr>
                  </c:forEach>
          </table>
        </div>
      </div>
      <div id="maindiv_bottom" class="maindiv_bottom">
        <div id="conmpanyinfo" class="conmpanyinfo"></div>
      </div>
    </div>
  </form>
</body>
</html>