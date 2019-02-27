<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="scheduleLayout" class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="scheduleList" checkbox="true" fitColumns="true" title="日程安排" actionUrl="tOScheduleController.do?datagrid&df=1" onClick="" 
    idField="id" fit="true" onDblClick="dbClickRow" queryMode="group" pageSize="100" pageList="[100,200,300]">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="用户id" field="userId" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="用户姓名" field="userName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" query="true" isLike="true" queryMode="single" width="170"></t:dgCol>
      <t:dgCol title="内容" field="content" query="true" isLike="true" queryMode="single" width="250" extendParams="formatter:contentFormatter,"></t:dgCol>
      <t:dgCol title="创建人id" field="createBy" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createName" hidden="false" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="开始时间" field="beginTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="结束时间" field="endTime" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="地点" field="address" hidden="true" queryMode="single" width="120" overflowView="true"></t:dgCol>
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="是否完成" field="finishedFlag" replace="已完成_1,未完成_0" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="事项类型" field="scheduleType" codeDict="1,SXLX"  queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="公开状态" field="openStatus" codeDict="1,GKZT" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="接收标志" field="sendReceiveFlag" hidden="true" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgToolBar title="恢复" icon="icon-redo" funname="doBack"></t:dgToolBar>
    </t:datagrid>
  </div>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#scheduleListtb").find("input[name='beginTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#scheduleListtb").find("input[name='beginTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#scheduleListtb").find("input[name='endTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#scheduleListtb").find("input[name='endTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                
            });
  
  
  var li_east = 0;
//查看类型链接
function openPanel(id){
    var title = '发送/留言';
    if(li_east == 0){
        $('#scheduleLayout').layout('expand','east');
    }
    $('#scheduleLayout').layout('panel','east').panel('setTitle', title);
    $('#sendReplyListpanel').panel("refresh", "tOScheduleController.do?goSendReplyList&scheduleId=" + id);
}

function contentFormatter(value, row, index) {
    if (row.finishedFlag == '0') {
        return '<span style="color:blue">' + value + '</span>';
    } else {
        return value;
    }
}

//回收站
function doBack(){
    var row = $('#scheduleList').datagrid('getSelections');
    if (row.length <= 0) {
        tip('请至少选择一条记录！');
        return;
    } else {
        $.messager.confirm('确认', '您确认恢复勾选的日程安排吗？', function(r) {
            if (r) {
        var ids = [];
        for (var i = 0; i < row.length; i++) {
            ids.push(row[i].id);
        }
        ids = ids.join(',');
        $.ajax({
            url : 'tOScheduleController.do?doBack',
            data : {
                ids : ids
            },
            type : 'post',
            dataType : 'json',
            success : function(data) {
                tip(data.msg);
                $("#scheduleList").datagrid('reload');
            }
        });
    }
    });
    }
}
</script>