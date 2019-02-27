<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%-- <t:base type="jquery,easyui,tools,DatePicker"></t:base> --%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgbzHjsymxList" checkbox="true" fitColumns="false" title="环境试验费标准明细表" actionUrl="tBJgbzHjsymxController.do?datagrid&syxmid=${syxmid}" 
  idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="试验项目id"  field="syxmid"  treefield="fieldMap.syxmid" hidden="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收费标准"  field="sfbz"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="收费单位"  field="sfdw"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="试验设备"  field="sysb"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="beiz"    queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgbzHjsymxController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgbzHjsymxController.do?goUpdate&syxmid=${syxmid}" funname="add" height="320" width="520"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgbzHjsymxController.do?goUpdate" funname="update" height="320" width="520"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/tbjgbzhjsymx/tBJgbzHjsymxList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgbzHjsymxListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzHjsymxListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBJgbzHjsymxController.do?upload', "tBJgbzHjsymxList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgbzHjsymxController.do?exportXls","tBJgbzHjsymxList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgbzHjsymxController.do?exportXlsByT","tBJgbzHjsymxList");
}
 </script>