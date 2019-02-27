<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="org.jeecgframework.web.system.pojo.base.TSUser"%>
<%@page import="org.jeecgframework.core.util.ResourceUtil"%>
<%@page import="com.kingtake.common.constant.ReceiveBillConstant"%>
<%
TSUser user = ResourceUtil.getSessionUserName();
String uid = user.getId();
request.setAttribute("uid", uid);
request.setAttribute("unCreated", ReceiveBillConstant.BILL_UNCREATED);
request.setAttribute("flowing", ReceiveBillConstant.BILL_FLOWING);
request.setAttribute("rebut", ReceiveBillConstant.BILL_REBUT);
request.setAttribute("complete", ReceiveBillConstant.BILL_COMPLETE);
%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
<input type="hidden" name="刷新" id="reloadBtn" onclick="reloadPage()">
  <t:datagrid name="tOSendReceiveRegList" onDblClick="dbcl" checkbox="false" 
  	fitColumns="true" title="${title}" idField="id" fit="true" queryMode="group" extendParams="remoteSort:false,"
  	actionUrl="tOSendReceiveRegController.do?datagridForList&registerType=${registerType}">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"></t:dgCol>
   <c:if test="${registerType eq 0}">
    <t:dgCol title="归档人id"  field="archiveUserid"  hidden="true"  queryMode="single" width="280" ></t:dgCol>
   <t:dgCol title="收文编号"  field="filesId" queryMode="group" ></t:dgCol>
   <t:dgCol title="公文编号"  field="mergeFileNum"  queryMode="group"  width="120" extendParams="formatter:fileNumFormatter,"></t:dgCol>
   <t:dgCol title="来文单位"  field="office" query="true"  isLike="true" queryMode="single" ></t:dgCol>
   <t:dgCol title="密级" field="securityGrade" codeDict="0,XMMJ"  queryMode="group"  width="60" align="center" ></t:dgCol>
   <t:dgCol title="标题"  field="title" query="true"  isLike="true" queryMode="single" width="280" ></t:dgCol>
   <t:dgCol title="份数"  field="count"    queryMode="group"  width="40"></t:dgCol>
   <t:dgCol title="承办人"  field="undertakeContactName"    queryMode="group"></t:dgCol>
   <t:dgCol title="承办单位"  field="undertakeDeptName"    queryMode="group"></t:dgCol>
   <t:dgCol title="公文种类" field="regType" codeDict="1,GWZL"  queryMode="single" query="true"  width="80" align="center"></t:dgCol>
   <t:dgCol title="收文日期"  field="registerDate" formatter="yyyy-MM-dd"   query="true" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="是否加急"  field="zyjb" codeDict="0,SFBZ" query="true" queryMode="single"  width="60"></t:dgCol>
   </c:if>
   <c:if test="${registerType eq 1}">
    <t:dgCol title="归档人id"  field="archiveUserid"  hidden="true"  queryMode="single" width="280" ></t:dgCol>
   <t:dgCol title="发文日期"  field="registerDate" formatter="yyyy-MM-dd"   query="true" queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="公文编号"  field="mergeFileNum"  queryMode="group"  width="180" extendParams="formatter:fileNumFormatter,"></t:dgCol>
   <t:dgCol title="公文种类" field="regType" codeDict="1,GWZL"  queryMode="single" query="true"  width="80" align="center"></t:dgCol>
   <t:dgCol title="密级" field="securityGrade" codeDict="0,XMMJ"  queryMode="group"  width="60" align="center" ></t:dgCol>
   <t:dgCol title="标题"  field="title" query="true"  isLike="true" queryMode="single" width="280" ></t:dgCol>
   <t:dgCol title="承办人"  field="undertakeContactName"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="承办单位"  field="undertakeDeptName"  queryMode="single" width="100" ></t:dgCol>
   <t:dgCol title="发文单位"  field="office" queryMode="single" width="100" ></t:dgCol>
   </c:if>
   <t:dgCol title="公文状态" field="generationFlag" queryMode="group" codeDict="1,YPDZT" width="90" align="center"></t:dgCol>
   <t:dgCol title="关键字"  field="keyname"  hidden="true" queryMode="group"  width="160"></t:dgCol>
   <t:dgCol title="是否电子审批"  field="eauditFlag" hidden="true" queryMode="group"  width="160"></t:dgCol>
