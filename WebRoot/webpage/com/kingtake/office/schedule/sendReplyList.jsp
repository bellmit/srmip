<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<c:if test="${type eq 'cal' }">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</c:if>
  <input id=scheduleId type="hidden" value="${scheduleId }">
  <div class="easyui-layout" fit="true">
  <div data-options="region:'north',split:true" style="height:200px;" >
  <table id="sendList"></table>
  </div>
  <div data-options="region:'center'">
  <table id="responseList"></table>
  <%-- <t:datagrid name="responseList" checkbox="true" fitColumns="true" title='回复记录' 
     idField="id" fit="true"  actionUrl="tOScheduleController.do?responseList&id=${scheduleId}" queryMode="group"  >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="回复人id"  field="resUserId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="回复人"  field="resUserName" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="回复时间" field="resTime" hidden="true" formatter="yyyy-MM-dd hh:mm:ss"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="回复"  field="resContent" width="120" ></t:dgCol>
   <t:dgToolBar title="回复" icon="icon-redo" funname="goResponse"></t:dgToolBar>  
  </t:datagrid>
  </div> --%>
  </div>
  </div>
 <script type="text/javascript">
 $(function(){
        var scheduleId = $("#scheduleId").val();
        $('#sendList').datagrid({
                                idField : 'id',
                                title : '发送记录',
                                url : 'tOResearchActivityController.do?logDatagrid&researchId='+scheduleId+'&field=id,receiverId,receiverName,receiveTime,receiveFlag,isDelete,',
                                striped : true,
                                fit : true,
                                loadMsg : '数据加载中...',
                                pageSize : 10,
                                pagination : true,
                                pageList : [ 10, 20, 30 ],
                                sortOrder : 'asc',
                                rownumbers : true,
                                singleSelect : false,
                                fitColumns : true,
                                showFooter : true,
                                toolbar: [{
                            		iconCls: 'icon-redo',
                            		text : '发送',
                            		handler: function(){
                            		    goViewReceiveList();
                            		}
                            	}],
                                columns : [ [ {
                                    field : 'id',
                                    title : '主键',
                                    width : 120,
                                    hidden : true,
                                    sortable : true
                                }, {
                                    field : 'receiverId',
                                    title : '接收人id',
                                    width : 120,
                                    hidden : true,
                                    sortable : true
                                }, {
                                    field : 'receiverName',
                                    title : '接收人名称',
                                    width : 120,
                                    sortable : true
                                }, {
                                    field : 'receiveTime',
                                    title : '接收时间',
                                    width : 120,
                                    sortable : true,
                                    hidden : true,
                                    formatter : function(value, rec, index) {
                                        return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                                    }
                                }, {
                                    field : 'receiveFlag',
                                    title : '是否阅读',
                                    width : 120,
                                    sortable : true,
                                    formatter : function(value, rec, index) {
                                        var valArray = value.split(",");
                                        if (valArray.length > 1) {
                                            var checkboxValue = "";
                                            for (var k = 0; k < valArray.length; k++) {
                                                if (valArray[k] == '0') {
                                                    checkboxValue = checkboxValue + '否' + ','
                                                }
                                                if (valArray[k] == '1') {
                                                    checkboxValue = checkboxValue + '是' + ','
                                                }
                                            }
                                            return checkboxValue.substring(0, checkboxValue.length - 1);
                                        } else {
                                            if (value == '0') {
                                                return '否'
                                            }
                                            if (value == '1') {
                                                return '是'
                                            } else {
                                                return value
                                            }
                                        }
                                    }
                                } ] ],
                                onLoadSuccess : function(data) {
                                    $("#sendList").datagrid("clearSelections");
                                },
                                onClickRow : function(rowIndex, rowData) {
                                    rowid = rowData.id;
                                    gridname = 'sendList';
                                }
                            });
            $('#sendList').datagrid('getPager').pagination({
                beforePageText : '',
                afterPageText : '/{pages}',
                displayMsg : '',
                showPageList : true,
                showRefresh : true
            });
            $('#sendList').datagrid('getPager').pagination({
                onBeforeRefresh : function(pageNumber, pageSize) {
                    $(this).pagination('loading');
                    $(this).pagination('loaded');
                }
            });
            
            $('#responseList').datagrid({
                idField : 'id',
                title : '留言记录',
                url : 'tOScheduleController.do?responseList&id='+scheduleId+'&field=id,resUserId,resUserName,resTime,resContent,',
                striped : true,
                fit : true,
                loadMsg : '数据加载中...',
                pageSize : 10,
                pagination : true,
                pageList : [ 10, 20, 30 ],
                sortOrder : 'asc',
                rownumbers : true,
                singleSelect : false,
                fitColumns : true,
                showFooter : true,
                toolbar: [{
            		iconCls: 'icon-redo',
            		text : '留言',
            		handler: function(){
            		    goResponse();
            		}
            	}],
                columns : [ [ {
                    field : 'id',
                    title : '主键',
                    width : 120,
                    hidden : true,
                    sortable : true
                }, {
                    field : 'resUserId',
                    title : '回复人id',
                    width : 120,
                    hidden : true,
                    sortable : true
                }, {
                    field : 'resUserName',
                    title : '回复人',
                    width : 120,
                    hidden : true,
                    sortable : true
                }, {
                    field : 'resTime',
                    title : '回复时间',
                    width : 120,
                    sortable : true,
                    hidden : true,
                    formatter : function(value, rec, index) {
                        return new Date().format('yyyy-MM-dd hh:mm:ss', value);
                    }
                }, {
                    field : 'resContent',
                    title : '内容',
                    width : 120,
                    sortable : true,
                    formatter : function(value, rec, index) {
                        var content = value+"("+rec.resUserName+","+new Date().format('yyyy-MM-dd hh:mm:ss', rec.resTime)+")";
                        var res = '<span title="'+content+'">'+content+'</span>';
                        return res;
                    }
                } ] ],
                onLoadSuccess : function(data) {
                    $("#responseList").datagrid("clearSelections");
                },
                onClickRow : function(rowIndex, rowData) {
                    rowid = rowData.id;
                    gridname = 'responseList';
                }
            });
$('#responseList').datagrid('getPager').pagination({
beforePageText : '',
afterPageText : '/{pages}',
displayMsg : '',
showPageList : true,
showRefresh : true
});
$('#responseList').datagrid('getPager').pagination({
onBeforeRefresh : function(pageNumber, pageSize) {
    $(this).pagination('loading');
    $(this).pagination('loaded');
}
});
        });

        //进行回复
        function goResponse() {
            var id = $("#scheduleId").val();
            var url = 'tOScheduleController.do?goResponse';
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
        function doResponse(id, resContent) {
            $.ajax({
                url : 'tOScheduleController.do?doResponse&type=sender',
                data : {
                    id : id,
                    resContent : resContent
                },
                cache : false,
                type : 'POST',
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    if (data.success) {
                        $("#responseList").datagrid("reload");
                    }
                }
            });
        }
        
        //发送
        function goViewReceiveList(){
            var id = $("#scheduleId").val();
            var title = "查看发送记录";
            var url = "tOScheduleController.do?goReceiveLogList&researchId="+id+"&sendType=schedule";
            var width = 700;
            var height = 400;
            if(typeof(windowapi) == 'undefined'){
        		$.dialog({
        			content: 'url:'+url,
        			lock : true,
        			width:width,
        			height: height,
        			title:title,
        			opacity : 0.3,
        			fixed:true,
        			cache:false, 
        		    cancelVal: '关闭',
        		    cancel: function(){
        		        $("#sendList").datagrid("reload");
        		    }
        		}).zindex();
        	}else{
        		W.$.dialog({
        			content: 'url:'+url,
        			lock : true,
        			width:width,
        			height: height,
        			parent:windowapi,
        			title:title,
        			opacity : 0.3,
        			fixed:true,
        			cache:false, 
        		    cancelVal: '关闭',
        		    cancel: function(){
        		        $("#sendList").datagrid("reload");
        		    }
        		}).zindex();
        	}
        }
    </script>