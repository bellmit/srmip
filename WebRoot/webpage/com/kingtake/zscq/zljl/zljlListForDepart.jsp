<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="zljlList" checkbox="true" fitColumns="true" title='专利奖励<font color="red">（请先通过时间段查询，再录入相关专利奖励。注：时间段指包含专利证书登记中‘授权公告日’和授权中的‘发文日’）</font>' actionUrl="tZZljlController.do?datagridForZl" idField="id" fit="true" queryMode="group" pagination="false" onDblClick="goDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档号"  field="gdh"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专利类型"  field="lx" codeDict="1,ZLLX"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专利名称"  field="mc"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发明人"  field="fmr"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="lxr"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人电话"  field="lxrdh"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人电话"  field="lxrdh"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="专利状态"  field="zlzt" codeDict="1,ZLZT"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="时间段"  field="fwr" hidden="true" query="true" formatter="yyyy-MM-dd"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <t:dgToolBar title="录入奖励信息" icon="icon-add" funname="addZljl"></t:dgToolBar>
   <%-- <t:dgToolBar title="查看" icon="icon-search" url="tZZljlController.do?goUpdate" funname="detail" height="200"></t:dgToolBar>
   <t:dgDelOpt title="删除" url="tZZljlController.do?doDel&id={id}" />
   <t:dgFunOpt title="编辑" funname="goUpdate(id)" ></t:dgFunOpt> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
     $("#zljlListtb").find("input[name='fwr_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     $("#zljlListtb").find("input[name='fwr_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//编辑
function goUpdate(id){
    var url = "tZZljlController.do?goUpdate&id="+id;
    createwindow("编辑",url,null,"200");
}

//查看
function goDetail(rowIndex, rowData){
    createdetailwindow("查看", "tZZlsqController.do?goUpdate&load=detail&id="+rowData.id,null,'100%');
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
 </script>
 <script type="text/javascript" src="webpage/common/util.js"></script> 