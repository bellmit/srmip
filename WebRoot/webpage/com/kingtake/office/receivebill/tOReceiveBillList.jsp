<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%
// TSUser user = ResourceUtil.getSessionUserName();
// String uid = user.getUserName();
// request.setAttribute("uid", uid);
%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOReceiveBillList" checkbox="true" fitColumns="true" title="收文阅批单信息表" actionUrl="tOReceiveBillController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="来文单位id"  field="receiveUnitId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title"   query="true" queryMode="single"  width="120" isLike="true"></t:dgCol>
   <t:dgCol title="来文单位"  field="receiveUnitName"   query="true" queryMode="single"  width="120" isLike="true"></t:dgCol>
   <t:dgCol title="公文编号"  field="billNum"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="密级"  field="secrityGrade"   codeDict="0,XMMJ"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="校首长阅批"  field="leaderReview"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="领导阅批"  field="officeReview"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位意见"  field="dutyOpinion"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位"  field="dutyName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人id"  field="contactId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="contactName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="电话"  field="contactTel"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="登记日期"  field="registerTime"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="登记人id"  field="registerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="登记人姓名"  field="registerName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="登记人部门id"  field="registerDepartId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="登记人部门名称"  field="registerDepartName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="状态"  field="archiveFlag"  codeDict="1,YPDZT"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人id"  field="archiveUserid"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人姓名"  field="archiveUsername"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档时间"  field="archiveDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt"></t:dgCol>
   <t:dgFunOpt exp="archiveFlag#eq#0" title="修改"  funname="toupdate(id)" operationCode="update"/>
   <t:dgFunOpt exp="archiveFlag#eq#3" title="修改"  funname="toupdate(id)" operationCode="update"/>
   <t:dgFunOpt exp="archiveFlag#ne#2" title="发送"  funname="send(id)" operationCode="send"/>
   <t:dgFunOpt exp="archiveFlag#eq#1" title="归档"  funname="archive(id)" operationCode="archive"/>
   <t:dgFunOpt exp="archiveFlag#eq#2" title="套红模板"  funname="courseListWordXlsByT(id)" />
   <t:dgFunOpt exp="archiveFlag#eq#2" title="套打模板"  funname="courseListWordXlsByT1(id)" />
   <t:dgDelOpt title="删除" url="tOReceiveBillController.do?doDel&id={id}"  operationCode="del"/>
   <t:dgToolBar title="录入" icon="icon-add" onclick="addBill()" height="520" width="800" operationCode="add"></t:dgToolBar>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tOReceiveBillController.do?goUpdate" funname="update"  height="520" width="800" operationCode="edit"></t:dgToolBar> --%>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOReceiveBillController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOReceiveBillController.do?goUpdate" funname="detail" height="520" width="800" ></t:dgToolBar>
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
<%--    <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/office/receivebill/tOReceiveBillList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOReceiveBillListtb").find("input[name='registerTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='registerTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='archiveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='archiveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOReceiveBillListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOReceiveBillController.do?upload', "tOReceiveBillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOReceiveBillController.do?exportXls","tOReceiveBillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOReceiveBillController.do?exportXlsByT","tOReceiveBillList");
}

function addBill(){
	$.dialog({
		content: 'url:tOReceiveBillController.do?goAdd',
		lock : true,
		//zIndex:1990,
		width:800,
		height:520,
		title:'阅批单录入',
		opacity : 0.3,
		cache:false,
		button:[{
			name:'保存并发送',
			callback:function(data){
				iframe = this.iframe.contentWindow;
				var flag =iframe.formCheck();
				if(flag==true){
				var addForm = $('#formobj', iframe.document).serialize();
				$.post('tOReceiveBillController.do?doAdd',addForm,function(data){
					data = JSON.parse(data);
					iframe.uploadFile(data);
					var id = data.obj.id;
// 					add("发送","tOReceiveBillController.do?goSend&id="+id,"",520,220);
					$.dialog({
						content: 'url:tOReceiveBillController.do?goSend&id='+id,
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
							reloadtOReceiveBillList();
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
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOReceiveBillList();
	    }
	    
	}).zindex();
	
}

function send(id){
	add("发送","tOReceiveBillController.do?goSend&id="+id,"",520,220);
}

function toupdate(id){
// 	add("修改","tOReceiveBillController.do?goUpdate&id="+id,"tOReceiveBillList",800,520);
	$.dialog({
		content: 'url:tOReceiveBillController.do?goUpdate&id='+id,
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
// 					add("发送","tOReceiveBillController.do?goSend&id="+id,"",520,220);
					iframe = this.iframe.contentWindow;
					var flag =iframe.formCheck();
					if(flag==true){
					var addForm = $('#formobj', iframe.document).serialize();
					$.post('tOReceiveBillController.do?doUpdate',addForm,function(data){
						data = JSON.parse(data);
						iframe.uploadFile(data);
						var id = data.obj.id;
// 						add("发送","tOReceiveBillController.do?goSend&id="+id,"",520,220);
						$.dialog({
							content: 'url:tOReceiveBillController.do?goSend&id='+id,
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
								reloadtOReceiveBillList();
								return false;
						    },
//	 					    cancelVal: 'Close',
						    cancel: true /*为true等价于function(){}*/
						}).zindex();
					});
					}
					return false;
			}
		}],
	    ok: function(){
	    	iframe = this.iframe.contentWindow;
			saveObj();
			return false;
	    },
	    okVal:'保存',
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOReceiveBillList();
	    }
	    
	}).zindex();
}

function archive(id){
	$.post("tOReceiveBillController.do?archive&id="+id,"",function(data){
		reloadtOReceiveBillList();
		data = JSON.parse(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg,
        // 							timeout : 1000 * 6
        });
		console.info(data);
	}); 
}

function goupdate(id){
	console.info(id);
	
}

function courseListWordXlsByT(id) {
	JeecgExcelExport("tOReceiveBillController.do?exportDocByT&id="+id+"&flag=0","tOReceiveBillList");
}

function courseListWordXlsByT1(id) {
	JeecgExcelExport("tOReceiveBillController.do?exportDocByT&id="+id+"&flag=1","tOReceiveBillList");
}

 </script>