<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
	//编辑时审批已处理：提示不可编辑
	/* if(location.href.indexOf("tip=true") != -1){
		var parent = frameElement.api.opener;
		var msg = $("#tipMsg", parent.document).val();
		tip(msg);
	} */
	
});

/* function getValue(params, url){
	var temp = params.value;
	if(params.value && params.value!=''){
		$.ajax({
			async:false,
			type:'post',
			url:url,
			data:params,
			success:function(data){
				data = $.parseJSON(data);
				if(data && data.length == 1){
					temp = data[0].name;
				}
			}
		});
	}
	return temp;
} */
</script>
<body>
<!-- <div class="easyui-layout" fit="true"> -->
<%-- <input id="jsonArray" value="${array}" type="hidden"> --%>
<%-- <div data-options="region:'north'" style="height:200px;">
    <div id="tb" style="padding-left:20px;">
      <div style="margin-top:5px;">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="approveAgain()">重新申请</a>
      </div>
    </div>
      <table id="approvalList" fit="true" class="easyui-datagrid"
        data-options="singleSelect:true, pagination:true, rownumbers:true, border:false, toolbar:'#tb',
        url:'tBAppraisaApprovalController.do?datagridByApprId&apprId=${appraisalProjectId}&field=id,appraisalProject.projectName,createDate,appraisalProject.planUnitname,projectCharacter,contactUsername,totalAmount,auditStatus',
        onClickRow:clickRow,
        onLoadSuccess:initOpt">
        <thead>
          <tr>
<!--             <th data-options="field:'appraisalProject.projectName', width:125">项目名称</th> -->
            <th data-options="field:'createDate',width:120">创建时间</th>
            <th data-options="field:'appraisalProject.planUnitname',width:125">申请单位</th>
            <th data-options="field:'projectCharacter',width:120,formatter:function(value){var params = {codeType:'1', code:'CGJDXMXZ', value:value}; return getValue(params, 'tBCodeTypeController.do?getValue')}">项目性质</th>
            <th data-options="field:'contactUsername',width:120">联系人</th>
            <th data-options="field:'auditStatus',width:120,formatter:function(value){var params = {codeType:'1', code:'SPZT', value:value}; return getValue(params, 'tBCodeTypeController.do?getValue')}">状态</th>
            <th data-options="field:'totalAmount',width:120">总经费</th>
            <th field="opt" width="120" >操作</th>
          </tr>
        </thead>
      </table>
    </div>  --%>  
<!--     <div data-options="region:'center'" style="padding:5px;"> -->
<t:tabs id="cc" iframe="false" tabPosition="top" >
	<!-- 申请审批信息 -->
	<t:tab href="tBAppraisaApprovalController.do?goApproval&appraisalProject.id=${appraisalProjectId}&id=${apprId}&send=${send}&idFlag=${idFlag}"
		icon="icon-search" title="鉴定申请审批表" id="appr"></t:tab>
	
	<!-- 审批信息 -->
	<t:tab href="tPmApprLogsController.do?tPmApprLogs&apprId=${apprId}&apprType=${apprType}&send=false" 
icon="icon-search" title="审批记录表" id="apprLogs2"></t:tab>
</t:tabs>
<!--     </div> -->
<!--     </div> -->
<!-- <script type="text/javascript">
function clickRow(rowIndex, rowData){
  
}

function initOpt(data){
	var array = data.rows;
	for(var i=0;i<array.length;i++){
		console.log(array[i].auditStatus);
		if(array[i].auditStatus==3){
	$('#approvalList').datagrid('updateRow',{
		index: i,
		row: {
			opt: "[<a href='javascript:void(0)' onclick='saveNode()'>作废</a>]"
		}
	});
		}
	}
}

function approveAgain() {
	var selected = $("#approvalList").datagrid("getSelected");
}
</script> -->
</body>
