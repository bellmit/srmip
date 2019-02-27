<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPurchasePlanMainList" checkbox="false" fitColumns="true" onDblClick="dbClickRow" title="科研采购计划" actionUrl="tBPurchasePlanMainController.do?datagridForAudit&type=${type}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划时间" field="planDate" formatter="yyyy-MM" query="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="责任单位id" field="dutyDepartId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="责任单位" field="dutyDepartName" query="true" isLike="true"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="负责人id" field="managerId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="负责人" field="managerName" query="true" queryMode="single" isLike="true"  width="120"></t:dgCol>
      <t:dgCol title="项目名称" field="projectName" query="true" isLike="true"  queryMode="single" width="200"></t:dgCol>
      <t:dgCol title="项目编码" field="projectCode" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="总经费" field="totalFunds" query="true"  queryMode="group" width="120" extendParams="formatter:formatCurrency," align="right"></t:dgCol>
      <t:dgCol title="审核人" field="checkUserName"  width="80"></t:dgCol>
      <t:dgCol title="审核时间" field="checkTime"  formatter="yyyy-MM-dd hh:mm:ss" width="80"></t:dgCol>
      <t:dgCol title="审核状态" field="submitFlag" replace="未提交_0,已提交_1,已通过_2,不通过_3,重新提交_4" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPurchasePlanMainController.do?goUpdate" funname="detail" width="780"></t:dgToolBar>
      <t:dgCol field="opt" title="操作"></t:dgCol>
      <c:if test="${type eq '1' }">
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      <t:dgFunOpt exp="submitFlag#eq#1" funname="passAudit(id)" title="审核通过"></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq#4" funname="passAudit(id)" title="审核通过"></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq#1" funname="rejectAudit(id)" title="审核不通过"></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq#4" funname="rejectAudit(id)" title="审核不通过"></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq#4" funname="viewSuggestion(id)" title="查看修改意见"></t:dgFunOpt>
      </c:if>
      <c:if test="${type eq '2' }">
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      </c:if>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/purchaseplanmain/tBPurchasePlanMainList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script> 
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
  
//审核不通过
  function rejectAudit(id) {
      if (typeof (windowapi) == 'undefined') {
          $.dialog.confirm("确定审核不通过吗？", function() {
              openMsgDialog(id);
          }, function() {
          }).zindex();
      } else {
          W.$.dialog.confirm("确定审核不通过吗？", function() {
              openMsgDialog(id);
          }, function() {
          }, windowapi).zindex();
      }
  }

  //打开弹窗填写修改意见
  function openMsgDialog(id) {
      var url = "tPmCheckReportController.do?goPropose&id="+id;
      var title = "填写修改意见";
      $.dialog({
          content : 'url:' + url,
          title : '提出修改意见',
          lock : true,
          opacity : 0.3,
          width : '450px',
          height : '120px',
          ok : function() {
              iframe = this.iframe.contentWindow;
              var msgText = iframe.getMsgText();
              var proposeIframe = iframe;
              $.ajax({
                  url : 'tBPurchasePlanMainController.do?doPropose',
                  data : {
                      id : id,
                      msgText : msgText
                  },
                  type : 'post',
                  dataType : 'json',
                  success : function(data) {
                      tip(data.msg);
                      reloadTable();
                  }
              });
          },
          cancel : function() {
              reloadTable();
          },
      }).zindex();
  }

  //审核
  function passAudit(id) {
      if (typeof (windowapi) == 'undefined') {
          $.dialog.confirm("确定审核通过吗？", function() {
              doPass(id);
          }, function() {
          }).zindex();
      } else {
          W.$.dialog.confirm("确定审核通过吗？", function() {
              doPass(id);
          }, function() {
          }, windowapi).zindex();
      }
      
  }
  
  function doPass(id){
      $.ajax({
          url : 'tBPurchasePlanMainController.do?doPass',
          type : 'GET',
          dataType : 'json',
          cache : 'false',
          data : {
              'id' : id
          },
          success : function(data) {
              tip(data.msg);
              if (data.success) {
                  reloadTable();
              }
          }
      });
  }
  
//查看审核意见
  function viewSuggestion(id,index){
      var url = "tBPurchasePlanMainController.do?goPropose&id="+id;
      createdetailwindow("查看修改意见",url,450,120);
  }
  
  function dbClickRow(rowIndex, rowData) {
      gridname = 'tBPurchasePlanMainList';
      var url = 'tBPurchasePlanMainController.do?goUpdate';
      url += '&load=detail&id='+rowData.id;
      var title = '查看采购计划';
      var width = '780px';
      var height = '400px';
      createdetailwindow(title,url,width,height);
  }
  
//导出
  function ExportXls() {
    JeecgExcelExport("tBPurchasePlanMainController.do?exportXls&type=${type}", "tBPurchasePlanMainList");
  }
  
</script>