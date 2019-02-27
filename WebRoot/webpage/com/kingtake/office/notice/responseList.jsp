<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <input id="noticeId" type="hidden" value="${noticeId }">
  <div class="easyui-tabs" fit="true">
  <div title="留言">
  <t:datagrid name="responseList" checkbox="true" fitColumns="true" title='<font color="red" style="font-size: 20px;">注：该面板针对该通知公告发给他人后，向接收人进行回复。</font>'
     idField="id" fit="true"  actionUrl="tONoticeController.do?responseList&id=${noticeId}" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="留言人id"  field="resUserId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="留言人"  field="resUserName"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="留言时间"  field="resTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="留言内容"  field="resContent" overflowView="true" width="120"></t:dgCol>
   <t:dgToolBar title="留言" icon="icon-redo" funname="goResponse"></t:dgToolBar>  
  </t:datagrid>
  </div>
  </div>
  <div title="通知公告" data-options='href:"tONoticeController.do?goUpdate&load=detail&id=${noticeId}",fit:true'></div>
  </div>
 <script type="text/javascript">
//进行回复
 function goResponse(){
 var id = $("#noticeId").val();
 var url = 'tONoticeController.do?goResponse';
 var width = 640;
 var height = 180;
 var title = "回复";
 if (typeof (windowapi) == 'undefined') {
     $.dialog({
         content : 'url:' + url,
         lock : true,
         width : width,
         height : height,
         title : title,
         opacity : 0.3,
         cache : false,
         ok : function() {
             iframe = this.iframe.contentWindow;
             var resContent = iframe.getResContent();
             if (resContent == "") {
                tip("请填写回复内容！");
                return;
             }
             doResponse(id, resContent);
         },
         cancelVal : '关闭',
         cancel : true
     }).zindex();
 } else {
     W.$.dialog({
         content : 'url:' + url,
         lock : true,
         width : width,
         height : height,
         parent : windowapi,
         title : title,
         opacity : 0.3,
         cache : false,
         ok : function() {
             iframe = this.iframe.contentWindow;
             var resContent = iframe.getResContent();
             if (resContent == "") {
                tip("请填写回复内容！");
                return;
             }
             doResponse(id, resContent);
         },
         cancelVal : '关闭',
         cancel : true
     }).zindex();
 }
 }

 //回复
 function doResponse(id,resContent){
 $.ajax({
     url:'tONoticeController.do?doResponse&type=sender',
     data:{
         id:id,
         resContent:resContent
     },
     cache:false,
     type:'POST',
     dataType:'json',
     success:function(data){
         tip(data.msg);
         if(data.success){
             $("#responseList").datagrid("reload");
         }
     }
 });
 }

 </script>