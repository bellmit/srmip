<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
function fileFormatter(value,row,index){
    return '<a href="'+row.filePath+'" style="color:red;text-decoration: underline;">'+value+'</a>';
}
</script>
<div class="easyui-layout" fit="true" >
  <div region="center" style="padding:1px;">
  <t:datagrid name="dbImportList" checkbox="true" fitColumns="true" title="项目数据导入" 
  		actionUrl="dbImportController.do?datagrid" 
 	 	idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作人"  field="impUserName"  queryMode="single" width="120"></t:dgCol>
   <t:dgCol title="操作时间"  field="impTime"  queryMode="single"  width="120"></t:dgCol>
<%--    <t:dgCol title="文件名"  field="fileName"  queryMode="group" width="120" extendParams="formatter:fileFormatter,"></t:dgCol> --%>
   <t:dgCol title="文件名"  field="fileName"  queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="路径"  field="filePath" hidden="true" queryMode="group" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="130" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="dbImportController.do?doDel&id={id}" />
<%--    <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
<%--    <t:dgToolBar title="导出" icon="icon-put" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="导出项目给财务" icon="icon-put" funname="ExportXmlToProject"></t:dgToolBar>
   <t:dgToolBar title="导出外协信息给财务" icon="icon-put" funname="ExportXmlToOutCome"></t:dgToolBar>
   	<t:dgToolBar title="导出外协合同支付节点给财务" icon="icon-put" funname="ExportXmlToOutComeNode"></t:dgToolBar>
   <t:dgToolBar title="导出预算主表" icon="icon-put" funname="ExportXmlToYszb"></t:dgToolBar>
	<t:dgToolBar title="导出预算明细" icon="icon-put" funname="ExportXmlToYsmx"></t:dgToolBar>
	<t:dgToolBar title="导出到款分配主表" icon="icon-put" funname="ExportXmlToDkzb"></t:dgToolBar>
	<t:dgToolBar title="导出到款分配明细" icon="icon-put" funname="ExportXmlToDkmx"></t:dgToolBar>
	<t:dgToolBar title="导出到款分配发票登记明细" icon="icon-put" funname="ExportXmlToFpmx"></t:dgToolBar>
	<t:dgToolBar title="导出校内协作" icon="icon-put" funname="ExportXmlToXnxz"></t:dgToolBar>
	<t:dgToolBar title="导出校内协作明细" icon="icon-put" funname="ExportXmlToXnmx"></t:dgToolBar>
	<t:dgToolBar title="导出垫支经费" icon="icon-put" funname="ExportXmlToDzjf"></t:dgToolBar>
	<t:dgToolBar title="导出调整预算" icon="icon-put" funname="ExportXmlToTzys"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="导出计划主表" icon="icon-put" funname="ExportXmlToJhzb"></t:dgToolBar> --%>
<%-- 	<t:dgToolBar title="导出计划明细 " icon="icon-put" funname="ExportXmlToJhmx"></t:dgToolBar> --%>
	<t:dgToolBar title="导出数据给财务" icon="icon-put" funname="ExportXmlToCwOpen"></t:dgToolBar>
	<t:dgToolBar title="导入财务数据包" icon="icon-put" funname="ImportCw"></t:dgToolBar>
	<t:dgToolBar title="生成到款信息单据" icon="icon-putout" funname="ExportDkWord"></t:dgToolBar>
  </t:datagrid>
  </div>
  </div>
</div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
//导入
function ImportXls() {
	openuploadwin('数据导入', 'dbImportController.do?upload', "dbImportList");
}

//导出
function ExportXls() {
	JeecgExcelExport("dbImportController.do?exportProjectXls","dbImportList");
}


//导出项目信息给财务
function ExportXmlToProject() {
	$.ajax({
      url : 'dbImportController.do?exportXmlToProject',
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
      url : 'dbImportController.do?exportXmlToOutCome',
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
      url : 'dbImportController.do?exportXmlToOutComeNode',
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

//导出预算主表信息给财务
function ExportXmlToYszb() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToYszb',
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

//导出预算明细信息给财务
function ExportXmlToYsmx() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToYsmx',
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

//导出到款主表信息给财务
function ExportXmlToDkzb() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToDkzb',
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

//导出到款明细信息给财务
function ExportXmlToDkmx() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToDkmx',
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

//导出到款发票明细信息给财务
function ExportXmlToFpmx() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToFpmx',
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

//导出校内协作
function ExportXmlToXnxz() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToXnxz',
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

//导出校内协作明细
function ExportXmlToXnmx() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToXnmx',
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

//导出垫支经费
function ExportXmlToDzjf() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToDzjf',
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

//导出调整预算
function ExportXmlToTzys() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToTzys',
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

//导出计划主表
function ExportXmlToJhzb() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToJhzb',
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

//导出计划明细
function ExportXmlToJhmx() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToJhmx',
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

//导出计划明细
function ExportXmlToCw() {
	$.ajax({
        url : 'dbImportController.do?exportXmlToCw',
        type : 'post',
        dataType : 'json',
        success : function(data) {
//             tip(data.msg);
            if(data.success){
            	ExportXmlToCwNew();
            	$('#dbImportList').datagrid('reload');
            }
        }
    });
}

//导入到款信息
function ImportCw() {
    openwindow('数据导入', 'dbImportController.do?ImportCw', "dbImportList");
}

//导出数据给财务
function ExportXmlToCwNew() {
	JeecgExcelExport("dbImportController.do?downloadTemplate","dbImportList");
}

//导出数据给财务
function ExportXmlToCwOpen() {
	add('数据导出', 'dbImportController.do?exportXmlToCwOpen', "dbImportList");
}

//导出到款信息WORD
// function ExportDkWord(title,url,gname,width,height) {
// 	gridname = gname;	
			
// 	JeecgExcelExport("dbImportController.do?ExportDkWord",gridname);       
// }

//导出到款信息WORD
function ExportDkWord() {
	$.ajax({
        url : 'dbImportController.do?createWord',
        type : 'post',
        dataType : 'json',
        success : function(data) {
        	downloadDkWord(data.attributes.FileName);
        }
    });
}

//下载 到款信息WORD
function downloadDkWord(FileName) {
	JeecgExcelExport("dbImportController.do?downloadWord&FileName=" + FileName,"dbImportList");
}

 </script>