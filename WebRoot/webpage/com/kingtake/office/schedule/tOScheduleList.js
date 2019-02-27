var count = 0;
/**
 * 加载日历事件
 */
function loadSchedule() {
    $('#calendar').fullCalendar({
                        header : {
                            left : 'prev,next today',
                            center : 'title',
                            right : ''
                        },
                        firstDay : 1,
                        timeFormat : 'H:mm',
                        axisFormat : 'H:mm',
                        weekMode : "variable",
                        height : "700",
                        dayClick : function(date) {
                            date = $.fullCalendar.formatDate(date, 'yyyy-MM-dd HH:mm:ss');
                            add('新增日程', 'tOScheduleController.do?goUpdate&beginTime=' + date, 'tOScheduleList', 800,
                                    480);
                        },
                        buttonText:{
                            today:    '本月'
                        },
                        monthNames:['一月','二月', '三月', '四月', '五月', '六月', '七月',
                                    '八月', '九月', '十月', '十一月', '十二月'],
                        dayNames:['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
                        dayNamesShort:['日','一','二','三','四','五','六'],
                        eventClick : function(calEvent, jsEvent, view) {
                            var scheduleId = calEvent.id;
                            //finishFlag = calEvent.finishFlag;
                        },
                        events : function(start, end, callback) {
                            $.ajax({
                                        type : 'post',
                                        url : 'tOScheduleController.do?dataList',
                                        asyn : false,
                                        success : function(result) {
                                            var json = jQuery.parseJSON(result);
                                            var events = new Array();
                                            for ( var i in json) {
                                                events.push({
                                                    id : json[i].ID,
                                                    title : json[i].TITLE+"("+json[i].CREATE_NAME+")",
                                                    start : new Date(json[i].BEGIN_TIME),
                                                    end : new Date(json[i].END_TIME),
                                                    backgroundColor : "#" + json[i].COLOR,
                                                    finishFlag : json[i].FINISHED_FLAG,
                                                    receiveFlag : json[i].receiveFlag
                                                });
                                            }
                                            callback(events);

                                        }
                                    });
                        },eventRender:function(event, element){
                            var scheduleId = event.id;
                            element.bind('dblclick',function(){
                                add('编辑', 'tOScheduleController.do?goUpdate&id=' + scheduleId+'&isOpen=1', 'tOScheduleList', 800, 480);
                            });
                        },eventAfterRender: function(event, element, view) {
                            var inner = $(element).find("div.fc-event-inner");
                            inner.css("float","left");
                            if(event.receiveFlag=='3'){
                                inner.css("width", (parseInt($(element).width())-100) + "px");
                            }else{
                                inner.css("width", (parseInt($(element).width())-80) + "px");
                            }
                            inner.css("overflow","hidden");
                            var div = $("<div style=''></div>");
                            if (event.finishFlag == '0') {
                                $(div).append("<img style='float:right; height:16px; width:16px;' src='images/finish.png' alt='完成' title='完成' onclick=finishSchedule('"+event.id+"',1) class='hand' />");
                            } else {
                                $(div).append("<img style='float:right; height:16px; width:16px;' src='images/cancel.png' alt='取消完成' title='取消完成' onclick=finishSchedule('"+event.id+"',0) class='hand' />");
                            }
                            if(event.receiveFlag=='3'){
                                $(div).append("<img style='float:right; height:16px; width:16px;' src='images/reply.png' alt='回复' title='回复' onclick=goResponse('"+event.id+"') class='hand' />");
                            }
                            $(div).append("<img style='float:right; height:16px; width:16px;margin-right:3px;' src='images/delete.png' alt='删除' title='删除' onclick=delSchedule('"+event.id+"') class='hand' />"
                                    +"<img style='float:right; height:16px; width:16px;margin-right:3px;' src='images/send.png' alt='发送' title='发送/留言' onclick=sendSchedule('"+event.id+"') class='hand' />"
                                            + "<img style='float:right;height:16px; width:16px;margin-right:3px;' src='images/edit.png' alt='编辑' title='编辑' onclick=editSchedule('"+event.id+"') class='hand' />");
                            $(element).append(div);
                        }
                    });
}

//发送、留言
function sendSchedule(scheduleId){
    createdetailwindow("发送/留言","tOScheduleController.do?goSendReplyList&scheduleId=" + scheduleId+"&type=cal", 700,400);
}

function selectReceiver(scheduleId){
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
                submit(scheduleId, userId, realName);
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
                submit(scheduleId, userId, realName);
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
        }
    });
}

