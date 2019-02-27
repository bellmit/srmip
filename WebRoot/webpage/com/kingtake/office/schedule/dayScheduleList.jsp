<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="scheduleLayout" class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="scheduleList" checkbox="true" fitColumns="true" title="日程安排"  actionUrl="tOScheduleController.do?datagrid&df=0" onClick="" 
    idField="id" fit="true" onDblClick="dbClickRow" queryMode="group" pageSize="100" pageList="[100,200,300]" extendParams="remoteSort:false,">
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
      <%-- <t:dgCol title="关联人员id" field="relateUserid" hidden="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="关联人员姓名" field="relateUsername" hidden="true" queryMode="single" width="150"></t:dgCol> --%>
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="single" width="150"></t:dgCol>
      <t:dgCol title="是否完成" field="finishedFlag" replace="已完成_1,未完成_0" queryMode="single" width="60"></t:dgCol>
      <t:dgCol title="事项类型" field="scheduleType" codeDict="1,SXLX"  queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="公开状态" field="openStatus" codeDict="1,GKZT" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="接收标志" field="sendReceiveFlag" hidden="true" queryMode="group" width="60"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
      <t:dgToolBar title="录入" icon="icon-add" url="tOScheduleController.do?goListUpdate" funname="add" height="100%" width="100%"></t:dgToolBar>
      <t:dgFunOpt title="接收" exp="sendReceiveFlag#eq#2" funname="doRecieve(id)"  />
      <%-- <t:dgFunOpt title="发送" exp="sendReceiveFlag#ne#2" funname="send(id)" /> --%>
      <t:dgFunOpt title="编辑" exp="sendReceiveFlag#ne#2" funname="goUpdate(id)" ></t:dgFunOpt>
      <t:dgFunOpt title="发送/留言" funname="openPanel(id)" ></t:dgFunOpt>
      <t:dgDelOpt title="删除" exp="sendReceiveFlag#ne#2" url="tOScheduleController.do?doDel&id={id}" />
      <t:dgToolBar title="回复发送人" icon="icon-redo" funname="goResponse"  />
      <%-- <t:dgToolBar title="我的回复" icon="icon-search" funname="goResponseList"  /> --%>
      <t:dgToolBar title="查看内容" icon="icon-search" url="tOScheduleController.do?goUpdate" funname="detail" height="100%" width="100%"></t:dgToolBar>
      <%-- <t:dgToolBar title="发送" icon="icon-search"  funname="goViewReceiveList"></t:dgToolBar> --%>
      <t:dgToolBar title="隐藏" icon="icon-hidden" funname="goDelete"></t:dgToolBar>
      <t:dgToolBar title="回收站" icon="icon-recycle" funname="goRecycle"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
    </t:datagrid>
  </div>
  </div>
  <div data-options="region:'east',
  title:'mytitle',
  collapsed:true,
  split:true,
  border:false,
  onExpand : function(){
    li_east = 1;
  },
  onCollapse : function() {
      li_east = 0;
  }"
     style="width: 380px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="sendReplyListpanel"></div>
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

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tOResearchActivityController.do?upload', "tOResearchActivityList");
    }

    //导出
    function ExportXls() {
        var rows = $("#scheduleList").datagrid("getChecked");
        if (rows.length > 0) {
            var ids = [];
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
            JeecgExcelExport("tOScheduleController.do?exportXls&ids=" + ids.join(","), "scheduleList");
        } else {
            JeecgExcelExport("tOScheduleController.do?exportXls", "scheduleList");
        }
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tOResearchActivityController.do?exportXlsByT", "scheduleList");
    }

    //数据编辑
    function goUpdate(id) {
        var url = "tOScheduleController.do?goListUpdate&id=" + id;
        createwindow("编辑", url, "100%", "100%");
    }

    function dbClickRow(rowIndex, rowData) {
        gridname = 'scheduleList';
        var url = 'tOScheduleController.do?goUpdate&isOpen=1';
        url += '&load=detail&id=' + rowData.id;
        var title = '查看日程安排';
        var width = window.top.document.body.offsetWidth;
        ;
        var height = window.top.document.body.offsetHeight - 100;
        $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            fixed : true,
            cache : false,
            cancelVal : '关闭',
            cancel : function() {
                reloadTable();
            }
        }).zindex();
    }

    //数据编辑
    function send(id) {
        var operateUrl = 'tOHolidayPlanController.do?goSelectOperator&mode=multiply';
        //打开选择人窗口
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
                    submit(id, userId, realName);
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
                    submit(id, userId, realName);
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }

    function submit(id, userId, realName) {
        var url = "tOScheduleController.do?doSubmit";
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            data : {
                'id' : id,
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
                    $("#scheduleList").datagrid('reload');
                }
            }
        });
    }

    function goViewReceiveList() {
        var rows = $("#scheduleList").datagrid("getChecked");
        if (rows.length == 0 || rows.length > 1) {
            tip("请选择一行记录查看！");
            return;
        }
        var row = rows[0];
        var id = row.id;
        createdetailwindow("查看发送记录",
                "tOScheduleController.do?goReceiveLogList&researchId=" + id + "&sendType=schedule", 700, 400);
    }

    //接收交班材料
    function doRecieve(id) {
        var url = "tOScheduleController.do?doRecieve";
        $.messager.confirm('确认', '您确认接收该日程安排吗？', function(r) {
            if (r) {
                $.ajax({
                    async : false,
                    cache : false,
                    type : 'POST',
                    data : {
                        id : id
                    },
                    url : url,// 请求的action路径
                    error : function() {// 请求失败处理函数
                    },
                    success : function(data) {
                        var d = $.parseJSON(data);
                        var msg = d.msg;
                        tip(msg);
                        if (d.success) {
                            $("#scheduleList").datagrid('reload');
                        }
                    }
                });
            }
        });
    }

    //回复
    function goResponse() {
        var tabId = "scheduleList";
        gridname = tabId;
        var rowsData = $('#' + tabId).datagrid('getSelections');
        if (!rowsData || rowsData.length == 0 || rowsData.length > 1) {
            tip('请选择一条日程安排打开回复面板！');
            return;
        }
        if (rowsData[0].sendReceiveFlag == '1') {
            tip('该日程安排由本人创建，不能进行回复！');
            return;
        }
        var id = rowsData[0].id;
        var title = "回复发送人";
        var url = "tOScheduleController.do?goUpdate&id=" + id + "&select=1";
        createdetailwindow(title, url, "100%", "100%");
    }

    //我的回复列表
    function goResponseList() {
        var title = "我的回复列表";
        var url = "tOScheduleController.do?goMyResponseList";
        var width = 700;
        var height = 300;
        createdetailwindow(title, url, width, height);
    }

    function goDelete() {
        var row = $('#scheduleList').datagrid('getSelections');
        if (row.length <= 0) {
            tip('请至少选择一条记录！');
            return;
        } else {
            $.messager.confirm('确认', '您确认隐藏勾选的日程安排吗？', function(r) {
                if (r) {
                    var ids = [];
                    for (var i = 0; i < row.length; i++) {
                        ids.push(row[i].id);
                    }
                    ids = ids.join(',');
                    $.ajax({
                        url : 'tOScheduleController.do?doHide',
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

    var li_east = 0;
    //查看类型链接
    function openPanel(id) {
        var title = '发送/留言';
        if (li_east == 0) {
            $('#scheduleLayout').layout('expand', 'east');
        }
        $('#scheduleLayout').layout('panel', 'east').panel('setTitle', title);
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
    function goRecycle() {
        var title = "我的回收站";
        var url = "tOScheduleController.do?goRecycle";
        var width = window.top.document.body.offsetWidth;
        var height = window.top.document.body.offsetHeight - 100;
        $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            cancelVal : '关闭',
            cancel : function() {
                $("#scheduleList").datagrid('reload');
            }
        }).zindex();
    }
</script>