<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<input type="hidden" value="${projectId }" id="projectId"/>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmPayNodeList" checkbox="false" fitColumns="true" 
			actionUrl="tPmPayNodeController.do?datagrid&tpId=${projectId}" 
			idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc" onDblClick="dblDetail">
			
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="项目基_主键" field="tPId" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="支付时间" field="payTime" formatter="yyyy-MM-dd" query="true" queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="支付金额(元)" field="payAmount" queryMode="group" width="90" align="right" extendParams="formatter:formatCurrency,"></t:dgCol>
			<t:dgCol title="备注" field="memo" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建人id" field="createBy" hidden="true" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建人" field="createName" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" formatter="yyyy-MM-dd" queryMode="group" width="150" align="center"></t:dgCol>
			<t:dgCol title="审核人id" field="auditUserid" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="审核标志" field="auditFlag" queryMode="group" width="80" codeDict="0,SFBZ"></t:dgCol>
			<t:dgCol title="审核人" field="auditUsername" queryMode="group" width="80"></t:dgCol>
			<t:dgCol title="审核时间" field="auditTime" formatter="yyyy-MM-dd"queryMode="group" width="90" align="center"></t:dgCol>
			<t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" hidden="true" formatter="yyyy-MM-dd"queryMode="group" width="90" align="center"></t:dgCol>
			
			<t:dgToolBar title="查看" icon="icon-search" url="tPmPayNodeController.do?goUpdate" 
				funname="detail" width="600" height="400"></t:dgToolBar>
				
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/project/m2pay/tPmPayNodeList.js"></script>
<script type="text/javascript" src="webpage/common/util.js"></script>	
<script type="text/javascript">
	//双击查看详情
	function dblDetail(rowIndex, rowDate){
		var title = "查看";
		var width = 600;
		var height = 400;
		var url = "tPmPayNodeController.do?goUpdate&load=detail&id=" + rowDate.id;
		createdetailwindow(title,url,width,height);
	}

	$(document).ready(function(){
		//设置datagrid的title
		var projectName = window.parent.getParameter();
		$("#tPmPayNodeList").datagrid({
			title:projectName+'-支付节点管理'
		});
 
		//给时间控件加上样式
		$("#tPmPayNodeListtb").find("input[name='payTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='payTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		 $("#tPmPayNodeListtb").find("input[name='auditTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='auditTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("#tPmPayNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	});
	 
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'tPmPayNodeController.do?upload', "tPmPayNodeList");
	}
	
	//导出
	function ExportXls() {
		JeecgExcelExport("tPmPayNodeController.do?exportXls&tpId="+$("#projectId").val(),
				"tPmPayNodeList");
	}
	
	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("tPmPayNodeController.do?exportXlsByT","tPmPayNodeList");
	}
	
	//更新
	function updatePayNode(id){
	    gridname='tPmPayNodeList' ;
	    var url = 'tPmPayNodeController.do?goUpdate';
	    url += '&id='+id;
	    createwindow('编辑',url,600,400);
	}
</script>