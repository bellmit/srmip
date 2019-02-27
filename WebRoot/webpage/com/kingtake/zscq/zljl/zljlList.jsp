<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="zljlList" checkbox="true" fitColumns="true" title="专利奖励" actionUrl="tZZljlController.do?datagrid&zlsqId=${zlsqId }" idField="id" fit="true" queryMode="group" onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="奖励时间"  field="jlsj"  formatter="yyyy-MM-dd" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="奖励金额"  field="jlje" extendParams="formatter:formatCurrency,"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="确认状态"  field="qrzt" replace="已生成_1,已上传领报单_2,返回修改_3,已确认_4" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <c:if test="${role eq 'fmr' }">
   <t:dgFunOpt exp="qrzt#eq#1" title="上传领报单" funname="uploadLbd(id)"></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#3" title="上传领报单" funname="uploadLbd(id)"></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#2" title="上传领报单" funname="uploadLbd(id)"></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#4" title="查看领报单" funname="viewLbd(id)"></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#3" title="查看修改意见" funname="viewMsgText(id)" ></t:dgFunOpt>
   </c:if>
   <c:if test="${role eq 'depart' }">
   <t:dgFunOpt exp="qrzt#ne#1" title="查看领报单" funname="viewLbd(id)"></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#2" funname="goConfirm(id)" title="确认"></t:dgFunOpt>
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
    var url = "tZZljlController.do?goUpdate&id="+id;
    createwindow("编辑",url,null,"200");
}

//查看
function goDetail(rowIndex, rowData){
    createdetailwindow("查看", "tZZljlController.do?goUpdate&load=detail&id="+rowData.id,null,200);
}

function addZljl(){
    var rows = $("#zljlList").datagrid("getChecked");
    if(rows==0){
        tip("请勾选筛选出的专利进行奖励录入！");
        return ;
    }
    var ids = [];
    for(var i=0;i<rows.length;i++){
        ids.push(rows[i].id);
    }
    var url = "tZZljlController.do?goAddZljl";
    $.dialog({
		content: 'url:'+url,
		data:ids,
		lock : true,
		width:500,
		height:250,
		title:"录入奖励信息",
		opacity : 0.3,
		cache:false,
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
			saveObj();
			return false;
	    },
	    cancelVal: '关闭',
	    cancel: true /*为true等价于function(){}*/
	}).zindex();
}

//上传领报单
function uploadLbd(id){
    gridname="zljlList";
    createwindow('领报单上传', 'tZZljlController.do?goUploadLbd&id='+id,610,200);
}

//查看领报单
function viewLbd(id){
    gridname="zljlList";
    createwindow('领报单上传', 'tZZljlController.do?goUploadLbd&load=detail&id='+id,610,200);
}

//查看修改意见
function viewMsgText(id){
    $.dialog({
        content : 'url:tZZljlController.do?goPropose&id='+id,
        title : '提出修改意见',
        lock : true,
        opacity : 0.3,
        width : '450px',
        height : '120px',
        cancel : function() {
        },
    }).zindex();
}

//确认
function goConfirm(id){
    var url = "tZZljlController.do?goConfirm&id="+id;
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
       url:'tZZljlController.do?doConfirm',
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
 </script>
 <script type="text/javascript" src="webpage/common/util.js"></script> 