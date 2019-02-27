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
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOSendReceiveRegList" onDblClick="dbcl" checkbox="true" 
  	fitColumns="true" title="收文列表" idField="id" fit="true" queryMode="group"
  	actionUrl="tOBorrowBillController.do?datagridForList">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"></t:dgCol>
   <t:dgCol title="归档人id"  field="archiveUserid"  hidden="true"  queryMode="single" width="280" ></t:dgCol>
   <t:dgCol title="标题"  field="title" query="true" sortable="false" isLike="true" queryMode="single" width="280" ></t:dgCol>
   <t:dgCol title="公文编号"  field="mergeFileNum" sortable="false" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="密级" field="securityGrade" codeDict="0,XMMJ" sortable="false" queryMode="group"  width="60" align="center"></t:dgCol>
   <t:dgCol title="公文种类" field="regType" codeDict="1,GWZL" sortable="false" queryMode="single" query="true"  width="80" align="center"></t:dgCol>
   <t:dgCol title="收文日期"  field="registerDate" formatter="yyyy-MM-dd" sortable="false"  query="true" queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="公文状态" field="generationFlag" queryMode="group" codeDict="1,YPDZT" width="90" align="center"></t:dgCol>
   <t:dgCol title="关键字"  field="keyname" sortable="false" hidden="true" queryMode="group"  width="160"></t:dgCol>
   <t:dgCol title="收发文标志" hidden="true" sortable="false" field="registerType" queryMode="group" codeDict="1,SRFLAG" width="80"></t:dgCol>
  </t:datagrid>
  </div>
 </div>
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
	 createdetailwindow("查看", "tOSendReceiveRegController.do?goUpdate&id="+id+"&load=detail","100%","100%");
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
                // 	    	reloadtOSendReceiveRegList();
                //		    	window.location.reload();
            }
        });
    }

    function doArchive(id, registerType, regType,mergeFileNum,index) {
        var url;
        var row = $('#tOSendReceiveRegList').datagrid('getRows')[index];
        if (registerType == 0) {//收文
            url = "tOReceiveBillController.do?doArchive&regId=" + id;
            sendArchive(url);
        } else {//发文
            if (row.regType == '13') {//呈批件
                url = "tOApprovalController.do?doArchive&regId=" + id;
                sendArchive(url);
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
    function ImportXls() {
        openuploadwin('Excel导入', 'tOSendReceiveRegController.do?upload', "tOSendReceiveRegList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tOSendReceiveRegController.do?exportXls&registerType=${registerType}", "tOSendReceiveRegList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOSendReceiveRegController.do?exportXlsByT", "tOSendReceiveRegList");
    }
    
    //选择借阅的公文
    function selectReg(){
        var checked = $("#tOSendReceiveRegList").datagrid("getChecked");
        if(checked.length==0){
            tip("请选择借阅的收文！");
            return null;
        }
        var ids = [];
        for(var i=0;i<checked.length;i++){
            ids.push(checked[i].id);
        }
        return ids;
    }
</script>