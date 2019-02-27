<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOSummaryList" checkbox="true" fitColumns="true" title="总结材料" actionUrl="tOSummaryController.do?datagrid&df=1" idField="id" fit="true" onDblClick="dbClickRow" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="title" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="单位" field="deptName" query="true" isLike="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="材料类型" field="materiaType" codeDict="1,ZJCLLX" query="true" isLike="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="提交状态" field="submitFlag" replace="未提交_0,已发送_1,已完成_3,已驳回_2" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="发送接收标志" field="SendReceiveFlag" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
      <t:dgToolBar title="恢复" icon="icon-redo" funname="doBack"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/office/summary/tOSummaryList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tOSummaryListtb").find("input[name='beginTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOSummaryListtb").find("input[name='beginTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOSummaryListtb").find("input[name='endTime_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tOSummaryListtb").find("input[name='endTime_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                
            });

  //数据编辑
  function goUpdate(id){
      var url = "tOSummaryController.do?goUpdate&id="+id;
      createwindow("编辑",url,"100%","100%");
  }
  
//回收站
  function doBack(){
      var row = $('#tOSummaryList').datagrid('getSelections');
      if (row.length <= 0) {
          tip('请至少选择一条记录！');
          return;
      } else {
          $.messager.confirm('确认', '您确认恢复勾选的总结材料吗？', function(r) {
              if (r) {
          var ids = [];
          for (var i = 0; i < row.length; i++) {
              ids.push(row[i].id);
          }
          ids = ids.join(',');
          $.ajax({
              url : 'tOSummaryController.do?doBack',
              data : {
                  ids : ids
              },
              type : 'post',
              dataType : 'json',
              success : function(data) {
                  tip(data.msg);
                  $("#tOSummaryList").datagrid('reload');
              }
          });
      }
      });
      }
  }
    
</script>
<script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>