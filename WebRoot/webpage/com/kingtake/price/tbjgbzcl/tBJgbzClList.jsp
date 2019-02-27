<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgbzClList" checkbox="true" fitColumns="true" title="价格库系统_差旅费" actionUrl="tBJgbzClController.do?datagrid" 
  idField="id" fit="true" queryMode="group" onDblClick="dblDetail" sortName="createDate" sortOrder="asc" extendParams="nowrap:false,">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="地区"  field="dq"   query="true" queryMode="single" isLike="true"  width="200"></t:dgCol>
   <t:dgCol title="大军区职以上以及相当职务人员"  field="djqz" align="center"   queryMode="single"  width="210"></t:dgCol>
   <t:dgCol title="军职以及相当职务人员"  field="jz"  align="center"  queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="师职以及相当职务人员"  field="sz"  align="center"  queryMode="single"  width="160"></t:dgCol>
   <t:dgCol title="团职以下以及相当职务人员"  field="tz"  align="center"  queryMode="single"  width="180"></t:dgCol>
   <t:dgCol title="伙食补助费标准"  field="hsbz"  align="center" overflowView="true"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="市内交通费标准"  field="snjt"  align="center"  queryMode="single"  width="150"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgbzClController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgbzClController.do?goUpdate" width="650" height="400" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgbzClController.do?goUpdate" width="650" height="400" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgbzClController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgbzClController.do?goUpdate" width="650" height="400" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
  </t:datagrid>
  </div>
  </div>
  <div region="south" style="height:100px;width:100%;padding:2px;">
  <table border="1px" cellspacing="0" cellpadding="5" style="border-color: #eee">
  	<tr>
  		<td>地区</td>
  		<td>大军区职以上以及相当职务人员</td>
  		<td>军职以及相当职务人员</td>
  		<td>师职以及相当职务人员</td>
  		<td>团职以下以及相当职务人员</td>
  		<td>伙食补助费标准</td>
  		<td>市内交通费标准</td>
  	</tr>
    <tr>
      <td><span id="dqView"></span></td>
      <td><span id="djqzView"></span></td>
      <td><span id="jzView"></span></td>
      <td><span id="szView"></span></td>
      <td><span id="tzView"></span></td>
      <td><span id="hsbzView"></span></td>
      <td><span id="snjtView"></span></td>
    </tr>
  </table>
  </div>
  
</div>


 <input id="jgkId" type="hidden" value="${jgkId}">
 <input id="jgkBeiz" type="hidden" value="${jgkBeiz}">
 <script src = "webpage/com/kingtake/price/tbjgbzcl/tBJgbzClList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgbzClListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzClListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			
 			addPlugin();//引入插件
 			
 			 $('#tBJgbzClList').datagrid({
 			    onLoadSuccess:function(data){
 		             $(this).datagrid("autoMergeCells");
 		        }
 			 });
 });
 
 $(function(){
	 $('#tBJgbzClList').datagrid({
		 onClickRow:function(index,data){
		 	$("#dqView").html(data.dq);
		 	$("#djqzView").html(data.djqz);
		 	$("#jzView").html(data.jz);
		 	$("#szView").html(data.sz);
		 	$("#tzView").html(data.tz);
		 	$("#hsbzView").html(data.hsbz);
		 	$("#snjtView").html(data.snjt);
		 }
	 })
 });
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 650;
	 var height = 450;
	 var url = "tBJgbzClController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
  
 
 function saveBeiz() {
	 var jgkid = $("#jgkId").val();
	 var jgkBeiz = $("#beiz").val();
     $.ajax({
         url : 'tBJgbzBeizController.do?doUpdate',
         data : {
             'id' : jgkid,
             'jgkdm' : "cl",
             'beiz': jgkBeiz
         },
         cache : false,
         type : 'POST',
         dataType : 'json',
         success : function(data) {
             if (data.success) {
            	 $("#jgkId").val(data.obj.id);
             }
             tip(data.msg);
         }
     });
 }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBJgbzClController.do?upload', "tBJgbzClList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgbzClController.do?exportXls","tBJgbzClList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgbzClController.do?exportXlsByT","tBJgbzClList");
}
 </script>