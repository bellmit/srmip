<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<style>
<!--
.btnPosition{
  margin: 8 20;
}
-->
</style>
<t:base type="jquery,easyui,tools,bootstrap"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 0px;">
    <input type="hidden" value="${projectId}" id="projectId">
    <div style="height:30px;">1、课题组进行项目确认操作，点击“项目确认”即表示已确认。</div>
    <div style="height:30px;">2、课题组确认后会告知该项目分管参谋发送项目已经课题组确认信息。</div>
    
    <div class="btnPosition">
      <button type="button" class="btn btn-primary btn-lg btn-block" onclick="confirmProj()">请点击进行确认</button>
    </div>
  </div>
</div>
<script type="text/javascript">
  function confirmProj() {
    $.ajax({
      url : "tPmProjectController.do?researchGroupConfirm",
      type : "POST",
      dataType : "json",
      data : {
        "id" : "${projectId}"
      },
      cache : false,
      success : function(data) {
        $.messager.show({
          title : '提示',
          msg : data.msg,
          timeout : 3000,
          showType : 'show',
          style : {
            right : '',
            top : document.body.scrollTop + document.documentElement.scrollTop,
            bottom : ''
          }
        });
      }
    });
  }
</script>