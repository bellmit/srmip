<%@page import="com.kingtake.common.constant.ApprovalConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">

function dblclick(rowIndex,rowData){
    var url = "tPmProjectController.do?goEditForResearchGroup";
    url += '&load=detail&id='+rowData.id;
	createdetailwindow("查看",url,'100%','100%');
}
</script>

<input type="hidden" id="applying" value="${applying }"/>
<input type="hidden" id="executing" value="${executing }"/>
<input type="hidden" id="yes" value="${yes }"/>
<input type="hidden" id="no" value="${no }"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmProjectList" fitColumns="false" title="项目列表" onDblClick="dblclick" pageSize="20" pageList="[20,40,60]"
  	actionUrl="tPmProjectController.do?datagridState&approvalStatus=0&lxStatus=0" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc" view="scrollview">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="100"></t:dgCol>
   <%-- <t:dgCol title="项目编号"  field="projectNo" queryMode="group"  width="150"></t:dgCol> --%>
   <t:dgCol title="项目申请号"  field="cxm" queryMode="single" width="200"></t:dgCol>
   <t:dgCol title="项目名称"  field="projectName" query="true" isLike="true" width="120"></t:dgCol>
   <%-- <t:dgCol title="责任部门" field="dutyDepart_departname" query="true" isLike="true" queryMode="single" width="120"></t:dgCol> --%>
   <t:dgCol title="项目类型" field="xmlx" query="true" isLike="true" queryMode="single" codeDict="0,XMLX" width="120"></t:dgCol>
   <t:dgCol title="承研部门"  field="devDepart_departname" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>       
   <t:dgCol title="部门类型"  field="bmlx" hidden="true" width="120"></t:dgCol> 
   <t:dgCol title="申报人"  field="projectManager"  query="true"  isLike="true" queryMode="single"  width="80"></t:dgCol>
   <%-- <t:dgCol title="总经费" field="allFee" queryMode="group" width="120" align="right" extendParams="formatter:formatCurrency,"></t:dgCol> 
   <t:dgCol title="来源单位" field="sourceUnit" hidden="flase" isLike="true" queryMode="single"  width="120"></t:dgCol>--%>
   <t:dgCol title="项目来源"  field="xmly" codeDict="0,XMLY" queryMode="group"  width="100"></t:dgCol>
