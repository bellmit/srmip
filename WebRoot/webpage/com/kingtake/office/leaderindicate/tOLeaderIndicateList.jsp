<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOLeaderIndicateList" checkbox="true" fitColumns="true" title="校首长批示落实情况" actionUrl="tOLeaderIndicateController.do?datagrid" idField="id" fit="true" onDblClick="dbClickRow" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="单位" field="deptName" query="true" isLike="true" queryMode="single" width="150"></t:dgCol>
     <%--  <t:dgCol title="接收人id" field="receiverId" hidden="true" ></t:dgCol>
      <t:dgCol title="接收人" field="receiverName" queryMode="group" width="120"></t:dgCol> --%>
      <t:dgCol title="提交状态" field="submitFlag" replace="未发送_0,已发送_1,已完成_3,已驳回_2" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="发送接收标志" field="SendReceiveFlag" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add" url="tOLeaderIndicateController.do?goUpdate" funname="add" height="100%" width="100%"></t:dgToolBar>
      <t:dgFunOpt title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
      <t:dgFunOpt title="导出" funname="ExportXls(id)" ></t:dgFunOpt>
      <t:dgDelOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="删除" url="tOLeaderIndicateController.do?doDel&id={id}" />
      <t:dgFunOpt title="发送" funname="doWorkPointSubmit(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&SendReceiveFlag#eq#2" title="处理完毕" funname="doRecieve(id)" ></t:dgFunOpt>
      <t:dgToolBar title="查看校首长批示情况" icon="icon-search" url="tOLeaderIndicateController.do?goUpdate" funname="detail" height="100%" width="100%"></t:dgToolBar>
      <t:dgToolBar title="查看处理情况" icon="icon-search" funname="goViewReceiveList"></t:dgToolBar>
      <t:dgToolBar title="发送提醒" icon="icon-message"  funname="sendPlanMsg"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/leaderindicate/tOLeaderIndicateList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tOResearchActivityListtb").find("input[name='beginTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOResearchActivityListtb").find("input[name='beginTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOResearchActivityListtb").find("input[name='endTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOResearchActivityListtb").find("input[name='endTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOLeaderIndicateController.do?upload', "tOLeaderIndicateList");
    }

    //导出
    function ExportXls(id) {
        JeecgExcelExport("tOLeaderIndicateController.do?exportXls&id="+id, "tOLeaderIndicateList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOLeaderIndicateController.do?exportXlsByT", "tOLeaderIndicateList");
    }
    
 
  //数据编辑
  function goUpdate(id){
      var url = "tOLeaderIndicateController.do?goUpdate&id="+id;
      createwindow("编辑",url,"100%","100%");
  }
  
  function sendPlanMsg(){
      sendMsg("校首长批示落实情况");
  }
</script>
<script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>