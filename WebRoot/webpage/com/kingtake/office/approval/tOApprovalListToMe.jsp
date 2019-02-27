<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uname = user.getUserName();
request.setAttribute("uname", uname);
request.setAttribute("uid", user.getId());
request.setAttribute("temporary", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("sendOff", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function transaction(rowIndex){
	var rowData = $('#tOApprovalList').datagrid('getData').rows[rowIndex];
	doubleClick(rowIndex,rowData);
}

function doubleClick(rowIndex,rowData){
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rowData.id;
	var rid = rowData.rid;
	if(operateStatus == '0'){
		$.dialog({
			id:'inputPage',
			content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus,
			lock : true,
			//zIndex:1990,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"呈批件处理",
			opacity : 0.3,
			cache:false,
			okVal:'发送',
			ok:function(){
			    iframe = this.iframe.contentWindow;
//  			    iframe.SaveSignature();
				goSend(id,rid);
				return false;
			},
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtOApprovalList();
		    }
		});
	}else{
		goDetail(rowIndex);
	}
}

function goDetail(rowIndex){
	var rowsData = $('#tOApprovalList').datagrid('getSelections');
	var id = rowsData[0].id;
	$.dialog({
		id:'detailPage',
		content: 'url:tOApprovalController.do?goDetail&id='+id,
		lock : true,
		//zIndex:1990,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
    	left : '0%',
    	top : '0%',
		title:"阅批单查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOApprovalList();
	    }
	});
}

function goSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOApprovalController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

