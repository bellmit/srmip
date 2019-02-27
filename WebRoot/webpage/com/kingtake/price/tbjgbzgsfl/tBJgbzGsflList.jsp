<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgbzGsflList" checkbox="true" fitColumns="false" title="工时费率标准" actionUrl="tBJgbzGsflController.do?datagrid" 
  idField="id" fit="true" queryMode="group" onDblClick="dblDetail" sortName="createDate" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="单位"  field="dw"   query="true" isLike="true" queryMode="single"  width="350"></t:dgCol>
   <t:dgCol title="小时费率"  field="xsfl"  align="right"   queryMode="single"  extendParams="formatter:formatCurrency," width="250"></t:dgCol>
   <t:dgCol title="备注"  field="beiz"    queryMode="single"  width="420"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgbzGsflController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgbzGsflController.do?goUpdate" height="300" width="550" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgbzGsflController.do?goUpdate" height="300" width="550" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgbzGsflController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgbzGsflController.do?goUpdate" height="300" width="550" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
     <div region="south" style="height:100px;width:100%;padding:2px;">
  <table>
    <tr>
      <td align="left"><label class="Validform_label"> 备注: </label></td>
      <td class="value"><textarea id="beiz" style="width: 1200px;" class="inputxt" rows="5" name="beiz" datatype="byterange" min="0" max="1000">${jgkBeiz}</textarea> <span
        class="Validform_checktip"></span> <label class="Validform_label" style="display: none;">备注</label></td>
      <td align="right"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="saveBeiz()">保存</a></td>
    </tr>
  </table>
  </div>
  </div>
 <script src = "webpage/com/kingtake/tbjgbzgsfl/tBJgbzGsflList.js"></script>	
 <script type="text/javascript" src="webpage/common/util.js"></script>  	
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgbzGsflListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzGsflListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 550;
	 var height = 300;
	 var url = "tBJgbzGsflController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
 
 function saveBeiz() {
	 var jgkid = $("#jgkId").val();
	 var jgkBeiz = $("#beiz").val();
     $.ajax({
         url : 'tBJgbzBeizController.do?doUpdate',
         data : {
             'id' : jgkid,
             'jgkdm' : "gsfl",
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
	openuploadwin('Excel导入', 'tBJgbzGsflController.do?upload', "tBJgbzGsflList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgbzGsflController.do?exportXls","tBJgbzGsflList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgbzGsflController.do?exportXlsByT","tBJgbzGsflList");
}
 </script>