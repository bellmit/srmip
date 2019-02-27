<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
  	<!-- title="到账信息查询及认领审核" onDblClick="goDetail"-->
    <t:datagrid name="tPmIncomeApplyList"  fitColumns="true"  actionUrl="tPmIncomeApplyController.do?datagridForDepartment&auditStatus=2,3" idField="id" fit="true"
      queryMode="group" >
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="到账凭证号/计划文件号" field="voucherNo"  queryMode="single" width="180" query="true" ></t:dgCol>
      <t:dgCol title="发票号/计划文件名" field="invoice_invoiceNum1" queryMode="single" width="180" query="true" ></t:dgCol>
      <t:dgCol title="认领金额" field="applyAmount" queryMode="single" width="120" align="right" formatter1="function(v){return '<font color=red >'+transformAmount(v)+'</font>';}"></t:dgCol>
      <t:dgCol title="申请人" field="applyUser" hidden="true" query="false" isLike="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款说明" field="incomeRemark" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="科研审核状态" field="auditStatus" replace="通过_2,未通过_3" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="财务财务状态" field="cwStatus"   replace="未审核_0,通过_1,不通过_2" width="120"></t:dgCol> 
      <t:dgCol title="审核意见" field="audit_Msg" hidden="true" queryMode="single" width="120" extendParams="formatter:auditStatusFormat,"></t:dgCol>
      <t:dgCol title="发送审核人" field="submit_username" hidden="true" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="发送时间" field="submit_time" hidden="true" formatter="yyyy-MM-dd"  queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="审核人" field="audit_username" hidden="false" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="审核时间" field="audit_time" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="立项依据" field="lxyj" hidden="false" width="100" formatter1="formatter_lxyj" codeDict="0,LXYJ" query="true" ></t:dgCol>  
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt title="查看明细" funname="goDetail(id, lxyj)" />
      
      <%--<t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeApplyController.do?goEdit&type=jg" funname="detail" width="1100" height="800"></t:dgToolBar>--%>
      <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
    </t:datagrid>
  </div>
</div>
<script type="text/javascript">
	function formatter_lxyj(value,data,index){
		return value=='1' ? '计划下达':'合同认领';
	}
	
	$(document).ready(function() {
		//$("#ddlRegType ").get(0).options[i].text == text
		var sel = $("select[name='lxyj']").get(0);
		sel.options.length=0;
		sel.options.add(new Option('---请选择---','0'));
		sel.options.add(new Option('计划下达','1'));
		sel.options.add(new Option('合同认领','2'));
		
		//var sel2 = $("select[name='auditStatus']").get(0);
		//sel2.options.remove(1); 
		//$("select[name='auditStatus']").val("1");
		
	});   

    //查看
    function goDetail(id, lxyj) {

        var boo = typeof (windowapi) == 'undefined';
        var jq = boo ? $ : W.$;
        var url = 'url:tPmIncomeApplyController.do?goEdit&load=detail&type=jg&lxyj='+lxyj+'&id='+id; 
        var json ={
                id : 'incomeApply',
                content : url,
                lock : true,
                width : 950,
                height : 550,
                title : "查看",
                opacity : 0.3,
                cache : false,
                cancelVal : '关闭',
                cancel : function(){}
            };
        if(!boo){
        	json.parent = windowapi;
        }
        jq.dialog(json);
     } 

    //提交审核
    function passAudit(id) {
        if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确定审核通过吗？", function() {
                doAudit(id,'pass');
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确定审核通过吗？", function() {
                doAudit(id,'pass');
            }, function() {
            },windowapi).zindex();
        }
    }
  
  //审核不通过
    function rejectAudit(id) {
        if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确定审核不通过吗？", function() {
                doAudit(id,'reject');
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确定审核不通过吗？", function() {
                doAudit(id,'reject');
            }, function() {
            },windowapi).zindex();
        }
    }
  
  //审核
  function doAudit(id,opt){
      $.ajax({
          url:'tPmIncomeApplyController.do?doAudit',
          type:'POST',
          dataType:'json',
          cache:false,
          data:{'id':id,"opt":opt},
          success:function(data){
             tip(data.msg);
             if(data.success){
                 reloadTable();
             }
          }
      });
  }
  
  //审批状态格式化
  function auditStatusFormat(value,row,index){
	 var fm = row.audit_Msg ? '<a href="#" onclick=showMsg("'+row.audit_Msg+'")>查看意见</a>' : "";
	 return fm;
  }
  
  //弹出消息
  function showMsg(msg){
	  $.messager.alert('意见',msg);
  }
</script>