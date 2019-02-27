<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOHolidayPlanList" checkbox="true" fitColumns="true" title="假前工作计划" actionUrl="tOHolidayPlanController.do?datagrid" idField="id" fit="true" queryMode="group" onDblClick="dbClickRow">
      <t:dgCol title="id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="假期" field="holidayCode" codeDict="1,JQ" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="单位id" field="deptId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="单位名称" field="deptName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划内容" field="planContent" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="接收人id" field="receiverId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="接收人" field="receiverName" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="提交状态" field="submitFlag" replace="未提交_0,已提交_1,已接收_3,已驳回_2" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="修改意见" field="msgText" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="createBy" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="createName" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="createDate" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="updateBy" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="updateName" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="updateDate" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
      <t:dgFunOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
      <t:dgDelOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&submitFlag#ne\#${submitReturn}&&createBy#eq\#${uname}" title="删除" url="tOHolidayPlanController.do?doDel&id={id}" />
      <t:dgFunOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="提交" funname="doHolidayPlanSubmit(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&receiverId#eq\#${uid}" title="接收" funname="doRecieve(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&receiverId#eq\#${uid}" title="退回" funname="doReturn(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitReturn}" funname="viewMsgText(id)" title="查看修改意见"></t:dgFunOpt>
      <t:dgToolBar title="录入" icon="icon-add" url="tOHolidayPlanController.do?goUpdate" funname="add"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
      <t:dgToolBar title="发送提醒" icon="icon-message"  funname="sendPlanMsg"></t:dgToolBar>
      <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/holiday/tOHolidayPlanList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tOHolidayPlanListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOHolidayPlanListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOHolidayPlanListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOHolidayPlanListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOHolidayPlanController.do?upload', "tOHolidayPlanList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tOHolidayPlanController.do?exportXls", "tOHolidayPlanList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOHolidayPlanController.do?exportXlsByT", "tOHolidayPlanList");
    }
    
    function sendPlanMsg(){
        sendMsg("假前工作计划");
    }
</script>
<script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>