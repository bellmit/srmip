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
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOSendBillList" checkbox="true" fitColumns="true" title="发文呈批单" actionUrl="tOSendBillController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="公文标题"  field="sendTitle"   query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单位"  field="sendUnit"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发文年度"  field="sendYear"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发文号"  field="sendNum"    queryMode="group"  width="120" extendParams="formatter:fileNumFormatter,"></t:dgCol>
   <t:dgCol title="文种id"  field="sendTypeCode"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="文种"  field="sendTypeName"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="密级"  field="secrityGrade"   codeDict="0,XMMJ"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="印数"  field="printNum"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟稿说明"  field="draftExplain"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="拟稿说明日期"  field="draftDate"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="承办单位id"  field="undertakeUnitId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位名称"  field="undertakeUnitName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="核稿人id"  field="nuclearDraftUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="核稿人姓名"  field="nuclearDraftUsername"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人id"  field="contactId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人姓名"  field="contactName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="contactPhone"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处理状态"  field="archiveFlag"   codeDict="1,YPDZT"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="归档人id"  field="archiveUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人姓名"  field="archiveUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档时间"  field="archiveDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="驳回人id"  field="backUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="驳回人姓名"  field="backUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="驳回人意见"  field="backSuggestion"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="单位ID"  field="sendUnitId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="200" frozenColumn="true"></t:dgCol>
   <t:dgFunOpt exp="archiveFlag#eq\#${temporary}&&createBy#eq\#${uid}" title="修改"  funname="toupdate(id)" operationCode="update"/>
   <t:dgFunOpt exp="archiveFlag#eq\#${rebut}&&createBy#eq\#${uid}" title="修改"  funname="toupdate(id)" operationCode="update"/>
   <t:dgFunOpt exp="archiveFlag#ne\#${complete}&&createBy#eq\#${uid}" title="发送"  funname="send(id)" operationCode="send"/>
   <t:dgFunOpt exp="archiveFlag#eq\#${sendOff}&&createBy#eq\#${uid}" title="归档"  funname="archive(id)" operationCode="archive"/>
   <t:dgFunOpt exp="archiveFlag#eq\#${complete}" title="套红模板"  funname="courseListWordXlsByT(id)" />
   <t:dgFunOpt exp="archiveFlag#eq\#${complete}" title="套打模板"  funname="courseListWordXlsByT1(id)" />
   <t:dgDelOpt title="删除" url="tOSendBillController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tOSendBillController.do?goAdd" funname="add" width="800" height="520"></t:dgToolBar> --%>
<t:dgToolBar title="录入" icon="icon-add" onclick="addBill()" height="520" width="800" operationCode="add"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tOSendBillController.do?goUpdate" funname="update" height="520" width="800" ></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOSendBillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOSendBillController.do?goUpdate" funname="detail" height="520" width="800" ></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/sendbill/tOSendBillList.js?${tm}"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOSendBillListtb").find("input[name='draftDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='draftDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='archiveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='archiveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOSendBillListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOSendBillController.do?upload', "tOSendBillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOSendBillController.do?exportXls","tOSendBillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOSendBillController.do?exportXlsByT","tOSendBillList");
}

function courseListWordXlsByT(id) {
	JeecgExcelExport("tOSendBillController.do?exportDocByT&id="+id+"&flag=0","tOSendBillList");
}

function courseListWordXlsByT1(id) {
	JeecgExcelExport("tOSendBillController.do?exportDocByT&id="+id+"&flag=1","tOSendBillList");
}

function addBill(){
	$.dialog({
		content: 'url:tOSendBillController.do?goAdd',
		lock : true,
		//zIndex:1990,
		width:800,
		height:520,
		title:'发文呈批单录入',
		opacity : 0.3,
		cache:false,
		button:[{
			name:'保存并发送',
			callback:function(data){
				iframe = this.iframe.contentWindow;
				var flag =iframe.formCheck();
				if(flag==true){
				var addForm = $('#formobj', iframe.document).serialize();
				$.post('tOSendBillController.do?doAdd',addForm,function(data){
					data = JSON.parse(data);
					iframe.uploadFile(data);
					var id = data.obj.id;
// 					window.location.reload();
// 					add("发送","tOSendBillController.do?goSend&id="+id,"",520,220);
					$.dialog({
						content: 'url:tOSendBillController.do?goSend&id='+id,
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
							reloadtOSendBillList();
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
			reloadtOSendBillList();
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOSendBillList();
	    }
	    
	}).zindex();
}

function toupdate(id){
// 	add("修改","tOReceiveBillController.do?goUpdate&id="+id,"tOReceiveBillList",800,520);
	$.dialog({
		content: 'url:tOSendBillController.do?goUpdate&id='+id,
		lock : true,
		//zIndex:1990,
		width:800,
		height:520,
		title:'阅批单修改',
		opacity : 0.3,
		cache:false,
		button:[{
			name:'保存并发送',
			callback:function(){
				iframe = this.iframe.contentWindow;
				var flag =iframe.formCheck();
				if(flag==true){
				var addForm = $('#formobj', iframe.document).serialize();
				$.post('tOSendBillController.do?doUpdate',addForm,function(data){
					data = JSON.parse(data);
					iframe.uploadFile(data);
					var id = data.obj.id;
					$.dialog({
						content: 'url:tOSendBillController.do?goSend&id='+id,
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
							reloadtOSendBillList();
							saveObj();
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
	    	reloadtOSendBillList();
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOSendBillList();
	    }
	    
	}).zindex();
}

function send(id){
	$.dialog({
		content: 'url:tOSendBillController.do?goSend&id='+id,
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
			reloadtOSendBillList();
			saveObj();
			return false;
	    },
//		    cancelVal: 'Close',
	    cancel: true /*为true等价于function(){}*/
	}).zindex();
}

function archive(id){
	$.post("tOSendBillController.do?archive&id="+id,"",function(data){
		reloadtOSendBillList();
		data = JSON.parse(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg,
        // 							timeout : 1000 * 6
        });
	}); 
}
 </script>