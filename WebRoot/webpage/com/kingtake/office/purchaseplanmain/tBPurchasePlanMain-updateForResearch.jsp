<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>科研采购计划</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
  
  function setPlanDate() {
    $('#planDate').val($('#planDateText').val() + "-01");
    var rows = $("#tBPurchasePlanDetailList").datagrid("getRows");
    var nodeListStr = JSON.stringify(rows);
    $("#planDetailStr").val(nodeListStr);
  }
  
  function saveCallback(data){
	  var win;
		var dialog = W.$.dialog.list['processDialog'];
	    if (dialog == undefined) {
	    	win = frameElement.api.opener;
	    } else {
	    	win = dialog.content;
	    }
    win.tip(data.msg);
    if(data.success){
       win.reloadDetails();
       frameElement.api.close();
    }
  }
  
</script>
</head>
<body>
<div class="easyui-layout" fit="true" id="planLayout">
  <div region="north" style="padding: 1px;height: 230px;">
  <t:formvalid formid="formobj" dialog="true" layout="table" tiptype="1" action="tBPurchasePlanMainController.do?doUpdateForResearch" beforeSubmit="setPlanDate();" callback="@Override saveCallback">
    <input id="id" name="id" type="hidden" value="${tBPurchasePlanMainPage.id }">
    <input id="dutyDepartId" name="dutyDepartId" type="hidden" value="${tBPurchasePlanMainPage.dutyDepartId }">
    <input id="managerId" name="managerId" type="hidden" value="${tBPurchasePlanMainPage.managerId }">
    <input id="planDate" name="planDate" type="hidden" value="${tBPurchasePlanMainPage.planDate }">
    <textarea id="planDetailStr" name="planDetailStr" rows="1" cols="1" style="display: none;"></textarea>
    <table cellpadding="0" cellspacing="1" class="formtable" >
      <tr>
        <td align="right" style="width: 40%;">
          <label class="Validform_label"> 计划时间: </label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="planDateText" name="planDateText" datatype="*" type="text" style="width: 350px" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" value='<fmt:formatDate value='${tBPurchasePlanMainPage.planDate}' type="date" pattern="yyyy-MM"/>'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">计划时间</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">责任单位:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="dutyDepartName" name="dutyDepartName" type="text"  datatype="*" value='${tBPurchasePlanMainPage.dutyDepartName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">责任单位</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">负责人:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="managerName" name="managerName" type="text"  datatype="*1-36" style="width: 350px" class="inputxt" readonly="readonly" value='${tBPurchasePlanMainPage.managerName}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">负责人</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">项目编码:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="projectCode" name="projectCode" type="text"  style="width: 350px" datatype="s2-20" class="inputxt" value='${tBPurchasePlanMainPage.projectCode}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目编码</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">项目名称:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="projectName" name="projectName" type="text"  style="width: 350px" datatype="*1-200" class="inputxt" value='${tBPurchasePlanMainPage.projectName}'>
          <input id="projectId" name="projectId" type="hidden"  value='${tBPurchasePlanMainPage.projectId}'>
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">项目名称</label>
        </td>
      </tr>
      <tr>
        <td align="right">
          <label class="Validform_label">总经费:</label>
          <font color="red">*</font>
        </td>
        <td class="value">
          <input id="totalFunds" name="totalFunds" type="text"  value='${tBPurchasePlanMainPage.totalFunds}' class="easyui-numberbox"
            data-options="min:1,max:100000000000,precision:2,groupSeparator:','" style="width: 350px; text-align: right;" datatype="*">
          <span class="Validform_checktip"></span>
          <label class="Validform_label" style="display: none;">总经费</label>
        </td>
      </tr>
    </table>
  </t:formvalid>
  </div>
  <div region="center">
  <c:if test="${empty tBPurchasePlanMainPage.id}">
  <t:datagrid name="tBPurchasePlanDetailList" checkbox="true" onDblClick="dbClickRow" extendParams="singleSelect:true," fitColumns="true" title="采购计划明细" actionUrl="tBPurchasePlanMainController.do?datagridForDetail&purchasePlanId=111" idField="id" fit="true" queryMode="group" >
  <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="主表记录id" field="purchasePlanId" hidden="true"  queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="采购计划名称" field="planName"   queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="采购概算" field="purchaseEstimates"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购方式" field="purchaseMode" codeDict="1,CGFS" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购来源" field="purchaseSource"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购理由" field="purchaseReason"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol field="opt" title="操作" frozenColumn="true"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add"  funname="goAdd" width="780"></t:dgToolBar>
      <t:dgFunOpt title="编辑"  funname="goUpdate(id)"></t:dgFunOpt>
      <t:dgToolBar title="查看" icon="icon-search"  funname="goDetail" width="780"></t:dgToolBar>
      <t:dgToolBar title="批量删除" icon="icon-remove"  funname="deleteDetail"></t:dgToolBar>
    </t:datagrid>
  </c:if>
  <c:if test="${!empty tBPurchasePlanMainPage.id}">
  <t:datagrid name="tBPurchasePlanDetailList" checkbox="true" onDblClick="dbClickRow" extendParams="singleSelect:true," fitColumns="true" title="采购计划明细" actionUrl="tBPurchasePlanMainController.do?datagridForDetail&purchasePlanId=${tBPurchasePlanMainPage.id}" idField="id" fit="true" queryMode="group">
  <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="主表记录id" field="purchasePlanId" hidden="true"  queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="采购计划名称" field="planName"   queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="编号" field="mergeCode"   queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="采购概算" field="purchaseEstimates"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购方式" field="purchaseMode" codeDict="1,CGFS" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="采购来源" field="purchaseSource"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="采购理由" field="purchaseReason"  queryMode="group" overflowView="true" width="120"></t:dgCol>
      <c:if test="${load ne 'detail' }">
      <t:dgCol field="opt" title="操作" frozenColumn="true"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add"  funname="goAdd" width="780"></t:dgToolBar>
      <t:dgFunOpt title="编辑"  funname="goUpdate(id)"></t:dgFunOpt>
      <t:dgToolBar title="查看" icon="icon-search"  funname="goDetail" width="780"></t:dgToolBar>
      <t:dgToolBar title="批量删除" icon="icon-remove"  funname="deleteDetail"></t:dgToolBar>
      </c:if>
    </t:datagrid>
  </c:if>
      
    </div>
    </div>
    </div>
    <script type="text/javascript">
    //编辑
    function goUpdate(id){
  	  var title = "编辑采购计划明细";
  	  var url = "tBPurchasePlanMainController.do?goUpdateDetailForResearch&id="+id;
  	  var width = width?width:700;
  	  var height = height?height:400;
  		if(width=="100%"){
  			width = window.top.document.body.offsetWidth;
  		}
  		if(height=="100%"){
  			height =window.top.document.body.offsetHeight-100;
  		}
  			W.$.dialog({
  				id : 'cgjhDetailDialog',
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
    
    //双击
    function dbClickRow(rowIndex, rowData){
      var url = "tBPurchasePlanMainController.do?goUpdateDetailForResearch";
      url += '&load=detail&id='+rowData.id;
  	  var title = "查看采购计划明细";
  	  var width = width?width:700;
	  var height = height?height:400;
		if(width=="100%"){
			width = window.top.document.body.offsetWidth;
		}
		if(height=="100%"){
			height =window.top.document.body.offsetHeight-100;
		}
  			W.$.dialog({
  				id : 'cgjhDetailDialog',
  				content: 'url:'+url,
  				lock : true,
  				width:width,
  				height:height,
  				parent:windowapi,
  				title:title,
  				opacity : 0.3,
  				cache:false,
  			    cancelVal: '关闭',
  			    cancel: true 
  			}).zindex();
    }
    
    //录入
    function goAdd(){
  	  var title = "新增采购计划明细";
  	  var url = "tBPurchasePlanMainController.do?goAddDetailForResearch&purchasePlanId=${tBPurchasePlanMainPage.id}";
  	  var width = width?width:700;
  	  var height = height?height:400;
  		if(width=="100%"){
  			width = window.top.document.body.offsetWidth;
  		}
  		if(height=="100%"){
  			height =window.top.document.body.offsetHeight-100;
  		}
  			W.$.dialog({
  				id : 'cgjhDetailDialog',
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
    
    function loadDetail(formData){
    	$("#tBPurchasePlanDetailList").datagrid("appendRow",{
  			id:formData.id,
  			purchasePlanId:formData.purchasePlanId,
  			planName:formData.planName,
  			purchaseEstimates:formData.purchaseEstimates,
  			purchaseMode:formData.purchaseMode,
  			purchaseSource:formData.purchaseSource,
  			purchaseReason:formData.purchaseReason
  		});
    }
    
    function goDetail(){
    	var rowsData = $('#tBPurchasePlanDetailList').datagrid('getSelections');
    	if (!rowsData || rowsData.length == 0) {
    		tip('请选择查看的项');
    		return;
    	}
    	if (rowsData.length > 1) {
    		tip('请选择一条记录再查看');
    		return;
    	}
    	if(rowsData[0].id==""){
    		tip('请先保存再查看');
    		return;
    	}
    	var url = "tBPurchasePlanMainController.do?goUpdateDetailForResearch";
        url += '&load=detail&id='+rowsData[0].id;
    	  var title = "查看采购计划明细";
    	  var width = width?width:700;
      	  var height = height?height:400;
      		if(width=="100%"){
      			width = window.top.document.body.offsetWidth;
      		}
      		if(height=="100%"){
      			height =window.top.document.body.offsetHeight-100;
      		}
    			W.$.dialog({
    				id : 'cgjhDetailDialog',
    				content: 'url:'+url,
    				lock : true,
    				width:width,
    				height:height,
    				parent:windowapi,
    				title:title,
    				opacity : 0.3,
    				cache:false,
    			    cancelVal: '关闭',
    			    cancel: true 
    			}).zindex();
      }
    
    //删除明细
    function deleteDetail(){
    	var checked = $("#tBPurchasePlanDetailList").datagrid("getChecked");
    	if(checked.length==0){
    		tip("请先选中需要删除的明细");
    		return false;
    	}
    	for(var i = 0;i<checked.length;i++){
    	  var index = $("#tBPurchasePlanDetailList").datagrid("getRowIndex",checked[i]);
    	  $("#tBPurchasePlanDetailList").datagrid("deleteRow",index);
    	}
    }
    </script>
</body>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMain.js"></script>