function goFinish(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOApprovalController.do?goFinish&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

function doFinish(id,rid){
	$.dialog.confirm("是否确定将公文置为完成状态？",function(){
		$.ajax({
			url:'tOApprovalController.do?doFinish&id='+id+'&rid='+rid,
			type : 'POST',
			timeout : 3000,
			dataType : 'json',
			success:function(data){
				tip(data.msg);
				refresh();
//	 			reloadtOApprovalList();
			}
		});
	},function(){});
	
}

function goOfficePage(id){
	 $.dialog({
			id:'inputPage',
			content: 'url:tOApprovalController.do?goOfficePage&id='+id,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
    		left : '0%',
    		top : '0%',
			title:"呈批件套红",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}

//套打
function goTDOfficePage(id){
	 $.dialog({
			id:'inputPage',
			content: 'url:tOApprovalController.do?goTDOfficePage&id='+id,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
    		left : '0%',
    		top : '0%',
			title:"呈批件套打",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}

function refresh(){
    var jq = top.jQuery;
    var tab = jq('#maintabs').tabs("getSelected"); 
	tab.panel('refresh');
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOApprovalList" onDblClick="doubleClick" fitColumns="false" title="呈批件信息列表" 
  	actionUrl="tOApprovalController.do?datagrid&operateStatus=${operateStatus}&registerType=${registerType}" 
  	idField="id" fit="true" queryMode="group">
  	<t:dgCol title="内容"  field="APPLICATION_CONTENT"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
  	<t:dgCol title="接收主键"  field="rid"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="归档人id"  field="ARCHIVE_USERID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人姓名"  field="ARCHIVE_USERNAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档时间"  field="ARCHIVE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="创建人"  field="CREATE_BY"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="CREATE_NAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="UPDATE_BY"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="UPDATE_NAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="UPDATE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="驳回人id"  field="BACK_USERID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="驳回人姓名"  field="BACK_USERNAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="驳回人意见"  field="BACK_SUGGESTION"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收单位id"  field="RECEIVE_UNITID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收单位名称"  field="RECEIVE_UNITNAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="落款单位id"  field="SIGN_UNITID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="落款单位名称"  field="SIGN_UNITNAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="承办单位id"  field="UNDERTAKE_UNIT_ID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人id"  field="CONTACT_ID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处理状态" hidden="true"  field="OPERATE_STATUS"  codeDict="1,CHULIZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="签收状态" hidden="true" field="SIGN_IN_FLAG"  codeDict="1,QSZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="是否为传阅"  field="IFCIRCULATE" hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="年度"  field="APPROVAL_YEAR"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    
   <t:dgCol title="公文标题"  field="title"  frozenColumn="true" sortable="false" query="true"   queryMode="single"  width="230"></t:dgCol>
   <t:dgCol title="公文编号"  field="FILE_NUM"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="密级"  field="SECRITY_GRADE"  codeDict="0,XMMJ"    queryMode="group"  width="50"></t:dgCol>
   <t:dgCol title="承办单位"  field="UNDERTAKE_UNIT_NAME"    queryMode="group"  width="160"></t:dgCol>
   <t:dgCol title="联系人"  field="CONTACT_NAME" queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="电话"  field="CONTACT_PHONE" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="公文状态"  field="ARCHIVE_FLAG" codeDict="1,YPDZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="生成时间"  field="CREATE_DATE" formatter="yyyy-MM-dd"  queryMode="group"  width="90" align="center"></t:dgCol>
   
   <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
<%--    <t:dgFunOpt exp="ARCHIVE_FLAG#ne\#${complete}&&CREATE_BY#eq\#${uname}" title="发送"  funname="send(id)"/> --%>
<%--    <t:dgFunOpt exp="ARCHIVE_FLAG#ne\#${complete}&&IFCIRCULATE#eq#0&&CREATE_BY#ne\#${uid}" title="发送"  funname="send(id)"/> --%>
<c:if test="${operateStatus eq 0}"><!-- 未处理才会有办理和归档 -->
   <t:dgFunOpt exp="ARCHIVE_FLAG#ne\#${complete}&&SIGN_IN_FLAG#ne#0" title="办理"  funname="transaction"/>
   <t:dgFunOpt exp="CONTACT_ID#eq\#${uid}" title="完成"  funname="goFinish(id,rid)"></t:dgFunOpt>
</c:if>
<c:if test="${operateStatus eq 1}"><!-- 已处理才会有套红套打 -->
   <t:dgFunOpt  title="套红"  funname="goOfficePage(id)" />
   <t:dgFunOpt  title="套打"  funname="goTDOfficePage(id)" />
</c:if>
<%--    <t:dgDelOpt exp="ARCHIVE_FLAG#eq\#${temporary}&&CREATE_BY#eq\#${uname}" title="删除" url="tOApprovalController.do?doDel&id={id}" /> --%>
<%--    <t:dgToolBar title="录入" icon="icon-add" url="tOApprovalController.do?goAdd" funname="add"  width="800" height="520"></t:dgToolBar> --%>
<%-- <t:dgToolBar title="录入" icon="icon-add" onclick="addBill()" height="520" width="800" operationCode="add"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="编辑" icon="icon-edit" url="tOApprovalController.do?goUpdate" funname="update" width="800" height="520"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOApprovalController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search"   funname="goDetail"></t:dgToolBar>
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
	JeecgExcelExport("tOApprovalController.do?exportXls&operateStatus=${operateStatus}&registerType=${registerType}","tOApprovalList");
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

// function transaction(rowIndex){
// 	var rowData = $('#tOApprovalList').datagrid('getData').rows[rowIndex];
// 	dbcl2(rowIndex,rowData);
// }
function dbcl2(rowIndex,rowData){
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rowData.id;
	$.ajax({
		url:'tOApprovalController.do?ifSignIn&id='+id,
		type : 'POST',
		timeout : 3000,
		success:function(data){
			//返回值意义：0-未签收，1-已签收
			data = JSON.parse(data);
			var signInFlag = data.obj;
			if(signInFlag=='1'){
				$.ajax({
					url:'tOApprovalController.do?getViewByRole&id='+id+'&operateStatus='+operateStatus,
					type : 'POST',
					timeout : 3000,
					success:function(data){
						//返回值意义：0-仅查看，1-作为接收人，2-作为创建人，3-作为被传阅人
						data = JSON.parse(data);
						var editFlag = data.attributes.editFlag;
						var ifcirculate = data.attributes.ifcirculate;
						//已处理tab页列表双击
						if(operateStatus == '1'){
							if(ifcirculate=='0'&&rowData.ARCHIVE_FLAG!='2'){//如果不是被传阅人且公文未归档查看时显示发送按钮
								$.dialog({
									id:'inputPage',
									content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&editFlag='+editFlag+'&ifcirculate='+ifcirculate+'&load=detail',
									lock : true,
									width:900,
									height:520,
									title:"呈批件处理",
									opacity : 0.3,
									cache:false,
									okVal:'发送',
									ok:function(){
										$.ajax({
											url:'tOApprovalController.do?ifaccessory&id='+id,
											type : 'POST',
											timeout : 3000,
											success:function(data){
												data = JSON.parse(data);
												var accessoryFlag = data.obj;//0-没有附件，1-有附件
												if(accessoryFlag=='1'){
													add("发送","tOApprovalController.do?goSend&id="+id+"&ifcirculate=0","tOReceiveBillList",480,200);
												}else{
													$.dialog.confirm("未上传附件不可发送，是否现在上传？",function(){
														toupdate(id);
													},function(){});
													return false;
												}
											}
										});
									},
								    cancelVal: '关闭',
								    cancel: function(){
								    }
								});
							}else{//否则仅查看
							$.dialog({
								id:'inputPage',
								content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&load=detail&ifcirculate='+ifcirculate+'&editFlag='+editFlag,
								lock : true,
								width:900,
								height:520,
								title:"呈批件查看",
								opacity : 0.3,
								cache:false,
							    cancelVal: '关闭',
							    cancel: function(){
							    	reloadtOApprovalList();
							    }
							});
							}
						}else{
						if(editFlag=='1'){
							$.dialog({
								id:'inputPage',
								content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&editFlag='+editFlag+'&ifcirculate='+ifcirculate,
								lock : true,
								//zIndex:1990,
								width:900,
								height:520,
								title:"呈批件处理",
								opacity : 0.3,
								cache:false,
								okVal:'保存',
								ok:function(){
									iframe = this.iframe.contentWindow;
									iframe.pass();
									var flag = iframe.checkReview();
									if(flag){
										$.dialog.confirm('意见签署完毕，将返回给发送人！',function(){
											saveObj();
										},function(){
										}).zindex();
									}else{
										$.dialog.alert('请先填写意见！', function(){}).zindex();
									}
									return false;
								},
								button:[{
									name:"退回",
									callback:function(){
										iframe = this.iframe.contentWindow;
										iframe.goback();
			 							var flag = iframe.checkReview();
										if(flag){
											$.dialog.confirm('该记录将被退回给发送人，请确认！',function(){
												saveObj();
											},function(){
											}).zindex();
										}else{
											$.dialog.alert('请先填写意见！', function(){}).zindex();
										}
										return false;
									}
								},{
									name:"呈送上级",
									callback:function(){
										iframe = this.iframe.contentWindow;
										iframe.pass();
										var flag = iframe.checkReview();
										if(flag){
											add("呈送","tOApprovalController.do?goPresent&id="+id+"&ifcirculate=0","tOReceiveBillList",520,220);
										}else{
											$.dialog.alert('请先填写意见！', function(){}).zindex();
										}
										return false;
									}
								}],
							    cancelVal: '关闭',
							    cancel: function(){
							    	reloadtOApprovalList();
//						 	    	window.location.reload();
							    }
							});
							}else if(editFlag=='2'){
								$.dialog({
									id:'inputPage',
									content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&editFlag='+editFlag+'&ifcirculate='+ifcirculate+'&load=detail',
									lock : true,
									width:900,
									height:520,
									title:"呈批件处理",
									opacity : 0.3,
									cache:false,
									okVal:'发送',
									ok:function(){
										$.ajax({
											url:'tOApprovalController.do?ifaccessory&id='+id,
											type : 'POST',
											timeout : 3000,
											success:function(data){
												data = JSON.parse(data);
												var accessoryFlag = data.obj;//0-没有附件，1-有附件
												if(accessoryFlag=='1'){
													add("发送","tOApprovalController.do?goSend&id="+id+"&ifcirculate=0","tOReceiveBillList",480,200);
												}else{
													$.dialog.confirm("未上传附件不可发送，是否现在上传？",function(){
														toupdate(id);
													},function(){});
													return false;
												}
											}
										});
									},
								    cancelVal: '关闭',
								    cancel: function(){
								    }
								});
							}else if(editFlag=='3'){
								$.dialog({
									id:'inputPage',
									content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&editFlag='+editFlag+'&ifcirculate='+ifcirculate,
									lock : true,
									//zIndex:1990,
									width:900,
									height:520,
									title:"呈批件处理",
									opacity : 0.3,
									cache:false,
									okVal:'保存',
									ok:function(){
										iframe = this.iframe.contentWindow;
										iframe.pass();
										var flag = iframe.checkReview();
										if(flag){
											$.dialog.confirm('意见签署完毕，将返回给发送人！',function(){
												saveObj();
											},function(){
											}).zindex();
										}else{
											$.dialog.alert('请先填写意见！', function(){}).zindex();
										}
										return false;
									},
								    cancelVal: '关闭',
								    cancel: function(){
								    }
								});
							}else{
								$.dialog({
									id:'inputPage',
									content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&load=detail&ifcirculate='+ifcirculate+'&editFlag='+editFlag,
									lock : true,
									width:900,
									height:520,
									title:"呈批件查看",
									opacity : 0.3,
									cache:false,
								    cancelVal: '关闭',
								    cancel: function(){
								    	reloadtOApprovalList();
								    }
								});
							}
					}
					}
				});
			}else{
				$.dialog({
					id:'inputPage',
					content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus=0&load=detail&ifcirculate=1&editFlag=0',
					lock : true,
					//zIndex:1990,
					width:900,
					height:520,
					title:"呈批件处理",
					opacity : 0.3,
					cache:false,
					okVal:'签收',
					ok:function(){
						doSignIn(id);
//							return false;
					},
				    cancelVal: '关闭',
				    cancel: function(){
//			 	    	window.location.reload();
				    }
				});
			}
		}
	});
}

function doSignIn(id){
	$.post("tOApprovalController.do?doSignIn&id="+id,"",function(data){
		reloadtOApprovalList();
		data = JSON.parse(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg,
        // 							timeout : 1000 * 6
        });
	}); 
}


function dbcl(rowIndex,rowData){
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rowData.id;
	$.ajax({
		url:'tOApprovalController.do?ifEdit&id='+id+'&operateStatus='+operateStatus,
		type : 'POST',
		timeout : 3000,
		success:function(data){
			data = JSON.parse(data);
			if(data.obj=='1'){
				$.dialog({
					id:'inputPage',
					content: 'url:tOApprovalController.do?goOperate&id='+id,
					lock : true,
					//zIndex:1990,
					width:800,
					height:520,
					title:"呈批件处理",
					opacity : 0.3,
					cache:false,
					okVal:'批示意见',
					ok:function(){
						inputSuggestion(id);
						return false;
					},
				    cancelVal: '关闭',
				    cancel: function(){
				    	reloadtOApprovalList();
//			 	    	window.location.reload();
				    }
				});
				}else if(data.obj=='2'){
					$.dialog({
						content: 'url:tOApprovalController.do?goOperate&id='+id,
						lock : true,
						//zIndex:1990,
						width:800,
						height:520,
						title:"呈批件处理",
						opacity : 0.3,
						cache:false,
						okVal:'发送',
						ok:function(){
							send();
// 							add("发送","tOApprovalController.do?goSend&id="+id,"",520,220);
							return false;
						},
					    cancelVal: '关闭',
					    cancel: function(){
//				 	    	window.location.reload();
					    }
					});
				}else{
					$.dialog({
						content: 'url:tOApprovalController.do?goOperate&id='+id,
						lock : true,
						width:800,
						height:520,
						title:"呈批件处理",
						opacity : 0.3,
						cache:false,
					    cancelVal: '关闭',
					    cancel: function(){
//			 		    	window.location.reload();
					    }
					});
				}
		}
	});
}

function inputSuggestion(id){
	 $.dialog({
			content: 'url:tOApprovalController.do?goInput&id='+id,
			lock : true,
			//zIndex:1990,
			width:600,
			height:220,
			title:"意见填写",
			opacity : 0.3,
			cache:false,
	 	    ok: function(){
	 	    	iframe = this.iframe.contentWindow;
	 	    	var suggesForm = $('#formobj', iframe.document).serialize();
	 	    	var rid = $('#receiveid', iframe.document).val();
	 	    	var reloadPage = this.get("inputPage");
	 	    	$.post('tOApprovalController.do?doSuggestion&rid='+rid,suggesForm,function(data){
	 	    		data = JSON.parse(data);
	 	    		reloadPage.location.reload();
	 	    	});
	 	    },
		    cancelVal: '关闭',
		    cancel: function(){
		    	
		    }
		}).zindex();
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

function toupdate(id){
	$.dialog({
		content: 'url:tOApprovalController.do?goUpdate&id='+id,
		lock : true,
		//zIndex:1990,
		width:900,
		height:520,
		title:'呈批件录入',
		opacity : 0.3,
		cache:false,
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
	$.ajax({
		url:'tOApprovalController.do?ifaccessory&id='+id,
		type : 'POST',
		timeout : 3000,
		success:function(data){
			data = JSON.parse(data);
			var accessoryFlag = data.obj;//0-没有附件，1-有附件
			if(accessoryFlag=='1'){
				add("发送","tOApprovalController.do?goSend&id="+id+"&ifcirculate=0","tOReceiveBillList",480,200);
			}else{
				$.dialog.confirm("未上传附件不可发送，是否现在上传？",function(){
					toupdate(id);
				},function(){});
				return false;
			}
		}
	});
// 	$.dialog({
// 		content: 'url:tOApprovalController.do?goSend&id='+id,
// 		lock : true,
// 		//zIndex:1990,
// 		width:520,
// 		height:220,
// 		title:"发送",
// 		opacity : 0.3,
// 		cache:false,
// 		okVal: '发送',
// 	    ok: function(){
// 	    	iframe = this.iframe.contentWindow;
// 	    	reloadtOApprovalList();
// 			saveObj();
// 			return false;
// 	    },
// 	    cancel: true /*为true等价于function(){}*/
// 	}).zindex();
}

function archive(id){
	$.dialog.confirm('归档后该文件将无法再次发送或审批，是否确定执行此操作？', function(){
		$.post("tOApprovalController.do?archive&id="+id,"",function(data){
			reloadtOApprovalList();
			data = JSON.parse(data);
			$.dialog.setting.zIndex = 1980;
	        $.messager.show({
	            title : '提示信息',
	            msg : data.msg,
	        // 							timeout : 1000 * 6
	        });
//	 		console.info(data);
		});
	}, function(){
	});
	 
}
 </script>