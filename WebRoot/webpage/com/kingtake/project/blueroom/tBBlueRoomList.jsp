<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tBBlueRoomList" checkbox="false" onDblClick="goDetail()" fitColumns="true" title="蓝色讲坛信息表" actionUrl="tBBlueRoomController.do?datagrid" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="150"></t:dgCol>
      <t:dgCol title="名称" field="roomName" query="true" isLike="true" queryMode="single" width="200"></t:dgCol>
      <t:dgCol title="级别" field="roomLevel" queryMode="single" width="40" codeDict="1,LSJTJB"></t:dgCol>
      <t:dgCol title="举办单位id" field="holdUnitId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="举办单位名称" field="holdUnitName" queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="主办单位id" field="hostUnitId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="主办单位名称" field="hostUnitName" queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="举办地点" field="holdAddress" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="活动规模" field="activityScale" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="报告人姓名" field="reporterName" query="true" isLike="true" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="报告时间" field="beginReportTime" formatter="yyyy-MM-dd" queryMode="single" width="80"></t:dgCol>
      <!-- 取消开始时间和结束时间，将数据库中的开始时间作为报告时间 -->
      <%-- <t:dgCol title="结束报告时间" field="endReportTime" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol> --%>
      <t:dgCol title="推荐单位id" field="recommendUnitId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="推荐单位名称" field="recommendUnitName" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="担任职务" field="postPosition" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="报告内容" field="reportContent" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="备注" field="memo" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="状态" field="confirmFlag" hidden="false" queryMode="single" width="40" replace="未确认_0,已确认_1"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="confirmFlag#eq#0" title="确认" funname="goOk(id)"></t:dgFunOpt>
      <t:dgFunOpt exp="confirmFlag#eq#0" funname='goEdit(id)' title="编辑"></t:dgFunOpt>
      <t:dgDelOpt exp="confirmFlag#eq#0" title="删除" url="tBBlueRoomController.do?doDel&id={id}" />

      <t:dgToolBar title="录入" icon="icon-add" url="tBBlueRoomController.do?goEdit" funname="add" width="600" height="540"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBBlueRoomController.do?goEdit" funname="detail" width="600" height="540"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/blueroom/tBBlueRoomList.js"></script>
<script type="text/javascript">
  $(document).ready(function() {
    //给时间控件加上样式
    $("#tBBlueRoomListtb").find("input[name='beginReportTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    });
    /* $("#tBBlueRoomListtb").find("input[name='endReportTime']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function() {
      WdatePicker({
        dateFmt : 'yyyy-MM-dd'
      });
    }); */
    //设置查询条件输入框的样式
    $('#roomName').attr('style', 'width:150px;');
  });

  //导出
  function ExportXls() {
    JeecgExcelExport("tBBlueRoomController.do?exportXls", "tBBlueRoomList");
  }
  //确认操作(0:未确认，1：已确认)
  function goOk(id) {
    $.messager.confirm('确认', '确认后将无法进行修改与删除，您确定要进行该操作吗？', function(rtnVal) {
      if (rtnVal) {
        changeFlag(id, '1');
      }
    });
  }

  //查看
  function goDetail() {
    var rows = $("#tBBlueRoomList").datagrid("getSelections");
    if (rows.length == 0) {
      tip("请至少选择一条数据进行查看！");
      return false;
    }
    var id = rows[0].id;
    $.dialog({
      content : 'url:tBBlueRoomController.do?goEdit&id=' + id + '&load=detail',
      lock : true,
      width : '600px',
      height : '540px',
      title : "查看",
      opacity : 0.3,
      cache : false,
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }
  //改变记录确认状态
  //status(0:未确认，1：已确认)
  function changeFlag(id, status) {
    $.ajax({
      url : 'tBBlueRoomController.do?changeFlag',
      type : 'post',
      data : {
        id : id,
        confirmFlag : status
      },
      success : function(result) {
        result = $.parseJSON(result);
        $('#tBBlueRoomList').datagrid('reload');
        tip(result.msg);
      }
    });
  }
  
  //编辑
  function goEdit(id) {
    $.dialog({
      content : 'url:tBBlueRoomController.do?goEdit&id=' + id,
      lock : true,
      width : '600px',
      height : '540px',
      title : "修改",
      opacity : 0.3,
      cache : false,
      ok : function() {
        iframe = this.iframe.contentWindow;
        saveObj();
        return false;
      },
      cancelVal : '关闭',
      cancel : function() {
      }
    });
  }
</script>