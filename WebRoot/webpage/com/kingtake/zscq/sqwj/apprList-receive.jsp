<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="sqwjAuditList" checkbox="false" fitColumns="true" actionUrl="tZSqwjController.do?datagridAudit&type=${type}" onDblClick="goViewDetail" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="专利申请主键" field="zlsqId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="归档号" field="gdh" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="完成单位" field="wcdw" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="类型" field="lx" codeDict="1,ZLLX" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目" field="glxm" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="名称" field="mc"  queryMode="single" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="发明人" field="fmr"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="审查状态" field="applyStatus" replace="暂存_0,已提交_1,修改_2,审查完成_3" queryMode="group" width="80"></t:dgCol>
      <!-- 已处理 -->
      <c:if test="${type eq 1}">
        <t:dgToolBar title="查看" icon="icon-search" url="tZSqwjController.do?goSqwj" funname="detail" height="100%" width="100%"></t:dgToolBar>
      </c:if>
      <!-- 未处理 -->
      <c:if test="${type eq 0}">
        <t:dgCol title="发送时间" field="submit_time" queryMode="group" formatter="yyyy-MM-dd hh:mm:ss" width="100" align="center"></t:dgCol>
        <t:dgCol title="是否有修改意见" field="ismsgText" hidden="true"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="180"></t:dgCol>
        <t:dgToolBar title="查看" icon="icon-search" url="tZSqwjController.do?goSqwj" funname="detail" height="100%" width="100%"></t:dgToolBar>
        <t:dgFunOpt exp="applyStatus#eq#1" title="通过" funname="passAudit(id)" />
        <t:dgFunOpt exp="applyStatus#eq#1" title="不通过" funname="rejectAudit(id)" />
        <t:dgFunOpt exp="ismsgText#eq#1" title="查看修改意见" funname="viewSuggestion(id)" />
      </c:if>

    </t:datagrid>
    <input id="tipMsg" type="hidden" value="" />
  </div>
</div>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
    //提交审核
    function passAudit(id) {
        if (typeof (windowapi) == 'undefined') {
            $.dialog.confirm("确定审核通过吗？", function() {
                doPass(id);
            }, function() {
            }).zindex();
        } else {
            W.$.dialog.confirm("确定审核通过吗？", function() {
                doPass(id);
            }, function() {
            }, windowapi).zindex();
        }
    }

    //审核不通过
    function rejectAudit(id) {
        if (typeof (windowapi) == 'undefined') {
            $.dialog.confirm("确定审核不通过吗？", function() {
                openMsgDialog(id);
            }, function() {
            }).zindex();
        } else {
            W.$.dialog.confirm("确定审核不通过吗？", function() {
                openMsgDialog(id);
            }, function() {
            }, windowapi).zindex();
        }
    }

    //打开弹窗填写修改意见
    function openMsgDialog(id) {
        var url = "tZSqwjController.do?goPropose&id=" + id;
        var title = "填写修改意见";
        $.dialog({
            content : 'url:' + url,
            title : '提出修改意见',
            lock : true,
            opacity : 0.3,
            width : '450px',
            height : '120px',
            ok : function() {
                iframe = this.iframe.contentWindow;
                var msgText = iframe.getMsgText();
                var proposeIframe = iframe;
                $.ajax({
                    url : 'tZSqwjController.do?doPropose',
                    data : {
                        id : id,
                        msgText : msgText
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        tip(data.msg);
                        if (data.success) {
                            reloadTable();
                        }
                    }
                });
            },
            cancel : function() {
                reloadTable();
            },
        }).zindex();
    }

    //审核
    function doPass(id, opt) {
        $.ajax({
            url : 'tZSqwjController.do?doPass',
            type : 'GET',
            dataType : 'json',
            cache : 'false',
            data : {
                'id' : id
            },
            success : function(data) {
                tip(data.msg);
                if (data.success) {
                    reloadTable();
                }
            }
        });
    }

    //查看详情
    function goViewDetail(rowIndex,rowData){
        var id = rowData.id;
        var url = "tZSqwjController.do?goSqwj";
        url += '&id=' + id;
        createdetailwindow("查看", url, "100%", "100%");
    }
    
  //查看审批意见
    function viewSuggestion(id,index){
        var url = "tZSqwjController.do?goPropose&id="+id;
        createdetailwindow("查看修改意见",url,450,120);
    }
</script>