<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBDeptChangeConfirmList" checkbox="true" fitColumns="true" title="部门调动确认" actionUrl="tBDeptChangeConfirmController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="用户"  field="userName"  queryMode="single" replace="基本信息_1,过程管理_2"  width="120"></t:dgCol>
   <t:dgCol title="原部门"  field="deptOName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="现部门"  field="deptNName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="确认状态"  field="confirmStatus" replace="待确认_0,同意_1,驳回_2"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt exp="confirmStatus#eq#0" title="确认" funname="passAudit(id)"></t:dgFunOpt>
   <t:dgFunOpt exp="confirmStatus#eq#0" title="驳回" funname="rejectAudit(id)"></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//提交审核
 function passAudit(id) {
     if (typeof (windowapi) == 'undefined') {
         $.dialog.confirm("确定通过吗？", function() {
             doPass(id);
         }, function() {
         }).zindex();
     } else {
         W.$.dialog.confirm("确定通过吗？", function() {
             doPass(id);
         }, function() {
         }, windowapi).zindex();
     }
 }

 //审核不通过
 function rejectAudit(id) {
     if (typeof (windowapi) == 'undefined') {
         $.dialog.confirm("确定驳回吗？", function() {
             openMsgDialog(id);
         }, function() {
         }).zindex();
     } else {
         W.$.dialog.confirm("确定驳回吗？", function() {
             openMsgDialog(id);
         }, function() {
         }, windowapi).zindex();
     }
 }

 //打开弹窗填写修改意见
 function openMsgDialog(id) {
     var url = "tBDeptChangeConfirmController.do?goPropose&id=" + id;
     var title = "填写意见";
     $.dialog({
         content : 'url:' + url,
         title : '提出意见',
         lock : true,
         opacity : 0.3,
         width : '450px',
         height : '120px',
         ok : function() {
             iframe = this.iframe.contentWindow;
             var msgText = iframe.getMsgText();
             var proposeIframe = iframe;
             $.ajax({
                 url : 'tBDeptChangeConfirmController.do?doPropose',
                 data : {
                     id : id,
                     msgText : msgText
                 },
                 type : 'post',
                 dataType : 'json',
                 success : function(data) {
                     tip(data.msg);
                     if (data.success) {
                         reloadTable();
                     }
                 }
             });
         },
         cancel : function() {
             reloadTable();
         },
     }).zindex();
 }

 //审核
 function doPass(id, opt) {
     $.ajax({
         url : 'tBDeptChangeConfirmController.do?doPass',
         type : 'GET',
         dataType : 'json',
         cache : 'false',
         data : {
             'id' : id
         },
         success : function(data) {
             tip(data.msg);
             if (data.success) {
                 reloadTable();
             }
         }
     });
 }
 </script>