<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
//审批状态格式化
function auditStatusFormat(value,row,index){
	  if(value=="0"){
		  return "未提交";
	  }else if(value=="1"){
		  return "待审核";
	  }else if(value=="2"){
		  return "通过";
	  }else if(value=="3"){
		  return '未通过<a href="#" style="cursor: pointer;color:red;" onclick=showMsg("'+row.id+'")>[查看意见]</a>';
    }
}

function ysStatusFormat(value,row,index){
	  if(value=="0"){
		  return "否";
	  }else if(value=="1"){
		  return "是";
	  }
}

//弹出消息
function showMsg(id){
	  $.ajax({
		 url:'tPmIncomePlanController.do?getPropose&id='+id,
		 cache:false,
		 type:'GET',
		 dataType:'json',
		 success:function(data){
			 $.messager.alert('意见',data.msg);
		 }
	  });
}

/** 发送审核 */
function sendAduit(id){
	$.messager.confirm('确认','您确认将该计划发送审核吗？',function(r){    
	    if (r){    
	    	 var url = "tPmProjectPlanController.do?doSendAudit&id="+id;
		   	 $.ajax({
		   		 url:url,
		   		 cache:false,
		   		 type:'GET',
		   		 dataType:'json',
		   		 success:function(data){
		   			 if(data.success){
		   				//debugger;
		   				var url = "tPmApprLogsController.do?goApprSend&apprId="+id+"&apprType=24";
		   			    $.dialog({
		   			        id : 'apprSend',
		   			        lock : true,
		   			        background : '#000', /* 背景色 */
		   			        opacity : 0.5, /* 透明度 */
		   			        width : 520,
		   			        height : 260,
		   			        title : '发送审核',
		   			        content : 'url:' + url,
		   			        ok : function() {
		   			        	//debugger;
		   			        	iframe = this.iframe.contentWindow;
		   			        	var receiveUseNames = iframe.$("#receiveUseNames").val();
		   			        	var receiveUseIds = iframe.$("#receiveUseIds").val();
		   			        	var senderTip = iframe.$("#senderTip").val();
		   			        	
		   			        	var data = {id:id, receiveUseNames:receiveUseNames, receiveUseIds:receiveUseIds, senderTip:senderTip};
		   			        	
		   			        	try{
		   							//saveObj();
		   							$.ajax({
		   				                url : 'tPmProjectPlanController.do?sendAudit',
		   				                type : 'post',
		   				                dataType : 'json',
		   				                data : data,
		   				                success : function(data) {
		   				                	
		   				                }
		   				            });
		   			        	}catch(e){}
		   			        	
		   						tPmProjectPlanListsearch();
		   						try{
	   								var apprInfo = window.$.dialog.list['apprSend'];
	   								apprInfo.close();
	   							}catch(e){}
		   							
		   						return false;
		   			        },
		   			        cancelVal : '关闭',
		   			        cancel : function(){
		   			          //reloadTable();
		   			        }
		   			      });
		   				
		   				
		   			 }else{
		   				$.messager.alert('信息',"发送审核失败！失败原因如下：<br>"+data.msg.replace(/#/g,'<br>'));
		   			 }
		   		 }
		   	  });
	    }    
	});
}

/**进入发送审核流程
function setAppr(id){
	//var url = "tPmApprLogsController.do?goApprSend&apprId="+id+"&apprType=?"
			
	openSendAudit(id,"0","20") ; //id,0,20

	//http://localhost:8080/srmip/webpage/com/kingtake/apprUtil/apprUtil.js		
}*/

//发送审核或审核通过，不可删
function isShowDel(value,rec,index){
	return (rec.aduitStatus== "1" || rec.aduitStatus=="2") ? false : true;
}

//审核通过，不可再改分配
function isShowfpje(value,rec,index){
	return (rec.aduitStatus=="2") ? false : true;
}

//未发送审核，审核未通过 可再发送审核
function isShowSendAduit(value,rec,index){
	return (rec.aduitStatus=="0" || rec.aduitStatus=="3") ? true : false;
}

//
function aduitStatusTotext(value,rec,index){
	var arr = ["待发送审核","待审核","已审核","审核不通过"];
	return arr[value];
}

//打印
function exportJh(id) {
    $.ajax({
        url : "budgetExportController.do?createWordJhjfptzs",
        type : "post",
        data : {"jhffId":id},
        success : function(result) {
            var obj = eval('(' + result + ')');
            var fileName = obj.attributes.FileName;
            window.location.href="http://"+window.location.host+"/tPmIncomeApplyController.do?downloadWord&FileName="+fileName;
        }
    });
}


</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmProjectPlanList" onDblClick="goDetail" fitColumns="true" title="计划下达列表" actionUrl="tPmProjectPlanController.do?datagrid" idField="id" fit="true"
      queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="文件号" field="documentNo" queryMode="single" query="true" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="文件名" field="documentName" queryMode="single" width="160" query="true" isLike="true"></t:dgCol>
      <t:dgCol title="年度" field="planYear" queryMode="single" width="90" query="true" isLike="true"></t:dgCol>
      <t:dgCol title="发文时间" field="documentTime" formatter="yyyy-MM-dd" queryMode="group" width="90"></t:dgCol>
      <t:dgCol title="计划下达总金额" field="amount" hidden="true" queryMode="single" width="120"></t:dgCol>      
      <t:dgCol title="来源经费科目" field="fundsSubject" hidden="true" queryMode="single" width="120"></t:dgCol>      
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol> 
      <t:dgCol title="审核状态" field="aduitStatus" hidden="false" queryMode="group" width="120" formatter1="aduitStatusTotext"></t:dgCol> 
      
      <t:dgCol title="操作" field="opt" width="300" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="分配计划下达金额" funname="fpje(id)"  checkFun="isShowfpje"/> 
      <t:dgDelOpt title="删除" url="tPmProjectPlanController.do?doDel&id={id}" checkFun="isShowDel" />
      <t:dgFunOpt title="发送审核" funname="sendAduit(id)" checkFun="isShowSendAduit"/>
      <t:dgFunOpt title="打印" funname="exportJh(id)" checkFun=""/>
      
      <!-- checkFun="" -->
      
      <%-- <t:dgFunOpt title="生成计划经费分配通知书" funname="exportJhWord(id)" />    --%>    
   	  <t:dgToolBar title="录入" icon="icon-add" url="tPmProjectPlanController.do?goEdit" funname="add" width="900" height="100"></t:dgToolBar>
   	  <t:dgToolBar title="编辑" icon="icon-edit" url="tPmProjectPlanController.do?goEdit" funname="update" width="900" height="100"></t:dgToolBar>
   	  <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectPlanController.do?goEdit" funname="detail" width="900" height="100"></t:dgToolBar>
	  <%-- <t:dgToolBar title="生成计划经费分配通知书" icon="icon-putout" funname="exportJhWord()"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
    });
    
    //查看
    function goDetail(rowIndex,rowData) {
        var id = rowData.id;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'tPmProjectPlanList',
                content : 'url:tPmProjectPlanController.do?goEdit&load=detail&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                id : 'tPmProjectPlanList',
                content : 'url:tPmProjectPlanController.do?goEdit&load=detail&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
     }   	           
  
    //分配金额
    function fpje(id) {
        var dialog;
        if (typeof (windowapi) == 'undefined') {
            dialog = $.dialog({
                id : 'projectPlan',
                content : 'url:tPmIncomePlanController.do?incomePlanList&stage=xd&id=' + id,
                lock : true,
                width : 1200,
                height : window.top.document.body.offsetHeight-100,
                title : "项目信息",
                opacity : 0.3,
                zIndex:10,
                cache : false,
//                 ok : function() {
//                     iframe = this.iframe.contentWindow;
//                     saveObj();
//                     return false;
//                 },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            dialog =  W.$.dialog({
                id : 'projectPlan',
                content : 'url:tPmIncomePlanController.do?incomePlanList&stage=xd&id=' + id,
                lock : true,
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                title : "修改",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
//                 ok : function() {
//                     iframe = this.iframe.contentWindow;
//                     saveObj();
//                     return false;
//                 },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
    }
    
    //导出计划经费分配通知书WORD
    function exportJhWord(id) {    	
    	if(id == ""){
    		tip('请选择数据');
    	}else{
    		$.ajax({
                url : 'tPmProjectPlanController.do?createWord&id=' + id,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                	downloadWord(data.attributes.FileName);
                }
            });
    	}    	
    }
    
    //下载计划经费分配通知书WORD
    function downloadWord(FileName) {
    	JeecgExcelExport("tPmProjectPlanController.do?downloadWord&FileName=" + FileName,"tPmProjectPlanList");
    }
</script>