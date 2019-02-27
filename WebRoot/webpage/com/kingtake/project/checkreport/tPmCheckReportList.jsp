<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmCheckReportList" checkbox="true" fitColumns="true" title="中期检查报告" actionUrl="tPmCheckReportController.do?datagrid&projectId=${projectId}" idField="id" fit="true"
      queryMode="group" onDblClick="goDetail">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="项目基_主键" field="tpId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="标题" field="reportTitle" query="true" queryMode="single" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="报告起始时间" field="yearStart" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="报告结束时间" field="yearEnd" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="备注" field="remark" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审查状态" field="checkStatus" replace="未提交_0,已提交_1,审核通过_2,审核不通过_3,重新提交_4" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审查意见" field="checkSuggest" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审查人id" field="checkUserid" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审核人姓名" field="checkUsername"  queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="审核时间" field="checkDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
      <t:dgDelOpt exp="checkStatus#eq#0" title="删除" url="tPmCheckReportController.do?doDel&id={id}" />
      <t:dgToolBar title="录入" icon="icon-add" url="tPmCheckReportController.do?goUpdate&projectId=${projectId}" funname="add" height="300"></t:dgToolBar>
      <t:dgFunOpt exp="checkStatus#eq#0" title="编辑" funname="goUpdate(id,checkStatus)"></t:dgFunOpt>
      <t:dgFunOpt exp="checkStatus#eq#3" title="编辑" funname="goUpdate(id,checkStatus)"></t:dgFunOpt>
      <t:dgFunOpt exp="checkStatus#eq#0" title="提交" funname="submitAudit(id)"></t:dgFunOpt>
      <t:dgFunOpt exp="checkStatus#eq#3" title="重新提交" funname="reSubmitAudit(id)"></t:dgFunOpt>
      <%-- <t:dgFunOpt exp="checkStatus#eq#3" title="查看修改意见" funname="viewSuggestion(id)"></t:dgFunOpt> --%>
      <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmCheckReportController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
      <t:dgToolBar title="查看" icon="icon-search" url="tPmCheckReportController.do?goUpdate" funname="detail"></t:dgToolBar>
      <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script src="webpage/com/kingtake/project/checkreport/tPmCheckReportList.js"></script>
<script type="text/javascript">
    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tPmCheckReportListtb").find("input[name='yearStart_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='yearStart_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='yearEnd_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='yearEnd_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='checkDate_begin']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmCheckReportListtb").find("input[name='checkDate_end']").attr("class", "Wdate").attr("style",
                        "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tPmCheckReportController.do?upload', "tPmCheckReportList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tPmCheckReportController.do?exportXls", "tPmCheckReportList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tPmCheckReportController.do?exportXlsByT", "tPmCheckReportList");
    }

    function goUpdate(id, checkStatus, index) {
        var width = 750;
        var height = 400;
        var dialog;
        var title = "编辑";
        var url = "tPmCheckReportController.do?goUpdate&id=" + id;
        if (typeof (windowapi) == 'undefined') {
            dialog = $.dialog({
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                title : title,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancel : true,
                cancelVal : "取消"
            }).zindex();
        } else {
            dialog = W.$.dialog({
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                parent : windowapi,
                title : title,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancel : true,
                cancelVal : "取消"
            }).zindex();
        }
        if(checkStatus=='3'){
            dialog.button({
                name:'查看修改意见',
                callback:function(){
                    viewSuggestion(id);
                    return false;
                }
            })
        }
    }

    //提交审核
    function submitAudit(id,index) {
        var url = 'tPmDeclareController.do?goSelectOperator2';
        if (typeof (windowapi) == 'undefined') {
            $.dialog.confirm("确定提交审核吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180, id);
            }, function() {
            }).zindex();
        } else {
            W.$.dialog.confirm("确定提交审核吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180, id);
            }, function() {
            }, windowapi).zindex();
        }
    }
    
    //提交审核
    function reSubmitAudit(id,index) {
        var url = 'tPmDeclareController.do?goSelectOperator2';
        if (typeof (windowapi) == 'undefined') {
            $.dialog.confirm("确定修改完毕，重新提交审核吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180, id);
            }, function() {
            }).zindex();
        } else {
            W.$.dialog.confirm("确定修改完毕，重新提交审核吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180, id);
            }, function() {
            }, windowapi).zindex();
        }
    }

    //打开选择人窗口
    function openOperatorDialog(title, url, width, height, id) {
        var width = width ? width : 700;
        var height = height ? height : 400;
        if (width == "100%") {
            width = window.top.document.body.offsetWidth;
        }
        if (height == "100%") {
            height = window.top.document.body.offsetHeight - 100;
        }

        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                title : title,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var userId = iframe.getUserId();
                    if (userId == "") {
                        return false;
                    }
                    var realname = iframe.getRealName();
                    var deptId = iframe.getDepartId();
                    doAudit(id, userId, realname, deptId);
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        } else {
            W.$.dialog({
                content : 'url:' + url,
                lock : true,
                width : width,
                height : height,
                parent : windowapi,
                title : title,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    var userId = iframe.getUserId();
                    if (userId == "") {
                        return false;
                    }
                    var realname = iframe.getRealName();
                    var deptId = iframe.getDepartId();
                    doAudit(id, userId, realname, deptId);
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }

    //审核
    function doAudit(id, userId, realname, deptId) {
        $.ajax({
            url : 'tPmCheckReportController.do?doAudit',
            type : 'POST',
            dataType : 'json',
            cache : 'false',
            data : {
                'id' : id,
                "opt" : 'submit',
                'userId' : userId,
                'realname' : realname,
                'deptId' : deptId
            },
            success : function(data) {
                tip(data.msg);
                if (data.success) {
                    reloadTable();
                }
            }
        });
    }

  //查看审批意见
    function viewSuggestion(id){
        var url = "tPmCheckReportController.do?goPropose&id="+id;
        createdetailwindow("查看修改意见",url,450,120);
    }
  
  //查看
  function goDetail(rowIndex, rowData){
      var url = "tPmCheckReportController.do?goUpdate&load=detail&id="+rowData.id;
      createdetailwindow("查看",url,750,400);
  }
</script>