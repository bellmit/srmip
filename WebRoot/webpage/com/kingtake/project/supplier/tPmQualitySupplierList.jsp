<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
$(function(){
});

function validFormatter(value,row,index){
    if(value=='1'){
        return '有效';
    }else if(value=='0'){
        return '<font color="red">失效</font>';
    }else{
        return '<font color="red">待审</font>';
    }
  }
  
</script>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding:1px;">
		<t:datagrid name="tPmQualitySupplierList" checkbox="true" fitColumns="true" title='供方名录信息表<a href="#" onclick=toastrMsg("qualitySupplier") style="color: red">(查看说明)</a>' 
			actionUrl="tPmQualitySupplierController.do?datagrid" idField="id" fit="true" queryMode="group"
			onDblClick="detailTPmQualitySupplierList">
		
			<t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="供方名称" field="supplierName" isLike="true" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="等级" field="supplierLevel" hidden="true" codeDict="1,GFDJ" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="类别" field="supplierType" codeDict="1,GFLB" query="true" queryMode="single" width="120"></t:dgCol>
			<t:dgCol title="提供产品" field="supplierProduct" query="true" queryMode="single" width="120" overflowView="true"></t:dgCol>
			<t:dgCol title="通信地址" field="supplierAddress"  queryMode="group" width="120" hidden="true"></t:dgCol>
			<t:dgCol title="邮编" field="supplierPostcode" queryMode="group" width="80" hidden="true"></t:dgCol>
			<t:dgCol title="联系电话" field="supplierPhone" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="联系人" field="supplierContact" query="true" isLike="true" queryMode="single" width="100"></t:dgCol>
			<t:dgCol title="传真" field="supplierFax"  queryMode="group" width="120" hidden="true"></t:dgCol>
      <t:dgCol title="承制资格" field="isQualify" queryMode="single" query="true" width="80" codeDict="0,SFBZ"></t:dgCol>
      <t:dgCol title="临时供方" field="isTemp" queryMode="single" query="true" width="80" codeDict="0,SFBZ"></t:dgCol>
			<t:dgCol title="供方状态" field="validFlag" queryMode="single" query="false" width="90" extendParams="formatter:validFormatter,"></t:dgCol>
			<t:dgCol title="开户行名称" field="bankName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开户行行号" field="bankNum" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="开户行地址" field="bankAddress" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="企业法人" field="enterpriseLegalPerson" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="组织机构代码" field="organizationCode" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="企业登记地址" field="registerAddress" hidden="false" queryMode="single" query="true" overflowView="true" isLike="true" width="120"></t:dgCol>
      <t:dgCol title="审核人" field="auditUserName" query="false"  queryMode="group" width="70"></t:dgCol>
      <t:dgCol title="审核时间" field="auditTime" query="false" formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="失效时间" field="supplierTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="100"></t:dgCol>
			<t:dgCol title="失效原因" field="reason" query="true" isLike="true" queryMode="single" width="100" overflowView="true"></t:dgCol>
			<%-- <t:dgCol title="创建人" field="createBy" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="创建人姓名" field="createName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="创建时间" field="createDate" hidden="true" query="false" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
			<%-- <t:dgCol title="修改人" field="updateBy" hidden="true" queryMode="group" width="120"></t:dgCol> --%>
			<t:dgCol title="修改人姓名" field="updateName" hidden="true" queryMode="group" width="120"></t:dgCol>
			<t:dgCol title="修改时间" field="updateDate" formatter="yyyy-MM-dd" hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
			
			<t:dgCol title="操作" field="opt" width="250" frozenColumn="true"></t:dgCol>
			<t:dgDelOpt title="删除" url="tPmQualitySupplierController.do?doDel&id={id}" />
			<t:dgFunOpt title="资质审核" funname="goAudit(id)"  />
			<t:dgFunOpt title="合作项目" funname="goCooperation(id)"  />
			<t:dgToolBar title="录入" icon="icon-add" url="tPmQualitySupplierController.do?goUpdate" 
				funname="add" height="400"></t:dgToolBar>
			<t:dgToolBar title="编辑" icon="icon-edit" url="tPmQualitySupplierController.do?goUpdate" 
				funname="update" height="400"></t:dgToolBar>
			<t:dgToolBar title="批量删除" icon="icon-remove" url="tPmQualitySupplierController.do?doBatchDel"
				 funname="deleteALLSelect"></t:dgToolBar>
			<t:dgToolBar title="查看" icon="icon-search" url="tPmQualitySupplierController.do?goUpdate" 
				funname="detail" height="400"></t:dgToolBar>
			<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
            <t:dgToolBar title="设置失效年限" icon="icon-clock" funname="setTimeEnd"></t:dgToolBar>
		</t:datagrid>
	</div>
</div>
<script src = "webpage/com/kingtake/project/supplier/tPmQualitySupplierList.js"></script>		
<script type="text/javascript">
function detailTPmQualitySupplierList(rowIndex, rowDate){
	var title = "查看";
	var url = "tPmQualitySupplierController.do?goUpdate&id=" + rowDate.id+"&load=detail";
	var height = 400;
	var height = window.top.document.body.offsetHeight-100;
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
		    id:'supplierCooprate',
			content: 'url:'+url,
			lock : true,
			width:width,
			height: height,
			title:title,
			opacity : 0.3,
			fixed:true,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}else{
		W.$.dialog({
		    id:'supplierCooprate',
			content: 'url:'+url,
			lock : true,
			width:width,
			height: height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			fixed:true,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true 
		}).zindex();
	}
}

$(document).ready(function(){
	//给时间控件加上样式
	$("#tPmQualitySupplierListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySupplierListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySupplierListtb").find("input[name='supplierTime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySupplierListtb").find("input[name='supplierTime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
	$("#tPmQualitySupplierListtb").find("input[name='registerAddress']").attr("style","height:20px;width:230px;");

});
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tPmQualitySupplierController.do?upload', "tPmQualitySupplierList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tPmQualitySupplierController.do?exportXls","tPmQualitySupplierList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tPmQualitySupplierController.do?exportXlsByT","tPmQualitySupplierList");
}

function goAudit(id,index){
    var url = "tPmQualitySupplierController.do?goAudit&id="+id;
    createwindow("资质审核详情",url,800,400);
}

function goCooperation(id,index){
    var url = "tPmQualitySupplierController.do?cooperationList&supplierId="+id;
    createdetailwindow("合作项目",url,800,400);
}

//设置失效年限
function setTimeEnd(){
    var title = '设置失效年限<font color="red">（超过年限没有合作则自动失效,设置为0默认不启用）</font>';
    var width = 500;
    var height = 100;
    var url = "tPmQualitySupplierController.do?goEndTimeSet";
    $.dialog({
            content : 'url:' + url,
            lock : true,
            width : width,
            height : height,
            title : title,
            opacity : 0.3,
            cache : false,
            ok : function() {
                iframe = this.iframe.contentWindow;
                var sxnx = iframe.getSxnx();
                if (sxnx==""||sxnx==undefined) {
                    return false;
                } else {
                    $.ajax({
                        url : 'tPmQualitySupplierController.do?doEndTimeSet',
                        data : {
                            sxnx : sxnx
                        },
                        cache : false,
                        type : 'POST',
                        dataType : 'json',
                        success : function(data) {
                            tip(data.msg);
                        }
                    });
                }
            },
            cancelVal : '关闭',
            cancel : true
        }).zindex();
    }
</script>
 <%@ include file="/webpage/com/kingtake/common/toastr/toastr.jsp"%>
 </body>
 </html>