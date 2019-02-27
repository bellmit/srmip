<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
//审批状态格式化
function auditStatusFormat(value,row,index){
	  if(value=="0"){
		  return "未提交";
	  }else if(value=="1"){
		  return "待审核";
	  }else if(value=="2"){
		  return "通过";
	  }else if(value=="3"){
		  return '未通过<a href="#" style="cursor: pointer;color:red;" onclick=showMsg("'+row.id+'")>[查看意见]</a>';
    }
}

//弹出消息
function showMsg(id){
	  $.ajax({
		 url:'tPmIncomePlanController.do?getPropose&id='+id,
		 cache:false,
		 type:'GET',
		 dataType:'json',
		 success:function(data){
			 $.messager.alert('意见',data.msg);
		 }
	  });
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmIncomePlanList" onDblClick="goDetail" fitColumns="true" title="计划下达及审核" actionUrl="tPmIncomePlanController.do?datagridNew&projectId=${projectId}&stage=${stage}" idField="id" fit="true"
      queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="文件号" field="documentNo" queryMode="single" query="true" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="文件名" field="documentName" queryMode="single" width="120" query="true" isLike="true"></t:dgCol>
      <t:dgCol title="发文时间" field="documentTime" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="计划下达金额" field="planAmount" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来源经费科目" field="fundsSubject" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="科研审核标志" field="approvalStatus" extendParams="formatter:auditStatusFormat," queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="财务审核标志" field="cwStatus"  replace="未审核_0,通过_1,不通过_2" queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
   	  <t:dgToolBar title="查看" icon="icon-search" onclick="detail()" width="1000" height="500"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
        debugger;
    });
    
    function detail(){
    	var row = $('#tPmIncomePlanList').datagrid('getSelected'); 
    	var id = row.id;
    	if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'tPmIncomePlanList',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&type=jg&lxyj=1&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-300,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                id : 'tPmIncomePlanList',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&type=jg&lxyj=1&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-300,
                title : "查看",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
    }
    
    //查看
    function goDetail(rowIndex,rowData) {
        var id = rowData.id;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'tPmIncomePlanList',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&type=jg&lxyj=1&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            W.$.dialog({
                id : 'tPmIncomePlanList',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&type=jg&lxyj=1&id=' + id,
                lock : true,
                width : 1000,
                height : window.top.document.body.offsetHeight-100,
                title : "查看",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
     } 
    
  	//提交审核
    function submitAudit(id) {
    	$.ajax({
            url:'tPmIncomePlanController.do?beforeApproval',
            type:'POST',
            dataType:'json',
            cache:'false',
            data:{'id':id},
            success:function(data){
               if(!data.success){
            	   tip(data.msg);
               }               
               if(data.success){
            	   var url = 'tPmDeclareController.do?goSelectOperator2';
                   if(typeof(windowapi) == 'undefined'){
                       $.dialog.confirm("确定提交审核吗？", function() {
                           openOperatorDialog("选择审批人", url, 640, 180,id);
                       }, function() {
                       }).zindex();
                   }else{
                       W.$.dialog.confirm("确定提交审核吗？", function() {
                           openOperatorDialog("选择审批人", url, 640, 180,id);
                       }, function() {
                       },windowapi).zindex();
                   }
               }
            }
        });        
    }
  	
  //打开选择人窗口
    function openOperatorDialog(title, url, width, height,id) {
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
                    doAudit(id,userId,realname,deptId);
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
                    doAudit(id,userId,realname,deptId);
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }
  
    //提交审核
    function doAudit(id,userId,realname,deptId){
        $.ajax({
            url:'tPmIncomePlanController.do?doAudit',
            type:'POST',
            dataType:'json',
            cache:'false',
            data:{'id':id,"opt":'submit','userId':userId,'realname':realname,'deptId':deptId},
            success:function(data){
               tip(data.msg);
               if(data.success){
                   reloadTable();
               }
            }
        });
    }
    
    //审核通过
    function passAudit(id) {
        if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确定审核通过吗？", function() {
            	pass(id,'pass');
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确定审核通过吗？", function() {
            	pass(id,'pass');
            }, function() {
            },windowapi).zindex();
        }
    }
    
  //审核通过
    function pass(id, opt) {
        $.ajax({
            url : 'tPmIncomePlanController.do?doAudit',
            type : 'GET',
            dataType : 'json',
            cache : 'false',
            data : {
                'id' : id,
                "opt" : opt
            },
            success : function(data) {
                tip(data.msg);
                if (data.success) {
                    reloadTable();
                }
            }
        });
    }
  
    //审核不通过
    function rejectAudit(id) {
        if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确定审核不通过吗？", function() {
                openMsgDialog(id);
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确定审核不通过吗？", function() {
                openMsgDialog(id);
            }, function() {
            },windowapi).zindex();
        }
    }
  
    //打开弹窗填写修改意见
    function openMsgDialog(id){
        var url = "tPmIncomePlanController.do?goPropose&id="+id;
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
                      url : 'tPmIncomePlanController.do?doPropose',
                      data : {
                          id : id,
                          msgText : msgText
                      },
                      type : 'post',
                      dataType:'json',
                      success : function(data) {
                          tip(data.msg);
                          reloadTable();
                      }
                  });
              },
              cancel : function() {
  	           reloadTable();
              },
          }).zindex();
      }
  
    //填写经费构成
    function jffj(id) {
        var dialog;
        if (typeof (windowapi) == 'undefined') {
            dialog = $.dialog({
                id : 'incomePlan',
                content : 'url:tPmIncomePlanController.do?goEdit&stage=jffj&id=' + id,
                lock : true,
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                title : "修改",
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        } else {
            dialog =  W.$.dialog({
                id : 'incomePlan',
                content : 'url:tPmIncomePlanController.do?goEdit&stage=jffj&id=' + id,
                lock : true,
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                title : "修改",
                opacity : 0.3,
                parent : windowapi,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancelVal : '关闭',
                cancel : function() {
                }
            });
        }
    }
</script>