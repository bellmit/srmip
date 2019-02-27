<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBJgbzJdhyList" checkbox="true" fitColumns="true" title="军队会议费" actionUrl="tBJgbzJdhyController.do?datagrid" 
  idField="id" fit="true" queryMode="group" onDblClick="dblDetail" sortName="createDate" sortOrder="asc" extendParams="nowrap:false,">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="招待所类别"  field="zdslb"   query="true" isLike="true" queryMode="single" overflowView="true"  width="400"></t:dgCol>
   <t:dgCol title="一类会议"  field="ylhy" align="center"    queryMode="single"  width="110" ></t:dgCol>
   <t:dgCol title="二类会议"  field="elhy"  align="center"   queryMode="single"  width="110" ></t:dgCol>
   <t:dgCol title="四类会议"  field="silhy"  align="center"   queryMode="single"  width="110" ></t:dgCol>
   <t:dgCol title="三类会议"  field="sanlhy" align="center"    queryMode="single"  width="110"></t:dgCol>
   <t:dgCol title="备注"  field="beiz"    queryMode="single"  width="250"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBJgbzJdhyController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tBJgbzJdhyController.do?goUpdate" height="420" width="550" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBJgbzJdhyController.do?goUpdate" height="420" width="550" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBJgbzJdhyController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBJgbzJdhyController.do?goUpdate" height="420" width="550" funname="detail"></t:dgToolBar>
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
 <script src = "webpage/com/kingtake/price/tbjgbzjdhy/tBJgbzJdhyList.js"></script>	
 <script type="text/javascript" src="webpage/common/util.js"></script>	
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBJgbzJdhyListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBJgbzJdhyListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 //双击查看详情
 function dblDetail(rowIndex, rowDate){
	 var title = "查看";
	 var width = 550;
	 var height = 420;
	 var url = "tBJgbzJdhyController.do?goUpdate&load=detail&id=" +rowDate.id;
	 createdetailwindow(title,url,width,height);	 
 }
 
 
 function saveBeiz() {
	 var jgkid = $("#jgkId").val();
	 var jgkBeiz = $("#beiz").val();
     $.ajax({
         url : 'tBJgbzBeizController.do?doUpdate',
         data : {
             'id' : jgkid,
             'jgkdm' : "jdhy",
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
	openuploadwin('Excel导入', 'tBJgbzJdhyController.do?upload', "tBJgbzJdhyList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBJgbzJdhyController.do?exportXls","tBJgbzJdhyList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBJgbzJdhyController.do?exportXlsByT","tBJgbzJdhyList");
}
 </script>