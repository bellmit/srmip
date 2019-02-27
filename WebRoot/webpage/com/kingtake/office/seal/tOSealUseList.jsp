<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input id="apprType" type="hidden" value="<%=ApprovalConstant.APPR_TYPE_SEAL_USE%>">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOSealUseList" checkbox="true" fitColumns="true" title="印章使用" actionUrl="tOSealUseController.do?datagrid" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="编号字" field="numberWord" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="编号号" field="numberSymbol" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="编号" field="number"  queryMode="group" width="120" extendParams="formatter:numberFormatter,"></t:dgCol>
      <t:dgCol title="流水号" field="searialNum"  queryMode="group" width="120" ></t:dgCol>
      <t:dgCol title="材料名称" field="materialName" queryMode="group" width="200"></t:dgCol>
      <t:dgCol title="页数" field="pagesNum" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="份数" field="copiesNum"  queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="密级" field="secretDegree" queryMode="group" width="60" codeDict="0,XMMJ"></t:dgCol>
      <t:dgCol title="申请人" field="createName" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="经办人" field="operatorName" queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="申请时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="印章类型" field="sealType" queryMode="group" width="80" codeDict="1,YZLX"></t:dgCol>
      <t:dgCol title="审核状态" field="auditStatus" queryMode="group" width="80" codeDict="1,SPZT"></t:dgCol>
      <t:dgCol title="经办人单位id" field="operatorDepartid" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="经办人id" field="operatorId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="主要内容" field="mainContent" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="依据" field="accordings" queryMode="group" width="200"></t:dgCol>
      <t:dgCol title="申请时间" field="applyDate" formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="120" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="auditStatus#eq#2" funname="sendAudit(id,auditStatus)" title="查看审核"></t:dgFunOpt>
      <t:dgFunOpt exp="auditStatus#ne#2" funname="sendAudit(id,auditStatus)" title="发送审核"></t:dgFunOpt>
      <t:dgDelOpt exp="auditStatus#eq#0" title="删除" url="tOSealUseController.do?doDel&id={id}" />
      <t:dgToolBar title="录入" icon="icon-add" url="tOSealUseController.do?goEdit" funname="add" width="750" height="455"></t:dgToolBar>
      <t:dgFunOpt exp="auditStatus#eq#0" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="auditStatus#eq#3" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
      <%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="tOSealUseController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
      <t:dgToolBar title="查看" icon="icon-search" url="tOSealUseController.do?goEdit" funname="detail" width="750" height="455"></t:dgToolBar>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/seal/tOSealUseList.js"></script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    //给时间控件加上样式
    $("#tOSealUseListtb").find("input[name='applyDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tOSealUseListtb").find("input[name='applyDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tOSealUseListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tOSealUseListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tOSealUseListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    $("#tOSealUseListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
  });

  //导入
  function ImportXls() {
    openuploadwin('Excel导入', 'tOSealUseController.do?upload', "tOSealUseList");
  }

  //导出
  function ExportXls() {
    JeecgExcelExport("tOSealUseController.do?exportXls", "tOSealUseList");
  }

  //模板下载
  function ExportXlsByT() {
    JeecgExcelExport("tOSealUseController.do?exportXlsByT", "tOSealUseList");
  }
  
  function sendAudit(id,auditStatus){
    if (auditStatus == '0' || auditStatus == '3') {
      $.dialog({
        id : 'apprInfo',
        lock : true,
        background : '#000', /* 背景色 */
        opacity : 0.5, /* 透明度 */
        width : 950,
        height : window.top.document.body.offsetHeight-100,
        title : '印章使用审核',
        parent : windowapi,
        content : 'url:tOSealUseController.do?goAudit&id=' + id,
        ok : function() {
        	openSendAudit1(id,auditStatus);
        	return false;
        },
        okVal : "发送审核",
        cancelVal : '关闭',
        cancel : function(){
          reloadTable();
        }
      });
    } else if (auditStatus == '1'||auditStatus == '2') {
      $.dialog({
        id : 'apprInfo',
        lock : true,
        background : '#000', /* 背景色 */
        opacity : 0.5, /* 透明度 */
        width : 950,
        height : window.top.document.body.offsetHeight-100,
        title : '印章使用审核',
        parent : windowapi,
        content : 'url:tOSealUseController.do?goAudit&id=' + id,
        /* ok : function() {
            updateFinishApplyFinishFlag(id);
        },
        okVal : "取消完成", */
        cancelVal : '关闭',
        cancel : function(){
          reloadTable();
        }
      });
    }
  }
  //更新审核状态
  function updateFinishApplyFinishFlag(apprId) {
    var url1 = 'tOSealUseController.do?updateFinishFlag';
    var url2 = url1 + '2';
    updateFinishFlag(apprId, url1, url2);
  }
  
  //弹出发送
  function openSendAudit1(id,auditStatus){
	  var apprType = $("#apprType").val();
	  var url = "tPmApprLogsController.do?goApprSend&apprId="+id+"&apprType="+apprType;
	  $.dialog({
	        id : 'apprSend',
	        lock : true,
	        background : '#000', /* 背景色 */
	        opacity : 0.5, /* 透明度 */
	        width : 520,
	        height : 260,
	        title : '发送审核',
	        parent : windowapi,
	        content : 'url:' + url,
	        ok : function() {
	        	iframe = this.iframe.contentWindow;
				saveObj();
				return false;
	        },
	        cancelVal : '关闭',
	        cancel : function(){
	          reloadTable();
	        }
	      });
  }
  
  //编辑
  function goUpdate(id){
	  var url = 'tOSealUseController.do?goEdit';
	  url += '&id='+id;
	  createwindow("编辑",url,750,"100%");
  }
</script>