/**
 * 重新获取所有的日历事件
 */
function reloadSchedule() {
    $('#calendar').fullCalendar('refetchEvents');
    $('#calendar').fullCalendar('rerenderEvents');
}

/**
 * 信息提示框
 * 
 * @param msg
 */
function showMsg(msg) {
    $.messager.show({
        title : '提示信息',
        msg : msg,
        timeout : 3000,
        showType : 'slide'
    });
}

/**
 * 弹出编辑日程的页面
 * 
 * @param div
 */
function editSchedule(scheduleId) {
    add('编辑', 'tOScheduleController.do?goUpdate&id=' + scheduleId+'&isOpen=1', 'tOScheduleList', 800, 480);
}

/**
 * 删除日程
 * 
 * @param div
 */
function delSchedule(scheduleId) {
    $.messager.confirm('确认', "确认删除该条记录吗？", function(yes) {
        if (yes) {
            // 删除
            $.ajax({
                type : 'post',
                url : 'tOScheduleController.do?doDel',
                data : {
                    id : scheduleId
                },
                asyn : false,
                success : function(result) {
                    var json = jQuery.parseJSON(result);
                    reloadSchedule();
                    $.messager.show({
                        title : '提示信息',
                        msg : json.msg,
                        timeout : 3000,
                        showType : 'slide'
                    });
                }
            });
        }
    });
}

/**
 * 完成日程
 * 
 * @param div
 */
function finishSchedule(scheduleId,flag) {
    var info = "";
    if (flag == '1') {
        info = "确认将该日程安排状态设置为已完成吗？";
    } else {
        info = "确认将该日程安排状态设置为未完成吗？";
    }
    $.messager.confirm('确认', info, function(yes) {
        if (yes) {
            // 完成
            $.ajax({
                type : 'post',
                url : 'tOScheduleController.do?doUpdateFinishFlag',
                data : {
                    id : scheduleId,
                    finishedFlag : flag
                },
                cache : false,
                success : function(result) {
                    var json = jQuery.parseJSON(result);
                    reloadSchedule();
                    $.messager.show({
                        title : '提示信息',
                        msg : json.msg,
                        timeout : 3000,
                        showType : 'slide'
                    });
                }
            });
        }
    });
}

//回复
function goResponse(id) {
    var url = 'tOScheduleController.do?goResponse';

    // 打开回复窗口
    var width = 640;
    var height = 180;
    var title = "回复";
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
                var resContent = iframe.getResContent();
                if (resContent == "") {
                    tip("请填写回复内容！");
                    return;
                }
                doResponse(id, resContent);
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
                var resContent = iframe.getResContent();
                if (resContent == "") {
                    tip("请填写回复内容！");
                    return;
                }
                doResponse(id, resContent);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
}

//回复
function doResponse(id,resContent){
    $.ajax({
        url:'tOScheduleController.do?doResponse',
        data:{
            id:id,
            resContent:resContent
        },
        cache:false,
        type:'POST',
        dataType:'json',
        success:function(data){
            $.messager.show({
                title : '提示信息',
                msg : data.msg,
                timeout : 3000,
                showType : 'slide'
            });
            if(data.success){
                reloadSchedule();
            }
        }
    });
}

/**
 * 打开导出交办材料的窗口
 */
function openHandoverWin() {
    add('生成交班材料', 'tOScheduleController.do?goHandover', 'tOScheduleList', 620, 130);
}
