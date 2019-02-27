<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgbzZjzxList" checkbox="true" fitColumns="false" title="专家咨询费标准" actionUrl="tBJgbzZjzxController.do?datagrid" 
  idField="id" fit="true" queryMode="group" onDblClick="dblDetail" sortName="createDate" sortOrder="asc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="咨询专家"  field="zxzj"  codeDict="1,ZXZJ" query="true" queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="咨询方式"  field="zxfs" codeDict="1,ZXFS"  query="true" queryMode="single"  width="220"></t:dgCol>
   <t:dgCol title="标准"  field="bz"   query="true" isLike="true" queryMode="single"  width="300"></t:dgCol>
   <t:dgCol title="备注"  field="beiz"    queryMode="single"  width="300"></t:dgCol>   
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgbzZjzxController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgbzZjzxController.do?goUpdate" height="400" width="520" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgbzZjzxController.do?goUpdate" height="400" width="520" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgbzZjzxController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgbzZjzxController.do?goUpdate" height="400" width="520" funname="detail"></t:dgToolBar>
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
 <script src = "webpage/com/kingtake/tbjgbzzjzx/tBJgbzZjzxList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgbzZjzxListtb").find("input[name='zxsj_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzZjzxListtb").find("input[name='zxsj_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzZjzxListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzZjzxListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 520;
	 var height = 400;
	 var url = "tBJgbzZjzxController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
 
 function saveBeiz() {
	 var jgkid = $("#jgkId").val();
	 var jgkBeiz = $("#beiz").val();
     $.ajax({
         url : 'tBJgbzBeizController.do?doUpdate',
         data : {
             'id' : jgkid,
             'jgkdm' : "zjzx",
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
	openuploadwin('Excel导入', 'tBJgbzZjzxController.do?upload', "tBJgbzZjzxList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgbzZjzxController.do?exportXls","tBJgbzZjzxList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgbzZjzxController.do?exportXlsByT","tBJgbzZjzxList");
}
 </script>