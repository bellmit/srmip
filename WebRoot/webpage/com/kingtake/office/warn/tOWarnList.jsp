<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
    $("#tOWarnListtb").find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()); 
});
</script>
<div id="tempSearchColums" style="display: none">
  <div name="searchColums">
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="<t:mutiLang langKey="common.department"/>">
                     提醒状态：
            </span>
            <t:codeTypeSelect name="warnStatus" type="select" codeType="0" code="WARNSTATUS" labelText="全选" id=""></t:codeTypeSelect>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_list">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tOWarnList" checkbox="true" fitColumns="true" title="公共提醒信息表" 
  	actionUrl="tOWarnController.do?datagrid" idField="id" fit="true" 
  	queryMode="group" onClick="toQuery">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="提醒内容"  field="warnContent"  queryMode="single" query="true" 
   	isLike="true" width="150" frozenColumn="true"></t:dgCol>
   <t:dgCol title="类型"  field="warnType" codeDict="0,WARNTYPE"   queryMode="group"  
   	width="70"></t:dgCol>
   <%-- <t:dgCol title="关联业务url"  field="formUrl"    queryMode="group"  width="120"></t:dgCol> --%>
   <t:dgCol title="提醒时限范围开始时间"  field="warnBeginTime" formatter="yyyy-MM-dd hh:mm:ss"   
   	queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="提醒时限范围结束时间"  field="warnEndTime" formatter="yyyy-MM-dd hh:mm:ss"   
   	queryMode="group"  width="150" align="center"></t:dgCol>
   <t:dgCol title="提醒状态"  field="warnStatus"  codeDict="0,WARNSTATUS" queryMode="single"  
   	width="70"></t:dgCol>
   <t:dgCol title="提醒频率"  field="warnFrequency" codeDict="0,WARNFREQUENCY"    
   	queryMode="group"  width="70"></t:dgCol>
   <t:dgCol title="提醒时间点"  field="warnTimePoint" queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="提醒方式"  field="warnWay" codeDict="0,WARNWAY"  queryMode="group"  
   	width="70"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130"></t:dgCol>
   <t:dgDelOpt title="删除" url="tOWarnController.do?doDel&id={id}" />
   <t:dgFunOpt funname="doNothing" title="查看接收人"></t:dgFunOpt>
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="tOWarnController.do?goAddUpdate" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tOWarnController.do?goAddUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tOWarnController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tOWarnController.do?goAddUpdate" funname="detail"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="添加提醒" icon="icon-add" funname="goCommonWarn"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
  </div>
  <div data-options="region:'east',
	title:'mytitle',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 700px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="subListpanel"></div>
</div>
</div>
 <script src = "webpage/com/kingtake/office/warn/tOWarnList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tOWarnListtb").find("input[name='warnBeginTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='warnBeginTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='warnEndTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='warnEndTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tOWarnListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			
 		    var datagrid = $("#tOWarnList");
 		    datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tOWarnController.do?upload', "tOWarnList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tOWarnController.do?exportXls","tOWarnList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tOWarnController.do?exportXlsByT","tOWarnList");
}


//单击查看
function toQuery(rowIndex, rowData){
	var id = rowData.id;
	queryTypeValue(id);
}

//相应行点击事件即可
function doNothing(){
	return;
}
//添加公共提醒
function goCommonWarn(){
    add("添加公共提醒","tOWarnController.do?goCommonWarn");
}
 </script>