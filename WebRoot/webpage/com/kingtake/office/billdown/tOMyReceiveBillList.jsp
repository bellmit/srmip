<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input id="userId" type="hidden" value="${userId}">
<div id="sendDownLayout" class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOSendDownBillList" checkbox="true" fitColumns="false" title="发文下达" actionUrl="tOSendDownBillController.do?datagrid" idField="id" fit="true" onDblClick="dbClickRow"
      queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="下达表主键" field="downId" hidden="true"></t:dgCol>
      <t:dgCol title="发送人id" field="senderId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="发送人" field="senderName" queryMode="group" width="90"></t:dgCol>
      <t:dgCol title="发送时间" field="sendTime" formatter="yyyy-MM-dd" queryMode="group" width="90"></t:dgCol>
      <t:dgCol title="标题" field="title" query="true" sortable="false" queryMode="single" width="280"></t:dgCol>
      <t:dgCol title="公文编号" field="mergeFileNum" sortable="false" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="密级" field="securityGrade" codeDict="0,XMMJ" sortable="false" queryMode="group" width="60" align="center"></t:dgCol>
      <t:dgCol title="公文种类" field="regType" codeDict="1,GWZL" sortable="false" queryMode="single" query="true" width="80" align="center"></t:dgCol>
      <t:dgCol title="发文日期" field="registerDate" formatter="yyyy-MM-dd" sortable="false" query="true" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="公文状态" field="generationFlag" queryMode="group" codeDict="1,YPDZT" width="90" align="center"></t:dgCol>
      <t:dgCol title="收发文标志" hidden="true" sortable="false" field="registerType" queryMode="group" codeDict="1,SRFLAG" width="80"></t:dgCol>
      <t:dgCol title="下达标志" field="sendStatus" queryMode="group" extendParams="formatter:downFlagFormatter," width="80"></t:dgCol>
      <t:dgCol title="状态" field="status" replace="未接收_0,已接收_1" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="250" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="status#eq#1" title="发送" funname="doSendDownSubmit(id,downId)"></t:dgFunOpt>
      <t:dgFunOpt exp="status#eq#0" title="接收" funname="doRecieve(downId)"></t:dgFunOpt>
      <t:dgFunOpt title="公文" funname="doViewBill(id,downId)"></t:dgFunOpt>
      <t:dgFunOpt exp="sendStatus#eq#1" title="查看接收情况" funname="queryTypeValue(id)"></t:dgFunOpt>
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
<script src="webpage/com/kingtake/office/billdown/tOSendDownBillList.js?${tm}"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tOSendDownBillListtb").find("input[name='registerDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOSendDownBillListtb").find("input[name='registerDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOWorkPointController.do?upload', "tOWorkPointList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tOWorkPointController.do?exportXls", "tOWorkPointList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOWorkPointController.do?exportXlsByT", "tOWorkPointList");
    }
    
 
  //数据编辑
  function goUpdate(id){
      var url = "tOBorrowBillController.do?goUpdate&id="+id;
      createwindow("编辑",url,"100%","100%");
  }
  
</script>
