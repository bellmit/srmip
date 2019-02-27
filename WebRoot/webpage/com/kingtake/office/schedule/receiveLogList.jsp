<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.jeecgframework.core.util.ResourceUtil,org.jeecgframework.web.system.pojo.base.TSUser" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<%
TSUser user = ResourceUtil.getSessionUserName();
String userId = user.getId();
String realName = user.getRealName();
%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<input type="hidden" id="userId" value="<%=userId%>">
<input type="hidden" id="realName" value="<%=realName%>">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <input type="hidden" id="scheduleId" value="${researchId}">
  <t:datagrid name="tOReceiveLogList" checkbox="true" fitColumns="true" 
     idField="id" fit="true"  actionUrl="tOResearchActivityController.do?logDatagrid&researchId=${researchId }" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送人id"  field="senderId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="发送人名称"  field="senderName"     width="120"></t:dgCol>
   <t:dgCol title="发送时间"  field="sendTime" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人id"  field="receiverId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="接收人名称"  field="receiverName"     width="120"></t:dgCol>
   <t:dgCol title="接收时间"  field="receiveTime"  formatter="yyyy-MM-dd hh:mm:ss"   width="120"></t:dgCol>
   <t:dgCol title="是否阅读"  field="receiveFlag" replace="否_0,是_1" width="120"></t:dgCol>
   <t:dgCol title="是否可删除" field="isDelete" hidden="true"></t:dgCol>
   <t:dgToolBar title="添加" funname="addReceive" icon="icon-add"></t:dgToolBar>
   <%-- <t:dgToolBar title="删除" funname="deleteRow" icon="icon-remove"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="发送" funname="sendSchedule" icon="icon-putout"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
    //给时间控件加上样式
    $("#tOHandoverListtb").find("input[name='handoverTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tOHandoverListtb").find("input[name='handoverTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tOHandoverListtb").find("input[name='recieveTime_begin']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
    $("#tOHandoverListtb").find("input[name='recieveTime_end']").attr("class","Wdate").attr("style","height:23px;width:100px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//双击查看方法
function dblClickDetail(rowIndex, rowData){
    var title = "查看交班材料信息";
    var url = "tOHandoverController.do?goUpdate&load=detail&id=" + rowData.id;
    var width = 700;
    var height = 350;
    createdetailwindow(title,url,width,height);
}
 
//导入
function ImportXls() {
  openuploadwin('Excel导入', 'tOHandoverController.do?upload', "tOHandoverList");
}

//导出
function ExportXls() {
  JeecgExcelExport("tOHandoverController.do?exportXls","tOHandoverList");
}

//模板下载
function ExportXlsByT() {
  JeecgExcelExport("tOHandoverController.do?exportXlsByT","tOHandoverList");
}

function getSelectedRows(){
   var rows = $("#tOHandoverList").datagrid("getChecked");
   return rows;
}

function addReceive(title,addurl,gname,width,height){
    var operateUrl = 'tOHolidayPlanController.do?goSelectOperator&mode=multiply';
    //打开选择人窗口
    var width = 640;
    var height = 180;
    var title = "选择接收人";
    var scheduleId = $("#scheduleId").val();
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
                submit(scheduleId,receiveIds,receiveNames);
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
                submit(scheduleId,userId,realName);
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
}

/* function addRow(userIds, realNames){
    var userId = $("#userId").val();
    var realName = $("#realName").val();
    var userIdArr = userIds.split(",");
    var userNamesArr = realNames.split(",");
    var rows = $("#tOReceiveLogList").datagrid("getRows");
    for(var i=0;i<userIdArr.length;i++){
        var uId = userIdArr[i];
        var uName = userNamesArr[i];
        if(!checkExist(uId,rows)){
         $("#tOReceiveLogList").datagrid('appendRow',{
           id:'logTmpId'+i,
           senderId:userId,
           senderName:realName,
           sendTime:'',
           receiverId:uId,
           receiverName:uName,
           receiveTime:'',
           receiveFlag:'0',
           isDelete:'1'
         });
        }
    }
}
 */
/* function deleteRow(){
    var delRows = [];
    var checkedRows = $("#tOReceiveLogList").datagrid("getChecked");
    if(checkedRows.length==0){
        tip("请选择一条记录进行删除！");
        return;
    }
    for(var i=checkedRows.length-1;i>=0;i--){
        if(checkedRows[i].isDelete=="1"){
          var index = $("#tOReceiveLogList").datagrid("getRowIndex",checkedRows[i]);
          $("#tOReceiveLogList").datagrid("deleteRow",index);
        }else{
            tip("已发送的记录不能删除！");
        }
    }
} */

/* function checkExist(userId,rows){
    var flag = false;
    for(var i=0;i<rows.length;i++){
        if(rows[i].receiverId==userId){
            flag = true;
            break;
        }
    }
    return flag;
} */

/* function selectReceiver(scheduleId){
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
} */

/* function sendSchedule(){
    var userIds = [];
    var userNames = [];
    var checkedRows = $("#tOReceiveLogList").datagrid("getChecked");
    if(checkedRows.length==0){
        var rows = $("#tOReceiveLogList").datagrid("getRows");
        for(var i=0;i<rows.length;i++){
            if(rows[i].isDelete=='1'){
            userIds.push(rows[i].receiverId);
            userNames.push(rows[i].receiverName);
            }
        }
    }else{
        for(var i=0;i<checkedRows.length;i++){
            if(checkedRows[i].isDelete=='1'){
            userIds.push(checkedRows[i].receiverId);
            userNames.push(checkedRows[i].receiverName);
            }
        }
    }
    if(userNames.length==0){
        tip("没有找到符合条件的发送人！");
        return ;
    }
    var receiveNames = userNames.join(",");
    var receiveIds = userIds.join(",");
    $.messager.confirm('确认','确认将此日程安排发送给['+receiveNames+']吗？',function(r){    
        if (r){    
            var scheduleId = $("#scheduleId").val();
            submit(scheduleId,receiveIds,receiveNames);
        }    
    });
} */

function submit(id, userId, realName) {
    var url = "tOScheduleController.do?doSubmit";
    $.ajax({
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
            if(d.success){
                $("#tOReceiveLogList").datagrid("reload");
            }
        }
    });
}

 </script>