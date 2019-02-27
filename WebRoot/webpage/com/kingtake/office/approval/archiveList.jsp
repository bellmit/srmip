<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="archiveList" checkbox="false" fitColumns="true" 
			actionUrl="tOApprovalController.do?queryDatagrid" title="公文检索查询列表"
			idField="id" fit="true" queryMode="group">
			
			<t:dgCol title="公文主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="标题" field="title" query="true" queryMode="single" width="200"></t:dgCol>
			<t:dgCol title="公文编号" field="file_num" query="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="密级" field="security_grade" query="true" queryMode="single" width="60" codeDict="0,XMMJ"></t:dgCol>
			<t:dgCol title="承办单位id" field="undertake_unit_id" hidden="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="承办单位" field="undertake_unit_name"  query="true"  queryMode="single" width="120"></t:dgCol> 
			<t:dgCol title="联系人id" field="contact_id" hidden="true" queryMode="group" width="90"></t:dgCol>
			<t:dgCol title="联系人" field="contact_name" query="true"  queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="联系方式" field="contact_phone" queryMode="single" width="120"></t:dgCol>
<%-- 			<t:dgCol title="处理状态" field="archive_flag" hidden="true" queryMode="group" width="80" codeDict="1,YPDZT"></t:dgCol> --%>
			<t:dgCol title="归档人id" field="archive_userid" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="归档人" field="archive_username" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="归档时间" field="archive_date" hidden="true" queryMode="single" width="150" 
				align="center" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>  
			<t:dgCol title="创建人账号" field="create_by" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建人" field="create_name" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建时间" field="create_date" hidden="true" queryMode="group" width="150" 
				align="center" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="修改人账号" field="update_by" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="修改人" field="update_name" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="修改时间" field="update_date" hidden="true" queryMode="group" width="150" 
				align="center" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
			<t:dgCol title="类型" field="type" hidden="true"  queryMode="group" width="150"></t:dgCol>
			<t:dgCol title="是否电子审批" field="EAUDIT_FLAG" queryMode="group" width="80" codeDict="0,SFBZ"></t:dgCol>
			<t:dgCol title="操作" width="150" field="opt"></t:dgCol>
			<t:dgFunOpt  title="套红"  exp="EAUDIT_FLAG#eq#1" funname="goOfficePage(id,type)" operationCode="th"></t:dgFunOpt>
   			<t:dgFunOpt  title="套打"  exp="EAUDIT_FLAG#eq#1" funname="goTDOfficePage(id,type)" operationCode="td"></t:dgFunOpt>
   			<t:dgFunOpt  title="查看公文"  exp="EAUDIT_FLAG#eq#1" funname="goDetail(id,type)"></t:dgFunOpt>
<%-- 		   	<t:dgToolBar title="查看公文" icon="icon-search" funname="detailFun" width="1000" height="620"></t:dgToolBar> --%>
		   	<t:dgToolBar title="查看公文登记" icon="icon-search" funname="detailReg" width="1000" height="620"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
//查看公文
function detailFun(title,url,id,width,height){
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看记录');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
	
	var type = rowsData[0].type;
	if(type == 'APPR'){
		url = 'tOApprovalController.do?goDetail';
	}else if(type == 'RECEIVE'){
		url = 'tOReceiveBillController.do?goDetail';
	}else if(type == 'SEND'){
		url = 'tOSendBillController.do?goDetail';
	}
	
    url += '&load=detail&id='+rowsData[0].id;
	createdetailwindow(title,url,width,height);
}
//查看公文登记
function detailReg(title,url,id,width,height){
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length == 0) {
		tip('请选择查看记录');
		return;
	}
	if (rowsData.length > 1) {
		tip('请选择一条记录再查看');
		return;
	}
	
	var type = rowsData[0].type;
	if(type == 'APPR'){
		url = 'tOApprovalController.do?goSendReceiveReg';
	}else if(type == 'RECEIVE'){
		url = 'tOReceiveBillController.do?goSendReceiveReg';
	}else if(type == 'SEND'){
		url = 'tOSendBillController.do?goSendReceiveReg';
	}else if(type == 'REG'){
		url = 'tOSendReceiveRegController.do?goUpdate';
	}
    url += '&load=detail&id='+rowsData[0].id;
	createdetailwindow(title,url,width,height);
}

