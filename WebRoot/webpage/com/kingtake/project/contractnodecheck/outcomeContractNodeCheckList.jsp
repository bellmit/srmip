<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function auditStatusFormatter(value,row,index){
	var checkStatus = row['check_status'];
	var payStatus = row['pay_status'];
	if(checkStatus=='0'){
		return "未发送";
	}else if(checkStatus=="1"){
		return "已发送";
	}else if(checkStatus=="3"){
		return "节点考核未通过";
	}else if(checkStatus=="2" && payStatus=="0"){
		return "节点考核已通过";
	}else if(payStatus=="2"){
		return "支付审核未通过";
	}else if(payStatus=="1"){
		return "支付审核已通过";
	}
}
</script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding: 1px;">
    <t:datagrid name="tPmContractNodeCheckList" checkbox="false" fitColumns="true" title="出账合同节点考核"
      actionUrl="tPmContractNodeCheckController.do?outcomeContractNodeDatagrid&datagridType=${datagridType}&projectId=${projectId}" idField="id" fit="true" queryMode="group" pagination="false">
      <t:dgCol title="合同节点id" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="合同主键id" field="in_out_contractid" hidden="true" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="合同名称" field="contract_name" query="false" queryMode="single" width="120"></t:dgCol>
      <t:dgCol title="节点名称" field="node_name" queryMode="group" width="120"></t:dgCol>
      <t:dgCol title="完成时间" field="complete_date" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="验收状态" field="check_status" hidden="true" query="false" queryMode="group" codeDict="1,SPZT" width="80"></t:dgCol>
      <t:dgCol title="审核状态" field="audit_status" query="false" queryMode="group" width="120" extendParams="formatter:auditStatusFormatter,"></t:dgCol>
      <t:dgCol title="支付申请状态" field="pay_status" hidden="true" query="false" queryMode="group"  width="80"></t:dgCol>
      <t:dgCol title="合同验收主键" field="check_id" hidden="true" query="false" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="支付申请主键" field="pay_id" hidden="true" query="false" queryMode="group" width="80"></t:dgCol>

      <t:dgCol title="节点验收操作" field="opt" width="350" formatter="" frozenColumn="true"></t:dgCol>
      <t:dgFunOpt exp="check_status#eq#u" funname="addOrUpdateCheck(id,check_id)" title="录入完成信息"></t:dgFunOpt>
      <t:dgFunOpt exp="check_status#ne#u&&check_status#ne#1&&check_status#ne#2" funname="addOrUpdateCheck(id,check_id)" title="录入完成信息"></t:dgFunOpt>
      <t:dgFunOpt exp="pay_status#eq#2" funname="addOrUpdateCheck(id,check_id)" title="录入完成信息"></t:dgFunOpt>
      <t:dgFunOpt exp="pay_status#eq#2" funname="submitPayApply(check_id)" title="发送支付申请"></t:dgFunOpt>
      <t:dgFunOpt exp="check_status#ne#u" funname="sendCheckAppr(check_id, check_status)" title="节点考核"></t:dgFunOpt>
      <t:dgFunOpt funname="exportWord(id,check_id)" title="导出合同节点考核审批表"></t:dgFunOpt>
      <t:dgToolBar title="查看合同信息" icon="icon-search" url="tPmOutcomeContractApprController.do?goUpdateTab&load=detail&node=false" funname="detailContract" height="600" width="750"></t:dgToolBar>
      <t:dgToolBar title="查看合同节点" icon="icon-search" url="tPmContractNodeController.do?goUpdate" funname="detail" width="600" height="380"></t:dgToolBar>

    </t:datagrid>
    <input id="tipMsg" type="hidden" value="" />
  </div>
