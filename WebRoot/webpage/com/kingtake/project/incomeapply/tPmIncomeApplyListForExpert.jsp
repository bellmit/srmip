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
		 url:'tPmIncomeApplyController.do?getPropose&id='+id,
		 cache:false,
		 type:'GET',
		 dataType:'json',
		 success:function(data){
			 $.messager.alert('意见',data.msg);
		 }
	  });
}
</script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 120px;text-align:right;" >
                             审核状态：
            </span>
        <select id="auditStatus" name="auditStatus"  style="width: 120px; height:26px; vertical-align: middle;">
          <option value="">请选择</option>
          <option value="0">未提交</option>
          <option value="1">待审核</option>
          <option value="2">通过</option>
          <option value="3">未通过</option>
        </select>
      </span>
    </div>
</div>
<div class="easyui-layout" fit="true" style="height: 500px;">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmIncomeApplyList" onDblClick="goDetail" fitColumns="true" title="到账信息查询及认领" actionUrl="tPmIncomeApplyController.do?datagrid&projectId=${projectId}" idField="id" fit="true"
      queryMode="group">
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="到账凭证号" field="voucherNo" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="发票号" field="invoice_invoiceNum1" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="认领金额" field="applyAmount" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" queryMode="single" width="100"></t:dgCol>
      <t:dgCol title="申请人" field="applyUser" query="true" queryMode="single" width="80"></t:dgCol>
      <t:dgCol title="申请单位" field="applyDept"  queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款说明" field="incomeRemark" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="审核状态" field="auditStatus" extendParams="formatter:auditStatusFormat,"  width="80"></t:dgCol>
      <t:dgCol title="修改意见" field="msgText" hidden="true" width="80"></t:dgCol>
      <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="提交时间" field="submitTime" formatter="yyyy-MM-dd"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审核时间" field="auditTime" formatter="yyyy-MM-dd"  queryMode="group" width="120"></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeApplyController.do?goEdit" funname="detail" width="900" height="600"></t:dgToolBar>
    </t:datagrid>
  </div>
</div>

<script type="text/javascript">
    $(document).ready(function() {
        //给时间控件加上样式
        gridname="tPmIncomeApplyList";
        $("#tPmIncomeApplyListtb").find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
    });

    //导出
    function ExportXls() {
        JeecgExcelExport("tPmIncomeApplyController.do?exportXls", "tPmIncomeApplyList");
    }

    function goAdd() {
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&projectId=${projectId}',
                lock : true,
                title : '新增',
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        } else {
            W.$.dialog({
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&projectId=${projectId}',
                lock : true,
                title : '新增',
                width : 950,
                height : window.top.document.body.offsetHeight-100,
                parent : windowapi,
                opacity : 0.3,
                cache : false,
                ok : function() {
                    iframe = this.iframe.contentWindow;
                    saveObj();
                    return false;
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }

    //编辑
    function goEdit(id,index) {
        var dialog;
        if (typeof (windowapi) == 'undefined') {
            dialog = $.dialog({
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&id=' + id,
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
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&id=' + id,
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
        var rows = $("#tPmIncomeApplyList").datagrid("getRows");
        var auditStatus = rows[index].auditStatus;
        if(auditStatus=='3'){
           var msgText = rows[index].msgText;
           dialog.button({
               name:'查看修改意见',
               callback:function(){
                   this.iframe.contentWindow.openDialog(msgText);
                   return false;
               }
           });
        }
    }

    //查看
    function goDetail(rowIndex,rowData) {
        var id = rowData.id;
        if (typeof (windowapi) == 'undefined') {
            $.dialog({
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&id=' + id,
                lock : true,
                width : 950,
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
                id : 'incomeApply',
                content : 'url:tPmIncomeApplyController.do?goEdit&load=detail&id=' + id,
                lock : true,
                width : 950,
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
  
  //审核
  function doAudit(id,userId,realname,deptId){
      $.ajax({
          url:'tPmIncomeApplyController.do?doAudit',
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
  
  
</script>