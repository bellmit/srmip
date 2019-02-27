<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<style type="text/css">
a:hover {
  color: red;
  text-decoration: none;
  cursor: hand;
}
</style>
  
<%--<div class="easyui-layout" fit="false" style="height:400px;">--%>
  <%--<div region="center" style="padding:1px;">--%>
<div style="width: 100%;height: 300px;">
  <input type="hidden" id="projectId" value="${projectId}" >
  
  <t:datagrid name="tPmPriceList" checkbox="true" fitColumns="true" title="报价"
  	actionUrl="tPmPriceController.do?datagrid&project.id=${projectId}" idField="id" 
  	fit="false" queryMode="group" onDblClick="dblDetail">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="项目_主键"  field="project.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="标题"  field="title" query="true" isLike="true" queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="关联合同"  field="contractName" extendParams="formatter:contractInfo," queryMode="single"  width="200"></t:dgCol>
   <t:dgCol title="创建人"  field="createBy"  hidden="true"  queryMode="group"  width="125"></t:dgCol>
   <t:dgCol title="创建人姓名"  field="createName"    queryMode="group"  width="100"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="修改人"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人姓名"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" frozenColumn="true"></t:dgCol>
   <t:dgDelOpt title="删除" url="tPmPriceController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tPmPriceController.do?goAdd&project.id=${projectId}" funname="add" width="450" height="300"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tPmPriceController.do?goUpdate" funname="update" width="450" height="300"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tPmPriceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tPmPriceController.do?goUpdate" funname="detail" width="400" height="300"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  <%--</div>--%>
 </div>
 
<script type="text/javascript">
//双击查看详情
function dblDetail(rowIndex, rowDate){
	var title = "查看";
	var width = 400;
	var height = 300;
	var url = "tPmPriceController.do?goUpdate&load=detail&id=" + rowDate.id;
	createdetailwindow(title,url,width,height);
}

$(document).ready(function(){
     //设置datagrid的title
     var projectName = window.parent.getParameter();
     $("#tPmPriceList").datagrid({
         title:projectName+'-报价',
         height:300
     });
});

//导入
function ImportXls() {
  openuploadwin('Excel导入', 'tPmPriceController.do?upload', "tPmPriceList");
}

//导出
function ExportXls() {
    var url = "tPmPriceController.do?exportXls";
    var projectId = $("#projectId").val();
    if(projectId!=""){
        url = url+"&project.id="+projectId;
    }
  JeecgExcelExport(url,"tPmPriceList");
}

//模板下载
function ExportXlsByT() {
  JeecgExcelExport("tPmPriceController.do?exportXlsByT","tPmPriceList");
}
//将合同类型显示在合同名称后面
function contractInfo(value,row,index){
  return value+'('+(row.contractType=='<%=com.kingtake.common.constant.ProjectConstant.INCOME_CONTRACT%>'?"进账":"出账")+')';
}
//附件格式化
function certificateFormatter(value,row,index){
    var certificateStr = "";
    var ids = row.certificates_id;
    if(ids!=""){
      var idArray = ids.split(",");
      var titlesArray = row.certificates_attachmenttitle.split(",");
      for(var i=0;i<idArray.length;i++){
          if(titlesArray[i]!=""){
          var title = titlesArray[i];
          if(title.length>10){
              title = title.substring(0,15)+"...";
          }else{
              title = titlesArray[i];
          }
          var str = "<a onclick=openwindow('预览','commonController.do?goAccessoryTab&bid="+row.id+"&index="+i+"'"+",'fList',1000,700) title="+titlesArray[i]+">"+title+"</a>";
          certificateStr = certificateStr+str+"|  ";
        }
      }
   }
    return certificateStr;
}
</script>
 