<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tZZlsqList" checkbox="true" fitColumns="true" title="专利申请列表" actionUrl="tZZlsqController.do?datagrid&role=depart" idField="id" fit="true" queryMode="group" onDblClick="goViewDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="归档号" field="gdh" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="完成单位" field="wcdw" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="类型" field="lx" codeDict="1,ZLLX" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目" field="glxm" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="名称" field="mc" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="发明人" field="fmr" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="第一发明人身份证号" hidden="true" field="dyfmrsfzh" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="代理机构" field="dljgId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="联系人" field="lxr" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="联系人电话" field="lxrdh" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="备注" field="bz" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="专利状态" field="zlzt" codeDict="1,ZLZT" queryMode="group" width="90"></t:dgCol>
      <t:dgCol title="审批状态" field="apprStatus" codeDict="1,SPZT" queryMode="group" width="90"></t:dgCol>
      <t:dgCol title="申请文件状态" field="wjzt" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
      <t:dgFunOpt title="编辑" funname="goUpdate(id)"></t:dgFunOpt>
      <t:dgToolBar title="查看" icon="icon-search" url="tZZlsqController.do?goUpdate&opt=view" funname="detail" height="100%"></t:dgToolBar>
      <t:dgFunOpt exp="apprStatus#eq#2" title="申请流程" funname="openProcess(id)"></t:dgFunOpt>
    </t:datagrid>
  </div>
</div>
<input id="tipMsg" type="hidden">
<script src="webpage/com/kingtake/zscq/zlsq/tZZlsqList.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tZZlsqListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tZZlsqListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tZZlsqListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tZZlsqListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tZZlsqController.do?upload', "tZZlsqList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tZZlsqController.do?exportXls","tZZlsqList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tZZlsqController.do?exportXlsByT","tZZlsqList");
}

//发送专利申请审批
function sendTravelAppr(id, apprStatus){
	var title = "审批";
	var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + id +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_ZLSQ%>';
	var width = "100%";
	var height = window.top.document.body.offsetHeight-100;
	var dialogId = "apprInfo";
	
	if(apprStatus == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("专利申请审批流程已完成，不能再发送审批");
	}else if(apprStatus == '<%=ApprovalConstant.APPRSTATUS_REBUT%>') {
            url += '&tip=true';
            $("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑出账审批信息！");
        }

        tabDetailDialog(title, url, width, height, dialogId);
    }

    //更新差旅请假审批状态
    function updateApprStatus(id) {
        var url1 = 'tZZlsqController.do?updateFinishFlag';
        var url2 = url1 + '2';
        updateFinishFlag(id, url1, url2);
    }

    function goUpdate(id) {
        var url = "tZZlsqController.do?goUpdate";
        url += '&id=' + id;
        createwindow("编辑", url, null, "100%");
    }
    
    function goViewDetail(rowIndex, rowData) {
        var id = rowData.id;
        var url = "tZZlsqController.do?goUpdate&load=detail";
        url += '&id=' + id;
        createdetailwindow("查看", url, null, "100%");
    }

    function loadButton() {
    }

    function openProcess(id){
        addTab("专利申请管理", "tZZlsqController.do?goDepartMain&id="+id+"&opt=edit", "default");
    }
    
    //提交申请文件
    function sendApplyFile(id, apprStatus) {
        var buttons = [];
        if (apprStatus == '0' || apprStatus == '2') {
            buttons.push({
                name : '提交',
                focus : true,
                callback : function() {
                    submitFile(id);
                    return false;
                }
            });
        }
        buttons.push({
            name : '取消'
        });
        var url = "tZSqwjController.do?goSqwj&zlsqId=" + id;
        var title = "递交申请文件";
        var  width = window.top.document.body.offsetWidth;
        var  height = window.top.document.body.offsetHeight - 100;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id:'sqwjDialog',
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                title : title,
                opacity : 0.3,
                fixed : true,
                cache : false,
                button : buttons
            }).zindex();
        } else {
            W.$.dialog({
                id:'sqwjDialog',
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                parent : windowapi,
                title : title,
                opacity : 0.3,
                fixed : true,
                cache : false,
                button : buttons
            }).zindex();
        }
    }
    
    //提交申请
    function submitFile(id) {
         $.dialog.confirm('您确认已经上传申请文件完毕，发送审查吗？', function() {
                var operateUrl = 'tPmDeclareController.do?goSelectOperator2';
                // 打开选择人窗口
                var width = 640;
                var height = 180;
                var title = "选择接收人";

                if (typeof (windowapi) == 'undefined') {
                    $.dialog({
                        content : 'url:' + operateUrl,
                        lock : true,
                        width : width,
                        height : height,
                        title : title,
                        opacity : 0.3,
                        cache : false,
                        ok : function() {
                            iframe = this.iframe.contentWindow;
                            var realName = iframe.getRealName();
                            var userId = iframe.getUserId();
                            if (realName == "") {
                                return false;
                            }
                            doSubmit(id, userId, realName);
                        },
                        cancelVal : '关闭',
                        cancel : true
                    }).zindex();
                } else {
                    W.$.dialog({
                        content : 'url:' + operateUrl,
                        lock : true,
                        width : width,
                        height : height,
                        parent : windowapi,
                        title : title,
                        opacity : 0.3,
                        cache : false,
                        ok : function() {
                            iframe = this.iframe.contentWindow;
                            var realName = iframe.getRealName();
                            var userId = iframe.getUserId();
                            if (realName == "") {
                                return false;
                            }
                            doSubmit(id, userId, realName);
                        },
                        cancelVal : '关闭',
                        cancel : true
                    }).zindex();
                }
        },function(){},windowapi);

    }

    function doSubmit(id, userId, realName) {
        var url = "tZSqwjController.do?doSubmit";
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            data : {
                'zlsqId' : id,
                'receiverId' : userId,
                'receiverName' : realName
            },
            url : url,// 请求的action路径
            error : function() {// 请求失败处理函数
            },
            success : function(data) {
                var d = $.parseJSON(data);
                var msg = d.msg;
                tip(msg);
                if (d.success) {
                    $("#tZZlsqList").datagrid('reload');
                    var dialog = $.dialog.list['sqwjDialog'];
                    dialog.close();
                }
            }
        });
    }
</script>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script src="webpage/com/kingtake/project/manage/addTab.js"></script>
