<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input id="zlsqId" type="hidden" value="${zlsqId}"> 
  <t:datagrid name="tZSctzsList" checkbox="true" fitColumns="true" title="审查通知书列表" actionUrl="tZSctzsController.do?datagrid&zlsqId=${zlsqId}" idField="id" fit="true" queryMode="group" onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="通知类型"  field="tzlx" codeDict="1,ZLSCTZLX"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发文日"  field="fwr"  formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="要求回复时间"  field="yqhfsj" extendParams="formatter:yqhfsjFormatter,"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否放弃"  field="sffq" replace="是_1,否_0"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="确认状态"  field="qrzt" replace="待回复_1,已回复_2,修改_3,已确认_4"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <c:if test="${role eq 'depart' }">
   <t:dgDelOpt exp="qrzt#eq#1" title="删除" url="tZSctzsController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tZSctzsController.do?goUpdate&zlsqId=${zlsqId}" funname="add" height="100%"></t:dgToolBar>
   <t:dgFunOpt exp="qrzt#eq#1" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
   <t:dgToolBar title="查看" icon="icon-search" url="tZSctzsController.do?goUpdate" funname="detail" height="100%"></t:dgToolBar>
   <t:dgFunOpt exp="qrzt#eq#2" funname="goConfirm(id)" title="确认"></t:dgFunOpt>
   </c:if>
   <c:if test="${role eq 'fmr' }">
   <t:dgFunOpt exp="qrzt#eq#1" funname="reply(id)" title="回复"></t:dgFunOpt>
   <t:dgFunOpt exp="qrzt#eq#3" funname="reply(id)" title="回复"></t:dgFunOpt>
   </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tZSqrxxController.do?upload', "tZSqrxxList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tZSqrxxController.do?exportXls","tZSqrxxList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tZSqrxxController.do?exportXlsByT","tZSqrxxList");
}

//回复
function reply(id){
    var url = "tZSctzsController.do?goUpdate&id="+id+"&opt=reply";
    gridname="tZSctzsList";
    createwindow("回复",url,null,"100%");
}

//要求回复时间格式化
function yqhfsjFormatter(value,row,index){
    if(value!=""){
        return value+"个月";
    }else{
        return "";
    }
}

//编辑
function goUpdate(id){
    var url = "tZSctzsController.do?goUpdate&id="+id;
    createwindow("编辑",url,null,"100%");
}

//确认
function goConfirm(id){
    var url = "tZSctzsController.do?goConfirm&id="+id;
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
       url:'tZSctzsController.do?doConfirm',
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
    createdetailwindow("查看", "tZSctzsController.do?goUpdate&load=detail&id="+rowData.id,null,'100%');
}
 </script>