<%--    <t:dgCol title="项目类型id"  field="projectType_id" hidden="true"  width="100"></t:dgCol>
   <t:dgCol title="项目类型"  field="projectType_projectTypeName" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="经费类型" field="feeType_fundsName" hidden="false" queryMode="group" width="120"></t:dgCol> --%>
   <t:dgCol title="项目类别母项"  field="xmml" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="项目类别"  field="xmlbStr" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="经费类型"  field="jflxStr" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="起始日期"  field="beginDate" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>
   <t:dgCol title="截止日期"  field="endDate" formatter="yyyy-MM-dd" width="100" align="center"></t:dgCol>      
   <%-- <t:dgCol title="外来编号" field="outsideNo" hidden="false" queryMode="single" width="120"></t:dgCol> --%>
   <t:dgCol title="秘密等级" field="secretDegree" hidden="false" queryMode="group" codeDict="0,XMMJ" width="120"></t:dgCol>
  <%--  <t:dgCol title="项目状态"  field="projectStatus" replace="申请中_01,申报书提交_02,审查报批_03,正在执行_04,暂停执行_05,已验收_06,已结题_07,未立项_99,完成状态_98,立项_97" width="70"></t:dgCol> --%>
   <%-- <t:dgCol title="母项目编码" field="parentPmProject_projectNo" hidden="false" queryMode="group" width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="会计编码"  field="accountingCode"    queryMode="group"  width="100"></t:dgCol> --%>
   <%-- <t:dgCol title="确认状态"  field="approvalStatus" codeDict="1,LXZT" width="70"></t:dgCol> --%>
   <t:dgCol title="审核状态"  field="auditStatus" codeDict="1,SPZT" width="70"></t:dgCol>
   <%-- <t:dgCol title="上报状态"  field="reportState" extendParams="formatter:formatreportState," width="70"></t:dgCol> --%>
   <t:dgCol title="项目简介"  field="projectAbstract"  queryMode="group" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="150" frozenColumn="true"></t:dgCol>
   <%-- <t:dgFunOpt exp="approvalStatus#ne#1&&approvalStatus#ne#2" funname="applyPass(id)" title="确认" ></t:dgFunOpt> --%>
   <t:dgFunOpt exp="approvalStatus#ne#1&&approvalStatus#ne#2" funname="goSend(id,auditStatus,bmlx)" title="发送审批" ></t:dgFunOpt>
   <%-- <t:dgFunOpt exp="approvalStatus#ne#1&&approvalStatus#ne#2" funname="refuse(id)" title="驳回" ></t:dgFunOpt> --%>
   <t:dgFunOpt exp="approvalStatus#eq#2" funname="viewMsgText(id)" title="查看修改意见"></t:dgFunOpt>
   <%-- <t:dgFunOpt exp="approvalStatus#ne#0" funname="cancelPass(id)" title="取消"></t:dgFunOpt> --%>
   <%-- <t:dgFunOpt exp="approvalStatus#ne\#${yes}" funname="approval(id,projectNo)" title="立项"></t:dgFunOpt> 
   <t:dgFunOpt exp="approvalStatus#ne\#${no}" funname="noApproval(id)" title="不立项"></t:dgFunOpt>--%>
   <t:dgToolBar title="增加项目" icon="icon-edit" funname="assign" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" funname="goEdit" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmProjectController.do?goEditForResearchGroup" 
   	funname="detail" width="100%" height="100%"></t:dgToolBar>
   	<%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportSendXlsByT"></t:dgToolBar>
   	<t:dgToolBar title="导入" icon="icon-put" funname="ImportSendXls"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript" src="webpage/com/kingtake/project/manage/addTab.js"></script>
 <script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>	
 <script type="text/javascript" src="webpage/common/util.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//给时间控件加上样式
	$("#tPmProjectListtb").find("input[name='beginDate_begin']")
		.attr("class","Wdate").attr("style","height:20px;width:90px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmProjectListtb").find("input[name='beginDate_end']")
		.attr("class","Wdate").attr("style","height:20px;width:90px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});

$(function(){
	var input = $("#tPmProjectListtb input[name='projectType.projectTypeName']");
	input.combotree({
		width : 180,
		url : 'tPmProjectController.do?getProjectType',
		valueField : 'id',
		textField : 'projectTypeName'
	});
});

function formatStatus(value){
	if(value == $("#executing").val()){
		return "执行中";
	}
	return "申请中";
}

function formatreportState(value){
	if(value == "0"){
		return "未生效";
	}else if(value == "1"){
		return "已上报";
	}
	return "";
}

function assign(){
	$.dialog({
		content: 'url:tPmProjectController.do?goAdd&assignFlag=1&entryType=JG&type=0',
		lock : true,
		width : window.top.document.body.offsetWidth,
		height : window.top.document.body.offsetHeight-100,
        left : '0%',
        top : '0%',
		title:"项目信息",
		opacity : 0.3,
		cache:false,
	    cancelVal: '关闭',
	    cancel: function(){
	    	reloadtPmProjectList();
	    }
	});
}

function goEdit(){
	var rows = $("#tPmProjectList").datagrid("getSelections");
    if(rows.length!=1){
        tip('请选择一个项目进行编辑！');
        return false;
    }
        var projectName = rows[0].projectName;
        var id = rows[0].id;
        var url = "tPmProjectController.do?goEditForResearchGroup&id="+id;
        addTab('编辑项目['+projectName+']', url, 'default');
}

function applyPass(id,index){
    var rows = $("#tPmProjectList").datagrid("getRows");
    var projectType = rows[index].projectType_id;
    if(projectType==""){
        $.messager.alert('警告','项目类型尚未指定，不能确认！');  
        return false;
    }
	$.messager.confirm('确认','您确认将该项目锁定吗？',function(r){    
	    if (r){    
	    	changeState(id, '1');  
	    }    
	});
}

function goSend(id,auditStatus,bmlx,index){
    var rows = $("#tPmProjectList").datagrid("getRows");
    var xmlbStr = rows[index].xmlbStr;
    if(xmlbStr==""){
        $.messager.alert('警告','项目类别尚未指定，不能确认！');  
        return false;
    }
    if(auditStatus==0){
    	$.messager.confirm('确认','您确认将该项目发送审核吗？',function(r){    
    	    if (r){    
    	    	//changeState(id, '1');
    	    	sendStart(id,auditStatus,bmlx);
    	    }    
    	});
    }else{
    	sendStart(id,auditStatus,bmlx);
    }
	
}

function sendStart(id, auditStatus,bmlx){
	var title = "审核";
	var url = 'tPmApprLogsController.do?goApprTab&load=detail' + 
			'&apprId=' + id +
			'&bmlx=' + bmlx +
			'&apprType=<%=ApprovalConstant.APPR_TYPE_XM%>';
	var width = 900;
	var height = window.top.document.body.offsetHeight-100;
	
	if(auditStatus == '<%=ApprovalConstant.APPRSTATUS_FINISH%>'){
		url += '&send=false&tip=true';
		$("#tipMsg").val("审核流程已完成，不能再发送审核");
	}else if(auditStatus == '<%=ApprovalConstant.APPRSTATUS_REBUT%>'){
		url += '&tip=true';
		$("#tipMsg").val("流程被驳回，请确定修改后再发送，发送后将不能再编辑审核信息！");
	}
	sendApprDialog(title, url, width, height, id,auditStatus,'<%=ApprovalConstant.APPR_TYPE_XM%>' );
}

function cancelPass(id){
	$.messager.confirm('确认','您确认将该项目取消锁定吗？',function(r){    
	    if (r){    
	    	changeState(id, '0'); 
	    }    
	});
}

function changeState(id, state){
	$.ajax({
    	url:'tPmProjectController.do?applyPass',
    	type:'post',
    	data:{id:id, state:state},
    	success:function(result){
    		result = $.parseJSON(result);
    		$("#tPmProjectList").datagrid('reload');
    		tip(result.msg);
    	}
    });
}

function approval(id,projectNo){
  if(projectNo == null || projectNo == ""){
    $.dialog({
      content: 'url:tPmProjectController.do?goEditForResearchGroup&operCode=1&id='+id,//operCode为1表示是立项弹出的项目编辑界面，主要用于让机关录入项目编号信息
      lock : true,
      width : window.top.document.body.offsetWidth,
      height : window.top.document.body.offsetHeight-100,
      left : '0%',
      top : '0%',
      title:"项目信息-请补充<font color='red'>项目编号</font>",
      opacity : 0.3,
      ok:function(){
        iframe = this.iframe.contentWindow;
        saveObj();
        changeApprovalState(id, $("#yes").val()); 
        return false;
      },
      okVal:'确定',
      cache:false,
      cancelVal: '关闭',
      cancel: function(){
        reloadtPmProjectList();
      }
    });
  }else{
  	$.messager.confirm('确认','您确认将所选项目状态改为已立项吗？',function(r){    
  	    if (r){    
  	    	changeApprovalState(id, $("#yes").val());  
  	    }    
  	});
  }
}

function noApproval(id){
	$.messager.confirm('确认','您确认将所选项目状态改为未立项吗？',function(r){    
	    if (r){    
	    	changeApprovalState(id, $("#no").val());  
	    }    
	});
}

function changeApprovalState(id, state){
	$.ajax({
    	url:'tPmProjectController.do?changeApprovalState',
    	type:'post',
    	data:{id:id, state:state},
    	success:function(result){
    		result = $.parseJSON(result);
    		$("#tPmProjectList").datagrid('reload');
    		tip(result.msg);
    	}
    });
}

function refuse(id){
    $.dialog({
        content : 'url:tPmProjectController.do?goPropose&id='+id,
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
                url : 'tPmProjectController.do?doPropose',
                data : {
                    id : id,
                    msgText : msgText
                },
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    tip(data.msg);
                    if(data.success){
                        reloadTable();
                    }
                }
            });
        },
        cancel : function() {
        },
    }).zindex();
}


