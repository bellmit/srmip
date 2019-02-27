<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tPmContractNodeList" checkbox="false" fitColumns="false" 
  	actionUrl="tPmContractNodeController.do?datagrid&inOutContractid=${contractId}" 
  	idField="id" fit="true" queryMode="group" pagination="false" sortName="createDate">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="合同主键"  field="inOutContractid"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="支付（或来款）节点主键"  field="projPayNodeId" hidden="true" queryMode="group"  align="right"  width="120"></t:dgCol>
   <t:dgCol title="节点名称"  field="contractNodeName" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="prjType"  queryMode="group"  width="80" codeDict="1,HTJDLX" hidden="true"></t:dgCol>
   <t:dgCol title="计划/合同标志"  field="planContractFlag"  hidden="true"  queryMode="group" replace="计划_1,合同_2" width="80"></t:dgCol>
   <t:dgCol title="进出帐标志"  field="inOutFlag"  hidden="true"  queryMode="group"  width="120"></t:dgCol>      
   <t:dgCol title="完成时间"  field="completeDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="成果形式"  field="resultForm" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="评价方法"  field="evaluationMethod"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="当前节点百分比"  field="payPercent" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="累计比例"  field="cumulativeProportion" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="节点金额"  field="payAmount" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remarks"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createName" hidden="true"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"  hidden="true" queryMode="group"  width="90" align="center"></t:dgCol>
   
   <c:if test="${node}">
       <t:dgCol title="操作" field="opt" width="80" frozenColumn="true"></t:dgCol>
   	   <t:dgFunOpt title="删除"  funname="delNodeFun(id)" ></t:dgFunOpt>
       <t:dgToolBar title="录入合同节点" icon="icon-add" url="tPmContractNodeController.do?goAdd" 
       	funname="judgeBuforeAddNode" width="700" height="450"></t:dgToolBar>
       <t:dgToolBar title="编辑合同节点" icon="icon-edit" url="tPmContractNodeController.do?goUpdate" 
       	funname="updateChildFun" width="700" height="450"></t:dgToolBar>
   </c:if>
   <t:dgToolBar title="查看合同节点" icon="icon-search" url="tPmContractNodeController.do?goUpdate" 
   	funname="detailChildFun" width="700" height="450"></t:dgToolBar>
   	
  </t:datagrid>
  </div>
 </div>
<script src = "webpage/com/kingtake/project/contractnode/tPmContractNodeList.js"></script>
<script src = "webpage/com/kingtake/apprUtil/apprUtil.js"></script>
<script type="text/javascript">
//添加合同节点
function judgeBuforeAddNode(title,addurl,gname,width,height){
	var contractId = $("#id").val();//审批表主键
	if(contractId){
		var contractType = $("#inOrOutContract").val();
		addurl += "&inOutContractid=" + contractId + "&inOutFlag=" + contractType;
		addChildFun(title,addurl,gname,width,height);
	}else{
		$("#callBackType").val("node");
		confirmChildDialog("添加节点前会保存合同信息，确定添加吗？");
	}
}

//删除合同节点
function delNodeFun(id){
	var url = "tPmContractNodeController.do?doDel&id=" + id;
	var gname = "tPmContractNodeList";
	delChildDialog(url, gname);
}

//根据合同审批表id刷新合同节点
function reloadNodeList(){
	var contractId = $("#id").val();
	$('#tPmContractNodeList').datagrid('reload', {'inOutContractid':contractId});
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tPmContractNodeListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmContractNodeListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmContractNodeListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmContractNodeListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmContractNodeListtb").find("input[name='completeDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmContractNodeListtb").find("input[name='completeDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
});

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmContractNodeController.do?upload', "tPmContractNodeList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmContractNodeController.do?exportXls","tPmContractNodeList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmContractNodeController.do?exportXlsByT","tPmContractNodeList");
}
</script>