<%--    <t:dgCol title="机关"  field="office"    queryMode="group"  ></t:dgCol> --%>
<%--    <t:dgCol title="序号"  field="num"    queryMode="group"  width="40"></t:dgCol> --%>
<%--    <t:dgCol title="号数"  field="referenceNum"  queryMode="group" width="40"></t:dgCol> --%>
   <t:dgCol title="收发文标志" hidden="true" sortable="false" field="registerType" queryMode="group" codeDict="1,SRFLAG" width="80"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt exp="generationFlag#eq\#${unCreated}" title="删除" url="tOSendReceiveRegController.do?doDel&id={id}" />
   <t:dgFunOpt exp="eauditFlag#eq#1&&generationFlag#eq\#${complete}&&archiveUserid#eq\#${uid}" funname="doArchive(id,registerType,regType,mergeFileNum)" title="归档"></t:dgFunOpt>
   <t:dgFunOpt exp="eauditFlag#eq#1&&generationFlag#ne\#${unCreated}" funname="goDetail(id,registerType,regType)" title="查看公文"></t:dgFunOpt>
   <t:dgToolBar title="录入" icon="icon-add" url="tOSendReceiveRegController.do?goAdd&registerType=${registerType}" funname="add" height="100%" width="100%"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit"  funname="goUpdate" height="100%" width="100%"></t:dgToolBar>
   <t:dgToolBar title="查看基本信息" icon="icon-search" url="tOSendReceiveRegController.do?goUpdate" funname="detail" height="100%" width="100%"></t:dgToolBar>
   <t:dgToolBar title="导出文件清单" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="复制" icon="icon-edit" funname="copy"></t:dgToolBar>
   <t:dgToolBar title="查看历史版本" icon="icon-search" funname="goHistory"></t:dgToolBar>
   <%-- <t:dgToolBar title="生成公文" icon="icon-edit"  funname="createForm"></t:dgToolBar> --%>
   <t:dgFunOpt exp="eauditFlag#eq#1&&generationFlag#eq\#${unCreated}" title="公文流转" funname="createForm" ></t:dgFunOpt>
   <t:dgFunOpt exp="eauditFlag#eq#1&&generationFlag#eq\#${rebut}" title="公文流转" funname="createForm" ></t:dgFunOpt>
   <t:dgFunOpt exp="eauditFlag#eq#1&&generationFlag#ne\#${unCreated}" title="更新公文" funname="updateBill(id)" ></t:dgFunOpt>
   <t:dgFunOpt exp="eauditFlag#eq#1" title="打包下载" funname="doZip(id)" ></t:dgFunOpt>
<%--    <c:if test="${registerType eq 1}"> --%>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportSendXlsByT"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportSendXls"></t:dgToolBar>
<%--    </c:if> --%>
  </t:datagrid>
  </div>
 </div>
