<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uid = user.getId();
request.setAttribute("uid", uid);
request.setAttribute("temporary", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("sendOff", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function transaction(rowIndex){
	
	var rowData = $('#tOSendBillList').datagrid('getData').rows[rowIndex];
	doubleClick(rowIndex,rowData);
}

function doubleClick(rowIndex,rowData){
    if(rowData.SEND_TYPE_CODE=='13'){
        approvalDoubleClick(rowIndex,rowData);
    }else{
        sendBillDoubleClick(rowIndex,rowData);
    }
}
function sendBillDoubleClick(rowIndex,rowData){
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rowData.id;
	var rid = rowData.rid;
	if(operateStatus == '0'){//办理
		$.dialog({
			id:'inputPage',
			content: 'url:tOSendBillController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&rid='+rid,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"发文呈批单处理",
			opacity : 0.3,
			cache:false,
			button:[{
	            name: '完成审批',
	            callback: function () {
	            	var r = confirm('确定完成审批,将公文返还给承办人吗？'); 
		     	    if (r) {
	                          doSendBillPassReturn(id,rid,"1");
		     			   }
	                return false;
	            }
	        },{
	            name : '继续流转',
	            focus : true,
	            callback : function () {
	                iframe = this.iframe.contentWindow;
					goSend(id,rid,"1");
					return false;
	            }
	        },{
	            name: '返回修改',
	            callback: function () {
	                var r = confirm('确定返回给承办人修改?');
	     			if (r) {
	                       doSendBillPassReturn(id,rid,"0");
	     			}
	                return false;
	            }
	        }],
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtOSendBillList();
		    }
		});
	}else{
		goDetail(rowIndex);
	}
}

function approvalDoubleClick(rowIndex,rowData){
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rowData.id;
	var rid = rowData.rid;
	if(operateStatus == '0'){
		$.dialog({
			id:'inputPage',
			content: 'url:tOApprovalController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&rid='+rid,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"呈批件处理",
			opacity : 0.3,
			cache:false,
			button:[{
	            name: '完成审批',
	            callback: function () {
	            	var r = confirm('确定完成审批,将公文返还给承办人吗？');
		     		if (r) {
	                        doApprovalPassReturn(id,rid,"1");
		     		}
	                return false;
	            }
	        },{
	            name : '继续流转',
	            focus : true,
	            callback : function () {
	                iframe = this.iframe.contentWindow;
					goSend(id,rid,"2");
					return false;
	            }
	        },{
	            name: '返回修改',
	            callback: function () {
	            	var r = confirm('确定返回给承办人修改?');
		     		if (r) {
	                     doApprovalPassReturn(id,rid,"0");
		     		}
	                return false;
	            }
	        }],
		    cancelVal: '关闭',
		    cancel: function(){
		        reloadtOSendBillList();
		    }
		});
	}else{
		goDetail(rowIndex);
	}
}

function goDetail(){
    var selections = $('#tOSendBillList').datagrid("getSelections");
    if(selections.length==0){
        tip("请先选择一行进行查看！");
        return false;
    }
    if(selections.length>1){
        tip("请只选择一行进行查看！");
        return false;
    }
    var rowIndex = $('#tOSendBillList').datagrid("getRowIndex",selections[0]);
    if(selections[0].SEND_TYPE_CODE=='13'){
        goApprovalDetail(rowIndex);
    }else{
        goSendBillDetail(rowIndex);
    }
}
function goSendBillDetail(rowIndex){
	var rowsData = $('#tOSendBillList').datagrid('getSelections');
	var id = rowsData[0].id;
	$.dialog({
		id:'detailPage',
		content: 'url:tOSendBillController.do?goDetail&id='+id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
    	left : '0%',
    	top : '0%',
		title:"呈批单查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	        reloadtOSendBillList();
	    }
	});
}

function goApprovalDetail(rowIndex){
	var rowsData = $('#tOSendBillList').datagrid('getSelections');
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
	        reloadtOSendBillList();
	    }
	});
}
//发送
function goSend(id,rid,type){
    if(type=='1'){
        goSendBillSend(id,rid);
    }else{
        goApprovalSend(id,rid);
    }
}

function goSendBillSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOSendBillController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

