<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tErExpertList" checkbox="true" fitColumns="false" title="专家信息" actionUrl="tErExpertController.do?datagrid" 
  	idField="id" fit="true" queryMode="group" sortName="expertNum">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="编号"  field="expertNum"  query="true" isLike="true"  queryMode="single"  width="80" ></t:dgCol>
   <t:dgCol title="姓名"  field="name" query="true" isLike="true"  queryMode="single"  width="60"></t:dgCol>
   <t:dgCol title="性别"  field="sex"    queryMode="group" codeDict="0,SEX" width="40"></t:dgCol>
   <t:dgCol title="出生年月"  field="expertBirthDate" formatter="yyyy-MM-dd"  hidden="true" queryMode="group"  width="90" align="center"></t:dgCol>
   <t:dgCol title="身份证件类型"  field="idType"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="证件号码"  field="idNo"   hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="单位id"  field="expertDepartId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="单位名称"  field="expertDepartName"  query="true" isLike="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="专业"  field="expertProfession"  query="true" isLike="true"  queryMode="single" codeDict="1,MAJOR" width="120"></t:dgCol>
   <t:dgCol title="职称"  field="expertPosition"    queryMode="group" codeDict="1,ZHCH" width="120"></t:dgCol>
   <t:dgCol title="执业资格"  field="expertPraciticReq"  hidden="true"  queryMode="group" codeDict="1,ZYZG" width="120"></t:dgCol> 
   <t:dgCol title="地区"  field="expertDistrict" hidden="true"   queryMode="group"  codeDict="0,NATIVEPLACE" width="120"></t:dgCol>
   <t:dgCol title="电话/手机"  field="expertPhone"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="简介"  field="expertSummary"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%-- <t:dgCol title="有效标记"  field="expertValidFlag"    queryMode="group" codeDict="0,SFBZ" width="60"></t:dgCol> --%>
   <t:dgCol title="是否正式专家"  field="isOfficial"    queryMode="group" codeDict="0,SFBZ" width="90"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tErExpertController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="tErExpertController.do?goAdd" funname="add" width="650" height="400"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tErExpertController.do?goUpdate" funname="update" width="650" height="400"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tErExpertController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tErExpertController.do?goUpdate" funname="detail" width="600" height="350"></t:dgToolBar>
   <%-- <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar> --%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="重置密码" icon="icon-reload" funname="resetPassword"></t:dgToolBar>
   <%-- <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/kingtake/expert/info/tErExpertList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tErExpertListtb").find("input[name='expertBirthDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tErExpertListtb").find("input[name='expertBirthDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tErExpertController.do?upload', "tErExpertList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tErExpertController.do?exportXls","tErExpertList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tErExpertController.do?exportXlsByT","tErExpertList");
}

function resetPassword(){
    var rows = $("#tErExpertList").datagrid("getChecked");
    if(rows.lenth==0){
        tip("请先选择专家");
        return ;
    }
    var expertIds = [];
    for(var i=0;i<rows.length;i++){
        expertIds.push(rows[i].id);
    }
    $.ajax({
        url : 'tErExpertController.do?resetPwd',
        data : {"ids":expertIds.join(",")},
        dataType : 'json',
        type : 'POST',
        success : function(data) {
                tip(data.msg);
                if(data.success){
                    $("#tErExpertList").datagrid("reload");
                }
        }
    });
}

/* $(function(){
	$("input[name='expertDepartName']").combotree({
		url:'departController.do?getDepartTree', 
		multiple:false,
		editable:true, 
		cascadeCheck:false
	});
	
	$("input[name='expertProfession']").combo({
		valueField: 'code',    
        textField: 'name', 
		url : 'tBCodeTypeController.do?getCodeTypeList&codeType=1&code=MAJOR',
	});
}); */
 </script>