//查看修改意见
function viewMsgText(id){
    $.dialog({
        content : 'url:tPmProjectController.do?goPropose&id='+id,
        title : '提出修改意见',
        lock : true,
        opacity : 0.3,
        width : '450px',
        height : '120px',
        cancel : function() {
        },
    }).zindex();
}

//模板下载
function ExportSendXlsByT() {
    JeecgExcelExport("tPmProjectController.do?exportXlsByT", "tPmProjectList");
}

//导入
function ImportSendXls() {
    openuploadwin('Excel导入', 'tPmProjectController.do?upload&lxStatus=0', "tPmProjectList");
}

//导出项目信息给财务
function ExportXmlToProject() {
	$.ajax({
        url : 'tPmProjectController.do?exportXmlToProject',
        type : 'post',
        dataType : 'json',
        success : function(data) {
            tip(data.msg);
            if(data.success){
                reloadTable();
            }
        }
    });
}

//导出外协信息给财务
function ExportXmlToOutCome() {
	$.ajax({
        url : 'tPmProjectController.do?exportXmlToOutCome',
        type : 'post',
        dataType : 'json',
        success : function(data) {
            tip(data.msg);
            if(data.success){
                reloadTable();
            }
        }
    });
}

//导出外协合同支付节点信息给财务
function ExportXmlToOutComeNode() {
	$.ajax({
        url : 'tPmProjectController.do?exportXmlToOutComeNode',
        type : 'post',
        dataType : 'json',
        success : function(data) {
            tip(data.msg);
            if(data.success){
                reloadTable();
            }
        }
    });
}

</script>