//发文发送
function doSendBillPassReturn(id,rid,flag){
    var url = "tOSendBillController.do?passReturn";
    doPassReturn(id,rid,flag,url);
}

//呈批件发送
function doApprovalPassReturn(id,rid,flag){
    var url = "tOApprovalController.do?passReturn";
    doPassReturn(id,rid,flag,url);
}

//发送
function doPassReturn(id,rid,flag,url){
    var win = $.dialog.list['inputPage'].content;
    var remark = "";
    if(flag=="1"){
        win.SaveSignature();
        remark = "同意";
    }else{
        remark = "返回修改";
    }
    
    $.ajax({
        url:url,
        cache:false,
        type:'POST',
        dataType:'json',
        data:{
            id:id,
            rid:rid,
            opinion:flag,
            leaderReview:remark
        },
        success:function(data){
            tip(data.msg);
            if(data.success){
                refresh();
               win.close();
            }
        }
    })
}

function goApprovalSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOApprovalController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

function refresh(){
    var jq = top.jQuery;
    var tab = jq('#maintabs').tabs("getSelected"); 
	tab.panel('refresh');
}

//完成
function goFinish(id,rid,index){
    var rows = $('#tOSendBillList').datagrid("getRows");
    if(rows[index].SEND_TYPE_CODE=='13'){
        goApprovalFinish(id,rid);
    }else{
        goSendBillFinish(id,rid);
    }
}

