<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOWorkPointList" checkbox="true" fitColumns="true" title="工作要点" actionUrl="tOWorkPointController.do?datagrid" idField="id" fit="true" onDblClick="dbClickRow" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="时间" field="time" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="工作内容" field="workContent" queryMode="group" width="250"></t:dgCol>
      <t:dgCol title="接收人id" field="receiverId" hidden="true" ></t:dgCol>
      <t:dgCol title="接收人" field="receiverName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="单位" field="deptName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="提交状态" field="submitFlag" replace="未提交_0,已提交_1,已接收_3,已驳回_2" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add" url="tOWorkPointController.do?goUpdate" funname="add"></t:dgToolBar>
      <t:dgFunOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
      <t:dgDelOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="删除" url="tOWorkPointController.do?doDel&id={id}" />
      <t:dgFunOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="发送" funname="doWorkPointSubmit(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&receiverId#eq\#${uid}" title="接收" funname="doRecieve(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&receiverId#eq\#${uid}" title="退回" funname="doReturn(id)" ></t:dgFunOpt>
      <t:dgToolBar title="查看" icon="icon-search" url="tOWorkPointController.do?goUpdate" funname="detail"></t:dgToolBar>
      <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="发送提醒" icon="icon-message"  funname="sendPlanMsg"></t:dgToolBar>
      <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/workpoint/tOWorkPointList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tOWorkPointListtb").find("input[name='time_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='time_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOWorkPointListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
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
      var url = "tOWorkPointController.do?goUpdate&id="+id;
      createwindow("编辑",url,700,350);
  }
  
  function sendPlanMsg(){
      sendMsg("工作要点");
  }
</script>
<script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>