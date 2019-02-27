<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tdKycgList" checkbox="true" fitColumns="false" title="历史科研成果" actionUrl="tdKycgController.do?datagrid&projectId=${projectId}" idField="cgdm" fit="true" queryMode="group">
   <t:dgCol title="成果代码"  field="cgdm"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="xmdm"  field="xmdm"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="基层编号"  field="jcbh"  isLike="true" query="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="成果名称"  field="cgmc" isLike="true" query="true"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="完成单位"  field="wcdw"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目组联系人"  field="xmzlxr"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目组联系方式"  field="xmzlxrs"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定主持单位"  field="jglxr"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定主持人"  field="jglxfs"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定时间"  field="jdsj" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定单位"  field="jddd"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定形式"  field="jdxs"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="当前状态"  field="cgzt"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="申请日期"  field="sqrq" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="归档号"  field="gdh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="审查日期"  field="scrq" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="申请上报日期"  field="sbrq" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="鉴定审批号"  field="jdsph"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="通知编号"  field="tzbh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="成果上报日期"  field="clsbrq" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="水平评价"  field="sppj"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证书编号"  field="zsbh"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="第一完成人"  field="wcr"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证书领取人"  field="zslqr"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证书领取日期"  field="zslqrq" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="备注"  field="bz"    queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="操作" field="opt" width="100"></t:dgCol> --%>
   <%-- <t:dgDelOpt title="删除" url="tdKycgController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tdKycgController.do?goAdd" funname="add" width="100%" height="100%"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tdKycgController.do?goUpdate" funname="goUpdate" width="850" height="100%"></t:dgToolBar>
   <%-- <t:dgToolBar title="批量删除"  icon="icon-remove" url="tdKycgController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="tdKycgController.do?goUpdate" funname="goDetail" width="850" height="100%"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tdKycgListtb").find("input[name='jdsj_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='jdsj_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='sqrq_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='sqrq_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='scrq_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='scrq_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='sbrq_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='sbrq_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='clsbrq_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='clsbrq_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='zslqrq_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tdKycgListtb").find("input[name='zslqrq_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tdKycgController.do?upload', "tdKycgList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tdKycgController.do?exportXls","tdKycgList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tdKycgController.do?exportXlsByT","tdKycgList");
}

function goDetail(title,url, id,width,height){
    	var rowsData = $('#'+id).datagrid('getSelections');
    	if (!rowsData || rowsData.length == 0) {
    		tip('请选择查看行');
    		return;
    	}
    	if (rowsData.length > 1) {
    		tip('请选择一条记录再查看');
    		return;
    	}
        url += '&load=detail&cgdm='+rowsData[0].cgdm;
    	createdetailwindow(title,url,width,height);
}

//编辑
function goUpdate(title,url, id,width,height){
    	var rowsData = $('#'+id).datagrid('getSelections');
    	if (!rowsData || rowsData.length == 0) {
    		tip('请选择编辑行');
    		return;
    	}
    	if (rowsData.length > 1) {
    		tip('请选择一条记录再编辑');
    		return;
    	}
        url += '&cgdm='+rowsData[0].cgdm;
        createwindow(title,url,width,height);
}
 </script>