<script src = "webpage/com/kingtake/office/sendReceive/tOSendReceiveRegList.js?${tm}"></script>		
<script type="text/javascript">
 $(document).ready(function(){
	//给时间控件加上样式
	$("#tOSendReceiveRegListtb").find("input[name='registerDate_begin']")
		.attr("class","Wdate").attr("style","height:23px;width:100px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'registerDate_end\')}'});});
	$("#tOSendReceiveRegListtb").find("input[name='registerDate_end']")
		.attr("class","Wdate").attr("style","height:23px;width:100px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'registerDate_begin\')}'});});
	$("#tOSendReceiveRegListtb select[name='regType']").attr('style','height:23px;width:100px;vertical-align: bottom;');
 });
 
 $(function(){
	 $("#tOSendReceiveRegListtb input[name='title']").attr('style','width:180;');
		var input = $("#tOSendReceiveRegListtb input[name='regType']");
		input.combobox({
			width : 80,
			url : 'tBCodeTypeController.do?getDetailList&codeTypeId=40288af64e56bf48014e57136e1f001a',
			valueField : 'code',
			textField : 'name'
		});
	});
 
 function dbcl(rowIndex,rowData){
     var id = rowData.id;
     var regType = rowData.regType;
     var registerType = rowData.registerType;
     if(rowData.generationFlag =="<%=ReceiveBillConstant.BILL_UNCREATED%>" && rowData.generationFlag !="<%=ReceiveBillConstant.BILL_REBUT%>" && rowData.eauditFlag=='1'){
         createForm(rowIndex);
     }else if(rowData.eauditFlag=='0'){
         detail("查看基本信息","tOSendReceiveRegController.do?goUpdate", "tOSendReceiveRegList",'100%','100%');
     }else{
         goDetail(id,registerType,regType);
     }
	 /* var id = rowData.id;
	 createdetailwindow("查看", "tOSendReceiveRegController.do?goUpdate&id="+id+"&load=detail","100%","100%"); */
 }
 
 function goUpdate(){
	 var row = $('#tOSendReceiveRegList').datagrid('getSelections')[0];
	 if(row==null){
		 tip("请选择一条记录进行编辑！");
	 }else{
		 if(row.generationFlag !="<%=ReceiveBillConstant.BILL_UNCREATED%>" && row.generationFlag !="<%=ReceiveBillConstant.BILL_REBUT%>"){
			 tip("仅未生成或被驳回的登记记录可编辑！");
		 }else{
			 var id = row.id;
			 $.dialog({
					content: 'url:tOSendReceiveRegController.do?goUpdate&id='+id,
					lock : true,
					title:"编辑",
					width : window.top.document.body.offsetWidth,
					height : window.top.document.body.offsetHeight-100,
					opacity : 0.3,
					cache:false,
					okVal:'保存',
					ok:function(){
						iframe = this.iframe.contentWindow;
						saveObj();
						reloadtOSendReceiveRegList();
						return false;
					},
				    cancelVal: '关闭',
				    cancel: function(){
				    	reloadtOSendReceiveRegList();
				    }
				}).zindex();
		 }
	 }
	 
 }
 
 function createForm(index){
	    var row = $('#tOSendReceiveRegList').datagrid('getRows')[index];
		var registerType = row.registerType;
		var generationFlag = row.generationFlag;
		if(generationFlag=="<%=ReceiveBillConstant.BILL_UNCREATED%>" || generationFlag=="<%=ReceiveBillConstant.BILL_REBUT%>") {
            var id = row.id;
            var url;
            if (registerType == 0) {//收文
                url = "url:tOReceiveBillController.do?goAdd&regid=" + id;
                goForm(url);
            } else {//发文
                if (row.regType == '13') {//呈批件
                    url = "url:tOApprovalController.do?goAdd&regid=" + id;
                    goApprovalForm(url);
                } else {
                    url = "url:tOSendBillController.do?goAdd&regid=" + id;
                    goApprovalForm(url);
                }
            }
        } else {
            tip("该登记记录存在有效公文，无法再次生成！");
        }
    }

    function goForm(url) {
        $.dialog({
            id : 'regList',
            content : url,
            lock : true,
            width : window.top.document.body.offsetWidth,
            height : window.top.document.body.offsetHeight - 100,
            left : '0%',
            top : '0%',
            title : "生成公文",
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                saveObj();
                return false;
            },
            cancelVal : '关闭',
            cancel : function() {
            }
        });
    }

    function goApprovalForm(url) {
        $.dialog({
            id : 'approvalForm',
            content : url,
            lock : true,
            width : window.top.document.body.offsetWidth,
            height : window.top.document.body.offsetHeight - 100,
            left : '0%',
            top : '0%',
            title : "生成公文",
            opacity : 0.3,
            cache : false,
            okVal : "发送",
            ok : function() {
                iframe = this.iframe.contentWindow;
                iframe.goFirstSend();
                return false;

            },
            cancelVal : '关闭',
            cancel : function() {
            }
        });
    }

    function goSend() {
        var iWidth = 900; //弹出窗口的宽度;
        var iHeight = 500; //弹出窗口的高度;
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2; //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; //获得窗口的水平位置;
        var openUrl = "tOApprovalController.do?goFirstSend";
        window.open(openUrl, "", "height=" + iHeight + ", width=" + iWidth + ", top=" + iTop + ", left=" + iLeft);
    }

    function goDetail(regId, registerType, regType) {
        var url;
        if (registerType == 0) {//收文
            url = "url:tOReceiveBillController.do?goDetailTab&regId=" + regId;
        } else {//发文
            if (regType == '13') {//呈批件
                url = "url:tOApprovalController.do?goDetailTab&regId=" + regId;
            } else {
                url = "url:tOSendBillController.do?goDetailTab&regId=" + regId;
            }
        }

        $.dialog({
            content : url,
            lock : true,
            width : window.top.document.body.offsetWidth,
            height : window.top.document.body.offsetHeight - 100,
            left : '0%',
            top : '0%',
            title : "查看公文",
            opacity : 0.3,
            cache : false,
            cancelVal : '关闭',
            cancel : function() {
            }
        });
    }

    function doArchive(id, registerType, regType,mergeFileNum,index) {
        var url;
        var row = $('#tOSendReceiveRegList').datagrid('getRows')[index];
        if (registerType == 0) {//收文
            url = "tOReceiveBillController.do?doArchive&regId=" + id;
            $.messager.confirm('确认','您确定归档该公文吗？',function(r){    
                if (r){   
                 sendArchive(url);
                }
            });
        } else {//发文
            if (row.regType == '13') {//呈批件
                url = "tOApprovalController.do?doArchive&regId=" + id;
                if(mergeFileNum==""){//如果没有编号，则自动生成
                    $.dialog({
                        content : 'url:tOSendBillController.do?goDocNumReg&regId='+id,
                        lock : true,
                        width : 600,
                        height : 150,
                        title : '填写公文编号',
                        opacity : 0.3,
                        fixed : true,
                        cache : false,
                        ok : function() {
                            iframe = this.iframe.contentWindow;
                            var valid = iframe.checkForm();
                            if (valid) {
                                var docNum = iframe.getDocNum();
                                $.ajax({
                                    url : url,
                                    type : 'POST',
                                    data : docNum,
                                    dataType : 'json',
                                    success : function(data) {
                                        tip(data.msg);
                                        reloadtOSendReceiveRegList();
                                    }
                                });
                            } else {
                                return false;
                            }
                        },
                        cancelVal : '关闭',
                        cancel : true
                    /*为true等价于function(){}*/
                    }).zindex();
                   }else{
                $.messager.confirm('确认','您确定归档该公文吗？',function(r){    
                    if (r){    
                       sendArchive(url);
                    }
                });
               }
            } else {//发文
                url = "tOSendBillController.do?doArchive&regId=" + id;
                if(mergeFileNum==""){//如果没有编号，则自动生成
                $.dialog({
                    content : 'url:tOSendBillController.do?goDocNumReg&regId='+id,
                    lock : true,
                    width : 600,
                    height : 150,
                    title : '填写公文编号',
                    opacity : 0.3,
                    fixed : true,
                    cache : false,
                    ok : function() {
                        iframe = this.iframe.contentWindow;
                        var valid = iframe.checkForm();
                        if (valid) {
                            var docNum = iframe.getDocNum();
                            $.ajax({
                                url : url,
                                type : 'POST',
                                data : docNum,
                                dataType : 'json',
                                success : function(data) {
                                    tip(data.msg);
                                    reloadtOSendReceiveRegList();
                                }
                            });
                        } else {
                            return false;
                        }
                    },
                    cancelVal : '关闭',
                    cancel : true
                /*为true等价于function(){}*/
                }).zindex();
               }else{
                   $.messager.confirm('确认','您确定归档该公文吗？',function(r){    
                       if (r){    
                           $.ajax({
                               url : url,
                               type : 'POST',
                               dataType : 'json',
                               success : function(data) {
                                   tip(data.msg);
                                   reloadtOSendReceiveRegList();
                               }
                           });   
                       }    
                   }); 
               }
            }
        }

    }

    function sendArchive(url) {
        $.ajax({
            url : url,
            type : 'POST',
            timeout : 3000,
            dataType : 'json',
            success : function(data) {
                tip(data.msg);
                reloadtOSendReceiveRegList();
            }
        });
    }

    function reloadPage() {
        reloadtOSendReceiveRegList();
    }

    //导入
    function ImportSendXls() {
        openuploadwin('Excel导入', 'tOSendReceiveRegController.do?upload&regesterType=${registerType}', "tOSendReceiveRegList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tOSendReceiveRegController.do?exportXls&registerType=${registerType}", "tOSendReceiveRegList");
    }

    //模板下载
    function ExportSendXlsByT() {
        JeecgExcelExport("tOSendReceiveRegController.do?exportXlsByT&registerType=${registerType}", "tOSendReceiveRegList");
    }
    
    //更新公文
    function updateBill(id){
        var url = "tOSendReceiveRegController.do?goUpdateBill";
    	url += '&id='+id;
    	$.messager.confirm('确认','此操作会将公文中的信息覆盖，确定继续吗？',function(r){    
    	    if (r){    
    	     createwindow("更新公文",url,"100%","100%");
    	    }    
    	}); 
    }
    
    //更新公文
    function doZip(id){
        var url = "tOSendReceiveRegController.do?doZip&gzip=false";
    	url += '&regId='+id;
    	window.open(url);
    }
    
    //公文编号格式化
    function fileNumFormatter(value,row,index){
        if(value!=""){
            return value+"号";
        }
    }
    
</script>