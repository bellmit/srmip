<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgQcsbList" checkbox="true" fitColumns="false" title="器材设备价格库" actionUrl="tBJgQcsbController.do?datagrid" 
  idField="id" fit="true" queryMode="group" onDblClick="dblDetail" sortName="createDate" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="平台名称"  field="ptmc"   query="true"  isLike="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="系统设备名称"  field="xtsbmc"   query="true" isLike="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="器材设备名称"  field="qcsbmc"   query="true" isLike="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="规格型号"  field="xhgg"  query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="计量单位"  field="jldw" query="true" isLike="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="承制单位"  field="czdw"  query="true" isLike="true"  queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="单价"  field="dj"    align="right"   queryMode="single"  extendParams="formatter:formatCurrency,"  width="100"></t:dgCol>
   <t:dgCol title="采购时间"  field="cgsj" align="center" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="审价单位"  field="sjdw"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="合同履行监督单位"  field="htlxjddw"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="备注"  field="beiz"    queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgQcsbController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgQcsbController.do?goUpdate" height="520" width="850" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgQcsbController.do?goUpdate" height="520" width="850" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgQcsbController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgQcsbController.do?goUpdate" height="520" width="850" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/tbjgqcsb/tBJgQcsbList.js"></script>	
 <script type="text/javascript" src="webpage/common/util.js"></script>	
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgQcsbListtb").find("input[name='cgsj']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgQcsbListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgQcsbListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 850;
	 var height = 520;
	 var url = "tBJgQcsbController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBJgQcsbController.do?upload', "tBJgQcsbList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgQcsbController.do?exportXls","tBJgQcsbList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgQcsbController.do?exportXlsByT","tBJgQcsbList");
}
 </script>