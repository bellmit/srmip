<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<input id="processInsId" type="hidden" value="${processInstId }" />
<input id="taskId" type="hidden" value="${taskId }" />
<input id="read" type="hidden" value="${read }" />
<script>
//查看流程
function viewHistory(processInstanceId, title) {
    goProcessHisTab(processInstanceId, title);
}

/**
 * 查看流程意见
 */
function viewRemark() {
    var processInsId = $("#processInsId").val();
    var url = "tPmDeclareController.do?goViewProcess&processInstId=" + processInsId;
    createdetailwindow("查看流程意见", url, null, null);
}

// 重新提交
function compeleteProcess() {
    var taskId = $("#taskId").val();
    if(typeof(windowapi)=='undefined'){
        $.dialog.confirm('您确定已经修改完毕，重新提交吗?', function(r) {
            if (r) {
                $.ajax({
                    url : "activitiController.do?processComplete",
                    type : "POST",
                    dataType : "json",
                    data : {
                        "taskId" : taskId,
                        "nextCodeCount" : 1,
                        "model" : '1',
                        "reason" : "重新提交",
                        "option" : "重新提交"
                    },
                    async : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            window.location.reload();
                        }
                    }
                });
            }
        },function(){});
    }else{
        W.$.dialog.confirm('您确定已经修改完毕，重新提交吗?', function(r) {
            if (r) {
                $.ajax({
                    url : "activitiController.do?processComplete",
                    type : "POST",
                    dataType : "json",
                    data : {
                        "taskId" : taskId,
                        "nextCodeCount" : 1,
                        "model" : '1',
                        "reason" : "重新提交",
                        "option" : "重新提交"
                    },
                    async : false,
                    cache : false,
                    success : function(data) {
                        if (data.success) {
                            window.location.reload();
                        }
                    }
                });
            }
        },function(){},windowapi);
    }
    
}
</script>