</div>
<script src="webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
	//录入或更新合同验收
	function addOrUpdateCheck(contractNodeId,checkId){
		var title = "录入完成信息";
		var gname = "tPmContractNodeCheckList";
		var width = 700;
		var height ='100%';
		var dialogId = "apprInfo";
		if(checkId == ""){
			//尚未生成表单
			var url = "tPmContractNodeCheckController.do?goUpateOutContractNodeCheck&contractNodeId=" + contractNodeId;
			addFun(title, url, gname, width, height, dialogId);
		}else{
			title = "编辑完成信息";
			var judgeUrl = "tPmContractNodeCheckController.do?updateFlag";
			var updateUrl = "tPmContractNodeCheckController.do?goUpateOutContractNodeCheck";
			var unEditUrl = updateUrl + "&load=detail&tip=true";
			judgeUpdate(title,width,height,checkId,judgeUrl,updateUrl,unEditUrl,dialogId);
		}
	}
	
	//审核tab页
	function sendCheckAppr(checkId, finishFlag){
		var title = "审核";
		var url = 'tPmApprLogsController.do?goApprTab&edit=false' + 
				'&apprId=' + checkId +
				'&apprType=<%=ApprovalConstant.APPR_TYPE_NODECHECK%>';
		var width = 900;
		var height = 500;
		var dialogId = "apprInfo";
		
		if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
			url += '&send=false&tip=true';
			$("#tipMsg").val("合同节点验收报告审核流程已完成，不能再发送审核");
		}else if(finishFlag == '<%=ApprovalConstant.APPRSTATUS_REBUT%>') {
            url += '&tip=true';
            $("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑合同节点验收信息！");
        }

		sendApprDialog(title, url, width, height, checkId, finishFlag, '<%=ApprovalConstant.APPR_TYPE_NODECHECK%>');
    }
	
    //查看合同信息
    function detailContract(title, url, id, width, height) {
        var rowsData = $('#' + id).datagrid('getSelections');

        if (!rowsData || rowsData.length == 0) {
            tip('请选择查看项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再查看');
            return;
        }
        url += '&load=detail&id=' + rowsData[0].in_out_contractid;
        tabDetailDialog(title, url, width, height);
    }

    $(document).ready(
            function() {
                //给时间控件加上样式
                $("#tPmIncomeContractApprListtb").find("input[name='startTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='startTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='endTime_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='endTime_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='createDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='createDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='updateDate_begin']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
                $("#tPmIncomeContractApprListtb").find("input[name='updateDate_end']").attr("class", "Wdate").attr(
                        "style", "height:20px;width:90px;").click(function() {
                    WdatePicker({
                        dateFmt : 'yyyy-MM-dd'
                    });
                });
            });

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tPmIncomeContractApprController.do?upload', "tPmIncomeContractApprList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tPmIncomeContractApprController.do?exportXls", "tPmIncomeContractApprList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tPmIncomeContractApprController.do?exportXlsByT", "tPmIncomeContractApprList");
    }

    //支付申请
    function payApply(id, checkId) {
        var url1 = 'tPmContractNodeCheckController.do?updateOperateStatus';
        var url2 = url1 + '2';
        updateFinishFlag(checkId, url1, url2);
    }
    
    //导出合同节点考核审批表WORD
    function exportWord(contractNodeId, checkId) {
        JeecgExcelExport("tPmContractNodeCheckController.do?exportWord&contractNodeId=" + contractNodeId + "&checkId=" + checkId, "tPmContractNodeCheckList");
    }
    
  //提交支付申请
    function submitPayApply(id) {
        var url = 'tPmDeclareController.do?goSelectOperator2';
        if(typeof(windowapi) == 'undefined'){
            $.dialog.confirm("确定修改完毕，重新提交支付申请吗？", function() {
                openOperatorDialog("选择审批人", url, 640, 180,id);
            }, function() {
            }).zindex();
        }else{
            W.$.dialog.confirm("确定修改完毕，重新提交支付申请吗？", function() {
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
                    doSend(id,userId,realname,deptId);
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
                    doSend(id,userId,realname,deptId);
                },
                cancelVal : '关闭',
                cancel : true
            }).zindex();
        }
    }
  
  //审核
  function doSend(id,userId,realname,deptId){
      $.ajax({
          url:'tPmContractNodeCheckController.do?doSendPayApply',
          type:'POST',
          dataType:'json',
          cache:'false',
          data:{'id':id,'userId':userId,'realname':realname,'deptId':deptId},
          success:function(data){
             tip(data.msg);
             if(data.success){
                 reloadTable();
             }
          }
      });
  }

</script>