/* function dbcl(rowIndex,rowData){
	var type = rowData.type;
	var url;
	if(type == 'APPR'){
		url = 'tOApprovalController.do?goUpdate';
	}else if(type == 'RECEIVE'){
		url = 'tOReceiveBillController.do?goUpdate';
	}else if(type == 'SEND'){
		url = 'tOSendBillController.do?goUpdate';
	}
	
    url += '&load=detail&id='+rowData.id;
	createdetailwindow("查看",url,900,520);
} */

function courseListWordXlsByT(id) {
	var rowsData = $('#archiveList').datagrid('getSelections');
	var type = rowsData[0].type;
	if(type == 'APPR'){
		url = "tOApprovalController.do?exportDocByT&id="+id+"&flag=0";
	}else if(type == 'RECEIVE'){
		url = "tOReceiveBillController.do?exportDocByT&id="+id+"&flag=0";
	}else if(type == 'SEND'){
		url = "tOSendBillController.do?exportDocByT&id="+id+"&flag=0";
	}
	JeecgExcelExport(url,"archiveList");
}

function courseListWordXlsByT1(id) {
	var rowsData = $('#archiveList').datagrid('getSelections');
	var type = rowsData[0].type;
	if(type == 'APPR'){
		url = "tOApprovalController.do?exportDocByT&id="+id+"&flag=1";
	}else if(type == 'RECEIVE'){
		url = "tOReceiveBillController.do?exportDocByT&id="+id+"&flag=1";
	}else if(type == 'SEND'){
		url = "tOSendBillController.do?exportDocByT&id="+id+"&flag=1";
	}
	JeecgExcelExport(url,"archiveList");
}

//套红
function goOfficePage(id,type){
    var url;
    var title;
    if(type=='RECEIVE'){
        url='tOReceiveBillController.do?goOfficePage&id='+id;
        title='阅批单套红';
    }else if(type=='SEND'){
        url='tOSendBillController.do?goOfficePage&id='+id;
        title='发文呈批单套红';
    }else if(type=='APPR'){
        url='tOApprovalController.do?goOfficePage&id='+id;
        title="呈批件套红";
    }
	 $.dialog({
			id:'inputPage',
			content: 'url:'+url,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
    		left : '0%',
    		top : '0%',
			title : title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}
//套打
function goTDOfficePage(id,type){
    var url;
    var title;
    if(type=='RECEIVE'){
        url='tOReceiveBillController.do?goTDOfficePage&id='+id;
        title='阅批单套打';
    }else if(type=='SEND'){
        url='tOSendBillController.do?goTDOfficePage&id='+id;
        title='发文呈批单套打';
    }else if(type=='APPR'){
        url='tOApprovalController.do?goTDOfficePage&id='+id;
        title="呈批件套打";
    }
	 $.dialog({
			id:'inputPage',
			content: 'url:'+url,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
    		left : '0%',
    		top : '0%',
			title : title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}

//查看公文
function goDetail(id,type){	
    var url;
    var title = "查看公文";
    if(type == 'APPR'){
		url = 'tOApprovalController.do?goDetail';
	}else if(type == 'RECEIVE'){
		url = 'tOReceiveBillController.do?goDetail';
	}else if(type == 'SEND'){
		url = 'tOSendBillController.do?goDetail';
	}
    url += '&load=detail&id=' + id;
	 $.dialog({
			id:'inputPage',
			content: 'url:'+url,
			lock : true,
			width : window.top.document.body.offsetWidth,
			height : window.top.document.body.offsetHeight-100,
    		left : '0%',
    		top : '0%',
			title : title,
			opacity : 0.3,
			cache:false,
		    cancelVal: '关闭',
		    cancel: function(){
		    }
		});
}
</script>