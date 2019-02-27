<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input id="zlsqId" type="hidden" value="${zlsqId}"> 
  <t:datagrid name="zhyyList" checkbox="true" fitColumns="true" title="转化应用列表" actionUrl="tZZhyyController.do?datagrid&zlsqId=${zlsqId}" idField="id" fit="true" queryMode="group" onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="转化类别"  field="zhlb" codeDict="1,ZLZHLB"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="录入人姓名"  field="createName" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="确认状态"  field="qrzt" replace="已生成_0,已提交_1,修改_2,已确认_3"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <c:if test="${role eq 'depart' }">
   <t:dgToolBar title="查看" icon="icon-search" url="tZZhyyController.do?goUpdate" funname="detail" height="200"></t:dgToolBar>
   <t:dgFunOpt exp="qrzt#eq#1" funname="goConfirm(id)" title="确认"></t:dgFunOpt>
   </c:if>
   <c:if test="${role eq 'fmr' }">
   <t:dgDelOpt exp="qrzt#eq#0" title="删除" url="tZZhyyController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tZZhyyController.do?goUpdate&zlsqId=${zlsqId}" funname="add" height="200"></t:dgToolBar>
   <t:dgFunOpt exp="qrzt#eq#0" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#2" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#0" title="提交" funname="doSubmit(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#2" title="提交" funname="doSubmit(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#2" title="查看修改意见" funname="viewMsgText(id)" ></t:dgFunOpt>
   </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//编辑
function goUpdate(id){
    var url = "tZZhyyController.do?goUpdate&id="+id;
    createwindow("编辑",url,null,"200");
}

//确认
function goConfirm(id){
    var url = "tZZhyyController.do?goConfirm&id="+id;
    var title = "确认";
    if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+url,
			lock : true,
			width:500,
			height: 200,
			title:title,
			opacity : 0.3,
			fixed:true,
			cache:false, 
			ok:function(){
			    iframe = this.iframe.contentWindow;
			    var qrzt = iframe.getQrzt();
			    var xgyj = iframe.getMsgText();
			    doConfirm(id,qrzt,xgyj);
			},
			okVal:'确认',
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}else{
		W.$.dialog({
			content: 'url:'+url,
			lock : true,
			width:500,
			height: 200,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			fixed:true,
			cache:false, 
			ok:function(){
			    iframe = this.iframe.contentWindow;
			    var qrzt = iframe.getQrzt();
			    var xgyj = iframe.getMsgText();
			    doConfirm(id,qrzt,xgyj);
			},
			okVal:'确认',
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
}

//确认
function doConfirm(id,qrzt,xgyj){
    $.ajax({
       url:'tZZhyyController.do?doConfirm',
       data:{id:id,qrzt:qrzt,xgyj:xgyj},
       cache:false,
       dataType:'json',
       success:function(data){
           tip(data.msg);
           if(data.success){
               reloadTable();
           }
       }
    });
}

//查看
function goDetail(rowIndex, rowData){
    createdetailwindow("查看", "tZZhyyController.do?goUpdate&load=detail&id="+rowData.id,null,200);
}

//提交申请
function doSubmit(id) {
     $.dialog.confirm('您确认已经录入完毕，提交给机关确认吗？', function() {
         var url = "tZZhyyController.do?doSubmit";
         $.ajax({
             async : false,
             cache : false,
             type : 'POST',
             data : {
                 'id' : id
             },
             url : url,// 请求的action路径
             error : function() {// 请求失败处理函数
             },
             success : function(data) {
                 var d = $.parseJSON(data);
                 var msg = d.msg;
                 tip(msg);
                 if (d.success) {
                     $("#zhyyList").datagrid('reload');
                 }
             }
         });
    },function(){});
}

//查看修改意见
function viewMsgText(id){
    $.dialog({
        content : 'url:tZZhyyController.do?goPropose&id='+id,
        title : '提出修改意见',
        lock : true,
        opacity : 0.3,
        width : '450px',
        height : '120px',
        cancel : function() {
        },
    }).zindex();
}
 </script>