<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uid = user.getId();
request.setAttribute("uid", uid);
request.setAttribute("flowing", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function transaction(rowIndex){
	var rowData = $('#tOReceiveBillList').datagrid('getData').rows[rowIndex];
	doubleClick(rowIndex,rowData);
}

function doubleClick(rowIndex,rowData){
	var operateStatus = <%=request.getAttribute("operateStatus") %>;
	var id = rowData.id;
	var rid = rowData.rid;
	if(operateStatus == '0'){//办理
		$.dialog({
			id:'inputPage',
			content: 'url:tOReceiveBillController.do?goOperate&id='+id+'&operateStatus='+operateStatus+'&rid='+rid,
			lock : true,
			//zIndex:1990,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
        	left : '0%',
        	top : '0%',
			title:"阅批单处理",
			opacity : 0.3,
			cache:false,
			button:[{
	            name: '完成审批',
	            callback: function () {
	            var r = confirm('确定完成审批,将公文返还给承办人吗？'); 
		     	    if (r) {
	                doPass(id,rid);
		     	    }
	                return false;
	            }
	        },{
	            name : '继续流转',
	            focus : true,
	            callback : function () {
	                iframe = this.iframe.contentWindow;
					goSend(id,rid);
					return false;
	            }
	        },{
	            name: '返回修改',
	            callback: function () {
	            var r = confirm('确定返回给承办人修改?');
	     			if (r) {
	                doReturn(id,rid);
	     			}
	                return false;
	            }
	        }],
		    cancelVal: '关闭',
		    cancel: function(){
		    	reloadtOReceiveBillList();
		    }
		});
	}else{
		goDetail(rowIndex);
	}
}

function goDetail(rowIndex){
	var rowsData = $('#tOReceiveBillList').datagrid('getSelections');
	var id = rowsData[0].id;
	$.dialog({
		id:'detailPage',
		content: 'url:tOReceiveBillController.do?goDetail&id='+id,
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
    	left : '0%',
    	top : '0%',
		title:"阅批单查看",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtOReceiveBillList();
	    }
	});
}

