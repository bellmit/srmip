<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;"> <!-- title="到账信息查询及认领审核" onDblClick="goDetail"  -->
    <t:datagrid name="tPmIncomeApplyList"  fitColumns="true" actionUrl="tPmIncomeApplyController.do?datagridForDepartment&auditStatus=1" idField="id" fit="true"
      queryMode="group"> 
      <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="关联项目id" field="projectId" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="到账凭证号/计划文件号" field="voucherNo" queryMode="single" width="180" query="true" ></t:dgCol>
      <t:dgCol title="发票号/计划文件名" field="invoice_invoiceNum1" queryMode="single" width="180" query="true" ></t:dgCol>
      <t:dgCol title="总金额(元)" field="applyAmount" queryMode="single" width="120" align="right" formatter1="function(v){return '<font color=red >'+transformAmount(v)+'</font>';}"></t:dgCol>
      <t:dgCol title="来款金额" field="incomeAmount" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款时间" field="incomeTime" formatter="yyyy-MM-dd" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="来款说明" field="incomeRemark" hidden="true" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="科研审核状态" field="auditStatus" replace="待审核_1" queryMode="single"  width="120"></t:dgCol> <%--query="true" codeDict="0,auditStatus"--%>
      <t:dgCol title="财务审核状态" field="cwStatus" replace="未审核_0,通过_1,不通过_2"  width="120"></t:dgCol> 
      <t:dgCol title="发送审核建议" field="msgText" hidden="true" width="180" extendParams="formatter:auditStatusFormat,"></t:dgCol>
      <t:dgCol title="审核人" field="submit_username" hidden="false" queryMode="group" width="100"></t:dgCol>
      <t:dgCol title="发送时间" field="submit_time" hidden="true" formatter="yyyy-MM-dd" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="立项依据" field="lxyj" hidden="false" width="100" formatter1="formatter_lxyj" codeDict="0,LXYJ" query="true" ></t:dgCol> 
      <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="auditStatus#eq#1" title="审核" funname="doShenHe(id,lxyj)" />
      
      <%-- <t:dgToolBar title="编辑" icon="icon-edit" url="tPmIncomeApplyController.do?goEdit&type=jg" funname="update" width="1100" height="500"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="查看" icon="icon-search" url="tPmIncomeApplyController.do?goEdit&type=jg" funname="detail" width="1100" height="500"></t:dgToolBar> --%>
      <%-- <t:dgToolBar title="审核" icon="icon-search" onclick="doShenHe()" funname="detail" width="1100" height="500"></t:dgToolBar> --%>
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
    
    //=============lijun审核合同类型的项目认领
    function doShenHe(id,lxyj) {
    	//debugger;
    	/* var checked = $("#tPmIncomeApplyList").datagrid("getSelections");
    	if(checked == null || checked.length==0) {
    		alert("请选择要审核的记录！");
    		return false;
    	}
    	var id = checked[0].id; */
    	
    	var url = 'url:tPmIncomeApplyController.do?goEdit&load=detail&type=jg&lxyj='+lxyj+'&id='+id;
    	
    	var jq = typeof (windowapi) == 'undefined' ? $ : W.$ ;
    	
    	jq.dialog({
             id : 'incomeApply',
             content : url,
             lock : true,
             width : 950,
             height : 550,
             title : "审核",
             opacity : 0.3,
             cache : false,
             button:[
            	{
            		focus : true, name : '通过', callback : function() { passAudit(id, lxyj); }
    			},
    			{
	     			name : '不通过', callback : function() { rejectAudit(id, lxyj); },
    			}
             ]
         });
    }
    
    /* //审核前检查预留金额模块填写了没
    function auditCheck(id){
    	$.ajax({
            url : 'tPmIncomeApplyController.do?beforeAudit',
            data : {
                id : id
            },
            type : 'post',
            dataType:'json',
            success : function(data) {                
                if(data.msg == "ok"){
                	passAudit(id);
                }else{
                	tip(data.msg);
                }
            }
        });
    } */
    
  //提交审核
    function passAudit(id, lxyj) {
    	var boo = typeof(windowapi) == 'undefined';
    	var jq = boo ? $ : W.$;
    	
        $.dialog.confirm("确定审核通过吗？", function() {
            doAudit(id, 'pass', lxyj);
        }, function(){}, (boo ? windowapi : null) ).zindex();
    }
  
  //审核不通过
    function rejectAudit(id,lxyj) {
    	var boo = typeof(windowapi) == 'undefined';
    	var jq = boo ? $ : W.$;

    	$.dialog.confirm("确定审核不通过吗？", function() {
            openMsgDialog(id, lxyj);
        }, function(){}, (boo ? windowapi : null) ).zindex();
    }
  
  //打开弹窗填写修改意见
  function openMsgDialog(id, lxyj){
    	var url = "tPmIncomeApplyController.do?goPropose";
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
                    url : 'tPmIncomeApplyController.do?doPropose',
                    data : { id:id, msgText:msgText, lxyj:lxyj },
                    type : 'post',
                    dataType:'json',
                    success : function(data) {
                        tip(data.msg);
                        reloadTable();
                    }
                });
            },
            cancel : function() {
	           //reloadTable();
            },
        }).zindex();
    }

    //审核
    function doAudit(id, opt, lxyj) {
    	var url =  'tPmIncomeApplyController.do?doAudit';
        $.ajax({
            url : url,
            type : 'GET',
            dataType : 'json',
            cache : 'false',
            data : {
                id:id, opt:opt, lxyj:lxyj, aduitStatus:2
            },
            success : function(data) {
                tip(data.msg);
                if (data.success) {
                    reloadTable();
                }
            }
        });
    }
    
    //审批状态格式化
    function auditStatusFormat(value,row,index){
    	 var fm = row.msgText ? '<a href="#"  onclick=showMsg("'+row.msgText+'")>查看意见</a>' : "";
    	 return fm;
    }
    
    //弹出消息
    function showMsg(msg){
  	  $.messager.alert('意见',msg);
    }
</script>