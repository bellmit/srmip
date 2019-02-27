<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input id="tipMsg" type="hidden" value=""/>
<div class="easyui-layout" fit="true" id="planLayout">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPurchasePlanMainList" checkbox="true" onDblClick="dbClickRow" extendParams="singleSelect:true," fitColumns="true" title="采购计划" actionUrl="tBPurchasePlanMainController.do?datagrid&projectId=${projectId}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划时间" field="planDate" formatter="yyyy-MM" hidden="false" query="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="责任单位id" field="dutyDepartId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="责任单位" field="dutyDepartName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="负责人id" field="managerId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="负责人" field="managerName" queryMode="single" width="80" query="true" isLike="true"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" queryMode="single" isLike="true" query="true" width="200"></t:dgCol>
      <t:dgCol title="项目编码" field="projectCode" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="总经费" field="totalFunds" query="true" queryMode="group" width="120" extendParams="formatter:formatCurrency," align="right"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审核人" field="finishUserName"  width="80"></t:dgCol>
      <t:dgCol title="审核时间" field="finishTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
      <t:dgCol title="审核状态" field="finishFlag"  codeDict="1,SPZT" width="80"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
      <t:dgDelOpt exp="submitFlag#eq#0" title="删除" url="tBPurchasePlanMainController.do?doDel&id={id}" />
      <%-- <t:dgFunOpt funname="viewDetail(id)" title="明细"></t:dgFunOpt> --%>
      <t:dgToolBar title="录入" icon="icon-add"  funname="goAdd" width="100%" height="100%"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPurchasePlanMainController.do?goUpdateForResearch" funname="detail" width="100%" height="100%" ></t:dgToolBar>
      <t:dgFunOpt  title="发送审核" funname="sendPlanAppr(id, finishFlag)" ></t:dgFunOpt>
      <t:dgFunOpt exp="finishFlag#eq#0" funname="goUpdate(id)" title="编辑"></t:dgFunOpt>
      <t:dgFunOpt exp="finishFlag#eq#3" funname="goUpdate(id)" title="编辑"></t:dgFunOpt>
      <t:dgDelOpt exp="finishFlag#eq#0" title="删除" url="tBPurchasePlanMainController.do?doDel&id={id}" />
    </t:datagrid>
  </div>
  </div>
</div>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMainList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script> 
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    //给时间控件加上样式
    $("#tBPurchasePlanMainListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });

    $("#tBPurchasePlanMainListtb").find("input[name='planDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:120px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tBPurchasePlanMainListtb").find("input[name='planDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:120px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
  });
  
  function dbClickRow(rowIndex, rowData) {
    gridname = 'tBPurchasePlanMainList';
    var url = 'tBPurchasePlanMainController.do?goUpdateForResearch';
    url += '&load=detail&id='+rowData.id;
    var title = '查看采购计划';
    var width = '100%';
    var height = '100%';
    createdetailwindow(title,url,width,height);
}
  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tBPurchasePlanMainController.do?upload', "tBPurchasePlanMainList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tBPurchasePlanMainController.do?exportXls", "tBPurchasePlanMainList");
  }

  //模板下载
  function ExportXlsByT() {
    JeecgExcelExport("tBPurchasePlanMainController.do?exportXlsByT", "tBPurchasePlanMainList");
  }
  
    //发送进账合同审核
	function sendPlanAppr(planApprId, finishFlag){
		var title = "审核";
		var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
				'&apprId=' + planApprId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_CGJH%>';
		var width = 900;
		var height = window.top.document.body.offsetHeight-100;
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("采购计划审核流程已完成，不能再发送审核");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
			url += '&tip=true';
			$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑审核信息！");
		}
		sendApprDialog(title, url, width, height, planApprId,finishFlag,'<%=ApprovalConstant.APPR_TYPE_CGJH%>' );
	}
    
    function goEdit(id){
        var url = "tBPurchasePlanMainController.do?goUpdate&id="+id;
        var title = "编辑";
        var width = 700;
        var height = 400;
        createwindow(title,url,width,height);
    }
    
  //查看审核意见
    function viewSuggestion(id,index){
        var url = "tBPurchasePlanMainController.do?goPropose&id="+id;
        createdetailwindow("查看修改意见",url,450,120);
    }
  
  function reloadDetails(){
      $("#tBPurchasePlanMainList").datagrid("reload");
  }
  
//录入
  function goAdd(){
	  var title = "录入采购计划明细";
	  var url = "tBPurchasePlanMainController.do?goUpdateForResearch&projectId=${projectId}";
			var width = window.top.document.body.offsetWidth;
			var height =window.top.document.body.offsetHeight-100;
			W.$.dialog({
				id : 'cgjhDialog',
				content: 'url:'+url,
				lock : true,
				width:width,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true 
			}).zindex();
  }
  
  //编辑
  function goUpdate(id){
	  var title = "编辑采购计划明细";
	  var url = "tBPurchasePlanMainController.do?goUpdateForResearch&id="+id;
			var width = window.top.document.body.offsetWidth;
			var height =window.top.document.body.offsetHeight-100;
			W.$.dialog({
				id : 'cgjhDialog',
				content: 'url:'+url,
				lock : true,
				width:width,
				height:height,
				parent:windowapi,
				title:title,
				opacity : 0.3,
				cache:false,
			    ok: function(){
			    	iframe = this.iframe.contentWindow;
					saveObj();
					return false;
			    },
			    cancelVal: '关闭',
			    cancel: true 
			}).zindex();
  }
</script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>