<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true" id="planLayout">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBPurchasePlanMainList" checkbox="true" onDblClick="dbClickRow" extendParams="singleSelect:true," fitColumns="true" title="科研采购计划" actionUrl="tBPurchasePlanMainController.do?datagrid" idField="id" fit="true" queryMode="group">
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
      <t:dgCol title="审核人" field="checkUserName"  width="80"></t:dgCol>
      <t:dgCol title="审核时间" field="checkTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
      <t:dgCol title="审核状态" field="finishFlag" replace="未提交_0,已提交_1,已通过_2,不通过_3,重新提交_4" width="80"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
      <t:dgDelOpt exp="finishFlag#eq#0" title="删除" url="tBPurchasePlanMainController.do?doDel&id={id}" />
      <t:dgFunOpt funname="viewDetail(id)" title="明细"></t:dgFunOpt>
      <t:dgToolBar title="录入" icon="icon-add" url="tBPurchasePlanMainController.do?goAdd" funname="add" width="700" height="400"></t:dgToolBar>
      <%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tBPurchasePlanMainController.do?goUpdate" funname="update" width="780"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="tBPurchasePlanMainController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBPurchasePlanMainController.do?goUpdate" funname="detail" width="700" height="400" ></t:dgToolBar>
      <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
     <%--  <t:dgFunOpt exp="finishFlag#eq#0" funname="send(id)" title="提交"></t:dgFunOpt>
      <t:dgFunOpt exp="finishFlag#eq#3" funname="send(id)" title="提交"></t:dgFunOpt> --%>
      <t:dgFunOpt exp="finishFlag#eq#0" funname="goEdit(id)" title="编辑"></t:dgFunOpt>
      <t:dgFunOpt exp="finishFlag#eq#3" funname="goEdit(id)" title="编辑"></t:dgFunOpt>
      <t:dgFunOpt exp="finishFlag#eq#3" funname="viewSuggestion(id)" title="查看修改意见"></t:dgFunOpt>
      <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
  </div>
  <div data-options="region:'east',
  title:'mytitle',
  collapsed:true,
  split:true,
  border:false,
  onExpand : function(){
    li_east = 1;
  },
  onCollapse : function() {
      li_east = 0;
  }"
     style="width: 380px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
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
  
  function dbClickRow(rowIndex, rowData) {
    gridname = 'tBPurchasePlanMainList';
    var url = 'tBPurchasePlanMainController.do?goUpdate';
    url += '&load=detail&id='+rowData.id;
    var title = '查看采购计划';
    var width = '780px';
    var height = '400px';
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
  
    //提交
    function send(id) {
        $.dialog.confirm("您确定提交吗？", function() {
            submit(id);
        }, function() {
        }).zindex();
    }

    function submit(id) {
        var url = "tBPurchasePlanMainController.do?doSubmit";
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            data : {
                'id' : id
            },
            url : url,// 请求的action路径
            dataType : 'json',
            error : function() {// 请求失败处理函数
            },
            success : function(data) {
                var msg = data.msg;
                tip(msg);
                if (data.success) {
                    $("#tBPurchasePlanMainList").datagrid('reload');
                }
            }
        });
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
      $("#tBPurchasePlanDetailList").datagrid("reload");
  }
</script>