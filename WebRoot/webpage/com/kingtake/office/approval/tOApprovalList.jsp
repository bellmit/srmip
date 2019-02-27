<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uid = user.getUserName();
request.setAttribute("uid", uid);
request.setAttribute("temporary", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("sendOff", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOApprovalList" checkbox="true" fitColumns="false" title="呈批件信息表" actionUrl="tOApprovalController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="文号"  field="applicationFileno"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="内容"  field="applicationContent"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处理状态"  field="archiveFlag"   codeDict="1,YPDZT"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人id"  field="archiveUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人姓名"  field="archiveUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档时间"  field="archiveDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="驳回人id"  field="backUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="驳回人姓名"  field="backUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="驳回人意见"  field="backSuggestion"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收单位id"  field="receiveUnitid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收单位名称"  field="receiveUnitname"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="落款单位id"  field="signUnitid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="落款单位名称"  field="signUnitname"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="密级"  field="secrityGrade"  codeDict="0,XMMJ"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位id"  field="undertakeUnitId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位名称"  field="undertakeUnitName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人id"  field="contactId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人姓名"  field="contactName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="contactPhone"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="年度"  field="approvalYear"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
   <t:dgFunOpt exp="archiveFlag#eq\#${temporary}&&createBy#eq\#${uid}" title="修改"  funname="toupdate(id)" operationCode="update"/>
   <t:dgFunOpt exp="archiveFlag#eq\#${rebut}&&createBy#eq\#${uid}" title="修改"  funname="toupdate(id)" operationCode="update"/>
   <t:dgFunOpt exp="archiveFlag#ne\#${complete}&&createBy#eq\#${uid}" title="发送"  funname="send(id)" operationCode="send"/>
   <t:dgFunOpt exp="archiveFlag#eq\#${sendOff}&&createBy#eq\#${uid}" title="归档"  funname="archive(id)" operationCode="archive"/>
   <t:dgFunOpt exp="archiveFlag#eq\#${complete}" title="套红模板"  funname="courseListWordXlsByT(id)" />
   <t:dgFunOpt exp="archiveFlag#eq\#${complete}" title="套打模板"  funname="courseListWordXlsByT1(id)" />
   <t:dgDelOpt title="删除" url="tOApprovalController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tOApprovalController.do?goAdd" funname="add"  width="800" height="520"></t:dgToolBar> --%>
<t:dgToolBar title="录入" icon="icon-add" onclick="addBill()" height="520" width="800" operationCode="add"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tOApprovalController.do?goUpdate" funname="update" width="800" height="520"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOApprovalController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOApprovalController.do?goUpdate" funname="detail"  width="800" height="520"></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/approval/tOApprovalList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOApprovalListtb").find("input[name='archiveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalListtb").find("input[name='archiveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOApprovalListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOApprovalController.do?upload', "tOApprovalList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOApprovalController.do?exportXls","tOApprovalList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOApprovalController.do?exportXlsByT","tOApprovalList");
}

function courseListWordXlsByT(id) {
	JeecgExcelExport("tOApprovalController.do?exportDocByT&id="+id+"&flag=0","tOApprovalList");
}

function courseListWordXlsByT1(id) {
	JeecgExcelExport("tOApprovalController.do?exportDocByT&id="+id+"&flag=1","tOApprovalList");
}

function addBill(){
	$.dialog({
		content: 'url:tOApprovalController.do?goAdd',
		lock : true,
		//zIndex:1990,
		width:800,
		height:520,
		title:'呈批件录入',
		opacity : 0.3,
		cache:false,
		button:[{
			name:'保存并发送',
			callback:function(data){
				iframe = this.iframe.contentWindow;
				var flag =iframe.formCheck();
				if(flag==true){
				var addForm = $('#formobj', iframe.document).serialize();
				$.post('tOApprovalController.do?doAdd',addForm,function(data){
					data = JSON.parse(data);
					iframe.uploadFile(data);
					var id = data.obj.id;
// 					window.location.reload();
// 					add("发送","tOSendBillController.do?goSend&id="+id,"",520,220);
					$.dialog({
						content: 'url:tOApprovalController.do?goSend&id='+id,
						lock : true,
						//zIndex:1990,
						width:520,
						height:220,
						title:"发送",
						opacity : 0.3,
						cache:false,
						okVal: '发送',
					    ok: function(){
					    	iframe = this.iframe.contentWindow;
							saveObj();
							reloadtOApprovalList();
							return false;
					    },
// 					    cancelVal: 'Close',
					    cancel: true /*为true等价于function(){}*/
					}).zindex();
				});
				}
				return false;
			}
		}],
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
	    	reloadtOApprovalList();
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOApprovalList();
	    }
	    
	}).zindex();
}

function toupdate(){
	$.dialog({
		content: 'url:tOApprovalController.do?goUpdate&id='+id,
		lock : true,
		//zIndex:1990,
		width:800,
		height:520,
		title:'呈批件录入',
		opacity : 0.3,
		cache:false,
		button:[{
			name:'保存并发送',
			callback:function(data){
				iframe = this.iframe.contentWindow;
				var flag =iframe.formCheck();
				if(flag==true){
				var addForm = $('#formobj', iframe.document).serialize();
				$.post('tOApprovalController.do?doUpdate',addForm,function(data){
					data = JSON.parse(data);
					iframe.uploadFile(data);
					var id = data.obj.id;
// 					window.location.reload();
// 					add("发送","tOSendBillController.do?goSend&id="+id,"",520,220);
					$.dialog({
						content: 'url:tOApprovalController.do?goSend&id='+id,
						lock : true,
						//zIndex:1990,
						width:520,
						height:220,
						title:"发送",
						opacity : 0.3,
						cache:false,
						okVal: '发送',
					    ok: function(){
					    	iframe = this.iframe.contentWindow;
							saveObj();
							reloadtOApprovalList();
							return false;
					    },
// 					    cancelVal: 'Close',
					    cancel: true /*为true等价于function(){}*/
					}).zindex();
				});
				}
				return false;
			}
		}],
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
	    	reloadtOApprovalList();
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOApprovalList();
	    }
	    
	}).zindex();
}

function send(id){
	$.dialog({
		content: 'url:tOApprovalController.do?goSend&id='+id,
		lock : true,
		//zIndex:1990,
		width:520,
		height:220,
		title:"发送",
		opacity : 0.3,
		cache:false,
		okVal: '发送',
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
	    	reloadtOApprovalList();
			saveObj();
			return false;
	    },
//		    cancelVal: 'Close',
	    cancel: true /*为true等价于function(){}*/
	}).zindex();
}

function archive(id){
	$.post("tOApprovalController.do?archive&id="+id,"",function(data){
		reloadtOApprovalList();
		data = JSON.parse(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg,
        // 							timeout : 1000 * 6
        });
// 		console.info(data);
	}); 
}
 </script>