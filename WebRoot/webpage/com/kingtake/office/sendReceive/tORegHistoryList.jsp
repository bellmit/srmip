<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uid = user.getId();
request.setAttribute("uid", uid);
request.setAttribute("unCreated", ReceiveBillConstant.BILL_UNCREATED);
request.setAttribute("flowing", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tORegHistoryList" checkbox="false" 
  	fitColumns="true" title="公文历史版本列表" idField="id" fit="true" queryMode="group"
  	actionUrl="tOSendReceiveRegController.do?datagridForHistory&regId=${regId}">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"></t:dgCol>
   <t:dgCol title="标题"  field="title" sortable="false"  width="280" ></t:dgCol>
   <t:dgCol title="创建人"  field="createUserName" sortable="false" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
   <t:dgFunOpt title="打包下载" funname="doZip(id)" ></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript">
 $(function(){
     
 });
 
//更新公文
 function doZip(id){
     var url = "tOSendReceiveRegController.do?doHistoryZip&gzip=false";
 	url += '&id='+id;
 	window.open(url);
 }
</script>