//发文完成
function goSendBillFinish(id,rid){
	var iWidth=500; //弹出窗口的宽度;
    var iHeight=250; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOSendBillController.do?goFinish&id="+id+"&rid="+rid;
    createwindow("完成审批",openUrl,iWidth,iHeight);
    //window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

//呈批件完成
function goApprovalFinish(id,rid){
	var iWidth=500; //弹出窗口的宽度;
    var iHeight=250; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOApprovalController.do?goFinish&id="+id+"&rid="+rid;
    createwindow("完成审批",openUrl,iWidth,iHeight);
    //window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

</script>
<script type="text/javascript">
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOSendBillController.do?upload', "tOSendBillList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOSendBillController.do?exportXls&operateStatus=${operateStatus}&registerType=${registerType}","tOSendBillList");
}

//套红
function goOfficePage(id,index){
    var rows = $('#tOSendBillList').datagrid("getRows");
    if(rows[index].SEND_TYPE_CODE=='13'){
        goApprovalOfficePage(id);
    }else{
        goSendBillOfficePage(id);
    }
}
//套打
function goTDOfficePage(id,index){
    var rows = $('#tOSendBillList').datagrid("getRows");
    if(rows[index].SEND_TYPE_CODE=='13'){
        goApprovalTDOfficePage(id);
    }else{
        goSendBillTDOfficePage(id);
    }
}
function goSendBillOfficePage(id){
	 $.dialog({
			id:'inputPage',
			content: 'url:tOSendBillController.do?goOfficePage&id='+id,
			lock : true,
			//zIndex:1990,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
   		left : '0%',
   		top : '0%',
			title:"发文呈批单套红",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}

//套打
function goSendBillTDOfficePage(id){
	 $.dialog({
			id:'inputPage',
			content: 'url:tOSendBillController.do?goTDOfficePage&id='+id,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
  		    left : '0%',
  		    top : '0%',
			title:"发文呈批单套打",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}

function goApprovalOfficePage(id){
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
function goApprovalTDOfficePage(id){
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

</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;height: 400px;">
  <t:datagrid name="tOSendBillList"  onDblClick="doubleClick" fit="true" fitColumns="true" title="发文呈批单信息列表" 
  	actionUrl="tOSendBillController.do?datagrid&operateStatus=${operateStatus}&registerType=${registerType}" 
  	idField="id" queryMode="group" extendParams="nowrap:false, ">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="接收主键"  field="rid"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="公文标题"  frozenColumn="true"  field="SEND_TITLE"   query="true" queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="公文编号"  field="FILE_NUM"    queryMode="group"  width="120" extendParams="formatter:fileNumFormatter,"></t:dgCol>
   <%-- <t:dgCol title="单位ID"  field="SEND_UNIT_ID"  hidden="true"  queryMode="group"  width="80"></t:dgCol> --%>
   <%-- <t:dgCol title="单位"  field="SEND_UNIT"    queryMode="group"  width="100"></t:dgCol> --%>
   <t:dgCol title="发文年度"  field="SEND_YEAR"  hidden="true"    queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="公文种类"  field="SEND_TYPE_CODE"  query="true" codeDict="1,GWZL"  queryMode="single"  width="80"></t:dgCol>
<%--    <t:dgCol title="公文种类"  field="SEND_TYPE_NAME"   queryMode="single"  width="70"></t:dgCol> --%>
   <t:dgCol title="密级"  field="SECRITY_GRADE"   codeDict="0,XMMJ"   queryMode="group"  width="40"></t:dgCol>
   <%-- <t:dgCol title="印数"  field="PRINT_NUM"  hidden="true"    queryMode="group"  width="80"></t:dgCol> --%>
   <%-- <t:dgCol title="拟稿说明"  field="DRAFT_EXPLAIN"  hidden="true"    queryMode="group"  width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="拟稿说明日期"  field="DRAFT_DATE"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="80" align="center"></t:dgCol> --%>
   <t:dgCol title="承办单位id"  field="UNDERTAKE_UNIT_ID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="承办单位"  field="UNDERTAKE_UNIT_NAME"    queryMode="group"  width="100"></t:dgCol>
   <%-- <t:dgCol title="核稿人id"  field="NUCLEAR_DRAFT_USERID"  hidden="true"  queryMode="group"  width="80"></t:dgCol> --%>
   <%-- <t:dgCol title="核稿人"  field="NUCLEAR_DRAFT_USERNAME"   hidden="true"   queryMode="group"  width="100"></t:dgCol> --%>
   <t:dgCol title="联系人id"  field="CONTACT_ID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="主要联系人id"  field="mainContactId" hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="联系人"  field="CONTACT_NAME"    queryMode="group"  width="60"></t:dgCol>
   <%-- <t:dgCol title="联系电话"  field="CONTACT_PHONE"   hidden="true"   queryMode="group"  width="100"></t:dgCol> --%>
   <t:dgCol title="公文状态"  field="ARCHIVE_FLAG"   codeDict="1,YPDZT"   queryMode="group"  width="70"></t:dgCol>
<%--    <t:dgCol title="核稿状态"  field="NUCLEAR_DRAFT_STATUS"   codeDict="1,HGZT"   queryMode="group"  width="70"></t:dgCol> --%>
   <t:dgCol title="处理状态" hidden="true"  field="OPERATE_STATUS"  codeDict="1,CHULIZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="签收状态" hidden="true" field="SIGN_IN_FLAG"  codeDict="1,QSZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="是否为传阅"  field="IFCIRCULATE" hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="归档人id"  field="ARCHIVE_USERID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="归档人"  field="ARCHIVE_USERNAME"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="归档时间"  field="ARCHIVE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="80" align="center"></t:dgCol>
   <t:dgCol title="创建人"  field="CREATE_BY"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="CREATE_NAME"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="修改人"  field="UPDATE_BY"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="UPDATE_NAME"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="修改时间"  field="UPDATE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="80" align="center"></t:dgCol>
   <t:dgCol title="驳回人id"  field="BACK_USERID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="驳回人"  field="BACK_USERNAME"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="驳回人意见"  field="BACK_SUGGESTION"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="生成时间"  field="CREATE_DATE" formatter="yyyy-MM-dd"  queryMode="group"  width="100" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130" frozenColumn="true"></t:dgCol>
   <c:if test="${operateStatus eq 0}"><!-- 未处理才会有办理和完成 -->
   <t:dgFunOpt  title="办理"  funname="transaction"/>
   <t:dgFunOpt exp="mainContactId#eq\#${uid}" title="完成"  funname="goFinish(id,rid)"></t:dgFunOpt> 
   </c:if>
   <c:if test="${operateStatus eq 1}"><!-- 已处理才会有套红套打 -->
   <t:dgFunOpt title="套红"  funname="goOfficePage(id)" />
   <t:dgFunOpt title="套打"  funname="goTDOfficePage(id)" />
   </c:if>
   <t:dgToolBar title="查看" icon="icon-search" funname="goDetail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
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
 			$("#tOSendBillListtb").find("select[name='SEND.TYPE.CODE']").attr("style","height:23px;width:100px;;vertical-align: bottom;");
 });
 

 </script>