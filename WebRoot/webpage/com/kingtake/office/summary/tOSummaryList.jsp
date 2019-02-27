<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tOSummaryList" checkbox="true" fitColumns="true" title="总结材料" actionUrl="tOSummaryController.do?datagrid&df=0" idField="id" fit="true" onDblClick="dbClickRow" queryMode="group">
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
      <t:dgToolBar title="录入" icon="icon-add" url="tOSummaryController.do?goUpdate" funname="add" height="100%" width="100%"></t:dgToolBar>
      <t:dgFunOpt title="编辑" funname="goUpdate(id)" ></t:dgFunOpt>
      <t:dgFunOpt title="导出" funname="ExportXls(id)" ></t:dgFunOpt>
      <t:dgDelOpt exp="submitFlag#ne\#${submitYes}&&submitFlag#ne\#${submitReceive}&&createBy#eq\#${uname}" title="删除" url="tOSummaryController.do?doDel&id={id}" />
      <t:dgFunOpt title="发送" funname="doWorkPointSubmit(id)" ></t:dgFunOpt>
      <t:dgFunOpt exp="submitFlag#eq\#${submitYes}&&SendReceiveFlag#eq#2" title="处理完毕" funname="doRecieve(id)" ></t:dgFunOpt>
      <t:dgToolBar title="查看总结材料" icon="icon-search" url="tOSummaryController.do?goUpdate" funname="detail" height="100%" width="100%"></t:dgToolBar>
      <t:dgToolBar title="查看处理情况" icon="icon-search" funname="goViewReceiveList"></t:dgToolBar>
      <t:dgToolBar title="发送提醒" icon="icon-message"  funname="sendPlanMsg"></t:dgToolBar>
      <t:dgToolBar title="隐藏" icon="icon-hidden" funname="goDelete"></t:dgToolBar>
      <t:dgToolBar title="回收站" icon="icon-recycle" funname="goRecycle"></t:dgToolBar>
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

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOSummaryController.do?upload', "tOSummaryList");
    }

    //导出
    function ExportXls(id) {
        JeecgExcelExport("tOSummaryController.do?exportXls&id="+id, "tOSummaryList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOSummaryController.do?exportXlsByT", "tOSummaryList");
    }
    
 
  //数据编辑
  function goUpdate(id){
      var url = "tOSummaryController.do?goUpdate&id="+id;
      createwindow("编辑",url,"100%","100%");
  }
  
  function sendPlanMsg(){
      sendMsg("总结材料");
  }
  
    function goDelete() {
        var row = $('#tOSummaryList').datagrid('getSelections');
        if (row.length <= 0) {
            tip('请至少选择一条总结材料！');
            return;
        } else {
            $.messager.confirm('确认', '您确认隐藏勾选的总结材料吗？', function(r) {
                if (r) {
                    var ids = [];
                    for (var i = 0; i < row.length; i++) {
                        ids.push(row[i].id);
                    }
                    ids = ids.join(',');
                    $.ajax({
                        url : 'tOSummaryController.do?doHide',
                        data : {
                            ids : ids
                        },
                        type : 'post',
                        dataType : 'json',
                        success : function(data) {
                            tip(data.msg);
                            reloadtOSummaryList();
                        }
                    });
                }
            });
        }
    }
    
  //回收站
    function goRecycle(){
        var title="我的回收站";
        var url = "tOSummaryController.do?goRecycle";
        var width = window.top.document.body.offsetWidth;
        var height = window.top.document.body.offsetHeight-100;
        $.dialog({
    		content: 'url:'+url,
    		lock : true,
    		width:width,
    		height:height,
    		title:title,
    		opacity : 0.3,
    		cache:false,
    	    cancelVal: '关闭',
    	    cancel: function(){
    	        $("#tOSummaryList").datagrid('reload');
    	    }
    	}).zindex();
    }
</script>
<script type="text/javascript" src="webpage/com/kingtake/common/sendMsg.js"></script>