<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 1px;">
		<t:datagrid name="tBAppraisalProjectList" fitColumns="true" title="鉴定计划"
			actionUrl="tBAppraisalProjectController.do?datagridSummary"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group"></t:dgCol>
			<t:dgCol title="年度" field="planYear" query="true" queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="成果名称" field="achievementName" query="true" isLike="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="承研单位" field="projectDeveloperDepartname" width="100"></t:dgCol>
			<t:dgCol title="项目组长" field="projectManagerName" width="100"></t:dgCol>
			<t:dgCol title="鉴定主持单位" field="appraisalUnit" width="100"></t:dgCol>
			<t:dgCol title="主持单位联系人" field="appraisalContact" width="100"></t:dgCol>
			<t:dgCol title="主持单位联系电话" field="appraisalContactPhone" width="100"></t:dgCol>
			<t:dgCol title="鉴定时间" field="appraisalTime" query="true" queryMode="group" formatter="yyyy-MM-dd" width="90" align="center"></t:dgCol>
			<t:dgCol title="鉴定地点" field="appraisalAddress" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="鉴定形式" field="appraisalForm" queryMode="group" width="100" codeDict="1,JDXS"></t:dgCol>
			<t:dgCol title="备注" field="memo" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="状态" field="state" codeDict="1,SPZT" width="100"></t:dgCol>
			<%-- <t:dgToolBar title="录入" icon="icon-add" url="tBAppraisalProjectController.do?goAdd&projectId=${projectId }"
				height="380" width="630" funname="add"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tBAppraisalProjectController.do?goUpdate" 
				height="380" width="630" funname="update"></t:dgToolBar> --%>
			<t:dgToolBar title="查看" icon="icon-search" url="tBAppraisalProjectController.do?goUpdate"
				height="380" width="630" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="汇总" icon="icon-putout" funname="summary"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>

<script src = "webpage/com/kingtake/project/appraisal/tBAppraisalProjectList.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//给时间控件加上样式
	$("#tBAppraisalProjectListtb").find("input[name='planYear']")
		.attr("class","Wdate").attr("style","height:25px;width:100px;")
		.click(function(){WdatePicker({dateFmt:'yyyy'});});
	$("#tBAppraisalProjectListtb").find("input[name='appraisalTime_begin']")
		.attr("class","Wdate").attr("style","height:25px;width:100px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tBAppraisalProjectListtb").find("input[name='appraisalTime_end']")
		.attr("class","Wdate").attr("style","height:25px;width:100px;")
		.click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});

//导出
function summary() {
    $.ajax({
		cache : false,
		type : 'POST',
		url : "tBAppraisalProjectController.do?getSelectYear",// 请求的action路径
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.length>0) {
			    var yearSelect = $("<select id='yearSelect' name='planYear'></select>");
			    for(var i=0;i<d.length;i++){
			    	yearSelect.append("<option value=\""+d[i]+"\">"+d[i]+"</option>");
			    }
			    if (typeof (windowapi) == 'undefined') {
		            $.dialog({
		                content : $('<div id="dialogDiv">请选择导出年份：</div>').append(yearSelect),
		                zIndex : 2100,
		                title : '选择年份',
		                lock : true,
		                width : '400px',
		                height : '100px',
		                left : '50%',
		                top : '50%',
		                opacity : 0.4,
		                button : [ {
		                    name : '确定',
		                    callback : function(){
		                        var planYear =window.top.$("#dialogDiv #yearSelect").val();
		                        var name = $("#tBAppraisalProjectListtb").find("input[name='projectName']").val();
		                        JeecgExcelExport("tBAppraisalProjectController.do?summaryExport&year="+planYear+"&name="+name, "tBAppraisalProjectList");
		                    },
		                    focus : true
		                }, {
		                    name : '取消',
		                    callback : function() {
		                    }
		                } ]
		            });
		        }
			}
		}
	});
}

/**
 * 提交鉴定计划给机关
 */
function doSubmit(id){
	$.messager.confirm('确认','您确认将鉴定计划提交吗？',function(r){    
	    if (r){    
	        $.ajax({
	        	type : 'post',
	        	data : {id : id},
	        	url : "tBAppraisalProjectController.do?doSubmit",
	        	success : function(result){
	        		result = $.parseJSON(result);
	        		$('#tBAppraisalProjectList').datagrid('reload');    
	        		showMsg(result.msg);
	        	}
	        }); 
	    }    
	});  
}

</script>