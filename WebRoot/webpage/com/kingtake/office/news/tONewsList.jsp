<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tONewsList" checkbox="true" fitColumns="true" title="要讯" actionUrl="tONewsController.do?datagrid" idField="id" fit="true" onDblClick="dbClickRow" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="单位" field="departName" queryMode="group" width="150"></t:dgCol>
      <t:dgCol title="撰稿人" field="writer" width="80"></t:dgCol>
      <t:dgCol title="核稿人" field="checkUserName" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="核稿人Id" field="checkUserId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="时间" field="time" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="电话" field="phone" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="提交状态" field="submitFlag" replace="未提交_0,已提交_1,已审核_3,已驳回_2" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add" url="tONewsController.do?goUpdate" funname="add" height="100%" width="1000"></t:dgToolBar>
      <t:dgFunOpt title="编辑" funname="goUpdate(id)"></t:dgFunOpt>
      <t:dgDelOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="删除" url="tONewsController.do?doDel&id={id}" />
      <t:dgFunOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="提交" funname="doNewsSubmit(id)"></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&checkUserId#eq\#${uid}" title="通过" funname="doRecieve(id)"></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&checkUserId#eq\#${uid}" title="退回" funname="doReturn(id)"></t:dgFunOpt>
      <t:dgToolBar title="查看" icon="icon-search" url="tONewsController.do?goUpdate" funname="detail" height="100%" width="1000"></t:dgToolBar>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
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
        openuploadwin('Excel导入', 'tONewsController.do?upload', "tOWorkPointList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tONewsController.do?exportXls", "tOWorkPointList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tONewsController.do?exportXlsByT", "tOWorkPointList");
    }

    //数据编辑
    function goUpdate(id) {
        var url = "tONewsController.do?goUpdate&id=" + id;
        createwindow("编辑", url, 1000, '100%');
    }
</script>
<script src="webpage/com/kingtake/office/news/tONewsList.js"></script>