function goSend(id,rid){
	var iWidth=900; //弹出窗口的宽度;
    var iHeight=500; //弹出窗口的高度;
    var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
    var openUrl = "tOReceiveBillController.do?goSend&id="+id+"&rid="+rid;
    window.open(openUrl,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
}

//完成审批发给联系人
function doPass(id,rid){
    doPassReturn(id,rid,"1");
}
//返回修改
function doReturn(id,rid){
    doPassReturn(id,rid,"0");
}

function doPassReturn(id,rid,flag){
    var win = $.dialog.list['inputPage'].content;
    var remark = "";
    if(flag=="1"){
        win.SaveSignature();
        remark = "同意";
    }else{
        remark = "返回修改";
    }
    var openUrl = "tOReceiveBillController.do?passReturn";
    $.ajax({
        url:openUrl,
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

function doFinish(id,rid){
	$.dialog.confirm("是否确定将公文置为完成状态？",function(){
		$.ajax({
			url:'tOReceiveBillController.do?doFinish&id='+id+'&rid='+rid,
			type : 'POST',
			timeout : 3000,
			dataType : 'json',
			success:function(data){
				tip(data.msg);
				refresh();
//	 			reloadtOReceiveBillList();
			}
		});
	},function(){});
	
}

function refresh(){
    var jq = top.jQuery;
    var tab = jq('#maintabs').tabs("getSelected"); 
	tab.panel('refresh');
}

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOReceiveBillController.do?upload', "tOReceiveBillList");
}

//导出
function ExportXls(index) {
	JeecgExcelExport("tOReceiveBillController.do?exportXls&operateStatus=${operateStatus}&registerType=${registerType}","tOReceiveBillList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOReceiveBillController.do?exportXlsByT","tOReceiveBillList");
}

function doSignIn(id,index){
	$.post("tOReceiveBillController.do?doSignIn&id="+id,"",function(data){
		reloadtOReceiveBillList();
		data = $.parseJSON(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg
        // 							timeout : 1000 * 6
        });
	}); 
}

function archive(id,index){
	$.dialog.confirm('归档后该文件将无法再次发送或审批，是否确定执行此操作？', function(){
	$.post("tOReceiveBillController.do?archive&id="+id,"",function(data){
		reloadtOReceiveBillList();
		data = $.parseJSON(data);
		$.dialog.setting.zIndex = 1980;
        $.messager.show({
            title : '提示信息',
            msg : data.msg
        // 							timeout : 1000 * 6
        });
	}); 
	},function(){
	});
}

function courseListWordXlsByT(id,index) {
	JeecgExcelExport("tOReceiveBillController.do?exportDocByT&id="+id+"&flag=0","tOReceiveBillList");
}

function courseListWordXlsByT1(id,index) {
	JeecgExcelExport("tOReceiveBillController.do?exportDocByT&id="+id+"&flag=1","tOReceiveBillList");
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;height: 400px;">
  <t:datagrid name="tOReceiveBillList"  onDblClick="doubleClick" fitColumns="true"  title="收文阅批单信息列表" 
  	actionUrl="tOReceiveBillController.do?datagrid&operateStatus=${operateStatus}&registerType=${registerType}" 
  	idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="接收主键"  field="rid"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="来文单位id"  field="RECEIVE_UNIT_ID"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="联系人id"  field="CONTACT_ID"  hidden="true"  queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="联系电话" field="CONTACT_TEL"   hidden="true"   queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="登记日期" field="REGISTER_TIME" hidden="true" formatter="yyyy-MM-dd" queryMode="group"  width="80" align="center"></t:dgCol>
   <t:dgCol title="登记人id"  field="REGISTER_ID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="登记人姓名"  field="REGISTER_NAME"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="登记人部门id"  field="REGISTER_DEPART_ID"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="登记人部门名称"  field="REGISTER_DEPART_NAME"  hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="处理状态" hidden="true"  field="OPERATE_STATUS"  codeDict="1,CHULIZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="签收状态" hidden="true" field="SIGN_IN_FLAG"  codeDict="1,QSZT"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="是否为传阅"  field="IFCIRCULATE" hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="归档人id"  field="ARCHIVE_USERID"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档人姓名"  field="ARCHIVE_USERNAME"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档时间"  field="ARCHIVE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="创建人"  field="CREATE_BY"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="CREATE_NAME"  hidden="true"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="UPDATE_BY"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="UPDATE_NAME"   hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="UPDATE_DATE" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   
   <t:dgCol title="公文标题"  field="TITLE"  frozenColumn="true" query="true" queryMode="single" width="230"></t:dgCol>
   <t:dgCol title="公文编号" field="FILE_NUM" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="来文单位" field="RECEIVE_UNIT_NAME" query="true" queryMode="single" width="150"></t:dgCol>
   <t:dgCol title="密级"  field="SECRITY_GRADE" codeDict="0,XMMJ"  queryMode="single"  width="40"></t:dgCol>
   <t:dgCol title="承办单位"  field="DUTY_NAME"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="联系人"  field="CONTACT_NAME"    queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="公文状态"  field="ARCHIVE_FLAG"  codeDict="1,YPDZT"  queryMode="group"  width="60"></t:dgCol>
   <t:dgCol title="公文种类"  field="REG_TYPE"  codeDict="1,GWZL"  queryMode="single" query="true" width="60"></t:dgCol>
   <t:dgCol title="生成时间"  field="CREATE_DATE"  formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>

   <t:dgCol title="操作" width="150" field="opt" frozenColumn="true"></t:dgCol>
   <c:if test="${operateStatus eq 0}"><!-- 未处理才会有办理和归档 -->
   <t:dgFunOpt  title="办理"  funname="transaction"/>
   <t:dgFunOpt exp="CONTACT_ID#eq\#${uid}" title="完成"  funname="doFinish(id,rid)"></t:dgFunOpt> 
   </c:if>
	<c:if test="${operateStatus eq 1}"><!-- 已处理才会有套红套打 -->
   <t:dgFunOpt title="套红"  funname="goOfficePage(id)" ></t:dgFunOpt>
   <t:dgFunOpt title="套打"  funname="goTDOfficePage(id)" ></t:dgFunOpt> 
	</c:if>
   <t:dgToolBar title="查看" icon="icon-search"  funname="goDetail"></t:dgToolBar>
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
 			$("#tOReceiveBillListtb").find("select[name='REG.TYPE']").attr("style","height:23px;width:100px;vertical-align: bottom;");
 });
 
 //套红
 function goOfficePage(id){
	 $.dialog({
			id:'inputPage',
			content: 'url:tOReceiveBillController.do?goOfficePage&id='+id,
			lock : true,
			//zIndex:1990,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
     		left : '0%',
     		top : '0%',
			title:"阅批单套红",
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
			content: 'url:tOReceiveBillController.do?goTDOfficePage&id='+id,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
     		left : '0%',
     		top : '0%',
			title:"阅批单套打",
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
 }
 
 $(function(){
		var input = $("#tOReceiveBillListtb input[name='REG.TYPE']");
		input.combobox({
			width : 100,
			url : 'tBCodeTypeController.do?getDetailList&codeTypeId=40288af64e56bf48014e57136e1f001a',
			valueField : 'code',
			textField : 'name'
		});
	});

 </script>